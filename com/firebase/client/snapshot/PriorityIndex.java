/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ public class PriorityIndex extends Index
/*    */ {
/*  5 */   private static final PriorityIndex INSTANCE = new PriorityIndex();
/*    */   
/*    */   public static PriorityIndex getInstance() {
/*  8 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int compare(NamedNode a, NamedNode b)
/*    */   {
/* 17 */     Node aPrio = a.getNode().getPriority();
/* 18 */     Node bPrio = b.getNode().getPriority();
/* 19 */     return NodeUtilities.nameAndPriorityCompare(a.getName(), aPrio, b.getName(), bPrio);
/*    */   }
/*    */   
/*    */   public boolean isDefinedOn(Node a)
/*    */   {
/* 24 */     return !a.getPriority().isEmpty();
/*    */   }
/*    */   
/*    */   public NamedNode makePost(ChildKey name, Node value)
/*    */   {
/* 29 */     return new NamedNode(name, new StringNode("[PRIORITY-POST]", value));
/*    */   }
/*    */   
/*    */   public NamedNode maxPost()
/*    */   {
/* 34 */     return makePost(ChildKey.getMaxName(), Node.MAX_NODE);
/*    */   }
/*    */   
/*    */   public String getQueryDefinition()
/*    */   {
/* 39 */     throw new IllegalArgumentException("Can't get query definition on priority index!");
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 44 */     return o instanceof PriorityIndex;
/*    */   }
/*    */   
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 50 */     return 3155577;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 55 */     return "PriorityIndex";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/PriorityIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */