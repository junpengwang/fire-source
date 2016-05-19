/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
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
/*    */ public final class TypeWrappedSerializer
/*    */   extends JsonSerializer<Object>
/*    */ {
/*    */   protected final TypeSerializer _typeSerializer;
/*    */   protected final JsonSerializer<Object> _serializer;
/*    */   
/*    */   public TypeWrappedSerializer(TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*    */   {
/* 27 */     this._typeSerializer = paramTypeSerializer;
/* 28 */     this._serializer = paramJsonSerializer;
/*    */   }
/*    */   
/*    */ 
/*    */   public void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 35 */     this._serializer.serializeWithType(paramObject, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 46 */     this._serializer.serializeWithType(paramObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
/*    */   }
/*    */   
/*    */   public Class<Object> handledType() {
/* 50 */     return Object.class;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/TypeWrappedSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */