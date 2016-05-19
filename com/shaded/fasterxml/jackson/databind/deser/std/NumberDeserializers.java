/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.HashSet;
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
/*     */ public class NumberDeserializers
/*     */ {
/*  27 */   private static final HashSet<String> _classNames = new HashSet();
/*     */   
/*     */   static {
/*  30 */     Class[] arrayOfClass1 = { Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, Number.class, BigDecimal.class, BigInteger.class };
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
/*  42 */     for (Class localClass : arrayOfClass1) {
/*  43 */       _classNames.add(localClass.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static StdDeserializer<?>[] all()
/*     */   {
/*  53 */     return new StdDeserializer[] { new BooleanDeserializer(Boolean.class, null), new ByteDeserializer(Byte.class, null), new ShortDeserializer(Short.class, null), new CharacterDeserializer(Character.class, null), new IntegerDeserializer(Integer.class, null), new LongDeserializer(Long.class, null), new FloatDeserializer(Float.class, null), new DoubleDeserializer(Double.class, null), new BooleanDeserializer(Boolean.TYPE, Boolean.FALSE), new ByteDeserializer(Byte.TYPE, Byte.valueOf(0)), new ShortDeserializer(Short.TYPE, Short.valueOf(0)), new CharacterDeserializer(Character.TYPE, Character.valueOf('\000')), new IntegerDeserializer(Integer.TYPE, Integer.valueOf(0)), new LongDeserializer(Long.TYPE, Long.valueOf(0L)), new FloatDeserializer(Float.TYPE, Float.valueOf(0.0F)), new DoubleDeserializer(Double.TYPE, Double.valueOf(0.0D)), new NumberDeserializer(), new BigDecimalDeserializer(), new BigIntegerDeserializer() };
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
/*     */ 
/*     */ 
/*     */   public static JsonDeserializer<?> find(Class<?> paramClass, String paramString)
/*     */   {
/*  85 */     if (paramClass.isPrimitive()) {
/*  86 */       if (paramClass == Integer.TYPE) {
/*  87 */         return IntegerDeserializer.primitiveInstance;
/*     */       }
/*  89 */       if (paramClass == Boolean.TYPE) {
/*  90 */         return BooleanDeserializer.primitiveInstance;
/*     */       }
/*  92 */       if (paramClass == Long.TYPE) {
/*  93 */         return LongDeserializer.primitiveInstance;
/*     */       }
/*  95 */       if (paramClass == Double.TYPE) {
/*  96 */         return DoubleDeserializer.primitiveInstance;
/*     */       }
/*  98 */       if (paramClass == Character.TYPE) {
/*  99 */         return CharacterDeserializer.primitiveInstance;
/*     */       }
/* 101 */       if (paramClass == Byte.TYPE) {
/* 102 */         return ByteDeserializer.primitiveInstance;
/*     */       }
/* 104 */       if (paramClass == Short.TYPE) {
/* 105 */         return ShortDeserializer.primitiveInstance;
/*     */       }
/* 107 */       if (paramClass == Float.TYPE) {
/* 108 */         return FloatDeserializer.primitiveInstance;
/*     */       }
/* 110 */     } else if (_classNames.contains(paramString))
/*     */     {
/* 112 */       if (paramClass == Integer.class) {
/* 113 */         return IntegerDeserializer.wrapperInstance;
/*     */       }
/* 115 */       if (paramClass == Boolean.class) {
/* 116 */         return BooleanDeserializer.wrapperInstance;
/*     */       }
/* 118 */       if (paramClass == Long.class) {
/* 119 */         return LongDeserializer.wrapperInstance;
/*     */       }
/* 121 */       if (paramClass == Double.class) {
/* 122 */         return DoubleDeserializer.wrapperInstance;
/*     */       }
/* 124 */       if (paramClass == Character.class) {
/* 125 */         return CharacterDeserializer.wrapperInstance;
/*     */       }
/* 127 */       if (paramClass == Byte.class) {
/* 128 */         return ByteDeserializer.wrapperInstance;
/*     */       }
/* 130 */       if (paramClass == Short.class) {
/* 131 */         return ShortDeserializer.wrapperInstance;
/*     */       }
/* 133 */       if (paramClass == Float.class) {
/* 134 */         return FloatDeserializer.wrapperInstance;
/*     */       }
/* 136 */       if (paramClass == Number.class) {
/* 137 */         return NumberDeserializer.instance;
/*     */       }
/* 139 */       if (paramClass == BigDecimal.class) {
/* 140 */         return BigDecimalDeserializer.instance;
/*     */       }
/* 142 */       if (paramClass == BigInteger.class) {
/* 143 */         return BigIntegerDeserializer.instance;
/*     */       }
/*     */     } else {
/* 146 */       return null;
/*     */     }
/*     */     
/* 149 */     throw new IllegalArgumentException("Internal error: can't find deserializer for " + paramClass.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static abstract class PrimitiveOrWrapperDeserializer<T>
/*     */     extends StdScalarDeserializer<T>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */     protected final T _nullValue;
/*     */     
/*     */ 
/*     */ 
/*     */     protected PrimitiveOrWrapperDeserializer(Class<T> paramClass, T paramT)
/*     */     {
/* 168 */       super();
/* 169 */       this._nullValue = paramT;
/*     */     }
/*     */     
/*     */     public final T getNullValue()
/*     */     {
/* 174 */       return (T)this._nullValue;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class BooleanDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Boolean>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/* 190 */     private static final BooleanDeserializer primitiveInstance = new BooleanDeserializer(Boolean.class, Boolean.FALSE);
/* 191 */     private static final BooleanDeserializer wrapperInstance = new BooleanDeserializer(Boolean.TYPE, null);
/*     */     
/*     */     public BooleanDeserializer(Class<Boolean> paramClass, Boolean paramBoolean)
/*     */     {
/* 195 */       super(paramBoolean);
/*     */     }
/*     */     
/*     */ 
/*     */     public Boolean deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 202 */       return _parseBoolean(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Boolean deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 212 */       return _parseBoolean(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class ByteDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Byte>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 222 */     private static final ByteDeserializer primitiveInstance = new ByteDeserializer(Byte.TYPE, Byte.valueOf((byte)0));
/* 223 */     private static final ByteDeserializer wrapperInstance = new ByteDeserializer(Byte.class, null);
/*     */     
/*     */     public ByteDeserializer(Class<Byte> paramClass, Byte paramByte)
/*     */     {
/* 227 */       super(paramByte);
/*     */     }
/*     */     
/*     */ 
/*     */     public Byte deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 234 */       return _parseByte(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class ShortDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Short>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 244 */     private static final ShortDeserializer primitiveInstance = new ShortDeserializer(Short.class, Short.valueOf((short)0));
/* 245 */     private static final ShortDeserializer wrapperInstance = new ShortDeserializer(Short.TYPE, null);
/*     */     
/*     */     public ShortDeserializer(Class<Short> paramClass, Short paramShort)
/*     */     {
/* 249 */       super(paramShort);
/*     */     }
/*     */     
/*     */ 
/*     */     public Short deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 256 */       return _parseShort(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class CharacterDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Character>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 266 */     private static final CharacterDeserializer primitiveInstance = new CharacterDeserializer(Character.class, Character.valueOf('\000'));
/* 267 */     private static final CharacterDeserializer wrapperInstance = new CharacterDeserializer(Character.TYPE, null);
/*     */     
/*     */     public CharacterDeserializer(Class<Character> paramClass, Character paramCharacter)
/*     */     {
/* 271 */       super(paramCharacter);
/*     */     }
/*     */     
/*     */ 
/*     */     public Character deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 278 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */       
/*     */ 
/* 281 */       if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 282 */         int i = paramJsonParser.getIntValue();
/* 283 */         if ((i >= 0) && (i <= 65535)) {
/* 284 */           return Character.valueOf((char)i);
/*     */         }
/* 286 */       } else if (localJsonToken == JsonToken.VALUE_STRING)
/*     */       {
/* 288 */         String str = paramJsonParser.getText();
/* 289 */         if (str.length() == 1) {
/* 290 */           return Character.valueOf(str.charAt(0));
/*     */         }
/*     */         
/* 293 */         if (str.length() == 0) {
/* 294 */           return (Character)getEmptyValue();
/*     */         }
/*     */       }
/* 297 */       throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class IntegerDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Integer>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 307 */     private static final IntegerDeserializer primitiveInstance = new IntegerDeserializer(Integer.class, Integer.valueOf(0));
/* 308 */     private static final IntegerDeserializer wrapperInstance = new IntegerDeserializer(Integer.TYPE, null);
/*     */     
/*     */     public IntegerDeserializer(Class<Integer> paramClass, Integer paramInteger)
/*     */     {
/* 312 */       super(paramInteger);
/*     */     }
/*     */     
/*     */ 
/*     */     public Integer deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 319 */       return _parseInteger(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Integer deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 329 */       return _parseInteger(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class LongDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Long>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 339 */     private static final LongDeserializer primitiveInstance = new LongDeserializer(Long.class, Long.valueOf(0L));
/* 340 */     private static final LongDeserializer wrapperInstance = new LongDeserializer(Long.TYPE, null);
/*     */     
/*     */     public LongDeserializer(Class<Long> paramClass, Long paramLong)
/*     */     {
/* 344 */       super(paramLong);
/*     */     }
/*     */     
/*     */ 
/*     */     public Long deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 351 */       return _parseLong(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class FloatDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Float>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 361 */     private static final FloatDeserializer primitiveInstance = new FloatDeserializer(Float.class, Float.valueOf(0.0F));
/* 362 */     private static final FloatDeserializer wrapperInstance = new FloatDeserializer(Float.TYPE, null);
/*     */     
/*     */     public FloatDeserializer(Class<Float> paramClass, Float paramFloat)
/*     */     {
/* 366 */       super(paramFloat);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Float deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 376 */       return _parseFloat(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class DoubleDeserializer
/*     */     extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Double>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 386 */     private static final DoubleDeserializer primitiveInstance = new DoubleDeserializer(Double.class, Double.valueOf(0.0D));
/* 387 */     private static final DoubleDeserializer wrapperInstance = new DoubleDeserializer(Double.TYPE, null);
/*     */     
/*     */     public DoubleDeserializer(Class<Double> paramClass, Double paramDouble)
/*     */     {
/* 391 */       super(paramDouble);
/*     */     }
/*     */     
/*     */ 
/*     */     public Double deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 398 */       return _parseDouble(paramJsonParser, paramDeserializationContext);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Double deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 408 */       return _parseDouble(paramJsonParser, paramDeserializationContext);
/*     */     }
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
/*     */   @JacksonStdImpl
/*     */   public static final class NumberDeserializer
/*     */     extends StdScalarDeserializer<Number>
/*     */   {
/* 427 */     public static final NumberDeserializer instance = new NumberDeserializer();
/*     */     
/* 429 */     public NumberDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     public Number deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 435 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 436 */       if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 437 */         if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
/* 438 */           return paramJsonParser.getBigIntegerValue();
/*     */         }
/* 440 */         return paramJsonParser.getNumberValue(); }
/* 441 */       if (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)
/*     */       {
/*     */ 
/*     */ 
/* 445 */         if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 446 */           return paramJsonParser.getDecimalValue();
/*     */         }
/* 448 */         return Double.valueOf(paramJsonParser.getDoubleValue());
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 454 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/* 455 */         String str = paramJsonParser.getText().trim();
/*     */         try {
/* 457 */           if (str.indexOf('.') >= 0)
/*     */           {
/* 459 */             if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
/* 460 */               return new BigDecimal(str);
/*     */             }
/* 462 */             return new Double(str);
/*     */           }
/*     */           
/* 465 */           if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
/* 466 */             return new BigInteger(str);
/*     */           }
/* 468 */           long l = Long.parseLong(str);
/* 469 */           if ((l <= 2147483647L) && (l >= -2147483648L)) {
/* 470 */             return Integer.valueOf((int)l);
/*     */           }
/* 472 */           return Long.valueOf(l);
/*     */         } catch (IllegalArgumentException localIllegalArgumentException) {
/* 474 */           throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid number");
/*     */         }
/*     */       }
/*     */       
/* 478 */       throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */     }
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
/*     */     public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 493 */       switch (NumberDeserializers.1.$SwitchMap$com$fasterxml$jackson$core$JsonToken[paramJsonParser.getCurrentToken().ordinal()])
/*     */       {
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/* 498 */         return deserialize(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 500 */       return paramTypeDeserializer.deserializeTypedFromScalar(paramJsonParser, paramDeserializationContext);
/*     */     }
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
/*     */   @JacksonStdImpl
/*     */   public static class BigIntegerDeserializer
/*     */     extends StdScalarDeserializer<BigInteger>
/*     */   {
/* 520 */     public static final BigIntegerDeserializer instance = new BigIntegerDeserializer();
/*     */     
/* 522 */     public BigIntegerDeserializer() { super(); }
/*     */     
/*     */ 
/*     */ 
/*     */     public BigInteger deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 529 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */       
/*     */ 
/* 532 */       if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 533 */         switch (NumberDeserializers.1.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[paramJsonParser.getNumberType().ordinal()]) {
/*     */         case 1: 
/*     */         case 2: 
/* 536 */           return BigInteger.valueOf(paramJsonParser.getLongValue()); }
/*     */       } else {
/* 538 */         if (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)
/*     */         {
/*     */ 
/*     */ 
/* 542 */           return paramJsonParser.getDecimalValue().toBigInteger(); }
/* 543 */         if (localJsonToken != JsonToken.VALUE_STRING)
/*     */         {
/* 545 */           throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken); }
/*     */       }
/* 547 */       String str = paramJsonParser.getText().trim();
/* 548 */       if (str.length() == 0) {
/* 549 */         return null;
/*     */       }
/*     */       try {
/* 552 */         return new BigInteger(str);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 554 */         throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid representation");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static class BigDecimalDeserializer
/*     */     extends StdScalarDeserializer<BigDecimal>
/*     */   {
/* 564 */     public static final BigDecimalDeserializer instance = new BigDecimalDeserializer();
/*     */     
/* 566 */     public BigDecimalDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     public BigDecimal deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 572 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 573 */       if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
/* 574 */         return paramJsonParser.getDecimalValue();
/*     */       }
/*     */       
/* 577 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/* 578 */         String str = paramJsonParser.getText().trim();
/* 579 */         if (str.length() == 0) {
/* 580 */           return null;
/*     */         }
/*     */         try {
/* 583 */           return new BigDecimal(str);
/*     */         } catch (IllegalArgumentException localIllegalArgumentException) {
/* 585 */           throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid representation");
/*     */         }
/*     */       }
/*     */       
/* 589 */       throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/NumberDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */