/*     */ package com.firebase.client.core.persistence;
/*     */ 
/*     */ import com.firebase.client.collection.ImmutableSortedMap;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.utilities.ImmutableTree;
/*     */ import com.firebase.client.core.utilities.ImmutableTree.TreeVisitor;
/*     */ import com.firebase.client.core.utilities.Predicate;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PruneForest
/*     */ {
/*     */   private final ImmutableTree<Boolean> pruneForest;
/*  26 */   private static final Predicate<Boolean> KEEP_PREDICATE = new Predicate()
/*     */   {
/*     */     public boolean evaluate(Boolean prune) {
/*  29 */       return !prune.booleanValue();
/*     */     }
/*     */   };
/*     */   
/*  33 */   private static final Predicate<Boolean> PRUNE_PREDICATE = new Predicate()
/*     */   {
/*     */     public boolean evaluate(Boolean prune) {
/*  36 */       return prune.booleanValue();
/*     */     }
/*     */   };
/*     */   
/*  40 */   private static final ImmutableTree<Boolean> PRUNE_TREE = new ImmutableTree(Boolean.valueOf(true));
/*  41 */   private static final ImmutableTree<Boolean> KEEP_TREE = new ImmutableTree(Boolean.valueOf(false));
/*     */   
/*     */   public PruneForest() {
/*  44 */     this.pruneForest = ImmutableTree.emptyInstance();
/*     */   }
/*     */   
/*     */   private PruneForest(ImmutableTree<Boolean> pruneForest) {
/*  48 */     this.pruneForest = pruneForest;
/*     */   }
/*     */   
/*     */   public boolean prunesAnything() {
/*  52 */     return this.pruneForest.containsMatchingValue(PRUNE_PREDICATE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean shouldPruneUnkeptDescendants(Path path)
/*     */   {
/*  63 */     Boolean shouldPrune = (Boolean)this.pruneForest.leafMostValue(path);
/*  64 */     return (shouldPrune != null) && (shouldPrune.booleanValue());
/*     */   }
/*     */   
/*     */   public boolean shouldKeep(Path path) {
/*  68 */     Boolean shouldPrune = (Boolean)this.pruneForest.leafMostValue(path);
/*  69 */     return (shouldPrune != null) && (!shouldPrune.booleanValue());
/*     */   }
/*     */   
/*     */   public boolean affectsPath(Path path) {
/*  73 */     return (this.pruneForest.rootMostValue(path) != null) || (!this.pruneForest.subtree(path).isEmpty());
/*     */   }
/*     */   
/*     */   public PruneForest child(ChildKey key) {
/*  77 */     ImmutableTree<Boolean> childPruneTree = this.pruneForest.getChild(key);
/*  78 */     if (childPruneTree == null) {
/*  79 */       childPruneTree = new ImmutableTree(this.pruneForest.getValue());
/*     */     }
/*  81 */     else if ((childPruneTree.getValue() == null) && (this.pruneForest.getValue() != null)) {
/*  82 */       childPruneTree = childPruneTree.set(Path.getEmptyPath(), this.pruneForest.getValue());
/*     */     }
/*     */     
/*  85 */     return new PruneForest(childPruneTree);
/*     */   }
/*     */   
/*     */   public PruneForest child(Path path) {
/*  89 */     if (path.isEmpty()) {
/*  90 */       return this;
/*     */     }
/*  92 */     return child(path.getFront()).child(path.popFront());
/*     */   }
/*     */   
/*     */   public <T> T foldKeptNodes(T startValue, final ImmutableTree.TreeVisitor<Void, T> treeVisitor)
/*     */   {
/*  97 */     (T)this.pruneForest.fold(startValue, new ImmutableTree.TreeVisitor()
/*     */     {
/*     */       public T onNodeValue(Path relativePath, Boolean prune, T accum) {
/* 100 */         if (!prune.booleanValue()) {
/* 101 */           return (T)treeVisitor.onNodeValue(relativePath, null, accum);
/*     */         }
/* 103 */         return accum;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public PruneForest prune(Path path)
/*     */   {
/* 110 */     if (this.pruneForest.rootMostValueMatching(path, KEEP_PREDICATE) != null) {
/* 111 */       throw new IllegalArgumentException("Can't prune path that was kept previously!");
/*     */     }
/* 113 */     if (this.pruneForest.rootMostValueMatching(path, PRUNE_PREDICATE) != null)
/*     */     {
/* 115 */       return this;
/*     */     }
/* 117 */     ImmutableTree<Boolean> newPruneTree = this.pruneForest.setTree(path, PRUNE_TREE);
/* 118 */     return new PruneForest(newPruneTree);
/*     */   }
/*     */   
/*     */   public PruneForest keep(Path path)
/*     */   {
/* 123 */     if (this.pruneForest.rootMostValueMatching(path, KEEP_PREDICATE) != null)
/*     */     {
/* 125 */       return this;
/*     */     }
/* 127 */     ImmutableTree<Boolean> newPruneTree = this.pruneForest.setTree(path, KEEP_TREE);
/* 128 */     return new PruneForest(newPruneTree);
/*     */   }
/*     */   
/*     */   public PruneForest keepAll(Path path, Set<ChildKey> children)
/*     */   {
/* 133 */     if (this.pruneForest.rootMostValueMatching(path, KEEP_PREDICATE) != null)
/*     */     {
/* 135 */       return this;
/*     */     }
/* 137 */     return doAll(path, children, KEEP_TREE);
/*     */   }
/*     */   
/*     */   public PruneForest pruneAll(Path path, Set<ChildKey> children)
/*     */   {
/* 142 */     if (this.pruneForest.rootMostValueMatching(path, KEEP_PREDICATE) != null) {
/* 143 */       throw new IllegalArgumentException("Can't prune path that was kept previously!");
/*     */     }
/*     */     
/* 146 */     if (this.pruneForest.rootMostValueMatching(path, PRUNE_PREDICATE) != null)
/*     */     {
/* 148 */       return this;
/*     */     }
/* 150 */     return doAll(path, children, PRUNE_TREE);
/*     */   }
/*     */   
/*     */   private PruneForest doAll(Path path, Set<ChildKey> children, ImmutableTree<Boolean> keepOrPruneTree)
/*     */   {
/* 155 */     ImmutableTree<Boolean> subtree = this.pruneForest.subtree(path);
/* 156 */     ImmutableSortedMap<ChildKey, ImmutableTree<Boolean>> childrenMap = subtree.getChildren();
/* 157 */     for (ChildKey key : children) {
/* 158 */       childrenMap = childrenMap.insert(key, keepOrPruneTree);
/*     */     }
/* 160 */     return new PruneForest(this.pruneForest.setTree(path, new ImmutableTree(subtree.getValue(), childrenMap)));
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 165 */     if (this == o) return true;
/* 166 */     if (!(o instanceof PruneForest)) { return false;
/*     */     }
/* 168 */     PruneForest that = (PruneForest)o;
/*     */     
/* 170 */     if (!this.pruneForest.equals(that.pruneForest)) { return false;
/*     */     }
/* 172 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 177 */     return this.pruneForest.hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 182 */     return "{PruneForest:" + this.pruneForest.toString() + "}";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/PruneForest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */