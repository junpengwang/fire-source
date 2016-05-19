/*     */ package com.firebase.client;
/*     */ 
/*     */ import com.firebase.client.snapshot.Node;
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
/*     */ 
/*     */ 
/*     */ public class Transaction
/*     */ {
/*     */   public static abstract interface Handler
/*     */   {
/*     */     public abstract Transaction.Result doTransaction(MutableData paramMutableData);
/*     */     
/*     */     public abstract void onComplete(FirebaseError paramFirebaseError, boolean paramBoolean, DataSnapshot paramDataSnapshot);
/*     */   }
/*     */   
/*     */   public static class Result
/*     */   {
/*     */     private boolean success;
/*     */     private Node data;
/*     */     
/*     */     private Result(boolean success, Node data)
/*     */     {
/*  39 */       this.success = success;
/*  40 */       this.data = data;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean isSuccess()
/*     */     {
/*  47 */       return this.success;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public Node getNode()
/*     */     {
/*  55 */       return this.data;
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
/*     */   public static Result abort()
/*     */   {
/*  94 */     return new Result(false, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Result success(MutableData resultData)
/*     */   {
/* 102 */     return new Result(true, resultData.getNode(), null);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/Transaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */