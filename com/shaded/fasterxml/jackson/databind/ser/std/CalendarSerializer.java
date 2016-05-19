/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import java.io.IOException;
/*    */ import java.text.DateFormat;
/*    */ import java.util.Calendar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class CalendarSerializer
/*    */   extends DateTimeSerializerBase<Calendar>
/*    */ {
/* 21 */   public static final CalendarSerializer instance = new CalendarSerializer();
/*    */   
/* 23 */   public CalendarSerializer() { this(false, null); }
/*    */   
/*    */   public CalendarSerializer(boolean paramBoolean, DateFormat paramDateFormat) {
/* 26 */     super(Calendar.class, paramBoolean, paramDateFormat);
/*    */   }
/*    */   
/*    */ 
/*    */   public CalendarSerializer withFormat(boolean paramBoolean, DateFormat paramDateFormat)
/*    */   {
/* 32 */     if (paramBoolean) {
/* 33 */       return new CalendarSerializer(true, null);
/*    */     }
/* 35 */     return new CalendarSerializer(false, paramDateFormat);
/*    */   }
/*    */   
/*    */   protected long _timestamp(Calendar paramCalendar)
/*    */   {
/* 40 */     return paramCalendar == null ? 0L : paramCalendar.getTimeInMillis();
/*    */   }
/*    */   
/*    */ 
/*    */   public void serialize(Calendar paramCalendar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 47 */     if (this._useTimestamp) {
/* 48 */       paramJsonGenerator.writeNumber(_timestamp(paramCalendar));
/* 49 */     } else if (this._customFormat != null)
/*    */     {
/* 51 */       synchronized (this._customFormat) {
/* 52 */         paramJsonGenerator.writeString(this._customFormat.format(paramCalendar));
/*    */       }
/*    */     } else {
/* 55 */       paramSerializerProvider.defaultSerializeDateValue(paramCalendar.getTime(), paramJsonGenerator);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/CalendarSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */