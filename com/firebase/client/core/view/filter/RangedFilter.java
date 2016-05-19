/*     */ package com.firebase.client.core.view.filter;
/*     */ 
/*     */ import com.firebase.client.core.view.QueryParams;
/*     */ import com.firebase.client.snapshot.Index;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ 
/*     */ public class RangedFilter implements NodeFilter
/*     */ {
/*     */   private final IndexedFilter indexedFilter;
/*     */   private final Index index;
/*     */   private final NamedNode startPost;
/*     */   private final NamedNode endPost;
/*     */   
/*     */   public RangedFilter(QueryParams params)
/*     */   {
/*  17 */     this.indexedFilter = new IndexedFilter(params.getIndex());
/*  18 */     this.index = params.getIndex();
/*  19 */     this.startPost = getStartPost(params);
/*  20 */     this.endPost = getEndPost(params);
/*     */   }
/*     */   
/*     */   public NamedNode getStartPost() {
/*  24 */     return this.startPost;
/*     */   }
/*     */   
/*     */   public NamedNode getEndPost() {
/*  28 */     return this.endPost;
/*     */   }
/*     */   
/*     */   private static NamedNode getStartPost(QueryParams params) {
/*  32 */     if (params.hasStart()) {
/*  33 */       com.firebase.client.snapshot.ChildKey startName = params.getIndexStartName();
/*  34 */       return params.getIndex().makePost(startName, params.getIndexStartValue());
/*     */     }
/*  36 */     return params.getIndex().minPost();
/*     */   }
/*     */   
/*     */   private static NamedNode getEndPost(QueryParams params)
/*     */   {
/*  41 */     if (params.hasEnd()) {
/*  42 */       com.firebase.client.snapshot.ChildKey endName = params.getIndexEndName();
/*  43 */       return params.getIndex().makePost(endName, params.getIndexEndValue());
/*     */     }
/*  45 */     return params.getIndex().maxPost();
/*     */   }
/*     */   
/*     */   public boolean matches(NamedNode node)
/*     */   {
/*  50 */     if ((this.index.compare(getStartPost(), node) <= 0) && (this.index.compare(node, getEndPost()) <= 0)) {
/*  51 */       return true;
/*     */     }
/*  53 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IndexedNode updateChild(IndexedNode snap, com.firebase.client.snapshot.ChildKey key, com.firebase.client.snapshot.Node newChild, NodeFilter.CompleteChildSource source, ChildChangeAccumulator optChangeAccumulator)
/*     */   {
/*  59 */     if (!matches(new NamedNode(key, newChild))) {
/*  60 */       newChild = com.firebase.client.snapshot.EmptyNode.Empty();
/*     */     }
/*  62 */     return this.indexedFilter.updateChild(snap, key, newChild, source, optChangeAccumulator);
/*     */   }
/*     */   
/*     */   public IndexedNode updateFullNode(IndexedNode oldSnap, IndexedNode newSnap, ChildChangeAccumulator optChangeAccumulator) {
/*     */     IndexedNode filtered;
/*     */     IndexedNode filtered;
/*  68 */     if (newSnap.getNode().isLeafNode())
/*     */     {
/*  70 */       filtered = IndexedNode.from(com.firebase.client.snapshot.EmptyNode.Empty(), this.index);
/*     */     }
/*     */     else {
/*  73 */       filtered = newSnap.updatePriority(com.firebase.client.snapshot.PriorityUtilities.NullPriority());
/*  74 */       for (NamedNode child : newSnap) {
/*  75 */         if (!matches(child)) {
/*  76 */           filtered = filtered.updateChild(child.getName(), com.firebase.client.snapshot.EmptyNode.Empty());
/*     */         }
/*     */       }
/*     */     }
/*  80 */     return this.indexedFilter.updateFullNode(oldSnap, filtered, optChangeAccumulator);
/*     */   }
/*     */   
/*     */ 
/*     */   public IndexedNode updatePriority(IndexedNode oldSnap, com.firebase.client.snapshot.Node newPriority)
/*     */   {
/*  86 */     return oldSnap;
/*     */   }
/*     */   
/*     */   public NodeFilter getIndexedFilter()
/*     */   {
/*  91 */     return this.indexedFilter;
/*     */   }
/*     */   
/*     */   public Index getIndex()
/*     */   {
/*  96 */     return this.index;
/*     */   }
/*     */   
/*     */   public boolean filtersNodes()
/*     */   {
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/filter/RangedFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */