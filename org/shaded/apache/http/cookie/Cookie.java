package org.shaded.apache.http.cookie;

import java.util.Date;

public abstract interface Cookie
{
  public abstract String getName();
  
  public abstract String getValue();
  
  public abstract String getComment();
  
  public abstract String getCommentURL();
  
  public abstract Date getExpiryDate();
  
  public abstract boolean isPersistent();
  
  public abstract String getDomain();
  
  public abstract String getPath();
  
  public abstract int[] getPorts();
  
  public abstract boolean isSecure();
  
  public abstract int getVersion();
  
  public abstract boolean isExpired(Date paramDate);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/Cookie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */