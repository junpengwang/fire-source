/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.io.NumberOutput;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LongNode
/*     */   extends NumericNode
/*     */ {
/*     */   final long _value;
/*     */   
/*  26 */   public LongNode(long paramLong) { this._value = paramLong; }
/*     */   
/*  28 */   public static LongNode valueOf(long paramLong) { return new LongNode(paramLong); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken asToken()
/*     */   {
/*  36 */     return JsonToken.VALUE_NUMBER_INT;
/*     */   }
/*     */   
/*  39 */   public JsonParser.NumberType numberType() { return JsonParser.NumberType.LONG; }
/*     */   
/*     */   public boolean isIntegralNumber()
/*     */   {
/*  43 */     return true;
/*     */   }
/*     */   
/*  46 */   public boolean isLong() { return true; }
/*     */   
/*     */ 
/*  49 */   public boolean canConvertToInt() { return (this._value >= -2147483648L) && (this._value <= 2147483647L); }
/*     */   
/*  51 */   public boolean canConvertToLong() { return true; }
/*     */   
/*     */   public Number numberValue()
/*     */   {
/*  55 */     return Long.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  59 */     return (short)(int)this._value;
/*     */   }
/*     */   
/*  62 */   public int intValue() { return (int)this._value; }
/*     */   
/*     */   public long longValue() {
/*  65 */     return this._value;
/*     */   }
/*     */   
/*  68 */   public float floatValue() { return (float)this._value; }
/*     */   
/*     */   public double doubleValue() {
/*  71 */     return this._value;
/*     */   }
/*     */   
/*  74 */   public BigDecimal decimalValue() { return BigDecimal.valueOf(this._value); }
/*     */   
/*     */   public BigInteger bigIntegerValue() {
/*  77 */     return BigInteger.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public String asText() {
/*  81 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */   
/*     */   public boolean asBoolean(boolean paramBoolean)
/*     */   {
/*  86 */     return this._value != 0L;
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  93 */     paramJsonGenerator.writeNumber(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  99 */     if (paramObject == this) return true;
/* 100 */     if (paramObject == null) return false;
/* 101 */     if (paramObject.getClass() != getClass()) {
/* 102 */       return false;
/*     */     }
/* 104 */     return ((LongNode)paramObject)._value == this._value;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 109 */     return (int)this._value ^ (int)(this._value >> 32);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/LongNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */