/*    */ package org.shaded.apache.http.client.protocol;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*    */ import org.shaded.apache.http.auth.AuthSchemeRegistry;
/*    */ import org.shaded.apache.http.client.CookieStore;
/*    */ import org.shaded.apache.http.client.CredentialsProvider;
/*    */ import org.shaded.apache.http.cookie.CookieSpecRegistry;
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
/*    */ @NotThreadSafe
/*    */ public class ClientContextConfigurer
/*    */   implements ClientContext
/*    */ {
/*    */   private final HttpContext context;
/*    */   
/*    */   public ClientContextConfigurer(HttpContext context)
/*    */   {
/* 51 */     if (context == null)
/* 52 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 53 */     this.context = context;
/*    */   }
/*    */   
/*    */   public void setCookieSpecRegistry(CookieSpecRegistry registry) {
/* 57 */     this.context.setAttribute("http.cookiespec-registry", registry);
/*    */   }
/*    */   
/*    */   public void setAuthSchemeRegistry(AuthSchemeRegistry registry) {
/* 61 */     this.context.setAttribute("http.authscheme-registry", registry);
/*    */   }
/*    */   
/*    */   public void setCookieStore(CookieStore store) {
/* 65 */     this.context.setAttribute("http.cookie-store", store);
/*    */   }
/*    */   
/*    */   public void setCredentialsProvider(CredentialsProvider provider) {
/* 69 */     this.context.setAttribute("http.auth.credentials-provider", provider);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public void setAuthSchemePref(List<String> list)
/*    */   {
/* 77 */     this.context.setAttribute("http.auth.scheme-pref", list);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/protocol/ClientContextConfigurer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */