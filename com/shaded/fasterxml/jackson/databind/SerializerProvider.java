/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.FilterProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ResolvableSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.SerializerCache;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.SerializerFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.FailingSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.TypeWrappedSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.WritableObjectId;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.NullSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.RootNameLookup;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
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
/*     */ public abstract class SerializerProvider
/*     */   extends DatabindContext
/*     */ {
/*  39 */   protected static final JavaType TYPE_OBJECT = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static final boolean CACHE_UNKNOWN_MAPPINGS = false;
/*     */   
/*     */ 
/*     */ 
/*  48 */   public static final JsonSerializer<Object> DEFAULT_NULL_KEY_SERIALIZER = new FailingSerializer("Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)");
/*     */   
/*     */ 
/*  51 */   public static final JsonSerializer<Object> DEFAULT_UNKNOWN_SERIALIZER = new UnknownSerializer();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SerializationConfig _config;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Class<?> _serializationView;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SerializerFactory _serializerFactory;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SerializerCache _serializerCache;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final RootNameLookup _rootNames;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 108 */   protected JsonSerializer<Object> _unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _keySerializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 121 */   protected JsonSerializer<Object> _nullValueSerializer = NullSerializer.instance;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 130 */   protected JsonSerializer<Object> _nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
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
/*     */   protected final ReadOnlyClassToSerializerMap _knownSerializers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected DateFormat _dateFormat;
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
/*     */   public SerializerProvider()
/*     */   {
/* 164 */     this._config = null;
/* 165 */     this._serializerFactory = null;
/* 166 */     this._serializerCache = new SerializerCache();
/*     */     
/* 168 */     this._knownSerializers = null;
/* 169 */     this._rootNames = new RootNameLookup();
/*     */     
/* 171 */     this._serializationView = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SerializerProvider(SerializerProvider paramSerializerProvider, SerializationConfig paramSerializationConfig, SerializerFactory paramSerializerFactory)
/*     */   {
/* 182 */     if (paramSerializationConfig == null) {
/* 183 */       throw new NullPointerException();
/*     */     }
/* 185 */     this._serializerFactory = paramSerializerFactory;
/* 186 */     this._config = paramSerializationConfig;
/*     */     
/* 188 */     this._serializerCache = paramSerializerProvider._serializerCache;
/* 189 */     this._unknownTypeSerializer = paramSerializerProvider._unknownTypeSerializer;
/* 190 */     this._keySerializer = paramSerializerProvider._keySerializer;
/* 191 */     this._nullValueSerializer = paramSerializerProvider._nullValueSerializer;
/* 192 */     this._nullKeySerializer = paramSerializerProvider._nullKeySerializer;
/* 193 */     this._rootNames = paramSerializerProvider._rootNames;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 198 */     this._knownSerializers = this._serializerCache.getReadOnlyLookupMap();
/*     */     
/* 200 */     this._serializationView = paramSerializationConfig.getActiveView();
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
/*     */   public void setDefaultKeySerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 217 */     if (paramJsonSerializer == null) {
/* 218 */       throw new IllegalArgumentException("Can not pass null JsonSerializer");
/*     */     }
/* 220 */     this._keySerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNullValueSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 230 */     if (paramJsonSerializer == null) {
/* 231 */       throw new IllegalArgumentException("Can not pass null JsonSerializer");
/*     */     }
/* 233 */     this._nullValueSerializer = paramJsonSerializer;
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
/*     */   public void setNullKeySerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 247 */     if (paramJsonSerializer == null) {
/* 248 */       throw new IllegalArgumentException("Can not pass null JsonSerializer");
/*     */     }
/* 250 */     this._nullKeySerializer = paramJsonSerializer;
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
/*     */   public final SerializationConfig getConfig()
/*     */   {
/* 263 */     return this._config;
/*     */   }
/*     */   
/*     */   public final AnnotationIntrospector getAnnotationIntrospector() {
/* 267 */     return this._config.getAnnotationIntrospector();
/*     */   }
/*     */   
/*     */   public final TypeFactory getTypeFactory()
/*     */   {
/* 272 */     return this._config.getTypeFactory();
/*     */   }
/*     */   
/*     */   public final Class<?> getActiveView() {
/* 276 */     return this._serializationView;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public final Class<?> getSerializationView()
/*     */   {
/* 282 */     return this._serializationView;
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
/*     */   public final boolean isEnabled(SerializationFeature paramSerializationFeature)
/*     */   {
/* 299 */     return this._config.isEnabled(paramSerializationFeature);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final FilterProvider getFilterProvider()
/*     */   {
/* 310 */     return this._config.getFilterProvider();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 320 */     return this._config.getLocale();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TimeZone getTimeZone()
/*     */   {
/* 330 */     return this._config.getTimeZone();
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
/*     */ 
/*     */ 
/*     */   public abstract WritableObjectId findObjectId(Object paramObject, ObjectIdGenerator<?> paramObjectIdGenerator);
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
/*     */   public JsonSerializer<Object> findValueSerializer(Class<?> paramClass, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 378 */     JsonSerializer localJsonSerializer = this._knownSerializers.untypedValueSerializer(paramClass);
/* 379 */     if (localJsonSerializer == null)
/*     */     {
/* 381 */       localJsonSerializer = this._serializerCache.untypedValueSerializer(paramClass);
/* 382 */       if (localJsonSerializer == null)
/*     */       {
/* 384 */         localJsonSerializer = this._serializerCache.untypedValueSerializer(this._config.constructType(paramClass));
/* 385 */         if (localJsonSerializer == null)
/*     */         {
/* 387 */           localJsonSerializer = _createAndCacheUntypedSerializer(paramClass);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 393 */           if (localJsonSerializer == null) {
/* 394 */             localJsonSerializer = getUnknownTypeSerializer(paramClass);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 399 */             return localJsonSerializer;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 405 */     return _handleContextual(localJsonSerializer, paramBeanProperty);
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
/*     */   public JsonSerializer<Object> findValueSerializer(JavaType paramJavaType, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 422 */     JsonSerializer localJsonSerializer = this._knownSerializers.untypedValueSerializer(paramJavaType);
/* 423 */     if (localJsonSerializer == null)
/*     */     {
/* 425 */       localJsonSerializer = this._serializerCache.untypedValueSerializer(paramJavaType);
/* 426 */       if (localJsonSerializer == null)
/*     */       {
/* 428 */         localJsonSerializer = _createAndCacheUntypedSerializer(paramJavaType);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 434 */         if (localJsonSerializer == null) {
/* 435 */           localJsonSerializer = getUnknownTypeSerializer(paramJavaType.getRawClass());
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 440 */           return localJsonSerializer;
/*     */         }
/*     */       }
/*     */     }
/* 444 */     return _handleContextual(localJsonSerializer, paramBeanProperty);
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
/*     */   public JsonSerializer<Object> findTypedValueSerializer(Class<?> paramClass, boolean paramBoolean, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 467 */     Object localObject = this._knownSerializers.typedValueSerializer(paramClass);
/* 468 */     if (localObject != null) {
/* 469 */       return (JsonSerializer<Object>)localObject;
/*     */     }
/*     */     
/* 472 */     localObject = this._serializerCache.typedValueSerializer(paramClass);
/* 473 */     if (localObject != null) {
/* 474 */       return (JsonSerializer<Object>)localObject;
/*     */     }
/*     */     
/*     */ 
/* 478 */     localObject = findValueSerializer(paramClass, paramBeanProperty);
/* 479 */     TypeSerializer localTypeSerializer = this._serializerFactory.createTypeSerializer(this._config, this._config.constructType(paramClass));
/*     */     
/* 481 */     if (localTypeSerializer != null) {
/* 482 */       localTypeSerializer = localTypeSerializer.forProperty(paramBeanProperty);
/* 483 */       localObject = new TypeWrappedSerializer(localTypeSerializer, (JsonSerializer)localObject);
/*     */     }
/* 485 */     if (paramBoolean) {
/* 486 */       this._serializerCache.addTypedSerializer(paramClass, (JsonSerializer)localObject);
/*     */     }
/* 488 */     return (JsonSerializer<Object>)localObject;
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
/*     */   public JsonSerializer<Object> findTypedValueSerializer(JavaType paramJavaType, boolean paramBoolean, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 512 */     Object localObject = this._knownSerializers.typedValueSerializer(paramJavaType);
/* 513 */     if (localObject != null) {
/* 514 */       return (JsonSerializer<Object>)localObject;
/*     */     }
/*     */     
/* 517 */     localObject = this._serializerCache.typedValueSerializer(paramJavaType);
/* 518 */     if (localObject != null) {
/* 519 */       return (JsonSerializer<Object>)localObject;
/*     */     }
/*     */     
/*     */ 
/* 523 */     localObject = findValueSerializer(paramJavaType, paramBeanProperty);
/* 524 */     TypeSerializer localTypeSerializer = this._serializerFactory.createTypeSerializer(this._config, paramJavaType);
/* 525 */     if (localTypeSerializer != null) {
/* 526 */       localTypeSerializer = localTypeSerializer.forProperty(paramBeanProperty);
/* 527 */       localObject = new TypeWrappedSerializer(localTypeSerializer, (JsonSerializer)localObject);
/*     */     }
/* 529 */     if (paramBoolean) {
/* 530 */       this._serializerCache.addTypedSerializer(paramJavaType, (JsonSerializer)localObject);
/*     */     }
/* 532 */     return (JsonSerializer<Object>)localObject;
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
/*     */   public JsonSerializer<Object> findKeySerializer(JavaType paramJavaType, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 549 */     JsonSerializer localJsonSerializer = this._serializerFactory.createKeySerializer(this._config, paramJavaType, this._keySerializer);
/*     */     
/* 551 */     return _handleContextualResolvable(localJsonSerializer, paramBeanProperty);
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
/*     */   public JsonSerializer<Object> getDefaultNullKeySerializer()
/*     */   {
/* 564 */     return this._nullKeySerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonSerializer<Object> getDefaultNullValueSerializer()
/*     */   {
/* 571 */     return this._nullValueSerializer;
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
/*     */   public JsonSerializer<Object> findNullKeySerializer(JavaType paramJavaType, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 594 */     return getDefaultNullKeySerializer();
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
/*     */   public JsonSerializer<Object> findNullValueSerializer(BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 609 */     return getDefaultNullValueSerializer();
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
/*     */   public JsonSerializer<Object> getUnknownTypeSerializer(Class<?> paramClass)
/*     */   {
/* 625 */     return this._unknownTypeSerializer;
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
/*     */   public abstract JsonSerializer<Object> serializerInstance(Annotated paramAnnotated, Object paramObject)
/*     */     throws JsonMappingException;
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
/*     */   public final void defaultSerializeValue(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 665 */     if (paramObject == null) {
/* 666 */       getDefaultNullValueSerializer().serialize(null, paramJsonGenerator, this);
/*     */     } else {
/* 668 */       Class localClass = paramObject.getClass();
/* 669 */       findTypedValueSerializer(localClass, true, null).serialize(paramObject, paramJsonGenerator, this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void defaultSerializeField(String paramString, Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 681 */     paramJsonGenerator.writeFieldName(paramString);
/* 682 */     if (paramObject == null)
/*     */     {
/*     */ 
/*     */ 
/* 686 */       getDefaultNullValueSerializer().serialize(null, paramJsonGenerator, this);
/*     */     } else {
/* 688 */       Class localClass = paramObject.getClass();
/* 689 */       findTypedValueSerializer(localClass, true, null).serialize(paramObject, paramJsonGenerator, this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void defaultSerializeDateValue(long paramLong, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 710 */     if (isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
/* 711 */       paramJsonGenerator.writeNumber(paramLong);
/*     */     } else {
/* 713 */       paramJsonGenerator.writeString(_dateFormat().format(new Date(paramLong)));
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
/*     */   public final void defaultSerializeDateValue(Date paramDate, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 728 */     if (isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
/* 729 */       paramJsonGenerator.writeNumber(paramDate.getTime());
/*     */     } else {
/* 731 */       paramJsonGenerator.writeString(_dateFormat().format(paramDate));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void defaultSerializeDateKey(long paramLong, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 743 */     if (isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
/* 744 */       paramJsonGenerator.writeFieldName(String.valueOf(paramLong));
/*     */     } else {
/* 746 */       paramJsonGenerator.writeFieldName(_dateFormat().format(new Date(paramLong)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void defaultSerializeDateKey(Date paramDate, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 758 */     if (isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
/* 759 */       paramJsonGenerator.writeFieldName(String.valueOf(paramDate.getTime()));
/*     */     } else {
/* 761 */       paramJsonGenerator.writeFieldName(_dateFormat().format(paramDate));
/*     */     }
/*     */   }
/*     */   
/*     */   public final void defaultSerializeNull(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 768 */     getDefaultNullValueSerializer().serialize(null, paramJsonGenerator, this);
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
/*     */   protected void _reportIncompatibleRootType(Object paramObject, JavaType paramJavaType)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 783 */     if (paramJavaType.isPrimitive()) {
/* 784 */       Class localClass = ClassUtil.wrapperType(paramJavaType.getRawClass());
/*     */       
/* 786 */       if (localClass.isAssignableFrom(paramObject.getClass())) {
/* 787 */         return;
/*     */       }
/*     */     }
/* 790 */     throw new JsonMappingException("Incompatible types: declared root type (" + paramJavaType + ") vs " + paramObject.getClass().getName());
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
/*     */   protected JsonSerializer<Object> _findExplicitUntypedSerializer(Class<?> paramClass)
/*     */     throws JsonMappingException
/*     */   {
/* 805 */     JsonSerializer localJsonSerializer = this._knownSerializers.untypedValueSerializer(paramClass);
/* 806 */     if (localJsonSerializer != null) {
/* 807 */       return localJsonSerializer;
/*     */     }
/*     */     
/* 810 */     localJsonSerializer = this._serializerCache.untypedValueSerializer(paramClass);
/* 811 */     if (localJsonSerializer != null) {
/* 812 */       return localJsonSerializer;
/*     */     }
/* 814 */     return _createAndCacheUntypedSerializer(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _createAndCacheUntypedSerializer(Class<?> paramClass)
/*     */     throws JsonMappingException
/*     */   {
/*     */     JsonSerializer localJsonSerializer;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 833 */       localJsonSerializer = _createUntypedSerializer(this._config.constructType(paramClass));
/*     */ 
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException)
/*     */     {
/* 838 */       throw new JsonMappingException(localIllegalArgumentException.getMessage(), null, localIllegalArgumentException);
/*     */     }
/*     */     
/* 841 */     if (localJsonSerializer != null) {
/* 842 */       this._serializerCache.addAndResolveNonTypedSerializer(paramClass, localJsonSerializer, this);
/*     */     }
/* 844 */     return localJsonSerializer;
/*     */   }
/*     */   
/*     */   protected JsonSerializer<Object> _createAndCacheUntypedSerializer(JavaType paramJavaType) throws JsonMappingException
/*     */   {
/*     */     JsonSerializer localJsonSerializer;
/*     */     try
/*     */     {
/* 852 */       localJsonSerializer = _createUntypedSerializer(paramJavaType);
/*     */ 
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException)
/*     */     {
/* 857 */       throw new JsonMappingException(localIllegalArgumentException.getMessage(), null, localIllegalArgumentException);
/*     */     }
/*     */     
/* 860 */     if (localJsonSerializer != null) {
/* 861 */       this._serializerCache.addAndResolveNonTypedSerializer(paramJavaType, localJsonSerializer, this);
/*     */     }
/* 863 */     return localJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _createUntypedSerializer(JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 873 */     return this._serializerFactory.createSerializer(this, paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _handleContextualResolvable(JsonSerializer<?> paramJsonSerializer, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 884 */     if ((paramJsonSerializer instanceof ResolvableSerializer)) {
/* 885 */       ((ResolvableSerializer)paramJsonSerializer).resolve(this);
/*     */     }
/* 887 */     return _handleContextual(paramJsonSerializer, paramBeanProperty);
/*     */   }
/*     */   
/*     */ 
/*     */   protected JsonSerializer<Object> _handleResolvable(JsonSerializer<?> paramJsonSerializer)
/*     */     throws JsonMappingException
/*     */   {
/* 894 */     if ((paramJsonSerializer instanceof ResolvableSerializer)) {
/* 895 */       ((ResolvableSerializer)paramJsonSerializer).resolve(this);
/*     */     }
/* 897 */     return paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _handleContextual(JsonSerializer<?> paramJsonSerializer, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 905 */     if ((paramJsonSerializer instanceof ContextualSerializer)) {
/* 906 */       paramJsonSerializer = ((ContextualSerializer)paramJsonSerializer).createContextual(this, paramBeanProperty);
/*     */     }
/* 908 */     return paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final DateFormat _dateFormat()
/*     */   {
/* 919 */     if (this._dateFormat != null) {
/* 920 */       return this._dateFormat;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 927 */     DateFormat localDateFormat = this._config.getDateFormat();
/* 928 */     this._dateFormat = (localDateFormat = (DateFormat)localDateFormat.clone());
/* 929 */     return localDateFormat;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/SerializerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */