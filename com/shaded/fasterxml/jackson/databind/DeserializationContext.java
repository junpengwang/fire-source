/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.DeserializerCache;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.DeserializerFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.exc.InvalidFormatException;
/*     */ import com.shaded.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.LinkedNode;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ObjectBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
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
/*     */ public abstract class DeserializationContext
/*     */   extends DatabindContext
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7727373309391091315L;
/*     */   private static final int MAX_ERROR_STR_LEN = 500;
/*     */   protected final DeserializerCache _cache;
/*     */   protected final DeserializerFactory _factory;
/*     */   protected final DeserializationConfig _config;
/*     */   protected final int _featureFlags;
/*     */   protected final Class<?> _view;
/*     */   protected transient JsonParser _parser;
/*     */   protected final InjectableValues _injectableValues;
/*     */   protected transient ArrayBuilders _arrayBuilders;
/*     */   protected transient ObjectBuffer _objectBuffer;
/*     */   protected transient DateFormat _dateFormat;
/*     */   
/*     */   protected DeserializationContext(DeserializerFactory paramDeserializerFactory)
/*     */   {
/* 130 */     this(paramDeserializerFactory, null);
/*     */   }
/*     */   
/*     */ 
/*     */   protected DeserializationContext(DeserializerFactory paramDeserializerFactory, DeserializerCache paramDeserializerCache)
/*     */   {
/* 136 */     if (paramDeserializerFactory == null) {
/* 137 */       throw new IllegalArgumentException("Can not pass null DeserializerFactory");
/*     */     }
/* 139 */     this._factory = paramDeserializerFactory;
/* 140 */     this._cache = (paramDeserializerCache == null ? new DeserializerCache() : paramDeserializerCache);
/*     */     
/* 142 */     this._featureFlags = 0;
/* 143 */     this._config = null;
/* 144 */     this._injectableValues = null;
/* 145 */     this._view = null;
/*     */   }
/*     */   
/*     */ 
/*     */   protected DeserializationContext(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory)
/*     */   {
/* 151 */     this._cache = paramDeserializationContext._cache;
/* 152 */     this._factory = paramDeserializerFactory;
/*     */     
/* 154 */     this._config = paramDeserializationContext._config;
/* 155 */     this._featureFlags = paramDeserializationContext._featureFlags;
/* 156 */     this._view = paramDeserializationContext._view;
/* 157 */     this._parser = paramDeserializationContext._parser;
/* 158 */     this._injectableValues = paramDeserializationContext._injectableValues;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected DeserializationContext(DeserializationContext paramDeserializationContext, DeserializationConfig paramDeserializationConfig, JsonParser paramJsonParser, InjectableValues paramInjectableValues)
/*     */   {
/* 165 */     this._cache = paramDeserializationContext._cache;
/* 166 */     this._factory = paramDeserializationContext._factory;
/*     */     
/* 168 */     this._config = paramDeserializationConfig;
/* 169 */     this._featureFlags = paramDeserializationConfig.getDeserializationFeatures();
/* 170 */     this._view = paramDeserializationConfig.getActiveView();
/* 171 */     this._parser = paramJsonParser;
/* 172 */     this._injectableValues = paramInjectableValues;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig getConfig()
/*     */   {
/* 182 */     return this._config;
/*     */   }
/*     */   
/* 185 */   public final Class<?> getActiveView() { return this._view; }
/*     */   
/*     */   public final AnnotationIntrospector getAnnotationIntrospector()
/*     */   {
/* 189 */     return this._config.getAnnotationIntrospector();
/*     */   }
/*     */   
/*     */   public final TypeFactory getTypeFactory()
/*     */   {
/* 194 */     return this._config.getTypeFactory();
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
/*     */   public DeserializerFactory getFactory()
/*     */   {
/* 207 */     return this._factory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isEnabled(DeserializationFeature paramDeserializationFeature)
/*     */   {
/* 218 */     return (this._featureFlags & paramDeserializationFeature.getMask()) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonParser getParser()
/*     */   {
/* 229 */     return this._parser;
/*     */   }
/*     */   
/*     */   public final Object findInjectableValue(Object paramObject1, BeanProperty paramBeanProperty, Object paramObject2)
/*     */   {
/* 234 */     if (this._injectableValues == null) {
/* 235 */       throw new IllegalStateException("No 'injectableValues' configured, can not inject value with id [" + paramObject1 + "]");
/*     */     }
/* 237 */     return this._injectableValues.findInjectableValue(paramObject1, this, paramBeanProperty, paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Base64Variant getBase64Variant()
/*     */   {
/* 249 */     return this._config.getBase64Variant();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonNodeFactory getNodeFactory()
/*     */   {
/* 259 */     return this._config.getNodeFactory();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 269 */     return this._config.getLocale();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TimeZone getTimeZone()
/*     */   {
/* 279 */     return this._config.getTimeZone();
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
/*     */   public boolean hasValueDeserializerFor(JavaType paramJavaType)
/*     */   {
/* 293 */     return this._cache.hasValueDeserializerFor(this, this._factory, paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonDeserializer<Object> findContextualValueDeserializer(JavaType paramJavaType, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 305 */     JsonDeserializer localJsonDeserializer = this._cache.findValueDeserializer(this, this._factory, paramJavaType);
/*     */     
/* 307 */     if ((localJsonDeserializer != null) && 
/* 308 */       ((localJsonDeserializer instanceof ContextualDeserializer))) {
/* 309 */       localJsonDeserializer = ((ContextualDeserializer)localJsonDeserializer).createContextual(this, paramBeanProperty);
/*     */     }
/*     */     
/* 312 */     return localJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonDeserializer<Object> findRootValueDeserializer(JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 322 */     JsonDeserializer localJsonDeserializer = this._cache.findValueDeserializer(this, this._factory, paramJavaType);
/*     */     
/* 324 */     if (localJsonDeserializer == null) {
/* 325 */       return null;
/*     */     }
/* 327 */     if ((localJsonDeserializer instanceof ContextualDeserializer)) {
/* 328 */       localJsonDeserializer = ((ContextualDeserializer)localJsonDeserializer).createContextual(this, null);
/*     */     }
/* 330 */     TypeDeserializer localTypeDeserializer = this._factory.findTypeDeserializer(this._config, paramJavaType);
/* 331 */     if (localTypeDeserializer != null)
/*     */     {
/* 333 */       localTypeDeserializer = localTypeDeserializer.forProperty(null);
/* 334 */       return new TypeWrappedDeserializer(localTypeDeserializer, localJsonDeserializer);
/*     */     }
/* 336 */     return localJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final KeyDeserializer findKeyDeserializer(JavaType paramJavaType, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 347 */     KeyDeserializer localKeyDeserializer = this._cache.findKeyDeserializer(this, this._factory, paramJavaType);
/*     */     
/*     */ 
/* 350 */     if ((localKeyDeserializer instanceof ContextualKeyDeserializer)) {
/* 351 */       localKeyDeserializer = ((ContextualKeyDeserializer)localKeyDeserializer).createContextual(this, paramBeanProperty);
/*     */     }
/* 353 */     return localKeyDeserializer;
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
/*     */ 
/*     */   public abstract ReadableObjectId findObjectId(Object paramObject, ObjectIdGenerator<?> paramObjectIdGenerator);
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
/*     */   public final JavaType constructType(Class<?> paramClass)
/*     */   {
/* 382 */     return this._config.constructType(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> findClass(String paramString)
/*     */     throws ClassNotFoundException
/*     */   {
/* 394 */     return ClassUtil.findClass(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract JsonDeserializer<Object> deserializerInstance(Annotated paramAnnotated, Object paramObject)
/*     */     throws JsonMappingException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract KeyDeserializer keyDeserializerInstance(Annotated paramAnnotated, Object paramObject)
/*     */     throws JsonMappingException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ObjectBuffer leaseObjectBuffer()
/*     */   {
/* 425 */     ObjectBuffer localObjectBuffer = this._objectBuffer;
/* 426 */     if (localObjectBuffer == null) {
/* 427 */       localObjectBuffer = new ObjectBuffer();
/*     */     } else {
/* 429 */       this._objectBuffer = null;
/*     */     }
/* 431 */     return localObjectBuffer;
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
/*     */   public final void returnObjectBuffer(ObjectBuffer paramObjectBuffer)
/*     */   {
/* 445 */     if ((this._objectBuffer == null) || (paramObjectBuffer.initialCapacity() >= this._objectBuffer.initialCapacity()))
/*     */     {
/* 447 */       this._objectBuffer = paramObjectBuffer;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ArrayBuilders getArrayBuilders()
/*     */   {
/* 457 */     if (this._arrayBuilders == null) {
/* 458 */       this._arrayBuilders = new ArrayBuilders();
/*     */     }
/* 460 */     return this._arrayBuilders;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date parseDate(String paramString)
/*     */     throws IllegalArgumentException
/*     */   {
/*     */     try
/*     */     {
/* 483 */       return getDateFormat().parse(paramString);
/*     */     } catch (ParseException localParseException) {
/* 485 */       throw new IllegalArgumentException("Failed to parse Date value '" + paramString + "': " + localParseException.getMessage());
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
/*     */   public Calendar constructCalendar(Date paramDate)
/*     */   {
/* 498 */     Calendar localCalendar = Calendar.getInstance(getTimeZone());
/* 499 */     localCalendar.setTime(paramDate);
/* 500 */     return localCalendar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean handleUnknownProperty(JsonParser paramJsonParser, JsonDeserializer<?> paramJsonDeserializer, Object paramObject, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 524 */     LinkedNode localLinkedNode = this._config.getProblemHandlers();
/* 525 */     if (localLinkedNode != null) {
/* 526 */       while (localLinkedNode != null)
/*     */       {
/* 528 */         if (((DeserializationProblemHandler)localLinkedNode.value()).handleUnknownProperty(this, paramJsonParser, paramJsonDeserializer, paramObject, paramString)) {
/* 529 */           return true;
/*     */         }
/* 531 */         localLinkedNode = localLinkedNode.next();
/*     */       }
/*     */     }
/* 534 */     return false;
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
/*     */   public void reportUnknownProperty(Object paramObject, String paramString, JsonDeserializer<?> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/* 550 */     if (!isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
/* 551 */       return;
/*     */     }
/*     */     
/* 554 */     Collection localCollection = paramJsonDeserializer == null ? null : paramJsonDeserializer.getKnownPropertyNames();
/* 555 */     throw UnrecognizedPropertyException.from(this._parser, paramObject, paramString, localCollection);
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
/*     */   public JsonMappingException mappingException(Class<?> paramClass)
/*     */   {
/* 569 */     return mappingException(paramClass, this._parser.getCurrentToken());
/*     */   }
/*     */   
/*     */   public JsonMappingException mappingException(Class<?> paramClass, JsonToken paramJsonToken)
/*     */   {
/* 574 */     String str = _calcName(paramClass);
/* 575 */     return JsonMappingException.from(this._parser, "Can not deserialize instance of " + str + " out of " + paramJsonToken + " token");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonMappingException mappingException(String paramString)
/*     */   {
/* 584 */     return JsonMappingException.from(getParser(), paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonMappingException instantiationException(Class<?> paramClass, Throwable paramThrowable)
/*     */   {
/* 594 */     return JsonMappingException.from(this._parser, "Can not construct instance of " + paramClass.getName() + ", problem: " + paramThrowable.getMessage(), paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonMappingException instantiationException(Class<?> paramClass, String paramString)
/*     */   {
/* 600 */     return JsonMappingException.from(this._parser, "Can not construct instance of " + paramClass.getName() + ", problem: " + paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public JsonMappingException weirdStringException(Class<?> paramClass, String paramString)
/*     */   {
/* 612 */     return weirdStringException(null, paramClass, paramString);
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
/*     */   public JsonMappingException weirdStringException(String paramString1, Class<?> paramClass, String paramString2)
/*     */   {
/* 626 */     return InvalidFormatException.from(this._parser, "Can not construct instance of " + paramClass.getName() + " from String value '" + _valueDesc() + "': " + paramString2, paramString1, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public JsonMappingException weirdNumberException(Class<?> paramClass, String paramString)
/*     */   {
/* 637 */     return weirdStringException(null, paramClass, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonMappingException weirdNumberException(Number paramNumber, Class<?> paramClass, String paramString)
/*     */   {
/* 645 */     return InvalidFormatException.from(this._parser, "Can not construct instance of " + paramClass.getName() + " from number value (" + _valueDesc() + "): " + paramString, null, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonMappingException weirdKeyException(Class<?> paramClass, String paramString1, String paramString2)
/*     */   {
/* 657 */     return InvalidFormatException.from(this._parser, "Can not construct Map key of type " + paramClass.getName() + " from String \"" + _desc(paramString1) + "\": " + paramString2, paramString1, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonMappingException wrongTokenException(JsonParser paramJsonParser, JsonToken paramJsonToken, String paramString)
/*     */   {
/* 668 */     return JsonMappingException.from(paramJsonParser, "Unexpected token (" + paramJsonParser.getCurrentToken() + "), expected " + paramJsonToken + ": " + paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonMappingException unknownTypeException(JavaType paramJavaType, String paramString)
/*     */   {
/* 677 */     return JsonMappingException.from(this._parser, "Could not resolve type id '" + paramString + "' into a subtype of " + paramJavaType);
/*     */   }
/*     */   
/*     */   public JsonMappingException endOfInputException(Class<?> paramClass)
/*     */   {
/* 682 */     return JsonMappingException.from(this._parser, "Unexpected end-of-input when trying to deserialize a " + paramClass.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected DateFormat getDateFormat()
/*     */   {
/* 694 */     if (this._dateFormat != null) {
/* 695 */       return this._dateFormat;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 702 */     DateFormat localDateFormat = this._config.getDateFormat();
/* 703 */     this._dateFormat = (localDateFormat = (DateFormat)localDateFormat.clone());
/* 704 */     return localDateFormat;
/*     */   }
/*     */   
/*     */   protected String determineClassName(Object paramObject)
/*     */   {
/* 709 */     return ClassUtil.getClassDescription(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String _calcName(Class<?> paramClass)
/*     */   {
/* 720 */     if (paramClass.isArray()) {
/* 721 */       return _calcName(paramClass.getComponentType()) + "[]";
/*     */     }
/* 723 */     return paramClass.getName();
/*     */   }
/*     */   
/*     */   protected String _valueDesc()
/*     */   {
/*     */     try {
/* 729 */       return _desc(this._parser.getText());
/*     */     } catch (Exception localException) {}
/* 731 */     return "[N/A]";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String _desc(String paramString)
/*     */   {
/* 737 */     if (paramString.length() > 500) {
/* 738 */       paramString = paramString.substring(0, 500) + "]...[" + paramString.substring(paramString.length() - 500);
/*     */     }
/* 740 */     return paramString;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/DeserializationContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */