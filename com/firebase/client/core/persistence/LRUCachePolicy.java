/*    */ package com.firebase.client.core.persistence;
/*    */ 
/*    */ public class LRUCachePolicy implements CachePolicy
/*    */ {
/*    */   private static final long SERVER_UPDATES_BETWEEN_CACHE_SIZE_CHECKS = 1000L;
/*    */   private static final long MAX_NUMBER_OF_PRUNABLE_QUERIES_TO_KEEP = 1000L;
/*    */   private static final float PERCENT_OF_QUERIES_TO_PRUNE_AT_ONCE = 0.2F;
/*    */   public final long maxSizeBytes;
/*    */   
/*    */   public LRUCachePolicy(long maxSizeBytes) {
/* 11 */     this.maxSizeBytes = maxSizeBytes;
/*    */   }
/*    */   
/*    */   public boolean shouldPrune(long currentSizeBytes, long countOfPrunableQueries) {
/* 15 */     return (currentSizeBytes > this.maxSizeBytes) || (countOfPrunableQueries > 1000L);
/*    */   }
/*    */   
/*    */   public boolean shouldCheckCacheSize(long serverUpdatesSinceLastCheck) {
/* 19 */     return serverUpdatesSinceLastCheck > 1000L;
/*    */   }
/*    */   
/*    */   public float getPercentOfQueriesToPruneAtOnce() {
/* 23 */     return 0.2F;
/*    */   }
/*    */   
/*    */   public long getMaxNumberOfQueriesToKeep() {
/* 27 */     return 1000L;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/LRUCachePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */