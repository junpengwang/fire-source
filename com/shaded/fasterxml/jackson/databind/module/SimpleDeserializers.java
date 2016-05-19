/*     */ package com.shaded.fasterxml.jackson.databind.module;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.Deserializers;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class SimpleDeserializers implements Deserializers, java.io.Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3006673354353448880L;
/*  25 */   protected HashMap<ClassKey, JsonDeserializer<?>> _classMappings = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleDeserializers() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleDeserializers(Map<Class<?>, JsonDeserializer<?>> paramMap)
/*     */   {
/*  39 */     addDeserializers(paramMap);
/*     */   }
/*     */   
/*     */   public <T> void addDeserializer(Class<T> paramClass, JsonDeserializer<? extends T> paramJsonDeserializer)
/*     */   {
/*  44 */     ClassKey localClassKey = new ClassKey(paramClass);
/*  45 */     if (this._classMappings == null) {
/*  46 */       this._classMappings = new HashMap();
/*     */     }
/*  48 */     this._classMappings.put(localClassKey, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addDeserializers(Map<Class<?>, JsonDeserializer<?>> paramMap)
/*     */   {
/*  57 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  58 */       Class localClass = (Class)localEntry.getKey();
/*     */       
/*  60 */       JsonDeserializer localJsonDeserializer = (JsonDeserializer)localEntry.getValue();
/*  61 */       addDeserializer(localClass, localJsonDeserializer);
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
/*     */ 
/*     */   public JsonDeserializer<?> findArrayDeserializer(ArrayType paramArrayType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/*  77 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramArrayType.getRawClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findBeanDeserializer(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/*  85 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramJavaType.getRawClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findCollectionDeserializer(CollectionType paramCollectionType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/*  95 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramCollectionType.getRawClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType paramCollectionLikeType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/* 105 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramCollectionLikeType.getRawClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findEnumDeserializer(Class<?> paramClass, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 113 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findMapDeserializer(MapType paramMapType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/* 124 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramMapType.getRawClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType paramMapLikeType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/* 135 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramMapLikeType.getRawClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> paramClass, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 143 */     return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(paramClass));
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/module/SimpleDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */