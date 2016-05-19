/*   */ package com.firebase.client.core.view;
/*   */ 
/*   */ import com.firebase.client.core.Path;
/*   */ 
/*   */ public abstract interface Event {
/*   */   public abstract Path getPath();
/*   */   
/*   */   public static enum EventType {
/* 9 */     CHILD_REMOVED,  CHILD_ADDED,  CHILD_MOVED,  CHILD_CHANGED,  VALUE;
/*   */     
/*   */     private EventType() {}
/*   */   }
/*   */   
/*   */   public abstract void fire();
/*   */   
/*   */   public abstract String toString();
/*   */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/Event.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */