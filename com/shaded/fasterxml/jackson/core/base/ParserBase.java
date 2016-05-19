/*      */ package com.shaded.fasterxml.jackson.core.base;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*      */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*      */ import com.shaded.fasterxml.jackson.core.Version;
/*      */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*      */ import com.shaded.fasterxml.jackson.core.io.NumberInput;
/*      */ import com.shaded.fasterxml.jackson.core.json.JsonReadContext;
/*      */ import com.shaded.fasterxml.jackson.core.json.PackageVersion;
/*      */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import com.shaded.fasterxml.jackson.core.util.TextBuffer;
/*      */ import java.io.IOException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ParserBase
/*      */   extends ParserMinimalBase
/*      */ {
/*      */   protected final IOContext _ioContext;
/*      */   protected boolean _closed;
/*   55 */   protected int _inputPtr = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   60 */   protected int _inputEnd = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   72 */   protected long _currInputProcessed = 0L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   78 */   protected int _currInputRow = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   86 */   protected int _currInputRowStart = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  102 */   protected long _tokenInputTotal = 0L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  107 */   protected int _tokenInputRow = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  113 */   protected int _tokenInputCol = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonReadContext _parsingContext;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonToken _nextToken;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final TextBuffer _textBuffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  152 */   protected char[] _nameCopyBuffer = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  159 */   protected boolean _nameCopied = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  165 */   protected ByteArrayBuilder _byteArrayBuilder = null;
/*      */   
/*      */ 
/*      */ 
/*      */   protected byte[] _binaryValue;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NR_UNKNOWN = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NR_INT = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NR_LONG = 2;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NR_BIGINT = 4;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NR_DOUBLE = 8;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NR_BIGDECIMAL = 16;
/*      */   
/*      */ 
/*  196 */   static final BigInteger BI_MIN_INT = BigInteger.valueOf(-2147483648L);
/*  197 */   static final BigInteger BI_MAX_INT = BigInteger.valueOf(2147483647L);
/*      */   
/*  199 */   static final BigInteger BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
/*  200 */   static final BigInteger BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
/*      */   
/*  202 */   static final BigDecimal BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
/*  203 */   static final BigDecimal BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
/*      */   
/*  205 */   static final BigDecimal BD_MIN_INT = new BigDecimal(BI_MIN_INT);
/*  206 */   static final BigDecimal BD_MAX_INT = new BigDecimal(BI_MAX_INT);
/*      */   
/*      */   static final long MIN_INT_L = -2147483648L;
/*      */   
/*      */   static final long MAX_INT_L = 2147483647L;
/*      */   
/*      */   static final double MIN_LONG_D = -9.223372036854776E18D;
/*      */   
/*      */   static final double MAX_LONG_D = 9.223372036854776E18D;
/*      */   
/*      */   static final double MIN_INT_D = -2.147483648E9D;
/*      */   
/*      */   static final double MAX_INT_D = 2.147483647E9D;
/*      */   
/*      */   protected static final int INT_0 = 48;
/*      */   
/*      */   protected static final int INT_1 = 49;
/*      */   
/*      */   protected static final int INT_2 = 50;
/*      */   
/*      */   protected static final int INT_3 = 51;
/*      */   
/*      */   protected static final int INT_4 = 52;
/*      */   
/*      */   protected static final int INT_5 = 53;
/*      */   
/*      */   protected static final int INT_6 = 54;
/*      */   
/*      */   protected static final int INT_7 = 55;
/*      */   
/*      */   protected static final int INT_8 = 56;
/*      */   
/*      */   protected static final int INT_9 = 57;
/*      */   
/*      */   protected static final int INT_MINUS = 45;
/*      */   
/*      */   protected static final int INT_PLUS = 43;
/*      */   
/*      */   protected static final int INT_DECIMAL_POINT = 46;
/*      */   protected static final int INT_e = 101;
/*      */   protected static final int INT_E = 69;
/*      */   protected static final char CHAR_NULL = '\000';
/*  248 */   protected int _numTypesValid = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int _numberInt;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected long _numberLong;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected double _numberDouble;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BigInteger _numberBigInt;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BigDecimal _numberBigDecimal;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _numberNegative;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int _intLength;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int _fractLength;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int _expLength;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ParserBase(IOContext paramIOContext, int paramInt)
/*      */   {
/*  301 */     this._features = paramInt;
/*  302 */     this._ioContext = paramIOContext;
/*  303 */     this._textBuffer = paramIOContext.constructTextBuffer();
/*  304 */     this._parsingContext = JsonReadContext.createRootContext();
/*      */   }
/*      */   
/*      */   public Version version()
/*      */   {
/*  309 */     return PackageVersion.VERSION;
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
/*      */   public String getCurrentName()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  327 */     if ((this._currToken == JsonToken.START_OBJECT) || (this._currToken == JsonToken.START_ARRAY)) {
/*  328 */       JsonReadContext localJsonReadContext = this._parsingContext.getParent();
/*  329 */       return localJsonReadContext.getCurrentName();
/*      */     }
/*  331 */     return this._parsingContext.getCurrentName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void overrideCurrentName(String paramString)
/*      */   {
/*  338 */     JsonReadContext localJsonReadContext = this._parsingContext;
/*  339 */     if ((this._currToken == JsonToken.START_OBJECT) || (this._currToken == JsonToken.START_ARRAY)) {
/*  340 */       localJsonReadContext = localJsonReadContext.getParent();
/*      */     }
/*  342 */     localJsonReadContext.setCurrentName(paramString);
/*      */   }
/*      */   
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  348 */     if (!this._closed) {
/*  349 */       this._closed = true;
/*      */       try {
/*  351 */         _closeInput();
/*      */       }
/*      */       finally
/*      */       {
/*  355 */         _releaseBuffers();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isClosed() {
/*  361 */     return this._closed;
/*      */   }
/*      */   
/*      */   public JsonReadContext getParsingContext()
/*      */   {
/*  366 */     return this._parsingContext;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonLocation getTokenLocation()
/*      */   {
/*  377 */     return new JsonLocation(this._ioContext.getSourceReference(), getTokenCharacterOffset(), getTokenLineNr(), getTokenColumnNr());
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
/*      */   public JsonLocation getCurrentLocation()
/*      */   {
/*  390 */     int i = this._inputPtr - this._currInputRowStart + 1;
/*  391 */     return new JsonLocation(this._ioContext.getSourceReference(), this._currInputProcessed + this._inputPtr - 1L, this._currInputRow, i);
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
/*      */   public boolean hasTextCharacters()
/*      */   {
/*  405 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  406 */       return true;
/*      */     }
/*  408 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  409 */       return this._nameCopied;
/*      */     }
/*  411 */     return false;
/*      */   }
/*      */   
/*      */   public Object getEmbeddedObject()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  417 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  426 */   public long getTokenCharacterOffset() { return this._tokenInputTotal; }
/*  427 */   public int getTokenLineNr() { return this._tokenInputRow; }
/*      */   
/*      */   public int getTokenColumnNr() {
/*  430 */     int i = this._tokenInputCol;
/*  431 */     return i < 0 ? i : i + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void loadMoreGuaranteed()
/*      */     throws IOException
/*      */   {
/*  443 */     if (!loadMore()) {
/*  444 */       _reportInvalidEOF();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract boolean loadMore()
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract void _finishString()
/*      */     throws IOException, JsonParseException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract void _closeInput()
/*      */     throws IOException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _releaseBuffers()
/*      */     throws IOException
/*      */   {
/*  474 */     this._textBuffer.releaseBuffers();
/*  475 */     char[] arrayOfChar = this._nameCopyBuffer;
/*  476 */     if (arrayOfChar != null) {
/*  477 */       this._nameCopyBuffer = null;
/*  478 */       this._ioContext.releaseNameCopyBuffer(arrayOfChar);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _handleEOF()
/*      */     throws JsonParseException
/*      */   {
/*  490 */     if (!this._parsingContext.inRoot()) {
/*  491 */       _reportInvalidEOF(": expected close marker for " + this._parsingContext.getTypeDesc() + " (from " + this._parsingContext.getStartLocation(this._ioContext.getSourceReference()) + ")");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _reportMismatchedEndMarker(int paramInt, char paramChar)
/*      */     throws JsonParseException
/*      */   {
/*  504 */     String str = "" + this._parsingContext.getStartLocation(this._ioContext.getSourceReference());
/*  505 */     _reportError("Unexpected close marker '" + (char)paramInt + "': expected '" + paramChar + "' (for " + this._parsingContext.getTypeDesc() + " starting at " + str + ")");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteArrayBuilder _getByteArrayBuilder()
/*      */   {
/*  516 */     if (this._byteArrayBuilder == null) {
/*  517 */       this._byteArrayBuilder = new ByteArrayBuilder();
/*      */     } else {
/*  519 */       this._byteArrayBuilder.reset();
/*      */     }
/*  521 */     return this._byteArrayBuilder;
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
/*      */   protected final JsonToken reset(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  534 */     if ((paramInt2 < 1) && (paramInt3 < 1)) {
/*  535 */       return resetInt(paramBoolean, paramInt1);
/*      */     }
/*  537 */     return resetFloat(paramBoolean, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */   
/*      */   protected final JsonToken resetInt(boolean paramBoolean, int paramInt)
/*      */   {
/*  542 */     this._numberNegative = paramBoolean;
/*  543 */     this._intLength = paramInt;
/*  544 */     this._fractLength = 0;
/*  545 */     this._expLength = 0;
/*  546 */     this._numTypesValid = 0;
/*  547 */     return JsonToken.VALUE_NUMBER_INT;
/*      */   }
/*      */   
/*      */   protected final JsonToken resetFloat(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  552 */     this._numberNegative = paramBoolean;
/*  553 */     this._intLength = paramInt1;
/*  554 */     this._fractLength = paramInt2;
/*  555 */     this._expLength = paramInt3;
/*  556 */     this._numTypesValid = 0;
/*  557 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*      */   }
/*      */   
/*      */   protected final JsonToken resetAsNaN(String paramString, double paramDouble)
/*      */   {
/*  562 */     this._textBuffer.resetWithString(paramString);
/*  563 */     this._numberDouble = paramDouble;
/*  564 */     this._numTypesValid = 8;
/*  565 */     return JsonToken.VALUE_NUMBER_FLOAT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Number getNumberValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  577 */     if (this._numTypesValid == 0) {
/*  578 */       _parseNumericValue(0);
/*      */     }
/*      */     
/*  581 */     if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/*  582 */       if ((this._numTypesValid & 0x1) != 0) {
/*  583 */         return Integer.valueOf(this._numberInt);
/*      */       }
/*  585 */       if ((this._numTypesValid & 0x2) != 0) {
/*  586 */         return Long.valueOf(this._numberLong);
/*      */       }
/*  588 */       if ((this._numTypesValid & 0x4) != 0) {
/*  589 */         return this._numberBigInt;
/*      */       }
/*      */       
/*  592 */       return this._numberBigDecimal;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  598 */     if ((this._numTypesValid & 0x10) != 0) {
/*  599 */       return this._numberBigDecimal;
/*      */     }
/*  601 */     if ((this._numTypesValid & 0x8) == 0) {
/*  602 */       _throwInternal();
/*      */     }
/*  604 */     return Double.valueOf(this._numberDouble);
/*      */   }
/*      */   
/*      */   public JsonParser.NumberType getNumberType()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  610 */     if (this._numTypesValid == 0) {
/*  611 */       _parseNumericValue(0);
/*      */     }
/*  613 */     if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/*  614 */       if ((this._numTypesValid & 0x1) != 0) {
/*  615 */         return JsonParser.NumberType.INT;
/*      */       }
/*  617 */       if ((this._numTypesValid & 0x2) != 0) {
/*  618 */         return JsonParser.NumberType.LONG;
/*      */       }
/*  620 */       return JsonParser.NumberType.BIG_INTEGER;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  629 */     if ((this._numTypesValid & 0x10) != 0) {
/*  630 */       return JsonParser.NumberType.BIG_DECIMAL;
/*      */     }
/*  632 */     return JsonParser.NumberType.DOUBLE;
/*      */   }
/*      */   
/*      */   public int getIntValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  638 */     if ((this._numTypesValid & 0x1) == 0) {
/*  639 */       if (this._numTypesValid == 0) {
/*  640 */         _parseNumericValue(1);
/*      */       }
/*  642 */       if ((this._numTypesValid & 0x1) == 0) {
/*  643 */         convertNumberToInt();
/*      */       }
/*      */     }
/*  646 */     return this._numberInt;
/*      */   }
/*      */   
/*      */   public long getLongValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  652 */     if ((this._numTypesValid & 0x2) == 0) {
/*  653 */       if (this._numTypesValid == 0) {
/*  654 */         _parseNumericValue(2);
/*      */       }
/*  656 */       if ((this._numTypesValid & 0x2) == 0) {
/*  657 */         convertNumberToLong();
/*      */       }
/*      */     }
/*  660 */     return this._numberLong;
/*      */   }
/*      */   
/*      */   public BigInteger getBigIntegerValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  666 */     if ((this._numTypesValid & 0x4) == 0) {
/*  667 */       if (this._numTypesValid == 0) {
/*  668 */         _parseNumericValue(4);
/*      */       }
/*  670 */       if ((this._numTypesValid & 0x4) == 0) {
/*  671 */         convertNumberToBigInteger();
/*      */       }
/*      */     }
/*  674 */     return this._numberBigInt;
/*      */   }
/*      */   
/*      */   public float getFloatValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  680 */     double d = getDoubleValue();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  689 */     return (float)d;
/*      */   }
/*      */   
/*      */   public double getDoubleValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  695 */     if ((this._numTypesValid & 0x8) == 0) {
/*  696 */       if (this._numTypesValid == 0) {
/*  697 */         _parseNumericValue(8);
/*      */       }
/*  699 */       if ((this._numTypesValid & 0x8) == 0) {
/*  700 */         convertNumberToDouble();
/*      */       }
/*      */     }
/*  703 */     return this._numberDouble;
/*      */   }
/*      */   
/*      */   public BigDecimal getDecimalValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  709 */     if ((this._numTypesValid & 0x10) == 0) {
/*  710 */       if (this._numTypesValid == 0) {
/*  711 */         _parseNumericValue(16);
/*      */       }
/*  713 */       if ((this._numTypesValid & 0x10) == 0) {
/*  714 */         convertNumberToBigDecimal();
/*      */       }
/*      */     }
/*  717 */     return this._numberBigDecimal;
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
/*      */   protected void _parseNumericValue(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  739 */     if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/*  740 */       char[] arrayOfChar = this._textBuffer.getTextBuffer();
/*  741 */       int i = this._textBuffer.getTextOffset();
/*  742 */       int j = this._intLength;
/*  743 */       if (this._numberNegative) {
/*  744 */         i++;
/*      */       }
/*  746 */       if (j <= 9) {
/*  747 */         int k = NumberInput.parseInt(arrayOfChar, i, j);
/*  748 */         this._numberInt = (this._numberNegative ? -k : k);
/*  749 */         this._numTypesValid = 1;
/*  750 */         return;
/*      */       }
/*  752 */       if (j <= 18) {
/*  753 */         long l = NumberInput.parseLong(arrayOfChar, i, j);
/*  754 */         if (this._numberNegative) {
/*  755 */           l = -l;
/*      */         }
/*      */         
/*  758 */         if (j == 10) {
/*  759 */           if (this._numberNegative) {
/*  760 */             if (l >= -2147483648L) {
/*  761 */               this._numberInt = ((int)l);
/*  762 */               this._numTypesValid = 1;
/*      */             }
/*      */             
/*      */           }
/*  766 */           else if (l <= 2147483647L) {
/*  767 */             this._numberInt = ((int)l);
/*  768 */             this._numTypesValid = 1;
/*  769 */             return;
/*      */           }
/*      */         }
/*      */         
/*  773 */         this._numberLong = l;
/*  774 */         this._numTypesValid = 2;
/*  775 */         return;
/*      */       }
/*  777 */       _parseSlowIntValue(paramInt, arrayOfChar, i, j);
/*  778 */       return;
/*      */     }
/*  780 */     if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
/*  781 */       _parseSlowFloatValue(paramInt);
/*  782 */       return;
/*      */     }
/*  784 */     _reportError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _parseSlowFloatValue(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/*      */     try
/*      */     {
/*  798 */       if (paramInt == 16) {
/*  799 */         this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
/*  800 */         this._numTypesValid = 16;
/*      */       }
/*      */       else {
/*  803 */         this._numberDouble = this._textBuffer.contentsAsDouble();
/*  804 */         this._numTypesValid = 8;
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException) {
/*  808 */       _wrapError("Malformed numeric value '" + this._textBuffer.contentsAsString() + "'", localNumberFormatException);
/*      */     }
/*      */   }
/*      */   
/*      */   private void _parseSlowIntValue(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  815 */     String str = this._textBuffer.contentsAsString();
/*      */     try
/*      */     {
/*  818 */       if (NumberInput.inLongRange(paramArrayOfChar, paramInt2, paramInt3, this._numberNegative))
/*      */       {
/*  820 */         this._numberLong = Long.parseLong(str);
/*  821 */         this._numTypesValid = 2;
/*      */       }
/*      */       else {
/*  824 */         this._numberBigInt = new BigInteger(str);
/*  825 */         this._numTypesValid = 4;
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException) {
/*  829 */       _wrapError("Malformed numeric value '" + str + "'", localNumberFormatException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void convertNumberToInt()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  843 */     if ((this._numTypesValid & 0x2) != 0)
/*      */     {
/*  845 */       int i = (int)this._numberLong;
/*  846 */       if (i != this._numberLong) {
/*  847 */         _reportError("Numeric value (" + getText() + ") out of range of int");
/*      */       }
/*  849 */       this._numberInt = i;
/*  850 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  851 */       if ((BI_MIN_INT.compareTo(this._numberBigInt) > 0) || (BI_MAX_INT.compareTo(this._numberBigInt) < 0))
/*      */       {
/*  853 */         reportOverflowInt();
/*      */       }
/*  855 */       this._numberInt = this._numberBigInt.intValue();
/*  856 */     } else if ((this._numTypesValid & 0x8) != 0)
/*      */     {
/*  858 */       if ((this._numberDouble < -2.147483648E9D) || (this._numberDouble > 2.147483647E9D)) {
/*  859 */         reportOverflowInt();
/*      */       }
/*  861 */       this._numberInt = ((int)this._numberDouble);
/*  862 */     } else if ((this._numTypesValid & 0x10) != 0) {
/*  863 */       if ((BD_MIN_INT.compareTo(this._numberBigDecimal) > 0) || (BD_MAX_INT.compareTo(this._numberBigDecimal) < 0))
/*      */       {
/*  865 */         reportOverflowInt();
/*      */       }
/*  867 */       this._numberInt = this._numberBigDecimal.intValue();
/*      */     } else {
/*  869 */       _throwInternal();
/*      */     }
/*  871 */     this._numTypesValid |= 0x1;
/*      */   }
/*      */   
/*      */   protected void convertNumberToLong()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  877 */     if ((this._numTypesValid & 0x1) != 0) {
/*  878 */       this._numberLong = this._numberInt;
/*  879 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  880 */       if ((BI_MIN_LONG.compareTo(this._numberBigInt) > 0) || (BI_MAX_LONG.compareTo(this._numberBigInt) < 0))
/*      */       {
/*  882 */         reportOverflowLong();
/*      */       }
/*  884 */       this._numberLong = this._numberBigInt.longValue();
/*  885 */     } else if ((this._numTypesValid & 0x8) != 0)
/*      */     {
/*  887 */       if ((this._numberDouble < -9.223372036854776E18D) || (this._numberDouble > 9.223372036854776E18D)) {
/*  888 */         reportOverflowLong();
/*      */       }
/*  890 */       this._numberLong = (this._numberDouble);
/*  891 */     } else if ((this._numTypesValid & 0x10) != 0) {
/*  892 */       if ((BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0) || (BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0))
/*      */       {
/*  894 */         reportOverflowLong();
/*      */       }
/*  896 */       this._numberLong = this._numberBigDecimal.longValue();
/*      */     } else {
/*  898 */       _throwInternal();
/*      */     }
/*  900 */     this._numTypesValid |= 0x2;
/*      */   }
/*      */   
/*      */   protected void convertNumberToBigInteger()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  906 */     if ((this._numTypesValid & 0x10) != 0)
/*      */     {
/*  908 */       this._numberBigInt = this._numberBigDecimal.toBigInteger();
/*  909 */     } else if ((this._numTypesValid & 0x2) != 0) {
/*  910 */       this._numberBigInt = BigInteger.valueOf(this._numberLong);
/*  911 */     } else if ((this._numTypesValid & 0x1) != 0) {
/*  912 */       this._numberBigInt = BigInteger.valueOf(this._numberInt);
/*  913 */     } else if ((this._numTypesValid & 0x8) != 0) {
/*  914 */       this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
/*      */     } else {
/*  916 */       _throwInternal();
/*      */     }
/*  918 */     this._numTypesValid |= 0x4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void convertNumberToDouble()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  930 */     if ((this._numTypesValid & 0x10) != 0) {
/*  931 */       this._numberDouble = this._numberBigDecimal.doubleValue();
/*  932 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  933 */       this._numberDouble = this._numberBigInt.doubleValue();
/*  934 */     } else if ((this._numTypesValid & 0x2) != 0) {
/*  935 */       this._numberDouble = this._numberLong;
/*  936 */     } else if ((this._numTypesValid & 0x1) != 0) {
/*  937 */       this._numberDouble = this._numberInt;
/*      */     } else {
/*  939 */       _throwInternal();
/*      */     }
/*  941 */     this._numTypesValid |= 0x8;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void convertNumberToBigDecimal()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  953 */     if ((this._numTypesValid & 0x8) != 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  958 */       this._numberBigDecimal = new BigDecimal(getText());
/*  959 */     } else if ((this._numTypesValid & 0x4) != 0) {
/*  960 */       this._numberBigDecimal = new BigDecimal(this._numberBigInt);
/*  961 */     } else if ((this._numTypesValid & 0x2) != 0) {
/*  962 */       this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
/*  963 */     } else if ((this._numTypesValid & 0x1) != 0) {
/*  964 */       this._numberBigDecimal = BigDecimal.valueOf(this._numberInt);
/*      */     } else {
/*  966 */       _throwInternal();
/*      */     }
/*  968 */     this._numTypesValid |= 0x10;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void reportUnexpectedNumberChar(int paramInt, String paramString)
/*      */     throws JsonParseException
/*      */   {
/*  980 */     String str = "Unexpected character (" + _getCharDesc(paramInt) + ") in numeric value";
/*  981 */     if (paramString != null) {
/*  982 */       str = str + ": " + paramString;
/*      */     }
/*  984 */     _reportError(str);
/*      */   }
/*      */   
/*      */   protected void reportInvalidNumber(String paramString)
/*      */     throws JsonParseException
/*      */   {
/*  990 */     _reportError("Invalid numeric value: " + paramString);
/*      */   }
/*      */   
/*      */   protected void reportOverflowInt()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  996 */     _reportError("Numeric value (" + getText() + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
/*      */   }
/*      */   
/*      */   protected void reportOverflowLong()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1002 */     _reportError("Numeric value (" + getText() + ") out of range of long (" + Long.MIN_VALUE + " - " + Long.MAX_VALUE + ")");
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
/*      */   protected char _decodeEscaped()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1018 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */   protected final int _decodeBase64Escape(Base64Variant paramBase64Variant, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1025 */     if (paramInt1 != 92) {
/* 1026 */       throw reportInvalidBase64Char(paramBase64Variant, paramInt1, paramInt2);
/*      */     }
/* 1028 */     int i = _decodeEscaped();
/*      */     
/* 1030 */     if ((i <= 32) && 
/* 1031 */       (paramInt2 == 0)) {
/* 1032 */       return -1;
/*      */     }
/*      */     
/*      */ 
/* 1036 */     int j = paramBase64Variant.decodeBase64Char(i);
/* 1037 */     if (j < 0) {
/* 1038 */       throw reportInvalidBase64Char(paramBase64Variant, i, paramInt2);
/*      */     }
/* 1040 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */   protected final int _decodeBase64Escape(Base64Variant paramBase64Variant, char paramChar, int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1047 */     if (paramChar != '\\') {
/* 1048 */       throw reportInvalidBase64Char(paramBase64Variant, paramChar, paramInt);
/*      */     }
/* 1050 */     char c = _decodeEscaped();
/*      */     
/* 1052 */     if ((c <= ' ') && 
/* 1053 */       (paramInt == 0)) {
/* 1054 */       return -1;
/*      */     }
/*      */     
/*      */ 
/* 1058 */     int i = paramBase64Variant.decodeBase64Char(c);
/* 1059 */     if (i < 0) {
/* 1060 */       throw reportInvalidBase64Char(paramBase64Variant, c, paramInt);
/*      */     }
/* 1062 */     return i;
/*      */   }
/*      */   
/*      */   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant paramBase64Variant, int paramInt1, int paramInt2)
/*      */     throws IllegalArgumentException
/*      */   {
/* 1068 */     return reportInvalidBase64Char(paramBase64Variant, paramInt1, paramInt2, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant paramBase64Variant, int paramInt1, int paramInt2, String paramString)
/*      */     throws IllegalArgumentException
/*      */   {
/*      */     String str;
/*      */     
/*      */ 
/* 1079 */     if (paramInt1 <= 32) {
/* 1080 */       str = "Illegal white space character (code 0x" + Integer.toHexString(paramInt1) + ") as character #" + (paramInt2 + 1) + " of 4-char base64 unit: can only used between units";
/* 1081 */     } else if (paramBase64Variant.usesPaddingChar(paramInt1)) {
/* 1082 */       str = "Unexpected padding character ('" + paramBase64Variant.getPaddingChar() + "') as character #" + (paramInt2 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
/* 1083 */     } else if ((!Character.isDefined(paramInt1)) || (Character.isISOControl(paramInt1)))
/*      */     {
/* 1085 */       str = "Illegal character (code 0x" + Integer.toHexString(paramInt1) + ") in base64 content";
/*      */     } else {
/* 1087 */       str = "Illegal character '" + (char)paramInt1 + "' (code 0x" + Integer.toHexString(paramInt1) + ") in base64 content";
/*      */     }
/* 1089 */     if (paramString != null) {
/* 1090 */       str = str + ": " + paramString;
/*      */     }
/* 1092 */     return new IllegalArgumentException(str);
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/base/ParserBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */