package org.shaded.apache.http;

import java.io.IOException;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface HttpRequestInterceptor
{
  public abstract void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
    throws HttpException, IOException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpRequestInterceptor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */