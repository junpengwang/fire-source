/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public class CollectionSerializer
/*     */   extends AsArraySerializerBase<Collection<?>>
/*     */ {
/*     */   public CollectionSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/*  35 */     super(Collection.class, paramJavaType, paramBoolean, paramTypeSerializer, paramBeanProperty, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionSerializer(CollectionSerializer paramCollectionSerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  41 */     super(paramCollectionSerializer, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*     */   }
/*     */   
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */   {
/*  46 */     return new CollectionSerializer(this._elementType, this._staticTyping, paramTypeSerializer, this._property, this._elementSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionSerializer withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  52 */     return new CollectionSerializer(this, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty(Collection<?> paramCollection)
/*     */   {
/*  63 */     return (paramCollection == null) || (paramCollection.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasSingleElement(Collection<?> paramCollection)
/*     */   {
/*  68 */     Iterator localIterator = paramCollection.iterator();
/*  69 */     if (!localIterator.hasNext()) {
/*  70 */       return false;
/*     */     }
/*  72 */     localIterator.next();
/*  73 */     return !localIterator.hasNext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeContents(Collection<?> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  86 */     if (this._elementSerializer != null) {
/*  87 */       serializeContentsUsing(paramCollection, paramJsonGenerator, paramSerializerProvider, this._elementSerializer);
/*  88 */       return;
/*     */     }
/*  90 */     Iterator localIterator = paramCollection.iterator();
/*  91 */     if (!localIterator.hasNext()) {
/*  92 */       return;
/*     */     }
/*  94 */     PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/*  95 */     TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/*     */     
/*  97 */     int i = 0;
/*     */     try {
/*     */       do {
/* 100 */         Object localObject = localIterator.next();
/* 101 */         if (localObject == null) {
/* 102 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/* 104 */           Class localClass = localObject.getClass();
/* 105 */           JsonSerializer localJsonSerializer = localPropertySerializerMap.serializerFor(localClass);
/* 106 */           if (localJsonSerializer == null)
/*     */           {
/* 108 */             if (this._elementType.hasGenericTypes()) {
/* 109 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, paramSerializerProvider.constructSpecializedType(this._elementType, localClass), paramSerializerProvider);
/*     */             }
/*     */             else {
/* 112 */               localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
/*     */             }
/* 114 */             localPropertySerializerMap = this._dynamicSerializers;
/*     */           }
/* 116 */           if (localTypeSerializer == null) {
/* 117 */             localJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */           } else {
/* 119 */             localJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */           }
/*     */         }
/* 122 */         i++;
/* 123 */       } while (localIterator.hasNext());
/*     */     }
/*     */     catch (Exception localException) {
/* 126 */       wrapAndThrow(paramSerializerProvider, localException, paramCollection, i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void serializeContentsUsing(Collection<?> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 134 */     Iterator localIterator = paramCollection.iterator();
/* 135 */     if (localIterator.hasNext()) {
/* 136 */       TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 137 */       int i = 0;
/*     */       do {
/* 139 */         Object localObject = localIterator.next();
/*     */         try {
/* 141 */           if (localObject == null) {
/* 142 */             paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */           }
/* 144 */           else if (localTypeSerializer == null) {
/* 145 */             paramJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */           } else {
/* 147 */             paramJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*     */           }
/*     */           
/* 150 */           i++;
/*     */         }
/*     */         catch (Exception localException) {
/* 153 */           wrapAndThrow(paramSerializerProvider, localException, paramCollection, i);
/*     */         }
/* 155 */       } while (localIterator.hasNext());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/CollectionSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */