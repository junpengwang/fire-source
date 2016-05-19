/*     */ package com.shaded.fasterxml.jackson.databind.jsontype;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
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
/*     */ public abstract class TypeSerializer
/*     */ {
/*     */   public abstract TypeSerializer forProperty(BeanProperty paramBeanProperty);
/*     */   
/*     */   public abstract JsonTypeInfo.As getTypeInclusion();
/*     */   
/*     */   public abstract String getPropertyName();
/*     */   
/*     */   public abstract TypeIdResolver getTypeIdResolver();
/*     */   
/*     */   public abstract void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public void writeTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 148 */     writeTypePrefixForScalar(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 160 */     writeTypePrefixForObject(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 172 */     writeTypePrefixForArray(paramObject, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   public abstract void writeCustomTypePrefixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeCustomTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeCustomTypePrefixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeCustomTypeSuffixForScalar(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeCustomTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeCustomTypeSuffixForArray(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*     */     throws IOException, JsonProcessingException;
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/TypeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */