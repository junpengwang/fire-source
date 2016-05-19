package org.shaded.apache.http.entity;

import org.shaded.apache.http.HttpException;
import org.shaded.apache.http.HttpMessage;

public abstract interface ContentLengthStrategy
{
  public static final int IDENTITY = -1;
  public static final int CHUNKED = -2;
  
  public abstract long determineLength(HttpMessage paramHttpMessage)
    throws HttpException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/ContentLengthStrategy.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */