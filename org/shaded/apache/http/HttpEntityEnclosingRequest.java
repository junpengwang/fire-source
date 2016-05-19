package org.shaded.apache.http;

public abstract interface HttpEntityEnclosingRequest
  extends HttpRequest
{
  public abstract boolean expectContinue();
  
  public abstract void setEntity(HttpEntity paramHttpEntity);
  
  public abstract HttpEntity getEntity();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpEntityEnclosingRequest.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */