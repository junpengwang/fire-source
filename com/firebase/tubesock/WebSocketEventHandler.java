package com.firebase.tubesock;

public abstract interface WebSocketEventHandler
{
  public abstract void onOpen();
  
  public abstract void onMessage(WebSocketMessage paramWebSocketMessage);
  
  public abstract void onClose();
  
  public abstract void onError(WebSocketException paramWebSocketException);
  
  public abstract void onLogMessage(String paramString);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/WebSocketEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */