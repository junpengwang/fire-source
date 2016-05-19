/*    */ package org.shaded.apache.http.client;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/*    */ public class HttpResponseException
/*    */   extends ClientProtocolException
/*    */ {
/*    */   private static final long serialVersionUID = -7186627969477257933L;
/*    */   private final int statusCode;
/*    */   
/*    */   public HttpResponseException(int statusCode, String s)
/*    */   {
/* 44 */     super(s);
/* 45 */     this.statusCode = statusCode;
/*    */   }
/*    */   
/*    */   public int getStatusCode() {
/* 49 */     return this.statusCode;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/HttpResponseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */