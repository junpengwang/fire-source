package com.shaded.fasterxml.jackson.databind.jsonFormatVisitors;

public abstract interface JsonStringFormatVisitor
  extends JsonValueFormatVisitor
{
  public static class Base
    extends JsonValueFormatVisitor.Base
    implements JsonStringFormatVisitor
  {}
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonFormatVisitors/JsonStringFormatVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */