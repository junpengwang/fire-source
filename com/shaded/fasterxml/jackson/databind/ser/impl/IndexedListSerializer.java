/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public final class IndexedListSerializer
/*     */   extends AsArraySerializerBase<List<?>>
/*     */ {
/*     */   public IndexedListSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/*  28 */     super(List.class, paramJavaType, paramBoolean, paramTypeSerializer, paramBeanProperty, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */   public IndexedListSerializer(IndexedListSerializer paramIndexedListSerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  34 */     super(paramIndexedListSerializer, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */   public IndexedListSerializer withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  40 */     return new IndexedListSerializer(this, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty(List<?> paramList)
/*     */   {
/*  51 */     return (paramList == null) || (paramList.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasSingleElement(List<?> paramList)
/*     */   {
/*  56 */     return paramList.size() == 1;
/*     */   }
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */   {
/*  61 */     return new IndexedListSerializer(this._elementType, this._staticTyping, paramTypeSerializer, this._property, this._elementSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */   public void serializeContents(List<?> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  68 */     if (this._elementSerializer != null) {
/*  69 */       serializeContentsUsing(paramList, paramJsonGenerator, paramSerializerProvider, this._elementSerializer);
/*  70 */       return;
/*     */     }
/*  72 */     if (this._valueTypeSerializer != null) {
/*  73 */       serializeTypedContents(paramList, paramJsonGenerator, paramSerializerProvider);
/*  74 */       return;
/*     */     }
/*  76 */     int i = paramList.size();
/*  77 */     if (i == 0) {
/*  78 */       return;
/*     */     }
/*  80 */     int j = 0;
/*     */     try {
/*  82 */       PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/*  83 */       for (; j < i; j++) {
/*  84 */         Object localObject = paramList.get(j);
/*  85 */         if (localObject == null) {
/*  86 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/*  88 */           Class localClass = localObject.getClass();
/*  89 */           JsonSerializer localJsonSerializer = localPropertySerializerMap.serializerFor(localClass);
/*  90 */           if (localJsonSerializer == null)
/*     */           {
/*  92 */             if (this._elementType.hasGenericTypes()) {
/*  93 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, paramSerializerProvider.constructSpecializedType(this._elementType, localClass), paramSerializerProvider);
/*     */             }
/*     */             else {
/*  96 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
/*     */             }
/*  98 */             localPropertySerializerMap = this._dynamicSerializers;
/*     */           }
/* 100 */           localJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 105 */       wrapAndThrow(paramSerializerProvider, localException, paramList, j);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void serializeContentsUsing(List<?> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 113 */     int i = paramList.size();
/* 114 */     if (i == 0) {
/* 115 */       return;
/*     */     }
/* 117 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 118 */     for (int j = 0; j < i; j++) {
/* 119 */       Object localObject = paramList.get(j);
/*     */       try {
/* 121 */         if (localObject == null) {
/* 122 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/* 123 */         } else if (localTypeSerializer == null) {
/* 124 */           paramJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */         } else {
/* 126 */           paramJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {
/* 130 */         wrapAndThrow(paramSerializerProvider, localException, paramList, j);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void serializeTypedContents(List<?> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 138 */     int i = paramList.size();
/* 139 */     if (i == 0) {
/* 140 */       return;
/*     */     }
/* 142 */     int j = 0;
/*     */     try {
/* 144 */       TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 145 */       PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/* 146 */       for (; j < i; j++) {
/* 147 */         Object localObject = paramList.get(j);
/* 148 */         if (localObject == null) {
/* 149 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/* 151 */           Class localClass = localObject.getClass();
/* 152 */           JsonSerializer localJsonSerializer = localPropertySerializerMap.serializerFor(localClass);
/* 153 */           if (localJsonSerializer == null)
/*     */           {
/* 155 */             if (this._elementType.hasGenericTypes()) {
/* 156 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, paramSerializerProvider.constructSpecializedType(this._elementType, localClass), paramSerializerProvider);
/*     */             }
/*     */             else {
/* 159 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
/*     */             }
/* 161 */             localPropertySerializerMap = this._dynamicSerializers;
/*     */           }
/* 163 */           localJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 168 */       wrapAndThrow(paramSerializerProvider, localException, paramList, j);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/IndexedListSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */