/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ public class NamedNode
/*    */ {
/*    */   private final ChildKey name;
/*    */   private final Node node;
/*  7 */   private static final NamedNode MIN_NODE = new NamedNode(ChildKey.getMinName(), EmptyNode.Empty());
/*  8 */   private static final NamedNode MAX_NODE = new NamedNode(ChildKey.getMaxName(), Node.MAX_NODE);
/*    */   
/*    */   public static NamedNode getMinNode() {
/* 11 */     return MIN_NODE;
/*    */   }
/*    */   
/*    */   public static NamedNode getMaxNode() {
/* 15 */     return MAX_NODE;
/*    */   }
/*    */   
/*    */   public NamedNode(ChildKey name, Node node) {
/* 19 */     this.name = name;
/* 20 */     this.node = node;
/*    */   }
/*    */   
/*    */   public ChildKey getName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public Node getNode() {
/* 28 */     return this.node;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 33 */     return "NamedNode{name=" + this.name + ", node=" + this.node + '}';
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 41 */     if (this == o) return true;
/* 42 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*    */     }
/* 44 */     NamedNode namedNode = (NamedNode)o;
/*    */     
/* 46 */     if (!this.name.equals(namedNode.name)) return false;
/* 47 */     if (!this.node.equals(namedNode.node)) { return false;
/*    */     }
/* 49 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 54 */     int result = this.name.hashCode();
/* 55 */     result = 31 * result + this.node.hashCode();
/* 56 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/NamedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */