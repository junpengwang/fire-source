package org.shaded.apache.http;

import java.io.IOException;

public abstract interface HttpServerConnection
  extends HttpConnection
{
  public abstract HttpRequest receiveRequestHeader()
    throws HttpException, IOException;
  
  public abstract void receiveRequestEntity(HttpEntityEnclosingRequest paramHttpEntityEnclosingRequest)
    throws HttpException, IOException;
  
  public abstract void sendResponseHeader(HttpResponse paramHttpResponse)
    throws HttpException, IOException;
  
  public abstract void sendResponseEntity(HttpResponse paramHttpResponse)
    throws HttpException, IOException;
  
  public abstract void flush()
    throws IOException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpServerConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */