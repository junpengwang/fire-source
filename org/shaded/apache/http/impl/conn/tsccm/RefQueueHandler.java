package org.shaded.apache.http.impl.conn.tsccm;

import java.lang.ref.Reference;

@Deprecated
public abstract interface RefQueueHandler
{
  public abstract void handleReference(Reference<?> paramReference);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/RefQueueHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */