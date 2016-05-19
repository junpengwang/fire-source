/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public final class StringSerializer
/*    */   extends NonTypedScalarSerializerBase<String>
/*    */ {
/*    */   public StringSerializer()
/*    */   {
/* 25 */     super(String.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isEmpty(String paramString)
/*    */   {
/* 32 */     return (paramString == null) || (paramString.length() == 0);
/*    */   }
/*    */   
/*    */ 
/*    */   public void serialize(String paramString, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 39 */     paramJsonGenerator.writeString(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */   {
/* 45 */     return createSchemaNode("string", true);
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 52 */     if (paramJsonFormatVisitorWrapper != null) paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StringSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */