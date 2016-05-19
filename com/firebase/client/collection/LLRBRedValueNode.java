/*    */ package com.firebase.client.collection;
/*    */ 
/*    */ public class LLRBRedValueNode<K, V>
/*    */   extends LLRBValueNode<K, V>
/*    */ {
/*    */   LLRBRedValueNode(K key, V value)
/*    */   {
/*  8 */     super(key, value, LLRBEmptyNode.getInstance(), LLRBEmptyNode.getInstance());
/*    */   }
/*    */   
/*    */   LLRBRedValueNode(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right) {
/* 12 */     super(key, value, left, right);
/*    */   }
/*    */   
/*    */   protected LLRBNode.Color getColor()
/*    */   {
/* 17 */     return LLRBNode.Color.RED;
/*    */   }
/*    */   
/*    */   public boolean isRed()
/*    */   {
/* 22 */     return true;
/*    */   }
/*    */   
/*    */   protected LLRBValueNode<K, V> copy(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right)
/*    */   {
/* 27 */     K newKey = key == null ? getKey() : key;
/* 28 */     V newValue = value == null ? getValue() : value;
/* 29 */     LLRBNode<K, V> newLeft = left == null ? getLeft() : left;
/* 30 */     LLRBNode<K, V> newRight = right == null ? getRight() : right;
/* 31 */     return new LLRBRedValueNode(newKey, newValue, newLeft, newRight);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/LLRBRedValueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */