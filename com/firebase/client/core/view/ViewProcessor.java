/*     */ package com.firebase.client.core.view;
/*     */ 
/*     */ import com.firebase.client.core.CompoundWrite;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.WriteTreeRef;
/*     */ import com.firebase.client.core.operation.Overwrite;
/*     */ import com.firebase.client.core.view.filter.ChildChangeAccumulator;
/*     */ import com.firebase.client.core.view.filter.NodeFilter;
/*     */ import com.firebase.client.core.view.filter.NodeFilter.CompleteChildSource;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class ViewProcessor
/*     */ {
/*     */   private final NodeFilter filter;
/*     */   
/*     */   public ViewProcessor(NodeFilter filter)
/*     */   {
/*  21 */     this.filter = filter;
/*     */   }
/*     */   
/*     */   public static class ProcessorResult {
/*     */     public final ViewCache viewCache;
/*     */     public final java.util.List<Change> changes;
/*     */     
/*     */     public ProcessorResult(ViewCache viewCache, java.util.List<Change> changes) {
/*  29 */       this.viewCache = viewCache;
/*  30 */       this.changes = changes;
/*     */     }
/*     */   }
/*     */   
/*     */   public ProcessorResult applyOperation(ViewCache oldViewCache, com.firebase.client.core.operation.Operation operation, WriteTreeRef writesCache, Node optCompleteCache) {
/*  35 */     ChildChangeAccumulator accumulator = new ChildChangeAccumulator();
/*     */     ViewCache newViewCache;
/*  37 */     ViewCache newViewCache; ViewCache newViewCache; switch (operation.getType()) {
/*     */     case Overwrite: 
/*  39 */       Overwrite overwrite = (Overwrite)operation;
/*  40 */       ViewCache newViewCache; if (overwrite.getSource().isFromUser()) {
/*  41 */         newViewCache = applyUserOverwrite(oldViewCache, overwrite.getPath(), overwrite.getSnapshot(), writesCache, optCompleteCache, accumulator);
/*     */       } else {
/*  43 */         assert (overwrite.getSource().isFromServer());
/*  44 */         boolean constrainNode = overwrite.getSource().isTagged();
/*  45 */         newViewCache = applyServerOverwrite(oldViewCache, overwrite.getPath(), overwrite.getSnapshot(), writesCache, optCompleteCache, constrainNode, accumulator);
/*     */       }
/*  47 */       break;
/*     */     
/*     */     case Merge: 
/*  50 */       com.firebase.client.core.operation.Merge merge = (com.firebase.client.core.operation.Merge)operation;
/*  51 */       if (merge.getSource().isFromUser()) {
/*  52 */         newViewCache = applyUserMerge(oldViewCache, merge.getPath(), merge.getChildren(), writesCache, optCompleteCache, accumulator);
/*     */       } else {
/*  54 */         assert (merge.getSource().isFromServer());
/*  55 */         boolean constrainNode = merge.getSource().isTagged();
/*  56 */         newViewCache = applyServerMerge(oldViewCache, merge.getPath(), merge.getChildren(), writesCache, optCompleteCache, constrainNode, accumulator);
/*     */       }
/*  58 */       break;
/*     */     
/*     */     case AckUserWrite: 
/*  61 */       com.firebase.client.core.operation.AckUserWrite ackUserWrite = (com.firebase.client.core.operation.AckUserWrite)operation;
/*  62 */       if (!ackUserWrite.isRevert()) {
/*  63 */         newViewCache = ackUserWrite(oldViewCache, ackUserWrite.getPath(), writesCache, optCompleteCache, accumulator);
/*     */       } else {
/*  65 */         newViewCache = revertUserWrite(oldViewCache, ackUserWrite.getPath(), writesCache, optCompleteCache, accumulator);
/*     */       }
/*  67 */       break;
/*     */     
/*     */     case ListenComplete: 
/*  70 */       newViewCache = listenComplete(oldViewCache, operation.getPath(), writesCache, optCompleteCache, accumulator);
/*  71 */       break;
/*     */     
/*     */     default: 
/*  74 */       throw new AssertionError("Unknown operation: " + operation.getType());
/*     */     }
/*     */     
/*  77 */     java.util.List<Change> changes = new java.util.ArrayList(accumulator.getChanges());
/*  78 */     maybeAddValueEvent(oldViewCache, newViewCache, changes);
/*  79 */     return new ProcessorResult(newViewCache, changes);
/*     */   }
/*     */   
/*     */   private void maybeAddValueEvent(ViewCache oldViewCache, ViewCache newViewCache, java.util.List<Change> accumulator) {
/*  83 */     CacheNode eventSnap = newViewCache.getEventCache();
/*  84 */     if (eventSnap.isFullyInitialized()) {
/*  85 */       boolean isLeafOrEmpty = (eventSnap.getNode().isLeafNode()) || (eventSnap.getNode().isEmpty());
/*  86 */       if ((!accumulator.isEmpty()) || (!oldViewCache.getEventCache().isFullyInitialized()) || ((isLeafOrEmpty) && (!eventSnap.getNode().equals(oldViewCache.getCompleteEventSnap()))) || (!eventSnap.getNode().getPriority().equals(oldViewCache.getCompleteEventSnap().getPriority())))
/*     */       {
/*     */ 
/*     */ 
/*  90 */         accumulator.add(Change.valueChange(eventSnap.getIndexedNode()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private ViewCache generateEventCacheAfterServerEvent(ViewCache viewCache, Path changePath, WriteTreeRef writesCache, NodeFilter.CompleteChildSource source, ChildChangeAccumulator accumulator) {
/*  96 */     CacheNode oldEventSnap = viewCache.getEventCache();
/*  97 */     if (writesCache.shadowingWrite(changePath) != null)
/*     */     {
/*  99 */       return viewCache; }
/*     */     IndexedNode newEventCache;
/*     */     IndexedNode newEventCache;
/* 102 */     if (changePath.isEmpty())
/*     */     {
/* 104 */       assert (viewCache.getServerCache().isFullyInitialized()) : "If change path is empty, we must have complete server data";
/*     */       Node nodeWithLocalWrites;
/* 106 */       Node nodeWithLocalWrites; if (viewCache.getServerCache().isFiltered())
/*     */       {
/*     */ 
/*     */ 
/* 110 */         Node serverCache = viewCache.getCompleteServerSnap();
/* 111 */         Node completeChildren = (serverCache instanceof com.firebase.client.snapshot.ChildrenNode) ? serverCache : com.firebase.client.snapshot.EmptyNode.Empty();
/* 112 */         nodeWithLocalWrites = writesCache.calcCompleteEventChildren(completeChildren);
/*     */       } else {
/* 114 */         nodeWithLocalWrites = writesCache.calcCompleteEventCache(viewCache.getCompleteServerSnap());
/*     */       }
/* 116 */       IndexedNode indexedNode = IndexedNode.from(nodeWithLocalWrites, this.filter.getIndex());
/* 117 */       newEventCache = this.filter.updateFullNode(viewCache.getEventCache().getIndexedNode(), indexedNode, accumulator);
/*     */     } else {
/* 119 */       ChildKey childKey = changePath.getFront();
/* 120 */       IndexedNode newEventCache; if (childKey.isPriorityChildName()) {
/* 121 */         assert (changePath.size() == 1) : "Can't have a priority with additional path components";
/* 122 */         Node oldEventNode = oldEventSnap.getNode();
/* 123 */         Node serverNode = viewCache.getServerCache().getNode();
/*     */         
/* 125 */         Node updatedPriority = writesCache.calcEventCacheAfterServerOverwrite(changePath, oldEventNode, serverNode);
/* 126 */         IndexedNode newEventCache; if (updatedPriority != null) {
/* 127 */           newEventCache = this.filter.updatePriority(oldEventSnap.getIndexedNode(), updatedPriority);
/*     */         }
/*     */         else {
/* 130 */           newEventCache = oldEventSnap.getIndexedNode();
/*     */         }
/*     */       } else {
/* 133 */         Path childChangePath = changePath.popFront();
/*     */         Node newEventChild;
/*     */         Node newEventChild;
/* 136 */         if (oldEventSnap.isCompleteForChild(childKey)) {
/* 137 */           Node serverNode = viewCache.getServerCache().getNode();
/* 138 */           Node eventChildUpdate = writesCache.calcEventCacheAfterServerOverwrite(changePath, oldEventSnap.getNode(), serverNode);
/* 139 */           Node newEventChild; if (eventChildUpdate != null) {
/* 140 */             newEventChild = oldEventSnap.getNode().getImmediateChild(childKey).updateChild(childChangePath, eventChildUpdate);
/*     */           }
/*     */           else {
/* 143 */             newEventChild = oldEventSnap.getNode().getImmediateChild(childKey);
/*     */           }
/*     */         } else {
/* 146 */           newEventChild = writesCache.calcCompleteChild(childKey, viewCache.getServerCache()); }
/*     */         IndexedNode newEventCache;
/* 148 */         if (newEventChild != null) {
/* 149 */           newEventCache = this.filter.updateChild(oldEventSnap.getIndexedNode(), childKey, newEventChild, source, accumulator);
/*     */         }
/*     */         else {
/* 152 */           newEventCache = oldEventSnap.getIndexedNode();
/*     */         }
/*     */       }
/*     */     }
/* 156 */     return viewCache.updateEventSnap(newEventCache, (oldEventSnap.isFullyInitialized()) || (changePath.isEmpty()), this.filter.filtersNodes());
/*     */   }
/*     */   
/*     */   private ViewCache applyServerOverwrite(ViewCache oldViewCache, Path changePath, Node changedSnap, WriteTreeRef writesCache, Node optCompleteCache, boolean constrainServerNode, ChildChangeAccumulator accumulator)
/*     */   {
/* 161 */     CacheNode oldServerSnap = oldViewCache.getServerCache();
/*     */     
/* 163 */     NodeFilter serverFilter = constrainServerNode ? this.filter : this.filter.getIndexedFilter();
/* 164 */     IndexedNode newServerCache; IndexedNode newServerCache; if (changePath.isEmpty()) {
/* 165 */       newServerCache = serverFilter.updateFullNode(oldServerSnap.getIndexedNode(), IndexedNode.from(changedSnap, serverFilter.getIndex()), null); } else { IndexedNode newServerCache;
/* 166 */       if ((serverFilter.filtersNodes()) && (!oldServerSnap.isFiltered()))
/*     */       {
/* 168 */         assert (!changePath.isEmpty()) : "An empty path should have been caught in the other branch";
/* 169 */         ChildKey childKey = changePath.getFront();
/* 170 */         Path updatePath = changePath.popFront();
/* 171 */         Node newChild = oldServerSnap.getNode().getImmediateChild(childKey).updateChild(updatePath, changedSnap);
/* 172 */         IndexedNode newServerNode = oldServerSnap.getIndexedNode().updateChild(childKey, newChild);
/* 173 */         newServerCache = serverFilter.updateFullNode(oldServerSnap.getIndexedNode(), newServerNode, null);
/*     */       } else {
/* 175 */         ChildKey childKey = changePath.getFront();
/* 176 */         if ((!oldServerSnap.isCompleteForPath(changePath)) && (changePath.size() > 1))
/*     */         {
/* 178 */           return oldViewCache;
/*     */         }
/* 180 */         Node childNode = oldServerSnap.getNode().getImmediateChild(childKey);
/* 181 */         Node newChildNode = childNode.updateChild(changePath.popFront(), changedSnap);
/* 182 */         IndexedNode newServerCache; if (childKey.isPriorityChildName()) {
/* 183 */           newServerCache = serverFilter.updatePriority(oldServerSnap.getIndexedNode(), newChildNode);
/*     */         } else
/* 185 */           newServerCache = serverFilter.updateChild(oldServerSnap.getIndexedNode(), childKey, newChildNode, NO_COMPLETE_SOURCE, null);
/*     */       }
/*     */     }
/* 188 */     ViewCache newViewCache = oldViewCache.updateServerSnap(newServerCache, (oldServerSnap.isFullyInitialized()) || (changePath.isEmpty()), serverFilter.filtersNodes());
/* 189 */     NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, newViewCache, optCompleteCache);
/* 190 */     return generateEventCacheAfterServerEvent(newViewCache, changePath, writesCache, source, accumulator);
/*     */   }
/*     */   
/*     */   private ViewCache applyUserOverwrite(ViewCache oldViewCache, Path changePath, Node changedSnap, WriteTreeRef writesCache, Node optCompleteCache, ChildChangeAccumulator accumulator) {
/* 194 */     CacheNode oldEventSnap = oldViewCache.getEventCache();
/*     */     
/* 196 */     NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, oldViewCache, optCompleteCache);
/* 197 */     ViewCache newViewCache; ViewCache newViewCache; if (changePath.isEmpty()) {
/* 198 */       IndexedNode newIndexed = IndexedNode.from(changedSnap, this.filter.getIndex());
/* 199 */       IndexedNode newEventCache = this.filter.updateFullNode(oldViewCache.getEventCache().getIndexedNode(), newIndexed, accumulator);
/* 200 */       newViewCache = oldViewCache.updateEventSnap(newEventCache, true, this.filter.filtersNodes());
/*     */     } else {
/* 202 */       ChildKey childKey = changePath.getFront();
/* 203 */       ViewCache newViewCache; if (childKey.isPriorityChildName()) {
/* 204 */         IndexedNode newEventCache = this.filter.updatePriority(oldViewCache.getEventCache().getIndexedNode(), changedSnap);
/* 205 */         newViewCache = oldViewCache.updateEventSnap(newEventCache, oldEventSnap.isFullyInitialized(), oldEventSnap.isFiltered());
/*     */       } else {
/* 207 */         Path childChangePath = changePath.popFront();
/* 208 */         Node oldChild = oldEventSnap.getNode().getImmediateChild(childKey);
/*     */         Node newChild;
/* 210 */         Node newChild; if (childChangePath.isEmpty())
/*     */         {
/* 212 */           newChild = changedSnap;
/*     */         } else {
/* 214 */           Node childNode = source.getCompleteChild(childKey);
/* 215 */           Node newChild; if (childNode != null) { Node newChild;
/* 216 */             if ((childChangePath.getBack().isPriorityChildName()) && (childNode.getChild(childChangePath.getParent()).isEmpty()))
/*     */             {
/*     */ 
/* 219 */               newChild = childNode;
/*     */             } else {
/* 221 */               newChild = childNode.updateChild(childChangePath, changedSnap);
/*     */             }
/*     */           }
/*     */           else {
/* 225 */             newChild = com.firebase.client.snapshot.EmptyNode.Empty();
/*     */           } }
/*     */         ViewCache newViewCache;
/* 228 */         if (!oldChild.equals(newChild)) {
/* 229 */           IndexedNode newEventSnap = this.filter.updateChild(oldEventSnap.getIndexedNode(), childKey, newChild, source, accumulator);
/* 230 */           newViewCache = oldViewCache.updateEventSnap(newEventSnap, oldEventSnap.isFullyInitialized(), this.filter.filtersNodes());
/*     */         } else {
/* 232 */           newViewCache = oldViewCache;
/*     */         }
/*     */       }
/*     */     }
/* 236 */     return newViewCache;
/*     */   }
/*     */   
/*     */   private static boolean cacheHasChild(ViewCache viewCache, ChildKey childKey) {
/* 240 */     return viewCache.getEventCache().isCompleteForChild(childKey);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ViewCache applyUserMerge(ViewCache viewCache, Path path, CompoundWrite changedChildren, WriteTreeRef writesCache, Node serverCache, ChildChangeAccumulator accumulator)
/*     */   {
/* 250 */     assert (changedChildren.rootWrite() == null) : "Can't have a merge that is an overwrite";
/* 251 */     ViewCache currentViewCache = viewCache;
/* 252 */     for (Map.Entry<Path, Node> entry : changedChildren) {
/* 253 */       Path writePath = path.child((Path)entry.getKey());
/* 254 */       if (cacheHasChild(viewCache, writePath.getFront())) {
/* 255 */         currentViewCache = applyUserOverwrite(currentViewCache, writePath, (Node)entry.getValue(), writesCache, serverCache, accumulator);
/*     */       }
/*     */     }
/*     */     
/* 259 */     for (Map.Entry<Path, Node> entry : changedChildren) {
/* 260 */       Path writePath = path.child((Path)entry.getKey());
/* 261 */       if (!cacheHasChild(viewCache, writePath.getFront())) {
/* 262 */         currentViewCache = applyUserOverwrite(currentViewCache, writePath, (Node)entry.getValue(), writesCache, serverCache, accumulator);
/*     */       }
/*     */     }
/* 265 */     return currentViewCache;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private ViewCache applyServerMerge(ViewCache viewCache, Path path, CompoundWrite changedChildren, WriteTreeRef writesCache, Node serverCache, boolean constrainServerNode, ChildChangeAccumulator accumulator)
/*     */   {
/* 272 */     if ((viewCache.getServerCache().getNode().isEmpty()) && (!viewCache.getServerCache().isFullyInitialized())) {
/* 273 */       return viewCache;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 282 */     ViewCache curViewCache = viewCache;
/* 283 */     assert (changedChildren.rootWrite() == null) : "Can't have a merge that is an overwrite";
/*     */     CompoundWrite actualMerge;
/* 285 */     CompoundWrite actualMerge; if (path.isEmpty()) {
/* 286 */       actualMerge = changedChildren;
/*     */     } else {
/* 288 */       actualMerge = CompoundWrite.emptyWrite().addWrites(path, changedChildren);
/*     */     }
/* 290 */     Node serverNode = viewCache.getServerCache().getNode();
/* 291 */     java.util.Map<ChildKey, CompoundWrite> childCompoundWrites = actualMerge.childCompoundWrites();
/* 292 */     for (Map.Entry<ChildKey, CompoundWrite> childMerge : childCompoundWrites.entrySet()) {
/* 293 */       ChildKey childKey = (ChildKey)childMerge.getKey();
/* 294 */       if (serverNode.hasChild(childKey)) {
/* 295 */         Node serverChild = serverNode.getImmediateChild(childKey);
/* 296 */         Node newChild = ((CompoundWrite)childMerge.getValue()).apply(serverChild);
/* 297 */         curViewCache = applyServerOverwrite(curViewCache, new Path(new ChildKey[] { childKey }), newChild, writesCache, serverCache, constrainServerNode, accumulator);
/*     */       }
/*     */     }
/* 300 */     for (Map.Entry<ChildKey, CompoundWrite> childMerge : childCompoundWrites.entrySet()) {
/* 301 */       ChildKey childKey = (ChildKey)childMerge.getKey();
/* 302 */       CompoundWrite childCompoundWrite = (CompoundWrite)childMerge.getValue();
/* 303 */       boolean isUnknownDeepMerge = (!viewCache.getServerCache().isFullyInitialized()) && (childCompoundWrite.rootWrite() == null);
/* 304 */       if ((!serverNode.hasChild(childKey)) && (!isUnknownDeepMerge)) {
/* 305 */         Node serverChild = serverNode.getImmediateChild(childKey);
/* 306 */         Node newChild = ((CompoundWrite)childMerge.getValue()).apply(serverChild);
/* 307 */         curViewCache = applyServerOverwrite(curViewCache, new Path(new ChildKey[] { childKey }), newChild, writesCache, serverCache, constrainServerNode, accumulator);
/*     */       }
/*     */     }
/*     */     
/* 311 */     return curViewCache;
/*     */   }
/*     */   
/*     */   private ViewCache ackUserWrite(ViewCache viewCache, Path ackPath, WriteTreeRef writesCache, Node optCompleteCache, ChildChangeAccumulator accumulator)
/*     */   {
/* 316 */     if (writesCache.shadowingWrite(ackPath) != null) {
/* 317 */       return viewCache;
/*     */     }
/* 319 */     NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, viewCache, optCompleteCache);
/* 320 */     IndexedNode oldEventCache = viewCache.getEventCache().getIndexedNode();
/* 321 */     IndexedNode newEventCache = oldEventCache;
/*     */     boolean eventCacheComplete;
/* 323 */     boolean eventCacheComplete; if (viewCache.getServerCache().isFullyInitialized())
/*     */     {
/* 325 */       if (ackPath.isEmpty()) {
/* 326 */         Node update = writesCache.calcCompleteEventCache(viewCache.getCompleteServerSnap());
/* 327 */         IndexedNode indexedNode = IndexedNode.from(update, this.filter.getIndex());
/* 328 */         newEventCache = this.filter.updateFullNode(viewCache.getEventCache().getIndexedNode(), indexedNode, accumulator);
/* 329 */       } else if (ackPath.getFront().isPriorityChildName())
/*     */       {
/* 331 */         Node updatedPriority = writesCache.calcCompleteChild(ackPath.getFront(), viewCache.getServerCache());
/* 332 */         if ((updatedPriority != null) && (!oldEventCache.getNode().isEmpty()) && (!oldEventCache.getNode().getPriority().equals(updatedPriority))) {
/* 333 */           newEventCache = this.filter.updatePriority(oldEventCache, updatedPriority);
/*     */         }
/*     */       }
/*     */       else {
/* 337 */         ChildKey childKey = ackPath.getFront();
/* 338 */         Node updatedChild = writesCache.calcCompleteChild(childKey, viewCache.getServerCache());
/* 339 */         if (updatedChild != null) {
/* 340 */           newEventCache = this.filter.updateChild(viewCache.getEventCache().getIndexedNode(), childKey, updatedChild, source, accumulator);
/*     */         }
/*     */       }
/* 343 */       eventCacheComplete = true;
/*     */     } else {
/*     */       boolean eventCacheComplete;
/* 346 */       if ((viewCache.getEventCache().isFullyInitialized()) || (ackPath.isEmpty()))
/*     */       {
/*     */ 
/*     */ 
/* 350 */         newEventCache = oldEventCache;
/* 351 */         for (com.firebase.client.snapshot.NamedNode entry : viewCache.getEventCache().getNode()) {
/* 352 */           Node completeChild = writesCache.calcCompleteChild(entry.getName(), viewCache.getServerCache());
/* 353 */           if (completeChild != null) {
/* 354 */             newEventCache = this.filter.updateChild(newEventCache, entry.getName(), completeChild, source, accumulator);
/*     */           }
/*     */         }
/*     */         
/* 358 */         eventCacheComplete = viewCache.getEventCache().isFullyInitialized();
/*     */       } else {
/* 360 */         ChildKey childKey = ackPath.getFront();
/* 361 */         if ((ackPath.size() == 1) || (viewCache.getEventCache().isCompleteForChild(childKey))) {
/* 362 */           Node completeChild = writesCache.calcCompleteChild(childKey, viewCache.getServerCache());
/* 363 */           if (completeChild != null) {
/* 364 */             newEventCache = this.filter.updateChild(oldEventCache, childKey, completeChild, source, accumulator);
/*     */           }
/*     */         }
/* 367 */         eventCacheComplete = false;
/*     */       }
/*     */     }
/* 370 */     return viewCache.updateEventSnap(newEventCache, eventCacheComplete, this.filter.filtersNodes());
/*     */   }
/*     */   
/*     */   public ViewCache revertUserWrite(ViewCache viewCache, Path path, WriteTreeRef writesCache, Node optCompleteServerCache, ChildChangeAccumulator accumulator)
/*     */   {
/* 375 */     if (writesCache.shadowingWrite(path) != null) {
/* 376 */       return viewCache;
/*     */     }
/* 378 */     NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, viewCache, optCompleteServerCache);
/* 379 */     IndexedNode oldEventCache = viewCache.getEventCache().getIndexedNode();
/*     */     IndexedNode newEventCache;
/* 381 */     IndexedNode newEventCache; if ((path.isEmpty()) || (path.getFront().isPriorityChildName())) { Node newNode;
/*     */       Node newNode;
/* 383 */       if (viewCache.getServerCache().isFullyInitialized()) {
/* 384 */         newNode = writesCache.calcCompleteEventCache(viewCache.getCompleteServerSnap());
/*     */       } else {
/* 386 */         newNode = writesCache.calcCompleteEventChildren(viewCache.getServerCache().getNode());
/*     */       }
/* 388 */       IndexedNode indexedNode = IndexedNode.from(newNode, this.filter.getIndex());
/* 389 */       newEventCache = this.filter.updateFullNode(oldEventCache, indexedNode, accumulator);
/*     */     } else {
/* 391 */       ChildKey childKey = path.getFront();
/* 392 */       Node newChild = writesCache.calcCompleteChild(childKey, viewCache.getServerCache());
/* 393 */       if ((newChild == null) && (viewCache.getServerCache().isCompleteForChild(childKey)))
/* 394 */         newChild = oldEventCache.getNode().getImmediateChild(childKey);
/*     */       IndexedNode newEventCache;
/* 396 */       if (newChild != null) {
/* 397 */         newEventCache = this.filter.updateChild(oldEventCache, childKey, newChild, source, accumulator); } else { IndexedNode newEventCache;
/* 398 */         if ((newChild == null) && (viewCache.getEventCache().getNode().hasChild(childKey)))
/*     */         {
/* 400 */           newEventCache = this.filter.updateChild(oldEventCache, childKey, com.firebase.client.snapshot.EmptyNode.Empty(), source, accumulator);
/*     */         } else
/* 402 */           newEventCache = oldEventCache;
/*     */       }
/* 404 */       if ((newEventCache.getNode().isEmpty()) && (viewCache.getServerCache().isFullyInitialized()))
/*     */       {
/* 406 */         Node complete = writesCache.calcCompleteEventCache(viewCache.getCompleteServerSnap());
/* 407 */         if (complete.isLeafNode()) {
/* 408 */           IndexedNode indexedNode = IndexedNode.from(complete, this.filter.getIndex());
/* 409 */           newEventCache = this.filter.updateFullNode(newEventCache, indexedNode, accumulator);
/*     */         }
/*     */       }
/*     */     }
/* 413 */     boolean complete = (viewCache.getServerCache().isFullyInitialized()) || (writesCache.shadowingWrite(Path.getEmptyPath()) != null);
/* 414 */     return viewCache.updateEventSnap(newEventCache, complete, this.filter.filtersNodes());
/*     */   }
/*     */   
/*     */   private ViewCache listenComplete(ViewCache viewCache, Path path, WriteTreeRef writesCache, Node serverCache, ChildChangeAccumulator accumulator)
/*     */   {
/* 419 */     CacheNode oldServerNode = viewCache.getServerCache();
/* 420 */     ViewCache newViewCache = viewCache.updateServerSnap(oldServerNode.getIndexedNode(), (oldServerNode.isFullyInitialized()) || (path.isEmpty()), oldServerNode.isFiltered());
/* 421 */     return generateEventCacheAfterServerEvent(newViewCache, path, writesCache, NO_COMPLETE_SOURCE, accumulator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 427 */   private static NodeFilter.CompleteChildSource NO_COMPLETE_SOURCE = new NodeFilter.CompleteChildSource()
/*     */   {
/*     */     public Node getCompleteChild(ChildKey childKey) {
/* 430 */       return null;
/*     */     }
/*     */     
/*     */     public com.firebase.client.snapshot.NamedNode getChildAfterChild(com.firebase.client.snapshot.Index index, com.firebase.client.snapshot.NamedNode child, boolean reverse)
/*     */     {
/* 435 */       return null;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */   private static class WriteTreeCompleteChildSource
/*     */     implements NodeFilter.CompleteChildSource
/*     */   {
/*     */     private final WriteTreeRef writes;
/*     */     private final ViewCache viewCache;
/*     */     private final Node optCompleteServerCache;
/*     */     
/*     */     public WriteTreeCompleteChildSource(WriteTreeRef writes, ViewCache viewCache, Node optCompleteServerCache)
/*     */     {
/* 449 */       this.writes = writes;
/* 450 */       this.viewCache = viewCache;
/* 451 */       this.optCompleteServerCache = optCompleteServerCache;
/*     */     }
/*     */     
/*     */     public Node getCompleteChild(ChildKey childKey)
/*     */     {
/* 456 */       CacheNode node = this.viewCache.getEventCache();
/* 457 */       if (node.isCompleteForChild(childKey))
/* 458 */         return node.getNode().getImmediateChild(childKey);
/*     */       CacheNode serverNode;
/*     */       CacheNode serverNode;
/* 461 */       if (this.optCompleteServerCache != null)
/*     */       {
/* 463 */         serverNode = new CacheNode(IndexedNode.from(this.optCompleteServerCache, com.firebase.client.snapshot.KeyIndex.getInstance()), true, false);
/*     */       } else {
/* 465 */         serverNode = this.viewCache.getServerCache();
/*     */       }
/* 467 */       return this.writes.calcCompleteChild(childKey, serverNode);
/*     */     }
/*     */     
/*     */ 
/*     */     public com.firebase.client.snapshot.NamedNode getChildAfterChild(com.firebase.client.snapshot.Index index, com.firebase.client.snapshot.NamedNode child, boolean reverse)
/*     */     {
/* 473 */       Node completeServerData = this.optCompleteServerCache != null ? this.optCompleteServerCache : this.viewCache.getCompleteServerSnap();
/* 474 */       return this.writes.calcNextNodeAfterPost(completeServerData, child, reverse, index);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/ViewProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */