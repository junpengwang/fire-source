/*      */ package com.shaded.fasterxml.jackson.core;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Writer;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Iterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class JsonParser
/*      */   implements Closeable, Versioned
/*      */ {
/*      */   private static final int MIN_BYTE_I = -128;
/*      */   private static final int MAX_BYTE_I = 255;
/*      */   private static final int MIN_SHORT_I = -32768;
/*      */   private static final int MAX_SHORT_I = 32767;
/*      */   protected int _features;
/*      */   protected JsonParser() {}
/*      */   
/*      */   public static enum NumberType
/*      */   {
/*   37 */     INT,  LONG,  BIG_INTEGER,  FLOAT,  DOUBLE,  BIG_DECIMAL;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private NumberType() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static enum Feature
/*      */   {
/*   59 */     AUTO_CLOSE_SOURCE(true), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   75 */     ALLOW_COMMENTS(false), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   86 */     ALLOW_UNQUOTED_FIELD_NAMES(false), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   99 */     ALLOW_SINGLE_QUOTES(false), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  112 */     ALLOW_UNQUOTED_CONTROL_CHARS(false), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  123 */     ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  135 */     ALLOW_NUMERIC_LEADING_ZEROS(false), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  155 */     ALLOW_NON_NUMERIC_NUMBERS(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final boolean _defaultState;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static int collectDefaults()
/*      */     {
/*  170 */       int i = 0;
/*  171 */       for (Feature localFeature : values()) {
/*  172 */         if (localFeature.enabledByDefault()) {
/*  173 */           i |= localFeature.getMask();
/*      */         }
/*      */       }
/*  176 */       return i;
/*      */     }
/*      */     
/*      */     private Feature(boolean paramBoolean) {
/*  180 */       this._defaultState = paramBoolean;
/*      */     }
/*      */     
/*  183 */     public boolean enabledByDefault() { return this._defaultState; }
/*      */     
/*  185 */     public int getMask() { return 1 << ordinal(); }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonParser(int paramInt)
/*      */   {
/*  209 */     this._features = paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract ObjectCodec getCodec();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void setCodec(ObjectCodec paramObjectCodec);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getInputSource()
/*      */   {
/*  242 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSchema(FormatSchema paramFormatSchema)
/*      */   {
/*  267 */     throw new UnsupportedOperationException("Parser of type " + getClass().getName() + " does not support schema of type '" + paramFormatSchema.getSchemaType() + "'");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FormatSchema getSchema()
/*      */   {
/*  278 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean canUseSchema(FormatSchema paramFormatSchema)
/*      */   {
/*  290 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean requiresCustomCodec()
/*      */   {
/*  307 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Version version();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void close()
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int releaseBuffered(OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/*  369 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int releaseBuffered(Writer paramWriter)
/*      */     throws IOException
/*      */   {
/*  389 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser enable(Feature paramFeature)
/*      */   {
/*  404 */     this._features |= paramFeature.getMask();
/*  405 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser disable(Feature paramFeature)
/*      */   {
/*  414 */     this._features &= (paramFeature.getMask() ^ 0xFFFFFFFF);
/*  415 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser configure(Feature paramFeature, boolean paramBoolean)
/*      */   {
/*  424 */     if (paramBoolean) {
/*  425 */       enable(paramFeature);
/*      */     } else {
/*  427 */       disable(paramFeature);
/*      */     }
/*  429 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(Feature paramFeature)
/*      */   {
/*  436 */     return (this._features & paramFeature.getMask()) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonToken nextToken()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonToken nextValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean nextFieldName(SerializableString paramSerializableString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  493 */     return (nextToken() == JsonToken.FIELD_NAME) && (paramSerializableString.getValue().equals(getCurrentName()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String nextTextValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  510 */     return nextToken() == JsonToken.VALUE_STRING ? getText() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int nextIntValue(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  527 */     return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long nextLongValue(long paramLong)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  544 */     return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Boolean nextBooleanValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  564 */     switch (nextToken()) {
/*      */     case VALUE_TRUE: 
/*  566 */       return Boolean.TRUE;
/*      */     case VALUE_FALSE: 
/*  568 */       return Boolean.FALSE;
/*      */     }
/*  570 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonParser skipChildren()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract boolean isClosed();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonToken getCurrentToken();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract boolean hasCurrentToken();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract String getCurrentName()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonStreamContext getParsingContext();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonLocation getTokenLocation();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonLocation getCurrentLocation();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isExpectedStartArrayToken()
/*      */   {
/*  686 */     return getCurrentToken() == JsonToken.START_ARRAY;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void clearCurrentToken();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonToken getLastClearedToken();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void overrideCurrentName(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract String getText()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract char[] getTextCharacters()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getTextLength()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getTextOffset()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract boolean hasTextCharacters();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Number getNumberValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract NumberType getNumberType()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte getByteValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  857 */     int i = getIntValue();
/*      */     
/*      */ 
/*      */ 
/*  861 */     if ((i < -128) || (i > 255)) {
/*  862 */       throw _constructError("Numeric value (" + getText() + ") out of range of Java byte");
/*      */     }
/*  864 */     return (byte)i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public short getShortValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  883 */     int i = getIntValue();
/*  884 */     if ((i < 32768) || (i > 32767)) {
/*  885 */       throw _constructError("Numeric value (" + getText() + ") out of range of Java short");
/*      */     }
/*  887 */     return (short)i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract int getIntValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract long getLongValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract BigInteger getBigIntegerValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract float getFloatValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract double getDoubleValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract BigDecimal getDecimalValue()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getBooleanValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  994 */     JsonToken localJsonToken = getCurrentToken();
/*  995 */     if (localJsonToken == JsonToken.VALUE_TRUE) return true;
/*  996 */     if (localJsonToken == JsonToken.VALUE_FALSE) return false;
/*  997 */     throw new JsonParseException("Current token (" + localJsonToken + ") not of boolean type", getCurrentLocation());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Object getEmbeddedObject()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract byte[] getBinaryValue(Base64Variant paramBase64Variant)
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] getBinaryValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1048 */     return getBinaryValue(Base64Variants.getDefaultVariant());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int readBinaryValue(OutputStream paramOutputStream)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1066 */     return readBinaryValue(Base64Variants.getDefaultVariant(), paramOutputStream);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int readBinaryValue(Base64Variant paramBase64Variant, OutputStream paramOutputStream)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1083 */     _reportUnsupportedOperation();
/* 1084 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getValueAsInt()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1105 */     return getValueAsInt(0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getValueAsInt(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1120 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getValueAsLong()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1135 */     return getValueAsLong(0L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getValueAsLong(long paramLong)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1150 */     return paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getValueAsDouble()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1165 */     return getValueAsDouble(0.0D);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getValueAsDouble(double paramDouble)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1180 */     return paramDouble;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getValueAsBoolean()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1195 */     return getValueAsBoolean(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getValueAsBoolean(boolean paramBoolean)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1210 */     return paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getValueAsString()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1225 */     return getValueAsString(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract String getValueAsString(String paramString)
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValueAs(Class<T> paramClass)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1272 */     ObjectCodec localObjectCodec = getCodec();
/* 1273 */     if (localObjectCodec == null) {
/* 1274 */       throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
/*      */     }
/* 1276 */     return (T)localObjectCodec.readValue(this, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValueAs(TypeReference<?> paramTypeReference)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1301 */     ObjectCodec localObjectCodec = getCodec();
/* 1302 */     if (localObjectCodec == null) {
/* 1303 */       throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1308 */     return (T)localObjectCodec.readValue(this, paramTypeReference);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Iterator<T> readValuesAs(Class<T> paramClass)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1318 */     ObjectCodec localObjectCodec = getCodec();
/* 1319 */     if (localObjectCodec == null) {
/* 1320 */       throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
/*      */     }
/* 1322 */     return localObjectCodec.readValues(this, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Iterator<T> readValuesAs(TypeReference<?> paramTypeReference)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1332 */     ObjectCodec localObjectCodec = getCodec();
/* 1333 */     if (localObjectCodec == null) {
/* 1334 */       throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
/*      */     }
/* 1336 */     return localObjectCodec.readValues(this, paramTypeReference);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends TreeNode> T readValueAsTree()
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1350 */     ObjectCodec localObjectCodec = getCodec();
/* 1351 */     if (localObjectCodec == null) {
/* 1352 */       throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into JsonNode tree");
/*      */     }
/* 1354 */     return localObjectCodec.readTree(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonParseException _constructError(String paramString)
/*      */   {
/* 1369 */     return new JsonParseException(paramString, getCurrentLocation());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _reportUnsupportedOperation()
/*      */   {
/* 1379 */     throw new UnsupportedOperationException("Operation not supported by parser of type " + getClass().getName());
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */