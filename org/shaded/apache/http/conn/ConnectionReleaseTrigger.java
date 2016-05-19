package org.shaded.apache.http.conn;

import java.io.IOException;

public abstract interface ConnectionReleaseTrigger
{
  public abstract void releaseConnection()
    throws IOException;
  
  public abstract void abortConnection()
    throws IOException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ConnectionReleaseTrigger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */