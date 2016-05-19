/*    */ package org.shaded.apache.http.client.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.commons.logging.Log;
/*    */ import org.shaded.apache.commons.logging.LogFactory;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpRequest;
/*    */ import org.shaded.apache.http.HttpRequestInterceptor;
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.auth.AuthScheme;
/*    */ import org.shaded.apache.http.auth.AuthState;
/*    */ import org.shaded.apache.http.auth.AuthenticationException;
/*    */ import org.shaded.apache.http.auth.Credentials;
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
/*    */ @Immutable
/*    */ public class RequestProxyAuthentication
/*    */   implements HttpRequestInterceptor
/*    */ {
/* 55 */   private final Log log = LogFactory.getLog(getClass());
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void process(HttpRequest request, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 63 */     if (request == null) {
/* 64 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/* 66 */     if (context == null) {
/* 67 */       throw new IllegalArgumentException("HTTP context may not be null");
/*    */     }
/*    */     
/* 70 */     if (request.containsHeader("Proxy-Authorization")) {
/* 71 */       return;
/*    */     }
/*    */     
/*    */ 
/* 75 */     AuthState authState = (AuthState)context.getAttribute("http.auth.proxy-scope");
/*    */     
/* 77 */     if (authState == null) {
/* 78 */       return;
/*    */     }
/*    */     
/* 81 */     AuthScheme authScheme = authState.getAuthScheme();
/* 82 */     if (authScheme == null) {
/* 83 */       return;
/*    */     }
/*    */     
/* 86 */     Credentials creds = authState.getCredentials();
/* 87 */     if (creds == null) {
/* 88 */       this.log.debug("User credentials not available");
/* 89 */       return;
/*    */     }
/* 91 */     if ((authState.getAuthScope() != null) || (!authScheme.isConnectionBased())) {
/*    */       try {
/* 93 */         request.addHeader(authScheme.authenticate(creds, request));
/*    */       } catch (AuthenticationException ex) {
/* 95 */         if (this.log.isErrorEnabled()) {
/* 96 */           this.log.error("Proxy authentication error: " + ex.getMessage());
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/protocol/RequestProxyAuthentication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */