/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializable;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
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
/*     */ public class JSONWrappedObject
/*     */   implements JsonSerializable
/*     */ {
/*     */   protected final String _prefix;
/*     */   protected final String _suffix;
/*     */   protected final Object _value;
/*     */   protected final JavaType _serializationType;
/*     */   
/*     */   public JSONWrappedObject(String paramString1, String paramString2, Object paramObject)
/*     */   {
/*  49 */     this(paramString1, paramString2, paramObject, (JavaType)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JSONWrappedObject(String paramString1, String paramString2, Object paramObject, JavaType paramJavaType)
/*     */   {
/*  59 */     this._prefix = paramString1;
/*  60 */     this._suffix = paramString2;
/*  61 */     this._value = paramObject;
/*  62 */     this._serializationType = paramJavaType;
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
/*     */   public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  76 */     serialize(paramJsonGenerator, paramSerializerProvider);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  84 */     if (this._prefix != null) paramJsonGenerator.writeRaw(this._prefix);
/*  85 */     if (this._value == null) {
/*  86 */       paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*  87 */     } else if (this._serializationType != null) {
/*  88 */       paramSerializerProvider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/*  90 */       Class localClass = this._value.getClass();
/*  91 */       paramSerializerProvider.findTypedValueSerializer(localClass, true, null).serialize(this._value, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/*  93 */     if (this._suffix != null) { paramJsonGenerator.writeRaw(this._suffix);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */   public String getPrefix() { return this._prefix; }
/* 103 */   public String getSuffix() { return this._suffix; }
/* 104 */   public Object getValue() { return this._value; }
/* 105 */   public JavaType getSerializationType() { return this._serializationType; }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/JSONWrappedObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */