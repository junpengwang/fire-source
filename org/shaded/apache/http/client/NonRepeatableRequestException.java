/*    */ package org.shaded.apache.http.client;
/*    */ 
/*    */ import org.shaded.apache.http.ProtocolException;
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
/*    */ 
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
/*    */ public class NonRepeatableRequestException
/*    */   extends ProtocolException
/*    */ {
/*    */   private static final long serialVersionUID = 82685265288806048L;
/*    */   
/*    */   public NonRepeatableRequestException() {}
/*    */   
/*    */   public NonRepeatableRequestException(String message)
/*    */   {
/* 58 */     super(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NonRepeatableRequestException(String message, Throwable cause)
/*    */   {
/* 68 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/NonRepeatableRequestException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */