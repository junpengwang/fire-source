/*    */ package com.firebase.client.core;
/*    */ 
/*    */ import com.firebase.client.ChildEventListener;
/*    */ import com.firebase.client.core.view.Change;
/*    */ import com.firebase.client.core.view.DataEvent;
/*    */ import com.firebase.client.core.view.QuerySpec;
/*    */ 
/*    */ public class ChildEventRegistration implements EventRegistration
/*    */ {
/*    */   private final Repo repo;
/*    */   private final ChildEventListener eventListener;
/*    */   
/*    */   public ChildEventRegistration(Repo repo, ChildEventListener eventListener)
/*    */   {
/* 15 */     this.repo = repo;
/* 16 */     this.eventListener = eventListener;
/*    */   }
/*    */   
/*    */   public boolean respondsTo(com.firebase.client.core.view.Event.EventType eventType)
/*    */   {
/* 21 */     return eventType != com.firebase.client.core.view.Event.EventType.VALUE;
/*    */   }
/*    */   
/*    */   public boolean equals(Object other)
/*    */   {
/* 26 */     return ((other instanceof ChildEventRegistration)) && (((ChildEventRegistration)other).eventListener.equals(this.eventListener));
/*    */   }
/*    */   
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 32 */     return this.eventListener.hashCode();
/*    */   }
/*    */   
/*    */   public DataEvent createEvent(Change change, QuerySpec query)
/*    */   {
/* 37 */     com.firebase.client.Firebase ref = new com.firebase.client.Firebase(this.repo, query.getPath().child(change.getChildKey()));
/*    */     
/* 39 */     com.firebase.client.DataSnapshot snapshot = new com.firebase.client.DataSnapshot(ref, change.getIndexedNode());
/* 40 */     String prevName = change.getPrevName() != null ? change.getPrevName().asString() : null;
/* 41 */     return new DataEvent(change.getEventType(), this, snapshot, prevName);
/*    */   }
/*    */   
/*    */   public void fireEvent(DataEvent eventData)
/*    */   {
/* 46 */     switch (eventData.getEventType()) {
/*    */     case CHILD_ADDED: 
/* 48 */       this.eventListener.onChildAdded(eventData.getSnapshot(), eventData.getPreviousName());
/* 49 */       break;
/*    */     case CHILD_CHANGED: 
/* 51 */       this.eventListener.onChildChanged(eventData.getSnapshot(), eventData.getPreviousName());
/* 52 */       break;
/*    */     case CHILD_MOVED: 
/* 54 */       this.eventListener.onChildMoved(eventData.getSnapshot(), eventData.getPreviousName());
/* 55 */       break;
/*    */     case CHILD_REMOVED: 
/* 57 */       this.eventListener.onChildRemoved(eventData.getSnapshot());
/* 58 */       break;
/*    */     }
/*    */     
/*    */   }
/*    */   
/*    */ 
/*    */   public void fireCancelEvent(com.firebase.client.FirebaseError error)
/*    */   {
/* 66 */     this.eventListener.onCancelled(error);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 71 */     return "ChildEventRegistration";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/ChildEventRegistration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */