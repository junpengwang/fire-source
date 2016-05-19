/*    */ package org.shaded.apache.http.util;
/*    */ 
/*    */ import java.lang.reflect.Method;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ExceptionUtils
/*    */ {
/* 44 */   private static final Method INIT_CAUSE_METHOD = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static Method getInitCauseMethod()
/*    */   {
/*    */     try
/*    */     {
/* 57 */       Class[] paramsClasses = { Throwable.class };
/* 58 */       return Throwable.class.getMethod("initCause", paramsClasses);
/*    */     } catch (NoSuchMethodException e) {}
/* 60 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void initCause(Throwable throwable, Throwable cause)
/*    */   {
/* 71 */     if (INIT_CAUSE_METHOD != null) {
/*    */       try {
/* 73 */         INIT_CAUSE_METHOD.invoke(throwable, new Object[] { cause });
/*    */       }
/*    */       catch (Exception e) {}
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/util/ExceptionUtils.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */