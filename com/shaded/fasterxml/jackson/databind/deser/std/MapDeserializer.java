/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class MapDeserializer
/*     */   extends ContainerDeserializerBase<Map<Object, Object>>
/*     */   implements ContextualDeserializer, ResolvableDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = -3378654289961736240L;
/*     */   protected final JavaType _mapType;
/*     */   protected final KeyDeserializer _keyDeserializer;
/*     */   protected boolean _standardStringKey;
/*     */   protected final JsonDeserializer<Object> _valueDeserializer;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final boolean _hasDefaultCreator;
/*     */   protected JsonDeserializer<Object> _delegateDeserializer;
/*     */   protected PropertyBasedCreator _propertyBasedCreator;
/*     */   protected HashSet<String> _ignorableProperties;
/*     */   
/*     */   public MapDeserializer(JavaType paramJavaType, ValueInstantiator paramValueInstantiator, KeyDeserializer paramKeyDeserializer, JsonDeserializer<Object> paramJsonDeserializer, TypeDeserializer paramTypeDeserializer)
/*     */   {
/*  98 */     super(Map.class);
/*  99 */     this._mapType = paramJavaType;
/* 100 */     this._keyDeserializer = paramKeyDeserializer;
/* 101 */     this._valueDeserializer = paramJsonDeserializer;
/* 102 */     this._valueTypeDeserializer = paramTypeDeserializer;
/* 103 */     this._valueInstantiator = paramValueInstantiator;
/* 104 */     this._hasDefaultCreator = paramValueInstantiator.canCreateUsingDefault();
/* 105 */     this._delegateDeserializer = null;
/* 106 */     this._propertyBasedCreator = null;
/* 107 */     this._standardStringKey = _isStdKeyDeser(paramJavaType, paramKeyDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MapDeserializer(MapDeserializer paramMapDeserializer)
/*     */   {
/* 116 */     super(paramMapDeserializer._valueClass);
/* 117 */     this._mapType = paramMapDeserializer._mapType;
/* 118 */     this._keyDeserializer = paramMapDeserializer._keyDeserializer;
/* 119 */     this._valueDeserializer = paramMapDeserializer._valueDeserializer;
/* 120 */     this._valueTypeDeserializer = paramMapDeserializer._valueTypeDeserializer;
/* 121 */     this._valueInstantiator = paramMapDeserializer._valueInstantiator;
/* 122 */     this._propertyBasedCreator = paramMapDeserializer._propertyBasedCreator;
/* 123 */     this._delegateDeserializer = paramMapDeserializer._delegateDeserializer;
/* 124 */     this._hasDefaultCreator = paramMapDeserializer._hasDefaultCreator;
/*     */     
/* 126 */     this._ignorableProperties = paramMapDeserializer._ignorableProperties;
/*     */     
/* 128 */     this._standardStringKey = paramMapDeserializer._standardStringKey;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MapDeserializer(MapDeserializer paramMapDeserializer, KeyDeserializer paramKeyDeserializer, JsonDeserializer<Object> paramJsonDeserializer, TypeDeserializer paramTypeDeserializer, HashSet<String> paramHashSet)
/*     */   {
/* 136 */     super(paramMapDeserializer._valueClass);
/* 137 */     this._mapType = paramMapDeserializer._mapType;
/* 138 */     this._keyDeserializer = paramKeyDeserializer;
/* 139 */     this._valueDeserializer = paramJsonDeserializer;
/* 140 */     this._valueTypeDeserializer = paramTypeDeserializer;
/* 141 */     this._valueInstantiator = paramMapDeserializer._valueInstantiator;
/* 142 */     this._propertyBasedCreator = paramMapDeserializer._propertyBasedCreator;
/* 143 */     this._delegateDeserializer = paramMapDeserializer._delegateDeserializer;
/* 144 */     this._hasDefaultCreator = paramMapDeserializer._hasDefaultCreator;
/* 145 */     this._ignorableProperties = paramHashSet;
/*     */     
/* 147 */     this._standardStringKey = _isStdKeyDeser(this._mapType, paramKeyDeserializer);
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
/*     */   protected MapDeserializer withResolved(KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer, HashSet<String> paramHashSet)
/*     */   {
/* 160 */     if ((this._keyDeserializer == paramKeyDeserializer) && (this._valueDeserializer == paramJsonDeserializer) && (this._valueTypeDeserializer == paramTypeDeserializer) && (this._ignorableProperties == paramHashSet))
/*     */     {
/* 162 */       return this;
/*     */     }
/* 164 */     return new MapDeserializer(this, paramKeyDeserializer, paramJsonDeserializer, paramTypeDeserializer, paramHashSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean _isStdKeyDeser(JavaType paramJavaType, KeyDeserializer paramKeyDeserializer)
/*     */   {
/* 174 */     if (paramKeyDeserializer == null) {
/* 175 */       return true;
/*     */     }
/* 177 */     JavaType localJavaType = paramJavaType.getKeyType();
/* 178 */     if (localJavaType == null) {
/* 179 */       return true;
/*     */     }
/* 181 */     Class localClass = localJavaType.getRawClass();
/* 182 */     return ((localClass == String.class) || (localClass == Object.class)) && (isDefaultKeyDeserializer(paramKeyDeserializer));
/*     */   }
/*     */   
/*     */ 
/*     */   public void setIgnorableProperties(String[] paramArrayOfString)
/*     */   {
/* 188 */     this._ignorableProperties = ((paramArrayOfString == null) || (paramArrayOfString.length == 0) ? null : ArrayBuilders.arrayToSet(paramArrayOfString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resolve(DeserializationContext paramDeserializationContext)
/*     */     throws JsonMappingException
/*     */   {
/*     */     Object localObject;
/*     */     
/*     */ 
/*     */ 
/* 202 */     if (this._valueInstantiator.canCreateUsingDelegate()) {
/* 203 */       localObject = this._valueInstantiator.getDelegateType(paramDeserializationContext.getConfig());
/* 204 */       if (localObject == null) {
/* 205 */         throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._mapType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 213 */       this._delegateDeserializer = findDeserializer(paramDeserializationContext, (JavaType)localObject, null);
/*     */     }
/* 215 */     if (this._valueInstantiator.canCreateFromObjectWith()) {
/* 216 */       localObject = this._valueInstantiator.getFromObjectArguments(paramDeserializationContext.getConfig());
/* 217 */       this._propertyBasedCreator = PropertyBasedCreator.construct(paramDeserializationContext, this._valueInstantiator, (SettableBeanProperty[])localObject);
/*     */     }
/* 219 */     this._standardStringKey = _isStdKeyDeser(this._mapType, this._keyDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 230 */     KeyDeserializer localKeyDeserializer = this._keyDeserializer;
/* 231 */     if (localKeyDeserializer == null) {
/* 232 */       localKeyDeserializer = paramDeserializationContext.findKeyDeserializer(this._mapType.getKeyType(), paramBeanProperty);
/*     */     }
/* 234 */     else if ((localKeyDeserializer instanceof ContextualKeyDeserializer)) {
/* 235 */       localKeyDeserializer = ((ContextualKeyDeserializer)localKeyDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/* 238 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/*     */     
/* 240 */     localJsonDeserializer = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, localJsonDeserializer);
/* 241 */     if (localJsonDeserializer == null) {
/* 242 */       localJsonDeserializer = paramDeserializationContext.findContextualValueDeserializer(this._mapType.getContentType(), paramBeanProperty);
/*     */     }
/* 244 */     else if ((localJsonDeserializer instanceof ContextualDeserializer)) {
/* 245 */       localJsonDeserializer = ((ContextualDeserializer)localJsonDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/* 248 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/* 249 */     if (localTypeDeserializer != null) {
/* 250 */       localTypeDeserializer = localTypeDeserializer.forProperty(paramBeanProperty);
/*     */     }
/* 252 */     HashSet localHashSet = this._ignorableProperties;
/* 253 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/* 254 */     if ((localAnnotationIntrospector != null) && (paramBeanProperty != null)) {
/* 255 */       String[] arrayOfString1 = localAnnotationIntrospector.findPropertiesToIgnore(paramBeanProperty.getMember());
/* 256 */       if (arrayOfString1 != null) {
/* 257 */         localHashSet = localHashSet == null ? new HashSet() : new HashSet(localHashSet);
/* 258 */         for (String str : arrayOfString1) {
/* 259 */           localHashSet.add(str);
/*     */         }
/*     */       }
/*     */     }
/* 263 */     return withResolved(localKeyDeserializer, localTypeDeserializer, localJsonDeserializer, localHashSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType getContentType()
/*     */   {
/* 274 */     return this._mapType.getContentType();
/*     */   }
/*     */   
/*     */   public JsonDeserializer<Object> getContentDeserializer()
/*     */   {
/* 279 */     return this._valueDeserializer;
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
/*     */   public Map<Object, Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 293 */     if (this._propertyBasedCreator != null) {
/* 294 */       return _deserializeUsingCreator(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 296 */     if (this._delegateDeserializer != null) {
/* 297 */       return (Map)this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */     
/* 300 */     if (!this._hasDefaultCreator) {
/* 301 */       throw paramDeserializationContext.instantiationException(getMapClass(), "No default constructor found");
/*     */     }
/*     */     
/* 304 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 305 */     if ((localJsonToken != JsonToken.START_OBJECT) && (localJsonToken != JsonToken.FIELD_NAME) && (localJsonToken != JsonToken.END_OBJECT))
/*     */     {
/* 307 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/* 308 */         return (Map)this._valueInstantiator.createFromString(paramDeserializationContext, paramJsonParser.getText());
/*     */       }
/* 310 */       throw paramDeserializationContext.mappingException(getMapClass());
/*     */     }
/* 312 */     Map localMap = (Map)this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 313 */     if (this._standardStringKey) {
/* 314 */       _readAndBindStringMap(paramJsonParser, paramDeserializationContext, localMap);
/* 315 */       return localMap;
/*     */     }
/* 317 */     _readAndBind(paramJsonParser, paramDeserializationContext, localMap);
/* 318 */     return localMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<Object, Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Map<Object, Object> paramMap)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 327 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 328 */     if ((localJsonToken != JsonToken.START_OBJECT) && (localJsonToken != JsonToken.FIELD_NAME)) {
/* 329 */       throw paramDeserializationContext.mappingException(getMapClass());
/*     */     }
/* 331 */     if (this._standardStringKey) {
/* 332 */       _readAndBindStringMap(paramJsonParser, paramDeserializationContext, paramMap);
/* 333 */       return paramMap;
/*     */     }
/* 335 */     _readAndBind(paramJsonParser, paramDeserializationContext, paramMap);
/* 336 */     return paramMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 345 */     return paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 355 */   public final Class<?> getMapClass() { return this._mapType.getRawClass(); }
/*     */   
/* 357 */   public JavaType getValueType() { return this._mapType; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void _readAndBind(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Map<Object, Object> paramMap)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 369 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 370 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 371 */       localJsonToken = paramJsonParser.nextToken();
/*     */     }
/* 373 */     KeyDeserializer localKeyDeserializer = this._keyDeserializer;
/* 374 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 375 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/* 376 */     for (; localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken())
/*     */     {
/* 378 */       String str = paramJsonParser.getCurrentName();
/* 379 */       Object localObject1 = localKeyDeserializer.deserializeKey(str, paramDeserializationContext);
/*     */       
/* 381 */       localJsonToken = paramJsonParser.nextToken();
/* 382 */       if ((this._ignorableProperties != null) && (this._ignorableProperties.contains(str))) {
/* 383 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/*     */         Object localObject2;
/* 388 */         if (localJsonToken == JsonToken.VALUE_NULL) {
/* 389 */           localObject2 = null;
/* 390 */         } else if (localTypeDeserializer == null) {
/* 391 */           localObject2 = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */         } else {
/* 393 */           localObject2 = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 399 */         paramMap.put(localObject1, localObject2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void _readAndBindStringMap(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Map<Object, Object> paramMap)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 412 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 413 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 414 */       localJsonToken = paramJsonParser.nextToken();
/*     */     }
/* 416 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 417 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/* 418 */     for (; localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken())
/*     */     {
/* 420 */       String str = paramJsonParser.getCurrentName();
/*     */       
/* 422 */       localJsonToken = paramJsonParser.nextToken();
/* 423 */       if ((this._ignorableProperties != null) && (this._ignorableProperties.contains(str))) {
/* 424 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/*     */         Object localObject;
/* 429 */         if (localJsonToken == JsonToken.VALUE_NULL) {
/* 430 */           localObject = null;
/* 431 */         } else if (localTypeDeserializer == null) {
/* 432 */           localObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */         } else {
/* 434 */           localObject = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */         }
/* 436 */         paramMap.put(str, localObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Map<Object, Object> _deserializeUsingCreator(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 444 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/*     */     
/* 446 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, null);
/*     */     
/* 448 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 449 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 450 */       localJsonToken = paramJsonParser.nextToken();
/*     */     }
/* 452 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 453 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/* 454 */     for (; localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 455 */       String str = paramJsonParser.getCurrentName();
/* 456 */       localJsonToken = paramJsonParser.nextToken();
/* 457 */       if ((this._ignorableProperties != null) && (this._ignorableProperties.contains(str))) {
/* 458 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/* 462 */         SettableBeanProperty localSettableBeanProperty = localPropertyBasedCreator.findCreatorProperty(str);
/* 463 */         Object localObject1; Object localObject2; if (localSettableBeanProperty != null)
/*     */         {
/* 465 */           localObject1 = localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext);
/* 466 */           if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty.getCreatorIndex(), localObject1)) {
/* 467 */             paramJsonParser.nextToken();
/*     */             try
/*     */             {
/* 470 */               localObject2 = (Map)localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */             } catch (Exception localException2) {
/* 472 */               wrapAndThrow(localException2, this._mapType.getRawClass());
/* 473 */               return null;
/*     */             }
/* 475 */             _readAndBind(paramJsonParser, paramDeserializationContext, (Map)localObject2);
/* 476 */             return (Map<Object, Object>)localObject2;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 481 */           localObject1 = paramJsonParser.getCurrentName();
/* 482 */           localObject2 = this._keyDeserializer.deserializeKey((String)localObject1, paramDeserializationContext);
/*     */           Object localObject3;
/* 484 */           if (localJsonToken == JsonToken.VALUE_NULL) {
/* 485 */             localObject3 = null;
/* 486 */           } else if (localTypeDeserializer == null) {
/* 487 */             localObject3 = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */           } else {
/* 489 */             localObject3 = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */           }
/* 491 */           localPropertyValueBuffer.bufferMapProperty(localObject2, localObject3);
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/* 496 */       return (Map)localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */     } catch (Exception localException1) {
/* 498 */       wrapAndThrow(localException1, this._mapType.getRawClass()); }
/* 499 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void wrapAndThrow(Throwable paramThrowable, Object paramObject)
/*     */     throws IOException
/*     */   {
/* 508 */     while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
/* 509 */       paramThrowable = paramThrowable.getCause();
/*     */     }
/*     */     
/* 512 */     if ((paramThrowable instanceof Error)) {
/* 513 */       throw ((Error)paramThrowable);
/*     */     }
/*     */     
/* 516 */     if (((paramThrowable instanceof IOException)) && (!(paramThrowable instanceof JsonMappingException))) {
/* 517 */       throw ((IOException)paramThrowable);
/*     */     }
/* 519 */     throw JsonMappingException.wrapWithPath(paramThrowable, paramObject, null);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/MapDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */