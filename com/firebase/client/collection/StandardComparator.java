/*    */ package com.firebase.client.collection;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class StandardComparator<A extends Comparable<A>> implements Comparator<A>
/*    */ {
/*  7 */   private static StandardComparator INSTANCE = new StandardComparator();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static <T extends Comparable<T>> StandardComparator<T> getComparator(Class<T> clazz)
/*    */   {
/* 14 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public int compare(A o1, A o2)
/*    */   {
/* 19 */     return o1.compareTo(o2);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/StandardComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */