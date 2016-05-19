/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.FormatSchema;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator.Feature;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*     */ import com.shaded.fasterxml.jackson.core.PrettyPrinter;
/*     */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*     */ import com.shaded.fasterxml.jackson.core.io.CharacterEscapes;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ public class JsonGeneratorDelegate extends JsonGenerator
/*     */ {
/*     */   protected JsonGenerator delegate;
/*     */   
/*     */   public JsonGeneratorDelegate(JsonGenerator paramJsonGenerator)
/*     */   {
/*  25 */     this.delegate = paramJsonGenerator;
/*     */   }
/*     */   
/*     */   public ObjectCodec getCodec()
/*     */   {
/*  30 */     return this.delegate.getCodec();
/*     */   }
/*     */   
/*     */   public JsonGenerator setCodec(ObjectCodec paramObjectCodec)
/*     */   {
/*  35 */     this.delegate.setCodec(paramObjectCodec);
/*  36 */     return this;
/*     */   }
/*     */   
/*     */   public void setSchema(FormatSchema paramFormatSchema)
/*     */   {
/*  41 */     this.delegate.setSchema(paramFormatSchema);
/*     */   }
/*     */   
/*     */   public FormatSchema getSchema()
/*     */   {
/*  46 */     return this.delegate.getSchema();
/*     */   }
/*     */   
/*     */   public boolean canUseSchema(FormatSchema paramFormatSchema)
/*     */   {
/*  51 */     return this.delegate.canUseSchema(paramFormatSchema);
/*     */   }
/*     */   
/*     */   public com.shaded.fasterxml.jackson.core.Version version()
/*     */   {
/*  56 */     return this.delegate.version();
/*     */   }
/*     */   
/*     */   public Object getOutputTarget()
/*     */   {
/*  61 */     return this.delegate.getOutputTarget();
/*     */   }
/*     */   
/*     */   public JsonGenerator setRootValueSeparator(SerializableString paramSerializableString)
/*     */   {
/*  66 */     this.delegate.setRootValueSeparator(paramSerializableString);
/*  67 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonGenerator enable(JsonGenerator.Feature paramFeature)
/*     */   {
/*  78 */     this.delegate.enable(paramFeature);
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   public JsonGenerator disable(JsonGenerator.Feature paramFeature)
/*     */   {
/*  84 */     this.delegate.disable(paramFeature);
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isEnabled(JsonGenerator.Feature paramFeature)
/*     */   {
/*  90 */     return this.delegate.isEnabled(paramFeature);
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
/*     */   public JsonGenerator setPrettyPrinter(PrettyPrinter paramPrettyPrinter)
/*     */   {
/* 104 */     this.delegate.setPrettyPrinter(paramPrettyPrinter);
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public PrettyPrinter getPrettyPrinter()
/*     */   {
/* 110 */     return this.delegate.getPrettyPrinter();
/*     */   }
/*     */   
/*     */   public JsonGenerator useDefaultPrettyPrinter()
/*     */   {
/* 115 */     this.delegate.useDefaultPrettyPrinter();
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   public JsonGenerator setHighestNonEscapedChar(int paramInt)
/*     */   {
/* 121 */     this.delegate.setHighestNonEscapedChar(paramInt);
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public int getHighestEscapedChar()
/*     */   {
/* 127 */     return this.delegate.getHighestEscapedChar();
/*     */   }
/*     */   
/*     */   public CharacterEscapes getCharacterEscapes()
/*     */   {
/* 132 */     return this.delegate.getCharacterEscapes();
/*     */   }
/*     */   
/*     */   public JsonGenerator setCharacterEscapes(CharacterEscapes paramCharacterEscapes)
/*     */   {
/* 137 */     this.delegate.setCharacterEscapes(paramCharacterEscapes);
/* 138 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeStartArray()
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 149 */     this.delegate.writeStartArray();
/*     */   }
/*     */   
/*     */   public void writeEndArray()
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 155 */     this.delegate.writeEndArray();
/*     */   }
/*     */   
/*     */   public void writeStartObject() throws IOException, JsonGenerationException
/*     */   {
/* 160 */     this.delegate.writeStartObject();
/*     */   }
/*     */   
/*     */   public void writeEndObject() throws IOException, JsonGenerationException
/*     */   {
/* 165 */     this.delegate.writeEndObject();
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeFieldName(String paramString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 172 */     this.delegate.writeFieldName(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeFieldName(SerializableString paramSerializableString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 179 */     this.delegate.writeFieldName(paramSerializableString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeString(String paramString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 190 */     this.delegate.writeString(paramString);
/*     */   }
/*     */   
/*     */   public void writeString(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*     */   {
/* 195 */     this.delegate.writeString(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void writeString(SerializableString paramSerializableString) throws IOException, JsonGenerationException
/*     */   {
/* 200 */     this.delegate.writeString(paramSerializableString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeRawUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 207 */     this.delegate.writeRawUTF8String(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 214 */     this.delegate.writeUTF8String(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeRaw(String paramString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 225 */     this.delegate.writeRaw(paramString);
/*     */   }
/*     */   
/*     */   public void writeRaw(String paramString, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*     */   {
/* 230 */     this.delegate.writeRaw(paramString, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void writeRaw(SerializableString paramSerializableString)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 236 */     this.delegate.writeRaw(paramSerializableString);
/*     */   }
/*     */   
/*     */   public void writeRaw(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*     */   {
/* 241 */     this.delegate.writeRaw(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void writeRaw(char paramChar) throws IOException, JsonGenerationException
/*     */   {
/* 246 */     this.delegate.writeRaw(paramChar);
/*     */   }
/*     */   
/*     */   public void writeRawValue(String paramString) throws IOException, JsonGenerationException
/*     */   {
/* 251 */     this.delegate.writeRawValue(paramString);
/*     */   }
/*     */   
/*     */   public void writeRawValue(String paramString, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*     */   {
/* 256 */     this.delegate.writeRawValue(paramString, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void writeRawValue(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*     */   {
/* 261 */     this.delegate.writeRawValue(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 268 */     this.delegate.writeBinary(paramBase64Variant, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 274 */     return this.delegate.writeBinary(paramBase64Variant, paramInputStream, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeNumber(short paramShort)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 285 */     this.delegate.writeNumber(paramShort);
/*     */   }
/*     */   
/*     */   public void writeNumber(int paramInt) throws IOException, JsonGenerationException
/*     */   {
/* 290 */     this.delegate.writeNumber(paramInt);
/*     */   }
/*     */   
/*     */   public void writeNumber(long paramLong) throws IOException, JsonGenerationException
/*     */   {
/* 295 */     this.delegate.writeNumber(paramLong);
/*     */   }
/*     */   
/*     */   public void writeNumber(BigInteger paramBigInteger)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 301 */     this.delegate.writeNumber(paramBigInteger);
/*     */   }
/*     */   
/*     */   public void writeNumber(double paramDouble)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 307 */     this.delegate.writeNumber(paramDouble);
/*     */   }
/*     */   
/*     */   public void writeNumber(float paramFloat)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 313 */     this.delegate.writeNumber(paramFloat);
/*     */   }
/*     */   
/*     */   public void writeNumber(BigDecimal paramBigDecimal)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 319 */     this.delegate.writeNumber(paramBigDecimal);
/*     */   }
/*     */   
/*     */   public void writeNumber(String paramString) throws IOException, JsonGenerationException, UnsupportedOperationException
/*     */   {
/* 324 */     this.delegate.writeNumber(paramString);
/*     */   }
/*     */   
/*     */   public void writeBoolean(boolean paramBoolean) throws IOException, JsonGenerationException
/*     */   {
/* 329 */     this.delegate.writeBoolean(paramBoolean);
/*     */   }
/*     */   
/*     */   public void writeNull() throws IOException, JsonGenerationException
/*     */   {
/* 334 */     this.delegate.writeNull();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeObject(Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 345 */     this.delegate.writeObject(paramObject);
/*     */   }
/*     */   
/*     */   public void writeTree(com.shaded.fasterxml.jackson.core.TreeNode paramTreeNode) throws IOException, JsonProcessingException
/*     */   {
/* 350 */     this.delegate.writeTree(paramTreeNode);
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
/*     */ 
/*     */   public void copyCurrentEvent(JsonParser paramJsonParser)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 369 */     this.delegate.copyCurrentEvent(paramJsonParser);
/*     */   }
/*     */   
/*     */   public void copyCurrentStructure(JsonParser paramJsonParser) throws IOException, JsonProcessingException
/*     */   {
/* 374 */     this.delegate.copyCurrentStructure(paramJsonParser);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public com.shaded.fasterxml.jackson.core.JsonStreamContext getOutputContext()
/*     */   {
/* 385 */     return this.delegate.getOutputContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 396 */     this.delegate.flush();
/*     */   }
/*     */   
/*     */   public void close() throws IOException
/*     */   {
/* 401 */     this.delegate.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isClosed()
/*     */   {
/* 412 */     return this.delegate.isClosed();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/JsonGeneratorDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */