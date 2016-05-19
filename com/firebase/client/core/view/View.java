/*     */ package com.firebase.client.core.view;
/*     */ 
/*     */ import com.firebase.client.core.EventRegistration;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.operation.Operation;
/*     */ import com.firebase.client.core.view.filter.IndexedFilter;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import java.util.List;
/*     */ 
/*     */ public class View
/*     */ {
/*     */   private final QuerySpec query;
/*     */   private final ViewProcessor processor;
/*     */   private ViewCache viewCache;
/*     */   private final List<EventRegistration> eventRegistrations;
/*     */   private final EventGenerator eventGenerator;
/*     */   
/*     */   public View(QuerySpec query, ViewCache initialViewCache)
/*     */   {
/*  21 */     this.query = query;
/*  22 */     IndexedFilter indexFilter = new IndexedFilter(query.getIndex());
/*  23 */     com.firebase.client.core.view.filter.NodeFilter filter = query.getParams().getNodeFilter();
/*  24 */     this.processor = new ViewProcessor(filter);
/*  25 */     CacheNode initialServerCache = initialViewCache.getServerCache();
/*  26 */     CacheNode initialEventCache = initialViewCache.getEventCache();
/*     */     
/*     */ 
/*  29 */     IndexedNode emptyIndexedNode = IndexedNode.from(com.firebase.client.snapshot.EmptyNode.Empty(), query.getIndex());
/*  30 */     IndexedNode serverSnap = indexFilter.updateFullNode(emptyIndexedNode, initialServerCache.getIndexedNode(), null);
/*  31 */     IndexedNode eventSnap = filter.updateFullNode(emptyIndexedNode, initialEventCache.getIndexedNode(), null);
/*  32 */     CacheNode newServerCache = new CacheNode(serverSnap, initialServerCache.isFullyInitialized(), indexFilter.filtersNodes());
/*  33 */     CacheNode newEventCache = new CacheNode(eventSnap, initialEventCache.isFullyInitialized(), filter.filtersNodes());
/*     */     
/*  35 */     this.viewCache = new ViewCache(newEventCache, newServerCache);
/*     */     
/*  37 */     this.eventRegistrations = new java.util.ArrayList();
/*     */     
/*  39 */     this.eventGenerator = new EventGenerator(query);
/*     */   }
/*     */   
/*     */   public static class OperationResult {
/*     */     public final List<DataEvent> events;
/*     */     public final List<Change> changes;
/*     */     
/*     */     public OperationResult(List<DataEvent> events, List<Change> changes) {
/*  47 */       this.events = events;
/*  48 */       this.changes = changes;
/*     */     }
/*     */   }
/*     */   
/*     */   public QuerySpec getQuery() {
/*  53 */     return this.query;
/*     */   }
/*     */   
/*     */   public Node getCompleteNode() {
/*  57 */     return this.viewCache.getCompleteEventSnap();
/*     */   }
/*     */   
/*     */   public Node getServerCache() {
/*  61 */     return this.viewCache.getServerCache().getNode();
/*     */   }
/*     */   
/*     */   public Node getEventCache() {
/*  65 */     return this.viewCache.getEventCache().getNode();
/*     */   }
/*     */   
/*     */   public Node getCompleteServerCache(Path path) {
/*  69 */     Node cache = this.viewCache.getCompleteServerSnap();
/*  70 */     if (cache != null)
/*     */     {
/*     */ 
/*  73 */       if ((this.query.loadsAllData()) || ((!path.isEmpty()) && (!cache.getImmediateChild(path.getFront()).isEmpty())))
/*     */       {
/*  75 */         return cache.getChild(path);
/*     */       }
/*     */     }
/*  78 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  82 */     return this.eventRegistrations.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*  86 */   public void addEventRegistration(EventRegistration registration) { this.eventRegistrations.add(registration); }
/*     */   
/*     */   public List<Event> removeEventRegistration(EventRegistration registration, com.firebase.client.FirebaseError cancelError) { List<Event> cancelEvents;
/*     */     Path path;
/*     */     List<Event> cancelEvents;
/*  91 */     if (cancelError != null) {
/*  92 */       cancelEvents = new java.util.ArrayList();
/*  93 */       assert (registration == null) : "A cancel should cancel all event registrations";
/*  94 */       path = this.query.getPath();
/*  95 */       for (EventRegistration eventRegistration : this.eventRegistrations) {
/*  96 */         cancelEvents.add(new CancelEvent(eventRegistration, cancelError, path));
/*     */       }
/*     */     } else {
/*  99 */       cancelEvents = java.util.Collections.emptyList();
/*     */     }
/* 101 */     if (registration != null)
/*     */     {
/* 103 */       this.eventRegistrations.remove(registration);
/*     */     } else {
/* 105 */       this.eventRegistrations.clear();
/*     */     }
/* 107 */     return cancelEvents;
/*     */   }
/*     */   
/*     */   public OperationResult applyOperation(Operation operation, com.firebase.client.core.WriteTreeRef writesCache, Node optCompleteServerCache) {
/* 111 */     if ((operation.getType() == com.firebase.client.core.operation.Operation.OperationType.Merge) && (operation.getSource().getQueryParams() != null)) {
/* 112 */       assert (this.viewCache.getCompleteServerSnap() != null) : "We should always have a full cache before handling merges";
/* 113 */       assert (this.viewCache.getCompleteEventSnap() != null) : "Missing event cache, even though we have a server cache";
/*     */     }
/* 115 */     ViewCache oldViewCache = this.viewCache;
/* 116 */     ViewProcessor.ProcessorResult result = this.processor.applyOperation(oldViewCache, operation, writesCache, optCompleteServerCache);
/*     */     
/*     */ 
/* 119 */     assert ((result.viewCache.getServerCache().isFullyInitialized()) || (!oldViewCache.getServerCache().isFullyInitialized())) : "Once a server snap is complete, it should never go back";
/*     */     
/* 121 */     this.viewCache = result.viewCache;
/* 122 */     List<DataEvent> events = generateEventsForChanges(result.changes, result.viewCache.getEventCache().getIndexedNode(), null);
/*     */     
/* 124 */     return new OperationResult(events, result.changes);
/*     */   }
/*     */   
/*     */   public List<DataEvent> getInitialEvents(EventRegistration registration) {
/* 128 */     CacheNode eventSnap = this.viewCache.getEventCache();
/* 129 */     List<Change> initialChanges = new java.util.ArrayList();
/* 130 */     for (com.firebase.client.snapshot.NamedNode child : eventSnap.getNode()) {
/* 131 */       initialChanges.add(Change.childAddedChange(child.getName(), child.getNode()));
/*     */     }
/* 133 */     if (eventSnap.isFullyInitialized()) {
/* 134 */       initialChanges.add(Change.valueChange(eventSnap.getIndexedNode()));
/*     */     }
/* 136 */     return generateEventsForChanges(initialChanges, eventSnap.getIndexedNode(), registration);
/*     */   }
/*     */   
/*     */   private List<DataEvent> generateEventsForChanges(List<Change> changes, IndexedNode eventCache, EventRegistration registration) { List<EventRegistration> registrations;
/*     */     List<EventRegistration> registrations;
/* 141 */     if (registration == null) {
/* 142 */       registrations = this.eventRegistrations;
/*     */     } else {
/* 144 */       registrations = java.util.Arrays.asList(new EventRegistration[] { registration });
/*     */     }
/* 146 */     return this.eventGenerator.generateEventsForChanges(changes, eventCache, registrations);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/View.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */