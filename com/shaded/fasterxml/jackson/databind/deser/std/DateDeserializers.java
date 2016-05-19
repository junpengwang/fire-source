/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.StdDateFormat;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class DateDeserializers
/*     */ {
/*  29 */   private static final HashSet<String> _classNames = new HashSet();
/*     */   
/*  31 */   static { Class[] arrayOfClass1 = { Calendar.class, GregorianCalendar.class, java.sql.Date.class, java.util.Date.class, Timestamp.class, TimeZone.class };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  39 */     for (Class localClass : arrayOfClass1) {
/*  40 */       _classNames.add(localClass.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static StdDeserializer<?>[] all()
/*     */   {
/*  50 */     return new StdDeserializer[] { CalendarDeserializer.instance, DateDeserializer.instance, CalendarDeserializer.gregorianInstance, SqlDateDeserializer.instance, TimestampDeserializer.instance, TimeZoneDeserializer.instance };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonDeserializer<?> find(Class<?> paramClass, String paramString)
/*     */   {
/*  65 */     if (!_classNames.contains(paramString)) {
/*  66 */       return null;
/*     */     }
/*     */     
/*  69 */     if (paramClass == Calendar.class) {
/*  70 */       return CalendarDeserializer.instance;
/*     */     }
/*  72 */     if (paramClass == java.util.Date.class) {
/*  73 */       return DateDeserializer.instance;
/*     */     }
/*  75 */     if (paramClass == java.sql.Date.class) {
/*  76 */       return SqlDateDeserializer.instance;
/*     */     }
/*  78 */     if (paramClass == Timestamp.class) {
/*  79 */       return TimestampDeserializer.instance;
/*     */     }
/*  81 */     if (paramClass == TimeZone.class) {
/*  82 */       return TimeZoneDeserializer.instance;
/*     */     }
/*  84 */     if (paramClass == GregorianCalendar.class) {
/*  85 */       return CalendarDeserializer.gregorianInstance;
/*     */     }
/*     */     
/*  88 */     throw new IllegalArgumentException("Internal error: can't find deserializer for " + paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static abstract class DateBasedDeserializer<T>
/*     */     extends StdScalarDeserializer<T>
/*     */     implements ContextualDeserializer
/*     */   {
/*     */     protected final DateFormat _customFormat;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final String _formatString;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected DateBasedDeserializer(Class<?> paramClass)
/*     */     {
/* 113 */       super();
/* 114 */       this._customFormat = null;
/* 115 */       this._formatString = null;
/*     */     }
/*     */     
/*     */     protected DateBasedDeserializer(DateBasedDeserializer<T> paramDateBasedDeserializer, DateFormat paramDateFormat, String paramString)
/*     */     {
/* 120 */       super();
/* 121 */       this._customFormat = paramDateFormat;
/* 122 */       this._formatString = paramString;
/*     */     }
/*     */     
/*     */ 
/*     */     protected abstract DateBasedDeserializer<T> withDateFormat(DateFormat paramDateFormat, String paramString);
/*     */     
/*     */     public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */       throws JsonMappingException
/*     */     {
/* 131 */       if (paramBeanProperty != null) {
/* 132 */         JsonFormat.Value localValue = paramDeserializationContext.getAnnotationIntrospector().findFormat(paramBeanProperty.getMember());
/* 133 */         if (localValue != null) {
/* 134 */           TimeZone localTimeZone = localValue.getTimeZone();
/*     */           
/* 136 */           String str = localValue.getPattern();
/* 137 */           Object localObject; if (str.length() > 0) {
/* 138 */             localObject = localValue.getLocale();
/* 139 */             if (localObject == null) {
/* 140 */               localObject = paramDeserializationContext.getLocale();
/*     */             }
/* 142 */             SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(str, (Locale)localObject);
/* 143 */             if (localTimeZone == null) {
/* 144 */               localTimeZone = paramDeserializationContext.getTimeZone();
/*     */             }
/* 146 */             localSimpleDateFormat.setTimeZone(localTimeZone);
/* 147 */             return withDateFormat(localSimpleDateFormat, str);
/*     */           }
/*     */           
/* 150 */           if (localTimeZone != null) {
/* 151 */             localObject = paramDeserializationContext.getConfig().getDateFormat();
/*     */             
/* 153 */             if (localObject.getClass() == StdDateFormat.class) {
/* 154 */               localObject = ((StdDateFormat)localObject).withTimeZone(localTimeZone);
/*     */             }
/*     */             else {
/* 157 */               localObject = (DateFormat)((DateFormat)localObject).clone();
/* 158 */               ((DateFormat)localObject).setTimeZone(localTimeZone);
/*     */             }
/* 160 */             return withDateFormat((DateFormat)localObject, str);
/*     */           }
/*     */         }
/*     */       }
/* 164 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */     protected java.util.Date _parseDate(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 171 */       if ((this._customFormat != null) && (paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING)) {
/* 172 */         String str = paramJsonParser.getText().trim();
/* 173 */         if (str.length() == 0) {
/* 174 */           return (java.util.Date)getEmptyValue();
/*     */         }
/* 176 */         synchronized (this._customFormat) {
/*     */           try {
/* 178 */             return this._customFormat.parse(str);
/*     */           } catch (ParseException localParseException) {
/* 180 */             throw new IllegalArgumentException("Failed to parse Date value '" + str + "' (format: \"" + this._formatString + "\"): " + localParseException.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 185 */       return super._parseDate(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static class CalendarDeserializer
/*     */     extends DateDeserializers.DateBasedDeserializer<Calendar>
/*     */   {
/* 199 */     public static final CalendarDeserializer instance = new CalendarDeserializer();
/* 200 */     public static final CalendarDeserializer gregorianInstance = new CalendarDeserializer(GregorianCalendar.class);
/*     */     
/*     */ 
/*     */     protected final Class<? extends Calendar> _calendarClass;
/*     */     
/*     */ 
/*     */ 
/*     */     public CalendarDeserializer()
/*     */     {
/* 209 */       super();
/* 210 */       this._calendarClass = null;
/*     */     }
/*     */     
/*     */     public CalendarDeserializer(Class<? extends Calendar> paramClass) {
/* 214 */       super();
/* 215 */       this._calendarClass = paramClass;
/*     */     }
/*     */     
/*     */     public CalendarDeserializer(CalendarDeserializer paramCalendarDeserializer, DateFormat paramDateFormat, String paramString) {
/* 219 */       super(paramDateFormat, paramString);
/* 220 */       this._calendarClass = paramCalendarDeserializer._calendarClass;
/*     */     }
/*     */     
/*     */     protected CalendarDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
/*     */     {
/* 225 */       return new CalendarDeserializer(this, paramDateFormat, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */     public Calendar deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 232 */       java.util.Date localDate = _parseDate(paramJsonParser, paramDeserializationContext);
/* 233 */       if (localDate == null) {
/* 234 */         return null;
/*     */       }
/* 236 */       if (this._calendarClass == null) {
/* 237 */         return paramDeserializationContext.constructCalendar(localDate);
/*     */       }
/*     */       try {
/* 240 */         Calendar localCalendar = (Calendar)this._calendarClass.newInstance();
/* 241 */         localCalendar.setTimeInMillis(localDate.getTime());
/* 242 */         TimeZone localTimeZone = paramDeserializationContext.getTimeZone();
/* 243 */         if (localTimeZone != null) {
/* 244 */           localCalendar.setTimeZone(localTimeZone);
/*     */         }
/* 246 */         return localCalendar;
/*     */       } catch (Exception localException) {
/* 248 */         throw paramDeserializationContext.instantiationException(this._calendarClass, localException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class DateDeserializer
/*     */     extends DateDeserializers.DateBasedDeserializer<java.util.Date>
/*     */   {
/* 263 */     public static final DateDeserializer instance = new DateDeserializer();
/*     */     
/* 265 */     public DateDeserializer() { super(); }
/*     */     
/* 267 */     public DateDeserializer(DateDeserializer paramDateDeserializer, DateFormat paramDateFormat, String paramString) { super(paramDateFormat, paramString); }
/*     */     
/*     */ 
/*     */     protected DateDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
/*     */     {
/* 272 */       return new DateDeserializer(this, paramDateFormat, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */     public java.util.Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 279 */       return _parseDate(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class SqlDateDeserializer
/*     */     extends DateDeserializers.DateBasedDeserializer<java.sql.Date>
/*     */   {
/* 290 */     public static final SqlDateDeserializer instance = new SqlDateDeserializer();
/*     */     
/* 292 */     public SqlDateDeserializer() { super(); }
/*     */     
/* 294 */     public SqlDateDeserializer(SqlDateDeserializer paramSqlDateDeserializer, DateFormat paramDateFormat, String paramString) { super(paramDateFormat, paramString); }
/*     */     
/*     */ 
/*     */     protected SqlDateDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
/*     */     {
/* 299 */       return new SqlDateDeserializer(this, paramDateFormat, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */     public java.sql.Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 306 */       java.util.Date localDate = _parseDate(paramJsonParser, paramDeserializationContext);
/* 307 */       return localDate == null ? null : new java.sql.Date(localDate.getTime());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class TimestampDeserializer
/*     */     extends DateDeserializers.DateBasedDeserializer<Timestamp>
/*     */   {
/* 321 */     public static final TimestampDeserializer instance = new TimestampDeserializer();
/*     */     
/* 323 */     public TimestampDeserializer() { super(); }
/*     */     
/* 325 */     public TimestampDeserializer(TimestampDeserializer paramTimestampDeserializer, DateFormat paramDateFormat, String paramString) { super(paramDateFormat, paramString); }
/*     */     
/*     */ 
/*     */     protected TimestampDeserializer withDateFormat(DateFormat paramDateFormat, String paramString)
/*     */     {
/* 330 */       return new TimestampDeserializer(this, paramDateFormat, paramString);
/*     */     }
/*     */     
/*     */ 
/*     */     public Timestamp deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 337 */       return new Timestamp(_parseDate(paramJsonParser, paramDeserializationContext).getTime());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static class TimeZoneDeserializer
/*     */     extends FromStringDeserializer<TimeZone>
/*     */   {
/* 353 */     public static final TimeZoneDeserializer instance = new TimeZoneDeserializer();
/*     */     
/* 355 */     public TimeZoneDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected TimeZone _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IOException
/*     */     {
/* 361 */       return TimeZone.getTimeZone(paramString);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/DateDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */