package org.shaded.apache.http;

public abstract interface HttpRequestFactory
{
  public abstract HttpRequest newHttpRequest(RequestLine paramRequestLine)
    throws MethodNotSupportedException;
  
  public abstract HttpRequest newHttpRequest(String paramString1, String paramString2)
    throws MethodNotSupportedException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpRequestFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */