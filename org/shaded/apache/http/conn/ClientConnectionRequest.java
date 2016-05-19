package org.shaded.apache.http.conn;

import java.util.concurrent.TimeUnit;

public abstract interface ClientConnectionRequest
{
  public abstract ManagedClientConnection getConnection(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ConnectionPoolTimeoutException;
  
  public abstract void abortRequest();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ClientConnectionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */