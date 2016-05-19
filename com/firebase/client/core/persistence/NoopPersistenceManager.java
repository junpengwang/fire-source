/*     */ package com.firebase.client.core.persistence;
/*     */ 
/*     */ import com.firebase.client.core.CompoundWrite;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.UserWriteRecord;
/*     */ import com.firebase.client.core.view.CacheNode;
/*     */ import com.firebase.client.core.view.QuerySpec;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.EmptyNode;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ public class NoopPersistenceManager
/*     */   implements PersistenceManager
/*     */ {
/*  22 */   private boolean insideTransaction = false;
/*     */   
/*     */   public void saveUserOverwrite(Path path, Node node, long writeId)
/*     */   {
/*  26 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void saveUserMerge(Path path, CompoundWrite children, long writeId)
/*     */   {
/*  31 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void removeUserWrite(long writeId)
/*     */   {
/*  36 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void removeAllUserWrites()
/*     */   {
/*  41 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void applyUserWriteToServerCache(Path path, Node node)
/*     */   {
/*  46 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void applyUserWriteToServerCache(Path path, CompoundWrite merge)
/*     */   {
/*  51 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public List<UserWriteRecord> loadUserWrites()
/*     */   {
/*  56 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public CacheNode serverCache(QuerySpec query)
/*     */   {
/*  61 */     return new CacheNode(IndexedNode.from(EmptyNode.Empty(), query.getIndex()), false, false);
/*     */   }
/*     */   
/*     */   public void updateServerCache(QuerySpec query, Node node)
/*     */   {
/*  66 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void updateServerCache(Path path, CompoundWrite children)
/*     */   {
/*  71 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void setQueryActive(QuerySpec query)
/*     */   {
/*  76 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void setQueryInactive(QuerySpec query) {
/*  80 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void setQueryComplete(QuerySpec query)
/*     */   {
/*  85 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void setTrackedQueryKeys(QuerySpec query, Set<ChildKey> keys)
/*     */   {
/*  90 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */   public void updateTrackedQueryKeys(QuerySpec query, Set<ChildKey> added, Set<ChildKey> removed)
/*     */   {
/*  95 */     verifyInsideTransaction();
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T runInTransaction(Callable<T> callable)
/*     */   {
/* 101 */     Utilities.hardAssert(!this.insideTransaction, "runInTransaction called when an existing transaction is already in progress.");
/* 102 */     this.insideTransaction = true;
/*     */     try {
/* 104 */       return (T)callable.call();
/*     */     } catch (Throwable e) {
/* 106 */       throw new RuntimeException(e);
/*     */     } finally {
/* 108 */       this.insideTransaction = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void verifyInsideTransaction() {
/* 113 */     Utilities.hardAssert(this.insideTransaction, "Transaction expected to already be in progress.");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/persistence/NoopPersistenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */