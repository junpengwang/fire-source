/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.BeanDeserializerBase;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanAsArrayDeserializer
/*     */   extends BeanDeserializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final BeanDeserializerBase _delegate;
/*     */   protected final SettableBeanProperty[] _orderedProperties;
/*     */   
/*     */   public BeanAsArrayDeserializer(BeanDeserializerBase paramBeanDeserializerBase, SettableBeanProperty[] paramArrayOfSettableBeanProperty)
/*     */   {
/*  50 */     super(paramBeanDeserializerBase);
/*  51 */     this._delegate = paramBeanDeserializerBase;
/*  52 */     this._orderedProperties = paramArrayOfSettableBeanProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer)
/*     */   {
/*  62 */     return this._delegate.unwrappingDeserializer(paramNameTransformer);
/*     */   }
/*     */   
/*     */   public BeanAsArrayDeserializer withObjectIdReader(ObjectIdReader paramObjectIdReader)
/*     */   {
/*  67 */     return new BeanAsArrayDeserializer(this._delegate.withObjectIdReader(paramObjectIdReader), this._orderedProperties);
/*     */   }
/*     */   
/*     */ 
/*     */   public BeanAsArrayDeserializer withIgnorableProperties(HashSet<String> paramHashSet)
/*     */   {
/*  73 */     return new BeanAsArrayDeserializer(this._delegate.withIgnorableProperties(paramHashSet), this._orderedProperties);
/*     */   }
/*     */   
/*     */ 
/*     */   protected BeanDeserializerBase asArrayDeserializer()
/*     */   {
/*  79 */     return this;
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
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  93 */     if (paramJsonParser.getCurrentToken() != JsonToken.START_ARRAY) {
/*  94 */       return _deserializeFromNonArray(paramJsonParser, paramDeserializationContext);
/*     */     }
/*  96 */     if (!this._vanillaProcessing) {
/*  97 */       return _deserializeNonVanilla(paramJsonParser, paramDeserializationContext);
/*     */     }
/*  99 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 100 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 101 */     int i = 0;
/* 102 */     int j = arrayOfSettableBeanProperty.length;
/*     */     for (;;) {
/* 104 */       if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 105 */         return localObject;
/*     */       }
/* 107 */       if (i == j) {
/*     */         break;
/*     */       }
/* 110 */       SettableBeanProperty localSettableBeanProperty = arrayOfSettableBeanProperty[i];
/* 111 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 113 */           localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject);
/*     */         } catch (Exception localException) {
/* 115 */           wrapAndThrow(localException, localObject, localSettableBeanProperty.getName(), paramDeserializationContext);
/*     */         }
/*     */       } else {
/* 118 */         paramJsonParser.skipChildren();
/*     */       }
/* 120 */       i++;
/*     */     }
/*     */     
/* 123 */     if (!this._ignoreAllUnknown) {
/* 124 */       throw paramDeserializationContext.mappingException("Unexpected JSON values; expected at most " + j + " properties (in JSON Array)");
/*     */     }
/*     */     
/* 127 */     while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 128 */       paramJsonParser.skipChildren();
/*     */     }
/* 130 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 140 */     if (this._injectables != null) {
/* 141 */       injectValues(paramDeserializationContext, paramObject);
/*     */     }
/* 143 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 144 */     int i = 0;
/* 145 */     int j = arrayOfSettableBeanProperty.length;
/*     */     for (;;) {
/* 147 */       if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 148 */         return paramObject;
/*     */       }
/* 150 */       if (i == j) {
/*     */         break;
/*     */       }
/* 153 */       SettableBeanProperty localSettableBeanProperty = arrayOfSettableBeanProperty[i];
/* 154 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 156 */           localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
/*     */         } catch (Exception localException) {
/* 158 */           wrapAndThrow(localException, paramObject, localSettableBeanProperty.getName(), paramDeserializationContext);
/*     */         }
/*     */       } else {
/* 161 */         paramJsonParser.skipChildren();
/*     */       }
/* 163 */       i++;
/*     */     }
/*     */     
/*     */ 
/* 167 */     if (!this._ignoreAllUnknown) {
/* 168 */       throw paramDeserializationContext.mappingException("Unexpected JSON values; expected at most " + j + " properties (in JSON Array)");
/*     */     }
/*     */     
/* 171 */     while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 172 */       paramJsonParser.skipChildren();
/*     */     }
/* 174 */     return paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 183 */     return _deserializeFromNonArray(paramJsonParser, paramDeserializationContext);
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
/*     */   protected Object _deserializeNonVanilla(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 199 */     if (this._nonStandardCreation) {
/* 200 */       return _deserializeWithCreator(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 202 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 203 */     if (this._injectables != null) {
/* 204 */       injectValues(paramDeserializationContext, localObject);
/*     */     }
/* 206 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 207 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 208 */     int i = 0;
/* 209 */     int j = arrayOfSettableBeanProperty.length;
/*     */     for (;;) {
/* 211 */       if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 212 */         return localObject;
/*     */       }
/* 214 */       if (i == j) {
/*     */         break;
/*     */       }
/* 217 */       SettableBeanProperty localSettableBeanProperty = arrayOfSettableBeanProperty[i];
/* 218 */       i++;
/* 219 */       if ((localSettableBeanProperty != null) && (
/* 220 */         (localClass == null) || (localSettableBeanProperty.visibleInView(localClass)))) {
/*     */         try {
/* 222 */           localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject);
/*     */         } catch (Exception localException) {
/* 224 */           wrapAndThrow(localException, localObject, localSettableBeanProperty.getName(), paramDeserializationContext);
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 230 */         paramJsonParser.skipChildren();
/*     */       }
/*     */     }
/* 233 */     if (!this._ignoreAllUnknown) {
/* 234 */       throw paramDeserializationContext.mappingException("Unexpected JSON values; expected at most " + j + " properties (in JSON Array)");
/*     */     }
/*     */     
/* 237 */     while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 238 */       paramJsonParser.skipChildren();
/*     */     }
/* 240 */     return localObject;
/*     */   }
/*     */   
/*     */   protected Object _deserializeWithCreator(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 246 */     if (this._delegateDeserializer != null) {
/* 247 */       return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 249 */     if (this._propertyBasedCreator != null) {
/* 250 */       return _deserializeUsingPropertyBased(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/* 253 */     if (this._beanType.isAbstract()) {
/* 254 */       throw JsonMappingException.from(paramJsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
/*     */     }
/*     */     
/* 257 */     throw JsonMappingException.from(paramJsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
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
/* 273 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/* 274 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
/*     */     
/* 276 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 277 */     int i = arrayOfSettableBeanProperty.length;
/* 278 */     int j = 0;
/* 279 */     Object localObject1 = null;
/* 281 */     for (; 
/* 281 */         paramJsonParser.nextToken() != JsonToken.END_ARRAY; j++) {
/* 282 */       SettableBeanProperty localSettableBeanProperty1 = j < i ? arrayOfSettableBeanProperty[j] : null;
/* 283 */       if (localSettableBeanProperty1 == null) {
/* 284 */         paramJsonParser.skipChildren();
/*     */ 
/*     */ 
/*     */       }
/* 288 */       else if (localObject1 != null) {
/*     */         try {
/* 290 */           localSettableBeanProperty1.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject1);
/*     */         } catch (Exception localException2) {
/* 292 */           wrapAndThrow(localException2, localObject1, localSettableBeanProperty1.getName(), paramDeserializationContext);
/*     */         }
/*     */       }
/*     */       else {
/* 296 */         String str = localSettableBeanProperty1.getName();
/*     */         
/* 298 */         SettableBeanProperty localSettableBeanProperty2 = localPropertyBasedCreator.findCreatorProperty(str);
/* 299 */         if (localSettableBeanProperty2 != null)
/*     */         {
/* 301 */           Object localObject2 = localSettableBeanProperty2.deserialize(paramJsonParser, paramDeserializationContext);
/* 302 */           if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty2.getCreatorIndex(), localObject2)) {
/*     */             try {
/* 304 */               localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */             } catch (Exception localException3) {
/* 306 */               wrapAndThrow(localException3, this._beanType.getRawClass(), str, paramDeserializationContext);
/* 307 */               continue;
/*     */             }
/*     */             
/* 310 */             if (localObject1.getClass() != this._beanType.getRawClass())
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 315 */               throw paramDeserializationContext.mappingException("Can not support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type " + this._beanType.getRawClass().getName() + ", actual type " + localObject1.getClass().getName());
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 322 */         else if (!localPropertyValueBuffer.readIdProperty(str))
/*     */         {
/*     */ 
/*     */ 
/* 326 */           localPropertyValueBuffer.bufferProperty(localSettableBeanProperty1, localSettableBeanProperty1.deserialize(paramJsonParser, paramDeserializationContext));
/*     */         }
/*     */       }
/*     */     }
/* 330 */     if (localObject1 == null) {
/*     */       try {
/* 332 */         localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */       } catch (Exception localException1) {
/* 334 */         wrapInstantiationProblem(localException1, paramDeserializationContext);
/* 335 */         return null;
/*     */       }
/*     */     }
/* 338 */     return localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object _deserializeFromNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 351 */     throw paramDeserializationContext.mappingException("Can not deserialize a POJO (of type " + this._beanType.getRawClass().getName() + ") from non-Array representation (token: " + paramJsonParser.getCurrentToken() + "): type/property designed to be serialized as JSON Array");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/BeanAsArrayDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */