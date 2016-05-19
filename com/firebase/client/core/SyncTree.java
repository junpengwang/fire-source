/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.FirebaseError;
/*     */ import com.firebase.client.collection.ImmutableSortedMap;
/*     */ import com.firebase.client.collection.LLRBNode.NodeVisitor;
/*     */ import com.firebase.client.core.operation.AckUserWrite;
/*     */ import com.firebase.client.core.operation.ListenComplete;
/*     */ import com.firebase.client.core.operation.Merge;
/*     */ import com.firebase.client.core.operation.Operation;
/*     */ import com.firebase.client.core.operation.OperationSource;
/*     */ import com.firebase.client.core.operation.Overwrite;
/*     */ import com.firebase.client.core.persistence.PersistenceManager;
/*     */ import com.firebase.client.core.utilities.ImmutableTree;
/*     */ import com.firebase.client.core.utilities.ImmutableTree.TreeVisitor;
/*     */ import com.firebase.client.core.view.CacheNode;
/*     */ import com.firebase.client.core.view.Change;
/*     */ import com.firebase.client.core.view.DataEvent;
/*     */ import com.firebase.client.core.view.Event;
/*     */ import com.firebase.client.core.view.Event.EventType;
/*     */ import com.firebase.client.core.view.QuerySpec;
/*     */ import com.firebase.client.core.view.View;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.EmptyNode;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.utilities.Clock;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import com.firebase.client.utilities.Pair;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ public class SyncTree
/*     */ {
/*     */   private ImmutableTree<SyncPoint> syncPointTree;
/*     */   private final WriteTree pendingWriteTree;
/*     */   private final Map<Tag, QuerySpec> tagToQueryMap;
/*     */   private final Map<QuerySpec, Tag> queryToTagMap;
/*     */   private final Set<QuerySpec> keepSyncedQueries;
/*     */   private final ListenProvider listenProvider;
/*     */   private final PersistenceManager persistenceManager;
/*     */   private final LogWrapper logger;
/*     */   
/*     */   private class ListenContainer implements SyncTree.SyncTreeHash, SyncTree.CompletionListener
/*     */   {
/*     */     private final View view;
/*     */     private final Tag tag;
/*     */     
/*     */     public ListenContainer(View view)
/*     */     {
/*  59 */       this.view = view;
/*  60 */       this.tag = SyncTree.this.tagForQuery(view.getQuery());
/*     */     }
/*     */     
/*     */     public String getHash()
/*     */     {
/*  65 */       Node cache = this.view.getServerCache();
/*  66 */       if (cache == null) {
/*  67 */         cache = EmptyNode.Empty();
/*     */       }
/*  69 */       return cache.getHash();
/*     */     }
/*     */     
/*     */     public List<? extends Event> onListenComplete(FirebaseError error)
/*     */     {
/*  74 */       if (error == null) {
/*  75 */         QuerySpec query = this.view.getQuery();
/*  76 */         if (this.tag != null) {
/*  77 */           return SyncTree.this.applyTaggedListenComplete(this.tag);
/*     */         }
/*  79 */         return SyncTree.this.applyListenComplete(query.getPath());
/*     */       }
/*     */       
/*  82 */       SyncTree.this.logger.warn("Listen at " + this.view.getQuery().getPath() + " failed: " + error.toString());
/*     */       
/*     */ 
/*     */ 
/*  86 */       return SyncTree.this.removeEventRegistration(this.view.getQuery(), null, error);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SyncTree(Context context, PersistenceManager persistenceManager, ListenProvider listenProvider)
/*     */   {
/* 108 */     this.syncPointTree = ImmutableTree.emptyInstance();
/* 109 */     this.pendingWriteTree = new WriteTree();
/* 110 */     this.tagToQueryMap = new HashMap();
/* 111 */     this.queryToTagMap = new HashMap();
/* 112 */     this.keepSyncedQueries = new HashSet();
/* 113 */     this.listenProvider = listenProvider;
/* 114 */     this.persistenceManager = persistenceManager;
/* 115 */     this.logger = context.getLogger("SyncTree");
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 119 */     return this.syncPointTree.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyUserOverwrite(final Path path, final Node newDataUnresolved, Node newData, final long writeId, final boolean visible, final boolean persist)
/*     */   {
/* 127 */     Utilities.hardAssert((visible) || (!persist), "We shouldn't be persisting non-visible writes.");
/* 128 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 130 */         if (persist) {
/* 131 */           SyncTree.this.persistenceManager.saveUserOverwrite(path, newDataUnresolved, writeId);
/*     */         }
/*     */         
/* 134 */         SyncTree.this.pendingWriteTree.addOverwrite(path, visible, Long.valueOf(writeId), this.val$visible);
/* 135 */         if (!this.val$visible) {
/* 136 */           return Collections.emptyList();
/*     */         }
/* 138 */         return SyncTree.this.applyOperationToSyncPoints(new Overwrite(OperationSource.USER, path, visible));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyUserMerge(final Path path, final CompoundWrite unresolvedChildren, CompoundWrite children, final long writeId, final boolean persist)
/*     */   {
/* 149 */     (List)this.persistenceManager.runInTransaction(new Callable()
/*     */     {
/*     */       public List<? extends Event> call() throws Exception {
/* 152 */         if (persist) {
/* 153 */           SyncTree.this.persistenceManager.saveUserMerge(path, unresolvedChildren, writeId);
/*     */         }
/* 155 */         SyncTree.this.pendingWriteTree.addMerge(path, this.val$children, Long.valueOf(writeId));
/*     */         
/* 157 */         return SyncTree.this.applyOperationToSyncPoints(new Merge(OperationSource.USER, path, this.val$children));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<? extends Event> ackUserWrite(final long writeId, boolean revert, final boolean persist, final Clock serverClock)
/*     */   {
/* 167 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 169 */         if (persist) {
/* 170 */           SyncTree.this.persistenceManager.removeUserWrite(writeId);
/*     */         }
/* 172 */         UserWriteRecord write = SyncTree.this.pendingWriteTree.getWrite(writeId);
/* 173 */         Path pathToReevaluate = SyncTree.this.pendingWriteTree.removeWrite(writeId);
/* 174 */         if ((write.isVisible()) && 
/* 175 */           (!serverClock)) {
/* 176 */           Map<String, Object> serverValues = ServerValues.generateServerValues(this.val$serverClock);
/* 177 */           if (write.isOverwrite()) {
/* 178 */             Node resolvedNode = ServerValues.resolveDeferredValueSnapshot(write.getOverwrite(), serverValues);
/* 179 */             SyncTree.this.persistenceManager.applyUserWriteToServerCache(write.getPath(), resolvedNode);
/*     */           } else {
/* 181 */             CompoundWrite resolvedMerge = ServerValues.resolveDeferredValueMerge(write.getMerge(), serverValues);
/* 182 */             SyncTree.this.persistenceManager.applyUserWriteToServerCache(write.getPath(), resolvedMerge);
/*     */           }
/*     */         }
/*     */         
/* 186 */         if (pathToReevaluate == null) {
/* 187 */           return Collections.emptyList();
/*     */         }
/* 189 */         return SyncTree.this.applyOperationToSyncPoints(new AckUserWrite(pathToReevaluate, serverClock));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<? extends Event> removeAllWrites()
/*     */   {
/* 199 */     (List)this.persistenceManager.runInTransaction(new Callable()
/*     */     {
/*     */       public List<? extends Event> call() throws Exception {
/* 202 */         SyncTree.this.persistenceManager.removeAllUserWrites();
/* 203 */         List<UserWriteRecord> purgedWrites = SyncTree.this.pendingWriteTree.purgeAllWrites();
/* 204 */         if (purgedWrites.isEmpty()) {
/* 205 */           return Collections.emptyList();
/*     */         }
/* 207 */         return SyncTree.this.applyOperationToSyncPoints(new AckUserWrite(Path.getEmptyPath(), true));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyServerOverwrite(final Path path, final Node newData)
/*     */   {
/* 217 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 219 */         SyncTree.this.persistenceManager.updateServerCache(QuerySpec.defaultQueryAtPath(path), newData);
/* 220 */         return SyncTree.this.applyOperationToSyncPoints(new Overwrite(OperationSource.SERVER, path, newData));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyServerMerge(final Path path, final Map<Path, Node> changedChildren)
/*     */   {
/* 229 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 231 */         CompoundWrite merge = CompoundWrite.fromPathMerge(changedChildren);
/* 232 */         SyncTree.this.persistenceManager.updateServerCache(path, merge);
/* 233 */         return SyncTree.this.applyOperationToSyncPoints(new Merge(OperationSource.SERVER, path, merge));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyListenComplete(final Path path)
/*     */   {
/* 242 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 244 */         SyncTree.this.persistenceManager.setQueryComplete(QuerySpec.defaultQueryAtPath(path));
/* 245 */         return SyncTree.this.applyOperationToSyncPoints(new ListenComplete(OperationSource.SERVER, path));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyTaggedListenComplete(final Tag tag)
/*     */   {
/* 254 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 256 */         QuerySpec query = SyncTree.this.queryForTag(tag);
/* 257 */         if (query != null) {
/* 258 */           SyncTree.this.persistenceManager.setQueryComplete(query);
/* 259 */           Operation op = new ListenComplete(OperationSource.forServerTaggedQuery(query.getParams()), Path.getEmptyPath());
/* 260 */           return SyncTree.this.applyTaggedOperation(query, op);
/*     */         }
/*     */         
/* 263 */         return Collections.emptyList();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private List<? extends Event> applyTaggedOperation(QuerySpec query, Operation operation)
/*     */   {
/* 270 */     Path queryPath = query.getPath();
/* 271 */     SyncPoint syncPoint = (SyncPoint)this.syncPointTree.get(queryPath);
/* 272 */     assert (syncPoint != null) : "Missing sync point for query tag that we're tracking";
/* 273 */     WriteTreeRef writesCache = this.pendingWriteTree.childWrites(queryPath);
/* 274 */     return syncPoint.applyOperation(operation, writesCache, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyTaggedQueryOverwrite(final Path path, final Node snap, final Tag tag)
/*     */   {
/* 281 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 283 */         QuerySpec query = SyncTree.this.queryForTag(tag);
/* 284 */         if (query != null) {
/* 285 */           Path relativePath = Path.getRelative(query.getPath(), path);
/* 286 */           QuerySpec queryToOverwrite = relativePath.isEmpty() ? query : QuerySpec.defaultQueryAtPath(path);
/* 287 */           SyncTree.this.persistenceManager.updateServerCache(queryToOverwrite, snap);
/* 288 */           Operation op = new Overwrite(OperationSource.forServerTaggedQuery(query.getParams()), relativePath, snap);
/* 289 */           return SyncTree.this.applyTaggedOperation(query, op);
/*     */         }
/*     */         
/* 292 */         return Collections.emptyList();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<? extends Event> applyTaggedQueryMerge(final Path path, final Map<Path, Node> changedChildren, final Tag tag)
/*     */   {
/* 302 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 304 */         QuerySpec query = SyncTree.this.queryForTag(tag);
/* 305 */         if (query != null) {
/* 306 */           Path relativePath = Path.getRelative(query.getPath(), path);
/* 307 */           CompoundWrite merge = CompoundWrite.fromPathMerge(changedChildren);
/* 308 */           SyncTree.this.persistenceManager.updateServerCache(path, merge);
/* 309 */           Operation op = new Merge(OperationSource.forServerTaggedQuery(query.getParams()), relativePath, merge);
/* 310 */           return SyncTree.this.applyTaggedOperation(query, op);
/*     */         }
/*     */         
/* 313 */         return Collections.emptyList();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<? extends Event> addEventRegistration(final QuerySpec query, final EventRegistration eventRegistration)
/*     */   {
/* 323 */     (List)this.persistenceManager.runInTransaction(new Callable() {
/*     */       public List<? extends Event> call() {
/* 325 */         Path path = query.getPath();
/*     */         
/* 327 */         Node serverCacheNode = null;
/* 328 */         boolean foundAncestorDefaultView = false;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 333 */         ImmutableTree<SyncPoint> tree = SyncTree.this.syncPointTree;
/* 334 */         Path currentPath = path;
/* 335 */         while ((!tree.isEmpty()) && (serverCacheNode == null)) {
/* 336 */           SyncPoint currentSyncPoint = (SyncPoint)tree.getValue();
/* 337 */           if (currentSyncPoint != null) {
/* 338 */             serverCacheNode = currentSyncPoint.getCompleteServerCache(currentPath);
/* 339 */             foundAncestorDefaultView = (foundAncestorDefaultView) || (currentSyncPoint.hasCompleteView());
/*     */           }
/* 341 */           ChildKey front = currentPath.isEmpty() ? ChildKey.fromString("") : currentPath.getFront();
/* 342 */           tree = tree.getChild(front);
/* 343 */           currentPath = currentPath.popFront();
/*     */         }
/*     */         
/*     */ 
/* 347 */         SyncPoint syncPoint = (SyncPoint)SyncTree.this.syncPointTree.get(path);
/* 348 */         if (syncPoint == null) {
/* 349 */           syncPoint = new SyncPoint(SyncTree.this.persistenceManager);
/* 350 */           SyncTree.this.syncPointTree = SyncTree.this.syncPointTree.set(path, syncPoint);
/*     */         } else {
/* 352 */           foundAncestorDefaultView = (foundAncestorDefaultView) || (syncPoint.hasCompleteView());
/* 353 */           serverCacheNode = serverCacheNode != null ? serverCacheNode : syncPoint.getCompleteServerCache(Path.getEmptyPath());
/*     */         }
/*     */         
/* 356 */         SyncTree.this.persistenceManager.setQueryActive(query);
/*     */         CacheNode serverCache;
/*     */         CacheNode serverCache;
/* 359 */         if (serverCacheNode != null) {
/* 360 */           serverCache = new CacheNode(IndexedNode.from(serverCacheNode, query.getIndex()), true, false);
/*     */         }
/*     */         else {
/* 363 */           CacheNode persistentServerCache = SyncTree.this.persistenceManager.serverCache(query);
/* 364 */           CacheNode serverCache; if (persistentServerCache.isFullyInitialized()) {
/* 365 */             serverCache = persistentServerCache;
/*     */           } else {
/* 367 */             serverCacheNode = EmptyNode.Empty();
/* 368 */             ImmutableTree<SyncPoint> subtree = SyncTree.this.syncPointTree.subtree(path);
/* 369 */             for (Map.Entry<ChildKey, ImmutableTree<SyncPoint>> child : subtree.getChildren()) {
/* 370 */               SyncPoint childSyncPoint = (SyncPoint)((ImmutableTree)child.getValue()).getValue();
/* 371 */               if (childSyncPoint != null) {
/* 372 */                 Node completeCache = childSyncPoint.getCompleteServerCache(Path.getEmptyPath());
/* 373 */                 if (completeCache != null) {
/* 374 */                   serverCacheNode = serverCacheNode.updateImmediateChild((ChildKey)child.getKey(), completeCache);
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/* 379 */             for (NamedNode child : persistentServerCache.getNode()) {
/* 380 */               if (!serverCacheNode.hasChild(child.getName())) {
/* 381 */                 serverCacheNode = serverCacheNode.updateImmediateChild(child.getName(), child.getNode());
/*     */               }
/*     */             }
/* 384 */             serverCache = new CacheNode(IndexedNode.from(serverCacheNode, query.getIndex()), false, false);
/*     */           }
/*     */         }
/*     */         
/* 388 */         boolean viewAlreadyExists = syncPoint.viewExistsForQuery(query);
/* 389 */         if ((!viewAlreadyExists) && (!query.loadsAllData()))
/*     */         {
/* 391 */           assert (!SyncTree.this.queryToTagMap.containsKey(query)) : "View does not exist but we have a tag";
/* 392 */           Tag tag = SyncTree.this.getNextQueryTag();
/* 393 */           SyncTree.this.queryToTagMap.put(query, tag);
/* 394 */           SyncTree.this.tagToQueryMap.put(tag, query);
/*     */         }
/* 396 */         WriteTreeRef writesCache = SyncTree.this.pendingWriteTree.childWrites(path);
/* 397 */         List<? extends Event> events = syncPoint.addEventRegistration(query, eventRegistration, writesCache, serverCache);
/* 398 */         if ((!viewAlreadyExists) && (!foundAncestorDefaultView)) {
/* 399 */           View view = syncPoint.viewForQuery(query);
/* 400 */           SyncTree.this.setupListener(query, view);
/*     */         }
/* 402 */         return events;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Event> removeEventRegistration(QuerySpec query, EventRegistration eventRegistration)
/*     */   {
/* 414 */     return removeEventRegistration(query, eventRegistration, null);
/*     */   }
/*     */   
/*     */   public List<Event> removeEventRegistration(final QuerySpec query, final EventRegistration eventRegistration, final FirebaseError cancelError) {
/* 418 */     (List)this.persistenceManager.runInTransaction(new Callable()
/*     */     {
/*     */       public List<Event> call() {
/* 421 */         Path path = query.getPath();
/* 422 */         SyncPoint maybeSyncPoint = (SyncPoint)SyncTree.this.syncPointTree.get(path);
/* 423 */         List<Event> cancelEvents = new ArrayList();
/*     */         
/*     */ 
/*     */ 
/* 427 */         if ((maybeSyncPoint != null) && ((query.isDefault()) || (maybeSyncPoint.viewExistsForQuery(query))))
/*     */         {
/*     */ 
/* 430 */           Pair<List<QuerySpec>, List<Event>> removedAndEvents = maybeSyncPoint.removeEventRegistration(query, eventRegistration, cancelError);
/* 431 */           if (maybeSyncPoint.isEmpty()) {
/* 432 */             SyncTree.this.syncPointTree = SyncTree.this.syncPointTree.remove(path);
/*     */           }
/* 434 */           List<QuerySpec> removed = (List)removedAndEvents.getFirst();
/* 435 */           cancelEvents = (List)removedAndEvents.getSecond();
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 442 */           boolean removingDefault = false;
/* 443 */           for (QuerySpec queryRemoved : removed) {
/* 444 */             SyncTree.this.persistenceManager.setQueryInactive(query);
/* 445 */             removingDefault = (removingDefault) || (queryRemoved.loadsAllData());
/*     */           }
/* 447 */           ImmutableTree<SyncPoint> currentTree = SyncTree.this.syncPointTree;
/* 448 */           boolean covered = (currentTree.getValue() != null) && (((SyncPoint)currentTree.getValue()).hasCompleteView());
/* 449 */           for (ChildKey component : path) {
/* 450 */             currentTree = currentTree.getChild(component);
/* 451 */             covered = (covered) || ((currentTree.getValue() != null) && (((SyncPoint)currentTree.getValue()).hasCompleteView()));
/* 452 */             if ((covered) || (currentTree.isEmpty())) {
/*     */               break;
/*     */             }
/*     */           }
/*     */           
/* 457 */           if ((removingDefault) && (!covered)) {
/* 458 */             ImmutableTree<SyncPoint> subtree = SyncTree.this.syncPointTree.subtree(path);
/*     */             
/*     */ 
/* 461 */             if (!subtree.isEmpty())
/*     */             {
/* 463 */               List<View> newViews = SyncTree.this.collectDistinctViewsForSubTree(subtree);
/*     */               
/*     */ 
/* 466 */               for (View view : newViews) {
/* 467 */                 SyncTree.ListenContainer container = new SyncTree.ListenContainer(SyncTree.this, view);
/* 468 */                 QuerySpec newQuery = view.getQuery();
/* 469 */                 SyncTree.this.listenProvider.startListening(newQuery, container.tag, container, container);
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 478 */           if ((!covered) && (!removed.isEmpty()) && (cancelError == null))
/*     */           {
/*     */ 
/* 481 */             if (removingDefault) {
/* 482 */               SyncTree.this.listenProvider.stopListening(query, null);
/*     */             } else {
/* 484 */               for (QuerySpec queryToRemove : removed) {
/* 485 */                 Tag tag = SyncTree.this.tagForQuery(queryToRemove);
/* 486 */                 assert (tag != null);
/* 487 */                 SyncTree.this.listenProvider.stopListening(queryToRemove, tag);
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 492 */           SyncTree.this.removeTags(removed);
/*     */         }
/*     */         
/*     */ 
/* 496 */         return cancelEvents;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/* 501 */   private static final EventRegistration keepSyncedEventRegistration = new EventRegistration() {
/* 502 */     public boolean respondsTo(Event.EventType eventType) { return false; }
/* 503 */     public DataEvent createEvent(Change change, QuerySpec query) { return null; }
/*     */     
/*     */     public void fireEvent(DataEvent dataEvent) {}
/*     */     
/*     */     public void fireCancelEvent(FirebaseError error) {} };
/*     */   
/* 509 */   public void keepSynced(QuerySpec query, boolean keep) { if ((keep) && (!this.keepSyncedQueries.contains(query)))
/*     */     {
/* 511 */       addEventRegistration(query, keepSyncedEventRegistration);
/* 512 */       this.keepSyncedQueries.add(query);
/* 513 */     } else if ((!keep) && (this.keepSyncedQueries.contains(query))) {
/* 514 */       removeEventRegistration(query, keepSyncedEventRegistration);
/* 515 */       this.keepSyncedQueries.remove(query);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<View> collectDistinctViewsForSubTree(ImmutableTree<SyncPoint> subtree)
/*     */   {
/* 524 */     ArrayList<View> accumulator = new ArrayList();
/* 525 */     collectDistinctViewsForSubTree(subtree, accumulator);
/* 526 */     return accumulator;
/*     */   }
/*     */   
/*     */   private void collectDistinctViewsForSubTree(ImmutableTree<SyncPoint> subtree, List<View> accumulator) {
/* 530 */     SyncPoint maybeSyncPoint = (SyncPoint)subtree.getValue();
/* 531 */     if ((maybeSyncPoint != null) && (maybeSyncPoint.hasCompleteView())) {
/* 532 */       accumulator.add(maybeSyncPoint.getCompleteView());
/*     */     } else {
/* 534 */       if (maybeSyncPoint != null) {
/* 535 */         accumulator.addAll(maybeSyncPoint.getQueryViews());
/*     */       }
/* 537 */       for (Map.Entry<ChildKey, ImmutableTree<SyncPoint>> entry : subtree.getChildren()) {
/* 538 */         collectDistinctViewsForSubTree((ImmutableTree)entry.getValue(), accumulator);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeTags(List<QuerySpec> queries)
/*     */   {
/* 545 */     for (QuerySpec removedQuery : queries) {
/* 546 */       if (!removedQuery.loadsAllData())
/*     */       {
/* 548 */         Tag tag = tagForQuery(removedQuery);
/* 549 */         assert (tag != null);
/* 550 */         this.queryToTagMap.remove(removedQuery);
/* 551 */         this.tagToQueryMap.remove(tag);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setupListener(QuerySpec query, View view)
/*     */   {
/* 560 */     Path path = query.getPath();
/* 561 */     Tag tag = tagForQuery(query);
/* 562 */     ListenContainer container = new ListenContainer(view);
/*     */     
/* 564 */     this.listenProvider.startListening(query, tag, container, container);
/*     */     
/* 566 */     ImmutableTree<SyncPoint> subtree = this.syncPointTree.subtree(path);
/*     */     
/*     */ 
/* 569 */     if (tag != null) {
/* 570 */       if ((!$assertionsDisabled) && (((SyncPoint)subtree.getValue()).hasCompleteView())) throw new AssertionError("If we're adding a query, it shouldn't be shadowed");
/*     */     }
/*     */     else {
/* 573 */       subtree.foreach(new ImmutableTree.TreeVisitor()
/*     */       {
/*     */         public Void onNodeValue(Path relativePath, SyncPoint maybeChildSyncPoint, Void accum) {
/* 576 */           if ((!relativePath.isEmpty()) && (maybeChildSyncPoint.hasCompleteView())) {
/* 577 */             QuerySpec query = maybeChildSyncPoint.getCompleteView().getQuery();
/* 578 */             SyncTree.this.listenProvider.stopListening(query, SyncTree.this.tagForQuery(query));
/*     */           }
/*     */           else {
/* 581 */             for (View syncPointView : maybeChildSyncPoint.getQueryViews()) {
/* 582 */               QuerySpec childQuery = syncPointView.getQuery();
/* 583 */               SyncTree.this.listenProvider.stopListening(childQuery, SyncTree.this.tagForQuery(childQuery));
/*     */             }
/*     */           }
/* 586 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private QuerySpec queryForTag(Tag tag)
/*     */   {
/* 596 */     return (QuerySpec)this.tagToQueryMap.get(tag);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Tag tagForQuery(QuerySpec query)
/*     */   {
/* 603 */     return (Tag)this.queryToTagMap.get(query);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node calcCompleteEventCache(Path path, List<Long> writeIdsToExclude)
/*     */   {
/* 613 */     ImmutableTree<SyncPoint> tree = this.syncPointTree;
/* 614 */     SyncPoint currentSyncPoint = (SyncPoint)tree.getValue();
/* 615 */     Node serverCache = null;
/* 616 */     Path pathToFollow = path;
/* 617 */     Path pathSoFar = Path.getEmptyPath();
/*     */     do {
/* 619 */       ChildKey front = pathToFollow.getFront();
/* 620 */       pathToFollow = pathToFollow.popFront();
/* 621 */       pathSoFar = pathSoFar.child(front);
/* 622 */       Path relativePath = Path.getRelative(pathSoFar, path);
/* 623 */       tree = front != null ? tree.getChild(front) : ImmutableTree.emptyInstance();
/* 624 */       currentSyncPoint = (SyncPoint)tree.getValue();
/* 625 */       if (currentSyncPoint != null) {
/* 626 */         serverCache = currentSyncPoint.getCompleteServerCache(relativePath);
/*     */       }
/* 628 */     } while ((!pathToFollow.isEmpty()) && (serverCache == null));
/* 629 */     return this.pendingWriteTree.calcCompleteEventCache(path, serverCache, writeIdsToExclude, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 635 */   private long nextQueryTag = 1L;
/*     */   
/*     */ 
/*     */ 
/*     */   private Tag getNextQueryTag()
/*     */   {
/* 641 */     return new Tag(this.nextQueryTag++);
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
/*     */ 
/*     */   private List<Event> applyOperationToSyncPoints(Operation operation)
/*     */   {
/* 658 */     return applyOperationHelper(operation, this.syncPointTree, null, this.pendingWriteTree.childWrites(Path.getEmptyPath()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<Event> applyOperationHelper(Operation operation, ImmutableTree<SyncPoint> syncPointTree, Node serverCache, WriteTreeRef writesCache)
/*     */   {
/* 667 */     if (operation.getPath().isEmpty()) {
/* 668 */       return applyOperationDescendantsHelper(operation, syncPointTree, serverCache, writesCache);
/*     */     }
/* 670 */     SyncPoint syncPoint = (SyncPoint)syncPointTree.getValue();
/*     */     
/*     */ 
/* 673 */     if ((serverCache == null) && (syncPoint != null)) {
/* 674 */       serverCache = syncPoint.getCompleteServerCache(Path.getEmptyPath());
/*     */     }
/*     */     
/* 677 */     List<Event> events = new ArrayList();
/* 678 */     ChildKey childKey = operation.getPath().getFront();
/* 679 */     Operation childOperation = operation.operationForChild(childKey);
/* 680 */     ImmutableTree<SyncPoint> childTree = (ImmutableTree)syncPointTree.getChildren().get(childKey);
/* 681 */     if ((childTree != null) && (childOperation != null)) {
/* 682 */       Node childServerCache = serverCache != null ? serverCache.getImmediateChild(childKey) : null;
/* 683 */       WriteTreeRef childWritesCache = writesCache.child(childKey);
/* 684 */       events.addAll(applyOperationHelper(childOperation, childTree, childServerCache, childWritesCache));
/*     */     }
/*     */     
/* 687 */     if (syncPoint != null) {
/* 688 */       events.addAll(syncPoint.applyOperation(operation, writesCache, serverCache));
/*     */     }
/*     */     
/* 691 */     return events;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<Event> applyOperationDescendantsHelper(final Operation operation, ImmutableTree<SyncPoint> syncPointTree, Node serverCache, final WriteTreeRef writesCache)
/*     */   {
/* 700 */     SyncPoint syncPoint = (SyncPoint)syncPointTree.getValue();
/*     */     
/*     */     Node resolvedServerCache;
/*     */     final Node resolvedServerCache;
/* 704 */     if ((serverCache == null) && (syncPoint != null)) {
/* 705 */       resolvedServerCache = syncPoint.getCompleteServerCache(Path.getEmptyPath());
/*     */     } else {
/* 707 */       resolvedServerCache = serverCache;
/*     */     }
/*     */     
/* 710 */     final List<Event> events = new ArrayList();
/* 711 */     syncPointTree.getChildren().inOrderTraversal(new LLRBNode.NodeVisitor()
/*     */     {
/*     */       public void visitEntry(ChildKey key, ImmutableTree<SyncPoint> childTree) {
/* 714 */         Node childServerCache = null;
/* 715 */         if (resolvedServerCache != null) {
/* 716 */           childServerCache = resolvedServerCache.getImmediateChild(key);
/*     */         }
/* 718 */         WriteTreeRef childWritesCache = writesCache.child(key);
/* 719 */         Operation childOperation = operation.operationForChild(key);
/* 720 */         if (childOperation != null) {
/* 721 */           events.addAll(SyncTree.this.applyOperationDescendantsHelper(childOperation, childTree, childServerCache, childWritesCache));
/*     */         }
/*     */       }
/*     */     });
/*     */     
/* 726 */     if (syncPoint != null) {
/* 727 */       events.addAll(syncPoint.applyOperation(operation, writesCache, resolvedServerCache));
/*     */     }
/*     */     
/* 730 */     return events;
/*     */   }
/*     */   
/*     */   public static abstract interface ListenProvider
/*     */   {
/*     */     public abstract void startListening(QuerySpec paramQuerySpec, Tag paramTag, SyncTree.SyncTreeHash paramSyncTreeHash, SyncTree.CompletionListener paramCompletionListener);
/*     */     
/*     */     public abstract void stopListening(QuerySpec paramQuerySpec, Tag paramTag);
/*     */   }
/*     */   
/*     */   public static abstract interface CompletionListener
/*     */   {
/*     */     public abstract List<? extends Event> onListenComplete(FirebaseError paramFirebaseError);
/*     */   }
/*     */   
/*     */   public static abstract interface SyncTreeHash
/*     */   {
/*     */     public abstract String getHash();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/SyncTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */