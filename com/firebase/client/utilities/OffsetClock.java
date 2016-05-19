/*    */ package com.firebase.client.utilities;
/*    */ 
/*    */ public class OffsetClock implements Clock {
/*    */   private final Clock baseClock;
/*  5 */   private long offset = 0L;
/*    */   
/*    */   public OffsetClock(Clock baseClock, long offset) {
/*  8 */     this.baseClock = baseClock;
/*  9 */     this.offset = offset;
/*    */   }
/*    */   
/*    */   public void setOffset(long offset) {
/* 13 */     this.offset = offset;
/*    */   }
/*    */   
/*    */   public long millis()
/*    */   {
/* 18 */     return this.baseClock.millis() + this.offset;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/OffsetClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */