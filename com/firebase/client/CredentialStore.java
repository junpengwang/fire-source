package com.firebase.client;

public abstract interface CredentialStore
{
  public abstract String loadCredential(String paramString1, String paramString2);
  
  public abstract boolean storeCredential(String paramString1, String paramString2, String paramString3);
  
  public abstract boolean clearCredential(String paramString1, String paramString2);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/CredentialStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */