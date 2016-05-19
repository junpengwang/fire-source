/*     */ package com.shaded.fasterxml.jackson.core;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.type.ResolvedType;
/*     */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObjectCodec
/*     */ {
/*     */   public abstract <T> T readValue(JsonParser paramJsonParser, Class<T> paramClass)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract <T> T readValue(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract <T> T readValue(JsonParser paramJsonParser, ResolvedType paramResolvedType)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract <T extends TreeNode> T readTree(JsonParser paramJsonParser)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract <T> Iterator<T> readValues(JsonParser paramJsonParser, Class<T> paramClass)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract <T> Iterator<T> readValues(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract <T> Iterator<T> readValues(JsonParser paramJsonParser, ResolvedType paramResolvedType)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract void writeValue(JsonGenerator paramJsonGenerator, Object paramObject)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract TreeNode createObjectNode();
/*     */   
/*     */   public abstract TreeNode createArrayNode();
/*     */   
/*     */   public abstract JsonParser treeAsTokens(TreeNode paramTreeNode);
/*     */   
/*     */   public abstract <T> T treeToValue(TreeNode paramTreeNode, Class<T> paramClass)
/*     */     throws JsonProcessingException;
/*     */   
/*     */   @Deprecated
/*     */   public abstract JsonFactory getJsonFactory();
/*     */   
/*     */   public JsonFactory getFactory()
/*     */   {
/* 161 */     return getJsonFactory();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/ObjectCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */