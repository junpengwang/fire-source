package org.shaded.apache.http.cookie;

import java.util.List;
import org.shaded.apache.http.Header;

public abstract interface CookieSpec
{
  public abstract int getVersion();
  
  public abstract List<Cookie> parse(Header paramHeader, CookieOrigin paramCookieOrigin)
    throws MalformedCookieException;
  
  public abstract void validate(Cookie paramCookie, CookieOrigin paramCookieOrigin)
    throws MalformedCookieException;
  
  public abstract boolean match(Cookie paramCookie, CookieOrigin paramCookieOrigin);
  
  public abstract List<Header> formatCookies(List<Cookie> paramList);
  
  public abstract Header getVersionHeader();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/CookieSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */