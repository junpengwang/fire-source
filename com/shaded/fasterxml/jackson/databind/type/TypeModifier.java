package com.shaded.fasterxml.jackson.databind.type;

import com.shaded.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.Type;

public abstract class TypeModifier
{
  public abstract JavaType modifyType(JavaType paramJavaType, Type paramType, TypeBindings paramTypeBindings, TypeFactory paramTypeFactory);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/TypeModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */