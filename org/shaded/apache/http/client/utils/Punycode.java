/*    */ package org.shaded.apache.http.client.utils;
/*    */ 
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
/*    */ @Immutable
/*    */ public class Punycode
/*    */ {
/*    */   private static final Idn impl;
/*    */   
/*    */   static
/*    */   {
/*    */     Idn _impl;
/*    */     try
/*    */     {
/* 47 */       _impl = new JdkIdn();
/*    */     } catch (Exception e) {
/* 49 */       _impl = new Rfc3492Idn();
/*    */     }
/* 51 */     impl = _impl;
/*    */   }
/*    */   
/*    */   public static String toUnicode(String punycode) {
/* 55 */     return impl.toUnicode(punycode);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/utils/Punycode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */