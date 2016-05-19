/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmptyIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/* 15 */   private static final EmptyIterator<?> instance = new EmptyIterator();
/*    */   
/*    */ 
/*    */ 
/*    */   public static <T> Iterator<T> instance()
/*    */   {
/* 21 */     return instance;
/*    */   }
/*    */   
/*    */ 
/* 25 */   public boolean hasNext() { return false; }
/*    */   
/* 27 */   public T next() { throw new NoSuchElementException(); }
/*    */   
/*    */   public void remove()
/*    */   {
/* 31 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/EmptyIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */