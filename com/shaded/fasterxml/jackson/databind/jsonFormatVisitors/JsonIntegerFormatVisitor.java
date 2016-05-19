package com.shaded.fasterxml.jackson.databind.jsonFormatVisitors;

import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;

public abstract interface JsonIntegerFormatVisitor
  extends JsonValueFormatVisitor
{
  public abstract void numberType(JsonParser.NumberType paramNumberType);
  
  public static class Base
    extends JsonValueFormatVisitor.Base
    implements JsonIntegerFormatVisitor
  {
    public void numberType(JsonParser.NumberType paramNumberType) {}
  }
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonFormatVisitors/JsonIntegerFormatVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */