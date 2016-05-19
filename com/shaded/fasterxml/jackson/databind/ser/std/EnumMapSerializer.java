/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.EnumValues;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class EnumMapSerializer
/*     */   extends ContainerSerializer<EnumMap<? extends Enum<?>, ?>>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final boolean _staticTyping;
/*     */   protected final BeanProperty _property;
/*     */   protected final EnumValues _keyEnums;
/*     */   protected final JavaType _valueType;
/*     */   protected final JsonSerializer<Object> _valueSerializer;
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */   
/*     */   public EnumMapSerializer(JavaType paramJavaType, boolean paramBoolean, EnumValues paramEnumValues, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/*  70 */     super(EnumMap.class, false);
/*  71 */     this._property = null;
/*  72 */     this._staticTyping = ((paramBoolean) || ((paramJavaType != null) && (paramJavaType.isFinal())));
/*  73 */     this._valueType = paramJavaType;
/*  74 */     this._keyEnums = paramEnumValues;
/*  75 */     this._valueTypeSerializer = paramTypeSerializer;
/*  76 */     this._valueSerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumMapSerializer(EnumMapSerializer paramEnumMapSerializer, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  86 */     super(paramEnumMapSerializer);
/*  87 */     this._property = paramBeanProperty;
/*  88 */     this._staticTyping = paramEnumMapSerializer._staticTyping;
/*  89 */     this._valueType = paramEnumMapSerializer._valueType;
/*  90 */     this._keyEnums = paramEnumMapSerializer._keyEnums;
/*  91 */     this._valueTypeSerializer = paramEnumMapSerializer._valueTypeSerializer;
/*  92 */     this._valueSerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */   public EnumMapSerializer _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */   {
/*  97 */     return new EnumMapSerializer(this._valueType, this._staticTyping, this._keyEnums, paramTypeSerializer, this._valueSerializer);
/*     */   }
/*     */   
/*     */   public EnumMapSerializer withValueSerializer(BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer) {
/* 101 */     if ((this._property == paramBeanProperty) && (paramJsonSerializer == this._valueSerializer)) {
/* 102 */       return this;
/*     */     }
/* 104 */     return new EnumMapSerializer(this, paramBeanProperty, paramJsonSerializer);
/*     */   }
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
/* 116 */     JsonSerializer localJsonSerializer = null;
/*     */     
/* 118 */     if (paramBeanProperty != null) {
/* 119 */       AnnotatedMember localAnnotatedMember = paramBeanProperty.getMember();
/* 120 */       if (localAnnotatedMember != null) {
/* 121 */         Object localObject = paramSerializerProvider.getAnnotationIntrospector().findContentSerializer(localAnnotatedMember);
/* 122 */         if (localObject != null) {
/* 123 */           localJsonSerializer = paramSerializerProvider.serializerInstance(localAnnotatedMember, localObject);
/*     */         }
/*     */       }
/*     */     }
/* 127 */     if (localJsonSerializer == null) {
/* 128 */       localJsonSerializer = this._valueSerializer;
/*     */     }
/*     */     
/* 131 */     localJsonSerializer = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, localJsonSerializer);
/* 132 */     if (localJsonSerializer == null) {
/* 133 */       if (this._staticTyping) {
/* 134 */         return withValueSerializer(paramBeanProperty, paramSerializerProvider.findValueSerializer(this._valueType, paramBeanProperty));
/*     */       }
/* 136 */     } else if ((this._valueSerializer instanceof ContextualSerializer)) {
/* 137 */       localJsonSerializer = ((ContextualSerializer)localJsonSerializer).createContextual(paramSerializerProvider, paramBeanProperty);
/*     */     }
/* 139 */     if (localJsonSerializer != this._valueSerializer) {
/* 140 */       return withValueSerializer(paramBeanProperty, localJsonSerializer);
/*     */     }
/* 142 */     return this;
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
/* 153 */     return this._valueType;
/*     */   }
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer()
/*     */   {
/* 158 */     return this._valueSerializer;
/*     */   }
/*     */   
/*     */   public boolean isEmpty(EnumMap<? extends Enum<?>, ?> paramEnumMap)
/*     */   {
/* 163 */     return (paramEnumMap == null) || (paramEnumMap.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasSingleElement(EnumMap<? extends Enum<?>, ?> paramEnumMap)
/*     */   {
/* 168 */     return paramEnumMap.size() == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serialize(EnumMap<? extends Enum<?>, ?> paramEnumMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 181 */     paramJsonGenerator.writeStartObject();
/* 182 */     if (!paramEnumMap.isEmpty()) {
/* 183 */       serializeContents(paramEnumMap, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 185 */     paramJsonGenerator.writeEndObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void serializeWithType(EnumMap<? extends Enum<?>, ?> paramEnumMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 193 */     paramTypeSerializer.writeTypePrefixForObject(paramEnumMap, paramJsonGenerator);
/* 194 */     if (!paramEnumMap.isEmpty()) {
/* 195 */       serializeContents(paramEnumMap, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 197 */     paramTypeSerializer.writeTypeSuffixForObject(paramEnumMap, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   protected void serializeContents(EnumMap<? extends Enum<?>, ?> paramEnumMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 203 */     if (this._valueSerializer != null) {
/* 204 */       serializeContentsUsing(paramEnumMap, paramJsonGenerator, paramSerializerProvider, this._valueSerializer);
/* 205 */       return;
/*     */     }
/* 207 */     Object localObject1 = null;
/* 208 */     Object localObject2 = null;
/* 209 */     EnumValues localEnumValues = this._keyEnums;
/* 210 */     int i = !paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) ? 1 : 0;
/* 211 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/*     */     
/* 213 */     for (Map.Entry localEntry : paramEnumMap.entrySet()) {
/* 214 */       Object localObject3 = localEntry.getValue();
/* 215 */       if ((i == 0) || (localObject3 != null))
/*     */       {
/*     */ 
/*     */ 
/* 219 */         Enum localEnum = (Enum)localEntry.getKey();
/* 220 */         Object localObject4; if (localEnumValues == null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 226 */           localObject4 = (StdSerializer)paramSerializerProvider.findValueSerializer(localEnum.getDeclaringClass(), this._property);
/*     */           
/* 228 */           localEnumValues = ((EnumSerializer)localObject4).getEnumValues();
/*     */         }
/* 230 */         paramJsonGenerator.writeFieldName(localEnumValues.serializedValueFor(localEnum));
/* 231 */         if (localObject3 == null) {
/* 232 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         }
/*     */         else {
/* 235 */           localObject4 = localObject3.getClass();
/*     */           Object localObject5;
/* 237 */           if (localObject4 == localObject2) {
/* 238 */             localObject5 = localObject1;
/*     */           } else {
/* 240 */             localObject5 = paramSerializerProvider.findValueSerializer((Class)localObject4, this._property);
/* 241 */             localObject1 = localObject5;
/* 242 */             localObject2 = localObject4;
/*     */           }
/*     */           try {
/* 245 */             if (localTypeSerializer == null) {
/* 246 */               ((JsonSerializer)localObject5).serialize(localObject3, paramJsonGenerator, paramSerializerProvider);
/*     */             } else {
/* 248 */               ((JsonSerializer)localObject5).serializeWithType(localObject3, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */             }
/*     */           }
/*     */           catch (Exception localException) {
/* 252 */             wrapAndThrow(paramSerializerProvider, localException, paramEnumMap, ((Enum)localEntry.getKey()).name());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void serializeContentsUsing(EnumMap<? extends Enum<?>, ?> paramEnumMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<Object> paramJsonSerializer) throws IOException, JsonGenerationException
/*     */   {
/* 261 */     EnumValues localEnumValues = this._keyEnums;
/* 262 */     int i = !paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) ? 1 : 0;
/* 263 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/*     */     
/* 265 */     for (Map.Entry localEntry : paramEnumMap.entrySet()) {
/* 266 */       Object localObject = localEntry.getValue();
/* 267 */       if ((i == 0) || (localObject != null))
/*     */       {
/*     */ 
/* 270 */         Enum localEnum = (Enum)localEntry.getKey();
/* 271 */         if (localEnumValues == null)
/*     */         {
/* 273 */           StdSerializer localStdSerializer = (StdSerializer)paramSerializerProvider.findValueSerializer(localEnum.getDeclaringClass(), this._property);
/*     */           
/* 275 */           localEnumValues = ((EnumSerializer)localStdSerializer).getEnumValues();
/*     */         }
/* 277 */         paramJsonGenerator.writeFieldName(localEnumValues.serializedValueFor(localEnum));
/* 278 */         if (localObject == null) {
/* 279 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/*     */           try
/*     */           {
/* 283 */             if (localTypeSerializer == null) {
/* 284 */               paramJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */             } else {
/* 286 */               paramJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */             }
/*     */           } catch (Exception localException) {
/* 289 */             wrapAndThrow(paramSerializerProvider, localException, paramEnumMap, ((Enum)localEntry.getKey()).name());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/* 299 */     ObjectNode localObjectNode1 = createSchemaNode("object", true);
/* 300 */     if ((paramType instanceof ParameterizedType)) {
/* 301 */       Type[] arrayOfType = ((ParameterizedType)paramType).getActualTypeArguments();
/* 302 */       if (arrayOfType.length == 2) {
/* 303 */         JavaType localJavaType1 = paramSerializerProvider.constructType(arrayOfType[0]);
/* 304 */         JavaType localJavaType2 = paramSerializerProvider.constructType(arrayOfType[1]);
/* 305 */         ObjectNode localObjectNode2 = JsonNodeFactory.instance.objectNode();
/* 306 */         Class localClass = localJavaType1.getRawClass();
/* 307 */         for (Enum localEnum : (Enum[])localClass.getEnumConstants()) {
/* 308 */           JsonSerializer localJsonSerializer = paramSerializerProvider.findValueSerializer(localJavaType2.getRawClass(), this._property);
/* 309 */           JsonNode localJsonNode = (localJsonSerializer instanceof SchemaAware) ? ((SchemaAware)localJsonSerializer).getSchema(paramSerializerProvider, null) : JsonSchema.getDefaultSchemaNode();
/*     */           
/*     */ 
/* 312 */           localObjectNode2.put(paramSerializerProvider.getConfig().getAnnotationIntrospector().findEnumValue(localEnum), localJsonNode);
/*     */         }
/* 314 */         localObjectNode1.put("properties", localObjectNode2);
/*     */       }
/*     */     }
/* 317 */     return localObjectNode1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 329 */     if (paramJsonFormatVisitorWrapper == null) {
/* 330 */       return;
/*     */     }
/* 332 */     JsonObjectFormatVisitor localJsonObjectFormatVisitor = paramJsonFormatVisitorWrapper.expectObjectFormat(paramJavaType);
/* 333 */     if (localJsonObjectFormatVisitor == null) {
/* 334 */       return;
/*     */     }
/* 336 */     JavaType localJavaType = paramJavaType.containedType(1);
/* 337 */     JsonSerializer localJsonSerializer = this._valueSerializer;
/* 338 */     if ((localJsonSerializer == null) && (localJavaType != null)) {
/* 339 */       localJsonSerializer = paramJsonFormatVisitorWrapper.getProvider().findValueSerializer(localJavaType, this._property);
/*     */     }
/* 341 */     if (localJavaType == null) {
/* 342 */       localJavaType = paramJsonFormatVisitorWrapper.getProvider().constructType(Object.class);
/*     */     }
/* 344 */     EnumValues localEnumValues = this._keyEnums;
/* 345 */     Object localObject2; if (localEnumValues == null) {
/* 346 */       localObject1 = paramJavaType.containedType(0);
/* 347 */       if (localObject1 == null) {
/* 348 */         throw new IllegalStateException("Can not resolve Enum type of EnumMap: " + paramJavaType);
/*     */       }
/* 350 */       localObject2 = localObject1 == null ? null : paramJsonFormatVisitorWrapper.getProvider().findValueSerializer((JavaType)localObject1, this._property);
/*     */       
/* 352 */       if (!(localObject2 instanceof EnumSerializer)) {
/* 353 */         throw new IllegalStateException("Can not resolve Enum type of EnumMap: " + paramJavaType);
/*     */       }
/* 355 */       localEnumValues = ((EnumSerializer)localObject2).getEnumValues();
/*     */     }
/* 357 */     for (Object localObject1 = localEnumValues.internalMap().entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/* 358 */       String str = ((SerializedString)((Map.Entry)localObject2).getValue()).getValue();
/*     */       
/* 360 */       if (localJsonSerializer == null) {
/* 361 */         localJsonSerializer = paramJsonFormatVisitorWrapper.getProvider().findValueSerializer(((Map.Entry)localObject2).getKey().getClass(), this._property);
/*     */       }
/* 363 */       localJsonObjectFormatVisitor.property(str, localJsonSerializer, localJavaType);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/EnumMapSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */