/*      */ package com.shaded.fasterxml.jackson.core.json;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerator.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.PrettyPrinter;
/*      */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*      */ import com.shaded.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*      */ import com.shaded.fasterxml.jackson.core.io.NumberOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Writer;
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ public final class WriterBasedJsonGenerator extends JsonGeneratorImpl
/*      */ {
/*      */   protected static final int SHORT_WRITE = 32;
/*   19 */   protected static final char[] HEX_CHARS = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final Writer _writer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected char[] _outputBuffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   38 */   protected int _outputHead = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   44 */   protected int _outputTail = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int _outputEnd;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected char[] _entityBuffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected SerializableString _currentEscape;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public WriterBasedJsonGenerator(IOContext paramIOContext, int paramInt, com.shaded.fasterxml.jackson.core.ObjectCodec paramObjectCodec, Writer paramWriter)
/*      */   {
/*   74 */     super(paramIOContext, paramInt, paramObjectCodec);
/*   75 */     this._writer = paramWriter;
/*   76 */     this._outputBuffer = paramIOContext.allocConcatBuffer();
/*   77 */     this._outputEnd = this._outputBuffer.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getOutputTarget()
/*      */   {
/*   88 */     return this._writer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeFieldName(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  100 */     int i = this._writeContext.writeFieldName(paramString);
/*  101 */     if (i == 4) {
/*  102 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  104 */     _writeFieldName(paramString, i == 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeFieldName(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  112 */     int i = this._writeContext.writeFieldName(paramSerializableString.getValue());
/*  113 */     if (i == 4) {
/*  114 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  116 */     _writeFieldName(paramSerializableString, i == 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeStartArray()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  128 */     _verifyValueWrite("start an array");
/*  129 */     this._writeContext = this._writeContext.createChildArrayContext();
/*  130 */     if (this._cfgPrettyPrinter != null) {
/*  131 */       this._cfgPrettyPrinter.writeStartArray(this);
/*      */     } else {
/*  133 */       if (this._outputTail >= this._outputEnd) {
/*  134 */         _flushBuffer();
/*      */       }
/*  136 */       this._outputBuffer[(this._outputTail++)] = '[';
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeEndArray()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  143 */     if (!this._writeContext.inArray()) {
/*  144 */       _reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
/*      */     }
/*  146 */     if (this._cfgPrettyPrinter != null) {
/*  147 */       this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
/*      */     } else {
/*  149 */       if (this._outputTail >= this._outputEnd) {
/*  150 */         _flushBuffer();
/*      */       }
/*  152 */       this._outputBuffer[(this._outputTail++)] = ']';
/*      */     }
/*  154 */     this._writeContext = this._writeContext.getParent();
/*      */   }
/*      */   
/*      */   public void writeStartObject()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  160 */     _verifyValueWrite("start an object");
/*  161 */     this._writeContext = this._writeContext.createChildObjectContext();
/*  162 */     if (this._cfgPrettyPrinter != null) {
/*  163 */       this._cfgPrettyPrinter.writeStartObject(this);
/*      */     } else {
/*  165 */       if (this._outputTail >= this._outputEnd) {
/*  166 */         _flushBuffer();
/*      */       }
/*  168 */       this._outputBuffer[(this._outputTail++)] = '{';
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeEndObject()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  175 */     if (!this._writeContext.inObject()) {
/*  176 */       _reportError("Current context not an object but " + this._writeContext.getTypeDesc());
/*      */     }
/*  178 */     if (this._cfgPrettyPrinter != null) {
/*  179 */       this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
/*      */     } else {
/*  181 */       if (this._outputTail >= this._outputEnd) {
/*  182 */         _flushBuffer();
/*      */       }
/*  184 */       this._outputBuffer[(this._outputTail++)] = '}';
/*      */     }
/*  186 */     this._writeContext = this._writeContext.getParent();
/*      */   }
/*      */   
/*      */   protected void _writeFieldName(String paramString, boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  192 */     if (this._cfgPrettyPrinter != null) {
/*  193 */       _writePPFieldName(paramString, paramBoolean);
/*  194 */       return;
/*      */     }
/*      */     
/*  197 */     if (this._outputTail + 1 >= this._outputEnd) {
/*  198 */       _flushBuffer();
/*      */     }
/*  200 */     if (paramBoolean) {
/*  201 */       this._outputBuffer[(this._outputTail++)] = ',';
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  207 */     if (!isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/*  208 */       _writeString(paramString);
/*  209 */       return;
/*      */     }
/*      */     
/*      */ 
/*  213 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */     
/*  215 */     _writeString(paramString);
/*      */     
/*  217 */     if (this._outputTail >= this._outputEnd) {
/*  218 */       _flushBuffer();
/*      */     }
/*  220 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */   public void _writeFieldName(SerializableString paramSerializableString, boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  226 */     if (this._cfgPrettyPrinter != null) {
/*  227 */       _writePPFieldName(paramSerializableString, paramBoolean);
/*  228 */       return;
/*      */     }
/*      */     
/*  231 */     if (this._outputTail + 1 >= this._outputEnd) {
/*  232 */       _flushBuffer();
/*      */     }
/*  234 */     if (paramBoolean) {
/*  235 */       this._outputBuffer[(this._outputTail++)] = ',';
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  240 */     char[] arrayOfChar = paramSerializableString.asQuotedChars();
/*  241 */     if (!isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/*  242 */       writeRaw(arrayOfChar, 0, arrayOfChar.length);
/*  243 */       return;
/*      */     }
/*      */     
/*  246 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */     
/*  248 */     int i = arrayOfChar.length;
/*  249 */     if (this._outputTail + i + 1 >= this._outputEnd) {
/*  250 */       writeRaw(arrayOfChar, 0, i);
/*      */       
/*  252 */       if (this._outputTail >= this._outputEnd) {
/*  253 */         _flushBuffer();
/*      */       }
/*  255 */       this._outputBuffer[(this._outputTail++)] = '"';
/*      */     } else {
/*  257 */       System.arraycopy(arrayOfChar, 0, this._outputBuffer, this._outputTail, i);
/*  258 */       this._outputTail += i;
/*  259 */       this._outputBuffer[(this._outputTail++)] = '"';
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _writePPFieldName(String paramString, boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  270 */     if (paramBoolean) {
/*  271 */       this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
/*      */     } else {
/*  273 */       this._cfgPrettyPrinter.beforeObjectEntries(this);
/*      */     }
/*      */     
/*  276 */     if (isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/*  277 */       if (this._outputTail >= this._outputEnd) {
/*  278 */         _flushBuffer();
/*      */       }
/*  280 */       this._outputBuffer[(this._outputTail++)] = '"';
/*  281 */       _writeString(paramString);
/*  282 */       if (this._outputTail >= this._outputEnd) {
/*  283 */         _flushBuffer();
/*      */       }
/*  285 */       this._outputBuffer[(this._outputTail++)] = '"';
/*      */     } else {
/*  287 */       _writeString(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void _writePPFieldName(SerializableString paramSerializableString, boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  294 */     if (paramBoolean) {
/*  295 */       this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
/*      */     } else {
/*  297 */       this._cfgPrettyPrinter.beforeObjectEntries(this);
/*      */     }
/*      */     
/*  300 */     char[] arrayOfChar = paramSerializableString.asQuotedChars();
/*  301 */     if (isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/*  302 */       if (this._outputTail >= this._outputEnd) {
/*  303 */         _flushBuffer();
/*      */       }
/*  305 */       this._outputBuffer[(this._outputTail++)] = '"';
/*  306 */       writeRaw(arrayOfChar, 0, arrayOfChar.length);
/*  307 */       if (this._outputTail >= this._outputEnd) {
/*  308 */         _flushBuffer();
/*      */       }
/*  310 */       this._outputBuffer[(this._outputTail++)] = '"';
/*      */     } else {
/*  312 */       writeRaw(arrayOfChar, 0, arrayOfChar.length);
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
/*      */   public void writeString(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  326 */     _verifyValueWrite("write text value");
/*  327 */     if (paramString == null) {
/*  328 */       _writeNull();
/*  329 */       return;
/*      */     }
/*  331 */     if (this._outputTail >= this._outputEnd) {
/*  332 */       _flushBuffer();
/*      */     }
/*  334 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  335 */     _writeString(paramString);
/*      */     
/*  337 */     if (this._outputTail >= this._outputEnd) {
/*  338 */       _flushBuffer();
/*      */     }
/*  340 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeString(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  347 */     _verifyValueWrite("write text value");
/*  348 */     if (this._outputTail >= this._outputEnd) {
/*  349 */       _flushBuffer();
/*      */     }
/*  351 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  352 */     _writeString(paramArrayOfChar, paramInt1, paramInt2);
/*      */     
/*  354 */     if (this._outputTail >= this._outputEnd) {
/*  355 */       _flushBuffer();
/*      */     }
/*  357 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeString(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  364 */     _verifyValueWrite("write text value");
/*  365 */     if (this._outputTail >= this._outputEnd) {
/*  366 */       _flushBuffer();
/*      */     }
/*  368 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */     
/*  370 */     char[] arrayOfChar = paramSerializableString.asQuotedChars();
/*  371 */     int i = arrayOfChar.length;
/*      */     
/*  373 */     if (i < 32) {
/*  374 */       int j = this._outputEnd - this._outputTail;
/*  375 */       if (i > j) {
/*  376 */         _flushBuffer();
/*      */       }
/*  378 */       System.arraycopy(arrayOfChar, 0, this._outputBuffer, this._outputTail, i);
/*  379 */       this._outputTail += i;
/*      */     }
/*      */     else {
/*  382 */       _flushBuffer();
/*  383 */       this._writer.write(arrayOfChar, 0, i);
/*      */     }
/*  385 */     if (this._outputTail >= this._outputEnd) {
/*  386 */       _flushBuffer();
/*      */     }
/*  388 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeRawUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  396 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  404 */     _reportUnsupportedOperation();
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
/*      */   public void writeRaw(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  418 */     int i = paramString.length();
/*  419 */     int j = this._outputEnd - this._outputTail;
/*      */     
/*  421 */     if (j == 0) {
/*  422 */       _flushBuffer();
/*  423 */       j = this._outputEnd - this._outputTail;
/*      */     }
/*      */     
/*  426 */     if (j >= i) {
/*  427 */       paramString.getChars(0, i, this._outputBuffer, this._outputTail);
/*  428 */       this._outputTail += i;
/*      */     } else {
/*  430 */       writeRawLong(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeRaw(String paramString, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  439 */     int i = this._outputEnd - this._outputTail;
/*      */     
/*  441 */     if (i < paramInt2) {
/*  442 */       _flushBuffer();
/*  443 */       i = this._outputEnd - this._outputTail;
/*      */     }
/*      */     
/*  446 */     if (i >= paramInt2) {
/*  447 */       paramString.getChars(paramInt1, paramInt1 + paramInt2, this._outputBuffer, this._outputTail);
/*  448 */       this._outputTail += paramInt2;
/*      */     } else {
/*  450 */       writeRawLong(paramString.substring(paramInt1, paramInt1 + paramInt2));
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeRaw(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  457 */     writeRaw(paramSerializableString.getValue());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeRaw(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  465 */     if (paramInt2 < 32) {
/*  466 */       int i = this._outputEnd - this._outputTail;
/*  467 */       if (paramInt2 > i) {
/*  468 */         _flushBuffer();
/*      */       }
/*  470 */       System.arraycopy(paramArrayOfChar, paramInt1, this._outputBuffer, this._outputTail, paramInt2);
/*  471 */       this._outputTail += paramInt2;
/*  472 */       return;
/*      */     }
/*      */     
/*  475 */     _flushBuffer();
/*  476 */     this._writer.write(paramArrayOfChar, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeRaw(char paramChar)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  483 */     if (this._outputTail >= this._outputEnd) {
/*  484 */       _flushBuffer();
/*      */     }
/*  486 */     this._outputBuffer[(this._outputTail++)] = paramChar;
/*      */   }
/*      */   
/*      */   private void writeRawLong(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  492 */     int i = this._outputEnd - this._outputTail;
/*      */     
/*  494 */     paramString.getChars(0, i, this._outputBuffer, this._outputTail);
/*  495 */     this._outputTail += i;
/*  496 */     _flushBuffer();
/*  497 */     int j = i;
/*  498 */     int k = paramString.length() - i;
/*      */     
/*  500 */     while (k > this._outputEnd) {
/*  501 */       int m = this._outputEnd;
/*  502 */       paramString.getChars(j, j + m, this._outputBuffer, 0);
/*  503 */       this._outputHead = 0;
/*  504 */       this._outputTail = m;
/*  505 */       _flushBuffer();
/*  506 */       j += m;
/*  507 */       k -= m;
/*      */     }
/*      */     
/*  510 */     paramString.getChars(j, j + k, this._outputBuffer, 0);
/*  511 */     this._outputHead = 0;
/*  512 */     this._outputTail = k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  525 */     _verifyValueWrite("write binary value");
/*      */     
/*  527 */     if (this._outputTail >= this._outputEnd) {
/*  528 */       _flushBuffer();
/*      */     }
/*  530 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  531 */     _writeBinary(paramBase64Variant, paramArrayOfByte, paramInt1, paramInt1 + paramInt2);
/*      */     
/*  533 */     if (this._outputTail >= this._outputEnd) {
/*  534 */       _flushBuffer();
/*      */     }
/*  536 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  544 */     _verifyValueWrite("write binary value");
/*      */     
/*  546 */     if (this._outputTail >= this._outputEnd) {
/*  547 */       _flushBuffer();
/*      */     }
/*  549 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  550 */     byte[] arrayOfByte = this._ioContext.allocBase64Buffer();
/*      */     int i;
/*      */     try {
/*  553 */       if (paramInt < 0) {
/*  554 */         i = _writeBinary(paramBase64Variant, paramInputStream, arrayOfByte);
/*      */       } else {
/*  556 */         int j = _writeBinary(paramBase64Variant, paramInputStream, arrayOfByte, paramInt);
/*  557 */         if (j > 0) {
/*  558 */           _reportError("Too few bytes available: missing " + j + " bytes (out of " + paramInt + ")");
/*      */         }
/*  560 */         i = paramInt;
/*      */       }
/*      */     } finally {
/*  563 */       this._ioContext.releaseBase64Buffer(arrayOfByte);
/*      */     }
/*      */     
/*  566 */     if (this._outputTail >= this._outputEnd) {
/*  567 */       _flushBuffer();
/*      */     }
/*  569 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  570 */     return i;
/*      */   }
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
/*  583 */     _verifyValueWrite("write number");
/*  584 */     if (this._cfgNumbersAsStrings) {
/*  585 */       _writeQuotedShort(paramShort);
/*  586 */       return;
/*      */     }
/*      */     
/*  589 */     if (this._outputTail + 6 >= this._outputEnd) {
/*  590 */       _flushBuffer();
/*      */     }
/*  592 */     this._outputTail = NumberOutput.outputInt(paramShort, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedShort(short paramShort) throws IOException {
/*  596 */     if (this._outputTail + 8 >= this._outputEnd) {
/*  597 */       _flushBuffer();
/*      */     }
/*  599 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  600 */     this._outputTail = NumberOutput.outputInt(paramShort, this._outputBuffer, this._outputTail);
/*  601 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  608 */     _verifyValueWrite("write number");
/*  609 */     if (this._cfgNumbersAsStrings) {
/*  610 */       _writeQuotedInt(paramInt);
/*  611 */       return;
/*      */     }
/*      */     
/*  614 */     if (this._outputTail + 11 >= this._outputEnd) {
/*  615 */       _flushBuffer();
/*      */     }
/*  617 */     this._outputTail = NumberOutput.outputInt(paramInt, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedInt(int paramInt) throws IOException {
/*  621 */     if (this._outputTail + 13 >= this._outputEnd) {
/*  622 */       _flushBuffer();
/*      */     }
/*  624 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  625 */     this._outputTail = NumberOutput.outputInt(paramInt, this._outputBuffer, this._outputTail);
/*  626 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(long paramLong)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  633 */     _verifyValueWrite("write number");
/*  634 */     if (this._cfgNumbersAsStrings) {
/*  635 */       _writeQuotedLong(paramLong);
/*  636 */       return;
/*      */     }
/*  638 */     if (this._outputTail + 21 >= this._outputEnd)
/*      */     {
/*  640 */       _flushBuffer();
/*      */     }
/*  642 */     this._outputTail = NumberOutput.outputLong(paramLong, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedLong(long paramLong) throws IOException {
/*  646 */     if (this._outputTail + 23 >= this._outputEnd) {
/*  647 */       _flushBuffer();
/*      */     }
/*  649 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  650 */     this._outputTail = NumberOutput.outputLong(paramLong, this._outputBuffer, this._outputTail);
/*  651 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeNumber(BigInteger paramBigInteger)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  660 */     _verifyValueWrite("write number");
/*  661 */     if (paramBigInteger == null) {
/*  662 */       _writeNull();
/*  663 */     } else if (this._cfgNumbersAsStrings) {
/*  664 */       _writeQuotedRaw(paramBigInteger);
/*      */     } else {
/*  666 */       writeRaw(paramBigInteger.toString());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeNumber(double paramDouble)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  675 */     if ((this._cfgNumbersAsStrings) || (((Double.isNaN(paramDouble)) || (Double.isInfinite(paramDouble))) && (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))))
/*      */     {
/*      */ 
/*      */ 
/*  679 */       writeString(String.valueOf(paramDouble));
/*  680 */       return;
/*      */     }
/*      */     
/*  683 */     _verifyValueWrite("write number");
/*  684 */     writeRaw(String.valueOf(paramDouble));
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(float paramFloat)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  691 */     if ((this._cfgNumbersAsStrings) || (((Float.isNaN(paramFloat)) || (Float.isInfinite(paramFloat))) && (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))))
/*      */     {
/*      */ 
/*      */ 
/*  695 */       writeString(String.valueOf(paramFloat));
/*  696 */       return;
/*      */     }
/*      */     
/*  699 */     _verifyValueWrite("write number");
/*  700 */     writeRaw(String.valueOf(paramFloat));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeNumber(java.math.BigDecimal paramBigDecimal)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  708 */     _verifyValueWrite("write number");
/*  709 */     if (paramBigDecimal == null) {
/*  710 */       _writeNull();
/*  711 */     } else if (this._cfgNumbersAsStrings) {
/*  712 */       _writeQuotedRaw(paramBigDecimal);
/*      */     } else {
/*  714 */       writeRaw(paramBigDecimal.toString());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  722 */     _verifyValueWrite("write number");
/*  723 */     if (this._cfgNumbersAsStrings) {
/*  724 */       _writeQuotedRaw(paramString);
/*      */     } else {
/*  726 */       writeRaw(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private void _writeQuotedRaw(Object paramObject) throws IOException
/*      */   {
/*  732 */     if (this._outputTail >= this._outputEnd) {
/*  733 */       _flushBuffer();
/*      */     }
/*  735 */     this._outputBuffer[(this._outputTail++)] = '"';
/*  736 */     writeRaw(paramObject.toString());
/*  737 */     if (this._outputTail >= this._outputEnd) {
/*  738 */       _flushBuffer();
/*      */     }
/*  740 */     this._outputBuffer[(this._outputTail++)] = '"';
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeBoolean(boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  747 */     _verifyValueWrite("write boolean value");
/*  748 */     if (this._outputTail + 5 >= this._outputEnd) {
/*  749 */       _flushBuffer();
/*      */     }
/*  751 */     int i = this._outputTail;
/*  752 */     char[] arrayOfChar = this._outputBuffer;
/*  753 */     if (paramBoolean) {
/*  754 */       arrayOfChar[i] = 't';
/*  755 */       arrayOfChar[(++i)] = 'r';
/*  756 */       arrayOfChar[(++i)] = 'u';
/*  757 */       arrayOfChar[(++i)] = 'e';
/*      */     } else {
/*  759 */       arrayOfChar[i] = 'f';
/*  760 */       arrayOfChar[(++i)] = 'a';
/*  761 */       arrayOfChar[(++i)] = 'l';
/*  762 */       arrayOfChar[(++i)] = 's';
/*  763 */       arrayOfChar[(++i)] = 'e';
/*      */     }
/*  765 */     this._outputTail = (i + 1);
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNull()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  772 */     _verifyValueWrite("write null value");
/*  773 */     _writeNull();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _verifyValueWrite(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  786 */     int i = this._writeContext.writeValue();
/*  787 */     if (i == 5) {
/*  788 */       _reportError("Can not " + paramString + ", expecting field name");
/*      */     }
/*  790 */     if (this._cfgPrettyPrinter == null) {
/*      */       int j;
/*  792 */       switch (i) {
/*      */       case 1: 
/*  794 */         j = 44;
/*  795 */         break;
/*      */       case 2: 
/*  797 */         j = 58;
/*  798 */         break;
/*      */       case 3: 
/*  800 */         if (this._rootValueSeparator != null) {
/*  801 */           writeRaw(this._rootValueSeparator.getValue());
/*      */         }
/*  803 */         return;
/*      */       case 0: 
/*      */       default: 
/*  806 */         return;
/*      */       }
/*  808 */       if (this._outputTail >= this._outputEnd) {
/*  809 */         _flushBuffer();
/*      */       }
/*  811 */       this._outputBuffer[this._outputTail] = j;
/*  812 */       this._outputTail += 1;
/*  813 */       return;
/*      */     }
/*      */     
/*  816 */     _verifyPrettyValueWrite(paramString, i);
/*      */   }
/*      */   
/*      */ 
/*      */   protected void _verifyPrettyValueWrite(String paramString, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  823 */     switch (paramInt) {
/*      */     case 1: 
/*  825 */       this._cfgPrettyPrinter.writeArrayValueSeparator(this);
/*  826 */       break;
/*      */     case 2: 
/*  828 */       this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
/*  829 */       break;
/*      */     case 3: 
/*  831 */       this._cfgPrettyPrinter.writeRootValueSeparator(this);
/*  832 */       break;
/*      */     
/*      */     case 0: 
/*  835 */       if (this._writeContext.inArray()) {
/*  836 */         this._cfgPrettyPrinter.beforeArrayValues(this);
/*  837 */       } else if (this._writeContext.inObject()) {
/*  838 */         this._cfgPrettyPrinter.beforeObjectEntries(this);
/*      */       }
/*      */       break;
/*      */     default: 
/*  842 */       _throwInternal();
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void flush()
/*      */     throws IOException
/*      */   {
/*  857 */     _flushBuffer();
/*  858 */     if ((this._writer != null) && 
/*  859 */       (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM))) {
/*  860 */       this._writer.flush();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  869 */     super.close();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  875 */     if ((this._outputBuffer != null) && (isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT))) {
/*      */       for (;;)
/*      */       {
/*  878 */         JsonWriteContext localJsonWriteContext = getOutputContext();
/*  879 */         if (localJsonWriteContext.inArray()) {
/*  880 */           writeEndArray();
/*  881 */         } else { if (!localJsonWriteContext.inObject()) break;
/*  882 */           writeEndObject();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  888 */     _flushBuffer();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  896 */     if (this._writer != null) {
/*  897 */       if ((this._ioContext.isResourceManaged()) || (isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET))) {
/*  898 */         this._writer.close();
/*  899 */       } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM))
/*      */       {
/*  901 */         this._writer.flush();
/*      */       }
/*      */     }
/*      */     
/*  905 */     _releaseBuffers();
/*      */   }
/*      */   
/*      */ 
/*      */   protected void _releaseBuffers()
/*      */   {
/*  911 */     char[] arrayOfChar = this._outputBuffer;
/*  912 */     if (arrayOfChar != null) {
/*  913 */       this._outputBuffer = null;
/*  914 */       this._ioContext.releaseConcatBuffer(arrayOfChar);
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
/*      */   private void _writeString(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  932 */     int i = paramString.length();
/*  933 */     if (i > this._outputEnd) {
/*  934 */       _writeLongString(paramString);
/*  935 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  940 */     if (this._outputTail + i > this._outputEnd) {
/*  941 */       _flushBuffer();
/*      */     }
/*  943 */     paramString.getChars(0, i, this._outputBuffer, this._outputTail);
/*      */     
/*  945 */     if (this._characterEscapes != null) {
/*  946 */       _writeStringCustom(i);
/*  947 */     } else if (this._maximumNonEscapedChar != 0) {
/*  948 */       _writeStringASCII(i, this._maximumNonEscapedChar);
/*      */     } else {
/*  950 */       _writeString2(i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void _writeString2(int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  958 */     int i = this._outputTail + paramInt;
/*  959 */     int[] arrayOfInt = this._outputEscapes;
/*  960 */     int j = arrayOfInt.length;
/*      */     
/*      */ 
/*  963 */     while (this._outputTail < i)
/*      */     {
/*      */       for (;;)
/*      */       {
/*  967 */         k = this._outputBuffer[this._outputTail];
/*  968 */         if ((k < j) && (arrayOfInt[k] != 0)) {
/*      */           break;
/*      */         }
/*  971 */         if (++this._outputTail >= i) {
/*      */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  980 */       int k = this._outputTail - this._outputHead;
/*  981 */       if (k > 0) {
/*  982 */         this._writer.write(this._outputBuffer, this._outputHead, k);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  987 */       char c = this._outputBuffer[(this._outputTail++)];
/*  988 */       _prependOrWriteCharacterEscape(c, arrayOfInt[c]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _writeLongString(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1000 */     _flushBuffer();
/*      */     
/*      */ 
/* 1003 */     int i = paramString.length();
/* 1004 */     int j = 0;
/*      */     do {
/* 1006 */       int k = this._outputEnd;
/* 1007 */       int m = j + k > i ? i - j : k;
/*      */       
/* 1009 */       paramString.getChars(j, j + m, this._outputBuffer, 0);
/* 1010 */       if (this._characterEscapes != null) {
/* 1011 */         _writeSegmentCustom(m);
/* 1012 */       } else if (this._maximumNonEscapedChar != 0) {
/* 1013 */         _writeSegmentASCII(m, this._maximumNonEscapedChar);
/*      */       } else {
/* 1015 */         _writeSegment(m);
/*      */       }
/* 1017 */       j += m;
/* 1018 */     } while (j < i);
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
/*      */   private void _writeSegment(int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1033 */     int[] arrayOfInt = this._outputEscapes;
/* 1034 */     int i = arrayOfInt.length;
/*      */     
/* 1036 */     int j = 0;
/* 1037 */     int k = j;
/*      */     
/*      */ 
/* 1040 */     while (j < paramInt)
/*      */     {
/*      */       int m;
/*      */       for (;;) {
/* 1044 */         m = this._outputBuffer[j];
/* 1045 */         if ((m >= i) || (arrayOfInt[m] == 0))
/*      */         {
/*      */ 
/* 1048 */           j++; if (j >= paramInt) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1057 */       int n = j - k;
/* 1058 */       if (n > 0) {
/* 1059 */         this._writer.write(this._outputBuffer, k, n);
/* 1060 */         if (j >= paramInt) {
/*      */           break;
/*      */         }
/*      */       }
/* 1064 */       j++;
/*      */       
/* 1066 */       k = _prependOrWriteCharacterEscape(this._outputBuffer, j, paramInt, m, arrayOfInt[m]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _writeString(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1077 */     if (this._characterEscapes != null) {
/* 1078 */       _writeStringCustom(paramArrayOfChar, paramInt1, paramInt2);
/* 1079 */       return;
/*      */     }
/* 1081 */     if (this._maximumNonEscapedChar != 0) {
/* 1082 */       _writeStringASCII(paramArrayOfChar, paramInt1, paramInt2, this._maximumNonEscapedChar);
/* 1083 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1090 */     paramInt2 += paramInt1;
/* 1091 */     int[] arrayOfInt = this._outputEscapes;
/* 1092 */     int i = arrayOfInt.length;
/* 1093 */     while (paramInt1 < paramInt2) {
/* 1094 */       int j = paramInt1;
/*      */       for (;;)
/*      */       {
/* 1097 */         k = paramArrayOfChar[paramInt1];
/* 1098 */         if ((k < i) && (arrayOfInt[k] != 0)) {
/*      */           break;
/*      */         }
/* 1101 */         paramInt1++; if (paramInt1 >= paramInt2) {
/*      */           break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1107 */       int k = paramInt1 - j;
/* 1108 */       if (k < 32)
/*      */       {
/* 1110 */         if (this._outputTail + k > this._outputEnd) {
/* 1111 */           _flushBuffer();
/*      */         }
/* 1113 */         if (k > 0) {
/* 1114 */           System.arraycopy(paramArrayOfChar, j, this._outputBuffer, this._outputTail, k);
/* 1115 */           this._outputTail += k;
/*      */         }
/*      */       } else {
/* 1118 */         _flushBuffer();
/* 1119 */         this._writer.write(paramArrayOfChar, j, k);
/*      */       }
/*      */       
/* 1122 */       if (paramInt1 >= paramInt2) {
/*      */         break;
/*      */       }
/*      */       
/* 1126 */       char c = paramArrayOfChar[(paramInt1++)];
/* 1127 */       _appendCharacterEscape(c, arrayOfInt[c]);
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
/*      */   private void _writeStringASCII(int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1145 */     int i = this._outputTail + paramInt1;
/* 1146 */     int[] arrayOfInt = this._outputEscapes;
/* 1147 */     int j = Math.min(arrayOfInt.length, paramInt2 + 1);
/* 1148 */     int k = 0;
/*      */     
/*      */ 
/* 1151 */     while (this._outputTail < i)
/*      */     {
/*      */       int m;
/*      */       do
/*      */       {
/* 1156 */         m = this._outputBuffer[this._outputTail];
/* 1157 */         if (m < j) {
/* 1158 */           k = arrayOfInt[m];
/* 1159 */           if (k != 0) {
/*      */             break;
/*      */           }
/* 1162 */         } else if (m > paramInt2) {
/* 1163 */           k = -1;
/* 1164 */           break;
/*      */         }
/* 1166 */       } while (++this._outputTail < i);
/* 1167 */       break;
/*      */       
/*      */ 
/* 1170 */       int n = this._outputTail - this._outputHead;
/* 1171 */       if (n > 0) {
/* 1172 */         this._writer.write(this._outputBuffer, this._outputHead, n);
/*      */       }
/* 1174 */       this._outputTail += 1;
/* 1175 */       _prependOrWriteCharacterEscape(m, k);
/*      */     }
/*      */   }
/*      */   
/*      */   private void _writeSegmentASCII(int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1182 */     int[] arrayOfInt = this._outputEscapes;
/* 1183 */     int i = Math.min(arrayOfInt.length, paramInt2 + 1);
/*      */     
/* 1185 */     int j = 0;
/* 1186 */     int k = 0;
/* 1187 */     int m = j;
/*      */     
/*      */ 
/* 1190 */     while (j < paramInt1)
/*      */     {
/*      */       int n;
/*      */       for (;;) {
/* 1194 */         n = this._outputBuffer[j];
/* 1195 */         if (n < i) {
/* 1196 */           k = arrayOfInt[n];
/* 1197 */           if (k != 0) {
/*      */             break;
/*      */           }
/* 1200 */         } else if (n > paramInt2) {
/* 1201 */           k = -1;
/* 1202 */           break;
/*      */         }
/* 1204 */         j++; if (j >= paramInt1) {
/*      */           break;
/*      */         }
/*      */       }
/* 1208 */       int i1 = j - m;
/* 1209 */       if (i1 > 0) {
/* 1210 */         this._writer.write(this._outputBuffer, m, i1);
/* 1211 */         if (j >= paramInt1) {
/*      */           break;
/*      */         }
/*      */       }
/* 1215 */       j++;
/* 1216 */       m = _prependOrWriteCharacterEscape(this._outputBuffer, j, paramInt1, n, k);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void _writeStringASCII(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1224 */     paramInt2 += paramInt1;
/* 1225 */     int[] arrayOfInt = this._outputEscapes;
/* 1226 */     int i = Math.min(arrayOfInt.length, paramInt3 + 1);
/*      */     
/* 1228 */     int j = 0;
/*      */     
/* 1230 */     while (paramInt1 < paramInt2) {
/* 1231 */       int k = paramInt1;
/*      */       int m;
/*      */       for (;;)
/*      */       {
/* 1235 */         m = paramArrayOfChar[paramInt1];
/* 1236 */         if (m < i) {
/* 1237 */           j = arrayOfInt[m];
/* 1238 */           if (j != 0) {
/*      */             break;
/*      */           }
/* 1241 */         } else if (m > paramInt3) {
/* 1242 */           j = -1;
/* 1243 */           break;
/*      */         }
/* 1245 */         paramInt1++; if (paramInt1 >= paramInt2) {
/*      */           break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1251 */       int n = paramInt1 - k;
/* 1252 */       if (n < 32)
/*      */       {
/* 1254 */         if (this._outputTail + n > this._outputEnd) {
/* 1255 */           _flushBuffer();
/*      */         }
/* 1257 */         if (n > 0) {
/* 1258 */           System.arraycopy(paramArrayOfChar, k, this._outputBuffer, this._outputTail, n);
/* 1259 */           this._outputTail += n;
/*      */         }
/*      */       } else {
/* 1262 */         _flushBuffer();
/* 1263 */         this._writer.write(paramArrayOfChar, k, n);
/*      */       }
/*      */       
/* 1266 */       if (paramInt1 >= paramInt2) {
/*      */         break;
/*      */       }
/*      */       
/* 1270 */       paramInt1++;
/* 1271 */       _appendCharacterEscape(m, j);
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
/*      */   private void _writeStringCustom(int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1289 */     int i = this._outputTail + paramInt;
/* 1290 */     int[] arrayOfInt = this._outputEscapes;
/* 1291 */     int j = this._maximumNonEscapedChar < 1 ? 65535 : this._maximumNonEscapedChar;
/* 1292 */     int k = Math.min(arrayOfInt.length, j + 1);
/* 1293 */     int m = 0;
/* 1294 */     CharacterEscapes localCharacterEscapes = this._characterEscapes;
/*      */     
/*      */ 
/* 1297 */     while (this._outputTail < i)
/*      */     {
/*      */       int n;
/*      */       do
/*      */       {
/* 1302 */         n = this._outputBuffer[this._outputTail];
/* 1303 */         if (n < k) {
/* 1304 */           m = arrayOfInt[n];
/* 1305 */           if (m != 0)
/*      */             break;
/*      */         } else {
/* 1308 */           if (n > j) {
/* 1309 */             m = -1;
/* 1310 */             break;
/*      */           }
/* 1312 */           if ((this._currentEscape = localCharacterEscapes.getEscapeSequence(n)) != null) {
/* 1313 */             m = -2;
/* 1314 */             break;
/*      */           }
/*      */         }
/* 1317 */       } while (++this._outputTail < i);
/* 1318 */       break;
/*      */       
/*      */ 
/* 1321 */       int i1 = this._outputTail - this._outputHead;
/* 1322 */       if (i1 > 0) {
/* 1323 */         this._writer.write(this._outputBuffer, this._outputHead, i1);
/*      */       }
/* 1325 */       this._outputTail += 1;
/* 1326 */       _prependOrWriteCharacterEscape(n, m);
/*      */     }
/*      */   }
/*      */   
/*      */   private void _writeSegmentCustom(int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1333 */     int[] arrayOfInt = this._outputEscapes;
/* 1334 */     int i = this._maximumNonEscapedChar < 1 ? 65535 : this._maximumNonEscapedChar;
/* 1335 */     int j = Math.min(arrayOfInt.length, i + 1);
/* 1336 */     CharacterEscapes localCharacterEscapes = this._characterEscapes;
/*      */     
/* 1338 */     int k = 0;
/* 1339 */     int m = 0;
/* 1340 */     int n = k;
/*      */     
/*      */ 
/* 1343 */     while (k < paramInt)
/*      */     {
/*      */       int i1;
/*      */       for (;;) {
/* 1347 */         i1 = this._outputBuffer[k];
/* 1348 */         if (i1 < j) {
/* 1349 */           m = arrayOfInt[i1];
/* 1350 */           if (m != 0)
/*      */             break;
/*      */         } else {
/* 1353 */           if (i1 > i) {
/* 1354 */             m = -1;
/* 1355 */             break;
/*      */           }
/* 1357 */           if ((this._currentEscape = localCharacterEscapes.getEscapeSequence(i1)) != null) {
/* 1358 */             m = -2;
/* 1359 */             break;
/*      */           }
/*      */         }
/* 1362 */         k++; if (k >= paramInt) {
/*      */           break;
/*      */         }
/*      */       }
/* 1366 */       int i2 = k - n;
/* 1367 */       if (i2 > 0) {
/* 1368 */         this._writer.write(this._outputBuffer, n, i2);
/* 1369 */         if (k >= paramInt) {
/*      */           break;
/*      */         }
/*      */       }
/* 1373 */       k++;
/* 1374 */       n = _prependOrWriteCharacterEscape(this._outputBuffer, k, paramInt, i1, m);
/*      */     }
/*      */   }
/*      */   
/*      */   private void _writeStringCustom(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1381 */     paramInt2 += paramInt1;
/* 1382 */     int[] arrayOfInt = this._outputEscapes;
/* 1383 */     int i = this._maximumNonEscapedChar < 1 ? 65535 : this._maximumNonEscapedChar;
/* 1384 */     int j = Math.min(arrayOfInt.length, i + 1);
/* 1385 */     CharacterEscapes localCharacterEscapes = this._characterEscapes;
/*      */     
/* 1387 */     int k = 0;
/*      */     
/* 1389 */     while (paramInt1 < paramInt2) {
/* 1390 */       int m = paramInt1;
/*      */       int n;
/*      */       for (;;)
/*      */       {
/* 1394 */         n = paramArrayOfChar[paramInt1];
/* 1395 */         if (n < j) {
/* 1396 */           k = arrayOfInt[n];
/* 1397 */           if (k != 0)
/*      */             break;
/*      */         } else {
/* 1400 */           if (n > i) {
/* 1401 */             k = -1;
/* 1402 */             break;
/*      */           }
/* 1404 */           if ((this._currentEscape = localCharacterEscapes.getEscapeSequence(n)) != null) {
/* 1405 */             k = -2;
/* 1406 */             break;
/*      */           }
/*      */         }
/* 1409 */         paramInt1++; if (paramInt1 >= paramInt2) {
/*      */           break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1415 */       int i1 = paramInt1 - m;
/* 1416 */       if (i1 < 32)
/*      */       {
/* 1418 */         if (this._outputTail + i1 > this._outputEnd) {
/* 1419 */           _flushBuffer();
/*      */         }
/* 1421 */         if (i1 > 0) {
/* 1422 */           System.arraycopy(paramArrayOfChar, m, this._outputBuffer, this._outputTail, i1);
/* 1423 */           this._outputTail += i1;
/*      */         }
/*      */       } else {
/* 1426 */         _flushBuffer();
/* 1427 */         this._writer.write(paramArrayOfChar, m, i1);
/*      */       }
/*      */       
/* 1430 */       if (paramInt1 >= paramInt2) {
/*      */         break;
/*      */       }
/*      */       
/* 1434 */       paramInt1++;
/* 1435 */       _appendCharacterEscape(n, k);
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
/*      */   protected void _writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1449 */     int i = paramInt2 - 3;
/*      */     
/* 1451 */     int j = this._outputEnd - 6;
/* 1452 */     int k = paramBase64Variant.getMaxLineLength() >> 2;
/*      */     
/*      */ 
/* 1455 */     while (paramInt1 <= i) {
/* 1456 */       if (this._outputTail > j) {
/* 1457 */         _flushBuffer();
/*      */       }
/*      */       
/* 1460 */       m = paramArrayOfByte[(paramInt1++)] << 8;
/* 1461 */       m |= paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 1462 */       m = m << 8 | paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 1463 */       this._outputTail = paramBase64Variant.encodeBase64Chunk(m, this._outputBuffer, this._outputTail);
/* 1464 */       k--; if (k <= 0)
/*      */       {
/* 1466 */         this._outputBuffer[(this._outputTail++)] = '\\';
/* 1467 */         this._outputBuffer[(this._outputTail++)] = 'n';
/* 1468 */         k = paramBase64Variant.getMaxLineLength() >> 2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1473 */     int m = paramInt2 - paramInt1;
/* 1474 */     if (m > 0) {
/* 1475 */       if (this._outputTail > j) {
/* 1476 */         _flushBuffer();
/*      */       }
/* 1478 */       int n = paramArrayOfByte[(paramInt1++)] << 16;
/* 1479 */       if (m == 2) {
/* 1480 */         n |= (paramArrayOfByte[(paramInt1++)] & 0xFF) << 8;
/*      */       }
/* 1482 */       this._outputTail = paramBase64Variant.encodeBase64Partial(n, m, this._outputBuffer, this._outputTail);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected int _writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1491 */     int i = 0;
/* 1492 */     int j = 0;
/* 1493 */     int k = -3;
/*      */     
/*      */ 
/* 1496 */     int m = this._outputEnd - 6;
/* 1497 */     int n = paramBase64Variant.getMaxLineLength() >> 2;
/*      */     int i1;
/* 1499 */     while (paramInt > 2) {
/* 1500 */       if (i > k) {
/* 1501 */         j = _readMore(paramInputStream, paramArrayOfByte, i, j, paramInt);
/* 1502 */         i = 0;
/* 1503 */         if (j < 3) {
/*      */           break;
/*      */         }
/* 1506 */         k = j - 3;
/*      */       }
/* 1508 */       if (this._outputTail > m) {
/* 1509 */         _flushBuffer();
/*      */       }
/* 1511 */       i1 = paramArrayOfByte[(i++)] << 8;
/* 1512 */       i1 |= paramArrayOfByte[(i++)] & 0xFF;
/* 1513 */       i1 = i1 << 8 | paramArrayOfByte[(i++)] & 0xFF;
/* 1514 */       paramInt -= 3;
/* 1515 */       this._outputTail = paramBase64Variant.encodeBase64Chunk(i1, this._outputBuffer, this._outputTail);
/* 1516 */       n--; if (n <= 0) {
/* 1517 */         this._outputBuffer[(this._outputTail++)] = '\\';
/* 1518 */         this._outputBuffer[(this._outputTail++)] = 'n';
/* 1519 */         n = paramBase64Variant.getMaxLineLength() >> 2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1524 */     if (paramInt > 0) {
/* 1525 */       j = _readMore(paramInputStream, paramArrayOfByte, i, j, paramInt);
/* 1526 */       i = 0;
/* 1527 */       if (j > 0) {
/* 1528 */         if (this._outputTail > m) {
/* 1529 */           _flushBuffer();
/*      */         }
/* 1531 */         i1 = paramArrayOfByte[(i++)] << 16;
/*      */         int i2;
/* 1533 */         if (i < j) {
/* 1534 */           i1 |= (paramArrayOfByte[i] & 0xFF) << 8;
/* 1535 */           i2 = 2;
/*      */         } else {
/* 1537 */           i2 = 1;
/*      */         }
/* 1539 */         this._outputTail = paramBase64Variant.encodeBase64Partial(i1, i2, this._outputBuffer, this._outputTail);
/* 1540 */         paramInt -= i2;
/*      */       }
/*      */     }
/* 1543 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected int _writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, byte[] paramArrayOfByte)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1551 */     int i = 0;
/* 1552 */     int j = 0;
/* 1553 */     int k = -3;
/* 1554 */     int m = 0;
/*      */     
/*      */ 
/* 1557 */     int n = this._outputEnd - 6;
/* 1558 */     int i1 = paramBase64Variant.getMaxLineLength() >> 2;
/*      */     int i2;
/*      */     for (;;)
/*      */     {
/* 1562 */       if (i > k) {
/* 1563 */         j = _readMore(paramInputStream, paramArrayOfByte, i, j, paramArrayOfByte.length);
/* 1564 */         i = 0;
/* 1565 */         if (j < 3) {
/*      */           break;
/*      */         }
/* 1568 */         k = j - 3;
/*      */       }
/* 1570 */       if (this._outputTail > n) {
/* 1571 */         _flushBuffer();
/*      */       }
/*      */       
/* 1574 */       i2 = paramArrayOfByte[(i++)] << 8;
/* 1575 */       i2 |= paramArrayOfByte[(i++)] & 0xFF;
/* 1576 */       i2 = i2 << 8 | paramArrayOfByte[(i++)] & 0xFF;
/* 1577 */       m += 3;
/* 1578 */       this._outputTail = paramBase64Variant.encodeBase64Chunk(i2, this._outputBuffer, this._outputTail);
/* 1579 */       i1--; if (i1 <= 0) {
/* 1580 */         this._outputBuffer[(this._outputTail++)] = '\\';
/* 1581 */         this._outputBuffer[(this._outputTail++)] = 'n';
/* 1582 */         i1 = paramBase64Variant.getMaxLineLength() >> 2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1587 */     if (i < j) {
/* 1588 */       if (this._outputTail > n) {
/* 1589 */         _flushBuffer();
/*      */       }
/* 1591 */       i2 = paramArrayOfByte[(i++)] << 16;
/* 1592 */       int i3 = 1;
/* 1593 */       if (i < j) {
/* 1594 */         i2 |= (paramArrayOfByte[i] & 0xFF) << 8;
/* 1595 */         i3 = 2;
/*      */       }
/* 1597 */       m += i3;
/* 1598 */       this._outputTail = paramBase64Variant.encodeBase64Partial(i2, i3, this._outputBuffer, this._outputTail);
/*      */     }
/* 1600 */     return m;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int _readMore(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*      */     throws IOException
/*      */   {
/* 1608 */     int i = 0;
/* 1609 */     while (paramInt1 < paramInt2) {
/* 1610 */       paramArrayOfByte[(i++)] = paramArrayOfByte[(paramInt1++)];
/*      */     }
/* 1612 */     paramInt1 = 0;
/* 1613 */     paramInt2 = i;
/* 1614 */     paramInt3 = Math.min(paramInt3, paramArrayOfByte.length);
/*      */     do
/*      */     {
/* 1617 */       int j = paramInt3 - paramInt2;
/* 1618 */       if (j == 0) {
/*      */         break;
/*      */       }
/* 1621 */       int k = paramInputStream.read(paramArrayOfByte, paramInt2, j);
/* 1622 */       if (k < 0) {
/* 1623 */         return paramInt2;
/*      */       }
/* 1625 */       paramInt2 += k;
/* 1626 */     } while (paramInt2 < 3);
/* 1627 */     return paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _writeNull()
/*      */     throws IOException
/*      */   {
/* 1638 */     if (this._outputTail + 4 >= this._outputEnd) {
/* 1639 */       _flushBuffer();
/*      */     }
/* 1641 */     int i = this._outputTail;
/* 1642 */     char[] arrayOfChar = this._outputBuffer;
/* 1643 */     arrayOfChar[i] = 'n';
/* 1644 */     arrayOfChar[(++i)] = 'u';
/* 1645 */     arrayOfChar[(++i)] = 'l';
/* 1646 */     arrayOfChar[(++i)] = 'l';
/* 1647 */     this._outputTail = (i + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _prependOrWriteCharacterEscape(char paramChar, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1664 */     if (paramInt >= 0) {
/* 1665 */       if (this._outputTail >= 2) {
/* 1666 */         int i = this._outputTail - 2;
/* 1667 */         this._outputHead = i;
/* 1668 */         this._outputBuffer[(i++)] = '\\';
/* 1669 */         this._outputBuffer[i] = ((char)paramInt);
/* 1670 */         return;
/*      */       }
/*      */       
/* 1673 */       localObject = this._entityBuffer;
/* 1674 */       if (localObject == null) {
/* 1675 */         localObject = _allocateEntityBuffer();
/*      */       }
/* 1677 */       this._outputHead = this._outputTail;
/* 1678 */       localObject[1] = ((char)paramInt);
/* 1679 */       this._writer.write((char[])localObject, 0, 2); return;
/*      */     }
/*      */     int k;
/* 1682 */     if (paramInt != -2) {
/* 1683 */       if (this._outputTail >= 6) {
/* 1684 */         localObject = this._outputBuffer;
/* 1685 */         j = this._outputTail - 6;
/* 1686 */         this._outputHead = j;
/* 1687 */         localObject[j] = 92;
/* 1688 */         localObject[(++j)] = 117;
/*      */         
/* 1690 */         if (paramChar > 'ÿ') {
/* 1691 */           k = paramChar >> '\b' & 0xFF;
/* 1692 */           localObject[(++j)] = HEX_CHARS[(k >> 4)];
/* 1693 */           localObject[(++j)] = HEX_CHARS[(k & 0xF)];
/* 1694 */           paramChar = (char)(paramChar & 0xFF);
/*      */         } else {
/* 1696 */           localObject[(++j)] = 48;
/* 1697 */           localObject[(++j)] = 48;
/*      */         }
/* 1699 */         localObject[(++j)] = HEX_CHARS[(paramChar >> '\004')];
/* 1700 */         localObject[(++j)] = HEX_CHARS[(paramChar & 0xF)];
/* 1701 */         return;
/*      */       }
/*      */       
/* 1704 */       localObject = this._entityBuffer;
/* 1705 */       if (localObject == null) {
/* 1706 */         localObject = _allocateEntityBuffer();
/*      */       }
/* 1708 */       this._outputHead = this._outputTail;
/* 1709 */       if (paramChar > 'ÿ') {
/* 1710 */         j = paramChar >> '\b' & 0xFF;
/* 1711 */         k = paramChar & 0xFF;
/* 1712 */         localObject[10] = HEX_CHARS[(j >> 4)];
/* 1713 */         localObject[11] = HEX_CHARS[(j & 0xF)];
/* 1714 */         localObject[12] = HEX_CHARS[(k >> 4)];
/* 1715 */         localObject[13] = HEX_CHARS[(k & 0xF)];
/* 1716 */         this._writer.write((char[])localObject, 8, 6);
/*      */       } else {
/* 1718 */         localObject[6] = HEX_CHARS[(paramChar >> '\004')];
/* 1719 */         localObject[7] = HEX_CHARS[(paramChar & 0xF)];
/* 1720 */         this._writer.write((char[])localObject, 2, 6);
/*      */       }
/* 1722 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1726 */     if (this._currentEscape == null) {
/* 1727 */       localObject = this._characterEscapes.getEscapeSequence(paramChar).getValue();
/*      */     } else {
/* 1729 */       localObject = this._currentEscape.getValue();
/* 1730 */       this._currentEscape = null;
/*      */     }
/* 1732 */     int j = ((String)localObject).length();
/* 1733 */     if (this._outputTail >= j) {
/* 1734 */       k = this._outputTail - j;
/* 1735 */       this._outputHead = k;
/* 1736 */       ((String)localObject).getChars(0, j, this._outputBuffer, k);
/* 1737 */       return;
/*      */     }
/*      */     
/* 1740 */     this._outputHead = this._outputTail;
/* 1741 */     this._writer.write((String)localObject);
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
/*      */   private int _prependOrWriteCharacterEscape(char[] paramArrayOfChar, int paramInt1, int paramInt2, char paramChar, int paramInt3)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1755 */     if (paramInt3 >= 0) {
/* 1756 */       if ((paramInt1 > 1) && (paramInt1 < paramInt2)) {
/* 1757 */         paramInt1 -= 2;
/* 1758 */         paramArrayOfChar[paramInt1] = '\\';
/* 1759 */         paramArrayOfChar[(paramInt1 + 1)] = ((char)paramInt3);
/*      */       } else {
/* 1761 */         char[] arrayOfChar = this._entityBuffer;
/* 1762 */         if (arrayOfChar == null) {
/* 1763 */           arrayOfChar = _allocateEntityBuffer();
/*      */         }
/* 1765 */         arrayOfChar[1] = ((char)paramInt3);
/* 1766 */         this._writer.write(arrayOfChar, 0, 2);
/*      */       }
/* 1768 */       return paramInt1; }
/*      */     Object localObject;
/* 1770 */     if (paramInt3 != -2) {
/* 1771 */       if ((paramInt1 > 5) && (paramInt1 < paramInt2)) {
/* 1772 */         paramInt1 -= 6;
/* 1773 */         paramArrayOfChar[(paramInt1++)] = '\\';
/* 1774 */         paramArrayOfChar[(paramInt1++)] = 'u';
/*      */         
/* 1776 */         if (paramChar > 'ÿ') {
/* 1777 */           int i = paramChar >> '\b' & 0xFF;
/* 1778 */           paramArrayOfChar[(paramInt1++)] = HEX_CHARS[(i >> 4)];
/* 1779 */           paramArrayOfChar[(paramInt1++)] = HEX_CHARS[(i & 0xF)];
/* 1780 */           paramChar = (char)(paramChar & 0xFF);
/*      */         } else {
/* 1782 */           paramArrayOfChar[(paramInt1++)] = '0';
/* 1783 */           paramArrayOfChar[(paramInt1++)] = '0';
/*      */         }
/* 1785 */         paramArrayOfChar[(paramInt1++)] = HEX_CHARS[(paramChar >> '\004')];
/* 1786 */         paramArrayOfChar[paramInt1] = HEX_CHARS[(paramChar & 0xF)];
/* 1787 */         paramInt1 -= 5;
/*      */       }
/*      */       else {
/* 1790 */         localObject = this._entityBuffer;
/* 1791 */         if (localObject == null) {
/* 1792 */           localObject = _allocateEntityBuffer();
/*      */         }
/* 1794 */         this._outputHead = this._outputTail;
/* 1795 */         if (paramChar > 'ÿ') {
/* 1796 */           j = paramChar >> '\b' & 0xFF;
/* 1797 */           int k = paramChar & 0xFF;
/* 1798 */           localObject[10] = HEX_CHARS[(j >> 4)];
/* 1799 */           localObject[11] = HEX_CHARS[(j & 0xF)];
/* 1800 */           localObject[12] = HEX_CHARS[(k >> 4)];
/* 1801 */           localObject[13] = HEX_CHARS[(k & 0xF)];
/* 1802 */           this._writer.write((char[])localObject, 8, 6);
/*      */         } else {
/* 1804 */           localObject[6] = HEX_CHARS[(paramChar >> '\004')];
/* 1805 */           localObject[7] = HEX_CHARS[(paramChar & 0xF)];
/* 1806 */           this._writer.write((char[])localObject, 2, 6);
/*      */         }
/*      */       }
/* 1809 */       return paramInt1;
/*      */     }
/*      */     
/* 1812 */     if (this._currentEscape == null) {
/* 1813 */       localObject = this._characterEscapes.getEscapeSequence(paramChar).getValue();
/*      */     } else {
/* 1815 */       localObject = this._currentEscape.getValue();
/* 1816 */       this._currentEscape = null;
/*      */     }
/* 1818 */     int j = ((String)localObject).length();
/* 1819 */     if ((paramInt1 >= j) && (paramInt1 < paramInt2)) {
/* 1820 */       paramInt1 -= j;
/* 1821 */       ((String)localObject).getChars(0, j, paramArrayOfChar, paramInt1);
/*      */     } else {
/* 1823 */       this._writer.write((String)localObject);
/*      */     }
/* 1825 */     return paramInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _appendCharacterEscape(char paramChar, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1835 */     if (paramInt >= 0) {
/* 1836 */       if (this._outputTail + 2 > this._outputEnd) {
/* 1837 */         _flushBuffer();
/*      */       }
/* 1839 */       this._outputBuffer[(this._outputTail++)] = '\\';
/* 1840 */       this._outputBuffer[(this._outputTail++)] = ((char)paramInt);
/* 1841 */       return;
/*      */     }
/* 1843 */     if (paramInt != -2) {
/* 1844 */       if (this._outputTail + 2 > this._outputEnd) {
/* 1845 */         _flushBuffer();
/*      */       }
/* 1847 */       int i = this._outputTail;
/* 1848 */       char[] arrayOfChar = this._outputBuffer;
/* 1849 */       arrayOfChar[(i++)] = '\\';
/* 1850 */       arrayOfChar[(i++)] = 'u';
/*      */       
/* 1852 */       if (paramChar > 'ÿ') {
/* 1853 */         int k = paramChar >> '\b' & 0xFF;
/* 1854 */         arrayOfChar[(i++)] = HEX_CHARS[(k >> 4)];
/* 1855 */         arrayOfChar[(i++)] = HEX_CHARS[(k & 0xF)];
/* 1856 */         paramChar = (char)(paramChar & 0xFF);
/*      */       } else {
/* 1858 */         arrayOfChar[(i++)] = '0';
/* 1859 */         arrayOfChar[(i++)] = '0';
/*      */       }
/* 1861 */       arrayOfChar[(i++)] = HEX_CHARS[(paramChar >> '\004')];
/* 1862 */       arrayOfChar[i] = HEX_CHARS[(paramChar & 0xF)];
/* 1863 */       this._outputTail = i; return;
/*      */     }
/*      */     
/*      */     String str;
/* 1867 */     if (this._currentEscape == null) {
/* 1868 */       str = this._characterEscapes.getEscapeSequence(paramChar).getValue();
/*      */     } else {
/* 1870 */       str = this._currentEscape.getValue();
/* 1871 */       this._currentEscape = null;
/*      */     }
/* 1873 */     int j = str.length();
/* 1874 */     if (this._outputTail + j > this._outputEnd) {
/* 1875 */       _flushBuffer();
/* 1876 */       if (j > this._outputEnd) {
/* 1877 */         this._writer.write(str);
/* 1878 */         return;
/*      */       }
/*      */     }
/* 1881 */     str.getChars(0, j, this._outputBuffer, this._outputTail);
/* 1882 */     this._outputTail += j;
/*      */   }
/*      */   
/*      */   private char[] _allocateEntityBuffer()
/*      */   {
/* 1887 */     char[] arrayOfChar = new char[14];
/*      */     
/* 1889 */     arrayOfChar[0] = '\\';
/*      */     
/* 1891 */     arrayOfChar[2] = '\\';
/* 1892 */     arrayOfChar[3] = 'u';
/* 1893 */     arrayOfChar[4] = '0';
/* 1894 */     arrayOfChar[5] = '0';
/*      */     
/* 1896 */     arrayOfChar[8] = '\\';
/* 1897 */     arrayOfChar[9] = 'u';
/* 1898 */     this._entityBuffer = arrayOfChar;
/* 1899 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */   protected void _flushBuffer() throws IOException
/*      */   {
/* 1904 */     int i = this._outputTail - this._outputHead;
/* 1905 */     if (i > 0) {
/* 1906 */       int j = this._outputHead;
/* 1907 */       this._outputTail = (this._outputHead = 0);
/* 1908 */       this._writer.write(this._outputBuffer, j, i);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/WriterBasedJsonGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */