/*     */ package com.firebase.client.core.persistence;
/*     */ 
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.utilities.ImmutableTree;
/*     */ import com.firebase.client.core.utilities.Predicate;
/*     */ import com.firebase.client.core.view.QueryParams;
/*     */ import com.firebase.client.core.view.QuerySpec;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.utilities.Clock;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class TrackedQueryManager
/*     */ {
/*  19 */   private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_DEFAULT_COMPLETE_PREDICATE = new Predicate()
/*     */   {
/*     */     public boolean evaluate(Map<QueryParams, TrackedQuery> trackedQueries) {
/*  22 */       TrackedQuery trackedQuery = (TrackedQuery)trackedQueries.get(QueryParams.DEFAULT_PARAMS);
/*  23 */       return (trackedQuery != null) && (trackedQuery.complete);
/*     */     }
/*     */   };
/*     */   
/*  27 */   private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_ACTIVE_DEFAULT_PREDICATE = new Predicate()
/*     */   {
/*     */     public boolean evaluate(Map<QueryParams, TrackedQuery> trackedQueries) {
/*  30 */       TrackedQuery trackedQuery = (TrackedQuery)trackedQueries.get(QueryParams.DEFAULT_PARAMS);
/*  31 */       return (trackedQuery != null) && (trackedQuery.active);
/*     */     }
/*     */   };
/*     */   
/*  35 */   private static final Predicate<TrackedQuery> IS_QUERY_PRUNABLE_PREDICATE = new Predicate()
/*     */   {
/*     */     public boolean evaluate(TrackedQuery query) {
/*  38 */       return !query.active;
/*     */     }
/*     */   };
/*     */   
/*  42 */   private static final Predicate<TrackedQuery> IS_QUERY_UNPRUNABLE_PREDICATE = new Predicate()
/*     */   {
/*     */     public boolean evaluate(TrackedQuery query) {
/*  45 */       return !TrackedQueryManager.IS_QUERY_PRUNABLE_PREDICATE.evaluate(query);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */   private ImmutableTree<Map<QueryParams, TrackedQuery>> trackedQueryTree;
/*     */   
/*     */ 
/*     */   private final PersistenceStorageEngine storageLayer;
/*     */   
/*     */   private final LogWrapper logger;
/*     */   
/*     */   private final Clock clock;
/*     */   
/*  59 */   private long currentQueryId = 0L;
/*     */   
/*     */   private static void assertValidTrackedQuery(QuerySpec query) {
/*  62 */     Utilities.hardAssert((!query.loadsAllData()) || (query.isDefault()), "Can't have tracked non-default query that loads all data");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static QuerySpec normalizeQuery(QuerySpec query)
/*     */   {
/*  69 */     return query.loadsAllData() ? QuerySpec.defaultQueryAtPath(query.getPath()) : query;
/*     */   }
/*     */   
/*     */   public TrackedQueryManager(PersistenceStorageEngine storageLayer, LogWrapper logger, Clock clock) {
/*  73 */     this.storageLayer = storageLayer;
/*  74 */     this.logger = logger;
/*  75 */     this.clock = clock;
/*  76 */     this.trackedQueryTree = new ImmutableTree(null);
/*     */     
/*  78 */     resetPreviouslyActiveTrackedQueries();
/*     */     
/*     */ 
/*  81 */     List<TrackedQuery> trackedQueries = this.storageLayer.loadTrackedQueries();
/*  82 */     for (TrackedQuery query : trackedQueries) {
/*  83 */       this.currentQueryId = Math.max(query.id + 1L, this.currentQueryId);
/*  84 */       cacheTrackedQuery(query);
/*     */     }
/*     */   }
/*     */   
/*     */   private void resetPreviouslyActiveTrackedQueries()
/*     */   {
/*     */     try
/*     */     {
/*  92 */       this.storageLayer.beginTransaction();
/*  93 */       this.storageLayer.resetPreviouslyActiveTrackedQueries(this.clock.millis());
/*  94 */       this.storageLayer.setTransactionSuccessful();
/*     */     } finally {
/*  96 */       this.storageLayer.endTransaction();
/*     */     }
/*     */   }
/*     */   
/*     */   public TrackedQuery findTrackedQuery(QuerySpec query) {
/* 101 */     query = normalizeQuery(query);
/* 102 */     Map<QueryParams, TrackedQuery> set = (Map)this.trackedQueryTree.get(query.getPath());
/* 103 */     return set != null ? (TrackedQuery)set.get(query.getParams()) : null;
/*     */   }
/*     */   
/*     */   public void removeTrackedQuery(QuerySpec query) {
/* 107 */     query = normalizeQuery(query);
/* 108 */     TrackedQuery trackedQuery = findTrackedQuery(query);
/* 109 */     assert (trackedQuery != null) : "Query must exist to be removed.";
/*     */     
/* 111 */     this.storageLayer.deleteTrackedQuery(trackedQuery.id);
/* 112 */     Map<QueryParams, TrackedQuery> trackedQueries = (Map)this.trackedQueryTree.get(query.getPath());
/* 113 */     trackedQueries.remove(query.getParams());
/* 114 */     if (trackedQueries.isEmpty()) {
/* 115 */       this.trackedQueryTree = this.trackedQueryTree.remove(query.getPath());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setQueryActive(QuerySpec query) {
/* 120 */     setQueryActiveFlag(query, true);
/*     */   }
/*     */   
/*     */   public void setQueryInactive(QuerySpec query) {
/* 124 */     setQueryActiveFlag(query, false);
/*     */   }
/*     */   
/*     */   private void setQueryActiveFlag(QuerySpec query, boolean isActive) {
/* 128 */     query = normalizeQuery(query);
/* 129 */     TrackedQuery trackedQuery = findTrackedQuery(query);
/*     */     
/*     */ 
/* 132 */     long lastUse = this.clock.millis();
/* 133 */     if (trackedQuery != null) {
/* 134 */       trackedQuery = trackedQuery.updateLastUse(lastUse).setActiveState(isActive);
/*     */     } else {
/* 136 */       assert (isActive) : "If we're setting the query to inactive, we should already be tracking it!";
/* 137 */       trackedQuery = new TrackedQuery(this.currentQueryId++, query, lastUse, false, isActive);
/*     */     }
/*     */     
/* 140 */     saveTrackedQuery(trackedQuery);
/*     */   }
/*     */   
/*     */   public void setQueryCompleteIfExists(QuerySpec query) {
/* 144 */     query = normalizeQuery(query);
/* 145 */     TrackedQuery trackedQuery = findTrackedQuery(query);
/* 146 */     if ((trackedQuery != null) && (!trackedQuery.complete)) {
/* 147 */       saveTrackedQuery(trackedQuery.setComplete());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setQueriesComplete(Path path) {
/* 152 */     this.trackedQueryTree.subtree(path).foreach(new com.firebase.client.core.utilities.ImmutableTree.TreeVisitor()
/*     */     {
/*     */       public Void onNodeValue(Path relativePath, Map<QueryParams, TrackedQuery> value, Void accum) {
/* 155 */         for (Map.Entry<QueryParams, TrackedQuery> e : value.entrySet()) {
/* 156 */           TrackedQuery trackedQuery = (TrackedQuery)e.getValue();
/* 157 */           if (!trackedQuery.complete) {
/* 158 */             TrackedQueryManager.this.saveTrackedQuery(trackedQuery.setComplete());
/*     */           }
/*     */         }
/* 161 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public boolean isQueryComplete(QuerySpec query) {
/* 167 */     if (includedInDefaultCompleteQuery(query.getPath()))
/* 168 */       return true;
/* 169 */     if (query.loadsAllData())
/*     */     {
/* 171 */       return false;
/*     */     }
/* 173 */     Map<QueryParams, TrackedQuery> trackedQueries = (Map)this.trackedQueryTree.get(query.getPath());
/* 174 */     return (trackedQueries != null) && (trackedQueries.containsKey(query.getParams())) && (((TrackedQuery)trackedQueries.get(query.getParams())).complete);
/*     */   }
/*     */   
/*     */ 
/*     */   public PruneForest pruneOldQueries(CachePolicy cachePolicy)
/*     */   {
/* 180 */     List<TrackedQuery> prunable = getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE);
/* 181 */     long countToPrune = calculateCountToPrune(cachePolicy, prunable.size());
/* 182 */     PruneForest forest = new PruneForest();
/*     */     
/* 184 */     if (this.logger.logsDebug()) {
/* 185 */       this.logger.debug("Pruning old queries.  Prunable: " + prunable.size() + " Count to prune: " + countToPrune);
/*     */     }
/* 187 */     java.util.Collections.sort(prunable, new java.util.Comparator()
/*     */     {
/*     */       public int compare(TrackedQuery q1, TrackedQuery q2) {
/* 190 */         return Utilities.compareLongs(q1.lastUse, q2.lastUse);
/*     */       }
/*     */     });
/*     */     
/* 194 */     for (int i = 0; i < countToPrune; i++) {
/* 195 */       TrackedQuery toPrune = (TrackedQuery)prunable.get(i);
/* 196 */       forest = forest.prune(toPrune.querySpec.getPath());
/* 197 */       removeTrackedQuery(toPrune.querySpec);
/*     */     }
/*     */     
/*     */ 
/* 201 */     for (int i = (int)countToPrune; i < prunable.size(); i++) {
/* 202 */       TrackedQuery toKeep = (TrackedQuery)prunable.get(i);
/* 203 */       forest = forest.keep(toKeep.querySpec.getPath());
/*     */     }
/*     */     
/*     */ 
/* 207 */     List<TrackedQuery> unprunable = getQueriesMatching(IS_QUERY_UNPRUNABLE_PREDICATE);
/* 208 */     if (this.logger.logsDebug()) this.logger.debug("Unprunable queries: " + unprunable.size());
/* 209 */     for (TrackedQuery toKeep : unprunable) {
/* 210 */       forest = forest.keep(toKeep.querySpec.getPath());
/*     */     }
/*     */     
/* 213 */     return forest;
/*     */   }
/*     */   
/*     */   private static long calculateCountToPrune(CachePolicy cachePolicy, long prunableCount) {
/* 217 */     long countToKeep = prunableCount;
/*     */     
/*     */ 
/* 220 */     float percentToKeep = 1.0F - cachePolicy.getPercentOfQueriesToPruneAtOnce();
/* 221 */     countToKeep = Math.floor((float)countToKeep * percentToKeep);
/*     */     
/*     */ 
/* 224 */     countToKeep = Math.min(countToKeep, cachePolicy.getMaxNumberOfQueriesToKeep());
/*     */     
/*     */ 
/* 227 */     return prunableCount - countToKeep;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<ChildKey> getKnownCompleteChildren(Path path)
/*     */   {
/* 237 */     assert (!isQueryComplete(QuerySpec.defaultQueryAtPath(path))) : "Path is fully complete.";
/*     */     
/* 239 */     Set<ChildKey> completeChildren = new java.util.HashSet();
/*     */     
/* 241 */     Set<Long> queryIds = filteredQueryIdsAtPath(path);
/* 242 */     if (!queryIds.isEmpty()) {
/* 243 */       completeChildren.addAll(this.storageLayer.loadTrackedQueryKeys(queryIds));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 248 */     for (Map.Entry<ChildKey, ImmutableTree<Map<QueryParams, TrackedQuery>>> childEntry : this.trackedQueryTree.subtree(path).getChildren()) {
/* 249 */       ChildKey childKey = (ChildKey)childEntry.getKey();
/* 250 */       ImmutableTree<Map<QueryParams, TrackedQuery>> childTree = (ImmutableTree)childEntry.getValue();
/* 251 */       if ((childTree.getValue() != null) && (HAS_DEFAULT_COMPLETE_PREDICATE.evaluate(childTree.getValue()))) {
/* 252 */         completeChildren.add(childKey);
/*     */       }
/*     */     }
/*     */     
/* 256 */     return completeChildren;
/*     */   }
/*     */   
/*     */   public void ensureCompleteTrackedQuery(Path path) {
/* 260 */     if (!includedInDefaultCompleteQuery(path))
/*     */     {
/*     */ 
/*     */ 
/* 264 */       QuerySpec querySpec = QuerySpec.defaultQueryAtPath(path);
/* 265 */       TrackedQuery trackedQuery = findTrackedQuery(querySpec);
/* 266 */       if (trackedQuery == null) {
/* 267 */         trackedQuery = new TrackedQuery(this.currentQueryId++, querySpec, this.clock.millis(), true, false);
/*     */       } else {
/* 269 */         assert (!trackedQuery.complete) : "This should have been handled above!";
/* 270 */         trackedQuery = trackedQuery.setComplete();
/*     */       }
/* 272 */       saveTrackedQuery(trackedQuery);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasActiveDefaultQuery(Path path) {
/* 277 */     return this.trackedQueryTree.rootMostValueMatching(path, HAS_ACTIVE_DEFAULT_PREDICATE) != null;
/*     */   }
/*     */   
/*     */   public long countOfPrunableQueries() {
/* 281 */     return getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE).size();
/*     */   }
/*     */   
/*     */   void verifyCache()
/*     */   {
/* 286 */     List<TrackedQuery> storedTrackedQueries = this.storageLayer.loadTrackedQueries();
/*     */     
/* 288 */     final List<TrackedQuery> trackedQueries = new java.util.ArrayList();
/* 289 */     this.trackedQueryTree.foreach(new com.firebase.client.core.utilities.ImmutableTree.TreeVisitor()
/*     */     {
/*     */       public Void onNodeValue(Path relativePath, Map<QueryParams, TrackedQuery> value, Void accum) {
/* 292 */         for (TrackedQuery trackedQuery : value.values()) {
/* 293 */           trackedQueries.add(trackedQuery);
/*     */         }
/* 295 */         return null;
/*     */       }
/* 297 */     });
/* 298 */     java.util.Collections.sort(trackedQueries, new java.util.Comparator()
/*     */     {
/*     */       public int compare(TrackedQuery o1, TrackedQuery o2) {
/* 301 */         return Utilities.compareLongs(o1.id, o2.id);
/*     */       }
/*     */       
/* 304 */     });
/* 305 */     Utilities.hardAssert(storedTrackedQueries.equals(trackedQueries), "Tracked queries out of sync.  Tracked queries: " + trackedQueries + " Stored queries: " + storedTrackedQueries);
/*     */   }
/*     */   
/*     */   private boolean includedInDefaultCompleteQuery(Path path)
/*     */   {
/* 310 */     return this.trackedQueryTree.findRootMostMatchingPath(path, HAS_DEFAULT_COMPLETE_PREDICATE) != null;
/*     */   }
/*     */   
/*     */   private Set<Long> filteredQueryIdsAtPath(Path path) {
/* 314 */     Set<Long> ids = new java.util.HashSet();
/*     */     
/* 316 */     Map<QueryParams, TrackedQuery> queries = (Map)this.trackedQueryTree.get(path);
/* 317 */     if (queries != null) {
/* 318 */       for (TrackedQuery query : queries.values()) {
/* 319 */         if (!query.querySpec.loadsAllData()) {
/* 320 */           ids.add(Long.valueOf(query.id));
/*     */         }
/*     */       }
/*     */     }
/* 324 */     return ids;
/*     */   }
/*     */   
/*     */   private void cacheTrackedQuery(TrackedQuery query) {
/* 328 */     assertValidTrackedQuery(query.querySpec);
/*     */     
/* 330 */     Map<QueryParams, TrackedQuery> trackedSet = (Map)this.trackedQueryTree.get(query.querySpec.getPath());
/* 331 */     if (trackedSet == null) {
/* 332 */       trackedSet = new java.util.HashMap();
/* 333 */       this.trackedQueryTree = this.trackedQueryTree.set(query.querySpec.getPath(), trackedSet);
/*     */     }
/*     */     
/*     */ 
/* 337 */     TrackedQuery existing = (TrackedQuery)trackedSet.get(query.querySpec.getParams());
/* 338 */     Utilities.hardAssert((existing == null) || (existing.id == query.id));
/*     */     
/* 340 */     trackedSet.put(query.querySpec.getParams(), query);
/*     */   }
/*     */   
/*     */   private void saveTrackedQuery(TrackedQuery query) {
/* 344 */     cacheTrackedQuery(query);
/* 345 */     this.storageLayer.saveTrackedQuery(query);
/*     */   }
/*     */   
/*     */   private List<TrackedQuery> getQueriesMatching(Predicate<TrackedQuery> predicate) {
/* 349 */     List<TrackedQuery> matching = new java.util.ArrayList();
/* 350 */     for (Map.Entry<Path, Map<QueryParams, TrackedQuery>> entry : this.trackedQueryTree) {
/* 351 */       for (TrackedQuery query : ((Map)entry.getValue()).values()) {
/* 352 */         if (predicate.evaluate(query)) {
/* 353 */           matching.add(query);
/*     */         }
/*     */       }
/*     */     }
/* 357 */     return matching;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/TrackedQueryManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */