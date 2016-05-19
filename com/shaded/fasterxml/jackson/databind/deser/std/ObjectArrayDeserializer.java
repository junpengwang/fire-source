/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ObjectBuffer;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
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
/*     */ @JacksonStdImpl
/*     */ public class ObjectArrayDeserializer
/*     */   extends ContainerDeserializerBase<Object[]>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final ArrayType _arrayType;
/*     */   protected final boolean _untyped;
/*     */   protected final Class<?> _elementClass;
/*     */   protected JsonDeserializer<Object> _elementDeserializer;
/*     */   protected final TypeDeserializer _elementTypeDeserializer;
/*     */   
/*     */   public ObjectArrayDeserializer(ArrayType paramArrayType, JsonDeserializer<Object> paramJsonDeserializer, TypeDeserializer paramTypeDeserializer)
/*     */   {
/*  64 */     super(Object[].class);
/*  65 */     this._arrayType = paramArrayType;
/*  66 */     this._elementClass = paramArrayType.getContentType().getRawClass();
/*  67 */     this._untyped = (this._elementClass == Object.class);
/*  68 */     this._elementDeserializer = paramJsonDeserializer;
/*  69 */     this._elementTypeDeserializer = paramTypeDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectArrayDeserializer withDeserializer(TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  79 */     if ((paramJsonDeserializer == this._elementDeserializer) && (paramTypeDeserializer == this._elementTypeDeserializer)) {
/*  80 */       return this;
/*     */     }
/*  82 */     return new ObjectArrayDeserializer(this._arrayType, paramJsonDeserializer, paramTypeDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  90 */     JsonDeserializer localJsonDeserializer = this._elementDeserializer;
/*     */     
/*  92 */     localJsonDeserializer = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, localJsonDeserializer);
/*  93 */     if (localJsonDeserializer == null) {
/*  94 */       localJsonDeserializer = paramDeserializationContext.findContextualValueDeserializer(this._arrayType.getContentType(), paramBeanProperty);
/*     */     }
/*  96 */     else if ((localJsonDeserializer instanceof ContextualDeserializer)) {
/*  97 */       localJsonDeserializer = ((ContextualDeserializer)localJsonDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/* 100 */     TypeDeserializer localTypeDeserializer = this._elementTypeDeserializer;
/* 101 */     if (localTypeDeserializer != null) {
/* 102 */       localTypeDeserializer = localTypeDeserializer.forProperty(paramBeanProperty);
/*     */     }
/* 104 */     return withDeserializer(localTypeDeserializer, localJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType getContentType()
/*     */   {
/* 115 */     return this._arrayType.getContentType();
/*     */   }
/*     */   
/*     */   public JsonDeserializer<Object> getContentDeserializer()
/*     */   {
/* 120 */     return this._elementDeserializer;
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
/*     */   public Object[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 134 */     if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 135 */       return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/* 138 */     ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
/* 139 */     Object[] arrayOfObject = localObjectBuffer.resetAndStart();
/* 140 */     int i = 0;
/*     */     
/* 142 */     TypeDeserializer localTypeDeserializer = this._elementTypeDeserializer;
/*     */     JsonToken localJsonToken;
/* 144 */     Object localObject; while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */     {
/*     */ 
/*     */ 
/* 148 */       if (localJsonToken == JsonToken.VALUE_NULL) {
/* 149 */         localObject = null;
/* 150 */       } else if (localTypeDeserializer == null) {
/* 151 */         localObject = this._elementDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */       } else {
/* 153 */         localObject = this._elementDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */       }
/* 155 */       if (i >= arrayOfObject.length) {
/* 156 */         arrayOfObject = localObjectBuffer.appendCompletedChunk(arrayOfObject);
/* 157 */         i = 0;
/*     */       }
/* 159 */       arrayOfObject[(i++)] = localObject;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 164 */     if (this._untyped) {
/* 165 */       localObject = localObjectBuffer.completeAndClearBuffer(arrayOfObject, i);
/*     */     } else {
/* 167 */       localObject = localObjectBuffer.completeAndClearBuffer(arrayOfObject, i, this._elementClass);
/*     */     }
/* 169 */     paramDeserializationContext.returnObjectBuffer(localObjectBuffer);
/* 170 */     return (Object[])localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 181 */     return (Object[])paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Byte[] deserializeFromBase64(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 194 */     byte[] arrayOfByte = paramJsonParser.getBinaryValue(paramDeserializationContext.getBase64Variant());
/*     */     
/* 196 */     Byte[] arrayOfByte1 = new Byte[arrayOfByte.length];
/* 197 */     int i = 0; for (int j = arrayOfByte.length; i < j; i++) {
/* 198 */       arrayOfByte1[i] = Byte.valueOf(arrayOfByte[i]);
/*     */     }
/* 200 */     return arrayOfByte1;
/*     */   }
/*     */   
/*     */ 
/*     */   private final Object[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 207 */     if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */     {
/* 209 */       localObject1 = paramJsonParser.getText();
/* 210 */       if (((String)localObject1).length() == 0) {
/* 211 */         return null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 216 */     if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))
/*     */     {
/*     */ 
/*     */ 
/* 220 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (this._elementClass == Byte.class))
/*     */       {
/* 222 */         return deserializeFromBase64(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 224 */       throw paramDeserializationContext.mappingException(this._arrayType.getRawClass());
/*     */     }
/* 226 */     Object localObject1 = paramJsonParser.getCurrentToken();
/*     */     
/*     */     Object localObject2;
/* 229 */     if (localObject1 == JsonToken.VALUE_NULL) {
/* 230 */       localObject2 = null;
/* 231 */     } else if (this._elementTypeDeserializer == null) {
/* 232 */       localObject2 = this._elementDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */     } else {
/* 234 */       localObject2 = this._elementDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, this._elementTypeDeserializer);
/*     */     }
/*     */     
/*     */     Object[] arrayOfObject;
/*     */     
/* 239 */     if (this._untyped) {
/* 240 */       arrayOfObject = new Object[1];
/*     */     } else {
/* 242 */       arrayOfObject = (Object[])Array.newInstance(this._elementClass, 1);
/*     */     }
/* 244 */     arrayOfObject[0] = localObject2;
/* 245 */     return arrayOfObject;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/ObjectArrayDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */