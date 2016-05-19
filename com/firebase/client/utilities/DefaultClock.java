/*   */ package com.firebase.client.utilities;
/*   */ 
/*   */ public class DefaultClock implements Clock
/*   */ {
/*   */   public long millis() {
/* 6 */     return System.currentTimeMillis();
/*   */   }
/*   */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/DefaultClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */