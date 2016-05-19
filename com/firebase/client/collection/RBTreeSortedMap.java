/*     */ package com.firebase.client.collection;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class RBTreeSortedMap<K, V> extends ImmutableSortedMap<K, V>
/*     */ {
/*     */   private LLRBNode<K, V> root;
/*     */   private Comparator<K> comparator;
/*     */   
/*     */   RBTreeSortedMap(Comparator<K> comparator)
/*     */   {
/*  16 */     this.root = LLRBEmptyNode.getInstance();
/*  17 */     this.comparator = comparator;
/*     */   }
/*     */   
/*     */   private RBTreeSortedMap(LLRBNode<K, V> root, Comparator<K> comparator) {
/*  21 */     this.root = root;
/*  22 */     this.comparator = comparator;
/*     */   }
/*     */   
/*     */   LLRBNode<K, V> getRoot()
/*     */   {
/*  27 */     return this.root;
/*     */   }
/*     */   
/*     */   private LLRBNode<K, V> getNode(K key) {
/*  31 */     LLRBNode<K, V> node = this.root;
/*  32 */     while (!node.isEmpty()) {
/*  33 */       int cmp = this.comparator.compare(key, node.getKey());
/*  34 */       if (cmp < 0) {
/*  35 */         node = node.getLeft();
/*  36 */       } else { if (cmp == 0) {
/*  37 */           return node;
/*     */         }
/*  39 */         node = node.getRight();
/*     */       }
/*     */     }
/*  42 */     return null;
/*     */   }
/*     */   
/*     */   public boolean containsKey(K key)
/*     */   {
/*  47 */     return getNode(key) != null;
/*     */   }
/*     */   
/*     */   public V get(K key)
/*     */   {
/*  52 */     LLRBNode<K, V> node = getNode(key);
/*  53 */     return (V)(node != null ? node.getValue() : null);
/*     */   }
/*     */   
/*     */   public ImmutableSortedMap<K, V> remove(K key)
/*     */   {
/*  58 */     if (!containsKey(key)) {
/*  59 */       return this;
/*     */     }
/*  61 */     LLRBNode<K, V> newRoot = this.root.remove(key, this.comparator).copy(null, null, LLRBNode.Color.BLACK, null, null);
/*     */     
/*  63 */     return new RBTreeSortedMap(newRoot, this.comparator);
/*     */   }
/*     */   
/*     */ 
/*     */   public ImmutableSortedMap<K, V> insert(K key, V value)
/*     */   {
/*  69 */     LLRBNode<K, V> newRoot = this.root.insert(key, value, this.comparator).copy(null, null, LLRBNode.Color.BLACK, null, null);
/*     */     
/*  71 */     return new RBTreeSortedMap(newRoot, this.comparator);
/*     */   }
/*     */   
/*     */   public K getMinKey()
/*     */   {
/*  76 */     return (K)this.root.getMin().getKey();
/*     */   }
/*     */   
/*     */   public K getMaxKey()
/*     */   {
/*  81 */     return (K)this.root.getMax().getKey();
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/*  86 */     return this.root.count();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  91 */     return this.root.isEmpty();
/*     */   }
/*     */   
/*     */   public void inOrderTraversal(LLRBNode.NodeVisitor<K, V> visitor)
/*     */   {
/*  96 */     this.root.inOrderTraversal(visitor);
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<K, V>> iterator()
/*     */   {
/* 101 */     return new ImmutableSortedMapIterator(this.root, null, this.comparator, false);
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<K, V>> iteratorFrom(K key)
/*     */   {
/* 106 */     return new ImmutableSortedMapIterator(this.root, key, this.comparator, false);
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<K, V>> reverseIteratorFrom(K key)
/*     */   {
/* 111 */     return new ImmutableSortedMapIterator(this.root, key, this.comparator, true);
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<K, V>> reverseIterator()
/*     */   {
/* 116 */     return new ImmutableSortedMapIterator(this.root, null, this.comparator, true);
/*     */   }
/*     */   
/*     */   public K getPredecessorKey(K key)
/*     */   {
/* 121 */     LLRBNode<K, V> node = this.root;
/* 122 */     LLRBNode<K, V> rightParent = null;
/* 123 */     while (!node.isEmpty()) {
/* 124 */       int cmp = this.comparator.compare(key, node.getKey());
/* 125 */       if (cmp == 0) {
/* 126 */         if (!node.getLeft().isEmpty()) {
/* 127 */           node = node.getLeft();
/* 128 */           while (!node.getRight().isEmpty()) {
/* 129 */             node = node.getRight();
/*     */           }
/* 131 */           return (K)node.getKey(); }
/* 132 */         if (rightParent != null) {
/* 133 */           return (K)rightParent.getKey();
/*     */         }
/* 135 */         return null;
/*     */       }
/* 137 */       if (cmp < 0) {
/* 138 */         node = node.getLeft();
/*     */       } else {
/* 140 */         rightParent = node;
/* 141 */         node = node.getRight();
/*     */       }
/*     */     }
/* 144 */     throw new IllegalArgumentException("Couldn't find predecessor key of non-present key: " + key);
/*     */   }
/*     */   
/*     */   public K getSuccessorKey(K key)
/*     */   {
/* 149 */     LLRBNode<K, V> node = this.root;
/* 150 */     LLRBNode<K, V> leftParent = null;
/* 151 */     while (!node.isEmpty()) {
/* 152 */       int cmp = this.comparator.compare(node.getKey(), key);
/* 153 */       if (cmp == 0) {
/* 154 */         if (!node.getRight().isEmpty()) {
/* 155 */           node = node.getRight();
/* 156 */           while (!node.getLeft().isEmpty()) {
/* 157 */             node = node.getLeft();
/*     */           }
/* 159 */           return (K)node.getKey(); }
/* 160 */         if (leftParent != null) {
/* 161 */           return (K)leftParent.getKey();
/*     */         }
/* 163 */         return null;
/*     */       }
/* 165 */       if (cmp < 0) {
/* 166 */         node = node.getRight();
/*     */       } else {
/* 168 */         leftParent = node;
/* 169 */         node = node.getLeft();
/*     */       }
/*     */     }
/* 172 */     throw new IllegalArgumentException("Couldn't find successor key of non-present key: " + key);
/*     */   }
/*     */   
/*     */   public Comparator<K> getComparator()
/*     */   {
/* 177 */     return this.comparator;
/*     */   }
/*     */   
/*     */ 
/*     */   public static <A, B, C> RBTreeSortedMap<A, C> buildFrom(List<A> keys, Map<B, C> values, ImmutableSortedMap.Builder.KeyTranslator<A, B> translator, Comparator<A> comparator)
/*     */   {
/* 183 */     return Builder.buildFrom(keys, values, translator, comparator);
/*     */   }
/*     */   
/*     */   public static <A, B> RBTreeSortedMap<A, B> fromMap(Map<A, B> values, Comparator<A> comparator) {
/* 187 */     return Builder.buildFrom(new java.util.ArrayList(values.keySet()), values, ImmutableSortedMap.Builder.identityTranslator(), comparator);
/*     */   }
/*     */   
/*     */   private static class Builder<A, B, C>
/*     */   {
/*     */     private final List<A> keys;
/*     */     private final Map<B, C> values;
/*     */     private final ImmutableSortedMap.Builder.KeyTranslator<A, B> keyTranslator;
/*     */     private LLRBValueNode<A, C> root;
/*     */     private LLRBValueNode<A, C> leaf;
/*     */     
/*     */     static class Base1_2 implements Iterable<RBTreeSortedMap.Builder.BooleanChunk>
/*     */     {
/*     */       private long value;
/*     */       private final int length;
/*     */       
/*     */       public Base1_2(int size) {
/* 204 */         int toCalc = size + 1;
/* 205 */         this.length = ((int)Math.floor(Math.log(toCalc) / Math.log(2.0D)));
/* 206 */         long mask = Math.pow(2.0D, this.length) - 1L;
/* 207 */         this.value = (toCalc & mask);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       public Iterator<RBTreeSortedMap.Builder.BooleanChunk> iterator()
/*     */       {
/* 216 */         new Iterator()
/*     */         {
/* 218 */           private int current = RBTreeSortedMap.Builder.Base1_2.this.length - 1;
/*     */           
/*     */           public boolean hasNext()
/*     */           {
/* 222 */             return this.current >= 0;
/*     */           }
/*     */           
/*     */           public RBTreeSortedMap.Builder.BooleanChunk next()
/*     */           {
/* 227 */             long result = RBTreeSortedMap.Builder.Base1_2.this.value & 1 << this.current;
/* 228 */             RBTreeSortedMap.Builder.BooleanChunk next = new RBTreeSortedMap.Builder.BooleanChunk();
/* 229 */             next.isOne = (result == 0L);
/* 230 */             next.chunkSize = ((int)Math.pow(2.0D, this.current));
/* 231 */             this.current -= 1;
/* 232 */             return next;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           public void remove() {}
/*     */         };
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Builder(List<A> keys, Map<B, C> values, ImmutableSortedMap.Builder.KeyTranslator<A, B> translator)
/*     */     {
/* 251 */       this.keys = keys;
/* 252 */       this.values = values;
/* 253 */       this.keyTranslator = translator;
/*     */     }
/*     */     
/*     */     private C getValue(A key)
/*     */     {
/* 258 */       return (C)this.values.get(this.keyTranslator.translate(key));
/*     */     }
/*     */     
/*     */     private LLRBNode<A, C> buildBalancedTree(int start, int size) {
/* 262 */       if (size == 0)
/* 263 */         return LLRBEmptyNode.getInstance();
/* 264 */       if (size == 1) {
/* 265 */         A key = this.keys.get(start);
/* 266 */         return new LLRBBlackValueNode(key, getValue(key), null, null);
/*     */       }
/* 268 */       int half = size / 2;
/* 269 */       int middle = start + half;
/* 270 */       LLRBNode<A, C> left = buildBalancedTree(start, half);
/* 271 */       LLRBNode<A, C> right = buildBalancedTree(middle + 1, half);
/* 272 */       A key = this.keys.get(middle);
/* 273 */       return new LLRBBlackValueNode(key, getValue(key), left, right);
/*     */     }
/*     */     
/*     */     private void buildPennant(LLRBNode.Color color, int chunkSize, int start)
/*     */     {
/* 278 */       LLRBNode<A, C> treeRoot = buildBalancedTree(start + 1, chunkSize - 1);
/* 279 */       A key = this.keys.get(start);
/*     */       LLRBValueNode<A, C> node;
/* 281 */       LLRBValueNode<A, C> node; if (color == LLRBNode.Color.RED) {
/* 282 */         node = new LLRBRedValueNode(key, getValue(key), null, treeRoot);
/*     */       } else {
/* 284 */         node = new LLRBBlackValueNode(key, getValue(key), null, treeRoot);
/*     */       }
/* 286 */       if (this.root == null) {
/* 287 */         this.root = node;
/* 288 */         this.leaf = node;
/*     */       } else {
/* 290 */         this.leaf.setLeft(node);
/* 291 */         this.leaf = node;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public static <A, B, C> RBTreeSortedMap<A, C> buildFrom(List<A> keys, Map<B, C> values, ImmutableSortedMap.Builder.KeyTranslator<A, B> translator, Comparator<A> comparator)
/*     */     {
/* 298 */       Builder<A, B, C> builder = new Builder(keys, values, translator);
/* 299 */       java.util.Collections.sort(keys, comparator);
/* 300 */       Iterator<BooleanChunk> iter = new Base1_2(keys.size()).iterator();
/* 301 */       int index = keys.size();
/* 302 */       while (iter.hasNext()) {
/* 303 */         BooleanChunk next = (BooleanChunk)iter.next();
/* 304 */         index -= next.chunkSize;
/* 305 */         if (next.isOne) {
/* 306 */           builder.buildPennant(LLRBNode.Color.BLACK, next.chunkSize, index);
/*     */         } else {
/* 308 */           builder.buildPennant(LLRBNode.Color.BLACK, next.chunkSize, index);
/* 309 */           index -= next.chunkSize;
/* 310 */           builder.buildPennant(LLRBNode.Color.RED, next.chunkSize, index);
/*     */         }
/*     */       }
/* 313 */       return new RBTreeSortedMap(builder.root == null ? LLRBEmptyNode.getInstance() : builder.root, comparator, null);
/*     */     }
/*     */     
/*     */     static class BooleanChunk
/*     */     {
/*     */       public boolean isOne;
/*     */       public int chunkSize;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/RBTreeSortedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */