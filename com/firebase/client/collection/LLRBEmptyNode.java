/*    */ package com.firebase.client.collection;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LLRBEmptyNode<K, V>
/*    */   implements LLRBNode<K, V>
/*    */ {
/* 12 */   private static final LLRBEmptyNode INSTANCE = new LLRBEmptyNode();
/*    */   
/*    */   public static <K, V> LLRBEmptyNode<K, V> getInstance()
/*    */   {
/* 16 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LLRBNode<K, V> copy(K key, V value, LLRBNode.Color color, LLRBNode<K, V> left, LLRBNode<K, V> right)
/*    */   {
/* 25 */     return this;
/*    */   }
/*    */   
/*    */   public LLRBNode<K, V> insert(K key, V value, Comparator<K> comparator)
/*    */   {
/* 30 */     return new LLRBRedValueNode(key, value);
/*    */   }
/*    */   
/*    */   public LLRBNode<K, V> remove(K key, Comparator<K> comparator)
/*    */   {
/* 35 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isEmpty()
/*    */   {
/* 40 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isRed()
/*    */   {
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public K getKey()
/*    */   {
/* 50 */     return null;
/*    */   }
/*    */   
/*    */   public V getValue()
/*    */   {
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   public LLRBNode<K, V> getLeft()
/*    */   {
/* 60 */     return this;
/*    */   }
/*    */   
/*    */   public LLRBNode<K, V> getRight()
/*    */   {
/* 65 */     return this;
/*    */   }
/*    */   
/*    */   public LLRBNode<K, V> getMin()
/*    */   {
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public LLRBNode<K, V> getMax()
/*    */   {
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public int count()
/*    */   {
/* 80 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void inOrderTraversal(LLRBNode.NodeVisitor<K, V> visitor) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean shortCircuitingInOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor<K, V> visitor)
/*    */   {
/* 91 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean shortCircuitingReverseOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor<K, V> visitor)
/*    */   {
/* 97 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/LLRBEmptyNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */