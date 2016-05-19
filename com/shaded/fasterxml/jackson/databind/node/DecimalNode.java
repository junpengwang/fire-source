/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DecimalNode
/*     */   extends NumericNode
/*     */ {
/*  18 */   public static final DecimalNode ZERO = new DecimalNode(BigDecimal.ZERO);
/*     */   
/*  20 */   private static final BigDecimal MIN_INTEGER = BigDecimal.valueOf(-2147483648L);
/*  21 */   private static final BigDecimal MAX_INTEGER = BigDecimal.valueOf(2147483647L);
/*  22 */   private static final BigDecimal MIN_LONG = BigDecimal.valueOf(Long.MIN_VALUE);
/*  23 */   private static final BigDecimal MAX_LONG = BigDecimal.valueOf(Long.MAX_VALUE);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final BigDecimal _value;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  33 */   public DecimalNode(BigDecimal paramBigDecimal) { this._value = paramBigDecimal; }
/*     */   
/*  35 */   public static DecimalNode valueOf(BigDecimal paramBigDecimal) { return new DecimalNode(paramBigDecimal); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken asToken()
/*     */   {
/*  43 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*     */   }
/*     */   
/*  46 */   public JsonParser.NumberType numberType() { return JsonParser.NumberType.BIG_DECIMAL; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFloatingPointNumber()
/*     */   {
/*  55 */     return true;
/*     */   }
/*     */   
/*  58 */   public boolean isBigDecimal() { return true; }
/*     */   
/*     */   public boolean canConvertToInt() {
/*  61 */     return (this._value.compareTo(MIN_INTEGER) >= 0) && (this._value.compareTo(MAX_INTEGER) <= 0);
/*     */   }
/*     */   
/*  64 */   public boolean canConvertToLong() { return (this._value.compareTo(MIN_LONG) >= 0) && (this._value.compareTo(MAX_LONG) <= 0); }
/*     */   
/*     */   public Number numberValue()
/*     */   {
/*  68 */     return this._value;
/*     */   }
/*     */   
/*  71 */   public short shortValue() { return this._value.shortValue(); }
/*     */   
/*     */   public int intValue() {
/*  74 */     return this._value.intValue();
/*     */   }
/*     */   
/*  77 */   public long longValue() { return this._value.longValue(); }
/*     */   
/*     */   public BigInteger bigIntegerValue()
/*     */   {
/*  81 */     return this._value.toBigInteger();
/*     */   }
/*     */   
/*  84 */   public float floatValue() { return this._value.floatValue(); }
/*     */   
/*     */   public double doubleValue() {
/*  87 */     return this._value.doubleValue();
/*     */   }
/*     */   
/*  90 */   public BigDecimal decimalValue() { return this._value; }
/*     */   
/*     */   public String asText()
/*     */   {
/*  94 */     return this._value.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 101 */     paramJsonGenerator.writeNumber(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 107 */     if (paramObject == this) return true;
/* 108 */     if (paramObject == null) return false;
/* 109 */     if (paramObject.getClass() != getClass()) {
/* 110 */       return false;
/*     */     }
/* 112 */     return ((DecimalNode)paramObject)._value.equals(this._value);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 116 */     return this._value.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/DecimalNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */