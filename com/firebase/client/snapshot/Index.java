/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public abstract class Index implements Comparator<NamedNode>
/*    */ {
/*    */   public abstract boolean isDefinedOn(Node paramNode);
/*    */   
/*    */   public boolean indexedValueChanged(Node oldNode, Node newNode) {
/* 10 */     NamedNode oldWrapped = new NamedNode(ChildKey.getMinName(), oldNode);
/* 11 */     NamedNode newWrapped = new NamedNode(ChildKey.getMinName(), newNode);
/* 12 */     return compare(oldWrapped, newWrapped) != 0;
/*    */   }
/*    */   
/*    */   public abstract NamedNode makePost(ChildKey paramChildKey, Node paramNode);
/*    */   
/*    */   public NamedNode minPost() {
/* 18 */     return NamedNode.getMinNode();
/*    */   }
/*    */   
/*    */   public abstract NamedNode maxPost();
/*    */   
/*    */   public abstract String getQueryDefinition();
/*    */   
/*    */   public static Index fromQueryDefinition(String str) {
/* 26 */     if (str.equals(".value"))
/* 27 */       return ValueIndex.getInstance();
/* 28 */     if (str.equals(".key"))
/* 29 */       return KeyIndex.getInstance();
/* 30 */     if (str.equals(".priority")) {
/* 31 */       throw new IllegalStateException("queryDefinition shouldn't ever be .priority since it's the default");
/*    */     }
/* 33 */     return new SubKeyIndex(ChildKey.fromString(str));
/*    */   }
/*    */   
/*    */   public int compare(NamedNode one, NamedNode two, boolean reverse)
/*    */   {
/* 38 */     if (reverse) {
/* 39 */       return compare(two, one);
/*    */     }
/* 41 */     return compare(one, two);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/Index.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */