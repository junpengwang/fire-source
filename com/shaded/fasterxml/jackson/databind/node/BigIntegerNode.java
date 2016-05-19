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
/*     */ public final class BigIntegerNode
/*     */   extends NumericNode
/*     */ {
/*  16 */   private static final BigInteger MIN_INTEGER = BigInteger.valueOf(-2147483648L);
/*  17 */   private static final BigInteger MAX_INTEGER = BigInteger.valueOf(2147483647L);
/*  18 */   private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
/*  19 */   private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final BigInteger _value;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  29 */   public BigIntegerNode(BigInteger paramBigInteger) { this._value = paramBigInteger; }
/*     */   
/*  31 */   public static BigIntegerNode valueOf(BigInteger paramBigInteger) { return new BigIntegerNode(paramBigInteger); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken asToken()
/*     */   {
/*  40 */     return JsonToken.VALUE_NUMBER_INT;
/*     */   }
/*     */   
/*  43 */   public JsonParser.NumberType numberType() { return JsonParser.NumberType.BIG_INTEGER; }
/*     */   
/*     */   public boolean isIntegralNumber() {
/*  46 */     return true;
/*     */   }
/*     */   
/*  49 */   public boolean isBigInteger() { return true; }
/*     */   
/*     */   public boolean canConvertToInt() {
/*  52 */     return (this._value.compareTo(MIN_INTEGER) >= 0) && (this._value.compareTo(MAX_INTEGER) <= 0);
/*     */   }
/*     */   
/*  55 */   public boolean canConvertToLong() { return (this._value.compareTo(MIN_LONG) >= 0) && (this._value.compareTo(MAX_LONG) <= 0); }
/*     */   
/*     */ 
/*     */   public Number numberValue()
/*     */   {
/*  60 */     return this._value;
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  64 */     return this._value.shortValue();
/*     */   }
/*     */   
/*  67 */   public int intValue() { return this._value.intValue(); }
/*     */   
/*     */   public long longValue() {
/*  70 */     return this._value.longValue();
/*     */   }
/*     */   
/*  73 */   public BigInteger bigIntegerValue() { return this._value; }
/*     */   
/*     */   public float floatValue() {
/*  76 */     return this._value.floatValue();
/*     */   }
/*     */   
/*  79 */   public double doubleValue() { return this._value.doubleValue(); }
/*     */   
/*     */   public BigDecimal decimalValue() {
/*  82 */     return new BigDecimal(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String asText()
/*     */   {
/*  92 */     return this._value.toString();
/*     */   }
/*     */   
/*     */   public boolean asBoolean(boolean paramBoolean)
/*     */   {
/*  97 */     return !BigInteger.ZERO.equals(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 104 */     paramJsonGenerator.writeNumber(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 110 */     if (paramObject == this) return true;
/* 111 */     if (paramObject == null) return false;
/* 112 */     if (paramObject.getClass() != getClass()) {
/* 113 */       return false;
/*     */     }
/* 115 */     return ((BigIntegerNode)paramObject)._value.equals(this._value);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 120 */     return this._value.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/BigIntegerNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */