package org.shaded.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;

public abstract interface EofSensorWatcher
{
  public abstract boolean eofDetected(InputStream paramInputStream)
    throws IOException;
  
  public abstract boolean streamClosed(InputStream paramInputStream)
    throws IOException;
  
  public abstract boolean streamAbort(InputStream paramInputStream)
    throws IOException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/EofSensorWatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */