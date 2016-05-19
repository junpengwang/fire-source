/*    */ package com.firebase.client.core.view;
/*    */ 
/*    */ import com.firebase.client.EventTarget;
/*    */ import com.firebase.client.core.Context;
/*    */ import com.firebase.client.utilities.LogWrapper;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventRaiser
/*    */ {
/*    */   private final EventTarget eventTarget;
/*    */   private final LogWrapper logger;
/*    */   
/*    */   public EventRaiser(Context ctx)
/*    */   {
/* 25 */     this.eventTarget = ctx.getEventTarget();
/* 26 */     this.logger = ctx.getLogger("EventRaiser");
/*    */   }
/*    */   
/*    */   public void raiseEvents(List<? extends Event> events) {
/* 30 */     if (this.logger.logsDebug()) { this.logger.debug("Raising " + events.size() + " event(s)");
/*    */     }
/* 32 */     final ArrayList<Event> eventsClone = new ArrayList(events);
/* 33 */     this.eventTarget.postEvent(new Runnable()
/*    */     {
/*    */       public void run() {
/* 36 */         for (Event event : eventsClone) {
/* 37 */           if (EventRaiser.this.logger.logsDebug()) EventRaiser.this.logger.debug("Raising " + event.toString());
/* 38 */           event.fire();
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/EventRaiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */