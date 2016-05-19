/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.ByteBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.FloatBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.LongBuilder;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class PrimitiveArrayDeserializers<T> extends StdDeserializer<T>
/*     */ {
/*     */   protected PrimitiveArrayDeserializers(Class<T> paramClass)
/*     */   {
/*  19 */     super(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */   public static com.shaded.fasterxml.jackson.databind.JsonDeserializer<?> forType(Class<?> paramClass)
/*     */   {
/*  25 */     if (paramClass == Integer.TYPE) {
/*  26 */       return IntDeser.instance;
/*     */     }
/*  28 */     if (paramClass == Long.TYPE) {
/*  29 */       return LongDeser.instance;
/*     */     }
/*     */     
/*  32 */     if (paramClass == Byte.TYPE) {
/*  33 */       return new ByteDeser();
/*     */     }
/*  35 */     if (paramClass == Short.TYPE) {
/*  36 */       return new ShortDeser();
/*     */     }
/*  38 */     if (paramClass == Float.TYPE) {
/*  39 */       return new FloatDeser();
/*     */     }
/*  41 */     if (paramClass == Double.TYPE) {
/*  42 */       return new DoubleDeser();
/*     */     }
/*  44 */     if (paramClass == Boolean.TYPE) {
/*  45 */       return new BooleanDeser();
/*     */     }
/*  47 */     if (paramClass == Character.TYPE) {
/*  48 */       return new CharDeser();
/*     */     }
/*  50 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  61 */     return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class CharDeser
/*     */     extends PrimitiveArrayDeserializers<char[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */     public CharDeser()
/*     */     {
/*  76 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public char[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/*  86 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*  87 */       Object localObject; if (localJsonToken == JsonToken.VALUE_STRING)
/*     */       {
/*  89 */         localObject = paramJsonParser.getTextCharacters();
/*  90 */         int i = paramJsonParser.getTextOffset();
/*  91 */         int j = paramJsonParser.getTextLength();
/*     */         
/*  93 */         char[] arrayOfChar = new char[j];
/*  94 */         System.arraycopy(localObject, i, arrayOfChar, 0, j);
/*  95 */         return arrayOfChar;
/*     */       }
/*  97 */       if (paramJsonParser.isExpectedStartArrayToken())
/*     */       {
/*  99 */         localObject = new StringBuilder(64);
/* 100 */         while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY) {
/* 101 */           if (localJsonToken != JsonToken.VALUE_STRING) {
/* 102 */             throw paramDeserializationContext.mappingException(Character.TYPE);
/*     */           }
/* 104 */           String str = paramJsonParser.getText();
/* 105 */           if (str.length() != 1) {
/* 106 */             throw com.shaded.fasterxml.jackson.databind.JsonMappingException.from(paramJsonParser, "Can not convert a JSON String of length " + str.length() + " into a char element of char array");
/*     */           }
/* 108 */           ((StringBuilder)localObject).append(str.charAt(0));
/*     */         }
/* 110 */         return ((StringBuilder)localObject).toString().toCharArray();
/*     */       }
/*     */       
/* 113 */       if (localJsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 114 */         localObject = paramJsonParser.getEmbeddedObject();
/* 115 */         if (localObject == null) return null;
/* 116 */         if ((localObject instanceof char[])) {
/* 117 */           return (char[])localObject;
/*     */         }
/* 119 */         if ((localObject instanceof String)) {
/* 120 */           return ((String)localObject).toCharArray();
/*     */         }
/*     */         
/* 123 */         if ((localObject instanceof byte[])) {
/* 124 */           return com.shaded.fasterxml.jackson.core.Base64Variants.getDefaultVariant().encode((byte[])localObject, false).toCharArray();
/*     */         }
/*     */       }
/*     */       
/* 128 */       throw paramDeserializationContext.mappingException(this._valueClass);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class BooleanDeser
/*     */     extends PrimitiveArrayDeserializers<boolean[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */     public BooleanDeser()
/*     */     {
/* 144 */       super();
/*     */     }
/*     */     
/*     */     public boolean[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 150 */       if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 151 */         return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 153 */       com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder localBooleanBuilder = paramDeserializationContext.getArrayBuilders().getBooleanBuilder();
/* 154 */       boolean[] arrayOfBoolean = (boolean[])localBooleanBuilder.resetAndStart();
/* 155 */       int i = 0;
/*     */       
/* 157 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
/*     */       {
/* 159 */         boolean bool = _parseBooleanPrimitive(paramJsonParser, paramDeserializationContext);
/* 160 */         if (i >= arrayOfBoolean.length) {
/* 161 */           arrayOfBoolean = (boolean[])localBooleanBuilder.appendCompletedChunk(arrayOfBoolean, i);
/* 162 */           i = 0;
/*     */         }
/* 164 */         arrayOfBoolean[(i++)] = bool;
/*     */       }
/* 166 */       return (boolean[])localBooleanBuilder.completeAndClearBuffer(arrayOfBoolean, i);
/*     */     }
/*     */     
/*     */ 
/*     */     private final boolean[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 173 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 175 */         if (paramJsonParser.getText().length() == 0) {
/* 176 */           return null;
/*     */         }
/*     */       }
/* 179 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 180 */         throw paramDeserializationContext.mappingException(this._valueClass);
/*     */       }
/* 182 */       return new boolean[] { _parseBooleanPrimitive(paramJsonParser, paramDeserializationContext) };
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class ByteDeser
/*     */     extends PrimitiveArrayDeserializers<byte[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */     public ByteDeser()
/*     */     {
/* 196 */       super();
/*     */     }
/*     */     
/*     */     public byte[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 202 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */       
/*     */ 
/* 205 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/* 206 */         return paramJsonParser.getBinaryValue(paramDeserializationContext.getBase64Variant());
/*     */       }
/*     */       
/* 209 */       if (localJsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 210 */         localObject = paramJsonParser.getEmbeddedObject();
/* 211 */         if (localObject == null) return null;
/* 212 */         if ((localObject instanceof byte[])) {
/* 213 */           return (byte[])localObject;
/*     */         }
/*     */       }
/* 216 */       if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 217 */         return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 219 */       Object localObject = paramDeserializationContext.getArrayBuilders().getByteBuilder();
/* 220 */       byte[] arrayOfByte = (byte[])((ArrayBuilders.ByteBuilder)localObject).resetAndStart();
/* 221 */       int i = 0;
/*     */       
/* 223 */       while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */       {
/*     */         int j;
/* 226 */         if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT))
/*     */         {
/* 228 */           j = paramJsonParser.getByteValue();
/*     */         }
/*     */         else {
/* 231 */           if (localJsonToken != JsonToken.VALUE_NULL) {
/* 232 */             throw paramDeserializationContext.mappingException(this._valueClass.getComponentType());
/*     */           }
/* 234 */           j = 0;
/*     */         }
/* 236 */         if (i >= arrayOfByte.length) {
/* 237 */           arrayOfByte = (byte[])((ArrayBuilders.ByteBuilder)localObject).appendCompletedChunk(arrayOfByte, i);
/* 238 */           i = 0;
/*     */         }
/* 240 */         arrayOfByte[(i++)] = j;
/*     */       }
/* 242 */       return (byte[])((ArrayBuilders.ByteBuilder)localObject).completeAndClearBuffer(arrayOfByte, i);
/*     */     }
/*     */     
/*     */ 
/*     */     private final byte[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 249 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 251 */         if (paramJsonParser.getText().length() == 0) {
/* 252 */           return null;
/*     */         }
/*     */       }
/* 255 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 256 */         throw paramDeserializationContext.mappingException(this._valueClass);
/*     */       }
/*     */       
/* 259 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 260 */       int i; if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT))
/*     */       {
/* 262 */         i = paramJsonParser.getByteValue();
/*     */       }
/*     */       else {
/* 265 */         if (localJsonToken != JsonToken.VALUE_NULL) {
/* 266 */           throw paramDeserializationContext.mappingException(this._valueClass.getComponentType());
/*     */         }
/* 268 */         i = 0;
/*     */       }
/* 270 */       return new byte[] { i };
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class ShortDeser extends PrimitiveArrayDeserializers<short[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public ShortDeser() {
/* 280 */       super();
/*     */     }
/*     */     
/*     */     public short[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 286 */       if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 287 */         return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 289 */       com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.ShortBuilder localShortBuilder = paramDeserializationContext.getArrayBuilders().getShortBuilder();
/* 290 */       short[] arrayOfShort = (short[])localShortBuilder.resetAndStart();
/* 291 */       int i = 0;
/*     */       
/* 293 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 294 */         int j = _parseShortPrimitive(paramJsonParser, paramDeserializationContext);
/* 295 */         if (i >= arrayOfShort.length) {
/* 296 */           arrayOfShort = (short[])localShortBuilder.appendCompletedChunk(arrayOfShort, i);
/* 297 */           i = 0;
/*     */         }
/* 299 */         arrayOfShort[(i++)] = j;
/*     */       }
/* 301 */       return (short[])localShortBuilder.completeAndClearBuffer(arrayOfShort, i);
/*     */     }
/*     */     
/*     */ 
/*     */     private final short[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 308 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 310 */         if (paramJsonParser.getText().length() == 0) {
/* 311 */           return null;
/*     */         }
/*     */       }
/* 314 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 315 */         throw paramDeserializationContext.mappingException(this._valueClass);
/*     */       }
/* 317 */       return new short[] { _parseShortPrimitive(paramJsonParser, paramDeserializationContext) };
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class IntDeser
/*     */     extends PrimitiveArrayDeserializers<int[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 327 */     public static final IntDeser instance = new IntDeser();
/*     */     
/* 329 */     public IntDeser() { super(); }
/*     */     
/*     */ 
/*     */     public int[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 335 */       if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 336 */         return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 338 */       com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.IntBuilder localIntBuilder = paramDeserializationContext.getArrayBuilders().getIntBuilder();
/* 339 */       int[] arrayOfInt = (int[])localIntBuilder.resetAndStart();
/* 340 */       int i = 0;
/*     */       
/* 342 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
/*     */       {
/* 344 */         int j = _parseIntPrimitive(paramJsonParser, paramDeserializationContext);
/* 345 */         if (i >= arrayOfInt.length) {
/* 346 */           arrayOfInt = (int[])localIntBuilder.appendCompletedChunk(arrayOfInt, i);
/* 347 */           i = 0;
/*     */         }
/* 349 */         arrayOfInt[(i++)] = j;
/*     */       }
/* 351 */       return (int[])localIntBuilder.completeAndClearBuffer(arrayOfInt, i);
/*     */     }
/*     */     
/*     */ 
/*     */     private final int[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 358 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 360 */         if (paramJsonParser.getText().length() == 0) {
/* 361 */           return null;
/*     */         }
/*     */       }
/* 364 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 365 */         throw paramDeserializationContext.mappingException(this._valueClass);
/*     */       }
/* 367 */       return new int[] { _parseIntPrimitive(paramJsonParser, paramDeserializationContext) };
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   static final class LongDeser
/*     */     extends PrimitiveArrayDeserializers<long[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 377 */     public static final LongDeser instance = new LongDeser();
/*     */     
/* 379 */     public LongDeser() { super(); }
/*     */     
/*     */ 
/*     */     public long[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 385 */       if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 386 */         return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 388 */       ArrayBuilders.LongBuilder localLongBuilder = paramDeserializationContext.getArrayBuilders().getLongBuilder();
/* 389 */       long[] arrayOfLong = (long[])localLongBuilder.resetAndStart();
/* 390 */       int i = 0;
/*     */       
/* 392 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 393 */         long l = _parseLongPrimitive(paramJsonParser, paramDeserializationContext);
/* 394 */         if (i >= arrayOfLong.length) {
/* 395 */           arrayOfLong = (long[])localLongBuilder.appendCompletedChunk(arrayOfLong, i);
/* 396 */           i = 0;
/*     */         }
/* 398 */         arrayOfLong[(i++)] = l;
/*     */       }
/* 400 */       return (long[])localLongBuilder.completeAndClearBuffer(arrayOfLong, i);
/*     */     }
/*     */     
/*     */ 
/*     */     private final long[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 407 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 409 */         if (paramJsonParser.getText().length() == 0) {
/* 410 */           return null;
/*     */         }
/*     */       }
/* 413 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 414 */         throw paramDeserializationContext.mappingException(this._valueClass);
/*     */       }
/* 416 */       return new long[] { _parseLongPrimitive(paramJsonParser, paramDeserializationContext) };
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class FloatDeser extends PrimitiveArrayDeserializers<float[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public FloatDeser() {
/* 426 */       super();
/*     */     }
/*     */     
/*     */     public float[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 432 */       if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 433 */         return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 435 */       ArrayBuilders.FloatBuilder localFloatBuilder = paramDeserializationContext.getArrayBuilders().getFloatBuilder();
/* 436 */       float[] arrayOfFloat = (float[])localFloatBuilder.resetAndStart();
/* 437 */       int i = 0;
/*     */       
/* 439 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
/*     */       {
/* 441 */         float f = _parseFloatPrimitive(paramJsonParser, paramDeserializationContext);
/* 442 */         if (i >= arrayOfFloat.length) {
/* 443 */           arrayOfFloat = (float[])localFloatBuilder.appendCompletedChunk(arrayOfFloat, i);
/* 444 */           i = 0;
/*     */         }
/* 446 */         arrayOfFloat[(i++)] = f;
/*     */       }
/* 448 */       return (float[])localFloatBuilder.completeAndClearBuffer(arrayOfFloat, i);
/*     */     }
/*     */     
/*     */ 
/*     */     private final float[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 455 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 457 */         if (paramJsonParser.getText().length() == 0) {
/* 458 */           return null;
/*     */         }
/*     */       }
/* 461 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 462 */         throw paramDeserializationContext.mappingException(this._valueClass);
/*     */       }
/* 464 */       return new float[] { _parseFloatPrimitive(paramJsonParser, paramDeserializationContext) };
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   static final class DoubleDeser extends PrimitiveArrayDeserializers<double[]>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public DoubleDeser() {
/* 474 */       super();
/*     */     }
/*     */     
/*     */     public double[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 480 */       if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 481 */         return handleNonArray(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 483 */       com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.DoubleBuilder localDoubleBuilder = paramDeserializationContext.getArrayBuilders().getDoubleBuilder();
/* 484 */       double[] arrayOfDouble = (double[])localDoubleBuilder.resetAndStart();
/* 485 */       int i = 0;
/*     */       
/* 487 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 488 */         double d = _parseDoublePrimitive(paramJsonParser, paramDeserializationContext);
/* 489 */         if (i >= arrayOfDouble.length) {
/* 490 */           arrayOfDouble = (double[])localDoubleBuilder.appendCompletedChunk(arrayOfDouble, i);
/* 491 */           i = 0;
/*     */         }
/* 493 */         arrayOfDouble[(i++)] = d;
/*     */       }
/* 495 */       return (double[])localDoubleBuilder.completeAndClearBuffer(arrayOfDouble, i);
/*     */     }
/*     */     
/*     */ 
/*     */     private final double[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 502 */       if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)))
/*     */       {
/* 504 */         if (paramJsonParser.getText().length() == 0) {
/* 505 */           return null;
/*     */         }
/*     */       }
/* 508 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 509 */         throw paramDeserializationContext.mappingException(this._valueClass);
/*     */       }
/* 511 */       return new double[] { _parseDoublePrimitive(paramJsonParser, paramDeserializationContext) };
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/PrimitiveArrayDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */