/*    */ package org.shaded.apache.http.impl;
/*    */ 
/*    */ import org.shaded.apache.http.ConnectionReuseStrategy;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.protocol.HttpContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoConnectionReuseStrategy
/*    */   implements ConnectionReuseStrategy
/*    */ {
/*    */   public boolean keepAlive(HttpResponse response, HttpContext context)
/*    */   {
/* 54 */     if (response == null) {
/* 55 */       throw new IllegalArgumentException("HTTP response may not be null");
/*    */     }
/* 57 */     if (context == null) {
/* 58 */       throw new IllegalArgumentException("HTTP context may not be null");
/*    */     }
/*    */     
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/NoConnectionReuseStrategy.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */