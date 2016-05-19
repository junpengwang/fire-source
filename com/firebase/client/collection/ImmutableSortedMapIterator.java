/*    */ package com.firebase.client.collection;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.EmptyStackException;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.NoSuchElementException;
/*    */ import java.util.Stack;
/*    */ 
/*    */ public class ImmutableSortedMapIterator<K, V> implements Iterator<Map.Entry<K, V>>
/*    */ {
/*    */   private final Stack<LLRBValueNode<K, V>> nodeStack;
/*    */   private final boolean isReverse;
/*    */   
/*    */   ImmutableSortedMapIterator(LLRBNode<K, V> root, K startKey, Comparator<K> comparator, boolean isReverse)
/*    */   {
/* 17 */     this.nodeStack = new Stack();
/* 18 */     this.isReverse = isReverse;
/*    */     
/* 20 */     LLRBNode<K, V> node = root;
/* 21 */     while (!node.isEmpty()) { int cmp;
/*    */       int cmp;
/* 23 */       if (startKey != null) {
/* 24 */         cmp = isReverse ? comparator.compare(startKey, node.getKey()) : comparator.compare(node.getKey(), startKey);
/*    */       } else {
/* 26 */         cmp = 1;
/*    */       }
/* 28 */       if (cmp < 0)
/*    */       {
/* 30 */         if (isReverse) {
/* 31 */           node = node.getLeft();
/*    */         } else
/* 33 */           node = node.getRight();
/*    */       } else {
/* 35 */         if (cmp == 0)
/*    */         {
/* 37 */           this.nodeStack.push((LLRBValueNode)node);
/* 38 */           break;
/*    */         }
/* 40 */         this.nodeStack.push((LLRBValueNode)node);
/* 41 */         if (isReverse) {
/* 42 */           node = node.getRight();
/*    */         } else {
/* 44 */           node = node.getLeft();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean hasNext()
/*    */   {
/* 52 */     return this.nodeStack.size() > 0;
/*    */   }
/*    */   
/*    */   public Map.Entry<K, V> next()
/*    */   {
/*    */     try {
/* 58 */       LLRBValueNode<K, V> node = (LLRBValueNode)this.nodeStack.pop();
/* 59 */       Map.Entry<K, V> entry = new java.util.AbstractMap.SimpleEntry(node.getKey(), node.getValue());
/* 60 */       if (this.isReverse) {
/* 61 */         LLRBNode<K, V> next = node.getLeft();
/* 62 */         while (!next.isEmpty()) {
/* 63 */           this.nodeStack.push((LLRBValueNode)next);
/* 64 */           next = next.getRight();
/*    */         }
/*    */       } else {
/* 67 */         LLRBNode<K, V> next = node.getRight();
/* 68 */         while (!next.isEmpty()) {
/* 69 */           this.nodeStack.push((LLRBValueNode)next);
/* 70 */           next = next.getLeft();
/*    */         }
/*    */       }
/* 73 */       return entry;
/*    */     }
/*    */     catch (EmptyStackException e) {
/* 76 */       throw new NoSuchElementException();
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove()
/*    */   {
/* 82 */     throw new UnsupportedOperationException("remove called on immutable collection");
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/ImmutableSortedMapIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */