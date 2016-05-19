/*    */ package com.firebase.client.core.operation;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ 
/*    */ public class Overwrite extends Operation
/*    */ {
/*    */   private final Node snapshot;
/*    */   
/*    */   public Overwrite(OperationSource source, Path path, Node snapshot)
/*    */   {
/* 12 */     super(Operation.OperationType.Overwrite, source, path);
/* 13 */     this.snapshot = snapshot;
/*    */   }
/*    */   
/*    */   public Node getSnapshot() {
/* 17 */     return this.snapshot;
/*    */   }
/*    */   
/*    */   public Operation operationForChild(com.firebase.client.snapshot.ChildKey childKey) {
/* 21 */     if (this.path.isEmpty()) {
/* 22 */       return new Overwrite(this.source, Path.getEmptyPath(), this.snapshot.getImmediateChild(childKey));
/*    */     }
/* 24 */     return new Overwrite(this.source, this.path.popFront(), this.snapshot);
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 30 */     return String.format("Overwrite { path=%s, source=%s, snapshot=%s }", new Object[] { getPath(), getSource(), this.snapshot });
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/operation/Overwrite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */