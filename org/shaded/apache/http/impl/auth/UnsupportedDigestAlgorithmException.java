/*    */ package org.shaded.apache.http.impl.auth;
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
/*    */ 
/*    */ 
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
/*    */ public class UnsupportedDigestAlgorithmException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 319558534317118022L;
/*    */   
/*    */   public UnsupportedDigestAlgorithmException() {}
/*    */   
/*    */   public UnsupportedDigestAlgorithmException(String message)
/*    */   {
/* 56 */     super(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public UnsupportedDigestAlgorithmException(String message, Throwable cause)
/*    */   {
/* 67 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/UnsupportedDigestAlgorithmException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */