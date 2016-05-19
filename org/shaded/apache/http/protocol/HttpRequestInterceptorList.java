package org.shaded.apache.http.protocol;

import java.util.List;
import org.shaded.apache.http.HttpRequestInterceptor;

public abstract interface HttpRequestInterceptorList
{
  public abstract void addRequestInterceptor(HttpRequestInterceptor paramHttpRequestInterceptor);
  
  public abstract void addRequestInterceptor(HttpRequestInterceptor paramHttpRequestInterceptor, int paramInt);
  
  public abstract int getRequestInterceptorCount();
  
  public abstract HttpRequestInterceptor getRequestInterceptor(int paramInt);
  
  public abstract void clearRequestInterceptors();
  
  public abstract void removeRequestInterceptorByClass(Class paramClass);
  
  public abstract void setInterceptors(List paramList);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/HttpRequestInterceptorList.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */