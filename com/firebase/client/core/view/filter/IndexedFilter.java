/*    */ package com.firebase.client.core.view.filter;
/*    */ 
/*    */ import com.firebase.client.core.view.Change;
/*    */ import com.firebase.client.snapshot.Index;
/*    */ import com.firebase.client.snapshot.IndexedNode;
/*    */ import com.firebase.client.snapshot.NamedNode;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ 
/*    */ public class IndexedFilter implements NodeFilter
/*    */ {
/*    */   private final Index index;
/*    */   
/*    */   public IndexedFilter(Index index)
/*    */   {
/* 15 */     this.index = index;
/*    */   }
/*    */   
/*    */   public IndexedNode updateChild(IndexedNode indexedNode, com.firebase.client.snapshot.ChildKey key, Node newChild, NodeFilter.CompleteChildSource source, ChildChangeAccumulator optChangeAccumulator)
/*    */   {
/* 20 */     assert (indexedNode.hasIndex(this.index)) : "The index must match the filter";
/* 21 */     Node snap = indexedNode.getNode();
/* 22 */     Node oldChild = snap.getImmediateChild(key);
/* 23 */     if (oldChild.equals(newChild))
/*    */     {
/* 25 */       return indexedNode;
/*    */     }
/* 27 */     if (optChangeAccumulator != null) {
/* 28 */       if (newChild.isEmpty()) {
/* 29 */         if (snap.hasChild(key)) {
/* 30 */           optChangeAccumulator.trackChildChange(Change.childRemovedChange(key, oldChild));
/*    */         }
/* 32 */         else if ((!$assertionsDisabled) && (!snap.isLeafNode())) throw new AssertionError("A child remove without an old child only makes sense on a leaf node");
/*    */       }
/* 34 */       else if (oldChild.isEmpty()) {
/* 35 */         optChangeAccumulator.trackChildChange(Change.childAddedChange(key, newChild));
/*    */       } else {
/* 37 */         optChangeAccumulator.trackChildChange(Change.childChangedChange(key, newChild, oldChild));
/*    */       }
/*    */     }
/* 40 */     if ((snap.isLeafNode()) && (newChild.isEmpty())) {
/* 41 */       return indexedNode;
/*    */     }
/*    */     
/* 44 */     return indexedNode.updateChild(key, newChild);
/*    */   }
/*    */   
/*    */ 
/*    */   public IndexedNode updateFullNode(IndexedNode oldSnap, IndexedNode newSnap, ChildChangeAccumulator optChangeAccumulator)
/*    */   {
/* 50 */     assert (newSnap.hasIndex(this.index)) : "Can't use IndexedNode that doesn't have filter's index";
/* 51 */     if (optChangeAccumulator != null) {
/* 52 */       for (NamedNode child : oldSnap.getNode()) {
/* 53 */         if (!newSnap.getNode().hasChild(child.getName())) {
/* 54 */           optChangeAccumulator.trackChildChange(Change.childRemovedChange(child.getName(), child.getNode()));
/*    */         }
/*    */       }
/* 57 */       if (!newSnap.getNode().isLeafNode()) {
/* 58 */         for (NamedNode child : newSnap.getNode()) {
/* 59 */           if (oldSnap.getNode().hasChild(child.getName())) {
/* 60 */             Node oldChild = oldSnap.getNode().getImmediateChild(child.getName());
/* 61 */             if (!oldChild.equals(child.getNode())) {
/* 62 */               optChangeAccumulator.trackChildChange(Change.childChangedChange(child.getName(), child.getNode(), oldChild));
/*    */             }
/*    */           } else {
/* 65 */             optChangeAccumulator.trackChildChange(Change.childAddedChange(child.getName(), child.getNode()));
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 70 */     return newSnap;
/*    */   }
/*    */   
/*    */   public IndexedNode updatePriority(IndexedNode oldSnap, Node newPriority)
/*    */   {
/* 75 */     if (oldSnap.getNode().isEmpty()) {
/* 76 */       return oldSnap;
/*    */     }
/* 78 */     return oldSnap.updatePriority(newPriority);
/*    */   }
/*    */   
/*    */ 
/*    */   public NodeFilter getIndexedFilter()
/*    */   {
/* 84 */     return this;
/*    */   }
/*    */   
/*    */   public Index getIndex()
/*    */   {
/* 89 */     return this.index;
/*    */   }
/*    */   
/*    */   public boolean filtersNodes()
/*    */   {
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/filter/IndexedFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */