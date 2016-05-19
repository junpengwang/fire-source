package org.shaded.apache.http.protocol;

public abstract interface HttpContext
{
  public static final String RESERVED_PREFIX = "http.";
  
  public abstract Object getAttribute(String paramString);
  
  public abstract void setAttribute(String paramString, Object paramObject);
  
  public abstract Object removeAttribute(String paramString);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/HttpContext.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */