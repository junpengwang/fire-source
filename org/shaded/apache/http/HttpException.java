/*    */ package org.shaded.apache.http;
/*    */ 
/*    */ import org.shaded.apache.http.util.ExceptionUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -5437299376222011036L;
/*    */   
/*    */   public HttpException() {}
/*    */   
/*    */   public HttpException(String message)
/*    */   {
/* 61 */     super(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public HttpException(String message, Throwable cause)
/*    */   {
/* 72 */     super(message);
/* 73 */     ExceptionUtils.initCause(this, cause);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */