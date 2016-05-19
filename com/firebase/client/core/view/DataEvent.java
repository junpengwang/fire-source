/*    */ package com.firebase.client.core.view;
/*    */ 
/*    */ import com.firebase.client.DataSnapshot;
/*    */ import com.firebase.client.core.EventRegistration;
/*    */ 
/*    */ public class DataEvent implements Event
/*    */ {
/*    */   private final Event.EventType eventType;
/*    */   private final EventRegistration eventRegistration;
/*    */   private final DataSnapshot snapshot;
/*    */   private final String prevName;
/*    */   
/*    */   public DataEvent(Event.EventType eventType, EventRegistration eventRegistration, DataSnapshot snapshot, String prevName)
/*    */   {
/* 15 */     this.eventType = eventType;
/* 16 */     this.eventRegistration = eventRegistration;
/* 17 */     this.snapshot = snapshot;
/* 18 */     this.prevName = prevName;
/*    */   }
/*    */   
/*    */   public com.firebase.client.core.Path getPath()
/*    */   {
/* 23 */     com.firebase.client.core.Path path = this.snapshot.getRef().getPath();
/* 24 */     if (this.eventType == Event.EventType.VALUE) {
/* 25 */       return path;
/*    */     }
/* 27 */     return path.getParent();
/*    */   }
/*    */   
/*    */   public DataSnapshot getSnapshot()
/*    */   {
/* 32 */     return this.snapshot;
/*    */   }
/*    */   
/*    */   public String getPreviousName() {
/* 36 */     return this.prevName;
/*    */   }
/*    */   
/*    */   public Event.EventType getEventType() {
/* 40 */     return this.eventType;
/*    */   }
/*    */   
/*    */   public void fire()
/*    */   {
/* 45 */     this.eventRegistration.fireEvent(this);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 50 */     if (this.eventType == Event.EventType.VALUE) {
/* 51 */       return getPath() + ": " + this.eventType + ": " + this.snapshot.getValue(true);
/*    */     }
/* 53 */     return getPath() + ": " + this.eventType + ": { " + this.snapshot.getKey() + ": " + this.snapshot.getValue(true) + " }";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/DataEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */