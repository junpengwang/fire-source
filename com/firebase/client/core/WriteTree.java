/*     */ package com.firebase.client.core;
/*     */ 
/*     */ import com.firebase.client.core.utilities.Predicate;
/*     */ import com.firebase.client.core.view.CacheNode;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.EmptyNode;
/*     */ import com.firebase.client.snapshot.Index;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
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
/*     */ public class WriteTree
/*     */ {
/*     */   private CompoundWrite visibleWrites;
/*     */   private List<UserWriteRecord> allWrites;
/*     */   private Long lastWriteId;
/*     */   
/*     */   public WriteTree()
/*     */   {
/*  35 */     this.visibleWrites = CompoundWrite.emptyWrite();
/*  36 */     this.allWrites = new ArrayList();
/*  37 */     this.lastWriteId = Long.valueOf(-1L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WriteTreeRef childWrites(Path path)
/*     */   {
/*  45 */     return new WriteTreeRef(path, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addOverwrite(Path path, Node snap, Long writeId, boolean visible)
/*     */   {
/*  52 */     assert (writeId.longValue() > this.lastWriteId.longValue());
/*  53 */     this.allWrites.add(new UserWriteRecord(writeId.longValue(), path, snap, visible));
/*  54 */     if (visible) {
/*  55 */       this.visibleWrites = this.visibleWrites.addWrite(path, snap);
/*     */     }
/*  57 */     this.lastWriteId = writeId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addMerge(Path path, CompoundWrite changedChildren, Long writeId)
/*     */   {
/*  64 */     assert (writeId.longValue() > this.lastWriteId.longValue());
/*  65 */     this.allWrites.add(new UserWriteRecord(writeId.longValue(), path, changedChildren));
/*  66 */     this.visibleWrites = this.visibleWrites.addWrites(path, changedChildren);
/*  67 */     this.lastWriteId = writeId;
/*     */   }
/*     */   
/*     */   public UserWriteRecord getWrite(long writeId) {
/*  71 */     for (UserWriteRecord record : this.allWrites) {
/*  72 */       if (record.getWriteId() == writeId) {
/*  73 */         return record;
/*     */       }
/*     */     }
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   public List<UserWriteRecord> purgeAllWrites() {
/*  80 */     List<UserWriteRecord> purgedWrites = new ArrayList(this.allWrites);
/*     */     
/*  82 */     this.visibleWrites = CompoundWrite.emptyWrite();
/*  83 */     this.allWrites = new ArrayList();
/*  84 */     return purgedWrites;
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
/*     */   public Path removeWrite(long writeId)
/*     */   {
/* 102 */     UserWriteRecord writeToRemove = null;
/* 103 */     int idx = 0;
/* 104 */     for (UserWriteRecord record : this.allWrites) {
/* 105 */       if (record.getWriteId() == writeId) {
/* 106 */         writeToRemove = record;
/* 107 */         break;
/*     */       }
/* 109 */       idx++;
/*     */     }
/* 111 */     assert (writeToRemove != null) : "removeWrite called with nonexistent writeId";
/*     */     
/* 113 */     this.allWrites.remove(writeToRemove);
/*     */     
/* 115 */     boolean removedWriteWasVisible = writeToRemove.isVisible();
/* 116 */     boolean removedWriteOverlapsWithOtherWrites = false;
/* 117 */     int i = this.allWrites.size() - 1;
/*     */     
/* 119 */     while ((removedWriteWasVisible) && (i >= 0)) {
/* 120 */       UserWriteRecord currentWrite = (UserWriteRecord)this.allWrites.get(i);
/* 121 */       if (currentWrite.isVisible()) {
/* 122 */         if ((i >= idx) && (recordContainsPath(currentWrite, writeToRemove.getPath())))
/*     */         {
/* 124 */           removedWriteWasVisible = false;
/* 125 */         } else if (writeToRemove.getPath().contains(currentWrite.getPath()))
/*     */         {
/* 127 */           removedWriteOverlapsWithOtherWrites = true;
/*     */         }
/*     */       }
/* 130 */       i--;
/*     */     }
/*     */     
/* 133 */     if (!removedWriteWasVisible)
/* 134 */       return null;
/* 135 */     if (removedWriteOverlapsWithOtherWrites)
/*     */     {
/* 137 */       resetTree();
/* 138 */       return writeToRemove.getPath();
/*     */     }
/*     */     
/* 141 */     if (writeToRemove.isOverwrite()) {
/* 142 */       this.visibleWrites = this.visibleWrites.removeWrite(writeToRemove.getPath());
/*     */     } else {
/* 144 */       for (Map.Entry<Path, Node> entry : writeToRemove.getMerge()) {
/* 145 */         Path path = (Path)entry.getKey();
/* 146 */         this.visibleWrites = this.visibleWrites.removeWrite(writeToRemove.getPath().child(path));
/*     */       }
/*     */     }
/* 149 */     return writeToRemove.getPath();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node getCompleteWriteData(Path path)
/*     */   {
/* 158 */     return this.visibleWrites.getCompleteNode(path);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node calcCompleteEventCache(Path treePath, Node completeServerCache)
/*     */   {
/* 166 */     return calcCompleteEventCache(treePath, completeServerCache, new ArrayList());
/*     */   }
/*     */   
/*     */   public Node calcCompleteEventCache(Path treePath, Node completeServerCache, List<Long> writeIdsToExclude) {
/* 170 */     return calcCompleteEventCache(treePath, completeServerCache, writeIdsToExclude, false);
/*     */   }
/*     */   
/*     */   public Node calcCompleteEventCache(final Path treePath, Node completeServerCache, final List<Long> writeIdsToExclude, final boolean includeHiddenWrites)
/*     */   {
/* 175 */     if ((writeIdsToExclude.isEmpty()) && (!includeHiddenWrites)) {
/* 176 */       Node shadowingNode = this.visibleWrites.getCompleteNode(treePath);
/* 177 */       if (shadowingNode != null) {
/* 178 */         return shadowingNode;
/*     */       }
/* 180 */       CompoundWrite subMerge = this.visibleWrites.childCompoundWrite(treePath);
/* 181 */       if (subMerge.isEmpty())
/* 182 */         return completeServerCache;
/* 183 */       if ((completeServerCache == null) && (!subMerge.hasCompleteWrite(Path.getEmptyPath())))
/*     */       {
/* 185 */         return null; }
/*     */       Node layeredCache;
/*     */       Node layeredCache;
/* 188 */       if (completeServerCache != null) {
/* 189 */         layeredCache = completeServerCache;
/*     */       } else {
/* 191 */         layeredCache = EmptyNode.Empty();
/*     */       }
/* 193 */       return subMerge.apply(layeredCache);
/*     */     }
/*     */     
/*     */ 
/* 197 */     CompoundWrite merge = this.visibleWrites.childCompoundWrite(treePath);
/* 198 */     if ((!includeHiddenWrites) && (merge.isEmpty())) {
/* 199 */       return completeServerCache;
/*     */     }
/*     */     
/* 202 */     if ((!includeHiddenWrites) && (completeServerCache == null) && (!merge.hasCompleteWrite(Path.getEmptyPath()))) {
/* 203 */       return null;
/*     */     }
/* 205 */     Predicate<UserWriteRecord> filter = new Predicate()
/*     */     {
/*     */       public boolean evaluate(UserWriteRecord write) {
/* 208 */         return ((write.isVisible()) || (includeHiddenWrites)) && (!writeIdsToExclude.contains(Long.valueOf(write.getWriteId()))) && ((write.getPath().contains(treePath)) || (treePath.contains(write.getPath())));
/*     */ 
/*     */       }
/*     */       
/*     */ 
/* 213 */     };
/* 214 */     CompoundWrite mergeAtPath = layerTree(this.allWrites, filter, treePath);
/* 215 */     Node layeredCache = completeServerCache != null ? completeServerCache : EmptyNode.Empty();
/* 216 */     return mergeAtPath.apply(layeredCache);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node calcCompleteEventChildren(Path treePath, Node completeServerChildren)
/*     */   {
/* 227 */     Node completeChildren = EmptyNode.Empty();
/* 228 */     Node topLevelSet = this.visibleWrites.getCompleteNode(treePath);
/* 229 */     if (topLevelSet != null) {
/* 230 */       if (!topLevelSet.isLeafNode())
/*     */       {
/* 232 */         for (NamedNode childEntry : topLevelSet) {
/* 233 */           completeChildren = completeChildren.updateImmediateChild(childEntry.getName(), childEntry.getNode());
/*     */         }
/*     */       }
/* 236 */       return completeChildren;
/*     */     }
/*     */     
/*     */ 
/* 240 */     CompoundWrite merge = this.visibleWrites.childCompoundWrite(treePath);
/* 241 */     for (NamedNode entry : completeServerChildren) {
/* 242 */       Node node = merge.childCompoundWrite(new Path(new ChildKey[] { entry.getName() })).apply(entry.getNode());
/* 243 */       completeChildren = completeChildren.updateImmediateChild(entry.getName(), node);
/*     */     }
/*     */     
/* 246 */     for (NamedNode node : merge.getCompleteChildren()) {
/* 247 */       completeChildren = completeChildren.updateImmediateChild(node.getName(), node.getNode());
/*     */     }
/* 249 */     return completeChildren;
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
/*     */   public Node calcEventCacheAfterServerOverwrite(Path treePath, Path childPath, Node existingEventSnap, Node existingServerSnap)
/*     */   {
/* 269 */     assert ((existingEventSnap != null) || (existingServerSnap != null)) : "Either existingEventSnap or existingServerSnap must exist";
/* 270 */     Path path = treePath.child(childPath);
/* 271 */     if (this.visibleWrites.hasCompleteWrite(path))
/*     */     {
/*     */ 
/* 274 */       return null;
/*     */     }
/*     */     
/* 277 */     CompoundWrite childMerge = this.visibleWrites.childCompoundWrite(path);
/* 278 */     if (childMerge.isEmpty())
/*     */     {
/* 280 */       return existingServerSnap.getChild(childPath);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 288 */     return childMerge.apply(existingServerSnap.getChild(childPath));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node calcCompleteChild(Path treePath, ChildKey childKey, CacheNode existingServerSnap)
/*     */   {
/* 298 */     Path path = treePath.child(childKey);
/* 299 */     Node shadowingNode = this.visibleWrites.getCompleteNode(path);
/* 300 */     if (shadowingNode != null) {
/* 301 */       return shadowingNode;
/*     */     }
/* 303 */     if (existingServerSnap.isCompleteForChild(childKey)) {
/* 304 */       CompoundWrite childMerge = this.visibleWrites.childCompoundWrite(path);
/* 305 */       return childMerge.apply(existingServerSnap.getNode().getImmediateChild(childKey));
/*     */     }
/* 307 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node shadowingWrite(Path path)
/*     */   {
/* 318 */     return this.visibleWrites.getCompleteNode(path);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NamedNode calcNextNodeAfterPost(Path treePath, Node completeServerData, NamedNode post, boolean reverse, Index index)
/*     */   {
/* 327 */     CompoundWrite merge = this.visibleWrites.childCompoundWrite(treePath);
/* 328 */     Node shadowingNode = merge.getCompleteNode(Path.getEmptyPath());
/* 329 */     Node toIterate; if (shadowingNode != null) {
/* 330 */       toIterate = shadowingNode; } else { Node toIterate;
/* 331 */       if (completeServerData != null) {
/* 332 */         toIterate = merge.apply(completeServerData);
/*     */       }
/*     */       else
/* 335 */         return null; }
/*     */     Node toIterate;
/* 337 */     NamedNode currentNext = null;
/* 338 */     for (NamedNode node : toIterate) {
/* 339 */       if ((index.compare(node, post, reverse) > 0) && ((currentNext == null) || (index.compare(node, currentNext, reverse) < 0))) {
/* 340 */         currentNext = node;
/*     */       }
/*     */     }
/* 343 */     return currentNext;
/*     */   }
/*     */   
/*     */   private boolean recordContainsPath(UserWriteRecord writeRecord, Path path) {
/* 347 */     if (writeRecord.isOverwrite()) {
/* 348 */       return writeRecord.getPath().contains(path);
/*     */     }
/* 350 */     for (Map.Entry<Path, Node> entry : writeRecord.getMerge()) {
/* 351 */       if (writeRecord.getPath().child((Path)entry.getKey()).contains(path)) {
/* 352 */         return true;
/*     */       }
/*     */     }
/* 355 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void resetTree()
/*     */   {
/* 363 */     this.visibleWrites = layerTree(this.allWrites, DEFAULT_FILTER, Path.getEmptyPath());
/* 364 */     if (this.allWrites.size() > 0) {
/* 365 */       this.lastWriteId = Long.valueOf(((UserWriteRecord)this.allWrites.get(this.allWrites.size() - 1)).getWriteId());
/*     */     } else {
/* 367 */       this.lastWriteId = Long.valueOf(-1L);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 374 */   private static final Predicate<UserWriteRecord> DEFAULT_FILTER = new Predicate()
/*     */   {
/*     */     public boolean evaluate(UserWriteRecord write) {
/* 377 */       return write.isVisible();
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static CompoundWrite layerTree(List<UserWriteRecord> writes, Predicate<UserWriteRecord> filter, Path treeRoot)
/*     */   {
/* 386 */     CompoundWrite compoundWrite = CompoundWrite.emptyWrite();
/* 387 */     for (UserWriteRecord write : writes)
/*     */     {
/*     */ 
/*     */ 
/* 391 */       if (filter.evaluate(write)) {
/* 392 */         Path writePath = write.getPath();
/* 393 */         if (write.isOverwrite()) {
/* 394 */           if (treeRoot.contains(writePath)) {
/* 395 */             Path relativePath = Path.getRelative(treeRoot, writePath);
/* 396 */             compoundWrite = compoundWrite.addWrite(relativePath, write.getOverwrite());
/* 397 */           } else if (writePath.contains(treeRoot)) {
/* 398 */             compoundWrite = compoundWrite.addWrite(Path.getEmptyPath(), write.getOverwrite().getChild(Path.getRelative(writePath, treeRoot)));
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 403 */         else if (treeRoot.contains(writePath)) {
/* 404 */           Path relativePath = Path.getRelative(treeRoot, writePath);
/* 405 */           compoundWrite = compoundWrite.addWrites(relativePath, write.getMerge());
/* 406 */         } else if (writePath.contains(treeRoot)) {
/* 407 */           Path relativePath = Path.getRelative(writePath, treeRoot);
/* 408 */           if (relativePath.isEmpty()) {
/* 409 */             compoundWrite = compoundWrite.addWrites(Path.getEmptyPath(), write.getMerge());
/*     */           } else {
/* 411 */             Node deepNode = write.getMerge().getCompleteNode(relativePath);
/* 412 */             if (deepNode != null) {
/* 413 */               compoundWrite = compoundWrite.addWrite(Path.getEmptyPath(), deepNode);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 422 */     return compoundWrite;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/WriteTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */