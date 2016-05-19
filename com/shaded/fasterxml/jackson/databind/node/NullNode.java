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
/*    */ public final class NullNode
/*    */   extends ValueNode
/*    */ {
/* 18 */   public static final NullNode instance = new NullNode();
/*    */   
/*    */   public static NullNode getInstance()
/*    */   {
/* 22 */     return instance;
/*    */   }
/*    */   
/*    */   public JsonNodeType getNodeType()
/*    */   {
/* 27 */     return JsonNodeType.NULL;
/*    */   }
/*    */   
/* 30 */   public JsonToken asToken() { return JsonToken.VALUE_NULL; }
/*    */   
/*    */   public String asText()
/*    */   {
/* 34 */     return "null";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 50 */     paramJsonGenerator.writeNull();
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 56 */     return paramObject == this;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/NullNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */