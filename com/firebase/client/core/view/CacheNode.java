/*    */ package com.firebase.client.core.view;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ import com.firebase.client.snapshot.IndexedNode;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheNode
/*    */ {
/*    */   private final IndexedNode indexedNode;
/*    */   private final boolean fullyInitialized;
/*    */   private final boolean filtered;
/*    */   
/*    */   public CacheNode(IndexedNode node, boolean fullyInitialized, boolean filtered)
/*    */   {
/* 20 */     this.indexedNode = node;
/* 21 */     this.fullyInitialized = fullyInitialized;
/* 22 */     this.filtered = filtered;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isFullyInitialized()
/*    */   {
/* 29 */     return this.fullyInitialized;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isFiltered()
/*    */   {
/* 36 */     return this.filtered;
/*    */   }
/*    */   
/*    */   public boolean isCompleteForPath(Path path) {
/* 40 */     if (path.isEmpty()) {
/* 41 */       return (isFullyInitialized()) && (!this.filtered);
/*    */     }
/* 43 */     ChildKey childKey = path.getFront();
/* 44 */     return isCompleteForChild(childKey);
/*    */   }
/*    */   
/*    */   public boolean isCompleteForChild(ChildKey key)
/*    */   {
/* 49 */     return ((isFullyInitialized()) && (!this.filtered)) || (this.indexedNode.getNode().hasChild(key));
/*    */   }
/*    */   
/*    */   public Node getNode() {
/* 53 */     return this.indexedNode.getNode();
/*    */   }
/*    */   
/*    */   public IndexedNode getIndexedNode() {
/* 57 */     return this.indexedNode;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/CacheNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */