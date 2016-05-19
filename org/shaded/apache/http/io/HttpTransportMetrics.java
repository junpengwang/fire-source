package org.shaded.apache.http.io;

public abstract interface HttpTransportMetrics
{
  public abstract long getBytesTransferred();
  
  public abstract void reset();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/io/HttpTransportMetrics.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */