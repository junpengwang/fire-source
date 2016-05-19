package com.firebase.client;

import java.util.concurrent.ScheduledFuture;

public abstract interface RunLoop
{
  public abstract void scheduleNow(Runnable paramRunnable);
  
  public abstract ScheduledFuture schedule(Runnable paramRunnable, long paramLong);
  
  public abstract void shutdown();
  
  public abstract void restart();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/RunLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */