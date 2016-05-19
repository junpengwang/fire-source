/*     */ package com.firebase.client.collection;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class ArraySortedMap<K, V> extends ImmutableSortedMap<K, V>
/*     */ {
/*     */   private final K[] keys;
/*     */   private final V[] values;
/*     */   private final Comparator<K> comparator;
/*     */   
/*     */   public static <A, B, C> ArraySortedMap<A, C> buildFrom(List<A> keys, Map<B, C> values, ImmutableSortedMap.Builder.KeyTranslator<A, B> translator, Comparator<A> comparator)
/*     */   {
/*  17 */     java.util.Collections.sort(keys, comparator);
/*  18 */     int size = keys.size();
/*  19 */     A[] keyArray = (Object[])new Object[size];
/*  20 */     C[] valueArray = (Object[])new Object[size];
/*  21 */     int pos = 0;
/*  22 */     for (A k : keys) {
/*  23 */       keyArray[pos] = k;
/*  24 */       C value = values.get(translator.translate(k));
/*  25 */       valueArray[pos] = value;
/*  26 */       pos++;
/*     */     }
/*  28 */     return new ArraySortedMap(comparator, keyArray, valueArray);
/*     */   }
/*     */   
/*     */   public static <K, V> ArraySortedMap<K, V> fromMap(Map<K, V> map, Comparator<K> comparator) {
/*  32 */     return buildFrom(new java.util.ArrayList(map.keySet()), map, ImmutableSortedMap.Builder.identityTranslator(), comparator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArraySortedMap(Comparator<K> comparator)
/*     */   {
/*  41 */     this.keys = ((Object[])new Object[0]);
/*  42 */     this.values = ((Object[])new Object[0]);
/*  43 */     this.comparator = comparator;
/*     */   }
/*     */   
/*     */   private ArraySortedMap(Comparator<K> comparator, K[] keys, V[] values)
/*     */   {
/*  48 */     this.keys = keys;
/*  49 */     this.values = values;
/*  50 */     this.comparator = comparator;
/*     */   }
/*     */   
/*     */   public boolean containsKey(K key)
/*     */   {
/*  55 */     return findKey(key) != -1;
/*     */   }
/*     */   
/*     */   public V get(K key)
/*     */   {
/*  60 */     int pos = findKey(key);
/*  61 */     return (V)(pos != -1 ? this.values[pos] : null);
/*     */   }
/*     */   
/*     */   public ImmutableSortedMap<K, V> remove(K key)
/*     */   {
/*  66 */     int pos = findKey(key);
/*  67 */     if (pos == -1) {
/*  68 */       return this;
/*     */     }
/*  70 */     K[] keys = removeFromArray(this.keys, pos);
/*  71 */     V[] values = removeFromArray(this.values, pos);
/*  72 */     return new ArraySortedMap(this.comparator, keys, values);
/*     */   }
/*     */   
/*     */ 
/*     */   public ImmutableSortedMap<K, V> insert(K key, V value)
/*     */   {
/*  78 */     int pos = findKey(key);
/*  79 */     if (pos != -1) {
/*  80 */       if ((this.keys[pos] == key) && (this.values[pos] == value)) {
/*  81 */         return this;
/*     */       }
/*     */       
/*  84 */       K[] newKeys = replaceInArray(this.keys, pos, key);
/*  85 */       V[] newValues = replaceInArray(this.values, pos, value);
/*  86 */       return new ArraySortedMap(this.comparator, newKeys, newValues);
/*     */     }
/*     */     
/*  89 */     if (this.keys.length > 25)
/*     */     {
/*  91 */       Map<K, V> map = new java.util.HashMap(this.keys.length + 1);
/*  92 */       for (int i = 0; i < this.keys.length; i++) {
/*  93 */         map.put(this.keys[i], this.values[i]);
/*     */       }
/*  95 */       map.put(key, value);
/*  96 */       return RBTreeSortedMap.fromMap(map, this.comparator);
/*     */     }
/*  98 */     int newPos = findKeyOrInsertPosition(key);
/*  99 */     K[] keys = addToArray(this.keys, newPos, key);
/* 100 */     V[] values = addToArray(this.values, newPos, value);
/* 101 */     return new ArraySortedMap(this.comparator, keys, values);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public K getMinKey()
/*     */   {
/* 108 */     return (K)(this.keys.length > 0 ? this.keys[0] : null);
/*     */   }
/*     */   
/*     */   public K getMaxKey()
/*     */   {
/* 113 */     return (K)(this.keys.length > 0 ? this.keys[(this.keys.length - 1)] : null);
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/* 118 */     return this.keys.length;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 123 */     return this.keys.length == 0;
/*     */   }
/*     */   
/*     */   public void inOrderTraversal(LLRBNode.NodeVisitor<K, V> visitor)
/*     */   {
/* 128 */     for (int i = 0; i < this.keys.length; i++) {
/* 129 */       visitor.visitEntry(this.keys[i], this.values[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private Iterator<Map.Entry<K, V>> iterator(final int pos, final boolean reverse) {
/* 134 */     new Iterator() {
/* 135 */       int currentPos = pos;
/*     */       
/*     */       public boolean hasNext() {
/* 138 */         return this.currentPos >= 0;
/*     */       }
/*     */       
/*     */       public Map.Entry<K, V> next()
/*     */       {
/* 143 */         K key = ArraySortedMap.this.keys[this.currentPos];
/* 144 */         V value = ArraySortedMap.this.values[this.currentPos];
/* 145 */         this.currentPos = (reverse ? this.currentPos - 1 : this.currentPos + 1);
/* 146 */         return new java.util.AbstractMap.SimpleImmutableEntry(key, value);
/*     */       }
/*     */       
/*     */       public void remove()
/*     */       {
/* 151 */         throw new UnsupportedOperationException("Can't remove elements from ImmutableSortedMap");
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<K, V>> iterator()
/*     */   {
/* 158 */     return iterator(0, false);
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<K, V>> iteratorFrom(K key)
/*     */   {
/* 163 */     int pos = findKeyOrInsertPosition(key);
/* 164 */     return iterator(pos, false);
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<K, V>> reverseIteratorFrom(K key)
/*     */   {
/* 169 */     int pos = findKeyOrInsertPosition(key);
/*     */     
/*     */ 
/* 172 */     if ((pos < this.keys.length) && (this.comparator.compare(this.keys[pos], key) == 0)) {
/* 173 */       return iterator(pos, true);
/*     */     }
/* 175 */     return iterator(pos - 1, true);
/*     */   }
/*     */   
/*     */ 
/*     */   public Iterator<Map.Entry<K, V>> reverseIterator()
/*     */   {
/* 181 */     return iterator(this.keys.length - 1, true);
/*     */   }
/*     */   
/*     */   public K getPredecessorKey(K key)
/*     */   {
/* 186 */     int pos = findKey(key);
/* 187 */     if (pos == -1) {
/* 188 */       throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
/*     */     }
/* 190 */     return (K)(pos > 0 ? this.keys[(pos - 1)] : null);
/*     */   }
/*     */   
/*     */ 
/*     */   public K getSuccessorKey(K key)
/*     */   {
/* 196 */     int pos = findKey(key);
/* 197 */     if (pos == -1) {
/* 198 */       throw new IllegalArgumentException("Can't find successor of nonexistent key");
/*     */     }
/* 200 */     return (K)(pos < this.keys.length - 1 ? this.keys[(pos + 1)] : null);
/*     */   }
/*     */   
/*     */ 
/*     */   public Comparator<K> getComparator()
/*     */   {
/* 206 */     return this.comparator;
/*     */   }
/*     */   
/*     */   private static <T> T[] removeFromArray(T[] arr, int pos)
/*     */   {
/* 211 */     int newSize = arr.length - 1;
/* 212 */     T[] newArray = (Object[])new Object[newSize];
/* 213 */     System.arraycopy(arr, 0, newArray, 0, pos);
/* 214 */     System.arraycopy(arr, pos + 1, newArray, pos, newSize - pos);
/* 215 */     return newArray;
/*     */   }
/*     */   
/*     */   private static <T> T[] addToArray(T[] arr, int pos, T value)
/*     */   {
/* 220 */     int newSize = arr.length + 1;
/* 221 */     T[] newArray = (Object[])new Object[newSize];
/* 222 */     System.arraycopy(arr, 0, newArray, 0, pos);
/* 223 */     newArray[pos] = value;
/* 224 */     System.arraycopy(arr, pos, newArray, pos + 1, newSize - pos - 1);
/* 225 */     return newArray;
/*     */   }
/*     */   
/*     */   private static <T> T[] replaceInArray(T[] arr, int pos, T value)
/*     */   {
/* 230 */     int size = arr.length;
/* 231 */     T[] newArray = (Object[])new Object[size];
/* 232 */     System.arraycopy(arr, 0, newArray, 0, size);
/* 233 */     newArray[pos] = value;
/* 234 */     return newArray;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int findKeyOrInsertPosition(K key)
/*     */   {
/* 242 */     int newPos = 0;
/* 243 */     while ((newPos < this.keys.length) && (this.comparator.compare(this.keys[newPos], key) < 0)) {
/* 244 */       newPos++;
/*     */     }
/* 246 */     return newPos;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int findKey(K key)
/*     */   {
/* 254 */     int i = 0;
/* 255 */     for (K otherKey : this.keys) {
/* 256 */       if (this.comparator.compare(key, otherKey) == 0) {
/* 257 */         return i;
/*     */       }
/* 259 */       i++;
/*     */     }
/* 261 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/ArraySortedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */