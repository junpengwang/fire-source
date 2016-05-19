/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Shape;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException.Reference;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.AnyGetterWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyFilter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.FilterProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.WritableObjectId;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ public abstract class BeanSerializerBase extends StdSerializer<Object> implements ContextualSerializer, com.shaded.fasterxml.jackson.databind.ser.ResolvableSerializer, JsonFormatVisitable, com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware
/*     */ {
/*  43 */   protected static final BeanPropertyWriter[] NO_PROPS = new BeanPropertyWriter[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final BeanPropertyWriter[] _props;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final BeanPropertyWriter[] _filteredProps;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final AnyGetterWriter _anyGetterWriter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Object _propertyFilterId;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final AnnotatedMember _typeId;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final ObjectIdWriter _objectIdWriter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JsonFormat.Shape _serializationShape;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanSerializerBase(JavaType paramJavaType, BeanSerializerBuilder paramBeanSerializerBuilder, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter1, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter2)
/*     */   {
/* 107 */     super(paramJavaType);
/* 108 */     this._props = paramArrayOfBeanPropertyWriter1;
/* 109 */     this._filteredProps = paramArrayOfBeanPropertyWriter2;
/* 110 */     if (paramBeanSerializerBuilder == null) {
/* 111 */       this._typeId = null;
/* 112 */       this._anyGetterWriter = null;
/* 113 */       this._propertyFilterId = null;
/* 114 */       this._objectIdWriter = null;
/* 115 */       this._serializationShape = null;
/*     */     } else {
/* 117 */       this._typeId = paramBeanSerializerBuilder.getTypeId();
/* 118 */       this._anyGetterWriter = paramBeanSerializerBuilder.getAnyGetter();
/* 119 */       this._propertyFilterId = paramBeanSerializerBuilder.getFilterId();
/* 120 */       this._objectIdWriter = paramBeanSerializerBuilder.getObjectIdWriter();
/* 121 */       JsonFormat.Value localValue = paramBeanSerializerBuilder.getBeanDescription().findExpectedFormat(null);
/* 122 */       this._serializationShape = (localValue == null ? null : localValue.getShape());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter1, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter2)
/*     */   {
/* 129 */     super(paramBeanSerializerBase._handledType);
/* 130 */     this._props = paramArrayOfBeanPropertyWriter1;
/* 131 */     this._filteredProps = paramArrayOfBeanPropertyWriter2;
/*     */     
/* 133 */     this._typeId = paramBeanSerializerBase._typeId;
/* 134 */     this._anyGetterWriter = paramBeanSerializerBase._anyGetterWriter;
/* 135 */     this._objectIdWriter = paramBeanSerializerBase._objectIdWriter;
/* 136 */     this._propertyFilterId = paramBeanSerializerBase._propertyFilterId;
/* 137 */     this._serializationShape = paramBeanSerializerBase._serializationShape;
/*     */   }
/*     */   
/*     */   protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, ObjectIdWriter paramObjectIdWriter)
/*     */   {
/* 142 */     super(paramBeanSerializerBase._handledType);
/* 143 */     this._props = paramBeanSerializerBase._props;
/* 144 */     this._filteredProps = paramBeanSerializerBase._filteredProps;
/*     */     
/* 146 */     this._typeId = paramBeanSerializerBase._typeId;
/* 147 */     this._anyGetterWriter = paramBeanSerializerBase._anyGetterWriter;
/* 148 */     this._objectIdWriter = paramObjectIdWriter;
/* 149 */     this._propertyFilterId = paramBeanSerializerBase._propertyFilterId;
/* 150 */     this._serializationShape = paramBeanSerializerBase._serializationShape;
/*     */   }
/*     */   
/*     */   protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, String[] paramArrayOfString)
/*     */   {
/* 155 */     super(paramBeanSerializerBase._handledType);
/*     */     
/*     */ 
/* 158 */     HashSet localHashSet = com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.arrayToSet(paramArrayOfString);
/* 159 */     BeanPropertyWriter[] arrayOfBeanPropertyWriter1 = paramBeanSerializerBase._props;
/* 160 */     BeanPropertyWriter[] arrayOfBeanPropertyWriter2 = paramBeanSerializerBase._filteredProps;
/* 161 */     int i = arrayOfBeanPropertyWriter1.length;
/*     */     
/* 163 */     ArrayList localArrayList1 = new ArrayList(i);
/* 164 */     ArrayList localArrayList2 = arrayOfBeanPropertyWriter2 == null ? null : new ArrayList(i);
/*     */     
/* 166 */     for (int j = 0; j < i; j++) {
/* 167 */       BeanPropertyWriter localBeanPropertyWriter = arrayOfBeanPropertyWriter1[j];
/*     */       
/* 169 */       if (!localHashSet.contains(localBeanPropertyWriter.getName()))
/*     */       {
/*     */ 
/* 172 */         localArrayList1.add(localBeanPropertyWriter);
/* 173 */         if (arrayOfBeanPropertyWriter2 != null)
/* 174 */           localArrayList2.add(arrayOfBeanPropertyWriter2[j]);
/*     */       }
/*     */     }
/* 177 */     this._props = ((BeanPropertyWriter[])localArrayList1.toArray(new BeanPropertyWriter[localArrayList1.size()]));
/* 178 */     this._filteredProps = (localArrayList2 == null ? null : (BeanPropertyWriter[])localArrayList2.toArray(new BeanPropertyWriter[localArrayList2.size()]));
/*     */     
/* 180 */     this._typeId = paramBeanSerializerBase._typeId;
/* 181 */     this._anyGetterWriter = paramBeanSerializerBase._anyGetterWriter;
/* 182 */     this._objectIdWriter = paramBeanSerializerBase._objectIdWriter;
/* 183 */     this._propertyFilterId = paramBeanSerializerBase._propertyFilterId;
/* 184 */     this._serializationShape = paramBeanSerializerBase._serializationShape;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract BeanSerializerBase withObjectIdWriter(ObjectIdWriter paramObjectIdWriter);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract BeanSerializerBase withIgnorals(String[] paramArrayOfString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract BeanSerializerBase asArraySerializer();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase)
/*     */   {
/* 217 */     this(paramBeanSerializerBase, paramBeanSerializerBase._props, paramBeanSerializerBase._filteredProps);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, NameTransformer paramNameTransformer)
/*     */   {
/* 225 */     this(paramBeanSerializerBase, rename(paramBeanSerializerBase._props, paramNameTransformer), rename(paramBeanSerializerBase._filteredProps, paramNameTransformer));
/*     */   }
/*     */   
/*     */ 
/*     */   private static final BeanPropertyWriter[] rename(BeanPropertyWriter[] paramArrayOfBeanPropertyWriter, NameTransformer paramNameTransformer)
/*     */   {
/* 231 */     if ((paramArrayOfBeanPropertyWriter == null) || (paramArrayOfBeanPropertyWriter.length == 0) || (paramNameTransformer == null) || (paramNameTransformer == NameTransformer.NOP)) {
/* 232 */       return paramArrayOfBeanPropertyWriter;
/*     */     }
/* 234 */     int i = paramArrayOfBeanPropertyWriter.length;
/* 235 */     BeanPropertyWriter[] arrayOfBeanPropertyWriter = new BeanPropertyWriter[i];
/* 236 */     for (int j = 0; j < i; j++) {
/* 237 */       BeanPropertyWriter localBeanPropertyWriter = paramArrayOfBeanPropertyWriter[j];
/* 238 */       if (localBeanPropertyWriter != null) {
/* 239 */         arrayOfBeanPropertyWriter[j] = localBeanPropertyWriter.rename(paramNameTransformer);
/*     */       }
/*     */     }
/* 242 */     return arrayOfBeanPropertyWriter;
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
/*     */   public void resolve(SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 259 */     int i = this._filteredProps == null ? 0 : this._filteredProps.length;
/* 260 */     int j = 0; for (int k = this._props.length; j < k; j++) {
/* 261 */       BeanPropertyWriter localBeanPropertyWriter = this._props[j];
/*     */       Object localObject1;
/* 263 */       Object localObject2; if ((!localBeanPropertyWriter.willSuppressNulls()) && (!localBeanPropertyWriter.hasNullSerializer())) {
/* 264 */         localObject1 = paramSerializerProvider.findNullValueSerializer(localBeanPropertyWriter);
/* 265 */         if (localObject1 != null) {
/* 266 */           localBeanPropertyWriter.assignNullSerializer((JsonSerializer)localObject1);
/*     */           
/* 268 */           if (j < i) {
/* 269 */             localObject2 = this._filteredProps[j];
/* 270 */             if (localObject2 != null) {
/* 271 */               ((BeanPropertyWriter)localObject2).assignNullSerializer((JsonSerializer)localObject1);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 277 */       if (!localBeanPropertyWriter.hasSerializer())
/*     */       {
/*     */ 
/*     */ 
/* 281 */         localObject1 = findConvertingSerializer(paramSerializerProvider, localBeanPropertyWriter);
/* 282 */         if (localObject1 == null)
/*     */         {
/* 284 */           localObject2 = localBeanPropertyWriter.getSerializationType();
/*     */           
/*     */ 
/*     */ 
/* 288 */           if (localObject2 == null) {
/* 289 */             localObject2 = paramSerializerProvider.constructType(localBeanPropertyWriter.getGenericPropertyType());
/* 290 */             if (!((JavaType)localObject2).isFinal()) {
/* 291 */               if ((!((JavaType)localObject2).isContainerType()) && (((JavaType)localObject2).containedTypeCount() <= 0)) continue;
/* 292 */               localBeanPropertyWriter.setNonTrivialBaseType((JavaType)localObject2); continue;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 297 */           localObject1 = paramSerializerProvider.findValueSerializer((JavaType)localObject2, localBeanPropertyWriter);
/*     */           
/*     */ 
/*     */ 
/* 301 */           if (((JavaType)localObject2).isContainerType()) {
/* 302 */             TypeSerializer localTypeSerializer = (TypeSerializer)((JavaType)localObject2).getContentType().getTypeHandler();
/* 303 */             if (localTypeSerializer != null)
/*     */             {
/* 305 */               if ((localObject1 instanceof ContainerSerializer))
/*     */               {
/*     */ 
/* 308 */                 ContainerSerializer localContainerSerializer = ((ContainerSerializer)localObject1).withValueTypeSerializer(localTypeSerializer);
/* 309 */                 localObject1 = localContainerSerializer;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 314 */         localBeanPropertyWriter.assignSerializer((JsonSerializer)localObject1);
/*     */         
/* 316 */         if (j < i) {
/* 317 */           localObject2 = this._filteredProps[j];
/* 318 */           if (localObject2 != null) {
/* 319 */             ((BeanPropertyWriter)localObject2).assignSerializer((JsonSerializer)localObject1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 325 */     if (this._anyGetterWriter != null) {
/* 326 */       this._anyGetterWriter.resolve(paramSerializerProvider);
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
/*     */   protected JsonSerializer<Object> findConvertingSerializer(SerializerProvider paramSerializerProvider, BeanPropertyWriter paramBeanPropertyWriter)
/*     */     throws JsonMappingException
/*     */   {
/* 341 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializerProvider.getAnnotationIntrospector();
/* 342 */     if (localAnnotationIntrospector != null) {
/* 343 */       Object localObject = localAnnotationIntrospector.findSerializationConverter(paramBeanPropertyWriter.getMember());
/* 344 */       if (localObject != null) {
/* 345 */         Converter localConverter = paramSerializerProvider.converterInstance(paramBeanPropertyWriter.getMember(), localObject);
/* 346 */         JavaType localJavaType = localConverter.getOutputType(paramSerializerProvider.getTypeFactory());
/* 347 */         JsonSerializer localJsonSerializer = paramSerializerProvider.findValueSerializer(localJavaType, paramBeanPropertyWriter);
/* 348 */         return new StdDelegatingSerializer(localConverter, localJavaType, localJsonSerializer);
/*     */       }
/*     */     }
/* 351 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 359 */     ObjectIdWriter localObjectIdWriter = this._objectIdWriter;
/* 360 */     String[] arrayOfString = null;
/* 361 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializerProvider.getAnnotationIntrospector();
/* 362 */     AnnotatedMember localAnnotatedMember = (paramBeanProperty == null) || (localAnnotationIntrospector == null) ? null : paramBeanProperty.getMember();
/*     */     
/*     */     Object localObject2;
/*     */     
/* 366 */     if (localAnnotatedMember != null) {
/* 367 */       arrayOfString = localAnnotationIntrospector.findPropertiesToIgnore(localAnnotatedMember);
/* 368 */       localObject1 = localAnnotationIntrospector.findObjectIdInfo(localAnnotatedMember);
/* 369 */       if (localObject1 == null)
/*     */       {
/* 371 */         if (localObjectIdWriter != null) {
/* 372 */           localObject1 = localAnnotationIntrospector.findObjectReferenceInfo(localAnnotatedMember, new ObjectIdInfo("", null, null));
/* 373 */           localObjectIdWriter = this._objectIdWriter.withAlwaysAsId(((ObjectIdInfo)localObject1).getAlwaysAsId());
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 382 */         localObject1 = localAnnotationIntrospector.findObjectReferenceInfo(localAnnotatedMember, (ObjectIdInfo)localObject1);
/*     */         
/* 384 */         localObject2 = ((ObjectIdInfo)localObject1).getGeneratorType();
/* 385 */         JavaType localJavaType1 = paramSerializerProvider.constructType((Type)localObject2);
/* 386 */         JavaType localJavaType2 = paramSerializerProvider.getTypeFactory().findTypeParameters(localJavaType1, ObjectIdGenerator.class)[0];
/*     */         
/* 388 */         if (localObject2 == ObjectIdGenerators.PropertyGenerator.class) {
/* 389 */           String str = ((ObjectIdInfo)localObject1).getPropertyName();
/* 390 */           Object localObject3 = null;
/*     */           
/* 392 */           int i = 0; for (int j = this._props.length;; i++) {
/* 393 */             if (i == j) {
/* 394 */               throw new IllegalArgumentException("Invalid Object Id definition for " + this._handledType.getName() + ": can not find property with name '" + str + "'");
/*     */             }
/*     */             
/* 397 */             BeanPropertyWriter localBeanPropertyWriter1 = this._props[i];
/* 398 */             if (str.equals(localBeanPropertyWriter1.getName())) {
/* 399 */               localObject3 = localBeanPropertyWriter1;
/*     */               
/*     */ 
/*     */ 
/* 403 */               if (i <= 0) break;
/* 404 */               System.arraycopy(this._props, 0, this._props, 1, i);
/* 405 */               this._props[0] = localObject3;
/* 406 */               if (this._filteredProps == null) break;
/* 407 */               BeanPropertyWriter localBeanPropertyWriter2 = this._filteredProps[i];
/* 408 */               System.arraycopy(this._filteredProps, 0, this._filteredProps, 1, i);
/* 409 */               this._filteredProps[0] = localBeanPropertyWriter2;
/* 410 */               break;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 415 */           localJavaType2 = ((BeanPropertyWriter)localObject3).getType();
/* 416 */           localObject4 = new PropertyBasedObjectIdGenerator((ObjectIdInfo)localObject1, (BeanPropertyWriter)localObject3);
/* 417 */           localObjectIdWriter = ObjectIdWriter.construct(localJavaType2, null, (ObjectIdGenerator)localObject4, ((ObjectIdInfo)localObject1).getAlwaysAsId());
/*     */         } else {
/* 419 */           localObject4 = paramSerializerProvider.objectIdGeneratorInstance(localAnnotatedMember, (ObjectIdInfo)localObject1);
/* 420 */           localObjectIdWriter = ObjectIdWriter.construct(localJavaType2, ((ObjectIdInfo)localObject1).getPropertyName(), (ObjectIdGenerator)localObject4, ((ObjectIdInfo)localObject1).getAlwaysAsId());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 426 */     Object localObject1 = this;
/* 427 */     if (localObjectIdWriter != null) {
/* 428 */       localObject4 = paramSerializerProvider.findValueSerializer(localObjectIdWriter.idType, paramBeanProperty);
/* 429 */       localObjectIdWriter = localObjectIdWriter.withSerializer((JsonSerializer)localObject4);
/* 430 */       if (localObjectIdWriter != this._objectIdWriter) {
/* 431 */         localObject1 = ((BeanSerializerBase)localObject1).withObjectIdWriter(localObjectIdWriter);
/*     */       }
/*     */     }
/*     */     
/* 435 */     if ((arrayOfString != null) && (arrayOfString.length != 0)) {
/* 436 */       localObject1 = ((BeanSerializerBase)localObject1).withIgnorals(arrayOfString);
/*     */     }
/*     */     
/* 439 */     Object localObject4 = null;
/* 440 */     if (localAnnotatedMember != null) {
/* 441 */       localObject2 = localAnnotationIntrospector.findFormat(localAnnotatedMember);
/*     */       
/* 443 */       if (localObject2 != null) {
/* 444 */         localObject4 = ((JsonFormat.Value)localObject2).getShape();
/*     */       }
/*     */     }
/* 447 */     if (localObject4 == null) {
/* 448 */       localObject4 = this._serializationShape;
/*     */     }
/* 450 */     if (localObject4 == JsonFormat.Shape.ARRAY) {
/* 451 */       localObject1 = ((BeanSerializerBase)localObject1).asArraySerializer();
/*     */     }
/* 453 */     return (JsonSerializer<?>)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean usesObjectId()
/*     */   {
/* 464 */     return this._objectIdWriter != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException;
/*     */   
/*     */ 
/*     */ 
/*     */   public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 478 */     if (this._objectIdWriter != null) {
/* 479 */       _serializeWithObjectId(paramObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
/* 480 */       return;
/*     */     }
/*     */     
/* 483 */     String str = this._typeId == null ? null : _customTypeId(paramObject);
/* 484 */     if (str == null) {
/* 485 */       paramTypeSerializer.writeTypePrefixForObject(paramObject, paramJsonGenerator);
/*     */     } else {
/* 487 */       paramTypeSerializer.writeCustomTypePrefixForObject(paramObject, paramJsonGenerator, str);
/*     */     }
/* 489 */     if (this._propertyFilterId != null) {
/* 490 */       serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 492 */       serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 494 */     if (str == null) {
/* 495 */       paramTypeSerializer.writeTypeSuffixForObject(paramObject, paramJsonGenerator);
/*     */     } else {
/* 497 */       paramTypeSerializer.writeCustomTypeSuffixForObject(paramObject, paramJsonGenerator, str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void _serializeWithObjectId(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, boolean paramBoolean)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 506 */     ObjectIdWriter localObjectIdWriter = this._objectIdWriter;
/* 507 */     WritableObjectId localWritableObjectId = paramSerializerProvider.findObjectId(paramObject, localObjectIdWriter.generator);
/*     */     
/* 509 */     if (localWritableObjectId.writeAsId(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter)) {
/* 510 */       return;
/*     */     }
/*     */     
/* 513 */     Object localObject = localWritableObjectId.generateId(paramObject);
/* 514 */     if (localObjectIdWriter.alwaysAsId) {
/* 515 */       localObjectIdWriter.serializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/* 516 */       return;
/*     */     }
/* 518 */     if (paramBoolean) {
/* 519 */       paramJsonGenerator.writeStartObject();
/*     */     }
/* 521 */     localWritableObjectId.writeAsField(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter);
/* 522 */     if (this._propertyFilterId != null) {
/* 523 */       serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 525 */       serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 527 */     if (paramBoolean) {
/* 528 */       paramJsonGenerator.writeEndObject();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void _serializeWithObjectId(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 537 */     ObjectIdWriter localObjectIdWriter = this._objectIdWriter;
/* 538 */     WritableObjectId localWritableObjectId = paramSerializerProvider.findObjectId(paramObject, localObjectIdWriter.generator);
/*     */     
/* 540 */     if (localWritableObjectId.writeAsId(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter)) {
/* 541 */       return;
/*     */     }
/*     */     
/* 544 */     Object localObject = localWritableObjectId.generateId(paramObject);
/* 545 */     if (localObjectIdWriter.alwaysAsId) {
/* 546 */       localObjectIdWriter.serializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/* 547 */       return;
/*     */     }
/* 549 */     String str = this._typeId == null ? null : _customTypeId(paramObject);
/* 550 */     if (str == null) {
/* 551 */       paramTypeSerializer.writeTypePrefixForObject(paramObject, paramJsonGenerator);
/*     */     } else {
/* 553 */       paramTypeSerializer.writeCustomTypePrefixForObject(paramObject, paramJsonGenerator, str);
/*     */     }
/* 555 */     localWritableObjectId.writeAsField(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter);
/* 556 */     if (this._propertyFilterId != null) {
/* 557 */       serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 559 */       serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 561 */     if (str == null) {
/* 562 */       paramTypeSerializer.writeTypeSuffixForObject(paramObject, paramJsonGenerator);
/*     */     } else {
/* 564 */       paramTypeSerializer.writeCustomTypeSuffixForObject(paramObject, paramJsonGenerator, str);
/*     */     }
/*     */   }
/*     */   
/*     */   private final String _customTypeId(Object paramObject)
/*     */   {
/* 570 */     Object localObject = this._typeId.getValue(paramObject);
/* 571 */     if (localObject == null) {
/* 572 */       return "";
/*     */     }
/* 574 */     return (localObject instanceof String) ? (String)localObject : localObject.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void serializeFields(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*     */     BeanPropertyWriter[] arrayOfBeanPropertyWriter;
/*     */     
/*     */ 
/*     */ 
/* 587 */     if ((this._filteredProps != null) && (paramSerializerProvider.getActiveView() != null)) {
/* 588 */       arrayOfBeanPropertyWriter = this._filteredProps;
/*     */     } else {
/* 590 */       arrayOfBeanPropertyWriter = this._props;
/*     */     }
/* 592 */     int i = 0;
/*     */     try {
/* 594 */       for (int j = arrayOfBeanPropertyWriter.length; i < j; i++) {
/* 595 */         localObject = arrayOfBeanPropertyWriter[i];
/* 596 */         if (localObject != null) {
/* 597 */           ((BeanPropertyWriter)localObject).serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */       }
/* 600 */       if (this._anyGetterWriter != null) {
/* 601 */         this._anyGetterWriter.getAndSerialize(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */     } catch (Exception localException) {
/* 604 */       localObject = i == arrayOfBeanPropertyWriter.length ? "[anySetter]" : arrayOfBeanPropertyWriter[i].getName();
/* 605 */       wrapAndThrow(paramSerializerProvider, localException, paramObject, (String)localObject);
/*     */ 
/*     */     }
/*     */     catch (StackOverflowError localStackOverflowError)
/*     */     {
/*     */ 
/* 611 */       Object localObject = new JsonMappingException("Infinite recursion (StackOverflowError)", localStackOverflowError);
/* 612 */       String str = i == arrayOfBeanPropertyWriter.length ? "[anySetter]" : arrayOfBeanPropertyWriter[i].getName();
/* 613 */       ((JsonMappingException)localObject).prependPath(new JsonMappingException.Reference(paramObject, str));
/* 614 */       throw ((Throwable)localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void serializeFieldsFiltered(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*     */     BeanPropertyWriter[] arrayOfBeanPropertyWriter;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 631 */     if ((this._filteredProps != null) && (paramSerializerProvider.getActiveView() != null)) {
/* 632 */       arrayOfBeanPropertyWriter = this._filteredProps;
/*     */     } else {
/* 634 */       arrayOfBeanPropertyWriter = this._props;
/*     */     }
/* 636 */     BeanPropertyFilter localBeanPropertyFilter = findFilter(paramSerializerProvider);
/*     */     
/* 638 */     if (localBeanPropertyFilter == null) {
/* 639 */       serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
/* 640 */       return;
/*     */     }
/*     */     
/* 643 */     int i = 0;
/*     */     try {
/* 645 */       for (int j = arrayOfBeanPropertyWriter.length; i < j; i++) {
/* 646 */         localObject = arrayOfBeanPropertyWriter[i];
/* 647 */         if (localObject != null) {
/* 648 */           localBeanPropertyFilter.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider, (BeanPropertyWriter)localObject);
/*     */         }
/*     */       }
/* 651 */       if (this._anyGetterWriter != null) {
/* 652 */         this._anyGetterWriter.getAndSerialize(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */     } catch (Exception localException) {
/* 655 */       localObject = i == arrayOfBeanPropertyWriter.length ? "[anySetter]" : arrayOfBeanPropertyWriter[i].getName();
/* 656 */       wrapAndThrow(paramSerializerProvider, localException, paramObject, (String)localObject);
/*     */     } catch (StackOverflowError localStackOverflowError) {
/* 658 */       Object localObject = new JsonMappingException("Infinite recursion (StackOverflowError)", localStackOverflowError);
/* 659 */       String str = i == arrayOfBeanPropertyWriter.length ? "[anySetter]" : arrayOfBeanPropertyWriter[i].getName();
/* 660 */       ((JsonMappingException)localObject).prependPath(new JsonMappingException.Reference(paramObject, str));
/* 661 */       throw ((Throwable)localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanPropertyFilter findFilter(SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 672 */     Object localObject = this._propertyFilterId;
/* 673 */     FilterProvider localFilterProvider = paramSerializerProvider.getFilterProvider();
/*     */     
/* 675 */     if (localFilterProvider == null) {
/* 676 */       throw new JsonMappingException("Can not resolve BeanPropertyFilter with id '" + localObject + "'; no FilterProvider configured");
/*     */     }
/* 678 */     BeanPropertyFilter localBeanPropertyFilter = localFilterProvider.findFilter(localObject);
/*     */     
/* 680 */     return localBeanPropertyFilter;
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/* 687 */     ObjectNode localObjectNode = createSchemaNode("object", true);
/*     */     
/*     */ 
/* 690 */     JsonSerializableSchema localJsonSerializableSchema = (JsonSerializableSchema)this._handledType.getAnnotation(JsonSerializableSchema.class);
/* 691 */     if (localJsonSerializableSchema != null) {
/* 692 */       localObject = localJsonSerializableSchema.id();
/* 693 */       if ((localObject != null) && (((String)localObject).length() > 0)) {
/* 694 */         localObjectNode.put("id", (String)localObject);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 700 */     Object localObject = localObjectNode.objectNode();
/*     */     BeanPropertyFilter localBeanPropertyFilter;
/* 702 */     if (this._propertyFilterId != null) {
/* 703 */       localBeanPropertyFilter = findFilter(paramSerializerProvider);
/*     */     } else {
/* 705 */       localBeanPropertyFilter = null;
/*     */     }
/*     */     
/* 708 */     for (int i = 0; i < this._props.length; i++) {
/* 709 */       BeanPropertyWriter localBeanPropertyWriter = this._props[i];
/* 710 */       if (localBeanPropertyFilter == null) {
/* 711 */         localBeanPropertyWriter.depositSchemaProperty((ObjectNode)localObject, paramSerializerProvider);
/*     */       } else {
/* 713 */         localBeanPropertyFilter.depositSchemaProperty(localBeanPropertyWriter, (ObjectNode)localObject, paramSerializerProvider);
/*     */       }
/*     */     }
/*     */     
/* 717 */     localObjectNode.put("properties", (JsonNode)localObject);
/* 718 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 726 */     com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor localJsonObjectFormatVisitor = paramJsonFormatVisitorWrapper == null ? null : paramJsonFormatVisitorWrapper.expectObjectFormat(paramJavaType);
/* 727 */     if (localJsonObjectFormatVisitor != null) {
/* 728 */       if (this._propertyFilterId != null) {
/* 729 */         BeanPropertyFilter localBeanPropertyFilter = findFilter(paramJsonFormatVisitorWrapper.getProvider());
/* 730 */         for (int j = 0; j < this._props.length; j++) {
/* 731 */           localBeanPropertyFilter.depositSchemaProperty(this._props[j], localJsonObjectFormatVisitor, paramJsonFormatVisitorWrapper.getProvider());
/*     */         }
/*     */       } else {
/* 734 */         for (int i = 0; i < this._props.length; i++) {
/* 735 */           this._props[i].depositSchemaProperty(localJsonObjectFormatVisitor);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/BeanSerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */