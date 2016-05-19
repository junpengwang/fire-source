/*     */ package com.firebase.client.snapshot;
/*     */ 
/*     */ import com.firebase.client.collection.ImmutableSortedSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedNode
/*     */   implements Iterable<NamedNode>
/*     */ {
/*  18 */   private static final ImmutableSortedSet<NamedNode> FALLBACK_INDEX = new ImmutableSortedSet(Collections.emptyList(), null);
/*     */   
/*     */   private final Node node;
/*     */   
/*     */   private ImmutableSortedSet<NamedNode> indexed;
/*     */   
/*     */   private final Index index;
/*     */   
/*     */   private IndexedNode(Node node, Index index)
/*     */   {
/*  28 */     this.index = index;
/*  29 */     this.node = node;
/*     */     
/*  31 */     this.indexed = null;
/*     */   }
/*     */   
/*     */   private IndexedNode(Node node, Index index, ImmutableSortedSet<NamedNode> indexed) {
/*  35 */     this.index = index;
/*  36 */     this.node = node;
/*  37 */     this.indexed = indexed;
/*     */   }
/*     */   
/*     */   private void ensureIndexed() {
/*  41 */     if (this.indexed == null)
/*     */     {
/*  43 */       if (this.index.equals(KeyIndex.getInstance())) {
/*  44 */         this.indexed = FALLBACK_INDEX;
/*     */       } else {
/*  46 */         List<NamedNode> children = new ArrayList();
/*  47 */         boolean sawIndexedValue = false;
/*  48 */         for (NamedNode entry : this.node) {
/*  49 */           sawIndexedValue = (sawIndexedValue) || (this.index.isDefinedOn(entry.getNode()));
/*  50 */           NamedNode namedNode = new NamedNode(entry.getName(), entry.getNode());
/*  51 */           children.add(namedNode);
/*     */         }
/*  53 */         if (sawIndexedValue) {
/*  54 */           this.indexed = new ImmutableSortedSet(children, this.index);
/*     */         } else {
/*  56 */           this.indexed = FALLBACK_INDEX;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static IndexedNode from(Node node) {
/*  63 */     return new IndexedNode(node, PriorityIndex.getInstance());
/*     */   }
/*     */   
/*     */   public static IndexedNode from(Node node, Index index) {
/*  67 */     return new IndexedNode(node, index);
/*     */   }
/*     */   
/*     */   public boolean hasIndex(Index index) {
/*  71 */     return this.index.equals(index);
/*     */   }
/*     */   
/*     */   public Node getNode() {
/*  75 */     return this.node;
/*     */   }
/*     */   
/*     */   public Iterator<NamedNode> iterator() {
/*  79 */     ensureIndexed();
/*  80 */     if (this.indexed == FALLBACK_INDEX) {
/*  81 */       return this.node.iterator();
/*     */     }
/*  83 */     return this.indexed.iterator();
/*     */   }
/*     */   
/*     */   public Iterator<NamedNode> reverseIterator()
/*     */   {
/*  88 */     ensureIndexed();
/*  89 */     if (this.indexed == FALLBACK_INDEX) {
/*  90 */       return this.node.reverseIterator();
/*     */     }
/*  92 */     return this.indexed.reverseIterator();
/*     */   }
/*     */   
/*     */   public IndexedNode updateChild(ChildKey key, Node child)
/*     */   {
/*  97 */     Node newNode = this.node.updateImmediateChild(key, child);
/*  98 */     if ((this.indexed == FALLBACK_INDEX) && (!this.index.isDefinedOn(child)))
/*     */     {
/* 100 */       return new IndexedNode(newNode, this.index, FALLBACK_INDEX); }
/* 101 */     if ((this.indexed == null) || (this.indexed == FALLBACK_INDEX))
/*     */     {
/* 103 */       return new IndexedNode(newNode, this.index, null);
/*     */     }
/* 105 */     Node oldChild = this.node.getImmediateChild(key);
/* 106 */     ImmutableSortedSet<NamedNode> newIndexed = this.indexed.remove(new NamedNode(key, oldChild));
/* 107 */     if (!child.isEmpty()) {
/* 108 */       newIndexed = newIndexed.insert(new NamedNode(key, child));
/*     */     }
/* 110 */     return new IndexedNode(newNode, this.index, newIndexed);
/*     */   }
/*     */   
/*     */   public IndexedNode updatePriority(Node priority)
/*     */   {
/* 115 */     return new IndexedNode(this.node.updatePriority(priority), this.index, this.indexed);
/*     */   }
/*     */   
/*     */   public NamedNode getFirstChild() {
/* 119 */     if (!(this.node instanceof ChildrenNode)) {
/* 120 */       return null;
/*     */     }
/* 122 */     ensureIndexed();
/* 123 */     if (this.indexed == FALLBACK_INDEX) {
/* 124 */       ChildKey firstKey = ((ChildrenNode)this.node).getFirstChildKey();
/* 125 */       return new NamedNode(firstKey, this.node.getImmediateChild(firstKey));
/*     */     }
/* 127 */     return (NamedNode)this.indexed.getMinEntry();
/*     */   }
/*     */   
/*     */ 
/*     */   public NamedNode getLastChild()
/*     */   {
/* 133 */     if (!(this.node instanceof ChildrenNode)) {
/* 134 */       return null;
/*     */     }
/* 136 */     ensureIndexed();
/* 137 */     if (this.indexed == FALLBACK_INDEX) {
/* 138 */       ChildKey lastKey = ((ChildrenNode)this.node).getLastChildKey();
/* 139 */       return new NamedNode(lastKey, this.node.getImmediateChild(lastKey));
/*     */     }
/* 141 */     return (NamedNode)this.indexed.getMaxEntry();
/*     */   }
/*     */   
/*     */ 
/*     */   public ChildKey getPredecessorChildName(ChildKey childKey, Node childNode, Index index)
/*     */   {
/* 147 */     if ((!this.index.equals(KeyIndex.getInstance())) && (!this.index.equals(index))) {
/* 148 */       throw new IllegalArgumentException("Index not available in IndexedNode!");
/*     */     }
/* 150 */     ensureIndexed();
/* 151 */     if (this.indexed == FALLBACK_INDEX) {
/* 152 */       return this.node.getPredecessorChildKey(childKey);
/*     */     }
/* 154 */     NamedNode node = (NamedNode)this.indexed.getPredecessorEntry(new NamedNode(childKey, childNode));
/* 155 */     return node != null ? node.getName() : null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/IndexedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */