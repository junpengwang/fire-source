/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.FormatSchema;
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.core.JsonStreamContext;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ public class JsonParserDelegate extends JsonParser
/*     */ {
/*     */   protected JsonParser delegate;
/*     */   
/*     */   public JsonParserDelegate(JsonParser paramJsonParser)
/*     */   {
/*  25 */     this.delegate = paramJsonParser;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCodec(ObjectCodec paramObjectCodec)
/*     */   {
/*  36 */     this.delegate.setCodec(paramObjectCodec);
/*     */   }
/*     */   
/*     */   public ObjectCodec getCodec()
/*     */   {
/*  41 */     return this.delegate.getCodec();
/*     */   }
/*     */   
/*     */   public JsonParser enable(JsonParser.Feature paramFeature)
/*     */   {
/*  46 */     this.delegate.enable(paramFeature);
/*  47 */     return this;
/*     */   }
/*     */   
/*     */   public JsonParser disable(JsonParser.Feature paramFeature)
/*     */   {
/*  52 */     this.delegate.disable(paramFeature);
/*  53 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isEnabled(JsonParser.Feature paramFeature)
/*     */   {
/*  58 */     return this.delegate.isEnabled(paramFeature);
/*     */   }
/*     */   
/*     */   public FormatSchema getSchema()
/*     */   {
/*  63 */     return this.delegate.getSchema();
/*     */   }
/*     */   
/*     */   public void setSchema(FormatSchema paramFormatSchema)
/*     */   {
/*  68 */     this.delegate.setSchema(paramFormatSchema);
/*     */   }
/*     */   
/*     */   public boolean canUseSchema(FormatSchema paramFormatSchema)
/*     */   {
/*  73 */     return this.delegate.canUseSchema(paramFormatSchema);
/*     */   }
/*     */   
/*     */   public boolean requiresCustomCodec()
/*     */   {
/*  78 */     return this.delegate.requiresCustomCodec();
/*     */   }
/*     */   
/*     */   public Version version()
/*     */   {
/*  83 */     return this.delegate.version();
/*     */   }
/*     */   
/*     */   public Object getInputSource()
/*     */   {
/*  88 */     return this.delegate.getInputSource();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  99 */     this.delegate.close();
/*     */   }
/*     */   
/*     */   public boolean isClosed()
/*     */   {
/* 104 */     return this.delegate.isClosed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken getCurrentToken()
/*     */   {
/* 115 */     return this.delegate.getCurrentToken();
/*     */   }
/*     */   
/*     */   public boolean hasCurrentToken()
/*     */   {
/* 120 */     return this.delegate.hasCurrentToken();
/*     */   }
/*     */   
/*     */   public String getCurrentName() throws IOException, JsonParseException
/*     */   {
/* 125 */     return this.delegate.getCurrentName();
/*     */   }
/*     */   
/*     */   public JsonLocation getCurrentLocation()
/*     */   {
/* 130 */     return this.delegate.getCurrentLocation();
/*     */   }
/*     */   
/*     */   public JsonStreamContext getParsingContext()
/*     */   {
/* 135 */     return this.delegate.getParsingContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearCurrentToken()
/*     */   {
/* 146 */     this.delegate.clearCurrentToken();
/*     */   }
/*     */   
/*     */   public JsonToken getLastClearedToken()
/*     */   {
/* 151 */     return this.delegate.getLastClearedToken();
/*     */   }
/*     */   
/*     */   public void overrideCurrentName(String paramString)
/*     */   {
/* 156 */     this.delegate.overrideCurrentName(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getText()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 167 */     return this.delegate.getText();
/*     */   }
/*     */   
/*     */   public boolean hasTextCharacters()
/*     */   {
/* 172 */     return this.delegate.hasTextCharacters();
/*     */   }
/*     */   
/*     */   public char[] getTextCharacters() throws IOException, JsonParseException
/*     */   {
/* 177 */     return this.delegate.getTextCharacters();
/*     */   }
/*     */   
/*     */   public int getTextLength() throws IOException, JsonParseException
/*     */   {
/* 182 */     return this.delegate.getTextLength();
/*     */   }
/*     */   
/*     */   public int getTextOffset() throws IOException, JsonParseException
/*     */   {
/* 187 */     return this.delegate.getTextOffset();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BigInteger getBigIntegerValue()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 198 */     return this.delegate.getBigIntegerValue();
/*     */   }
/*     */   
/*     */   public boolean getBooleanValue() throws IOException, JsonParseException
/*     */   {
/* 203 */     return this.delegate.getBooleanValue();
/*     */   }
/*     */   
/*     */   public byte getByteValue() throws IOException, JsonParseException
/*     */   {
/* 208 */     return this.delegate.getByteValue();
/*     */   }
/*     */   
/*     */   public short getShortValue() throws IOException, JsonParseException
/*     */   {
/* 213 */     return this.delegate.getShortValue();
/*     */   }
/*     */   
/*     */   public BigDecimal getDecimalValue() throws IOException, JsonParseException
/*     */   {
/* 218 */     return this.delegate.getDecimalValue();
/*     */   }
/*     */   
/*     */   public double getDoubleValue() throws IOException, JsonParseException
/*     */   {
/* 223 */     return this.delegate.getDoubleValue();
/*     */   }
/*     */   
/*     */   public float getFloatValue() throws IOException, JsonParseException
/*     */   {
/* 228 */     return this.delegate.getFloatValue();
/*     */   }
/*     */   
/*     */   public int getIntValue() throws IOException, JsonParseException
/*     */   {
/* 233 */     return this.delegate.getIntValue();
/*     */   }
/*     */   
/*     */   public long getLongValue() throws IOException, JsonParseException
/*     */   {
/* 238 */     return this.delegate.getLongValue();
/*     */   }
/*     */   
/*     */   public JsonParser.NumberType getNumberType() throws IOException, JsonParseException
/*     */   {
/* 243 */     return this.delegate.getNumberType();
/*     */   }
/*     */   
/*     */   public Number getNumberValue() throws IOException, JsonParseException
/*     */   {
/* 248 */     return this.delegate.getNumberValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getValueAsInt()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 259 */     return this.delegate.getValueAsInt();
/*     */   }
/*     */   
/*     */   public int getValueAsInt(int paramInt) throws IOException, JsonParseException
/*     */   {
/* 264 */     return this.delegate.getValueAsInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getValueAsLong() throws IOException, JsonParseException
/*     */   {
/* 269 */     return this.delegate.getValueAsLong();
/*     */   }
/*     */   
/*     */   public long getValueAsLong(long paramLong) throws IOException, JsonParseException
/*     */   {
/* 274 */     return this.delegate.getValueAsLong(paramLong);
/*     */   }
/*     */   
/*     */   public double getValueAsDouble() throws IOException, JsonParseException
/*     */   {
/* 279 */     return this.delegate.getValueAsDouble();
/*     */   }
/*     */   
/*     */   public double getValueAsDouble(double paramDouble) throws IOException, JsonParseException
/*     */   {
/* 284 */     return this.delegate.getValueAsDouble(paramDouble);
/*     */   }
/*     */   
/*     */   public boolean getValueAsBoolean() throws IOException, JsonParseException
/*     */   {
/* 289 */     return this.delegate.getValueAsBoolean();
/*     */   }
/*     */   
/*     */   public boolean getValueAsBoolean(boolean paramBoolean) throws IOException, JsonParseException
/*     */   {
/* 294 */     return this.delegate.getValueAsBoolean(paramBoolean);
/*     */   }
/*     */   
/*     */   public String getValueAsString() throws IOException, JsonParseException
/*     */   {
/* 299 */     return this.delegate.getValueAsString();
/*     */   }
/*     */   
/*     */   public String getValueAsString(String paramString) throws IOException, JsonParseException
/*     */   {
/* 304 */     return this.delegate.getValueAsString(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEmbeddedObject()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 315 */     return this.delegate.getEmbeddedObject();
/*     */   }
/*     */   
/*     */   public byte[] getBinaryValue(Base64Variant paramBase64Variant) throws IOException, JsonParseException
/*     */   {
/* 320 */     return this.delegate.getBinaryValue(paramBase64Variant);
/*     */   }
/*     */   
/*     */   public int readBinaryValue(Base64Variant paramBase64Variant, OutputStream paramOutputStream)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 326 */     return this.delegate.readBinaryValue(paramBase64Variant, paramOutputStream);
/*     */   }
/*     */   
/*     */   public JsonLocation getTokenLocation()
/*     */   {
/* 331 */     return this.delegate.getTokenLocation();
/*     */   }
/*     */   
/*     */   public JsonToken nextToken() throws IOException, JsonParseException
/*     */   {
/* 336 */     return this.delegate.nextToken();
/*     */   }
/*     */   
/*     */   public JsonToken nextValue() throws IOException, JsonParseException
/*     */   {
/* 341 */     return this.delegate.nextValue();
/*     */   }
/*     */   
/*     */   public JsonParser skipChildren() throws IOException, JsonParseException
/*     */   {
/* 346 */     this.delegate.skipChildren();
/*     */     
/* 348 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/JsonParserDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */