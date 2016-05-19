/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ObjectBuffer;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ public class UntypedObjectDeserializer
/*     */   extends StdDeserializer<Object>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  30 */   private static final Object[] NO_OBJECTS = new Object[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  35 */   public static final UntypedObjectDeserializer instance = new UntypedObjectDeserializer();
/*     */   
/*  37 */   public UntypedObjectDeserializer() { super(Object.class); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  49 */     switch (paramJsonParser.getCurrentToken()) {
/*     */     case START_OBJECT: 
/*  51 */       return mapObject(paramJsonParser, paramDeserializationContext);
/*     */     case START_ARRAY: 
/*  53 */       return mapArray(paramJsonParser, paramDeserializationContext);
/*     */     case FIELD_NAME: 
/*  55 */       return mapObject(paramJsonParser, paramDeserializationContext);
/*     */     case VALUE_EMBEDDED_OBJECT: 
/*  57 */       return paramJsonParser.getEmbeddedObject();
/*     */     case VALUE_STRING: 
/*  59 */       return paramJsonParser.getText();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case VALUE_NUMBER_INT: 
/*  65 */       if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
/*  66 */         return paramJsonParser.getBigIntegerValue();
/*     */       }
/*  68 */       return paramJsonParser.getNumberValue();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case VALUE_NUMBER_FLOAT: 
/*  74 */       if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/*  75 */         return paramJsonParser.getDecimalValue();
/*     */       }
/*  77 */       return Double.valueOf(paramJsonParser.getDoubleValue());
/*     */     
/*     */     case VALUE_TRUE: 
/*  80 */       return Boolean.TRUE;
/*     */     case VALUE_FALSE: 
/*  82 */       return Boolean.FALSE;
/*     */     
/*     */     case VALUE_NULL: 
/*  85 */       return null;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*  90 */     throw paramDeserializationContext.mappingException(Object.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  99 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 100 */     switch (localJsonToken)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */     case START_OBJECT: 
/*     */     case START_ARRAY: 
/*     */     case FIELD_NAME: 
/* 108 */       return paramTypeDeserializer.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case VALUE_STRING: 
/* 114 */       return paramJsonParser.getText();
/*     */     
/*     */ 
/*     */     case VALUE_NUMBER_INT: 
/* 118 */       if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
/* 119 */         return paramJsonParser.getBigIntegerValue();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 125 */       return paramJsonParser.getNumberValue();
/*     */     
/*     */ 
/*     */     case VALUE_NUMBER_FLOAT: 
/* 129 */       if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 130 */         return paramJsonParser.getDecimalValue();
/*     */       }
/* 132 */       return Double.valueOf(paramJsonParser.getDoubleValue());
/*     */     
/*     */     case VALUE_TRUE: 
/* 135 */       return Boolean.TRUE;
/*     */     case VALUE_FALSE: 
/* 137 */       return Boolean.FALSE;
/*     */     case VALUE_EMBEDDED_OBJECT: 
/* 139 */       return paramJsonParser.getEmbeddedObject();
/*     */     
/*     */     case VALUE_NULL: 
/* 142 */       return null; }
/*     */     
/* 144 */     throw paramDeserializationContext.mappingException(Object.class);
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
/*     */   protected Object mapArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 160 */     if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
/* 161 */       return mapArrayToArray(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/* 164 */     if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 165 */       return new ArrayList(4);
/*     */     }
/* 167 */     ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
/* 168 */     Object[] arrayOfObject = localObjectBuffer.resetAndStart();
/* 169 */     int i = 0;
/* 170 */     int j = 0;
/*     */     do {
/* 172 */       localObject = deserialize(paramJsonParser, paramDeserializationContext);
/* 173 */       j++;
/* 174 */       if (i >= arrayOfObject.length) {
/* 175 */         arrayOfObject = localObjectBuffer.appendCompletedChunk(arrayOfObject);
/* 176 */         i = 0;
/*     */       }
/* 178 */       arrayOfObject[(i++)] = localObject;
/* 179 */     } while (paramJsonParser.nextToken() != JsonToken.END_ARRAY);
/*     */     
/* 181 */     Object localObject = new ArrayList(j + (j >> 3) + 1);
/* 182 */     localObjectBuffer.completeAndClearBuffer(arrayOfObject, i, (List)localObject);
/* 183 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object mapObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 192 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 193 */     if (localJsonToken == JsonToken.START_OBJECT) {
/* 194 */       localJsonToken = paramJsonParser.nextToken();
/*     */     }
/*     */     
/* 197 */     if (localJsonToken != JsonToken.FIELD_NAME)
/*     */     {
/* 199 */       return new LinkedHashMap(4);
/*     */     }
/* 201 */     String str1 = paramJsonParser.getText();
/* 202 */     paramJsonParser.nextToken();
/* 203 */     Object localObject1 = deserialize(paramJsonParser, paramDeserializationContext);
/* 204 */     if (paramJsonParser.nextToken() != JsonToken.FIELD_NAME) {
/* 205 */       localObject2 = new LinkedHashMap(4);
/* 206 */       ((LinkedHashMap)localObject2).put(str1, localObject1);
/* 207 */       return localObject2;
/*     */     }
/* 209 */     Object localObject2 = paramJsonParser.getText();
/* 210 */     paramJsonParser.nextToken();
/* 211 */     Object localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
/* 212 */     if (paramJsonParser.nextToken() != JsonToken.FIELD_NAME) {
/* 213 */       localLinkedHashMap = new LinkedHashMap(4);
/* 214 */       localLinkedHashMap.put(str1, localObject1);
/* 215 */       localLinkedHashMap.put(localObject2, localObject3);
/* 216 */       return localLinkedHashMap;
/*     */     }
/*     */     
/* 219 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/* 220 */     localLinkedHashMap.put(str1, localObject1);
/* 221 */     localLinkedHashMap.put(localObject2, localObject3);
/*     */     do {
/* 223 */       String str2 = paramJsonParser.getText();
/* 224 */       paramJsonParser.nextToken();
/* 225 */       localLinkedHashMap.put(str2, deserialize(paramJsonParser, paramDeserializationContext));
/* 226 */     } while (paramJsonParser.nextToken() != JsonToken.END_OBJECT);
/* 227 */     return localLinkedHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object[] mapArrayToArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 237 */     if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
/* 238 */       return NO_OBJECTS;
/*     */     }
/* 240 */     ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
/* 241 */     Object[] arrayOfObject = localObjectBuffer.resetAndStart();
/* 242 */     int i = 0;
/*     */     do {
/* 244 */       Object localObject = deserialize(paramJsonParser, paramDeserializationContext);
/* 245 */       if (i >= arrayOfObject.length) {
/* 246 */         arrayOfObject = localObjectBuffer.appendCompletedChunk(arrayOfObject);
/* 247 */         i = 0;
/*     */       }
/* 249 */       arrayOfObject[(i++)] = localObject;
/* 250 */     } while (paramJsonParser.nextToken() != JsonToken.END_ARRAY);
/* 251 */     return localObjectBuffer.completeAndClearBuffer(arrayOfObject, i);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/UntypedObjectDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */