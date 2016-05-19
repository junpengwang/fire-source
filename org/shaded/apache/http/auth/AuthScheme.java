package org.shaded.apache.http.auth;

import org.shaded.apache.http.Header;
import org.shaded.apache.http.HttpRequest;

public abstract interface AuthScheme
{
  public abstract void processChallenge(Header paramHeader)
    throws MalformedChallengeException;
  
  public abstract String getSchemeName();
  
  public abstract String getParameter(String paramString);
  
  public abstract String getRealm();
  
  public abstract boolean isConnectionBased();
  
  public abstract boolean isComplete();
  
  public abstract Header authenticate(Credentials paramCredentials, HttpRequest paramHttpRequest)
    throws AuthenticationException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/AuthScheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */