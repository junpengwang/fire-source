/*    */ package com.firebase.client.core.view;
/*    */ 
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ import com.firebase.client.snapshot.IndexedNode;
/*    */ 
/*    */ public class Change
/*    */ {
/*    */   private final Event.EventType eventType;
/*    */   private final IndexedNode indexedNode;
/*    */   private final IndexedNode oldIndexedNode;
/*    */   private final ChildKey childKey;
/*    */   private final ChildKey prevName;
/*    */   
/*    */   private Change(Event.EventType eventType, IndexedNode indexedNode, ChildKey childKey, ChildKey prevName, IndexedNode oldIndexedNode)
/*    */   {
/* 16 */     this.eventType = eventType;
/* 17 */     this.indexedNode = indexedNode;
/* 18 */     this.childKey = childKey;
/* 19 */     this.prevName = prevName;
/* 20 */     this.oldIndexedNode = oldIndexedNode;
/*    */   }
/*    */   
/*    */   public static Change valueChange(IndexedNode snapshot) {
/* 24 */     return new Change(Event.EventType.VALUE, snapshot, null, null, null);
/*    */   }
/*    */   
/*    */   public static Change childAddedChange(ChildKey childKey, com.firebase.client.snapshot.Node snapshot) {
/* 28 */     return childAddedChange(childKey, IndexedNode.from(snapshot));
/*    */   }
/*    */   
/*    */   public static Change childAddedChange(ChildKey childKey, IndexedNode snapshot) {
/* 32 */     return new Change(Event.EventType.CHILD_ADDED, snapshot, childKey, null, null);
/*    */   }
/*    */   
/*    */   public static Change childRemovedChange(ChildKey childKey, com.firebase.client.snapshot.Node snapshot) {
/* 36 */     return childRemovedChange(childKey, IndexedNode.from(snapshot));
/*    */   }
/*    */   
/*    */   public static Change childRemovedChange(ChildKey childKey, IndexedNode snapshot) {
/* 40 */     return new Change(Event.EventType.CHILD_REMOVED, snapshot, childKey, null, null);
/*    */   }
/*    */   
/*    */   public static Change childChangedChange(ChildKey childKey, com.firebase.client.snapshot.Node newSnapshot, com.firebase.client.snapshot.Node oldSnapshot) {
/* 44 */     return childChangedChange(childKey, IndexedNode.from(newSnapshot), IndexedNode.from(oldSnapshot));
/*    */   }
/*    */   
/*    */   public static Change childChangedChange(ChildKey childKey, IndexedNode newSnapshot, IndexedNode oldSnapshot) {
/* 48 */     return new Change(Event.EventType.CHILD_CHANGED, newSnapshot, childKey, null, oldSnapshot);
/*    */   }
/*    */   
/*    */   public static Change childMovedChange(ChildKey childKey, com.firebase.client.snapshot.Node snapshot) {
/* 52 */     return childMovedChange(childKey, IndexedNode.from(snapshot));
/*    */   }
/*    */   
/*    */   public static Change childMovedChange(ChildKey childKey, IndexedNode snapshot) {
/* 56 */     return new Change(Event.EventType.CHILD_MOVED, snapshot, childKey, null, null);
/*    */   }
/*    */   
/*    */   public Change changeWithPrevName(ChildKey prevName) {
/* 60 */     return new Change(this.eventType, this.indexedNode, this.childKey, prevName, this.oldIndexedNode);
/*    */   }
/*    */   
/*    */   public ChildKey getChildKey() {
/* 64 */     return this.childKey;
/*    */   }
/*    */   
/*    */   public Event.EventType getEventType() {
/* 68 */     return this.eventType;
/*    */   }
/*    */   
/*    */   public IndexedNode getIndexedNode() {
/* 72 */     return this.indexedNode;
/*    */   }
/*    */   
/*    */   public ChildKey getPrevName() {
/* 76 */     return this.prevName;
/*    */   }
/*    */   
/*    */   public IndexedNode getOldIndexedNode() {
/* 80 */     return this.oldIndexedNode;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 85 */     return "Change: " + this.eventType + " " + this.childKey;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/Change.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */