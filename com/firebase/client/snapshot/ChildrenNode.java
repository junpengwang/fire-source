/*     */ package com.firebase.client.snapshot;
/*     */ 
/*     */ import com.firebase.client.collection.ImmutableSortedMap;
/*     */ import com.firebase.client.collection.LLRBNode.NodeVisitor;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class ChildrenNode implements Node
/*     */ {
/*  17 */   public static Comparator<ChildKey> NAME_ONLY_COMPARATOR = new Comparator()
/*     */   {
/*     */     public int compare(ChildKey o1, ChildKey o2) {
/*  20 */       return o1.compareTo(o2);
/*     */     }
/*     */   };
/*     */   
/*     */   private final ImmutableSortedMap<ChildKey, Node> children;
/*     */   
/*     */   private final Node priority;
/*  27 */   private String lazyHash = null;
/*     */   
/*     */   private static class NamedNodeIterator implements Iterator<NamedNode>
/*     */   {
/*     */     private final Iterator<Map.Entry<ChildKey, Node>> iterator;
/*     */     
/*     */     public NamedNodeIterator(Iterator<Map.Entry<ChildKey, Node>> iterator) {
/*  34 */       this.iterator = iterator;
/*     */     }
/*     */     
/*     */     public boolean hasNext()
/*     */     {
/*  39 */       return this.iterator.hasNext();
/*     */     }
/*     */     
/*     */     public NamedNode next()
/*     */     {
/*  44 */       Map.Entry<ChildKey, Node> entry = (Map.Entry)this.iterator.next();
/*  45 */       return new NamedNode((ChildKey)entry.getKey(), (Node)entry.getValue());
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/*  50 */       this.iterator.remove();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class ChildVisitor extends LLRBNode.NodeVisitor<ChildKey, Node>
/*     */   {
/*     */     public void visitEntry(ChildKey key, Node value)
/*     */     {
/*  58 */       visitChild(key, value);
/*     */     }
/*     */     
/*     */     public abstract void visitChild(ChildKey paramChildKey, Node paramNode);
/*     */   }
/*     */   
/*     */   protected ChildrenNode() {
/*  65 */     this.children = com.firebase.client.collection.ImmutableSortedMap.Builder.emptyMap(NAME_ONLY_COMPARATOR);
/*  66 */     this.priority = PriorityUtilities.NullPriority();
/*     */   }
/*     */   
/*     */   protected ChildrenNode(ImmutableSortedMap<ChildKey, Node> children, Node priority) {
/*  70 */     if ((children.isEmpty()) && (!priority.isEmpty())) {
/*  71 */       throw new IllegalArgumentException("Can't create empty ChildrenNode with priority!");
/*     */     }
/*  73 */     this.priority = priority;
/*  74 */     this.children = children;
/*     */   }
/*     */   
/*     */   public boolean hasChild(ChildKey name)
/*     */   {
/*  79 */     return !getImmediateChild(name).isEmpty();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  84 */     return this.children.isEmpty();
/*     */   }
/*     */   
/*     */   public int getChildCount()
/*     */   {
/*  89 */     return this.children.size();
/*     */   }
/*     */   
/*     */   public Object getValue()
/*     */   {
/*  94 */     return getValue(false);
/*     */   }
/*     */   
/*     */   public Object getValue(boolean useExportFormat)
/*     */   {
/*  99 */     if (isEmpty()) {
/* 100 */       return null;
/*     */     }
/*     */     
/* 103 */     int numKeys = 0;
/* 104 */     int maxKey = 0;
/* 105 */     boolean allIntegerKeys = true;
/* 106 */     Map<String, Object> result = new java.util.HashMap();
/* 107 */     for (Map.Entry<ChildKey, Node> entry : this.children) {
/* 108 */       String key = ((ChildKey)entry.getKey()).asString();
/* 109 */       result.put(key, ((Node)entry.getValue()).getValue(useExportFormat));
/* 110 */       numKeys++;
/*     */       
/* 112 */       if (allIntegerKeys) {
/* 113 */         if ((key.length() > 1) && (key.charAt(0) == '0')) {
/* 114 */           allIntegerKeys = false;
/*     */         } else {
/* 116 */           Integer keyAsInt = Utilities.tryParseInt(key);
/* 117 */           if ((keyAsInt != null) && (keyAsInt.intValue() >= 0)) {
/* 118 */             if (keyAsInt.intValue() > maxKey) {
/* 119 */               maxKey = keyAsInt.intValue();
/*     */             }
/*     */           } else {
/* 122 */             allIntegerKeys = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 128 */     if ((!useExportFormat) && (allIntegerKeys) && (maxKey < 2 * numKeys))
/*     */     {
/* 130 */       List<Object> arrayResult = new ArrayList(maxKey + 1);
/* 131 */       for (int i = 0; i <= maxKey; i++)
/*     */       {
/*     */ 
/* 134 */         arrayResult.add(result.get("" + i));
/*     */       }
/* 136 */       return arrayResult;
/*     */     }
/* 138 */     if ((useExportFormat) && (!this.priority.isEmpty())) {
/* 139 */       result.put(".priority", this.priority.getValue());
/*     */     }
/* 141 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChildKey getPredecessorChildKey(ChildKey childKey)
/*     */   {
/* 147 */     return (ChildKey)this.children.getPredecessorKey(childKey);
/*     */   }
/*     */   
/*     */   public ChildKey getSuccessorChildKey(ChildKey childKey)
/*     */   {
/* 152 */     return (ChildKey)this.children.getSuccessorKey(childKey);
/*     */   }
/*     */   
/*     */   public String getHashString()
/*     */   {
/* 157 */     StringBuilder toHash = new StringBuilder("priority:" + this.priority.getHashString() + ":");
/* 158 */     List<NamedNode> nodes = new ArrayList();
/* 159 */     boolean sawPriority = false;
/* 160 */     for (NamedNode node : this) {
/* 161 */       nodes.add(node);
/* 162 */       sawPriority = (sawPriority) || (!node.getNode().getPriority().isEmpty());
/*     */     }
/* 164 */     if (sawPriority) {
/* 165 */       Collections.sort(nodes, PriorityIndex.getInstance());
/*     */     }
/* 167 */     for (NamedNode node : nodes) {
/* 168 */       String hashString = node.getNode().getHash();
/* 169 */       if (!hashString.equals("")) {
/* 170 */         toHash.append(":");
/* 171 */         toHash.append(node.getName().asString());
/* 172 */         toHash.append(":");
/* 173 */         toHash.append(hashString);
/*     */       }
/*     */     }
/* 176 */     return toHash.toString();
/*     */   }
/*     */   
/*     */   public String getHash()
/*     */   {
/* 181 */     if (this.lazyHash == null) {
/* 182 */       String hashString = getHashString();
/* 183 */       this.lazyHash = (hashString.isEmpty() ? "" : Utilities.sha1HexDigest(hashString));
/*     */     }
/* 185 */     return this.lazyHash;
/*     */   }
/*     */   
/*     */   public boolean isLeafNode()
/*     */   {
/* 190 */     return false;
/*     */   }
/*     */   
/*     */   public Node getPriority()
/*     */   {
/* 195 */     return this.priority;
/*     */   }
/*     */   
/*     */   public Node updatePriority(Node priority)
/*     */   {
/* 200 */     if (this.children.isEmpty()) {
/* 201 */       return EmptyNode.Empty();
/*     */     }
/* 203 */     return new ChildrenNode(this.children, priority);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Node getImmediateChild(ChildKey name)
/*     */   {
/* 210 */     if ((name.isPriorityChildName()) && (!this.priority.isEmpty()))
/* 211 */       return this.priority;
/* 212 */     if (this.children.containsKey(name)) {
/* 213 */       return (Node)this.children.get(name);
/*     */     }
/* 215 */     return EmptyNode.Empty();
/*     */   }
/*     */   
/*     */ 
/*     */   public Node getChild(Path path)
/*     */   {
/* 221 */     ChildKey front = path.getFront();
/* 222 */     if (front == null) {
/* 223 */       return this;
/*     */     }
/* 225 */     return getImmediateChild(front).getChild(path.popFront());
/*     */   }
/*     */   
/*     */   public void forEachChild(ChildVisitor visitor)
/*     */   {
/* 230 */     this.children.inOrderTraversal(visitor);
/*     */   }
/*     */   
/*     */   public ChildKey getFirstChildKey() {
/* 234 */     return (ChildKey)this.children.getMinKey();
/*     */   }
/*     */   
/*     */   public ChildKey getLastChildKey() {
/* 238 */     return (ChildKey)this.children.getMaxKey();
/*     */   }
/*     */   
/*     */   public Node updateChild(Path path, Node newChildNode)
/*     */   {
/* 243 */     ChildKey front = path.getFront();
/* 244 */     if (front == null)
/* 245 */       return newChildNode;
/* 246 */     if (front.isPriorityChildName()) {
/* 247 */       assert (PriorityUtilities.isValidPriority(newChildNode));
/* 248 */       return updatePriority(newChildNode);
/*     */     }
/* 250 */     Node newImmediateChild = getImmediateChild(front).updateChild(path.popFront(), newChildNode);
/* 251 */     return updateImmediateChild(front, newImmediateChild);
/*     */   }
/*     */   
/*     */ 
/*     */   public Iterator<NamedNode> iterator()
/*     */   {
/* 257 */     return new NamedNodeIterator(this.children.iterator());
/*     */   }
/*     */   
/*     */   public Iterator<NamedNode> reverseIterator() {
/* 261 */     return new NamedNodeIterator(this.children.reverseIterator());
/*     */   }
/*     */   
/*     */   public Node updateImmediateChild(ChildKey key, Node newChildNode)
/*     */   {
/* 266 */     if (key.isPriorityChildName()) {
/* 267 */       return updatePriority(newChildNode);
/*     */     }
/* 269 */     ImmutableSortedMap<ChildKey, Node> newChildren = this.children;
/* 270 */     if (newChildren.containsKey(key)) {
/* 271 */       newChildren = newChildren.remove(key);
/*     */     }
/* 273 */     if (!newChildNode.isEmpty()) {
/* 274 */       newChildren = newChildren.insert(key, newChildNode);
/*     */     }
/* 276 */     if (newChildren.isEmpty())
/*     */     {
/* 278 */       return EmptyNode.Empty();
/*     */     }
/* 280 */     return new ChildrenNode(newChildren, this.priority);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int compareTo(Node o)
/*     */   {
/* 287 */     if (isEmpty()) {
/* 288 */       if (o.isEmpty()) {
/* 289 */         return 0;
/*     */       }
/* 291 */       return -1;
/*     */     }
/* 293 */     if (o.isLeafNode())
/*     */     {
/* 295 */       return 1; }
/* 296 */     if (o.isEmpty())
/* 297 */       return 1;
/* 298 */     if (o == Node.MAX_NODE) {
/* 299 */       return -1;
/*     */     }
/*     */     
/* 302 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object otherObj)
/*     */   {
/* 308 */     if (otherObj == null) {
/* 309 */       return false;
/*     */     }
/* 311 */     if (otherObj == this) {
/* 312 */       return true;
/*     */     }
/* 314 */     if (!(otherObj instanceof ChildrenNode)) {
/* 315 */       return false;
/*     */     }
/* 317 */     ChildrenNode other = (ChildrenNode)otherObj;
/* 318 */     if (!getPriority().equals(other.getPriority()))
/* 319 */       return false;
/* 320 */     if (this.children.size() != other.children.size()) {
/* 321 */       return false;
/*     */     }
/* 323 */     Iterator<Map.Entry<ChildKey, Node>> thisIterator = this.children.iterator();
/* 324 */     Iterator<Map.Entry<ChildKey, Node>> otherIterator = other.children.iterator();
/* 325 */     while ((thisIterator.hasNext()) && (otherIterator.hasNext())) {
/* 326 */       Map.Entry<ChildKey, Node> thisNameNode = (Map.Entry)thisIterator.next();
/* 327 */       Map.Entry<ChildKey, Node> otherNamedNode = (Map.Entry)otherIterator.next();
/* 328 */       if ((!((ChildKey)thisNameNode.getKey()).equals(otherNamedNode.getKey())) || (!((Node)thisNameNode.getValue()).equals(otherNamedNode.getValue())))
/*     */       {
/* 330 */         return false;
/*     */       }
/*     */     }
/* 333 */     if ((thisIterator.hasNext()) || (otherIterator.hasNext())) {
/* 334 */       throw new IllegalStateException("Something went wrong internally.");
/*     */     }
/* 336 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 342 */     int hashCode = 0;
/* 343 */     for (NamedNode entry : this) {
/* 344 */       hashCode = 31 * hashCode + entry.getName().hashCode();
/* 345 */       hashCode = 17 * hashCode + entry.getNode().hashCode();
/*     */     }
/* 347 */     return hashCode;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 352 */     StringBuilder builder = new StringBuilder();
/* 353 */     toString(builder, 0);
/* 354 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private static void addIndentation(StringBuilder builder, int indentation) {
/* 358 */     for (int i = 0; i < indentation; i++) {
/* 359 */       builder.append(" ");
/*     */     }
/*     */   }
/*     */   
/*     */   private void toString(StringBuilder builder, int indentation) {
/* 364 */     if ((this.children.isEmpty()) && (this.priority.isEmpty())) {
/* 365 */       builder.append("{ }");
/*     */     } else {
/* 367 */       builder.append("{\n");
/* 368 */       for (Map.Entry<ChildKey, Node> childEntry : this.children) {
/* 369 */         addIndentation(builder, indentation + 2);
/* 370 */         builder.append(((ChildKey)childEntry.getKey()).asString());
/* 371 */         builder.append("=");
/* 372 */         if ((childEntry.getValue() instanceof ChildrenNode)) {
/* 373 */           ChildrenNode childrenNode = (ChildrenNode)childEntry.getValue();
/* 374 */           childrenNode.toString(builder, indentation + 2);
/*     */         } else {
/* 376 */           builder.append(((Node)childEntry.getValue()).toString());
/*     */         }
/* 378 */         builder.append("\n");
/*     */       }
/* 380 */       if (!this.priority.isEmpty()) {
/* 381 */         addIndentation(builder, indentation + 2);
/* 382 */         builder.append(".priority=");
/* 383 */         builder.append(this.priority.toString());
/* 384 */         builder.append("\n");
/*     */       }
/* 386 */       addIndentation(builder, indentation);
/* 387 */       builder.append("}");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/ChildrenNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */