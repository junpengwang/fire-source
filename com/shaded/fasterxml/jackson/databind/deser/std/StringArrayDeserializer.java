/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ObjectBuffer;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public final class StringArrayDeserializer
/*     */   extends StdDeserializer<String[]>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = -7589512013334920693L;
/*  25 */   public static final StringArrayDeserializer instance = new StringArrayDeserializer();
/*     */   
/*     */ 
/*     */   protected JsonDeserializer<String> _elementDeserializer;
/*     */   
/*     */ 
/*     */   public StringArrayDeserializer()
/*     */   {
/*  33 */     super(String[].class);
/*  34 */     this._elementDeserializer = null;
/*     */   }
/*     */   
/*     */   protected StringArrayDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  39 */     super(String[].class);
/*  40 */     this._elementDeserializer = paramJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  48 */     if (!paramJsonParser.isExpectedStartArrayToken()) {
/*  49 */       return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */     }
/*  51 */     if (this._elementDeserializer != null) {
/*  52 */       return _deserializeCustom(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/*  55 */     ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
/*  56 */     Object[] arrayOfObject = localObjectBuffer.resetAndStart();
/*     */     
/*  58 */     int i = 0;
/*     */     
/*     */     JsonToken localJsonToken;
/*  61 */     while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */     {
/*     */ 
/*  64 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/*  65 */         localObject = paramJsonParser.getText();
/*  66 */       } else if (localJsonToken == JsonToken.VALUE_NULL) {
/*  67 */         localObject = null;
/*     */       } else {
/*  69 */         localObject = _parseString(paramJsonParser, paramDeserializationContext);
/*     */       }
/*  71 */       if (i >= arrayOfObject.length) {
/*  72 */         arrayOfObject = localObjectBuffer.appendCompletedChunk(arrayOfObject);
/*  73 */         i = 0;
/*     */       }
/*  75 */       arrayOfObject[(i++)] = localObject;
/*     */     }
/*  77 */     Object localObject = (String[])localObjectBuffer.completeAndClearBuffer(arrayOfObject, i, String.class);
/*  78 */     paramDeserializationContext.returnObjectBuffer(localObjectBuffer);
/*  79 */     return (String[])localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final String[] _deserializeCustom(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  88 */     ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
/*  89 */     Object[] arrayOfObject = localObjectBuffer.resetAndStart();
/*  90 */     JsonDeserializer localJsonDeserializer = this._elementDeserializer;
/*     */     
/*  92 */     int i = 0;
/*     */     
/*     */     JsonToken localJsonToken;
/*  95 */     while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */     {
/*  97 */       localObject = localJsonToken == JsonToken.VALUE_NULL ? null : (String)localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*  98 */       if (i >= arrayOfObject.length) {
/*  99 */         arrayOfObject = localObjectBuffer.appendCompletedChunk(arrayOfObject);
/* 100 */         i = 0;
/*     */       }
/* 102 */       arrayOfObject[(i++)] = localObject;
/*     */     }
/* 104 */     Object localObject = (String[])localObjectBuffer.completeAndClearBuffer(arrayOfObject, i, String.class);
/* 105 */     paramDeserializationContext.returnObjectBuffer(localObjectBuffer);
/* 106 */     return (String[])localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 114 */     return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */   private final String[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 121 */     if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))
/*     */     {
/* 123 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 125 */         String str = paramJsonParser.getText();
/* 126 */         if (str.length() == 0) {
/* 127 */           return null;
/*     */         }
/*     */       }
/* 130 */       throw paramDeserializationContext.mappingException(this._valueClass);
/*     */     }
/* 132 */     return new String[] { paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL ? null : _parseString(paramJsonParser, paramDeserializationContext) };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 143 */     JsonDeserializer localJsonDeserializer = this._elementDeserializer;
/*     */     
/* 145 */     localJsonDeserializer = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, localJsonDeserializer);
/* 146 */     if (localJsonDeserializer == null) {
/* 147 */       localJsonDeserializer = paramDeserializationContext.findContextualValueDeserializer(paramDeserializationContext.constructType(String.class), paramBeanProperty);
/*     */     }
/* 149 */     else if ((localJsonDeserializer instanceof ContextualDeserializer)) {
/* 150 */       localJsonDeserializer = ((ContextualDeserializer)localJsonDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/*     */ 
/* 154 */     if ((localJsonDeserializer != null) && (isDefaultDeserializer(localJsonDeserializer))) {
/* 155 */       localJsonDeserializer = null;
/*     */     }
/* 157 */     if (this._elementDeserializer != localJsonDeserializer) {
/* 158 */       return new StringArrayDeserializer(localJsonDeserializer);
/*     */     }
/* 160 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StringArrayDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */