/*    */ package com.firebase.client.collection;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class ImmutableSortedSet<T> implements Iterable<T>
/*    */ {
/*    */   private final ImmutableSortedMap<T, Void> map;
/*    */   
/*    */   private static class WrappedEntryIterator<T> implements Iterator<T> {
/*    */     final Iterator<java.util.Map.Entry<T, Void>> iterator;
/*    */     
/*    */     public WrappedEntryIterator(Iterator<java.util.Map.Entry<T, Void>> iterator) {
/* 13 */       this.iterator = iterator;
/*    */     }
/*    */     
/*    */     public boolean hasNext()
/*    */     {
/* 18 */       return this.iterator.hasNext();
/*    */     }
/*    */     
/*    */     public T next()
/*    */     {
/* 23 */       return (T)((java.util.Map.Entry)this.iterator.next()).getKey();
/*    */     }
/*    */     
/*    */     public void remove()
/*    */     {
/* 28 */       this.iterator.remove();
/*    */     }
/*    */   }
/*    */   
/*    */   public ImmutableSortedSet(java.util.List<T> elems, java.util.Comparator<T> comparator) {
/* 33 */     this.map = ImmutableSortedMap.Builder.buildFrom(elems, java.util.Collections.emptyMap(), ImmutableSortedMap.Builder.identityTranslator(), comparator);
/*    */   }
/*    */   
/*    */   private ImmutableSortedSet(ImmutableSortedMap<T, Void> map) {
/* 37 */     this.map = map;
/*    */   }
/*    */   
/*    */   public boolean contains(T entry) {
/* 41 */     return this.map.containsKey(entry);
/*    */   }
/*    */   
/*    */   public ImmutableSortedSet<T> remove(T entry) {
/* 45 */     ImmutableSortedMap<T, Void> newMap = this.map.remove(entry);
/* 46 */     return newMap == this.map ? this : new ImmutableSortedSet(newMap);
/*    */   }
/*    */   
/*    */   public ImmutableSortedSet<T> insert(T entry) {
/* 50 */     return new ImmutableSortedSet(this.map.insert(entry, null));
/*    */   }
/*    */   
/*    */   public T getMinEntry() {
/* 54 */     return (T)this.map.getMinKey();
/*    */   }
/*    */   
/*    */   public T getMaxEntry() {
/* 58 */     return (T)this.map.getMaxKey();
/*    */   }
/*    */   
/*    */   public int size() {
/* 62 */     return this.map.size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 66 */     return this.map.isEmpty();
/*    */   }
/*    */   
/*    */   public Iterator<T> iterator() {
/* 70 */     return new WrappedEntryIterator(this.map.iterator());
/*    */   }
/*    */   
/*    */   public Iterator<T> iteratorFrom(T entry) {
/* 74 */     return new WrappedEntryIterator(this.map.iteratorFrom(entry));
/*    */   }
/*    */   
/*    */   public Iterator<T> reverseIteratorFrom(T entry) {
/* 78 */     return new WrappedEntryIterator(this.map.reverseIteratorFrom(entry));
/*    */   }
/*    */   
/*    */   public Iterator<T> reverseIterator() {
/* 82 */     return new WrappedEntryIterator(this.map.reverseIterator());
/*    */   }
/*    */   
/*    */   public T getPredecessorEntry(T entry) {
/* 86 */     return (T)this.map.getPredecessorKey(entry);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/ImmutableSortedSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */