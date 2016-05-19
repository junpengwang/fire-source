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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsWrapperTypeSerializer
/*     */   extends TypeSerializerBase
/*     */ {
/*     */   public AsWrapperTypeSerializer(TypeIdResolver paramTypeIdResolver, BeanProperty paramBeanProperty)
/*     */   {
/*  26 */     super(paramTypeIdResolver, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public AsWrapperTypeSerializer forProperty(BeanProperty paramBeanProperty)
/*     */   {
/*  31 */     if (this._property == paramBeanProperty) return this;
/*  32 */     return new AsWrapperTypeSerializer(this._idResolver, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion() {
/*  36 */     return JsonTypeInfo.As.WRAPPER_OBJECT;
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  43 */     paramJsonGenerator.writeStartObject();
/*     */     
/*  45 */     paramJsonGenerator.writeObjectFieldStart(idFromValue(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  54 */     paramJsonGenerator.writeStartObject();
/*     */     
/*  56 */     paramJsonGenerator.writeObjectFieldStart(idFromValueAndType(paramObject, paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  64 */     paramJsonGenerator.writeStartObject();
/*     */     
/*  66 */     paramJsonGenerator.writeArrayFieldStart(idFromValue(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  75 */     paramJsonGenerator.writeStartObject();
/*     */     
/*  77 */     paramJsonGenerator.writeArrayFieldStart(idFromValueAndType(paramObject, paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  85 */     paramJsonGenerator.writeStartObject();
/*  86 */     paramJsonGenerator.writeFieldName(idFromValue(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  95 */     paramJsonGenerator.writeStartObject();
/*  96 */     paramJsonGenerator.writeFieldName(idFromValueAndType(paramObject, paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 104 */     paramJsonGenerator.writeEndObject();
/*     */     
/* 106 */     paramJsonGenerator.writeEndObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 114 */     paramJsonGenerator.writeEndArray();
/*     */     
/* 116 */     paramJsonGenerator.writeEndObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 124 */     paramJsonGenerator.writeEndObject();
/*     */   }
/*     */   
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
/* 137 */     paramJsonGenerator.writeStartObject();
/* 138 */     paramJsonGenerator.writeObjectFieldStart(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 145 */     paramJsonGenerator.writeStartObject();
/* 146 */     paramJsonGenerator.writeArrayFieldStart(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 153 */     paramJsonGenerator.writeStartObject();
/* 154 */     paramJsonGenerator.writeFieldName(paramString);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 160 */     writeTypeSuffixForObject(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 166 */     writeTypeSuffixForArray(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 172 */     writeTypeSuffixForScalar(paramObject, paramJsonGenerator);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsWrapperTypeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */