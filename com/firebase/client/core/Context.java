/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.CredentialStore;
/*     */ import com.firebase.client.EventTarget;
/*     */ import com.firebase.client.Firebase;
/*     */ import com.firebase.client.FirebaseException;
/*     */ import com.firebase.client.Logger;
/*     */ import com.firebase.client.Logger.Level;
/*     */ import com.firebase.client.RunLoop;
/*     */ import com.firebase.client.core.persistence.NoopPersistenceManager;
/*     */ import com.firebase.client.core.persistence.PersistenceManager;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Context
/*     */ {
/*     */   private static final long DEFAULT_CACHE_SIZE = 10485760L;
/*     */   protected Logger logger;
/*     */   protected EventTarget eventTarget;
/*     */   protected CredentialStore credentialStore;
/*     */   protected RunLoop runLoop;
/*     */   protected String persistenceKey;
/*     */   protected List<String> loggedComponents;
/*     */   protected String userAgent;
/*     */   protected String authenticationServer;
/*  31 */   protected Logger.Level logLevel = Logger.Level.INFO;
/*     */   protected boolean persistenceEnabled;
/*  33 */   protected AuthExpirationBehavior authExpirationBehavior = AuthExpirationBehavior.DEFAULT;
/*  34 */   protected long cacheSize = 10485760L;
/*     */   private PersistenceManager forcedPersistenceManager;
/*  36 */   private boolean frozen = false;
/*  37 */   private boolean stopped = false;
/*     */   private static Platform platform;
/*     */   private static android.content.Context androidContext;
/*     */   
/*     */   private Platform getPlatform()
/*     */   {
/*  43 */     if (platform == null) {
/*  44 */       if (AndroidSupport.isAndroid()) {
/*  45 */         throw new RuntimeException("You need to set the Android context using Firebase.setAndroidContext() before using Firebase.");
/*     */       }
/*  47 */       platform = JvmPlatform.INSTANCE;
/*     */     }
/*     */     
/*  50 */     return platform;
/*     */   }
/*     */   
/*     */   public static synchronized void setAndroidContext(android.content.Context context) {
/*  54 */     if (androidContext == null) {
/*  55 */       androidContext = context.getApplicationContext();
/*     */       try {
/*  57 */         Class androidPlatformClass = Class.forName("com.firebase.client.android.AndroidPlatform");
/*  58 */         Constructor constructor = androidPlatformClass.getConstructor(new Class[] { android.content.Context.class });
/*  59 */         platform = (Platform)constructor.newInstance(new Object[] { context });
/*     */       }
/*     */       catch (ClassNotFoundException e) {
/*  62 */         throw new RuntimeException("Android classes not found. Are you using the firebase-client-android artifact?");
/*     */       }
/*     */       catch (InvocationTargetException e) {
/*  65 */         throw new RuntimeException("Something went wrong, please report to support@firebase.com", e);
/*     */       } catch (NoSuchMethodException e) {
/*  67 */         throw new RuntimeException("Something went wrong, please report to support@firebase.com", e);
/*     */       } catch (InstantiationException e) {
/*  69 */         throw new RuntimeException("Something went wrong, please report to support@firebase.com", e);
/*     */       } catch (IllegalAccessException e) {
/*  71 */         throw new RuntimeException("Something went wrong, please report to support@firebase.com", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isFrozen() {
/*  77 */     return this.frozen;
/*     */   }
/*     */   
/*     */   public boolean isStopped() {
/*  81 */     return this.stopped;
/*     */   }
/*     */   
/*     */   synchronized void freeze() {
/*  85 */     if (!this.frozen) {
/*  86 */       this.frozen = true;
/*  87 */       initServices();
/*     */     }
/*     */   }
/*     */   
/*     */   public void requireStarted() {
/*  92 */     if (this.stopped) {
/*  93 */       restartServices();
/*  94 */       this.stopped = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void initServices()
/*     */   {
/* 100 */     ensureLogger();
/*     */     
/* 102 */     getPlatform();
/* 103 */     ensureUserAgent();
/*     */     
/* 105 */     ensureEventTarget();
/* 106 */     ensureRunLoop();
/* 107 */     ensureSessionIdentifier();
/* 108 */     ensureCredentialStore();
/*     */   }
/*     */   
/*     */   private void restartServices() {
/* 112 */     this.eventTarget.restart();
/* 113 */     this.runLoop.restart();
/*     */   }
/*     */   
/*     */   void stop() {
/* 117 */     this.stopped = true;
/* 118 */     this.eventTarget.shutdown();
/* 119 */     this.runLoop.shutdown();
/*     */   }
/*     */   
/*     */   protected void assertUnfrozen() {
/* 123 */     if (isFrozen()) {
/* 124 */       throw new FirebaseException("Modifications to Config objects must occur before they are in use");
/*     */     }
/*     */   }
/*     */   
/*     */   public LogWrapper getLogger(String component) {
/* 129 */     return new LogWrapper(this.logger, component);
/*     */   }
/*     */   
/*     */   public LogWrapper getLogger(String component, String prefix) {
/* 133 */     return new LogWrapper(this.logger, component, prefix);
/*     */   }
/*     */   
/*     */   PersistenceManager getPersistenceManager(String firebaseId)
/*     */   {
/* 138 */     if (this.forcedPersistenceManager != null) {
/* 139 */       return this.forcedPersistenceManager;
/*     */     }
/* 141 */     if (this.persistenceEnabled) {
/* 142 */       PersistenceManager cache = platform.createPersistenceManager(this, firebaseId);
/* 143 */       if (cache == null) {
/* 144 */         throw new IllegalArgumentException("You have enabled persistence, but persistence is not supported on this platform. If you have any questions around persistence please contact support@firebase.com.");
/*     */       }
/*     */       
/* 147 */       return cache;
/*     */     }
/* 149 */     return new NoopPersistenceManager();
/*     */   }
/*     */   
/*     */   public boolean isPersistenceEnabled()
/*     */   {
/* 154 */     return this.persistenceEnabled;
/*     */   }
/*     */   
/*     */   public AuthExpirationBehavior getAuthExpirationBehavior()
/*     */   {
/* 159 */     return this.authExpirationBehavior;
/*     */   }
/*     */   
/*     */   public long getPersistenceCacheSizeBytes() {
/* 163 */     return this.cacheSize;
/*     */   }
/*     */   
/*     */   void forcePersistenceManager(PersistenceManager persistenceManager)
/*     */   {
/* 168 */     this.forcedPersistenceManager = persistenceManager;
/*     */   }
/*     */   
/*     */   public EventTarget getEventTarget() {
/* 172 */     return this.eventTarget;
/*     */   }
/*     */   
/*     */   public RunLoop getRunLoop() {
/* 176 */     return this.runLoop;
/*     */   }
/*     */   
/*     */   public void runBackgroundTask(Runnable r) {
/* 180 */     getPlatform().runBackgroundTask(this, r);
/*     */   }
/*     */   
/*     */   public String getUserAgent() {
/* 184 */     return this.userAgent;
/*     */   }
/*     */   
/*     */   public String getPlatformVersion() {
/* 188 */     return getPlatform().getPlatformVersion();
/*     */   }
/*     */   
/*     */   public String getSessionPersistenceKey() {
/* 192 */     return this.persistenceKey;
/*     */   }
/*     */   
/*     */   public CredentialStore getCredentialStore() {
/* 196 */     return this.credentialStore;
/*     */   }
/*     */   
/*     */   public String getAuthenticationServer() {
/* 200 */     if (this.authenticationServer == null) {
/* 201 */       return "https://auth.firebase.com/";
/*     */     }
/* 203 */     return this.authenticationServer;
/*     */   }
/*     */   
/*     */   public boolean isCustomAuthenticationServerSet()
/*     */   {
/* 208 */     return this.authenticationServer != null;
/*     */   }
/*     */   
/*     */   private void ensureLogger() {
/* 212 */     if (this.logger == null) {
/* 213 */       this.logger = getPlatform().newLogger(this, this.logLevel, this.loggedComponents);
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureRunLoop() {
/* 218 */     if (this.runLoop == null) {
/* 219 */       this.runLoop = platform.newRunLoop(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureEventTarget() {
/* 224 */     if (this.eventTarget == null) {
/* 225 */       this.eventTarget = getPlatform().newEventTarget(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureUserAgent() {
/* 230 */     if (this.userAgent == null) {
/* 231 */       this.userAgent = buildUserAgent(getPlatform().getUserAgent(this));
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureCredentialStore() {
/* 236 */     if (this.credentialStore == null) {
/* 237 */       this.credentialStore = getPlatform().newCredentialStore(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureSessionIdentifier() {
/* 242 */     if (this.persistenceKey == null) {
/* 243 */       this.persistenceKey = "default";
/*     */     }
/*     */   }
/*     */   
/*     */   private String buildUserAgent(String platformAgent) {
/* 248 */     StringBuilder sb = new StringBuilder().append("Firebase/").append("5").append("/").append(Firebase.getSdkVersion()).append("/").append(platformAgent);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 255 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/Context.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */