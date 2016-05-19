/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.BeanAsArrayDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.TokenBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanDeserializer
/*     */   extends BeanDeserializerBase
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public BeanDeserializer(BeanDeserializerBuilder paramBeanDeserializerBuilder, BeanDescription paramBeanDescription, BeanPropertyMap paramBeanPropertyMap, Map<String, SettableBeanProperty> paramMap, HashSet<String> paramHashSet, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  38 */     super(paramBeanDeserializerBuilder, paramBeanDescription, paramBeanPropertyMap, paramMap, paramHashSet, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase)
/*     */   {
/*  47 */     super(paramBeanDeserializerBase, paramBeanDeserializerBase._ignoreAllUnknown);
/*     */   }
/*     */   
/*     */   protected BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, boolean paramBoolean) {
/*  51 */     super(paramBeanDeserializerBase, paramBoolean);
/*     */   }
/*     */   
/*     */   protected BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, NameTransformer paramNameTransformer) {
/*  55 */     super(paramBeanDeserializerBase, paramNameTransformer);
/*     */   }
/*     */   
/*     */   public BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, ObjectIdReader paramObjectIdReader) {
/*  59 */     super(paramBeanDeserializerBase, paramObjectIdReader);
/*     */   }
/*     */   
/*     */   public BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, HashSet<String> paramHashSet) {
/*  63 */     super(paramBeanDeserializerBase, paramHashSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer)
/*     */   {
/*  72 */     if (getClass() != BeanDeserializer.class) {
/*  73 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  79 */     return new BeanDeserializer(this, paramNameTransformer);
/*     */   }
/*     */   
/*     */   public BeanDeserializer withObjectIdReader(ObjectIdReader paramObjectIdReader)
/*     */   {
/*  84 */     return new BeanDeserializer(this, paramObjectIdReader);
/*     */   }
/*     */   
/*     */   public BeanDeserializer withIgnorableProperties(HashSet<String> paramHashSet)
/*     */   {
/*  89 */     return new BeanDeserializer(this, paramHashSet);
/*     */   }
/*     */   
/*     */   protected BeanDeserializerBase asArrayDeserializer()
/*     */   {
/*  94 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._beanProperties.getPropertiesInInsertionOrder();
/*  95 */     return new BeanAsArrayDeserializer(this, arrayOfSettableBeanProperty);
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
/*     */   public final Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 111 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 113 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 114 */       if (this._vanillaProcessing) {
/* 115 */         return vanillaDeserialize(paramJsonParser, paramDeserializationContext, paramJsonParser.nextToken());
/*     */       }
/* 117 */       paramJsonParser.nextToken();
/* 118 */       if (this._objectIdReader != null) {
/* 119 */         return deserializeWithObjectId(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 121 */       return deserializeFromObject(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 123 */     return _deserializeOther(paramJsonParser, paramDeserializationContext, localJsonToken);
/*     */   }
/*     */   
/*     */ 
/*     */   private final Object _deserializeOther(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonToken paramJsonToken)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 130 */     if (paramJsonToken == null) {
/* 131 */       return _missingToken(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/* 134 */     switch (paramJsonToken) {
/*     */     case VALUE_STRING: 
/* 136 */       return deserializeFromString(paramJsonParser, paramDeserializationContext);
/*     */     case VALUE_NUMBER_INT: 
/* 138 */       return deserializeFromNumber(paramJsonParser, paramDeserializationContext);
/*     */     case VALUE_NUMBER_FLOAT: 
/* 140 */       return deserializeFromDouble(paramJsonParser, paramDeserializationContext);
/*     */     case VALUE_EMBEDDED_OBJECT: 
/* 142 */       return paramJsonParser.getEmbeddedObject();
/*     */     case VALUE_TRUE: 
/*     */     case VALUE_FALSE: 
/* 145 */       return deserializeFromBoolean(paramJsonParser, paramDeserializationContext);
/*     */     
/*     */     case START_ARRAY: 
/* 148 */       return deserializeFromArray(paramJsonParser, paramDeserializationContext);
/*     */     case FIELD_NAME: 
/*     */     case END_OBJECT: 
/* 151 */       if (this._vanillaProcessing) {
/* 152 */         return vanillaDeserialize(paramJsonParser, paramDeserializationContext, paramJsonToken);
/*     */       }
/* 154 */       if (this._objectIdReader != null) {
/* 155 */         return deserializeWithObjectId(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 157 */       return deserializeFromObject(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 159 */     throw paramDeserializationContext.mappingException(getBeanClass());
/*     */   }
/*     */   
/*     */ 
/*     */   protected Object _missingToken(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws JsonProcessingException
/*     */   {
/* 166 */     throw paramDeserializationContext.endOfInputException(getBeanClass());
/*     */   }
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
/* 178 */     if (this._injectables != null) {
/* 179 */       injectValues(paramDeserializationContext, paramObject);
/*     */     }
/* 181 */     if (this._unwrappedPropertyHandler != null) {
/* 182 */       return deserializeWithUnwrapped(paramJsonParser, paramDeserializationContext, paramObject);
/*     */     }
/* 184 */     if (this._externalTypeIdHandler != null) {
/* 185 */       return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext, paramObject);
/*     */     }
/* 187 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 189 */     if (localJsonToken == JsonToken.START_OBJECT)
/* 190 */       localJsonToken = paramJsonParser.nextToken();
/*     */     Object localObject;
/* 192 */     if (this._needViewProcesing) {
/* 193 */       localObject = paramDeserializationContext.getActiveView();
/* 194 */       if (localObject != null) {
/* 195 */         return deserializeWithView(paramJsonParser, paramDeserializationContext, paramObject, (Class)localObject);
/*     */       }
/*     */     }
/* 198 */     for (; localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 199 */       localObject = paramJsonParser.getCurrentName();
/*     */       
/* 201 */       paramJsonParser.nextToken();
/* 202 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find((String)localObject);
/*     */       
/* 204 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 206 */           localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
/*     */         } catch (Exception localException) {
/* 208 */           wrapAndThrow(localException, paramObject, (String)localObject, paramDeserializationContext);
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 215 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject))) {
/* 216 */         paramJsonParser.skipChildren();
/* 217 */       } else if (this._anySetter != null) {
/* 218 */         this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject);
/*     */       }
/*     */       else
/*     */       {
/* 222 */         handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject);
/*     */       }
/*     */     }
/* 225 */     return paramObject;
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
/*     */   private final Object vanillaDeserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonToken paramJsonToken)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 242 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 243 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 244 */       String str = paramJsonParser.getCurrentName();
/*     */       
/* 246 */       paramJsonParser.nextToken();
/* 247 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 248 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 250 */           localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject);
/*     */         } catch (Exception localException) {
/* 252 */           wrapAndThrow(localException, localObject, str, paramDeserializationContext);
/*     */         }
/*     */       } else {
/* 255 */         handleUnknownVanilla(paramJsonParser, paramDeserializationContext, localObject, str);
/*     */       }
/*     */     }
/* 258 */     return localObject;
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
/* 269 */     if (this._nonStandardCreation) {
/* 270 */       if (this._unwrappedPropertyHandler != null) {
/* 271 */         return deserializeWithUnwrapped(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 273 */       if (this._externalTypeIdHandler != null) {
/* 274 */         return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 276 */       return deserializeFromObjectUsingNonDefault(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 278 */     Object localObject1 = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 279 */     if (this._injectables != null)
/* 280 */       injectValues(paramDeserializationContext, localObject1);
/*     */     Object localObject2;
/* 282 */     if (this._needViewProcesing) {
/* 283 */       localObject2 = paramDeserializationContext.getActiveView();
/* 284 */       if (localObject2 != null) {
/* 285 */         return deserializeWithView(paramJsonParser, paramDeserializationContext, localObject1, (Class)localObject2);
/*     */       }
/*     */     }
/* 288 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 289 */       localObject2 = paramJsonParser.getCurrentName();
/*     */       
/* 291 */       paramJsonParser.nextToken();
/* 292 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find((String)localObject2);
/* 293 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 295 */           localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject1);
/*     */         } catch (Exception localException1) {
/* 297 */           wrapAndThrow(localException1, localObject1, (String)localObject2, paramDeserializationContext);
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 304 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject2))) {
/* 305 */         paramJsonParser.skipChildren();
/* 306 */       } else if (this._anySetter != null) {
/*     */         try {
/* 308 */           this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject1, (String)localObject2);
/*     */         } catch (Exception localException2) {
/* 310 */           wrapAndThrow(localException2, localObject1, (String)localObject2, paramDeserializationContext);
/*     */         }
/*     */         
/*     */       }
/*     */       else {
/* 315 */         handleUnknownProperty(paramJsonParser, paramDeserializationContext, localObject1, (String)localObject2);
/*     */       }
/*     */     }
/* 318 */     return localObject1;
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
/*     */   protected Object _deserializeUsingPropertyBased(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 333 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/* 334 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
/*     */     
/*     */ 
/* 337 */     TokenBuffer localTokenBuffer = null;
/*     */     Object localObject1;
/* 339 */     for (JsonToken localJsonToken = paramJsonParser.getCurrentToken(); 
/* 340 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 341 */       localObject1 = paramJsonParser.getCurrentName();
/* 342 */       paramJsonParser.nextToken();
/*     */       
/* 344 */       SettableBeanProperty localSettableBeanProperty = localPropertyBasedCreator.findCreatorProperty((String)localObject1);
/* 345 */       Object localObject2; if (localSettableBeanProperty != null)
/*     */       {
/* 347 */         localObject2 = localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext);
/* 348 */         if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty.getCreatorIndex(), localObject2)) {
/* 349 */           paramJsonParser.nextToken();
/*     */           Object localObject3;
/*     */           try {
/* 352 */             localObject3 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */           } catch (Exception localException2) {
/* 354 */             wrapAndThrow(localException2, this._beanType.getRawClass(), (String)localObject1, paramDeserializationContext);
/* 355 */             localObject3 = null;
/*     */           }
/*     */           
/* 358 */           if (localObject3.getClass() != this._beanType.getRawClass()) {
/* 359 */             return handlePolymorphic(paramJsonParser, paramDeserializationContext, localObject3, localTokenBuffer);
/*     */           }
/* 361 */           if (localTokenBuffer != null) {
/* 362 */             localObject3 = handleUnknownProperties(paramDeserializationContext, localObject3, localTokenBuffer);
/*     */           }
/*     */           
/* 365 */           return deserialize(paramJsonParser, paramDeserializationContext, localObject3);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 370 */       else if (!localPropertyValueBuffer.readIdProperty((String)localObject1))
/*     */       {
/*     */ 
/*     */ 
/* 374 */         localObject2 = this._beanProperties.find((String)localObject1);
/* 375 */         if (localObject2 != null) {
/* 376 */           localPropertyValueBuffer.bufferProperty((SettableBeanProperty)localObject2, ((SettableBeanProperty)localObject2).deserialize(paramJsonParser, paramDeserializationContext));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 381 */         else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject1))) {
/* 382 */           paramJsonParser.skipChildren();
/*     */ 
/*     */ 
/*     */         }
/* 386 */         else if (this._anySetter != null) {
/* 387 */           localPropertyValueBuffer.bufferAnyProperty(this._anySetter, (String)localObject1, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
/*     */         }
/*     */         else
/*     */         {
/* 391 */           if (localTokenBuffer == null) {
/* 392 */             localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/*     */           }
/* 394 */           localTokenBuffer.writeFieldName((String)localObject1);
/* 395 */           localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */         }
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 401 */       localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */     } catch (Exception localException1) {
/* 403 */       wrapInstantiationProblem(localException1, paramDeserializationContext);
/* 404 */       localObject1 = null;
/*     */     }
/* 406 */     if (localTokenBuffer != null)
/*     */     {
/* 408 */       if (localObject1.getClass() != this._beanType.getRawClass()) {
/* 409 */         return handlePolymorphic(null, paramDeserializationContext, localObject1, localTokenBuffer);
/*     */       }
/*     */       
/* 412 */       return handleUnknownProperties(paramDeserializationContext, localObject1, localTokenBuffer);
/*     */     }
/* 414 */     return localObject1;
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
/* 427 */     for (JsonToken localJsonToken = paramJsonParser.getCurrentToken(); 
/* 428 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 429 */       String str = paramJsonParser.getCurrentName();
/*     */       
/* 431 */       paramJsonParser.nextToken();
/* 432 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 433 */       if (localSettableBeanProperty != null) {
/* 434 */         if (!localSettableBeanProperty.visibleInView(paramClass)) {
/* 435 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 439 */             localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
/*     */           } catch (Exception localException) {
/* 441 */             wrapAndThrow(localException, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 448 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 449 */         paramJsonParser.skipChildren();
/* 450 */       } else if (this._anySetter != null) {
/* 451 */         this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */       else
/*     */       {
/* 455 */         handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */     }
/* 458 */     return paramObject;
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
/* 474 */     if (this._delegateDeserializer != null) {
/* 475 */       return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 477 */     if (this._propertyBasedCreator != null) {
/* 478 */       return deserializeUsingPropertyBasedWithUnwrapped(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 480 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 481 */     localTokenBuffer.writeStartObject();
/* 482 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/*     */     
/* 484 */     if (this._injectables != null) {
/* 485 */       injectValues(paramDeserializationContext, localObject);
/*     */     }
/* 487 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 488 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 489 */       String str = paramJsonParser.getCurrentName();
/* 490 */       paramJsonParser.nextToken();
/* 491 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 492 */       if (localSettableBeanProperty != null) {
/* 493 */         if ((localClass != null) && (!localSettableBeanProperty.visibleInView(localClass))) {
/* 494 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 498 */             localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject);
/*     */           } catch (Exception localException1) {
/* 500 */             wrapAndThrow(localException1, localObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 505 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 506 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/* 510 */         localTokenBuffer.writeFieldName(str);
/* 511 */         localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */         
/* 513 */         if (this._anySetter != null) {
/*     */           try {
/* 515 */             this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject, str);
/*     */           } catch (Exception localException2) {
/* 517 */             wrapAndThrow(localException2, localObject, str, paramDeserializationContext);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 522 */     localTokenBuffer.writeEndObject();
/* 523 */     this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject, localTokenBuffer);
/* 524 */     return localObject;
/*     */   }
/*     */   
/*     */   protected Object deserializeWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 530 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 531 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 532 */       localJsonToken = paramJsonParser.nextToken();
/*     */     }
/* 534 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 535 */     localTokenBuffer.writeStartObject();
/* 536 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 537 */     for (; localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 538 */       String str = paramJsonParser.getCurrentName();
/* 539 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 540 */       paramJsonParser.nextToken();
/* 541 */       if (localSettableBeanProperty != null) {
/* 542 */         if ((localClass != null) && (!localSettableBeanProperty.visibleInView(localClass))) {
/* 543 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 547 */             localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
/*     */           } catch (Exception localException) {
/* 549 */             wrapAndThrow(localException, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */         }
/*     */       }
/* 553 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 554 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/* 558 */         localTokenBuffer.writeFieldName(str);
/* 559 */         localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */         
/* 561 */         if (this._anySetter != null)
/* 562 */           this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */     }
/* 565 */     localTokenBuffer.writeEndObject();
/* 566 */     this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, paramObject, localTokenBuffer);
/* 567 */     return paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 574 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/* 575 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
/*     */     
/* 577 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 578 */     localTokenBuffer.writeStartObject();
/*     */     Object localObject1;
/* 580 */     for (JsonToken localJsonToken = paramJsonParser.getCurrentToken(); 
/* 581 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 582 */       localObject1 = paramJsonParser.getCurrentName();
/* 583 */       paramJsonParser.nextToken();
/*     */       
/* 585 */       SettableBeanProperty localSettableBeanProperty = localPropertyBasedCreator.findCreatorProperty((String)localObject1);
/* 586 */       Object localObject2; if (localSettableBeanProperty != null)
/*     */       {
/* 588 */         localObject2 = localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext);
/* 589 */         if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty.getCreatorIndex(), localObject2)) {
/* 590 */           localJsonToken = paramJsonParser.nextToken();
/*     */           Object localObject3;
/*     */           try {
/* 593 */             localObject3 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */           } catch (Exception localException2) {
/* 595 */             wrapAndThrow(localException2, this._beanType.getRawClass(), (String)localObject1, paramDeserializationContext);
/* 596 */             continue;
/*     */           }
/*     */           
/* 599 */           while (localJsonToken == JsonToken.FIELD_NAME) {
/* 600 */             paramJsonParser.nextToken();
/* 601 */             localTokenBuffer.copyCurrentStructure(paramJsonParser);
/* 602 */             localJsonToken = paramJsonParser.nextToken();
/*     */           }
/* 604 */           localTokenBuffer.writeEndObject();
/* 605 */           if (localObject3.getClass() != this._beanType.getRawClass())
/*     */           {
/*     */ 
/* 608 */             localTokenBuffer.close();
/* 609 */             throw paramDeserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
/*     */           }
/* 611 */           return this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject3, localTokenBuffer);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 616 */       else if (!localPropertyValueBuffer.readIdProperty((String)localObject1))
/*     */       {
/*     */ 
/*     */ 
/* 620 */         localObject2 = this._beanProperties.find((String)localObject1);
/* 621 */         if (localObject2 != null) {
/* 622 */           localPropertyValueBuffer.bufferProperty((SettableBeanProperty)localObject2, ((SettableBeanProperty)localObject2).deserialize(paramJsonParser, paramDeserializationContext));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 628 */         else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject1))) {
/* 629 */           paramJsonParser.skipChildren();
/*     */         }
/*     */         else {
/* 632 */           localTokenBuffer.writeFieldName((String)localObject1);
/* 633 */           localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */           
/* 635 */           if (this._anySetter != null) {
/* 636 */             localPropertyValueBuffer.bufferAnyProperty(this._anySetter, (String)localObject1, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 643 */       localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */     } catch (Exception localException1) {
/* 645 */       wrapInstantiationProblem(localException1, paramDeserializationContext);
/* 646 */       return null;
/*     */     }
/* 648 */     return this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject1, localTokenBuffer);
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
/* 661 */     if (this._propertyBasedCreator != null) {
/* 662 */       return deserializeUsingPropertyBasedWithExternalTypeId(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 664 */     return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext, this._valueInstantiator.createUsingDefault(paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */   protected Object deserializeWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 671 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 672 */     ExternalTypeHandler localExternalTypeHandler = this._externalTypeIdHandler.start();
/* 673 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/* 674 */       String str = paramJsonParser.getCurrentName();
/* 675 */       paramJsonParser.nextToken();
/* 676 */       SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
/* 677 */       if (localSettableBeanProperty != null)
/*     */       {
/* 679 */         if (paramJsonParser.getCurrentToken().isScalarValue()) {
/* 680 */           localExternalTypeHandler.handleTypePropertyValue(paramJsonParser, paramDeserializationContext, str, paramObject);
/*     */         }
/* 682 */         if ((localClass != null) && (!localSettableBeanProperty.visibleInView(localClass))) {
/* 683 */           paramJsonParser.skipChildren();
/*     */         } else {
/*     */           try
/*     */           {
/* 687 */             localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
/*     */           } catch (Exception localException1) {
/* 689 */             wrapAndThrow(localException1, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 694 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 695 */         paramJsonParser.skipChildren();
/*     */ 
/*     */ 
/*     */       }
/* 699 */       else if (!localExternalTypeHandler.handlePropertyValue(paramJsonParser, paramDeserializationContext, str, paramObject))
/*     */       {
/*     */ 
/*     */ 
/* 703 */         if (this._anySetter != null) {
/*     */           try {
/* 705 */             this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */           } catch (Exception localException2) {
/* 707 */             wrapAndThrow(localException2, paramObject, str, paramDeserializationContext);
/*     */           }
/*     */           
/*     */         }
/*     */         else
/* 712 */           handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, str);
/*     */       }
/*     */     }
/* 715 */     return localExternalTypeHandler.complete(paramJsonParser, paramDeserializationContext, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */   protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 722 */     ExternalTypeHandler localExternalTypeHandler = this._externalTypeIdHandler.start();
/* 723 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/* 724 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
/*     */     
/* 726 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 727 */     localTokenBuffer.writeStartObject();
/*     */     
/* 729 */     for (JsonToken localJsonToken = paramJsonParser.getCurrentToken(); 
/* 730 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 731 */       String str = paramJsonParser.getCurrentName();
/* 732 */       paramJsonParser.nextToken();
/*     */       
/* 734 */       SettableBeanProperty localSettableBeanProperty = localPropertyBasedCreator.findCreatorProperty(str);
/* 735 */       Object localObject1; if (localSettableBeanProperty != null)
/*     */       {
/* 737 */         if (!localExternalTypeHandler.handlePropertyValue(paramJsonParser, paramDeserializationContext, str, localPropertyValueBuffer))
/*     */         {
/*     */ 
/*     */ 
/* 741 */           localObject1 = localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext);
/* 742 */           if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty.getCreatorIndex(), localObject1)) {
/* 743 */             localJsonToken = paramJsonParser.nextToken();
/*     */             Object localObject2;
/*     */             try {
/* 746 */               localObject2 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */             } catch (Exception localException2) {
/* 748 */               wrapAndThrow(localException2, this._beanType.getRawClass(), str, paramDeserializationContext);
/* 749 */               continue;
/*     */             }
/*     */             
/* 752 */             while (localJsonToken == JsonToken.FIELD_NAME) {
/* 753 */               paramJsonParser.nextToken();
/* 754 */               localTokenBuffer.copyCurrentStructure(paramJsonParser);
/* 755 */               localJsonToken = paramJsonParser.nextToken();
/*     */             }
/* 757 */             if (localObject2.getClass() != this._beanType.getRawClass())
/*     */             {
/*     */ 
/* 760 */               throw paramDeserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
/*     */             }
/* 762 */             return localExternalTypeHandler.complete(paramJsonParser, paramDeserializationContext, localObject2);
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */       }
/* 768 */       else if (!localPropertyValueBuffer.readIdProperty(str))
/*     */       {
/*     */ 
/*     */ 
/* 772 */         localObject1 = this._beanProperties.find(str);
/* 773 */         if (localObject1 != null) {
/* 774 */           localPropertyValueBuffer.bufferProperty((SettableBeanProperty)localObject1, ((SettableBeanProperty)localObject1).deserialize(paramJsonParser, paramDeserializationContext));
/*     */ 
/*     */ 
/*     */         }
/* 778 */         else if (!localExternalTypeHandler.handlePropertyValue(paramJsonParser, paramDeserializationContext, str, null))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 784 */           if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 785 */             paramJsonParser.skipChildren();
/*     */ 
/*     */ 
/*     */           }
/* 789 */           else if (this._anySetter != null) {
/* 790 */             localPropertyValueBuffer.bufferAnyProperty(this._anySetter, str, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/* 796 */       return localExternalTypeHandler.complete(paramJsonParser, paramDeserializationContext, localPropertyValueBuffer, localPropertyBasedCreator);
/*     */     } catch (Exception localException1) {
/* 798 */       wrapInstantiationProblem(localException1, paramDeserializationContext); }
/* 799 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/BeanDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */