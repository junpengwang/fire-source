package org.shaded.apache.http;

public abstract interface Header
{
  public abstract String getName();
  
  public abstract String getValue();
  
  public abstract HeaderElement[] getElements()
    throws ParseException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/Header.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */