/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StaticListSerializerBase<T extends Collection<?>>
/*    */   extends StdSerializer<T>
/*    */ {
/*    */   protected StaticListSerializerBase(Class<?> paramClass)
/*    */   {
/* 22 */     super(paramClass, false);
/*    */   }
/*    */   
/*    */   public boolean isEmpty(T paramT)
/*    */   {
/* 27 */     return (paramT == null) || (paramT.size() == 0);
/*    */   }
/*    */   
/*    */ 
/*    */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*    */   {
/* 33 */     ObjectNode localObjectNode = createSchemaNode("array", true);
/* 34 */     localObjectNode.put("items", contentSchema());
/* 35 */     return localObjectNode;
/*    */   }
/*    */   
/*    */ 
/*    */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */     throws JsonMappingException
/*    */   {
/* 42 */     acceptContentVisitor(paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType));
/*    */   }
/*    */   
/*    */   protected abstract JsonNode contentSchema();
/*    */   
/*    */   protected abstract void acceptContentVisitor(JsonArrayFormatVisitor paramJsonArrayFormatVisitor)
/*    */     throws JsonMappingException;
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StaticListSerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */