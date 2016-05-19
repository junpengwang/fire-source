/*     */ package com.firebase.client.snapshot;
/*     */ 
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public abstract class LeafNode<T extends LeafNode>
/*     */   implements Node
/*     */ {
/*     */   protected final Node priority;
/*     */   private String lazyHash;
/*     */   
/*     */   protected static enum LeafType
/*     */   {
/*  20 */     DeferredValue,  Boolean,  Number,  String;
/*     */     
/*     */     private LeafType() {}
/*     */   }
/*     */   
/*     */   LeafNode(Node priority)
/*     */   {
/*  27 */     this.priority = priority;
/*     */   }
/*     */   
/*     */   public boolean hasChild(ChildKey childKey)
/*     */   {
/*  32 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isLeafNode()
/*     */   {
/*  37 */     return true;
/*     */   }
/*     */   
/*     */   public Node getPriority()
/*     */   {
/*  42 */     return this.priority;
/*     */   }
/*     */   
/*     */   public Node getChild(Path path)
/*     */   {
/*  47 */     if (path.isEmpty())
/*  48 */       return this;
/*  49 */     if (path.getFront().isPriorityChildName()) {
/*  50 */       return this.priority;
/*     */     }
/*  52 */     return EmptyNode.Empty();
/*     */   }
/*     */   
/*     */ 
/*     */   public Node updateChild(Path path, Node node)
/*     */   {
/*  58 */     ChildKey front = path.getFront();
/*  59 */     if (front == null)
/*  60 */       return node;
/*  61 */     if ((node.isEmpty()) && (!front.isPriorityChildName())) {
/*  62 */       return this;
/*     */     }
/*  64 */     assert ((!path.getFront().isPriorityChildName()) || (path.size() == 1));
/*  65 */     return updateImmediateChild(front, EmptyNode.Empty().updateChild(path.popFront(), node));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   public int getChildCount()
/*     */   {
/*  76 */     return 0;
/*     */   }
/*     */   
/*     */   public ChildKey getPredecessorChildKey(ChildKey childKey)
/*     */   {
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   public ChildKey getSuccessorChildKey(ChildKey childKey)
/*     */   {
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public Node getImmediateChild(ChildKey name)
/*     */   {
/*  91 */     if (name.isPriorityChildName()) {
/*  92 */       return this.priority;
/*     */     }
/*  94 */     return EmptyNode.Empty();
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getValue(boolean useExportFormat)
/*     */   {
/* 100 */     if ((!useExportFormat) || (this.priority.isEmpty())) {
/* 101 */       return getValue();
/*     */     }
/* 103 */     Map<String, Object> result = new HashMap();
/* 104 */     result.put(".value", getValue());
/* 105 */     result.put(".priority", this.priority.getValue());
/* 106 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */   public Node updateImmediateChild(ChildKey name, Node node)
/*     */   {
/* 112 */     if (name.isPriorityChildName())
/* 113 */       return updatePriority(node);
/* 114 */     if (node.isEmpty()) {
/* 115 */       return this;
/*     */     }
/* 117 */     return EmptyNode.Empty().updateImmediateChild(name, node).updatePriority(this.priority);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getHash()
/*     */   {
/* 125 */     if (this.lazyHash == null) {
/* 126 */       this.lazyHash = Utilities.sha1HexDigest(getHashString());
/*     */     }
/* 128 */     return this.lazyHash;
/*     */   }
/*     */   
/*     */   protected String getPriorityHash() {
/* 132 */     if (this.priority.isEmpty()) {
/* 133 */       return "";
/*     */     }
/* 135 */     return "priority:" + this.priority.getHashString() + ":";
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract LeafType getLeafType();
/*     */   
/*     */   public Iterator<NamedNode> iterator()
/*     */   {
/* 143 */     return Collections.emptyList().iterator();
/*     */   }
/*     */   
/*     */   public Iterator<NamedNode> reverseIterator()
/*     */   {
/* 148 */     return Collections.emptyList().iterator();
/*     */   }
/*     */   
/*     */   private static int compareLongDoubleNodes(LongNode longNode, DoubleNode doubleNode) {
/* 152 */     Double longDoubleValue = Double.valueOf(((Long)longNode.getValue()).longValue());
/* 153 */     return longDoubleValue.compareTo((Double)doubleNode.getValue());
/*     */   }
/*     */   
/*     */   public int compareTo(Node other)
/*     */   {
/* 158 */     if (other.isEmpty())
/* 159 */       return 1;
/* 160 */     if ((other instanceof ChildrenNode)) {
/* 161 */       return -1;
/*     */     }
/* 163 */     assert (other.isLeafNode()) : "Node is not leaf node!";
/* 164 */     if (((this instanceof LongNode)) && ((other instanceof DoubleNode)))
/* 165 */       return compareLongDoubleNodes((LongNode)this, (DoubleNode)other);
/* 166 */     if (((this instanceof DoubleNode)) && ((other instanceof LongNode))) {
/* 167 */       return -1 * compareLongDoubleNodes((LongNode)other, (DoubleNode)this);
/*     */     }
/* 169 */     return leafCompare((LeafNode)other);
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract int compareLeafValues(T paramT);
/*     */   
/*     */   protected int leafCompare(LeafNode<?> other)
/*     */   {
/* 177 */     LeafType thisLeafType = getLeafType();
/* 178 */     LeafType otherLeafType = other.getLeafType();
/* 179 */     if (thisLeafType.equals(otherLeafType))
/*     */     {
/* 181 */       int value = compareLeafValues(other);
/* 182 */       return value;
/*     */     }
/* 184 */     return thisLeafType.compareTo(otherLeafType);
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */ 
/*     */   public abstract int hashCode();
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 196 */     String str = getValue(true).toString();
/* 197 */     return str.substring(0, 100) + "...";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/LeafNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */