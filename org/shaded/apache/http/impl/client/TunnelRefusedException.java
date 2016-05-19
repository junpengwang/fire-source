/*    */ package org.shaded.apache.http.impl.client;
/*    */ 
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpResponse;
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
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class TunnelRefusedException
/*    */   extends HttpException
/*    */ {
/*    */   private static final long serialVersionUID = -8646722842745617323L;
/*    */   private final HttpResponse response;
/*    */   
/*    */   public TunnelRefusedException(String message, HttpResponse response)
/*    */   {
/* 48 */     super(message);
/* 49 */     this.response = response;
/*    */   }
/*    */   
/*    */   public HttpResponse getResponse() {
/* 53 */     return this.response;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/TunnelRefusedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */