package com.firebase.client.core;

import com.firebase.client.CredentialStore;
import com.firebase.client.EventTarget;
import com.firebase.client.Logger;
import com.firebase.client.Logger.Level;
import com.firebase.client.RunLoop;
import com.firebase.client.core.persistence.PersistenceManager;
import java.util.List;

public abstract interface Platform
{
  public abstract Logger newLogger(Context paramContext, Logger.Level paramLevel, List<String> paramList);
  
  public abstract EventTarget newEventTarget(Context paramContext);
  
  public abstract RunLoop newRunLoop(Context paramContext);
  
  public abstract CredentialStore newCredentialStore(Context paramContext);
  
  public abstract String getUserAgent(Context paramContext);
  
  public abstract String getPlatformVersion();
  
  public abstract PersistenceManager createPersistenceManager(Context paramContext, String paramString);
  
  public abstract void runBackgroundTask(Context paramContext, Runnable paramRunnable);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */