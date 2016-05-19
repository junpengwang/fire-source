/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ public class ValueIndex extends Index
/*    */ {
/*  5 */   private static final ValueIndex INSTANCE = new ValueIndex();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ValueIndex getInstance()
/*    */   {
/* 12 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public boolean isDefinedOn(Node a)
/*    */   {
/* 17 */     return true;
/*    */   }
/*    */   
/*    */   public NamedNode makePost(ChildKey name, Node value)
/*    */   {
/* 22 */     return new NamedNode(name, value);
/*    */   }
/*    */   
/*    */   public NamedNode maxPost()
/*    */   {
/* 27 */     return new NamedNode(ChildKey.getMaxName(), Node.MAX_NODE);
/*    */   }
/*    */   
/*    */   public String getQueryDefinition()
/*    */   {
/* 32 */     return ".value";
/*    */   }
/*    */   
/*    */   public int compare(NamedNode one, NamedNode two)
/*    */   {
/* 37 */     int indexCmp = one.getNode().compareTo(two.getNode());
/* 38 */     if (indexCmp == 0) {
/* 39 */       return one.getName().compareTo(two.getName());
/*    */     }
/* 41 */     return indexCmp;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 48 */     return 4;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 53 */     return o instanceof ValueIndex;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 58 */     return "ValueIndex";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/ValueIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */