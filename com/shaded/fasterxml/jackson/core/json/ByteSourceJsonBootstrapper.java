/*     */ package com.shaded.fasterxml.jackson.core.json;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonEncoding;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*     */ import com.shaded.fasterxml.jackson.core.format.InputAccessor;
/*     */ import com.shaded.fasterxml.jackson.core.format.MatchStrength;
/*     */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*     */ import com.shaded.fasterxml.jackson.core.io.MergedStream;
/*     */ import com.shaded.fasterxml.jackson.core.io.UTF32Reader;
/*     */ import com.shaded.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
/*     */ import com.shaded.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.CharConversionException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteSourceJsonBootstrapper
/*     */ {
/*     */   static final byte UTF8_BOM_1 = -17;
/*     */   static final byte UTF8_BOM_2 = -69;
/*     */   static final byte UTF8_BOM_3 = -65;
/*     */   protected final IOContext _context;
/*     */   protected final InputStream _in;
/*     */   protected final byte[] _inputBuffer;
/*     */   private int _inputPtr;
/*     */   private int _inputEnd;
/*     */   private final boolean _bufferRecyclable;
/*     */   protected int _inputProcessed;
/*  74 */   protected boolean _bigEndian = true;
/*     */   
/*  76 */   protected int _bytesPerChar = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteSourceJsonBootstrapper(IOContext paramIOContext, InputStream paramInputStream)
/*     */   {
/*  86 */     this._context = paramIOContext;
/*  87 */     this._in = paramInputStream;
/*  88 */     this._inputBuffer = paramIOContext.allocReadIOBuffer();
/*  89 */     this._inputEnd = (this._inputPtr = 0);
/*  90 */     this._inputProcessed = 0;
/*  91 */     this._bufferRecyclable = true;
/*     */   }
/*     */   
/*     */   public ByteSourceJsonBootstrapper(IOContext paramIOContext, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  96 */     this._context = paramIOContext;
/*  97 */     this._in = null;
/*  98 */     this._inputBuffer = paramArrayOfByte;
/*  99 */     this._inputPtr = paramInt1;
/* 100 */     this._inputEnd = (paramInt1 + paramInt2);
/*     */     
/* 102 */     this._inputProcessed = (-paramInt1);
/* 103 */     this._bufferRecyclable = false;
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
/*     */   public JsonEncoding detectEncoding()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 120 */     int i = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/* 129 */     if (ensureLoaded(4)) {
/* 130 */       j = this._inputBuffer[this._inputPtr] << 24 | (this._inputBuffer[(this._inputPtr + 1)] & 0xFF) << 16 | (this._inputBuffer[(this._inputPtr + 2)] & 0xFF) << 8 | this._inputBuffer[(this._inputPtr + 3)] & 0xFF;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 135 */       if (handleBOM(j)) {
/* 136 */         i = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 144 */       else if (checkUTF32(j)) {
/* 145 */         i = 1;
/* 146 */       } else if (checkUTF16(j >>> 16)) {
/* 147 */         i = 1;
/*     */       }
/*     */     }
/* 150 */     else if (ensureLoaded(2)) {
/* 151 */       j = (this._inputBuffer[this._inputPtr] & 0xFF) << 8 | this._inputBuffer[(this._inputPtr + 1)] & 0xFF;
/*     */       
/* 153 */       if (checkUTF16(j)) {
/* 154 */         i = 1;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     JsonEncoding localJsonEncoding;
/*     */     
/* 161 */     if (i == 0) {
/* 162 */       localJsonEncoding = JsonEncoding.UTF8;
/*     */     } else {
/* 164 */       switch (this._bytesPerChar) {
/*     */       case 1: 
/* 166 */         localJsonEncoding = JsonEncoding.UTF8;
/* 167 */         break;
/*     */       case 2: 
/* 169 */         localJsonEncoding = this._bigEndian ? JsonEncoding.UTF16_BE : JsonEncoding.UTF16_LE;
/* 170 */         break;
/*     */       case 4: 
/* 172 */         localJsonEncoding = this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE;
/* 173 */         break;
/*     */       case 3: default: 
/* 175 */         throw new RuntimeException("Internal error");
/*     */       }
/*     */     }
/* 178 */     this._context.setEncoding(localJsonEncoding);
/* 179 */     return localJsonEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Reader constructReader()
/*     */     throws IOException
/*     */   {
/* 192 */     JsonEncoding localJsonEncoding = this._context.getEncoding();
/* 193 */     switch (localJsonEncoding) {
/*     */     case UTF32_BE: 
/*     */     case UTF32_LE: 
/* 196 */       return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case UTF16_BE: 
/*     */     case UTF16_LE: 
/*     */     case UTF8: 
/* 204 */       Object localObject = this._in;
/*     */       
/* 206 */       if (localObject == null) {
/* 207 */         localObject = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 212 */       else if (this._inputPtr < this._inputEnd) {
/* 213 */         localObject = new MergedStream(this._context, (InputStream)localObject, this._inputBuffer, this._inputPtr, this._inputEnd);
/*     */       }
/*     */       
/* 216 */       return new InputStreamReader((InputStream)localObject, localJsonEncoding.getJavaName());
/*     */     }
/*     */     
/* 219 */     throw new RuntimeException("Internal error");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonParser constructParser(int paramInt, ObjectCodec paramObjectCodec, BytesToNameCanonicalizer paramBytesToNameCanonicalizer, CharsToNameCanonicalizer paramCharsToNameCanonicalizer, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 227 */     JsonEncoding localJsonEncoding = detectEncoding();
/*     */     
/* 229 */     if (localJsonEncoding == JsonEncoding.UTF8)
/*     */     {
/*     */ 
/*     */ 
/* 233 */       if (paramBoolean1) {
/* 234 */         BytesToNameCanonicalizer localBytesToNameCanonicalizer = paramBytesToNameCanonicalizer.makeChild(paramBoolean1, paramBoolean2);
/* 235 */         return new UTF8StreamJsonParser(this._context, paramInt, this._in, paramObjectCodec, localBytesToNameCanonicalizer, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
/*     */       }
/*     */     }
/* 238 */     return new ReaderBasedJsonParser(this._context, paramInt, constructReader(), paramObjectCodec, paramCharsToNameCanonicalizer.makeChild(paramBoolean1, paramBoolean2));
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
/*     */ 
/*     */ 
/*     */   public static MatchStrength hasJSONFormat(InputAccessor paramInputAccessor)
/*     */     throws IOException
/*     */   {
/* 259 */     if (!paramInputAccessor.hasMoreBytes()) {
/* 260 */       return MatchStrength.INCONCLUSIVE;
/*     */     }
/* 262 */     byte b = paramInputAccessor.nextByte();
/*     */     
/* 264 */     if (b == -17) {
/* 265 */       if (!paramInputAccessor.hasMoreBytes()) {
/* 266 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 268 */       if (paramInputAccessor.nextByte() != -69) {
/* 269 */         return MatchStrength.NO_MATCH;
/*     */       }
/* 271 */       if (!paramInputAccessor.hasMoreBytes()) {
/* 272 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 274 */       if (paramInputAccessor.nextByte() != -65) {
/* 275 */         return MatchStrength.NO_MATCH;
/*     */       }
/* 277 */       if (!paramInputAccessor.hasMoreBytes()) {
/* 278 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 280 */       b = paramInputAccessor.nextByte();
/*     */     }
/*     */     
/* 283 */     int i = skipSpace(paramInputAccessor, b);
/* 284 */     if (i < 0) {
/* 285 */       return MatchStrength.INCONCLUSIVE;
/*     */     }
/*     */     
/* 288 */     if (i == 123)
/*     */     {
/* 290 */       i = skipSpace(paramInputAccessor);
/* 291 */       if (i < 0) {
/* 292 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 294 */       if ((i == 34) || (i == 125)) {
/* 295 */         return MatchStrength.SOLID_MATCH;
/*     */       }
/*     */       
/* 298 */       return MatchStrength.NO_MATCH;
/*     */     }
/*     */     
/*     */ 
/* 302 */     if (i == 91) {
/* 303 */       i = skipSpace(paramInputAccessor);
/* 304 */       if (i < 0) {
/* 305 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/*     */       
/* 308 */       if ((i == 93) || (i == 91)) {
/* 309 */         return MatchStrength.SOLID_MATCH;
/*     */       }
/* 311 */       return MatchStrength.SOLID_MATCH;
/*     */     }
/*     */     
/* 314 */     MatchStrength localMatchStrength = MatchStrength.WEAK_MATCH;
/*     */     
/*     */ 
/* 317 */     if (i == 34) {
/* 318 */       return localMatchStrength;
/*     */     }
/* 320 */     if ((i <= 57) && (i >= 48)) {
/* 321 */       return localMatchStrength;
/*     */     }
/* 323 */     if (i == 45) {
/* 324 */       i = skipSpace(paramInputAccessor);
/* 325 */       if (i < 0) {
/* 326 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 328 */       return (i <= 57) && (i >= 48) ? localMatchStrength : MatchStrength.NO_MATCH;
/*     */     }
/*     */     
/* 331 */     if (i == 110) {
/* 332 */       return tryMatch(paramInputAccessor, "ull", localMatchStrength);
/*     */     }
/* 334 */     if (i == 116) {
/* 335 */       return tryMatch(paramInputAccessor, "rue", localMatchStrength);
/*     */     }
/* 337 */     if (i == 102) {
/* 338 */       return tryMatch(paramInputAccessor, "alse", localMatchStrength);
/*     */     }
/* 340 */     return MatchStrength.NO_MATCH;
/*     */   }
/*     */   
/*     */   private static MatchStrength tryMatch(InputAccessor paramInputAccessor, String paramString, MatchStrength paramMatchStrength)
/*     */     throws IOException
/*     */   {
/* 346 */     int i = 0; for (int j = paramString.length(); i < j; i++) {
/* 347 */       if (!paramInputAccessor.hasMoreBytes()) {
/* 348 */         return MatchStrength.INCONCLUSIVE;
/*     */       }
/* 350 */       if (paramInputAccessor.nextByte() != paramString.charAt(i)) {
/* 351 */         return MatchStrength.NO_MATCH;
/*     */       }
/*     */     }
/* 354 */     return paramMatchStrength;
/*     */   }
/*     */   
/*     */   private static int skipSpace(InputAccessor paramInputAccessor) throws IOException
/*     */   {
/* 359 */     if (!paramInputAccessor.hasMoreBytes()) {
/* 360 */       return -1;
/*     */     }
/* 362 */     return skipSpace(paramInputAccessor, paramInputAccessor.nextByte());
/*     */   }
/*     */   
/*     */   private static int skipSpace(InputAccessor paramInputAccessor, byte paramByte) throws IOException
/*     */   {
/*     */     for (;;) {
/* 368 */       int i = paramByte & 0xFF;
/* 369 */       if ((i != 32) && (i != 13) && (i != 10) && (i != 9)) {
/* 370 */         return i;
/*     */       }
/* 372 */       if (!paramInputAccessor.hasMoreBytes()) {
/* 373 */         return -1;
/*     */       }
/* 375 */       paramByte = paramInputAccessor.nextByte();
/* 376 */       i = paramByte & 0xFF;
/*     */     }
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
/*     */   private boolean handleBOM(int paramInt)
/*     */     throws IOException
/*     */   {
/* 396 */     switch (paramInt) {
/*     */     case 65279: 
/* 398 */       this._bigEndian = true;
/* 399 */       this._inputPtr += 4;
/* 400 */       this._bytesPerChar = 4;
/* 401 */       return true;
/*     */     case -131072: 
/* 403 */       this._inputPtr += 4;
/* 404 */       this._bytesPerChar = 4;
/* 405 */       this._bigEndian = false;
/* 406 */       return true;
/*     */     case 65534: 
/* 408 */       reportWeirdUCS4("2143");
/*     */     case -16842752: 
/* 410 */       reportWeirdUCS4("3412");
/*     */     }
/*     */     
/* 413 */     int i = paramInt >>> 16;
/* 414 */     if (i == 65279) {
/* 415 */       this._inputPtr += 2;
/* 416 */       this._bytesPerChar = 2;
/* 417 */       this._bigEndian = true;
/* 418 */       return true;
/*     */     }
/* 420 */     if (i == 65534) {
/* 421 */       this._inputPtr += 2;
/* 422 */       this._bytesPerChar = 2;
/* 423 */       this._bigEndian = false;
/* 424 */       return true;
/*     */     }
/*     */     
/* 427 */     if (paramInt >>> 8 == 15711167) {
/* 428 */       this._inputPtr += 3;
/* 429 */       this._bytesPerChar = 1;
/* 430 */       this._bigEndian = true;
/* 431 */       return true;
/*     */     }
/* 433 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean checkUTF32(int paramInt)
/*     */     throws IOException
/*     */   {
/* 442 */     if (paramInt >> 8 == 0) {
/* 443 */       this._bigEndian = true;
/* 444 */     } else if ((paramInt & 0xFFFFFF) == 0) {
/* 445 */       this._bigEndian = false;
/* 446 */     } else if ((paramInt & 0xFF00FFFF) == 0) {
/* 447 */       reportWeirdUCS4("3412");
/* 448 */     } else if ((paramInt & 0xFFFF00FF) == 0) {
/* 449 */       reportWeirdUCS4("2143");
/*     */     }
/*     */     else {
/* 452 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 456 */     this._bytesPerChar = 4;
/* 457 */     return true;
/*     */   }
/*     */   
/*     */   private boolean checkUTF16(int paramInt)
/*     */   {
/* 462 */     if ((paramInt & 0xFF00) == 0) {
/* 463 */       this._bigEndian = true;
/* 464 */     } else if ((paramInt & 0xFF) == 0) {
/* 465 */       this._bigEndian = false;
/*     */     } else {
/* 467 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 471 */     this._bytesPerChar = 2;
/* 472 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void reportWeirdUCS4(String paramString)
/*     */     throws IOException
/*     */   {
/* 484 */     throw new CharConversionException("Unsupported UCS-4 endianness (" + paramString + ") detected");
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
/*     */   protected boolean ensureLoaded(int paramInt)
/*     */     throws IOException
/*     */   {
/* 499 */     int i = this._inputEnd - this._inputPtr;
/* 500 */     while (i < paramInt)
/*     */     {
/*     */       int j;
/* 503 */       if (this._in == null) {
/* 504 */         j = -1;
/*     */       } else {
/* 506 */         j = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
/*     */       }
/* 508 */       if (j < 1) {
/* 509 */         return false;
/*     */       }
/* 511 */       this._inputEnd += j;
/* 512 */       i += j;
/*     */     }
/* 514 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/ByteSourceJsonBootstrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */