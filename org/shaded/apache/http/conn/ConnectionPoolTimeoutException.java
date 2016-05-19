/*    */ package org.shaded.apache.http.conn;
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
/*    */ 
/*    */ @Immutable
/*    */ public class ConnectionPoolTimeoutException
/*    */   extends ConnectTimeoutException
/*    */ {
/*    */   private static final long serialVersionUID = -7898874842020245128L;
/*    */   
/*    */   public ConnectionPoolTimeoutException() {}
/*    */   
/*    */   public ConnectionPoolTimeoutException(String message)
/*    */   {
/* 57 */     super(message);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ConnectionPoolTimeoutException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */