/*    */ package com.shaded.fasterxml.jackson.databind.node;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NumericNode
/*    */   extends ValueNode
/*    */ {
/*    */   public final JsonNodeType getNodeType()
/*    */   {
/* 19 */     return JsonNodeType.NUMBER;
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract JsonParser.NumberType numberType();
/*    */   
/*    */ 
/*    */   public abstract Number numberValue();
/*    */   
/*    */ 
/*    */   public abstract int intValue();
/*    */   
/*    */ 
/*    */   public abstract long longValue();
/*    */   
/*    */   public abstract double doubleValue();
/*    */   
/*    */   public abstract BigDecimal decimalValue();
/*    */   
/*    */   public abstract BigInteger bigIntegerValue();
/*    */   
/*    */   public abstract boolean canConvertToInt();
/*    */   
/*    */   public abstract boolean canConvertToLong();
/*    */   
/*    */   public abstract String asText();
/*    */   
/*    */   public final int asInt()
/*    */   {
/* 48 */     return intValue();
/*    */   }
/*    */   
/*    */   public final int asInt(int paramInt) {
/* 52 */     return intValue();
/*    */   }
/*    */   
/*    */   public final long asLong()
/*    */   {
/* 57 */     return longValue();
/*    */   }
/*    */   
/*    */   public final long asLong(long paramLong) {
/* 61 */     return longValue();
/*    */   }
/*    */   
/*    */   public final double asDouble()
/*    */   {
/* 66 */     return doubleValue();
/*    */   }
/*    */   
/*    */   public final double asDouble(double paramDouble) {
/* 70 */     return doubleValue();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/NumericNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */