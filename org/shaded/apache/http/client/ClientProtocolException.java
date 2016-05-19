/*    */ package org.shaded.apache.http.client;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class ClientProtocolException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -5596590843227115865L;
/*    */   
/*    */   public ClientProtocolException() {}
/*    */   
/*    */   public ClientProtocolException(String s)
/*    */   {
/* 48 */     super(s);
/*    */   }
/*    */   
/*    */   public ClientProtocolException(Throwable cause) {
/* 52 */     initCause(cause);
/*    */   }
/*    */   
/*    */   public ClientProtocolException(String message, Throwable cause) {
/* 56 */     super(message);
/* 57 */     initCause(cause);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/ClientProtocolException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */