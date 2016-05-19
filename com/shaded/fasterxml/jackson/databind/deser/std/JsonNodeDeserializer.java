/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ArrayNode;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonNodeDeserializer
/*     */   extends BaseNodeDeserializer
/*     */ {
/*  23 */   private static final JsonNodeDeserializer instance = new JsonNodeDeserializer();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> paramClass)
/*     */   {
/*  32 */     if (paramClass == ObjectNode.class) {
/*  33 */       return ObjectDeserializer.getInstance();
/*     */     }
/*  35 */     if (paramClass == ArrayNode.class) {
/*  36 */       return ArrayDeserializer.getInstance();
/*     */     }
/*     */     
/*  39 */     return instance;
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
/*     */   public JsonNode deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  57 */     switch (paramJsonParser.getCurrentToken()) {
/*     */     case START_OBJECT: 
/*  59 */       return deserializeObject(paramJsonParser, paramDeserializationContext, paramDeserializationContext.getNodeFactory());
/*     */     case START_ARRAY: 
/*  61 */       return deserializeArray(paramJsonParser, paramDeserializationContext, paramDeserializationContext.getNodeFactory());
/*     */     }
/*  63 */     return deserializeAny(paramJsonParser, paramDeserializationContext, paramDeserializationContext.getNodeFactory());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class ObjectDeserializer
/*     */     extends BaseNodeDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  78 */     protected static final ObjectDeserializer _instance = new ObjectDeserializer();
/*     */     
/*     */     public static ObjectDeserializer getInstance()
/*     */     {
/*  82 */       return _instance;
/*     */     }
/*     */     
/*     */     public ObjectNode deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/*  88 */       if (paramJsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
/*  89 */         paramJsonParser.nextToken();
/*  90 */         return deserializeObject(paramJsonParser, paramDeserializationContext, paramDeserializationContext.getNodeFactory());
/*     */       }
/*  92 */       if (paramJsonParser.getCurrentToken() == JsonToken.FIELD_NAME) {
/*  93 */         return deserializeObject(paramJsonParser, paramDeserializationContext, paramDeserializationContext.getNodeFactory());
/*     */       }
/*  95 */       throw paramDeserializationContext.mappingException(ObjectNode.class);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static final class ArrayDeserializer
/*     */     extends BaseNodeDeserializer
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/* 104 */     protected static final ArrayDeserializer _instance = new ArrayDeserializer();
/*     */     
/*     */     public static ArrayDeserializer getInstance()
/*     */     {
/* 108 */       return _instance;
/*     */     }
/*     */     
/*     */     public ArrayNode deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 114 */       if (paramJsonParser.isExpectedStartArrayToken()) {
/* 115 */         return deserializeArray(paramJsonParser, paramDeserializationContext, paramDeserializationContext.getNodeFactory());
/*     */       }
/* 117 */       throw paramDeserializationContext.mappingException(ArrayNode.class);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/JsonNodeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */