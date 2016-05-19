/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.SortedMap;
/*     */ 
/*     */ @com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl
/*     */ public class MapSerializer extends ContainerSerializer<Map<?, ?>> implements ContextualSerializer
/*     */ {
/*  32 */   protected static final JavaType UNSPECIFIED_TYPE = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final BeanProperty _property;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final HashSet<String> _ignoredEntries;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean _valueTypeIsStatic;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _keyType;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _valueType;
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
/*     */   protected JsonSerializer<Object> _valueSerializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected PropertySerializerMap _dynamicValueSerializers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MapSerializer(HashSet<String> paramHashSet, JavaType paramJavaType1, JavaType paramJavaType2, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer1, JsonSerializer<?> paramJsonSerializer2)
/*     */   {
/*  93 */     super(Map.class, false);
/*  94 */     this._ignoredEntries = paramHashSet;
/*  95 */     this._keyType = paramJavaType1;
/*  96 */     this._valueType = paramJavaType2;
/*  97 */     this._valueTypeIsStatic = paramBoolean;
/*  98 */     this._valueTypeSerializer = paramTypeSerializer;
/*  99 */     this._keySerializer = paramJsonSerializer1;
/* 100 */     this._valueSerializer = paramJsonSerializer2;
/* 101 */     this._dynamicValueSerializers = PropertySerializerMap.emptyMap();
/* 102 */     this._property = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MapSerializer(MapSerializer paramMapSerializer, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer1, JsonSerializer<?> paramJsonSerializer2, HashSet<String> paramHashSet)
/*     */   {
/* 110 */     super(Map.class, false);
/* 111 */     this._ignoredEntries = paramHashSet;
/* 112 */     this._keyType = paramMapSerializer._keyType;
/* 113 */     this._valueType = paramMapSerializer._valueType;
/* 114 */     this._valueTypeIsStatic = paramMapSerializer._valueTypeIsStatic;
/* 115 */     this._valueTypeSerializer = paramMapSerializer._valueTypeSerializer;
/* 116 */     this._keySerializer = paramJsonSerializer1;
/* 117 */     this._valueSerializer = paramJsonSerializer2;
/* 118 */     this._dynamicValueSerializers = paramMapSerializer._dynamicValueSerializers;
/* 119 */     this._property = paramBeanProperty;
/*     */   }
/*     */   
/*     */   protected MapSerializer(MapSerializer paramMapSerializer, TypeSerializer paramTypeSerializer)
/*     */   {
/* 124 */     super(Map.class, false);
/* 125 */     this._ignoredEntries = paramMapSerializer._ignoredEntries;
/* 126 */     this._keyType = paramMapSerializer._keyType;
/* 127 */     this._valueType = paramMapSerializer._valueType;
/* 128 */     this._valueTypeIsStatic = paramMapSerializer._valueTypeIsStatic;
/* 129 */     this._valueTypeSerializer = paramTypeSerializer;
/* 130 */     this._keySerializer = paramMapSerializer._keySerializer;
/* 131 */     this._valueSerializer = paramMapSerializer._valueSerializer;
/* 132 */     this._dynamicValueSerializers = paramMapSerializer._dynamicValueSerializers;
/* 133 */     this._property = paramMapSerializer._property;
/*     */   }
/*     */   
/*     */ 
/*     */   public MapSerializer _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */   {
/* 139 */     return new MapSerializer(this, paramTypeSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MapSerializer withResolved(BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer1, JsonSerializer<?> paramJsonSerializer2, HashSet<String> paramHashSet)
/*     */   {
/* 146 */     return new MapSerializer(this, paramBeanProperty, paramJsonSerializer1, paramJsonSerializer2, paramHashSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static MapSerializer construct(String[] paramArrayOfString, JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer1, JsonSerializer<Object> paramJsonSerializer2)
/*     */   {
/* 153 */     HashSet localHashSet = toSet(paramArrayOfString);
/*     */     JavaType localJavaType1;
/*     */     JavaType localJavaType2;
/* 156 */     if (paramJavaType == null) {
/* 157 */       localJavaType2 = localJavaType1 = UNSPECIFIED_TYPE;
/*     */     } else {
/* 159 */       localJavaType2 = paramJavaType.getKeyType();
/* 160 */       localJavaType1 = paramJavaType.getContentType();
/*     */     }
/*     */     
/* 163 */     if (!paramBoolean) {
/* 164 */       paramBoolean = (localJavaType1 != null) && (localJavaType1.isFinal());
/*     */     }
/* 166 */     return new MapSerializer(localHashSet, localJavaType2, localJavaType1, paramBoolean, paramTypeSerializer, paramJsonSerializer1, paramJsonSerializer2);
/*     */   }
/*     */   
/*     */   private static HashSet<String> toSet(String[] paramArrayOfString)
/*     */   {
/* 171 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 172 */       return null;
/*     */     }
/* 174 */     HashSet localHashSet = new HashSet(paramArrayOfString.length);
/* 175 */     for (String str : paramArrayOfString) {
/* 176 */       localHashSet.add(str);
/*     */     }
/* 178 */     return localHashSet;
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
/*     */   public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 196 */     JsonSerializer localJsonSerializer1 = null;
/* 197 */     JsonSerializer localJsonSerializer2 = null;
/*     */     
/*     */     Object localObject2;
/* 200 */     if (paramBeanProperty != null) {
/* 201 */       localObject1 = paramBeanProperty.getMember();
/* 202 */       if (localObject1 != null)
/*     */       {
/* 204 */         localObject2 = paramSerializerProvider.getAnnotationIntrospector();
/* 205 */         localObject3 = ((AnnotationIntrospector)localObject2).findKeySerializer((Annotated)localObject1);
/* 206 */         if (localObject3 != null) {
/* 207 */           localJsonSerializer2 = paramSerializerProvider.serializerInstance((Annotated)localObject1, localObject3);
/*     */         }
/* 209 */         localObject3 = ((AnnotationIntrospector)localObject2).findContentSerializer((Annotated)localObject1);
/* 210 */         if (localObject3 != null) {
/* 211 */           localJsonSerializer1 = paramSerializerProvider.serializerInstance((Annotated)localObject1, localObject3);
/*     */         }
/*     */       }
/*     */     }
/* 215 */     if (localJsonSerializer1 == null) {
/* 216 */       localJsonSerializer1 = this._valueSerializer;
/*     */     }
/*     */     
/* 219 */     localJsonSerializer1 = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, localJsonSerializer1);
/* 220 */     if (localJsonSerializer1 == null)
/*     */     {
/*     */ 
/* 223 */       if ((this._valueTypeIsStatic) || (hasContentTypeAnnotation(paramSerializerProvider, paramBeanProperty))) {
/* 224 */         localJsonSerializer1 = paramSerializerProvider.findValueSerializer(this._valueType, paramBeanProperty);
/*     */       }
/* 226 */     } else if ((localJsonSerializer1 instanceof ContextualSerializer)) {
/* 227 */       localJsonSerializer1 = ((ContextualSerializer)localJsonSerializer1).createContextual(paramSerializerProvider, paramBeanProperty);
/*     */     }
/* 229 */     if (localJsonSerializer2 == null) {
/* 230 */       localJsonSerializer2 = this._keySerializer;
/*     */     }
/* 232 */     if (localJsonSerializer2 == null) {
/* 233 */       localJsonSerializer2 = paramSerializerProvider.findKeySerializer(this._keyType, paramBeanProperty);
/* 234 */     } else if ((localJsonSerializer2 instanceof ContextualSerializer)) {
/* 235 */       localJsonSerializer2 = ((ContextualSerializer)localJsonSerializer2).createContextual(paramSerializerProvider, paramBeanProperty);
/*     */     }
/* 237 */     Object localObject1 = this._ignoredEntries;
/* 238 */     Object localObject3 = paramSerializerProvider.getAnnotationIntrospector();
/* 239 */     if ((localObject3 != null) && (paramBeanProperty != null)) {
/* 240 */       localObject2 = ((AnnotationIntrospector)localObject3).findPropertiesToIgnore(paramBeanProperty.getMember());
/* 241 */       if (localObject2 != null) {
/* 242 */         localObject1 = localObject1 == null ? new HashSet() : new HashSet((java.util.Collection)localObject1);
/* 243 */         for (Object localObject5 : localObject2) {
/* 244 */           ((HashSet)localObject1).add(localObject5);
/*     */         }
/*     */       }
/*     */     }
/* 248 */     return withResolved(paramBeanProperty, localJsonSerializer2, localJsonSerializer1, (HashSet)localObject1);
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
/* 259 */     return this._valueType;
/*     */   }
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer()
/*     */   {
/* 264 */     return this._valueSerializer;
/*     */   }
/*     */   
/*     */   public boolean isEmpty(Map<?, ?> paramMap)
/*     */   {
/* 269 */     return (paramMap == null) || (paramMap.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasSingleElement(Map<?, ?> paramMap)
/*     */   {
/* 274 */     return paramMap.size() == 1;
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
/*     */   public JsonSerializer<?> getKeySerializer()
/*     */   {
/* 294 */     return this._keySerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serialize(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 307 */     paramJsonGenerator.writeStartObject();
/* 308 */     if (!paramMap.isEmpty()) {
/* 309 */       if (paramSerializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
/* 310 */         paramMap = _orderEntries(paramMap);
/*     */       }
/* 312 */       if (this._valueSerializer != null) {
/* 313 */         serializeFieldsUsing(paramMap, paramJsonGenerator, paramSerializerProvider, this._valueSerializer);
/*     */       } else {
/* 315 */         serializeFields(paramMap, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */     }
/* 318 */     paramJsonGenerator.writeEndObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void serializeWithType(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 326 */     paramTypeSerializer.writeTypePrefixForObject(paramMap, paramJsonGenerator);
/* 327 */     if (!paramMap.isEmpty()) {
/* 328 */       if (paramSerializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
/* 329 */         paramMap = _orderEntries(paramMap);
/*     */       }
/* 331 */       if (this._valueSerializer != null) {
/* 332 */         serializeFieldsUsing(paramMap, paramJsonGenerator, paramSerializerProvider, this._valueSerializer);
/*     */       } else {
/* 334 */         serializeFields(paramMap, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */     }
/* 337 */     paramTypeSerializer.writeTypeSuffixForObject(paramMap, paramJsonGenerator);
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
/*     */   public void serializeFields(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 353 */     if (this._valueTypeSerializer != null) {
/* 354 */       serializeTypedFields(paramMap, paramJsonGenerator, paramSerializerProvider);
/* 355 */       return;
/*     */     }
/* 357 */     JsonSerializer localJsonSerializer1 = this._keySerializer;
/*     */     
/* 359 */     HashSet localHashSet = this._ignoredEntries;
/* 360 */     int i = !paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) ? 1 : 0;
/*     */     
/* 362 */     PropertySerializerMap localPropertySerializerMap = this._dynamicValueSerializers;
/*     */     
/* 364 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 365 */       Object localObject1 = localEntry.getValue();
/*     */       
/* 367 */       Object localObject2 = localEntry.getKey();
/* 368 */       if (localObject2 == null) {
/* 369 */         paramSerializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */       else {
/* 372 */         if (((i != 0) && (localObject1 == null)) || (
/*     */         
/* 374 */           (localHashSet != null) && (localHashSet.contains(localObject2)))) continue;
/* 375 */         localJsonSerializer1.serialize(localObject2, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */       
/*     */ 
/* 379 */       if (localObject1 == null) {
/* 380 */         paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */       } else {
/* 382 */         Class localClass = localObject1.getClass();
/* 383 */         JsonSerializer localJsonSerializer2 = localPropertySerializerMap.serializerFor(localClass);
/* 384 */         if (localJsonSerializer2 == null) {
/* 385 */           if (this._valueType.hasGenericTypes()) {
/* 386 */             localJsonSerializer2 = _findAndAddDynamic(localPropertySerializerMap, paramSerializerProvider.constructSpecializedType(this._valueType, localClass), paramSerializerProvider);
/*     */           }
/*     */           else {
/* 389 */             localJsonSerializer2 = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
/*     */           }
/* 391 */           localPropertySerializerMap = this._dynamicValueSerializers;
/*     */         }
/*     */         try {
/* 394 */           localJsonSerializer2.serialize(localObject1, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */         catch (Exception localException) {
/* 397 */           String str = "" + localObject2;
/* 398 */           wrapAndThrow(paramSerializerProvider, localException, paramMap, str);
/*     */         }
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
/*     */   protected void serializeFieldsUsing(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 413 */     JsonSerializer localJsonSerializer = this._keySerializer;
/* 414 */     HashSet localHashSet = this._ignoredEntries;
/* 415 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 416 */     int i = !paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) ? 1 : 0;
/*     */     
/* 418 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 419 */       Object localObject1 = localEntry.getValue();
/* 420 */       Object localObject2 = localEntry.getKey();
/* 421 */       if (localObject2 == null) {
/* 422 */         paramSerializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */       else {
/* 425 */         if (((i != 0) && (localObject1 == null)) || (
/* 426 */           (localHashSet != null) && (localHashSet.contains(localObject2)))) continue;
/* 427 */         localJsonSerializer.serialize(localObject2, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/* 429 */       if (localObject1 == null) {
/* 430 */         paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */       } else {
/*     */         try {
/* 433 */           if (localTypeSerializer == null) {
/* 434 */             paramJsonSerializer.serialize(localObject1, paramJsonGenerator, paramSerializerProvider);
/*     */           } else {
/* 436 */             paramJsonSerializer.serializeWithType(localObject1, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */           }
/*     */         }
/*     */         catch (Exception localException) {
/* 440 */           String str = "" + localObject2;
/* 441 */           wrapAndThrow(paramSerializerProvider, localException, paramMap, str);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void serializeTypedFields(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 450 */     JsonSerializer localJsonSerializer = this._keySerializer;
/* 451 */     Object localObject1 = null;
/* 452 */     Object localObject2 = null;
/* 453 */     HashSet localHashSet = this._ignoredEntries;
/* 454 */     int i = !paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) ? 1 : 0;
/*     */     
/* 456 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 457 */       Object localObject3 = localEntry.getValue();
/*     */       
/* 459 */       Object localObject4 = localEntry.getKey();
/* 460 */       if (localObject4 == null) {
/* 461 */         paramSerializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */       else {
/* 464 */         if (((i != 0) && (localObject3 == null)) || (
/*     */         
/* 466 */           (localHashSet != null) && (localHashSet.contains(localObject4)))) continue;
/* 467 */         localJsonSerializer.serialize(localObject4, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */       
/*     */ 
/* 471 */       if (localObject3 == null) {
/* 472 */         paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */       } else {
/* 474 */         Class localClass = localObject3.getClass();
/*     */         Object localObject5;
/* 476 */         if (localClass == localObject2) {
/* 477 */           localObject5 = localObject1;
/*     */         } else {
/* 479 */           localObject5 = paramSerializerProvider.findValueSerializer(localClass, this._property);
/* 480 */           localObject1 = localObject5;
/* 481 */           localObject2 = localClass;
/*     */         }
/*     */         try {
/* 484 */           ((JsonSerializer)localObject5).serializeWithType(localObject3, paramJsonGenerator, paramSerializerProvider, this._valueTypeSerializer);
/*     */         }
/*     */         catch (Exception localException) {
/* 487 */           String str = "" + localObject4;
/* 488 */           wrapAndThrow(paramSerializerProvider, localException, paramMap, str);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public com.shaded.fasterxml.jackson.databind.JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */   {
/* 497 */     ObjectNode localObjectNode = createSchemaNode("object", true);
/*     */     
/*     */ 
/* 500 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 507 */     JsonMapFormatVisitor localJsonMapFormatVisitor = paramJsonFormatVisitorWrapper == null ? null : paramJsonFormatVisitorWrapper.expectMapFormat(paramJavaType);
/* 508 */     if (localJsonMapFormatVisitor != null) {
/* 509 */       localJsonMapFormatVisitor.keyFormat(this._keySerializer, this._keyType);
/* 510 */       JsonSerializer localJsonSerializer = this._valueSerializer;
/* 511 */       if (localJsonSerializer == null) {
/* 512 */         localJsonSerializer = _findAndAddDynamic(this._dynamicValueSerializers, this._valueType, paramJsonFormatVisitorWrapper.getProvider());
/*     */       }
/*     */       
/* 515 */       localJsonMapFormatVisitor.valueFormat(localJsonSerializer, this._valueType);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 528 */     PropertySerializerMap.SerializerAndMapResult localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(paramClass, paramSerializerProvider, this._property);
/*     */     
/* 530 */     if (paramPropertySerializerMap != localSerializerAndMapResult.map) {
/* 531 */       this._dynamicValueSerializers = localSerializerAndMapResult.map;
/*     */     }
/* 533 */     return localSerializerAndMapResult.serializer;
/*     */   }
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, JavaType paramJavaType, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 539 */     PropertySerializerMap.SerializerAndMapResult localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(paramJavaType, paramSerializerProvider, this._property);
/* 540 */     if (paramPropertySerializerMap != localSerializerAndMapResult.map) {
/* 541 */       this._dynamicValueSerializers = localSerializerAndMapResult.map;
/*     */     }
/* 543 */     return localSerializerAndMapResult.serializer;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Map<?, ?> _orderEntries(Map<?, ?> paramMap)
/*     */   {
/* 549 */     if ((paramMap instanceof SortedMap)) {
/* 550 */       return paramMap;
/*     */     }
/* 552 */     return new java.util.TreeMap(paramMap);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/MapSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */