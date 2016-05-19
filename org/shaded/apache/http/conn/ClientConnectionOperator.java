package org.shaded.apache.http.conn;

import java.io.IOException;
import java.net.InetAddress;
import org.shaded.apache.http.HttpHost;
import org.shaded.apache.http.params.HttpParams;
import org.shaded.apache.http.protocol.HttpContext;

public abstract interface ClientConnectionOperator
{
  public abstract OperatedClientConnection createConnection();
  
  public abstract void openConnection(OperatedClientConnection paramOperatedClientConnection, HttpHost paramHttpHost, InetAddress paramInetAddress, HttpContext paramHttpContext, HttpParams paramHttpParams)
    throws IOException;
  
  public abstract void updateSecureConnection(OperatedClientConnection paramOperatedClientConnection, HttpHost paramHttpHost, HttpContext paramHttpContext, HttpParams paramHttpParams)
    throws IOException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ClientConnectionOperator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */