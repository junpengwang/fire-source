/*     */ package com.firebase.client.core.utilities;
/*     */ 
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class Tree<T>
/*     */ {
/*     */   private ChildKey name;
/*     */   private Tree<T> parent;
/*     */   private TreeNode<T> node;
/*     */   
/*     */   public Tree(ChildKey name, Tree<T> parent, TreeNode<T> node)
/*     */   {
/*  28 */     this.name = name;
/*  29 */     this.parent = parent;
/*  30 */     this.node = node;
/*     */   }
/*     */   
/*     */   public Tree() {
/*  34 */     this(null, null, new TreeNode());
/*     */   }
/*     */   
/*     */   public TreeNode<T> lastNodeOnPath(Path path) {
/*  38 */     TreeNode<T> current = this.node;
/*  39 */     ChildKey next = path.getFront();
/*  40 */     while (next != null) {
/*  41 */       TreeNode<T> childNode = current.children.containsKey(next) ? (TreeNode)current.children.get(next) : null;
/*  42 */       if (childNode == null) {
/*  43 */         return current;
/*     */       }
/*  45 */       current = childNode;
/*  46 */       path = path.popFront();
/*  47 */       next = path.getFront();
/*     */     }
/*  49 */     return current;
/*     */   }
/*     */   
/*     */   public Tree<T> subTree(Path path) {
/*  53 */     Tree<T> child = this;
/*  54 */     ChildKey next = path.getFront();
/*  55 */     while (next != null) {
/*  56 */       TreeNode<T> childNode = child.node.children.containsKey(next) ? (TreeNode)child.node.children.get(next) : new TreeNode();
/*  57 */       child = new Tree(next, child, childNode);
/*  58 */       path = path.popFront();
/*  59 */       next = path.getFront();
/*     */     }
/*  61 */     return child;
/*     */   }
/*     */   
/*     */   public T getValue() {
/*  65 */     return (T)this.node.value;
/*     */   }
/*     */   
/*     */   public void setValue(T value) {
/*  69 */     this.node.value = value;
/*  70 */     updateParents();
/*     */   }
/*     */   
/*     */   public Tree<T> getParent() {
/*  74 */     return this.parent;
/*     */   }
/*     */   
/*     */   public ChildKey getName() {
/*  78 */     return this.name;
/*     */   }
/*     */   
/*     */   public Path getPath() {
/*  82 */     if (this.parent != null) {
/*  83 */       assert (this.name != null);
/*  84 */       return this.parent.getPath().child(this.name);
/*     */     }
/*  86 */     return this.name != null ? new Path(new ChildKey[] { this.name }) : Path.getEmptyPath();
/*     */   }
/*     */   
/*     */   public boolean hasChildren()
/*     */   {
/*  91 */     return !this.node.children.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  95 */     return (this.node.value == null) && (this.node.children.isEmpty());
/*     */   }
/*     */   
/*     */   public void forEachDescendant(TreeVisitor<T> visitor) {
/*  99 */     forEachDescendant(visitor, false, false);
/*     */   }
/*     */   
/*     */   public void forEachDescendant(TreeVisitor<T> visitor, boolean includeSelf) {
/* 103 */     forEachDescendant(visitor, includeSelf, false);
/*     */   }
/*     */   
/*     */   public void forEachDescendant(final TreeVisitor<T> visitor, boolean includeSelf, final boolean childrenFirst) {
/* 107 */     if ((includeSelf) && (!childrenFirst)) {
/* 108 */       visitor.visitTree(this);
/*     */     }
/*     */     
/* 111 */     forEachChild(new TreeVisitor()
/*     */     {
/*     */       public void visitTree(Tree<T> tree) {
/* 114 */         tree.forEachDescendant(visitor, true, childrenFirst);
/*     */       }
/*     */     });
/*     */     
/* 118 */     if ((includeSelf) && (childrenFirst)) {
/* 119 */       visitor.visitTree(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean forEachAncestor(TreeFilter<T> filter) {
/* 124 */     return forEachAncestor(filter, false);
/*     */   }
/*     */   
/*     */   public boolean forEachAncestor(TreeFilter<T> filter, boolean includeSelf) {
/* 128 */     Tree<T> tree = includeSelf ? this : this.parent;
/* 129 */     while (tree != null) {
/* 130 */       if (filter.filterTreeNode(tree)) {
/* 131 */         return true;
/*     */       }
/* 133 */       tree = tree.parent;
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void forEachChild(TreeVisitor<T> visitor)
/*     */   {
/* 141 */     Object[] entries = this.node.children.entrySet().toArray();
/* 142 */     for (int i = 0; i < entries.length; i++) {
/* 143 */       Map.Entry<ChildKey, TreeNode<T>> entry = (Map.Entry)entries[i];
/*     */       
/* 145 */       Tree<T> subTree = new Tree((ChildKey)entry.getKey(), this, (TreeNode)entry.getValue());
/* 146 */       visitor.visitTree(subTree);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateParents() {
/* 151 */     if (this.parent != null) {
/* 152 */       this.parent.updateChild(this.name, this);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateChild(ChildKey name, Tree<T> child) {
/* 157 */     boolean childEmpty = child.isEmpty();
/* 158 */     boolean childExists = this.node.children.containsKey(name);
/* 159 */     if ((childEmpty) && (childExists)) {
/* 160 */       this.node.children.remove(name);
/* 161 */       updateParents();
/* 162 */     } else if ((!childEmpty) && (!childExists)) {
/* 163 */       this.node.children.put(name, child.node);
/* 164 */       updateParents();
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 170 */     return toString("");
/*     */   }
/*     */   
/*     */   String toString(String prefix) {
/* 174 */     String nodeName = this.name == null ? "<anon>" : this.name.asString();
/* 175 */     return prefix + nodeName + "\n" + this.node.toString(new StringBuilder().append(prefix).append("\t").toString());
/*     */   }
/*     */   
/*     */   public static abstract interface TreeFilter<T>
/*     */   {
/*     */     public abstract boolean filterTreeNode(Tree<T> paramTree);
/*     */   }
/*     */   
/*     */   public static abstract interface TreeVisitor<T>
/*     */   {
/*     */     public abstract void visitTree(Tree<T> paramTree);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/utilities/Tree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */