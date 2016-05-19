/*   */ package com.firebase.client.core.utilities;
/*   */ 
/*   */ 
/*   */ public abstract interface Predicate<T>
/*   */ {
/* 6 */   public static final Predicate<Object> TRUE = new Predicate()
/*   */   {
/*   */     public boolean evaluate(Object object) {
/* 9 */       return true;
/*   */     }
/*   */   };
/*   */   
/*   */   public abstract boolean evaluate(T paramT);
/*   */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/utilities/Predicate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */