/*    */ package org.shaded.apache.http.conn;
/*    */ 
/*    */ import java.net.ConnectException;
/*    */ import org.shaded.apache.http.HttpHost;
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
/*    */ @Immutable
/*    */ public class HttpHostConnectException
/*    */   extends ConnectException
/*    */ {
/*    */   private static final long serialVersionUID = -3194482710275220224L;
/*    */   private final HttpHost host;
/*    */   
/*    */   public HttpHostConnectException(HttpHost host, ConnectException cause)
/*    */   {
/* 49 */     super("Connection to " + host + " refused");
/* 50 */     this.host = host;
/* 51 */     initCause(cause);
/*    */   }
/*    */   
/*    */   public HttpHost getHost() {
/* 55 */     return this.host;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/HttpHostConnectException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */