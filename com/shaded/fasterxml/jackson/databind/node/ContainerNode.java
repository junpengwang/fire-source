/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
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
/*     */ 
/*     */ public abstract class ContainerNode<T extends ContainerNode<T>>
/*     */   extends BaseJsonNode
/*     */   implements JsonNodeCreator
/*     */ {
/*     */   protected final JsonNodeFactory _nodeFactory;
/*     */   
/*     */   protected ContainerNode(JsonNodeFactory paramJsonNodeFactory)
/*     */   {
/*  27 */     this._nodeFactory = paramJsonNodeFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract JsonToken asToken();
/*     */   
/*     */ 
/*     */   public String asText()
/*     */   {
/*  37 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int size();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract JsonNode get(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract JsonNode get(String paramString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ArrayNode arrayNode()
/*     */   {
/*  66 */     return this._nodeFactory.arrayNode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final ObjectNode objectNode()
/*     */   {
/*  73 */     return this._nodeFactory.objectNode();
/*     */   }
/*     */   
/*  76 */   public final NullNode nullNode() { return this._nodeFactory.nullNode(); }
/*     */   
/*     */   public final BooleanNode booleanNode(boolean paramBoolean) {
/*  79 */     return this._nodeFactory.booleanNode(paramBoolean);
/*     */   }
/*     */   
/*  82 */   public final NumericNode numberNode(byte paramByte) { return this._nodeFactory.numberNode(paramByte); }
/*     */   
/*  84 */   public final NumericNode numberNode(short paramShort) { return this._nodeFactory.numberNode(paramShort); }
/*     */   
/*  86 */   public final NumericNode numberNode(int paramInt) { return this._nodeFactory.numberNode(paramInt); }
/*     */   
/*  88 */   public final NumericNode numberNode(long paramLong) { return this._nodeFactory.numberNode(paramLong); }
/*     */   
/*     */   public final NumericNode numberNode(BigInteger paramBigInteger)
/*     */   {
/*  92 */     return this._nodeFactory.numberNode(paramBigInteger);
/*     */   }
/*     */   
/*  95 */   public final NumericNode numberNode(float paramFloat) { return this._nodeFactory.numberNode(paramFloat); }
/*     */   
/*  97 */   public final NumericNode numberNode(double paramDouble) { return this._nodeFactory.numberNode(paramDouble); }
/*     */   
/*  99 */   public final NumericNode numberNode(BigDecimal paramBigDecimal) { return this._nodeFactory.numberNode(paramBigDecimal); }
/*     */   
/*     */ 
/*     */ 
/* 103 */   public final ValueNode numberNode(Byte paramByte) { return this._nodeFactory.numberNode(paramByte); }
/*     */   
/* 105 */   public final ValueNode numberNode(Short paramShort) { return this._nodeFactory.numberNode(paramShort); }
/*     */   
/* 107 */   public final ValueNode numberNode(Integer paramInteger) { return this._nodeFactory.numberNode(paramInteger); }
/*     */   
/* 109 */   public final ValueNode numberNode(Long paramLong) { return this._nodeFactory.numberNode(paramLong); }
/*     */   
/*     */ 
/* 112 */   public final ValueNode numberNode(Float paramFloat) { return this._nodeFactory.numberNode(paramFloat); }
/*     */   
/* 114 */   public final ValueNode numberNode(Double paramDouble) { return this._nodeFactory.numberNode(paramDouble); }
/*     */   
/*     */   public final TextNode textNode(String paramString) {
/* 117 */     return this._nodeFactory.textNode(paramString);
/*     */   }
/*     */   
/* 120 */   public final BinaryNode binaryNode(byte[] paramArrayOfByte) { return this._nodeFactory.binaryNode(paramArrayOfByte); }
/*     */   
/* 122 */   public final BinaryNode binaryNode(byte[] paramArrayOfByte, int paramInt1, int paramInt2) { return this._nodeFactory.binaryNode(paramArrayOfByte, paramInt1, paramInt2); }
/*     */   
/*     */   public final ValueNode pojoNode(Object paramObject) {
/* 125 */     return this._nodeFactory.pojoNode(paramObject);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public final POJONode POJONode(Object paramObject)
/*     */   {
/* 131 */     return (POJONode)this._nodeFactory.pojoNode(paramObject);
/*     */   }
/*     */   
/*     */   public abstract T removeAll();
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/ContainerNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */