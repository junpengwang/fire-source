/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializable;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JSONPObject
/*    */   implements JsonSerializable
/*    */ {
/*    */   protected final String _function;
/*    */   protected final Object _value;
/*    */   protected final JavaType _serializationType;
/*    */   
/*    */   public JSONPObject(String paramString, Object paramObject)
/*    */   {
/* 41 */     this(paramString, paramObject, (JavaType)null);
/*    */   }
/*    */   
/*    */   public JSONPObject(String paramString, Object paramObject, JavaType paramJavaType)
/*    */   {
/* 46 */     this._function = paramString;
/* 47 */     this._value = paramObject;
/* 48 */     this._serializationType = paramJavaType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 62 */     serialize(paramJsonGenerator, paramSerializerProvider);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 70 */     paramJsonGenerator.writeRaw(this._function);
/* 71 */     paramJsonGenerator.writeRaw('(');
/* 72 */     if (this._value == null) {
/* 73 */       paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/* 74 */     } else if (this._serializationType != null) {
/* 75 */       paramSerializerProvider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, paramJsonGenerator, paramSerializerProvider);
/*    */     } else {
/* 77 */       Class localClass = this._value.getClass();
/* 78 */       paramSerializerProvider.findTypedValueSerializer(localClass, true, null).serialize(this._value, paramJsonGenerator, paramSerializerProvider);
/*    */     }
/* 80 */     paramJsonGenerator.writeRaw(')');
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 89 */   public String getFunction() { return this._function; }
/* 90 */   public Object getValue() { return this._value; }
/* 91 */   public JavaType getSerializationType() { return this._serializationType; }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/JSONPObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */