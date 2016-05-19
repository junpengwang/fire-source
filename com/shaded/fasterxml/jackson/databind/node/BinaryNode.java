/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variants;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ public final class BinaryNode
/*     */   extends ValueNode
/*     */ {
/*  17 */   static final BinaryNode EMPTY_BINARY_NODE = new BinaryNode(new byte[0]);
/*     */   
/*     */   final byte[] _data;
/*     */   
/*     */   public BinaryNode(byte[] paramArrayOfByte)
/*     */   {
/*  23 */     this._data = paramArrayOfByte;
/*     */   }
/*     */   
/*     */   public BinaryNode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  28 */     if ((paramInt1 == 0) && (paramInt2 == paramArrayOfByte.length)) {
/*  29 */       this._data = paramArrayOfByte;
/*     */     } else {
/*  31 */       this._data = new byte[paramInt2];
/*  32 */       System.arraycopy(paramArrayOfByte, paramInt1, this._data, 0, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static BinaryNode valueOf(byte[] paramArrayOfByte)
/*     */   {
/*  38 */     if (paramArrayOfByte == null) {
/*  39 */       return null;
/*     */     }
/*  41 */     if (paramArrayOfByte.length == 0) {
/*  42 */       return EMPTY_BINARY_NODE;
/*     */     }
/*  44 */     return new BinaryNode(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public static BinaryNode valueOf(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  49 */     if (paramArrayOfByte == null) {
/*  50 */       return null;
/*     */     }
/*  52 */     if (paramInt2 == 0) {
/*  53 */       return EMPTY_BINARY_NODE;
/*     */     }
/*  55 */     return new BinaryNode(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNodeType getNodeType()
/*     */   {
/*  61 */     return JsonNodeType.BINARY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken asToken()
/*     */   {
/*  70 */     return JsonToken.VALUE_EMBEDDED_OBJECT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] binaryValue()
/*     */   {
/*  79 */     return this._data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String asText()
/*     */   {
/*  87 */     return Base64Variants.getDefaultVariant().encode(this._data, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  94 */     paramJsonGenerator.writeBinary(paramSerializerProvider.getConfig().getBase64Variant(), this._data, 0, this._data.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 101 */     if (paramObject == this) return true;
/* 102 */     if (paramObject == null) return false;
/* 103 */     if (paramObject.getClass() != getClass()) {
/* 104 */       return false;
/*     */     }
/* 106 */     return Arrays.equals(((BinaryNode)paramObject)._data, this._data);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 111 */     return this._data == null ? -1 : this._data.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 121 */     return Base64Variants.getDefaultVariant().encode(this._data, true);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/BinaryNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */