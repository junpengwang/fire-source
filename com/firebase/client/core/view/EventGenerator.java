/*    */ package com.firebase.client.core.view;
/*    */ 
/*    */ import com.firebase.client.core.EventRegistration;
/*    */ import com.firebase.client.snapshot.IndexedNode;
/*    */ import com.firebase.client.snapshot.NamedNode;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class EventGenerator
/*    */ {
/*    */   private final QuerySpec query;
/*    */   private final com.firebase.client.snapshot.Index index;
/*    */   
/*    */   public EventGenerator(QuerySpec query)
/*    */   {
/* 17 */     this.query = query;
/* 18 */     this.index = query.getIndex();
/*    */   }
/*    */   
/*    */   private void generateEventsForType(List<DataEvent> events, Event.EventType type, List<Change> changes, List<EventRegistration> eventRegistrations, IndexedNode eventCache) {
/* 22 */     List<Change> filteredChanges = new ArrayList();
/* 23 */     for (Change change : changes) {
/* 24 */       if (change.getEventType().equals(type)) {
/* 25 */         filteredChanges.add(change);
/*    */       }
/*    */     }
/* 28 */     java.util.Collections.sort(filteredChanges, changeComparator());
/* 29 */     for (Iterator i$ = filteredChanges.iterator(); i$.hasNext();) { change = (Change)i$.next();
/* 30 */       for (EventRegistration registration : eventRegistrations)
/* 31 */         if (registration.respondsTo(type))
/* 32 */           events.add(generateEvent(change, registration, eventCache));
/*    */     }
/*    */     Change change;
/*    */   }
/*    */   
/*    */   private DataEvent generateEvent(Change change, EventRegistration registration, IndexedNode eventCache) {
/*    */     Change newChange;
/*    */     Change newChange;
/* 40 */     if ((change.getEventType().equals(Event.EventType.VALUE)) || (change.getEventType().equals(Event.EventType.CHILD_REMOVED))) {
/* 41 */       newChange = change;
/*    */     } else {
/* 43 */       com.firebase.client.snapshot.ChildKey prevChildKey = eventCache.getPredecessorChildName(change.getChildKey(), change.getIndexedNode().getNode(), this.index);
/* 44 */       newChange = change.changeWithPrevName(prevChildKey);
/*    */     }
/* 46 */     return registration.createEvent(newChange, this.query);
/*    */   }
/*    */   
/*    */   public List<DataEvent> generateEventsForChanges(List<Change> changes, IndexedNode eventCache, List<EventRegistration> eventRegistrations) {
/* 50 */     List<DataEvent> events = new ArrayList();
/*    */     
/* 52 */     List<Change> moves = new ArrayList();
/* 53 */     for (Change change : changes) {
/* 54 */       if ((change.getEventType().equals(Event.EventType.CHILD_CHANGED)) && (this.index.indexedValueChanged(change.getOldIndexedNode().getNode(), change.getIndexedNode().getNode())))
/*    */       {
/* 56 */         moves.add(Change.childMovedChange(change.getChildKey(), change.getIndexedNode()));
/*    */       }
/*    */     }
/*    */     
/* 60 */     generateEventsForType(events, Event.EventType.CHILD_REMOVED, changes, eventRegistrations, eventCache);
/* 61 */     generateEventsForType(events, Event.EventType.CHILD_ADDED, changes, eventRegistrations, eventCache);
/* 62 */     generateEventsForType(events, Event.EventType.CHILD_MOVED, moves, eventRegistrations, eventCache);
/* 63 */     generateEventsForType(events, Event.EventType.CHILD_CHANGED, changes, eventRegistrations, eventCache);
/* 64 */     generateEventsForType(events, Event.EventType.VALUE, changes, eventRegistrations, eventCache);
/*    */     
/* 66 */     return events;
/*    */   }
/*    */   
/*    */   private java.util.Comparator<Change> changeComparator() {
/* 70 */     new java.util.Comparator()
/*    */     {
/*    */       public int compare(Change a, Change b)
/*    */       {
/* 74 */         assert ((a.getChildKey() != null) && (b.getChildKey() != null));
/* 75 */         NamedNode namedNodeA = new NamedNode(a.getChildKey(), a.getIndexedNode().getNode());
/* 76 */         NamedNode namedNodeB = new NamedNode(b.getChildKey(), b.getIndexedNode().getNode());
/* 77 */         return EventGenerator.this.index.compare(namedNodeA, namedNodeB);
/*    */       }
/*    */     };
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/EventGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */