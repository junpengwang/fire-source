/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.CreatorProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class StdValueInstantiator
/*     */   extends ValueInstantiator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final String _valueTypeDesc;
/*     */   protected final boolean _cfgEmptyStringsAsObjects;
/*     */   protected AnnotatedWithParams _defaultCreator;
/*     */   protected AnnotatedWithParams _withArgsCreator;
/*     */   protected CreatorProperty[] _constructorArguments;
/*     */   protected JavaType _delegateType;
/*     */   protected AnnotatedWithParams _delegateCreator;
/*     */   protected CreatorProperty[] _delegateArguments;
/*     */   protected AnnotatedWithParams _fromStringCreator;
/*     */   protected AnnotatedWithParams _fromIntCreator;
/*     */   protected AnnotatedWithParams _fromLongCreator;
/*     */   protected AnnotatedWithParams _fromDoubleCreator;
/*     */   protected AnnotatedWithParams _fromBooleanCreator;
/*     */   protected AnnotatedParameter _incompleteParameter;
/*     */   
/*     */   public StdValueInstantiator(DeserializationConfig paramDeserializationConfig, Class<?> paramClass)
/*     */   {
/*  71 */     this._cfgEmptyStringsAsObjects = (paramDeserializationConfig == null ? false : paramDeserializationConfig.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT));
/*     */     
/*  73 */     this._valueTypeDesc = (paramClass == null ? "UNKNOWN TYPE" : paramClass.getName());
/*     */   }
/*     */   
/*     */   public StdValueInstantiator(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*     */   {
/*  78 */     this._cfgEmptyStringsAsObjects = (paramDeserializationConfig == null ? false : paramDeserializationConfig.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT));
/*     */     
/*  80 */     this._valueTypeDesc = (paramJavaType == null ? "UNKNOWN TYPE" : paramJavaType.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StdValueInstantiator(StdValueInstantiator paramStdValueInstantiator)
/*     */   {
/*  89 */     this._cfgEmptyStringsAsObjects = paramStdValueInstantiator._cfgEmptyStringsAsObjects;
/*  90 */     this._valueTypeDesc = paramStdValueInstantiator._valueTypeDesc;
/*     */     
/*  92 */     this._defaultCreator = paramStdValueInstantiator._defaultCreator;
/*     */     
/*  94 */     this._constructorArguments = paramStdValueInstantiator._constructorArguments;
/*  95 */     this._withArgsCreator = paramStdValueInstantiator._withArgsCreator;
/*     */     
/*  97 */     this._delegateType = paramStdValueInstantiator._delegateType;
/*  98 */     this._delegateCreator = paramStdValueInstantiator._delegateCreator;
/*  99 */     this._delegateArguments = paramStdValueInstantiator._delegateArguments;
/*     */     
/* 101 */     this._fromStringCreator = paramStdValueInstantiator._fromStringCreator;
/* 102 */     this._fromIntCreator = paramStdValueInstantiator._fromIntCreator;
/* 103 */     this._fromLongCreator = paramStdValueInstantiator._fromLongCreator;
/* 104 */     this._fromDoubleCreator = paramStdValueInstantiator._fromDoubleCreator;
/* 105 */     this._fromBooleanCreator = paramStdValueInstantiator._fromBooleanCreator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void configureFromObjectSettings(AnnotatedWithParams paramAnnotatedWithParams1, AnnotatedWithParams paramAnnotatedWithParams2, JavaType paramJavaType, CreatorProperty[] paramArrayOfCreatorProperty1, AnnotatedWithParams paramAnnotatedWithParams3, CreatorProperty[] paramArrayOfCreatorProperty2)
/*     */   {
/* 117 */     this._defaultCreator = paramAnnotatedWithParams1;
/* 118 */     this._delegateCreator = paramAnnotatedWithParams2;
/* 119 */     this._delegateType = paramJavaType;
/* 120 */     this._delegateArguments = paramArrayOfCreatorProperty1;
/* 121 */     this._withArgsCreator = paramAnnotatedWithParams3;
/* 122 */     this._constructorArguments = paramArrayOfCreatorProperty2;
/*     */   }
/*     */   
/*     */   public void configureFromStringCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 126 */     this._fromStringCreator = paramAnnotatedWithParams;
/*     */   }
/*     */   
/*     */   public void configureFromIntCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 130 */     this._fromIntCreator = paramAnnotatedWithParams;
/*     */   }
/*     */   
/*     */   public void configureFromLongCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 134 */     this._fromLongCreator = paramAnnotatedWithParams;
/*     */   }
/*     */   
/*     */   public void configureFromDoubleCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 138 */     this._fromDoubleCreator = paramAnnotatedWithParams;
/*     */   }
/*     */   
/*     */   public void configureFromBooleanCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 142 */     this._fromBooleanCreator = paramAnnotatedWithParams;
/*     */   }
/*     */   
/*     */   public void configureIncompleteParameter(AnnotatedParameter paramAnnotatedParameter) {
/* 146 */     this._incompleteParameter = paramAnnotatedParameter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getValueTypeDesc()
/*     */   {
/* 157 */     return this._valueTypeDesc;
/*     */   }
/*     */   
/*     */   public boolean canCreateFromString()
/*     */   {
/* 162 */     return this._fromStringCreator != null;
/*     */   }
/*     */   
/*     */   public boolean canCreateFromInt()
/*     */   {
/* 167 */     return this._fromIntCreator != null;
/*     */   }
/*     */   
/*     */   public boolean canCreateFromLong()
/*     */   {
/* 172 */     return this._fromLongCreator != null;
/*     */   }
/*     */   
/*     */   public boolean canCreateFromDouble()
/*     */   {
/* 177 */     return this._fromDoubleCreator != null;
/*     */   }
/*     */   
/*     */   public boolean canCreateFromBoolean()
/*     */   {
/* 182 */     return this._fromBooleanCreator != null;
/*     */   }
/*     */   
/*     */   public boolean canCreateUsingDefault()
/*     */   {
/* 187 */     return this._defaultCreator != null;
/*     */   }
/*     */   
/*     */   public boolean canCreateUsingDelegate()
/*     */   {
/* 192 */     return this._delegateType != null;
/*     */   }
/*     */   
/*     */   public boolean canCreateFromObjectWith()
/*     */   {
/* 197 */     return this._withArgsCreator != null;
/*     */   }
/*     */   
/*     */   public JavaType getDelegateType(DeserializationConfig paramDeserializationConfig)
/*     */   {
/* 202 */     return this._delegateType;
/*     */   }
/*     */   
/*     */   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig paramDeserializationConfig)
/*     */   {
/* 207 */     return this._constructorArguments;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object createUsingDefault(DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 220 */     if (this._defaultCreator == null) {
/* 221 */       throw new IllegalStateException("No default constructor for " + getValueTypeDesc());
/*     */     }
/*     */     try {
/* 224 */       return this._defaultCreator.call();
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 226 */       throw wrapException(localExceptionInInitializerError);
/*     */     } catch (Exception localException) {
/* 228 */       throw wrapException(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Object createFromObjectWith(DeserializationContext paramDeserializationContext, Object[] paramArrayOfObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 236 */     if (this._withArgsCreator == null) {
/* 237 */       throw new IllegalStateException("No with-args constructor for " + getValueTypeDesc());
/*     */     }
/*     */     try {
/* 240 */       return this._withArgsCreator.call(paramArrayOfObject);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 242 */       throw wrapException(localExceptionInInitializerError);
/*     */     } catch (Exception localException) {
/* 244 */       throw wrapException(localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Object createUsingDelegate(DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 252 */     if (this._delegateCreator == null) {
/* 253 */       throw new IllegalStateException("No delegate constructor for " + getValueTypeDesc());
/*     */     }
/*     */     try
/*     */     {
/* 257 */       if (this._delegateArguments == null) {
/* 258 */         return this._delegateCreator.call1(paramObject);
/*     */       }
/*     */       
/* 261 */       int i = this._delegateArguments.length;
/* 262 */       Object[] arrayOfObject = new Object[i];
/* 263 */       for (int j = 0; j < i; j++) {
/* 264 */         CreatorProperty localCreatorProperty = this._delegateArguments[j];
/* 265 */         if (localCreatorProperty == null) {
/* 266 */           arrayOfObject[j] = paramObject;
/*     */         } else {
/* 268 */           arrayOfObject[j] = paramDeserializationContext.findInjectableValue(localCreatorProperty.getInjectableValueId(), localCreatorProperty, null);
/*     */         }
/*     */       }
/*     */       
/* 272 */       return this._delegateCreator.call(arrayOfObject);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 274 */       throw wrapException(localExceptionInInitializerError);
/*     */     } catch (Exception localException) {
/* 276 */       throw wrapException(localException);
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
/*     */   public Object createFromString(DeserializationContext paramDeserializationContext, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 290 */     if (this._fromStringCreator != null) {
/*     */       try {
/* 292 */         return this._fromStringCreator.call1(paramString);
/*     */       } catch (Exception localException) {
/* 294 */         throw wrapException(localException);
/*     */       } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 296 */         throw wrapException(localExceptionInInitializerError);
/*     */       }
/*     */     }
/* 299 */     return _createFromStringFallbacks(paramDeserializationContext, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object createFromInt(DeserializationContext paramDeserializationContext, int paramInt)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*     */     try
/*     */     {
/* 308 */       if (this._fromIntCreator != null) {
/* 309 */         return this._fromIntCreator.call1(Integer.valueOf(paramInt));
/*     */       }
/*     */       
/* 312 */       if (this._fromLongCreator != null) {
/* 313 */         return this._fromLongCreator.call1(Long.valueOf(paramInt));
/*     */       }
/*     */     } catch (Exception localException) {
/* 316 */       throw wrapException(localException);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 318 */       throw wrapException(localExceptionInInitializerError);
/*     */     }
/* 320 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Integral number; no single-int-arg constructor/factory method");
/*     */   }
/*     */   
/*     */ 
/*     */   public Object createFromLong(DeserializationContext paramDeserializationContext, long paramLong)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*     */     try
/*     */     {
/* 329 */       if (this._fromLongCreator != null) {
/* 330 */         return this._fromLongCreator.call1(Long.valueOf(paramLong));
/*     */       }
/*     */     } catch (Exception localException) {
/* 333 */       throw wrapException(localException);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 335 */       throw wrapException(localExceptionInInitializerError);
/*     */     }
/* 337 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Long integral number; no single-long-arg constructor/factory method");
/*     */   }
/*     */   
/*     */ 
/*     */   public Object createFromDouble(DeserializationContext paramDeserializationContext, double paramDouble)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*     */     try
/*     */     {
/* 346 */       if (this._fromDoubleCreator != null) {
/* 347 */         return this._fromDoubleCreator.call1(Double.valueOf(paramDouble));
/*     */       }
/*     */     } catch (Exception localException) {
/* 350 */       throw wrapException(localException);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 352 */       throw wrapException(localExceptionInInitializerError);
/*     */     }
/* 354 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Floating-point number; no one-double/Double-arg constructor/factory method");
/*     */   }
/*     */   
/*     */ 
/*     */   public Object createFromBoolean(DeserializationContext paramDeserializationContext, boolean paramBoolean)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*     */     try
/*     */     {
/* 363 */       if (this._fromBooleanCreator != null) {
/* 364 */         return this._fromBooleanCreator.call1(Boolean.valueOf(paramBoolean));
/*     */       }
/*     */     } catch (Exception localException) {
/* 367 */       throw wrapException(localException);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 369 */       throw wrapException(localExceptionInInitializerError);
/*     */     }
/* 371 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Boolean value; no single-boolean/Boolean-arg constructor/factory method");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedWithParams getDelegateCreator()
/*     */   {
/* 383 */     return this._delegateCreator;
/*     */   }
/*     */   
/*     */   public AnnotatedWithParams getDefaultCreator()
/*     */   {
/* 388 */     return this._defaultCreator;
/*     */   }
/*     */   
/*     */   public AnnotatedWithParams getWithArgsCreator()
/*     */   {
/* 393 */     return this._withArgsCreator;
/*     */   }
/*     */   
/*     */   public AnnotatedParameter getIncompleteParameter()
/*     */   {
/* 398 */     return this._incompleteParameter;
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
/*     */   protected Object _createFromStringFallbacks(DeserializationContext paramDeserializationContext, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 414 */     if (this._fromBooleanCreator != null) {
/* 415 */       String str = paramString.trim();
/* 416 */       if ("true".equals(str)) {
/* 417 */         return createFromBoolean(paramDeserializationContext, true);
/*     */       }
/* 419 */       if ("false".equals(str)) {
/* 420 */         return createFromBoolean(paramDeserializationContext, false);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 425 */     if ((this._cfgEmptyStringsAsObjects) && (paramString.length() == 0)) {
/* 426 */       return null;
/*     */     }
/* 428 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from String value; no single-String constructor/factory method");
/*     */   }
/*     */   
/*     */ 
/*     */   protected JsonMappingException wrapException(Throwable paramThrowable)
/*     */   {
/* 434 */     while (paramThrowable.getCause() != null) {
/* 435 */       paramThrowable = paramThrowable.getCause();
/*     */     }
/* 437 */     if ((paramThrowable instanceof JsonMappingException)) {
/* 438 */       return (JsonMappingException)paramThrowable;
/*     */     }
/* 440 */     return new JsonMappingException("Instantiation of " + getValueTypeDesc() + " value failed: " + paramThrowable.getMessage(), paramThrowable);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StdValueInstantiator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */