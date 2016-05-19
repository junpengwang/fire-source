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
/*    */ @Immutable
/*    */ public class BrowserCompatHostnameVerifier
/*    */   extends AbstractVerifier
/*    */ {
/*    */   public final void verify(String host, String[] cns, String[] subjectAlts)
/*    */     throws SSLException
/*    */   {
/* 54 */     verify(host, cns, subjectAlts, false);
/*    */   }
/*    */   
/*    */   public final String toString()
/*    */   {
/* 59 */     return "BROWSER_COMPATIBLE";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ssl/BrowserCompatHostnameVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */