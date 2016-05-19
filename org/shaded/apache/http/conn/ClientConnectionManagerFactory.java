package org.shaded.apache.http.conn;

import org.shaded.apache.http.conn.scheme.SchemeRegistry;
import org.shaded.apache.http.params.HttpParams;

public abstract interface ClientConnectionManagerFactory
{
  public abstract ClientConnectionManager newInstance(HttpParams paramHttpParams, SchemeRegistry paramSchemeRegistry);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ClientConnectionManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */