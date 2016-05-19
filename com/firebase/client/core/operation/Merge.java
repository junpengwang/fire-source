/*    */ package com.firebase.client.core.operation;
/*    */ 
/*    */ import com.firebase.client.core.CompoundWrite;
/*    */ import com.firebase.client.core.Path;
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Merge
/*    */   extends Operation
/*    */ {
/*    */   private final CompoundWrite children;
/*    */   
/*    */   public Merge(OperationSource source, Path path, CompoundWrite children)
/*    */   {
/* 17 */     super(Operation.OperationType.Merge, source, path);
/* 18 */     this.children = children;
/*    */   }
/*    */   
/*    */   public CompoundWrite getChildren() {
/* 22 */     return this.children;
/*    */   }
/*    */   
/*    */   public Operation operationForChild(ChildKey childKey) {
/* 26 */     if (this.path.isEmpty()) {
/* 27 */       CompoundWrite childTree = this.children.childCompoundWrite(new Path(new ChildKey[] { childKey }));
/* 28 */       if (childTree.isEmpty())
/*    */       {
/* 30 */         return null; }
/* 31 */       if (childTree.rootWrite() != null)
/*    */       {
/* 33 */         return new Overwrite(this.source, Path.getEmptyPath(), childTree.rootWrite());
/*    */       }
/* 35 */       return new Merge(this.source, Path.getEmptyPath(), childTree);
/*    */     }
/* 37 */     if (this.path.getFront().equals(childKey)) {
/* 38 */       return new Merge(this.source, this.path.popFront(), this.children);
/*    */     }
/*    */     
/* 41 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 47 */     return String.format("Merge { path=%s, source=%s, children=%s }", new Object[] { getPath(), getSource(), this.children });
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/operation/Merge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */