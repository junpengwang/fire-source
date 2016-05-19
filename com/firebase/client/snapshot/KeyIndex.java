/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ public class KeyIndex extends Index
/*    */ {
/*  5 */   private static final KeyIndex INSTANCE = new KeyIndex();
/*    */   
/*    */   public static KeyIndex getInstance() {
/*  8 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isDefinedOn(Node a)
/*    */   {
/* 17 */     return true;
/*    */   }
/*    */   
/*    */   public NamedNode makePost(ChildKey name, Node value)
/*    */   {
/* 22 */     assert ((value instanceof StringNode));
/*    */     
/* 24 */     return new NamedNode(ChildKey.fromString((String)value.getValue()), EmptyNode.Empty());
/*    */   }
/*    */   
/*    */   public NamedNode maxPost()
/*    */   {
/* 29 */     return NamedNode.getMaxNode();
/*    */   }
/*    */   
/*    */   public String getQueryDefinition()
/*    */   {
/* 34 */     return ".key";
/*    */   }
/*    */   
/*    */   public int compare(NamedNode o1, NamedNode o2)
/*    */   {
/* 39 */     return o1.getName().compareTo(o2.getName());
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 44 */     return o instanceof KeyIndex;
/*    */   }
/*    */   
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 50 */     return 37;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 55 */     return "KeyIndex";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/KeyIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */