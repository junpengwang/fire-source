/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.CreatorProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
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
/*     */ 
/*     */ public class JacksonDeserializers
/*     */ {
/*     */   @Deprecated
/*     */   public static StdDeserializer<?>[] all()
/*     */   {
/*  32 */     return new StdDeserializer[] { JavaTypeDeserializer.instance, TokenBufferDeserializer.instance };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonDeserializer<?> find(Class<?> paramClass)
/*     */   {
/*  40 */     if (paramClass == TokenBuffer.class) {
/*  41 */       return TokenBufferDeserializer.instance;
/*     */     }
/*  43 */     if (JavaType.class.isAssignableFrom(paramClass)) {
/*  44 */       return JavaTypeDeserializer.instance;
/*     */     }
/*  46 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public static ValueInstantiator findValueInstantiator(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */   {
/*  52 */     if (paramBeanDescription.getBeanClass() == JsonLocation.class) {
/*  53 */       return JsonLocationInstantiator.instance;
/*     */     }
/*  55 */     return null;
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
/*     */   public static class JavaTypeDeserializer
/*     */     extends StdScalarDeserializer<JavaType>
/*     */   {
/*  70 */     public static final JavaTypeDeserializer instance = new JavaTypeDeserializer();
/*     */     
/*  72 */     public JavaTypeDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     public JavaType deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/*  78 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */       
/*  80 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/*  81 */         String str = paramJsonParser.getText().trim();
/*  82 */         if (str.length() == 0) {
/*  83 */           return (JavaType)getEmptyValue();
/*     */         }
/*  85 */         return paramDeserializationContext.getTypeFactory().constructFromCanonical(str);
/*     */       }
/*     */       
/*  88 */       if (localJsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/*  89 */         return (JavaType)paramJsonParser.getEmbeddedObject();
/*     */       }
/*  91 */       throw paramDeserializationContext.mappingException(this._valueClass);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class JsonLocationInstantiator
/*     */     extends ValueInstantiator
/*     */   {
/* 102 */     public static final JsonLocationInstantiator instance = new JsonLocationInstantiator();
/*     */     
/*     */     public String getValueTypeDesc()
/*     */     {
/* 106 */       return JsonLocation.class.getName();
/*     */     }
/*     */     
/*     */     public boolean canCreateFromObjectWith() {
/* 110 */       return true;
/*     */     }
/*     */     
/*     */     public CreatorProperty[] getFromObjectArguments(DeserializationConfig paramDeserializationConfig) {
/* 114 */       JavaType localJavaType1 = paramDeserializationConfig.constructType(Integer.TYPE);
/* 115 */       JavaType localJavaType2 = paramDeserializationConfig.constructType(Long.TYPE);
/* 116 */       return new CreatorProperty[] { creatorProp("sourceRef", paramDeserializationConfig.constructType(Object.class), 0), creatorProp("byteOffset", localJavaType2, 1), creatorProp("charOffset", localJavaType2, 2), creatorProp("lineNr", localJavaType1, 3), creatorProp("columnNr", localJavaType1, 4) };
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private static CreatorProperty creatorProp(String paramString, JavaType paramJavaType, int paramInt)
/*     */     {
/* 126 */       return new CreatorProperty(paramString, paramJavaType, null, null, null, null, paramInt, null, true);
/*     */     }
/*     */     
/*     */ 
/*     */     public Object createFromObjectWith(DeserializationContext paramDeserializationContext, Object[] paramArrayOfObject)
/*     */     {
/* 132 */       return new JsonLocation(paramArrayOfObject[0], _long(paramArrayOfObject[1]), _long(paramArrayOfObject[2]), _int(paramArrayOfObject[3]), _int(paramArrayOfObject[4]));
/*     */     }
/*     */     
/*     */     private static final long _long(Object paramObject)
/*     */     {
/* 137 */       return paramObject == null ? 0L : ((Number)paramObject).longValue();
/*     */     }
/*     */     
/* 140 */     private static final int _int(Object paramObject) { return paramObject == null ? 0 : ((Number)paramObject).intValue(); }
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
/*     */   @JacksonStdImpl
/*     */   public static class TokenBufferDeserializer
/*     */     extends StdScalarDeserializer<TokenBuffer>
/*     */   {
/* 155 */     public static final TokenBufferDeserializer instance = new TokenBufferDeserializer();
/*     */     
/* 157 */     public TokenBufferDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     public TokenBuffer deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 163 */       TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/*     */       
/* 165 */       localTokenBuffer.copyCurrentStructure(paramJsonParser);
/* 166 */       return localTokenBuffer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/JacksonDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */