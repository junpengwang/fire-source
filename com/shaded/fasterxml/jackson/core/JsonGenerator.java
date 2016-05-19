/*      */ package com.shaded.fasterxml.jackson.core;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import java.io.Closeable;
/*      */ import java.io.Flushable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class JsonGenerator
/*      */   implements Closeable, Flushable, Versioned
/*      */ {
/*      */   protected PrettyPrinter _cfgPrettyPrinter;
/*      */   public abstract JsonGenerator setCodec(ObjectCodec paramObjectCodec);
/*      */   
/*      */   public abstract ObjectCodec getCodec();
/*      */   
/*      */   public static enum Feature
/*      */   {
/*   41 */     AUTO_CLOSE_TARGET(true), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   53 */     AUTO_CLOSE_JSON_CONTENT(true), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   64 */     QUOTE_FIELD_NAMES(true), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   78 */     QUOTE_NON_NUMERIC_NUMBERS(true), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   95 */     WRITE_NUMBERS_AS_STRINGS(false), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  108 */     FLUSH_PASSED_TO_STREAM(true), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  119 */     ESCAPE_NON_ASCII(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private final boolean _defaultState;
/*      */     
/*      */ 
/*      */     private final int _mask;
/*      */     
/*      */ 
/*      */ 
/*      */     public static int collectDefaults()
/*      */     {
/*  133 */       int i = 0;
/*  134 */       for (Feature localFeature : values()) {
/*  135 */         if (localFeature.enabledByDefault()) {
/*  136 */           i |= localFeature.getMask();
/*      */         }
/*      */       }
/*  139 */       return i;
/*      */     }
/*      */     
/*      */     private Feature(boolean paramBoolean) {
/*  143 */       this._mask = (1 << ordinal());
/*  144 */       this._defaultState = paramBoolean;
/*      */     }
/*      */     
/*  147 */     public boolean enabledByDefault() { return this._defaultState; }
/*  148 */     public int getMask() { return this._mask; }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  205 */     throw new UnsupportedOperationException("Generator of type " + getClass().getName() + " does not support schema of type '" + paramFormatSchema.getSchemaType() + "'");
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
/*  216 */     return null;
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
/*  228 */     return false;
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
/*      */   public Object getOutputTarget()
/*      */   {
/*  253 */     return null;
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
/*      */   public JsonGenerator setRootValueSeparator(SerializableString paramSerializableString)
/*      */   {
/*  266 */     throw new UnsupportedOperationException();
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
/*      */   public abstract JsonGenerator enable(Feature paramFeature);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonGenerator disable(Feature paramFeature);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final JsonGenerator configure(Feature paramFeature, boolean paramBoolean)
/*      */   {
/*  299 */     if (paramBoolean) {
/*  300 */       enable(paramFeature);
/*      */     } else {
/*  302 */       disable(paramFeature);
/*      */     }
/*  304 */     return this;
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
/*      */   public abstract boolean isEnabled(Feature paramFeature);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonGenerator setPrettyPrinter(PrettyPrinter paramPrettyPrinter)
/*      */   {
/*  331 */     this._cfgPrettyPrinter = paramPrettyPrinter;
/*  332 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PrettyPrinter getPrettyPrinter()
/*      */   {
/*  342 */     return this._cfgPrettyPrinter;
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
/*      */   public abstract JsonGenerator useDefaultPrettyPrinter();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonGenerator setHighestNonEscapedChar(int paramInt)
/*      */   {
/*  376 */     return this;
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
/*      */   public int getHighestEscapedChar()
/*      */   {
/*  392 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public CharacterEscapes getCharacterEscapes()
/*      */   {
/*  400 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonGenerator setCharacterEscapes(CharacterEscapes paramCharacterEscapes)
/*      */   {
/*  408 */     return this;
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
/*      */   public abstract void writeStartArray()
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeEndArray()
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeStartObject()
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeEndObject()
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeFieldName(String paramString)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeFieldName(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeString(String paramString)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeString(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeString(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRawUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRaw(String paramString)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRaw(String paramString, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRaw(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRaw(char paramChar)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeRaw(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  655 */     writeRaw(paramSerializableString.getValue());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRawValue(String paramString)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRawValue(String paramString, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeRawValue(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeBinary(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  708 */     writeBinary(Base64Variants.getDefaultVariant(), paramArrayOfByte, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeBinary(byte[] paramArrayOfByte)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  720 */     writeBinary(Base64Variants.getDefaultVariant(), paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public int writeBinary(InputStream paramInputStream, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  738 */     return writeBinary(Base64Variants.getDefaultVariant(), paramInputStream, paramInt);
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
/*      */   public abstract int writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, int paramInt)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeNumber(short paramShort)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  782 */     writeNumber(paramShort);
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
/*      */   public abstract void writeNumber(int paramInt)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeNumber(long paramLong)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeNumber(BigInteger paramBigInteger)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeNumber(double paramDouble)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeNumber(float paramFloat)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeNumber(BigDecimal paramBigDecimal)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeNumber(String paramString)
/*      */     throws IOException, JsonGenerationException, UnsupportedOperationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeBoolean(boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeNull()
/*      */     throws IOException, JsonGenerationException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeObject(Object paramObject)
/*      */     throws IOException, JsonProcessingException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract void writeTree(TreeNode paramTreeNode)
/*      */     throws IOException, JsonProcessingException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeStringField(String paramString1, String paramString2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  936 */     writeFieldName(paramString1);
/*  937 */     writeString(paramString2);
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
/*      */   public final void writeBooleanField(String paramString, boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  951 */     writeFieldName(paramString);
/*  952 */     writeBoolean(paramBoolean);
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
/*      */   public final void writeNullField(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  966 */     writeFieldName(paramString);
/*  967 */     writeNull();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeNumberField(String paramString, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  980 */     writeFieldName(paramString);
/*  981 */     writeNumber(paramInt);
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
/*      */   public final void writeNumberField(String paramString, long paramLong)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  995 */     writeFieldName(paramString);
/*  996 */     writeNumber(paramLong);
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
/*      */   public final void writeNumberField(String paramString, double paramDouble)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1010 */     writeFieldName(paramString);
/* 1011 */     writeNumber(paramDouble);
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
/*      */   public final void writeNumberField(String paramString, float paramFloat)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1025 */     writeFieldName(paramString);
/* 1026 */     writeNumber(paramFloat);
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
/*      */   public final void writeNumberField(String paramString, BigDecimal paramBigDecimal)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1041 */     writeFieldName(paramString);
/* 1042 */     writeNumber(paramBigDecimal);
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
/*      */   public final void writeBinaryField(String paramString, byte[] paramArrayOfByte)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1057 */     writeFieldName(paramString);
/* 1058 */     writeBinary(paramArrayOfByte);
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
/*      */   public final void writeArrayFieldStart(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1077 */     writeFieldName(paramString);
/* 1078 */     writeStartArray();
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
/*      */   public final void writeObjectFieldStart(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1097 */     writeFieldName(paramString);
/* 1098 */     writeStartObject();
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
/*      */   public final void writeObjectField(String paramString, Object paramObject)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1113 */     writeFieldName(paramString);
/* 1114 */     writeObject(paramObject);
/*      */   }
/*      */   
/*      */   public abstract void copyCurrentEvent(JsonParser paramJsonParser)
/*      */     throws IOException, JsonProcessingException;
/*      */   
/*      */   public abstract void copyCurrentStructure(JsonParser paramJsonParser)
/*      */     throws IOException, JsonProcessingException;
/*      */   
/*      */   public abstract JsonStreamContext getOutputContext();
/*      */   
/*      */   public abstract void flush()
/*      */     throws IOException;
/*      */   
/*      */   public abstract boolean isClosed();
/*      */   
/*      */   public abstract void close()
/*      */     throws IOException;
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */