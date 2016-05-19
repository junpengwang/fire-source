/*      */ package com.firebase.client.core;
/*      */ 
/*      */ import com.firebase.client.DataSnapshot;
/*      */ import com.firebase.client.Firebase;
/*      */ import com.firebase.client.Firebase.CompletionListener;
/*      */ import com.firebase.client.FirebaseError;
/*      */ import com.firebase.client.Transaction.Handler;
/*      */ import com.firebase.client.Transaction.Result;
/*      */ import com.firebase.client.core.utilities.Tree;
/*      */ import com.firebase.client.core.view.Event;
/*      */ import com.firebase.client.core.view.QuerySpec;
/*      */ import com.firebase.client.snapshot.ChildKey;
/*      */ import com.firebase.client.snapshot.Node;
/*      */ import com.firebase.client.utilities.LogWrapper;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ 
/*      */ public class Repo implements PersistentConnection.Delegate
/*      */ {
/*      */   private final RepoInfo repoInfo;
/*   23 */   private final com.firebase.client.utilities.OffsetClock serverClock = new com.firebase.client.utilities.OffsetClock(new com.firebase.client.utilities.DefaultClock(), 0L);
/*      */   private final PersistentConnection connection;
/*      */   private final com.firebase.client.authentication.AuthenticationManager authenticationManager;
/*      */   private SnapshotHolder infoData;
/*      */   private SparseSnapshotTree onDisconnect;
/*      */   private Tree<List<TransactionData>> transactionQueueTree;
/*   29 */   private boolean hijackHash = false;
/*      */   private final com.firebase.client.core.view.EventRaiser eventRaiser;
/*      */   private final Context ctx;
/*      */   private final LogWrapper operationLogger;
/*      */   private final LogWrapper transactionLogger;
/*      */   private final LogWrapper dataLogger;
/*   35 */   public long dataUpdateCount = 0L;
/*   36 */   private long nextWriteId = 1L;
/*      */   private SyncTree infoSyncTree;
/*      */   private SyncTree serverSyncTree;
/*      */   private com.firebase.client.FirebaseApp app;
/*   40 */   private boolean loggedTransactionPersistenceWarning = false;
/*      */   private static final int TRANSACTION_MAX_RETRIES = 25;
/*      */   
/*      */   private static class FirebaseAppImpl extends com.firebase.client.FirebaseApp {
/*   44 */     protected FirebaseAppImpl(Repo repo) { super(); }
/*      */   }
/*      */   
/*      */   Repo(RepoInfo repoInfo, Context ctx)
/*      */   {
/*   49 */     this.repoInfo = repoInfo;
/*   50 */     this.ctx = ctx;
/*   51 */     this.app = new FirebaseAppImpl(this);
/*      */     
/*   53 */     this.operationLogger = this.ctx.getLogger("RepoOperation");
/*   54 */     this.transactionLogger = this.ctx.getLogger("Transaction");
/*   55 */     this.dataLogger = this.ctx.getLogger("DataOperation");
/*      */     
/*   57 */     this.eventRaiser = new com.firebase.client.core.view.EventRaiser(this.ctx);
/*      */     
/*   59 */     this.connection = new PersistentConnection(ctx, repoInfo, this);
/*   60 */     this.authenticationManager = new com.firebase.client.authentication.AuthenticationManager(ctx, this, repoInfo, this.connection);
/*      */     
/*      */ 
/*      */ 
/*   64 */     this.authenticationManager.resumeSession();
/*      */     
/*      */ 
/*   67 */     scheduleNow(new Runnable()
/*      */     {
/*      */       public void run() {
/*   70 */         Repo.this.deferredInitialization();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void deferredInitialization()
/*      */   {
/*   81 */     this.connection.establishConnection();
/*      */     
/*   83 */     com.firebase.client.core.persistence.PersistenceManager persistenceManager = this.ctx.getPersistenceManager(this.repoInfo.host);
/*      */     
/*   85 */     this.infoData = new SnapshotHolder();
/*   86 */     this.onDisconnect = new SparseSnapshotTree();
/*      */     
/*   88 */     this.transactionQueueTree = new Tree();
/*      */     
/*   90 */     this.infoSyncTree = new SyncTree(this.ctx, new com.firebase.client.core.persistence.NoopPersistenceManager(), new SyncTree.ListenProvider()
/*      */     {
/*      */       public void startListening(final QuerySpec query, Tag tag, SyncTree.SyncTreeHash hash, final SyncTree.CompletionListener onComplete)
/*      */       {
/*   94 */         Repo.this.scheduleNow(new Runnable()
/*      */         {
/*      */ 
/*      */           public void run()
/*      */           {
/*   99 */             Node node = Repo.this.infoData.getNode(query.getPath());
/*  100 */             if (!node.isEmpty()) {
/*  101 */               List<? extends Event> infoEvents = Repo.this.infoSyncTree.applyServerOverwrite(query.getPath(), node);
/*  102 */               Repo.this.postEvents(infoEvents);
/*  103 */               onComplete.onListenComplete(null);
/*      */             }
/*      */           }
/*      */         });
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public void stopListening(QuerySpec query, Tag tag) {}
/*  112 */     });
/*  113 */     this.serverSyncTree = new SyncTree(this.ctx, persistenceManager, new SyncTree.ListenProvider()
/*      */     {
/*      */       public void startListening(QuerySpec query, Tag tag, SyncTree.SyncTreeHash hash, final SyncTree.CompletionListener onListenComplete) {
/*  116 */         Repo.this.connection.listen(query, hash, tag, new PersistentConnection.RequestResultListener()
/*      */         {
/*      */           public void onRequestResult(FirebaseError error) {
/*  119 */             List<? extends Event> events = onListenComplete.onListenComplete(error);
/*  120 */             Repo.this.postEvents(events);
/*      */           }
/*      */         });
/*      */       }
/*      */       
/*      */       public void stopListening(QuerySpec query, Tag tag)
/*      */       {
/*  127 */         Repo.this.connection.unlisten(query);
/*      */       }
/*      */       
/*  130 */     });
/*  131 */     restoreWrites(persistenceManager);
/*      */     
/*  133 */     boolean authenticated = this.authenticationManager.getAuth() != null;
/*  134 */     updateInfo(Constants.DOT_INFO_AUTHENTICATED, Boolean.valueOf(authenticated));
/*  135 */     updateInfo(Constants.DOT_INFO_CONNECTED, Boolean.valueOf(false));
/*      */   }
/*      */   
/*      */   private void restoreWrites(com.firebase.client.core.persistence.PersistenceManager persistenceManager) {
/*  139 */     List<UserWriteRecord> writes = persistenceManager.loadUserWrites();
/*      */     
/*  141 */     Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
/*  142 */     long lastWriteId = Long.MIN_VALUE;
/*  143 */     for (final UserWriteRecord write : writes) {
/*  144 */       Firebase.CompletionListener onComplete = new Firebase.CompletionListener()
/*      */       {
/*      */         public void onComplete(FirebaseError error, Firebase ref) {
/*  147 */           Repo.this.warnIfWriteFailed("Persisted write", write.getPath(), error);
/*  148 */           Repo.this.ackWriteAndRerunTransactions(write.getWriteId(), write.getPath(), error);
/*      */         }
/*      */       };
/*  151 */       if (lastWriteId >= write.getWriteId()) {
/*  152 */         throw new IllegalStateException("Write ids were not in order.");
/*      */       }
/*  154 */       lastWriteId = write.getWriteId();
/*  155 */       this.nextWriteId = (write.getWriteId() + 1L);
/*  156 */       if (write.isOverwrite()) {
/*  157 */         if (this.operationLogger.logsDebug()) this.operationLogger.debug("Restoring overwrite with id " + write.getWriteId());
/*  158 */         this.connection.put(write.getPath().toString(), write.getOverwrite().getValue(true), null, onComplete);
/*  159 */         Node resolved = ServerValues.resolveDeferredValueSnapshot(write.getOverwrite(), serverValues);
/*  160 */         this.serverSyncTree.applyUserOverwrite(write.getPath(), write.getOverwrite(), resolved, write.getWriteId(), true, false);
/*      */       }
/*      */       else {
/*  163 */         if (this.operationLogger.logsDebug()) this.operationLogger.debug("Restoring merge with id " + write.getWriteId());
/*  164 */         this.connection.merge(write.getPath().toString(), write.getMerge().getValue(true), onComplete);
/*  165 */         CompoundWrite resolved = ServerValues.resolveDeferredValueMerge(write.getMerge(), serverValues);
/*  166 */         this.serverSyncTree.applyUserMerge(write.getPath(), write.getMerge(), resolved, write.getWriteId(), false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public com.firebase.client.authentication.AuthenticationManager getAuthenticationManager() {
/*  172 */     return this.authenticationManager;
/*      */   }
/*      */   
/*      */   public com.firebase.client.FirebaseApp getFirebaseApp() {
/*  176 */     return this.app;
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/*  181 */     return this.repoInfo.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scheduleNow(Runnable r)
/*      */   {
/*  190 */     this.ctx.requireStarted();
/*  191 */     this.ctx.getRunLoop().scheduleNow(r);
/*      */   }
/*      */   
/*      */   public void postEvent(Runnable r) {
/*  195 */     this.ctx.requireStarted();
/*  196 */     this.ctx.getEventTarget().postEvent(r);
/*      */   }
/*      */   
/*      */   private void postEvents(List<? extends Event> events) {
/*  200 */     if (!events.isEmpty()) {
/*  201 */       this.eventRaiser.raiseEvents(events);
/*      */     }
/*      */   }
/*      */   
/*      */   public long getServerTime() {
/*  206 */     return this.serverClock.millis();
/*      */   }
/*      */   
/*      */   boolean hasListeners() {
/*  210 */     return (!this.infoSyncTree.isEmpty()) || (!this.serverSyncTree.isEmpty());
/*      */   }
/*      */   
/*      */ 
/*      */   public void onDataUpdate(String pathString, Object message, boolean isMerge, Tag tag)
/*      */   {
/*  216 */     if (this.operationLogger.logsDebug()) this.operationLogger.debug("onDataUpdate: " + pathString);
/*  217 */     if (this.dataLogger.logsDebug()) this.operationLogger.debug("onDataUpdate: " + pathString + " " + message);
/*  218 */     this.dataUpdateCount += 1L;
/*      */     
/*  220 */     Path path = new Path(pathString);
/*      */     try {
/*      */       List<? extends Event> events;
/*      */       List<? extends Event> events;
/*  224 */       if (tag != null) { List<? extends Event> events;
/*  225 */         if (isMerge) {
/*  226 */           Map<Path, Node> taggedChildren = new java.util.HashMap();
/*  227 */           Map<String, Object> rawMergeData = (Map)message;
/*  228 */           for (Map.Entry<String, Object> entry : rawMergeData.entrySet()) {
/*  229 */             Node newChildNode = com.firebase.client.snapshot.NodeUtilities.NodeFromJSON(entry.getValue());
/*  230 */             taggedChildren.put(new Path((String)entry.getKey()), newChildNode);
/*      */           }
/*  232 */           events = this.serverSyncTree.applyTaggedQueryMerge(path, taggedChildren, tag);
/*      */         } else {
/*  234 */           Node taggedSnap = com.firebase.client.snapshot.NodeUtilities.NodeFromJSON(message);
/*  235 */           events = this.serverSyncTree.applyTaggedQueryOverwrite(path, taggedSnap, tag);
/*      */         } } else { List<? extends Event> events;
/*  237 */         if (isMerge) {
/*  238 */           Map<Path, Node> changedChildren = new java.util.HashMap();
/*  239 */           Map<String, Object> rawMergeData = (Map)message;
/*  240 */           for (Map.Entry<String, Object> entry : rawMergeData.entrySet()) {
/*  241 */             Node newChildNode = com.firebase.client.snapshot.NodeUtilities.NodeFromJSON(entry.getValue());
/*  242 */             changedChildren.put(new Path((String)entry.getKey()), newChildNode);
/*      */           }
/*  244 */           events = this.serverSyncTree.applyServerMerge(path, changedChildren);
/*      */         } else {
/*  246 */           Node snap = com.firebase.client.snapshot.NodeUtilities.NodeFromJSON(message);
/*  247 */           events = this.serverSyncTree.applyServerOverwrite(path, snap);
/*      */         } }
/*  249 */       if (events.size() > 0)
/*      */       {
/*      */ 
/*  252 */         rerunTransactions(path);
/*      */       }
/*      */       
/*  255 */       postEvents(events);
/*      */     } catch (com.firebase.client.FirebaseException e) {
/*  257 */       this.operationLogger.error("FIREBASE INTERNAL ERROR", e);
/*      */     }
/*      */   }
/*      */   
/*      */   void callOnComplete(final Firebase.CompletionListener onComplete, final FirebaseError error, Path path) {
/*  262 */     if (onComplete != null)
/*      */     {
/*  264 */       ChildKey last = path.getBack();
/*  265 */       Firebase ref; final Firebase ref; if ((last != null) && (last.isPriorityChildName())) {
/*  266 */         ref = new Firebase(this, path.getParent());
/*      */       } else {
/*  268 */         ref = new Firebase(this, path);
/*      */       }
/*  270 */       postEvent(new Runnable()
/*      */       {
/*      */         public void run() {
/*  273 */           onComplete.onComplete(error, ref);
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */   
/*      */   private void ackWriteAndRerunTransactions(long writeId, Path path, FirebaseError error) {
/*  280 */     if ((error == null) || (error.getCode() != -25))
/*      */     {
/*      */ 
/*  283 */       boolean success = error == null;
/*  284 */       List<? extends Event> clearEvents = this.serverSyncTree.ackUserWrite(writeId, !success, true, this.serverClock);
/*  285 */       if (clearEvents.size() > 0) {
/*  286 */         rerunTransactions(path);
/*      */       }
/*  288 */       postEvents(clearEvents);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setValue(final Path path, Node newValueUnresolved, Firebase.CompletionListener onComplete) {
/*  293 */     if (this.operationLogger.logsDebug()) this.operationLogger.debug("set: " + path);
/*  294 */     if (this.dataLogger.logsDebug()) { this.dataLogger.debug("set: " + path + " " + newValueUnresolved);
/*      */     }
/*  296 */     Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
/*  297 */     Node newValue = ServerValues.resolveDeferredValueSnapshot(newValueUnresolved, serverValues);
/*      */     
/*  299 */     final long writeId = getNextWriteId();
/*  300 */     List<? extends Event> events = this.serverSyncTree.applyUserOverwrite(path, newValueUnresolved, newValue, writeId, true, true);
/*      */     
/*  302 */     postEvents(events);
/*      */     
/*  304 */     this.connection.put(path.toString(), newValueUnresolved.getValue(true), new Firebase.CompletionListener()
/*      */     {
/*      */       public void onComplete(FirebaseError error, Firebase ref) {
/*  307 */         Repo.this.warnIfWriteFailed("setValue", path, error);
/*  308 */         Repo.this.ackWriteAndRerunTransactions(writeId, path, error);
/*  309 */         Repo.this.callOnComplete(this.val$onComplete, error, path);
/*      */       }
/*      */       
/*  312 */     });
/*  313 */     Path affectedPath = abortTransactions(path, -9);
/*  314 */     rerunTransactions(affectedPath);
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateChildren(final Path path, CompoundWrite updates, Firebase.CompletionListener onComplete, Map<String, Object> unParsedUpdates)
/*      */   {
/*  320 */     if (this.operationLogger.logsDebug()) this.operationLogger.debug("update: " + path);
/*  321 */     if (this.dataLogger.logsDebug()) this.dataLogger.debug("update: " + path + " " + unParsedUpdates);
/*  322 */     if (updates.isEmpty()) {
/*  323 */       if (this.operationLogger.logsDebug()) { this.operationLogger.debug("update called with no changes. No-op");
/*      */       }
/*  325 */       callOnComplete(onComplete, null, path);
/*  326 */       return;
/*      */     }
/*      */     
/*      */ 
/*  330 */     Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
/*  331 */     CompoundWrite resolved = ServerValues.resolveDeferredValueMerge(updates, serverValues);
/*      */     
/*  333 */     final long writeId = getNextWriteId();
/*  334 */     List<? extends Event> events = this.serverSyncTree.applyUserMerge(path, updates, resolved, writeId, true);
/*  335 */     postEvents(events);
/*      */     
/*      */ 
/*  338 */     this.connection.merge(path.toString(), unParsedUpdates, new Firebase.CompletionListener()
/*      */     {
/*      */       public void onComplete(FirebaseError error, Firebase ref)
/*      */       {
/*  342 */         Repo.this.warnIfWriteFailed("updateChildren", path, error);
/*  343 */         Repo.this.ackWriteAndRerunTransactions(writeId, path, error);
/*  344 */         Repo.this.callOnComplete(this.val$onComplete, error, path);
/*      */       }
/*      */       
/*  347 */     });
/*  348 */     Path affectedPath = abortTransactions(path, -9);
/*  349 */     rerunTransactions(affectedPath);
/*      */   }
/*      */   
/*      */   public void purgeOutstandingWrites() {
/*  353 */     if (this.operationLogger.logsDebug()) this.operationLogger.debug("Purging writes");
/*  354 */     List<? extends Event> events = this.serverSyncTree.removeAllWrites();
/*  355 */     postEvents(events);
/*      */     
/*  357 */     abortTransactions(Path.getEmptyPath(), -25);
/*      */     
/*  359 */     this.connection.purgeOutstandingWrites();
/*      */   }
/*      */   
/*      */   public void removeEventCallback(QuerySpec query, EventRegistration eventRegistration)
/*      */   {
/*      */     List<Event> events;
/*      */     List<Event> events;
/*  366 */     if (Constants.DOT_INFO.equals(query.getPath().getFront())) {
/*  367 */       events = this.infoSyncTree.removeEventRegistration(query, eventRegistration);
/*      */     } else {
/*  369 */       events = this.serverSyncTree.removeEventRegistration(query, eventRegistration);
/*      */     }
/*  371 */     postEvents(events);
/*      */   }
/*      */   
/*      */   public void onDisconnectSetValue(final Path path, final Node newValue, final Firebase.CompletionListener onComplete) {
/*  375 */     this.connection.onDisconnectPut(path, newValue.getValue(true), new Firebase.CompletionListener()
/*      */     {
/*      */       public void onComplete(FirebaseError error, Firebase ref) {
/*  378 */         Repo.this.warnIfWriteFailed("onDisconnect().setValue", path, error);
/*  379 */         if (error == null) {
/*  380 */           Repo.this.onDisconnect.remember(path, newValue);
/*      */         }
/*  382 */         Repo.this.callOnComplete(onComplete, error, path);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public void onDisconnectUpdate(final Path path, final Map<ChildKey, Node> newChildren, final Firebase.CompletionListener listener, Map<String, Object> unParsedUpdates)
/*      */   {
/*  389 */     this.connection.onDisconnectMerge(path, unParsedUpdates, new Firebase.CompletionListener()
/*      */     {
/*      */       public void onComplete(FirebaseError error, Firebase ref) {
/*  392 */         Repo.this.warnIfWriteFailed("onDisconnect().updateChildren", path, error);
/*  393 */         if (error == null) {
/*  394 */           for (Map.Entry<ChildKey, Node> entry : newChildren.entrySet()) {
/*  395 */             Repo.this.onDisconnect.remember(path.child((ChildKey)entry.getKey()), (Node)entry.getValue());
/*      */           }
/*      */         }
/*  398 */         Repo.this.callOnComplete(listener, error, path);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public void onDisconnectCancel(final Path path, final Firebase.CompletionListener onComplete) {
/*  404 */     this.connection.onDisconnectCancel(path, new Firebase.CompletionListener()
/*      */     {
/*      */       public void onComplete(FirebaseError error, Firebase ref) {
/*  407 */         if (error == null) {
/*  408 */           Repo.this.onDisconnect.forget(path);
/*      */         }
/*  410 */         Repo.this.callOnComplete(onComplete, error, path);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public void onConnect() {
/*  416 */     onServerInfoUpdate(Constants.DOT_INFO_CONNECTED, Boolean.valueOf(true));
/*      */   }
/*      */   
/*      */   public void onDisconnect() {
/*  420 */     onServerInfoUpdate(Constants.DOT_INFO_CONNECTED, Boolean.valueOf(false));
/*  421 */     runOnDisconnectEvents();
/*      */   }
/*      */   
/*      */   public void onAuthStatus(boolean authOk) {
/*  425 */     onServerInfoUpdate(Constants.DOT_INFO_AUTHENTICATED, Boolean.valueOf(authOk));
/*      */   }
/*      */   
/*      */   public void onServerInfoUpdate(ChildKey key, Object value) {
/*  429 */     updateInfo(key, value);
/*      */   }
/*      */   
/*      */   public void onServerInfoUpdate(Map<ChildKey, Object> updates) {
/*  433 */     for (Map.Entry<ChildKey, Object> entry : updates.entrySet()) {
/*  434 */       updateInfo((ChildKey)entry.getKey(), entry.getValue());
/*      */     }
/*      */   }
/*      */   
/*      */   void interrupt() {
/*  439 */     this.connection.interrupt();
/*      */   }
/*      */   
/*      */   void resume() {
/*  443 */     this.connection.resume();
/*      */   }
/*      */   
/*      */   public void addEventCallback(QuerySpec query, EventRegistration eventRegistration)
/*      */   {
/*  448 */     ChildKey front = query.getPath().getFront();
/*  449 */     List<? extends Event> events; List<? extends Event> events; if ((front != null) && (front.equals(Constants.DOT_INFO))) {
/*  450 */       events = this.infoSyncTree.addEventRegistration(query, eventRegistration);
/*      */     } else {
/*  452 */       events = this.serverSyncTree.addEventRegistration(query, eventRegistration);
/*      */     }
/*  454 */     postEvents(events);
/*      */   }
/*      */   
/*      */   public void keepSynced(QuerySpec query, boolean keep) {
/*  458 */     assert (!query.getPath().getFront().equals(Constants.DOT_INFO));
/*      */     
/*  460 */     this.serverSyncTree.keepSynced(query, keep);
/*      */   }
/*      */   
/*      */   PersistentConnection getConnection() {
/*  464 */     return this.connection;
/*      */   }
/*      */   
/*      */   private void updateInfo(ChildKey childKey, Object value) {
/*  468 */     if (childKey.equals(Constants.DOT_INFO_SERVERTIME_OFFSET)) {
/*  469 */       this.serverClock.setOffset(((Long)value).longValue());
/*      */     }
/*      */     
/*  472 */     Path path = new Path(new ChildKey[] { Constants.DOT_INFO, childKey });
/*      */     try {
/*  474 */       Node node = com.firebase.client.snapshot.NodeUtilities.NodeFromJSON(value);
/*  475 */       this.infoData.update(path, node);
/*  476 */       List<? extends Event> events = this.infoSyncTree.applyServerOverwrite(path, node);
/*  477 */       postEvents(events);
/*      */     } catch (com.firebase.client.FirebaseException e) {
/*  479 */       this.operationLogger.error("Failed to parse info update", e);
/*      */     }
/*      */   }
/*      */   
/*      */   private long getNextWriteId() {
/*  484 */     return this.nextWriteId++;
/*      */   }
/*      */   
/*      */   private void runOnDisconnectEvents() {
/*  488 */     Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
/*  489 */     SparseSnapshotTree resolvedTree = ServerValues.resolveDeferredValueTree(this.onDisconnect, serverValues);
/*  490 */     final List<Event> events = new ArrayList();
/*      */     
/*  492 */     resolvedTree.forEachTree(Path.getEmptyPath(), new SparseSnapshotTree.SparseSnapshotTreeVisitor()
/*      */     {
/*      */       public void visitTree(Path prefixPath, Node node) {
/*  495 */         events.addAll(Repo.this.serverSyncTree.applyServerOverwrite(prefixPath, node));
/*  496 */         Path affectedPath = Repo.this.abortTransactions(prefixPath, -9);
/*  497 */         Repo.this.rerunTransactions(affectedPath);
/*      */       }
/*  499 */     });
/*  500 */     this.onDisconnect = new SparseSnapshotTree();
/*  501 */     postEvents(events);
/*      */   }
/*      */   
/*      */   private void warnIfWriteFailed(String writeType, Path path, FirebaseError error)
/*      */   {
/*  506 */     if ((error != null) && (error.getCode() != -1) && (error.getCode() != -25)) {
/*  507 */       this.operationLogger.warn(writeType + " at " + path.toString() + " failed: " + error.toString());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String TRANSACTION_TOO_MANY_RETRIES = "maxretries";
/*      */   
/*      */ 
/*      */   private static final String TRANSACTION_OVERRIDE_BY_SET = "overriddenBySet";
/*      */   
/*      */ 
/*      */   private static enum TransactionStatus
/*      */   {
/*  522 */     INITIALIZING, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  527 */     RUN, 
/*      */     
/*      */ 
/*  530 */     SENT, 
/*      */     
/*      */ 
/*  533 */     COMPLETED, 
/*      */     
/*      */ 
/*  536 */     SENT_NEEDS_ABORT, 
/*      */     
/*  538 */     NEEDS_ABORT;
/*      */     private TransactionStatus() {} }
/*  540 */   private long transactionOrder = 0L;
/*      */   
/*      */   private static class TransactionData implements Comparable<TransactionData>
/*      */   {
/*      */     private Path path;
/*      */     private Transaction.Handler handler;
/*      */     private com.firebase.client.ValueEventListener outstandingListener;
/*      */     private Repo.TransactionStatus status;
/*      */     private long order;
/*      */     private boolean applyLocally;
/*      */     private int retryCount;
/*      */     private FirebaseError abortReason;
/*      */     private long currentWriteId;
/*      */     private Node currentInputSnapshot;
/*      */     private Node currentOutputSnapshotRaw;
/*      */     private Node currentOutputSnapshotResolved;
/*      */     
/*      */     private TransactionData(Path path, Transaction.Handler handler, com.firebase.client.ValueEventListener outstandingListener, Repo.TransactionStatus status, boolean applyLocally, long order) {
/*  558 */       this.path = path;
/*  559 */       this.handler = handler;
/*  560 */       this.outstandingListener = outstandingListener;
/*  561 */       this.status = status;
/*  562 */       this.retryCount = 0;
/*  563 */       this.applyLocally = applyLocally;
/*  564 */       this.order = order;
/*  565 */       this.abortReason = null;
/*  566 */       this.currentInputSnapshot = null;
/*  567 */       this.currentOutputSnapshotRaw = null;
/*  568 */       this.currentOutputSnapshotResolved = null;
/*      */     }
/*      */     
/*      */     public int compareTo(TransactionData o)
/*      */     {
/*  573 */       if (this.order < o.order)
/*  574 */         return -1;
/*  575 */       if (this.order == o.order) {
/*  576 */         return 0;
/*      */       }
/*  578 */       return 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void startTransaction(Path path, final Transaction.Handler handler, boolean applyLocally)
/*      */   {
/*  585 */     if (this.operationLogger.logsDebug()) this.operationLogger.debug("transaction: " + path);
/*  586 */     if (this.dataLogger.logsDebug()) { this.operationLogger.debug("transaction: " + path);
/*      */     }
/*  588 */     if ((this.ctx.isPersistenceEnabled()) && (!this.loggedTransactionPersistenceWarning)) {
/*  589 */       this.loggedTransactionPersistenceWarning = true;
/*  590 */       this.transactionLogger.info("runTransaction() usage detected while persistence is enabled. Please be aware that transactions *will not* be persisted across app restarts.  See https://www.firebase.com/docs/android/guide/offline-capabilities.html#section-handling-transactions-offline for more details.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  597 */     Firebase watchRef = new Firebase(this, path);
/*  598 */     com.firebase.client.ValueEventListener listener = new com.firebase.client.ValueEventListener()
/*      */     {
/*      */       public void onDataChange(DataSnapshot snapshot) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       public void onCancelled(FirebaseError error) {}
/*  608 */     };
/*  609 */     addEventCallback(watchRef.getSpec(), new ValueEventRegistration(this, listener));
/*      */     
/*  611 */     TransactionData transaction = new TransactionData(path, handler, listener, TransactionStatus.INITIALIZING, applyLocally, nextTransactionOrder(), null);
/*      */     
/*      */ 
/*      */ 
/*  615 */     Node currentState = getLatestState(path);
/*  616 */     transaction.currentInputSnapshot = currentState;
/*  617 */     com.firebase.client.MutableData mutableCurrent = new com.firebase.client.MutableData(currentState);
/*      */     
/*  619 */     FirebaseError error = null;
/*      */     Transaction.Result result;
/*      */     try {
/*  622 */       result = handler.doTransaction(mutableCurrent);
/*  623 */       if (result == null) {
/*  624 */         throw new NullPointerException("Transaction returned null as result");
/*      */       }
/*      */     } catch (Throwable e) {
/*  627 */       error = FirebaseError.fromException(e);
/*  628 */       result = com.firebase.client.Transaction.abort();
/*      */     }
/*  630 */     if (!result.isSuccess())
/*      */     {
/*  632 */       transaction.currentOutputSnapshotRaw = null;
/*  633 */       transaction.currentOutputSnapshotResolved = null;
/*  634 */       final FirebaseError innerClassError = error;
/*  635 */       final DataSnapshot snap = new DataSnapshot(watchRef, com.firebase.client.snapshot.IndexedNode.from(transaction.currentInputSnapshot));
/*  636 */       postEvent(new Runnable()
/*      */       {
/*      */         public void run() {
/*  639 */           handler.onComplete(innerClassError, false, snap);
/*      */         }
/*      */       });
/*      */     }
/*      */     else {
/*  644 */       transaction.status = TransactionStatus.RUN;
/*      */       
/*  646 */       Tree<List<TransactionData>> queueNode = this.transactionQueueTree.subTree(path);
/*  647 */       List<TransactionData> nodeQueue = (List)queueNode.getValue();
/*  648 */       if (nodeQueue == null) {
/*  649 */         nodeQueue = new ArrayList();
/*      */       }
/*  651 */       nodeQueue.add(transaction);
/*  652 */       queueNode.setValue(nodeQueue);
/*      */       
/*  654 */       Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
/*  655 */       Node newNodeUnresolved = result.getNode();
/*  656 */       Node newNode = ServerValues.resolveDeferredValueSnapshot(newNodeUnresolved, serverValues);
/*      */       
/*  658 */       transaction.currentOutputSnapshotRaw = newNodeUnresolved;
/*  659 */       transaction.currentOutputSnapshotResolved = newNode;
/*  660 */       transaction.currentWriteId = getNextWriteId();
/*      */       
/*  662 */       List<? extends Event> events = this.serverSyncTree.applyUserOverwrite(path, newNodeUnresolved, newNode, transaction.currentWriteId, applyLocally, false);
/*      */       
/*  664 */       postEvents(events);
/*  665 */       sendAllReadyTransactions();
/*      */     }
/*      */   }
/*      */   
/*      */   private Node getLatestState(Path path) {
/*  670 */     return getLatestState(path, new ArrayList());
/*      */   }
/*      */   
/*      */   private Node getLatestState(Path path, List<Long> excudeSets) {
/*  674 */     Node state = this.serverSyncTree.calcCompleteEventCache(path, excudeSets);
/*  675 */     if (state == null) {
/*  676 */       state = com.firebase.client.snapshot.EmptyNode.Empty();
/*      */     }
/*  678 */     return state;
/*      */   }
/*      */   
/*      */   public void setHijackHash(boolean hijackHash) {
/*  682 */     this.hijackHash = hijackHash;
/*      */   }
/*      */   
/*      */   private void sendAllReadyTransactions() {
/*  686 */     Tree<List<TransactionData>> node = this.transactionQueueTree;
/*      */     
/*  688 */     pruneCompletedTransactions(node);
/*  689 */     sendReadyTransactions(node);
/*      */   }
/*      */   
/*      */   private void sendReadyTransactions(Tree<List<TransactionData>> node) {
/*  693 */     List<TransactionData> queue = (List)node.getValue();
/*  694 */     if (queue != null) {
/*  695 */       queue = buildTransactionQueue(node);
/*  696 */       assert (queue.size() > 0);
/*      */       
/*  698 */       Boolean allRun = Boolean.valueOf(true);
/*  699 */       for (TransactionData transaction : queue) {
/*  700 */         if (transaction.status != TransactionStatus.RUN) {
/*  701 */           allRun = Boolean.valueOf(false);
/*  702 */           break;
/*      */         }
/*      */       }
/*      */       
/*  706 */       if (allRun.booleanValue()) {
/*  707 */         sendTransactionQueue(queue, node.getPath());
/*      */       }
/*  709 */     } else if (node.hasChildren()) {
/*  710 */       node.forEachChild(new com.firebase.client.core.utilities.Tree.TreeVisitor()
/*      */       {
/*      */         public void visitTree(Tree<List<Repo.TransactionData>> tree) {
/*  713 */           Repo.this.sendReadyTransactions(tree);
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */   
/*      */   private void sendTransactionQueue(final List<TransactionData> queue, final Path path)
/*      */   {
/*  721 */     List<Long> setsToIgnore = new ArrayList();
/*  722 */     for (TransactionData txn : queue) {
/*  723 */       setsToIgnore.add(Long.valueOf(txn.currentWriteId));
/*      */     }
/*      */     
/*  726 */     Node latestState = getLatestState(path, setsToIgnore);
/*  727 */     Node snapToSend = latestState;
/*  728 */     String latestHash = "badhash";
/*  729 */     if (!this.hijackHash) {
/*  730 */       latestHash = latestState.getHash();
/*      */     }
/*      */     
/*  733 */     for (TransactionData txn : queue) {
/*  734 */       assert (txn.status == TransactionStatus.RUN);
/*  735 */       txn.status = TransactionStatus.SENT;
/*  736 */       TransactionData.access$1808(txn);
/*  737 */       Path relativePath = Path.getRelative(path, txn.path);
/*      */       
/*  739 */       snapToSend = snapToSend.updateChild(relativePath, txn.currentOutputSnapshotRaw);
/*      */     }
/*      */     
/*  742 */     Object dataToSend = snapToSend.getValue(true);
/*      */     
/*  744 */     final Repo repo = this;
/*  745 */     long writeId = getNextWriteId();
/*      */     
/*      */ 
/*  748 */     this.connection.put(path.toString(), dataToSend, latestHash, new Firebase.CompletionListener()
/*      */     {
/*      */       public void onComplete(FirebaseError error, Firebase ref) {
/*  751 */         Repo.this.warnIfWriteFailed("Transaction", path, error);
/*  752 */         List<Event> events = new ArrayList();
/*      */         
/*  754 */         if (error == null) {
/*  755 */           List<Runnable> callbacks = new ArrayList();
/*  756 */           for (final Repo.TransactionData txn : queue) {
/*  757 */             txn.status = Repo.TransactionStatus.COMPLETED;
/*  758 */             events.addAll(Repo.this.serverSyncTree.ackUserWrite(txn.currentWriteId, false, false, Repo.this.serverClock));
/*      */             
/*      */ 
/*  761 */             Node node = txn.currentOutputSnapshotResolved;
/*  762 */             final DataSnapshot snap = new DataSnapshot(new Firebase(repo, txn.path), com.firebase.client.snapshot.IndexedNode.from(node));
/*      */             
/*  764 */             callbacks.add(new Runnable()
/*      */             {
/*      */               public void run() {
/*  767 */                 txn.handler.onComplete(null, true, snap);
/*      */               }
/*      */               
/*  770 */             });
/*  771 */             Repo.this.removeEventCallback(QuerySpec.defaultQueryAtPath(txn.path), new ValueEventRegistration(Repo.this, txn.outstandingListener));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  776 */           Repo.this.pruneCompletedTransactions(Repo.this.transactionQueueTree.subTree(path));
/*      */           
/*      */ 
/*  779 */           Repo.this.sendAllReadyTransactions();
/*      */           
/*  781 */           repo.postEvents(events);
/*      */           
/*      */ 
/*  784 */           for (int i = 0; i < callbacks.size(); i++) {
/*  785 */             Repo.this.postEvent((Runnable)callbacks.get(i));
/*      */           }
/*      */         }
/*      */         else {
/*  789 */           if (error.getCode() == -1) {
/*  790 */             for (Repo.TransactionData transaction : queue) {
/*  791 */               if (transaction.status == Repo.TransactionStatus.SENT_NEEDS_ABORT) {
/*  792 */                 transaction.status = Repo.TransactionStatus.NEEDS_ABORT;
/*      */               } else {
/*  794 */                 transaction.status = Repo.TransactionStatus.RUN;
/*      */               }
/*      */             }
/*      */           } else {
/*  798 */             for (Repo.TransactionData transaction : queue) {
/*  799 */               transaction.status = Repo.TransactionStatus.NEEDS_ABORT;
/*  800 */               transaction.abortReason = error;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  805 */           Repo.this.rerunTransactions(path);
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   private void pruneCompletedTransactions(Tree<List<TransactionData>> node) {
/*  812 */     List<TransactionData> queue = (List)node.getValue();
/*  813 */     if (queue != null) {
/*  814 */       int i = 0;
/*  815 */       while (i < queue.size()) {
/*  816 */         TransactionData transaction = (TransactionData)queue.get(i);
/*  817 */         if (transaction.status == TransactionStatus.COMPLETED) {
/*  818 */           queue.remove(i);
/*      */         } else {
/*  820 */           i++;
/*      */         }
/*      */       }
/*  823 */       if (queue.size() > 0) {
/*  824 */         node.setValue(queue);
/*      */       } else {
/*  826 */         node.setValue(null);
/*      */       }
/*      */     }
/*      */     
/*  830 */     node.forEachChild(new com.firebase.client.core.utilities.Tree.TreeVisitor()
/*      */     {
/*      */       public void visitTree(Tree<List<Repo.TransactionData>> tree) {
/*  833 */         Repo.this.pruneCompletedTransactions(tree);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   private long nextTransactionOrder() {
/*  839 */     return this.transactionOrder++;
/*      */   }
/*      */   
/*      */   private Path rerunTransactions(Path changedPath) {
/*  843 */     Tree<List<TransactionData>> rootMostTransactionNode = getAncestorTransactionNode(changedPath);
/*  844 */     Path path = rootMostTransactionNode.getPath();
/*      */     
/*  846 */     List<TransactionData> queue = buildTransactionQueue(rootMostTransactionNode);
/*  847 */     rerunTransactionQueue(queue, path);
/*      */     
/*  849 */     return path;
/*      */   }
/*      */   
/*      */   private void rerunTransactionQueue(List<TransactionData> queue, Path path) {
/*  853 */     if (queue.isEmpty()) {
/*  854 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  859 */     List<Runnable> callbacks = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  864 */     List<Long> setsToIgnore = new ArrayList();
/*  865 */     for (TransactionData transaction : queue) {
/*  866 */       setsToIgnore.add(Long.valueOf(transaction.currentWriteId));
/*      */     }
/*      */     
/*  869 */     for (final TransactionData transaction : queue) {
/*  870 */       Path relativePath = Path.getRelative(path, transaction.path);
/*  871 */       boolean abortTransaction = false;
/*  872 */       FirebaseError abortReason = null;
/*  873 */       List<Event> events = new ArrayList();
/*      */       
/*  875 */       assert (relativePath != null);
/*      */       
/*  877 */       if (transaction.status == TransactionStatus.NEEDS_ABORT) {
/*  878 */         abortTransaction = true;
/*  879 */         abortReason = transaction.abortReason;
/*  880 */         if (abortReason.getCode() != -25) {
/*  881 */           events.addAll(this.serverSyncTree.ackUserWrite(transaction.currentWriteId, true, false, this.serverClock));
/*      */         }
/*  883 */       } else if (transaction.status == TransactionStatus.RUN) {
/*  884 */         if (transaction.retryCount >= 25) {
/*  885 */           abortTransaction = true;
/*  886 */           abortReason = FirebaseError.fromStatus("maxretries");
/*  887 */           events.addAll(this.serverSyncTree.ackUserWrite(transaction.currentWriteId, true, false, this.serverClock));
/*      */         }
/*      */         else {
/*  890 */           Node currentNode = getLatestState(transaction.path, setsToIgnore);
/*  891 */           transaction.currentInputSnapshot = currentNode;
/*  892 */           com.firebase.client.MutableData mutableCurrent = new com.firebase.client.MutableData(currentNode);
/*  893 */           FirebaseError error = null;
/*      */           Transaction.Result result;
/*      */           try {
/*  896 */             result = transaction.handler.doTransaction(mutableCurrent);
/*      */           } catch (Throwable e) {
/*  898 */             error = FirebaseError.fromException(e);
/*  899 */             result = com.firebase.client.Transaction.abort();
/*      */           }
/*  901 */           if (result.isSuccess()) {
/*  902 */             Long oldWriteId = Long.valueOf(transaction.currentWriteId);
/*  903 */             Map<String, Object> serverValues = ServerValues.generateServerValues(this.serverClock);
/*      */             
/*  905 */             Node newDataNode = result.getNode();
/*  906 */             Node newNodeResolved = ServerValues.resolveDeferredValueSnapshot(newDataNode, serverValues);
/*      */             
/*  908 */             transaction.currentOutputSnapshotRaw = newDataNode;
/*  909 */             transaction.currentOutputSnapshotResolved = newNodeResolved;
/*  910 */             transaction.currentWriteId = getNextWriteId();
/*      */             
/*      */ 
/*  913 */             setsToIgnore.remove(oldWriteId);
/*  914 */             events.addAll(this.serverSyncTree.applyUserOverwrite(transaction.path, newDataNode, newNodeResolved, transaction.currentWriteId, transaction.applyLocally, false));
/*      */             
/*  916 */             events.addAll(this.serverSyncTree.ackUserWrite(oldWriteId.longValue(), true, false, this.serverClock));
/*      */           }
/*      */           else {
/*  919 */             abortTransaction = true;
/*  920 */             abortReason = error;
/*  921 */             events.addAll(this.serverSyncTree.ackUserWrite(transaction.currentWriteId, true, false, this.serverClock));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  926 */       postEvents(events);
/*      */       
/*  928 */       if (abortTransaction)
/*      */       {
/*  930 */         transaction.status = TransactionStatus.COMPLETED;
/*  931 */         Firebase ref = new Firebase(this, transaction.path);
/*      */         
/*      */ 
/*  934 */         Node lastInput = transaction.currentInputSnapshot;
/*      */         
/*  936 */         final DataSnapshot snapshot = new DataSnapshot(ref, com.firebase.client.snapshot.IndexedNode.from(lastInput));
/*      */         
/*      */ 
/*      */ 
/*  940 */         scheduleNow(new Runnable()
/*      */         {
/*      */           public void run() {
/*  943 */             Repo.this.removeEventCallback(QuerySpec.defaultQueryAtPath(transaction.path), new ValueEventRegistration(Repo.this, transaction.outstandingListener));
/*      */           }
/*      */           
/*      */ 
/*  947 */         });
/*  948 */         final FirebaseError callbackError = abortReason;
/*  949 */         callbacks.add(new Runnable()
/*      */         {
/*      */           public void run() {
/*  952 */             transaction.handler.onComplete(callbackError, false, snapshot);
/*      */           }
/*      */         });
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  959 */     pruneCompletedTransactions(this.transactionQueueTree);
/*      */     
/*      */ 
/*  962 */     for (int i = 0; i < callbacks.size(); i++) {
/*  963 */       postEvent((Runnable)callbacks.get(i));
/*      */     }
/*      */     
/*      */ 
/*  967 */     sendAllReadyTransactions();
/*      */   }
/*      */   
/*      */   private Tree<List<TransactionData>> getAncestorTransactionNode(Path path) {
/*  971 */     Tree<List<TransactionData>> transactionNode = this.transactionQueueTree;
/*  972 */     while ((!path.isEmpty()) && (transactionNode.getValue() == null)) {
/*  973 */       transactionNode = transactionNode.subTree(new Path(new ChildKey[] { path.getFront() }));
/*  974 */       path = path.popFront();
/*      */     }
/*      */     
/*  977 */     return transactionNode;
/*      */   }
/*      */   
/*      */   private List<TransactionData> buildTransactionQueue(Tree<List<TransactionData>> transactionNode) {
/*  981 */     List<TransactionData> queue = new ArrayList();
/*  982 */     aggregateTransactionQueues(queue, transactionNode);
/*      */     
/*  984 */     java.util.Collections.sort(queue);
/*      */     
/*  986 */     return queue;
/*      */   }
/*      */   
/*      */   private void aggregateTransactionQueues(final List<TransactionData> queue, Tree<List<TransactionData>> node) {
/*  990 */     List<TransactionData> childQueue = (List)node.getValue();
/*  991 */     if (childQueue != null) {
/*  992 */       queue.addAll(childQueue);
/*      */     }
/*      */     
/*  995 */     node.forEachChild(new com.firebase.client.core.utilities.Tree.TreeVisitor()
/*      */     {
/*      */       public void visitTree(Tree<List<Repo.TransactionData>> tree) {
/*  998 */         Repo.this.aggregateTransactionQueues(queue, tree);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   private Path abortTransactions(Path path, final int reason) {
/* 1004 */     Path affectedPath = getAncestorTransactionNode(path).getPath();
/*      */     
/* 1006 */     if (this.transactionLogger.logsDebug()) { this.operationLogger.debug("Aborting transactions for path: " + path + ". Affected: " + affectedPath);
/*      */     }
/* 1008 */     Tree<List<TransactionData>> transactionNode = this.transactionQueueTree.subTree(path);
/* 1009 */     transactionNode.forEachAncestor(new com.firebase.client.core.utilities.Tree.TreeFilter()
/*      */     {
/*      */       public boolean filterTreeNode(Tree<List<Repo.TransactionData>> tree) {
/* 1012 */         Repo.this.abortTransactionsAtNode(tree, reason);
/* 1013 */         return false;
/*      */       }
/*      */       
/* 1016 */     });
/* 1017 */     abortTransactionsAtNode(transactionNode, reason);
/*      */     
/* 1019 */     transactionNode.forEachDescendant(new com.firebase.client.core.utilities.Tree.TreeVisitor()
/*      */     {
/*      */       public void visitTree(Tree<List<Repo.TransactionData>> tree) {
/* 1022 */         Repo.this.abortTransactionsAtNode(tree, reason);
/*      */       }
/*      */       
/* 1025 */     });
/* 1026 */     return affectedPath;
/*      */   }
/*      */   
/*      */   private void abortTransactionsAtNode(Tree<List<TransactionData>> node, int reason) {
/* 1030 */     List<TransactionData> queue = (List)node.getValue();
/* 1031 */     List<Event> events = new ArrayList();
/*      */     
/* 1033 */     if (queue != null) {
/* 1034 */       List<Runnable> callbacks = new ArrayList();
/*      */       FirebaseError abortError;
/* 1036 */       final FirebaseError abortError; if (reason == -9) {
/* 1037 */         abortError = FirebaseError.fromStatus("overriddenBySet");
/*      */       } else {
/* 1039 */         com.firebase.client.utilities.Utilities.hardAssert(reason == -25, "Unknown transaction abort reason: " + reason);
/* 1040 */         abortError = FirebaseError.fromCode(-25);
/*      */       }
/*      */       
/* 1043 */       int lastSent = -1;
/* 1044 */       for (int i = 0; i < queue.size(); i++) {
/* 1045 */         final TransactionData transaction = (TransactionData)queue.get(i);
/* 1046 */         if (transaction.status != TransactionStatus.SENT_NEEDS_ABORT)
/*      */         {
/* 1048 */           if (transaction.status == TransactionStatus.SENT) {
/* 1049 */             assert (lastSent == i - 1);
/* 1050 */             lastSent = i;
/*      */             
/* 1052 */             transaction.status = TransactionStatus.SENT_NEEDS_ABORT;
/* 1053 */             transaction.abortReason = abortError;
/*      */           } else {
/* 1055 */             assert (transaction.status == TransactionStatus.RUN);
/*      */             
/* 1057 */             removeEventCallback(QuerySpec.defaultQueryAtPath(transaction.path), new ValueEventRegistration(this, transaction.outstandingListener));
/*      */             
/* 1059 */             if (reason == -9) {
/* 1060 */               events.addAll(this.serverSyncTree.ackUserWrite(transaction.currentWriteId, true, false, this.serverClock));
/*      */             } else {
/* 1062 */               com.firebase.client.utilities.Utilities.hardAssert(reason == -25, "Unknown transaction abort reason: " + reason);
/*      */             }
/*      */             
/* 1065 */             callbacks.add(new Runnable()
/*      */             {
/*      */               public void run() {
/* 1068 */                 transaction.handler.onComplete(abortError, false, null);
/*      */               }
/*      */             });
/*      */           }
/*      */         }
/*      */       }
/* 1074 */       if (lastSent == -1)
/*      */       {
/* 1076 */         node.setValue(null);
/*      */       }
/*      */       else {
/* 1079 */         node.setValue(queue.subList(0, lastSent + 1));
/*      */       }
/*      */       
/*      */ 
/* 1083 */       postEvents(events);
/* 1084 */       for (Runnable r : callbacks) {
/* 1085 */         postEvent(r);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/Repo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */