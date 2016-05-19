/*    */ package org.shaded.apache.http.client.params;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class HttpClientParams
/*    */ {
/*    */   public static boolean isRedirecting(HttpParams params)
/*    */   {
/* 46 */     if (params == null) {
/* 47 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*    */     }
/* 49 */     return params.getBooleanParameter("http.protocol.handle-redirects", true);
/*    */   }
/*    */   
/*    */   public static void setRedirecting(HttpParams params, boolean value)
/*    */   {
/* 54 */     if (params == null) {
/* 55 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*    */     }
/* 57 */     params.setBooleanParameter("http.protocol.handle-redirects", value);
/*    */   }
/*    */   
/*    */   public static boolean isAuthenticating(HttpParams params)
/*    */   {
/* 62 */     if (params == null) {
/* 63 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*    */     }
/* 65 */     return params.getBooleanParameter("http.protocol.handle-authentication", true);
/*    */   }
/*    */   
/*    */   public static void setAuthenticating(HttpParams params, boolean value)
/*    */   {
/* 70 */     if (params == null) {
/* 71 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*    */     }
/* 73 */     params.setBooleanParameter("http.protocol.handle-authentication", value);
/*    */   }
/*    */   
/*    */   public static String getCookiePolicy(HttpParams params)
/*    */   {
/* 78 */     if (params == null) {
/* 79 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*    */     }
/* 81 */     String cookiePolicy = (String)params.getParameter("http.protocol.cookie-policy");
/*    */     
/* 83 */     if (cookiePolicy == null) {
/* 84 */       return "best-match";
/*    */     }
/* 86 */     return cookiePolicy;
/*    */   }
/*    */   
/*    */   public static void setCookiePolicy(HttpParams params, String cookiePolicy) {
/* 90 */     if (params == null) {
/* 91 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*    */     }
/* 93 */     params.setParameter("http.protocol.cookie-policy", cookiePolicy);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/params/HttpClientParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */