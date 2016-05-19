/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.PrettyPrinter;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class MinimalPrettyPrinter
/*     */   implements PrettyPrinter, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -562765100295218442L;
/*     */   public static final String DEFAULT_ROOT_VALUE_SEPARATOR = " ";
/*  36 */   protected String _rootValueSeparator = " ";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MinimalPrettyPrinter()
/*     */   {
/*  45 */     this(" ");
/*     */   }
/*     */   
/*     */   public MinimalPrettyPrinter(String paramString) {
/*  49 */     this._rootValueSeparator = paramString;
/*     */   }
/*     */   
/*     */   public void setRootValueSeparator(String paramString) {
/*  53 */     this._rootValueSeparator = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeRootValueSeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  65 */     if (this._rootValueSeparator != null) {
/*  66 */       paramJsonGenerator.writeRaw(this._rootValueSeparator);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeStartObject(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  74 */     paramJsonGenerator.writeRaw('{');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void beforeObjectEntries(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeObjectFieldValueSeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  95 */     paramJsonGenerator.writeRaw(':');
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
/*     */   public void writeObjectEntrySeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 109 */     paramJsonGenerator.writeRaw(',');
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEndObject(JsonGenerator paramJsonGenerator, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 116 */     paramJsonGenerator.writeRaw('}');
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeStartArray(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 123 */     paramJsonGenerator.writeRaw('[');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void beforeArrayValues(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeArrayValueSeparator(JsonGenerator paramJsonGenerator)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 144 */     paramJsonGenerator.writeRaw(',');
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEndArray(JsonGenerator paramJsonGenerator, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 151 */     paramJsonGenerator.writeRaw(']');
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/MinimalPrettyPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */