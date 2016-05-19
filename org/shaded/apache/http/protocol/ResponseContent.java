/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpEntity;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.HttpResponseInterceptor;
/*    */ import org.shaded.apache.http.HttpVersion;
/*    */ import org.shaded.apache.http.ProtocolException;
/*    */ import org.shaded.apache.http.ProtocolVersion;
/*    */ import org.shaded.apache.http.StatusLine;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResponseContent
/*    */   implements HttpResponseInterceptor
/*    */ {
/*    */   public void process(HttpResponse response, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 66 */     if (response == null) {
/* 67 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/* 69 */     if (response.containsHeader("Transfer-Encoding")) {
/* 70 */       throw new ProtocolException("Transfer-encoding header already present");
/*    */     }
/* 72 */     if (response.containsHeader("Content-Length")) {
/* 73 */       throw new ProtocolException("Content-Length header already present");
/*    */     }
/* 75 */     ProtocolVersion ver = response.getStatusLine().getProtocolVersion();
/* 76 */     HttpEntity entity = response.getEntity();
/* 77 */     if (entity != null) {
/* 78 */       long len = entity.getContentLength();
/* 79 */       if ((entity.isChunked()) && (!ver.lessEquals(HttpVersion.HTTP_1_0))) {
/* 80 */         response.addHeader("Transfer-Encoding", "chunked");
/* 81 */       } else if (len >= 0L) {
/* 82 */         response.addHeader("Content-Length", Long.toString(entity.getContentLength()));
/*    */       }
/*    */       
/* 85 */       if ((entity.getContentType() != null) && (!response.containsHeader("Content-Type")))
/*    */       {
/* 87 */         response.addHeader(entity.getContentType());
/*    */       }
/*    */       
/* 90 */       if ((entity.getContentEncoding() != null) && (!response.containsHeader("Content-Encoding")))
/*    */       {
/* 92 */         response.addHeader(entity.getContentEncoding());
/*    */       }
/*    */     } else {
/* 95 */       int status = response.getStatusLine().getStatusCode();
/* 96 */       if ((status != 204) && (status != 304) && (status != 205))
/*    */       {
/*    */ 
/* 99 */         response.addHeader("Content-Length", "0");
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/ResponseContent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */