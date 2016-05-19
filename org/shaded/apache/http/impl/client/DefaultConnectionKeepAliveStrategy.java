/*    */ package org.shaded.apache.http.impl.client;
/*    */ 
/*    */ import org.shaded.apache.http.HeaderElement;
/*    */ import org.shaded.apache.http.HeaderElementIterator;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.conn.ConnectionKeepAliveStrategy;
/*    */ import org.shaded.apache.http.message.BasicHeaderElementIterator;
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
/*    */ @Immutable
/*    */ public class DefaultConnectionKeepAliveStrategy
/*    */   implements ConnectionKeepAliveStrategy
/*    */ {
/*    */   public long getKeepAliveDuration(HttpResponse response, HttpContext context)
/*    */   {
/* 52 */     if (response == null) {
/* 53 */       throw new IllegalArgumentException("HTTP response may not be null");
/*    */     }
/* 55 */     HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));
/*    */     
/* 57 */     while (it.hasNext()) {
/* 58 */       HeaderElement he = it.nextElement();
/* 59 */       String param = he.getName();
/* 60 */       String value = he.getValue();
/* 61 */       if ((value != null) && (param.equalsIgnoreCase("timeout"))) {
/*    */         try {
/* 63 */           return Long.parseLong(value) * 1000L;
/*    */         }
/*    */         catch (NumberFormatException ignore) {}
/*    */       }
/*    */     }
/* 68 */     return -1L;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/DefaultConnectionKeepAliveStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */