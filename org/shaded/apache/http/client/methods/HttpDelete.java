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
/*    */ 
/*    */ @NotThreadSafe
/*    */ public class HttpDelete
/*    */   extends HttpRequestBase
/*    */ {
/*    */   public static final String METHOD_NAME = "DELETE";
/*    */   
/*    */   public HttpDelete() {}
/*    */   
/*    */   public HttpDelete(URI uri)
/*    */   {
/* 61 */     setURI(uri);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public HttpDelete(String uri)
/*    */   {
/* 69 */     setURI(URI.create(uri));
/*    */   }
/*    */   
/*    */   public String getMethod()
/*    */   {
/* 74 */     return "DELETE";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/methods/HttpDelete.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */