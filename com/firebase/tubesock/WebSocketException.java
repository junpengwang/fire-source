/*    */ package com.firebase.tubesock;
/*    */ 
/*    */ public class WebSocketException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public WebSocketException(String message) {
/*  8 */     super(message);
/*    */   }
/*    */   
/*    */   public WebSocketException(String message, Throwable t) {
/* 12 */     super(message, t);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/WebSocketException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */