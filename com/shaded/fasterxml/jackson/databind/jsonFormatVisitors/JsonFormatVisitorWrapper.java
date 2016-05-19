package com.shaded.fasterxml.jackson.databind.jsonFormatVisitors;

import com.shaded.fasterxml.jackson.databind.JavaType;
import com.shaded.fasterxml.jackson.databind.JsonMappingException;

public abstract interface JsonFormatVisitorWrapper
  extends JsonFormatVisitorWithSerializerProvider
{
  public abstract JsonObjectFormatVisitor expectObjectFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonArrayFormatVisitor expectArrayFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonStringFormatVisitor expectStringFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonNumberFormatVisitor expectNumberFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonIntegerFormatVisitor expectIntegerFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonBooleanFormatVisitor expectBooleanFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonNullFormatVisitor expectNullFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonAnyFormatVisitor expectAnyFormat(JavaType paramJavaType)
    throws JsonMappingException;
  
  public abstract JsonMapFormatVisitor expectMapFormat(JavaType paramJavaType)
    throws JsonMappingException;
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonFormatVisitors/JsonFormatVisitorWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */