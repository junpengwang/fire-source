package com.shaded.fasterxml.jackson.databind.jsontype;

import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
import com.shaded.fasterxml.jackson.databind.JavaType;
import com.shaded.fasterxml.jackson.databind.SerializationConfig;
import java.util.Collection;

public abstract interface TypeResolverBuilder<T extends TypeResolverBuilder<T>>
{
  public abstract Class<?> getDefaultImpl();
  
  public abstract TypeSerializer buildTypeSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection);
  
  public abstract TypeDeserializer buildTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection);
  
  public abstract T init(JsonTypeInfo.Id paramId, TypeIdResolver paramTypeIdResolver);
  
  public abstract T inclusion(JsonTypeInfo.As paramAs);
  
  public abstract T typeProperty(String paramString);
  
  public abstract T defaultImpl(Class<?> paramClass);
  
  public abstract T typeIdVisibility(boolean paramBoolean);
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/TypeResolverBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */