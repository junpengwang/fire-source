/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.FirebaseError;
/*     */ import com.firebase.client.core.operation.Operation;
/*     */ import com.firebase.client.core.operation.OperationSource;
/*     */ import com.firebase.client.core.persistence.PersistenceManager;
/*     */ import com.firebase.client.core.view.CacheNode;
/*     */ import com.firebase.client.core.view.Change;
/*     */ import com.firebase.client.core.view.DataEvent;
/*     */ import com.firebase.client.core.view.Event;
/*     */ import com.firebase.client.core.view.Event.EventType;
/*     */ import com.firebase.client.core.view.QueryParams;
/*     */ import com.firebase.client.core.view.QuerySpec;
/*     */ import com.firebase.client.core.view.View;
/*     */ import com.firebase.client.core.view.View.OperationResult;
/*     */ import com.firebase.client.core.view.ViewCache;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.utilities.Pair;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class SyncPoint
/*     */ {
/*     */   private final Map<QueryParams, View> views;
/*     */   private final PersistenceManager persistenceManager;
/*     */   
/*     */   public SyncPoint(PersistenceManager persistenceManager)
/*     */   {
/*  39 */     this.views = new HashMap();
/*  40 */     this.persistenceManager = persistenceManager;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  44 */     return this.views.isEmpty();
/*     */   }
/*     */   
/*     */   private List<DataEvent> applyOperationToView(View view, Operation operation, WriteTreeRef writes, Node optCompleteServerCache) {
/*  48 */     View.OperationResult result = view.applyOperation(operation, writes, optCompleteServerCache);
/*     */     
/*  50 */     if (!view.getQuery().loadsAllData()) {
/*  51 */       Set<ChildKey> removed = new HashSet();
/*  52 */       Set<ChildKey> added = new HashSet();
/*  53 */       for (Change change : result.changes) {
/*  54 */         Event.EventType type = change.getEventType();
/*  55 */         if (type == Event.EventType.CHILD_ADDED) {
/*  56 */           added.add(change.getChildKey());
/*  57 */         } else if (type == Event.EventType.CHILD_REMOVED) {
/*  58 */           removed.add(change.getChildKey());
/*     */         }
/*     */       }
/*  61 */       if ((!added.isEmpty()) || (!removed.isEmpty())) {
/*  62 */         this.persistenceManager.updateTrackedQueryKeys(view.getQuery(), added, removed);
/*     */       }
/*     */     }
/*  65 */     return result.events;
/*     */   }
/*     */   
/*     */   public List<DataEvent> applyOperation(Operation operation, WriteTreeRef writesCache, Node optCompleteServerCache) {
/*  69 */     QueryParams queryParams = operation.getSource().getQueryParams();
/*  70 */     if (queryParams != null) {
/*  71 */       View view = (View)this.views.get(queryParams);
/*  72 */       assert (view != null);
/*  73 */       return applyOperationToView(view, operation, writesCache, optCompleteServerCache);
/*     */     }
/*  75 */     List<DataEvent> events = new ArrayList();
/*  76 */     for (Map.Entry<QueryParams, View> entry : this.views.entrySet()) {
/*  77 */       View view = (View)entry.getValue();
/*  78 */       events.addAll(applyOperationToView(view, operation, writesCache, optCompleteServerCache));
/*     */     }
/*  80 */     return events;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DataEvent> addEventRegistration(QuerySpec query, EventRegistration eventRegistration, WriteTreeRef writesCache, CacheNode serverCache)
/*     */   {
/*  89 */     View view = (View)this.views.get(query.getParams());
/*  90 */     if (view == null)
/*     */     {
/*  92 */       Node eventCache = writesCache.calcCompleteEventCache(serverCache.isFullyInitialized() ? serverCache.getNode() : null);
/*     */       boolean eventCacheComplete;
/*  94 */       boolean eventCacheComplete; if (eventCache != null) {
/*  95 */         eventCacheComplete = true;
/*     */       } else {
/*  97 */         eventCache = writesCache.calcCompleteEventChildren(serverCache.getNode());
/*  98 */         eventCacheComplete = false;
/*     */       }
/* 100 */       IndexedNode indexed = IndexedNode.from(eventCache, query.getIndex());
/* 101 */       ViewCache viewCache = new ViewCache(new CacheNode(indexed, eventCacheComplete, false), serverCache);
/* 102 */       view = new View(query, viewCache);
/*     */       
/* 104 */       if (!query.loadsAllData()) {
/* 105 */         Set<ChildKey> allChildren = new HashSet();
/* 106 */         for (NamedNode node : view.getEventCache()) {
/* 107 */           allChildren.add(node.getName());
/*     */         }
/* 109 */         this.persistenceManager.setTrackedQueryKeys(query, allChildren);
/*     */       }
/* 111 */       this.views.put(query.getParams(), view);
/*     */     }
/*     */     
/*     */ 
/* 115 */     view.addEventRegistration(eventRegistration);
/* 116 */     return view.getInitialEvents(eventRegistration);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Pair<List<QuerySpec>, List<Event>> removeEventRegistration(QuerySpec query, EventRegistration eventRegistration, FirebaseError cancelError)
/*     */   {
/* 132 */     List<QuerySpec> removed = new ArrayList();
/* 133 */     List<Event> cancelEvents = new ArrayList();
/* 134 */     boolean hadCompleteView = hasCompleteView();
/* 135 */     if (query.isDefault())
/*     */     {
/* 137 */       Iterator<Map.Entry<QueryParams, View>> iterator = this.views.entrySet().iterator();
/* 138 */       while (iterator.hasNext()) {
/* 139 */         Map.Entry<QueryParams, View> entry = (Map.Entry)iterator.next();
/* 140 */         View view = (View)entry.getValue();
/* 141 */         cancelEvents.addAll(view.removeEventRegistration(eventRegistration, cancelError));
/* 142 */         if (view.isEmpty()) {
/* 143 */           iterator.remove();
/*     */           
/*     */ 
/* 146 */           if (!view.getQuery().loadsAllData()) {
/* 147 */             removed.add(view.getQuery());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 153 */       View view = (View)this.views.get(query.getParams());
/* 154 */       if (view != null) {
/* 155 */         cancelEvents.addAll(view.removeEventRegistration(eventRegistration, cancelError));
/* 156 */         if (view.isEmpty()) {
/* 157 */           this.views.remove(query.getParams());
/*     */           
/*     */ 
/* 160 */           if (!view.getQuery().loadsAllData()) {
/* 161 */             removed.add(view.getQuery());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 167 */     if ((hadCompleteView) && (!hasCompleteView()))
/*     */     {
/* 169 */       removed.add(QuerySpec.defaultQueryAtPath(query.getPath()));
/*     */     }
/* 171 */     return new Pair(removed, cancelEvents);
/*     */   }
/*     */   
/*     */   public List<View> getQueryViews() {
/* 175 */     List<View> views = new ArrayList();
/* 176 */     for (Map.Entry<QueryParams, View> entry : this.views.entrySet()) {
/* 177 */       View view = (View)entry.getValue();
/* 178 */       if (!view.getQuery().loadsAllData()) {
/* 179 */         views.add(view);
/*     */       }
/*     */     }
/* 182 */     return views;
/*     */   }
/*     */   
/*     */   public Node getCompleteServerCache(Path path) {
/* 186 */     for (View view : this.views.values()) {
/* 187 */       if (view.getCompleteServerCache(path) != null) {
/* 188 */         return view.getCompleteServerCache(path);
/*     */       }
/*     */     }
/* 191 */     return null;
/*     */   }
/*     */   
/*     */   public View viewForQuery(QuerySpec query) {
/* 195 */     if (query.loadsAllData()) {
/* 196 */       return getCompleteView();
/*     */     }
/* 198 */     return (View)this.views.get(query.getParams());
/*     */   }
/*     */   
/*     */   public boolean viewExistsForQuery(QuerySpec query)
/*     */   {
/* 203 */     return viewForQuery(query) != null;
/*     */   }
/*     */   
/*     */   public boolean hasCompleteView() {
/* 207 */     return getCompleteView() != null;
/*     */   }
/*     */   
/*     */   public View getCompleteView() {
/* 211 */     for (Map.Entry<QueryParams, View> entry : this.views.entrySet()) {
/* 212 */       View view = (View)entry.getValue();
/* 213 */       if (view.getQuery().loadsAllData()) {
/* 214 */         return view;
/*     */       }
/*     */     }
/* 217 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/SyncPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */