/*    */ package org.shaded.apache.http.client.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import org.shaded.apache.http.Header;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpRequest;
/*    */ import org.shaded.apache.http.HttpRequestInterceptor;
/*    */ import org.shaded.apache.http.RequestLine;
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.params.HttpParams;
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
/*    */ public class RequestDefaultHeaders
/*    */   implements HttpRequestInterceptor
/*    */ {
/*    */   public void process(HttpRequest request, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 56 */     if (request == null) {
/* 57 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/*    */     
/* 60 */     String method = request.getRequestLine().getMethod();
/* 61 */     if (method.equalsIgnoreCase("CONNECT")) {
/* 62 */       return;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 67 */     Collection<Header> defHeaders = (Collection)request.getParams().getParameter("http.default-headers");
/*    */     
/*    */ 
/* 70 */     if (defHeaders != null) {
/* 71 */       for (Header defHeader : defHeaders) {
/* 72 */         request.addHeader(defHeader);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/protocol/RequestDefaultHeaders.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */