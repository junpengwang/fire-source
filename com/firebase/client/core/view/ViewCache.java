/*    */ package com.firebase.client.core.view;
/*    */ 
/*    */ import com.firebase.client.snapshot.IndexedNode;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ 
/*    */ public class ViewCache
/*    */ {
/*    */   private final CacheNode eventSnap;
/*    */   private final CacheNode serverSnap;
/*    */   
/*    */   public ViewCache(CacheNode eventSnap, CacheNode serverSnap)
/*    */   {
/* 13 */     this.eventSnap = eventSnap;
/* 14 */     this.serverSnap = serverSnap;
/*    */   }
/*    */   
/*    */   public ViewCache updateEventSnap(IndexedNode eventSnap, boolean complete, boolean filtered) {
/* 18 */     return new ViewCache(new CacheNode(eventSnap, complete, filtered), this.serverSnap);
/*    */   }
/*    */   
/*    */   public ViewCache updateServerSnap(IndexedNode serverSnap, boolean complete, boolean filtered) {
/* 22 */     return new ViewCache(this.eventSnap, new CacheNode(serverSnap, complete, filtered));
/*    */   }
/*    */   
/*    */   public CacheNode getEventCache() {
/* 26 */     return this.eventSnap;
/*    */   }
/*    */   
/*    */   public Node getCompleteEventSnap() {
/* 30 */     return this.eventSnap.isFullyInitialized() ? this.eventSnap.getNode() : null;
/*    */   }
/*    */   
/*    */   public CacheNode getServerCache() {
/* 34 */     return this.serverSnap;
/*    */   }
/*    */   
/*    */   public Node getCompleteServerSnap() {
/* 38 */     return this.serverSnap.isFullyInitialized() ? this.serverSnap.getNode() : null;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/ViewCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */