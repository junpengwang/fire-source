/*    */ package org.shaded.apache.http.util;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LangUtils
/*    */ {
/*    */   public static final int HASH_SEED = 17;
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
/*    */ 
/*    */ 
/*    */ 
/*    */   public static final int HASH_OFFSET = 37;
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
/*    */ 
/*    */ 
/*    */ 
/*    */   public static int hashCode(int seed, int hashcode)
/*    */   {
/* 51 */     return seed * 37 + hashcode;
/*    */   }
/*    */   
/*    */   public static int hashCode(int seed, boolean b) {
/* 55 */     return hashCode(seed, b ? 1 : 0);
/*    */   }
/*    */   
/*    */   public static int hashCode(int seed, Object obj) {
/* 59 */     return hashCode(seed, obj != null ? obj.hashCode() : 0);
/*    */   }
/*    */   
/*    */   public static boolean equals(Object obj1, Object obj2) {
/* 63 */     return obj1 == null ? false : obj2 == null ? true : obj1.equals(obj2);
/*    */   }
/*    */   
/*    */   public static boolean equals(Object[] a1, Object[] a2) {
/* 67 */     if (a1 == null) {
/* 68 */       if (a2 == null) {
/* 69 */         return true;
/*    */       }
/* 71 */       return false;
/*    */     }
/*    */     
/* 74 */     if ((a2 != null) && (a1.length == a2.length)) {
/* 75 */       for (int i = 0; i < a1.length; i++) {
/* 76 */         if (!equals(a1[i], a2[i])) {
/* 77 */           return false;
/*    */         }
/*    */       }
/* 80 */       return true;
/*    */     }
/* 82 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/util/LangUtils.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */