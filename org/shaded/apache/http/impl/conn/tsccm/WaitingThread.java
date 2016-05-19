/*     */ package org.shaded.apache.http.impl.conn.tsccm;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotThreadSafe
/*     */ public class WaitingThread
/*     */ {
/*     */   private final Condition cond;
/*     */   private final RouteSpecificPool pool;
/*     */   private Thread waiter;
/*     */   private boolean aborted;
/*     */   
/*     */   public WaitingThread(Condition cond, RouteSpecificPool pool)
/*     */   {
/*  74 */     if (cond == null) {
/*  75 */       throw new IllegalArgumentException("Condition must not be null.");
/*     */     }
/*     */     
/*  78 */     this.cond = cond;
/*  79 */     this.pool = pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Condition getCondition()
/*     */   {
/*  90 */     return this.cond;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final RouteSpecificPool getPool()
/*     */   {
/* 102 */     return this.pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Thread getThread()
/*     */   {
/* 113 */     return this.waiter;
/*     */   }
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
/*     */   public boolean await(Date deadline)
/*     */     throws InterruptedException
/*     */   {
/* 141 */     if (this.waiter != null) {
/* 142 */       throw new IllegalStateException("A thread is already waiting on this object.\ncaller: " + Thread.currentThread() + "\nwaiter: " + this.waiter);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 148 */     if (this.aborted) {
/* 149 */       throw new InterruptedException("Operation interrupted");
/*     */     }
/* 151 */     this.waiter = Thread.currentThread();
/*     */     
/* 153 */     boolean success = false;
/*     */     try {
/* 155 */       if (deadline != null) {
/* 156 */         success = this.cond.awaitUntil(deadline);
/*     */       } else {
/* 158 */         this.cond.await();
/* 159 */         success = true;
/*     */       }
/* 161 */       if (this.aborted)
/* 162 */         throw new InterruptedException("Operation interrupted");
/*     */     } finally {
/* 164 */       this.waiter = null;
/*     */     }
/* 166 */     return success;
/*     */   }
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
/*     */   public void wakeup()
/*     */   {
/* 180 */     if (this.waiter == null) {
/* 181 */       throw new IllegalStateException("Nobody waiting on this object.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 187 */     this.cond.signalAll();
/*     */   }
/*     */   
/*     */   public void interrupt() {
/* 191 */     this.aborted = true;
/* 192 */     this.cond.signalAll();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/WaitingThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */