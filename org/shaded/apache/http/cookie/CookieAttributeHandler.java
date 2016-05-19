package org.shaded.apache.http.cookie;

public abstract interface CookieAttributeHandler
{
  public abstract void parse(SetCookie paramSetCookie, String paramString)
    throws MalformedCookieException;
  
  public abstract void validate(Cookie paramCookie, CookieOrigin paramCookieOrigin)
    throws MalformedCookieException;
  
  public abstract boolean match(Cookie paramCookie, CookieOrigin paramCookieOrigin);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/CookieAttributeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */