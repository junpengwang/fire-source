/*     */ package com.shaded.fasterxml.jackson.core.base;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator.Feature;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*     */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*     */ import com.shaded.fasterxml.jackson.core.TreeNode;
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.core.json.JsonWriteContext;
/*     */ import com.shaded.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*     */ import com.shaded.fasterxml.jackson.core.util.VersionUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GeneratorBase
/*     */   extends JsonGenerator
/*     */ {
/*     */   protected ObjectCodec _objectCodec;
/*     */   protected int _features;
/*     */   protected boolean _cfgNumbersAsStrings;
/*     */   protected JsonWriteContext _writeContext;
/*     */   protected boolean _closed;
/*     */   
/*     */   protected GeneratorBase(int paramInt, ObjectCodec paramObjectCodec)
/*     */   {
/*  73 */     this._features = paramInt;
/*  74 */     this._writeContext = JsonWriteContext.createRootContext();
/*  75 */     this._objectCodec = paramObjectCodec;
/*  76 */     this._cfgNumbersAsStrings = isEnabled(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Version version()
/*     */   {
/*  85 */     return VersionUtil.versionFor(getClass());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonGenerator enable(JsonGenerator.Feature paramFeature)
/*     */   {
/*  96 */     this._features |= paramFeature.getMask();
/*  97 */     if (paramFeature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
/*  98 */       this._cfgNumbersAsStrings = true;
/*  99 */     } else if (paramFeature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
/* 100 */       setHighestNonEscapedChar(127);
/*     */     }
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public JsonGenerator disable(JsonGenerator.Feature paramFeature)
/*     */   {
/* 107 */     this._features &= (paramFeature.getMask() ^ 0xFFFFFFFF);
/* 108 */     if (paramFeature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
/* 109 */       this._cfgNumbersAsStrings = false;
/* 110 */     } else if (paramFeature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
/* 111 */       setHighestNonEscapedChar(0);
/*     */     }
/* 113 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final boolean isEnabled(JsonGenerator.Feature paramFeature)
/*     */   {
/* 120 */     return (this._features & paramFeature.getMask()) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonGenerator useDefaultPrettyPrinter()
/*     */   {
/* 128 */     if (getPrettyPrinter() != null) {
/* 129 */       return this;
/*     */     }
/* 131 */     return setPrettyPrinter(new DefaultPrettyPrinter());
/*     */   }
/*     */   
/*     */   public JsonGenerator setCodec(ObjectCodec paramObjectCodec)
/*     */   {
/* 136 */     this._objectCodec = paramObjectCodec;
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public final ObjectCodec getCodec() {
/* 141 */     return this._objectCodec;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonWriteContext getOutputContext()
/*     */   {
/* 153 */     return this._writeContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeFieldName(SerializableString paramSerializableString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 174 */     writeFieldName(paramSerializableString.getValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeString(SerializableString paramSerializableString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 187 */     writeString(paramSerializableString.getValue());
/*     */   }
/*     */   
/*     */   public void writeRawValue(String paramString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 193 */     _verifyValueWrite("write raw value");
/* 194 */     writeRaw(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeRawValue(String paramString, int paramInt1, int paramInt2)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 201 */     _verifyValueWrite("write raw value");
/* 202 */     writeRaw(paramString, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeRawValue(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 209 */     _verifyValueWrite("write raw value");
/* 210 */     writeRaw(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public int writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 217 */     _reportUnsupportedOperation();
/* 218 */     return 0;
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
/*     */   public void writeObject(Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 249 */     if (paramObject == null)
/*     */     {
/* 251 */       writeNull();
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 258 */       if (this._objectCodec != null) {
/* 259 */         this._objectCodec.writeValue(this, paramObject);
/* 260 */         return;
/*     */       }
/* 262 */       _writeSimpleObject(paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeTree(TreeNode paramTreeNode)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 271 */     if (paramTreeNode == null) {
/* 272 */       writeNull();
/*     */     } else {
/* 274 */       if (this._objectCodec == null) {
/* 275 */         throw new IllegalStateException("No ObjectCodec defined");
/*     */       }
/* 277 */       this._objectCodec.writeValue(this, paramTreeNode);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void flush()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 293 */     this._closed = true;
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 297 */     return this._closed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void copyCurrentEvent(JsonParser paramJsonParser)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 309 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 311 */     if (localJsonToken == null) {
/* 312 */       _reportError("No current event to copy");
/*     */     }
/* 314 */     switch (localJsonToken) {
/*     */     case START_OBJECT: 
/* 316 */       writeStartObject();
/* 317 */       break;
/*     */     case END_OBJECT: 
/* 319 */       writeEndObject();
/* 320 */       break;
/*     */     case START_ARRAY: 
/* 322 */       writeStartArray();
/* 323 */       break;
/*     */     case END_ARRAY: 
/* 325 */       writeEndArray();
/* 326 */       break;
/*     */     case FIELD_NAME: 
/* 328 */       writeFieldName(paramJsonParser.getCurrentName());
/* 329 */       break;
/*     */     case VALUE_STRING: 
/* 331 */       if (paramJsonParser.hasTextCharacters()) {
/* 332 */         writeString(paramJsonParser.getTextCharacters(), paramJsonParser.getTextOffset(), paramJsonParser.getTextLength());
/*     */       } else {
/* 334 */         writeString(paramJsonParser.getText());
/*     */       }
/* 336 */       break;
/*     */     case VALUE_NUMBER_INT: 
/* 338 */       switch (paramJsonParser.getNumberType()) {
/*     */       case INT: 
/* 340 */         writeNumber(paramJsonParser.getIntValue());
/* 341 */         break;
/*     */       case BIG_INTEGER: 
/* 343 */         writeNumber(paramJsonParser.getBigIntegerValue());
/* 344 */         break;
/*     */       default: 
/* 346 */         writeNumber(paramJsonParser.getLongValue());
/*     */       }
/* 348 */       break;
/*     */     case VALUE_NUMBER_FLOAT: 
/* 350 */       switch (paramJsonParser.getNumberType()) {
/*     */       case BIG_DECIMAL: 
/* 352 */         writeNumber(paramJsonParser.getDecimalValue());
/* 353 */         break;
/*     */       case FLOAT: 
/* 355 */         writeNumber(paramJsonParser.getFloatValue());
/* 356 */         break;
/*     */       default: 
/* 358 */         writeNumber(paramJsonParser.getDoubleValue());
/*     */       }
/* 360 */       break;
/*     */     case VALUE_TRUE: 
/* 362 */       writeBoolean(true);
/* 363 */       break;
/*     */     case VALUE_FALSE: 
/* 365 */       writeBoolean(false);
/* 366 */       break;
/*     */     case VALUE_NULL: 
/* 368 */       writeNull();
/* 369 */       break;
/*     */     case VALUE_EMBEDDED_OBJECT: 
/* 371 */       writeObject(paramJsonParser.getEmbeddedObject());
/* 372 */       break;
/*     */     default: 
/* 374 */       _throwInternal();
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public final void copyCurrentStructure(JsonParser paramJsonParser)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 382 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/*     */ 
/* 385 */     if (localJsonToken == JsonToken.FIELD_NAME) {
/* 386 */       writeFieldName(paramJsonParser.getCurrentName());
/* 387 */       localJsonToken = paramJsonParser.nextToken();
/*     */     }
/*     */     
/*     */ 
/* 391 */     switch (localJsonToken) {
/*     */     case START_ARRAY: 
/* 393 */       writeStartArray();
/* 394 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/* 395 */         copyCurrentStructure(paramJsonParser);
/*     */       }
/* 397 */       writeEndArray();
/* 398 */       break;
/*     */     case START_OBJECT: 
/* 400 */       writeStartObject();
/* 401 */       while (paramJsonParser.nextToken() != JsonToken.END_OBJECT) {
/* 402 */         copyCurrentStructure(paramJsonParser);
/*     */       }
/* 404 */       writeEndObject();
/* 405 */       break;
/*     */     default: 
/* 407 */       copyCurrentEvent(paramJsonParser);
/*     */     }
/*     */     
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
/*     */   protected abstract void _releaseBuffers();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void _verifyValueWrite(String paramString)
/*     */     throws IOException, JsonGenerationException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _reportError(String paramString)
/*     */     throws JsonGenerationException
/*     */   {
/* 444 */     throw new JsonGenerationException(paramString);
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
/*     */   protected void _writeSimpleObject(Object paramObject)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 461 */     if (paramObject == null) {
/* 462 */       writeNull();
/* 463 */       return;
/*     */     }
/* 465 */     if ((paramObject instanceof String)) {
/* 466 */       writeString((String)paramObject);
/* 467 */       return;
/*     */     }
/* 469 */     if ((paramObject instanceof Number)) {
/* 470 */       Number localNumber = (Number)paramObject;
/* 471 */       if ((localNumber instanceof Integer)) {
/* 472 */         writeNumber(localNumber.intValue());
/* 473 */         return; }
/* 474 */       if ((localNumber instanceof Long)) {
/* 475 */         writeNumber(localNumber.longValue());
/* 476 */         return; }
/* 477 */       if ((localNumber instanceof Double)) {
/* 478 */         writeNumber(localNumber.doubleValue());
/* 479 */         return; }
/* 480 */       if ((localNumber instanceof Float)) {
/* 481 */         writeNumber(localNumber.floatValue());
/* 482 */         return; }
/* 483 */       if ((localNumber instanceof Short)) {
/* 484 */         writeNumber(localNumber.shortValue());
/* 485 */         return; }
/* 486 */       if ((localNumber instanceof Byte)) {
/* 487 */         writeNumber((short)localNumber.byteValue());
/* 488 */         return; }
/* 489 */       if ((localNumber instanceof BigInteger)) {
/* 490 */         writeNumber((BigInteger)localNumber);
/* 491 */         return; }
/* 492 */       if ((localNumber instanceof BigDecimal)) {
/* 493 */         writeNumber((BigDecimal)localNumber);
/* 494 */         return;
/*     */       }
/*     */       
/*     */ 
/* 498 */       if ((localNumber instanceof AtomicInteger)) {
/* 499 */         writeNumber(((AtomicInteger)localNumber).get());
/* 500 */         return; }
/* 501 */       if ((localNumber instanceof AtomicLong)) {
/* 502 */         writeNumber(((AtomicLong)localNumber).get());
/* 503 */         return;
/*     */       }
/* 505 */     } else { if ((paramObject instanceof byte[])) {
/* 506 */         writeBinary((byte[])paramObject);
/* 507 */         return; }
/* 508 */       if ((paramObject instanceof Boolean)) {
/* 509 */         writeBoolean(((Boolean)paramObject).booleanValue());
/* 510 */         return; }
/* 511 */       if ((paramObject instanceof AtomicBoolean)) {
/* 512 */         writeBoolean(((AtomicBoolean)paramObject).get());
/* 513 */         return;
/*     */       } }
/* 515 */     throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + paramObject.getClass().getName() + ")");
/*     */   }
/*     */   
/*     */ 
/*     */   protected final void _throwInternal() {}
/*     */   
/*     */ 
/*     */   protected void _reportUnsupportedOperation()
/*     */   {
/* 524 */     throw new UnsupportedOperationException("Operation not supported by generator of type " + getClass().getName());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/base/GeneratorBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */