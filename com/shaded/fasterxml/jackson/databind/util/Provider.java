package com.shaded.fasterxml.jackson.databind.util;

import java.util.Collection;

@Deprecated
public abstract interface Provider<T>
{
  public abstract Collection<T> provide();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/Provider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */