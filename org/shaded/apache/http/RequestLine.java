package org.shaded.apache.http;

public abstract interface RequestLine
{
  public abstract String getMethod();
  
  public abstract ProtocolVersion getProtocolVersion();
  
  public abstract String getUri();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/RequestLine.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */