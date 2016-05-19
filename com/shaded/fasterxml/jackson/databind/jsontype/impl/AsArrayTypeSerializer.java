/*     */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsArrayTypeSerializer
/*     */   extends TypeSerializerBase
/*     */ {
/*     */   public AsArrayTypeSerializer(TypeIdResolver paramTypeIdResolver, BeanProperty paramBeanProperty)
/*     */   {
/*  22 */     super(paramTypeIdResolver, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public AsArrayTypeSerializer forProperty(BeanProperty paramBeanProperty)
/*     */   {
/*  27 */     if (this._property == paramBeanProperty) return this;
/*  28 */     return new AsArrayTypeSerializer(this._idResolver, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion() {
/*  32 */     return JsonTypeInfo.As.WRAPPER_ARRAY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  44 */     paramJsonGenerator.writeStartArray();
/*  45 */     paramJsonGenerator.writeString(idFromValue(paramObject));
/*  46 */     paramJsonGenerator.writeStartObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  54 */     paramJsonGenerator.writeStartArray();
/*  55 */     paramJsonGenerator.writeString(idFromValueAndType(paramObject, paramClass));
/*  56 */     paramJsonGenerator.writeStartObject();
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  63 */     paramJsonGenerator.writeStartArray();
/*  64 */     paramJsonGenerator.writeString(idFromValue(paramObject));
/*  65 */     paramJsonGenerator.writeStartArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  73 */     paramJsonGenerator.writeStartArray();
/*  74 */     paramJsonGenerator.writeString(idFromValueAndType(paramObject, paramClass));
/*  75 */     paramJsonGenerator.writeStartArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  83 */     paramJsonGenerator.writeStartArray();
/*  84 */     paramJsonGenerator.writeString(idFromValue(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  93 */     paramJsonGenerator.writeStartArray();
/*  94 */     paramJsonGenerator.writeString(idFromValueAndType(paramObject, paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 107 */     paramJsonGenerator.writeEndObject();
/* 108 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 116 */     paramJsonGenerator.writeEndArray();
/* 117 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 125 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeCustomTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 137 */     paramJsonGenerator.writeStartArray();
/* 138 */     paramJsonGenerator.writeString(paramString);
/* 139 */     paramJsonGenerator.writeStartObject();
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 146 */     paramJsonGenerator.writeStartArray();
/* 147 */     paramJsonGenerator.writeString(paramString);
/* 148 */     paramJsonGenerator.writeStartArray();
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 155 */     paramJsonGenerator.writeStartArray();
/* 156 */     paramJsonGenerator.writeString(paramString);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 162 */     writeTypeSuffixForObject(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 168 */     writeTypeSuffixForArray(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 174 */     writeTypeSuffixForScalar(paramObject, paramJsonGenerator);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsArrayTypeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */