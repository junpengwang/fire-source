/*    */ package com.firebase.client.core;
/*    */ 
/*    */ import com.firebase.client.ValueEventListener;
/*    */ import com.firebase.client.core.view.DataEvent;
/*    */ import com.firebase.client.core.view.Event.EventType;
/*    */ import com.firebase.client.core.view.QuerySpec;
/*    */ 
/*    */ public class ValueEventRegistration implements EventRegistration
/*    */ {
/*    */   private final Repo repo;
/*    */   private final ValueEventListener eventListener;
/*    */   
/*    */   public ValueEventRegistration(Repo repo, ValueEventListener eventListener)
/*    */   {
/* 15 */     this.repo = repo;
/* 16 */     this.eventListener = eventListener;
/*    */   }
/*    */   
/*    */   public boolean respondsTo(Event.EventType eventType)
/*    */   {
/* 21 */     return eventType == Event.EventType.VALUE;
/*    */   }
/*    */   
/*    */   public boolean equals(Object other)
/*    */   {
/* 26 */     return ((other instanceof ValueEventRegistration)) && (((ValueEventRegistration)other).eventListener.equals(this.eventListener));
/*    */   }
/*    */   
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 32 */     return this.eventListener.hashCode();
/*    */   }
/*    */   
/*    */   public DataEvent createEvent(com.firebase.client.core.view.Change change, QuerySpec query)
/*    */   {
/* 37 */     com.firebase.client.Firebase ref = new com.firebase.client.Firebase(this.repo, query.getPath());
/*    */     
/* 39 */     com.firebase.client.DataSnapshot dataSnapshot = new com.firebase.client.DataSnapshot(ref, change.getIndexedNode());
/* 40 */     return new DataEvent(Event.EventType.VALUE, this, dataSnapshot, null);
/*    */   }
/*    */   
/*    */   public void fireEvent(DataEvent eventData)
/*    */   {
/* 45 */     this.eventListener.onDataChange(eventData.getSnapshot());
/*    */   }
/*    */   
/*    */   public void fireCancelEvent(com.firebase.client.FirebaseError error)
/*    */   {
/* 50 */     this.eventListener.onCancelled(error);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 55 */     return "ValueEventRegistration";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/ValueEventRegistration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */