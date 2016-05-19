/*     */ package com.shaded.fasterxml.jackson.core.base;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonStreamContext;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.core.io.NumberInput;
/*     */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*     */ import com.shaded.fasterxml.jackson.core.util.VersionUtil;
/*     */ import java.io.IOException;
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
/*     */ public abstract class ParserMinimalBase
/*     */   extends JsonParser
/*     */ {
/*     */   protected static final int INT_TAB = 9;
/*     */   protected static final int INT_LF = 10;
/*     */   protected static final int INT_CR = 13;
/*     */   protected static final int INT_SPACE = 32;
/*     */   protected static final int INT_LBRACKET = 91;
/*     */   protected static final int INT_RBRACKET = 93;
/*     */   protected static final int INT_LCURLY = 123;
/*     */   protected static final int INT_RCURLY = 125;
/*     */   protected static final int INT_QUOTE = 34;
/*     */   protected static final int INT_BACKSLASH = 92;
/*     */   protected static final int INT_SLASH = 47;
/*     */   protected static final int INT_COLON = 58;
/*     */   protected static final int INT_COMMA = 44;
/*     */   protected static final int INT_ASTERISK = 42;
/*     */   protected static final int INT_APOSTROPHE = 39;
/*     */   protected static final int INT_b = 98;
/*     */   protected static final int INT_f = 102;
/*     */   protected static final int INT_n = 110;
/*     */   protected static final int INT_r = 114;
/*     */   protected static final int INT_t = 116;
/*     */   protected static final int INT_u = 117;
/*     */   protected JsonToken _currToken;
/*     */   protected JsonToken _lastClearedToken;
/*     */   
/*     */   protected ParserMinimalBase() {}
/*     */   
/*     */   protected ParserMinimalBase(int paramInt)
/*     */   {
/*  80 */     super(paramInt);
/*     */   }
/*     */   
/*     */   public Version version()
/*     */   {
/*  85 */     return VersionUtil.versionFor(getClass());
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
/*     */   public abstract JsonToken nextToken()
/*     */     throws IOException, JsonParseException;
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
/*     */   public JsonToken getCurrentToken()
/*     */   {
/* 112 */     return this._currToken;
/*     */   }
/*     */   
/*     */   public boolean hasCurrentToken()
/*     */   {
/* 117 */     return this._currToken != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken nextValue()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 128 */     JsonToken localJsonToken = nextToken();
/* 129 */     if (localJsonToken == JsonToken.FIELD_NAME) {
/* 130 */       localJsonToken = nextToken();
/*     */     }
/* 132 */     return localJsonToken;
/*     */   }
/*     */   
/*     */   public JsonParser skipChildren()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 138 */     if ((this._currToken != JsonToken.START_OBJECT) && (this._currToken != JsonToken.START_ARRAY))
/*     */     {
/* 140 */       return this;
/*     */     }
/* 142 */     int i = 1;
/*     */     
/*     */ 
/*     */ 
/*     */     for (;;)
/*     */     {
/* 148 */       JsonToken localJsonToken = nextToken();
/* 149 */       if (localJsonToken == null) {
/* 150 */         _handleEOF();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 155 */         return this;
/*     */       }
/* 157 */       switch (localJsonToken) {
/*     */       case START_OBJECT: 
/*     */       case START_ARRAY: 
/* 160 */         i++;
/* 161 */         break;
/*     */       case END_OBJECT: 
/*     */       case END_ARRAY: 
/* 164 */         i--; if (i == 0) {
/* 165 */           return this;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void _handleEOF()
/*     */     throws JsonParseException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getCurrentName()
/*     */     throws IOException, JsonParseException;
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract void close()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean isClosed();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract JsonStreamContext getParsingContext();
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearCurrentToken()
/*     */   {
/* 205 */     if (this._currToken != null) {
/* 206 */       this._lastClearedToken = this._currToken;
/* 207 */       this._currToken = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public JsonToken getLastClearedToken()
/*     */   {
/* 213 */     return this._lastClearedToken;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void overrideCurrentName(String paramString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getText()
/*     */     throws IOException, JsonParseException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract char[] getTextCharacters()
/*     */     throws IOException, JsonParseException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean hasTextCharacters();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int getTextLength()
/*     */     throws IOException, JsonParseException;
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract int getTextOffset()
/*     */     throws IOException, JsonParseException;
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract byte[] getBinaryValue(Base64Variant paramBase64Variant)
/*     */     throws IOException, JsonParseException;
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getValueAsBoolean(boolean paramBoolean)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 259 */     if (this._currToken != null) { Object localObject;
/* 260 */       switch (this._currToken) {
/*     */       case VALUE_NUMBER_INT: 
/* 262 */         return getIntValue() != 0;
/*     */       case VALUE_TRUE: 
/* 264 */         return true;
/*     */       case VALUE_FALSE: 
/*     */       case VALUE_NULL: 
/* 267 */         return false;
/*     */       
/*     */       case VALUE_EMBEDDED_OBJECT: 
/* 270 */         localObject = getEmbeddedObject();
/* 271 */         if ((localObject instanceof Boolean)) {
/* 272 */           return ((Boolean)localObject).booleanValue();
/*     */         }
/*     */       
/*     */       case VALUE_STRING: 
/* 276 */         localObject = getText().trim();
/* 277 */         if ("true".equals(localObject)) {
/* 278 */           return true;
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 283 */     return paramBoolean;
/*     */   }
/*     */   
/*     */   public int getValueAsInt(int paramInt)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 289 */     if (this._currToken != null) {
/* 290 */       switch (this._currToken) {
/*     */       case VALUE_NUMBER_INT: 
/*     */       case VALUE_NUMBER_FLOAT: 
/* 293 */         return getIntValue();
/*     */       case VALUE_TRUE: 
/* 295 */         return 1;
/*     */       case VALUE_FALSE: 
/*     */       case VALUE_NULL: 
/* 298 */         return 0;
/*     */       case VALUE_STRING: 
/* 300 */         return NumberInput.parseAsInt(getText(), paramInt);
/*     */       
/*     */       case VALUE_EMBEDDED_OBJECT: 
/* 303 */         Object localObject = getEmbeddedObject();
/* 304 */         if ((localObject instanceof Number)) {
/* 305 */           return ((Number)localObject).intValue();
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 310 */     return paramInt;
/*     */   }
/*     */   
/*     */   public long getValueAsLong(long paramLong)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 316 */     if (this._currToken != null) {
/* 317 */       switch (this._currToken) {
/*     */       case VALUE_NUMBER_INT: 
/*     */       case VALUE_NUMBER_FLOAT: 
/* 320 */         return getLongValue();
/*     */       case VALUE_TRUE: 
/* 322 */         return 1L;
/*     */       case VALUE_FALSE: 
/*     */       case VALUE_NULL: 
/* 325 */         return 0L;
/*     */       case VALUE_STRING: 
/* 327 */         return NumberInput.parseAsLong(getText(), paramLong);
/*     */       
/*     */       case VALUE_EMBEDDED_OBJECT: 
/* 330 */         Object localObject = getEmbeddedObject();
/* 331 */         if ((localObject instanceof Number)) {
/* 332 */           return ((Number)localObject).longValue();
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 337 */     return paramLong;
/*     */   }
/*     */   
/*     */   public double getValueAsDouble(double paramDouble)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 343 */     if (this._currToken != null) {
/* 344 */       switch (this._currToken) {
/*     */       case VALUE_NUMBER_INT: 
/*     */       case VALUE_NUMBER_FLOAT: 
/* 347 */         return getDoubleValue();
/*     */       case VALUE_TRUE: 
/* 349 */         return 1.0D;
/*     */       case VALUE_FALSE: 
/*     */       case VALUE_NULL: 
/* 352 */         return 0.0D;
/*     */       case VALUE_STRING: 
/* 354 */         return NumberInput.parseAsDouble(getText(), paramDouble);
/*     */       
/*     */       case VALUE_EMBEDDED_OBJECT: 
/* 357 */         Object localObject = getEmbeddedObject();
/* 358 */         if ((localObject instanceof Number)) {
/* 359 */           return ((Number)localObject).doubleValue();
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 364 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public String getValueAsString(String paramString)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 370 */     if ((this._currToken != JsonToken.VALUE_STRING) && (
/* 371 */       (this._currToken == null) || (this._currToken == JsonToken.VALUE_NULL) || (!this._currToken.isScalarValue()))) {
/* 372 */       return paramString;
/*     */     }
/*     */     
/* 375 */     return getText();
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
/*     */   protected void _decodeBase64(String paramString, ByteArrayBuilder paramByteArrayBuilder, Base64Variant paramBase64Variant)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 391 */     int i = 0;
/* 392 */     int j = paramString.length();
/*     */     
/*     */ 
/* 395 */     while (i < j)
/*     */     {
/*     */       do
/*     */       {
/* 399 */         c = paramString.charAt(i++);
/* 400 */         if (i >= j) {
/*     */           break;
/*     */         }
/* 403 */       } while (c <= ' ');
/* 404 */       int k = paramBase64Variant.decodeBase64Char(c);
/* 405 */       if (k < 0) {
/* 406 */         _reportInvalidBase64(paramBase64Variant, c, 0, null);
/*     */       }
/* 408 */       int m = k;
/*     */       
/* 410 */       if (i >= j) {
/* 411 */         _reportBase64EOF();
/*     */       }
/* 413 */       char c = paramString.charAt(i++);
/* 414 */       k = paramBase64Variant.decodeBase64Char(c);
/* 415 */       if (k < 0) {
/* 416 */         _reportInvalidBase64(paramBase64Variant, c, 1, null);
/*     */       }
/* 418 */       m = m << 6 | k;
/*     */       
/* 420 */       if (i >= j)
/*     */       {
/* 422 */         if (!paramBase64Variant.usesPadding()) {
/* 423 */           m >>= 4;
/* 424 */           paramByteArrayBuilder.append(m);
/* 425 */           break;
/*     */         }
/* 427 */         _reportBase64EOF();
/*     */       }
/* 429 */       c = paramString.charAt(i++);
/* 430 */       k = paramBase64Variant.decodeBase64Char(c);
/*     */       
/*     */ 
/* 433 */       if (k < 0) {
/* 434 */         if (k != -2) {
/* 435 */           _reportInvalidBase64(paramBase64Variant, c, 2, null);
/*     */         }
/*     */         
/* 438 */         if (i >= j) {
/* 439 */           _reportBase64EOF();
/*     */         }
/* 441 */         c = paramString.charAt(i++);
/* 442 */         if (!paramBase64Variant.usesPaddingChar(c)) {
/* 443 */           _reportInvalidBase64(paramBase64Variant, c, 3, "expected padding character '" + paramBase64Variant.getPaddingChar() + "'");
/*     */         }
/*     */         
/* 446 */         m >>= 4;
/* 447 */         paramByteArrayBuilder.append(m);
/*     */       }
/*     */       else
/*     */       {
/* 451 */         m = m << 6 | k;
/*     */         
/* 453 */         if (i >= j)
/*     */         {
/* 455 */           if (!paramBase64Variant.usesPadding()) {
/* 456 */             m >>= 2;
/* 457 */             paramByteArrayBuilder.appendTwoBytes(m);
/* 458 */             break;
/*     */           }
/* 460 */           _reportBase64EOF();
/*     */         }
/* 462 */         c = paramString.charAt(i++);
/* 463 */         k = paramBase64Variant.decodeBase64Char(c);
/* 464 */         if (k < 0) {
/* 465 */           if (k != -2) {
/* 466 */             _reportInvalidBase64(paramBase64Variant, c, 3, null);
/*     */           }
/* 468 */           m >>= 2;
/* 469 */           paramByteArrayBuilder.appendTwoBytes(m);
/*     */         }
/*     */         else {
/* 472 */           m = m << 6 | k;
/* 473 */           paramByteArrayBuilder.appendThreeBytes(m);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void _reportInvalidBase64(Base64Variant paramBase64Variant, char paramChar, int paramInt, String paramString)
/*     */     throws JsonParseException
/*     */   {
/*     */     String str;
/*     */     
/* 486 */     if (paramChar <= ' ') {
/* 487 */       str = "Illegal white space character (code 0x" + Integer.toHexString(paramChar) + ") as character #" + (paramInt + 1) + " of 4-char base64 unit: can only used between units";
/* 488 */     } else if (paramBase64Variant.usesPaddingChar(paramChar)) {
/* 489 */       str = "Unexpected padding character ('" + paramBase64Variant.getPaddingChar() + "') as character #" + (paramInt + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
/* 490 */     } else if ((!Character.isDefined(paramChar)) || (Character.isISOControl(paramChar)))
/*     */     {
/* 492 */       str = "Illegal character (code 0x" + Integer.toHexString(paramChar) + ") in base64 content";
/*     */     } else {
/* 494 */       str = "Illegal character '" + paramChar + "' (code 0x" + Integer.toHexString(paramChar) + ") in base64 content";
/*     */     }
/* 496 */     if (paramString != null) {
/* 497 */       str = str + ": " + paramString;
/*     */     }
/* 499 */     throw _constructError(str);
/*     */   }
/*     */   
/*     */   protected void _reportBase64EOF() throws JsonParseException {
/* 503 */     throw _constructError("Unexpected end-of-String in base64 content");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _reportUnexpectedChar(int paramInt, String paramString)
/*     */     throws JsonParseException
/*     */   {
/* 516 */     String str = "Unexpected character (" + _getCharDesc(paramInt) + ")";
/* 517 */     if (paramString != null) {
/* 518 */       str = str + ": " + paramString;
/*     */     }
/* 520 */     _reportError(str);
/*     */   }
/*     */   
/*     */   protected void _reportInvalidEOF()
/*     */     throws JsonParseException
/*     */   {
/* 526 */     _reportInvalidEOF(" in " + this._currToken);
/*     */   }
/*     */   
/*     */   protected void _reportInvalidEOF(String paramString)
/*     */     throws JsonParseException
/*     */   {
/* 532 */     _reportError("Unexpected end-of-input" + paramString);
/*     */   }
/*     */   
/*     */   protected void _reportInvalidEOFInValue() throws JsonParseException
/*     */   {
/* 537 */     _reportInvalidEOF(" in a value");
/*     */   }
/*     */   
/*     */   protected void _throwInvalidSpace(int paramInt)
/*     */     throws JsonParseException
/*     */   {
/* 543 */     int i = (char)paramInt;
/* 544 */     String str = "Illegal character (" + _getCharDesc(i) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens";
/* 545 */     _reportError(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _throwUnquotedSpace(int paramInt, String paramString)
/*     */     throws JsonParseException
/*     */   {
/* 557 */     if ((!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS)) || (paramInt >= 32)) {
/* 558 */       int i = (char)paramInt;
/* 559 */       String str = "Illegal unquoted character (" + _getCharDesc(i) + "): has to be escaped using backslash to be included in " + paramString;
/* 560 */       _reportError(str);
/*     */     }
/*     */   }
/*     */   
/*     */   protected char _handleUnrecognizedCharacterEscape(char paramChar)
/*     */     throws JsonProcessingException
/*     */   {
/* 567 */     if (isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
/* 568 */       return paramChar;
/*     */     }
/*     */     
/* 571 */     if ((paramChar == '\'') && (isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES))) {
/* 572 */       return paramChar;
/*     */     }
/* 574 */     _reportError("Unrecognized character escape " + _getCharDesc(paramChar));
/* 575 */     return paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static final String _getCharDesc(int paramInt)
/*     */   {
/* 586 */     char c = (char)paramInt;
/* 587 */     if (Character.isISOControl(c)) {
/* 588 */       return "(CTRL-CHAR, code " + paramInt + ")";
/*     */     }
/* 590 */     if (paramInt > 255) {
/* 591 */       return "'" + c + "' (code " + paramInt + " / 0x" + Integer.toHexString(paramInt) + ")";
/*     */     }
/* 593 */     return "'" + c + "' (code " + paramInt + ")";
/*     */   }
/*     */   
/*     */   protected final void _reportError(String paramString)
/*     */     throws JsonParseException
/*     */   {
/* 599 */     throw _constructError(paramString);
/*     */   }
/*     */   
/*     */   protected final void _wrapError(String paramString, Throwable paramThrowable)
/*     */     throws JsonParseException
/*     */   {
/* 605 */     throw _constructError(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */   protected final void _throwInternal() {}
/*     */   
/*     */ 
/*     */   protected final JsonParseException _constructError(String paramString, Throwable paramThrowable)
/*     */   {
/* 614 */     return new JsonParseException(paramString, getCurrentLocation(), paramThrowable);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/base/ParserMinimalBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */