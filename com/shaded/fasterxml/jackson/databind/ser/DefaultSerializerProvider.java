/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.WritableObjectId;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.RootNameLookup;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.IdentityHashMap;
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
/*     */ public abstract class DefaultSerializerProvider
/*     */   extends SerializerProvider
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient IdentityHashMap<Object, WritableObjectId> _seenObjectIds;
/*     */   protected transient ArrayList<ObjectIdGenerator<?>> _objectIdGenerators;
/*     */   
/*     */   protected DefaultSerializerProvider() {}
/*     */   
/*     */   protected DefaultSerializerProvider(SerializerProvider paramSerializerProvider, SerializationConfig paramSerializationConfig, SerializerFactory paramSerializerFactory)
/*     */   {
/*  63 */     super(paramSerializerProvider, paramSerializationConfig, paramSerializerFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract DefaultSerializerProvider createInstance(SerializationConfig paramSerializationConfig, SerializerFactory paramSerializerFactory);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeValue(JsonGenerator paramJsonGenerator, Object paramObject)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*     */     JsonSerializer localJsonSerializer;
/*     */     
/*     */ 
/*     */ 
/*     */     boolean bool;
/*     */     
/*     */ 
/*     */ 
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*  91 */     if (paramObject == null) {
/*  92 */       localJsonSerializer = getDefaultNullValueSerializer();
/*  93 */       bool = false;
/*     */     } else {
/*  95 */       Class localClass = paramObject.getClass();
/*     */       
/*  97 */       localJsonSerializer = findTypedValueSerializer(localClass, true, null);
/*     */       
/*     */ 
/* 100 */       str = this._config.getRootName();
/* 101 */       if (str == null)
/*     */       {
/* 103 */         bool = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
/* 104 */         if (bool) {
/* 105 */           paramJsonGenerator.writeStartObject();
/* 106 */           paramJsonGenerator.writeFieldName(this._rootNames.findRootName(paramObject.getClass(), this._config));
/*     */         }
/* 108 */       } else if (str.length() == 0) {
/* 109 */         bool = false;
/*     */       }
/*     */       else {
/* 112 */         bool = true;
/* 113 */         paramJsonGenerator.writeStartObject();
/* 114 */         paramJsonGenerator.writeFieldName(str);
/*     */       }
/*     */     }
/*     */     try {
/* 118 */       localJsonSerializer.serialize(paramObject, paramJsonGenerator, this);
/* 119 */       if (bool) {
/* 120 */         paramJsonGenerator.writeEndObject();
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 123 */       throw localIOException;
/*     */     } catch (Exception localException) {
/* 125 */       str = localException.getMessage();
/* 126 */       if (str == null) {
/* 127 */         str = "[no message for " + localException.getClass().getName() + "]";
/*     */       }
/* 129 */       throw new JsonMappingException(str, localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeValue(JsonGenerator paramJsonGenerator, Object paramObject, JavaType paramJavaType)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*     */     JsonSerializer localJsonSerializer;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean bool;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 150 */     if (paramObject == null) {
/* 151 */       localJsonSerializer = getDefaultNullValueSerializer();
/* 152 */       bool = false;
/*     */     }
/*     */     else {
/* 155 */       if (!paramJavaType.getRawClass().isAssignableFrom(paramObject.getClass())) {
/* 156 */         _reportIncompatibleRootType(paramObject, paramJavaType);
/*     */       }
/*     */       
/* 159 */       localJsonSerializer = findTypedValueSerializer(paramJavaType, true, null);
/*     */       
/* 161 */       bool = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
/* 162 */       if (bool) {
/* 163 */         paramJsonGenerator.writeStartObject();
/* 164 */         paramJsonGenerator.writeFieldName(this._rootNames.findRootName(paramJavaType, this._config));
/*     */       }
/*     */     }
/*     */     try {
/* 168 */       localJsonSerializer.serialize(paramObject, paramJsonGenerator, this);
/* 169 */       if (bool) {
/* 170 */         paramJsonGenerator.writeEndObject();
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 173 */       throw localIOException;
/*     */     } catch (Exception localException) {
/* 175 */       String str = localException.getMessage();
/* 176 */       if (str == null) {
/* 177 */         str = "[no message for " + localException.getClass().getName() + "]";
/*     */       }
/* 179 */       throw new JsonMappingException(str, localException);
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
/*     */   public void serializeValue(JsonGenerator paramJsonGenerator, Object paramObject, JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*     */     boolean bool;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */     if (paramObject == null) {
/* 202 */       paramJsonSerializer = getDefaultNullValueSerializer();
/* 203 */       bool = false;
/*     */     }
/*     */     else {
/* 206 */       if ((paramJavaType != null) && 
/* 207 */         (!paramJavaType.getRawClass().isAssignableFrom(paramObject.getClass()))) {
/* 208 */         _reportIncompatibleRootType(paramObject, paramJavaType);
/*     */       }
/*     */       
/*     */ 
/* 212 */       if (paramJsonSerializer == null) {
/* 213 */         paramJsonSerializer = findTypedValueSerializer(paramJavaType, true, null);
/*     */       }
/* 215 */       bool = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
/* 216 */       if (bool) {
/* 217 */         paramJsonGenerator.writeStartObject();
/* 218 */         paramJsonGenerator.writeFieldName(this._rootNames.findRootName(paramJavaType, this._config));
/*     */       }
/*     */     }
/*     */     try {
/* 222 */       paramJsonSerializer.serialize(paramObject, paramJsonGenerator, this);
/* 223 */       if (bool) {
/* 224 */         paramJsonGenerator.writeEndObject();
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 227 */       throw localIOException;
/*     */     } catch (Exception localException) {
/* 229 */       String str = localException.getMessage();
/* 230 */       if (str == null) {
/* 231 */         str = "[no message for " + localException.getClass().getName() + "]";
/*     */       }
/* 233 */       throw new JsonMappingException(str, localException);
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
/*     */   public JsonSchema generateJsonSchema(Class<?> paramClass)
/*     */     throws JsonMappingException
/*     */   {
/* 248 */     if (paramClass == null) {
/* 249 */       throw new IllegalArgumentException("A class must be provided");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 254 */     JsonSerializer localJsonSerializer = findValueSerializer(paramClass, null);
/* 255 */     JsonNode localJsonNode = (localJsonSerializer instanceof SchemaAware) ? ((SchemaAware)localJsonSerializer).getSchema(this, null) : JsonSchema.getDefaultSchemaNode();
/*     */     
/* 257 */     if (!(localJsonNode instanceof ObjectNode)) {
/* 258 */       throw new IllegalArgumentException("Class " + paramClass.getName() + " would not be serialized as a JSON object and therefore has no schema");
/*     */     }
/*     */     
/* 261 */     return new JsonSchema((ObjectNode)localJsonNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JavaType paramJavaType, JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper)
/*     */     throws JsonMappingException
/*     */   {
/* 274 */     if (paramJavaType == null) {
/* 275 */       throw new IllegalArgumentException("A class must be provided");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 280 */     paramJsonFormatVisitorWrapper.setProvider(this);
/* 281 */     findValueSerializer(paramJavaType, null).acceptJsonFormatVisitor(paramJsonFormatVisitorWrapper, paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasSerializerFor(Class<?> paramClass)
/*     */   {
/*     */     try
/*     */     {
/* 293 */       return _findExplicitUntypedSerializer(paramClass) != null;
/*     */     }
/*     */     catch (JsonMappingException localJsonMappingException) {}
/*     */     
/* 297 */     return false;
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
/*     */ 
/*     */ 
/*     */   public int cachedSerializersCount()
/*     */   {
/* 319 */     return this._serializerCache.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flushCachedSerializers()
/*     */   {
/* 329 */     this._serializerCache.flush();
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
/*     */   public WritableObjectId findObjectId(Object paramObject, ObjectIdGenerator<?> paramObjectIdGenerator)
/*     */   {
/* 342 */     if (this._seenObjectIds == null) {
/* 343 */       this._seenObjectIds = new IdentityHashMap();
/*     */     } else {
/* 345 */       localObject = (WritableObjectId)this._seenObjectIds.get(paramObject);
/* 346 */       if (localObject != null) {
/* 347 */         return (WritableObjectId)localObject;
/*     */       }
/*     */     }
/*     */     
/* 351 */     Object localObject = null;
/*     */     
/* 353 */     if (this._objectIdGenerators == null) {
/* 354 */       this._objectIdGenerators = new ArrayList(8);
/*     */     } else {
/* 356 */       int i = 0; for (int j = this._objectIdGenerators.size(); i < j; i++) {
/* 357 */         ObjectIdGenerator localObjectIdGenerator = (ObjectIdGenerator)this._objectIdGenerators.get(i);
/* 358 */         if (localObjectIdGenerator.canUseFor(paramObjectIdGenerator)) {
/* 359 */           localObject = localObjectIdGenerator;
/* 360 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 364 */     if (localObject == null) {
/* 365 */       localObject = paramObjectIdGenerator.newForSerialization(this);
/* 366 */       this._objectIdGenerators.add(localObject);
/*     */     }
/* 368 */     WritableObjectId localWritableObjectId = new WritableObjectId((ObjectIdGenerator)localObject);
/* 369 */     this._seenObjectIds.put(paramObject, localWritableObjectId);
/* 370 */     return localWritableObjectId;
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
/*     */   public JsonSerializer<Object> serializerInstance(Annotated paramAnnotated, Object paramObject)
/*     */     throws JsonMappingException
/*     */   {
/* 385 */     if (paramObject == null) {
/* 386 */       return null;
/*     */     }
/*     */     
/*     */     JsonSerializer localJsonSerializer;
/* 390 */     if ((paramObject instanceof JsonSerializer)) {
/* 391 */       localJsonSerializer = (JsonSerializer)paramObject;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 396 */       if (!(paramObject instanceof Class)) {
/* 397 */         throw new IllegalStateException("AnnotationIntrospector returned serializer definition of type " + paramObject.getClass().getName() + "; expected type JsonSerializer or Class<JsonSerializer> instead");
/*     */       }
/*     */       
/* 400 */       Class localClass = (Class)paramObject;
/*     */       
/* 402 */       if ((localClass == JsonSerializer.None.class) || (localClass == NoClass.class)) {
/* 403 */         return null;
/*     */       }
/* 405 */       if (!JsonSerializer.class.isAssignableFrom(localClass)) {
/* 406 */         throw new IllegalStateException("AnnotationIntrospector returned Class " + localClass.getName() + "; expected Class<JsonSerializer>");
/*     */       }
/*     */       
/* 409 */       HandlerInstantiator localHandlerInstantiator = this._config.getHandlerInstantiator();
/* 410 */       localJsonSerializer = localHandlerInstantiator == null ? null : localHandlerInstantiator.serializerInstance(this._config, paramAnnotated, localClass);
/* 411 */       if (localJsonSerializer == null) {
/* 412 */         localJsonSerializer = (JsonSerializer)ClassUtil.createInstance(localClass, this._config.canOverrideAccessModifiers());
/*     */       }
/*     */     }
/*     */     
/* 416 */     return _handleResolvable(localJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class Impl
/*     */     extends DefaultSerializerProvider
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public Impl() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Impl(SerializerProvider paramSerializerProvider, SerializationConfig paramSerializationConfig, SerializerFactory paramSerializerFactory)
/*     */     {
/* 437 */       super(paramSerializationConfig, paramSerializerFactory);
/*     */     }
/*     */     
/*     */ 
/*     */     public Impl createInstance(SerializationConfig paramSerializationConfig, SerializerFactory paramSerializerFactory)
/*     */     {
/* 443 */       return new Impl(this, paramSerializationConfig, paramSerializerFactory);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/DefaultSerializerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */