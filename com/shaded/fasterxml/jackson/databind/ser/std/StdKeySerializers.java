/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import java.io.IOException;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ public class StdKeySerializers
/*    */ {
/* 15 */   protected static final JsonSerializer<Object> DEFAULT_KEY_SERIALIZER = new StdKeySerializer();
/*    */   
/*    */ 
/* 18 */   protected static final JsonSerializer<Object> DEFAULT_STRING_SERIALIZER = new StringKeySerializer();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static JsonSerializer<Object> getStdKeySerializer(JavaType paramJavaType)
/*    */   {
/* 26 */     if (paramJavaType == null) {
/* 27 */       return DEFAULT_KEY_SERIALIZER;
/*    */     }
/* 29 */     Class localClass = paramJavaType.getRawClass();
/* 30 */     if (localClass == String.class) {
/* 31 */       return DEFAULT_STRING_SERIALIZER;
/*    */     }
/* 33 */     if (localClass == Object.class) {
/* 34 */       return DEFAULT_KEY_SERIALIZER;
/*    */     }
/*    */     
/* 37 */     if (Date.class.isAssignableFrom(localClass)) {
/* 38 */       return DateKeySerializer.instance;
/*    */     }
/* 40 */     if (Calendar.class.isAssignableFrom(localClass)) {
/* 41 */       return CalendarKeySerializer.instance;
/*    */     }
/*    */     
/* 44 */     return DEFAULT_KEY_SERIALIZER;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static class StringKeySerializer
/*    */     extends StdSerializer<String>
/*    */   {
/*    */     public StringKeySerializer()
/*    */     {
/* 56 */       super();
/*    */     }
/*    */     
/*    */     public void serialize(String paramString, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */       throws IOException, JsonGenerationException
/*    */     {
/* 62 */       paramJsonGenerator.writeFieldName(paramString);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class DateKeySerializer
/*    */     extends StdSerializer<Date>
/*    */   {
/* 69 */     protected static final JsonSerializer<?> instance = new DateKeySerializer();
/*    */     
/* 71 */     public DateKeySerializer() { super(); }
/*    */     
/*    */ 
/*    */     public void serialize(Date paramDate, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */       throws IOException, JsonGenerationException
/*    */     {
/* 77 */       paramSerializerProvider.defaultSerializeDateKey(paramDate, paramJsonGenerator);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class CalendarKeySerializer
/*    */     extends StdSerializer<Calendar>
/*    */   {
/* 84 */     protected static final JsonSerializer<?> instance = new CalendarKeySerializer();
/*    */     
/* 86 */     public CalendarKeySerializer() { super(); }
/*    */     
/*    */ 
/*    */     public void serialize(Calendar paramCalendar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */       throws IOException, JsonGenerationException
/*    */     {
/* 92 */       paramSerializerProvider.defaultSerializeDateKey(paramCalendar.getTimeInMillis(), paramJsonGenerator);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StdKeySerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */