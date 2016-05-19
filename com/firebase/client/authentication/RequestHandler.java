package com.firebase.client.authentication;

import java.io.IOException;
import java.util.Map;

abstract interface RequestHandler
{
  public abstract void onResult(Map<String, Object> paramMap);
  
  public abstract void onError(IOException paramIOException);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/authentication/RequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */