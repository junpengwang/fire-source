package org.shaded.apache.http.client;

import java.util.Date;
import java.util.List;
import org.shaded.apache.http.cookie.Cookie;

public abstract interface CookieStore
{
  public abstract void addCookie(Cookie paramCookie);
  
  public abstract List<Cookie> getCookies();
  
  public abstract boolean clearExpired(Date paramDate);
  
  public abstract void clear();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/CookieStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */