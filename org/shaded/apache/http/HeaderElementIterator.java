package org.shaded.apache.http;

import java.util.Iterator;

public abstract interface HeaderElementIterator
  extends Iterator
{
  public abstract boolean hasNext();
  
  public abstract HeaderElement nextElement();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HeaderElementIterator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */