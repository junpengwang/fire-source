/*    */ package com.firebase.client.core;
/*    */ 
/*    */ import com.firebase.client.snapshot.EmptyNode;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SnapshotHolder
/*    */ {
/*    */   private Node rootNode;
/*    */   
/*    */   SnapshotHolder()
/*    */   {
/* 16 */     this.rootNode = EmptyNode.Empty();
/*    */   }
/*    */   
/*    */   public SnapshotHolder(Node node) {
/* 20 */     this.rootNode = node;
/*    */   }
/*    */   
/*    */   public Node getRootNode() {
/* 24 */     return this.rootNode;
/*    */   }
/*    */   
/*    */   public Node getNode(Path path) {
/* 28 */     return this.rootNode.getChild(path);
/*    */   }
/*    */   
/*    */   public void update(Path path, Node node) {
/* 32 */     this.rootNode = this.rootNode.updateChild(path, node);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/SnapshotHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */