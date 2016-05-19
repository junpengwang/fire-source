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
/*     */ 
/*     */ 
/*     */ public class AsExternalTypeSerializer
/*     */   extends TypeSerializerBase
/*     */ {
/*     */   protected final String _typePropertyName;
/*     */   
/*     */   public AsExternalTypeSerializer(TypeIdResolver paramTypeIdResolver, BeanProperty paramBeanProperty, String paramString)
/*     */   {
/*  30 */     super(paramTypeIdResolver, paramBeanProperty);
/*  31 */     this._typePropertyName = paramString;
/*     */   }
/*     */   
/*     */   public AsExternalTypeSerializer forProperty(BeanProperty paramBeanProperty)
/*     */   {
/*  36 */     if (this._property == paramBeanProperty) return this;
/*  37 */     return new AsExternalTypeSerializer(this._idResolver, paramBeanProperty, this._typePropertyName);
/*     */   }
/*     */   
/*     */   public String getPropertyName() {
/*  41 */     return this._typePropertyName;
/*     */   }
/*     */   
/*  44 */   public JsonTypeInfo.As getTypeInclusion() { return JsonTypeInfo.As.EXTERNAL_PROPERTY; }
/*     */   
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
/*  56 */     _writeObjectPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  63 */     _writeObjectPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  70 */     _writeArrayPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  77 */     _writeArrayPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  84 */     _writeScalarPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  91 */     _writeScalarPrefix(paramObject, paramJsonGenerator);
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
/* 104 */     _writeObjectSuffix(paramObject, paramJsonGenerator, idFromValue(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 111 */     _writeArraySuffix(paramObject, paramJsonGenerator, idFromValue(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 118 */     _writeScalarSuffix(paramObject, paramJsonGenerator, idFromValue(paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeCustomTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 131 */     _writeScalarPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   public void writeCustomTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 137 */     _writeObjectPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 144 */     _writeArrayPrefix(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 150 */     _writeScalarSuffix(paramObject, paramJsonGenerator, paramString);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 156 */     _writeObjectSuffix(paramObject, paramJsonGenerator, paramString);
/*     */   }
/*     */   
/*     */   public void writeCustomTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 162 */     _writeArraySuffix(paramObject, paramJsonGenerator, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void _writeScalarPrefix(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void _writeObjectPrefix(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 178 */     paramJsonGenerator.writeStartObject();
/*     */   }
/*     */   
/*     */   protected final void _writeArrayPrefix(Object paramObject, JsonGenerator paramJsonGenerator) throws IOException, JsonProcessingException
/*     */   {
/* 183 */     paramJsonGenerator.writeStartArray();
/*     */   }
/*     */   
/*     */   protected final void _writeScalarSuffix(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 189 */     paramJsonGenerator.writeStringField(this._typePropertyName, paramString);
/*     */   }
/*     */   
/*     */   protected final void _writeObjectSuffix(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 195 */     paramJsonGenerator.writeEndObject();
/* 196 */     paramJsonGenerator.writeStringField(this._typePropertyName, paramString);
/*     */   }
/*     */   
/*     */   protected final void _writeArraySuffix(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 202 */     paramJsonGenerator.writeEndArray();
/* 203 */     paramJsonGenerator.writeStringField(this._typePropertyName, paramString);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsExternalTypeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */