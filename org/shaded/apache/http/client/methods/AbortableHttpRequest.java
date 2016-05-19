package org.shaded.apache.http.client.methods;

import java.io.IOException;
import org.shaded.apache.http.conn.ClientConnectionRequest;
import org.shaded.apache.http.conn.ConnectionReleaseTrigger;

public abstract interface AbortableHttpRequest
{
  public abstract void setConnectionRequest(ClientConnectionRequest paramClientConnectionRequest)
    throws IOException;
  
  public abstract void setReleaseTrigger(ConnectionReleaseTrigger paramConnectionReleaseTrigger)
    throws IOException;
  
  public abstract void abort();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/methods/AbortableHttpRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */