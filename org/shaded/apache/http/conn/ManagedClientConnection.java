package org.shaded.apache.http.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import org.shaded.apache.http.HttpClientConnection;
import org.shaded.apache.http.HttpHost;
import org.shaded.apache.http.HttpInetConnection;
import org.shaded.apache.http.conn.routing.HttpRoute;
import org.shaded.apache.http.params.HttpParams;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface ManagedClientConnection
  extends HttpClientConnection, HttpInetConnection, ConnectionReleaseTrigger
{
  public abstract boolean isSecure();
  
  public abstract HttpRoute getRoute();
  
  public abstract SSLSession getSSLSession();
  
  public abstract void open(HttpRoute paramHttpRoute, HttpContext paramHttpContext, HttpParams paramHttpParams)
    throws IOException;
  
  public abstract void tunnelTarget(boolean paramBoolean, HttpParams paramHttpParams)
    throws IOException;
  
  public abstract void tunnelProxy(HttpHost paramHttpHost, boolean paramBoolean, HttpParams paramHttpParams)
    throws IOException;
  
  public abstract void layerProtocol(HttpContext paramHttpContext, HttpParams paramHttpParams)
    throws IOException;
  
  public abstract void markReusable();
  
  public abstract void unmarkReusable();
  
  public abstract boolean isMarkedReusable();
  
  public abstract void setState(Object paramObject);
  
  public abstract Object getState();
  
  public abstract void setIdleDuration(long paramLong, TimeUnit paramTimeUnit);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ManagedClientConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */