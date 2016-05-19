/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.EnumResolver;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumDeserializer
/*     */   extends StdScalarDeserializer<Enum<?>>
/*     */ {
/*     */   private static final long serialVersionUID = -5893263645879532318L;
/*     */   protected final EnumResolver<?> _resolver;
/*     */   
/*     */   public EnumDeserializer(EnumResolver<?> paramEnumResolver)
/*     */   {
/*  29 */     super(Enum.class);
/*  30 */     this._resolver = paramEnumResolver;
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
/*     */   public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig paramDeserializationConfig, Class<?> paramClass, AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/*  44 */     Class localClass = paramAnnotatedMethod.getRawParameterType(0);
/*  45 */     if (localClass == String.class) {
/*  46 */       localClass = null;
/*  47 */     } else if ((localClass == Integer.TYPE) || (localClass == Integer.class)) {
/*  48 */       localClass = Integer.class;
/*  49 */     } else if ((localClass == Long.TYPE) || (localClass == Long.class)) {
/*  50 */       localClass = Long.class;
/*     */     } else {
/*  52 */       throw new IllegalArgumentException("Parameter #0 type for factory method (" + paramAnnotatedMethod + ") not suitable, must be java.lang.String or int/Integer/long/Long");
/*     */     }
/*     */     
/*  55 */     if (paramDeserializationConfig.canOverrideAccessModifiers()) {
/*  56 */       ClassUtil.checkAndFixAccess(paramAnnotatedMethod.getMember());
/*     */     }
/*  58 */     return new FactoryBasedDeserializer(paramClass, paramAnnotatedMethod, localClass);
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
/*     */   public boolean isCachable()
/*     */   {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public Enum<?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  78 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/*     */     Enum localEnum;
/*  81 */     if ((localJsonToken == JsonToken.VALUE_STRING) || (localJsonToken == JsonToken.FIELD_NAME)) {
/*  82 */       String str = paramJsonParser.getText();
/*  83 */       localEnum = this._resolver.findEnum(str);
/*  84 */       if (localEnum == null) {
/*  85 */         if ((paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) && (
/*  86 */           (str.length() == 0) || (str.trim().length() == 0))) {
/*  87 */           return null;
/*     */         }
/*     */         
/*  90 */         if (!paramDeserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/*  91 */           throw paramDeserializationContext.weirdStringException(str, this._resolver.getEnumClass(), "value not one of declared Enum instance names");
/*     */         }
/*     */       }
/*     */       
/*  95 */       return localEnum;
/*     */     }
/*     */     
/*  98 */     if (localJsonToken == JsonToken.VALUE_NUMBER_INT)
/*     */     {
/*     */ 
/*     */ 
/* 102 */       if (paramDeserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
/* 103 */         throw paramDeserializationContext.mappingException("Not allowed to deserialize Enum value out of JSON number (disable DeserializationConfig.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS to allow)");
/*     */       }
/*     */       
/* 106 */       int i = paramJsonParser.getIntValue();
/* 107 */       localEnum = this._resolver.getEnum(i);
/* 108 */       if ((localEnum == null) && (!paramDeserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL))) {
/* 109 */         throw paramDeserializationContext.weirdNumberException(Integer.valueOf(i), this._resolver.getEnumClass(), "index value outside legal index range [0.." + this._resolver.lastValidIndex() + "]");
/*     */       }
/*     */       
/* 112 */       return localEnum;
/*     */     }
/* 114 */     throw paramDeserializationContext.mappingException(this._resolver.getEnumClass());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static class FactoryBasedDeserializer
/*     */     extends StdScalarDeserializer<Object>
/*     */   {
/*     */     private static final long serialVersionUID = -7775129435872564122L;
/*     */     
/*     */ 
/*     */ 
/*     */     protected final Class<?> _enumClass;
/*     */     
/*     */ 
/*     */ 
/*     */     protected final Class<?> _inputType;
/*     */     
/*     */ 
/*     */     protected final Method _factory;
/*     */     
/*     */ 
/*     */ 
/*     */     public FactoryBasedDeserializer(Class<?> paramClass1, AnnotatedMethod paramAnnotatedMethod, Class<?> paramClass2)
/*     */     {
/* 140 */       super();
/* 141 */       this._enumClass = paramClass1;
/* 142 */       this._factory = paramAnnotatedMethod.getAnnotated();
/* 143 */       this._inputType = paramClass2;
/*     */     }
/*     */     
/*     */ 
/*     */     public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/*     */       Object localObject;
/*     */       
/* 152 */       if (this._inputType == null) {
/* 153 */         localObject = paramJsonParser.getText();
/* 154 */       } else if (this._inputType == Integer.class) {
/* 155 */         localObject = Integer.valueOf(paramJsonParser.getValueAsInt());
/* 156 */       } else if (this._inputType == Long.class) {
/* 157 */         localObject = Long.valueOf(paramJsonParser.getValueAsLong());
/*     */       } else {
/* 159 */         throw paramDeserializationContext.mappingException(this._enumClass);
/*     */       }
/*     */       try {
/* 162 */         return this._factory.invoke(this._enumClass, new Object[] { localObject });
/*     */       } catch (Exception localException) {
/* 164 */         ClassUtil.unwrapAndThrowAsIAE(localException);
/*     */       }
/* 166 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/EnumDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */