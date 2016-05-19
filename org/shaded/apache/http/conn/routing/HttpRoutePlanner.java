package org.shaded.apache.http.conn.routing;

import org.shaded.apache.http.HttpException;
import org.shaded.apache.http.HttpHost;
import org.shaded.apache.http.HttpRequest;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface HttpRoutePlanner
{
  public abstract HttpRoute determineRoute(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpContext paramHttpContext)
    throws HttpException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/routing/HttpRoutePlanner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */