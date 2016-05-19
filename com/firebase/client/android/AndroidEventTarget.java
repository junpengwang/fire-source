/*    */ package com.firebase.client.android;
/*    */ 
/*    */ import android.os.Handler;
/*    */ import android.os.Looper;
/*    */ import com.firebase.client.EventTarget;
/*    */ 
/*    */ public class AndroidEventTarget implements EventTarget
/*    */ {
/*    */   private final Handler handler;
/*    */   
/*    */   public AndroidEventTarget()
/*    */   {
/* 13 */     this.handler = new Handler(Looper.getMainLooper());
/*    */   }
/*    */   
/*    */   public void postEvent(Runnable r)
/*    */   {
/* 18 */     this.handler.post(r);
/*    */   }
/*    */   
/*    */   public void shutdown() {}
/*    */   
/*    */   public void restart() {}
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/android/AndroidEventTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */