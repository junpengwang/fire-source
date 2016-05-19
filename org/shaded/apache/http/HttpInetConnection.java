package org.shaded.apache.http;

import java.net.InetAddress;

public abstract interface HttpInetConnection
  extends HttpConnection
{
  public abstract InetAddress getLocalAddress();
  
  public abstract int getLocalPort();
  
  public abstract InetAddress getRemoteAddress();
  
  public abstract int getRemotePort();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpInetConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */