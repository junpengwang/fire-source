/*      */ package com.shaded.fasterxml.jackson.core.json;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerator.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*      */ import com.shaded.fasterxml.jackson.core.PrettyPrinter;
/*      */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*      */ import com.shaded.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*      */ import com.shaded.fasterxml.jackson.core.io.NumberOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ public class UTF8JsonGenerator extends JsonGeneratorImpl
/*      */ {
/*      */   private static final byte BYTE_u = 117;
/*      */   private static final byte BYTE_0 = 48;
/*      */   private static final byte BYTE_LBRACKET = 91;
/*      */   private static final byte BYTE_RBRACKET = 93;
/*      */   private static final byte BYTE_LCURLY = 123;
/*      */   private static final byte BYTE_RCURLY = 125;
/*      */   private static final byte BYTE_BACKSLASH = 92;
/*      */   private static final byte BYTE_COMMA = 44;
/*      */   private static final byte BYTE_COLON = 58;
/*      */   private static final byte BYTE_QUOTE = 34;
/*      */   protected static final int SURR1_FIRST = 55296;
/*      */   protected static final int SURR1_LAST = 56319;
/*      */   protected static final int SURR2_FIRST = 56320;
/*      */   protected static final int SURR2_LAST = 57343;
/*      */   private static final int MAX_BYTES_TO_BUFFER = 512;
/*   35 */   static final byte[] HEX_CHARS = ;
/*      */   
/*   37 */   private static final byte[] NULL_BYTES = { 110, 117, 108, 108 };
/*   38 */   private static final byte[] TRUE_BYTES = { 116, 114, 117, 101 };
/*   39 */   private static final byte[] FALSE_BYTES = { 102, 97, 108, 115, 101 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final OutputStream _outputStream;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected byte[] _outputBuffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   62 */   protected int _outputTail = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final int _outputEnd;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final int _outputMaxContiguous;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected char[] _charBuffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final int _charBufferLength;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected byte[] _entityBuffer;
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
/*      */   public UTF8JsonGenerator(IOContext paramIOContext, int paramInt, ObjectCodec paramObjectCodec, OutputStream paramOutputStream)
/*      */   {
/*  108 */     super(paramIOContext, paramInt, paramObjectCodec);
/*  109 */     this._outputStream = paramOutputStream;
/*  110 */     this._bufferRecyclable = true;
/*  111 */     this._outputBuffer = paramIOContext.allocWriteEncodingBuffer();
/*  112 */     this._outputEnd = this._outputBuffer.length;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  118 */     this._outputMaxContiguous = (this._outputEnd >> 3);
/*  119 */     this._charBuffer = paramIOContext.allocConcatBuffer();
/*  120 */     this._charBufferLength = this._charBuffer.length;
/*      */     
/*      */ 
/*  123 */     if (isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
/*  124 */       setHighestNonEscapedChar(127);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public UTF8JsonGenerator(IOContext paramIOContext, int paramInt1, ObjectCodec paramObjectCodec, OutputStream paramOutputStream, byte[] paramArrayOfByte, int paramInt2, boolean paramBoolean)
/*      */   {
/*  133 */     super(paramIOContext, paramInt1, paramObjectCodec);
/*  134 */     this._outputStream = paramOutputStream;
/*  135 */     this._bufferRecyclable = paramBoolean;
/*  136 */     this._outputTail = paramInt2;
/*  137 */     this._outputBuffer = paramArrayOfByte;
/*  138 */     this._outputEnd = this._outputBuffer.length;
/*      */     
/*  140 */     this._outputMaxContiguous = (this._outputEnd >> 3);
/*  141 */     this._charBuffer = paramIOContext.allocConcatBuffer();
/*  142 */     this._charBufferLength = this._charBuffer.length;
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
/*  153 */     return this._outputStream;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeFieldName(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  165 */     int i = this._writeContext.writeFieldName(paramString);
/*  166 */     if (i == 4) {
/*  167 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  169 */     if (this._cfgPrettyPrinter != null) {
/*  170 */       _writePPFieldName(paramString, i == 1);
/*  171 */       return;
/*      */     }
/*  173 */     if (i == 1) {
/*  174 */       if (this._outputTail >= this._outputEnd) {
/*  175 */         _flushBuffer();
/*      */       }
/*  177 */       this._outputBuffer[(this._outputTail++)] = 44;
/*      */     }
/*  179 */     _writeFieldName(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final void writeFieldName(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  187 */     int i = this._writeContext.writeFieldName(paramSerializableString.getValue());
/*  188 */     if (i == 4) {
/*  189 */       _reportError("Can not write a field name, expecting a value");
/*      */     }
/*  191 */     if (this._cfgPrettyPrinter != null) {
/*  192 */       _writePPFieldName(paramSerializableString, i == 1);
/*  193 */       return;
/*      */     }
/*  195 */     if (i == 1) {
/*  196 */       if (this._outputTail >= this._outputEnd) {
/*  197 */         _flushBuffer();
/*      */       }
/*  199 */       this._outputBuffer[(this._outputTail++)] = 44;
/*      */     }
/*  201 */     _writeFieldName(paramSerializableString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeStartArray()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  213 */     _verifyValueWrite("start an array");
/*  214 */     this._writeContext = this._writeContext.createChildArrayContext();
/*  215 */     if (this._cfgPrettyPrinter != null) {
/*  216 */       this._cfgPrettyPrinter.writeStartArray(this);
/*      */     } else {
/*  218 */       if (this._outputTail >= this._outputEnd) {
/*  219 */         _flushBuffer();
/*      */       }
/*  221 */       this._outputBuffer[(this._outputTail++)] = 91;
/*      */     }
/*      */   }
/*      */   
/*      */   public final void writeEndArray()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  228 */     if (!this._writeContext.inArray()) {
/*  229 */       _reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
/*      */     }
/*  231 */     if (this._cfgPrettyPrinter != null) {
/*  232 */       this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
/*      */     } else {
/*  234 */       if (this._outputTail >= this._outputEnd) {
/*  235 */         _flushBuffer();
/*      */       }
/*  237 */       this._outputBuffer[(this._outputTail++)] = 93;
/*      */     }
/*  239 */     this._writeContext = this._writeContext.getParent();
/*      */   }
/*      */   
/*      */   public final void writeStartObject()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  245 */     _verifyValueWrite("start an object");
/*  246 */     this._writeContext = this._writeContext.createChildObjectContext();
/*  247 */     if (this._cfgPrettyPrinter != null) {
/*  248 */       this._cfgPrettyPrinter.writeStartObject(this);
/*      */     } else {
/*  250 */       if (this._outputTail >= this._outputEnd) {
/*  251 */         _flushBuffer();
/*      */       }
/*  253 */       this._outputBuffer[(this._outputTail++)] = 123;
/*      */     }
/*      */   }
/*      */   
/*      */   public final void writeEndObject()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  260 */     if (!this._writeContext.inObject()) {
/*  261 */       _reportError("Current context not an object but " + this._writeContext.getTypeDesc());
/*      */     }
/*  263 */     if (this._cfgPrettyPrinter != null) {
/*  264 */       this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
/*      */     } else {
/*  266 */       if (this._outputTail >= this._outputEnd) {
/*  267 */         _flushBuffer();
/*      */       }
/*  269 */       this._outputBuffer[(this._outputTail++)] = 125;
/*      */     }
/*  271 */     this._writeContext = this._writeContext.getParent();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void _writeFieldName(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  280 */     if (!isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/*  281 */       _writeStringSegments(paramString);
/*  282 */       return;
/*      */     }
/*  284 */     if (this._outputTail >= this._outputEnd) {
/*  285 */       _flushBuffer();
/*      */     }
/*  287 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */     
/*  289 */     int i = paramString.length();
/*  290 */     if (i <= this._charBufferLength) {
/*  291 */       paramString.getChars(0, i, this._charBuffer, 0);
/*      */       
/*  293 */       if (i <= this._outputMaxContiguous) {
/*  294 */         if (this._outputTail + i > this._outputEnd) {
/*  295 */           _flushBuffer();
/*      */         }
/*  297 */         _writeStringSegment(this._charBuffer, 0, i);
/*      */       } else {
/*  299 */         _writeStringSegments(this._charBuffer, 0, i);
/*      */       }
/*      */     } else {
/*  302 */       _writeStringSegments(paramString);
/*      */     }
/*      */     
/*      */ 
/*  306 */     if (this._outputTail >= this._outputEnd) {
/*  307 */       _flushBuffer();
/*      */     }
/*  309 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */   protected final void _writeFieldName(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  315 */     if (!isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/*  316 */       i = paramSerializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/*  317 */       if (i < 0) {
/*  318 */         _writeBytes(paramSerializableString.asQuotedUTF8());
/*      */       } else {
/*  320 */         this._outputTail += i;
/*      */       }
/*  322 */       return;
/*      */     }
/*  324 */     if (this._outputTail >= this._outputEnd) {
/*  325 */       _flushBuffer();
/*      */     }
/*  327 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  328 */     int i = paramSerializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/*  329 */     if (i < 0) {
/*  330 */       _writeBytes(paramSerializableString.asQuotedUTF8());
/*      */     } else {
/*  332 */       this._outputTail += i;
/*      */     }
/*  334 */     if (this._outputTail >= this._outputEnd) {
/*  335 */       _flushBuffer();
/*      */     }
/*  337 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void _writePPFieldName(String paramString, boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  347 */     if (paramBoolean) {
/*  348 */       this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
/*      */     } else {
/*  350 */       this._cfgPrettyPrinter.beforeObjectEntries(this);
/*      */     }
/*      */     
/*  353 */     if (isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/*  354 */       if (this._outputTail >= this._outputEnd) {
/*  355 */         _flushBuffer();
/*      */       }
/*  357 */       this._outputBuffer[(this._outputTail++)] = 34;
/*  358 */       int i = paramString.length();
/*  359 */       if (i <= this._charBufferLength) {
/*  360 */         paramString.getChars(0, i, this._charBuffer, 0);
/*      */         
/*  362 */         if (i <= this._outputMaxContiguous) {
/*  363 */           if (this._outputTail + i > this._outputEnd) {
/*  364 */             _flushBuffer();
/*      */           }
/*  366 */           _writeStringSegment(this._charBuffer, 0, i);
/*      */         } else {
/*  368 */           _writeStringSegments(this._charBuffer, 0, i);
/*      */         }
/*      */       } else {
/*  371 */         _writeStringSegments(paramString);
/*      */       }
/*  373 */       if (this._outputTail >= this._outputEnd) {
/*  374 */         _flushBuffer();
/*      */       }
/*  376 */       this._outputBuffer[(this._outputTail++)] = 34;
/*      */     } else {
/*  378 */       _writeStringSegments(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void _writePPFieldName(SerializableString paramSerializableString, boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  385 */     if (paramBoolean) {
/*  386 */       this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
/*      */     } else {
/*  388 */       this._cfgPrettyPrinter.beforeObjectEntries(this);
/*      */     }
/*      */     
/*  391 */     boolean bool = isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES);
/*  392 */     if (bool) {
/*  393 */       if (this._outputTail >= this._outputEnd) {
/*  394 */         _flushBuffer();
/*      */       }
/*  396 */       this._outputBuffer[(this._outputTail++)] = 34;
/*      */     }
/*  398 */     _writeBytes(paramSerializableString.asQuotedUTF8());
/*  399 */     if (bool) {
/*  400 */       if (this._outputTail >= this._outputEnd) {
/*  401 */         _flushBuffer();
/*      */       }
/*  403 */       this._outputBuffer[(this._outputTail++)] = 34;
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
/*  417 */     _verifyValueWrite("write text value");
/*  418 */     if (paramString == null) {
/*  419 */       _writeNull();
/*  420 */       return;
/*      */     }
/*      */     
/*  423 */     int i = paramString.length();
/*  424 */     if (i > this._charBufferLength) {
/*  425 */       _writeLongString(paramString);
/*  426 */       return;
/*      */     }
/*      */     
/*  429 */     paramString.getChars(0, i, this._charBuffer, 0);
/*      */     
/*  431 */     if (i > this._outputMaxContiguous) {
/*  432 */       _writeLongString(this._charBuffer, 0, i);
/*  433 */       return;
/*      */     }
/*  435 */     if (this._outputTail + i >= this._outputEnd) {
/*  436 */       _flushBuffer();
/*      */     }
/*  438 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  439 */     _writeStringSegment(this._charBuffer, 0, i);
/*      */     
/*      */ 
/*      */ 
/*  443 */     if (this._outputTail >= this._outputEnd) {
/*  444 */       _flushBuffer();
/*      */     }
/*  446 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */   private void _writeLongString(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  452 */     if (this._outputTail >= this._outputEnd) {
/*  453 */       _flushBuffer();
/*      */     }
/*  455 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  456 */     _writeStringSegments(paramString);
/*  457 */     if (this._outputTail >= this._outputEnd) {
/*  458 */       _flushBuffer();
/*      */     }
/*  460 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */   private void _writeLongString(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  466 */     if (this._outputTail >= this._outputEnd) {
/*  467 */       _flushBuffer();
/*      */     }
/*  469 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  470 */     _writeStringSegments(this._charBuffer, 0, paramInt2);
/*  471 */     if (this._outputTail >= this._outputEnd) {
/*  472 */       _flushBuffer();
/*      */     }
/*  474 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeString(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  481 */     _verifyValueWrite("write text value");
/*  482 */     if (this._outputTail >= this._outputEnd) {
/*  483 */       _flushBuffer();
/*      */     }
/*  485 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */     
/*  487 */     if (paramInt2 <= this._outputMaxContiguous) {
/*  488 */       if (this._outputTail + paramInt2 > this._outputEnd) {
/*  489 */         _flushBuffer();
/*      */       }
/*  491 */       _writeStringSegment(paramArrayOfChar, paramInt1, paramInt2);
/*      */     } else {
/*  493 */       _writeStringSegments(paramArrayOfChar, paramInt1, paramInt2);
/*      */     }
/*      */     
/*  496 */     if (this._outputTail >= this._outputEnd) {
/*  497 */       _flushBuffer();
/*      */     }
/*  499 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public final void writeString(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  506 */     _verifyValueWrite("write text value");
/*  507 */     if (this._outputTail >= this._outputEnd) {
/*  508 */       _flushBuffer();
/*      */     }
/*  510 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  511 */     int i = paramSerializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/*  512 */     if (i < 0) {
/*  513 */       _writeBytes(paramSerializableString.asQuotedUTF8());
/*      */     } else {
/*  515 */       this._outputTail += i;
/*      */     }
/*  517 */     if (this._outputTail >= this._outputEnd) {
/*  518 */       _flushBuffer();
/*      */     }
/*  520 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeRawUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  527 */     _verifyValueWrite("write text value");
/*  528 */     if (this._outputTail >= this._outputEnd) {
/*  529 */       _flushBuffer();
/*      */     }
/*  531 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  532 */     _writeBytes(paramArrayOfByte, paramInt1, paramInt2);
/*  533 */     if (this._outputTail >= this._outputEnd) {
/*  534 */       _flushBuffer();
/*      */     }
/*  536 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  543 */     _verifyValueWrite("write text value");
/*  544 */     if (this._outputTail >= this._outputEnd) {
/*  545 */       _flushBuffer();
/*      */     }
/*  547 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */     
/*  549 */     if (paramInt2 <= this._outputMaxContiguous) {
/*  550 */       _writeUTF8Segment(paramArrayOfByte, paramInt1, paramInt2);
/*      */     } else {
/*  552 */       _writeUTF8Segments(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*  554 */     if (this._outputTail >= this._outputEnd) {
/*  555 */       _flushBuffer();
/*      */     }
/*  557 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
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
/*  570 */     int i = 0;
/*  571 */     int j = paramString.length();
/*  572 */     while (j > 0) {
/*  573 */       char[] arrayOfChar = this._charBuffer;
/*  574 */       int k = arrayOfChar.length;
/*  575 */       int m = j < k ? j : k;
/*  576 */       paramString.getChars(i, i + m, arrayOfChar, 0);
/*  577 */       writeRaw(arrayOfChar, 0, m);
/*  578 */       i += m;
/*  579 */       j -= m;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeRaw(String paramString, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  587 */     while (paramInt2 > 0) {
/*  588 */       char[] arrayOfChar = this._charBuffer;
/*  589 */       int i = arrayOfChar.length;
/*  590 */       int j = paramInt2 < i ? paramInt2 : i;
/*  591 */       paramString.getChars(paramInt1, paramInt1 + j, arrayOfChar, 0);
/*  592 */       writeRaw(arrayOfChar, 0, j);
/*  593 */       paramInt1 += j;
/*  594 */       paramInt2 -= j;
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeRaw(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  601 */     byte[] arrayOfByte = paramSerializableString.asUnquotedUTF8();
/*  602 */     if (arrayOfByte.length > 0) {
/*  603 */       _writeBytes(arrayOfByte);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeRaw(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  614 */     int i = paramInt2 + paramInt2 + paramInt2;
/*  615 */     if (this._outputTail + i > this._outputEnd)
/*      */     {
/*  617 */       if (this._outputEnd < i) {
/*  618 */         _writeSegmentedRaw(paramArrayOfChar, paramInt1, paramInt2);
/*  619 */         return;
/*      */       }
/*      */       
/*  622 */       _flushBuffer();
/*      */     }
/*      */     
/*  625 */     paramInt2 += paramInt1;
/*      */     
/*      */ 
/*      */ 
/*  629 */     while (paramInt1 < paramInt2)
/*      */     {
/*      */       for (;;) {
/*  632 */         i = paramArrayOfChar[paramInt1];
/*  633 */         if (i > 127) {
/*      */           break;
/*      */         }
/*  636 */         this._outputBuffer[(this._outputTail++)] = ((byte)i);
/*  637 */         paramInt1++; if (paramInt1 >= paramInt2) {
/*      */           return;
/*      */         }
/*      */       }
/*  641 */       i = paramArrayOfChar[(paramInt1++)];
/*  642 */       if (i < 2048) {
/*  643 */         this._outputBuffer[(this._outputTail++)] = ((byte)(0xC0 | i >> 6));
/*  644 */         this._outputBuffer[(this._outputTail++)] = ((byte)(0x80 | i & 0x3F));
/*      */       } else {
/*  646 */         _outputRawMultiByteChar(i, paramArrayOfChar, paramInt1, paramInt2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeRaw(char paramChar)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  655 */     if (this._outputTail + 3 >= this._outputEnd) {
/*  656 */       _flushBuffer();
/*      */     }
/*  658 */     byte[] arrayOfByte = this._outputBuffer;
/*  659 */     if (paramChar <= '') {
/*  660 */       arrayOfByte[(this._outputTail++)] = ((byte)paramChar);
/*  661 */     } else if (paramChar < 'ࠀ') {
/*  662 */       arrayOfByte[(this._outputTail++)] = ((byte)(0xC0 | paramChar >> '\006'));
/*  663 */       arrayOfByte[(this._outputTail++)] = ((byte)(0x80 | paramChar & 0x3F));
/*      */     } else {
/*  665 */       _outputRawMultiByteChar(paramChar, null, 0, 0);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void _writeSegmentedRaw(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  676 */     int i = this._outputEnd;
/*  677 */     byte[] arrayOfByte = this._outputBuffer;
/*      */     
/*      */ 
/*  680 */     while (paramInt1 < paramInt2)
/*      */     {
/*      */       for (;;) {
/*  683 */         j = paramArrayOfChar[paramInt1];
/*  684 */         if (j >= 128) {
/*      */           break;
/*      */         }
/*      */         
/*  688 */         if (this._outputTail >= i) {
/*  689 */           _flushBuffer();
/*      */         }
/*  691 */         arrayOfByte[(this._outputTail++)] = ((byte)j);
/*  692 */         paramInt1++; if (paramInt1 >= paramInt2) {
/*      */           return;
/*      */         }
/*      */       }
/*  696 */       if (this._outputTail + 3 >= this._outputEnd) {
/*  697 */         _flushBuffer();
/*      */       }
/*  699 */       int j = paramArrayOfChar[(paramInt1++)];
/*  700 */       if (j < 2048) {
/*  701 */         arrayOfByte[(this._outputTail++)] = ((byte)(0xC0 | j >> 6));
/*  702 */         arrayOfByte[(this._outputTail++)] = ((byte)(0x80 | j & 0x3F));
/*      */       } else {
/*  704 */         _outputRawMultiByteChar(j, paramArrayOfChar, paramInt1, paramInt2);
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
/*      */   public void writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  720 */     _verifyValueWrite("write binary value");
/*      */     
/*  722 */     if (this._outputTail >= this._outputEnd) {
/*  723 */       _flushBuffer();
/*      */     }
/*  725 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  726 */     _writeBinary(paramBase64Variant, paramArrayOfByte, paramInt1, paramInt1 + paramInt2);
/*      */     
/*  728 */     if (this._outputTail >= this._outputEnd) {
/*  729 */       _flushBuffer();
/*      */     }
/*  731 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  739 */     _verifyValueWrite("write binary value");
/*      */     
/*  741 */     if (this._outputTail >= this._outputEnd) {
/*  742 */       _flushBuffer();
/*      */     }
/*  744 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  745 */     byte[] arrayOfByte = this._ioContext.allocBase64Buffer();
/*      */     int i;
/*      */     try {
/*  748 */       if (paramInt < 0) {
/*  749 */         i = _writeBinary(paramBase64Variant, paramInputStream, arrayOfByte);
/*      */       } else {
/*  751 */         int j = _writeBinary(paramBase64Variant, paramInputStream, arrayOfByte, paramInt);
/*  752 */         if (j > 0) {
/*  753 */           _reportError("Too few bytes available: missing " + j + " bytes (out of " + paramInt + ")");
/*      */         }
/*  755 */         i = paramInt;
/*      */       }
/*      */     } finally {
/*  758 */       this._ioContext.releaseBase64Buffer(arrayOfByte);
/*      */     }
/*      */     
/*  761 */     if (this._outputTail >= this._outputEnd) {
/*  762 */       _flushBuffer();
/*      */     }
/*  764 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  765 */     return i;
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
/*  778 */     _verifyValueWrite("write number");
/*      */     
/*  780 */     if (this._outputTail + 6 >= this._outputEnd) {
/*  781 */       _flushBuffer();
/*      */     }
/*  783 */     if (this._cfgNumbersAsStrings) {
/*  784 */       _writeQuotedShort(paramShort);
/*  785 */       return;
/*      */     }
/*  787 */     this._outputTail = NumberOutput.outputInt(paramShort, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedShort(short paramShort) throws IOException {
/*  791 */     if (this._outputTail + 8 >= this._outputEnd) {
/*  792 */       _flushBuffer();
/*      */     }
/*  794 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  795 */     this._outputTail = NumberOutput.outputInt(paramShort, this._outputBuffer, this._outputTail);
/*  796 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  803 */     _verifyValueWrite("write number");
/*      */     
/*  805 */     if (this._outputTail + 11 >= this._outputEnd) {
/*  806 */       _flushBuffer();
/*      */     }
/*  808 */     if (this._cfgNumbersAsStrings) {
/*  809 */       _writeQuotedInt(paramInt);
/*  810 */       return;
/*      */     }
/*  812 */     this._outputTail = NumberOutput.outputInt(paramInt, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedInt(int paramInt) throws IOException {
/*  816 */     if (this._outputTail + 13 >= this._outputEnd) {
/*  817 */       _flushBuffer();
/*      */     }
/*  819 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  820 */     this._outputTail = NumberOutput.outputInt(paramInt, this._outputBuffer, this._outputTail);
/*  821 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(long paramLong)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  828 */     _verifyValueWrite("write number");
/*  829 */     if (this._cfgNumbersAsStrings) {
/*  830 */       _writeQuotedLong(paramLong);
/*  831 */       return;
/*      */     }
/*  833 */     if (this._outputTail + 21 >= this._outputEnd)
/*      */     {
/*  835 */       _flushBuffer();
/*      */     }
/*  837 */     this._outputTail = NumberOutput.outputLong(paramLong, this._outputBuffer, this._outputTail);
/*      */   }
/*      */   
/*      */   private void _writeQuotedLong(long paramLong) throws IOException {
/*  841 */     if (this._outputTail + 23 >= this._outputEnd) {
/*  842 */       _flushBuffer();
/*      */     }
/*  844 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  845 */     this._outputTail = NumberOutput.outputLong(paramLong, this._outputBuffer, this._outputTail);
/*  846 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(BigInteger paramBigInteger)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  853 */     _verifyValueWrite("write number");
/*  854 */     if (paramBigInteger == null) {
/*  855 */       _writeNull();
/*  856 */     } else if (this._cfgNumbersAsStrings) {
/*  857 */       _writeQuotedRaw(paramBigInteger);
/*      */     } else {
/*  859 */       writeRaw(paramBigInteger.toString());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeNumber(double paramDouble)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  868 */     if ((this._cfgNumbersAsStrings) || (((Double.isNaN(paramDouble)) || (Double.isInfinite(paramDouble))) && (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))))
/*      */     {
/*      */ 
/*      */ 
/*  872 */       writeString(String.valueOf(paramDouble));
/*  873 */       return;
/*      */     }
/*      */     
/*  876 */     _verifyValueWrite("write number");
/*  877 */     writeRaw(String.valueOf(paramDouble));
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(float paramFloat)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  884 */     if ((this._cfgNumbersAsStrings) || (((Float.isNaN(paramFloat)) || (Float.isInfinite(paramFloat))) && (isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))))
/*      */     {
/*      */ 
/*      */ 
/*  888 */       writeString(String.valueOf(paramFloat));
/*  889 */       return;
/*      */     }
/*      */     
/*  892 */     _verifyValueWrite("write number");
/*  893 */     writeRaw(String.valueOf(paramFloat));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeNumber(BigDecimal paramBigDecimal)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  901 */     _verifyValueWrite("write number");
/*  902 */     if (paramBigDecimal == null) {
/*  903 */       _writeNull();
/*  904 */     } else if (this._cfgNumbersAsStrings) {
/*  905 */       _writeQuotedRaw(paramBigDecimal);
/*      */     } else {
/*  907 */       writeRaw(paramBigDecimal.toString());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNumber(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  915 */     _verifyValueWrite("write number");
/*  916 */     if (this._cfgNumbersAsStrings) {
/*  917 */       _writeQuotedRaw(paramString);
/*      */     } else {
/*  919 */       writeRaw(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private void _writeQuotedRaw(Object paramObject) throws IOException
/*      */   {
/*  925 */     if (this._outputTail >= this._outputEnd) {
/*  926 */       _flushBuffer();
/*      */     }
/*  928 */     this._outputBuffer[(this._outputTail++)] = 34;
/*  929 */     writeRaw(paramObject.toString());
/*  930 */     if (this._outputTail >= this._outputEnd) {
/*  931 */       _flushBuffer();
/*      */     }
/*  933 */     this._outputBuffer[(this._outputTail++)] = 34;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeBoolean(boolean paramBoolean)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  940 */     _verifyValueWrite("write boolean value");
/*  941 */     if (this._outputTail + 5 >= this._outputEnd) {
/*  942 */       _flushBuffer();
/*      */     }
/*  944 */     byte[] arrayOfByte = paramBoolean ? TRUE_BYTES : FALSE_BYTES;
/*  945 */     int i = arrayOfByte.length;
/*  946 */     System.arraycopy(arrayOfByte, 0, this._outputBuffer, this._outputTail, i);
/*  947 */     this._outputTail += i;
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeNull()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  954 */     _verifyValueWrite("write null value");
/*  955 */     _writeNull();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void _verifyValueWrite(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  968 */     int i = this._writeContext.writeValue();
/*  969 */     if (i == 5) {
/*  970 */       _reportError("Can not " + paramString + ", expecting field name");
/*      */     }
/*  972 */     if (this._cfgPrettyPrinter == null) {
/*      */       int j;
/*  974 */       switch (i) {
/*      */       case 1: 
/*  976 */         j = 44;
/*  977 */         break;
/*      */       case 2: 
/*  979 */         j = 58;
/*  980 */         break;
/*      */       case 3: 
/*  982 */         if (this._rootValueSeparator != null) {
/*  983 */           byte[] arrayOfByte = this._rootValueSeparator.asUnquotedUTF8();
/*  984 */           if (arrayOfByte.length > 0) {
/*  985 */             _writeBytes(arrayOfByte);
/*      */           }
/*      */         }
/*  988 */         return;
/*      */       case 0: 
/*      */       default: 
/*  991 */         return;
/*      */       }
/*  993 */       if (this._outputTail >= this._outputEnd) {
/*  994 */         _flushBuffer();
/*      */       }
/*  996 */       this._outputBuffer[this._outputTail] = j;
/*  997 */       this._outputTail += 1;
/*  998 */       return;
/*      */     }
/*      */     
/* 1001 */     _verifyPrettyValueWrite(paramString, i);
/*      */   }
/*      */   
/*      */ 
/*      */   protected final void _verifyPrettyValueWrite(String paramString, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1008 */     switch (paramInt) {
/*      */     case 1: 
/* 1010 */       this._cfgPrettyPrinter.writeArrayValueSeparator(this);
/* 1011 */       break;
/*      */     case 2: 
/* 1013 */       this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
/* 1014 */       break;
/*      */     case 3: 
/* 1016 */       this._cfgPrettyPrinter.writeRootValueSeparator(this);
/* 1017 */       break;
/*      */     
/*      */     case 0: 
/* 1020 */       if (this._writeContext.inArray()) {
/* 1021 */         this._cfgPrettyPrinter.beforeArrayValues(this);
/* 1022 */       } else if (this._writeContext.inObject()) {
/* 1023 */         this._cfgPrettyPrinter.beforeObjectEntries(this);
/*      */       }
/*      */       break;
/*      */     default: 
/* 1027 */       _throwInternal();
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
/*      */   public final void flush()
/*      */     throws IOException
/*      */   {
/* 1042 */     _flushBuffer();
/* 1043 */     if ((this._outputStream != null) && 
/* 1044 */       (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM))) {
/* 1045 */       this._outputStream.flush();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/* 1054 */     super.close();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1060 */     if ((this._outputBuffer != null) && (isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT))) {
/*      */       for (;;)
/*      */       {
/* 1063 */         JsonWriteContext localJsonWriteContext = getOutputContext();
/* 1064 */         if (localJsonWriteContext.inArray()) {
/* 1065 */           writeEndArray();
/* 1066 */         } else { if (!localJsonWriteContext.inObject()) break;
/* 1067 */           writeEndObject();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1073 */     _flushBuffer();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1081 */     if (this._outputStream != null) {
/* 1082 */       if ((this._ioContext.isResourceManaged()) || (isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET))) {
/* 1083 */         this._outputStream.close();
/* 1084 */       } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM))
/*      */       {
/* 1086 */         this._outputStream.flush();
/*      */       }
/*      */     }
/*      */     
/* 1090 */     _releaseBuffers();
/*      */   }
/*      */   
/*      */ 
/*      */   protected void _releaseBuffers()
/*      */   {
/* 1096 */     byte[] arrayOfByte = this._outputBuffer;
/* 1097 */     if ((arrayOfByte != null) && (this._bufferRecyclable)) {
/* 1098 */       this._outputBuffer = null;
/* 1099 */       this._ioContext.releaseWriteEncodingBuffer(arrayOfByte);
/*      */     }
/* 1101 */     char[] arrayOfChar = this._charBuffer;
/* 1102 */     if (arrayOfChar != null) {
/* 1103 */       this._charBuffer = null;
/* 1104 */       this._ioContext.releaseConcatBuffer(arrayOfChar);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void _writeBytes(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 1116 */     int i = paramArrayOfByte.length;
/* 1117 */     if (this._outputTail + i > this._outputEnd) {
/* 1118 */       _flushBuffer();
/*      */       
/* 1120 */       if (i > 512) {
/* 1121 */         this._outputStream.write(paramArrayOfByte, 0, i);
/* 1122 */         return;
/*      */       }
/*      */     }
/* 1125 */     System.arraycopy(paramArrayOfByte, 0, this._outputBuffer, this._outputTail, i);
/* 1126 */     this._outputTail += i;
/*      */   }
/*      */   
/*      */   private final void _writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*      */   {
/* 1131 */     if (this._outputTail + paramInt2 > this._outputEnd) {
/* 1132 */       _flushBuffer();
/*      */       
/* 1134 */       if (paramInt2 > 512) {
/* 1135 */         this._outputStream.write(paramArrayOfByte, paramInt1, paramInt2);
/* 1136 */         return;
/*      */       }
/*      */     }
/* 1139 */     System.arraycopy(paramArrayOfByte, paramInt1, this._outputBuffer, this._outputTail, paramInt2);
/* 1140 */     this._outputTail += paramInt2;
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
/*      */   private final void _writeStringSegments(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1159 */     int i = paramString.length();
/* 1160 */     int j = 0;
/* 1161 */     char[] arrayOfChar = this._charBuffer;
/*      */     
/* 1163 */     while (i > 0) {
/* 1164 */       int k = Math.min(this._outputMaxContiguous, i);
/* 1165 */       paramString.getChars(j, j + k, arrayOfChar, 0);
/* 1166 */       if (this._outputTail + k > this._outputEnd) {
/* 1167 */         _flushBuffer();
/*      */       }
/* 1169 */       _writeStringSegment(arrayOfChar, 0, k);
/* 1170 */       j += k;
/* 1171 */       i -= k;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void _writeStringSegments(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*      */     do
/*      */     {
/* 1185 */       int i = Math.min(this._outputMaxContiguous, paramInt2);
/* 1186 */       if (this._outputTail + i > this._outputEnd) {
/* 1187 */         _flushBuffer();
/*      */       }
/* 1189 */       _writeStringSegment(paramArrayOfChar, paramInt1, i);
/* 1190 */       paramInt1 += i;
/* 1191 */       paramInt2 -= i;
/* 1192 */     } while (paramInt2 > 0);
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
/*      */   private final void _writeStringSegment(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1215 */     paramInt2 += paramInt1;
/*      */     
/* 1217 */     int i = this._outputTail;
/* 1218 */     byte[] arrayOfByte = this._outputBuffer;
/* 1219 */     int[] arrayOfInt = this._outputEscapes;
/*      */     
/* 1221 */     while (paramInt1 < paramInt2) {
/* 1222 */       int j = paramArrayOfChar[paramInt1];
/*      */       
/* 1224 */       if ((j > 127) || (arrayOfInt[j] != 0)) {
/*      */         break;
/*      */       }
/* 1227 */       arrayOfByte[(i++)] = ((byte)j);
/* 1228 */       paramInt1++;
/*      */     }
/* 1230 */     this._outputTail = i;
/* 1231 */     if (paramInt1 < paramInt2)
/*      */     {
/* 1233 */       if (this._characterEscapes != null) {
/* 1234 */         _writeCustomStringSegment2(paramArrayOfChar, paramInt1, paramInt2);
/*      */       }
/* 1236 */       else if (this._maximumNonEscapedChar == 0) {
/* 1237 */         _writeStringSegment2(paramArrayOfChar, paramInt1, paramInt2);
/*      */       } else {
/* 1239 */         _writeStringSegmentASCII2(paramArrayOfChar, paramInt1, paramInt2);
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
/*      */   private final void _writeStringSegment2(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1253 */     if (this._outputTail + 6 * (paramInt2 - paramInt1) > this._outputEnd) {
/* 1254 */       _flushBuffer();
/*      */     }
/*      */     
/* 1257 */     int i = this._outputTail;
/*      */     
/* 1259 */     byte[] arrayOfByte = this._outputBuffer;
/* 1260 */     int[] arrayOfInt = this._outputEscapes;
/*      */     
/* 1262 */     while (paramInt1 < paramInt2) {
/* 1263 */       int j = paramArrayOfChar[(paramInt1++)];
/* 1264 */       if (j <= 127) {
/* 1265 */         if (arrayOfInt[j] == 0) {
/* 1266 */           arrayOfByte[(i++)] = ((byte)j);
/*      */         }
/*      */         else {
/* 1269 */           int k = arrayOfInt[j];
/* 1270 */           if (k > 0) {
/* 1271 */             arrayOfByte[(i++)] = 92;
/* 1272 */             arrayOfByte[(i++)] = ((byte)k);
/*      */           }
/*      */           else {
/* 1275 */             i = _writeGenericEscape(j, i);
/*      */           }
/*      */         }
/*      */       }
/* 1279 */       else if (j <= 2047) {
/* 1280 */         arrayOfByte[(i++)] = ((byte)(0xC0 | j >> 6));
/* 1281 */         arrayOfByte[(i++)] = ((byte)(0x80 | j & 0x3F));
/*      */       } else {
/* 1283 */         i = _outputMultiByteChar(j, i);
/*      */       }
/*      */     }
/* 1286 */     this._outputTail = i;
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
/*      */   private final void _writeStringSegmentASCII2(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1304 */     if (this._outputTail + 6 * (paramInt2 - paramInt1) > this._outputEnd) {
/* 1305 */       _flushBuffer();
/*      */     }
/*      */     
/* 1308 */     int i = this._outputTail;
/*      */     
/* 1310 */     byte[] arrayOfByte = this._outputBuffer;
/* 1311 */     int[] arrayOfInt = this._outputEscapes;
/* 1312 */     int j = this._maximumNonEscapedChar;
/*      */     
/* 1314 */     while (paramInt1 < paramInt2) {
/* 1315 */       int k = paramArrayOfChar[(paramInt1++)];
/* 1316 */       if (k <= 127) {
/* 1317 */         if (arrayOfInt[k] == 0) {
/* 1318 */           arrayOfByte[(i++)] = ((byte)k);
/*      */         }
/*      */         else {
/* 1321 */           int m = arrayOfInt[k];
/* 1322 */           if (m > 0) {
/* 1323 */             arrayOfByte[(i++)] = 92;
/* 1324 */             arrayOfByte[(i++)] = ((byte)m);
/*      */           }
/*      */           else {
/* 1327 */             i = _writeGenericEscape(k, i);
/*      */           }
/*      */         }
/*      */       }
/* 1331 */       else if (k > j) {
/* 1332 */         i = _writeGenericEscape(k, i);
/*      */ 
/*      */       }
/* 1335 */       else if (k <= 2047) {
/* 1336 */         arrayOfByte[(i++)] = ((byte)(0xC0 | k >> 6));
/* 1337 */         arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*      */       } else {
/* 1339 */         i = _outputMultiByteChar(k, i);
/*      */       }
/*      */     }
/* 1342 */     this._outputTail = i;
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
/*      */   private void _writeCustomStringSegment2(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1360 */     if (this._outputTail + 6 * (paramInt2 - paramInt1) > this._outputEnd) {
/* 1361 */       _flushBuffer();
/*      */     }
/* 1363 */     int i = this._outputTail;
/*      */     
/* 1365 */     byte[] arrayOfByte = this._outputBuffer;
/* 1366 */     int[] arrayOfInt = this._outputEscapes;
/*      */     
/* 1368 */     int j = this._maximumNonEscapedChar <= 0 ? 65535 : this._maximumNonEscapedChar;
/* 1369 */     CharacterEscapes localCharacterEscapes = this._characterEscapes;
/*      */     
/* 1371 */     while (paramInt1 < paramInt2) {
/* 1372 */       int k = paramArrayOfChar[(paramInt1++)];
/* 1373 */       if (k <= 127) {
/* 1374 */         if (arrayOfInt[k] == 0) {
/* 1375 */           arrayOfByte[(i++)] = ((byte)k);
/*      */         }
/*      */         else {
/* 1378 */           int m = arrayOfInt[k];
/* 1379 */           if (m > 0) {
/* 1380 */             arrayOfByte[(i++)] = 92;
/* 1381 */             arrayOfByte[(i++)] = ((byte)m);
/* 1382 */           } else if (m == -2) {
/* 1383 */             SerializableString localSerializableString2 = localCharacterEscapes.getEscapeSequence(k);
/* 1384 */             if (localSerializableString2 == null) {
/* 1385 */               _reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(k) + ", although was supposed to have one");
/*      */             }
/*      */             
/* 1388 */             i = _writeCustomEscape(arrayOfByte, i, localSerializableString2, paramInt2 - paramInt1);
/*      */           }
/*      */           else {
/* 1391 */             i = _writeGenericEscape(k, i);
/*      */           }
/*      */         }
/*      */       }
/* 1395 */       else if (k > j) {
/* 1396 */         i = _writeGenericEscape(k, i);
/*      */       }
/*      */       else {
/* 1399 */         SerializableString localSerializableString1 = localCharacterEscapes.getEscapeSequence(k);
/* 1400 */         if (localSerializableString1 != null) {
/* 1401 */           i = _writeCustomEscape(arrayOfByte, i, localSerializableString1, paramInt2 - paramInt1);
/*      */ 
/*      */         }
/* 1404 */         else if (k <= 2047) {
/* 1405 */           arrayOfByte[(i++)] = ((byte)(0xC0 | k >> 6));
/* 1406 */           arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*      */         } else {
/* 1408 */           i = _outputMultiByteChar(k, i);
/*      */         }
/*      */       } }
/* 1411 */     this._outputTail = i;
/*      */   }
/*      */   
/*      */   private int _writeCustomEscape(byte[] paramArrayOfByte, int paramInt1, SerializableString paramSerializableString, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1417 */     byte[] arrayOfByte = paramSerializableString.asUnquotedUTF8();
/* 1418 */     int i = arrayOfByte.length;
/* 1419 */     if (i > 6) {
/* 1420 */       return _handleLongCustomEscape(paramArrayOfByte, paramInt1, this._outputEnd, arrayOfByte, paramInt2);
/*      */     }
/*      */     
/* 1423 */     System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt1, i);
/* 1424 */     return paramInt1 + i;
/*      */   }
/*      */   
/*      */ 
/*      */   private int _handleLongCustomEscape(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1431 */     int i = paramArrayOfByte2.length;
/* 1432 */     if (paramInt1 + i > paramInt2) {
/* 1433 */       this._outputTail = paramInt1;
/* 1434 */       _flushBuffer();
/* 1435 */       paramInt1 = this._outputTail;
/* 1436 */       if (i > paramArrayOfByte1.length) {
/* 1437 */         this._outputStream.write(paramArrayOfByte2, 0, i);
/* 1438 */         return paramInt1;
/*      */       }
/* 1440 */       System.arraycopy(paramArrayOfByte2, 0, paramArrayOfByte1, paramInt1, i);
/* 1441 */       paramInt1 += i;
/*      */     }
/*      */     
/* 1444 */     if (paramInt1 + 6 * paramInt3 > paramInt2) {
/* 1445 */       _flushBuffer();
/* 1446 */       return this._outputTail;
/*      */     }
/* 1448 */     return paramInt1;
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
/*      */   private void _writeUTF8Segments(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*      */     do
/*      */     {
/* 1466 */       int i = Math.min(this._outputMaxContiguous, paramInt2);
/* 1467 */       _writeUTF8Segment(paramArrayOfByte, paramInt1, i);
/* 1468 */       paramInt1 += i;
/* 1469 */       paramInt2 -= i;
/* 1470 */     } while (paramInt2 > 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private void _writeUTF8Segment(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1477 */     int[] arrayOfInt = this._outputEscapes;
/*      */     
/* 1479 */     int i = paramInt1; for (int j = paramInt1 + paramInt2; i < j;)
/*      */     {
/* 1481 */       int k = paramArrayOfByte[(i++)];
/* 1482 */       if ((k >= 0) && (arrayOfInt[k] != 0)) {
/* 1483 */         _writeUTF8Segment2(paramArrayOfByte, paramInt1, paramInt2);
/* 1484 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1489 */     if (this._outputTail + paramInt2 > this._outputEnd) {
/* 1490 */       _flushBuffer();
/*      */     }
/* 1492 */     System.arraycopy(paramArrayOfByte, paramInt1, this._outputBuffer, this._outputTail, paramInt2);
/* 1493 */     this._outputTail += paramInt2;
/*      */   }
/*      */   
/*      */   private void _writeUTF8Segment2(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1499 */     int i = this._outputTail;
/*      */     
/*      */ 
/* 1502 */     if (i + paramInt2 * 6 > this._outputEnd) {
/* 1503 */       _flushBuffer();
/* 1504 */       i = this._outputTail;
/*      */     }
/*      */     
/* 1507 */     byte[] arrayOfByte = this._outputBuffer;
/* 1508 */     int[] arrayOfInt = this._outputEscapes;
/* 1509 */     paramInt2 += paramInt1;
/*      */     
/* 1511 */     while (paramInt1 < paramInt2) {
/* 1512 */       int j = paramArrayOfByte[(paramInt1++)];
/* 1513 */       int k = j;
/* 1514 */       if ((k < 0) || (arrayOfInt[k] == 0)) {
/* 1515 */         arrayOfByte[(i++)] = j;
/*      */       }
/*      */       else {
/* 1518 */         int m = arrayOfInt[k];
/* 1519 */         if (m > 0) {
/* 1520 */           arrayOfByte[(i++)] = 92;
/* 1521 */           arrayOfByte[(i++)] = ((byte)m);
/*      */         }
/*      */         else {
/* 1524 */           i = _writeGenericEscape(k, i);
/*      */         }
/*      */       } }
/* 1527 */     this._outputTail = i;
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
/*      */   protected void _writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1541 */     int i = paramInt2 - 3;
/*      */     
/* 1543 */     int j = this._outputEnd - 6;
/* 1544 */     int k = paramBase64Variant.getMaxLineLength() >> 2;
/*      */     
/*      */ 
/* 1547 */     while (paramInt1 <= i) {
/* 1548 */       if (this._outputTail > j) {
/* 1549 */         _flushBuffer();
/*      */       }
/*      */       
/* 1552 */       m = paramArrayOfByte[(paramInt1++)] << 8;
/* 1553 */       m |= paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 1554 */       m = m << 8 | paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 1555 */       this._outputTail = paramBase64Variant.encodeBase64Chunk(m, this._outputBuffer, this._outputTail);
/* 1556 */       k--; if (k <= 0)
/*      */       {
/* 1558 */         this._outputBuffer[(this._outputTail++)] = 92;
/* 1559 */         this._outputBuffer[(this._outputTail++)] = 110;
/* 1560 */         k = paramBase64Variant.getMaxLineLength() >> 2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1565 */     int m = paramInt2 - paramInt1;
/* 1566 */     if (m > 0) {
/* 1567 */       if (this._outputTail > j) {
/* 1568 */         _flushBuffer();
/*      */       }
/* 1570 */       int n = paramArrayOfByte[(paramInt1++)] << 16;
/* 1571 */       if (m == 2) {
/* 1572 */         n |= (paramArrayOfByte[(paramInt1++)] & 0xFF) << 8;
/*      */       }
/* 1574 */       this._outputTail = paramBase64Variant.encodeBase64Partial(n, m, this._outputBuffer, this._outputTail);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected int _writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1583 */     int i = 0;
/* 1584 */     int j = 0;
/* 1585 */     int k = -3;
/*      */     
/*      */ 
/* 1588 */     int m = this._outputEnd - 6;
/* 1589 */     int n = paramBase64Variant.getMaxLineLength() >> 2;
/*      */     int i1;
/* 1591 */     while (paramInt > 2) {
/* 1592 */       if (i > k) {
/* 1593 */         j = _readMore(paramInputStream, paramArrayOfByte, i, j, paramInt);
/* 1594 */         i = 0;
/* 1595 */         if (j < 3) {
/*      */           break;
/*      */         }
/* 1598 */         k = j - 3;
/*      */       }
/* 1600 */       if (this._outputTail > m) {
/* 1601 */         _flushBuffer();
/*      */       }
/* 1603 */       i1 = paramArrayOfByte[(i++)] << 8;
/* 1604 */       i1 |= paramArrayOfByte[(i++)] & 0xFF;
/* 1605 */       i1 = i1 << 8 | paramArrayOfByte[(i++)] & 0xFF;
/* 1606 */       paramInt -= 3;
/* 1607 */       this._outputTail = paramBase64Variant.encodeBase64Chunk(i1, this._outputBuffer, this._outputTail);
/* 1608 */       n--; if (n <= 0) {
/* 1609 */         this._outputBuffer[(this._outputTail++)] = 92;
/* 1610 */         this._outputBuffer[(this._outputTail++)] = 110;
/* 1611 */         n = paramBase64Variant.getMaxLineLength() >> 2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1616 */     if (paramInt > 0) {
/* 1617 */       j = _readMore(paramInputStream, paramArrayOfByte, i, j, paramInt);
/* 1618 */       i = 0;
/* 1619 */       if (j > 0) {
/* 1620 */         if (this._outputTail > m) {
/* 1621 */           _flushBuffer();
/*      */         }
/* 1623 */         i1 = paramArrayOfByte[(i++)] << 16;
/*      */         int i2;
/* 1625 */         if (i < j) {
/* 1626 */           i1 |= (paramArrayOfByte[i] & 0xFF) << 8;
/* 1627 */           i2 = 2;
/*      */         } else {
/* 1629 */           i2 = 1;
/*      */         }
/* 1631 */         this._outputTail = paramBase64Variant.encodeBase64Partial(i1, i2, this._outputBuffer, this._outputTail);
/* 1632 */         paramInt -= i2;
/*      */       }
/*      */     }
/* 1635 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected int _writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, byte[] paramArrayOfByte)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/* 1643 */     int i = 0;
/* 1644 */     int j = 0;
/* 1645 */     int k = -3;
/* 1646 */     int m = 0;
/*      */     
/*      */ 
/* 1649 */     int n = this._outputEnd - 6;
/* 1650 */     int i1 = paramBase64Variant.getMaxLineLength() >> 2;
/*      */     int i2;
/*      */     for (;;)
/*      */     {
/* 1654 */       if (i > k) {
/* 1655 */         j = _readMore(paramInputStream, paramArrayOfByte, i, j, paramArrayOfByte.length);
/* 1656 */         i = 0;
/* 1657 */         if (j < 3) {
/*      */           break;
/*      */         }
/* 1660 */         k = j - 3;
/*      */       }
/* 1662 */       if (this._outputTail > n) {
/* 1663 */         _flushBuffer();
/*      */       }
/*      */       
/* 1666 */       i2 = paramArrayOfByte[(i++)] << 8;
/* 1667 */       i2 |= paramArrayOfByte[(i++)] & 0xFF;
/* 1668 */       i2 = i2 << 8 | paramArrayOfByte[(i++)] & 0xFF;
/* 1669 */       m += 3;
/* 1670 */       this._outputTail = paramBase64Variant.encodeBase64Chunk(i2, this._outputBuffer, this._outputTail);
/* 1671 */       i1--; if (i1 <= 0) {
/* 1672 */         this._outputBuffer[(this._outputTail++)] = 92;
/* 1673 */         this._outputBuffer[(this._outputTail++)] = 110;
/* 1674 */         i1 = paramBase64Variant.getMaxLineLength() >> 2;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1679 */     if (i < j) {
/* 1680 */       if (this._outputTail > n) {
/* 1681 */         _flushBuffer();
/*      */       }
/* 1683 */       i2 = paramArrayOfByte[(i++)] << 16;
/* 1684 */       int i3 = 1;
/* 1685 */       if (i < j) {
/* 1686 */         i2 |= (paramArrayOfByte[i] & 0xFF) << 8;
/* 1687 */         i3 = 2;
/*      */       }
/* 1689 */       m += i3;
/* 1690 */       this._outputTail = paramBase64Variant.encodeBase64Partial(i2, i3, this._outputBuffer, this._outputTail);
/*      */     }
/* 1692 */     return m;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int _readMore(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*      */     throws IOException
/*      */   {
/* 1700 */     int i = 0;
/* 1701 */     while (paramInt1 < paramInt2) {
/* 1702 */       paramArrayOfByte[(i++)] = paramArrayOfByte[(paramInt1++)];
/*      */     }
/* 1704 */     paramInt1 = 0;
/* 1705 */     paramInt2 = i;
/* 1706 */     paramInt3 = Math.min(paramInt3, paramArrayOfByte.length);
/*      */     do
/*      */     {
/* 1709 */       int j = paramInt3 - paramInt2;
/* 1710 */       if (j == 0) {
/*      */         break;
/*      */       }
/* 1713 */       int k = paramInputStream.read(paramArrayOfByte, paramInt2, j);
/* 1714 */       if (k < 0) {
/* 1715 */         return paramInt2;
/*      */       }
/* 1717 */       paramInt2 += k;
/* 1718 */     } while (paramInt2 < 3);
/* 1719 */     return paramInt2;
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
/*      */   private int _outputRawMultiByteChar(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
/*      */     throws IOException
/*      */   {
/* 1737 */     if ((paramInt1 >= 55296) && 
/* 1738 */       (paramInt1 <= 57343))
/*      */     {
/* 1740 */       if (paramInt2 >= paramInt3) {
/* 1741 */         _reportError("Split surrogate on writeRaw() input (last character)");
/*      */       }
/* 1743 */       _outputSurrogates(paramInt1, paramArrayOfChar[paramInt2]);
/* 1744 */       return paramInt2 + 1;
/*      */     }
/*      */     
/* 1747 */     byte[] arrayOfByte = this._outputBuffer;
/* 1748 */     arrayOfByte[(this._outputTail++)] = ((byte)(0xE0 | paramInt1 >> 12));
/* 1749 */     arrayOfByte[(this._outputTail++)] = ((byte)(0x80 | paramInt1 >> 6 & 0x3F));
/* 1750 */     arrayOfByte[(this._outputTail++)] = ((byte)(0x80 | paramInt1 & 0x3F));
/* 1751 */     return paramInt2;
/*      */   }
/*      */   
/*      */   protected final void _outputSurrogates(int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 1757 */     int i = _decodeSurrogate(paramInt1, paramInt2);
/* 1758 */     if (this._outputTail + 4 > this._outputEnd) {
/* 1759 */       _flushBuffer();
/*      */     }
/* 1761 */     byte[] arrayOfByte = this._outputBuffer;
/* 1762 */     arrayOfByte[(this._outputTail++)] = ((byte)(0xF0 | i >> 18));
/* 1763 */     arrayOfByte[(this._outputTail++)] = ((byte)(0x80 | i >> 12 & 0x3F));
/* 1764 */     arrayOfByte[(this._outputTail++)] = ((byte)(0x80 | i >> 6 & 0x3F));
/* 1765 */     arrayOfByte[(this._outputTail++)] = ((byte)(0x80 | i & 0x3F));
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
/*      */   private int _outputMultiByteChar(int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 1780 */     byte[] arrayOfByte = this._outputBuffer;
/* 1781 */     if ((paramInt1 >= 55296) && (paramInt1 <= 57343)) {
/* 1782 */       arrayOfByte[(paramInt2++)] = 92;
/* 1783 */       arrayOfByte[(paramInt2++)] = 117;
/*      */       
/* 1785 */       arrayOfByte[(paramInt2++)] = HEX_CHARS[(paramInt1 >> 12 & 0xF)];
/* 1786 */       arrayOfByte[(paramInt2++)] = HEX_CHARS[(paramInt1 >> 8 & 0xF)];
/* 1787 */       arrayOfByte[(paramInt2++)] = HEX_CHARS[(paramInt1 >> 4 & 0xF)];
/* 1788 */       arrayOfByte[(paramInt2++)] = HEX_CHARS[(paramInt1 & 0xF)];
/*      */     } else {
/* 1790 */       arrayOfByte[(paramInt2++)] = ((byte)(0xE0 | paramInt1 >> 12));
/* 1791 */       arrayOfByte[(paramInt2++)] = ((byte)(0x80 | paramInt1 >> 6 & 0x3F));
/* 1792 */       arrayOfByte[(paramInt2++)] = ((byte)(0x80 | paramInt1 & 0x3F));
/*      */     }
/* 1794 */     return paramInt2;
/*      */   }
/*      */   
/*      */   protected final int _decodeSurrogate(int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 1800 */     if ((paramInt2 < 56320) || (paramInt2 > 57343)) {
/* 1801 */       String str = "Incomplete surrogate pair: first char 0x" + Integer.toHexString(paramInt1) + ", second 0x" + Integer.toHexString(paramInt2);
/* 1802 */       _reportError(str);
/*      */     }
/* 1804 */     int i = 65536 + (paramInt1 - 55296 << 10) + (paramInt2 - 56320);
/* 1805 */     return i;
/*      */   }
/*      */   
/*      */   private void _writeNull() throws IOException
/*      */   {
/* 1810 */     if (this._outputTail + 4 >= this._outputEnd) {
/* 1811 */       _flushBuffer();
/*      */     }
/* 1813 */     System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
/* 1814 */     this._outputTail += 4;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int _writeGenericEscape(int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 1825 */     byte[] arrayOfByte = this._outputBuffer;
/* 1826 */     arrayOfByte[(paramInt2++)] = 92;
/* 1827 */     arrayOfByte[(paramInt2++)] = 117;
/* 1828 */     if (paramInt1 > 255) {
/* 1829 */       int i = paramInt1 >> 8 & 0xFF;
/* 1830 */       arrayOfByte[(paramInt2++)] = HEX_CHARS[(i >> 4)];
/* 1831 */       arrayOfByte[(paramInt2++)] = HEX_CHARS[(i & 0xF)];
/* 1832 */       paramInt1 &= 0xFF;
/*      */     } else {
/* 1834 */       arrayOfByte[(paramInt2++)] = 48;
/* 1835 */       arrayOfByte[(paramInt2++)] = 48;
/*      */     }
/*      */     
/* 1838 */     arrayOfByte[(paramInt2++)] = HEX_CHARS[(paramInt1 >> 4)];
/* 1839 */     arrayOfByte[(paramInt2++)] = HEX_CHARS[(paramInt1 & 0xF)];
/* 1840 */     return paramInt2;
/*      */   }
/*      */   
/*      */   protected final void _flushBuffer() throws IOException
/*      */   {
/* 1845 */     int i = this._outputTail;
/* 1846 */     if (i > 0) {
/* 1847 */       this._outputTail = 0;
/* 1848 */       this._outputStream.write(this._outputBuffer, 0, i);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/UTF8JsonGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */