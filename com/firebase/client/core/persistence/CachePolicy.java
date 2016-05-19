/*   */ package com.firebase.client.core.persistence;
/*   */ 
/*   */ 
/*   */ 
/*   */ 
/*   */ 
/*   */ public abstract interface CachePolicy
/*   */ {
/* 9 */   public static final CachePolicy NONE = new CachePolicy() {
/* : */     public boolean shouldPrune(long currentSizeBytes, long countOfPrunableQueries) { return false; }
/* ; */     public boolean shouldCheckCacheSize(long serverUpdatesSinceLastCheck) { return false; }
/* < */     public float getPercentOfQueriesToPruneAtOnce() { return 0.0F; }
/* = */     public long getMaxNumberOfQueriesToKeep() { return Long.MAX_VALUE; }
/*   */   };
/*   */   
/*   */   public abstract boolean shouldPrune(long paramLong1, long paramLong2);
/*   */   
/*   */   public abstract boolean shouldCheckCacheSize(long paramLong);
/*   */   
/*   */   public abstract float getPercentOfQueriesToPruneAtOnce();
/*   */   
/*   */   public abstract long getMaxNumberOfQueriesToKeep();
/*   */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/CachePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */