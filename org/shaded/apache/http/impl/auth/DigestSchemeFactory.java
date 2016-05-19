/*    */ package org.shaded.apache.http.impl.auth;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.auth.AuthScheme;
/*    */ import org.shaded.apache.http.auth.AuthSchemeFactory;
/*    */ import org.shaded.apache.http.params.HttpParams;
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
/*    */ public class DigestSchemeFactory
/*    */   implements AuthSchemeFactory
/*    */ {
/*    */   public AuthScheme newInstance(HttpParams params)
/*    */   {
/* 46 */     return new DigestScheme();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/DigestSchemeFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */