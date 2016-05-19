/*     */ package com.shaded.fasterxml.jackson.databind.module;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.Serializers.Base;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapLikeType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
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
/*     */ public class SimpleSerializers
/*     */   extends Serializers.Base
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8531646511998456779L;
/*  37 */   protected HashMap<ClassKey, JsonSerializer<?>> _classMappings = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  42 */   protected HashMap<ClassKey, JsonSerializer<?>> _interfaceMappings = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleSerializers() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleSerializers(List<JsonSerializer<?>> paramList)
/*     */   {
/*  56 */     addSerializers(paramList);
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
/*     */   public void addSerializer(JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  71 */     Class localClass = paramJsonSerializer.handledType();
/*  72 */     if ((localClass == null) || (localClass == Object.class)) {
/*  73 */       throw new IllegalArgumentException("JsonSerializer of type " + paramJsonSerializer.getClass().getName() + " does not define valid handledType() -- must either register with method that takes type argument " + " or make serializer extend 'com.fasterxml.jackson.databind.ser.std.StdSerializer'");
/*     */     }
/*     */     
/*     */ 
/*  77 */     _addSerializer(localClass, paramJsonSerializer);
/*     */   }
/*     */   
/*     */   public <T> void addSerializer(Class<? extends T> paramClass, JsonSerializer<T> paramJsonSerializer)
/*     */   {
/*  82 */     _addSerializer(paramClass, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addSerializers(List<JsonSerializer<?>> paramList)
/*     */   {
/*  89 */     for (JsonSerializer localJsonSerializer : paramList) {
/*  90 */       addSerializer(localJsonSerializer);
/*     */     }
/*     */   }
/*     */   
/*     */   private void _addSerializer(Class<?> paramClass, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  96 */     ClassKey localClassKey = new ClassKey(paramClass);
/*     */     
/*  98 */     if (paramClass.isInterface()) {
/*  99 */       if (this._interfaceMappings == null) {
/* 100 */         this._interfaceMappings = new HashMap();
/*     */       }
/* 102 */       this._interfaceMappings.put(localClassKey, paramJsonSerializer);
/*     */     } else {
/* 104 */       if (this._classMappings == null) {
/* 105 */         this._classMappings = new HashMap();
/*     */       }
/* 107 */       this._classMappings.put(localClassKey, paramJsonSerializer);
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
/*     */   public JsonSerializer<?> findSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */   {
/* 121 */     Class localClass1 = paramJavaType.getRawClass();
/* 122 */     ClassKey localClassKey = new ClassKey(localClass1);
/* 123 */     JsonSerializer localJsonSerializer = null;
/*     */     
/*     */ 
/* 126 */     if (localClass1.isInterface()) {
/* 127 */       if (this._interfaceMappings != null) {
/* 128 */         localJsonSerializer = (JsonSerializer)this._interfaceMappings.get(localClassKey);
/* 129 */         if (localJsonSerializer != null) {
/* 130 */           return localJsonSerializer;
/*     */         }
/*     */       }
/*     */     }
/* 134 */     else if (this._classMappings != null) {
/* 135 */       localJsonSerializer = (JsonSerializer)this._classMappings.get(localClassKey);
/* 136 */       if (localJsonSerializer != null) {
/* 137 */         return localJsonSerializer;
/*     */       }
/*     */       
/* 140 */       for (Class localClass2 = localClass1; localClass2 != null; localClass2 = localClass2.getSuperclass()) {
/* 141 */         localClassKey.reset(localClass2);
/* 142 */         localJsonSerializer = (JsonSerializer)this._classMappings.get(localClassKey);
/* 143 */         if (localJsonSerializer != null) {
/* 144 */           return localJsonSerializer;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 150 */     if (this._interfaceMappings != null) {
/* 151 */       localJsonSerializer = _findInterfaceMapping(localClass1, localClassKey);
/* 152 */       if (localJsonSerializer != null) {
/* 153 */         return localJsonSerializer;
/*     */       }
/*     */       
/* 156 */       if (!localClass1.isInterface()) {
/* 157 */         while ((localClass1 = localClass1.getSuperclass()) != null) {
/* 158 */           localJsonSerializer = _findInterfaceMapping(localClass1, localClassKey);
/* 159 */           if (localJsonSerializer != null) {
/* 160 */             return localJsonSerializer;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 165 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> findArraySerializer(SerializationConfig paramSerializationConfig, ArrayType paramArrayType, BeanDescription paramBeanDescription, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 172 */     return findSerializer(paramSerializationConfig, paramArrayType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> findCollectionSerializer(SerializationConfig paramSerializationConfig, CollectionType paramCollectionType, BeanDescription paramBeanDescription, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 179 */     return findSerializer(paramSerializationConfig, paramCollectionType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig paramSerializationConfig, CollectionLikeType paramCollectionLikeType, BeanDescription paramBeanDescription, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 186 */     return findSerializer(paramSerializationConfig, paramCollectionLikeType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> findMapSerializer(SerializationConfig paramSerializationConfig, MapType paramMapType, BeanDescription paramBeanDescription, JsonSerializer<Object> paramJsonSerializer1, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer2)
/*     */   {
/* 194 */     return findSerializer(paramSerializationConfig, paramMapType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> findMapLikeSerializer(SerializationConfig paramSerializationConfig, MapLikeType paramMapLikeType, BeanDescription paramBeanDescription, JsonSerializer<Object> paramJsonSerializer1, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer2)
/*     */   {
/* 202 */     return findSerializer(paramSerializationConfig, paramMapLikeType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<?> _findInterfaceMapping(Class<?> paramClass, ClassKey paramClassKey)
/*     */   {
/* 213 */     for (Class localClass : paramClass.getInterfaces()) {
/* 214 */       paramClassKey.reset(localClass);
/* 215 */       JsonSerializer localJsonSerializer = (JsonSerializer)this._interfaceMappings.get(paramClassKey);
/* 216 */       if (localJsonSerializer != null) {
/* 217 */         return localJsonSerializer;
/*     */       }
/* 219 */       localJsonSerializer = _findInterfaceMapping(localClass, paramClassKey);
/* 220 */       if (localJsonSerializer != null) {
/* 221 */         return localJsonSerializer;
/*     */       }
/*     */     }
/* 224 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/module/SimpleSerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */