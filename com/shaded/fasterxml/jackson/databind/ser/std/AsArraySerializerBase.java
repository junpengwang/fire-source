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
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
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
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
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
/*     */ public abstract class AsArraySerializerBase<T>
/*     */   extends ContainerSerializer<T>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final boolean _staticTyping;
/*     */   protected final JavaType _elementType;
/*     */   protected final TypeSerializer _valueTypeSerializer;
/*     */   protected final JsonSerializer<Object> _elementSerializer;
/*     */   protected final BeanProperty _property;
/*     */   protected PropertySerializerMap _dynamicSerializers;
/*     */   
/*     */   protected AsArraySerializerBase(Class<?> paramClass, JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/*  65 */     super(paramClass, false);
/*  66 */     this._elementType = paramJavaType;
/*     */     
/*  68 */     this._staticTyping = ((paramBoolean) || ((paramJavaType != null) && (paramJavaType.isFinal())));
/*  69 */     this._valueTypeSerializer = paramTypeSerializer;
/*  70 */     this._property = paramBeanProperty;
/*  71 */     this._elementSerializer = paramJsonSerializer;
/*  72 */     this._dynamicSerializers = PropertySerializerMap.emptyMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected AsArraySerializerBase(AsArraySerializerBase<?> paramAsArraySerializerBase, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  79 */     super(paramAsArraySerializerBase);
/*  80 */     this._elementType = paramAsArraySerializerBase._elementType;
/*  81 */     this._staticTyping = paramAsArraySerializerBase._staticTyping;
/*  82 */     this._valueTypeSerializer = paramTypeSerializer;
/*  83 */     this._property = paramBeanProperty;
/*  84 */     this._elementSerializer = paramJsonSerializer;
/*  85 */     this._dynamicSerializers = paramAsArraySerializerBase._dynamicSerializers;
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
/*     */   public abstract AsArraySerializerBase<T> withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer);
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
/* 108 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 109 */     if (localTypeSerializer != null) {
/* 110 */       localTypeSerializer = localTypeSerializer.forProperty(paramBeanProperty);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
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
/* 128 */       localJsonSerializer = this._elementSerializer;
/*     */     }
/*     */     
/* 131 */     localJsonSerializer = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, localJsonSerializer);
/* 132 */     if (localJsonSerializer == null) {
/* 133 */       if (localJsonSerializer == null)
/*     */       {
/*     */ 
/* 136 */         if ((this._elementType != null) && (
/* 137 */           (this._staticTyping) || (hasContentTypeAnnotation(paramSerializerProvider, paramBeanProperty)))) {
/* 138 */           localJsonSerializer = paramSerializerProvider.findValueSerializer(this._elementType, paramBeanProperty);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 143 */     else if ((localJsonSerializer instanceof ContextualSerializer)) {
/* 144 */       localJsonSerializer = ((ContextualSerializer)localJsonSerializer).createContextual(paramSerializerProvider, paramBeanProperty);
/*     */     }
/*     */     
/* 147 */     if ((localJsonSerializer != this._elementSerializer) || (paramBeanProperty != this._property) || (this._valueTypeSerializer != localTypeSerializer)) {
/* 148 */       return withResolved(paramBeanProperty, localTypeSerializer, localJsonSerializer);
/*     */     }
/* 150 */     return this;
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
/* 161 */     return this._elementType;
/*     */   }
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer()
/*     */   {
/* 166 */     return this._elementSerializer;
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
/*     */   public final void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 180 */     if ((paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && (hasSingleElement(paramT)))
/*     */     {
/* 182 */       serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
/* 183 */       return;
/*     */     }
/* 185 */     paramJsonGenerator.writeStartArray();
/* 186 */     serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
/* 187 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 196 */     paramTypeSerializer.writeTypePrefixForArray(paramT, paramJsonGenerator);
/* 197 */     serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
/* 198 */     paramTypeSerializer.writeTypeSuffixForArray(paramT, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void serializeContents(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/* 214 */     ObjectNode localObjectNode = createSchemaNode("array", true);
/* 215 */     JavaType localJavaType = null;
/* 216 */     Object localObject1; Object localObject2; if (paramType != null) {
/* 217 */       localObject1 = paramSerializerProvider.constructType(paramType);
/* 218 */       localJavaType = ((JavaType)localObject1).getContentType();
/* 219 */       if ((localJavaType == null) && 
/* 220 */         ((paramType instanceof ParameterizedType))) {
/* 221 */         localObject2 = ((ParameterizedType)paramType).getActualTypeArguments();
/* 222 */         if (localObject2.length == 1) {
/* 223 */           localJavaType = paramSerializerProvider.constructType(localObject2[0]);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 228 */     if ((localJavaType == null) && (this._elementType != null)) {
/* 229 */       localJavaType = this._elementType;
/*     */     }
/* 231 */     if (localJavaType != null) {
/* 232 */       localObject1 = null;
/*     */       
/* 234 */       if (localJavaType.getRawClass() != Object.class) {
/* 235 */         localObject2 = paramSerializerProvider.findValueSerializer(localJavaType, this._property);
/* 236 */         if ((localObject2 instanceof SchemaAware)) {
/* 237 */           localObject1 = ((SchemaAware)localObject2).getSchema(paramSerializerProvider, null);
/*     */         }
/*     */       }
/* 240 */       if (localObject1 == null) {
/* 241 */         localObject1 = JsonSchema.getDefaultSchemaNode();
/*     */       }
/* 243 */       localObjectNode.put("items", (JsonNode)localObject1);
/*     */     }
/* 245 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 252 */     JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper == null ? null : paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 253 */     if (localJsonArrayFormatVisitor != null) {
/* 254 */       TypeFactory localTypeFactory = paramJsonFormatVisitorWrapper.getProvider().getTypeFactory();
/* 255 */       JavaType localJavaType = localTypeFactory.moreSpecificType(this._elementType, paramJavaType.getContentType());
/* 256 */       if (localJavaType == null) {
/* 257 */         throw new JsonMappingException("Could not resolve type");
/*     */       }
/* 259 */       JsonSerializer localJsonSerializer = this._elementSerializer;
/* 260 */       if (localJsonSerializer == null) {
/* 261 */         localJsonSerializer = paramJsonFormatVisitorWrapper.getProvider().findValueSerializer(localJavaType, this._property);
/*     */       }
/* 263 */       localJsonArrayFormatVisitor.itemsFormat(localJsonSerializer, localJavaType);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 270 */     PropertySerializerMap.SerializerAndMapResult localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(paramClass, paramSerializerProvider, this._property);
/*     */     
/* 272 */     if (paramPropertySerializerMap != localSerializerAndMapResult.map) {
/* 273 */       this._dynamicSerializers = localSerializerAndMapResult.map;
/*     */     }
/* 275 */     return localSerializerAndMapResult.serializer;
/*     */   }
/*     */   
/*     */   protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, JavaType paramJavaType, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 281 */     PropertySerializerMap.SerializerAndMapResult localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(paramJavaType, paramSerializerProvider, this._property);
/* 282 */     if (paramPropertySerializerMap != localSerializerAndMapResult.map) {
/* 283 */       this._dynamicSerializers = localSerializerAndMapResult.map;
/*     */     }
/* 285 */     return localSerializerAndMapResult.serializer;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/AsArraySerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */