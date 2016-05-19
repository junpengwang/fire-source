package org.shaded.apache.http.client;

import java.net.URI;
import org.shaded.apache.http.HttpResponse;
import org.shaded.apache.http.ProtocolException;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface RedirectHandler
{
  public abstract boolean isRedirectRequested(HttpResponse paramHttpResponse, HttpContext paramHttpContext);
  
  public abstract URI getLocationURI(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
    throws ProtocolException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/RedirectHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */