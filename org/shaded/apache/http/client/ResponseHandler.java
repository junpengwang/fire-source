package org.shaded.apache.http.client;

import java.io.IOException;
import org.shaded.apache.http.HttpResponse;

public abstract interface ResponseHandler<T>
{
  public abstract T handleResponse(HttpResponse paramHttpResponse)
    throws ClientProtocolException, IOException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/ResponseHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */