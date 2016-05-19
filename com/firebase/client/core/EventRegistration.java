package com.firebase.client.core;

import com.firebase.client.FirebaseError;
import com.firebase.client.core.view.Change;
import com.firebase.client.core.view.DataEvent;
import com.firebase.client.core.view.Event.EventType;
import com.firebase.client.core.view.QuerySpec;

public abstract interface EventRegistration
{
  public abstract boolean respondsTo(Event.EventType paramEventType);
  
  public abstract boolean equals(Object paramObject);
  
  public abstract DataEvent createEvent(Change paramChange, QuerySpec paramQuerySpec);
  
  public abstract void fireEvent(DataEvent paramDataEvent);
  
  public abstract void fireCancelEvent(FirebaseError paramFirebaseError);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/EventRegistration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */