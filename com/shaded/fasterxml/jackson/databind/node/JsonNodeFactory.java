/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ public class JsonNodeFactory
/*     */   implements Serializable, JsonNodeCreator
/*     */ {
/*     */   private static final long serialVersionUID = -3271940633258788634L;
/*     */   private final boolean _cfgBigDecimalExact;
/*  22 */   private static final JsonNodeFactory decimalsNormalized = new JsonNodeFactory(false);
/*     */   
/*  24 */   private static final JsonNodeFactory decimalsAsIs = new JsonNodeFactory(true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  32 */   public static final JsonNodeFactory instance = decimalsNormalized;
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
/*     */   public JsonNodeFactory(boolean paramBoolean)
/*     */   {
/*  61 */     this._cfgBigDecimalExact = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonNodeFactory()
/*     */   {
/*  72 */     this(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonNodeFactory withExactBigDecimals(boolean paramBoolean)
/*     */   {
/*  84 */     return paramBoolean ? decimalsAsIs : decimalsNormalized;
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
/*     */   public BooleanNode booleanNode(boolean paramBoolean)
/*     */   {
/*  99 */     return paramBoolean ? BooleanNode.getTrue() : BooleanNode.getFalse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NullNode nullNode()
/*     */   {
/* 107 */     return NullNode.getInstance();
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
/*     */   public NumericNode numberNode(byte paramByte)
/*     */   {
/* 120 */     return IntNode.valueOf(paramByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueNode numberNode(Byte paramByte)
/*     */   {
/* 130 */     return paramByte == null ? nullNode() : IntNode.valueOf(paramByte.intValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NumericNode numberNode(short paramShort)
/*     */   {
/* 138 */     return ShortNode.valueOf(paramShort);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueNode numberNode(Short paramShort)
/*     */   {
/* 148 */     return paramShort == null ? nullNode() : ShortNode.valueOf(paramShort.shortValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NumericNode numberNode(int paramInt)
/*     */   {
/* 156 */     return IntNode.valueOf(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueNode numberNode(Integer paramInteger)
/*     */   {
/* 166 */     return paramInteger == null ? nullNode() : IntNode.valueOf(paramInteger.intValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NumericNode numberNode(long paramLong)
/*     */   {
/* 174 */     return LongNode.valueOf(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueNode numberNode(Long paramLong)
/*     */   {
/* 183 */     return paramLong == null ? nullNode() : LongNode.valueOf(paramLong.longValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NumericNode numberNode(BigInteger paramBigInteger)
/*     */   {
/* 191 */     return BigIntegerNode.valueOf(paramBigInteger);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NumericNode numberNode(float paramFloat)
/*     */   {
/* 198 */     return FloatNode.valueOf(paramFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueNode numberNode(Float paramFloat)
/*     */   {
/* 208 */     return paramFloat == null ? nullNode() : FloatNode.valueOf(paramFloat.floatValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NumericNode numberNode(double paramDouble)
/*     */   {
/* 216 */     return DoubleNode.valueOf(paramDouble);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueNode numberNode(Double paramDouble)
/*     */   {
/* 226 */     return paramDouble == null ? nullNode() : DoubleNode.valueOf(paramDouble.doubleValue());
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
/*     */   public NumericNode numberNode(BigDecimal paramBigDecimal)
/*     */   {
/* 246 */     if (this._cfgBigDecimalExact) {
/* 247 */       return DecimalNode.valueOf(paramBigDecimal);
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
/* 258 */     return paramBigDecimal.compareTo(BigDecimal.ZERO) == 0 ? DecimalNode.ZERO : DecimalNode.valueOf(paramBigDecimal.stripTrailingZeros());
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
/*     */   public TextNode textNode(String paramString)
/*     */   {
/* 273 */     return TextNode.valueOf(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BinaryNode binaryNode(byte[] paramArrayOfByte)
/*     */   {
/* 281 */     return BinaryNode.valueOf(paramArrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BinaryNode binaryNode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 290 */     return BinaryNode.valueOf(paramArrayOfByte, paramInt1, paramInt2);
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
/*     */   public ArrayNode arrayNode()
/*     */   {
/* 303 */     return new ArrayNode(this);
/*     */   }
/*     */   
/*     */ 
/*     */   public ObjectNode objectNode()
/*     */   {
/* 309 */     return new ObjectNode(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ValueNode pojoNode(Object paramObject)
/*     */   {
/* 318 */     return new POJONode(paramObject);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public POJONode POJONode(Object paramObject)
/*     */   {
/* 324 */     return new POJONode(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/JsonNodeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */