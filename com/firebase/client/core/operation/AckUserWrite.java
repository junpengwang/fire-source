/*    */ package com.firebase.client.core.operation;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ 
/*    */ public class AckUserWrite extends Operation
/*    */ {
/*    */   private final boolean revert;
/*    */   
/*    */   public AckUserWrite(Path path, boolean revert)
/*    */   {
/* 11 */     super(Operation.OperationType.AckUserWrite, OperationSource.USER, path);
/* 12 */     this.revert = revert;
/*    */   }
/*    */   
/*    */   public boolean isRevert() {
/* 16 */     return this.revert;
/*    */   }
/*    */   
/*    */   public Operation operationForChild(com.firebase.client.snapshot.ChildKey childKey) {
/* 20 */     if (!this.path.isEmpty()) {
/* 21 */       return new AckUserWrite(this.path.popFront(), this.revert);
/*    */     }
/* 23 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 29 */     return String.format("AckUserWrite { path=%s, revert=%s }", new Object[] { getPath(), Boolean.valueOf(this.revert) });
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/operation/AckUserWrite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */