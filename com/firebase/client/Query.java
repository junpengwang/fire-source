/*     */ package com.firebase.client;
/*     */ 
/*     */ import com.firebase.client.core.ChildEventRegistration;
/*     */ import com.firebase.client.core.EventRegistration;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.Repo;
/*     */ import com.firebase.client.core.ValueEventRegistration;
/*     */ import com.firebase.client.core.view.QueryParams;
/*     */ import com.firebase.client.core.view.QuerySpec;
/*     */ import com.firebase.client.snapshot.BooleanNode;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.DoubleNode;
/*     */ import com.firebase.client.snapshot.EmptyNode;
/*     */ import com.firebase.client.snapshot.KeyIndex;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.snapshot.PriorityIndex;
/*     */ import com.firebase.client.snapshot.PriorityUtilities;
/*     */ import com.firebase.client.snapshot.StringNode;
/*     */ import com.firebase.client.snapshot.SubKeyIndex;
/*     */ import com.firebase.client.utilities.Validation;
/*     */ 
/*     */ public class Query
/*     */ {
/*     */   protected final Repo repo;
/*     */   protected final Path path;
/*     */   protected final QueryParams params;
/*     */   private final boolean orderByCalled;
/*     */   
/*     */   Query(Repo repo, Path path, QueryParams params, boolean orderByCalled) throws FirebaseException
/*     */   {
/*  31 */     this.repo = repo;
/*  32 */     this.path = path;
/*  33 */     this.params = params;
/*  34 */     this.orderByCalled = orderByCalled;
/*  35 */     if (!params.isValid()) {
/*  36 */       throw new FirebaseException("Validation of queries failed. Please report to support@firebase.com");
/*     */     }
/*     */   }
/*     */   
/*     */   Query(Repo repo, Path path) {
/*  41 */     this.repo = repo;
/*  42 */     this.path = path;
/*  43 */     this.params = QueryParams.DEFAULT_PARAMS;
/*  44 */     this.orderByCalled = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void validateQueryEndpoints(QueryParams params)
/*     */   {
/*  51 */     if (params.getIndex().equals(KeyIndex.getInstance())) {
/*  52 */       String message = "You must use startAt(String value), endAt(String value) or equalTo(String value) in combination with orderByKey(). Other type of values or using the version with 2 parameters is not supported";
/*     */       
/*     */ 
/*  55 */       if (params.hasStart()) {
/*  56 */         Node startNode = params.getIndexStartValue();
/*  57 */         ChildKey startName = params.getIndexStartName();
/*  58 */         if ((startName != ChildKey.getMinName()) || (!(startNode instanceof StringNode))) {
/*  59 */           throw new IllegalArgumentException(message);
/*     */         }
/*     */       }
/*  62 */       if (params.hasEnd()) {
/*  63 */         Node endNode = params.getIndexEndValue();
/*  64 */         ChildKey endName = params.getIndexEndName();
/*  65 */         if ((endName != ChildKey.getMaxName()) || (!(endNode instanceof StringNode))) {
/*  66 */           throw new IllegalArgumentException(message);
/*     */         }
/*     */       }
/*  69 */     } else if ((params.getIndex().equals(PriorityIndex.getInstance())) && (
/*  70 */       ((params.hasStart()) && (!PriorityUtilities.isValidPriority(params.getIndexStartValue()))) || ((params.hasEnd()) && (!PriorityUtilities.isValidPriority(params.getIndexEndValue())))))
/*     */     {
/*  72 */       throw new IllegalArgumentException("When using orderByPriority(), values provided to startAt(), endAt(), or equalTo() must be valid priorities.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void validateLimit(QueryParams params)
/*     */   {
/*  82 */     if ((params.hasStart()) && (params.hasEnd()) && (params.hasLimit()) && (!params.hasAnchoredLimit())) {
/*  83 */       throw new IllegalArgumentException("Can't combine startAt(), endAt() and limit(). Use limitToFirst() or limitToLast() instead");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void validateEqualToCall()
/*     */   {
/*  92 */     if (this.params.hasStart()) {
/*  93 */       throw new IllegalArgumentException("Can't call equalTo() and startAt() combined");
/*     */     }
/*  95 */     if (this.params.hasEnd()) {
/*  96 */       throw new IllegalArgumentException("Can't call equalTo() and endAt() combined");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void validateNoOrderByCall()
/*     */   {
/* 104 */     if (this.orderByCalled) {
/* 105 */       throw new IllegalArgumentException("You can't combine multiple orderBy calls!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueEventListener addValueEventListener(ValueEventListener listener)
/*     */   {
/* 118 */     addEventCallback(new ValueEventRegistration(this.repo, listener));
/* 119 */     return listener;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChildEventListener addChildEventListener(ChildEventListener listener)
/*     */   {
/* 129 */     addEventCallback(new ChildEventRegistration(this.repo, listener));
/* 130 */     return listener;
/*     */   }
/*     */   
/*     */   private static class SingleEventProgress {
/* 134 */     private boolean called = false;
/*     */     
/*     */     public boolean hasBeenCalled() {
/* 137 */       return this.called;
/*     */     }
/*     */     
/*     */     public void setCalled() {
/* 141 */       this.called = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addListenerForSingleValueEvent(final ValueEventListener listener)
/*     */   {
/* 151 */     final SingleEventProgress progress = new SingleEventProgress(null);
/* 152 */     addEventCallback(new ValueEventRegistration(this.repo, new ValueEventListener()
/*     */     {
/*     */       public void onDataChange(DataSnapshot snapshot) {
/* 155 */         if (!progress.hasBeenCalled()) {
/* 156 */           progress.setCalled();
/* 157 */           Query.this.removeEventListener(this);
/* 158 */           listener.onDataChange(snapshot);
/*     */         }
/*     */       }
/*     */       
/*     */       public void onCancelled(FirebaseError error)
/*     */       {
/* 164 */         listener.onCancelled(error);
/*     */       }
/*     */     }));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeEventListener(final ValueEventListener listener)
/*     */   {
/* 174 */     if (listener == null) {
/* 175 */       throw new NullPointerException("listener must not be null");
/*     */     }
/* 177 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 181 */         Query.this.repo.removeEventCallback(Query.this.getSpec(), new ValueEventRegistration(Query.this.repo, listener));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeEventListener(final ChildEventListener listener)
/*     */   {
/* 191 */     if (listener == null) {
/* 192 */       throw new NullPointerException("listener must not be null");
/*     */     }
/* 194 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 197 */         Query.this.repo.removeEventCallback(Query.this.getSpec(), new ChildEventRegistration(Query.this.repo, listener));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void keepSynced(final boolean keepSynced)
/*     */   {
/* 211 */     if (this.path.getFront().equals(ChildKey.getInfoKey())) {
/* 212 */       throw new FirebaseException("Can't call keepSynced() on .info paths.");
/*     */     }
/*     */     
/* 215 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 218 */         Query.this.repo.keepSynced(Query.this.getSpec(), keepSynced);
/*     */       }
/*     */     });
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
/*     */   public Query startAt()
/*     */   {
/* 242 */     return startAt(EmptyNode.Empty(), null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query startAt(String value)
/*     */   {
/* 252 */     return startAt(value, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query startAt(double value)
/*     */   {
/* 262 */     return startAt(value, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query startAt(boolean value)
/*     */   {
/* 273 */     return startAt(value, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query startAt(String value, String key)
/*     */   {
/* 285 */     Node node = value != null ? new StringNode(value, PriorityUtilities.NullPriority()) : EmptyNode.Empty();
/* 286 */     return startAt(node, key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query startAt(double value, String key)
/*     */   {
/* 298 */     return startAt(new DoubleNode(Double.valueOf(value), PriorityUtilities.NullPriority()), key);
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
/*     */   public Query startAt(boolean value, String key)
/*     */   {
/* 311 */     return startAt(new BooleanNode(Boolean.valueOf(value), PriorityUtilities.NullPriority()), key);
/*     */   }
/*     */   
/*     */   private Query startAt(Node node, String key) {
/* 315 */     Validation.validateNullableKey(key);
/* 316 */     if ((!node.isLeafNode()) && (!node.isEmpty())) {
/* 317 */       throw new IllegalArgumentException("Can only use simple values for startAt()");
/*     */     }
/* 319 */     if (this.params.hasStart()) {
/* 320 */       throw new IllegalArgumentException("Can't call startAt() or equalTo() multiple times");
/*     */     }
/* 322 */     ChildKey childKey = key != null ? ChildKey.fromString(key) : null;
/* 323 */     QueryParams newParams = this.params.startAt(node, childKey);
/* 324 */     validateLimit(newParams);
/* 325 */     validateQueryEndpoints(newParams);
/* 326 */     assert (newParams.isValid());
/* 327 */     return new Query(this.repo, this.path, newParams, this.orderByCalled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query endAt()
/*     */   {
/* 336 */     return endAt(Node.MAX_NODE, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query endAt(String value)
/*     */   {
/* 346 */     return endAt(value, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query endAt(double value)
/*     */   {
/* 356 */     return endAt(value, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query endAt(boolean value)
/*     */   {
/* 367 */     return endAt(value, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query endAt(String value, String key)
/*     */   {
/* 379 */     Node node = value != null ? new StringNode(value, PriorityUtilities.NullPriority()) : EmptyNode.Empty();
/* 380 */     return endAt(node, key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query endAt(double value, String key)
/*     */   {
/* 392 */     return endAt(new DoubleNode(Double.valueOf(value), PriorityUtilities.NullPriority()), key);
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
/*     */   public Query endAt(boolean value, String key)
/*     */   {
/* 405 */     return endAt(new BooleanNode(Boolean.valueOf(value), PriorityUtilities.NullPriority()), key);
/*     */   }
/*     */   
/*     */   private Query endAt(Node node, String key) {
/* 409 */     Validation.validateNullableKey(key);
/* 410 */     if ((!node.isLeafNode()) && (!node.isEmpty())) {
/* 411 */       throw new IllegalArgumentException("Can only use simple values for endAt()");
/*     */     }
/* 413 */     ChildKey childKey = key != null ? ChildKey.fromString(key) : null;
/* 414 */     if (this.params.hasEnd()) {
/* 415 */       throw new IllegalArgumentException("Can't call endAt() or equalTo() multiple times");
/*     */     }
/* 417 */     QueryParams newParams = this.params.endAt(node, childKey);
/* 418 */     validateLimit(newParams);
/* 419 */     validateQueryEndpoints(newParams);
/* 420 */     assert (newParams.isValid());
/* 421 */     return new Query(this.repo, this.path, newParams, this.orderByCalled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query equalTo(String value)
/*     */   {
/* 430 */     validateEqualToCall();
/* 431 */     return startAt(value).endAt(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query equalTo(double value)
/*     */   {
/* 440 */     validateEqualToCall();
/* 441 */     return startAt(value).endAt(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query equalTo(boolean value)
/*     */   {
/* 451 */     validateEqualToCall();
/* 452 */     return startAt(value).endAt(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query equalTo(String value, String key)
/*     */   {
/* 463 */     validateEqualToCall();
/* 464 */     return startAt(value, key).endAt(value, key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query equalTo(double value, String key)
/*     */   {
/* 475 */     validateEqualToCall();
/* 476 */     return startAt(value, key).endAt(value, key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query equalTo(boolean value, String key)
/*     */   {
/* 487 */     validateEqualToCall();
/* 488 */     return startAt(value, key).endAt(value, key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Query limit(int limit)
/*     */   {
/* 499 */     if (limit <= 0) {
/* 500 */       throw new IllegalArgumentException("Limit must be a positive integer!");
/*     */     }
/* 502 */     if (this.params.hasLimit()) {
/* 503 */       throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
/*     */     }
/* 505 */     QueryParams newParams = this.params.limit(limit);
/* 506 */     validateLimit(newParams);
/* 507 */     return new Query(this.repo, this.path, newParams, this.orderByCalled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query limitToFirst(int limit)
/*     */   {
/* 517 */     if (limit <= 0) {
/* 518 */       throw new IllegalArgumentException("Limit must be a positive integer!");
/*     */     }
/* 520 */     if (this.params.hasLimit()) {
/* 521 */       throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
/*     */     }
/* 523 */     return new Query(this.repo, this.path, this.params.limitToFirst(limit), this.orderByCalled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query limitToLast(int limit)
/*     */   {
/* 533 */     if (limit <= 0) {
/* 534 */       throw new IllegalArgumentException("Limit must be a positive integer!");
/*     */     }
/* 536 */     if (this.params.hasLimit()) {
/* 537 */       throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
/*     */     }
/* 539 */     return new Query(this.repo, this.path, this.params.limitToLast(limit), this.orderByCalled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query orderByChild(String childKey)
/*     */   {
/* 549 */     if (childKey == null) {
/* 550 */       throw new NullPointerException("Key can't be null");
/*     */     }
/* 552 */     if ((childKey.equals("$key")) || (childKey.equals(".key"))) {
/* 553 */       throw new IllegalArgumentException("Can't use '" + childKey + "' as childKey, please use orderByKey() instead!");
/*     */     }
/* 555 */     if ((childKey.equals("$priority")) || (childKey.equals(".priority"))) {
/* 556 */       throw new IllegalArgumentException("Can't use '" + childKey + "' as childKey, please use orderByPriority() instead!");
/*     */     }
/* 558 */     if ((childKey.equals("$value")) || (childKey.equals(".value"))) {
/* 559 */       throw new IllegalArgumentException("Can't use '" + childKey + "' as childKey, please use orderByValue() instead!");
/*     */     }
/* 561 */     Validation.validateNullableKey(childKey);
/* 562 */     validateNoOrderByCall();
/* 563 */     ChildKey childName = ChildKey.fromString(childKey);
/* 564 */     com.firebase.client.snapshot.Index index = new SubKeyIndex(childName);
/* 565 */     return new Query(this.repo, this.path, this.params.orderBy(index), true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query orderByPriority()
/*     */   {
/* 574 */     validateNoOrderByCall();
/* 575 */     QueryParams newParams = this.params.orderBy(PriorityIndex.getInstance());
/* 576 */     validateQueryEndpoints(newParams);
/* 577 */     return new Query(this.repo, this.path, newParams, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query orderByKey()
/*     */   {
/* 586 */     validateNoOrderByCall();
/* 587 */     QueryParams newParams = this.params.orderBy(KeyIndex.getInstance());
/* 588 */     validateQueryEndpoints(newParams);
/* 589 */     return new Query(this.repo, this.path, newParams, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Query orderByValue()
/*     */   {
/* 598 */     validateNoOrderByCall();
/* 599 */     return new Query(this.repo, this.path, this.params.orderBy(com.firebase.client.snapshot.ValueIndex.getInstance()), true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Firebase getRef()
/*     */   {
/* 606 */     return new Firebase(this.repo, getPath());
/*     */   }
/*     */   
/*     */   private void addEventCallback(final EventRegistration listener) {
/* 610 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 614 */         Query.this.repo.addEventCallback(Query.this.getSpec(), listener);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Path getPath()
/*     */   {
/* 626 */     return this.path;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Repo getRepo()
/*     */   {
/* 634 */     return this.repo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QuerySpec getSpec()
/*     */   {
/* 642 */     return new QuerySpec(this.path, this.params);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/Query.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */