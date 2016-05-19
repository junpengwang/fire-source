package org.shaded.apache.http;

import org.shaded.apache.http.protocol.HttpContext;

public abstract interface ConnectionReuseStrategy
{
  public abstract boolean keepAlive(HttpResponse paramHttpResponse, HttpContext paramHttpContext);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/ConnectionReuseStrategy.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */