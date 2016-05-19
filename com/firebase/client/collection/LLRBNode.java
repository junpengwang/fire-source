/*    */ package com.firebase.client.collection;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public abstract interface LLRBNode<K, V> { public abstract LLRBNode<K, V> copy(K paramK, V paramV, Color paramColor, LLRBNode<K, V> paramLLRBNode1, LLRBNode<K, V> paramLLRBNode2);
/*    */   
/*    */   public abstract LLRBNode<K, V> insert(K paramK, V paramV, Comparator<K> paramComparator);
/*    */   
/*    */   public abstract LLRBNode<K, V> remove(K paramK, Comparator<K> paramComparator);
/*    */   
/*    */   public abstract boolean isEmpty();
/*    */   
/*    */   public abstract boolean isRed();
/*    */   
/*    */   public abstract K getKey();
/*    */   
/*    */   public abstract V getValue();
/*    */   
/*    */   public static abstract interface ShortCircuitingNodeVisitor<K, V> { public abstract boolean shouldContinue(K paramK, V paramV);
/*    */   }
/*    */   
/* 22 */   public static abstract class NodeVisitor<K, V> implements LLRBNode.ShortCircuitingNodeVisitor<K, V> { public boolean shouldContinue(K key, V value) { visitEntry(key, value);
/* 23 */       return true; }
/*    */     
/*    */     public abstract void visitEntry(K paramK, V paramV); }
/*    */   
/*    */   public abstract LLRBNode<K, V> getLeft();
/*    */   
/* 29 */   public static enum Color { RED,  BLACK;
/*    */     
/*    */     private Color() {}
/*    */   }
/*    */   
/*    */   public abstract LLRBNode<K, V> getRight();
/*    */   
/*    */   public abstract LLRBNode<K, V> getMin();
/*    */   
/*    */   public abstract LLRBNode<K, V> getMax();
/*    */   
/*    */   public abstract int count();
/*    */   
/*    */   public abstract void inOrderTraversal(NodeVisitor<K, V> paramNodeVisitor);
/*    */   
/*    */   public abstract boolean shortCircuitingInOrderTraversal(ShortCircuitingNodeVisitor<K, V> paramShortCircuitingNodeVisitor);
/*    */   
/*    */   public abstract boolean shortCircuitingReverseOrderTraversal(ShortCircuitingNodeVisitor<K, V> paramShortCircuitingNodeVisitor);
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/LLRBNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */