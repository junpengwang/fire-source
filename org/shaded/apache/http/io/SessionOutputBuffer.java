package org.shaded.apache.http.io;

import java.io.IOException;
import org.shaded.apache.http.util.CharArrayBuffer;

public abstract interface SessionOutputBuffer
{
  public abstract void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract void write(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract void write(int paramInt)
    throws IOException;
  
  public abstract void writeLine(String paramString)
    throws IOException;
  
  public abstract void writeLine(CharArrayBuffer paramCharArrayBuffer)
    throws IOException;
  
  public abstract void flush()
    throws IOException;
  
  public abstract HttpTransportMetrics getMetrics();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/io/SessionOutputBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */