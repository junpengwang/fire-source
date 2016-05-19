/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpRequest;
/*    */ import org.shaded.apache.http.HttpRequestInterceptor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestDate
/*    */   implements HttpRequestInterceptor
/*    */ {
/* 53 */   private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void process(HttpRequest request, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 61 */     if (request == null) {
/* 62 */       throw new IllegalArgumentException("HTTP request may not be null.");
/*    */     }
/*    */     
/* 65 */     if (((request instanceof HttpEntityEnclosingRequest)) && (!request.containsHeader("Date")))
/*    */     {
/* 67 */       String httpdate = DATE_GENERATOR.getCurrentDate();
/* 68 */       request.setHeader("Date", httpdate);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/RequestDate.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */