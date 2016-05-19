package com.firebase.client;

public abstract interface EventTarget
{
  public abstract void postEvent(Runnable paramRunnable);
  
  public abstract void shutdown();
  
  public abstract void restart();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/EventTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */