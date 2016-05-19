/*    */ package com.firebase.client.core.operation;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ 
/*    */ public class ListenComplete extends Operation
/*    */ {
/*    */   public ListenComplete(OperationSource source, Path path)
/*    */   {
/*  9 */     super(Operation.OperationType.ListenComplete, source, path);
/* 10 */     assert (!source.isFromUser()) : "Can't have a listen complete from a user source";
/*    */   }
/*    */   
/*    */   public Operation operationForChild(com.firebase.client.snapshot.ChildKey childKey)
/*    */   {
/* 15 */     if (this.path.isEmpty()) {
/* 16 */       return new ListenComplete(this.source, Path.getEmptyPath());
/*    */     }
/* 18 */     return new ListenComplete(this.source, this.path.popFront());
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 24 */     return String.format("ListenComplete { path=%s, source=%s }", new Object[] { getPath(), getSource() });
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/operation/ListenComplete.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */