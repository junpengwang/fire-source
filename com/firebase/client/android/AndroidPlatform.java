/*     */ package com.firebase.client.android;
/*     */ 
/*     */ import android.os.Build.VERSION;
/*     */ import android.os.Handler;
/*     */ import android.util.Log;
/*     */ import com.firebase.client.EventTarget;
/*     */ import com.firebase.client.Firebase;
/*     */ import com.firebase.client.Logger;
/*     */ import com.firebase.client.Logger.Level;
/*     */ import com.firebase.client.RunLoop;
/*     */ import com.firebase.client.core.persistence.CachePolicy;
/*     */ import com.firebase.client.utilities.DefaultRunLoop;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class AndroidPlatform implements com.firebase.client.core.Platform
/*     */ {
/*     */   private final android.content.Context applicationContext;
/*  20 */   private final Set<String> createdPersistenceCaches = new java.util.HashSet();
/*     */   
/*     */   public AndroidPlatform(android.content.Context context) {
/*  23 */     this.applicationContext = context.getApplicationContext();
/*     */   }
/*     */   
/*     */   public EventTarget newEventTarget(com.firebase.client.core.Context context)
/*     */   {
/*  28 */     return new AndroidEventTarget();
/*     */   }
/*     */   
/*     */   public RunLoop newRunLoop(com.firebase.client.core.Context ctx)
/*     */   {
/*  33 */     final LogWrapper logger = ctx.getLogger("RunLoop");
/*  34 */     new DefaultRunLoop()
/*     */     {
/*     */       public void handleException(final Throwable e) {
/*  37 */         final String message = "Uncaught exception in Firebase runloop (" + Firebase.getSdkVersion() + "). Please report to support@firebase.com";
/*     */         
/*     */ 
/*  40 */         logger.error(message, e);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*  45 */         Handler handler = new Handler(AndroidPlatform.this.applicationContext.getMainLooper());
/*  46 */         handler.post(new Runnable()
/*     */         {
/*     */           public void run() {
/*  49 */             throw new RuntimeException(message, e);
/*     */           }
/*     */         });
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Logger newLogger(com.firebase.client.core.Context context, Logger.Level component, List<String> enabledComponents)
/*     */   {
/*  58 */     return new AndroidLogger(component, enabledComponents);
/*     */   }
/*     */   
/*     */   public String getUserAgent(com.firebase.client.core.Context context)
/*     */   {
/*  63 */     return Build.VERSION.SDK_INT + "/Android";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void runBackgroundTask(com.firebase.client.core.Context context, final Runnable r)
/*     */   {
/*  72 */     new Thread()
/*     */     {
/*     */       public void run() {
/*     */         try {
/*  76 */           r.run();
/*     */         } catch (Throwable e) {
/*  78 */           Log.e("Firebase", "An unexpected error occurred. Please contact support@firebase.com. Details: " + e.getMessage());
/*  79 */           throw new RuntimeException(e);
/*     */         }
/*     */       }
/*     */     }.start();
/*     */   }
/*     */   
/*     */   public String getPlatformVersion()
/*     */   {
/*  87 */     return "android-" + Firebase.getSdkVersion();
/*     */   }
/*     */   
/*     */   public com.firebase.client.core.persistence.PersistenceManager createPersistenceManager(com.firebase.client.core.Context firebaseContext, String firebaseId)
/*     */   {
/*  92 */     String sessionId = firebaseContext.getSessionPersistenceKey();
/*  93 */     String cacheId = firebaseId + "_" + sessionId;
/*     */     
/*  95 */     if (this.createdPersistenceCaches.contains(cacheId)) {
/*  96 */       throw new com.firebase.client.FirebaseException("SessionPersistenceKey '" + sessionId + "' has already been used.");
/*     */     }
/*  98 */     this.createdPersistenceCaches.add(cacheId);
/*  99 */     SqlPersistenceStorageEngine engine = new SqlPersistenceStorageEngine(this.applicationContext, firebaseContext, cacheId);
/* 100 */     CachePolicy cachePolicy = new com.firebase.client.core.persistence.LRUCachePolicy(firebaseContext.getPersistenceCacheSizeBytes());
/* 101 */     return new com.firebase.client.core.persistence.DefaultPersistenceManager(firebaseContext, engine, cachePolicy);
/*     */   }
/*     */   
/*     */   public com.firebase.client.CredentialStore newCredentialStore(com.firebase.client.core.Context context)
/*     */   {
/* 106 */     return new AndroidCredentialStore(this.applicationContext);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/android/AndroidPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */