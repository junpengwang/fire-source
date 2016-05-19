/*     */ package com.firebase.client;
/*     */ 
/*     */ import com.firebase.client.core.AuthExpirationBehavior;
/*     */ import com.firebase.client.core.Context;
/*     */ import java.util.List;
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
/*     */ public class Config
/*     */   extends Context
/*     */ {
/*     */   public synchronized void setLogger(Logger logger)
/*     */   {
/*  33 */     assertUnfrozen();
/*  34 */     this.logger = logger;
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
/*     */   public synchronized void setEventTarget(EventTarget eventTarget)
/*     */   {
/*  48 */     assertUnfrozen();
/*  49 */     this.eventTarget = eventTarget;
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
/*     */   public synchronized void setLogLevel(Logger.Level logLevel)
/*     */   {
/*  62 */     assertUnfrozen();
/*  63 */     this.logLevel = logLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setDebugLogComponents(List<String> debugComponents)
/*     */   {
/*  73 */     assertUnfrozen();
/*  74 */     setLogLevel(Logger.Level.DEBUG);
/*  75 */     this.loggedComponents = debugComponents;
/*     */   }
/*     */   
/*     */   void setRunLoop(RunLoop runLoop) {
/*  79 */     this.runLoop = runLoop;
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
/*     */   synchronized void setCredentialStore(CredentialStore store)
/*     */   {
/*  96 */     assertUnfrozen();
/*  97 */     this.credentialStore = store;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setAuthenticationServer(String host)
/*     */   {
/* 106 */     assertUnfrozen();
/* 107 */     this.authenticationServer = host;
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
/*     */   public synchronized void setSessionPersistenceKey(String sessionKey)
/*     */   {
/* 120 */     assertUnfrozen();
/* 121 */     if ((sessionKey == null) || (sessionKey.isEmpty())) {
/* 122 */       throw new IllegalArgumentException("Session identifier is not allowed to be empty or null!");
/*     */     }
/* 124 */     this.persistenceKey = sessionKey;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public synchronized void enablePersistence()
/*     */   {
/* 134 */     assertUnfrozen();
/* 135 */     setPersistenceEnabled(true);
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
/*     */   public synchronized void setPersistenceEnabled(boolean isEnabled)
/*     */   {
/* 155 */     assertUnfrozen();
/* 156 */     this.persistenceEnabled = isEnabled;
/*     */     
/* 158 */     if (isEnabled) {
/* 159 */       setAuthExpirationBehavior(AuthExpirationBehavior.PAUSE_WRITES_UNTIL_REAUTH);
/*     */     } else {
/* 161 */       setAuthExpirationBehavior(AuthExpirationBehavior.DEFAULT);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized void setAuthExpirationBehavior(AuthExpirationBehavior behavior)
/*     */   {
/* 168 */     assertUnfrozen();
/* 169 */     this.authExpirationBehavior = behavior;
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
/*     */   public synchronized void setPersistenceCacheSizeBytes(long cacheSizeInBytes)
/*     */   {
/* 185 */     assertUnfrozen();
/*     */     
/* 187 */     if (cacheSizeInBytes < 1048576L) {
/* 188 */       throw new FirebaseException("The minimum cache size must be at least 1MB");
/*     */     }
/* 190 */     if (cacheSizeInBytes > 104857600L) {
/* 191 */       throw new FirebaseException("Firebase currently doesn't support a cache size larger than 100MB");
/*     */     }
/*     */     
/* 194 */     this.cacheSize = cacheSizeInBytes;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */