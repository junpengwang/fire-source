/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
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
/*     */ public abstract class ValueNode
/*     */   extends BaseJsonNode
/*     */ {
/*     */   public <T extends JsonNode> T deepCopy()
/*     */   {
/*  28 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract JsonToken asToken();
/*     */   
/*     */   public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  37 */     paramTypeSerializer.writeTypePrefixForScalar(this, paramJsonGenerator);
/*  38 */     serialize(paramJsonGenerator, paramSerializerProvider);
/*  39 */     paramTypeSerializer.writeTypeSuffixForScalar(this, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  49 */     return asText();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonNode get(int paramInt)
/*     */   {
/*  60 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public final JsonNode path(int paramInt)
/*     */   {
/*  66 */     return MissingNode.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */   public final boolean has(int paramInt)
/*     */   {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public final boolean hasNonNull(int paramInt)
/*     */   {
/*  78 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public final JsonNode get(String paramString)
/*     */   {
/*  84 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public final JsonNode path(String paramString)
/*     */   {
/*  90 */     return MissingNode.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */   public final boolean has(String paramString)
/*     */   {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public final boolean hasNonNull(String paramString)
/*     */   {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JsonNode findValue(String paramString)
/*     */   {
/* 114 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final ObjectNode findParent(String paramString)
/*     */   {
/* 121 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public final List<JsonNode> findValues(String paramString, List<JsonNode> paramList)
/*     */   {
/* 127 */     return paramList;
/*     */   }
/*     */   
/*     */ 
/*     */   public final List<String> findValuesAsText(String paramString, List<String> paramList)
/*     */   {
/* 133 */     return paramList;
/*     */   }
/*     */   
/*     */ 
/*     */   public final List<JsonNode> findParents(String paramString, List<JsonNode> paramList)
/*     */   {
/* 139 */     return paramList;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/ValueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */