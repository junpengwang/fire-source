/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LinkedNode<T>
/*    */ {
/*    */   final T _value;
/*    */   
/*    */ 
/*    */   final LinkedNode<T> _next;
/*    */   
/*    */ 
/*    */ 
/*    */   public LinkedNode(T paramT, LinkedNode<T> paramLinkedNode)
/*    */   {
/* 17 */     this._value = paramT;
/* 18 */     this._next = paramLinkedNode;
/*    */   }
/*    */   
/* 21 */   public LinkedNode<T> next() { return this._next; }
/*    */   
/* 23 */   public T value() { return (T)this._value; }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static <ST> boolean contains(LinkedNode<ST> paramLinkedNode, ST paramST)
/*    */   {
/* 37 */     while (paramLinkedNode != null) {
/* 38 */       if (paramLinkedNode.value() == paramST) {
/* 39 */         return true;
/*    */       }
/* 41 */       paramLinkedNode = paramLinkedNode.next();
/*    */     }
/* 43 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/LinkedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */