/*    */ package org.shaded.apache.http.cookie;
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
/*    */ 
/*    */ @Immutable
/*    */ public class MalformedCookieException
/*    */   extends ProtocolException
/*    */ {
/*    */   private static final long serialVersionUID = -6695462944287282185L;
/*    */   
/*    */   public MalformedCookieException() {}
/*    */   
/*    */   public MalformedCookieException(String message)
/*    */   {
/* 59 */     super(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public MalformedCookieException(String message, Throwable cause)
/*    */   {
/* 70 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/MalformedCookieException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */