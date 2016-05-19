/*    */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
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
/*    */ public final class TypeWrappedDeserializer
/*    */   extends JsonDeserializer<Object>
/*    */ {
/*    */   final TypeDeserializer _typeDeserializer;
/*    */   final JsonDeserializer<Object> _deserializer;
/*    */   
/*    */   public TypeWrappedDeserializer(TypeDeserializer paramTypeDeserializer, JsonDeserializer<Object> paramJsonDeserializer)
/*    */   {
/* 28 */     this._typeDeserializer = paramTypeDeserializer;
/* 29 */     this._deserializer = paramJsonDeserializer;
/*    */   }
/*    */   
/*    */ 
/*    */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 36 */     return this._deserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, this._typeDeserializer);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 45 */     throw new IllegalStateException("Type-wrapped deserializer's deserializeWithType should never get called");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 56 */     return this._deserializer.deserialize(paramJsonParser, paramDeserializationContext, paramObject);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/TypeWrappedDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */