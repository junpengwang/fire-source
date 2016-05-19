package org.shaded.apache.http.client;

import java.util.Map;
import org.shaded.apache.http.Header;
import org.shaded.apache.http.HttpResponse;
import org.shaded.apache.http.auth.AuthScheme;
import org.shaded.apache.http.auth.AuthenticationException;
import org.shaded.apache.http.auth.MalformedChallengeException;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface AuthenticationHandler
{
  public abstract boolean isAuthenticationRequested(HttpResponse paramHttpResponse, HttpContext paramHttpContext);
  
  public abstract Map<String, Header> getChallenges(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
    throws MalformedChallengeException;
  
  public abstract AuthScheme selectScheme(Map<String, Header> paramMap, HttpResponse paramHttpResponse, HttpContext paramHttpContext)
    throws AuthenticationException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/AuthenticationHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */