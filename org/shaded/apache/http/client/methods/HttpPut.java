/*    */ package org.shaded.apache.http.client.methods;
/*    */ 
/*    */ import java.net.URI;
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/*    */ public class HttpPut
/*    */   extends HttpEntityEnclosingRequestBase
/*    */ {
/*    */   public static final String METHOD_NAME = "PUT";
/*    */   
/*    */   public HttpPut() {}
/*    */   
/*    */   public HttpPut(URI uri)
/*    */   {
/* 60 */     setURI(uri);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public HttpPut(String uri)
/*    */   {
/* 68 */     setURI(URI.create(uri));
/*    */   }
/*    */   
/*    */   public String getMethod()
/*    */   {
/* 73 */     return "PUT";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/methods/HttpPut.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */