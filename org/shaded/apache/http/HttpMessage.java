package org.shaded.apache.http;

import org.shaded.apache.http.params.HttpParams;

public abstract interface HttpMessage
{
  public abstract ProtocolVersion getProtocolVersion();
  
  public abstract boolean containsHeader(String paramString);
  
  public abstract Header[] getHeaders(String paramString);
  
  public abstract Header getFirstHeader(String paramString);
  
  public abstract Header getLastHeader(String paramString);
  
  public abstract Header[] getAllHeaders();
  
  public abstract void addHeader(Header paramHeader);
  
  public abstract void addHeader(String paramString1, String paramString2);
  
  public abstract void setHeader(Header paramHeader);
  
  public abstract void setHeader(String paramString1, String paramString2);
  
  public abstract void setHeaders(Header[] paramArrayOfHeader);
  
  public abstract void removeHeader(Header paramHeader);
  
  public abstract void removeHeaders(String paramString);
  
  public abstract HeaderIterator headerIterator();
  
  public abstract HeaderIterator headerIterator(String paramString);
  
  public abstract HttpParams getParams();
  
  public abstract void setParams(HttpParams paramHttpParams);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpMessage.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */