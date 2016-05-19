package com.shaded.fasterxml.jackson.databind.jsontype;

import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.shaded.fasterxml.jackson.databind.JavaType;

public abstract interface TypeIdResolver
{
  public abstract void init(JavaType paramJavaType);
  
  public abstract String idFromValue(Object paramObject);
  
  public abstract String idFromValueAndType(Object paramObject, Class<?> paramClass);
  
  public abstract String idFromBaseType();
  
  public abstract JavaType typeFromId(String paramString);
  
  public abstract JsonTypeInfo.Id getMechanism();
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/TypeIdResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */