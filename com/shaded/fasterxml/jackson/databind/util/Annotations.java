package com.shaded.fasterxml.jackson.databind.util;

import java.lang.annotation.Annotation;

public abstract interface Annotations
{
  public abstract <A extends Annotation> A get(Class<A> paramClass);
  
  public abstract int size();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/Annotations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */