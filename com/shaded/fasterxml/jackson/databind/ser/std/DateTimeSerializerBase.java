/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Shape;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.StdDateFormat;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ public abstract class DateTimeSerializerBase<T>
/*     */   extends StdScalarSerializer<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final boolean _useTimestamp;
/*     */   protected final DateFormat _customFormat;
/*     */   
/*     */   protected DateTimeSerializerBase(Class<T> paramClass, boolean paramBoolean, DateFormat paramDateFormat)
/*     */   {
/*  40 */     super(paramClass);
/*  41 */     this._useTimestamp = paramBoolean;
/*  42 */     this._customFormat = paramDateFormat;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract DateTimeSerializerBase<T> withFormat(boolean paramBoolean, DateFormat paramDateFormat);
/*     */   
/*     */   public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  51 */     if (paramBeanProperty != null) {
/*  52 */       JsonFormat.Value localValue = paramSerializerProvider.getAnnotationIntrospector().findFormat(paramBeanProperty.getMember());
/*  53 */       if (localValue != null)
/*     */       {
/*  55 */         if (localValue.getShape().isNumeric()) {
/*  56 */           return withFormat(true, null);
/*     */         }
/*     */         
/*  59 */         TimeZone localTimeZone = localValue.getTimeZone();
/*  60 */         String str = localValue.getPattern();
/*  61 */         Object localObject; if (str.length() > 0) {
/*  62 */           localObject = localValue.getLocale();
/*  63 */           if (localObject == null) {
/*  64 */             localObject = paramSerializerProvider.getLocale();
/*     */           }
/*  66 */           SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(str, (Locale)localObject);
/*  67 */           if (localTimeZone == null) {
/*  68 */             localTimeZone = paramSerializerProvider.getTimeZone();
/*     */           }
/*  70 */           localSimpleDateFormat.setTimeZone(localTimeZone);
/*  71 */           return withFormat(false, localSimpleDateFormat);
/*     */         }
/*     */         
/*  74 */         if (localTimeZone != null) {
/*  75 */           localObject = paramSerializerProvider.getConfig().getDateFormat();
/*     */           
/*  77 */           if (localObject.getClass() == StdDateFormat.class) {
/*  78 */             localObject = StdDateFormat.getISO8601Format(localTimeZone);
/*     */           }
/*     */           else {
/*  81 */             localObject = (DateFormat)((DateFormat)localObject).clone();
/*  82 */             ((DateFormat)localObject).setTimeZone(localTimeZone);
/*     */           }
/*  84 */           return withFormat(false, (DateFormat)localObject);
/*     */         }
/*     */       }
/*     */     }
/*  88 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty(T paramT)
/*     */   {
/* 100 */     return (paramT == null) || (_timestamp(paramT) == 0L);
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract long _timestamp(T paramT);
/*     */   
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */   {
/* 109 */     boolean bool = this._useTimestamp;
/* 110 */     if ((!bool) && 
/* 111 */       (this._customFormat == null)) {
/* 112 */       bool = paramSerializerProvider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
/*     */     }
/*     */     
/* 115 */     return createSchemaNode(bool ? "number" : "string", true);
/*     */   }
/*     */   
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 122 */     boolean bool = this._useTimestamp;
/* 123 */     if ((!bool) && 
/* 124 */       (this._customFormat == null)) {
/* 125 */       bool = paramJsonFormatVisitorWrapper.getProvider().isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
/*     */     }
/*     */     Object localObject;
/* 128 */     if (bool) {
/* 129 */       localObject = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/* 130 */       if (localObject != null) {
/* 131 */         ((JsonIntegerFormatVisitor)localObject).numberType(JsonParser.NumberType.LONG);
/* 132 */         ((JsonIntegerFormatVisitor)localObject).format(JsonValueFormat.UTC_MILLISEC);
/*     */       }
/*     */     } else {
/* 135 */       localObject = paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/* 136 */       if (localObject != null) {
/* 137 */         ((JsonStringFormatVisitor)localObject).format(JsonValueFormat.DATE_TIME);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException;
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/DateTimeSerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */