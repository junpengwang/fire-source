package com.shaded.fasterxml.jackson.databind.jsonFormatVisitors;

import java.util.Set;

public abstract interface JsonValueFormatVisitor
{
  public abstract void format(JsonValueFormat paramJsonValueFormat);
  
  public abstract void enumTypes(Set<String> paramSet);
  
  public static class Base
    implements JsonValueFormatVisitor
  {
    public void format(JsonValueFormat paramJsonValueFormat) {}
    
    public void enumTypes(Set<String> paramSet) {}
  }
}


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonFormatVisitors/JsonValueFormatVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */