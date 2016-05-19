/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.StdSerializer;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FailingSerializer
/*    */   extends StdSerializer<Object>
/*    */ {
/*    */   final String _msg;
/*    */   
/*    */   public FailingSerializer(String paramString)
/*    */   {
/* 28 */     super(Object.class);
/* 29 */     this._msg = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */   public void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 36 */     throw new JsonGenerationException(this._msg);
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */     throws JsonMappingException
/*    */   {
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType) {}
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/FailingSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */