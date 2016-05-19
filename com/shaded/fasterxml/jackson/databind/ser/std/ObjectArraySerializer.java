/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class ObjectArraySerializer
/*     */   extends ArraySerializerBase<Object[]>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final boolean _staticTyping;
/*     */   protected final JavaType _elementType;
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */   protected JsonSerializer<Object> _elementSerializer;
/*     */   protected PropertySerializerMap _dynamicSerializers;
/*     */   
/*     */   public ObjectArraySerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/*  67 */     super(Object[].class, null);
/*  68 */     this._elementType = paramJavaType;
/*  69 */     this._staticTyping = paramBoolean;
/*  70 */     this._valueTypeSerializer = paramTypeSerializer;
/*  71 */     this._dynamicSerializers = PropertySerializerMap.emptyMap();
/*  72 */     this._elementSerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */   public ObjectArraySerializer(ObjectArraySerializer paramObjectArraySerializer, TypeSerializer paramTypeSerializer)
/*     */   {
/*  77 */     super(paramObjectArraySerializer);
/*  78 */     this._elementType = paramObjectArraySerializer._elementType;
/*  79 */     this._valueTypeSerializer = paramTypeSerializer;
/*  80 */     this._staticTyping = paramObjectArraySerializer._staticTyping;
/*  81 */     this._dynamicSerializers = paramObjectArraySerializer._dynamicSerializers;
/*  82 */     this._elementSerializer = paramObjectArraySerializer._elementSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ObjectArraySerializer(ObjectArraySerializer paramObjectArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  89 */     super(paramObjectArraySerializer, paramBeanProperty);
/*  90 */     this._elementType = paramObjectArraySerializer._elementType;
/*  91 */     this._valueTypeSerializer = paramTypeSerializer;
/*  92 */     this._staticTyping = paramObjectArraySerializer._staticTyping;
/*  93 */     this._dynamicSerializers = paramObjectArraySerializer._dynamicSerializers;
/*  94 */     this._elementSerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */   {
/* 100 */     return new ObjectArraySerializer(this._elementType, this._staticTyping, paramTypeSerializer, this._elementSerializer);
/*     */   }
/*     */   
/*     */   public ObjectArraySerializer withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 105 */     if ((this._property == paramBeanProperty) && (paramJsonSerializer == this._elementSerializer) && (this._valueTypeSerializer == paramTypeSerializer)) {
/* 106 */       return this;
/*     */     }
/* 108 */     return new ObjectArraySerializer(this, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
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
/*     */   public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 122 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 123 */     if (localTypeSerializer != null) {
/* 124 */       localTypeSerializer = localTypeSerializer.forProperty(paramBeanProperty);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 130 */     JsonSerializer localJsonSerializer = null;
/*     */     
/* 132 */     if (paramBeanProperty != null) {
/* 133 */       AnnotatedMember localAnnotatedMember = paramBeanProperty.getMember();
/* 134 */       if (localAnnotatedMember != null) {
/* 135 */         Object localObject = paramSerializerProvider.getAnnotationIntrospector().findContentSerializer(localAnnotatedMember);
/* 136 */         if (localObject != null) {
/* 137 */           localJsonSerializer = paramSerializerProvider.serializerInstance(localAnnotatedMember, localObject);
/*     */         }
/*     */       }
/*     */     }
/* 141 */     if (localJsonSerializer == null) {
/* 142 */       localJsonSerializer = this._elementSerializer;
/*     */     }
/*     */     
/* 145 */     localJsonSerializer = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, localJsonSerializer);
/* 146 */     if (localJsonSerializer == null)
/*     */     {
/*     */ 
/* 149 */       if ((this._elementType != null) && (
/* 150 */         (this._staticTyping) || (hasContentTypeAnnotation(paramSerializerProvider, paramBeanProperty)))) {
/* 151 */         localJsonSerializer = paramSerializerProvider.findValueSerializer(this._elementType, paramBeanProperty);
/*     */       }
/*     */     }
/* 154 */     else if ((localJsonSerializer instanceof ContextualSerializer)) {
/* 155 */       localJsonSerializer = ((ContextualSerializer)localJsonSerializer).createContextual(paramSerializerProvider, paramBeanProperty);
/*     */     }
/* 157 */     return withResolved(paramBeanProperty, localTypeSerializer, localJsonSerializer);
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
/* 168 */     return this._elementType;
/*     */   }
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer()
/*     */   {
/* 173 */     return this._elementSerializer;
/*     */   }
/*     */   
/*     */   public boolean isEmpty(Object[] paramArrayOfObject)
/*     */   {
/* 178 */     return (paramArrayOfObject == null) || (paramArrayOfObject.length == 0);
/*     */   }
/*     */   
/*     */   public boolean hasSingleElement(Object[] paramArrayOfObject)
/*     */   {
/* 183 */     return paramArrayOfObject.length == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeContents(Object[] paramArrayOfObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 196 */     int i = paramArrayOfObject.length;
/* 197 */     if (i == 0) {
/* 198 */       return;
/*     */     }
/* 200 */     if (this._elementSerializer != null) {
/* 201 */       serializeContentsUsing(paramArrayOfObject, paramJsonGenerator, paramSerializerProvider, this._elementSerializer);
/* 202 */       return;
/*     */     }
/* 204 */     if (this._valueTypeSerializer != null) {
/* 205 */       serializeTypedContents(paramArrayOfObject, paramJsonGenerator, paramSerializerProvider);
/* 206 */       return;
/*     */     }
/* 208 */     int j = 0;
/* 209 */     Object localObject1 = null;
/*     */     try {
/* 211 */       PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/* 212 */       for (; j < i; j++) {
/* 213 */         localObject1 = paramArrayOfObject[j];
/* 214 */         if (localObject1 == null) {
/* 215 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         }
/*     */         else {
/* 218 */           localObject2 = localObject1.getClass();
/* 219 */           JsonSerializer localJsonSerializer = localPropertySerializerMap.serializerFor((Class)localObject2);
/* 220 */           if (localJsonSerializer == null)
/*     */           {
/* 222 */             if (this._elementType.hasGenericTypes()) {
/* 223 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, paramSerializerProvider.constructSpecializedType(this._elementType, (Class)localObject2), paramSerializerProvider);
/*     */             }
/*     */             else {
/* 226 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, (Class)localObject2, paramSerializerProvider);
/*     */             }
/*     */           }
/* 229 */           localJsonSerializer.serialize(localObject1, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */       }
/* 232 */     } catch (IOException localIOException) { throw localIOException;
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */ 
/* 239 */       Object localObject2 = localException;
/* 240 */       while (((localObject2 instanceof InvocationTargetException)) && (((Throwable)localObject2).getCause() != null)) {
/* 241 */         localObject2 = ((Throwable)localObject2).getCause();
/*     */       }
/* 243 */       if ((localObject2 instanceof Error)) {
/* 244 */         throw ((Error)localObject2);
/*     */       }
/* 246 */       throw JsonMappingException.wrapWithPath((Throwable)localObject2, localObject1, j);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void serializeContentsUsing(Object[] paramArrayOfObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 254 */     int i = paramArrayOfObject.length;
/* 255 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/*     */     
/* 257 */     int j = 0;
/* 258 */     Object localObject1 = null;
/*     */     try {
/* 260 */       for (; j < i; j++) {
/* 261 */         localObject1 = paramArrayOfObject[j];
/* 262 */         if (localObject1 == null) {
/* 263 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */ 
/*     */         }
/* 266 */         else if (localTypeSerializer == null) {
/* 267 */           paramJsonSerializer.serialize(localObject1, paramJsonGenerator, paramSerializerProvider);
/*     */         } else {
/* 269 */           paramJsonSerializer.serializeWithType(localObject1, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */         }
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 273 */       throw localIOException;
/*     */     } catch (Exception localException) {
/* 275 */       Object localObject2 = localException;
/* 276 */       while (((localObject2 instanceof InvocationTargetException)) && (((Throwable)localObject2).getCause() != null)) {
/* 277 */         localObject2 = ((Throwable)localObject2).getCause();
/*     */       }
/* 279 */       if ((localObject2 instanceof Error)) {
/* 280 */         throw ((Error)localObject2);
/*     */       }
/* 282 */       throw JsonMappingException.wrapWithPath((Throwable)localObject2, localObject1, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void serializeTypedContents(Object[] paramArrayOfObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 289 */     int i = paramArrayOfObject.length;
/* 290 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 291 */     int j = 0;
/* 292 */     Object localObject1 = null;
/*     */     try {
/* 294 */       PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/* 295 */       for (; j < i; j++) {
/* 296 */         localObject1 = paramArrayOfObject[j];
/* 297 */         if (localObject1 == null) {
/* 298 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         }
/*     */         else {
/* 301 */           localObject2 = localObject1.getClass();
/* 302 */           JsonSerializer localJsonSerializer = localPropertySerializerMap.serializerFor((Class)localObject2);
/* 303 */           if (localJsonSerializer == null) {
/* 304 */             localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, (Class)localObject2, paramSerializerProvider);
/*     */           }
/* 306 */           localJsonSerializer.serializeWithType(localObject1, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */         }
/*     */       }
/* 309 */     } catch (IOException localIOException) { throw localIOException;
/*     */     } catch (Exception localException) {
/* 311 */       Object localObject2 = localException;
/* 312 */       while (((localObject2 instanceof InvocationTargetException)) && (((Throwable)localObject2).getCause() != null)) {
/* 313 */         localObject2 = ((Throwable)localObject2).getCause();
/*     */       }
/* 315 */       if ((localObject2 instanceof Error)) {
/* 316 */         throw ((Error)localObject2);
/*     */       }
/* 318 */       throw JsonMappingException.wrapWithPath((Throwable)localObject2, localObject1, j);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/* 327 */     ObjectNode localObjectNode = createSchemaNode("array", true);
/* 328 */     if (paramType != null) {
/* 329 */       JavaType localJavaType = paramSerializerProvider.constructType(paramType);
/* 330 */       if (localJavaType.isArrayType()) {
/* 331 */         Class localClass = ((ArrayType)localJavaType).getContentType().getRawClass();
/*     */         
/* 333 */         if (localClass == Object.class) {
/* 334 */           localObjectNode.put("items", JsonSchema.getDefaultSchemaNode());
/*     */         } else {
/* 336 */           JsonSerializer localJsonSerializer = paramSerializerProvider.findValueSerializer(localClass, this._property);
/* 337 */           JsonNode localJsonNode = (localJsonSerializer instanceof SchemaAware) ? ((SchemaAware)localJsonSerializer).getSchema(paramSerializerProvider, null) : JsonSchema.getDefaultSchemaNode();
/*     */           
/*     */ 
/* 340 */           localObjectNode.put("items", localJsonNode);
/*     */         }
/*     */       }
/*     */     }
/* 344 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 351 */     JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 352 */     if (localJsonArrayFormatVisitor != null) {
/* 353 */       TypeFactory localTypeFactory = paramJsonFormatVisitorWrapper.getProvider().getTypeFactory();
/* 354 */       JavaType localJavaType = localTypeFactory.moreSpecificType(this._elementType, paramJavaType.getContentType());
/* 355 */       if (localJavaType == null) {
/* 356 */         throw new JsonMappingException("Could not resolve type");
/*     */       }
/* 358 */       JsonSerializer localJsonSerializer = this._elementSerializer;
/* 359 */       if (localJsonSerializer == null) {
/* 360 */         localJsonSerializer = paramJsonFormatVisitorWrapper.getProvider().findValueSerializer(localJavaType, this._property);
/*     */       }
/* 362 */       localJsonArrayFormatVisitor.itemsFormat(localJsonSerializer, localJavaType);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 369 */     PropertySerializerMap.SerializerAndMapResult localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(paramClass, paramSerializerProvider, this._property);
/*     */     
/* 371 */     if (paramPropertySerializerMap != localSerializerAndMapResult.map) {
/* 372 */       this._dynamicSerializers = localSerializerAndMapResult.map;
/*     */     }
/* 374 */     return localSerializerAndMapResult.serializer;
/*     */   }
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, JavaType paramJavaType, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 380 */     PropertySerializerMap.SerializerAndMapResult localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(paramJavaType, paramSerializerProvider, this._property);
/*     */     
/* 382 */     if (paramPropertySerializerMap != localSerializerAndMapResult.map) {
/* 383 */       this._dynamicSerializers = localSerializerAndMapResult.map;
/*     */     }
/* 385 */     return localSerializerAndMapResult.serializer;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/ObjectArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */