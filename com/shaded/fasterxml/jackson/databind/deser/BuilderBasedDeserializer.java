/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.BeanAsArrayBuilderDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.TokenBuffer;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class BuilderBasedDeserializer
/*     */   extends BeanDeserializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedMethod _buildMethod;
/*     */   
/*     */   public BuilderBasedDeserializer(BeanDeserializerBuilder paramBeanDeserializerBuilder, BeanDescription paramBeanDescription, BeanPropertyMap paramBeanPropertyMap, Map<String, SettableBeanProperty> paramMap, HashSet<String> paramHashSet, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  45 */     super(paramBeanDeserializerBuilder, paramBeanDescription, paramBeanPropertyMap, paramMap, paramHashSet, paramBoolean1, paramBoolean2);
/*     */     
/*  47 */     this._buildMethod = paramBeanDeserializerBuilder.getBuildMethod();
/*     */     
/*  49 */     if (this._objectIdReader != null) {
/*  50 */       throw new IllegalArgumentException("Can not use Object Id with Builder-based deserialization (type " + paramBeanDescription.getType() + ")");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BuilderBasedDeserializer(BuilderBasedDeserializer paramBuilderBasedDeserializer)
/*     */   {
/*  61 */     this(paramBuilderBasedDeserializer, paramBuilderBasedDeserializer._ignoreAllUnknown);
/*     */   }
/*     */   
/*     */   protected BuilderBasedDeserializer(BuilderBasedDeserializer paramBuilderBasedDeserializer, boolean paramBoolean)
/*     */   {
/*  66 */     super(paramBuilderBasedDeserializer, paramBoolean);
/*  67 */     this._buildMethod = paramBuilderBasedDeserializer._buildMethod;
/*     */   }
/*     */   
/*     */   protected BuilderBasedDeserializer(BuilderBasedDeserializer paramBuilderBasedDeserializer, NameTransformer paramNameTransformer) {
/*  71 */     super(paramBuilderBasedDeserializer, paramNameTransformer);
/*  72 */     this._buildMethod = paramBuilderBasedDeserializer._buildMethod;
/*     */   }
/*     */   
/*     */   public BuilderBasedDeserializer(BuilderBasedDeserializer paramBuilderBasedDeserializer, ObjectIdReader paramObjectIdReader) {
/*  76 */     super(paramBuilderBasedDeserializer, paramObjectIdReader);
/*  77 */     this._buildMethod = paramBuilderBasedDeserializer._buildMethod;
/*     */   }
/*     */   
/*     */   public BuilderBasedDeserializer(BuilderBasedDeserializer paramBuilderBasedDeserializer, HashSet<String> paramHashSet) {
/*  81 */     super(paramBuilderBasedDeserializer, paramHashSet);
/*  82 */     this._buildMethod = paramBuilderBasedDeserializer._buildMethod;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer)
/*     */   {
/*  92 */     return new BuilderBasedDeserializer(this, paramNameTransformer);
/*     */   }
/*     */   
/*     */   public BuilderBasedDeserializer withObjectIdReader(ObjectIdReader paramObjectIdReader)
/*     */   {
/*  97 */     return new BuilderBasedDeserializer(this, paramObjectIdReader);
/*     */   }
/*     */   
/*     */   public BuilderBasedDeserializer withIgnorableProperties(HashSet<String> paramHashSet)
/*     */   {
/* 102 */     return new BuilderBasedDeserializer(this, paramHashSet);
/*     */   }
/*     */   
/*     */   protected BeanAsArrayBuilderDeserializer asArrayDeserializer()
/*     */   {
/* 107 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._beanProperties.getPropertiesInInsertionOrder();
/* 108 */     return new BeanAsArrayBuilderDeserializer(this, arrayOfSettableBeanProperty, this._buildMethod);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Object finishBuild(DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 121 */       return this._buildMethod.getMember().invoke(paramObject, new Object[0]);
/*     */     } catch (Exception localException) {
/* 123 */       wrapInstantiationProblem(localException, paramDeserializationContext); }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 135 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/*     */ 
/* 138 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 139 */       localJsonToken = paramJsonParser.nextToken();
/* 140 */       if (this._vanillaProcessing) {
/* 141 */         return finishBuild(paramDeserializationContext, vanillaDeserialize(paramJsonParser, paramDeserializationContext, localJsonToken));
/*     */       }
/* 143 */       Object localObject = deserializeFromObject(paramJsonParser, paramDeserializationContext);
/* 144 */       return finishBuild(paramDeserializationContext, localObject);
/*     */     }
/*     */     
/* 147 */     switch (localJsonToken) {
/*     */     case VALUE_STRING: 
/* 149 */       return finishBuild(paramDeserializationContext, deserializeFromString(paramJsonParser, paramDeserializationContext));
/*     */     case VALUE_NUMBER_INT: 
/* 151 */       return finishBuild(paramDeserializationContext, deserializeFromNumber(paramJsonParser, paramDeserializationContext));
/*     */     case VALUE_NUMBER_FLOAT: 
/* 153 */       return finishBuild(paramDeserializationContext, deserializeFromDouble(paramJsonParser, paramDeserializationContext));
/*     */     case VALUE_EMBEDDED_OBJECT: 
/* 155 */       return paramJsonParser.getEmbeddedObject();
/*     */     case VALUE_TRUE: 
/*     */     case VALUE_FALSE: 
/* 158 */       return finishBuild(paramDeserializationContext, deserializeFromBoolean(paramJsonParser, paramDeserializationContext));
/*     */     
/*     */     case START_ARRAY: 
/* 161 */       return finishBuild(paramDeserializationContext, deserializeFromArray(paramJsonParser, paramDeserializationContext));
/*     */     case FIELD_NAME: 
/*     */     case END_OBJECT: 
/* 164 */       return finishBuild(paramDeserializationContext, deserializeFromObject(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 166 */     throw paramDeserializationContext.mappingException(getBeanClass());
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
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 183 */     return finishBuild(paramDeserializationContext, _deserialize(paramJsonParser, paramDeserializationContext, paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Object _deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 196 */     if (this._injectables != null) {
/* 197 */       injectValues(paramDeserializationContext, paramObject);
/*     */     }
/* 199 */     if (this._unwrappedPropertyHandler != null) {
/* 200 */       return deserializeWithUnwrapped(paramJsonParser, paramDeserializationContext, paramObject);
/*     */     }
/* 202 */     if (this._externalTypeIdHandler != null) {
/* 203 */       return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext, paramObject);
/*     */     }
/* 205 */     if (this._needViewProcesing) {
/* 206 */       localObject = paramDeserializationContext.getActiveView();
/* 207 */       if (localObject != null) {
/* 208 */         return deserializeWithView(paramJsonParser, paramDeserializationContext, paramObject, (Class)localObject);
/*     */       }
/*     */     }
/* 211 */     Object localObject = paramJsonParser.getCurrentToken();
/*     */     
/* 213 */     if (localObject == JsonToken.START_OBJECT) {}
/* 214 */     for (localObject = paramJsonParser.nextToken(); 
/*     */         
/* 216 */         localObject == JsonToken.FIELD_NAME; localObject = paramJsonParser.nextToken()) {
/* 217 */       String str = paramJsonParser.getCurrentName();
/*     */       
/* 219 */       paramJsonParser.nextToken();
/* 220 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/*     */       
/* 222 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 224 */           paramObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, paramObject);
/*     */         } catch (Exception localException) {
/* 226 */           wrapAndThrow(localException, paramObject, str, paramDeserializationContext);
/*     */         }
/*     */         
/*     */       }
/* 230 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 231 */         paramJsonParser.skipChildren();
/* 232 */       } else if (this._anySetter != null)
/*     */       {
/* 234 */         this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */       else
/*     */       {
/* 238 */         handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */     }
/* 241 */     return paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Object vanillaDeserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonToken paramJsonToken)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 252 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 253 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 254 */       String str = paramJsonParser.getCurrentName();
/*     */       
/* 256 */       paramJsonParser.nextToken();
/* 257 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 258 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 260 */           localObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, localObject);
/*     */         } catch (Exception localException) {
/* 262 */           wrapAndThrow(localException, localObject, str, paramDeserializationContext);
/*     */         }
/*     */       } else {
/* 265 */         handleUnknownVanilla(paramJsonParser, paramDeserializationContext, localObject, str);
/*     */       }
/*     */     }
/* 268 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 279 */     if (this._nonStandardCreation) {
/* 280 */       if (this._unwrappedPropertyHandler != null) {
/* 281 */         return deserializeWithUnwrapped(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 283 */       if (this._externalTypeIdHandler != null) {
/* 284 */         return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 286 */       return deserializeFromObjectUsingNonDefault(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 288 */     Object localObject1 = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 289 */     if (this._injectables != null)
/* 290 */       injectValues(paramDeserializationContext, localObject1);
/*     */     Object localObject2;
/* 292 */     if (this._needViewProcesing) {
/* 293 */       localObject2 = paramDeserializationContext.getActiveView();
/* 294 */       if (localObject2 != null) {
/* 295 */         return deserializeWithView(paramJsonParser, paramDeserializationContext, localObject1, (Class)localObject2);
/*     */       }
/*     */     }
/* 298 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 299 */       localObject2 = paramJsonParser.getCurrentName();
/*     */       
/* 301 */       paramJsonParser.nextToken();
/* 302 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find((String)localObject2);
/* 303 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 305 */           localObject1 = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, localObject1);
/*     */         } catch (Exception localException1) {
/* 307 */           wrapAndThrow(localException1, localObject1, (String)localObject2, paramDeserializationContext);
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 314 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject2))) {
/* 315 */         paramJsonParser.skipChildren();
/* 316 */       } else if (this._anySetter != null) {
/*     */         try {
/* 318 */           this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject1, (String)localObject2);
/*     */         } catch (Exception localException2) {
/* 320 */           wrapAndThrow(localException2, localObject1, (String)localObject2, paramDeserializationContext);
/*     */         }
/*     */         
/*     */       }
/*     */       else {
/* 325 */         handleUnknownProperty(paramJsonParser, paramDeserializationContext, localObject1, (String)localObject2);
/*     */       }
/*     */     }
/* 328 */     return localObject1;
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
/*     */   protected final Object _deserializeUsingPropertyBased(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 344 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/* 345 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
/*     */     
/*     */ 
/* 348 */     TokenBuffer localTokenBuffer = null;
/*     */     Object localObject1;
/* 350 */     for (JsonToken localJsonToken = paramJsonParser.getCurrentToken(); 
/* 351 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 352 */       localObject1 = paramJsonParser.getCurrentName();
/* 353 */       paramJsonParser.nextToken();
/*     */       
/* 355 */       SettableBeanProperty localSettableBeanProperty = localPropertyBasedCreator.findCreatorProperty((String)localObject1);
/* 356 */       Object localObject2; if (localSettableBeanProperty != null)
/*     */       {
/* 358 */         localObject2 = localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext);
/* 359 */         if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty.getCreatorIndex(), localObject2)) {
/* 360 */           paramJsonParser.nextToken();
/*     */           Object localObject3;
/*     */           try {
/* 363 */             localObject3 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */           } catch (Exception localException2) {
/* 365 */             wrapAndThrow(localException2, this._beanType.getRawClass(), (String)localObject1, paramDeserializationContext);
/* 366 */             continue;
/*     */           }
/*     */           
/* 369 */           if (localObject3.getClass() != this._beanType.getRawClass()) {
/* 370 */             return handlePolymorphic(paramJsonParser, paramDeserializationContext, localObject3, localTokenBuffer);
/*     */           }
/* 372 */           if (localTokenBuffer != null) {
/* 373 */             localObject3 = handleUnknownProperties(paramDeserializationContext, localObject3, localTokenBuffer);
/*     */           }
/*     */           
/* 376 */           return _deserialize(paramJsonParser, paramDeserializationContext, localObject3);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 381 */       else if (!localPropertyValueBuffer.readIdProperty((String)localObject1))
/*     */       {
/*     */ 
/*     */ 
/* 385 */         localObject2 = this._beanProperties.find((String)localObject1);
/* 386 */         if (localObject2 != null) {
/* 387 */           localPropertyValueBuffer.bufferProperty((SettableBeanProperty)localObject2, ((SettableBeanProperty)localObject2).deserialize(paramJsonParser, paramDeserializationContext));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 392 */         else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject1))) {
/* 393 */           paramJsonParser.skipChildren();
/*     */ 
/*     */ 
/*     */         }
/* 397 */         else if (this._anySetter != null) {
/* 398 */           localPropertyValueBuffer.bufferAnyProperty(this._anySetter, (String)localObject1, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
/*     */         }
/*     */         else
/*     */         {
/* 402 */           if (localTokenBuffer == null) {
/* 403 */             localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/*     */           }
/* 405 */           localTokenBuffer.writeFieldName((String)localObject1);
/* 406 */           localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */         }
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 412 */       localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */     } catch (Exception localException1) {
/* 414 */       wrapInstantiationProblem(localException1, paramDeserializationContext);
/* 415 */       return null;
/*     */     }
/* 417 */     if (localTokenBuffer != null)
/*     */     {
/* 419 */       if (localObject1.getClass() != this._beanType.getRawClass()) {
/* 420 */         return handlePolymorphic(null, paramDeserializationContext, localObject1, localTokenBuffer);
/*     */       }
/*     */       
/* 423 */       return handleUnknownProperties(paramDeserializationContext, localObject1, localTokenBuffer);
/*     */     }
/* 425 */     return localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Object deserializeWithView(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 438 */     for (JsonToken localJsonToken = paramJsonParser.getCurrentToken(); 
/* 439 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 440 */       String str = paramJsonParser.getCurrentName();
/*     */       
/* 442 */       paramJsonParser.nextToken();
/* 443 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 444 */       if (localSettableBeanProperty != null) {
/* 445 */         if (!localSettableBeanProperty.visibleInView(paramClass)) {
/* 446 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 450 */             paramObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, paramObject);
/*     */           } catch (Exception localException) {
/* 452 */             wrapAndThrow(localException, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 459 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 460 */         paramJsonParser.skipChildren();
/* 461 */       } else if (this._anySetter != null) {
/* 462 */         this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */       else
/*     */       {
/* 466 */         handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */     }
/* 469 */     return paramObject;
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
/*     */   protected Object deserializeWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 485 */     if (this._delegateDeserializer != null) {
/* 486 */       return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 488 */     if (this._propertyBasedCreator != null) {
/* 489 */       return deserializeUsingPropertyBasedWithUnwrapped(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 491 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 492 */     localTokenBuffer.writeStartObject();
/* 493 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/*     */     
/* 495 */     if (this._injectables != null) {
/* 496 */       injectValues(paramDeserializationContext, localObject);
/*     */     }
/*     */     
/* 499 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 501 */     for (; 
/* 501 */         paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 502 */       String str = paramJsonParser.getCurrentName();
/* 503 */       paramJsonParser.nextToken();
/* 504 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 505 */       if (localSettableBeanProperty != null) {
/* 506 */         if ((localClass != null) && (!localSettableBeanProperty.visibleInView(localClass))) {
/* 507 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 511 */             localObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, localObject);
/*     */           } catch (Exception localException1) {
/* 513 */             wrapAndThrow(localException1, localObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 518 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 519 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/* 523 */         localTokenBuffer.writeFieldName(str);
/* 524 */         localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */         
/* 526 */         if (this._anySetter != null) {
/*     */           try {
/* 528 */             this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject, str);
/*     */           } catch (Exception localException2) {
/* 530 */             wrapAndThrow(localException2, localObject, str, paramDeserializationContext);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 535 */     localTokenBuffer.writeEndObject();
/* 536 */     this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject, localTokenBuffer);
/* 537 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Object deserializeWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 544 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 545 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 546 */       localJsonToken = paramJsonParser.nextToken();
/*     */     }
/* 548 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 549 */     localTokenBuffer.writeStartObject();
/* 550 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 551 */     for (; localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 552 */       String str = paramJsonParser.getCurrentName();
/* 553 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 554 */       paramJsonParser.nextToken();
/* 555 */       if (localSettableBeanProperty != null) {
/* 556 */         if ((localClass != null) && (!localSettableBeanProperty.visibleInView(localClass))) {
/* 557 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 561 */             paramObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, paramObject);
/*     */           } catch (Exception localException) {
/* 563 */             wrapAndThrow(localException, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */         }
/*     */       }
/* 567 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 568 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/* 572 */         localTokenBuffer.writeFieldName(str);
/* 573 */         localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */         
/* 575 */         if (this._anySetter != null)
/* 576 */           this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */     }
/* 579 */     localTokenBuffer.writeEndObject();
/* 580 */     this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, paramObject, localTokenBuffer);
/* 581 */     return paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 589 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/* 590 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
/*     */     
/* 592 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 593 */     localTokenBuffer.writeStartObject();
/*     */     Object localObject1;
/* 595 */     for (JsonToken localJsonToken = paramJsonParser.getCurrentToken(); 
/* 596 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 597 */       localObject1 = paramJsonParser.getCurrentName();
/* 598 */       paramJsonParser.nextToken();
/*     */       
/* 600 */       SettableBeanProperty localSettableBeanProperty = localPropertyBasedCreator.findCreatorProperty((String)localObject1);
/* 601 */       Object localObject2; if (localSettableBeanProperty != null)
/*     */       {
/* 603 */         localObject2 = localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext);
/* 604 */         if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty.getCreatorIndex(), localObject2)) {
/* 605 */           localJsonToken = paramJsonParser.nextToken();
/*     */           Object localObject3;
/*     */           try {
/* 608 */             localObject3 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */           } catch (Exception localException2) {
/* 610 */             wrapAndThrow(localException2, this._beanType.getRawClass(), (String)localObject1, paramDeserializationContext);
/* 611 */             continue;
/*     */           }
/*     */           
/* 614 */           while (localJsonToken == JsonToken.FIELD_NAME) {
/* 615 */             paramJsonParser.nextToken();
/* 616 */             localTokenBuffer.copyCurrentStructure(paramJsonParser);
/* 617 */             localJsonToken = paramJsonParser.nextToken();
/*     */           }
/* 619 */           localTokenBuffer.writeEndObject();
/* 620 */           if (localObject3.getClass() != this._beanType.getRawClass())
/*     */           {
/*     */ 
/* 623 */             throw paramDeserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
/*     */           }
/* 625 */           return this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject3, localTokenBuffer);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 630 */       else if (!localPropertyValueBuffer.readIdProperty((String)localObject1))
/*     */       {
/*     */ 
/*     */ 
/* 634 */         localObject2 = this._beanProperties.find((String)localObject1);
/* 635 */         if (localObject2 != null) {
/* 636 */           localPropertyValueBuffer.bufferProperty((SettableBeanProperty)localObject2, ((SettableBeanProperty)localObject2).deserialize(paramJsonParser, paramDeserializationContext));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 642 */         else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject1))) {
/* 643 */           paramJsonParser.skipChildren();
/*     */         }
/*     */         else {
/* 646 */           localTokenBuffer.writeFieldName((String)localObject1);
/* 647 */           localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */           
/* 649 */           if (this._anySetter != null) {
/* 650 */             localPropertyValueBuffer.bufferAnyProperty(this._anySetter, (String)localObject1, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 658 */       localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */     } catch (Exception localException1) {
/* 660 */       wrapInstantiationProblem(localException1, paramDeserializationContext);
/* 661 */       return null;
/*     */     }
/* 663 */     return this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject1, localTokenBuffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object deserializeWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 676 */     if (this._propertyBasedCreator != null) {
/* 677 */       return deserializeUsingPropertyBasedWithExternalTypeId(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 679 */     return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext, this._valueInstantiator.createUsingDefault(paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */   protected Object deserializeWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 686 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 687 */     ExternalTypeHandler localExternalTypeHandler = this._externalTypeIdHandler.start();
/* 688 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 689 */       String str = paramJsonParser.getCurrentName();
/* 690 */       paramJsonParser.nextToken();
/* 691 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 692 */       if (localSettableBeanProperty != null) {
/* 693 */         if ((localClass != null) && (!localSettableBeanProperty.visibleInView(localClass))) {
/* 694 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 698 */             paramObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, paramObject);
/*     */           } catch (Exception localException1) {
/* 700 */             wrapAndThrow(localException1, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 705 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 706 */         paramJsonParser.skipChildren();
/*     */ 
/*     */ 
/*     */       }
/* 710 */       else if (!localExternalTypeHandler.handlePropertyValue(paramJsonParser, paramDeserializationContext, str, paramObject))
/*     */       {
/*     */ 
/*     */ 
/* 714 */         if (this._anySetter != null) {
/*     */           try {
/* 716 */             this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */           } catch (Exception localException2) {
/* 718 */             wrapAndThrow(localException2, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */         else {
/* 723 */           handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */         }
/*     */       }
/*     */     }
/* 727 */     return localExternalTypeHandler.complete(paramJsonParser, paramDeserializationContext, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 735 */     throw new IllegalStateException("Deserialization with Builder, External type id, @JsonCreator not yet implemented");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/BuilderBasedDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */