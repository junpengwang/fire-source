package org.shaded.apache.http.io;

import java.io.IOException;
import org.shaded.apache.http.HttpException;
import org.shaded.apache.http.HttpMessage;

public abstract interface HttpMessageWriter
{
  public abstract void write(HttpMessage paramHttpMessage)
    throws IOException, HttpException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/io/HttpMessageWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */