/*    */ package org.shaded.apache.http.impl.auth;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.auth.AuthenticationException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/*    */ public class NTLMEngineException
/*    */   extends AuthenticationException
/*    */ {
/*    */   private static final long serialVersionUID = 6027981323731768824L;
/*    */   
/*    */   public NTLMEngineException() {}
/*    */   
/*    */   public NTLMEngineException(String message)
/*    */   {
/* 54 */     super(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NTLMEngineException(String message, Throwable cause)
/*    */   {
/* 65 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/NTLMEngineException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */