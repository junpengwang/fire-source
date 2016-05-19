/*    */ package com.shaded.fasterxml.jackson.databind.node;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BooleanNode
/*    */   extends ValueNode
/*    */ {
/* 19 */   public static final BooleanNode TRUE = new BooleanNode(true);
/* 20 */   public static final BooleanNode FALSE = new BooleanNode(false);
/*    */   
/*    */   private final boolean _value;
/*    */   
/* 24 */   private BooleanNode(boolean paramBoolean) { this._value = paramBoolean; }
/*    */   
/* 26 */   public static BooleanNode getTrue() { return TRUE; }
/* 27 */   public static BooleanNode getFalse() { return FALSE; }
/*    */   
/* 29 */   public static BooleanNode valueOf(boolean paramBoolean) { return paramBoolean ? TRUE : FALSE; }
/*    */   
/*    */   public JsonNodeType getNodeType()
/*    */   {
/* 33 */     return JsonNodeType.BOOLEAN;
/*    */   }
/*    */   
/*    */   public JsonToken asToken() {
/* 37 */     return this._value ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
/*    */   }
/*    */   
/*    */   public boolean booleanValue()
/*    */   {
/* 42 */     return this._value;
/*    */   }
/*    */   
/*    */   public String asText()
/*    */   {
/* 47 */     return this._value ? "true" : "false";
/*    */   }
/*    */   
/*    */   public boolean asBoolean()
/*    */   {
/* 52 */     return this._value;
/*    */   }
/*    */   
/*    */   public boolean asBoolean(boolean paramBoolean)
/*    */   {
/* 57 */     return this._value;
/*    */   }
/*    */   
/*    */   public int asInt(int paramInt)
/*    */   {
/* 62 */     return this._value ? 1 : 0;
/*    */   }
/*    */   
/*    */   public long asLong(long paramLong) {
/* 66 */     return this._value ? 1L : 0L;
/*    */   }
/*    */   
/*    */   public double asDouble(double paramDouble) {
/* 70 */     return this._value ? 1.0D : 0.0D;
/*    */   }
/*    */   
/*    */ 
/*    */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 77 */     paramJsonGenerator.writeBoolean(this._value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 87 */     if (paramObject == this) return true;
/* 88 */     if (paramObject == null) return false;
/* 89 */     if (paramObject.getClass() != getClass()) {
/* 90 */       return false;
/*    */     }
/* 92 */     return this._value == ((BooleanNode)paramObject)._value;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/BooleanNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */