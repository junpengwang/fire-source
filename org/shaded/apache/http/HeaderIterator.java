package org.shaded.apache.http;

import java.util.Iterator;

public abstract interface HeaderIterator
  extends Iterator
{
  public abstract boolean hasNext();
  
  public abstract Header nextHeader();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HeaderIterator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */