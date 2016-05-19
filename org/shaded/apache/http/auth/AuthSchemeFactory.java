package org.shaded.apache.http.auth;

import org.shaded.apache.http.params.HttpParams;

public abstract interface AuthSchemeFactory
{
  public abstract AuthScheme newInstance(HttpParams paramHttpParams);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/AuthSchemeFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */