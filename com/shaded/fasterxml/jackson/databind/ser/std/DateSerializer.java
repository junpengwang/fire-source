/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import java.io.IOException;
/*    */ import java.text.DateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class DateSerializer
/*    */   extends DateTimeSerializerBase<Date>
/*    */ {
/* 24 */   public static final DateSerializer instance = new DateSerializer();
/*    */   
/*    */   public DateSerializer() {
/* 27 */     this(false, null);
/*    */   }
/*    */   
/*    */   public DateSerializer(boolean paramBoolean, DateFormat paramDateFormat) {
/* 31 */     super(Date.class, paramBoolean, paramDateFormat);
/*    */   }
/*    */   
/*    */ 
/*    */   public DateSerializer withFormat(boolean paramBoolean, DateFormat paramDateFormat)
/*    */   {
/* 37 */     if (paramBoolean) {
/* 38 */       return new DateSerializer(true, null);
/*    */     }
/* 40 */     return new DateSerializer(false, paramDateFormat);
/*    */   }
/*    */   
/*    */   protected long _timestamp(Date paramDate)
/*    */   {
/* 45 */     return paramDate == null ? 0L : paramDate.getTime();
/*    */   }
/*    */   
/*    */ 
/*    */   public void serialize(Date paramDate, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 52 */     if (this._useTimestamp) {
/* 53 */       paramJsonGenerator.writeNumber(_timestamp(paramDate));
/* 54 */     } else if (this._customFormat != null)
/*    */     {
/* 56 */       synchronized (this._customFormat) {
/* 57 */         paramJsonGenerator.writeString(this._customFormat.format(paramDate));
/*    */       }
/*    */     } else {
/* 60 */       paramSerializerProvider.defaultSerializeDateValue(paramDate, paramJsonGenerator);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/DateSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */