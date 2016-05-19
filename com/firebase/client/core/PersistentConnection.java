/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PersistentConnection implements com.firebase.client.realtime.Connection.Delegate { private static final String REQUEST_ERROR = "error";
/*     */   private static final String REQUEST_QUERIES = "q";
/*     */   private static final String REQUEST_TAG = "t";
/*     */   private static final String REQUEST_STATUS = "s";
/*     */   private static final String REQUEST_PATH = "p";
/*     */   private static final String REQUEST_NUMBER = "r";
/*     */   private static final String REQUEST_PAYLOAD = "b";
/*     */   private static final String REQUEST_COUNTERS = "c";
/*     */   private static final String REQUEST_DATA_PAYLOAD = "d";
/*     */   private static final String REQUEST_DATA_HASH = "h";
/*     */   private static final String REQUEST_CREDENTIAL = "cred";
/*     */   private static final String REQUEST_ACTION = "a";
/*     */   private static final String REQUEST_ACTION_STATS = "s";
/*     */   private static final String REQUEST_ACTION_LISTEN = "l";
/*     */   private static final String REQUEST_ACTION_QUERY = "q";
/*     */   private static final String REQUEST_ACTION_PUT = "p";
/*     */   private static final String REQUEST_ACTION_MERGE = "m";
/*     */   private static final String REQUEST_ACTION_UNLISTEN = "u";
/*     */   private static final String REQUEST_ACTION_QUERY_UNLISTEN = "n";
/*     */   private static final String REQUEST_ACTION_ONDISCONNECT_PUT = "o";
/*     */   
/*     */   public static abstract interface Delegate { public abstract void onDataUpdate(String paramString, Object paramObject, boolean paramBoolean, Tag paramTag);
/*     */     
/*     */     public abstract void onConnect();
/*     */     
/*     */     public abstract void onDisconnect();
/*     */     
/*     */     public abstract void onAuthStatus(boolean paramBoolean);
/*     */     
/*     */     public abstract void onServerInfoUpdate(Map<com.firebase.client.snapshot.ChildKey, Object> paramMap);
/*     */   }
/*     */   
/*     */   private static abstract interface ResponseListener { public abstract void onResponse(Map<String, Object> paramMap);
/*     */   }
/*     */   
/*     */   static abstract interface RequestResultListener { public abstract void onRequestResult(com.firebase.client.FirebaseError paramFirebaseError);
/*     */   }
/*     */   
/*  43 */   static class OutstandingListen { private OutstandingListen(PersistentConnection.RequestResultListener listener, com.firebase.client.core.view.QuerySpec query, Tag tag, SyncTree.SyncTreeHash hashFunction) { this.resultListener = listener;
/*  44 */       this.query = query;
/*  45 */       this.hashFunction = hashFunction;
/*  46 */       this.tag = tag; }
/*     */     
/*     */     private final PersistentConnection.RequestResultListener resultListener;
/*     */     
/*  50 */     public com.firebase.client.core.view.QuerySpec getQuery() { return this.query; }
/*     */     
/*     */     private final com.firebase.client.core.view.QuerySpec query;
/*     */     
/*  54 */     public Tag getTag() { return this.tag; }
/*     */     
/*     */     private final SyncTree.SyncTreeHash hashFunction;
/*     */     private final Tag tag;
/*  58 */     public SyncTree.SyncTreeHash getHashFunction() { return this.hashFunction; }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/*  63 */       return this.query.toString() + " (Tag: " + this.tag + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class OutstandingPut
/*     */   {
/*     */     private String action;
/*     */     private Map<String, Object> request;
/*     */     private com.firebase.client.Firebase.CompletionListener onComplete;
/*     */     
/*     */     private OutstandingPut(String action, Map<String, Object> request, com.firebase.client.Firebase.CompletionListener onComplete) {
/*  74 */       this.action = action;
/*  75 */       this.request = request;
/*  76 */       this.onComplete = onComplete;
/*     */     }
/*     */     
/*     */     public String getAction() {
/*  80 */       return this.action;
/*     */     }
/*     */     
/*     */     public Map<String, Object> getRequest() {
/*  84 */       return this.request;
/*     */     }
/*     */     
/*     */     public com.firebase.client.Firebase.CompletionListener getOnComplete() {
/*  88 */       return this.onComplete;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class OutstandingDisconnect
/*     */   {
/*     */     private final String action;
/*     */     private final Path path;
/*     */     private final Object data;
/*     */     private final com.firebase.client.Firebase.CompletionListener onComplete;
/*     */     
/*     */     private OutstandingDisconnect(String action, Path path, Object data, com.firebase.client.Firebase.CompletionListener onComplete) {
/* 100 */       this.action = action;
/* 101 */       this.path = path;
/* 102 */       this.data = data;
/* 103 */       this.onComplete = onComplete;
/*     */     }
/*     */     
/*     */     public String getAction() {
/* 107 */       return this.action;
/*     */     }
/*     */     
/*     */     public Path getPath() {
/* 111 */       return this.path;
/*     */     }
/*     */     
/*     */     public Object getData() {
/* 115 */       return this.data;
/*     */     }
/*     */     
/*     */     public com.firebase.client.Firebase.CompletionListener getOnComplete() {
/* 119 */       return this.onComplete;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class AuthCredential {
/*     */     private java.util.List<com.firebase.client.Firebase.AuthListener> listeners;
/*     */     private String credential;
/* 126 */     private boolean onSuccessCalled = false;
/*     */     private Object authData;
/*     */     
/*     */     AuthCredential(com.firebase.client.Firebase.AuthListener listener, String credential)
/*     */     {
/* 131 */       this.listeners = new java.util.ArrayList();
/* 132 */       this.listeners.add(listener);
/* 133 */       this.credential = credential;
/*     */     }
/*     */     
/*     */     public boolean matches(String credential) {
/* 137 */       return this.credential.equals(credential);
/*     */     }
/*     */     
/*     */     public void preempt() {
/* 141 */       com.firebase.client.FirebaseError error = com.firebase.client.FirebaseError.fromStatus("preempted");
/* 142 */       for (com.firebase.client.Firebase.AuthListener listener : this.listeners) {
/* 143 */         listener.onAuthError(error);
/*     */       }
/*     */     }
/*     */     
/*     */     public void addListener(com.firebase.client.Firebase.AuthListener listener) {
/* 148 */       this.listeners.add(listener);
/*     */     }
/*     */     
/*     */     public void replay(com.firebase.client.Firebase.AuthListener listener)
/*     */     {
/* 153 */       assert (this.authData != null);
/* 154 */       listener.onAuthSuccess(this.authData);
/*     */     }
/*     */     
/*     */     public boolean isComplete() {
/* 158 */       return this.onSuccessCalled;
/*     */     }
/*     */     
/*     */     public String getCredential() {
/* 162 */       return this.credential;
/*     */     }
/*     */     
/*     */     public void onCancel(com.firebase.client.FirebaseError error) {
/* 166 */       if (this.onSuccessCalled) {
/* 167 */         onRevoked(error);
/*     */       } else {
/* 169 */         for (com.firebase.client.Firebase.AuthListener listener : this.listeners) {
/* 170 */           listener.onAuthError(error);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public void onRevoked(com.firebase.client.FirebaseError error) {
/* 176 */       for (com.firebase.client.Firebase.AuthListener listener : this.listeners) {
/* 177 */         listener.onAuthRevoked(error);
/*     */       }
/*     */     }
/*     */     
/*     */     public void onSuccess(Object authData) {
/* 182 */       if (!this.onSuccessCalled) {
/* 183 */         this.onSuccessCalled = true;
/* 184 */         this.authData = authData;
/* 185 */         for (com.firebase.client.Firebase.AuthListener listener : this.listeners) {
/* 186 */           listener.onAuthSuccess(authData);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static enum ConnectionState {
/* 193 */     Disconnected, 
/* 194 */     Authenticating, 
/* 195 */     Connected;
/*     */     
/*     */ 
/*     */     private ConnectionState() {}
/*     */   }
/*     */   
/*     */ 
/*     */   private static final String REQUEST_ACTION_ONDISCONNECT_MERGE = "om";
/*     */   
/*     */   private static final String REQUEST_ACTION_ONDISCONNECT_CANCEL = "oc";
/*     */   
/*     */   private static final String REQUEST_ACTION_AUTH = "auth";
/*     */   
/*     */   private static final String REQUEST_ACTION_UNAUTH = "unauth";
/*     */   
/*     */   private static final String RESPONSE_FOR_REQUEST = "b";
/*     */   
/*     */   private static final String SERVER_ASYNC_ACTION = "a";
/*     */   
/*     */   private static final String SERVER_ASYNC_PAYLOAD = "b";
/*     */   
/*     */   private static final String SERVER_ASYNC_DATA_UPDATE = "d";
/*     */   
/*     */   private static final String SERVER_ASYNC_DATA_MERGE = "m";
/*     */   
/*     */   private static final String SERVER_ASYNC_AUTH_REVOKED = "ac";
/*     */   
/*     */   private static final String SERVER_ASYNC_LISTEN_CANCELLED = "c";
/*     */   
/*     */   private static final String SERVER_ASYNC_SECURITY_DEBUG = "sd";
/*     */   
/*     */   private static final String SERVER_DATA_UPDATE_PATH = "p";
/*     */   
/*     */   private static final String SERVER_DATA_UPDATE_BODY = "d";
/*     */   
/*     */   private static final String SERVER_DATA_TAG = "t";
/*     */   
/*     */   private static final String SERVER_DATA_WARNINGS = "w";
/*     */   
/*     */   private static final String SERVER_RESPONSE_DATA = "d";
/*     */   
/*     */   private static final long RECONNECT_MIN_DELAY = 1000L;
/*     */   
/*     */   private static final long RECONNECT_RESET_TIMEOUT = 30000L;
/*     */   private static final long RECONNECT_MAX_DELAY = 30000L;
/*     */   private static final double RECONNECT_MULTIPLIER = 1.3D;
/* 241 */   private static long connectionIds = 0L;
/*     */   
/*     */   private Delegate delegate;
/*     */   private RepoInfo repoInfo;
/* 245 */   private boolean shouldReconnect = true;
/* 246 */   private boolean firstConnection = true;
/*     */   private long lastConnectionAttemptTime;
/*     */   private long lastConnectionEstablishedTime;
/*     */   private com.firebase.client.realtime.Connection realtime;
/* 250 */   private ConnectionState connectionState = ConnectionState.Disconnected;
/* 251 */   private long writeCounter = 0L;
/* 252 */   private long requestCounter = 0L;
/* 253 */   private long reconnectDelay = 1000L;
/*     */   
/*     */   private Map<Long, ResponseListener> requestCBHash;
/*     */   private boolean writesPaused;
/*     */   private java.util.List<OutstandingDisconnect> onDisconnectRequestQueue;
/*     */   private Map<Long, OutstandingPut> outstandingPuts;
/*     */   private Map<com.firebase.client.core.view.QuerySpec, OutstandingListen> listens;
/*     */   private java.util.Random random;
/*     */   private java.util.concurrent.ScheduledFuture reconnectFuture;
/*     */   private AuthCredential authCredential;
/*     */   private Context ctx;
/*     */   private com.firebase.client.utilities.LogWrapper logger;
/*     */   
/*     */   public PersistentConnection(Context ctx, RepoInfo info, Delegate delegate)
/*     */   {
/* 268 */     this.delegate = delegate;
/* 269 */     this.ctx = ctx;
/* 270 */     this.repoInfo = info;
/* 271 */     this.listens = new java.util.HashMap();
/* 272 */     this.requestCBHash = new java.util.HashMap();
/* 273 */     this.writesPaused = false;
/* 274 */     this.outstandingPuts = new java.util.HashMap();
/* 275 */     this.onDisconnectRequestQueue = new java.util.ArrayList();
/* 276 */     this.random = new java.util.Random();
/* 277 */     long connId = connectionIds++;
/* 278 */     this.logger = this.ctx.getLogger("PersistentConnection", "pc_" + connId);
/*     */   }
/*     */   
/*     */   public void establishConnection()
/*     */   {
/* 283 */     if (this.shouldReconnect) {
/* 284 */       this.lastConnectionAttemptTime = System.currentTimeMillis();
/* 285 */       this.lastConnectionEstablishedTime = 0L;
/*     */       
/* 287 */       this.realtime = new com.firebase.client.realtime.Connection(this.ctx, this.repoInfo, this);
/* 288 */       this.realtime.open();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onReady(long timestamp)
/*     */   {
/* 295 */     if (this.logger.logsDebug()) this.logger.debug("onReady");
/* 296 */     this.lastConnectionEstablishedTime = System.currentTimeMillis();
/* 297 */     handleTimestamp(timestamp);
/*     */     
/* 299 */     if (this.firstConnection) {
/* 300 */       sendConnectStats();
/*     */     }
/*     */     
/* 303 */     restoreState();
/* 304 */     this.firstConnection = false;
/* 305 */     this.delegate.onConnect();
/*     */   }
/*     */   
/*     */   public void listen(com.firebase.client.core.view.QuerySpec query, SyncTree.SyncTreeHash currentHashFn, Tag tag, RequestResultListener listener) {
/* 309 */     if (this.logger.logsDebug()) {
/* 310 */       this.logger.debug("Listening on " + query);
/*     */     }
/*     */     
/* 313 */     assert (!this.listens.containsKey(query)) : "listen() called twice for same QuerySpec.";
/* 314 */     if (this.logger.logsDebug()) this.logger.debug("Adding listen query: " + query);
/* 315 */     OutstandingListen outstandingListen = new OutstandingListen(listener, query, tag, currentHashFn, null);
/* 316 */     this.listens.put(query, outstandingListen);
/* 317 */     if (connected()) {
/* 318 */       sendListen(outstandingListen);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Map<com.firebase.client.core.view.QuerySpec, OutstandingListen> getListens()
/*     */   {
/* 326 */     return this.listens;
/*     */   }
/*     */   
/*     */   public void put(String pathString, Object data, com.firebase.client.Firebase.CompletionListener onComplete) {
/* 330 */     put(pathString, data, null, onComplete);
/*     */   }
/*     */   
/*     */   public void put(String pathString, Object data, String hash, com.firebase.client.Firebase.CompletionListener onComplete) {
/* 334 */     putInternal("p", pathString, data, hash, onComplete);
/*     */   }
/*     */   
/*     */   public void merge(String pathString, Object data, com.firebase.client.Firebase.CompletionListener onComplete) {
/* 338 */     putInternal("m", pathString, data, null, onComplete);
/*     */   }
/*     */   
/*     */   public void purgeOutstandingWrites() {
/* 342 */     com.firebase.client.FirebaseError error = com.firebase.client.FirebaseError.fromCode(-25);
/* 343 */     for (OutstandingPut put : this.outstandingPuts.values()) {
/* 344 */       if (put.onComplete != null) {
/* 345 */         put.onComplete.onComplete(error, null);
/*     */       }
/*     */     }
/* 348 */     for (OutstandingDisconnect onDisconnect : this.onDisconnectRequestQueue) {
/* 349 */       if (onDisconnect.onComplete != null) {
/* 350 */         onDisconnect.onComplete.onComplete(error, null);
/*     */       }
/*     */     }
/* 353 */     this.outstandingPuts.clear();
/* 354 */     this.onDisconnectRequestQueue.clear();
/*     */   }
/*     */   
/*     */   public void onDataMessage(Map<String, Object> message) {
/* 358 */     if (message.containsKey("r"))
/*     */     {
/*     */ 
/* 361 */       long rn = ((Integer)message.get("r")).intValue();
/* 362 */       ResponseListener responseListener = (ResponseListener)this.requestCBHash.remove(Long.valueOf(rn));
/* 363 */       if (responseListener != null)
/*     */       {
/* 365 */         Map<String, Object> response = (Map)message.get("b");
/*     */         
/* 367 */         responseListener.onResponse(response);
/*     */       }
/* 369 */     } else if (!message.containsKey("error"))
/*     */     {
/* 371 */       if (message.containsKey("a")) {
/* 372 */         String action = (String)message.get("a");
/*     */         
/* 374 */         Map<String, Object> body = (Map)message.get("b");
/*     */         
/* 376 */         onDataPush(action, body);
/*     */       }
/* 378 */       else if (this.logger.logsDebug()) { this.logger.debug("Ignoring unknown message: " + message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDisconnect() {
/* 384 */     if (this.logger.logsDebug()) this.logger.debug("Got on disconnect");
/* 385 */     this.connectionState = ConnectionState.Disconnected;
/* 386 */     if (!this.shouldReconnect)
/*     */     {
/* 388 */       cancelTransactions();
/* 389 */       this.requestCBHash.clear();
/*     */     } else {
/* 391 */       if (this.lastConnectionEstablishedTime > 0L) {
/* 392 */         long timeSinceLastConnectSucceeded = System.currentTimeMillis() - this.lastConnectionEstablishedTime;
/* 393 */         if (timeSinceLastConnectSucceeded > 30000L) {
/* 394 */           this.reconnectDelay = 1000L;
/*     */         }
/* 396 */         this.lastConnectionEstablishedTime = 0L;
/*     */       }
/* 398 */       long timeSinceLastConnectAttempt = System.currentTimeMillis() - this.lastConnectionAttemptTime;
/*     */       
/* 400 */       long recDelay = Math.max(1L, this.reconnectDelay - timeSinceLastConnectAttempt);
/* 401 */       recDelay = this.random.nextInt((int)recDelay);
/*     */       
/* 403 */       if (this.logger.logsDebug()) this.logger.debug("Reconnecting in " + recDelay + "ms");
/* 404 */       this.reconnectFuture = this.ctx.getRunLoop().schedule(new Runnable()
/*     */       {
/*     */ 
/* 407 */         public void run() { PersistentConnection.this.establishConnection(); } }, recDelay);
/*     */       
/*     */ 
/*     */ 
/* 411 */       this.reconnectDelay = Math.min(30000L, (this.reconnectDelay * 1.3D));
/*     */     }
/* 413 */     this.delegate.onDisconnect();
/*     */   }
/*     */   
/*     */   public void onKill(String reason) {
/* 417 */     if (this.logger.logsDebug()) this.logger.debug("Firebase connection was forcefully killed by the server. Will not attempt reconnect. Reason: " + reason);
/* 418 */     this.shouldReconnect = false;
/*     */   }
/*     */   
/*     */   void unlisten(com.firebase.client.core.view.QuerySpec query) {
/* 422 */     if (this.logger.logsDebug()) { this.logger.debug("unlistening on " + query);
/*     */     }
/* 424 */     OutstandingListen listen = removeListen(query);
/* 425 */     if ((listen != null) && (connected())) {
/* 426 */       sendUnlisten(listen);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean connected() {
/* 431 */     return this.connectionState != ConnectionState.Disconnected;
/*     */   }
/*     */   
/*     */   void onDisconnectPut(Path path, Object data, com.firebase.client.Firebase.CompletionListener onComplete) {
/* 435 */     if (canSendWrites()) {
/* 436 */       sendOnDisconnect("o", path, data, onComplete);
/*     */     } else {
/* 438 */       this.onDisconnectRequestQueue.add(new OutstandingDisconnect("o", path, data, onComplete, null));
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean canSendWrites()
/*     */   {
/* 444 */     return (this.connectionState == ConnectionState.Connected) && (!this.writesPaused);
/*     */   }
/*     */   
/*     */   void onDisconnectMerge(Path path, Map<String, Object> updates, com.firebase.client.Firebase.CompletionListener onComplete)
/*     */   {
/* 449 */     if (canSendWrites()) {
/* 450 */       sendOnDisconnect("om", path, updates, onComplete);
/*     */     } else {
/* 452 */       this.onDisconnectRequestQueue.add(new OutstandingDisconnect("om", path, updates, onComplete, null));
/*     */     }
/*     */   }
/*     */   
/*     */   void onDisconnectCancel(Path path, com.firebase.client.Firebase.CompletionListener onComplete)
/*     */   {
/* 458 */     if (canSendWrites()) {
/* 459 */       sendOnDisconnect("oc", path, null, onComplete);
/*     */     } else {
/* 461 */       this.onDisconnectRequestQueue.add(new OutstandingDisconnect("oc", path, null, onComplete, null));
/*     */     }
/*     */   }
/*     */   
/*     */   void interrupt()
/*     */   {
/* 467 */     this.shouldReconnect = false;
/* 468 */     if (this.realtime != null) {
/* 469 */       this.realtime.close();
/* 470 */       this.realtime = null;
/*     */     } else {
/* 472 */       if (this.reconnectFuture != null) {
/* 473 */         this.reconnectFuture.cancel(false);
/* 474 */         this.reconnectFuture = null;
/*     */       }
/* 476 */       onDisconnect();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resume() {
/* 481 */     this.shouldReconnect = true;
/* 482 */     if (this.realtime == null) {
/* 483 */       establishConnection();
/*     */     }
/*     */   }
/*     */   
/*     */   public void auth(String credential, com.firebase.client.Firebase.AuthListener listener) {
/* 488 */     if (this.authCredential == null) {
/* 489 */       this.authCredential = new AuthCredential(listener, credential);
/* 490 */     } else if (this.authCredential.matches(credential)) {
/* 491 */       this.authCredential.addListener(listener);
/* 492 */       if (this.authCredential.isComplete()) {
/* 493 */         this.authCredential.replay(listener);
/*     */       }
/*     */     } else {
/* 496 */       this.authCredential.preempt();
/* 497 */       this.authCredential = new AuthCredential(listener, credential);
/*     */     }
/* 499 */     if (connected()) {
/* 500 */       if (this.logger.logsDebug()) this.logger.debug("Authenticating with credential: " + credential);
/* 501 */       sendAuth();
/*     */     }
/*     */   }
/*     */   
/*     */   public void unauth(final com.firebase.client.Firebase.CompletionListener listener) {
/* 506 */     this.authCredential = null;
/* 507 */     this.delegate.onAuthStatus(false);
/*     */     
/* 509 */     if (connected()) {
/* 510 */       sendAction("unauth", new java.util.HashMap(), new ResponseListener()
/*     */       {
/*     */         public void onResponse(Map<String, Object> response)
/*     */         {
/* 514 */           String status = (String)response.get("s");
/* 515 */           com.firebase.client.FirebaseError error = null;
/* 516 */           if (!status.equals("ok")) {
/* 517 */             error = com.firebase.client.FirebaseError.fromStatus(status, (String)response.get("d"));
/*     */           }
/*     */           
/* 520 */           listener.onComplete(error, null);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   public void pauseWrites()
/*     */   {
/* 528 */     if (this.logger.logsDebug()) this.logger.debug("Writes paused.");
/* 529 */     this.writesPaused = true;
/*     */   }
/*     */   
/*     */   public void unpauseWrites() {
/* 533 */     if (this.logger.logsDebug()) this.logger.debug("Writes unpaused.");
/* 534 */     this.writesPaused = false;
/* 535 */     if (canSendWrites()) {
/* 536 */       restoreWrites();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean writesPaused() {
/* 541 */     return this.writesPaused;
/*     */   }
/*     */   
/*     */   private void sendOnDisconnect(String action, Path path, Object data, final com.firebase.client.Firebase.CompletionListener onComplete)
/*     */   {
/* 546 */     Map<String, Object> request = new java.util.HashMap();
/* 547 */     request.put("p", path.toString());
/* 548 */     request.put("d", data);
/* 549 */     if (this.logger.logsDebug()) this.logger.debug("onDisconnect " + action + " " + request);
/* 550 */     sendAction(action, request, new ResponseListener()
/*     */     {
/*     */       public void onResponse(Map<String, Object> response) {
/* 553 */         String status = (String)response.get("s");
/* 554 */         com.firebase.client.FirebaseError error = null;
/* 555 */         if (!status.equals("ok")) {
/* 556 */           error = com.firebase.client.FirebaseError.fromStatus(status, (String)response.get("d"));
/*     */         }
/*     */         
/* 559 */         if (onComplete != null) {
/* 560 */           onComplete.onComplete(error, null);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void cancelTransactions() {
/* 567 */     java.util.Iterator<java.util.Map.Entry<Long, OutstandingPut>> iter = this.outstandingPuts.entrySet().iterator();
/* 568 */     while (iter.hasNext()) {
/* 569 */       java.util.Map.Entry<Long, OutstandingPut> entry = (java.util.Map.Entry)iter.next();
/* 570 */       OutstandingPut put = (OutstandingPut)entry.getValue();
/* 571 */       if (put.getRequest().containsKey("h")) {
/* 572 */         put.getOnComplete().onComplete(com.firebase.client.FirebaseError.fromStatus("disconnected"), null);
/* 573 */         iter.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendUnlisten(OutstandingListen listen) {
/* 579 */     Map<String, Object> request = new java.util.HashMap();
/* 580 */     request.put("p", listen.query.getPath().toString());
/*     */     
/* 582 */     Tag tag = listen.getTag();
/* 583 */     if (tag != null) {
/* 584 */       request.put("q", listen.getQuery().getParams().getWireProtocolParams());
/* 585 */       request.put("t", Long.valueOf(tag.getTagNumber()));
/*     */     }
/*     */     
/* 588 */     sendAction("n", request, null);
/*     */   }
/*     */   
/*     */   private OutstandingListen removeListen(com.firebase.client.core.view.QuerySpec query) {
/* 592 */     if (this.logger.logsDebug()) this.logger.debug("removing query " + query);
/* 593 */     if (!this.listens.containsKey(query)) {
/* 594 */       if (this.logger.logsDebug()) this.logger.debug("Trying to remove listener for QuerySpec " + query + " but no listener exists.");
/* 595 */       return null;
/*     */     }
/* 597 */     OutstandingListen oldListen = (OutstandingListen)this.listens.get(query);
/* 598 */     this.listens.remove(query);
/* 599 */     return oldListen;
/*     */   }
/*     */   
/*     */   public java.util.Collection<OutstandingListen> removeListens(Path path)
/*     */   {
/* 604 */     if (this.logger.logsDebug()) this.logger.debug("removing all listens at path " + path);
/* 605 */     java.util.List<OutstandingListen> removedListens = new java.util.ArrayList();
/* 606 */     for (java.util.Map.Entry<com.firebase.client.core.view.QuerySpec, OutstandingListen> entry : this.listens.entrySet()) {
/* 607 */       com.firebase.client.core.view.QuerySpec query = (com.firebase.client.core.view.QuerySpec)entry.getKey();
/* 608 */       OutstandingListen listen = (OutstandingListen)entry.getValue();
/* 609 */       if (query.getPath().equals(path)) {
/* 610 */         removedListens.add(listen);
/*     */       }
/*     */     }
/*     */     
/* 614 */     for (OutstandingListen toRemove : removedListens) {
/* 615 */       this.listens.remove(toRemove.getQuery());
/*     */     }
/*     */     
/* 618 */     return removedListens;
/*     */   }
/*     */   
/*     */   private void onDataPush(String action, Map<String, Object> body) {
/* 622 */     if (this.logger.logsDebug()) this.logger.debug("handleServerMessage: " + action + " " + body);
/* 623 */     if ((action.equals("d")) || (action.equals("m"))) {
/* 624 */       boolean isMerge = action.equals("m");
/*     */       
/* 626 */       String pathString = (String)body.get("p");
/* 627 */       Object payloadData = body.get("d");
/* 628 */       Long tagNumber = com.firebase.client.utilities.Utilities.longFromObject(body.get("t"));
/* 629 */       Tag tag = tagNumber != null ? new Tag(tagNumber.longValue()) : null;
/*     */       
/* 631 */       if ((isMerge) && ((payloadData instanceof Map)) && (((Map)payloadData).size() == 0)) {
/* 632 */         if (this.logger.logsDebug()) this.logger.debug("ignoring empty merge for path " + pathString);
/*     */       } else {
/* 634 */         this.delegate.onDataUpdate(pathString, payloadData, isMerge, tag);
/*     */       }
/* 636 */     } else if (action.equals("c")) {
/* 637 */       String pathString = (String)body.get("p");
/* 638 */       onListenRevoked(new Path(pathString));
/* 639 */     } else if (action.equals("ac")) {
/* 640 */       String status = (String)body.get("s");
/* 641 */       String reason = (String)body.get("d");
/* 642 */       onAuthRevoked(status, reason);
/* 643 */     } else if (action.equals("sd")) {
/* 644 */       onSecurityDebugPacket(body);
/*     */     }
/* 646 */     else if (this.logger.logsDebug()) { this.logger.debug("Unrecognized action from server: " + action);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void onListenRevoked(Path path)
/*     */   {
/* 653 */     java.util.Collection<OutstandingListen> listens = removeListens(path);
/*     */     com.firebase.client.FirebaseError error;
/* 655 */     if (listens != null) {
/* 656 */       error = com.firebase.client.FirebaseError.fromStatus("permission_denied");
/* 657 */       for (OutstandingListen listen : listens) {
/* 658 */         listen.resultListener.onRequestResult(error);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void onAuthRevoked(String status, String reason) {
/* 664 */     if (this.authCredential != null) {
/* 665 */       com.firebase.client.FirebaseError error = com.firebase.client.FirebaseError.fromStatus(status, reason);
/* 666 */       this.authCredential.onRevoked(error);
/* 667 */       this.authCredential = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private void onSecurityDebugPacket(Map<String, Object> message)
/*     */   {
/* 673 */     this.logger.info((String)message.get("msg"));
/*     */   }
/*     */   
/*     */   private void sendAuth() {
/* 677 */     sendAuthHelper(false);
/*     */   }
/*     */   
/*     */   private void sendAuthAndRestoreWrites() {
/* 681 */     sendAuthHelper(true);
/*     */   }
/*     */   
/*     */   private void sendAuthHelper(final boolean restoreWritesAfterComplete) {
/* 685 */     assert (connected()) : "Must be connected to send auth.";
/* 686 */     assert (this.authCredential != null) : "Can't send auth if it's null.";
/*     */     
/* 688 */     Map<String, Object> request = new java.util.HashMap();
/* 689 */     request.put("cred", this.authCredential.getCredential());
/* 690 */     final AuthCredential credential = this.authCredential;
/* 691 */     sendAction("auth", request, new ResponseListener()
/*     */     {
/*     */       public void onResponse(Map<String, Object> response) {
/* 694 */         PersistentConnection.this.connectionState = PersistentConnection.ConnectionState.Connected;
/*     */         
/*     */ 
/* 697 */         if (credential == PersistentConnection.this.authCredential) {
/* 698 */           String status = (String)response.get("s");
/* 699 */           if (status.equals("ok")) {
/* 700 */             PersistentConnection.this.delegate.onAuthStatus(true);
/* 701 */             credential.onSuccess(response.get("d"));
/*     */           }
/*     */           else {
/* 704 */             PersistentConnection.this.authCredential = null;
/* 705 */             PersistentConnection.this.delegate.onAuthStatus(false);
/* 706 */             String reason = (String)response.get("d");
/* 707 */             credential.onCancel(com.firebase.client.FirebaseError.fromStatus(status, reason));
/*     */           }
/*     */         }
/*     */         
/* 711 */         if (restoreWritesAfterComplete) {
/* 712 */           PersistentConnection.this.restoreWrites();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void restoreState() {
/* 719 */     if (this.logger.logsDebug()) { this.logger.debug("calling restore state");
/*     */     }
/* 721 */     if (this.authCredential != null) {
/* 722 */       if (this.logger.logsDebug()) this.logger.debug("Restoring auth.");
/* 723 */       this.connectionState = ConnectionState.Authenticating;
/* 724 */       sendAuthAndRestoreWrites();
/*     */     } else {
/* 726 */       this.connectionState = ConnectionState.Connected;
/*     */     }
/*     */     
/*     */ 
/* 730 */     if (this.logger.logsDebug()) this.logger.debug("Restoring outstanding listens");
/* 731 */     for (OutstandingListen listen : this.listens.values()) {
/* 732 */       if (this.logger.logsDebug()) this.logger.debug("Restoring listen " + listen.getQuery());
/* 733 */       sendListen(listen);
/*     */     }
/*     */     
/* 736 */     if (this.connectionState == ConnectionState.Connected)
/*     */     {
/* 738 */       restoreWrites();
/*     */     }
/*     */   }
/*     */   
/*     */   private void restoreWrites() {
/* 743 */     assert (this.connectionState == ConnectionState.Connected) : "Should be connected if we're restoring writes.";
/*     */     
/* 745 */     if (this.writesPaused) {
/* 746 */       if (this.logger.logsDebug()) this.logger.debug("Writes are paused; skip restoring writes.");
/*     */     } else {
/* 748 */       if (this.logger.logsDebug()) { this.logger.debug("Restoring writes.");
/*     */       }
/* 750 */       java.util.ArrayList<Long> outstanding = new java.util.ArrayList(this.outstandingPuts.keySet());
/*     */       
/* 752 */       java.util.Collections.sort(outstanding);
/* 753 */       for (Long put : outstanding) {
/* 754 */         sendPut(put.longValue());
/*     */       }
/*     */       
/*     */ 
/* 758 */       for (OutstandingDisconnect disconnect : this.onDisconnectRequestQueue) {
/* 759 */         sendOnDisconnect(disconnect.getAction(), disconnect.getPath(), disconnect.getData(), disconnect.getOnComplete());
/*     */       }
/*     */       
/* 762 */       this.onDisconnectRequestQueue.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private void handleTimestamp(long timestamp) {
/* 767 */     if (this.logger.logsDebug()) this.logger.debug("handling timestamp");
/* 768 */     long timestampDelta = timestamp - System.currentTimeMillis();
/* 769 */     Map<com.firebase.client.snapshot.ChildKey, Object> updates = new java.util.HashMap();
/* 770 */     updates.put(Constants.DOT_INFO_SERVERTIME_OFFSET, Long.valueOf(timestampDelta));
/* 771 */     this.delegate.onServerInfoUpdate(updates);
/*     */   }
/*     */   
/*     */   private Map<String, Object> getPutObject(String pathString, Object data, String hash) {
/* 775 */     Map<String, Object> request = new java.util.HashMap();
/* 776 */     request.put("p", pathString);
/* 777 */     request.put("d", data);
/* 778 */     if (hash != null) {
/* 779 */       request.put("h", hash);
/*     */     }
/* 781 */     return request;
/*     */   }
/*     */   
/*     */   private void putInternal(String action, String pathString, Object data, String hash, com.firebase.client.Firebase.CompletionListener onComplete)
/*     */   {
/* 786 */     Map<String, Object> request = getPutObject(pathString, data, hash);
/*     */     
/*     */ 
/* 789 */     long writeId = this.writeCounter++;
/*     */     
/* 791 */     this.outstandingPuts.put(Long.valueOf(writeId), new OutstandingPut(action, request, onComplete, null));
/* 792 */     if (canSendWrites()) {
/* 793 */       sendPut(writeId);
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendPut(final long putId) {
/* 798 */     assert (canSendWrites()) : "sendPut called when we can't send writes (we're disconnected or writes are paused).";
/* 799 */     OutstandingPut put = (OutstandingPut)this.outstandingPuts.get(Long.valueOf(putId));
/* 800 */     final com.firebase.client.Firebase.CompletionListener onComplete = put.getOnComplete();
/* 801 */     final String action = put.getAction();
/*     */     
/* 803 */     sendAction(action, put.getRequest(), new ResponseListener()
/*     */     {
/*     */       public void onResponse(Map<String, Object> response) {
/* 806 */         if (PersistentConnection.this.logger.logsDebug()) { PersistentConnection.this.logger.debug(action + " response: " + response);
/*     */         }
/* 808 */         PersistentConnection.OutstandingPut currentPut = (PersistentConnection.OutstandingPut)PersistentConnection.this.outstandingPuts.get(Long.valueOf(putId));
/* 809 */         if (currentPut == onComplete) {
/* 810 */           PersistentConnection.this.outstandingPuts.remove(Long.valueOf(putId));
/*     */           
/* 812 */           if (this.val$onComplete != null) {
/* 813 */             String status = (String)response.get("s");
/* 814 */             if (status.equals("ok")) {
/* 815 */               this.val$onComplete.onComplete(null, null);
/*     */             } else {
/* 817 */               this.val$onComplete.onComplete(com.firebase.client.FirebaseError.fromStatus(status, (String)response.get("d")), null);
/*     */             }
/*     */             
/*     */           }
/*     */         }
/* 822 */         else if (PersistentConnection.this.logger.logsDebug()) { PersistentConnection.this.logger.debug("Ignoring on complete for put " + putId + " because it was removed already.");
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void sendListen(final OutstandingListen listen) {
/* 829 */     Map<String, Object> request = new java.util.HashMap();
/* 830 */     request.put("p", listen.getQuery().getPath().toString());
/* 831 */     Tag tag = listen.getTag();
/*     */     
/* 833 */     if (tag != null) {
/* 834 */       request.put("q", listen.getQuery().getParams().getWireProtocolParams());
/* 835 */       request.put("t", Long.valueOf(tag.getTagNumber()));
/*     */     }
/*     */     
/* 838 */     SyncTree.SyncTreeHash hashFunction = listen.getHashFunction();
/* 839 */     request.put("h", hashFunction.getHash());
/*     */     
/*     */ 
/* 842 */     sendAction("q", request, new ResponseListener()
/*     */     {
/*     */       public void onResponse(Map<String, Object> response)
/*     */       {
/* 846 */         String status = (String)response.get("s");
/*     */         
/* 848 */         if (status.equals("ok")) {
/* 849 */           Map<String, Object> serverBody = (Map)response.get("d");
/* 850 */           if (serverBody.containsKey("w")) {
/* 851 */             java.util.List<String> warnings = (java.util.List)serverBody.get("w");
/* 852 */             PersistentConnection.this.warnOnListenerWarnings(warnings, listen.getQuery());
/*     */           }
/*     */         }
/*     */         
/* 856 */         PersistentConnection.OutstandingListen currentListen = (PersistentConnection.OutstandingListen)PersistentConnection.this.listens.get(listen.getQuery());
/*     */         
/* 858 */         if (currentListen == listen) {
/* 859 */           if (!status.equals("ok")) {
/* 860 */             PersistentConnection.this.removeListen(listen.getQuery());
/* 861 */             com.firebase.client.FirebaseError error = com.firebase.client.FirebaseError.fromStatus(status, (String)response.get("d"));
/* 862 */             listen.resultListener.onRequestResult(error);
/*     */           } else {
/* 864 */             listen.resultListener.onRequestResult(null);
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void sendStats(Map<String, Integer> stats) {
/* 872 */     if (!stats.isEmpty()) {
/* 873 */       Map<String, Object> request = new java.util.HashMap();
/* 874 */       request.put("c", stats);
/* 875 */       sendAction("s", request, new ResponseListener()
/*     */       {
/*     */         public void onResponse(Map<String, Object> response) {
/* 878 */           String status = (String)response.get("s");
/* 879 */           if (!status.equals("ok")) {
/* 880 */             com.firebase.client.FirebaseError error = com.firebase.client.FirebaseError.fromStatus(status, (String)response.get("d"));
/*     */             
/* 882 */             if (PersistentConnection.this.logger.logsDebug()) { PersistentConnection.this.logger.debug("Failed to send stats: " + error);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/* 888 */     else if (this.logger.logsDebug()) { this.logger.debug("Not sending stats because stats are empty");
/*     */     }
/*     */   }
/*     */   
/*     */   private void warnOnListenerWarnings(java.util.List<String> warnings, com.firebase.client.core.view.QuerySpec query)
/*     */   {
/* 894 */     if (warnings.contains("no_index")) {
/* 895 */       String indexSpec = "\".indexOn\": \"" + query.getIndex().getQueryDefinition() + '"';
/* 896 */       this.logger.warn("Using an unspecified index. Consider adding '" + indexSpec + "' at " + query.getPath() + " to your security and Firebase rules for better performance");
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendConnectStats()
/*     */   {
/* 902 */     Map<String, Integer> stats = new java.util.HashMap();
/* 903 */     if (AndroidSupport.isAndroid()) {
/* 904 */       if (this.ctx.isPersistenceEnabled()) {
/* 905 */         stats.put("persistence.android.enabled", Integer.valueOf(1));
/*     */       }
/* 907 */       stats.put("sdk.android." + com.firebase.client.Firebase.getSdkVersion().replace('.', '-'), Integer.valueOf(1));
/*     */     } else {
/* 909 */       assert (!this.ctx.isPersistenceEnabled()) : "Stats for persistence on JVM missing (persistence not yet supported)";
/* 910 */       stats.put("sdk.java." + com.firebase.client.Firebase.getSdkVersion().replace('.', '-'), Integer.valueOf(1));
/*     */     }
/* 912 */     if (this.logger.logsDebug()) this.logger.debug("Sending first connection stats");
/* 913 */     sendStats(stats);
/*     */   }
/*     */   
/*     */   private void sendAction(String action, Map<String, Object> message, ResponseListener onResponse) {
/* 917 */     long rn = nextRequestNumber();
/* 918 */     Map<String, Object> request = new java.util.HashMap();
/* 919 */     request.put("r", Long.valueOf(rn));
/* 920 */     request.put("a", action);
/* 921 */     request.put("b", message);
/* 922 */     this.realtime.sendRequest(request);
/* 923 */     this.requestCBHash.put(Long.valueOf(rn), onResponse);
/*     */   }
/*     */   
/*     */   private long nextRequestNumber() {
/* 927 */     return this.requestCounter++;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/PersistentConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */