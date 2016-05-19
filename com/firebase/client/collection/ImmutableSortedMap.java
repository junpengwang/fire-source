/*     */ package com.firebase.client.collection;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public abstract class ImmutableSortedMap<K, V> implements Iterable<Map.Entry<K, V>>
/*     */ {
/*     */   public abstract boolean containsKey(K paramK);
/*     */   
/*     */   public abstract V get(K paramK);
/*     */   
/*     */   public abstract ImmutableSortedMap<K, V> remove(K paramK);
/*     */   
/*     */   public abstract ImmutableSortedMap<K, V> insert(K paramK, V paramV);
/*     */   
/*     */   public abstract K getMinKey();
/*     */   
/*     */   public abstract K getMaxKey();
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   public abstract boolean isEmpty();
/*     */   
/*     */   public abstract void inOrderTraversal(LLRBNode.NodeVisitor<K, V> paramNodeVisitor);
/*     */   
/*     */   public abstract Iterator<Map.Entry<K, V>> iterator();
/*     */   
/*     */   public abstract Iterator<Map.Entry<K, V>> iteratorFrom(K paramK);
/*     */   
/*     */   public abstract Iterator<Map.Entry<K, V>> reverseIteratorFrom(K paramK);
/*     */   
/*     */   public abstract Iterator<Map.Entry<K, V>> reverseIterator();
/*     */   
/*     */   public abstract K getPredecessorKey(K paramK);
/*     */   
/*     */   public abstract K getSuccessorKey(K paramK);
/*     */   
/*     */   public abstract Comparator<K> getComparator();
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/*  45 */     if (this == o) return true;
/*  46 */     if (!(o instanceof ImmutableSortedMap)) { return false;
/*     */     }
/*  48 */     ImmutableSortedMap<K, V> that = (ImmutableSortedMap)o;
/*     */     
/*  50 */     if (!getComparator().equals(that.getComparator())) return false;
/*  51 */     if (size() != that.size()) { return false;
/*     */     }
/*  53 */     Iterator<Map.Entry<K, V>> thisIterator = iterator();
/*  54 */     Iterator<Map.Entry<K, V>> thatIterator = that.iterator();
/*  55 */     while (thisIterator.hasNext()) {
/*  56 */       if (!((Map.Entry)thisIterator.next()).equals(thatIterator.next())) { return false;
/*     */       }
/*     */     }
/*  59 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  64 */     int result = getComparator().hashCode();
/*  65 */     for (Map.Entry<K, V> entry : this) {
/*  66 */       result = 31 * result + entry.hashCode();
/*     */     }
/*     */     
/*  69 */     return result;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  73 */     StringBuilder b = new StringBuilder();
/*  74 */     b.append(getClass().getSimpleName());
/*  75 */     b.append("{");
/*  76 */     boolean first = true;
/*  77 */     for (Map.Entry<K, V> entry : this) {
/*  78 */       if (first) {
/*  79 */         first = false;
/*     */       } else
/*  81 */         b.append(", ");
/*  82 */       b.append("(");
/*  83 */       b.append(entry.getKey());
/*  84 */       b.append("=>");
/*  85 */       b.append(entry.getValue());
/*  86 */       b.append(")");
/*     */     }
/*  88 */     b.append("};");
/*  89 */     return b.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static class Builder
/*     */   {
/*     */     static final int ARRAY_TO_RB_TREE_SIZE_THRESHOLD = 25;
/*     */     
/*     */ 
/*     */ 
/*     */     public static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<K> comparator)
/*     */     {
/* 102 */       return new ArraySortedMap(comparator);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 109 */     private static final KeyTranslator IDENTITY_TRANSLATOR = new KeyTranslator()
/*     */     {
/*     */       public Object translate(Object key) {
/* 112 */         return key;
/*     */       }
/*     */     };
/*     */     
/*     */     public static <A> KeyTranslator<A, A> identityTranslator()
/*     */     {
/* 118 */       return IDENTITY_TRANSLATOR;
/*     */     }
/*     */     
/*     */     public static <A, B> ImmutableSortedMap<A, B> fromMap(Map<A, B> values, Comparator<A> comparator) {
/* 122 */       if (values.size() < 25) {
/* 123 */         return ArraySortedMap.fromMap(values, comparator);
/*     */       }
/* 125 */       return RBTreeSortedMap.fromMap(values, comparator);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public static <A, B, C> ImmutableSortedMap<A, C> buildFrom(List<A> keys, Map<B, C> values, KeyTranslator<A, B> translator, Comparator<A> comparator)
/*     */     {
/* 132 */       if (keys.size() < 25) {
/* 133 */         return ArraySortedMap.buildFrom(keys, values, translator, comparator);
/*     */       }
/* 135 */       return RBTreeSortedMap.buildFrom(keys, values, translator, comparator);
/*     */     }
/*     */     
/*     */     public static abstract interface KeyTranslator<C, D>
/*     */     {
/*     */       public abstract D translate(C paramC);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/ImmutableSortedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */