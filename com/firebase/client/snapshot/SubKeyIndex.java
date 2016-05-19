/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ public class SubKeyIndex extends Index
/*    */ {
/*    */   private final ChildKey indexKey;
/*    */   
/*    */   public SubKeyIndex(ChildKey indexKey) {
/*  8 */     if (indexKey.isPriorityChildName()) {
/*  9 */       throw new IllegalArgumentException("Can't create SubKeyIndex with '.priority' as key. Please use PriorityIndex instead!");
/*    */     }
/* 11 */     this.indexKey = indexKey;
/*    */   }
/*    */   
/*    */   public boolean isDefinedOn(Node snapshot)
/*    */   {
/* 16 */     return !snapshot.getImmediateChild(this.indexKey).isEmpty();
/*    */   }
/*    */   
/*    */   public int compare(NamedNode a, NamedNode b)
/*    */   {
/* 21 */     Node aChild = a.getNode().getImmediateChild(this.indexKey);
/* 22 */     Node bChild = b.getNode().getImmediateChild(this.indexKey);
/* 23 */     int indexCmp = aChild.compareTo(bChild);
/* 24 */     if (indexCmp == 0) {
/* 25 */       return a.getName().compareTo(b.getName());
/*    */     }
/* 27 */     return indexCmp;
/*    */   }
/*    */   
/*    */ 
/*    */   public NamedNode makePost(ChildKey name, Node value)
/*    */   {
/* 33 */     Node node = EmptyNode.Empty().updateImmediateChild(this.indexKey, value);
/* 34 */     return new NamedNode(name, node);
/*    */   }
/*    */   
/*    */   public NamedNode maxPost()
/*    */   {
/* 39 */     Node node = EmptyNode.Empty().updateImmediateChild(this.indexKey, Node.MAX_NODE);
/* 40 */     return new NamedNode(ChildKey.getMaxName(), node);
/*    */   }
/*    */   
/*    */   public String getQueryDefinition()
/*    */   {
/* 45 */     return this.indexKey.asString();
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     if (this == o) return true;
/* 51 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*    */     }
/* 53 */     SubKeyIndex that = (SubKeyIndex)o;
/*    */     
/* 55 */     if (!this.indexKey.equals(that.indexKey)) { return false;
/*    */     }
/* 57 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 62 */     return this.indexKey.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/SubKeyIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */