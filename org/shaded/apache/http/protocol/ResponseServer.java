/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.HttpResponseInterceptor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResponseServer
/*    */   implements HttpResponseInterceptor
/*    */ {
/*    */   public void process(HttpResponse response, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 58 */     if (response == null) {
/* 59 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/* 61 */     if (!response.containsHeader("Server")) {
/* 62 */       String s = (String)response.getParams().getParameter("http.origin-server");
/*    */       
/* 64 */       if (s != null) {
/* 65 */         response.addHeader("Server", s);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/ResponseServer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */