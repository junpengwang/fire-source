/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface Node
/*    */   extends Comparable<Node>, Iterable<NamedNode>
/*    */ {
/* 49 */   public static final ChildrenNode MAX_NODE = new ChildrenNode()
/*    */   {
/*    */     public int compareTo(Node other) {
/* 52 */       return other == this ? 0 : 1;
/*    */     }
/*    */     
/*    */     public boolean equals(Object other)
/*    */     {
/* 57 */       return other == this;
/*    */     }
/*    */     
/*    */     public Node getPriority()
/*    */     {
/* 62 */       return this;
/*    */     }
/*    */     
/*    */     public boolean isEmpty()
/*    */     {
/* 67 */       return false;
/*    */     }
/*    */     
/*    */     public boolean hasChild(ChildKey childKey)
/*    */     {
/* 72 */       return false;
/*    */     }
/*    */     
/*    */     public Node getImmediateChild(ChildKey name)
/*    */     {
/* 77 */       if (name.isPriorityChildName()) {
/* 78 */         return getPriority();
/*    */       }
/* 80 */       return EmptyNode.Empty();
/*    */     }
/*    */     
/*    */ 
/*    */     public String toString()
/*    */     {
/* 86 */       return "<Max Node>";
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract boolean isLeafNode();
/*    */   
/*    */   public abstract Node getPriority();
/*    */   
/*    */   public abstract Node getChild(Path paramPath);
/*    */   
/*    */   public abstract Node getImmediateChild(ChildKey paramChildKey);
/*    */   
/*    */   public abstract Node updateImmediateChild(ChildKey paramChildKey, Node paramNode);
/*    */   
/*    */   public abstract ChildKey getPredecessorChildKey(ChildKey paramChildKey);
/*    */   
/*    */   public abstract ChildKey getSuccessorChildKey(ChildKey paramChildKey);
/*    */   
/*    */   public abstract Node updateChild(Path paramPath, Node paramNode);
/*    */   
/*    */   public abstract Node updatePriority(Node paramNode);
/*    */   
/*    */   public abstract boolean hasChild(ChildKey paramChildKey);
/*    */   
/*    */   public abstract boolean isEmpty();
/*    */   
/*    */   public abstract int getChildCount();
/*    */   
/*    */   public abstract Object getValue();
/*    */   
/*    */   public abstract Object getValue(boolean paramBoolean);
/*    */   
/*    */   public abstract String getHash();
/*    */   
/*    */   public abstract String getHashString();
/*    */   
/*    */   public abstract Iterator<NamedNode> reverseIterator();
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/Node.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */