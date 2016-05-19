/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpRequest;
/*    */ import org.shaded.apache.http.HttpRequestInterceptor;
/*    */ import org.shaded.apache.http.params.HttpProtocolParams;
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
/*    */ 
/*    */ 
/*    */ public class RequestUserAgent
/*    */   implements HttpRequestInterceptor
/*    */ {
/*    */   public void process(HttpRequest request, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 58 */     if (request == null) {
/* 59 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/* 61 */     if (!request.containsHeader("User-Agent")) {
/* 62 */       String useragent = HttpProtocolParams.getUserAgent(request.getParams());
/* 63 */       if (useragent != null) {
/* 64 */         request.addHeader("User-Agent", useragent);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/RequestUserAgent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */