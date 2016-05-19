/*    */ package com.firebase.client.core;
/*    */ 
/*    */ public class AndroidSupport {
/*  4 */   private static final boolean IS_ANDROID = ;
/*    */   
/*    */   private static boolean checkAndroid() {
/*    */     try {
/*  8 */       Class contextClass = Class.forName("android.app.Activity");
/*  9 */       return true;
/*    */     } catch (ClassNotFoundException e) {}
/* 11 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean isAndroid()
/*    */   {
/* 16 */     return IS_ANDROID;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/AndroidSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */