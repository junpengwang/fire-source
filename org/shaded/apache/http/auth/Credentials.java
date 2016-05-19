package org.shaded.apache.http.auth;

import java.security.Principal;

public abstract interface Credentials
{
  public abstract Principal getUserPrincipal();
  
  public abstract String getPassword();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/Credentials.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */