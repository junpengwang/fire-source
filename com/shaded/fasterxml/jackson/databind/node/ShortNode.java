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
/*     */ public final class ShortNode
/*     */   extends NumericNode
/*     */ {
/*     */   final short _value;
/*     */   
/*  26 */   public ShortNode(short paramShort) { this._value = paramShort; }
/*     */   
/*  28 */   public static ShortNode valueOf(short paramShort) { return new ShortNode(paramShort); }
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
/*  39 */   public JsonParser.NumberType numberType() { return JsonParser.NumberType.INT; }
/*     */   
/*     */   public boolean isIntegralNumber()
/*     */   {
/*  43 */     return true;
/*     */   }
/*     */   
/*  46 */   public boolean isShort() { return true; }
/*     */   
/*  48 */   public boolean canConvertToInt() { return true; }
/*  49 */   public boolean canConvertToLong() { return true; }
/*     */   
/*     */   public Number numberValue()
/*     */   {
/*  53 */     return Short.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  57 */     return this._value;
/*     */   }
/*     */   
/*  60 */   public int intValue() { return this._value; }
/*     */   
/*     */   public long longValue() {
/*  63 */     return this._value;
/*     */   }
/*     */   
/*  66 */   public float floatValue() { return this._value; }
/*     */   
/*     */   public double doubleValue() {
/*  69 */     return this._value;
/*     */   }
/*     */   
/*  72 */   public BigDecimal decimalValue() { return BigDecimal.valueOf(this._value); }
/*     */   
/*     */   public BigInteger bigIntegerValue() {
/*  75 */     return BigInteger.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public String asText() {
/*  79 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */   
/*     */   public boolean asBoolean(boolean paramBoolean)
/*     */   {
/*  84 */     return this._value != 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  91 */     paramJsonGenerator.writeNumber(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  97 */     if (paramObject == this) return true;
/*  98 */     if (paramObject == null) return false;
/*  99 */     if (paramObject.getClass() != getClass()) {
/* 100 */       return false;
/*     */     }
/* 102 */     return ((ShortNode)paramObject)._value == this._value;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 107 */     return this._value;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/ShortNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */