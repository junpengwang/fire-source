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
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsArrayTypeDeserializer
/*     */   extends TypeDeserializerBase
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5345570420394408290L;
/*     */   
/*     */   public AsArrayTypeDeserializer(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
/*     */   {
/*  32 */     super(paramJavaType, paramTypeIdResolver, paramString, paramBoolean, paramClass);
/*     */   }
/*     */   
/*     */   public AsArrayTypeDeserializer(AsArrayTypeDeserializer paramAsArrayTypeDeserializer, BeanProperty paramBeanProperty) {
/*  36 */     super(paramAsArrayTypeDeserializer, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public TypeDeserializer forProperty(BeanProperty paramBeanProperty)
/*     */   {
/*  41 */     if (paramBeanProperty == this._property) {
/*  42 */       return this;
/*     */     }
/*  44 */     return new AsArrayTypeDeserializer(this, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion()
/*     */   {
/*  49 */     return JsonTypeInfo.As.WRAPPER_ARRAY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeTypedFromArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  59 */     return _deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeTypedFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  69 */     return _deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object deserializeTypedFromScalar(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  76 */     return _deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object deserializeTypedFromAny(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  83 */     return _deserialize(paramJsonParser, paramDeserializationContext);
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
/*     */ 
/*     */ 
/*     */   private final Object _deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 100 */     boolean bool = paramJsonParser.isExpectedStartArrayToken();
/* 101 */     String str = _locateTypeId(paramJsonParser, paramDeserializationContext);
/* 102 */     JsonDeserializer localJsonDeserializer = _findDeserializer(paramDeserializationContext, str);
/*     */     
/* 104 */     if ((this._typeIdVisible) && (paramJsonParser.getCurrentToken() == JsonToken.START_OBJECT))
/*     */     {
/*     */ 
/* 107 */       localObject = new TokenBuffer(null);
/* 108 */       ((TokenBuffer)localObject).writeStartObject();
/* 109 */       ((TokenBuffer)localObject).writeFieldName(this._typePropertyName);
/* 110 */       ((TokenBuffer)localObject).writeString(str);
/* 111 */       paramJsonParser = JsonParserSequence.createFlattened(((TokenBuffer)localObject).asParser(paramJsonParser), paramJsonParser);
/* 112 */       paramJsonParser.nextToken();
/*     */     }
/* 114 */     Object localObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */     
/* 116 */     if ((bool) && (paramJsonParser.nextToken() != JsonToken.END_ARRAY)) {
/* 117 */       throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "expected closing END_ARRAY after type information and deserialized value");
/*     */     }
/*     */     
/* 120 */     return localObject;
/*     */   }
/*     */   
/*     */   protected final String _locateTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 126 */     if (!paramJsonParser.isExpectedStartArrayToken())
/*     */     {
/*     */ 
/* 129 */       if (this._defaultImpl != null) {
/* 130 */         return this._idResolver.idFromBaseType();
/*     */       }
/* 132 */       throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class " + baseTypeName());
/*     */     }
/*     */     
/* 135 */     JsonToken localJsonToken = paramJsonParser.nextToken();
/* 136 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 137 */       String str = paramJsonParser.getText();
/* 138 */       paramJsonParser.nextToken();
/* 139 */       return str;
/*     */     }
/* 141 */     if (this._defaultImpl != null) {
/* 142 */       return this._idResolver.idFromBaseType();
/*     */     }
/* 144 */     throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of " + baseTypeName() + ")");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsArrayTypeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */