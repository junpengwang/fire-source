/*    */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AsPropertyTypeSerializer
/*    */   extends AsArrayTypeSerializer
/*    */ {
/*    */   protected final String _typePropertyName;
/*    */   
/*    */   public AsPropertyTypeSerializer(TypeIdResolver paramTypeIdResolver, BeanProperty paramBeanProperty, String paramString)
/*    */   {
/* 28 */     super(paramTypeIdResolver, paramBeanProperty);
/* 29 */     this._typePropertyName = paramString;
/*    */   }
/*    */   
/*    */   public AsPropertyTypeSerializer forProperty(BeanProperty paramBeanProperty)
/*    */   {
/* 34 */     if (this._property == paramBeanProperty) return this;
/* 35 */     return new AsPropertyTypeSerializer(this._idResolver, paramBeanProperty, this._typePropertyName);
/*    */   }
/*    */   
/*    */   public String getPropertyName() {
/* 39 */     return this._typePropertyName;
/*    */   }
/*    */   
/* 42 */   public JsonTypeInfo.As getTypeInclusion() { return JsonTypeInfo.As.PROPERTY; }
/*    */   
/*    */ 
/*    */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 48 */     paramJsonGenerator.writeStartObject();
/* 49 */     paramJsonGenerator.writeStringField(this._typePropertyName, idFromValue(paramObject));
/*    */   }
/*    */   
/*    */ 
/*    */   public void writeTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, Class<?> paramClass)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 56 */     paramJsonGenerator.writeStartObject();
/* 57 */     paramJsonGenerator.writeStringField(this._typePropertyName, idFromValueAndType(paramObject, paramClass));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 69 */     paramJsonGenerator.writeEndObject();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeCustomTypePrefixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 87 */     paramJsonGenerator.writeStartObject();
/* 88 */     paramJsonGenerator.writeStringField(this._typePropertyName, paramString);
/*    */   }
/*    */   
/*    */   public void writeCustomTypeSuffixForObject(Object paramObject, JsonGenerator paramJsonGenerator, String paramString)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 94 */     paramJsonGenerator.writeEndObject();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsPropertyTypeSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */