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
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.sql.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class SqlDateSerializer
/*    */   extends StdScalarSerializer<Date>
/*    */ {
/*    */   public SqlDateSerializer()
/*    */   {
/* 26 */     super(Date.class);
/*    */   }
/*    */   
/*    */   public void serialize(Date paramDate, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 32 */     paramJsonGenerator.writeString(paramDate.toString());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */   {
/* 39 */     return createSchemaNode("string", true);
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 46 */     JsonStringFormatVisitor localJsonStringFormatVisitor = paramJsonFormatVisitorWrapper == null ? null : paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/* 47 */     if (localJsonStringFormatVisitor != null) {
/* 48 */       localJsonStringFormatVisitor.format(JsonValueFormat.DATE_TIME);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/SqlDateSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */