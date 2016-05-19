/*     */ package com.firebase.client.core.persistence;
/*     */ 
/*     */ import com.firebase.client.core.CompoundWrite;
/*     */ import com.firebase.client.core.Context;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.UserWriteRecord;
/*     */ import com.firebase.client.core.view.CacheNode;
/*     */ import com.firebase.client.core.view.QuerySpec;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.utilities.Clock;
/*     */ import com.firebase.client.utilities.DefaultClock;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ public class DefaultPersistenceManager implements PersistenceManager
/*     */ {
/*     */   private final PersistenceStorageEngine storageLayer;
/*     */   private final TrackedQueryManager trackedQueryManager;
/*     */   private final LogWrapper logger;
/*     */   private final CachePolicy cachePolicy;
/*  26 */   private long serverCacheUpdatesSinceLastPruneCheck = 0L;
/*     */   
/*     */   public DefaultPersistenceManager(Context ctx, PersistenceStorageEngine engine, CachePolicy cachePolicy) {
/*  29 */     this(ctx, engine, cachePolicy, new DefaultClock());
/*     */   }
/*     */   
/*     */   public DefaultPersistenceManager(Context ctx, PersistenceStorageEngine engine, CachePolicy cachePolicy, Clock clock) {
/*  33 */     this.storageLayer = engine;
/*  34 */     this.logger = ctx.getLogger("Persistence");
/*  35 */     this.trackedQueryManager = new TrackedQueryManager(this.storageLayer, this.logger, clock);
/*  36 */     this.cachePolicy = cachePolicy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void saveUserOverwrite(Path path, Node node, long writeId)
/*     */   {
/*  47 */     this.storageLayer.saveUserOverwrite(path, node, writeId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void saveUserMerge(Path path, CompoundWrite children, long writeId)
/*     */   {
/*  58 */     this.storageLayer.saveUserMerge(path, children, writeId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeUserWrite(long writeId)
/*     */   {
/*  67 */     this.storageLayer.removeUserWrite(writeId);
/*     */   }
/*     */   
/*     */   public void removeAllUserWrites()
/*     */   {
/*  72 */     this.storageLayer.removeAllUserWrites();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyUserWriteToServerCache(Path path, Node node)
/*     */   {
/*  84 */     if (!this.trackedQueryManager.hasActiveDefaultQuery(path)) {
/*  85 */       this.storageLayer.overwriteServerCache(path, node);
/*  86 */       this.trackedQueryManager.ensureCompleteTrackedQuery(path);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void applyUserWriteToServerCache(Path path, CompoundWrite merge)
/*     */   {
/*  93 */     for (Map.Entry<Path, Node> write : merge) {
/*  94 */       Path writePath = path.child((Path)write.getKey());
/*  95 */       Node writeNode = (Node)write.getValue();
/*  96 */       applyUserWriteToServerCache(writePath, writeNode);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<UserWriteRecord> loadUserWrites()
/*     */   {
/* 106 */     return this.storageLayer.loadUserWrites();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CacheNode serverCache(QuerySpec query)
/*     */   {
/*     */     Set<ChildKey> trackedKeys;
/*     */     
/*     */ 
/*     */     boolean complete;
/*     */     
/*     */     Set<ChildKey> trackedKeys;
/*     */     
/* 120 */     if (this.trackedQueryManager.isQueryComplete(query)) {
/* 121 */       boolean complete = true;
/* 122 */       TrackedQuery trackedQuery = this.trackedQueryManager.findTrackedQuery(query);
/* 123 */       Set<ChildKey> trackedKeys; if ((!query.loadsAllData()) && (trackedQuery != null) && (trackedQuery.complete)) {
/* 124 */         trackedKeys = this.storageLayer.loadTrackedQueryKeys(trackedQuery.id);
/*     */       } else {
/* 126 */         trackedKeys = null;
/*     */       }
/*     */     } else {
/* 129 */       complete = false;
/* 130 */       trackedKeys = this.trackedQueryManager.getKnownCompleteChildren(query.getPath());
/*     */     }
/*     */     
/*     */ 
/* 134 */     Node serverCacheNode = this.storageLayer.serverCache(query.getPath());
/* 135 */     if (trackedKeys != null) {
/* 136 */       Node filteredNode = com.firebase.client.snapshot.EmptyNode.Empty();
/* 137 */       for (ChildKey key : trackedKeys) {
/* 138 */         filteredNode = filteredNode.updateImmediateChild(key, serverCacheNode.getImmediateChild(key));
/*     */       }
/* 140 */       return new CacheNode(IndexedNode.from(filteredNode, query.getIndex()), complete, true);
/*     */     }
/* 142 */     return new CacheNode(IndexedNode.from(serverCacheNode, query.getIndex()), complete, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateServerCache(QuerySpec query, Node node)
/*     */   {
/* 148 */     if (query.loadsAllData()) {
/* 149 */       this.storageLayer.overwriteServerCache(query.getPath(), node);
/*     */     } else {
/* 151 */       this.storageLayer.mergeIntoServerCache(query.getPath(), node);
/*     */     }
/* 153 */     setQueryComplete(query);
/* 154 */     doPruneCheckAfterServerUpdate();
/*     */   }
/*     */   
/*     */   public void updateServerCache(Path path, CompoundWrite children)
/*     */   {
/* 159 */     this.storageLayer.mergeIntoServerCache(path, children);
/* 160 */     doPruneCheckAfterServerUpdate();
/*     */   }
/*     */   
/*     */   public void setQueryActive(QuerySpec query)
/*     */   {
/* 165 */     this.trackedQueryManager.setQueryActive(query);
/*     */   }
/*     */   
/*     */   public void setQueryInactive(QuerySpec query)
/*     */   {
/* 170 */     this.trackedQueryManager.setQueryInactive(query);
/*     */   }
/*     */   
/*     */   public void setQueryComplete(QuerySpec query)
/*     */   {
/* 175 */     if (query.loadsAllData()) {
/* 176 */       this.trackedQueryManager.setQueriesComplete(query.getPath());
/*     */     } else {
/* 178 */       this.trackedQueryManager.setQueryCompleteIfExists(query);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTrackedQueryKeys(QuerySpec query, Set<ChildKey> keys)
/*     */   {
/* 184 */     assert (!query.loadsAllData()) : "We should only track keys for filtered queries.";
/* 185 */     TrackedQuery trackedQuery = this.trackedQueryManager.findTrackedQuery(query);
/* 186 */     assert ((trackedQuery != null) && (trackedQuery.active)) : "We only expect tracked keys for currently-active queries.";
/*     */     
/* 188 */     this.storageLayer.saveTrackedQueryKeys(trackedQuery.id, keys);
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateTrackedQueryKeys(QuerySpec query, Set<ChildKey> added, Set<ChildKey> removed)
/*     */   {
/* 194 */     assert (!query.loadsAllData()) : "We should only track keys for filtered queries.";
/* 195 */     TrackedQuery trackedQuery = this.trackedQueryManager.findTrackedQuery(query);
/* 196 */     assert ((trackedQuery != null) && (trackedQuery.active)) : "We only expect tracked keys for currently-active queries.";
/*     */     
/* 198 */     this.storageLayer.updateTrackedQueryKeys(trackedQuery.id, added, removed);
/*     */   }
/*     */   
/*     */   public <T> T runInTransaction(Callable<T> callable)
/*     */   {
/*     */     try
/*     */     {
/* 205 */       this.storageLayer.beginTransaction();
/* 206 */       T result = callable.call();
/* 207 */       this.storageLayer.setTransactionSuccessful();
/* 208 */       return result;
/*     */     } catch (Throwable e) {
/* 210 */       throw new RuntimeException(e);
/*     */     } finally {
/* 212 */       this.storageLayer.endTransaction();
/*     */     }
/*     */   }
/*     */   
/*     */   private void doPruneCheckAfterServerUpdate() {
/* 217 */     this.serverCacheUpdatesSinceLastPruneCheck += 1L;
/* 218 */     if (this.cachePolicy.shouldCheckCacheSize(this.serverCacheUpdatesSinceLastPruneCheck)) {
/* 219 */       if (this.logger.logsDebug()) this.logger.debug("Reached prune check threshold.");
/* 220 */       this.serverCacheUpdatesSinceLastPruneCheck = 0L;
/* 221 */       boolean canPrune = true;
/* 222 */       long cacheSize = this.storageLayer.serverCacheEstimatedSizeInBytes();
/* 223 */       if (this.logger.logsDebug()) this.logger.debug("Cache size: " + cacheSize);
/* 224 */       while ((canPrune) && (this.cachePolicy.shouldPrune(cacheSize, this.trackedQueryManager.countOfPrunableQueries()))) {
/* 225 */         PruneForest pruneForest = this.trackedQueryManager.pruneOldQueries(this.cachePolicy);
/* 226 */         if (pruneForest.prunesAnything()) {
/* 227 */           this.storageLayer.pruneCache(Path.getEmptyPath(), pruneForest);
/*     */         } else {
/* 229 */           canPrune = false;
/*     */         }
/* 231 */         cacheSize = this.storageLayer.serverCacheEstimatedSizeInBytes();
/* 232 */         if (this.logger.logsDebug()) this.logger.debug("Cache size after prune: " + cacheSize);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/DefaultPersistenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */