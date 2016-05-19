package org.shaded.apache.http;

import java.io.IOException;

public abstract interface HttpClientConnection
  extends HttpConnection
{
  public abstract boolean isResponseAvailable(int paramInt)
    throws IOException;
  
  public abstract void sendRequestHeader(HttpRequest paramHttpRequest)
    throws HttpException, IOException;
  
  public abstract void sendRequestEntity(HttpEntityEnclosingRequest paramHttpEntityEnclosingRequest)
    throws HttpException, IOException;
  
  public abstract HttpResponse receiveResponseHeader()
    throws HttpException, IOException;
  
  public abstract void receiveResponseEntity(HttpResponse paramHttpResponse)
    throws HttpException, IOException;
  
  public abstract void flush()
    throws IOException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpClientConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */