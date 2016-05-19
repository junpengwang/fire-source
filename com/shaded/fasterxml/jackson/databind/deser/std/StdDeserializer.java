/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.io.NumberInput;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StdDeserializer<T>
/*     */   extends JsonDeserializer<T>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Class<?> _valueClass;
/*     */   
/*     */   protected StdDeserializer(Class<?> paramClass)
/*     */   {
/*  35 */     this._valueClass = paramClass;
/*     */   }
/*     */   
/*     */   protected StdDeserializer(JavaType paramJavaType) {
/*  39 */     this._valueClass = (paramJavaType == null ? null : paramJavaType.getRawClass());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getValueClass()
/*     */   {
/*  48 */     return this._valueClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType getValueType()
/*     */   {
/*  55 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isDefaultDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  64 */     return (paramJsonDeserializer != null) && (paramJsonDeserializer.getClass().getAnnotation(JacksonStdImpl.class) != null);
/*     */   }
/*     */   
/*     */   protected boolean isDefaultKeyDeserializer(KeyDeserializer paramKeyDeserializer) {
/*  68 */     return (paramKeyDeserializer != null) && (paramKeyDeserializer.getClass().getAnnotation(JacksonStdImpl.class) != null);
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
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  87 */     return paramTypeDeserializer.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
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
/*     */   protected final boolean _parseBooleanPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 101 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 102 */     if (localJsonToken == JsonToken.VALUE_TRUE) {
/* 103 */       return true;
/*     */     }
/* 105 */     if (localJsonToken == JsonToken.VALUE_FALSE) {
/* 106 */       return false;
/*     */     }
/* 108 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     if (localJsonToken == JsonToken.VALUE_NUMBER_INT)
/*     */     {
/* 114 */       if (paramJsonParser.getNumberType() == JsonParser.NumberType.INT) {
/* 115 */         return paramJsonParser.getIntValue() != 0;
/*     */       }
/* 117 */       return _parseBooleanFromNumber(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/* 120 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 121 */       String str = paramJsonParser.getText().trim();
/* 122 */       if ("true".equals(str)) {
/* 123 */         return true;
/*     */       }
/* 125 */       if (("false".equals(str)) || (str.length() == 0)) {
/* 126 */         return Boolean.FALSE.booleanValue();
/*     */       }
/* 128 */       throw paramDeserializationContext.weirdStringException(str, this._valueClass, "only \"true\" or \"false\" recognized");
/*     */     }
/*     */     
/* 131 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final Boolean _parseBoolean(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 137 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 138 */     if (localJsonToken == JsonToken.VALUE_TRUE) {
/* 139 */       return Boolean.TRUE;
/*     */     }
/* 141 */     if (localJsonToken == JsonToken.VALUE_FALSE) {
/* 142 */       return Boolean.FALSE;
/*     */     }
/*     */     
/* 145 */     if (localJsonToken == JsonToken.VALUE_NUMBER_INT)
/*     */     {
/* 147 */       if (paramJsonParser.getNumberType() == JsonParser.NumberType.INT) {
/* 148 */         return paramJsonParser.getIntValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
/*     */       }
/* 150 */       return Boolean.valueOf(_parseBooleanFromNumber(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 152 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 153 */       return (Boolean)getNullValue();
/*     */     }
/*     */     
/* 156 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 157 */       String str = paramJsonParser.getText().trim();
/* 158 */       if ("true".equals(str)) {
/* 159 */         return Boolean.TRUE;
/*     */       }
/* 161 */       if ("false".equals(str)) {
/* 162 */         return Boolean.FALSE;
/*     */       }
/* 164 */       if (str.length() == 0) {
/* 165 */         return (Boolean)getEmptyValue();
/*     */       }
/* 167 */       throw paramDeserializationContext.weirdStringException(str, this._valueClass, "only \"true\" or \"false\" recognized");
/*     */     }
/*     */     
/* 170 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final boolean _parseBooleanFromNumber(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 176 */     if (paramJsonParser.getNumberType() == JsonParser.NumberType.LONG) {
/* 177 */       return (paramJsonParser.getLongValue() == 0L ? Boolean.FALSE : Boolean.TRUE).booleanValue();
/*     */     }
/*     */     
/* 180 */     String str = paramJsonParser.getText();
/* 181 */     if (("0.0".equals(str)) || ("0".equals(str))) {
/* 182 */       return Boolean.FALSE.booleanValue();
/*     */     }
/* 184 */     return Boolean.TRUE.booleanValue();
/*     */   }
/*     */   
/*     */   protected Byte _parseByte(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 190 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 191 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 192 */       return Byte.valueOf(paramJsonParser.getByteValue());
/*     */     }
/* 194 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 195 */       String str = paramJsonParser.getText().trim();
/*     */       int j;
/*     */       try {
/* 198 */         int i = str.length();
/* 199 */         if (i == 0) {
/* 200 */           return (Byte)getEmptyValue();
/*     */         }
/* 202 */         j = NumberInput.parseInt(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 204 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid Byte value");
/*     */       }
/*     */       
/*     */ 
/* 208 */       if ((j < -128) || (j > 255)) {
/* 209 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "overflow, value can not be represented as 8-bit value");
/*     */       }
/* 211 */       return Byte.valueOf((byte)j);
/*     */     }
/* 213 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 214 */       return (Byte)getNullValue();
/*     */     }
/* 216 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected Short _parseShort(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 222 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 223 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 224 */       return Short.valueOf(paramJsonParser.getShortValue());
/*     */     }
/* 226 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 227 */       String str = paramJsonParser.getText().trim();
/*     */       int j;
/*     */       try {
/* 230 */         int i = str.length();
/* 231 */         if (i == 0) {
/* 232 */           return (Short)getEmptyValue();
/*     */         }
/* 234 */         j = NumberInput.parseInt(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 236 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid Short value");
/*     */       }
/*     */       
/* 239 */       if ((j < 32768) || (j > 32767)) {
/* 240 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "overflow, value can not be represented as 16-bit value");
/*     */       }
/* 242 */       return Short.valueOf((short)j);
/*     */     }
/* 244 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 245 */       return (Short)getNullValue();
/*     */     }
/* 247 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final short _parseShortPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 253 */     int i = _parseIntPrimitive(paramJsonParser, paramDeserializationContext);
/*     */     
/* 255 */     if ((i < 32768) || (i > 32767)) {
/* 256 */       throw paramDeserializationContext.weirdStringException(String.valueOf(i), this._valueClass, "overflow, value can not be represented as 16-bit value");
/*     */     }
/*     */     
/* 259 */     return (short)i;
/*     */   }
/*     */   
/*     */   protected final int _parseIntPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 265 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/*     */ 
/* 268 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 269 */       return paramJsonParser.getIntValue();
/*     */     }
/* 271 */     if (localJsonToken == JsonToken.VALUE_STRING)
/*     */     {
/*     */ 
/*     */ 
/* 275 */       String str = paramJsonParser.getText().trim();
/*     */       try {
/* 277 */         int i = str.length();
/* 278 */         if (i > 9) {
/* 279 */           long l = Long.parseLong(str);
/* 280 */           if ((l < -2147483648L) || (l > 2147483647L)) {
/* 281 */             throw paramDeserializationContext.weirdStringException(str, this._valueClass, "Overflow: numeric value (" + str + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
/*     */           }
/*     */           
/* 284 */           return (int)l;
/*     */         }
/* 286 */         if (i == 0) {
/* 287 */           return 0;
/*     */         }
/* 289 */         return NumberInput.parseInt(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 291 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid int value");
/*     */       }
/*     */     }
/* 294 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 295 */       return 0;
/*     */     }
/*     */     
/* 298 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final Integer _parseInteger(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 304 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 305 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 306 */       return Integer.valueOf(paramJsonParser.getIntValue());
/*     */     }
/* 308 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 309 */       String str = paramJsonParser.getText().trim();
/*     */       try {
/* 311 */         int i = str.length();
/* 312 */         if (i > 9) {
/* 313 */           long l = Long.parseLong(str);
/* 314 */           if ((l < -2147483648L) || (l > 2147483647L)) {
/* 315 */             throw paramDeserializationContext.weirdStringException(str, this._valueClass, "Overflow: numeric value (" + str + ") out of range of Integer (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
/*     */           }
/*     */           
/* 318 */           return Integer.valueOf((int)l);
/*     */         }
/* 320 */         if (i == 0) {
/* 321 */           return (Integer)getEmptyValue();
/*     */         }
/* 323 */         return Integer.valueOf(NumberInput.parseInt(str));
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 325 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid Integer value");
/*     */       }
/*     */     }
/* 328 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 329 */       return (Integer)getNullValue();
/*     */     }
/*     */     
/* 332 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final Long _parseLong(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 338 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/*     */ 
/* 341 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 342 */       return Long.valueOf(paramJsonParser.getLongValue());
/*     */     }
/*     */     
/* 345 */     if (localJsonToken == JsonToken.VALUE_STRING)
/*     */     {
/* 347 */       String str = paramJsonParser.getText().trim();
/* 348 */       if (str.length() == 0) {
/* 349 */         return (Long)getEmptyValue();
/*     */       }
/*     */       try {
/* 352 */         return Long.valueOf(NumberInput.parseLong(str));
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 354 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid Long value");
/*     */       } }
/* 356 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 357 */       return (Long)getNullValue();
/*     */     }
/*     */     
/* 360 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final long _parseLongPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 366 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 367 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 368 */       return paramJsonParser.getLongValue();
/*     */     }
/* 370 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 371 */       String str = paramJsonParser.getText().trim();
/* 372 */       if (str.length() == 0) {
/* 373 */         return 0L;
/*     */       }
/*     */       try {
/* 376 */         return NumberInput.parseLong(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 378 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid long value");
/*     */       } }
/* 380 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 381 */       return 0L;
/*     */     }
/* 383 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */ 
/*     */   protected final Float _parseFloat(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 390 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 392 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 393 */       return Float.valueOf(paramJsonParser.getFloatValue());
/*     */     }
/*     */     
/* 396 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 397 */       String str = paramJsonParser.getText().trim();
/* 398 */       if (str.length() == 0) {
/* 399 */         return (Float)getEmptyValue();
/*     */       }
/* 401 */       switch (str.charAt(0)) {
/*     */       case 'I': 
/* 403 */         if (("Infinity".equals(str)) || ("INF".equals(str))) {
/* 404 */           return Float.valueOf(Float.POSITIVE_INFINITY);
/*     */         }
/*     */         break;
/*     */       case 'N': 
/* 408 */         if ("NaN".equals(str)) {
/* 409 */           return Float.valueOf(NaN.0F);
/*     */         }
/*     */         break;
/*     */       case '-': 
/* 413 */         if (("-Infinity".equals(str)) || ("-INF".equals(str))) {
/* 414 */           return Float.valueOf(Float.NEGATIVE_INFINITY);
/*     */         }
/*     */         break;
/*     */       }
/*     */       try {
/* 419 */         return Float.valueOf(Float.parseFloat(str));
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 421 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid Float value");
/*     */       } }
/* 423 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 424 */       return (Float)getNullValue();
/*     */     }
/*     */     
/* 427 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final float _parseFloatPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 433 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 435 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 436 */       return paramJsonParser.getFloatValue();
/*     */     }
/* 438 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 439 */       String str = paramJsonParser.getText().trim();
/* 440 */       if (str.length() == 0) {
/* 441 */         return 0.0F;
/*     */       }
/* 443 */       switch (str.charAt(0)) {
/*     */       case 'I': 
/* 445 */         if (("Infinity".equals(str)) || ("INF".equals(str))) {
/* 446 */           return Float.POSITIVE_INFINITY;
/*     */         }
/*     */         break;
/*     */       case 'N': 
/* 450 */         if ("NaN".equals(str)) {
/* 451 */           return NaN.0F;
/*     */         }
/*     */         break;
/*     */       case '-': 
/* 455 */         if (("-Infinity".equals(str)) || ("-INF".equals(str))) {
/* 456 */           return Float.NEGATIVE_INFINITY;
/*     */         }
/*     */         break;
/*     */       }
/*     */       try {
/* 461 */         return Float.parseFloat(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 463 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid float value");
/*     */       } }
/* 465 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 466 */       return 0.0F;
/*     */     }
/*     */     
/* 469 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected final Double _parseDouble(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 475 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 477 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 478 */       return Double.valueOf(paramJsonParser.getDoubleValue());
/*     */     }
/* 480 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 481 */       String str = paramJsonParser.getText().trim();
/* 482 */       if (str.length() == 0) {
/* 483 */         return (Double)getEmptyValue();
/*     */       }
/* 485 */       switch (str.charAt(0)) {
/*     */       case 'I': 
/* 487 */         if (("Infinity".equals(str)) || ("INF".equals(str))) {
/* 488 */           return Double.valueOf(Double.POSITIVE_INFINITY);
/*     */         }
/*     */         break;
/*     */       case 'N': 
/* 492 */         if ("NaN".equals(str)) {
/* 493 */           return Double.valueOf(NaN.0D);
/*     */         }
/*     */         break;
/*     */       case '-': 
/* 497 */         if (("-Infinity".equals(str)) || ("-INF".equals(str))) {
/* 498 */           return Double.valueOf(Double.NEGATIVE_INFINITY);
/*     */         }
/*     */         break;
/*     */       }
/*     */       try {
/* 503 */         return Double.valueOf(parseDouble(str));
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 505 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid Double value");
/*     */       } }
/* 507 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 508 */       return (Double)getNullValue();
/*     */     }
/*     */     
/* 511 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */ 
/*     */   protected final double _parseDoublePrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 518 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 520 */     if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 521 */       return paramJsonParser.getDoubleValue();
/*     */     }
/*     */     
/* 524 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 525 */       String str = paramJsonParser.getText().trim();
/* 526 */       if (str.length() == 0) {
/* 527 */         return 0.0D;
/*     */       }
/* 529 */       switch (str.charAt(0)) {
/*     */       case 'I': 
/* 531 */         if (("Infinity".equals(str)) || ("INF".equals(str))) {
/* 532 */           return Double.POSITIVE_INFINITY;
/*     */         }
/*     */         break;
/*     */       case 'N': 
/* 536 */         if ("NaN".equals(str)) {
/* 537 */           return NaN.0D;
/*     */         }
/*     */         break;
/*     */       case '-': 
/* 541 */         if (("-Infinity".equals(str)) || ("-INF".equals(str))) {
/* 542 */           return Double.NEGATIVE_INFINITY;
/*     */         }
/*     */         break;
/*     */       }
/*     */       try {
/* 547 */         return parseDouble(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 549 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid double value");
/*     */       } }
/* 551 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 552 */       return 0.0D;
/*     */     }
/*     */     
/* 555 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */   protected Date _parseDate(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 561 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 562 */     if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 563 */       return new Date(paramJsonParser.getLongValue());
/*     */     }
/* 565 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 566 */       return (Date)getNullValue();
/*     */     }
/* 568 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 569 */       String str = null;
/*     */       try
/*     */       {
/* 572 */         str = paramJsonParser.getText().trim();
/* 573 */         if (str.length() == 0) {
/* 574 */           return (Date)getEmptyValue();
/*     */         }
/* 576 */         return paramDeserializationContext.parseDate(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 578 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid representation (error: " + localIllegalArgumentException.getMessage() + ")");
/*     */       }
/*     */     }
/*     */     
/* 582 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static final double parseDouble(String paramString)
/*     */     throws NumberFormatException
/*     */   {
/* 593 */     if ("2.2250738585072012e-308".equals(paramString)) {
/* 594 */       return Double.MIN_VALUE;
/*     */     }
/* 596 */     return Double.parseDouble(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final String _parseString(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 608 */     String str = paramJsonParser.getValueAsString();
/* 609 */     if (str != null) {
/* 610 */       return str;
/*     */     }
/* 612 */     throw paramDeserializationContext.mappingException(String.class, paramJsonParser.getCurrentToken());
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
/*     */   protected JsonDeserializer<Object> findDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 634 */     return paramDeserializationContext.findContextualValueDeserializer(paramJavaType, paramBeanProperty);
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
/*     */   protected JsonDeserializer<?> findConvertingContentDeserializer(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty, JsonDeserializer<?> paramJsonDeserializer)
/*     */     throws JsonMappingException
/*     */   {
/* 657 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/* 658 */     if ((localAnnotationIntrospector != null) && (paramBeanProperty != null)) {
/* 659 */       Object localObject = localAnnotationIntrospector.findDeserializationContentConverter(paramBeanProperty.getMember());
/* 660 */       if (localObject != null) {
/* 661 */         Converter localConverter = paramDeserializationContext.converterInstance(paramBeanProperty.getMember(), localObject);
/* 662 */         JavaType localJavaType = localConverter.getInputType(paramDeserializationContext.getTypeFactory());
/* 663 */         if (paramJsonDeserializer == null) {
/* 664 */           paramJsonDeserializer = paramDeserializationContext.findContextualValueDeserializer(localJavaType, paramBeanProperty);
/*     */         }
/* 666 */         return new StdDelegatingDeserializer(localConverter, localJavaType, paramJsonDeserializer);
/*     */       }
/*     */     }
/* 669 */     return paramJsonDeserializer;
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
/*     */   protected void handleUnknownProperty(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 700 */     if (paramObject == null) {
/* 701 */       paramObject = getValueClass();
/*     */     }
/*     */     
/* 704 */     if (paramDeserializationContext.handleUnknownProperty(paramJsonParser, this, paramObject, paramString)) {
/* 705 */       return;
/*     */     }
/*     */     
/* 708 */     paramDeserializationContext.reportUnknownProperty(paramObject, paramString, this);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 713 */     paramJsonParser.skipChildren();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StdDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */