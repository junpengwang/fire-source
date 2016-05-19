/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class POJONode
/*     */   extends ValueNode
/*     */ {
/*     */   protected final Object _value;
/*     */   
/*     */   public POJONode(Object paramObject)
/*     */   {
/*  19 */     this._value = paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNodeType getNodeType()
/*     */   {
/*  30 */     return JsonNodeType.POJO;
/*     */   }
/*     */   
/*  33 */   public JsonToken asToken() { return JsonToken.VALUE_EMBEDDED_OBJECT; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] binaryValue()
/*     */     throws IOException
/*     */   {
/*  43 */     if ((this._value instanceof byte[])) {
/*  44 */       return (byte[])this._value;
/*     */     }
/*  46 */     return super.binaryValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String asText()
/*     */   {
/*  57 */     return this._value == null ? "null" : this._value.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean asBoolean(boolean paramBoolean)
/*     */   {
/*  63 */     if ((this._value != null) && ((this._value instanceof Boolean))) {
/*  64 */       return ((Boolean)this._value).booleanValue();
/*     */     }
/*  66 */     return paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */   public int asInt(int paramInt)
/*     */   {
/*  72 */     if ((this._value instanceof Number)) {
/*  73 */       return ((Number)this._value).intValue();
/*     */     }
/*  75 */     return paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */   public long asLong(long paramLong)
/*     */   {
/*  81 */     if ((this._value instanceof Number)) {
/*  82 */       return ((Number)this._value).longValue();
/*     */     }
/*  84 */     return paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */   public double asDouble(double paramDouble)
/*     */   {
/*  90 */     if ((this._value instanceof Number)) {
/*  91 */       return ((Number)this._value).doubleValue();
/*     */     }
/*  93 */     return paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 106 */     if (this._value == null) {
/* 107 */       paramJsonGenerator.writeNull();
/*     */     } else {
/* 109 */       paramJsonGenerator.writeObject(this._value);
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
/*     */   public Object getPojo()
/*     */   {
/* 122 */     return this._value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 133 */     if (paramObject == this) return true;
/* 134 */     if (paramObject == null) return false;
/* 135 */     if (paramObject.getClass() != getClass()) {
/* 136 */       return false;
/*     */     }
/* 138 */     POJONode localPOJONode = (POJONode)paramObject;
/* 139 */     if (this._value == null) {
/* 140 */       return localPOJONode._value == null;
/*     */     }
/* 142 */     return this._value.equals(localPOJONode._value);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 146 */     return this._value.hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 151 */     return String.valueOf(this._value);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/POJONode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */