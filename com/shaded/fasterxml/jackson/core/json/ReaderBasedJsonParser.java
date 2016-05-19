/*      */ package com.shaded.fasterxml.jackson.core.json;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*      */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*      */ import com.shaded.fasterxml.jackson.core.base.ParserBase;
/*      */ import com.shaded.fasterxml.jackson.core.io.CharTypes;
/*      */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*      */ import com.shaded.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
/*      */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import com.shaded.fasterxml.jackson.core.util.TextBuffer;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ReaderBasedJsonParser
/*      */   extends ParserBase
/*      */ {
/*      */   protected Reader _reader;
/*      */   protected char[] _inputBuffer;
/*      */   protected ObjectCodec _objectCodec;
/*      */   protected final CharsToNameCanonicalizer _symbols;
/*      */   protected final int _hashSeed;
/*   62 */   protected boolean _tokenIncomplete = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ReaderBasedJsonParser(IOContext paramIOContext, int paramInt, Reader paramReader, ObjectCodec paramObjectCodec, CharsToNameCanonicalizer paramCharsToNameCanonicalizer)
/*      */   {
/*   73 */     super(paramIOContext, paramInt);
/*   74 */     this._reader = paramReader;
/*   75 */     this._inputBuffer = paramIOContext.allocTokenBuffer();
/*   76 */     this._objectCodec = paramObjectCodec;
/*   77 */     this._symbols = paramCharsToNameCanonicalizer;
/*   78 */     this._hashSeed = paramCharsToNameCanonicalizer.hashSeed();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectCodec getCodec()
/*      */   {
/*   89 */     return this._objectCodec;
/*      */   }
/*      */   
/*      */   public void setCodec(ObjectCodec paramObjectCodec)
/*      */   {
/*   94 */     this._objectCodec = paramObjectCodec;
/*      */   }
/*      */   
/*      */   public int releaseBuffered(Writer paramWriter)
/*      */     throws IOException
/*      */   {
/*  100 */     int i = this._inputEnd - this._inputPtr;
/*  101 */     if (i < 1) {
/*  102 */       return 0;
/*      */     }
/*      */     
/*  105 */     int j = this._inputPtr;
/*  106 */     paramWriter.write(this._inputBuffer, j, i);
/*  107 */     return i;
/*      */   }
/*      */   
/*      */   public Object getInputSource()
/*      */   {
/*  112 */     return this._reader;
/*      */   }
/*      */   
/*      */   protected boolean loadMore()
/*      */     throws IOException
/*      */   {
/*  118 */     this._currInputProcessed += this._inputEnd;
/*  119 */     this._currInputRowStart -= this._inputEnd;
/*      */     
/*  121 */     if (this._reader != null) {
/*  122 */       int i = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
/*  123 */       if (i > 0) {
/*  124 */         this._inputPtr = 0;
/*  125 */         this._inputEnd = i;
/*  126 */         return true;
/*      */       }
/*      */       
/*  129 */       _closeInput();
/*      */       
/*  131 */       if (i == 0) {
/*  132 */         throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
/*      */       }
/*      */     }
/*  135 */     return false;
/*      */   }
/*      */   
/*      */   protected char getNextChar(String paramString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  141 */     if ((this._inputPtr >= this._inputEnd) && 
/*  142 */       (!loadMore())) {
/*  143 */       _reportInvalidEOF(paramString);
/*      */     }
/*      */     
/*  146 */     return this._inputBuffer[(this._inputPtr++)];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _closeInput()
/*      */     throws IOException
/*      */   {
/*  159 */     if (this._reader != null) {
/*  160 */       if ((this._ioContext.isResourceManaged()) || (isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE))) {
/*  161 */         this._reader.close();
/*      */       }
/*  163 */       this._reader = null;
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
/*      */   protected void _releaseBuffers()
/*      */     throws IOException
/*      */   {
/*  177 */     super._releaseBuffers();
/*  178 */     char[] arrayOfChar = this._inputBuffer;
/*  179 */     if (arrayOfChar != null) {
/*  180 */       this._inputBuffer = null;
/*  181 */       this._ioContext.releaseTokenBuffer(arrayOfChar);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getText()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  201 */     JsonToken localJsonToken = this._currToken;
/*  202 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/*  203 */       if (this._tokenIncomplete) {
/*  204 */         this._tokenIncomplete = false;
/*  205 */         _finishString();
/*      */       }
/*  207 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  209 */     return _getText2(localJsonToken);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getValueAsString()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  218 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  219 */       if (this._tokenIncomplete) {
/*  220 */         this._tokenIncomplete = false;
/*  221 */         _finishString();
/*      */       }
/*  223 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  225 */     return super.getValueAsString(null);
/*      */   }
/*      */   
/*      */ 
/*      */   public String getValueAsString(String paramString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  232 */     if (this._currToken == JsonToken.VALUE_STRING) {
/*  233 */       if (this._tokenIncomplete) {
/*  234 */         this._tokenIncomplete = false;
/*  235 */         _finishString();
/*      */       }
/*  237 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  239 */     return super.getValueAsString(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */   protected String _getText2(JsonToken paramJsonToken)
/*      */   {
/*  245 */     if (paramJsonToken == null) {
/*  246 */       return null;
/*      */     }
/*  248 */     switch (paramJsonToken) {
/*      */     case FIELD_NAME: 
/*  250 */       return this._parsingContext.getCurrentName();
/*      */     
/*      */ 
/*      */     case VALUE_STRING: 
/*      */     case VALUE_NUMBER_INT: 
/*      */     case VALUE_NUMBER_FLOAT: 
/*  256 */       return this._textBuffer.contentsAsString();
/*      */     }
/*  258 */     return paramJsonToken.asString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public char[] getTextCharacters()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  266 */     if (this._currToken != null) {
/*  267 */       switch (this._currToken)
/*      */       {
/*      */       case FIELD_NAME: 
/*  270 */         if (!this._nameCopied) {
/*  271 */           String str = this._parsingContext.getCurrentName();
/*  272 */           int i = str.length();
/*  273 */           if (this._nameCopyBuffer == null) {
/*  274 */             this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(i);
/*  275 */           } else if (this._nameCopyBuffer.length < i) {
/*  276 */             this._nameCopyBuffer = new char[i];
/*      */           }
/*  278 */           str.getChars(0, i, this._nameCopyBuffer, 0);
/*  279 */           this._nameCopied = true;
/*      */         }
/*  281 */         return this._nameCopyBuffer;
/*      */       
/*      */       case VALUE_STRING: 
/*  284 */         if (this._tokenIncomplete) {
/*  285 */           this._tokenIncomplete = false;
/*  286 */           _finishString();
/*      */         }
/*      */       
/*      */       case VALUE_NUMBER_INT: 
/*      */       case VALUE_NUMBER_FLOAT: 
/*  291 */         return this._textBuffer.getTextBuffer();
/*      */       }
/*      */       
/*  294 */       return this._currToken.asCharArray();
/*      */     }
/*      */     
/*  297 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getTextLength()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  304 */     if (this._currToken != null) {
/*  305 */       switch (this._currToken)
/*      */       {
/*      */       case FIELD_NAME: 
/*  308 */         return this._parsingContext.getCurrentName().length();
/*      */       case VALUE_STRING: 
/*  310 */         if (this._tokenIncomplete) {
/*  311 */           this._tokenIncomplete = false;
/*  312 */           _finishString();
/*      */         }
/*      */       
/*      */       case VALUE_NUMBER_INT: 
/*      */       case VALUE_NUMBER_FLOAT: 
/*  317 */         return this._textBuffer.size();
/*      */       }
/*      */       
/*  320 */       return this._currToken.asCharArray().length;
/*      */     }
/*      */     
/*  323 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getTextOffset()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  330 */     if (this._currToken != null) {
/*  331 */       switch (this._currToken) {
/*      */       case FIELD_NAME: 
/*  333 */         return 0;
/*      */       case VALUE_STRING: 
/*  335 */         if (this._tokenIncomplete) {
/*  336 */           this._tokenIncomplete = false;
/*  337 */           _finishString();
/*      */         }
/*      */       
/*      */       case VALUE_NUMBER_INT: 
/*      */       case VALUE_NUMBER_FLOAT: 
/*  342 */         return this._textBuffer.getTextOffset();
/*      */       }
/*      */       
/*      */     }
/*  346 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public byte[] getBinaryValue(Base64Variant paramBase64Variant)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  353 */     if ((this._currToken != JsonToken.VALUE_STRING) && ((this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT) || (this._binaryValue == null)))
/*      */     {
/*  355 */       _reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  360 */     if (this._tokenIncomplete) {
/*      */       try {
/*  362 */         this._binaryValue = _decodeBase64(paramBase64Variant);
/*      */       } catch (IllegalArgumentException localIllegalArgumentException) {
/*  364 */         throw _constructError("Failed to decode VALUE_STRING as base64 (" + paramBase64Variant + "): " + localIllegalArgumentException.getMessage());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  369 */       this._tokenIncomplete = false;
/*      */     }
/*  371 */     else if (this._binaryValue == null) {
/*  372 */       ByteArrayBuilder localByteArrayBuilder = _getByteArrayBuilder();
/*  373 */       _decodeBase64(getText(), localByteArrayBuilder, paramBase64Variant);
/*  374 */       this._binaryValue = localByteArrayBuilder.toByteArray();
/*      */     }
/*      */     
/*  377 */     return this._binaryValue;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int readBinaryValue(Base64Variant paramBase64Variant, OutputStream paramOutputStream)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  385 */     if ((!this._tokenIncomplete) || (this._currToken != JsonToken.VALUE_STRING)) {
/*  386 */       arrayOfByte = getBinaryValue(paramBase64Variant);
/*  387 */       paramOutputStream.write(arrayOfByte);
/*  388 */       return arrayOfByte.length;
/*      */     }
/*      */     
/*  391 */     byte[] arrayOfByte = this._ioContext.allocBase64Buffer();
/*      */     try {
/*  393 */       return _readBinary(paramBase64Variant, paramOutputStream, arrayOfByte);
/*      */     } finally {
/*  395 */       this._ioContext.releaseBase64Buffer(arrayOfByte);
/*      */     }
/*      */   }
/*      */   
/*      */   protected int _readBinary(Base64Variant paramBase64Variant, OutputStream paramOutputStream, byte[] paramArrayOfByte)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  402 */     int i = 0;
/*  403 */     int j = paramArrayOfByte.length - 3;
/*  404 */     int k = 0;
/*      */     
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/*  410 */       if (this._inputPtr >= this._inputEnd) {
/*  411 */         loadMoreGuaranteed();
/*      */       }
/*  413 */       char c = this._inputBuffer[(this._inputPtr++)];
/*  414 */       if (c > ' ') {
/*  415 */         int m = paramBase64Variant.decodeBase64Char(c);
/*  416 */         if (m < 0) {
/*  417 */           if (c == '"') {
/*      */             break;
/*      */           }
/*  420 */           m = _decodeBase64Escape(paramBase64Variant, c, 0);
/*  421 */           if (m < 0) {}
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  427 */           if (i > j) {
/*  428 */             k += i;
/*  429 */             paramOutputStream.write(paramArrayOfByte, 0, i);
/*  430 */             i = 0;
/*      */           }
/*      */           
/*  433 */           int n = m;
/*      */           
/*      */ 
/*      */ 
/*  437 */           if (this._inputPtr >= this._inputEnd) {
/*  438 */             loadMoreGuaranteed();
/*      */           }
/*  440 */           c = this._inputBuffer[(this._inputPtr++)];
/*  441 */           m = paramBase64Variant.decodeBase64Char(c);
/*  442 */           if (m < 0) {
/*  443 */             m = _decodeBase64Escape(paramBase64Variant, c, 1);
/*      */           }
/*  445 */           n = n << 6 | m;
/*      */           
/*      */ 
/*  448 */           if (this._inputPtr >= this._inputEnd) {
/*  449 */             loadMoreGuaranteed();
/*      */           }
/*  451 */           c = this._inputBuffer[(this._inputPtr++)];
/*  452 */           m = paramBase64Variant.decodeBase64Char(c);
/*      */           
/*      */ 
/*  455 */           if (m < 0) {
/*  456 */             if (m != -2)
/*      */             {
/*  458 */               if ((c == '"') && (!paramBase64Variant.usesPadding())) {
/*  459 */                 n >>= 4;
/*  460 */                 paramArrayOfByte[(i++)] = ((byte)n);
/*  461 */                 break;
/*      */               }
/*  463 */               m = _decodeBase64Escape(paramBase64Variant, c, 2);
/*      */             }
/*  465 */             if (m == -2)
/*      */             {
/*  467 */               if (this._inputPtr >= this._inputEnd) {
/*  468 */                 loadMoreGuaranteed();
/*      */               }
/*  470 */               c = this._inputBuffer[(this._inputPtr++)];
/*  471 */               if (!paramBase64Variant.usesPaddingChar(c)) {
/*  472 */                 throw reportInvalidBase64Char(paramBase64Variant, c, 3, "expected padding character '" + paramBase64Variant.getPaddingChar() + "'");
/*      */               }
/*      */               
/*  475 */               n >>= 4;
/*  476 */               paramArrayOfByte[(i++)] = ((byte)n);
/*  477 */               continue;
/*      */             }
/*      */           }
/*      */           
/*  481 */           n = n << 6 | m;
/*      */           
/*  483 */           if (this._inputPtr >= this._inputEnd) {
/*  484 */             loadMoreGuaranteed();
/*      */           }
/*  486 */           c = this._inputBuffer[(this._inputPtr++)];
/*  487 */           m = paramBase64Variant.decodeBase64Char(c);
/*  488 */           if (m < 0) {
/*  489 */             if (m != -2)
/*      */             {
/*  491 */               if ((c == '"') && (!paramBase64Variant.usesPadding())) {
/*  492 */                 n >>= 2;
/*  493 */                 paramArrayOfByte[(i++)] = ((byte)(n >> 8));
/*  494 */                 paramArrayOfByte[(i++)] = ((byte)n);
/*  495 */                 break;
/*      */               }
/*  497 */               m = _decodeBase64Escape(paramBase64Variant, c, 3);
/*      */             }
/*  499 */             if (m == -2)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  506 */               n >>= 2;
/*  507 */               paramArrayOfByte[(i++)] = ((byte)(n >> 8));
/*  508 */               paramArrayOfByte[(i++)] = ((byte)n);
/*  509 */               continue;
/*      */             }
/*      */           }
/*      */           
/*  513 */           n = n << 6 | m;
/*  514 */           paramArrayOfByte[(i++)] = ((byte)(n >> 16));
/*  515 */           paramArrayOfByte[(i++)] = ((byte)(n >> 8));
/*  516 */           paramArrayOfByte[(i++)] = ((byte)n);
/*      */         } } }
/*  518 */     this._tokenIncomplete = false;
/*  519 */     if (i > 0) {
/*  520 */       k += i;
/*  521 */       paramOutputStream.write(paramArrayOfByte, 0, i);
/*      */     }
/*  523 */     return k;
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
/*  540 */     this._numTypesValid = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  546 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  547 */       return _nextAfterName();
/*      */     }
/*  549 */     if (this._tokenIncomplete) {
/*  550 */       _skipString();
/*      */     }
/*  552 */     int i = _skipWSOrEnd();
/*  553 */     if (i < 0)
/*      */     {
/*      */ 
/*      */ 
/*  557 */       close();
/*  558 */       return this._currToken = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  564 */     this._tokenInputTotal = (this._currInputProcessed + this._inputPtr - 1L);
/*  565 */     this._tokenInputRow = this._currInputRow;
/*  566 */     this._tokenInputCol = (this._inputPtr - this._currInputRowStart - 1);
/*      */     
/*      */ 
/*  569 */     this._binaryValue = null;
/*      */     
/*      */ 
/*  572 */     if (i == 93) {
/*  573 */       if (!this._parsingContext.inArray()) {
/*  574 */         _reportMismatchedEndMarker(i, '}');
/*      */       }
/*  576 */       this._parsingContext = this._parsingContext.getParent();
/*  577 */       return this._currToken = JsonToken.END_ARRAY;
/*      */     }
/*  579 */     if (i == 125) {
/*  580 */       if (!this._parsingContext.inObject()) {
/*  581 */         _reportMismatchedEndMarker(i, ']');
/*      */       }
/*  583 */       this._parsingContext = this._parsingContext.getParent();
/*  584 */       return this._currToken = JsonToken.END_OBJECT;
/*      */     }
/*      */     
/*      */ 
/*  588 */     if (this._parsingContext.expectComma()) {
/*  589 */       if (i != 44) {
/*  590 */         _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
/*      */       }
/*  592 */       i = _skipWS();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  599 */     boolean bool = this._parsingContext.inObject();
/*  600 */     Object localObject; if (bool)
/*      */     {
/*  602 */       localObject = _parseFieldName(i);
/*  603 */       this._parsingContext.setCurrentName((String)localObject);
/*  604 */       this._currToken = JsonToken.FIELD_NAME;
/*  605 */       i = _skipWS();
/*  606 */       if (i != 58) {
/*  607 */         _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
/*      */       }
/*  609 */       i = _skipWS();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  616 */     switch (i) {
/*      */     case 34: 
/*  618 */       this._tokenIncomplete = true;
/*  619 */       localObject = JsonToken.VALUE_STRING;
/*  620 */       break;
/*      */     case 91: 
/*  622 */       if (!bool) {
/*  623 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/*  625 */       localObject = JsonToken.START_ARRAY;
/*  626 */       break;
/*      */     case 123: 
/*  628 */       if (!bool) {
/*  629 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/*  631 */       localObject = JsonToken.START_OBJECT;
/*  632 */       break;
/*      */     
/*      */ 
/*      */     case 93: 
/*      */     case 125: 
/*  637 */       _reportUnexpectedChar(i, "expected a value");
/*      */     case 116: 
/*  639 */       _matchToken("true", 1);
/*  640 */       localObject = JsonToken.VALUE_TRUE;
/*  641 */       break;
/*      */     case 102: 
/*  643 */       _matchToken("false", 1);
/*  644 */       localObject = JsonToken.VALUE_FALSE;
/*  645 */       break;
/*      */     case 110: 
/*  647 */       _matchToken("null", 1);
/*  648 */       localObject = JsonToken.VALUE_NULL;
/*  649 */       break;
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
/*  666 */       localObject = parseNumberText(i);
/*  667 */       break;
/*      */     default: 
/*  669 */       localObject = _handleUnexpectedValue(i);
/*      */     }
/*      */     
/*      */     
/*  673 */     if (bool) {
/*  674 */       this._nextToken = ((JsonToken)localObject);
/*  675 */       return this._currToken;
/*      */     }
/*  677 */     this._currToken = ((JsonToken)localObject);
/*  678 */     return (JsonToken)localObject;
/*      */   }
/*      */   
/*      */   private JsonToken _nextAfterName()
/*      */   {
/*  683 */     this._nameCopied = false;
/*  684 */     JsonToken localJsonToken = this._nextToken;
/*  685 */     this._nextToken = null;
/*      */     
/*  687 */     if (localJsonToken == JsonToken.START_ARRAY) {
/*  688 */       this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  689 */     } else if (localJsonToken == JsonToken.START_OBJECT) {
/*  690 */       this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */     }
/*  692 */     return this._currToken = localJsonToken;
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
/*      */   public String nextTextValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  706 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  707 */       this._nameCopied = false;
/*  708 */       JsonToken localJsonToken = this._nextToken;
/*  709 */       this._nextToken = null;
/*  710 */       this._currToken = localJsonToken;
/*  711 */       if (localJsonToken == JsonToken.VALUE_STRING) {
/*  712 */         if (this._tokenIncomplete) {
/*  713 */           this._tokenIncomplete = false;
/*  714 */           _finishString();
/*      */         }
/*  716 */         return this._textBuffer.contentsAsString();
/*      */       }
/*  718 */       if (localJsonToken == JsonToken.START_ARRAY) {
/*  719 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  720 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/*  721 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/*  723 */       return null;
/*      */     }
/*      */     
/*  726 */     return nextToken() == JsonToken.VALUE_STRING ? getText() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int nextIntValue(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  734 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  735 */       this._nameCopied = false;
/*  736 */       JsonToken localJsonToken = this._nextToken;
/*  737 */       this._nextToken = null;
/*  738 */       this._currToken = localJsonToken;
/*  739 */       if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/*  740 */         return getIntValue();
/*      */       }
/*  742 */       if (localJsonToken == JsonToken.START_ARRAY) {
/*  743 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  744 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/*  745 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/*  747 */       return paramInt;
/*      */     }
/*      */     
/*  750 */     return nextToken() == JsonToken.VALUE_NUMBER_INT ? getIntValue() : paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public long nextLongValue(long paramLong)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  758 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  759 */       this._nameCopied = false;
/*  760 */       JsonToken localJsonToken = this._nextToken;
/*  761 */       this._nextToken = null;
/*  762 */       this._currToken = localJsonToken;
/*  763 */       if (localJsonToken == JsonToken.VALUE_NUMBER_INT) {
/*  764 */         return getLongValue();
/*      */       }
/*  766 */       if (localJsonToken == JsonToken.START_ARRAY) {
/*  767 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  768 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/*  769 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/*  771 */       return paramLong;
/*      */     }
/*      */     
/*  774 */     return nextToken() == JsonToken.VALUE_NUMBER_INT ? getLongValue() : paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Boolean nextBooleanValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/*  782 */     if (this._currToken == JsonToken.FIELD_NAME) {
/*  783 */       this._nameCopied = false;
/*  784 */       JsonToken localJsonToken = this._nextToken;
/*  785 */       this._nextToken = null;
/*  786 */       this._currToken = localJsonToken;
/*  787 */       if (localJsonToken == JsonToken.VALUE_TRUE) {
/*  788 */         return Boolean.TRUE;
/*      */       }
/*  790 */       if (localJsonToken == JsonToken.VALUE_FALSE) {
/*  791 */         return Boolean.FALSE;
/*      */       }
/*  793 */       if (localJsonToken == JsonToken.START_ARRAY) {
/*  794 */         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
/*  795 */       } else if (localJsonToken == JsonToken.START_OBJECT) {
/*  796 */         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
/*      */       }
/*  798 */       return null;
/*      */     }
/*  800 */     switch (nextToken()) {
/*      */     case VALUE_TRUE: 
/*  802 */       return Boolean.TRUE;
/*      */     case VALUE_FALSE: 
/*  804 */       return Boolean.FALSE;
/*      */     }
/*  806 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  813 */     super.close();
/*  814 */     this._symbols.release();
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
/*      */   protected JsonToken parseNumberText(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  847 */     boolean bool = paramInt == 45;
/*  848 */     int i = this._inputPtr;
/*  849 */     int j = i - 1;
/*  850 */     int k = this._inputEnd;
/*      */     
/*      */ 
/*      */ 
/*  854 */     if (bool) {
/*  855 */       if (i >= this._inputEnd) {
/*      */         break label349;
/*      */       }
/*  858 */       paramInt = this._inputBuffer[(i++)];
/*      */       
/*  860 */       if ((paramInt > 57) || (paramInt < 48)) {
/*  861 */         this._inputPtr = i;
/*  862 */         return _handleInvalidNumberStart(paramInt, true);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  870 */     if (paramInt != 48)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  880 */       int m = 1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  886 */       while (i < this._inputEnd)
/*      */       {
/*      */ 
/*  889 */         paramInt = this._inputBuffer[(i++)];
/*  890 */         if ((paramInt >= 48) && (paramInt <= 57))
/*      */         {
/*      */ 
/*  893 */           m++;
/*      */         }
/*      */         else {
/*  896 */           int n = 0;
/*      */           
/*      */ 
/*  899 */           if (paramInt == 46)
/*      */           {
/*      */             for (;;) {
/*  902 */               if (i >= k) {
/*      */                 break label349;
/*      */               }
/*  905 */               paramInt = this._inputBuffer[(i++)];
/*  906 */               if ((paramInt < 48) || (paramInt > 57)) {
/*      */                 break;
/*      */               }
/*  909 */               n++;
/*      */             }
/*      */             
/*  912 */             if (n == 0) {
/*  913 */               reportUnexpectedNumberChar(paramInt, "Decimal point not followed by a digit");
/*      */             }
/*      */           }
/*      */           
/*  917 */           int i1 = 0;
/*  918 */           if ((paramInt == 101) || (paramInt == 69)) {
/*  919 */             if (i >= k) {
/*      */               break;
/*      */             }
/*      */             
/*  923 */             paramInt = this._inputBuffer[(i++)];
/*  924 */             if ((paramInt == 45) || (paramInt == 43)) {
/*  925 */               if (i >= k) {
/*      */                 break;
/*      */               }
/*  928 */               paramInt = this._inputBuffer[(i++)];
/*      */             }
/*  930 */             while ((paramInt <= 57) && (paramInt >= 48)) {
/*  931 */               i1++;
/*  932 */               if (i >= k) {
/*      */                 break label349;
/*      */               }
/*  935 */               paramInt = this._inputBuffer[(i++)];
/*      */             }
/*      */             
/*  938 */             if (i1 == 0) {
/*  939 */               reportUnexpectedNumberChar(paramInt, "Exponent indicator not followed by a digit");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  944 */           i--;
/*  945 */           this._inputPtr = i;
/*  946 */           int i2 = i - j;
/*  947 */           this._textBuffer.resetWithShared(this._inputBuffer, j, i2);
/*  948 */           return reset(bool, m, n, i1);
/*      */         } } }
/*      */     label349:
/*  951 */     this._inputPtr = (bool ? j + 1 : j);
/*  952 */     return parseNumberText2(bool);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JsonToken parseNumberText2(boolean paramBoolean)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  965 */     char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/*  966 */     int i = 0;
/*      */     
/*      */ 
/*  969 */     if (paramBoolean) {
/*  970 */       arrayOfChar[(i++)] = '-';
/*      */     }
/*      */     
/*      */ 
/*  974 */     int j = 0;
/*  975 */     int k = this._inputPtr < this._inputEnd ? this._inputBuffer[(this._inputPtr++)] : getNextChar("No digit following minus sign");
/*  976 */     if (k == 48) {
/*  977 */       k = _verifyNoLeadingZeroes();
/*      */     }
/*  979 */     int m = 0;
/*      */     
/*      */ 
/*      */ 
/*  983 */     while ((k >= 48) && (k <= 57)) {
/*  984 */       j++;
/*  985 */       if (i >= arrayOfChar.length) {
/*  986 */         arrayOfChar = this._textBuffer.finishCurrentSegment();
/*  987 */         i = 0;
/*      */       }
/*  989 */       arrayOfChar[(i++)] = k;
/*  990 */       if ((this._inputPtr >= this._inputEnd) && (!loadMore()))
/*      */       {
/*  992 */         k = 0;
/*  993 */         m = 1;
/*  994 */         break;
/*      */       }
/*  996 */       k = this._inputBuffer[(this._inputPtr++)];
/*      */     }
/*      */     
/*  999 */     if (j == 0) {
/* 1000 */       reportInvalidNumber("Missing integer part (next char " + _getCharDesc(k) + ")");
/*      */     }
/*      */     
/* 1003 */     int n = 0;
/*      */     
/* 1005 */     if (k == 46) {
/* 1006 */       arrayOfChar[(i++)] = k;
/*      */       
/*      */       for (;;)
/*      */       {
/* 1010 */         if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1011 */           m = 1;
/* 1012 */           break;
/*      */         }
/* 1014 */         k = this._inputBuffer[(this._inputPtr++)];
/* 1015 */         if ((k < 48) || (k > 57)) {
/*      */           break;
/*      */         }
/* 1018 */         n++;
/* 1019 */         if (i >= arrayOfChar.length) {
/* 1020 */           arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1021 */           i = 0;
/*      */         }
/* 1023 */         arrayOfChar[(i++)] = k;
/*      */       }
/*      */       
/* 1026 */       if (n == 0) {
/* 1027 */         reportUnexpectedNumberChar(k, "Decimal point not followed by a digit");
/*      */       }
/*      */     }
/*      */     
/* 1031 */     int i1 = 0;
/* 1032 */     if ((k == 101) || (k == 69)) {
/* 1033 */       if (i >= arrayOfChar.length) {
/* 1034 */         arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1035 */         i = 0;
/*      */       }
/* 1037 */       arrayOfChar[(i++)] = k;
/*      */       
/* 1039 */       k = this._inputPtr < this._inputEnd ? this._inputBuffer[(this._inputPtr++)] : getNextChar("expected a digit for number exponent");
/*      */       
/*      */ 
/* 1042 */       if ((k == 45) || (k == 43)) {
/* 1043 */         if (i >= arrayOfChar.length) {
/* 1044 */           arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1045 */           i = 0;
/*      */         }
/* 1047 */         arrayOfChar[(i++)] = k;
/*      */         
/* 1049 */         k = this._inputPtr < this._inputEnd ? this._inputBuffer[(this._inputPtr++)] : getNextChar("expected a digit for number exponent");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1054 */       while ((k <= 57) && (k >= 48)) {
/* 1055 */         i1++;
/* 1056 */         if (i >= arrayOfChar.length) {
/* 1057 */           arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1058 */           i = 0;
/*      */         }
/* 1060 */         arrayOfChar[(i++)] = k;
/* 1061 */         if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1062 */           m = 1;
/* 1063 */           break;
/*      */         }
/* 1065 */         k = this._inputBuffer[(this._inputPtr++)];
/*      */       }
/*      */       
/* 1068 */       if (i1 == 0) {
/* 1069 */         reportUnexpectedNumberChar(k, "Exponent indicator not followed by a digit");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1074 */     if (m == 0) {
/* 1075 */       this._inputPtr -= 1;
/*      */     }
/* 1077 */     this._textBuffer.setCurrentLength(i);
/*      */     
/* 1079 */     return reset(paramBoolean, j, n, i1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private char _verifyNoLeadingZeroes()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1090 */     if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1091 */       return '0';
/*      */     }
/* 1093 */     char c = this._inputBuffer[this._inputPtr];
/*      */     
/* 1095 */     if ((c < '0') || (c > '9')) {
/* 1096 */       return '0';
/*      */     }
/* 1098 */     if (!isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
/* 1099 */       reportInvalidNumber("Leading zeroes not allowed");
/*      */     }
/*      */     
/* 1102 */     this._inputPtr += 1;
/* 1103 */     if (c == '0') {
/* 1104 */       while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 1105 */         c = this._inputBuffer[this._inputPtr];
/* 1106 */         if ((c < '0') || (c > '9')) {
/* 1107 */           return '0';
/*      */         }
/* 1109 */         this._inputPtr += 1;
/* 1110 */         if (c != '0') {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1115 */     return c;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonToken _handleInvalidNumberStart(int paramInt, boolean paramBoolean)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1125 */     if (paramInt == 73) {
/* 1126 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1127 */         (!loadMore())) {
/* 1128 */         _reportInvalidEOFInValue();
/*      */       }
/*      */       
/* 1131 */       paramInt = this._inputBuffer[(this._inputPtr++)];
/* 1132 */       String str; if (paramInt == 78) {
/* 1133 */         str = paramBoolean ? "-INF" : "+INF";
/* 1134 */         _matchToken(str, 3);
/* 1135 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1136 */           return resetAsNaN(str, paramBoolean ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
/*      */         }
/* 1138 */         _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/* 1139 */       } else if (paramInt == 110) {
/* 1140 */         str = paramBoolean ? "-Infinity" : "+Infinity";
/* 1141 */         _matchToken(str, 3);
/* 1142 */         if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1143 */           return resetAsNaN(str, paramBoolean ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
/*      */         }
/* 1145 */         _reportError("Non-standard token '" + str + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/*      */       }
/*      */     }
/* 1148 */     reportUnexpectedNumberChar(paramInt, "expected digit (0-9) to follow minus sign, for valid numeric value");
/* 1149 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String _parseFieldName(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1161 */     if (paramInt != 34) {
/* 1162 */       return _handleUnusualFieldName(paramInt);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1168 */     int i = this._inputPtr;
/* 1169 */     int j = this._hashSeed;
/* 1170 */     int k = this._inputEnd;
/*      */     
/* 1172 */     if (i < k) {
/* 1173 */       int[] arrayOfInt = CharTypes.getInputCodeLatin1();
/* 1174 */       int n = arrayOfInt.length;
/*      */       do
/*      */       {
/* 1177 */         int i1 = this._inputBuffer[i];
/* 1178 */         if ((i1 < n) && (arrayOfInt[i1] != 0)) {
/* 1179 */           if (i1 != 34) break;
/* 1180 */           int i2 = this._inputPtr;
/* 1181 */           this._inputPtr = (i + 1);
/* 1182 */           return this._symbols.findSymbol(this._inputBuffer, i2, i - i2, j);
/*      */         }
/*      */         
/*      */ 
/* 1186 */         j = j * 33 + i1;
/* 1187 */         i++;
/* 1188 */       } while (i < k);
/*      */     }
/*      */     
/* 1191 */     int m = this._inputPtr;
/* 1192 */     this._inputPtr = i;
/* 1193 */     return _parseFieldName2(m, j, 34);
/*      */   }
/*      */   
/*      */   private String _parseFieldName2(int paramInt1, int paramInt2, int paramInt3)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1199 */     this._textBuffer.resetWithShared(this._inputBuffer, paramInt1, this._inputPtr - paramInt1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1204 */     char[] arrayOfChar1 = this._textBuffer.getCurrentSegment();
/* 1205 */     int i = this._textBuffer.getCurrentSegmentSize();
/*      */     for (;;)
/*      */     {
/* 1208 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1209 */         (!loadMore())) {
/* 1210 */         _reportInvalidEOF(": was expecting closing '" + (char)paramInt3 + "' for name");
/*      */       }
/*      */       
/* 1213 */       int j = this._inputBuffer[(this._inputPtr++)];
/* 1214 */       int k = j;
/* 1215 */       if (k <= 92) {
/* 1216 */         if (k == 92)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1221 */           j = _decodeEscaped();
/* 1222 */         } else if (k <= paramInt3) {
/* 1223 */           if (k == paramInt3) {
/*      */             break;
/*      */           }
/* 1226 */           if (k < 32) {
/* 1227 */             _throwUnquotedSpace(k, "name");
/*      */           }
/*      */         }
/*      */       }
/* 1231 */       paramInt2 = paramInt2 * 33 + k;
/*      */       
/* 1233 */       arrayOfChar1[(i++)] = j;
/*      */       
/*      */ 
/* 1236 */       if (i >= arrayOfChar1.length) {
/* 1237 */         arrayOfChar1 = this._textBuffer.finishCurrentSegment();
/* 1238 */         i = 0;
/*      */       }
/*      */     }
/* 1241 */     this._textBuffer.setCurrentLength(i);
/*      */     
/* 1243 */     TextBuffer localTextBuffer = this._textBuffer;
/* 1244 */     char[] arrayOfChar2 = localTextBuffer.getTextBuffer();
/* 1245 */     int m = localTextBuffer.getTextOffset();
/* 1246 */     int n = localTextBuffer.size();
/*      */     
/* 1248 */     return this._symbols.findSymbol(arrayOfChar2, m, n, paramInt2);
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
/*      */   protected String _handleUnusualFieldName(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1262 */     if ((paramInt == 39) && (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES))) {
/* 1263 */       return _parseApostropheFieldName();
/*      */     }
/*      */     
/* 1266 */     if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
/* 1267 */       _reportUnexpectedChar(paramInt, "was expecting double-quote to start field name");
/*      */     }
/* 1269 */     int[] arrayOfInt = CharTypes.getInputCodeLatin1JsNames();
/* 1270 */     int i = arrayOfInt.length;
/*      */     
/*      */ 
/*      */     boolean bool;
/*      */     
/* 1275 */     if (paramInt < i) {
/* 1276 */       bool = (arrayOfInt[paramInt] == 0) && ((paramInt < 48) || (paramInt > 57));
/*      */     } else {
/* 1278 */       bool = Character.isJavaIdentifierPart((char)paramInt);
/*      */     }
/* 1280 */     if (!bool) {
/* 1281 */       _reportUnexpectedChar(paramInt, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
/*      */     }
/* 1283 */     int j = this._inputPtr;
/* 1284 */     int k = this._hashSeed;
/* 1285 */     int m = this._inputEnd;
/*      */     
/* 1287 */     if (j < m) {
/*      */       do {
/* 1289 */         n = this._inputBuffer[j];
/* 1290 */         int i1; if (n < i) {
/* 1291 */           if (arrayOfInt[n] != 0) {
/* 1292 */             i1 = this._inputPtr - 1;
/* 1293 */             this._inputPtr = j;
/* 1294 */             return this._symbols.findSymbol(this._inputBuffer, i1, j - i1, k);
/*      */           }
/* 1296 */         } else if (!Character.isJavaIdentifierPart((char)n)) {
/* 1297 */           i1 = this._inputPtr - 1;
/* 1298 */           this._inputPtr = j;
/* 1299 */           return this._symbols.findSymbol(this._inputBuffer, i1, j - i1, k);
/*      */         }
/* 1301 */         k = k * 33 + n;
/* 1302 */         j++;
/* 1303 */       } while (j < m);
/*      */     }
/* 1305 */     int n = this._inputPtr - 1;
/* 1306 */     this._inputPtr = j;
/* 1307 */     return _parseUnusualFieldName2(n, k, arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */   protected String _parseApostropheFieldName()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1314 */     int i = this._inputPtr;
/* 1315 */     int j = this._hashSeed;
/* 1316 */     int k = this._inputEnd;
/*      */     
/* 1318 */     if (i < k) {
/* 1319 */       int[] arrayOfInt = CharTypes.getInputCodeLatin1();
/* 1320 */       int n = arrayOfInt.length;
/*      */       do
/*      */       {
/* 1323 */         int i1 = this._inputBuffer[i];
/* 1324 */         if (i1 == 39) {
/* 1325 */           int i2 = this._inputPtr;
/* 1326 */           this._inputPtr = (i + 1);
/* 1327 */           return this._symbols.findSymbol(this._inputBuffer, i2, i - i2, j);
/*      */         }
/* 1329 */         if ((i1 < n) && (arrayOfInt[i1] != 0)) {
/*      */           break;
/*      */         }
/* 1332 */         j = j * 33 + i1;
/* 1333 */         i++;
/* 1334 */       } while (i < k);
/*      */     }
/*      */     
/* 1337 */     int m = this._inputPtr;
/* 1338 */     this._inputPtr = i;
/*      */     
/* 1340 */     return _parseFieldName2(m, j, 39);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonToken _handleUnexpectedValue(int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1351 */     switch (paramInt)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 39: 
/* 1360 */       if (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
/* 1361 */         return _handleApostropheValue();
/*      */       }
/*      */       break;
/*      */     case 78: 
/* 1365 */       _matchToken("NaN", 1);
/* 1366 */       if (isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
/* 1367 */         return resetAsNaN("NaN", NaN.0D);
/*      */       }
/* 1369 */       _reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
/* 1370 */       break;
/*      */     case 43: 
/* 1372 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1373 */         (!loadMore())) {
/* 1374 */         _reportInvalidEOFInValue();
/*      */       }
/*      */       
/* 1377 */       return _handleInvalidNumberStart(this._inputBuffer[(this._inputPtr++)], false); }
/*      */     
/* 1379 */     _reportUnexpectedChar(paramInt, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
/* 1380 */     return null;
/*      */   }
/*      */   
/*      */   protected JsonToken _handleApostropheValue()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1386 */     char[] arrayOfChar = this._textBuffer.emptyAndGetCurrentSegment();
/* 1387 */     int i = this._textBuffer.getCurrentSegmentSize();
/*      */     for (;;)
/*      */     {
/* 1390 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1391 */         (!loadMore())) {
/* 1392 */         _reportInvalidEOF(": was expecting closing quote for a string value");
/*      */       }
/*      */       
/* 1395 */       int j = this._inputBuffer[(this._inputPtr++)];
/* 1396 */       int k = j;
/* 1397 */       if (k <= 92) {
/* 1398 */         if (k == 92)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1403 */           j = _decodeEscaped();
/* 1404 */         } else if (k <= 39) {
/* 1405 */           if (k == 39) {
/*      */             break;
/*      */           }
/* 1408 */           if (k < 32) {
/* 1409 */             _throwUnquotedSpace(k, "string value");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1414 */       if (i >= arrayOfChar.length) {
/* 1415 */         arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1416 */         i = 0;
/*      */       }
/*      */       
/* 1419 */       arrayOfChar[(i++)] = j;
/*      */     }
/* 1421 */     this._textBuffer.setCurrentLength(i);
/* 1422 */     return JsonToken.VALUE_STRING;
/*      */   }
/*      */   
/*      */   private String _parseUnusualFieldName2(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1428 */     this._textBuffer.resetWithShared(this._inputBuffer, paramInt1, this._inputPtr - paramInt1);
/* 1429 */     char[] arrayOfChar1 = this._textBuffer.getCurrentSegment();
/* 1430 */     int i = this._textBuffer.getCurrentSegmentSize();
/* 1431 */     int j = paramArrayOfInt.length;
/*      */     
/*      */ 
/* 1434 */     while ((this._inputPtr < this._inputEnd) || 
/* 1435 */       (loadMore()))
/*      */     {
/*      */ 
/*      */ 
/* 1439 */       int k = this._inputBuffer[this._inputPtr];
/* 1440 */       int m = k;
/* 1441 */       if (m <= j ? 
/* 1442 */         paramArrayOfInt[m] != 0 : 
/*      */         
/*      */ 
/* 1445 */         !Character.isJavaIdentifierPart(k)) {
/*      */         break;
/*      */       }
/* 1448 */       this._inputPtr += 1;
/* 1449 */       paramInt2 = paramInt2 * 33 + m;
/*      */       
/* 1451 */       arrayOfChar1[(i++)] = k;
/*      */       
/*      */ 
/* 1454 */       if (i >= arrayOfChar1.length) {
/* 1455 */         arrayOfChar1 = this._textBuffer.finishCurrentSegment();
/* 1456 */         i = 0;
/*      */       }
/*      */     }
/* 1459 */     this._textBuffer.setCurrentLength(i);
/*      */     
/* 1461 */     TextBuffer localTextBuffer = this._textBuffer;
/* 1462 */     char[] arrayOfChar2 = localTextBuffer.getTextBuffer();
/* 1463 */     int n = localTextBuffer.getTextOffset();
/* 1464 */     int i1 = localTextBuffer.size();
/*      */     
/* 1466 */     return this._symbols.findSymbol(arrayOfChar2, n, i1, paramInt2);
/*      */   }
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
/* 1478 */     int i = this._inputPtr;
/* 1479 */     int j = this._inputEnd;
/*      */     
/* 1481 */     if (i < j) {
/* 1482 */       int[] arrayOfInt = CharTypes.getInputCodeLatin1();
/* 1483 */       int k = arrayOfInt.length;
/*      */       do
/*      */       {
/* 1486 */         int m = this._inputBuffer[i];
/* 1487 */         if ((m < k) && (arrayOfInt[m] != 0)) {
/* 1488 */           if (m != 34) break;
/* 1489 */           this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, i - this._inputPtr);
/* 1490 */           this._inputPtr = (i + 1);
/*      */           
/* 1492 */           return;
/*      */         }
/*      */         
/*      */ 
/* 1496 */         i++;
/* 1497 */       } while (i < j);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1503 */     this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, i - this._inputPtr);
/* 1504 */     this._inputPtr = i;
/* 1505 */     _finishString2();
/*      */   }
/*      */   
/*      */   protected void _finishString2()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1511 */     char[] arrayOfChar = this._textBuffer.getCurrentSegment();
/* 1512 */     int i = this._textBuffer.getCurrentSegmentSize();
/*      */     for (;;)
/*      */     {
/* 1515 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1516 */         (!loadMore())) {
/* 1517 */         _reportInvalidEOF(": was expecting closing quote for a string value");
/*      */       }
/*      */       
/* 1520 */       int j = this._inputBuffer[(this._inputPtr++)];
/* 1521 */       int k = j;
/* 1522 */       if (k <= 92) {
/* 1523 */         if (k == 92)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1528 */           j = _decodeEscaped();
/* 1529 */         } else if (k <= 34) {
/* 1530 */           if (k == 34) {
/*      */             break;
/*      */           }
/* 1533 */           if (k < 32) {
/* 1534 */             _throwUnquotedSpace(k, "string value");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1539 */       if (i >= arrayOfChar.length) {
/* 1540 */         arrayOfChar = this._textBuffer.finishCurrentSegment();
/* 1541 */         i = 0;
/*      */       }
/*      */       
/* 1544 */       arrayOfChar[(i++)] = j;
/*      */     }
/* 1546 */     this._textBuffer.setCurrentLength(i);
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
/* 1557 */     this._tokenIncomplete = false;
/*      */     
/* 1559 */     int i = this._inputPtr;
/* 1560 */     int j = this._inputEnd;
/* 1561 */     char[] arrayOfChar = this._inputBuffer;
/*      */     for (;;)
/*      */     {
/* 1564 */       if (i >= j) {
/* 1565 */         this._inputPtr = i;
/* 1566 */         if (!loadMore()) {
/* 1567 */           _reportInvalidEOF(": was expecting closing quote for a string value");
/*      */         }
/* 1569 */         i = this._inputPtr;
/* 1570 */         j = this._inputEnd;
/*      */       }
/* 1572 */       int k = arrayOfChar[(i++)];
/* 1573 */       int m = k;
/* 1574 */       if (m <= 92) {
/* 1575 */         if (m == 92)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1580 */           this._inputPtr = i;
/* 1581 */           k = _decodeEscaped();
/* 1582 */           i = this._inputPtr;
/* 1583 */           j = this._inputEnd;
/* 1584 */         } else if (m <= 34) {
/* 1585 */           if (m == 34) {
/* 1586 */             this._inputPtr = i;
/* 1587 */             break;
/*      */           }
/* 1589 */           if (m < 32) {
/* 1590 */             this._inputPtr = i;
/* 1591 */             _throwUnquotedSpace(m, "string value");
/*      */           }
/*      */         }
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
/*      */ 
/*      */ 
/*      */   protected void _skipCR()
/*      */     throws IOException
/*      */   {
/* 1610 */     if (((this._inputPtr < this._inputEnd) || (loadMore())) && 
/* 1611 */       (this._inputBuffer[this._inputPtr] == '\n')) {
/* 1612 */       this._inputPtr += 1;
/*      */     }
/*      */     
/* 1615 */     this._currInputRow += 1;
/* 1616 */     this._currInputRowStart = this._inputPtr;
/*      */   }
/*      */   
/*      */   protected void _skipLF() throws IOException
/*      */   {
/* 1621 */     this._currInputRow += 1;
/* 1622 */     this._currInputRowStart = this._inputPtr;
/*      */   }
/*      */   
/*      */   private int _skipWS()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1628 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 1629 */       int i = this._inputBuffer[(this._inputPtr++)];
/* 1630 */       if (i > 32) {
/* 1631 */         if (i != 47) {
/* 1632 */           return i;
/*      */         }
/* 1634 */         _skipComment();
/* 1635 */       } else if (i != 32) {
/* 1636 */         if (i == 10) {
/* 1637 */           _skipLF();
/* 1638 */         } else if (i == 13) {
/* 1639 */           _skipCR();
/* 1640 */         } else if (i != 9) {
/* 1641 */           _throwInvalidSpace(i);
/*      */         }
/*      */       }
/*      */     }
/* 1645 */     throw _constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
/*      */   }
/*      */   
/*      */   private int _skipWSOrEnd()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1651 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 1652 */       int i = this._inputBuffer[(this._inputPtr++)];
/* 1653 */       if (i > 32) {
/* 1654 */         if (i == 47) {
/* 1655 */           _skipComment();
/*      */         }
/*      */         else {
/* 1658 */           return i;
/*      */         }
/* 1660 */       } else if (i != 32) {
/* 1661 */         if (i == 10) {
/* 1662 */           _skipLF();
/* 1663 */         } else if (i == 13) {
/* 1664 */           _skipCR();
/* 1665 */         } else if (i != 9) {
/* 1666 */           _throwInvalidSpace(i);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1671 */     _handleEOF();
/* 1672 */     return -1;
/*      */   }
/*      */   
/*      */   private void _skipComment()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1678 */     if (!isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
/* 1679 */       _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
/*      */     }
/*      */     
/* 1682 */     if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/* 1683 */       _reportInvalidEOF(" in a comment");
/*      */     }
/* 1685 */     int i = this._inputBuffer[(this._inputPtr++)];
/* 1686 */     if (i == 47) {
/* 1687 */       _skipCppComment();
/* 1688 */     } else if (i == 42) {
/* 1689 */       _skipCComment();
/*      */     } else {
/* 1691 */       _reportUnexpectedChar(i, "was expecting either '*' or '/' for a comment");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void _skipCComment()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1700 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 1701 */       int i = this._inputBuffer[(this._inputPtr++)];
/* 1702 */       if (i <= 42) {
/* 1703 */         if (i == 42) {
/* 1704 */           if ((this._inputPtr >= this._inputEnd) && (!loadMore())) {
/*      */             break;
/*      */           }
/* 1707 */           if (this._inputBuffer[this._inputPtr] == '/') {
/* 1708 */             this._inputPtr += 1;
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1713 */         else if (i < 32) {
/* 1714 */           if (i == 10) {
/* 1715 */             _skipLF();
/* 1716 */           } else if (i == 13) {
/* 1717 */             _skipCR();
/* 1718 */           } else if (i != 9) {
/* 1719 */             _throwInvalidSpace(i);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1724 */     _reportInvalidEOF(" in a comment");
/*      */   }
/*      */   
/*      */ 
/*      */   private void _skipCppComment()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1731 */     while ((this._inputPtr < this._inputEnd) || (loadMore())) {
/* 1732 */       int i = this._inputBuffer[(this._inputPtr++)];
/* 1733 */       if (i < 32) {
/* 1734 */         if (i == 10) {
/* 1735 */           _skipLF();
/* 1736 */           break; }
/* 1737 */         if (i == 13) {
/* 1738 */           _skipCR();
/* 1739 */           break; }
/* 1740 */         if (i != 9) {
/* 1741 */           _throwInvalidSpace(i);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected char _decodeEscaped()
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1751 */     if ((this._inputPtr >= this._inputEnd) && 
/* 1752 */       (!loadMore())) {
/* 1753 */       _reportInvalidEOF(" in character escape sequence");
/*      */     }
/*      */     
/* 1756 */     char c = this._inputBuffer[(this._inputPtr++)];
/*      */     
/* 1758 */     switch (c)
/*      */     {
/*      */     case 'b': 
/* 1761 */       return '\b';
/*      */     case 't': 
/* 1763 */       return '\t';
/*      */     case 'n': 
/* 1765 */       return '\n';
/*      */     case 'f': 
/* 1767 */       return '\f';
/*      */     case 'r': 
/* 1769 */       return '\r';
/*      */     
/*      */ 
/*      */     case '"': 
/*      */     case '/': 
/*      */     case '\\': 
/* 1775 */       return c;
/*      */     
/*      */     case 'u': 
/*      */       break;
/*      */     
/*      */     default: 
/* 1781 */       return _handleUnrecognizedCharacterEscape(c);
/*      */     }
/*      */     
/*      */     
/* 1785 */     int i = 0;
/* 1786 */     for (int j = 0; j < 4; j++) {
/* 1787 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1788 */         (!loadMore())) {
/* 1789 */         _reportInvalidEOF(" in character escape sequence");
/*      */       }
/*      */       
/* 1792 */       int k = this._inputBuffer[(this._inputPtr++)];
/* 1793 */       int m = CharTypes.charToHex(k);
/* 1794 */       if (m < 0) {
/* 1795 */         _reportUnexpectedChar(k, "expected a hex-digit for character escape sequence");
/*      */       }
/* 1797 */       i = i << 4 | m;
/*      */     }
/* 1799 */     return (char)i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _matchToken(String paramString, int paramInt)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1808 */     int i = paramString.length();
/*      */     do
/*      */     {
/* 1811 */       if ((this._inputPtr >= this._inputEnd) && 
/* 1812 */         (!loadMore())) {
/* 1813 */         _reportInvalidToken(paramString.substring(0, paramInt));
/*      */       }
/*      */       
/* 1816 */       if (this._inputBuffer[this._inputPtr] != paramString.charAt(paramInt)) {
/* 1817 */         _reportInvalidToken(paramString.substring(0, paramInt));
/*      */       }
/* 1819 */       this._inputPtr += 1;
/* 1820 */       paramInt++; } while (paramInt < i);
/*      */     
/*      */ 
/* 1823 */     if ((this._inputPtr >= this._inputEnd) && 
/* 1824 */       (!loadMore())) {
/* 1825 */       return;
/*      */     }
/*      */     
/* 1828 */     char c = this._inputBuffer[this._inputPtr];
/* 1829 */     if ((c < '0') || (c == ']') || (c == '}')) {
/* 1830 */       return;
/*      */     }
/*      */     
/* 1833 */     if (Character.isJavaIdentifierPart(c)) {
/* 1834 */       _reportInvalidToken(paramString.substring(0, paramInt));
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
/*      */ 
/*      */ 
/*      */   protected byte[] _decodeBase64(Base64Variant paramBase64Variant)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1852 */     ByteArrayBuilder localByteArrayBuilder = _getByteArrayBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/* 1859 */       if (this._inputPtr >= this._inputEnd) {
/* 1860 */         loadMoreGuaranteed();
/*      */       }
/* 1862 */       char c = this._inputBuffer[(this._inputPtr++)];
/* 1863 */       if (c > ' ') {
/* 1864 */         int i = paramBase64Variant.decodeBase64Char(c);
/* 1865 */         if (i < 0) {
/* 1866 */           if (c == '"') {
/* 1867 */             return localByteArrayBuilder.toByteArray();
/*      */           }
/* 1869 */           i = _decodeBase64Escape(paramBase64Variant, c, 0);
/* 1870 */           if (i < 0) {}
/*      */         }
/*      */         else
/*      */         {
/* 1874 */           int j = i;
/*      */           
/*      */ 
/*      */ 
/* 1878 */           if (this._inputPtr >= this._inputEnd) {
/* 1879 */             loadMoreGuaranteed();
/*      */           }
/* 1881 */           c = this._inputBuffer[(this._inputPtr++)];
/* 1882 */           i = paramBase64Variant.decodeBase64Char(c);
/* 1883 */           if (i < 0) {
/* 1884 */             i = _decodeBase64Escape(paramBase64Variant, c, 1);
/*      */           }
/* 1886 */           j = j << 6 | i;
/*      */           
/*      */ 
/* 1889 */           if (this._inputPtr >= this._inputEnd) {
/* 1890 */             loadMoreGuaranteed();
/*      */           }
/* 1892 */           c = this._inputBuffer[(this._inputPtr++)];
/* 1893 */           i = paramBase64Variant.decodeBase64Char(c);
/*      */           
/*      */ 
/* 1896 */           if (i < 0) {
/* 1897 */             if (i != -2)
/*      */             {
/* 1899 */               if ((c == '"') && (!paramBase64Variant.usesPadding())) {
/* 1900 */                 j >>= 4;
/* 1901 */                 localByteArrayBuilder.append(j);
/* 1902 */                 return localByteArrayBuilder.toByteArray();
/*      */               }
/* 1904 */               i = _decodeBase64Escape(paramBase64Variant, c, 2);
/*      */             }
/* 1906 */             if (i == -2)
/*      */             {
/* 1908 */               if (this._inputPtr >= this._inputEnd) {
/* 1909 */                 loadMoreGuaranteed();
/*      */               }
/* 1911 */               c = this._inputBuffer[(this._inputPtr++)];
/* 1912 */               if (!paramBase64Variant.usesPaddingChar(c)) {
/* 1913 */                 throw reportInvalidBase64Char(paramBase64Variant, c, 3, "expected padding character '" + paramBase64Variant.getPaddingChar() + "'");
/*      */               }
/*      */               
/* 1916 */               j >>= 4;
/* 1917 */               localByteArrayBuilder.append(j);
/* 1918 */               continue;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1923 */           j = j << 6 | i;
/*      */           
/* 1925 */           if (this._inputPtr >= this._inputEnd) {
/* 1926 */             loadMoreGuaranteed();
/*      */           }
/* 1928 */           c = this._inputBuffer[(this._inputPtr++)];
/* 1929 */           i = paramBase64Variant.decodeBase64Char(c);
/* 1930 */           if (i < 0) {
/* 1931 */             if (i != -2)
/*      */             {
/* 1933 */               if ((c == '"') && (!paramBase64Variant.usesPadding())) {
/* 1934 */                 j >>= 2;
/* 1935 */                 localByteArrayBuilder.appendTwoBytes(j);
/* 1936 */                 return localByteArrayBuilder.toByteArray();
/*      */               }
/* 1938 */               i = _decodeBase64Escape(paramBase64Variant, c, 3);
/*      */             }
/* 1940 */             if (i == -2)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1946 */               j >>= 2;
/* 1947 */               localByteArrayBuilder.appendTwoBytes(j);
/* 1948 */               continue;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1953 */           j = j << 6 | i;
/* 1954 */           localByteArrayBuilder.appendThreeBytes(j);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _reportInvalidToken(String paramString)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1966 */     _reportInvalidToken(paramString, "'null', 'true', 'false' or NaN");
/*      */   }
/*      */   
/*      */   protected void _reportInvalidToken(String paramString1, String paramString2)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1972 */     StringBuilder localStringBuilder = new StringBuilder(paramString1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1978 */     while ((this._inputPtr < this._inputEnd) || 
/* 1979 */       (loadMore()))
/*      */     {
/*      */ 
/*      */ 
/* 1983 */       char c = this._inputBuffer[this._inputPtr];
/* 1984 */       if (!Character.isJavaIdentifierPart(c)) {
/*      */         break;
/*      */       }
/* 1987 */       this._inputPtr += 1;
/* 1988 */       localStringBuilder.append(c);
/*      */     }
/* 1990 */     _reportError("Unrecognized token '" + localStringBuilder.toString() + "': was expecting ");
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/ReaderBasedJsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */