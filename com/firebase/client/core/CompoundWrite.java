/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.core.utilities.ImmutableTree;
/*     */ import com.firebase.client.core.utilities.ImmutableTree.TreeVisitor;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.snapshot.NodeUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ 
/*     */ public class CompoundWrite
/*     */   implements Iterable<Map.Entry<Path, Node>>
/*     */ {
/*  20 */   private static final CompoundWrite EMPTY = new CompoundWrite(new ImmutableTree(null));
/*     */   private final ImmutableTree<Node> writeTree;
/*     */   
/*     */   private CompoundWrite(ImmutableTree<Node> writeTree)
/*     */   {
/*  25 */     this.writeTree = writeTree;
/*     */   }
/*     */   
/*     */   public static CompoundWrite emptyWrite() {
/*  29 */     return EMPTY;
/*     */   }
/*     */   
/*     */   public static CompoundWrite fromValue(Map<String, Object> merge) {
/*  33 */     ImmutableTree<Node> writeTree = ImmutableTree.emptyInstance();
/*  34 */     for (Map.Entry<String, Object> entry : merge.entrySet()) {
/*  35 */       ImmutableTree<Node> tree = new ImmutableTree(NodeUtilities.NodeFromJSON(entry.getValue()));
/*  36 */       writeTree = writeTree.setTree(new Path((String)entry.getKey()), tree);
/*     */     }
/*  38 */     return new CompoundWrite(writeTree);
/*     */   }
/*     */   
/*     */   public static CompoundWrite fromChildMerge(Map<ChildKey, Node> merge) {
/*  42 */     ImmutableTree<Node> writeTree = ImmutableTree.emptyInstance();
/*  43 */     for (Map.Entry<ChildKey, Node> entry : merge.entrySet()) {
/*  44 */       ImmutableTree<Node> tree = new ImmutableTree(entry.getValue());
/*  45 */       writeTree = writeTree.setTree(new Path(new ChildKey[] { (ChildKey)entry.getKey() }), tree);
/*     */     }
/*  47 */     return new CompoundWrite(writeTree);
/*     */   }
/*     */   
/*     */   public static CompoundWrite fromPathMerge(Map<Path, Node> merge) {
/*  51 */     ImmutableTree<Node> writeTree = ImmutableTree.emptyInstance();
/*  52 */     for (Map.Entry<Path, Node> entry : merge.entrySet()) {
/*  53 */       ImmutableTree<Node> tree = new ImmutableTree(entry.getValue());
/*  54 */       writeTree = writeTree.setTree((Path)entry.getKey(), tree);
/*     */     }
/*  56 */     return new CompoundWrite(writeTree);
/*     */   }
/*     */   
/*     */   public CompoundWrite addWrite(Path path, Node node) {
/*  60 */     if (path.isEmpty()) {
/*  61 */       return new CompoundWrite(new ImmutableTree(node));
/*     */     }
/*  63 */     Path rootMostPath = this.writeTree.findRootMostPathWithValue(path);
/*  64 */     if (rootMostPath != null) {
/*  65 */       Path relativePath = Path.getRelative(rootMostPath, path);
/*  66 */       Node value = (Node)this.writeTree.get(rootMostPath);
/*  67 */       ChildKey back = relativePath.getBack();
/*  68 */       if ((back != null) && (back.isPriorityChildName()) && (value.getChild(relativePath.getParent()).isEmpty()))
/*     */       {
/*  70 */         return this;
/*     */       }
/*  72 */       value = value.updateChild(relativePath, node);
/*  73 */       return new CompoundWrite(this.writeTree.set(rootMostPath, value));
/*     */     }
/*     */     
/*  76 */     ImmutableTree<Node> subtree = new ImmutableTree(node);
/*  77 */     ImmutableTree<Node> newWriteTree = this.writeTree.setTree(path, subtree);
/*  78 */     return new CompoundWrite(newWriteTree);
/*     */   }
/*     */   
/*     */ 
/*     */   public CompoundWrite addWrite(ChildKey key, Node node)
/*     */   {
/*  84 */     return addWrite(new Path(new ChildKey[] { key }), node);
/*     */   }
/*     */   
/*     */   public CompoundWrite addWrites(final Path path, CompoundWrite updates) {
/*  88 */     (CompoundWrite)updates.writeTree.fold(this, new ImmutableTree.TreeVisitor()
/*     */     {
/*     */       public CompoundWrite onNodeValue(Path relativePath, Node value, CompoundWrite accum) {
/*  91 */         return accum.addWrite(path.child(relativePath), value);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CompoundWrite removeWrite(Path path)
/*     */   {
/* 103 */     if (path.isEmpty()) {
/* 104 */       return EMPTY;
/*     */     }
/* 106 */     ImmutableTree<Node> newWriteTree = this.writeTree.setTree(path, ImmutableTree.emptyInstance());
/* 107 */     return new CompoundWrite(newWriteTree);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasCompleteWrite(Path path)
/*     */   {
/* 118 */     return getCompleteNode(path) != null;
/*     */   }
/*     */   
/*     */   public Node rootWrite() {
/* 122 */     return (Node)this.writeTree.getValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node getCompleteNode(Path path)
/*     */   {
/* 132 */     Path rootMost = this.writeTree.findRootMostPathWithValue(path);
/* 133 */     if (rootMost != null) {
/* 134 */       return ((Node)this.writeTree.get(rootMost)).getChild(Path.getRelative(rootMost, path));
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<NamedNode> getCompleteChildren()
/*     */   {
/* 145 */     List<NamedNode> children = new ArrayList();
/* 146 */     if (this.writeTree.getValue() != null) {
/* 147 */       for (NamedNode entry : (Node)this.writeTree.getValue()) {
/* 148 */         children.add(new NamedNode(entry.getName(), entry.getNode()));
/*     */       }
/*     */     } else {
/* 151 */       for (Map.Entry<ChildKey, ImmutableTree<Node>> entry : this.writeTree.getChildren()) {
/* 152 */         ImmutableTree<Node> childTree = (ImmutableTree)entry.getValue();
/* 153 */         if (childTree.getValue() != null) {
/* 154 */           children.add(new NamedNode((ChildKey)entry.getKey(), (Node)childTree.getValue()));
/*     */         }
/*     */       }
/*     */     }
/* 158 */     return children;
/*     */   }
/*     */   
/*     */   public CompoundWrite childCompoundWrite(Path path) {
/* 162 */     if (path.isEmpty()) {
/* 163 */       return this;
/*     */     }
/* 165 */     Node shadowingNode = getCompleteNode(path);
/* 166 */     if (shadowingNode != null) {
/* 167 */       return new CompoundWrite(new ImmutableTree(shadowingNode));
/*     */     }
/*     */     
/* 170 */     return new CompoundWrite(this.writeTree.subtree(path));
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<ChildKey, CompoundWrite> childCompoundWrites()
/*     */   {
/* 176 */     Map<ChildKey, CompoundWrite> children = new HashMap();
/* 177 */     for (Map.Entry<ChildKey, ImmutableTree<Node>> entries : this.writeTree.getChildren()) {
/* 178 */       children.put(entries.getKey(), new CompoundWrite((ImmutableTree)entries.getValue()));
/*     */     }
/* 180 */     return children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 188 */     return this.writeTree.isEmpty();
/*     */   }
/*     */   
/*     */   private Node applySubtreeWrite(Path relativePath, ImmutableTree<Node> writeTree, Node node) {
/* 192 */     if (writeTree.getValue() != null)
/*     */     {
/* 194 */       return node.updateChild(relativePath, (Node)writeTree.getValue());
/*     */     }
/* 196 */     Node priorityWrite = null;
/* 197 */     for (Map.Entry<ChildKey, ImmutableTree<Node>> childTreeEntry : writeTree.getChildren()) {
/* 198 */       ImmutableTree<Node> childTree = (ImmutableTree)childTreeEntry.getValue();
/* 199 */       ChildKey childKey = (ChildKey)childTreeEntry.getKey();
/* 200 */       if (childKey.isPriorityChildName())
/*     */       {
/*     */ 
/* 203 */         assert (childTree.getValue() != null) : "Priority writes must always be leaf nodes";
/* 204 */         priorityWrite = (Node)childTree.getValue();
/*     */       } else {
/* 206 */         node = applySubtreeWrite(relativePath.child(childKey), childTree, node);
/*     */       }
/*     */     }
/*     */     
/* 210 */     if ((!node.getChild(relativePath).isEmpty()) && (priorityWrite != null)) {
/* 211 */       node = node.updateChild(relativePath.child(ChildKey.getPriorityKey()), priorityWrite);
/*     */     }
/* 213 */     return node;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node apply(Node node)
/*     */   {
/* 224 */     return applySubtreeWrite(Path.getEmptyPath(), this.writeTree, node);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, Object> getValue(final boolean exportFormat)
/*     */   {
/* 233 */     final Map<String, Object> writes = new HashMap();
/* 234 */     this.writeTree.foreach(new ImmutableTree.TreeVisitor()
/*     */     {
/*     */       public Void onNodeValue(Path relativePath, Node value, Void accum) {
/* 237 */         writes.put(relativePath.wireFormat(), value.getValue(exportFormat));
/* 238 */         return null;
/*     */       }
/* 240 */     });
/* 241 */     return writes;
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<Path, Node>> iterator()
/*     */   {
/* 246 */     return this.writeTree.iterator();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 251 */     if (o == this) return true;
/* 252 */     if ((o == null) || (o.getClass() != getClass())) { return false;
/*     */     }
/* 254 */     return ((CompoundWrite)o).getValue(true).equals(getValue(true));
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 259 */     return getValue(true).hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 264 */     return "CompoundWrite{" + getValue(true).toString() + "}";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/CompoundWrite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */