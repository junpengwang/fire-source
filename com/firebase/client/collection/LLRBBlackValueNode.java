/*    */ package com.firebase.client.collection;
/*    */ 
/*    */ public class LLRBBlackValueNode<K, V> extends LLRBValueNode<K, V>
/*    */ {
/*    */   LLRBBlackValueNode(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right) {
/*  6 */     super(key, value, left, right);
/*    */   }
/*    */   
/*    */   protected LLRBNode.Color getColor()
/*    */   {
/* 11 */     return LLRBNode.Color.BLACK;
/*    */   }
/*    */   
/*    */   public boolean isRed()
/*    */   {
/* 16 */     return false;
/*    */   }
/*    */   
/*    */   protected LLRBValueNode<K, V> copy(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right)
/*    */   {
/* 21 */     K newKey = key == null ? getKey() : key;
/* 22 */     V newValue = value == null ? getValue() : value;
/* 23 */     LLRBNode<K, V> newLeft = left == null ? getLeft() : left;
/* 24 */     LLRBNode<K, V> newRight = right == null ? getRight() : right;
/* 25 */     return new LLRBBlackValueNode(newKey, newValue, newLeft, newRight);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/LLRBBlackValueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */