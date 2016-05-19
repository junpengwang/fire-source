/*    */ package com.shaded.fasterxml.jackson.databind.node;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*    */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializable;
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
/*    */ public abstract class BaseJsonNode
/*    */   extends JsonNode
/*    */   implements JsonSerializable
/*    */ {
/*    */   public final JsonNode findPath(String paramString)
/*    */   {
/* 33 */     JsonNode localJsonNode = findValue(paramString);
/* 34 */     if (localJsonNode == null) {
/* 35 */       return MissingNode.getInstance();
/*    */     }
/* 37 */     return localJsonNode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public JsonParser traverse()
/*    */   {
/* 48 */     return new TreeTraversingParser(this);
/*    */   }
/*    */   
/*    */   public JsonParser traverse(ObjectCodec paramObjectCodec)
/*    */   {
/* 53 */     return new TreeTraversingParser(this, paramObjectCodec);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract JsonToken asToken();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public JsonParser.NumberType numberType()
/*    */   {
/* 73 */     return null;
/*    */   }
/*    */   
/*    */   public abstract void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonProcessingException;
/*    */   
/*    */   public abstract void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonProcessingException;
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/BaseJsonNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */