/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.ChildrenNode;
/*     */ import com.firebase.client.snapshot.ChildrenNode.ChildVisitor;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SparseSnapshotTree
/*     */ {
/*     */   private Node value;
/*     */   private Map<ChildKey, SparseSnapshotTree> children;
/*     */   
/*     */   public SparseSnapshotTree()
/*     */   {
/*  22 */     this.value = null;
/*  23 */     this.children = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remember(Path path, Node data)
/*     */   {
/*  35 */     if (path.isEmpty()) {
/*  36 */       this.value = data;
/*  37 */       this.children = null;
/*  38 */     } else if (this.value != null) {
/*  39 */       this.value = this.value.updateChild(path, data);
/*     */     } else {
/*  41 */       if (this.children == null) {
/*  42 */         this.children = new HashMap();
/*     */       }
/*     */       
/*  45 */       ChildKey childKey = path.getFront();
/*  46 */       if (!this.children.containsKey(childKey)) {
/*  47 */         this.children.put(childKey, new SparseSnapshotTree());
/*     */       }
/*     */       
/*  50 */       SparseSnapshotTree child = (SparseSnapshotTree)this.children.get(childKey);
/*  51 */       child.remember(path.popFront(), data);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean forget(final Path path) {
/*  56 */     if (path.isEmpty()) {
/*  57 */       this.value = null;
/*  58 */       this.children = null;
/*  59 */       return true;
/*     */     }
/*  61 */     if (this.value != null) {
/*  62 */       if (this.value.isLeafNode())
/*     */       {
/*  64 */         return false;
/*     */       }
/*  66 */       ChildrenNode childrenNode = (ChildrenNode)this.value;
/*  67 */       this.value = null;
/*     */       
/*  69 */       childrenNode.forEachChild(new ChildrenNode.ChildVisitor()
/*     */       {
/*     */         public void visitChild(ChildKey name, Node child) {
/*  72 */           SparseSnapshotTree.this.remember(path.child(name), child);
/*     */         }
/*     */         
/*     */ 
/*  76 */       });
/*  77 */       return forget(path);
/*     */     }
/*  79 */     if (this.children != null) {
/*  80 */       ChildKey childKey = path.getFront();
/*  81 */       Path childPath = path.popFront();
/*     */       
/*  83 */       if (this.children.containsKey(childKey)) {
/*  84 */         SparseSnapshotTree child = (SparseSnapshotTree)this.children.get(childKey);
/*  85 */         boolean safeToRemove = child.forget(childPath);
/*  86 */         if (safeToRemove) {
/*  87 */           this.children.remove(childKey);
/*     */         }
/*     */       }
/*     */       
/*  91 */       if (this.children.isEmpty()) {
/*  92 */         this.children = null;
/*  93 */         return true;
/*     */       }
/*  95 */       return false;
/*     */     }
/*     */     
/*  98 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void forEachTree(final Path prefixPath, final SparseSnapshotTreeVisitor visitor)
/*     */   {
/* 104 */     if (this.value != null) {
/* 105 */       visitor.visitTree(prefixPath, this.value);
/*     */     } else {
/* 107 */       forEachChild(new SparseSnapshotChildVisitor()
/*     */       {
/*     */         public void visitChild(ChildKey key, SparseSnapshotTree tree) {
/* 110 */           tree.forEachTree(prefixPath.child(key), visitor);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   public void forEachChild(SparseSnapshotChildVisitor visitor) {
/* 117 */     if (this.children != null) {
/* 118 */       for (Map.Entry<ChildKey, SparseSnapshotTree> entry : this.children.entrySet()) {
/* 119 */         visitor.visitChild((ChildKey)entry.getKey(), (SparseSnapshotTree)entry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract interface SparseSnapshotChildVisitor
/*     */   {
/*     */     public abstract void visitChild(ChildKey paramChildKey, SparseSnapshotTree paramSparseSnapshotTree);
/*     */   }
/*     */   
/*     */   public static abstract interface SparseSnapshotTreeVisitor
/*     */   {
/*     */     public abstract void visitTree(Path paramPath, Node paramNode);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/SparseSnapshotTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */