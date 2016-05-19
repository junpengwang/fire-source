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
/*    */ public class NullSerializer
/*    */   extends StdSerializer<Object>
/*    */ {
/* 23 */   public static final NullSerializer instance = new NullSerializer();
/*    */   
/* 25 */   private NullSerializer() { super(Object.class); }
/*    */   
/*    */ 
/*    */   public void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 31 */     paramJsonGenerator.writeNull();
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */     throws JsonMappingException
/*    */   {
/* 38 */     return createSchemaNode("null");
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 45 */     paramJsonFormatVisitorWrapper.expectNullFormat(paramJavaType);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/NullSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */