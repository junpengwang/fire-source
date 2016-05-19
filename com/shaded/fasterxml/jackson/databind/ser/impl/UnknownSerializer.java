/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.StdSerializer;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ public class UnknownSerializer extends StdSerializer<Object>
/*    */ {
/*    */   public UnknownSerializer()
/*    */   {
/* 17 */     super(Object.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonMappingException
/*    */   {
/* 25 */     if (paramSerializerProvider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
/* 26 */       failForEmpty(paramObject);
/*    */     }
/*    */     
/* 29 */     paramJsonGenerator.writeStartObject();
/* 30 */     paramJsonGenerator.writeEndObject();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public final void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, com.shaded.fasterxml.jackson.core.JsonGenerationException
/*    */   {
/* 38 */     if (paramSerializerProvider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
/* 39 */       failForEmpty(paramObject);
/*    */     }
/* 41 */     paramTypeSerializer.writeTypePrefixForObject(paramObject, paramJsonGenerator);
/* 42 */     paramTypeSerializer.writeTypeSuffixForObject(paramObject, paramJsonGenerator);
/*    */   }
/*    */   
/*    */   public com.shaded.fasterxml.jackson.databind.JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType) throws JsonMappingException
/*    */   {
/* 47 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, com.shaded.fasterxml.jackson.databind.JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 54 */     paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
/*    */   }
/*    */   
/*    */   protected void failForEmpty(Object paramObject) throws JsonMappingException
/*    */   {
/* 59 */     throw new JsonMappingException("No serializer found for class " + paramObject.getClass().getName() + " and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) )");
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/UnknownSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */