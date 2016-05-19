/*    */ package com.shaded.fasterxml.jackson.databind.node;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ 
/*    */ public final class MissingNode
/*    */   extends ValueNode
/*    */ {
/* 26 */   private static final MissingNode instance = new MissingNode();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 33 */   public <T extends JsonNode> T deepCopy() { return this; }
/*    */   
/* 35 */   public static MissingNode getInstance() { return instance; }
/*    */   
/*    */ 
/*    */   public JsonNodeType getNodeType()
/*    */   {
/* 40 */     return JsonNodeType.MISSING;
/*    */   }
/*    */   
/* 43 */   public JsonToken asToken() { return JsonToken.NOT_AVAILABLE; }
/*    */   
/*    */   public String asText() {
/* 46 */     return "";
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 67 */     paramJsonGenerator.writeNull();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 75 */     paramJsonGenerator.writeNull();
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
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 89 */     return paramObject == this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 96 */     return "";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/MissingNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */