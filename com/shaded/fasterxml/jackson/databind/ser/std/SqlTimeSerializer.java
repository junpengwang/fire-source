/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.sql.Time;
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class SqlTimeSerializer extends StdScalarSerializer<Time>
/*    */ {
/*    */   public SqlTimeSerializer()
/*    */   {
/* 21 */     super(Time.class);
/*    */   }
/*    */   
/*    */   public void serialize(Time paramTime, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 27 */     paramJsonGenerator.writeString(paramTime.toString());
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */   {
/* 33 */     return createSchemaNode("string", true);
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws com.shaded.fasterxml.jackson.databind.JsonMappingException
/*    */   {
/* 40 */     JsonStringFormatVisitor localJsonStringFormatVisitor = paramJsonFormatVisitorWrapper == null ? null : paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/* 41 */     if (localJsonStringFormatVisitor != null) {
/* 42 */       localJsonStringFormatVisitor.format(JsonValueFormat.DATE_TIME);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/SqlTimeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */