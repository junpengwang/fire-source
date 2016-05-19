/*    */ package org.shaded.apache.http.impl.client;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.shaded.apache.http.Header;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.StatusLine;
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*    */ import org.shaded.apache.http.protocol.HttpContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class DefaultProxyAuthenticationHandler
/*    */   extends AbstractAuthenticationHandler
/*    */ {
/*    */   public boolean isAuthenticationRequested(HttpResponse response, HttpContext context)
/*    */   {
/* 58 */     if (response == null) {
/* 59 */       throw new IllegalArgumentException("HTTP response may not be null");
/*    */     }
/* 61 */     int status = response.getStatusLine().getStatusCode();
/* 62 */     return status == 407;
/*    */   }
/*    */   
/*    */   public Map<String, Header> getChallenges(HttpResponse response, HttpContext context)
/*    */     throws MalformedChallengeException
/*    */   {
/* 68 */     if (response == null) {
/* 69 */       throw new IllegalArgumentException("HTTP response may not be null");
/*    */     }
/* 71 */     Header[] headers = response.getHeaders("Proxy-Authenticate");
/* 72 */     return parseChallenges(headers);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/DefaultProxyAuthenticationHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */