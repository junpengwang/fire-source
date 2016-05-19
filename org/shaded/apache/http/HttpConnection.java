package org.shaded.apache.http;

import java.io.IOException;

public abstract interface HttpConnection
{
  public abstract void close()
    throws IOException;
  
  public abstract boolean isOpen();
  
  public abstract boolean isStale();
  
  public abstract void setSocketTimeout(int paramInt);
  
  public abstract int getSocketTimeout();
  
  public abstract void shutdown()
    throws IOException;
  
  public abstract HttpConnectionMetrics getMetrics();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */