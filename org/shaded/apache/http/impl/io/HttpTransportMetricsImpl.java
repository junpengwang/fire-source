/*    */ package org.shaded.apache.http.impl.io;
/*    */ 
/*    */ import org.shaded.apache.http.io.HttpTransportMetrics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpTransportMetricsImpl
/*    */   implements HttpTransportMetrics
/*    */ {
/* 43 */   private long bytesTransferred = 0L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getBytesTransferred()
/*    */   {
/* 50 */     return this.bytesTransferred;
/*    */   }
/*    */   
/*    */   public void setBytesTransferred(long count) {
/* 54 */     this.bytesTransferred = count;
/*    */   }
/*    */   
/*    */   public void incrementBytesTransferred(long count) {
/* 58 */     this.bytesTransferred += count;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 62 */     this.bytesTransferred = 0L;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/HttpTransportMetricsImpl.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */