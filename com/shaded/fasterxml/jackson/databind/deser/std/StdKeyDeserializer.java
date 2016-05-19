/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.util.EnumResolver;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public abstract class StdKeyDeserializer extends KeyDeserializer implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Class<?> _keyClass;
/*     */   
/*     */   protected StdKeyDeserializer(Class<?> paramClass)
/*     */   {
/*  28 */     this._keyClass = paramClass;
/*     */   }
/*     */   
/*     */   public final Object deserializeKey(String paramString, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  34 */     if (paramString == null) {
/*  35 */       return null;
/*     */     }
/*     */     try {
/*  38 */       Object localObject = _parse(paramString, paramDeserializationContext);
/*  39 */       if (localObject != null) {
/*  40 */         return localObject;
/*     */       }
/*     */     } catch (Exception localException) {
/*  43 */       throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "not a valid representation: " + localException.getMessage());
/*     */     }
/*  45 */     if ((this._keyClass.isEnum()) && (paramDeserializationContext.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL))) {
/*  46 */       return null;
/*     */     }
/*  48 */     throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "not a valid representation");
/*     */   }
/*     */   
/*  51 */   public Class<?> getKeyClass() { return this._keyClass; }
/*     */   
/*     */ 
/*     */ 
/*     */   protected abstract Object _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */   protected int _parseInt(String paramString)
/*     */     throws IllegalArgumentException
/*     */   {
/*  63 */     return Integer.parseInt(paramString);
/*     */   }
/*     */   
/*     */   protected long _parseLong(String paramString) throws IllegalArgumentException
/*     */   {
/*  68 */     return Long.parseLong(paramString);
/*     */   }
/*     */   
/*     */   protected double _parseDouble(String paramString) throws IllegalArgumentException
/*     */   {
/*  73 */     return com.shaded.fasterxml.jackson.core.io.NumberInput.parseDouble(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class StringKD
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*  87 */     private static final StringKD sString = new StringKD(String.class);
/*  88 */     private static final StringKD sObject = new StringKD(Object.class);
/*     */     
/*  90 */     private StringKD(Class<?> paramClass) { super(); }
/*     */     
/*     */     public static StringKD forType(Class<?> paramClass)
/*     */     {
/*  94 */       if (paramClass == String.class) {
/*  95 */         return sString;
/*     */       }
/*  97 */       if (paramClass == Object.class) {
/*  98 */         return sObject;
/*     */       }
/* 100 */       return new StringKD(paramClass);
/*     */     }
/*     */     
/*     */     public String _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 105 */       return paramString;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class BoolKD
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */     BoolKD()
/*     */     {
/* 120 */       super();
/*     */     }
/*     */     
/*     */     public Boolean _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 125 */       if ("true".equals(paramString)) {
/* 126 */         return Boolean.TRUE;
/*     */       }
/* 128 */       if ("false".equals(paramString)) {
/* 129 */         return Boolean.FALSE;
/*     */       }
/* 131 */       throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "value not 'true' or 'false'");
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class ByteKD extends StdKeyDeserializer {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     ByteKD() {
/* 140 */       super();
/*     */     }
/*     */     
/*     */     public Byte _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 145 */       int i = _parseInt(paramString);
/*     */       
/* 147 */       if ((i < -128) || (i > 255)) {
/* 148 */         throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "overflow, value can not be represented as 8-bit value");
/*     */       }
/* 150 */       return Byte.valueOf((byte)i);
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class ShortKD extends StdKeyDeserializer {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     ShortKD() {
/* 159 */       super();
/*     */     }
/*     */     
/*     */     public Short _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 164 */       int i = _parseInt(paramString);
/* 165 */       if ((i < 32768) || (i > 32767)) {
/* 166 */         throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "overflow, value can not be represented as 16-bit value");
/*     */       }
/* 168 */       return Short.valueOf((short)i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class CharKD
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     CharKD()
/*     */     {
/* 181 */       super();
/*     */     }
/*     */     
/*     */     public Character _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 186 */       if (paramString.length() == 1) {
/* 187 */         return Character.valueOf(paramString.charAt(0));
/*     */       }
/* 189 */       throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "can only convert 1-character Strings");
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class IntKD extends StdKeyDeserializer {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     IntKD() {
/* 198 */       super();
/*     */     }
/*     */     
/*     */     public Integer _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 203 */       return Integer.valueOf(_parseInt(paramString));
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class LongKD extends StdKeyDeserializer {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     LongKD() {
/* 212 */       super();
/*     */     }
/*     */     
/*     */     public Long _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 217 */       return Long.valueOf(_parseLong(paramString));
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class DoubleKD extends StdKeyDeserializer {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     DoubleKD() {
/* 226 */       super();
/*     */     }
/*     */     
/*     */     public Double _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/* 231 */       return Double.valueOf(_parseDouble(paramString));
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class FloatKD extends StdKeyDeserializer {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     FloatKD() {
/* 240 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public Float _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws JsonMappingException
/*     */     {
/* 248 */       return Float.valueOf((float)_parseDouble(paramString));
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class LocaleKD
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/* 258 */     LocaleKD() { super(); } protected JdkDeserializers.LocaleDeserializer _localeDeserializer = new JdkDeserializers.LocaleDeserializer();
/*     */     
/*     */     protected Locale _parse(String paramString, DeserializationContext paramDeserializationContext) throws JsonMappingException
/*     */     {
/*     */       try {
/* 263 */         return this._localeDeserializer._deserialize(paramString, paramDeserializationContext);
/*     */       } catch (IOException localIOException) {
/* 265 */         throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "unable to parse key as locale");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class DelegatingKD
/*     */     extends KeyDeserializer
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */     protected final Class<?> _keyClass;
/*     */     
/*     */ 
/*     */ 
/*     */     protected final JsonDeserializer<?> _delegate;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected DelegatingKD(Class<?> paramClass, JsonDeserializer<?> paramJsonDeserializer)
/*     */     {
/* 292 */       this._keyClass = paramClass;
/* 293 */       this._delegate = paramJsonDeserializer;
/*     */     }
/*     */     
/*     */ 
/*     */     public final Object deserializeKey(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 300 */       if (paramString == null) {
/* 301 */         return null;
/*     */       }
/*     */       try
/*     */       {
/* 305 */         Object localObject = this._delegate.deserialize(paramDeserializationContext.getParser(), paramDeserializationContext);
/* 306 */         if (localObject != null) {
/* 307 */           return localObject;
/*     */         }
/*     */       } catch (Exception localException) {
/* 310 */         throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "not a valid representation: " + localException.getMessage());
/*     */       }
/* 312 */       throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "not a valid representation");
/*     */     }
/*     */     
/* 315 */     public Class<?> getKeyClass() { return this._keyClass; }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class EnumKD
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     protected final EnumResolver<?> _resolver;
/*     */     protected final AnnotatedMethod _factory;
/*     */     
/*     */     protected EnumKD(EnumResolver<?> paramEnumResolver, AnnotatedMethod paramAnnotatedMethod)
/*     */     {
/* 328 */       super();
/* 329 */       this._resolver = paramEnumResolver;
/* 330 */       this._factory = paramAnnotatedMethod;
/*     */     }
/*     */     
/*     */     public Object _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws JsonMappingException
/*     */     {
/* 336 */       if (this._factory != null) {
/*     */         try {
/* 338 */           return this._factory.call1(paramString);
/*     */         } catch (Exception localException) {
/* 340 */           com.shaded.fasterxml.jackson.databind.util.ClassUtil.unwrapAndThrowAsIAE(localException);
/*     */         }
/*     */       }
/* 343 */       Enum localEnum = this._resolver.findEnum(paramString);
/* 344 */       if ((localEnum == null) && (!paramDeserializationContext.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL))) {
/* 345 */         throw paramDeserializationContext.weirdKeyException(this._keyClass, paramString, "not one of values for Enum class");
/*     */       }
/* 347 */       return localEnum;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static final class StringCtorKeyDeserializer
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected final Constructor<?> _ctor;
/*     */     
/*     */ 
/*     */     public StringCtorKeyDeserializer(Constructor<?> paramConstructor)
/*     */     {
/* 362 */       super();
/* 363 */       this._ctor = paramConstructor;
/*     */     }
/*     */     
/*     */     public Object _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws Exception
/*     */     {
/* 369 */       return this._ctor.newInstance(new Object[] { paramString });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static final class StringFactoryKeyDeserializer
/*     */     extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     final Method _factoryMethod;
/*     */     
/*     */ 
/*     */     public StringFactoryKeyDeserializer(Method paramMethod)
/*     */     {
/* 384 */       super();
/* 385 */       this._factoryMethod = paramMethod;
/*     */     }
/*     */     
/*     */     public Object _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws Exception
/*     */     {
/* 391 */       return this._factoryMethod.invoke(null, new Object[] { paramString });
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class DateKD extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected DateKD()
/*     */     {
/* 402 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */     public Object _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException, JsonMappingException
/*     */     {
/* 409 */       return paramDeserializationContext.parseDate(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class CalendarKD extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected CalendarKD()
/*     */     {
/* 420 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */     public Object _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException, JsonMappingException
/*     */     {
/* 427 */       Date localDate = paramDeserializationContext.parseDate(paramString);
/* 428 */       return localDate == null ? null : paramDeserializationContext.constructCalendar(localDate);
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class UuidKD extends StdKeyDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected UuidKD()
/*     */     {
/* 439 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */     public Object _parse(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException, JsonMappingException
/*     */     {
/* 446 */       return UUID.fromString(paramString);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StdKeyDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */