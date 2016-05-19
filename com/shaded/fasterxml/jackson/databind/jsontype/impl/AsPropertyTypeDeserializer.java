/*     */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.util.JsonParserSequence;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.util.TokenBuffer;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsPropertyTypeDeserializer
/*     */   extends AsArrayTypeDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public AsPropertyTypeDeserializer(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
/*     */   {
/*  31 */     super(paramJavaType, paramTypeIdResolver, paramString, paramBoolean, paramClass);
/*     */   }
/*     */   
/*     */   public AsPropertyTypeDeserializer(AsPropertyTypeDeserializer paramAsPropertyTypeDeserializer, BeanProperty paramBeanProperty) {
/*  35 */     super(paramAsPropertyTypeDeserializer, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public TypeDeserializer forProperty(BeanProperty paramBeanProperty)
/*     */   {
/*  40 */     if (paramBeanProperty == this._property) {
/*  41 */       return this;
/*     */     }
/*  43 */     return new AsPropertyTypeDeserializer(this, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion()
/*     */   {
/*  48 */     return JsonTypeInfo.As.PROPERTY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeTypedFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  60 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*  61 */     if (localJsonToken == JsonToken.START_OBJECT) {
/*  62 */       localJsonToken = paramJsonParser.nextToken();
/*  63 */     } else { if (localJsonToken == JsonToken.START_ARRAY)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  71 */         return _deserializeTypedUsingDefaultImpl(paramJsonParser, paramDeserializationContext, null); }
/*  72 */       if (localJsonToken != JsonToken.FIELD_NAME) {
/*  73 */         return _deserializeTypedUsingDefaultImpl(paramJsonParser, paramDeserializationContext, null);
/*     */       }
/*     */     }
/*  76 */     TokenBuffer localTokenBuffer = null;
/*  78 */     for (; 
/*  78 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/*  79 */       String str = paramJsonParser.getCurrentName();
/*  80 */       paramJsonParser.nextToken();
/*  81 */       if (this._typePropertyName.equals(str)) {
/*  82 */         return _deserializeTypedForId(paramJsonParser, paramDeserializationContext, localTokenBuffer);
/*     */       }
/*  84 */       if (localTokenBuffer == null) {
/*  85 */         localTokenBuffer = new TokenBuffer(null);
/*     */       }
/*  87 */       localTokenBuffer.writeFieldName(str);
/*  88 */       localTokenBuffer.copyCurrentStructure(paramJsonParser);
/*     */     }
/*  90 */     return _deserializeTypedUsingDefaultImpl(paramJsonParser, paramDeserializationContext, localTokenBuffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Object _deserializeTypedForId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TokenBuffer paramTokenBuffer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  98 */     String str = paramJsonParser.getText();
/*  99 */     JsonDeserializer localJsonDeserializer = _findDeserializer(paramDeserializationContext, str);
/* 100 */     if (this._typeIdVisible) {
/* 101 */       if (paramTokenBuffer == null) {
/* 102 */         paramTokenBuffer = new TokenBuffer(null);
/*     */       }
/* 104 */       paramTokenBuffer.writeFieldName(paramJsonParser.getCurrentName());
/* 105 */       paramTokenBuffer.writeString(str);
/*     */     }
/* 107 */     if (paramTokenBuffer != null) {
/* 108 */       paramJsonParser = JsonParserSequence.createFlattened(paramTokenBuffer.asParser(paramJsonParser), paramJsonParser);
/*     */     }
/*     */     
/* 111 */     paramJsonParser.nextToken();
/*     */     
/* 113 */     return localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object _deserializeTypedUsingDefaultImpl(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TokenBuffer paramTokenBuffer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 122 */     JsonDeserializer localJsonDeserializer = _findDefaultImplDeserializer(paramDeserializationContext);
/* 123 */     if (localJsonDeserializer != null) {
/* 124 */       if (paramTokenBuffer != null) {
/* 125 */         paramTokenBuffer.writeEndObject();
/* 126 */         paramJsonParser = paramTokenBuffer.asParser(paramJsonParser);
/*     */         
/* 128 */         paramJsonParser.nextToken();
/*     */       }
/* 130 */       return localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/* 133 */     Object localObject = TypeDeserializer.deserializeIfNatural(paramJsonParser, paramDeserializationContext, this._baseType);
/* 134 */     if (localObject != null) {
/* 135 */       return localObject;
/*     */     }
/*     */     
/* 138 */     if (paramJsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
/* 139 */       return super.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 141 */     throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.FIELD_NAME, "missing property '" + this._typePropertyName + "' that is to contain type id  (for class " + baseTypeName() + ")");
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
/*     */   public Object deserializeTypedFromAny(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 156 */     if (paramJsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
/* 157 */       return super.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 159 */     return deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsPropertyTypeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */