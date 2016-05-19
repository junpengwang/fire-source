/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
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
/*     */ public abstract class PropertySerializerMap
/*     */ {
/*     */   public abstract JsonSerializer<Object> serializerFor(Class<?> paramClass);
/*     */   
/*     */   public final SerializerAndMapResult findAndAddSerializer(Class<?> paramClass, SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  38 */     JsonSerializer localJsonSerializer = paramSerializerProvider.findValueSerializer(paramClass, paramBeanProperty);
/*  39 */     return new SerializerAndMapResult(localJsonSerializer, newWith(paramClass, localJsonSerializer));
/*     */   }
/*     */   
/*     */ 
/*     */   public final SerializerAndMapResult findAndAddSerializer(JavaType paramJavaType, SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  46 */     JsonSerializer localJsonSerializer = paramSerializerProvider.findValueSerializer(paramJavaType, paramBeanProperty);
/*  47 */     return new SerializerAndMapResult(localJsonSerializer, newWith(paramJavaType.getRawClass(), localJsonSerializer));
/*     */   }
/*     */   
/*     */   public abstract PropertySerializerMap newWith(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer);
/*     */   
/*     */   public static PropertySerializerMap emptyMap() {
/*  53 */     return Empty.instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class SerializerAndMapResult
/*     */   {
/*     */     public final JsonSerializer<Object> serializer;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public final PropertySerializerMap map;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public SerializerAndMapResult(JsonSerializer<Object> paramJsonSerializer, PropertySerializerMap paramPropertySerializerMap)
/*     */     {
/*  74 */       this.serializer = paramJsonSerializer;
/*  75 */       this.map = paramPropertySerializerMap;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class TypeAndSerializer
/*     */   {
/*     */     public final Class<?> type;
/*     */     
/*     */     public final JsonSerializer<Object> serializer;
/*     */     
/*     */     public TypeAndSerializer(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/*  88 */       this.type = paramClass;
/*  89 */       this.serializer = paramJsonSerializer;
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
/*     */   private static final class Empty
/*     */     extends PropertySerializerMap
/*     */   {
/* 105 */     protected static final Empty instance = new Empty();
/*     */     
/*     */     public JsonSerializer<Object> serializerFor(Class<?> paramClass)
/*     */     {
/* 109 */       return null;
/*     */     }
/*     */     
/*     */     public PropertySerializerMap newWith(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/* 114 */       return new PropertySerializerMap.Single(paramClass, paramJsonSerializer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class Single
/*     */     extends PropertySerializerMap
/*     */   {
/*     */     private final Class<?> _type;
/*     */     
/*     */     private final JsonSerializer<Object> _serializer;
/*     */     
/*     */ 
/*     */     public Single(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/* 130 */       this._type = paramClass;
/* 131 */       this._serializer = paramJsonSerializer;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<Object> serializerFor(Class<?> paramClass)
/*     */     {
/* 137 */       if (paramClass == this._type) {
/* 138 */         return this._serializer;
/*     */       }
/* 140 */       return null;
/*     */     }
/*     */     
/*     */     public PropertySerializerMap newWith(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/* 145 */       return new PropertySerializerMap.Double(this._type, this._serializer, paramClass, paramJsonSerializer);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Double extends PropertySerializerMap
/*     */   {
/*     */     private final Class<?> _type1;
/*     */     private final Class<?> _type2;
/*     */     private final JsonSerializer<Object> _serializer1;
/*     */     private final JsonSerializer<Object> _serializer2;
/*     */     
/*     */     public Double(Class<?> paramClass1, JsonSerializer<Object> paramJsonSerializer1, Class<?> paramClass2, JsonSerializer<Object> paramJsonSerializer2) {
/* 157 */       this._type1 = paramClass1;
/* 158 */       this._serializer1 = paramJsonSerializer1;
/* 159 */       this._type2 = paramClass2;
/* 160 */       this._serializer2 = paramJsonSerializer2;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<Object> serializerFor(Class<?> paramClass)
/*     */     {
/* 166 */       if (paramClass == this._type1) {
/* 167 */         return this._serializer1;
/*     */       }
/* 169 */       if (paramClass == this._type2) {
/* 170 */         return this._serializer2;
/*     */       }
/* 172 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */     public PropertySerializerMap newWith(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/* 178 */       PropertySerializerMap.TypeAndSerializer[] arrayOfTypeAndSerializer = new PropertySerializerMap.TypeAndSerializer[2];
/* 179 */       arrayOfTypeAndSerializer[0] = new PropertySerializerMap.TypeAndSerializer(this._type1, this._serializer1);
/* 180 */       arrayOfTypeAndSerializer[1] = new PropertySerializerMap.TypeAndSerializer(this._type2, this._serializer2);
/* 181 */       return new PropertySerializerMap.Multi(arrayOfTypeAndSerializer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class Multi
/*     */     extends PropertySerializerMap
/*     */   {
/*     */     private static final int MAX_ENTRIES = 8;
/*     */     
/*     */ 
/*     */     private final PropertySerializerMap.TypeAndSerializer[] _entries;
/*     */     
/*     */ 
/*     */ 
/*     */     public Multi(PropertySerializerMap.TypeAndSerializer[] paramArrayOfTypeAndSerializer)
/*     */     {
/* 200 */       this._entries = paramArrayOfTypeAndSerializer;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<Object> serializerFor(Class<?> paramClass)
/*     */     {
/* 206 */       int i = 0; for (int j = this._entries.length; i < j; i++) {
/* 207 */         PropertySerializerMap.TypeAndSerializer localTypeAndSerializer = this._entries[i];
/* 208 */         if (localTypeAndSerializer.type == paramClass) {
/* 209 */           return localTypeAndSerializer.serializer;
/*     */         }
/*     */       }
/* 212 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */     public PropertySerializerMap newWith(Class<?> paramClass, JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/* 218 */       int i = this._entries.length;
/*     */       
/* 220 */       if (i == 8) {
/* 221 */         return this;
/*     */       }
/*     */       
/* 224 */       PropertySerializerMap.TypeAndSerializer[] arrayOfTypeAndSerializer = new PropertySerializerMap.TypeAndSerializer[i + 1];
/* 225 */       System.arraycopy(this._entries, 0, arrayOfTypeAndSerializer, 0, i);
/* 226 */       arrayOfTypeAndSerializer[i] = new PropertySerializerMap.TypeAndSerializer(paramClass, paramJsonSerializer);
/* 227 */       return new Multi(arrayOfTypeAndSerializer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/PropertySerializerMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */