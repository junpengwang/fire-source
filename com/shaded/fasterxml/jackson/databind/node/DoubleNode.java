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
/*     */ public final class DoubleNode
/*     */   extends NumericNode
/*     */ {
/*     */   protected final double _value;
/*     */   
/*  27 */   public DoubleNode(double paramDouble) { this._value = paramDouble; }
/*     */   
/*  29 */   public static DoubleNode valueOf(double paramDouble) { return new DoubleNode(paramDouble); }
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
/*  40 */   public JsonParser.NumberType numberType() { return JsonParser.NumberType.DOUBLE; }
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
/*  52 */   public boolean isDouble() { return true; }
/*     */   
/*     */   public boolean canConvertToInt() {
/*  55 */     return (this._value >= -2.147483648E9D) && (this._value <= 2.147483647E9D);
/*     */   }
/*     */   
/*  58 */   public boolean canConvertToLong() { return (this._value >= -9.223372036854776E18D) && (this._value <= 9.223372036854776E18D); }
/*     */   
/*     */ 
/*     */   public Number numberValue()
/*     */   {
/*  63 */     return Double.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  67 */     return (short)(int)this._value;
/*     */   }
/*     */   
/*  70 */   public int intValue() { return (int)this._value; }
/*     */   
/*     */   public long longValue() {
/*  73 */     return this._value;
/*     */   }
/*     */   
/*  76 */   public float floatValue() { return (float)this._value; }
/*     */   
/*     */   public double doubleValue() {
/*  79 */     return this._value;
/*     */   }
/*     */   
/*  82 */   public BigDecimal decimalValue() { return BigDecimal.valueOf(this._value); }
/*     */   
/*     */   public BigInteger bigIntegerValue()
/*     */   {
/*  86 */     return decimalValue().toBigInteger();
/*     */   }
/*     */   
/*     */   public String asText()
/*     */   {
/*  91 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  98 */     paramJsonGenerator.writeNumber(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 104 */     if (paramObject == this) return true;
/* 105 */     if (paramObject == null) return false;
/* 106 */     if (paramObject.getClass() != getClass()) {
/* 107 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 112 */     double d = ((DoubleNode)paramObject)._value;
/* 113 */     return Double.compare(this._value, d) == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 120 */     long l = Double.doubleToLongBits(this._value);
/* 121 */     return (int)l ^ (int)(l >> 32);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/DoubleNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */