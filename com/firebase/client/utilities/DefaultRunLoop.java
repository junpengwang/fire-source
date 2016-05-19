/*    */ package com.firebase.client.utilities;
/*    */ 
/*    */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*    */ 
/*    */ public abstract class DefaultRunLoop implements com.firebase.client.RunLoop
/*    */ {
/*    */   private ScheduledThreadPoolExecutor executor;
/*    */   public abstract void handleException(Throwable paramThrowable);
/*    */   
/*    */   private class FirebaseThreadFactory implements java.util.concurrent.ThreadFactory
/*    */   {
/*    */     java.util.concurrent.ThreadFactory wrappedFactory;
/*    */     
/*    */     FirebaseThreadFactory()
/*    */     {
/* 16 */       this.wrappedFactory = java.util.concurrent.Executors.defaultThreadFactory();
/*    */     }
/*    */     
/*    */     public Thread newThread(Runnable r) {
/* 20 */       Thread thread = this.wrappedFactory.newThread(r);
/* 21 */       thread.setName("FirebaseWorker");
/* 22 */       thread.setDaemon(true);
/* 23 */       thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
/*    */       {
/*    */         public void uncaughtException(Thread t, Throwable e) {
/* 26 */           DefaultRunLoop.this.handleException(e);
/*    */         }
/* 28 */       });
/* 29 */       return thread;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public DefaultRunLoop()
/*    */   {
/* 38 */     int threadsInPool = 1;
/* 39 */     java.util.concurrent.ThreadFactory threadFactory = new FirebaseThreadFactory();
/* 40 */     this.executor = new ScheduledThreadPoolExecutor(threadsInPool, threadFactory);
/*    */     
/* 42 */     this.executor.setKeepAliveTime(3L, java.util.concurrent.TimeUnit.SECONDS);
/*    */   }
/*    */   
/*    */   public void scheduleNow(final Runnable runnable)
/*    */   {
/* 47 */     this.executor.execute(new Runnable()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try {
/* 52 */           runnable.run();
/*    */         } catch (Throwable e) {
/* 54 */           DefaultRunLoop.this.handleException(e);
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   public java.util.concurrent.ScheduledFuture schedule(final Runnable runnable, long milliseconds)
/*    */   {
/* 62 */     this.executor.schedule(new Runnable()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try {
/* 67 */           runnable.run();
/*    */         } catch (Throwable e) {
/* 69 */           DefaultRunLoop.this.handleException(e); } } }, milliseconds, java.util.concurrent.TimeUnit.MILLISECONDS);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void shutdown()
/*    */   {
/* 77 */     this.executor.setCorePoolSize(0);
/*    */   }
/*    */   
/*    */   public void restart()
/*    */   {
/* 82 */     this.executor.setCorePoolSize(1);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/DefaultRunLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */