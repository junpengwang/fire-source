/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
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
/*     */ public abstract class BeanSerializerModifier
/*     */ {
/*     */   public List<BeanPropertyWriter> changeProperties(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, List<BeanPropertyWriter> paramList)
/*     */   {
/*  49 */     return paramList;
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
/*     */   public List<BeanPropertyWriter> orderProperties(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, List<BeanPropertyWriter> paramList)
/*     */   {
/*  64 */     return paramList;
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
/*     */   public BeanSerializerBuilder updateBuilder(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, BeanSerializerBuilder paramBeanSerializerBuilder)
/*     */   {
/*  78 */     return paramBeanSerializerBuilder;
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
/*     */   public JsonSerializer<?> modifySerializer(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  95 */     return paramJsonSerializer;
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
/*     */   public JsonSerializer<?> modifyArraySerializer(SerializationConfig paramSerializationConfig, ArrayType paramArrayType, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 123 */     return paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> modifyCollectionSerializer(SerializationConfig paramSerializationConfig, CollectionType paramCollectionType, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 131 */     return paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> modifyCollectionLikeSerializer(SerializationConfig paramSerializationConfig, CollectionLikeType paramCollectionLikeType, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 139 */     return paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> modifyMapSerializer(SerializationConfig paramSerializationConfig, MapType paramMapType, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 147 */     return paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> modifyMapLikeSerializer(SerializationConfig paramSerializationConfig, MapLikeType paramMapLikeType, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 155 */     return paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> modifyEnumSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 163 */     return paramJsonSerializer;
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
/*     */   public JsonSerializer<?> modifyKeySerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 185 */     return paramJsonSerializer;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/BeanSerializerModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */