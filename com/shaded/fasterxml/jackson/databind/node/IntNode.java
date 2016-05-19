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
/*     */ public final class IntNode
/*     */   extends NumericNode
/*     */ {
/*     */   static final int MIN_CANONICAL = -1;
/*     */   static final int MAX_CANONICAL = 10;
/*     */   private static final IntNode[] CANONICALS;
/*     */   final int _value;
/*     */   
/*     */   static
/*     */   {
/*  25 */     int i = 12;
/*  26 */     CANONICALS = new IntNode[i];
/*  27 */     for (int j = 0; j < i; j++) {
/*  28 */       CANONICALS[j] = new IntNode(-1 + j);
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
/*  43 */   public IntNode(int paramInt) { this._value = paramInt; }
/*     */   
/*     */   public static IntNode valueOf(int paramInt) {
/*  46 */     if ((paramInt > 10) || (paramInt < -1)) return new IntNode(paramInt);
/*  47 */     return CANONICALS[(paramInt - -1)];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken asToken()
/*     */   {
/*  56 */     return JsonToken.VALUE_NUMBER_INT;
/*     */   }
/*     */   
/*  59 */   public JsonParser.NumberType numberType() { return JsonParser.NumberType.INT; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isIntegralNumber()
/*     */   {
/*  68 */     return true;
/*     */   }
/*     */   
/*  71 */   public boolean isInt() { return true; }
/*     */   
/*  73 */   public boolean canConvertToInt() { return true; }
/*  74 */   public boolean canConvertToLong() { return true; }
/*     */   
/*     */   public Number numberValue()
/*     */   {
/*  78 */     return Integer.valueOf(this._value);
/*     */   }
/*     */   
/*     */   public short shortValue() {
/*  82 */     return (short)this._value;
/*     */   }
/*     */   
/*  85 */   public int intValue() { return this._value; }
/*     */   
/*     */   public long longValue() {
/*  88 */     return this._value;
/*     */   }
/*     */   
/*  91 */   public float floatValue() { return this._value; }
/*     */   
/*     */   public double doubleValue() {
/*  94 */     return this._value;
/*     */   }
/*     */   
/*     */   public BigDecimal decimalValue() {
/*  98 */     return BigDecimal.valueOf(this._value);
/*     */   }
/*     */   
/* 101 */   public BigInteger bigIntegerValue() { return BigInteger.valueOf(this._value); }
/*     */   
/*     */   public String asText()
/*     */   {
/* 105 */     return NumberOutput.toString(this._value);
/*     */   }
/*     */   
/*     */   public boolean asBoolean(boolean paramBoolean)
/*     */   {
/* 110 */     return this._value != 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 117 */     paramJsonGenerator.writeNumber(this._value);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 123 */     if (paramObject == this) return true;
/* 124 */     if (paramObject == null) return false;
/* 125 */     if (paramObject.getClass() != getClass()) {
/* 126 */       return false;
/*     */     }
/* 128 */     return ((IntNode)paramObject)._value == this._value;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 132 */     return this._value;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/IntNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */