/*    */ package com.shaded.fasterxml.jackson.databind.jsonFormatVisitors;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonCreator;
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonValue;
/*    */ 
/*    */ public enum JsonFormatTypes
/*    */ {
/*  8 */   STRING, 
/*  9 */   NUMBER, 
/* 10 */   INTEGER, 
/* 11 */   BOOLEAN, 
/* 12 */   OBJECT, 
/* 13 */   ARRAY, 
/* 14 */   NULL, 
/* 15 */   ANY;
/*    */   
/*    */   private JsonFormatTypes() {}
/*    */   
/*    */   @JsonValue
/* 20 */   public String value() { return name().toLowerCase(); }
/*    */   
/*    */   @JsonCreator
/*    */   public static JsonFormatTypes forValue(String paramString)
/*    */   {
/* 25 */     return valueOf(paramString.toUpperCase());
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonFormatVisitors/JsonFormatTypes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */