/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.InjectableValues;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.LinkedHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DefaultDeserializationContext
/*     */   extends DeserializationContext
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient LinkedHashMap<ObjectIdGenerator.IdKey, ReadableObjectId> _objectIds;
/*     */   
/*     */   protected DefaultDeserializationContext(DeserializerFactory paramDeserializerFactory, DeserializerCache paramDeserializerCache)
/*     */   {
/*  39 */     super(paramDeserializerFactory, paramDeserializerCache);
/*     */   }
/*     */   
/*     */   protected DefaultDeserializationContext(DefaultDeserializationContext paramDefaultDeserializationContext, DeserializationConfig paramDeserializationConfig, JsonParser paramJsonParser, InjectableValues paramInjectableValues)
/*     */   {
/*  44 */     super(paramDefaultDeserializationContext, paramDeserializationConfig, paramJsonParser, paramInjectableValues);
/*     */   }
/*     */   
/*     */   protected DefaultDeserializationContext(DefaultDeserializationContext paramDefaultDeserializationContext, DeserializerFactory paramDeserializerFactory)
/*     */   {
/*  49 */     super(paramDefaultDeserializationContext, paramDeserializerFactory);
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
/*     */   public ReadableObjectId findObjectId(Object paramObject, ObjectIdGenerator<?> paramObjectIdGenerator)
/*     */   {
/*  62 */     ObjectIdGenerator.IdKey localIdKey = paramObjectIdGenerator.key(paramObject);
/*  63 */     if (this._objectIds == null) {
/*  64 */       this._objectIds = new LinkedHashMap();
/*     */     } else {
/*  66 */       localReadableObjectId = (ReadableObjectId)this._objectIds.get(localIdKey);
/*  67 */       if (localReadableObjectId != null) {
/*  68 */         return localReadableObjectId;
/*     */       }
/*     */     }
/*  71 */     ReadableObjectId localReadableObjectId = new ReadableObjectId(paramObject);
/*  72 */     this._objectIds.put(localIdKey, localReadableObjectId);
/*  73 */     return localReadableObjectId;
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
/*     */   public JsonDeserializer<Object> deserializerInstance(Annotated paramAnnotated, Object paramObject)
/*     */     throws JsonMappingException
/*     */   {
/*  88 */     if (paramObject == null) {
/*  89 */       return null;
/*     */     }
/*     */     
/*     */     JsonDeserializer localJsonDeserializer;
/*  93 */     if ((paramObject instanceof JsonDeserializer)) {
/*  94 */       localJsonDeserializer = (JsonDeserializer)paramObject;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*  99 */       if (!(paramObject instanceof Class)) {
/* 100 */         throw new IllegalStateException("AnnotationIntrospector returned deserializer definition of type " + paramObject.getClass().getName() + "; expected type JsonDeserializer or Class<JsonDeserializer> instead");
/*     */       }
/* 102 */       Class localClass = (Class)paramObject;
/*     */       
/* 104 */       if ((localClass == JsonDeserializer.None.class) || (localClass == NoClass.class)) {
/* 105 */         return null;
/*     */       }
/* 107 */       if (!JsonDeserializer.class.isAssignableFrom(localClass)) {
/* 108 */         throw new IllegalStateException("AnnotationIntrospector returned Class " + localClass.getName() + "; expected Class<JsonDeserializer>");
/*     */       }
/* 110 */       HandlerInstantiator localHandlerInstantiator = this._config.getHandlerInstantiator();
/* 111 */       localJsonDeserializer = localHandlerInstantiator == null ? null : localHandlerInstantiator.deserializerInstance(this._config, paramAnnotated, localClass);
/* 112 */       if (localJsonDeserializer == null) {
/* 113 */         localJsonDeserializer = (JsonDeserializer)ClassUtil.createInstance(localClass, this._config.canOverrideAccessModifiers());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 118 */     if ((localJsonDeserializer instanceof ResolvableDeserializer)) {
/* 119 */       ((ResolvableDeserializer)localJsonDeserializer).resolve(this);
/*     */     }
/* 121 */     return localJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final KeyDeserializer keyDeserializerInstance(Annotated paramAnnotated, Object paramObject)
/*     */     throws JsonMappingException
/*     */   {
/* 129 */     if (paramObject == null) {
/* 130 */       return null;
/*     */     }
/*     */     
/*     */     KeyDeserializer localKeyDeserializer;
/*     */     
/* 135 */     if ((paramObject instanceof KeyDeserializer)) {
/* 136 */       localKeyDeserializer = (KeyDeserializer)paramObject;
/*     */     } else {
/* 138 */       if (!(paramObject instanceof Class)) {
/* 139 */         throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + paramObject.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
/*     */       }
/*     */       
/*     */ 
/* 143 */       Class localClass = (Class)paramObject;
/*     */       
/* 145 */       if ((localClass == KeyDeserializer.None.class) || (localClass == NoClass.class)) {
/* 146 */         return null;
/*     */       }
/* 148 */       if (!KeyDeserializer.class.isAssignableFrom(localClass)) {
/* 149 */         throw new IllegalStateException("AnnotationIntrospector returned Class " + localClass.getName() + "; expected Class<KeyDeserializer>");
/*     */       }
/*     */       
/* 152 */       HandlerInstantiator localHandlerInstantiator = this._config.getHandlerInstantiator();
/* 153 */       localKeyDeserializer = localHandlerInstantiator == null ? null : localHandlerInstantiator.keyDeserializerInstance(this._config, paramAnnotated, localClass);
/* 154 */       if (localKeyDeserializer == null) {
/* 155 */         localKeyDeserializer = (KeyDeserializer)ClassUtil.createInstance(localClass, this._config.canOverrideAccessModifiers());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 160 */     if ((localKeyDeserializer instanceof ResolvableDeserializer)) {
/* 161 */       ((ResolvableDeserializer)localKeyDeserializer).resolve(this);
/*     */     }
/* 163 */     return localKeyDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract DefaultDeserializationContext with(DeserializerFactory paramDeserializerFactory);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract DefaultDeserializationContext createInstance(DeserializationConfig paramDeserializationConfig, JsonParser paramJsonParser, InjectableValues paramInjectableValues);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class Impl
/*     */     extends DefaultDeserializationContext
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Impl(DeserializerFactory paramDeserializerFactory)
/*     */     {
/* 203 */       super(null);
/*     */     }
/*     */     
/*     */     protected Impl(Impl paramImpl, DeserializationConfig paramDeserializationConfig, JsonParser paramJsonParser, InjectableValues paramInjectableValues)
/*     */     {
/* 208 */       super(paramDeserializationConfig, paramJsonParser, paramInjectableValues);
/*     */     }
/*     */     
/*     */     protected Impl(Impl paramImpl, DeserializerFactory paramDeserializerFactory) {
/* 212 */       super(paramDeserializerFactory);
/*     */     }
/*     */     
/*     */ 
/*     */     public DefaultDeserializationContext createInstance(DeserializationConfig paramDeserializationConfig, JsonParser paramJsonParser, InjectableValues paramInjectableValues)
/*     */     {
/* 218 */       return new Impl(this, paramDeserializationConfig, paramJsonParser, paramInjectableValues);
/*     */     }
/*     */     
/*     */     public DefaultDeserializationContext with(DeserializerFactory paramDeserializerFactory)
/*     */     {
/* 223 */       return new Impl(this, paramDeserializerFactory);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/DefaultDeserializationContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */