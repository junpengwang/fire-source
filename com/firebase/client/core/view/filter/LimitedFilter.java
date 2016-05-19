/*     */ package com.firebase.client.core.view.filter;
/*     */ 
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.EmptyNode;
/*     */ import com.firebase.client.snapshot.Index;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ 
/*     */ public class LimitedFilter implements NodeFilter
/*     */ {
/*     */   private final RangedFilter rangedFilter;
/*     */   private final Index index;
/*     */   private final int limit;
/*     */   private final boolean reverse;
/*     */   
/*     */   public LimitedFilter(com.firebase.client.core.view.QueryParams params)
/*     */   {
/*  19 */     this.rangedFilter = new RangedFilter(params);
/*  20 */     this.index = params.getIndex();
/*  21 */     this.limit = params.getLimit();
/*  22 */     this.reverse = (!params.isViewFromLeft());
/*     */   }
/*     */   
/*     */   public IndexedNode updateChild(IndexedNode snap, ChildKey key, Node newChild, NodeFilter.CompleteChildSource source, ChildChangeAccumulator optChangeAccumulator)
/*     */   {
/*  27 */     if (!this.rangedFilter.matches(new NamedNode(key, newChild))) {
/*  28 */       newChild = EmptyNode.Empty();
/*     */     }
/*  30 */     if (snap.getNode().getImmediateChild(key).equals(newChild))
/*     */     {
/*  32 */       return snap; }
/*  33 */     if (snap.getNode().getChildCount() < this.limit) {
/*  34 */       return this.rangedFilter.getIndexedFilter().updateChild(snap, key, newChild, source, optChangeAccumulator);
/*     */     }
/*  36 */     return fullLimitUpdateChild(snap, key, newChild, source, optChangeAccumulator);
/*     */   }
/*     */   
/*     */ 
/*     */   private IndexedNode fullLimitUpdateChild(IndexedNode oldIndexed, ChildKey childKey, Node childSnap, NodeFilter.CompleteChildSource source, ChildChangeAccumulator optChangeAccumulator)
/*     */   {
/*  42 */     assert (oldIndexed.getNode().getChildCount() == this.limit);
/*  43 */     NamedNode newChildNamedNode = new NamedNode(childKey, childSnap);
/*  44 */     NamedNode windowBoundary = this.reverse ? oldIndexed.getFirstChild() : oldIndexed.getLastChild();
/*  45 */     boolean inRange = this.rangedFilter.matches(newChildNamedNode);
/*  46 */     if (oldIndexed.getNode().hasChild(childKey)) {
/*  47 */       Node oldChildSnap = oldIndexed.getNode().getImmediateChild(childKey);
/*  48 */       NamedNode nextChild = source.getChildAfterChild(this.index, windowBoundary, this.reverse);
/*  49 */       while ((nextChild != null) && ((nextChild.getName().equals(childKey)) || (oldIndexed.getNode().hasChild(nextChild.getName()))))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  54 */         nextChild = source.getChildAfterChild(this.index, nextChild, this.reverse);
/*     */       }
/*  56 */       int compareNext = nextChild == null ? 1 : this.index.compare(nextChild, newChildNamedNode, this.reverse);
/*  57 */       boolean remainsInWindow = (inRange) && (!childSnap.isEmpty()) && (compareNext >= 0);
/*  58 */       if (remainsInWindow) {
/*  59 */         if (optChangeAccumulator != null) {
/*  60 */           optChangeAccumulator.trackChildChange(com.firebase.client.core.view.Change.childChangedChange(childKey, childSnap, oldChildSnap));
/*     */         }
/*  62 */         return oldIndexed.updateChild(childKey, childSnap);
/*     */       }
/*  64 */       if (optChangeAccumulator != null) {
/*  65 */         optChangeAccumulator.trackChildChange(com.firebase.client.core.view.Change.childRemovedChange(childKey, oldChildSnap));
/*     */       }
/*  67 */       IndexedNode newIndexed = oldIndexed.updateChild(childKey, EmptyNode.Empty());
/*  68 */       boolean nextChildInRange = (nextChild != null) && (this.rangedFilter.matches(nextChild));
/*  69 */       if (nextChildInRange) {
/*  70 */         if (optChangeAccumulator != null) {
/*  71 */           optChangeAccumulator.trackChildChange(com.firebase.client.core.view.Change.childAddedChange(nextChild.getName(), nextChild.getNode()));
/*     */         }
/*  73 */         return newIndexed.updateChild(nextChild.getName(), nextChild.getNode());
/*     */       }
/*  75 */       return newIndexed;
/*     */     }
/*     */     
/*  78 */     if (childSnap.isEmpty())
/*     */     {
/*  80 */       return oldIndexed; }
/*  81 */     if (inRange) {
/*  82 */       if (this.index.compare(windowBoundary, newChildNamedNode, this.reverse) >= 0) {
/*  83 */         if (optChangeAccumulator != null) {
/*  84 */           optChangeAccumulator.trackChildChange(com.firebase.client.core.view.Change.childRemovedChange(windowBoundary.getName(), windowBoundary.getNode()));
/*  85 */           optChangeAccumulator.trackChildChange(com.firebase.client.core.view.Change.childAddedChange(childKey, childSnap));
/*     */         }
/*  87 */         return oldIndexed.updateChild(childKey, childSnap).updateChild(windowBoundary.getName(), EmptyNode.Empty());
/*     */       }
/*  89 */       return oldIndexed;
/*     */     }
/*     */     
/*  92 */     return oldIndexed;
/*     */   }
/*     */   
/*     */   public IndexedNode updateFullNode(IndexedNode oldSnap, IndexedNode newSnap, ChildChangeAccumulator optChangeAccumulator)
/*     */   {
/*     */     IndexedNode filtered;
/*     */     IndexedNode filtered;
/*  99 */     if ((newSnap.getNode().isLeafNode()) || (newSnap.getNode().isEmpty()))
/*     */     {
/* 101 */       filtered = IndexedNode.from(EmptyNode.Empty(), this.index);
/*     */     } else {
/* 103 */       filtered = newSnap;
/*     */       
/* 105 */       filtered = filtered.updatePriority(com.firebase.client.snapshot.PriorityUtilities.NullPriority());
/*     */       int sign;
/*     */       java.util.Iterator<NamedNode> iterator;
/*     */       NamedNode startPost;
/*     */       NamedNode endPost;
/* 110 */       int sign; if (this.reverse) {
/* 111 */         java.util.Iterator<NamedNode> iterator = newSnap.reverseIterator();
/* 112 */         NamedNode startPost = this.rangedFilter.getEndPost();
/* 113 */         NamedNode endPost = this.rangedFilter.getStartPost();
/* 114 */         sign = -1;
/*     */       } else {
/* 116 */         iterator = newSnap.iterator();
/* 117 */         startPost = this.rangedFilter.getStartPost();
/* 118 */         endPost = this.rangedFilter.getEndPost();
/* 119 */         sign = 1;
/*     */       }
/*     */       
/* 122 */       int count = 0;
/* 123 */       boolean foundStartPost = false;
/* 124 */       while (iterator.hasNext()) {
/* 125 */         NamedNode next = (NamedNode)iterator.next();
/* 126 */         if ((!foundStartPost) && (this.index.compare(startPost, next) * sign <= 0))
/*     */         {
/* 128 */           foundStartPost = true;
/*     */         }
/* 130 */         boolean inRange = (foundStartPost) && (count < this.limit) && (this.index.compare(next, endPost) * sign <= 0);
/* 131 */         if (inRange) {
/* 132 */           count++;
/*     */         } else {
/* 134 */           filtered = filtered.updateChild(next.getName(), EmptyNode.Empty());
/*     */         }
/*     */       }
/*     */     }
/* 138 */     return this.rangedFilter.getIndexedFilter().updateFullNode(oldSnap, filtered, optChangeAccumulator);
/*     */   }
/*     */   
/*     */ 
/*     */   public IndexedNode updatePriority(IndexedNode oldSnap, Node newPriority)
/*     */   {
/* 144 */     return oldSnap;
/*     */   }
/*     */   
/*     */   public NodeFilter getIndexedFilter()
/*     */   {
/* 149 */     return this.rangedFilter.getIndexedFilter();
/*     */   }
/*     */   
/*     */   public Index getIndex()
/*     */   {
/* 154 */     return this.index;
/*     */   }
/*     */   
/*     */   public boolean filtersNodes()
/*     */   {
/* 159 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/filter/LimitedFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */