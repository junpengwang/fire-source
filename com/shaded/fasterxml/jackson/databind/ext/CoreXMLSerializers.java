/*    */ package com.shaded.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.Serializers.Base;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.CalendarSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.StdSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import javax.xml.datatype.Duration;
/*    */ import javax.xml.datatype.XMLGregorianCalendar;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoreXMLSerializers
/*    */   extends Serializers.Base
/*    */ {
/*    */   public JsonSerializer<?> findSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*    */   {
/* 34 */     Class localClass = paramJavaType.getRawClass();
/* 35 */     if ((Duration.class.isAssignableFrom(localClass)) || (QName.class.isAssignableFrom(localClass))) {
/* 36 */       return ToStringSerializer.instance;
/*    */     }
/* 38 */     if (XMLGregorianCalendar.class.isAssignableFrom(localClass)) {
/* 39 */       return XMLGregorianCalendarSerializer.instance;
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */   
/*    */   public static class XMLGregorianCalendarSerializer extends StdSerializer<XMLGregorianCalendar>
/*    */   {
/* 46 */     public static final XMLGregorianCalendarSerializer instance = new XMLGregorianCalendarSerializer();
/*    */     
/*    */     public XMLGregorianCalendarSerializer() {
/* 49 */       super();
/*    */     }
/*    */     
/*    */     public void serialize(XMLGregorianCalendar paramXMLGregorianCalendar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */       throws IOException, JsonGenerationException
/*    */     {
/* 55 */       CalendarSerializer.instance.serialize(paramXMLGregorianCalendar.toGregorianCalendar(), paramJsonGenerator, paramSerializerProvider);
/*    */     }
/*    */     
/*    */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType) throws JsonMappingException
/*    */     {
/* 60 */       return CalendarSerializer.instance.getSchema(paramSerializerProvider, paramType);
/*    */     }
/*    */     
/*    */ 
/*    */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*    */       throws JsonMappingException
/*    */     {
/* 67 */       CalendarSerializer.instance.acceptJsonFormatVisitor(paramJsonFormatVisitorWrapper, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ext/CoreXMLSerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */