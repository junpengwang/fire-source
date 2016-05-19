/*      */ package com.shaded.fasterxml.jackson.core.json;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*      */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*      */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*      */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*      */ import com.shaded.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
/*      */ import com.shaded.fasterxml.jackson.core.sym.Name;
/*      */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import com.shaded.fasterxml.jackson.core.util.TextBuffer;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ 
/*      */ public final class UTF8StreamJsonParser extends com.shaded.fasterxml.jackson.core.base.ParserBase
/*      */ {
/*      */   static final byte BYTE_LF = 10;
/*   21 */   private static final int[] sInputCodesUtf8 = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   27 */   private static final int[] sInputCodesLatin1 = com.shaded.fasterxml.jackson.core.io.CharTypes.getInputCodeLatin1();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectCodec _objectCodec;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final BytesToNameCanonicalizer _symbols;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   56 */   protected int[] _quadBuffer = new int[16];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   63 */   protected boolean _tokenIncomplete = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int _quad1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected InputStream _inputStream;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected byte[] _inputBuffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _bufferRecyclable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public UTF8StreamJsonParser(IOContext paramIOContext, int paramInt1, InputStream paramInputStream, ObjectCodec paramObjectCodec, BytesToNameCanonicalizer paramBytesToNameCanonicalizer, byte[] paramArrayOfByte, int paramInt2, int paramInt3, boolean paramBoolean)
/*      */   {
/*  111 */     super(paramIOContext, paramInt1);
/*  112 */     this._inputStream = paramInputStream;
/*  113 */     this._objectCodec = paramObjectCodec;
/*  114 */     this._symbols = paramBytesToNameCanonicalizer;
/*  115 */     this._inputBuffer = paramArrayOfByte;
/*  116 */     this._inputPtr = paramInt2;
/*  117 */     this._inputEnd = paramInt3;
/*  118 */     this._bufferRecyclable = paramBoolean;
/*      */   }
/*      */   
/*      */   public ObjectCodec getCodec()
/*      */   {
/*  123 */     return this._objectCodec;
/*      */   }
/*      */   
/*      */   public void setCodec(ObjectCodec paramObjectCodec)
/*      */   {
/*  128 */     this._objectCodec = paramObjectCodec;
/*      */   }
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
/*  140 */     int i = this._inputEnd - this._inputPtr;
/*  141 */     if (i < 1) {
/*  142 */       return 0;
/*      */     }
/*      */     
/*  145 */     int j = this._inputPtr;
/*  146 */     paramOutputStream.write(this._inputBuffer, j, i);
/*  147 */     return i;
/*      */   }
/*      */   
/*      */   public Object getInputSource()
/*      */   {
/*  152 */     return this._inputStream;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean loadMore()
/*      */     throws IOException
/*      */   {
/*  165 */     this._currInputProcessed += this._inputEnd;
/*  166 */     this._currInputRowStart -= this._inputEnd;
/*      */     
/*  168 */     if (this._inputStream != null) {
/*  169 */       int i = this._inputStream.read(this._inputBuffer, 0, this._inputBuffer.length);
/*  170 */       if (i > 0) {
/*  171 */         this._inputPtr = 0;
/*  172 */         this._inputEnd = i;
/*  173 */         return true;
/*      */       }
/*      */       
/*  176 */       _closeInput();
/*      */       
/*  178 */       if (i == 0) {
/*  179 */         throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
/*      */       }
/*      */     }
/*  182 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _loadToHaveAtLeast(int paramInt)
/*      */     throws IOException
/*      */   {
/*  193 */     if (this._inputStream == null) {
/*  194 */       return false;
/*      */     }
/*      */     
/*  197 */     int i = this._inputEnd - this._inputPtr;
/*  198 */     if ((i > 0) && (this._inputPtr > 0)) {
/*  199 */       this._currInputProcessed += this._inputPtr;
/*  200 */       this._currInputRowStart -= this._inputPtr;
/*  201 */       System.arraycopy(this._inputBuffer, this._inputPtr, this._inputBuffer, 0, i);
/*  202 */       this._inputEnd = i;
/*      */     } else {
/*  204 */       this._inputEnd = 0;
/*      */     }
/*  206 */     this._inputPtr = 0;
/*  207 */     while (this._inputEnd < paramInt) {
/*  208 */       int j = this._inputStream.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
/*  209 */       if (j < 1)
/*      */       {
/*  211 */         _closeInput();
/*      */         
/*  213 */         if (j == 0) {
/*  214 */           throw new IOException("InputStream.read() returned 0 characters when trying to read " + i + " bytes");
/*      */         }
/*  216 */         return false;
/*      */       }
/*  218 */       this._inputEnd += j;
/*      */     }
/*  220 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _closeInput()
/*      */     throws IOException
/*      */   {
/*  230 */     if (this._inputStream != null) {
/*  231 */       if ((this._ioContext.isResourceManaged()) || (isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE))) {
/*  232 */         this._inputStream.close();
/*      */       }
/*  234 */       this._inputStream = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _releaseBuffers()
/*      */     throws IOException
/*      */   {
/*  247 */     super._releaseBuffers();
/*  248 */     if (this._bufferRecyclable) {
/*  249 */       byte[] arrayOfByte = this._inputBuffer;
/*  250 */       if (arrayOfByte != null) {
/*  251 */         this._inputBuffer = null;
/*  252 */         this._ioContext.releaseReadIOBuffer(arrayOfByte);
/*      */       }
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
/*      */   public String getText()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  267 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  268 */       if (this._tokenIncomplete) {
/*  269 */         this._tokenIncomplete = false;
/*  270 */         _finishString();
/*      */       }
/*  272 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  274 */     return _getText2(this._currToken);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getValueAsString()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  283 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  284 */       if (this._tokenIncomplete) {
/*  285 */         this._tokenIncomplete = false;
/*  286 */         _finishString();
/*      */       }
/*  288 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  290 */     return super.getValueAsString(null);
/*      */   }
/*      */   
/*      */ 
/*      */   public String getValueAsString(String paramString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  297 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  298 */       if (this._tokenIncomplete) {
/*  299 */         this._tokenIncomplete = false;
/*  300 */         _finishString();
/*      */       }
/*  302 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  304 */     return super.getValueAsString(paramString);
/*      */   }
/*      */   
/*      */   protected String _getText2(JsonToken paramJsonToken)
/*      */   {
/*  309 */     if (paramJsonToken == null) {
/*  310 */       return null;
/*      */     }
/*  312 */     switch (paramJsonToken) {
/*      */     case FIELD_NAME: 
/*  314 */       return this._parsingContext.getCurrentName();
/*      */     
/*      */ 
/*      */     case VALUE_STRING: 
/*      */     case VALUE_NUMBER_INT: 
/*      */     case VALUE_NUMBER_FLOAT: 
/*  320 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  322 */     return paramJsonToken.asString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public char[] getTextCharacters()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  330 */     if (this._currToken != null) {
/*  331 */       switch (this._currToken)
/*      */       {
/*      */       case FIELD_NAME: 
/*  334 */         if (!this._nameCopied) {
/*  335 */           String str = this._parsingContext.getCurrentName();
/*  336 */           int i = str.length();
/*  337 */           if (this._nameCopyBuffer == null) {
/*  338 */             this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(i);
/*  339 */           } else if (this._nameCopyBuffer.length < i) {
/*  340 */             this._nameCopyBuffer = new char[i];
/*      */           }
/*  342 */           str.getChars(0, i, this._nameCopyBuffer, 0);
/*  343 */           this._nameCopied = true;
/*      */         }
/*  345 */         return this._nameCopyBuffer;
/*      */       
/*      */       case VALUE_STRING: 
/*  348 */         if (this._tokenIncomplete) {
/*  349 */           this._tokenIncomplete = false;
/*  350 */           _finishString();
/*      */         }
/*      */       
/*      */       case VALUE_NUMBER_INT: 
/*      */       case VALUE_NUMBER_FLOAT: 
/*  355 */         return this._textBuffer.getTextBuffer();
/*      */       }
/*      */       
/*  358 */       return this._currToken.asCharArray();
/*      */     }
/*      */     
/*  361 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getTextLength()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  368 */     if (this._currToken != null) {
/*  369 */       switch (this._currToken)
/*      */       {
/*      */       case FIELD_NAME: 
/*  372 */         return this._parsingContext.getCurrentName().length();
/*      */       case VALUE_STRING: 
/*  374 */         if (this._tokenIncomplete) {
/*  375 */           this._tokenIncomplete = false;
/*  376 */           _finishString();
/*      */         }
/*      */       
/*      */       case VALUE_NUMBER_INT: 
/*      */       case VALUE_NUMBER_FLOAT: 
/*  381 */         return this._textBuffer.size();
/*      */       }
/*      */       
/*  384 */       return this._currToken.asCharArray().length;
/*      */     }
/*      */     
/*  387 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getTextOffset()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  394 */     if (this._currToken != null) {
/*  395 */       switch (this._currToken) {
/*      */       case FIELD_NAME: 
/*  397 */         return 0;
/*      */       case VALUE_STRING: 
/*  399 */         if (this._tokenIncomplete) {
/*  400 */           this._tokenIncomplete = false;
/*  401 */           _finishString();
/*      */         }
/*      */       
/*      */       case VALUE_NUMBER_INT: 
/*      */       case VALUE_NUMBER_FLOAT: 
/*  406 */         return this._textBuffer.getTextOffset();
/*      */       }
/*      */       
/*      */     }
/*  410 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public byte[] getBinaryValue(Base64Variant paramBase64Variant)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  417 */     if ((this._currToken != JsonToken.VALUE_STRING) && ((this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT) || (this._binaryValue == null)))
/*      */     {
/*  419 */       _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  424 */     if (this._tokenIncomplete) {
/*      */       try {
/*  426 */         this._binaryValue = _decodeBase64(paramBase64Variant);
/*      */       } catch (IllegalArgumentException localIllegalArgumentException) {
/*  428 */         throw _constructError("Failed to decode VALUE_STRING as base64 (" + paramBase64Variant + "): " + localIllegalArgumentException.getMessage());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  433 */       this._tokenIncomplete = false;
/*      */     }
/*  435 */     else if (this._binaryValue == null) {
/*  436 */       ByteArrayBuilder localByteArrayBuilder = _getByteArrayBuilder();
/*  437 */       _decodeBase64(getText(), localByteArrayBuilder, paramBase64Variant);
/*  438 */       this._binaryValue = localByteArrayBuilder.toByteArray();
/*      */     }
/*      */     
/*  441 */     return this._binaryValue;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int readBinaryValue(Base64Variant paramBase64Variant, OutputStream paramOutputStream)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  449 */     if ((!this._tokenIncomplete) || (this._currToken != JsonToken.VALUE_STRING)) {
/*  450 */       arrayOfByte = getBinaryValue(paramBase64Variant);
/*  451 */       paramOutputStream.write(arrayOfByte);
/*  452 */       return arrayOfByte.length;
/*      */     }
/*      */     
/*  455 */     byte[] arrayOfByte = this._ioContext.allocBase64Buffer();
/*      */     try {
/*  457 */       return _readBinary(paramBase64Variant, paramOutputStream, arrayOfByte);
/*      */     } finally {
/*  459 */       this._ioContext.releaseBase64Buffer(arrayOfByte);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected int _readBinary(Base64Variant paramBase64Variant, OutputStream paramOutputStream, byte[] paramArrayOfByte)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  467 */     int i = 0;
/*  468 */     int j = paramArrayOfByte.length - 3;
/*  469 */     int k = 0;
/*      */     
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/*  475 */       if (this._inputPtr >= this._inputEnd) {
/*  476 */         loadMoreGuaranteed();
/*      */       }
/*  478 */       int m = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*  479 */       if (m > 32) {
/*  480 */         int n = paramBase64Variant.decodeBase64Char(m);
/*  481 */         if (n < 0) {
/*  482 */           if (m == 34) {
/*      */             break;
/*      */           }
/*  485 */           n = _decodeBase64Escape(paramBase64Variant, m, 0);
/*  486 */           if (n < 0) {}
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  492 */           if (i > j) {
/*  493 */             k += i;
/*  494 */             paramOutputStream.write(paramArrayOfByte, 0, i);
/*  495 */             i = 0;
/*      */           }
/*      */           
/*  498 */           int i1 = n;
/*      */           
/*      */ 
/*      */ 
/*  502 */           if (this._inputPtr >= this._inputEnd) {
/*  503 */             loadMoreGuaranteed();
/*      */           }
/*  505 */           m = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*  506 */           n = paramBase64Variant.decodeBase64Char(m);
/*  507 */           if (n < 0) {
/*  508 */             n = _decodeBase64Escape(paramBase64Variant, m, 1);
/*      */           }
/*  510 */           i1 = i1 << 6 | n;
/*      */           
/*      */ 
/*  513 */           if (this._inputPtr >= this._inputEnd) {
/*  514 */             loadMoreGuaranteed();
/*      */           }
/*  516 */           m = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*  517 */           n = paramBase64Variant.decodeBase64Char(m);
/*      */           
/*      */ 
/*  520 */           if (n < 0) {
/*  521 */             if (n != -2)
/*      */             {
/*  523 */               if ((m == 34) && (!paramBase64Variant.usesPadding())) {
/*  524 */                 i1 >>= 4;
/*  525 */                 paramArrayOfByte[(i++)] = ((byte)i1);
/*  526 */                 break;
/*      */               }
/*  528 */               n = _decodeBase64Escape(paramBase64Variant, m, 2);
/*      */             }
/*  530 */             if (n == -2)
/*      */             {
/*  532 */               if (this._inputPtr >= this._inputEnd) {
/*  533 */                 loadMoreGuaranteed();
/*      */               }
/*  535 */               m = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*  536 */               if (!paramBase64Variant.usesPaddingChar(m)) {
/*  537 */                 throw reportInvalidBase64Char(paramBase64Variant, m, 3, "expected padding character '" + paramBase64Variant.getPaddingChar() + "'");
/*      */               }
/*      */               
/*  540 */               i1 >>= 4;
/*  541 */               paramArrayOfByte[(i++)] = ((byte)i1);
/*  542 */               continue;
/*      */             }
/*      */           }
/*      */           
/*  546 */           i1 = i1 << 6 | n;
/*      */           
/*  548 */           if (this._inputPtr >= this._inputEnd) {
/*  549 */             loadMoreGuaranteed();
/*      */           }
/*  551 */           m = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*  552 */           n = paramBase64Variant.decodeBase64Char(m);
/*  553 */           if (n < 0) {
/*  554 */             if (n != -2)
/*      */             {
/*  556 */               if ((m == 34) && (!paramBase64Variant.usesPadding())) {
/*  557 */                 i1 >>= 2;
/*  558 */                 paramArrayOfByte[(i++)] = ((byte)(i1 >> 8));
/*  559 */                 paramArrayOfByte[(i++)] = ((byte)i1);
/*  560 */                 break;
/*      */               }
/*  562 */               n = _decodeBase64Escape(paramBase64Variant, m, 3);
/*      */             }
/*  564 */             if (n == -2)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  571 */               i1 >>= 2;
/*  572 */               paramArrayOfByte[(i++)] = ((byte)(i1 >> 8));
/*  573 */               paramArrayOfByte[(i++)] = ((byte)i1);
/*  574 */               continue;
/*      */             }
/*      */           }
/*      */           
/*  578 */           i1 = i1 << 6 | n;
/*  579 */           paramArrayOfByte[(i++)] = ((byte)(i1 >> 16));
/*  580 */           paramArrayOfByte[(i++)] = ((byte)(i1 >> 8));
/*  581 */           paramArrayOfByte[(i++)] = ((byte)i1);
/*      */         } } }
/*  583 */     this._tokenIncomplete = false;
/*  584 */     if (i > 0) {
/*  585 */       k += i;
/*  586 */       paramOutputStream.write(paramArrayOfByte, 0, i);
/*      */     }
/*  588 */     return k;
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
/*      */   public JsonToken nextToken()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  605 */     this._numTypesValid = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  610 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  611 */       return _nextAfterName();
/*      */     }
/*  613 */     if (this._tokenIncomplete) {
/*  614 */       _skipString();
/*      */     }
/*      */     
/*  617 */     int i = _skipWSOrEnd();
/*  618 */     if (i < 0)
/*      */     {
/*      */ 
/*      */ 
/*  622 */       close();
/*  623 */       return this._currToken = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  629 */     this._tokenInputTotal = (this._currInputProcessed + this._inputPtr - 1L);
/*  630 */     this._tokenInputRow = this._currInputRow;
/*  631 */     this._tokenInputCol = (this._inputPtr - this._currInputRowStart - 1);
/*      */     
/*      */ 
/*  634 */     this._binaryValue = null;
/*      */     
/*      */ 
/*  637 */     if (i == 93) {
/*  638 */       if (!this._parsingContext.inArray()) {
/*  639 */         _reportMismatchedEndMarker(i, '}');
/*      */       }
/*  641 */       this._parsingContext = this._parsingContext.getParent();
/*  642 */       return this._currToken = JsonToken.END_ARRAY;
/*      */     }
/*  644 */     if (i == 125) {
/*  645 */       if (!this._parsingContext.inObject()) {
/*  646 */         _reportMismatchedEndMarker(i, ']');
/*      */       }
/*  648 */       this._parsingContext = this._parsingContext.getParent();
/*  649 */       return this._currToken = JsonToken.END_OBJECT;
/*      */     }
/*      */     
/*      */ 
/*  653 */     if (this._parsingContext.expectComma()) {
/*  654 */       if (i != 44) {
/*  655 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
/*      */       }
/*  657 */       i = _skipWS();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  664 */     if (!this._parsingContext.inObject()) {
/*  665 */       return _nextTokenNotInObject(i);
/*      */     }
/*      */     
/*  668 */     Name localName = _parseFieldName(i);
/*  669 */     this._parsingContext.setCurrentName(localName.getName());
/*  670 */     this._currToken = JsonToken.FIELD_NAME;
/*  671 */     i = _skipWS();
/*  672 */     if (i != 58) {
/*  673 */       _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
/*      */     }
/*  675 */     i = _skipWS();
/*      */     
/*      */ 
/*  678 */     if (i == 34) {
/*  679 */       this._tokenIncomplete = true;
/*  680 */       this._nextToken = JsonToken.VALUE_STRING;
/*  681 */       return this._currToken;
/*      */     }
/*      */     
/*      */     JsonToken localJsonToken;
/*  685 */     switch (i) {
/*      */     case 91: 
/*  687 */       localJsonToken = JsonToken.START_ARRAY;
/*  688 */       break;
/*      */     case 123: 
/*  690 */       localJsonToken = JsonToken.START_OBJECT;
/*  691 */       break;
/*      */     
/*      */ 
/*      */     case 93: 
/*      */     case 125: 
/*  696 */       _reportUnexpectedChar(i, "expected a value");
/*      */     case 116: 
/*  698 */       _matchToken("true", 1);
/*  699 */       localJsonToken = JsonToken.VALUE_TRUE;
/*  700 */       break;
/*      */     case 102: 
/*  702 */       _matchToken("false", 1);
/*  703 */       localJsonToken = JsonToken.VALUE_FALSE;
/*  704 */       break;
/*      */     case 110: 
/*  706 */       _matchToken("null", 1);
/*  707 */       localJsonToken = JsonToken.VALUE_NULL;
/*  708 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 45: 
/*      */     case 48: 
/*      */     case 49: 
/*      */     case 50: 
/*      */     case 51: 
/*      */     case 52: 
/*      */     case 53: 
/*      */     case 54: 
/*      */     case 55: 
/*      */     case 56: 
/*      */     case 57: 
/*  725 */       localJsonToken = parseNumberText(i);
/*  726 */       break;
/*      */     default: 
/*  728 */       localJsonToken = _handleUnexpectedValue(i);
/*      */     }
/*  730 */     this._nextToken = localJsonToken;
/*  731 */     return this._currToken;
/*      */   }
/*      */   
/*      */   private JsonToken _nextTokenNotInObject(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  737 */     if (paramInt == 34) {
/*  738 */       this._tokenIncomplete = true;
/*  739 */       return this._currToken = JsonToken.VALUE_STRING;
/*      */     }
/*  741 */     switch (paramInt) {
/*      */     case 91: 
/*  743 */       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  744 */       return this._currToken = JsonToken.START_ARRAY;
/*      */     case 123: 
/*  746 */       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*  747 */       return this._currToken = JsonToken.START_OBJECT;
/*      */     
/*      */ 
/*      */     case 93: 
/*      */     case 125: 
/*  752 */       _reportUnexpectedChar(paramInt, "expected a value");
/*      */     case 116: 
/*  754 */       _matchToken("true", 1);
/*  755 */       return this._currToken = JsonToken.VALUE_TRUE;
/*      */     case 102: 
/*  757 */       _matchToken("false", 1);
/*  758 */       return this._currToken = JsonToken.VALUE_FALSE;
/*      */     case 110: 
/*  760 */       _matchToken("null", 1);
/*  761 */       return this._currToken = JsonToken.VALUE_NULL;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 45: 
/*      */     case 48: 
/*      */     case 49: 
/*      */     case 50: 
/*      */     case 51: 
/*      */     case 52: 
/*      */     case 53: 
/*      */     case 54: 
/*      */     case 55: 
/*      */     case 56: 
/*      */     case 57: 
/*  777 */       return this._currToken = parseNumberText(paramInt);
/*      */     }
/*  779 */     return this._currToken = _handleUnexpectedValue(paramInt);
/*      */   }
/*      */   
/*      */   private JsonToken _nextAfterName()
/*      */   {
/*  784 */     this._nameCopied = false;
/*  785 */     JsonToken localJsonToken = this._nextToken;
/*  786 */     this._nextToken = null;
/*      */     
/*  788 */     if (localJsonToken == JsonToken.START_ARRAY) {
/*  789 */       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  790 */     } else if (localJsonToken == JsonToken.START_OBJECT) {
/*  791 */       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */     }
/*  793 */     return this._currToken = localJsonToken;
/*      */   }
/*      */   
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  799 */     super.close();
/*      */     
/*  801 */     this._symbols.release();
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
/*      */   public boolean nextFieldName(SerializableString paramSerializableString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  816 */     this._numTypesValid = 0;
/*  817 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  818 */       _nextAfterName();
/*  819 */       return false;
/*      */     }
/*  821 */     if (this._tokenIncomplete) {
/*  822 */       _skipString();
/*      */     }
/*  824 */     int i = _skipWSOrEnd();
/*  825 */     if (i < 0) {
/*  826 */       close();
/*  827 */       this._currToken = null;
/*  828 */       return false;
/*      */     }
/*  830 */     this._tokenInputTotal = (this._currInputProcessed + this._inputPtr - 1L);
/*  831 */     this._tokenInputRow = this._currInputRow;
/*  832 */     this._tokenInputCol = (this._inputPtr - this._currInputRowStart - 1);
/*      */     
/*      */ 
/*  835 */     this._binaryValue = null;
/*      */     
/*      */ 
/*  838 */     if (i == 93) {
/*  839 */       if (!this._parsingContext.inArray()) {
/*  840 */         _reportMismatchedEndMarker(i, '}');
/*      */       }
/*  842 */       this._parsingContext = this._parsingContext.getParent();
/*  843 */       this._currToken = JsonToken.END_ARRAY;
/*  844 */       return false;
/*      */     }
/*  846 */     if (i == 125) {
/*  847 */       if (!this._parsingContext.inObject()) {
/*  848 */         _reportMismatchedEndMarker(i, ']');
/*      */       }
/*  850 */       this._parsingContext = this._parsingContext.getParent();
/*  851 */       this._currToken = JsonToken.END_OBJECT;
/*  852 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  856 */     if (this._parsingContext.expectComma()) {
/*  857 */       if (i != 44) {
/*  858 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
/*      */       }
/*  860 */       i = _skipWS();
/*      */     }
/*      */     
/*  863 */     if (!this._parsingContext.inObject()) {
/*  864 */       _nextTokenNotInObject(i);
/*  865 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  869 */     if (i == 34)
/*      */     {
/*  871 */       byte[] arrayOfByte = paramSerializableString.asQuotedUTF8();
/*  872 */       int j = arrayOfByte.length;
/*  873 */       if (this._inputPtr + j < this._inputEnd)
/*      */       {
/*  875 */         int k = this._inputPtr + j;
/*  876 */         if (this._inputBuffer[k] == 34) {
/*  877 */           int m = 0;
/*  878 */           int n = this._inputPtr;
/*      */           for (;;) {
/*  880 */             if (m == j) {
/*  881 */               this._inputPtr = (k + 1);
/*      */               
/*  883 */               this._parsingContext.setCurrentName(paramSerializableString.getValue());
/*  884 */               this._currToken = JsonToken.FIELD_NAME;
/*      */               
/*  886 */               _isNextTokenNameYes();
/*  887 */               return true;
/*      */             }
/*  889 */             if (arrayOfByte[m] != this._inputBuffer[(n + m)]) {
/*      */               break;
/*      */             }
/*  892 */             m++;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  897 */     return _isNextTokenNameMaybe(i, paramSerializableString);
/*      */   }
/*      */   
/*      */ 
/*      */   private void _isNextTokenNameYes()
/*      */     throws IOException, JsonParseException
/*      */   {
/*      */     int i;
/*  905 */     if ((this._inputPtr < this._inputEnd - 1) && (this._inputBuffer[this._inputPtr] == 58)) {
/*  906 */       i = this._inputBuffer[(++this._inputPtr)];
/*  907 */       this._inputPtr += 1;
/*  908 */       if (i == 34) {
/*  909 */         this._tokenIncomplete = true;
/*  910 */         this._nextToken = JsonToken.VALUE_STRING;
/*  911 */         return;
/*      */       }
/*  913 */       if (i == 123) {
/*  914 */         this._nextToken = JsonToken.START_OBJECT;
/*  915 */         return;
/*      */       }
/*  917 */       if (i == 91) {
/*  918 */         this._nextToken = JsonToken.START_ARRAY;
/*  919 */         return;
/*      */       }
/*  921 */       i &= 0xFF;
/*  922 */       if ((i <= 32) || (i == 47)) {
/*  923 */         this._inputPtr -= 1;
/*  924 */         i = _skipWS();
/*      */       }
/*      */     } else {
/*  927 */       i = _skipColon();
/*      */     }
/*  929 */     switch (i) {
/*      */     case 34: 
/*  931 */       this._tokenIncomplete = true;
/*  932 */       this._nextToken = JsonToken.VALUE_STRING;
/*  933 */       return;
/*      */     case 91: 
/*  935 */       this._nextToken = JsonToken.START_ARRAY;
/*  936 */       return;
/*      */     case 123: 
/*  938 */       this._nextToken = JsonToken.START_OBJECT;
/*  939 */       return;
/*      */     case 93: 
/*      */     case 125: 
/*  942 */       _reportUnexpectedChar(i, "expected a value");
/*      */     case 116: 
/*  944 */       _matchToken("true", 1);
/*  945 */       this._nextToken = JsonToken.VALUE_TRUE;
/*  946 */       return;
/*      */     case 102: 
/*  948 */       _matchToken("false", 1);
/*  949 */       this._nextToken = JsonToken.VALUE_FALSE;
/*  950 */       return;
/*      */     case 110: 
/*  952 */       _matchToken("null", 1);
/*  953 */       this._nextToken = JsonToken.VALUE_NULL;
/*  954 */       return;
/*      */     case 45: 
/*      */     case 48: 
/*      */     case 49: 
/*      */     case 50: 
/*      */     case 51: 
/*      */     case 52: 
/*      */     case 53: 
/*      */     case 54: 
/*      */     case 55: 
/*      */     case 56: 
/*      */     case 57: 
/*  966 */       this._nextToken = parseNumberText(i);
/*  967 */       return;
/*      */     }
/*  969 */     this._nextToken = _handleUnexpectedValue(i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean _isNextTokenNameMaybe(int paramInt, SerializableString paramSerializableString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  977 */     Name localName = _parseFieldName(paramInt);
/*      */     
/*      */ 
/*  980 */     Object localObject = localName.getName();
/*  981 */     this._parsingContext.setCurrentName((String)localObject);
/*  982 */     boolean bool = ((String)localObject).equals(paramSerializableString.getValue());
/*      */     
/*  984 */     this._currToken = JsonToken.FIELD_NAME;
/*  985 */     paramInt = _skipWS();
/*  986 */     if (paramInt != 58) {
/*  987 */       _reportUnexpectedChar(paramInt, "was expecting a colon to separate field name and value");
/*      */     }
/*  989 */     paramInt = _skipWS();
/*      */     
/*      */ 
/*  992 */     if (paramInt == 34) {
/*  993 */       this._tokenIncomplete = true;
/*  994 */       this._nextToken = JsonToken.VALUE_STRING;
/*  995 */       return bool;
/*      */     }
/*      */     
/*      */ 
/*  999 */     switch (paramInt) {
/*      */     case 91: 
/* 1001 */       localObject = JsonToken.START_ARRAY;
/* 1002 */       break;
/*      */     case 123: 
/* 1004 */       localObject = JsonToken.START_OBJECT;
/* 1005 */       break;
/*      */     case 93: 
/*      */     case 125: 
/* 1008 */       _reportUnexpectedChar(paramInt, "expected a value");
/*      */     case 116: 
/* 1010 */       _matchToken("true", 1);
/* 1011 */       localObject = JsonToken.VALUE_TRUE;
/* 1012 */       break;
/*      */     case 102: 
/* 1014 */       _matchToken("false", 1);
/* 1015 */       localObject = JsonToken.VALUE_FALSE;
/* 1016 */       break;
/*      */     case 110: 
/* 1018 */       _matchToken("null", 1);
/* 1019 */       localObject = JsonToken.VALUE_NULL;
/* 1020 */       break;
/*      */     
/*      */     case 45: 
/*      */     case 48: 
/*      */     case 49: 
/*      */     case 50: 
/*      */     case 51: 
/*      */     case 52: 
/*      */     case 53: 
/*      */     case 54: 
/*      */     case 55: 
/*      */     case 56: 
/*      */     case 57: 
/* 1033 */       localObject = parseNumberText(paramInt);
/* 1034 */       break;
/*      */     default: 
/* 1036 */       localObject = _handleUnexpectedValue(paramInt);
/*      */     }
/* 1038 */     this._nextToken = ((JsonToken)localObject);
/* 1039 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String nextTextValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1047 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1048 */       this._nameCopied = false;
/* 1049 */       JsonToken localJsonToken = this._nextToken;
/* 1050 */       this._nextToken = null;
/* 1051 */       this._currToken = localJsonToken;
/* 1052 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/* 1053 */         if (this._tokenIncomplete) {
/* 1054 */           this._tokenIncomplete = false;
/* 1055 */           _finishString();
/*      */         }
/* 1057 */         return this._textBuffer.contentsAsString();
/*      */       }
/* 1059 */       if (localJsonToken == JsonToken.START_ARRAY) {
/* 1060 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1061 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/* 1062 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/* 1064 */       return null;
/*      */     }
/*      */     
/* 1067 */     return nextToken() == JsonToken.VALUE_STRING ? getText() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int nextIntValue(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1075 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1076 */       this._nameCopied = false;
/* 1077 */       JsonToken localJsonToken = this._nextToken;
/* 1078 */       this._nextToken = null;
/* 1079 */       this._currToken = localJsonToken;
/* 1080 */       if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 1081 */         return getIntValue();
/*      */       }
/* 1083 */       if (localJsonToken == JsonToken.START_ARRAY) {
/* 1084 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1085 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/* 1086 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/* 1088 */       return paramInt;
/*      */     }
/*      */     
/* 1091 */     return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public long nextLongValue(long paramLong)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1099 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1100 */       this._nameCopied = false;
/* 1101 */       JsonToken localJsonToken = this._nextToken;
/* 1102 */       this._nextToken = null;
/* 1103 */       this._currToken = localJsonToken;
/* 1104 */       if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/* 1105 */         return getLongValue();
/*      */       }
/* 1107 */       if (localJsonToken == JsonToken.START_ARRAY) {
/* 1108 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1109 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/* 1110 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/* 1112 */       return paramLong;
/*      */     }
/*      */     
/* 1115 */     return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Boolean nextBooleanValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1123 */     if (this._currToken == JsonToken.FIELD_NAME) {
/* 1124 */       this._nameCopied = false;
/* 1125 */       JsonToken localJsonToken = this._nextToken;
/* 1126 */       this._nextToken = null;
/* 1127 */       this._currToken = localJsonToken;
/* 1128 */       if (localJsonToken == JsonToken.VALUE_TRUE) {
/* 1129 */         return Boolean.TRUE;
/*      */       }
/* 1131 */       if (localJsonToken == JsonToken.VALUE_FALSE) {
/* 1132 */         return Boolean.FALSE;
/*      */       }
/* 1134 */       if (localJsonToken == JsonToken.START_ARRAY) {
/* 1135 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/* 1136 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/* 1137 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/* 1139 */       return null;
/*      */     }
/* 1141 */     switch (nextToken()) {
/*      */     case VALUE_TRUE: 
/* 1143 */       return Boolean.TRUE;
/*      */     case VALUE_FALSE: 
/* 1145 */       return Boolean.FALSE;
/*      */     }
/* 1147 */     return null;
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
/*      */   protected JsonToken parseNumberText(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1176 */     char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 1177 */     int i = 0;
/* 1178 */     boolean bool = paramInt == 45;
/*      */     
/*      */ 
/* 1181 */     if (bool) {
/* 1182 */       arrayOfChar[(i++)] = '-';
/*      */       
/* 1184 */       if (this._inputPtr >= this._inputEnd) {
/* 1185 */         loadMoreGuaranteed();
/*      */       }
/* 1187 */       paramInt = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*      */       
/* 1189 */       if ((paramInt < 48) || (paramInt > 57)) {
/* 1190 */         return _handleInvalidNumberStart(paramInt, true);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1195 */     if (paramInt == 48) {
/* 1196 */       paramInt = _verifyNoLeadingZeroes();
/*      */     }
/*      */     
/*      */ 
/* 1200 */     arrayOfChar[(i++)] = ((char)paramInt);
/* 1201 */     int j = 1;
/*      */     
/*      */ 
/* 1204 */     int k = this._inputPtr + arrayOfChar.length;
/* 1205 */     if (k > this._inputEnd) {
/* 1206 */       k = this._inputEnd;
/*      */     }
/*      */     
/*      */     for (;;)
/*      */     {
/* 1211 */       if (this._inputPtr >= k)
/*      */       {
/* 1213 */         return _parserNumber2(arrayOfChar, i, bool, j);
/*      */       }
/* 1215 */       paramInt = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1216 */       if ((paramInt < 48) || (paramInt > 57)) {
/*      */         break;
/*      */       }
/* 1219 */       j++;
/* 1220 */       arrayOfChar[(i++)] = ((char)paramInt);
/*      */     }
/* 1222 */     if ((paramInt == 46) || (paramInt == 101) || (paramInt == 69)) {
/* 1223 */       return _parseFloatText(arrayOfChar, i, paramInt, bool, j);
/*      */     }
/*      */     
/* 1226 */     this._inputPtr -= 1;
/* 1227 */     this._textBuffer.setCurrentLength(i);
/*      */     
/*      */ 
/* 1230 */     return resetInt(bool, j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JsonToken _parserNumber2(char[] paramArrayOfChar, int paramInt1, boolean paramBoolean, int paramInt2)
/*      */     throws IOException, JsonParseException
/*      */   {
/*      */     for (;;)
/*      */     {
/* 1243 */       if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1244 */         this._textBuffer.setCurrentLength(paramInt1);
/* 1245 */         return resetInt(paramBoolean, paramInt2);
/*      */       }
/* 1247 */       int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1248 */       if ((i > 57) || (i < 48)) {
/* 1249 */         if ((i != 46) && (i != 101) && (i != 69)) break;
/* 1250 */         return _parseFloatText(paramArrayOfChar, paramInt1, i, paramBoolean, paramInt2);
/*      */       }
/*      */       
/*      */ 
/* 1254 */       if (paramInt1 >= paramArrayOfChar.length) {
/* 1255 */         paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1256 */         paramInt1 = 0;
/*      */       }
/* 1258 */       paramArrayOfChar[(paramInt1++)] = ((char)i);
/* 1259 */       paramInt2++;
/*      */     }
/* 1261 */     this._inputPtr -= 1;
/* 1262 */     this._textBuffer.setCurrentLength(paramInt1);
/*      */     
/*      */ 
/* 1265 */     return resetInt(paramBoolean, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int _verifyNoLeadingZeroes()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1277 */     if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1278 */       return 48;
/*      */     }
/* 1280 */     int i = this._inputBuffer[this._inputPtr] & 0xFF;
/*      */     
/* 1282 */     if ((i < 48) || (i > 57)) {
/* 1283 */       return 48;
/*      */     }
/*      */     
/* 1286 */     if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1287 */       reportInvalidNumber("Leading zeroes not allowed");
/*      */     }
/*      */     
/* 1290 */     this._inputPtr += 1;
/* 1291 */     if (i == 48) {
/* 1292 */       while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 1293 */         i = this._inputBuffer[this._inputPtr] & 0xFF;
/* 1294 */         if ((i < 48) || (i > 57)) {
/* 1295 */           return 48;
/*      */         }
/* 1297 */         this._inputPtr += 1;
/* 1298 */         if (i != 48) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1303 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   private JsonToken _parseFloatText(char[] paramArrayOfChar, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1310 */     int i = 0;
/* 1311 */     int j = 0;
/*      */     
/*      */ 
/* 1314 */     if (paramInt2 == 46) {
/* 1315 */       paramArrayOfChar[(paramInt1++)] = ((char)paramInt2);
/*      */       
/*      */       for (;;)
/*      */       {
/* 1319 */         if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1320 */           j = 1;
/* 1321 */           break;
/*      */         }
/* 1323 */         paramInt2 = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1324 */         if ((paramInt2 < 48) || (paramInt2 > 57)) {
/*      */           break;
/*      */         }
/* 1327 */         i++;
/* 1328 */         if (paramInt1 >= paramArrayOfChar.length) {
/* 1329 */           paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1330 */           paramInt1 = 0;
/*      */         }
/* 1332 */         paramArrayOfChar[(paramInt1++)] = ((char)paramInt2);
/*      */       }
/*      */       
/* 1335 */       if (i == 0) {
/* 1336 */         reportUnexpectedNumberChar(paramInt2, "Decimal point not followed by a digit");
/*      */       }
/*      */     }
/*      */     
/* 1340 */     int k = 0;
/* 1341 */     if ((paramInt2 == 101) || (paramInt2 == 69)) {
/* 1342 */       if (paramInt1 >= paramArrayOfChar.length) {
/* 1343 */         paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1344 */         paramInt1 = 0;
/*      */       }
/* 1346 */       paramArrayOfChar[(paramInt1++)] = ((char)paramInt2);
/*      */       
/* 1348 */       if (this._inputPtr >= this._inputEnd) {
/* 1349 */         loadMoreGuaranteed();
/*      */       }
/* 1351 */       paramInt2 = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*      */       
/* 1353 */       if ((paramInt2 == 45) || (paramInt2 == 43)) {
/* 1354 */         if (paramInt1 >= paramArrayOfChar.length) {
/* 1355 */           paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1356 */           paramInt1 = 0;
/*      */         }
/* 1358 */         paramArrayOfChar[(paramInt1++)] = ((char)paramInt2);
/*      */         
/* 1360 */         if (this._inputPtr >= this._inputEnd) {
/* 1361 */           loadMoreGuaranteed();
/*      */         }
/* 1363 */         paramInt2 = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*      */       }
/*      */       
/*      */ 
/* 1367 */       while ((paramInt2 <= 57) && (paramInt2 >= 48)) {
/* 1368 */         k++;
/* 1369 */         if (paramInt1 >= paramArrayOfChar.length) {
/* 1370 */           paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1371 */           paramInt1 = 0;
/*      */         }
/* 1373 */         paramArrayOfChar[(paramInt1++)] = ((char)paramInt2);
/* 1374 */         if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1375 */           j = 1;
/* 1376 */           break;
/*      */         }
/* 1378 */         paramInt2 = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*      */       }
/*      */       
/* 1381 */       if (k == 0) {
/* 1382 */         reportUnexpectedNumberChar(paramInt2, "Exponent indicator not followed by a digit");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1387 */     if (j == 0) {
/* 1388 */       this._inputPtr -= 1;
/*      */     }
/* 1390 */     this._textBuffer.setCurrentLength(paramInt1);
/*      */     
/*      */ 
/* 1393 */     return resetFloat(paramBoolean, paramInt3, i, k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Name _parseFieldName(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1405 */     if (paramInt != 34) {
/* 1406 */       return _handleUnusualFieldName(paramInt);
/*      */     }
/*      */     
/* 1409 */     if (this._inputPtr + 9 > this._inputEnd) {
/* 1410 */       return slowParseFieldName();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1419 */     byte[] arrayOfByte = this._inputBuffer;
/* 1420 */     int[] arrayOfInt = sInputCodesLatin1;
/*      */     
/* 1422 */     int i = arrayOfByte[(this._inputPtr++)] & 0xFF;
/*      */     
/* 1424 */     if (arrayOfInt[i] == 0) {
/* 1425 */       paramInt = arrayOfByte[(this._inputPtr++)] & 0xFF;
/* 1426 */       if (arrayOfInt[paramInt] == 0) {
/* 1427 */         i = i << 8 | paramInt;
/* 1428 */         paramInt = arrayOfByte[(this._inputPtr++)] & 0xFF;
/* 1429 */         if (arrayOfInt[paramInt] == 0) {
/* 1430 */           i = i << 8 | paramInt;
/* 1431 */           paramInt = arrayOfByte[(this._inputPtr++)] & 0xFF;
/* 1432 */           if (arrayOfInt[paramInt] == 0) {
/* 1433 */             i = i << 8 | paramInt;
/* 1434 */             paramInt = arrayOfByte[(this._inputPtr++)] & 0xFF;
/* 1435 */             if (arrayOfInt[paramInt] == 0) {
/* 1436 */               this._quad1 = i;
/* 1437 */               return parseMediumFieldName(paramInt, arrayOfInt);
/*      */             }
/* 1439 */             if (paramInt == 34) {
/* 1440 */               return findName(i, 4);
/*      */             }
/* 1442 */             return parseFieldName(i, paramInt, 4);
/*      */           }
/* 1444 */           if (paramInt == 34) {
/* 1445 */             return findName(i, 3);
/*      */           }
/* 1447 */           return parseFieldName(i, paramInt, 3);
/*      */         }
/* 1449 */         if (paramInt == 34) {
/* 1450 */           return findName(i, 2);
/*      */         }
/* 1452 */         return parseFieldName(i, paramInt, 2);
/*      */       }
/* 1454 */       if (paramInt == 34) {
/* 1455 */         return findName(i, 1);
/*      */       }
/* 1457 */       return parseFieldName(i, paramInt, 1);
/*      */     }
/* 1459 */     if (i == 34) {
/* 1460 */       return BytesToNameCanonicalizer.getEmptyName();
/*      */     }
/* 1462 */     return parseFieldName(0, i, 0);
/*      */   }
/*      */   
/*      */ 
/*      */   protected Name parseMediumFieldName(int paramInt, int[] paramArrayOfInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1469 */     int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1470 */     if (paramArrayOfInt[i] != 0) {
/* 1471 */       if (i == 34) {
/* 1472 */         return findName(this._quad1, paramInt, 1);
/*      */       }
/* 1474 */       return parseFieldName(this._quad1, paramInt, i, 1);
/*      */     }
/* 1476 */     paramInt = paramInt << 8 | i;
/* 1477 */     i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1478 */     if (paramArrayOfInt[i] != 0) {
/* 1479 */       if (i == 34) {
/* 1480 */         return findName(this._quad1, paramInt, 2);
/*      */       }
/* 1482 */       return parseFieldName(this._quad1, paramInt, i, 2);
/*      */     }
/* 1484 */     paramInt = paramInt << 8 | i;
/* 1485 */     i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1486 */     if (paramArrayOfInt[i] != 0) {
/* 1487 */       if (i == 34) {
/* 1488 */         return findName(this._quad1, paramInt, 3);
/*      */       }
/* 1490 */       return parseFieldName(this._quad1, paramInt, i, 3);
/*      */     }
/* 1492 */     paramInt = paramInt << 8 | i;
/* 1493 */     i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1494 */     if (paramArrayOfInt[i] != 0) {
/* 1495 */       if (i == 34) {
/* 1496 */         return findName(this._quad1, paramInt, 4);
/*      */       }
/* 1498 */       return parseFieldName(this._quad1, paramInt, i, 4);
/*      */     }
/* 1500 */     this._quadBuffer[0] = this._quad1;
/* 1501 */     this._quadBuffer[1] = paramInt;
/* 1502 */     return parseLongFieldName(i);
/*      */   }
/*      */   
/*      */ 
/*      */   protected Name parseLongFieldName(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1509 */     int[] arrayOfInt = sInputCodesLatin1;
/* 1510 */     int i = 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/* 1517 */       if (this._inputEnd - this._inputPtr < 4) {
/* 1518 */         return parseEscapedFieldName(this._quadBuffer, i, 0, paramInt, 0);
/*      */       }
/*      */       
/*      */ 
/* 1522 */       int j = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1523 */       if (arrayOfInt[j] != 0) {
/* 1524 */         if (j == 34) {
/* 1525 */           return findName(this._quadBuffer, i, paramInt, 1);
/*      */         }
/* 1527 */         return parseEscapedFieldName(this._quadBuffer, i, paramInt, j, 1);
/*      */       }
/*      */       
/* 1530 */       paramInt = paramInt << 8 | j;
/* 1531 */       j = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1532 */       if (arrayOfInt[j] != 0) {
/* 1533 */         if (j == 34) {
/* 1534 */           return findName(this._quadBuffer, i, paramInt, 2);
/*      */         }
/* 1536 */         return parseEscapedFieldName(this._quadBuffer, i, paramInt, j, 2);
/*      */       }
/*      */       
/* 1539 */       paramInt = paramInt << 8 | j;
/* 1540 */       j = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1541 */       if (arrayOfInt[j] != 0) {
/* 1542 */         if (j == 34) {
/* 1543 */           return findName(this._quadBuffer, i, paramInt, 3);
/*      */         }
/* 1545 */         return parseEscapedFieldName(this._quadBuffer, i, paramInt, j, 3);
/*      */       }
/*      */       
/* 1548 */       paramInt = paramInt << 8 | j;
/* 1549 */       j = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1550 */       if (arrayOfInt[j] != 0) {
/* 1551 */         if (j == 34) {
/* 1552 */           return findName(this._quadBuffer, i, paramInt, 4);
/*      */         }
/* 1554 */         return parseEscapedFieldName(this._quadBuffer, i, paramInt, j, 4);
/*      */       }
/*      */       
/*      */ 
/* 1558 */       if (i >= this._quadBuffer.length) {
/* 1559 */         this._quadBuffer = growArrayBy(this._quadBuffer, i);
/*      */       }
/* 1561 */       this._quadBuffer[(i++)] = paramInt;
/* 1562 */       paramInt = j;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Name slowParseFieldName()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1574 */     if ((this._inputPtr >= this._inputEnd) && 
/* 1575 */       (!loadMore())) {
/* 1576 */       _reportInvalidEOF(": was expecting closing '\"' for name");
/*      */     }
/*      */     
/* 1579 */     int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1580 */     if (i == 34) {
/* 1581 */       return BytesToNameCanonicalizer.getEmptyName();
/*      */     }
/* 1583 */     return parseEscapedFieldName(this._quadBuffer, 0, 0, i, 0);
/*      */   }
/*      */   
/*      */   private Name parseFieldName(int paramInt1, int paramInt2, int paramInt3)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1589 */     return parseEscapedFieldName(this._quadBuffer, 0, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */   
/*      */   private Name parseFieldName(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1595 */     this._quadBuffer[0] = paramInt1;
/* 1596 */     return parseEscapedFieldName(this._quadBuffer, 1, paramInt2, paramInt3, paramInt4);
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
/*      */   protected Name parseEscapedFieldName(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1615 */     int[] arrayOfInt = sInputCodesLatin1;
/*      */     for (;;)
/*      */     {
/* 1618 */       if (arrayOfInt[paramInt3] != 0) {
/* 1619 */         if (paramInt3 == 34) {
/*      */           break;
/*      */         }
/*      */         
/* 1623 */         if (paramInt3 != 92)
/*      */         {
/* 1625 */           _throwUnquotedSpace(paramInt3, "name");
/*      */         }
/*      */         else {
/* 1628 */           paramInt3 = _decodeEscaped();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1635 */         if (paramInt3 > 127)
/*      */         {
/* 1637 */           if (paramInt4 >= 4) {
/* 1638 */             if (paramInt1 >= paramArrayOfInt.length) {
/* 1639 */               this._quadBuffer = (paramArrayOfInt = growArrayBy(paramArrayOfInt, paramArrayOfInt.length));
/*      */             }
/* 1641 */             paramArrayOfInt[(paramInt1++)] = paramInt2;
/* 1642 */             paramInt2 = 0;
/* 1643 */             paramInt4 = 0;
/*      */           }
/* 1645 */           if (paramInt3 < 2048) {
/* 1646 */             paramInt2 = paramInt2 << 8 | 0xC0 | paramInt3 >> 6;
/* 1647 */             paramInt4++;
/*      */           }
/*      */           else {
/* 1650 */             paramInt2 = paramInt2 << 8 | 0xE0 | paramInt3 >> 12;
/* 1651 */             paramInt4++;
/*      */             
/* 1653 */             if (paramInt4 >= 4) {
/* 1654 */               if (paramInt1 >= paramArrayOfInt.length) {
/* 1655 */                 this._quadBuffer = (paramArrayOfInt = growArrayBy(paramArrayOfInt, paramArrayOfInt.length));
/*      */               }
/* 1657 */               paramArrayOfInt[(paramInt1++)] = paramInt2;
/* 1658 */               paramInt2 = 0;
/* 1659 */               paramInt4 = 0;
/*      */             }
/* 1661 */             paramInt2 = paramInt2 << 8 | 0x80 | paramInt3 >> 6 & 0x3F;
/* 1662 */             paramInt4++;
/*      */           }
/*      */           
/* 1665 */           paramInt3 = 0x80 | paramInt3 & 0x3F;
/*      */         }
/*      */       }
/*      */       
/* 1669 */       if (paramInt4 < 4) {
/* 1670 */         paramInt4++;
/* 1671 */         paramInt2 = paramInt2 << 8 | paramInt3;
/*      */       } else {
/* 1673 */         if (paramInt1 >= paramArrayOfInt.length) {
/* 1674 */           this._quadBuffer = (paramArrayOfInt = growArrayBy(paramArrayOfInt, paramArrayOfInt.length));
/*      */         }
/* 1676 */         paramArrayOfInt[(paramInt1++)] = paramInt2;
/* 1677 */         paramInt2 = paramInt3;
/* 1678 */         paramInt4 = 1;
/*      */       }
/* 1680 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1681 */         (!loadMore())) {
/* 1682 */         _reportInvalidEOF(" in field name");
/*      */       }
/*      */       
/* 1685 */       paramInt3 = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*      */     }
/*      */     
/* 1688 */     if (paramInt4 > 0) {
/* 1689 */       if (paramInt1 >= paramArrayOfInt.length) {
/* 1690 */         this._quadBuffer = (paramArrayOfInt = growArrayBy(paramArrayOfInt, paramArrayOfInt.length));
/*      */       }
/* 1692 */       paramArrayOfInt[(paramInt1++)] = paramInt2;
/*      */     }
/* 1694 */     Name localName = this._symbols.findName(paramArrayOfInt, paramInt1);
/* 1695 */     if (localName == null) {
/* 1696 */       localName = addName(paramArrayOfInt, paramInt1, paramInt4);
/*      */     }
/* 1698 */     return localName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Name _handleUnusualFieldName(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1711 */     if ((paramInt == 39) && (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES))) {
/* 1712 */       return _parseApostropheFieldName();
/*      */     }
/*      */     
/* 1715 */     if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/* 1716 */       _reportUnexpectedChar(paramInt, "was expecting double-quote to start field name");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1722 */     int[] arrayOfInt1 = com.shaded.fasterxml.jackson.core.io.CharTypes.getInputCodeUtf8JsNames();
/*      */     
/* 1724 */     if (arrayOfInt1[paramInt] != 0) {
/* 1725 */       _reportUnexpectedChar(paramInt, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1732 */     int[] arrayOfInt2 = this._quadBuffer;
/* 1733 */     int i = 0;
/* 1734 */     int j = 0;
/* 1735 */     int k = 0;
/*      */     
/*      */     for (;;)
/*      */     {
/* 1739 */       if (k < 4) {
/* 1740 */         k++;
/* 1741 */         j = j << 8 | paramInt;
/*      */       } else {
/* 1743 */         if (i >= arrayOfInt2.length) {
/* 1744 */           this._quadBuffer = (arrayOfInt2 = growArrayBy(arrayOfInt2, arrayOfInt2.length));
/*      */         }
/* 1746 */         arrayOfInt2[(i++)] = j;
/* 1747 */         j = paramInt;
/* 1748 */         k = 1;
/*      */       }
/* 1750 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1751 */         (!loadMore())) {
/* 1752 */         _reportInvalidEOF(" in field name");
/*      */       }
/*      */       
/* 1755 */       paramInt = this._inputBuffer[this._inputPtr] & 0xFF;
/* 1756 */       if (arrayOfInt1[paramInt] != 0) {
/*      */         break;
/*      */       }
/* 1759 */       this._inputPtr += 1;
/*      */     }
/*      */     
/* 1762 */     if (k > 0) {
/* 1763 */       if (i >= arrayOfInt2.length) {
/* 1764 */         this._quadBuffer = (arrayOfInt2 = growArrayBy(arrayOfInt2, arrayOfInt2.length));
/*      */       }
/* 1766 */       arrayOfInt2[(i++)] = j;
/*      */     }
/* 1768 */     Name localName = this._symbols.findName(arrayOfInt2, i);
/* 1769 */     if (localName == null) {
/* 1770 */       localName = addName(arrayOfInt2, i, k);
/*      */     }
/* 1772 */     return localName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Name _parseApostropheFieldName()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1783 */     if ((this._inputPtr >= this._inputEnd) && 
/* 1784 */       (!loadMore())) {
/* 1785 */       _reportInvalidEOF(": was expecting closing ''' for name");
/*      */     }
/*      */     
/* 1788 */     int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 1789 */     if (i == 39) {
/* 1790 */       return BytesToNameCanonicalizer.getEmptyName();
/*      */     }
/* 1792 */     int[] arrayOfInt1 = this._quadBuffer;
/* 1793 */     int j = 0;
/* 1794 */     int k = 0;
/* 1795 */     int m = 0;
/*      */     
/*      */ 
/*      */ 
/* 1799 */     int[] arrayOfInt2 = sInputCodesLatin1;
/*      */     
/*      */ 
/* 1802 */     while (i != 39)
/*      */     {
/*      */ 
/*      */ 
/* 1806 */       if ((i != 34) && (arrayOfInt2[i] != 0)) {
/* 1807 */         if (i != 92)
/*      */         {
/*      */ 
/* 1810 */           _throwUnquotedSpace(i, "name");
/*      */         }
/*      */         else {
/* 1813 */           i = _decodeEscaped();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1820 */         if (i > 127)
/*      */         {
/* 1822 */           if (m >= 4) {
/* 1823 */             if (j >= arrayOfInt1.length) {
/* 1824 */               this._quadBuffer = (arrayOfInt1 = growArrayBy(arrayOfInt1, arrayOfInt1.length));
/*      */             }
/* 1826 */             arrayOfInt1[(j++)] = k;
/* 1827 */             k = 0;
/* 1828 */             m = 0;
/*      */           }
/* 1830 */           if (i < 2048) {
/* 1831 */             k = k << 8 | 0xC0 | i >> 6;
/* 1832 */             m++;
/*      */           }
/*      */           else {
/* 1835 */             k = k << 8 | 0xE0 | i >> 12;
/* 1836 */             m++;
/*      */             
/* 1838 */             if (m >= 4) {
/* 1839 */               if (j >= arrayOfInt1.length) {
/* 1840 */                 this._quadBuffer = (arrayOfInt1 = growArrayBy(arrayOfInt1, arrayOfInt1.length));
/*      */               }
/* 1842 */               arrayOfInt1[(j++)] = k;
/* 1843 */               k = 0;
/* 1844 */               m = 0;
/*      */             }
/* 1846 */             k = k << 8 | 0x80 | i >> 6 & 0x3F;
/* 1847 */             m++;
/*      */           }
/*      */           
/* 1850 */           i = 0x80 | i & 0x3F;
/*      */         }
/*      */       }
/*      */       
/* 1854 */       if (m < 4) {
/* 1855 */         m++;
/* 1856 */         k = k << 8 | i;
/*      */       } else {
/* 1858 */         if (j >= arrayOfInt1.length) {
/* 1859 */           this._quadBuffer = (arrayOfInt1 = growArrayBy(arrayOfInt1, arrayOfInt1.length));
/*      */         }
/* 1861 */         arrayOfInt1[(j++)] = k;
/* 1862 */         k = i;
/* 1863 */         m = 1;
/*      */       }
/* 1865 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1866 */         (!loadMore())) {
/* 1867 */         _reportInvalidEOF(" in field name");
/*      */       }
/*      */       
/* 1870 */       i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*      */     }
/*      */     
/* 1873 */     if (m > 0) {
/* 1874 */       if (j >= arrayOfInt1.length) {
/* 1875 */         this._quadBuffer = (arrayOfInt1 = growArrayBy(arrayOfInt1, arrayOfInt1.length));
/*      */       }
/* 1877 */       arrayOfInt1[(j++)] = k;
/*      */     }
/* 1879 */     Name localName = this._symbols.findName(arrayOfInt1, j);
/* 1880 */     if (localName == null) {
/* 1881 */       localName = addName(arrayOfInt1, j, m);
/*      */     }
/* 1883 */     return localName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Name findName(int paramInt1, int paramInt2)
/*      */     throws JsonParseException
/*      */   {
/* 1896 */     Name localName = this._symbols.findName(paramInt1);
/* 1897 */     if (localName != null) {
/* 1898 */       return localName;
/*      */     }
/*      */     
/* 1901 */     this._quadBuffer[0] = paramInt1;
/* 1902 */     return addName(this._quadBuffer, 1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */   private Name findName(int paramInt1, int paramInt2, int paramInt3)
/*      */     throws JsonParseException
/*      */   {
/* 1909 */     Name localName = this._symbols.findName(paramInt1, paramInt2);
/* 1910 */     if (localName != null) {
/* 1911 */       return localName;
/*      */     }
/*      */     
/* 1914 */     this._quadBuffer[0] = paramInt1;
/* 1915 */     this._quadBuffer[1] = paramInt2;
/* 1916 */     return addName(this._quadBuffer, 2, paramInt3);
/*      */   }
/*      */   
/*      */   private Name findName(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*      */     throws JsonParseException
/*      */   {
/* 1922 */     if (paramInt1 >= paramArrayOfInt.length) {
/* 1923 */       this._quadBuffer = (paramArrayOfInt = growArrayBy(paramArrayOfInt, paramArrayOfInt.length));
/*      */     }
/* 1925 */     paramArrayOfInt[(paramInt1++)] = paramInt2;
/* 1926 */     Name localName = this._symbols.findName(paramArrayOfInt, paramInt1);
/* 1927 */     if (localName == null) {
/* 1928 */       return addName(paramArrayOfInt, paramInt1, paramInt3);
/*      */     }
/* 1930 */     return localName;
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
/*      */   private Name addName(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */     throws JsonParseException
/*      */   {
/* 1947 */     int i = (paramInt1 << 2) - 4 + paramInt2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     int j;
/*      */     
/*      */ 
/*      */ 
/* 1956 */     if (paramInt2 < 4) {
/* 1957 */       j = paramArrayOfInt[(paramInt1 - 1)];
/*      */       
/* 1959 */       paramArrayOfInt[(paramInt1 - 1)] = (j << (4 - paramInt2 << 3));
/*      */     } else {
/* 1961 */       j = 0;
/*      */     }
/*      */     
/*      */ 
/* 1965 */     char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 1966 */     int k = 0;
/*      */     
/* 1968 */     for (int m = 0; m < i;) {
/* 1969 */       int n = paramArrayOfInt[(m >> 2)];
/* 1970 */       int i1 = m & 0x3;
/* 1971 */       n = n >> (3 - i1 << 3) & 0xFF;
/* 1972 */       m++;
/*      */       
/* 1974 */       if (n > 127) {
/*      */         int i2;
/* 1976 */         if ((n & 0xE0) == 192) {
/* 1977 */           n &= 0x1F;
/* 1978 */           i2 = 1;
/* 1979 */         } else if ((n & 0xF0) == 224) {
/* 1980 */           n &= 0xF;
/* 1981 */           i2 = 2;
/* 1982 */         } else if ((n & 0xF8) == 240) {
/* 1983 */           n &= 0x7;
/* 1984 */           i2 = 3;
/*      */         } else {
/* 1986 */           _reportInvalidInitial(n);
/* 1987 */           i2 = n = 1;
/*      */         }
/* 1989 */         if (m + i2 > i) {
/* 1990 */           _reportInvalidEOF(" in field name");
/*      */         }
/*      */         
/*      */ 
/* 1994 */         int i3 = paramArrayOfInt[(m >> 2)];
/* 1995 */         i1 = m & 0x3;
/* 1996 */         i3 >>= 3 - i1 << 3;
/* 1997 */         m++;
/*      */         
/* 1999 */         if ((i3 & 0xC0) != 128) {
/* 2000 */           _reportInvalidOther(i3);
/*      */         }
/* 2002 */         n = n << 6 | i3 & 0x3F;
/* 2003 */         if (i2 > 1) {
/* 2004 */           i3 = paramArrayOfInt[(m >> 2)];
/* 2005 */           i1 = m & 0x3;
/* 2006 */           i3 >>= 3 - i1 << 3;
/* 2007 */           m++;
/*      */           
/* 2009 */           if ((i3 & 0xC0) != 128) {
/* 2010 */             _reportInvalidOther(i3);
/*      */           }
/* 2012 */           n = n << 6 | i3 & 0x3F;
/* 2013 */           if (i2 > 2) {
/* 2014 */             i3 = paramArrayOfInt[(m >> 2)];
/* 2015 */             i1 = m & 0x3;
/* 2016 */             i3 >>= 3 - i1 << 3;
/* 2017 */             m++;
/* 2018 */             if ((i3 & 0xC0) != 128) {
/* 2019 */               _reportInvalidOther(i3 & 0xFF);
/*      */             }
/* 2021 */             n = n << 6 | i3 & 0x3F;
/*      */           }
/*      */         }
/* 2024 */         if (i2 > 2) {
/* 2025 */           n -= 65536;
/* 2026 */           if (k >= arrayOfChar.length) {
/* 2027 */             arrayOfChar = this._textBuffer.expandCurrentSegment();
/*      */           }
/* 2029 */           arrayOfChar[(k++)] = ((char)(55296 + (n >> 10)));
/* 2030 */           n = 0xDC00 | n & 0x3FF;
/*      */         }
/*      */       }
/* 2033 */       if (k >= arrayOfChar.length) {
/* 2034 */         arrayOfChar = this._textBuffer.expandCurrentSegment();
/*      */       }
/* 2036 */       arrayOfChar[(k++)] = ((char)n);
/*      */     }
/*      */     
/*      */ 
/* 2040 */     String str = new String(arrayOfChar, 0, k);
/*      */     
/* 2042 */     if (paramInt2 < 4) {
/* 2043 */       paramArrayOfInt[(paramInt1 - 1)] = j;
/*      */     }
/* 2045 */     return this._symbols.addName(str, paramArrayOfInt, paramInt1);
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
/*      */   protected void _finishString()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2059 */     int i = this._inputPtr;
/* 2060 */     if (i >= this._inputEnd) {
/* 2061 */       loadMoreGuaranteed();
/* 2062 */       i = this._inputPtr;
/*      */     }
/* 2064 */     int j = 0;
/* 2065 */     char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 2066 */     int[] arrayOfInt = sInputCodesUtf8;
/*      */     
/* 2068 */     int k = Math.min(this._inputEnd, i + arrayOfChar.length);
/* 2069 */     byte[] arrayOfByte = this._inputBuffer;
/* 2070 */     while (i < k) {
/* 2071 */       int m = arrayOfByte[i] & 0xFF;
/* 2072 */       if (arrayOfInt[m] != 0) {
/* 2073 */         if (m != 34) break;
/* 2074 */         this._inputPtr = (i + 1);
/* 2075 */         this._textBuffer.setCurrentLength(j);
/* 2076 */         return;
/*      */       }
/*      */       
/*      */ 
/* 2080 */       i++;
/* 2081 */       arrayOfChar[(j++)] = ((char)m);
/*      */     }
/* 2083 */     this._inputPtr = i;
/* 2084 */     _finishString2(arrayOfChar, j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _finishString2(char[] paramArrayOfChar, int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2093 */     int[] arrayOfInt = sInputCodesUtf8;
/* 2094 */     byte[] arrayOfByte = this._inputBuffer;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/* 2101 */       int i = this._inputPtr;
/* 2102 */       if (i >= this._inputEnd) {
/* 2103 */         loadMoreGuaranteed();
/* 2104 */         i = this._inputPtr;
/*      */       }
/* 2106 */       if (paramInt >= paramArrayOfChar.length) {
/* 2107 */         paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 2108 */         paramInt = 0;
/*      */       }
/* 2110 */       int j = Math.min(this._inputEnd, i + (paramArrayOfChar.length - paramInt));
/* 2111 */       int k; while (i < j) {
/* 2112 */         k = arrayOfByte[(i++)] & 0xFF;
/* 2113 */         if (arrayOfInt[k] != 0) {
/* 2114 */           this._inputPtr = i;
/*      */           break label125;
/*      */         }
/* 2117 */         paramArrayOfChar[(paramInt++)] = ((char)k);
/*      */       }
/* 2119 */       this._inputPtr = i;
/* 2120 */       continue;
/*      */       label125:
/* 2122 */       if (k == 34) {
/*      */         break;
/*      */       }
/*      */       
/* 2126 */       switch (arrayOfInt[k]) {
/*      */       case 1: 
/* 2128 */         k = _decodeEscaped();
/* 2129 */         break;
/*      */       case 2: 
/* 2131 */         k = _decodeUtf8_2(k);
/* 2132 */         break;
/*      */       case 3: 
/* 2134 */         if (this._inputEnd - this._inputPtr >= 2) {
/* 2135 */           k = _decodeUtf8_3fast(k);
/*      */         } else {
/* 2137 */           k = _decodeUtf8_3(k);
/*      */         }
/* 2139 */         break;
/*      */       case 4: 
/* 2141 */         k = _decodeUtf8_4(k);
/*      */         
/* 2143 */         paramArrayOfChar[(paramInt++)] = ((char)(0xD800 | k >> 10));
/* 2144 */         if (paramInt >= paramArrayOfChar.length) {
/* 2145 */           paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 2146 */           paramInt = 0;
/*      */         }
/* 2148 */         k = 0xDC00 | k & 0x3FF;
/*      */         
/* 2150 */         break;
/*      */       default: 
/* 2152 */         if (k < 32)
/*      */         {
/* 2154 */           _throwUnquotedSpace(k, "string value");
/*      */         }
/*      */         else {
/* 2157 */           _reportInvalidChar(k);
/*      */         }
/*      */         break;
/*      */       }
/* 2161 */       if (paramInt >= paramArrayOfChar.length) {
/* 2162 */         paramArrayOfChar = this._textBuffer.finishCurrentSegment();
/* 2163 */         paramInt = 0;
/*      */       }
/*      */       
/* 2166 */       paramArrayOfChar[(paramInt++)] = ((char)k);
/*      */     }
/* 2168 */     this._textBuffer.setCurrentLength(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _skipString()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2179 */     this._tokenIncomplete = false;
/*      */     
/*      */ 
/* 2182 */     int[] arrayOfInt = sInputCodesUtf8;
/* 2183 */     byte[] arrayOfByte = this._inputBuffer;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/* 2191 */       int i = this._inputPtr;
/* 2192 */       int j = this._inputEnd;
/* 2193 */       if (i >= j) {
/* 2194 */         loadMoreGuaranteed();
/* 2195 */         i = this._inputPtr;
/* 2196 */         j = this._inputEnd; }
/*      */       int k;
/* 2198 */       while (i < j) {
/* 2199 */         k = arrayOfByte[(i++)] & 0xFF;
/* 2200 */         if (arrayOfInt[k] != 0) {
/* 2201 */           this._inputPtr = i;
/*      */           break label87;
/*      */         }
/*      */       }
/* 2205 */       this._inputPtr = i;
/* 2206 */       continue;
/*      */       label87:
/* 2208 */       if (k == 34) {
/*      */         break;
/*      */       }
/*      */       
/* 2212 */       switch (arrayOfInt[k]) {
/*      */       case 1: 
/* 2214 */         _decodeEscaped();
/* 2215 */         break;
/*      */       case 2: 
/* 2217 */         _skipUtf8_2(k);
/* 2218 */         break;
/*      */       case 3: 
/* 2220 */         _skipUtf8_3(k);
/* 2221 */         break;
/*      */       case 4: 
/* 2223 */         _skipUtf8_4(k);
/* 2224 */         break;
/*      */       default: 
/* 2226 */         if (k < 32)
/*      */         {
/* 2228 */           _throwUnquotedSpace(k, "string value");
/*      */         }
/*      */         else {
/* 2231 */           _reportInvalidChar(k);
/*      */         }
/*      */         
/*      */ 
/*      */         break;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected JsonToken _handleUnexpectedValue(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2245 */     switch (paramInt) {
/*      */     case 39: 
/* 2247 */       if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 2248 */         return _handleApostropheValue();
/*      */       }
/*      */       break;
/*      */     case 78: 
/* 2252 */       _matchToken("NaN", 1);
/* 2253 */       if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2254 */         return resetAsNaN("NaN", NaN.0D);
/*      */       }
/* 2256 */       _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/* 2257 */       break;
/*      */     case 43: 
/* 2259 */       if ((this._inputPtr >= this._inputEnd) && 
/* 2260 */         (!loadMore())) {
/* 2261 */         _reportInvalidEOFInValue();
/*      */       }
/*      */       
/* 2264 */       return _handleInvalidNumberStart(this._inputBuffer[(this._inputPtr++)] & 0xFF, false);
/*      */     }
/*      */     
/* 2267 */     _reportUnexpectedChar(paramInt, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/* 2268 */     return null;
/*      */   }
/*      */   
/*      */   protected JsonToken _handleApostropheValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2274 */     int i = 0;
/*      */     
/* 2276 */     int j = 0;
/* 2277 */     char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/*      */     
/*      */ 
/* 2280 */     int[] arrayOfInt = sInputCodesUtf8;
/* 2281 */     byte[] arrayOfByte = this._inputBuffer;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/* 2288 */       if (this._inputPtr >= this._inputEnd) {
/* 2289 */         loadMoreGuaranteed();
/*      */       }
/* 2291 */       if (j >= arrayOfChar.length) {
/* 2292 */         arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 2293 */         j = 0;
/*      */       }
/* 2295 */       int k = this._inputEnd;
/*      */       
/* 2297 */       int m = this._inputPtr + (arrayOfChar.length - j);
/* 2298 */       if (m < k) {
/* 2299 */         k = m;
/*      */       }
/*      */       
/* 2302 */       while (this._inputPtr < k) {
/* 2303 */         i = arrayOfByte[(this._inputPtr++)] & 0xFF;
/* 2304 */         if ((i == 39) || (arrayOfInt[i] != 0)) {
/*      */           break label140;
/*      */         }
/* 2307 */         arrayOfChar[(j++)] = ((char)i);
/*      */       }
/* 2309 */       continue;
/*      */       
/*      */       label140:
/* 2312 */       if (i == 39) {
/*      */         break;
/*      */       }
/*      */       
/* 2316 */       switch (arrayOfInt[i]) {
/*      */       case 1: 
/* 2318 */         if (i != 34) {
/* 2319 */           i = _decodeEscaped();
/*      */         }
/*      */         break;
/*      */       case 2: 
/* 2323 */         i = _decodeUtf8_2(i);
/* 2324 */         break;
/*      */       case 3: 
/* 2326 */         if (this._inputEnd - this._inputPtr >= 2) {
/* 2327 */           i = _decodeUtf8_3fast(i);
/*      */         } else {
/* 2329 */           i = _decodeUtf8_3(i);
/*      */         }
/* 2331 */         break;
/*      */       case 4: 
/* 2333 */         i = _decodeUtf8_4(i);
/*      */         
/* 2335 */         arrayOfChar[(j++)] = ((char)(0xD800 | i >> 10));
/* 2336 */         if (j >= arrayOfChar.length) {
/* 2337 */           arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 2338 */           j = 0;
/*      */         }
/* 2340 */         i = 0xDC00 | i & 0x3FF;
/*      */         
/* 2342 */         break;
/*      */       default: 
/* 2344 */         if (i < 32) {
/* 2345 */           _throwUnquotedSpace(i, "string value");
/*      */         }
/*      */         
/* 2348 */         _reportInvalidChar(i);
/*      */       }
/*      */       
/* 2351 */       if (j >= arrayOfChar.length) {
/* 2352 */         arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 2353 */         j = 0;
/*      */       }
/*      */       
/* 2356 */       arrayOfChar[(j++)] = ((char)i);
/*      */     }
/* 2358 */     this._textBuffer.setCurrentLength(j);
/*      */     
/* 2360 */     return JsonToken.VALUE_STRING;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonToken _handleInvalidNumberStart(int paramInt, boolean paramBoolean)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2370 */     while (paramInt == 73) {
/* 2371 */       if ((this._inputPtr >= this._inputEnd) && 
/* 2372 */         (!loadMore())) {
/* 2373 */         _reportInvalidEOFInValue();
/*      */       }
/*      */       
/* 2376 */       paramInt = this._inputBuffer[(this._inputPtr++)];
/*      */       String str;
/* 2378 */       if (paramInt == 78) {
/* 2379 */         str = paramBoolean ? "-INF" : "+INF";
/* 2380 */       } else { if (paramInt != 110) break;
/* 2381 */         str = paramBoolean ? "-Infinity" : "+Infinity";
/*      */       }
/*      */       
/*      */ 
/* 2385 */       _matchToken(str, 3);
/* 2386 */       if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 2387 */         return resetAsNaN(str, paramBoolean ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
/*      */       }
/* 2389 */       _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */     }
/* 2391 */     reportUnexpectedNumberChar(paramInt, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 2392 */     return null;
/*      */   }
/*      */   
/*      */   protected void _matchToken(String paramString, int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2398 */     int i = paramString.length();
/*      */     do
/*      */     {
/* 2401 */       if (((this._inputPtr >= this._inputEnd) && (!loadMore())) || (this._inputBuffer[this._inputPtr] != paramString.charAt(paramInt)))
/*      */       {
/* 2403 */         _reportInvalidToken(paramString.substring(0, paramInt));
/*      */       }
/* 2405 */       this._inputPtr += 1;
/* 2406 */       paramInt++; } while (paramInt < i);
/*      */     
/*      */ 
/* 2409 */     if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 2410 */       return;
/*      */     }
/* 2412 */     int j = this._inputBuffer[this._inputPtr] & 0xFF;
/* 2413 */     if ((j < 48) || (j == 93) || (j == 125)) {
/* 2414 */       return;
/*      */     }
/*      */     
/* 2417 */     char c = (char)_decodeCharForError(j);
/* 2418 */     if (Character.isJavaIdentifierPart(c)) {
/* 2419 */       _reportInvalidToken(paramString.substring(0, paramInt));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void _reportInvalidToken(String paramString)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2426 */     _reportInvalidToken(paramString, "'null', 'true', 'false' or NaN");
/*      */   }
/*      */   
/*      */   protected void _reportInvalidToken(String paramString1, String paramString2)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2432 */     StringBuilder localStringBuilder = new StringBuilder(paramString1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2439 */     while ((this._inputPtr < this._inputEnd) || (loadMore()))
/*      */     {
/*      */ 
/* 2442 */       int i = this._inputBuffer[(this._inputPtr++)];
/* 2443 */       char c = (char)_decodeCharForError(i);
/* 2444 */       if (!Character.isJavaIdentifierPart(c)) {
/*      */         break;
/*      */       }
/* 2447 */       localStringBuilder.append(c);
/*      */     }
/* 2449 */     _reportError("Unrecognized token '" + localStringBuilder.toString() + "': was expecting " + paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int _skipWS()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2461 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 2462 */       int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 2463 */       if (i > 32) {
/* 2464 */         if (i != 47) {
/* 2465 */           return i;
/*      */         }
/* 2467 */         _skipComment();
/* 2468 */       } else if (i != 32) {
/* 2469 */         if (i == 10) {
/* 2470 */           _skipLF();
/* 2471 */         } else if (i == 13) {
/* 2472 */           _skipCR();
/* 2473 */         } else if (i != 9) {
/* 2474 */           _throwInvalidSpace(i);
/*      */         }
/*      */       }
/*      */     }
/* 2478 */     throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
/*      */   }
/*      */   
/*      */   private int _skipWSOrEnd()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2484 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 2485 */       int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 2486 */       if (i > 32) {
/* 2487 */         if (i != 47) {
/* 2488 */           return i;
/*      */         }
/* 2490 */         _skipComment();
/* 2491 */       } else if (i != 32) {
/* 2492 */         if (i == 10) {
/* 2493 */           _skipLF();
/* 2494 */         } else if (i == 13) {
/* 2495 */           _skipCR();
/* 2496 */         } else if (i != 9) {
/* 2497 */           _throwInvalidSpace(i);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2502 */     _handleEOF();
/* 2503 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int _skipColon()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2513 */     if (this._inputPtr >= this._inputEnd) {
/* 2514 */       loadMoreGuaranteed();
/*      */     }
/*      */     
/* 2517 */     int i = this._inputBuffer[(this._inputPtr++)];
/* 2518 */     if (i == 58) {
/* 2519 */       if (this._inputPtr < this._inputEnd) {
/* 2520 */         i = this._inputBuffer[this._inputPtr] & 0xFF;
/* 2521 */         if ((i > 32) && (i != 47)) {
/* 2522 */           this._inputPtr += 1;
/* 2523 */           return i;
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 2528 */       i &= 0xFF;
/*      */       
/*      */       for (;;)
/*      */       {
/* 2532 */         switch (i) {
/*      */         case 9: 
/*      */         case 32: 
/*      */           break;
/*      */         case 13: 
/* 2537 */           _skipCR();
/* 2538 */           break;
/*      */         case 10: 
/* 2540 */           _skipLF();
/* 2541 */           break;
/*      */         case 47: 
/* 2543 */           _skipComment();
/* 2544 */           break;
/*      */         default: 
/* 2546 */           if (i >= 32) break label221;
/* 2547 */           _throwInvalidSpace(i);break;
/*      */         }
/*      */         
/*      */         
/* 2551 */         if (this._inputPtr >= this._inputEnd) {
/* 2552 */           loadMoreGuaranteed();
/*      */         }
/* 2554 */         i = this._inputBuffer[(this._inputPtr++)] & 0xFF; }
/*      */       label221:
/* 2556 */       if (i != 58) {
/* 2557 */         _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2562 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 2563 */       i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 2564 */       if (i > 32) {
/* 2565 */         if (i != 47) {
/* 2566 */           return i;
/*      */         }
/* 2568 */         _skipComment();
/* 2569 */       } else if (i != 32) {
/* 2570 */         if (i == 10) {
/* 2571 */           _skipLF();
/* 2572 */         } else if (i == 13) {
/* 2573 */           _skipCR();
/* 2574 */         } else if (i != 9) {
/* 2575 */           _throwInvalidSpace(i);
/*      */         }
/*      */       }
/*      */     }
/* 2579 */     throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
/*      */   }
/*      */   
/*      */   private void _skipComment()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2585 */     if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
/* 2586 */       _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
/*      */     }
/*      */     
/* 2589 */     if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 2590 */       _reportInvalidEOF(" in a comment");
/*      */     }
/* 2592 */     int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 2593 */     if (i == 47) {
/* 2594 */       _skipCppComment();
/* 2595 */     } else if (i == 42) {
/* 2596 */       _skipCComment();
/*      */     } else {
/* 2598 */       _reportUnexpectedChar(i, "was expecting either '*' or '/' for a comment");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void _skipCComment()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2606 */     int[] arrayOfInt = com.shaded.fasterxml.jackson.core.io.CharTypes.getInputCodeComment();
/*      */     
/*      */ 
/*      */ 
/* 2610 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 2611 */       int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 2612 */       int j = arrayOfInt[i];
/* 2613 */       if (j != 0)
/* 2614 */         switch (j) {
/*      */         case 42: 
/* 2616 */           if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/*      */             break label204;
/*      */           }
/* 2619 */           if (this._inputBuffer[this._inputPtr] == 47) {
/* 2620 */             this._inputPtr += 1; return;
/*      */           }
/*      */           
/*      */           break;
/*      */         case 10: 
/* 2625 */           _skipLF();
/* 2626 */           break;
/*      */         case 13: 
/* 2628 */           _skipCR();
/* 2629 */           break;
/*      */         case 2: 
/* 2631 */           _skipUtf8_2(i);
/* 2632 */           break;
/*      */         case 3: 
/* 2634 */           _skipUtf8_3(i);
/* 2635 */           break;
/*      */         case 4: 
/* 2637 */           _skipUtf8_4(i);
/* 2638 */           break;
/*      */         
/*      */         default: 
/* 2641 */           _reportInvalidChar(i);
/*      */         }
/*      */     }
/*      */     label204:
/* 2645 */     _reportInvalidEOF(" in a comment");
/*      */   }
/*      */   
/*      */ 
/*      */   private void _skipCppComment()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2652 */     int[] arrayOfInt = com.shaded.fasterxml.jackson.core.io.CharTypes.getInputCodeComment();
/* 2653 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 2654 */       int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 2655 */       int j = arrayOfInt[i];
/* 2656 */       if (j != 0) {
/* 2657 */         switch (j) {
/*      */         case 10: 
/* 2659 */           _skipLF();
/* 2660 */           return;
/*      */         case 13: 
/* 2662 */           _skipCR(); return;
/*      */         case 42: 
/*      */           break;
/*      */         
/*      */         case 2: 
/* 2667 */           _skipUtf8_2(i);
/* 2668 */           break;
/*      */         case 3: 
/* 2670 */           _skipUtf8_3(i);
/* 2671 */           break;
/*      */         case 4: 
/* 2673 */           _skipUtf8_4(i);
/* 2674 */           break;
/*      */         
/*      */         default: 
/* 2677 */           _reportInvalidChar(i);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected char _decodeEscaped()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2687 */     if ((this._inputPtr >= this._inputEnd) && 
/* 2688 */       (!loadMore())) {
/* 2689 */       _reportInvalidEOF(" in character escape sequence");
/*      */     }
/*      */     
/* 2692 */     int i = this._inputBuffer[(this._inputPtr++)];
/*      */     
/* 2694 */     switch (i)
/*      */     {
/*      */     case 98: 
/* 2697 */       return '\b';
/*      */     case 116: 
/* 2699 */       return '\t';
/*      */     case 110: 
/* 2701 */       return '\n';
/*      */     case 102: 
/* 2703 */       return '\f';
/*      */     case 114: 
/* 2705 */       return '\r';
/*      */     
/*      */ 
/*      */     case 34: 
/*      */     case 47: 
/*      */     case 92: 
/* 2711 */       return (char)i;
/*      */     
/*      */     case 117: 
/*      */       break;
/*      */     
/*      */     default: 
/* 2717 */       return _handleUnrecognizedCharacterEscape((char)_decodeCharForError(i));
/*      */     }
/*      */     
/*      */     
/* 2721 */     int j = 0;
/* 2722 */     for (int k = 0; k < 4; k++) {
/* 2723 */       if ((this._inputPtr >= this._inputEnd) && 
/* 2724 */         (!loadMore())) {
/* 2725 */         _reportInvalidEOF(" in character escape sequence");
/*      */       }
/*      */       
/* 2728 */       int m = this._inputBuffer[(this._inputPtr++)];
/* 2729 */       int n = com.shaded.fasterxml.jackson.core.io.CharTypes.charToHex(m);
/* 2730 */       if (n < 0) {
/* 2731 */         _reportUnexpectedChar(m, "expected a hex-digit for character escape sequence");
/*      */       }
/* 2733 */       j = j << 4 | n;
/*      */     }
/* 2735 */     return (char)j;
/*      */   }
/*      */   
/*      */   protected int _decodeCharForError(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2741 */     int i = paramInt;
/* 2742 */     if (i < 0)
/*      */     {
/*      */       int j;
/*      */       
/* 2746 */       if ((i & 0xE0) == 192) {
/* 2747 */         i &= 0x1F;
/* 2748 */         j = 1;
/* 2749 */       } else if ((i & 0xF0) == 224) {
/* 2750 */         i &= 0xF;
/* 2751 */         j = 2;
/* 2752 */       } else if ((i & 0xF8) == 240)
/*      */       {
/* 2754 */         i &= 0x7;
/* 2755 */         j = 3;
/*      */       } else {
/* 2757 */         _reportInvalidInitial(i & 0xFF);
/* 2758 */         j = 1;
/*      */       }
/*      */       
/* 2761 */       int k = nextByte();
/* 2762 */       if ((k & 0xC0) != 128) {
/* 2763 */         _reportInvalidOther(k & 0xFF);
/*      */       }
/* 2765 */       i = i << 6 | k & 0x3F;
/*      */       
/* 2767 */       if (j > 1) {
/* 2768 */         k = nextByte();
/* 2769 */         if ((k & 0xC0) != 128) {
/* 2770 */           _reportInvalidOther(k & 0xFF);
/*      */         }
/* 2772 */         i = i << 6 | k & 0x3F;
/* 2773 */         if (j > 2) {
/* 2774 */           k = nextByte();
/* 2775 */           if ((k & 0xC0) != 128) {
/* 2776 */             _reportInvalidOther(k & 0xFF);
/*      */           }
/* 2778 */           i = i << 6 | k & 0x3F;
/*      */         }
/*      */       }
/*      */     }
/* 2782 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int _decodeUtf8_2(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2794 */     if (this._inputPtr >= this._inputEnd) {
/* 2795 */       loadMoreGuaranteed();
/*      */     }
/* 2797 */     int i = this._inputBuffer[(this._inputPtr++)];
/* 2798 */     if ((i & 0xC0) != 128) {
/* 2799 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2801 */     return (paramInt & 0x1F) << 6 | i & 0x3F;
/*      */   }
/*      */   
/*      */   private int _decodeUtf8_3(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2807 */     if (this._inputPtr >= this._inputEnd) {
/* 2808 */       loadMoreGuaranteed();
/*      */     }
/* 2810 */     paramInt &= 0xF;
/* 2811 */     int i = this._inputBuffer[(this._inputPtr++)];
/* 2812 */     if ((i & 0xC0) != 128) {
/* 2813 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2815 */     int j = paramInt << 6 | i & 0x3F;
/* 2816 */     if (this._inputPtr >= this._inputEnd) {
/* 2817 */       loadMoreGuaranteed();
/*      */     }
/* 2819 */     i = this._inputBuffer[(this._inputPtr++)];
/* 2820 */     if ((i & 0xC0) != 128) {
/* 2821 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2823 */     j = j << 6 | i & 0x3F;
/* 2824 */     return j;
/*      */   }
/*      */   
/*      */   private int _decodeUtf8_3fast(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2830 */     paramInt &= 0xF;
/* 2831 */     int i = this._inputBuffer[(this._inputPtr++)];
/* 2832 */     if ((i & 0xC0) != 128) {
/* 2833 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2835 */     int j = paramInt << 6 | i & 0x3F;
/* 2836 */     i = this._inputBuffer[(this._inputPtr++)];
/* 2837 */     if ((i & 0xC0) != 128) {
/* 2838 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2840 */     j = j << 6 | i & 0x3F;
/* 2841 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int _decodeUtf8_4(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2851 */     if (this._inputPtr >= this._inputEnd) {
/* 2852 */       loadMoreGuaranteed();
/*      */     }
/* 2854 */     int i = this._inputBuffer[(this._inputPtr++)];
/* 2855 */     if ((i & 0xC0) != 128) {
/* 2856 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2858 */     paramInt = (paramInt & 0x7) << 6 | i & 0x3F;
/*      */     
/* 2860 */     if (this._inputPtr >= this._inputEnd) {
/* 2861 */       loadMoreGuaranteed();
/*      */     }
/* 2863 */     i = this._inputBuffer[(this._inputPtr++)];
/* 2864 */     if ((i & 0xC0) != 128) {
/* 2865 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2867 */     paramInt = paramInt << 6 | i & 0x3F;
/* 2868 */     if (this._inputPtr >= this._inputEnd) {
/* 2869 */       loadMoreGuaranteed();
/*      */     }
/* 2871 */     i = this._inputBuffer[(this._inputPtr++)];
/* 2872 */     if ((i & 0xC0) != 128) {
/* 2873 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2879 */     return (paramInt << 6 | i & 0x3F) - 65536;
/*      */   }
/*      */   
/*      */   private void _skipUtf8_2(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2885 */     if (this._inputPtr >= this._inputEnd) {
/* 2886 */       loadMoreGuaranteed();
/*      */     }
/* 2888 */     paramInt = this._inputBuffer[(this._inputPtr++)];
/* 2889 */     if ((paramInt & 0xC0) != 128) {
/* 2890 */       _reportInvalidOther(paramInt & 0xFF, this._inputPtr);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _skipUtf8_3(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2900 */     if (this._inputPtr >= this._inputEnd) {
/* 2901 */       loadMoreGuaranteed();
/*      */     }
/*      */     
/* 2904 */     paramInt = this._inputBuffer[(this._inputPtr++)];
/* 2905 */     if ((paramInt & 0xC0) != 128) {
/* 2906 */       _reportInvalidOther(paramInt & 0xFF, this._inputPtr);
/*      */     }
/* 2908 */     if (this._inputPtr >= this._inputEnd) {
/* 2909 */       loadMoreGuaranteed();
/*      */     }
/* 2911 */     paramInt = this._inputBuffer[(this._inputPtr++)];
/* 2912 */     if ((paramInt & 0xC0) != 128) {
/* 2913 */       _reportInvalidOther(paramInt & 0xFF, this._inputPtr);
/*      */     }
/*      */   }
/*      */   
/*      */   private void _skipUtf8_4(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2920 */     if (this._inputPtr >= this._inputEnd) {
/* 2921 */       loadMoreGuaranteed();
/*      */     }
/* 2923 */     int i = this._inputBuffer[(this._inputPtr++)];
/* 2924 */     if ((i & 0xC0) != 128) {
/* 2925 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2927 */     if (this._inputPtr >= this._inputEnd) {
/* 2928 */       loadMoreGuaranteed();
/*      */     }
/* 2930 */     i = this._inputBuffer[(this._inputPtr++)];
/* 2931 */     if ((i & 0xC0) != 128) {
/* 2932 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
/*      */     }
/* 2934 */     if (this._inputPtr >= this._inputEnd) {
/* 2935 */       loadMoreGuaranteed();
/*      */     }
/* 2937 */     i = this._inputBuffer[(this._inputPtr++)];
/* 2938 */     if ((i & 0xC0) != 128) {
/* 2939 */       _reportInvalidOther(i & 0xFF, this._inputPtr);
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
/*      */ 
/*      */ 
/*      */   protected void _skipCR()
/*      */     throws IOException
/*      */   {
/* 2955 */     if (((this._inputPtr < this._inputEnd) || (loadMore())) && 
/* 2956 */       (this._inputBuffer[this._inputPtr] == 10)) {
/* 2957 */       this._inputPtr += 1;
/*      */     }
/*      */     
/* 2960 */     this._currInputRow += 1;
/* 2961 */     this._currInputRowStart = this._inputPtr;
/*      */   }
/*      */   
/*      */   protected void _skipLF() throws IOException
/*      */   {
/* 2966 */     this._currInputRow += 1;
/* 2967 */     this._currInputRowStart = this._inputPtr;
/*      */   }
/*      */   
/*      */   private int nextByte()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 2973 */     if (this._inputPtr >= this._inputEnd) {
/* 2974 */       loadMoreGuaranteed();
/*      */     }
/* 2976 */     return this._inputBuffer[(this._inputPtr++)] & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _reportInvalidChar(int paramInt)
/*      */     throws JsonParseException
/*      */   {
/* 2989 */     if (paramInt < 32) {
/* 2990 */       _throwInvalidSpace(paramInt);
/*      */     }
/* 2992 */     _reportInvalidInitial(paramInt);
/*      */   }
/*      */   
/*      */   protected void _reportInvalidInitial(int paramInt)
/*      */     throws JsonParseException
/*      */   {
/* 2998 */     _reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(paramInt));
/*      */   }
/*      */   
/*      */   protected void _reportInvalidOther(int paramInt)
/*      */     throws JsonParseException
/*      */   {
/* 3004 */     _reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(paramInt));
/*      */   }
/*      */   
/*      */   protected void _reportInvalidOther(int paramInt1, int paramInt2)
/*      */     throws JsonParseException
/*      */   {
/* 3010 */     this._inputPtr = paramInt2;
/* 3011 */     _reportInvalidOther(paramInt1);
/*      */   }
/*      */   
/*      */   public static int[] growArrayBy(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 3016 */     if (paramArrayOfInt == null) {
/* 3017 */       return new int[paramInt];
/*      */     }
/* 3019 */     int[] arrayOfInt = paramArrayOfInt;
/* 3020 */     int i = paramArrayOfInt.length;
/* 3021 */     paramArrayOfInt = new int[i + paramInt];
/* 3022 */     System.arraycopy(arrayOfInt, 0, paramArrayOfInt, 0, i);
/* 3023 */     return paramArrayOfInt;
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
/*      */   protected byte[] _decodeBase64(Base64Variant paramBase64Variant)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 3039 */     ByteArrayBuilder localByteArrayBuilder = _getByteArrayBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/* 3046 */       if (this._inputPtr >= this._inputEnd) {
/* 3047 */         loadMoreGuaranteed();
/*      */       }
/* 3049 */       int i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 3050 */       if (i > 32) {
/* 3051 */         int j = paramBase64Variant.decodeBase64Char(i);
/* 3052 */         if (j < 0) {
/* 3053 */           if (i == 34) {
/* 3054 */             return localByteArrayBuilder.toByteArray();
/*      */           }
/* 3056 */           j = _decodeBase64Escape(paramBase64Variant, i, 0);
/* 3057 */           if (j < 0) {}
/*      */         }
/*      */         else
/*      */         {
/* 3061 */           int k = j;
/*      */           
/*      */ 
/*      */ 
/* 3065 */           if (this._inputPtr >= this._inputEnd) {
/* 3066 */             loadMoreGuaranteed();
/*      */           }
/* 3068 */           i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 3069 */           j = paramBase64Variant.decodeBase64Char(i);
/* 3070 */           if (j < 0) {
/* 3071 */             j = _decodeBase64Escape(paramBase64Variant, i, 1);
/*      */           }
/* 3073 */           k = k << 6 | j;
/*      */           
/*      */ 
/* 3076 */           if (this._inputPtr >= this._inputEnd) {
/* 3077 */             loadMoreGuaranteed();
/*      */           }
/* 3079 */           i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 3080 */           j = paramBase64Variant.decodeBase64Char(i);
/*      */           
/*      */ 
/* 3083 */           if (j < 0) {
/* 3084 */             if (j != -2)
/*      */             {
/* 3086 */               if ((i == 34) && (!paramBase64Variant.usesPadding())) {
/* 3087 */                 k >>= 4;
/* 3088 */                 localByteArrayBuilder.append(k);
/* 3089 */                 return localByteArrayBuilder.toByteArray();
/*      */               }
/* 3091 */               j = _decodeBase64Escape(paramBase64Variant, i, 2);
/*      */             }
/* 3093 */             if (j == -2)
/*      */             {
/* 3095 */               if (this._inputPtr >= this._inputEnd) {
/* 3096 */                 loadMoreGuaranteed();
/*      */               }
/* 3098 */               i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 3099 */               if (!paramBase64Variant.usesPaddingChar(i)) {
/* 3100 */                 throw reportInvalidBase64Char(paramBase64Variant, i, 3, "expected padding character '" + paramBase64Variant.getPaddingChar() + "'");
/*      */               }
/*      */               
/* 3103 */               k >>= 4;
/* 3104 */               localByteArrayBuilder.append(k);
/* 3105 */               continue;
/*      */             }
/*      */           }
/*      */           
/* 3109 */           k = k << 6 | j;
/*      */           
/* 3111 */           if (this._inputPtr >= this._inputEnd) {
/* 3112 */             loadMoreGuaranteed();
/*      */           }
/* 3114 */           i = this._inputBuffer[(this._inputPtr++)] & 0xFF;
/* 3115 */           j = paramBase64Variant.decodeBase64Char(i);
/* 3116 */           if (j < 0) {
/* 3117 */             if (j != -2)
/*      */             {
/* 3119 */               if ((i == 34) && (!paramBase64Variant.usesPadding())) {
/* 3120 */                 k >>= 2;
/* 3121 */                 localByteArrayBuilder.appendTwoBytes(k);
/* 3122 */                 return localByteArrayBuilder.toByteArray();
/*      */               }
/* 3124 */               j = _decodeBase64Escape(paramBase64Variant, i, 3);
/*      */             }
/* 3126 */             if (j == -2)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3133 */               k >>= 2;
/* 3134 */               localByteArrayBuilder.appendTwoBytes(k);
/* 3135 */               continue;
/*      */             }
/*      */           }
/*      */           
/* 3139 */           k = k << 6 | j;
/* 3140 */           localByteArrayBuilder.appendThreeBytes(k);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/UTF8StreamJsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */