/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.FieldPosition;
/*    */ import java.text.NumberFormat;
/*    */ import java.text.ParsePosition;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.GregorianCalendar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ISO8601DateFormat
/*    */   extends DateFormat
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 23 */   private static Calendar CALENDAR = new GregorianCalendar();
/* 24 */   private static NumberFormat NUMBER_FORMAT = new DecimalFormat();
/*    */   
/*    */   public ISO8601DateFormat() {
/* 27 */     this.numberFormat = NUMBER_FORMAT;
/* 28 */     this.calendar = CALENDAR;
/*    */   }
/*    */   
/*    */ 
/*    */   public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*    */   {
/* 34 */     String str = ISO8601Utils.format(paramDate);
/* 35 */     paramStringBuffer.append(str);
/* 36 */     return paramStringBuffer;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Date parse(String paramString, ParsePosition paramParsePosition)
/*    */   {
/* 44 */     paramParsePosition.setIndex(paramString.length());
/* 45 */     return ISO8601Utils.parse(paramString);
/*    */   }
/*    */   
/*    */   public Object clone()
/*    */   {
/* 50 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/ISO8601DateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */