/*    */ package com.firebase.client.utilities.tuple;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeAndPath
/*    */ {
/*    */   private Node node;
/*    */   private Path path;
/*    */   
/*    */   public NodeAndPath(Node node, Path path)
/*    */   {
/* 17 */     this.node = node;
/* 18 */     this.path = path;
/*    */   }
/*    */   
/*    */   public Node getNode() {
/* 22 */     return this.node;
/*    */   }
/*    */   
/*    */   public void setNode(Node node) {
/* 26 */     this.node = node;
/*    */   }
/*    */   
/*    */   public Path getPath() {
/* 30 */     return this.path;
/*    */   }
/*    */   
/*    */   public void setPath(Path path) {
/* 34 */     this.path = path;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/tuple/NodeAndPath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */