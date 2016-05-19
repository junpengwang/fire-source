/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ public abstract class StdScalarSerializer<T>
/*    */   extends StdSerializer<T>
/*    */ {
/*    */   protected StdScalarSerializer(Class<T> paramClass)
/*    */   {
/* 19 */     super(paramClass);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected StdScalarSerializer(Class<?> paramClass, boolean paramBoolean)
/*    */   {
/* 28 */     super(paramClass);
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
/*    */ 
/*    */   public void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 43 */     paramTypeSerializer.writeTypePrefixForScalar(paramT, paramJsonGenerator);
/* 44 */     serialize(paramT, paramJsonGenerator, paramSerializerProvider);
/* 45 */     paramTypeSerializer.writeTypeSuffixForScalar(paramT, paramJsonGenerator);
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */     throws JsonMappingException
/*    */   {
/* 52 */     return createSchemaNode("string", true);
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 59 */     paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StdScalarSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */