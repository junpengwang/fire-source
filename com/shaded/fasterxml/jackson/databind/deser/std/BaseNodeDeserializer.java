/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ArrayNode;
/*     */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.node.NullNode;
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
/*     */ abstract class BaseNodeDeserializer
/*     */   extends StdDeserializer<JsonNode>
/*     */ {
/*     */   public BaseNodeDeserializer()
/*     */   {
/* 132 */     super(JsonNode.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 143 */     return paramTypeDeserializer.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */   public JsonNode getNullValue()
/*     */   {
/* 148 */     return NullNode.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _reportProblem(JsonParser paramJsonParser, String paramString)
/*     */     throws JsonMappingException
/*     */   {
/* 160 */     throw new JsonMappingException(paramString, paramJsonParser.getTokenLocation());
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
/*     */   protected void _handleDuplicateField(String paramString, ObjectNode paramObjectNode, JsonNode paramJsonNode1, JsonNode paramJsonNode2)
/*     */     throws JsonProcessingException
/*     */   {}
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
/*     */   protected final ObjectNode deserializeObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonNodeFactory paramJsonNodeFactory)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 194 */     ObjectNode localObjectNode = paramJsonNodeFactory.objectNode();
/* 195 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 196 */     if (localJsonToken == JsonToken.START_OBJECT) {}
/* 197 */     for (localJsonToken = paramJsonParser.nextToken(); 
/*     */         
/* 199 */         localJsonToken == JsonToken.FIELD_NAME; localJsonToken = paramJsonParser.nextToken()) {
/* 200 */       String str = paramJsonParser.getCurrentName();
/*     */       Object localObject;
/* 202 */       switch (paramJsonParser.nextToken()) {
/*     */       case START_OBJECT: 
/* 204 */         localObject = deserializeObject(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory);
/* 205 */         break;
/*     */       case START_ARRAY: 
/* 207 */         localObject = deserializeArray(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory);
/* 208 */         break;
/*     */       case VALUE_STRING: 
/* 210 */         localObject = paramJsonNodeFactory.textNode(paramJsonParser.getText());
/* 211 */         break;
/*     */       default: 
/* 213 */         localObject = deserializeAny(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory);
/*     */       }
/* 215 */       JsonNode localJsonNode = localObjectNode.replace(str, (JsonNode)localObject);
/* 216 */       if (localJsonNode != null) {
/* 217 */         _handleDuplicateField(str, localObjectNode, localJsonNode, (JsonNode)localObject);
/*     */       }
/*     */     }
/* 220 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */   protected final ArrayNode deserializeArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonNodeFactory paramJsonNodeFactory)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 227 */     ArrayNode localArrayNode = paramJsonNodeFactory.arrayNode();
/*     */     for (;;) {
/* 229 */       JsonToken localJsonToken = paramJsonParser.nextToken();
/* 230 */       if (localJsonToken == null) {
/* 231 */         throw paramDeserializationContext.mappingException("Unexpected end-of-input when binding data into ArrayNode");
/*     */       }
/* 233 */       switch (localJsonToken) {
/*     */       case START_OBJECT: 
/* 235 */         localArrayNode.add(deserializeObject(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory));
/* 236 */         break;
/*     */       case START_ARRAY: 
/* 238 */         localArrayNode.add(deserializeArray(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory));
/* 239 */         break;
/*     */       case END_ARRAY: 
/* 241 */         return localArrayNode;
/*     */       case VALUE_STRING: 
/* 243 */         localArrayNode.add(paramJsonNodeFactory.textNode(paramJsonParser.getText()));
/* 244 */         break;
/*     */       default: 
/* 246 */         localArrayNode.add(deserializeAny(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory));
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */   protected final JsonNode deserializeAny(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonNodeFactory paramJsonNodeFactory)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*     */     Object localObject;
/* 256 */     switch (paramJsonParser.getCurrentToken()) {
/*     */     case START_OBJECT: 
/* 258 */       return deserializeObject(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory);
/*     */     
/*     */     case START_ARRAY: 
/* 261 */       return deserializeArray(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory);
/*     */     
/*     */     case FIELD_NAME: 
/* 264 */       return deserializeObject(paramJsonParser, paramDeserializationContext, paramJsonNodeFactory);
/*     */     
/*     */ 
/*     */ 
/*     */     case VALUE_EMBEDDED_OBJECT: 
/* 269 */       localObject = paramJsonParser.getEmbeddedObject();
/* 270 */       if (localObject == null) {
/* 271 */         return paramJsonNodeFactory.nullNode();
/*     */       }
/* 273 */       Class localClass = localObject.getClass();
/* 274 */       if (localClass == byte[].class) {
/* 275 */         return paramJsonNodeFactory.binaryNode((byte[])localObject);
/*     */       }
/*     */       
/* 278 */       return paramJsonNodeFactory.pojoNode(localObject);
/*     */     
/*     */ 
/*     */     case VALUE_STRING: 
/* 282 */       return paramJsonNodeFactory.textNode(paramJsonParser.getText());
/*     */     
/*     */ 
/*     */     case VALUE_NUMBER_INT: 
/* 286 */       localObject = paramJsonParser.getNumberType();
/* 287 */       if ((localObject == JsonParser.NumberType.BIG_INTEGER) || (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)))
/*     */       {
/* 289 */         return paramJsonNodeFactory.numberNode(paramJsonParser.getBigIntegerValue());
/*     */       }
/* 291 */       if (localObject == JsonParser.NumberType.INT) {
/* 292 */         return paramJsonNodeFactory.numberNode(paramJsonParser.getIntValue());
/*     */       }
/* 294 */       return paramJsonNodeFactory.numberNode(paramJsonParser.getLongValue());
/*     */     
/*     */ 
/*     */ 
/*     */     case VALUE_NUMBER_FLOAT: 
/* 299 */       localObject = paramJsonParser.getNumberType();
/* 300 */       if ((localObject == JsonParser.NumberType.BIG_DECIMAL) || (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)))
/*     */       {
/* 302 */         return paramJsonNodeFactory.numberNode(paramJsonParser.getDecimalValue());
/*     */       }
/* 304 */       return paramJsonNodeFactory.numberNode(paramJsonParser.getDoubleValue());
/*     */     
/*     */ 
/*     */     case VALUE_TRUE: 
/* 308 */       return paramJsonNodeFactory.booleanNode(true);
/*     */     
/*     */     case VALUE_FALSE: 
/* 311 */       return paramJsonNodeFactory.booleanNode(false);
/*     */     
/*     */     case VALUE_NULL: 
/* 314 */       return paramJsonNodeFactory.nullNode();
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */     throw paramDeserializationContext.mappingException(getValueClass());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/BaseNodeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */