package com.shaded.fasterxml.jackson.databind.deser;

import com.shaded.fasterxml.jackson.databind.DeserializationContext;
import com.shaded.fasterxml.jackson.databind.JsonMappingException;

public abstract interface ResolvableDeserializer
{
  public abstract void resolve(DeserializationContext paramDeserializationContext)
    throws JsonMappingException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/ResolvableDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */