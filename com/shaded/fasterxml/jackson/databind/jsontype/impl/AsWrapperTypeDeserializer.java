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
/*     */ public class AsWrapperTypeDeserializer
/*     */   extends TypeDeserializerBase
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5345570420394408290L;
/*     */   
/*     */   public AsWrapperTypeDeserializer(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
/*     */   {
/*  32 */     super(paramJavaType, paramTypeIdResolver, paramString, paramBoolean, null);
/*     */   }
/*     */   
/*     */   protected AsWrapperTypeDeserializer(AsWrapperTypeDeserializer paramAsWrapperTypeDeserializer, BeanProperty paramBeanProperty) {
/*  36 */     super(paramAsWrapperTypeDeserializer, paramBeanProperty);
/*     */   }
/*     */   
/*     */ 
/*     */   public TypeDeserializer forProperty(BeanProperty paramBeanProperty)
/*     */   {
/*  42 */     if (paramBeanProperty == this._property) {
/*  43 */       return this;
/*     */     }
/*  45 */     return new AsWrapperTypeDeserializer(this, paramBeanProperty);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.As getTypeInclusion()
/*     */   {
/*  50 */     return JsonTypeInfo.As.WRAPPER_OBJECT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeTypedFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  60 */     return _deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object deserializeTypedFromArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  67 */     return _deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object deserializeTypedFromScalar(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  74 */     return _deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object deserializeTypedFromAny(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  81 */     return _deserialize(paramJsonParser, paramDeserializationContext);
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
/*     */ 
/*     */   private final Object _deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  99 */     if (paramJsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
/* 100 */       throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.START_OBJECT, "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + baseTypeName());
/*     */     }
/*     */     
/*     */ 
/* 104 */     if (paramJsonParser.nextToken() != JsonToken.FIELD_NAME) {
/* 105 */       throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.FIELD_NAME, "need JSON String that contains type id (for subtype of " + baseTypeName() + ")");
/*     */     }
/*     */     
/* 108 */     String str = paramJsonParser.getText();
/* 109 */     JsonDeserializer localJsonDeserializer = _findDeserializer(paramDeserializationContext, str);
/* 110 */     paramJsonParser.nextToken();
/*     */     
/*     */ 
/* 113 */     if ((this._typeIdVisible) && (paramJsonParser.getCurrentToken() == JsonToken.START_OBJECT))
/*     */     {
/*     */ 
/* 116 */       localObject = new TokenBuffer(null);
/* 117 */       ((TokenBuffer)localObject).writeStartObject();
/* 118 */       ((TokenBuffer)localObject).writeFieldName(this._typePropertyName);
/* 119 */       ((TokenBuffer)localObject).writeString(str);
/* 120 */       paramJsonParser = JsonParserSequence.createFlattened(((TokenBuffer)localObject).asParser(paramJsonParser), paramJsonParser);
/* 121 */       paramJsonParser.nextToken();
/*     */     }
/*     */     
/* 124 */     Object localObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */     
/* 126 */     if (paramJsonParser.nextToken() != JsonToken.END_OBJECT) {
/* 127 */       throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_OBJECT, "expected closing END_OBJECT after type information and deserialized value");
/*     */     }
/*     */     
/* 130 */     return localObject;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsWrapperTypeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */