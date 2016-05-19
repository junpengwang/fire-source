/*    */ package org.shaded.apache.http.client.utils;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import org.shaded.apache.http.annotation.Immutable;
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
/*    */ @Immutable
/*    */ public class CloneUtils
/*    */ {
/*    */   public static Object clone(Object obj)
/*    */     throws CloneNotSupportedException
/*    */   {
/* 46 */     if (obj == null) {
/* 47 */       return null;
/*    */     }
/* 49 */     if ((obj instanceof Cloneable)) {
/* 50 */       Class<?> clazz = obj.getClass();
/*    */       Method m;
/*    */       try {
/* 53 */         m = clazz.getMethod("clone", (Class[])null);
/*    */       } catch (NoSuchMethodException ex) {
/* 55 */         throw new NoSuchMethodError(ex.getMessage());
/*    */       }
/*    */       try {
/* 58 */         return m.invoke(obj, (Object[])null);
/*    */       } catch (InvocationTargetException ex) {
/* 60 */         Throwable cause = ex.getCause();
/* 61 */         if ((cause instanceof CloneNotSupportedException)) {
/* 62 */           throw ((CloneNotSupportedException)cause);
/*    */         }
/* 64 */         throw new Error("Unexpected exception", cause);
/*    */       }
/*    */       catch (IllegalAccessException ex) {
/* 67 */         throw new IllegalAccessError(ex.getMessage());
/*    */       }
/*    */     }
/* 70 */     throw new CloneNotSupportedException();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/utils/CloneUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */