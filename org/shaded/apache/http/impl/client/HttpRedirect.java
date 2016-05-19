/*    */ package org.shaded.apache.http.impl.client;
/*    */ 
/*    */ import java.net.URI;
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*    */ import org.shaded.apache.http.client.methods.HttpRequestBase;
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
/*    */ @NotThreadSafe
/*    */ class HttpRedirect
/*    */   extends HttpRequestBase
/*    */ {
/*    */   private String method;
/*    */   
/*    */   public HttpRedirect(String method, URI uri)
/*    */   {
/* 50 */     if (method.equalsIgnoreCase("HEAD")) {
/* 51 */       this.method = "HEAD";
/*    */     } else {
/* 53 */       this.method = "GET";
/*    */     }
/* 55 */     setURI(uri);
/*    */   }
/*    */   
/*    */   public String getMethod()
/*    */   {
/* 60 */     return this.method;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/HttpRedirect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */