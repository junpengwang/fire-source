/*    */ package com.firebase.client.core.view.filter;
/*    */ 
/*    */ import com.firebase.client.core.view.Change;
/*    */ import com.firebase.client.core.view.Event.EventType;
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ChildChangeAccumulator
/*    */ {
/*    */   private final Map<ChildKey, Change> changeMap;
/*    */   
/*    */   public ChildChangeAccumulator()
/*    */   {
/* 17 */     this.changeMap = new HashMap();
/*    */   }
/*    */   
/*    */   public void trackChildChange(Change change) {
/* 21 */     Event.EventType type = change.getEventType();
/* 22 */     ChildKey childKey = change.getChildKey();
/* 23 */     assert ((type == Event.EventType.CHILD_ADDED) || (type == Event.EventType.CHILD_CHANGED) || (type == Event.EventType.CHILD_REMOVED)) : "Only child changes supported for tracking";
/* 24 */     assert (!change.getChildKey().isPriorityChildName());
/* 25 */     if (this.changeMap.containsKey(childKey)) {
/* 26 */       Change oldChange = (Change)this.changeMap.get(childKey);
/* 27 */       Event.EventType oldType = oldChange.getEventType();
/* 28 */       if ((type == Event.EventType.CHILD_ADDED) && (oldType == Event.EventType.CHILD_REMOVED)) {
/* 29 */         this.changeMap.put(change.getChildKey(), Change.childChangedChange(childKey, change.getIndexedNode(), oldChange.getIndexedNode()));
/* 30 */       } else if ((type == Event.EventType.CHILD_REMOVED) && (oldType == Event.EventType.CHILD_ADDED)) {
/* 31 */         this.changeMap.remove(childKey);
/* 32 */       } else if ((type == Event.EventType.CHILD_REMOVED) && (oldType == Event.EventType.CHILD_CHANGED)) {
/* 33 */         this.changeMap.put(childKey, Change.childRemovedChange(childKey, oldChange.getOldIndexedNode()));
/* 34 */       } else if ((type == Event.EventType.CHILD_CHANGED) && (oldType == Event.EventType.CHILD_ADDED)) {
/* 35 */         this.changeMap.put(childKey, Change.childAddedChange(childKey, change.getIndexedNode()));
/* 36 */       } else if ((type == Event.EventType.CHILD_CHANGED) && (oldType == Event.EventType.CHILD_CHANGED)) {
/* 37 */         this.changeMap.put(childKey, Change.childChangedChange(childKey, change.getIndexedNode(), oldChange.getOldIndexedNode()));
/*    */       } else {
/* 39 */         throw new IllegalStateException("Illegal combination of changes: " + change + " occurred after " + oldChange);
/*    */       }
/*    */     } else {
/* 42 */       this.changeMap.put(change.getChildKey(), change);
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Change> getChanges() {
/* 47 */     return new ArrayList(this.changeMap.values());
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/filter/ChildChangeAccumulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */