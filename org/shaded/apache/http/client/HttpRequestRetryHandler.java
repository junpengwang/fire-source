package org.shaded.apache.http.client;

import java.io.IOException;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface HttpRequestRetryHandler
{
  public abstract boolean retryRequest(IOException paramIOException, int paramInt, HttpContext paramHttpContext);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/HttpRequestRetryHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */