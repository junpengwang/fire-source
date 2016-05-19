/*    */ package com.firebase.client.core.operation;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ 
/*    */ public abstract class Operation { protected final OperationType type;
/*    */   protected final OperationSource source;
/*    */   protected final Path path;
/*    */   
/*  9 */   public static enum OperationType { Overwrite,  Merge,  AckUserWrite,  ListenComplete;
/*    */     
/*    */ 
/*    */     private OperationType() {}
/*    */   }
/*    */   
/*    */   protected Operation(OperationType type, OperationSource source, Path path)
/*    */   {
/* 17 */     this.type = type;
/* 18 */     this.source = source;
/* 19 */     this.path = path;
/*    */   }
/*    */   
/*    */   public Path getPath() {
/* 23 */     return this.path;
/*    */   }
/*    */   
/*    */   public OperationSource getSource() {
/* 27 */     return this.source;
/*    */   }
/*    */   
/*    */   public OperationType getType() {
/* 31 */     return this.type;
/*    */   }
/*    */   
/*    */   public abstract Operation operationForChild(com.firebase.client.snapshot.ChildKey paramChildKey);
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/operation/Operation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */