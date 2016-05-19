/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Shape;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DeserializerCache
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  44 */   protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _cachedDeserializers = new ConcurrentHashMap(64, 0.75F, 2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  52 */   protected final HashMap<JavaType, JsonDeserializer<Object>> _incompleteDeserializers = new HashMap(8);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Object writeReplace()
/*     */   {
/*  71 */     this._incompleteDeserializers.clear();
/*     */     
/*  73 */     return this;
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
/*     */   public int cachedDeserializersCount()
/*     */   {
/*  95 */     return this._cachedDeserializers.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flushCachedDeserializers()
/*     */   {
/* 106 */     this._cachedDeserializers.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<Object> findValueDeserializer(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 141 */     JsonDeserializer localJsonDeserializer = _findCachedDeserializer(paramJavaType);
/* 142 */     if (localJsonDeserializer != null) {
/* 143 */       return localJsonDeserializer;
/*     */     }
/*     */     
/* 146 */     localJsonDeserializer = _createAndCacheValueDeserializer(paramDeserializationContext, paramDeserializerFactory, paramJavaType);
/* 147 */     if (localJsonDeserializer == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 152 */       localJsonDeserializer = _handleUnknownValueDeserializer(paramJavaType);
/*     */     }
/* 154 */     return localJsonDeserializer;
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
/*     */   public KeyDeserializer findKeyDeserializer(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 169 */     KeyDeserializer localKeyDeserializer = paramDeserializerFactory.createKeyDeserializer(paramDeserializationContext, paramJavaType);
/* 170 */     if (localKeyDeserializer == null) {
/* 171 */       return _handleUnknownKeyDeserializer(paramJavaType);
/*     */     }
/*     */     
/* 174 */     if ((localKeyDeserializer instanceof ResolvableDeserializer)) {
/* 175 */       ((ResolvableDeserializer)localKeyDeserializer).resolve(paramDeserializationContext);
/*     */     }
/* 177 */     return localKeyDeserializer;
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
/*     */   public boolean hasValueDeserializerFor(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory, JavaType paramJavaType)
/*     */   {
/* 191 */     JsonDeserializer localJsonDeserializer = _findCachedDeserializer(paramJavaType);
/* 192 */     if (localJsonDeserializer == null) {
/*     */       try {
/* 194 */         localJsonDeserializer = _createAndCacheValueDeserializer(paramDeserializationContext, paramDeserializerFactory, paramJavaType);
/*     */       } catch (Exception localException) {
/* 196 */         return false;
/*     */       }
/*     */     }
/* 199 */     return localJsonDeserializer != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<Object> _findCachedDeserializer(JavaType paramJavaType)
/*     */   {
/* 210 */     if (paramJavaType == null) {
/* 211 */       throw new IllegalArgumentException("Null JavaType passed");
/*     */     }
/* 213 */     return (JsonDeserializer)this._cachedDeserializers.get(paramJavaType);
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
/*     */   protected JsonDeserializer<Object> _createAndCacheValueDeserializer(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 231 */     synchronized (this._incompleteDeserializers)
/*     */     {
/* 233 */       JsonDeserializer localJsonDeserializer1 = _findCachedDeserializer(paramJavaType);
/* 234 */       if (localJsonDeserializer1 != null) {
/* 235 */         return localJsonDeserializer1;
/*     */       }
/* 237 */       int i = this._incompleteDeserializers.size();
/*     */       
/* 239 */       if (i > 0) {
/* 240 */         localJsonDeserializer1 = (JsonDeserializer)this._incompleteDeserializers.get(paramJavaType);
/* 241 */         if (localJsonDeserializer1 != null) {
/* 242 */           return localJsonDeserializer1;
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 247 */         JsonDeserializer localJsonDeserializer2 = _createAndCache2(paramDeserializationContext, paramDeserializerFactory, paramJavaType);
/*     */         
/*     */ 
/* 250 */         if ((i == 0) && (this._incompleteDeserializers.size() > 0))
/* 251 */           this._incompleteDeserializers.clear(); return localJsonDeserializer2;
/*     */       }
/*     */       finally
/*     */       {
/* 250 */         if ((i == 0) && (this._incompleteDeserializers.size() > 0)) {
/* 251 */           this._incompleteDeserializers.clear();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<Object> _createAndCache2(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/*     */     JsonDeserializer localJsonDeserializer;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 267 */       localJsonDeserializer = _createDeserializer(paramDeserializationContext, paramDeserializerFactory, paramJavaType);
/*     */ 
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException)
/*     */     {
/* 272 */       throw new JsonMappingException(localIllegalArgumentException.getMessage(), null, localIllegalArgumentException);
/*     */     }
/* 274 */     if (localJsonDeserializer == null) {
/* 275 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 281 */     boolean bool1 = localJsonDeserializer instanceof ResolvableDeserializer;
/* 282 */     boolean bool2 = localJsonDeserializer.isCachable();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 296 */     if (bool1) {
/* 297 */       this._incompleteDeserializers.put(paramJavaType, localJsonDeserializer);
/* 298 */       ((ResolvableDeserializer)localJsonDeserializer).resolve(paramDeserializationContext);
/* 299 */       this._incompleteDeserializers.remove(paramJavaType);
/*     */     }
/* 301 */     if (bool2) {
/* 302 */       this._cachedDeserializers.put(paramJavaType, localJsonDeserializer);
/*     */     }
/* 304 */     return localJsonDeserializer;
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
/*     */   protected JsonDeserializer<Object> _createDeserializer(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 323 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*     */     
/*     */ 
/* 326 */     if ((paramJavaType.isAbstract()) || (paramJavaType.isMapLikeType()) || (paramJavaType.isCollectionLikeType())) {
/* 327 */       paramJavaType = paramDeserializerFactory.mapAbstractType(localDeserializationConfig, paramJavaType);
/*     */     }
/* 329 */     BeanDescription localBeanDescription = localDeserializationConfig.introspect(paramJavaType);
/*     */     
/* 331 */     JsonDeserializer localJsonDeserializer = findDeserializerFromAnnotation(paramDeserializationContext, localBeanDescription.getClassInfo());
/*     */     
/* 333 */     if (localJsonDeserializer != null) {
/* 334 */       return localJsonDeserializer;
/*     */     }
/*     */     
/*     */ 
/* 338 */     JavaType localJavaType1 = modifyTypeByAnnotation(paramDeserializationContext, localBeanDescription.getClassInfo(), paramJavaType);
/* 339 */     if (localJavaType1 != paramJavaType) {
/* 340 */       paramJavaType = localJavaType1;
/* 341 */       localBeanDescription = localDeserializationConfig.introspect(localJavaType1);
/*     */     }
/*     */     
/*     */ 
/* 345 */     Class localClass = localBeanDescription.findPOJOBuilder();
/* 346 */     if (localClass != null) {
/* 347 */       return paramDeserializerFactory.createBuilderBasedDeserializer(paramDeserializationContext, paramJavaType, localBeanDescription, localClass);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 352 */     Converter localConverter = localBeanDescription.findDeserializationConverter();
/* 353 */     if (localConverter == null) {
/* 354 */       return _createDeserializer2(paramDeserializationContext, paramDeserializerFactory, paramJavaType, localBeanDescription);
/*     */     }
/*     */     
/* 357 */     JavaType localJavaType2 = localConverter.getInputType(paramDeserializationContext.getTypeFactory());
/* 358 */     return new StdDelegatingDeserializer(localConverter, localJavaType2, _createDeserializer2(paramDeserializationContext, paramDeserializerFactory, localJavaType2, localBeanDescription));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<?> _createDeserializer2(DeserializationContext paramDeserializationContext, DeserializerFactory paramDeserializerFactory, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 366 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*     */     
/* 368 */     if (paramJavaType.isEnumType()) {
/* 369 */       return paramDeserializerFactory.createEnumDeserializer(paramDeserializationContext, paramJavaType, paramBeanDescription);
/*     */     }
/* 371 */     if (paramJavaType.isContainerType()) {
/* 372 */       if (paramJavaType.isArrayType())
/* 373 */         return paramDeserializerFactory.createArrayDeserializer(paramDeserializationContext, (ArrayType)paramJavaType, paramBeanDescription);
/*     */       Object localObject;
/* 375 */       if (paramJavaType.isMapLikeType()) {
/* 376 */         localObject = (MapLikeType)paramJavaType;
/* 377 */         if (((MapLikeType)localObject).isTrueMapType()) {
/* 378 */           return paramDeserializerFactory.createMapDeserializer(paramDeserializationContext, (MapType)localObject, paramBeanDescription);
/*     */         }
/* 380 */         return paramDeserializerFactory.createMapLikeDeserializer(paramDeserializationContext, (MapLikeType)localObject, paramBeanDescription);
/*     */       }
/* 382 */       if (paramJavaType.isCollectionLikeType())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 388 */         localObject = paramBeanDescription.findExpectedFormat(null);
/* 389 */         if ((localObject == null) || (((JsonFormat.Value)localObject).getShape() != JsonFormat.Shape.OBJECT)) {
/* 390 */           CollectionLikeType localCollectionLikeType = (CollectionLikeType)paramJavaType;
/* 391 */           if (localCollectionLikeType.isTrueCollectionType()) {
/* 392 */             return paramDeserializerFactory.createCollectionDeserializer(paramDeserializationContext, (CollectionType)localCollectionLikeType, paramBeanDescription);
/*     */           }
/* 394 */           return paramDeserializerFactory.createCollectionLikeDeserializer(paramDeserializationContext, localCollectionLikeType, paramBeanDescription);
/*     */         }
/*     */       }
/*     */     }
/* 398 */     if (JsonNode.class.isAssignableFrom(paramJavaType.getRawClass())) {
/* 399 */       return paramDeserializerFactory.createTreeDeserializer(localDeserializationConfig, paramJavaType, paramBeanDescription);
/*     */     }
/* 401 */     return paramDeserializerFactory.createBeanDeserializer(paramDeserializationContext, paramJavaType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext paramDeserializationContext, Annotated paramAnnotated)
/*     */     throws JsonMappingException
/*     */   {
/* 413 */     Object localObject = paramDeserializationContext.getAnnotationIntrospector().findDeserializer(paramAnnotated);
/* 414 */     if (localObject == null) {
/* 415 */       return null;
/*     */     }
/* 417 */     JsonDeserializer localJsonDeserializer = paramDeserializationContext.deserializerInstance(paramAnnotated, localObject);
/*     */     
/* 419 */     return findConvertingDeserializer(paramDeserializationContext, paramAnnotated, localJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<Object> findConvertingDeserializer(DeserializationContext paramDeserializationContext, Annotated paramAnnotated, JsonDeserializer<Object> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/* 432 */     Converter localConverter = findConverter(paramDeserializationContext, paramAnnotated);
/* 433 */     if (localConverter == null) {
/* 434 */       return paramJsonDeserializer;
/*     */     }
/* 436 */     JavaType localJavaType = localConverter.getInputType(paramDeserializationContext.getTypeFactory());
/* 437 */     return new StdDelegatingDeserializer(localConverter, localJavaType, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */   protected Converter<Object, Object> findConverter(DeserializationContext paramDeserializationContext, Annotated paramAnnotated)
/*     */     throws JsonMappingException
/*     */   {
/* 444 */     Object localObject = paramDeserializationContext.getAnnotationIntrospector().findDeserializationConverter(paramAnnotated);
/* 445 */     if (localObject == null) {
/* 446 */       return null;
/*     */     }
/* 448 */     return paramDeserializationContext.converterInstance(paramAnnotated, localObject);
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
/*     */   private JavaType modifyTypeByAnnotation(DeserializationContext paramDeserializationContext, Annotated paramAnnotated, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 471 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/* 472 */     Class localClass1 = localAnnotationIntrospector.findDeserializationType(paramAnnotated, paramJavaType);
/* 473 */     if (localClass1 != null) {
/*     */       try {
/* 475 */         paramJavaType = paramJavaType.narrowBy(localClass1);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException1) {
/* 477 */         throw new JsonMappingException("Failed to narrow type " + paramJavaType + " with concrete-type annotation (value " + localClass1.getName() + "), method '" + paramAnnotated.getName() + "': " + localIllegalArgumentException1.getMessage(), null, localIllegalArgumentException1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 482 */     if (paramJavaType.isContainerType()) {
/* 483 */       Class localClass2 = localAnnotationIntrospector.findDeserializationKeyType(paramAnnotated, paramJavaType.getKeyType());
/* 484 */       if (localClass2 != null)
/*     */       {
/* 486 */         if (!(paramJavaType instanceof MapLikeType)) {
/* 487 */           throw new JsonMappingException("Illegal key-type annotation: type " + paramJavaType + " is not a Map(-like) type");
/*     */         }
/*     */         try {
/* 490 */           paramJavaType = ((MapLikeType)paramJavaType).narrowKey(localClass2);
/*     */         } catch (IllegalArgumentException localIllegalArgumentException2) {
/* 492 */           throw new JsonMappingException("Failed to narrow key type " + paramJavaType + " with key-type annotation (" + localClass2.getName() + "): " + localIllegalArgumentException2.getMessage(), null, localIllegalArgumentException2);
/*     */         }
/*     */       }
/* 495 */       JavaType localJavaType1 = paramJavaType.getKeyType();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 500 */       if ((localJavaType1 != null) && (localJavaType1.getValueHandler() == null)) {
/* 501 */         localObject1 = localAnnotationIntrospector.findKeyDeserializer(paramAnnotated);
/* 502 */         if (localObject1 != null) {
/* 503 */           KeyDeserializer localKeyDeserializer = paramDeserializationContext.keyDeserializerInstance(paramAnnotated, localObject1);
/* 504 */           if (localKeyDeserializer != null) {
/* 505 */             paramJavaType = ((MapLikeType)paramJavaType).withKeyValueHandler(localKeyDeserializer);
/* 506 */             localJavaType1 = paramJavaType.getKeyType();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 512 */       Object localObject1 = localAnnotationIntrospector.findDeserializationContentType(paramAnnotated, paramJavaType.getContentType());
/* 513 */       if (localObject1 != null) {
/*     */         try {
/* 515 */           paramJavaType = paramJavaType.narrowContentsBy((Class)localObject1);
/*     */         } catch (IllegalArgumentException localIllegalArgumentException3) {
/* 517 */           throw new JsonMappingException("Failed to narrow content type " + paramJavaType + " with content-type annotation (" + ((Class)localObject1).getName() + "): " + localIllegalArgumentException3.getMessage(), null, localIllegalArgumentException3);
/*     */         }
/*     */       }
/*     */       
/* 521 */       JavaType localJavaType2 = paramJavaType.getContentType();
/* 522 */       if (localJavaType2.getValueHandler() == null) {
/* 523 */         Object localObject2 = localAnnotationIntrospector.findContentDeserializer(paramAnnotated);
/* 524 */         if (localObject2 != null) {
/* 525 */           JsonDeserializer localJsonDeserializer = null;
/* 526 */           if ((localObject2 instanceof JsonDeserializer)) {
/* 527 */             localObject2 = (JsonDeserializer)localObject2;
/*     */           } else {
/* 529 */             Class localClass3 = _verifyAsClass(localObject2, "findContentDeserializer", JsonDeserializer.None.class);
/* 530 */             if (localClass3 != null) {
/* 531 */               localJsonDeserializer = paramDeserializationContext.deserializerInstance(paramAnnotated, localClass3);
/*     */             }
/*     */           }
/* 534 */           if (localJsonDeserializer != null) {
/* 535 */             paramJavaType = paramJavaType.withContentValueHandler(localJsonDeserializer);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 540 */     return paramJavaType;
/*     */   }
/*     */   
/*     */   private Class<?> _verifyAsClass(Object paramObject, String paramString, Class<?> paramClass)
/*     */   {
/* 545 */     if (paramObject == null) {
/* 546 */       return null;
/*     */     }
/* 548 */     if (!(paramObject instanceof Class)) {
/* 549 */       throw new IllegalStateException("AnnotationIntrospector." + paramString + "() returned value of type " + paramObject.getClass().getName() + ": expected type JsonSerializer or Class<JsonSerializer> instead");
/*     */     }
/* 551 */     Class localClass = (Class)paramObject;
/* 552 */     if ((localClass == paramClass) || (localClass == NoClass.class)) {
/* 553 */       return null;
/*     */     }
/* 555 */     return localClass;
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
/*     */   protected JsonDeserializer<Object> _handleUnknownValueDeserializer(JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 570 */     Class localClass = paramJavaType.getRawClass();
/* 571 */     if (!ClassUtil.isConcrete(localClass)) {
/* 572 */       throw new JsonMappingException("Can not find a Value deserializer for abstract type " + paramJavaType);
/*     */     }
/* 574 */     throw new JsonMappingException("Can not find a Value deserializer for type " + paramJavaType);
/*     */   }
/*     */   
/*     */   protected KeyDeserializer _handleUnknownKeyDeserializer(JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 580 */     throw new JsonMappingException("Can not find a (Map) Key deserializer for type " + paramJavaType);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/DeserializerCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */