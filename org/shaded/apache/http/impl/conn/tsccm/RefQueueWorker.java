/*     */ package org.shaded.apache.http.impl.conn.tsccm;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class RefQueueWorker
/*     */   implements Runnable
/*     */ {
/*     */   protected final ReferenceQueue<?> refQueue;
/*     */   protected final RefQueueHandler refHandler;
/*     */   protected volatile Thread workerThread;
/*     */   
/*     */   public RefQueueWorker(ReferenceQueue<?> queue, RefQueueHandler handler)
/*     */   {
/*  67 */     if (queue == null) {
/*  68 */       throw new IllegalArgumentException("Queue must not be null.");
/*     */     }
/*  70 */     if (handler == null) {
/*  71 */       throw new IllegalArgumentException("Handler must not be null.");
/*     */     }
/*     */     
/*  74 */     this.refQueue = queue;
/*  75 */     this.refHandler = handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*  87 */     if (this.workerThread == null) {
/*  88 */       this.workerThread = Thread.currentThread();
/*     */     }
/*     */     
/*  91 */     while (this.workerThread == Thread.currentThread()) {
/*     */       try
/*     */       {
/*  94 */         Reference<?> ref = this.refQueue.remove();
/*  95 */         this.refHandler.handleReference(ref);
/*     */       }
/*     */       catch (InterruptedException ignore) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 107 */     Thread wt = this.workerThread;
/* 108 */     if (wt != null) {
/* 109 */       this.workerThread = null;
/* 110 */       wt.interrupt();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 122 */     return "RefQueueWorker::" + this.workerThread;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/RefQueueWorker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */