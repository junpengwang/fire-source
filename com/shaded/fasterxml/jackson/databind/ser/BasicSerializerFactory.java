/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Shape;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BasicBeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.BooleanSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.CalendarSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.DateSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.EnumMapSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.NullSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.NumberSerializers.NumberSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.SqlTimeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.StdContainerSerializers;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.StdJdkSerializers;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.EnumValues;
/*     */ import java.util.Calendar;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ public abstract class BasicSerializerFactory extends SerializerFactory implements java.io.Serializable
/*     */ {
/*  54 */   protected static final HashMap<String, JsonSerializer<?>> _concrete = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  62 */   protected static final HashMap<String, Class<? extends JsonSerializer<?>>> _concreteLazy = new HashMap();
/*     */   
/*     */   protected final SerializerFactoryConfig _factoryConfig;
/*     */   
/*     */ 
/*     */   static
/*     */   {
/*  69 */     _concrete.put(String.class.getName(), new com.shaded.fasterxml.jackson.databind.ser.std.StringSerializer());
/*  70 */     ToStringSerializer localToStringSerializer = ToStringSerializer.instance;
/*  71 */     _concrete.put(StringBuffer.class.getName(), localToStringSerializer);
/*  72 */     _concrete.put(StringBuilder.class.getName(), localToStringSerializer);
/*  73 */     _concrete.put(Character.class.getName(), localToStringSerializer);
/*  74 */     _concrete.put(Character.TYPE.getName(), localToStringSerializer);
/*     */     
/*     */ 
/*  77 */     com.shaded.fasterxml.jackson.databind.ser.std.NumberSerializers.addAll(_concrete);
/*  78 */     _concrete.put(Boolean.TYPE.getName(), new BooleanSerializer(true));
/*  79 */     _concrete.put(Boolean.class.getName(), new BooleanSerializer(false));
/*     */     
/*     */ 
/*  82 */     NumberSerializers.NumberSerializer localNumberSerializer = new NumberSerializers.NumberSerializer();
/*  83 */     _concrete.put(java.math.BigInteger.class.getName(), localNumberSerializer);
/*  84 */     _concrete.put(java.math.BigDecimal.class.getName(), localNumberSerializer);
/*     */     
/*     */ 
/*     */ 
/*  88 */     _concrete.put(Calendar.class.getName(), CalendarSerializer.instance);
/*  89 */     DateSerializer localDateSerializer = DateSerializer.instance;
/*  90 */     _concrete.put(java.util.Date.class.getName(), localDateSerializer);
/*     */     
/*  92 */     _concrete.put(java.sql.Timestamp.class.getName(), localDateSerializer);
/*     */     
/*     */ 
/*  95 */     _concreteLazy.put(java.sql.Date.class.getName(), com.shaded.fasterxml.jackson.databind.ser.std.SqlDateSerializer.class);
/*  96 */     _concreteLazy.put(java.sql.Time.class.getName(), SqlTimeSerializer.class);
/*     */     
/*     */ 
/*  99 */     for (Map.Entry localEntry : StdJdkSerializers.all()) {
/* 100 */       Object localObject = localEntry.getValue();
/* 101 */       if ((localObject instanceof JsonSerializer)) {
/* 102 */         _concrete.put(((Class)localEntry.getKey()).getName(), (JsonSerializer)localObject);
/* 103 */       } else if ((localObject instanceof Class))
/*     */       {
/* 105 */         Class localClass = (Class)localObject;
/* 106 */         _concreteLazy.put(((Class)localEntry.getKey()).getName(), localClass);
/*     */       } else {
/* 108 */         throw new IllegalStateException("Internal error: unrecognized value of type " + localEntry.getClass().getName());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 114 */     _concreteLazy.put(com.shaded.fasterxml.jackson.databind.util.TokenBuffer.class.getName(), TokenBufferSerializer.class);
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
/*     */ 
/*     */ 
/*     */   protected BasicSerializerFactory(SerializerFactoryConfig paramSerializerFactoryConfig)
/*     */   {
/* 141 */     this._factoryConfig = (paramSerializerFactoryConfig == null ? new SerializerFactoryConfig() : paramSerializerFactoryConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializerFactoryConfig getFactoryConfig()
/*     */   {
/* 152 */     return this._factoryConfig;
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
/*     */   public final SerializerFactory withAdditionalSerializers(Serializers paramSerializers)
/*     */   {
/* 173 */     return withConfig(this._factoryConfig.withAdditionalSerializers(paramSerializers));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SerializerFactory withAdditionalKeySerializers(Serializers paramSerializers)
/*     */   {
/* 182 */     return withConfig(this._factoryConfig.withAdditionalKeySerializers(paramSerializers));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SerializerFactory withSerializerModifier(BeanSerializerModifier paramBeanSerializerModifier)
/*     */   {
/* 191 */     return withConfig(this._factoryConfig.withSerializerModifier(paramBeanSerializerModifier));
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
/*     */   public JsonSerializer<Object> createKeySerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 212 */     BeanDescription localBeanDescription = paramSerializationConfig.introspectClassAnnotations(paramJavaType.getRawClass());
/* 213 */     Object localObject1 = null;
/*     */     Iterator localIterator;
/* 215 */     if (this._factoryConfig.hasKeySerializers())
/*     */     {
/* 217 */       for (localIterator = this._factoryConfig.keySerializers().iterator(); localIterator.hasNext();) { localObject2 = (Serializers)localIterator.next();
/* 218 */         localObject1 = ((Serializers)localObject2).findSerializer(paramSerializationConfig, paramJavaType, localBeanDescription);
/* 219 */         if (localObject1 != null)
/*     */           break;
/*     */       }
/*     */     }
/*     */     Object localObject2;
/* 224 */     if (localObject1 == null) {
/* 225 */       localObject1 = paramJsonSerializer;
/* 226 */       if (localObject1 == null) {
/* 227 */         localObject1 = com.shaded.fasterxml.jackson.databind.ser.std.StdKeySerializers.getStdKeySerializer(paramJavaType);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 232 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 233 */       for (localIterator = this._factoryConfig.serializerModifiers().iterator(); localIterator.hasNext();) { localObject2 = (BeanSerializerModifier)localIterator.next();
/* 234 */         localObject1 = ((BeanSerializerModifier)localObject2).modifyKeySerializer(paramSerializationConfig, paramJavaType, localBeanDescription, (JsonSerializer)localObject1);
/*     */       }
/*     */     }
/* 237 */     return (JsonSerializer<Object>)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeSerializer createTypeSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType)
/*     */   {
/* 249 */     BeanDescription localBeanDescription = paramSerializationConfig.introspectClassAnnotations(paramJavaType.getRawClass());
/* 250 */     AnnotatedClass localAnnotatedClass = localBeanDescription.getClassInfo();
/* 251 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/* 252 */     TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findTypeResolver(paramSerializationConfig, localAnnotatedClass, paramJavaType);
/*     */     
/*     */ 
/*     */ 
/* 256 */     java.util.Collection localCollection = null;
/* 257 */     if (localTypeResolverBuilder == null) {
/* 258 */       localTypeResolverBuilder = paramSerializationConfig.getDefaultTyper(paramJavaType);
/*     */     } else {
/* 260 */       localCollection = paramSerializationConfig.getSubtypeResolver().collectAndResolveSubtypes(localAnnotatedClass, paramSerializationConfig, localAnnotationIntrospector);
/*     */     }
/* 262 */     if (localTypeResolverBuilder == null) {
/* 263 */       return null;
/*     */     }
/* 265 */     return localTypeResolverBuilder.buildTypeSerializer(paramSerializationConfig, paramJavaType, localCollection);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonSerializer<?> getNullSerializer()
/*     */   {
/* 275 */     return NullSerializer.instance;
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
/*     */   protected final JsonSerializer<?> findSerializerByLookup(JavaType paramJavaType, SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */   {
/* 294 */     Class localClass1 = paramJavaType.getRawClass();
/* 295 */     String str = localClass1.getName();
/* 296 */     JsonSerializer localJsonSerializer = (JsonSerializer)_concrete.get(str);
/* 297 */     if (localJsonSerializer == null) {
/* 298 */       Class localClass2 = (Class)_concreteLazy.get(str);
/* 299 */       if (localClass2 != null) {
/*     */         try {
/* 301 */           return (JsonSerializer)localClass2.newInstance();
/*     */         } catch (Exception localException) {
/* 303 */           throw new IllegalStateException("Failed to instantiate standard serializer (of type " + localClass2.getName() + "): " + localException.getMessage(), localException);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 308 */     return localJsonSerializer;
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
/*     */   protected final JsonSerializer<?> findSerializerByAnnotations(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 331 */     Class localClass = paramJavaType.getRawClass();
/*     */     
/* 333 */     if (com.shaded.fasterxml.jackson.databind.JsonSerializable.class.isAssignableFrom(localClass)) {
/* 334 */       return com.shaded.fasterxml.jackson.databind.ser.std.SerializableSerializer.instance;
/*     */     }
/*     */     
/* 337 */     AnnotatedMethod localAnnotatedMethod = paramBeanDescription.findJsonValueMethod();
/* 338 */     if (localAnnotatedMethod != null) {
/* 339 */       java.lang.reflect.Method localMethod = localAnnotatedMethod.getAnnotated();
/* 340 */       if (paramSerializerProvider.canOverrideAccessModifiers()) {
/* 341 */         ClassUtil.checkAndFixAccess(localMethod);
/*     */       }
/* 343 */       JsonSerializer localJsonSerializer = findSerializerFromAnnotation(paramSerializerProvider, localAnnotatedMethod);
/* 344 */       return new com.shaded.fasterxml.jackson.databind.ser.std.JsonValueSerializer(localMethod, localJsonSerializer);
/*     */     }
/*     */     
/* 347 */     return null;
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
/*     */   protected final JsonSerializer<?> findSerializerByPrimaryType(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 362 */     Class localClass = paramJavaType.getRawClass();
/*     */     
/* 364 */     if (java.net.InetAddress.class.isAssignableFrom(localClass)) {
/* 365 */       return com.shaded.fasterxml.jackson.databind.ser.std.InetAddressSerializer.instance;
/*     */     }
/*     */     
/* 368 */     if (java.util.TimeZone.class.isAssignableFrom(localClass)) {
/* 369 */       return com.shaded.fasterxml.jackson.databind.ser.std.TimeZoneSerializer.instance;
/*     */     }
/*     */     
/* 372 */     if (java.nio.charset.Charset.class.isAssignableFrom(localClass)) {
/* 373 */       return ToStringSerializer.instance;
/*     */     }
/*     */     
/*     */ 
/* 377 */     JsonSerializer localJsonSerializer = findOptionalStdSerializer(paramSerializerProvider, paramJavaType, paramBeanDescription, paramBoolean);
/* 378 */     if (localJsonSerializer != null) {
/* 379 */       return localJsonSerializer;
/*     */     }
/*     */     
/* 382 */     if (Number.class.isAssignableFrom(localClass)) {
/* 383 */       return NumberSerializers.NumberSerializer.instance;
/*     */     }
/* 385 */     if (Enum.class.isAssignableFrom(localClass)) {
/* 386 */       return buildEnumSerializer(paramSerializerProvider.getConfig(), paramJavaType, paramBeanDescription);
/*     */     }
/* 388 */     if (Calendar.class.isAssignableFrom(localClass)) {
/* 389 */       return CalendarSerializer.instance;
/*     */     }
/* 391 */     if (java.util.Date.class.isAssignableFrom(localClass)) {
/* 392 */       return DateSerializer.instance;
/*     */     }
/* 394 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<?> findOptionalStdSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 406 */     return OptionalHandlerFactory.instance.findSerializer(paramSerializerProvider.getConfig(), paramJavaType, paramBeanDescription);
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
/*     */   protected final JsonSerializer<?> findSerializerByAddonType(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 422 */     Class localClass = paramJavaType.getRawClass();
/*     */     
/*     */ 
/* 425 */     if (Iterator.class.isAssignableFrom(localClass)) {
/* 426 */       return buildIteratorSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription, paramBoolean);
/*     */     }
/* 428 */     if (Iterable.class.isAssignableFrom(localClass)) {
/* 429 */       return buildIterableSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription, paramBoolean);
/*     */     }
/* 431 */     if (CharSequence.class.isAssignableFrom(localClass)) {
/* 432 */       return ToStringSerializer.instance;
/*     */     }
/* 434 */     return null;
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
/*     */   protected JsonSerializer<Object> findSerializerFromAnnotation(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
/*     */     throws JsonMappingException
/*     */   {
/* 449 */     Object localObject = paramSerializerProvider.getAnnotationIntrospector().findSerializer(paramAnnotated);
/* 450 */     if (localObject == null) {
/* 451 */       return null;
/*     */     }
/* 453 */     JsonSerializer localJsonSerializer = paramSerializerProvider.serializerInstance(paramAnnotated, localObject);
/*     */     
/* 455 */     return findConvertingSerializer(paramSerializerProvider, paramAnnotated, localJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<?> findConvertingSerializer(SerializerProvider paramSerializerProvider, Annotated paramAnnotated, JsonSerializer<?> paramJsonSerializer)
/*     */     throws JsonMappingException
/*     */   {
/* 468 */     Converter localConverter = findConverter(paramSerializerProvider, paramAnnotated);
/* 469 */     if (localConverter == null) {
/* 470 */       return paramJsonSerializer;
/*     */     }
/* 472 */     JavaType localJavaType = localConverter.getOutputType(paramSerializerProvider.getTypeFactory());
/* 473 */     return new StdDelegatingSerializer(localConverter, localJavaType, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */   protected Converter<Object, Object> findConverter(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
/*     */     throws JsonMappingException
/*     */   {
/* 480 */     Object localObject = paramSerializerProvider.getAnnotationIntrospector().findSerializationConverter(paramAnnotated);
/* 481 */     if (localObject == null) {
/* 482 */       return null;
/*     */     }
/* 484 */     return paramSerializerProvider.converterInstance(paramAnnotated, localObject);
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
/*     */   @Deprecated
/*     */   protected final JsonSerializer<?> buildContainerSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, BeanProperty paramBeanProperty, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 504 */     return buildContainerSerializer(paramSerializerProvider, paramJavaType, paramBeanDescription, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<?> buildContainerSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 514 */     SerializationConfig localSerializationConfig = paramSerializerProvider.getConfig();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 520 */     if ((!paramBoolean) && (paramJavaType.useStaticType()) && (
/* 521 */       (!paramJavaType.isContainerType()) || (paramJavaType.getContentType().getRawClass() != Object.class))) {
/* 522 */       paramBoolean = true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 527 */     JavaType localJavaType = paramJavaType.getContentType();
/* 528 */     TypeSerializer localTypeSerializer = createTypeSerializer(localSerializationConfig, localJavaType);
/*     */     
/*     */ 
/*     */ 
/* 532 */     if (localTypeSerializer != null) {
/* 533 */       paramBoolean = false;
/*     */     }
/* 535 */     JsonSerializer localJsonSerializer = _findContentSerializer(paramSerializerProvider, paramBeanDescription.getClassInfo());
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 538 */     Iterator localIterator; Serializers localSerializers; Object localObject3; Object localObject4; Object localObject5; if (paramJavaType.isMapLikeType()) {
/* 539 */       localObject1 = (MapLikeType)paramJavaType;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 545 */       localObject2 = _findKeySerializer(paramSerializerProvider, paramBeanDescription.getClassInfo());
/* 546 */       if (((MapLikeType)localObject1).isTrueMapType()) {
/* 547 */         return buildMapSerializer(localSerializationConfig, (MapType)localObject1, paramBeanDescription, paramBoolean, (JsonSerializer)localObject2, localTypeSerializer, localJsonSerializer);
/*     */       }
/*     */       
/*     */ 
/* 551 */       for (localIterator = customSerializers().iterator(); localIterator.hasNext();) { localSerializers = (Serializers)localIterator.next();
/* 552 */         localObject3 = (MapLikeType)paramJavaType;
/* 553 */         localObject4 = localSerializers.findMapLikeSerializer(localSerializationConfig, (MapLikeType)localObject3, paramBeanDescription, (JsonSerializer)localObject2, localTypeSerializer, localJsonSerializer);
/*     */         
/* 555 */         if (localObject4 != null)
/*     */         {
/* 557 */           if (this._factoryConfig.hasSerializerModifiers()) {
/* 558 */             for (localObject5 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject5).hasNext();) { BeanSerializerModifier localBeanSerializerModifier = (BeanSerializerModifier)((Iterator)localObject5).next();
/* 559 */               localObject4 = localBeanSerializerModifier.modifyMapLikeSerializer(localSerializationConfig, (MapLikeType)localObject3, paramBeanDescription, (JsonSerializer)localObject4);
/*     */             }
/*     */           }
/* 562 */           return (JsonSerializer<?>)localObject4;
/*     */         }
/*     */       }
/* 565 */       return null;
/*     */     }
/* 567 */     if (paramJavaType.isCollectionLikeType()) {
/* 568 */       localObject1 = (CollectionLikeType)paramJavaType;
/* 569 */       if (((CollectionLikeType)localObject1).isTrueCollectionType()) {
/* 570 */         return buildCollectionSerializer(localSerializationConfig, (CollectionType)localObject1, paramBeanDescription, paramBoolean, localTypeSerializer, localJsonSerializer);
/*     */       }
/*     */       
/* 573 */       localObject2 = (CollectionLikeType)paramJavaType;
/*     */       
/* 575 */       for (localIterator = customSerializers().iterator(); localIterator.hasNext();) { localSerializers = (Serializers)localIterator.next();
/* 576 */         localObject3 = localSerializers.findCollectionLikeSerializer(localSerializationConfig, (CollectionLikeType)localObject2, paramBeanDescription, localTypeSerializer, localJsonSerializer);
/*     */         
/* 578 */         if (localObject3 != null)
/*     */         {
/* 580 */           if (this._factoryConfig.hasSerializerModifiers()) {
/* 581 */             for (localObject4 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject4).hasNext();) { localObject5 = (BeanSerializerModifier)((Iterator)localObject4).next();
/* 582 */               localObject3 = ((BeanSerializerModifier)localObject5).modifyCollectionLikeSerializer(localSerializationConfig, (CollectionLikeType)localObject2, paramBeanDescription, (JsonSerializer)localObject3);
/*     */             }
/*     */           }
/* 585 */           return (JsonSerializer<?>)localObject3;
/*     */         }
/*     */       }
/*     */       
/* 589 */       return null;
/*     */     }
/* 591 */     if (paramJavaType.isArrayType()) {
/* 592 */       return buildArraySerializer(localSerializationConfig, (ArrayType)paramJavaType, paramBeanDescription, paramBoolean, localTypeSerializer, localJsonSerializer);
/*     */     }
/*     */     
/* 595 */     return null;
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
/*     */   @Deprecated
/*     */   protected final JsonSerializer<?> buildCollectionSerializer(SerializationConfig paramSerializationConfig, CollectionType paramCollectionType, BeanDescription paramBeanDescription, BeanProperty paramBeanProperty, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws JsonMappingException
/*     */   {
/* 612 */     return buildCollectionSerializer(paramSerializationConfig, paramCollectionType, paramBeanDescription, paramBoolean, paramTypeSerializer, paramJsonSerializer);
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
/*     */   protected JsonSerializer<?> buildCollectionSerializer(SerializationConfig paramSerializationConfig, CollectionType paramCollectionType, BeanDescription paramBeanDescription, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws JsonMappingException
/*     */   {
/* 627 */     Object localObject1 = null;
/*     */     
/* 629 */     for (Object localObject2 = customSerializers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (Serializers)((Iterator)localObject2).next();
/* 630 */       localObject1 = ((Serializers)localObject3).findCollectionSerializer(paramSerializationConfig, paramCollectionType, paramBeanDescription, paramTypeSerializer, paramJsonSerializer);
/*     */       
/* 632 */       if (localObject1 != null) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     Object localObject3;
/*     */     
/*     */ 
/* 641 */     if (localObject1 == null) {
/* 642 */       localObject2 = paramBeanDescription.findExpectedFormat(null);
/* 643 */       if ((localObject2 != null) && (((JsonFormat.Value)localObject2).getShape() == JsonFormat.Shape.OBJECT)) {
/* 644 */         return null;
/*     */       }
/* 646 */       localObject3 = paramCollectionType.getRawClass();
/* 647 */       Object localObject4; if (java.util.EnumSet.class.isAssignableFrom((Class)localObject3))
/*     */       {
/* 649 */         localObject4 = paramCollectionType.getContentType();
/*     */         
/* 651 */         if (!((JavaType)localObject4).isEnumType()) {
/* 652 */           localObject4 = null;
/*     */         }
/* 654 */         localObject1 = StdContainerSerializers.enumSetSerializer((JavaType)localObject4);
/*     */       } else {
/* 656 */         localObject4 = paramCollectionType.getContentType().getRawClass();
/* 657 */         if (isIndexedList((Class)localObject3)) {
/* 658 */           if (localObject4 == String.class)
/*     */           {
/* 660 */             if ((paramJsonSerializer == null) || (ClassUtil.isJacksonStdImpl(paramJsonSerializer))) {
/* 661 */               localObject1 = IndexedStringListSerializer.instance;
/*     */             }
/*     */           } else {
/* 664 */             localObject1 = StdContainerSerializers.indexedListSerializer(paramCollectionType.getContentType(), paramBoolean, paramTypeSerializer, paramJsonSerializer);
/*     */           }
/*     */         }
/* 667 */         else if (localObject4 == String.class)
/*     */         {
/* 669 */           if ((paramJsonSerializer == null) || (ClassUtil.isJacksonStdImpl(paramJsonSerializer))) {
/* 670 */             localObject1 = com.shaded.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer.instance;
/*     */           }
/*     */         }
/* 673 */         if (localObject1 == null) {
/* 674 */           localObject1 = StdContainerSerializers.collectionSerializer(paramCollectionType.getContentType(), paramBoolean, paramTypeSerializer, paramJsonSerializer);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 680 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 681 */       for (localObject2 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (BeanSerializerModifier)((Iterator)localObject2).next();
/* 682 */         localObject1 = ((BeanSerializerModifier)localObject3).modifyCollectionSerializer(paramSerializationConfig, paramCollectionType, paramBeanDescription, (JsonSerializer)localObject1);
/*     */       }
/*     */     }
/* 685 */     return (JsonSerializer<?>)localObject1;
/*     */   }
/*     */   
/*     */   protected boolean isIndexedList(Class<?> paramClass)
/*     */   {
/* 690 */     return RandomAccess.class.isAssignableFrom(paramClass);
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
/*     */   protected JsonSerializer<?> buildMapSerializer(SerializationConfig paramSerializationConfig, MapType paramMapType, BeanDescription paramBeanDescription, boolean paramBoolean, JsonSerializer<Object> paramJsonSerializer1, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer2)
/*     */     throws JsonMappingException
/*     */   {
/* 709 */     Object localObject1 = null;
/* 710 */     for (Object localObject2 = customSerializers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (Serializers)((Iterator)localObject2).next();
/* 711 */       localObject1 = ((Serializers)localObject3).findMapSerializer(paramSerializationConfig, paramMapType, paramBeanDescription, paramJsonSerializer1, paramTypeSerializer, paramJsonSerializer2);
/*     */       
/* 713 */       if (localObject1 != null)
/*     */         break;
/*     */     }
/*     */     Object localObject3;
/* 717 */     if (localObject1 == null) {
/* 718 */       if (EnumMap.class.isAssignableFrom(paramMapType.getRawClass())) {
/* 719 */         localObject2 = paramMapType.getKeyType();
/*     */         
/* 721 */         localObject3 = null;
/* 722 */         if (((JavaType)localObject2).isEnumType())
/*     */         {
/* 724 */           Class localClass = ((JavaType)localObject2).getRawClass();
/* 725 */           localObject3 = EnumValues.construct(localClass, paramSerializationConfig.getAnnotationIntrospector());
/*     */         }
/* 727 */         localObject1 = new EnumMapSerializer(paramMapType.getContentType(), paramBoolean, (EnumValues)localObject3, paramTypeSerializer, paramJsonSerializer2);
/*     */       }
/*     */       else {
/* 730 */         localObject1 = com.shaded.fasterxml.jackson.databind.ser.std.MapSerializer.construct(paramSerializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(paramBeanDescription.getClassInfo()), paramMapType, paramBoolean, paramTypeSerializer, paramJsonSerializer1, paramJsonSerializer2);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 736 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 737 */       for (localObject2 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (BeanSerializerModifier)((Iterator)localObject2).next();
/* 738 */         localObject1 = ((BeanSerializerModifier)localObject3).modifyMapSerializer(paramSerializationConfig, paramMapType, paramBeanDescription, (JsonSerializer)localObject1);
/*     */       }
/*     */     }
/* 741 */     return (JsonSerializer<?>)localObject1;
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
/*     */   protected JsonSerializer<?> buildArraySerializer(SerializationConfig paramSerializationConfig, ArrayType paramArrayType, BeanDescription paramBeanDescription, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws JsonMappingException
/*     */   {
/* 760 */     Object localObject1 = null;
/*     */     
/* 762 */     for (Object localObject2 = customSerializers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (Serializers)((Iterator)localObject2).next();
/* 763 */       localObject1 = ((Serializers)localObject3).findArraySerializer(paramSerializationConfig, paramArrayType, paramBeanDescription, paramTypeSerializer, paramJsonSerializer);
/*     */       
/* 765 */       if (localObject1 != null)
/*     */         break;
/*     */     }
/*     */     Object localObject3;
/* 769 */     if (localObject1 == null) {
/* 770 */       localObject2 = paramArrayType.getRawClass();
/*     */       
/* 772 */       if ((paramJsonSerializer == null) || (ClassUtil.isJacksonStdImpl(paramJsonSerializer))) {
/* 773 */         if (String[].class == localObject2) {
/* 774 */           localObject1 = StringArraySerializer.instance;
/*     */         }
/*     */         else {
/* 777 */           localObject1 = com.shaded.fasterxml.jackson.databind.ser.std.StdArraySerializers.findStandardImpl((Class)localObject2);
/*     */         }
/*     */       }
/* 780 */       if (localObject1 == null) {
/* 781 */         localObject1 = new com.shaded.fasterxml.jackson.databind.ser.std.ObjectArraySerializer(paramArrayType.getContentType(), paramBoolean, paramTypeSerializer, paramJsonSerializer);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 786 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 787 */       for (localObject2 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (BeanSerializerModifier)((Iterator)localObject2).next();
/* 788 */         localObject1 = ((BeanSerializerModifier)localObject3).modifyArraySerializer(paramSerializationConfig, paramArrayType, paramBeanDescription, (JsonSerializer)localObject1);
/*     */       }
/*     */     }
/* 791 */     return (JsonSerializer<?>)localObject1;
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
/*     */   protected JsonSerializer<?> buildIteratorSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 806 */     JavaType localJavaType = paramJavaType.containedType(0);
/* 807 */     if (localJavaType == null) {
/* 808 */       localJavaType = TypeFactory.unknownType();
/*     */     }
/* 810 */     TypeSerializer localTypeSerializer = createTypeSerializer(paramSerializationConfig, localJavaType);
/* 811 */     return StdContainerSerializers.iteratorSerializer(localJavaType, usesStaticTyping(paramSerializationConfig, paramBeanDescription, localTypeSerializer), localTypeSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<?> buildIterableSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 821 */     JavaType localJavaType = paramJavaType.containedType(0);
/* 822 */     if (localJavaType == null) {
/* 823 */       localJavaType = TypeFactory.unknownType();
/*     */     }
/* 825 */     TypeSerializer localTypeSerializer = createTypeSerializer(paramSerializationConfig, localJavaType);
/* 826 */     return StdContainerSerializers.iterableSerializer(localJavaType, usesStaticTyping(paramSerializationConfig, paramBeanDescription, localTypeSerializer), localTypeSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<?> buildEnumSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 839 */     JsonFormat.Value localValue = paramBeanDescription.findExpectedFormat(null);
/* 840 */     if ((localValue != null) && (localValue.getShape() == JsonFormat.Shape.OBJECT))
/*     */     {
/* 842 */       ((BasicBeanDescription)paramBeanDescription).removeProperty("declaringClass");
/*     */       
/* 844 */       return null;
/*     */     }
/*     */     
/* 847 */     Class localClass = paramJavaType.getRawClass();
/* 848 */     Object localObject = com.shaded.fasterxml.jackson.databind.ser.std.EnumSerializer.construct(localClass, paramSerializationConfig, paramBeanDescription, localValue);
/*     */     
/* 850 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 851 */       for (BeanSerializerModifier localBeanSerializerModifier : this._factoryConfig.serializerModifiers()) {
/* 852 */         localObject = localBeanSerializerModifier.modifyEnumSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription, (JsonSerializer)localObject);
/*     */       }
/*     */     }
/* 855 */     return (JsonSerializer<?>)localObject;
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
/*     */   protected <T extends JavaType> T modifyTypeByAnnotation(SerializationConfig paramSerializationConfig, Annotated paramAnnotated, T paramT)
/*     */   {
/* 872 */     Class localClass = paramSerializationConfig.getAnnotationIntrospector().findSerializationType(paramAnnotated);
/* 873 */     if (localClass != null) {
/*     */       try {
/* 875 */         paramT = paramT.widenBy(localClass);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 877 */         throw new IllegalArgumentException("Failed to widen type " + paramT + " with concrete-type annotation (value " + localClass.getName() + "), method '" + paramAnnotated.getName() + "': " + localIllegalArgumentException.getMessage());
/*     */       }
/*     */     }
/* 880 */     return modifySecondaryTypesByAnnotation(paramSerializationConfig, paramAnnotated, paramT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected static <T extends JavaType> T modifySecondaryTypesByAnnotation(SerializationConfig paramSerializationConfig, Annotated paramAnnotated, T paramT)
/*     */   {
/* 887 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/*     */     
/* 889 */     if (paramT.isContainerType()) {
/* 890 */       Class localClass1 = localAnnotationIntrospector.findSerializationKeyType(paramAnnotated, paramT.getKeyType());
/* 891 */       if (localClass1 != null)
/*     */       {
/* 893 */         if (!(paramT instanceof MapType)) {
/* 894 */           throw new IllegalArgumentException("Illegal key-type annotation: type " + paramT + " is not a Map type");
/*     */         }
/*     */         try {
/* 897 */           paramT = ((MapType)paramT).widenKey(localClass1);
/*     */         } catch (IllegalArgumentException localIllegalArgumentException1) {
/* 899 */           throw new IllegalArgumentException("Failed to narrow key type " + paramT + " with key-type annotation (" + localClass1.getName() + "): " + localIllegalArgumentException1.getMessage());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 904 */       Class localClass2 = localAnnotationIntrospector.findSerializationContentType(paramAnnotated, paramT.getContentType());
/* 905 */       if (localClass2 != null) {
/*     */         try {
/* 907 */           paramT = paramT.widenContentsBy(localClass2);
/*     */         } catch (IllegalArgumentException localIllegalArgumentException2) {
/* 909 */           throw new IllegalArgumentException("Failed to narrow content type " + paramT + " with content-type annotation (" + localClass2.getName() + "): " + localIllegalArgumentException2.getMessage());
/*     */         }
/*     */       }
/*     */     }
/* 913 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _findKeySerializer(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
/*     */     throws JsonMappingException
/*     */   {
/* 925 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializerProvider.getAnnotationIntrospector();
/* 926 */     Object localObject = localAnnotationIntrospector.findKeySerializer(paramAnnotated);
/* 927 */     if (localObject != null) {
/* 928 */       return paramSerializerProvider.serializerInstance(paramAnnotated, localObject);
/*     */     }
/* 930 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _findContentSerializer(SerializerProvider paramSerializerProvider, Annotated paramAnnotated)
/*     */     throws JsonMappingException
/*     */   {
/* 942 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializerProvider.getAnnotationIntrospector();
/* 943 */     Object localObject = localAnnotationIntrospector.findContentSerializer(paramAnnotated);
/* 944 */     if (localObject != null) {
/* 945 */       return paramSerializerProvider.serializerInstance(paramAnnotated, localObject);
/*     */     }
/* 947 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected final boolean usesStaticTyping(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty)
/*     */   {
/* 957 */     return usesStaticTyping(paramSerializationConfig, paramBeanDescription, paramTypeSerializer);
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
/*     */   protected boolean usesStaticTyping(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, TypeSerializer paramTypeSerializer)
/*     */   {
/* 974 */     if (paramTypeSerializer != null) {
/* 975 */       return false;
/*     */     }
/* 977 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/* 978 */     JsonSerialize.Typing localTyping = localAnnotationIntrospector.findSerializationTyping(paramBeanDescription.getClassInfo());
/* 979 */     if (localTyping != null) {
/* 980 */       return localTyping == JsonSerialize.Typing.STATIC;
/*     */     }
/* 982 */     return paramSerializationConfig.isEnabled(com.shaded.fasterxml.jackson.databind.MapperFeature.USE_STATIC_TYPING);
/*     */   }
/*     */   
/*     */   protected Class<?> _verifyAsClass(Object paramObject, String paramString, Class<?> paramClass)
/*     */   {
/* 987 */     if (paramObject == null) {
/* 988 */       return null;
/*     */     }
/* 990 */     if (!(paramObject instanceof Class)) {
/* 991 */       throw new IllegalStateException("AnnotationIntrospector." + paramString + "() returned value of type " + paramObject.getClass().getName() + ": expected type JsonSerializer or Class<JsonSerializer> instead");
/*     */     }
/* 993 */     Class localClass = (Class)paramObject;
/* 994 */     if ((localClass == paramClass) || (localClass == com.shaded.fasterxml.jackson.databind.annotation.NoClass.class)) {
/* 995 */       return null;
/*     */     }
/* 997 */     return localClass;
/*     */   }
/*     */   
/*     */   public abstract SerializerFactory withConfig(SerializerFactoryConfig paramSerializerFactoryConfig);
/*     */   
/*     */   public abstract JsonSerializer<Object> createSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType)
/*     */     throws JsonMappingException;
/*     */   
/*     */   protected abstract Iterable<Serializers> customSerializers();
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/BasicSerializerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */