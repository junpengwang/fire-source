/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.core.view.CacheNode;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.Index;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import java.util.Collections;
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
/*     */ public class WriteTreeRef
/*     */ {
/*     */   private final Path treePath;
/*     */   private final WriteTree writeTree;
/*     */   
/*     */   public WriteTreeRef(Path path, WriteTree writeTree)
/*     */   {
/*  32 */     this.treePath = path;
/*  33 */     this.writeTree = writeTree;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node calcCompleteEventCache(Node completeServerCache)
/*     */   {
/*  42 */     return calcCompleteEventCache(completeServerCache, Collections.emptyList());
/*     */   }
/*     */   
/*     */   public Node calcCompleteEventCache(Node completeServerCache, List<Long> writeIdsToExclude) {
/*  46 */     return calcCompleteEventCache(completeServerCache, writeIdsToExclude, false);
/*     */   }
/*     */   
/*     */   public Node calcCompleteEventCache(Node completeServerCache, List<Long> writeIdsToExclude, boolean includeHiddenWrites)
/*     */   {
/*  51 */     return this.writeTree.calcCompleteEventCache(this.treePath, completeServerCache, writeIdsToExclude, includeHiddenWrites);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node calcCompleteEventChildren(Node completeServerChildren)
/*     */   {
/*  60 */     return this.writeTree.calcCompleteEventChildren(this.treePath, completeServerChildren);
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
/*     */   public Node calcEventCacheAfterServerOverwrite(Path path, Node existingEventSnap, Node existingServerSnap)
/*     */   {
/*  78 */     return this.writeTree.calcEventCacheAfterServerOverwrite(this.treePath, path, existingEventSnap, existingServerSnap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node shadowingWrite(Path path)
/*     */   {
/*  88 */     return this.writeTree.shadowingWrite(this.treePath.child(path));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NamedNode calcNextNodeAfterPost(Node completeServerData, NamedNode startPost, boolean reverse, Index index)
/*     */   {
/*  96 */     return this.writeTree.calcNextNodeAfterPost(this.treePath, completeServerData, startPost, reverse, index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node calcCompleteChild(ChildKey childKey, CacheNode existingServerCache)
/*     */   {
/* 104 */     return this.writeTree.calcCompleteChild(this.treePath, childKey, existingServerCache);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public WriteTreeRef child(ChildKey childKey)
/*     */   {
/* 111 */     return new WriteTreeRef(this.treePath.child(childKey), this.writeTree);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/WriteTreeRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */