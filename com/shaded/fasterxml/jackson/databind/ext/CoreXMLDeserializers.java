/*     */ package com.shaded.fasterxml.jackson.databind.ext;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.Deserializers.Base;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ import javax.xml.datatype.DatatypeConfigurationException;
/*     */ import javax.xml.datatype.DatatypeFactory;
/*     */ import javax.xml.datatype.Duration;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreXMLDeserializers
/*     */   extends Deserializers.Base
/*     */ {
/*     */   static final DatatypeFactory _dataTypeFactory;
/*     */   
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  35 */       _dataTypeFactory = DatatypeFactory.newInstance();
/*     */     } catch (DatatypeConfigurationException localDatatypeConfigurationException) {
/*  37 */       throw new RuntimeException(localDatatypeConfigurationException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findBeanDeserializer(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */   {
/*  45 */     Class localClass = paramJavaType.getRawClass();
/*  46 */     if (localClass == QName.class) {
/*  47 */       return QNameDeserializer.instance;
/*     */     }
/*  49 */     if (localClass == XMLGregorianCalendar.class) {
/*  50 */       return GregorianCalendarDeserializer.instance;
/*     */     }
/*  52 */     if (localClass == Duration.class) {
/*  53 */       return DurationDeserializer.instance;
/*     */     }
/*  55 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class DurationDeserializer
/*     */     extends FromStringDeserializer<Duration>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*  68 */     public static final DurationDeserializer instance = new DurationDeserializer();
/*  69 */     public DurationDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected Duration _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException
/*     */     {
/*  75 */       return CoreXMLDeserializers._dataTypeFactory.newDuration(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GregorianCalendarDeserializer
/*     */     extends StdScalarDeserializer<XMLGregorianCalendar>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  83 */     public static final GregorianCalendarDeserializer instance = new GregorianCalendarDeserializer();
/*  84 */     public GregorianCalendarDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     public XMLGregorianCalendar deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/*  90 */       Date localDate = _parseDate(paramJsonParser, paramDeserializationContext);
/*  91 */       if (localDate == null) {
/*  92 */         return null;
/*     */       }
/*  94 */       GregorianCalendar localGregorianCalendar = new GregorianCalendar();
/*  95 */       localGregorianCalendar.setTime(localDate);
/*  96 */       TimeZone localTimeZone = paramDeserializationContext.getTimeZone();
/*  97 */       if (localTimeZone != null) {
/*  98 */         localGregorianCalendar.setTimeZone(localTimeZone);
/*     */       }
/* 100 */       return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(localGregorianCalendar);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class QNameDeserializer
/*     */     extends FromStringDeserializer<QName>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 108 */     public static final QNameDeserializer instance = new QNameDeserializer();
/*     */     
/*     */     public QNameDeserializer() {
/* 111 */       super();
/*     */     }
/*     */     
/*     */     protected QName _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException
/*     */     {
/* 117 */       return QName.valueOf(paramString);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ext/CoreXMLDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */