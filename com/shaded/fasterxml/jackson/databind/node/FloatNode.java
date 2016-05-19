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
/*     */ 
/*     */ public final class FloatNode
/*     */   extends NumericNode
/*     */ {
/*     */   protected final float _value;
/*     */   
/*  27 */   public FloatNode(float paramFloat) { this._value = paramFloat; }
/*     */   
/*  29 */   public static FloatNode valueOf(float paramFloat) { return new FloatNode(paramFloat); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken asToken()
/*     */   {
/*  37 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*     */   }
/*     */   
/*  40 */   public JsonParser.NumberType numberType() { return JsonParser.NumberType.FLOAT; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFloatingPointNumber()
/*     */   {
/*  49 */     return true;
/*     */   }
/*     */   
/*  52 */   public boolean isFloat() { return true; }
/*     */   
/*     */   public boolean canConvertToInt() {
/*  55 */     return (this._value >= -2.14748365E9F) && (this._value <= 2.14748365E9F);
/*     */   }
/*     */   
/*     */   public boolean canConvertToLong() {
/*  59 */     return (this._value >= -9.223372E18F) && (this._value <= 9.223372E18F);
/*     */   }
/*     */   
/*     */   public Number numberValue()
/*     */   {
/*  64 */     return Float.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  68 */     return (short)(int)this._value;
/*     */   }
/*     */   
/*  71 */   public int intValue() { return (int)this._value; }
/*     */   
/*     */   public long longValue() {
/*  74 */     return this._value;
/*     */   }
/*     */   
/*  77 */   public float floatValue() { return this._value; }
/*     */   
/*     */   public double doubleValue() {
/*  80 */     return this._value;
/*     */   }
/*     */   
/*  83 */   public BigDecimal decimalValue() { return BigDecimal.valueOf(this._value); }
/*     */   
/*     */   public BigInteger bigIntegerValue()
/*     */   {
/*  87 */     return decimalValue().toBigInteger();
/*     */   }
/*     */   
/*     */   public String asText()
/*     */   {
/*  92 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  99 */     paramJsonGenerator.writeNumber(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 105 */     if (paramObject == this) return true;
/* 106 */     if (paramObject == null) return false;
/* 107 */     if (paramObject.getClass() != getClass()) {
/* 108 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 113 */     float f = ((FloatNode)paramObject)._value;
/* 114 */     return Float.compare(this._value, f) == 0;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 119 */     return Float.floatToIntBits(this._value);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/FloatNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */