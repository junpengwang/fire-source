/*    */ package org.shaded.apache.http.impl.conn.tsccm;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotThreadSafe
/*    */ public class WaitingThreadAborter
/*    */ {
/*    */   private WaitingThread waitingThread;
/*    */   private boolean aborted;
/*    */   
/*    */   public void abort()
/*    */   {
/* 49 */     this.aborted = true;
/*    */     
/* 51 */     if (this.waitingThread != null) {
/* 52 */       this.waitingThread.interrupt();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setWaitingThread(WaitingThread waitingThread)
/*    */   {
/* 63 */     this.waitingThread = waitingThread;
/* 64 */     if (this.aborted) {
/* 65 */       waitingThread.interrupt();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/WaitingThreadAborter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */