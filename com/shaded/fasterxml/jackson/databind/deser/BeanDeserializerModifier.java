/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*     */ import java.util.List;
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
/*     */ public abstract class BeanDeserializerModifier
/*     */ {
/*     */   public List<BeanPropertyDefinition> updateProperties(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, List<BeanPropertyDefinition> paramList)
/*     */   {
/*  61 */     return paramList;
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
/*     */   public BeanDeserializerBuilder updateBuilder(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, BeanDeserializerBuilder paramBeanDeserializerBuilder)
/*     */   {
/*  74 */     return paramBeanDeserializerBuilder;
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
/*     */   public JsonDeserializer<?> modifyDeserializer(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  87 */     return paramJsonDeserializer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> modifyArrayDeserializer(DeserializationConfig paramDeserializationConfig, ArrayType paramArrayType, BeanDescription paramBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 115 */     return paramJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> modifyCollectionDeserializer(DeserializationConfig paramDeserializationConfig, CollectionType paramCollectionType, BeanDescription paramBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 123 */     return paramJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> modifyCollectionLikeDeserializer(DeserializationConfig paramDeserializationConfig, CollectionLikeType paramCollectionLikeType, BeanDescription paramBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 131 */     return paramJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> modifyMapDeserializer(DeserializationConfig paramDeserializationConfig, MapType paramMapType, BeanDescription paramBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 139 */     return paramJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> modifyMapLikeDeserializer(DeserializationConfig paramDeserializationConfig, MapLikeType paramMapLikeType, BeanDescription paramBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 147 */     return paramJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> modifyEnumDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 155 */     return paramJsonDeserializer;
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
/*     */   public KeyDeserializer modifyKeyDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, KeyDeserializer paramKeyDeserializer)
/*     */   {
/* 169 */     return paramKeyDeserializer;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/BeanDeserializerModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */