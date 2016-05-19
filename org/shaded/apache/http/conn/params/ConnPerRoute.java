package org.shaded.apache.http.conn.params;

import org.shaded.apache.http.conn.routing.HttpRoute;

public abstract interface ConnPerRoute
{
  public abstract int getMaxForRoute(HttpRoute paramHttpRoute);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/params/ConnPerRoute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */