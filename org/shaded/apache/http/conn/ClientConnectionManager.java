package org.shaded.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.shaded.apache.http.conn.routing.HttpRoute;
import org.shaded.apache.http.conn.scheme.SchemeRegistry;

public abstract interface ClientConnectionManager
{
  public abstract SchemeRegistry getSchemeRegistry();
  
  public abstract ClientConnectionRequest requestConnection(HttpRoute paramHttpRoute, Object paramObject);
  
  public abstract void releaseConnection(ManagedClientConnection paramManagedClientConnection, long paramLong, TimeUnit paramTimeUnit);
  
  public abstract void closeIdleConnections(long paramLong, TimeUnit paramTimeUnit);
  
  public abstract void closeExpiredConnections();
  
  public abstract void shutdown();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ClientConnectionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */