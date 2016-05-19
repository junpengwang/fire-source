/*    */ package org.shaded.apache.http.conn.ssl;
/*    */ 
/*    */ import javax.net.ssl.SSLException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class StrictHostnameVerifier
/*    */   extends AbstractVerifier
/*    */ {
/*    */   public final void verify(String host, String[] cns, String[] subjectAlts)
/*    */     throws SSLException
/*    */   {
/* 61 */     verify(host, cns, subjectAlts, true);
/*    */   }
/*    */   
/*    */   public final String toString()
/*    */   {
/* 66 */     return "STRICT";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ssl/StrictHostnameVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */