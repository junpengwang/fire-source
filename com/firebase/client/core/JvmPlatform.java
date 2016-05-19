/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.EventTarget;
/*     */ import com.firebase.client.Firebase;
/*     */ import com.firebase.client.Logger.Level;
/*     */ import com.firebase.client.RunLoop;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */  enum JvmPlatform implements Platform
/*     */ {
/*  15 */   INSTANCE;
/*     */   
/*     */   private JvmPlatform() {}
/*     */   
/*  19 */   public com.firebase.client.Logger newLogger(Context ctx, Logger.Level level, List<String> components) { return new com.firebase.client.utilities.DefaultLogger(level, components); }
/*     */   
/*     */ 
/*     */   public EventTarget newEventTarget(Context ctx)
/*     */   {
/*  24 */     int poolSize = 1;
/*  25 */     java.util.concurrent.BlockingQueue<Runnable> queue = new java.util.concurrent.LinkedBlockingQueue();
/*     */     
/*  27 */     final ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 3L, TimeUnit.SECONDS, queue, new ThreadFactory()
/*     */     {
/*     */ 
/*  30 */       ThreadFactory wrappedFactory = java.util.concurrent.Executors.defaultThreadFactory();
/*     */       
/*     */       public Thread newThread(Runnable r)
/*     */       {
/*  34 */         Thread thread = this.wrappedFactory.newThread(r);
/*  35 */         thread.setName("FirebaseEventTarget");
/*  36 */         thread.setDaemon(true);
/*     */         
/*  38 */         return thread;
/*     */       }
/*     */       
/*  41 */     });
/*  42 */     new EventTarget()
/*     */     {
/*     */       public void postEvent(Runnable r) {
/*  45 */         executor.execute(r);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       public void shutdown()
/*     */       {
/*  55 */         executor.setCorePoolSize(0);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       public void restart()
/*     */       {
/*  65 */         executor.setCorePoolSize(1);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public RunLoop newRunLoop(Context context)
/*     */   {
/*  72 */     final LogWrapper logger = context.getLogger("RunLoop");
/*  73 */     new com.firebase.client.utilities.DefaultRunLoop()
/*     */     {
/*     */       public void handleException(Throwable e) {
/*  76 */         logger.error("Uncaught exception in Firebase runloop (" + Firebase.getSdkVersion() + "). Please report to support@firebase.com", e);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUserAgent(Context ctx)
/*     */   {
/*  84 */     String deviceName = System.getProperty("java.vm.name", "Unknown JVM");
/*  85 */     String systemVersion = System.getProperty("java.specification.version", "Unknown");
/*     */     
/*  87 */     return systemVersion + "/" + deviceName;
/*     */   }
/*     */   
/*     */   public String getPlatformVersion()
/*     */   {
/*  92 */     return "jvm-" + Firebase.getSdkVersion();
/*     */   }
/*     */   
/*     */   public com.firebase.client.core.persistence.PersistenceManager createPersistenceManager(Context ctx, String namespace)
/*     */   {
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public com.firebase.client.CredentialStore newCredentialStore(Context ctx)
/*     */   {
/* 102 */     return new com.firebase.client.authentication.NoopCredentialStore(ctx);
/*     */   }
/*     */   
/*     */   public void runBackgroundTask(Context ctx, final Runnable r)
/*     */   {
/* 107 */     new Thread()
/*     */     {
/*     */       public void run() {
/*     */         try {
/* 111 */           r.run();
/*     */         } catch (Throwable e) {
/* 113 */           System.err.println("An unexpected error occurred. Please contact support@firebase.com. Details: " + e.getMessage());
/* 114 */           throw new RuntimeException(e);
/*     */         }
/*     */       }
/*     */     }.start();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/JvmPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */