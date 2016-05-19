package com.firebase.client;

public abstract interface ChildEventListener
{
  public abstract void onChildAdded(DataSnapshot paramDataSnapshot, String paramString);
  
  public abstract void onChildChanged(DataSnapshot paramDataSnapshot, String paramString);
  
  public abstract void onChildRemoved(DataSnapshot paramDataSnapshot);
  
  public abstract void onChildMoved(DataSnapshot paramDataSnapshot, String paramString);
  
  public abstract void onCancelled(FirebaseError paramFirebaseError);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/ChildEventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */