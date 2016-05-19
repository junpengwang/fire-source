/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpEntity;
/*    */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpRequest;
/*    */ import org.shaded.apache.http.HttpRequestInterceptor;
/*    */ import org.shaded.apache.http.HttpVersion;
/*    */ import org.shaded.apache.http.ProtocolVersion;
/*    */ import org.shaded.apache.http.RequestLine;
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
/*    */ public class RequestExpectContinue
/*    */   implements HttpRequestInterceptor
/*    */ {
/*    */   public void process(HttpRequest request, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 63 */     if (request == null) {
/* 64 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/* 66 */     if ((request instanceof HttpEntityEnclosingRequest)) {
/* 67 */       HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
/*    */       
/* 69 */       if ((entity != null) && (entity.getContentLength() != 0L)) {
/* 70 */         ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/* 71 */         if ((HttpProtocolParams.useExpectContinue(request.getParams())) && (!ver.lessEquals(HttpVersion.HTTP_1_0)))
/*    */         {
/* 73 */           request.addHeader("Expect", "100-Continue");
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/RequestExpectContinue.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */