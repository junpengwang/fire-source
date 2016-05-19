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
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class BeanAsArrayBuilderDeserializer
/*     */   extends BeanDeserializerBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final BeanDeserializerBase _delegate;
/*     */   protected final SettableBeanProperty[] _orderedProperties;
/*     */   protected final AnnotatedMethod _buildMethod;
/*     */   
/*     */   public BeanAsArrayBuilderDeserializer(BeanDeserializerBase paramBeanDeserializerBase, SettableBeanProperty[] paramArrayOfSettableBeanProperty, AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/*  45 */     super(paramBeanDeserializerBase);
/*  46 */     this._delegate = paramBeanDeserializerBase;
/*  47 */     this._orderedProperties = paramArrayOfSettableBeanProperty;
/*  48 */     this._buildMethod = paramAnnotatedMethod;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer)
/*     */   {
/*  58 */     return this._delegate.unwrappingDeserializer(paramNameTransformer);
/*     */   }
/*     */   
/*     */   public BeanAsArrayBuilderDeserializer withObjectIdReader(ObjectIdReader paramObjectIdReader)
/*     */   {
/*  63 */     return new BeanAsArrayBuilderDeserializer(this._delegate.withObjectIdReader(paramObjectIdReader), this._orderedProperties, this._buildMethod);
/*     */   }
/*     */   
/*     */ 
/*     */   public BeanAsArrayBuilderDeserializer withIgnorableProperties(HashSet<String> paramHashSet)
/*     */   {
/*  69 */     return new BeanAsArrayBuilderDeserializer(this._delegate.withIgnorableProperties(paramHashSet), this._orderedProperties, this._buildMethod);
/*     */   }
/*     */   
/*     */ 
/*     */   protected BeanAsArrayBuilderDeserializer asArrayDeserializer()
/*     */   {
/*  75 */     return this;
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
/*  88 */       return this._buildMethod.getMember().invoke(paramObject, new Object[0]);
/*     */     } catch (Exception localException) {
/*  90 */       wrapInstantiationProblem(localException, paramDeserializationContext); }
/*  91 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 100 */     if (paramJsonParser.getCurrentToken() != JsonToken.START_ARRAY) {
/* 101 */       return finishBuild(paramDeserializationContext, _deserializeFromNonArray(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 103 */     if (!this._vanillaProcessing) {
/* 104 */       return finishBuild(paramDeserializationContext, _deserializeNonVanilla(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 106 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 107 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 108 */     int i = 0;
/* 109 */     int j = arrayOfSettableBeanProperty.length;
/*     */     for (;;) {
/* 111 */       if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 112 */         return finishBuild(paramDeserializationContext, localObject);
/*     */       }
/* 114 */       if (i == j) {
/*     */         break;
/*     */       }
/* 117 */       SettableBeanProperty localSettableBeanProperty = arrayOfSettableBeanProperty[i];
/* 118 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 120 */           localObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, localObject);
/*     */         } catch (Exception localException) {
/* 122 */           wrapAndThrow(localException, localObject, localSettableBeanProperty.getName(), paramDeserializationContext);
/*     */         }
/*     */       } else {
/* 125 */         paramJsonParser.skipChildren();
/*     */       }
/* 127 */       i++;
/*     */     }
/*     */     
/* 130 */     if (!this._ignoreAllUnknown) {
/* 131 */       throw paramDeserializationContext.mappingException("Unexpected JSON values; expected at most " + j + " properties (in JSON Array)");
/*     */     }
/*     */     
/* 134 */     while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 135 */       paramJsonParser.skipChildren();
/*     */     }
/* 137 */     return finishBuild(paramDeserializationContext, localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 147 */     if (this._injectables != null) {
/* 148 */       injectValues(paramDeserializationContext, paramObject);
/*     */     }
/* 150 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 151 */     int i = 0;
/* 152 */     int j = arrayOfSettableBeanProperty.length;
/*     */     for (;;) {
/* 154 */       if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 155 */         return finishBuild(paramDeserializationContext, paramObject);
/*     */       }
/* 157 */       if (i == j) {
/*     */         break;
/*     */       }
/* 160 */       SettableBeanProperty localSettableBeanProperty = arrayOfSettableBeanProperty[i];
/* 161 */       if (localSettableBeanProperty != null) {
/*     */         try {
/* 163 */           paramObject = localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, paramObject);
/*     */         } catch (Exception localException) {
/* 165 */           wrapAndThrow(localException, paramObject, localSettableBeanProperty.getName(), paramDeserializationContext);
/*     */         }
/*     */       } else {
/* 168 */         paramJsonParser.skipChildren();
/*     */       }
/* 170 */       i++;
/*     */     }
/*     */     
/*     */ 
/* 174 */     if (!this._ignoreAllUnknown) {
/* 175 */       throw paramDeserializationContext.mappingException("Unexpected JSON values; expected at most " + j + " properties (in JSON Array)");
/*     */     }
/*     */     
/* 178 */     while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 179 */       paramJsonParser.skipChildren();
/*     */     }
/* 181 */     return finishBuild(paramDeserializationContext, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 189 */     return _deserializeFromNonArray(paramJsonParser, paramDeserializationContext);
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
/*     */   protected Object _deserializeNonVanilla(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 208 */     if (this._nonStandardCreation) {
/* 209 */       return _deserializeWithCreator(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 211 */     Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 212 */     if (this._injectables != null) {
/* 213 */       injectValues(paramDeserializationContext, localObject);
/*     */     }
/* 215 */     Class localClass = this._needViewProcesing ? paramDeserializationContext.getActiveView() : null;
/* 216 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 217 */     int i = 0;
/* 218 */     int j = arrayOfSettableBeanProperty.length;
/*     */     for (;;) {
/* 220 */       if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 221 */         return localObject;
/*     */       }
/* 223 */       if (i == j) {
/*     */         break;
/*     */       }
/* 226 */       SettableBeanProperty localSettableBeanProperty = arrayOfSettableBeanProperty[i];
/* 227 */       i++;
/* 228 */       if ((localSettableBeanProperty != null) && (
/* 229 */         (localClass == null) || (localSettableBeanProperty.visibleInView(localClass)))) {
/*     */         try {
/* 231 */           localSettableBeanProperty.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, localObject);
/*     */         } catch (Exception localException) {
/* 233 */           wrapAndThrow(localException, localObject, localSettableBeanProperty.getName(), paramDeserializationContext);
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 239 */         paramJsonParser.skipChildren();
/*     */       }
/*     */     }
/* 242 */     if (!this._ignoreAllUnknown) {
/* 243 */       throw paramDeserializationContext.mappingException("Unexpected JSON values; expected at most " + j + " properties (in JSON Array)");
/*     */     }
/*     */     
/* 246 */     while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 247 */       paramJsonParser.skipChildren();
/*     */     }
/* 249 */     return localObject;
/*     */   }
/*     */   
/*     */   protected Object _deserializeWithCreator(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 255 */     if (this._delegateDeserializer != null) {
/* 256 */       return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */     
/* 259 */     if (this._propertyBasedCreator != null) {
/* 260 */       return _deserializeUsingPropertyBased(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/* 263 */     if (this._beanType.isAbstract()) {
/* 264 */       throw JsonMappingException.from(paramJsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
/*     */     }
/*     */     
/* 267 */     throw JsonMappingException.from(paramJsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
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
/*     */   protected final Object _deserializeUsingPropertyBased(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 284 */     PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
/* 285 */     PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
/*     */     
/* 287 */     SettableBeanProperty[] arrayOfSettableBeanProperty = this._orderedProperties;
/* 288 */     int i = arrayOfSettableBeanProperty.length;
/* 289 */     int j = 0;
/* 290 */     Object localObject1 = null;
/* 292 */     for (; 
/* 292 */         paramJsonParser.nextToken() != JsonToken.END_ARRAY; j++) {
/* 293 */       SettableBeanProperty localSettableBeanProperty1 = j < i ? arrayOfSettableBeanProperty[j] : null;
/* 294 */       if (localSettableBeanProperty1 == null) {
/* 295 */         paramJsonParser.skipChildren();
/*     */ 
/*     */ 
/*     */       }
/* 299 */       else if (localObject1 != null) {
/*     */         try {
/* 301 */           localObject1 = localSettableBeanProperty1.deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, localObject1);
/*     */         } catch (Exception localException2) {
/* 303 */           wrapAndThrow(localException2, localObject1, localSettableBeanProperty1.getName(), paramDeserializationContext);
/*     */         }
/*     */       }
/*     */       else {
/* 307 */         String str = localSettableBeanProperty1.getName();
/*     */         
/* 309 */         SettableBeanProperty localSettableBeanProperty2 = localPropertyBasedCreator.findCreatorProperty(str);
/* 310 */         if (localSettableBeanProperty2 != null)
/*     */         {
/* 312 */           Object localObject2 = localSettableBeanProperty2.deserialize(paramJsonParser, paramDeserializationContext);
/* 313 */           if (localPropertyValueBuffer.assignParameter(localSettableBeanProperty2.getCreatorIndex(), localObject2)) {
/*     */             try {
/* 315 */               localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */             } catch (Exception localException3) {
/* 317 */               wrapAndThrow(localException3, this._beanType.getRawClass(), str, paramDeserializationContext);
/* 318 */               continue;
/*     */             }
/*     */             
/* 321 */             if (localObject1.getClass() != this._beanType.getRawClass())
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 326 */               throw paramDeserializationContext.mappingException("Can not support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type " + this._beanType.getRawClass().getName() + ", actual type " + localObject1.getClass().getName());
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 333 */         else if (!localPropertyValueBuffer.readIdProperty(str))
/*     */         {
/*     */ 
/*     */ 
/* 337 */           localPropertyValueBuffer.bufferProperty(localSettableBeanProperty1, localSettableBeanProperty1.deserialize(paramJsonParser, paramDeserializationContext));
/*     */         }
/*     */       }
/*     */     }
/* 341 */     if (localObject1 == null) {
/*     */       try {
/* 343 */         localObject1 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
/*     */       } catch (Exception localException1) {
/* 345 */         wrapInstantiationProblem(localException1, paramDeserializationContext);
/* 346 */         return null;
/*     */       }
/*     */     }
/* 349 */     return localObject1;
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
/* 362 */     throw paramDeserializationContext.mappingException("Can not deserialize a POJO (of type " + this._beanType.getRawClass().getName() + ") from non-Array representation (token: " + paramJsonParser.getCurrentToken() + "): type/property designed to be serialized as JSON Array");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/BeanAsArrayBuilderDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */