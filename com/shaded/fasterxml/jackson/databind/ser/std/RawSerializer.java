/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class RawSerializer<T>
/*    */   extends StdSerializer<T>
/*    */ {
/*    */   public RawSerializer(Class<?> paramClass)
/*    */   {
/* 27 */     super(paramClass, false);
/*    */   }
/*    */   
/*    */ 
/*    */   public void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 34 */     paramJsonGenerator.writeRawValue(paramT.toString());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 42 */     paramTypeSerializer.writeTypePrefixForScalar(paramT, paramJsonGenerator);
/* 43 */     serialize(paramT, paramJsonGenerator, paramSerializerProvider);
/* 44 */     paramTypeSerializer.writeTypeSuffixForScalar(paramT, paramJsonGenerator);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */   {
/* 51 */     return createSchemaNode("string", true);
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 58 */     paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/RawSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */