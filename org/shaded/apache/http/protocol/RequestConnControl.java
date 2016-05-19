/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpRequest;
/*    */ import org.shaded.apache.http.HttpRequestInterceptor;
/*    */ import org.shaded.apache.http.RequestLine;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestConnControl
/*    */   implements HttpRequestInterceptor
/*    */ {
/*    */   public void process(HttpRequest request, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 59 */     if (request == null) {
/* 60 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/*    */     
/* 63 */     String method = request.getRequestLine().getMethod();
/* 64 */     if (method.equalsIgnoreCase("CONNECT")) {
/* 65 */       return;
/*    */     }
/*    */     
/* 68 */     if (!request.containsHeader("Connection"))
/*    */     {
/*    */ 
/* 71 */       request.addHeader("Connection", "Keep-Alive");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/RequestConnControl.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */