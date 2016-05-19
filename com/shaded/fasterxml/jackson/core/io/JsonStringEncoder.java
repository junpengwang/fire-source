/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*     */ import com.shaded.fasterxml.jackson.core.util.TextBuffer;
/*     */ import java.lang.ref.SoftReference;
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
/*     */ public final class JsonStringEncoder
/*     */ {
/*  19 */   private static final char[] HEX_CHARS = ;
/*     */   
/*  21 */   private static final byte[] HEX_BYTES = CharTypes.copyHexBytes();
/*     */   
/*     */   private static final int SURR1_FIRST = 55296;
/*     */   
/*     */   private static final int SURR1_LAST = 56319;
/*     */   
/*     */   private static final int SURR2_FIRST = 56320;
/*     */   
/*     */   private static final int SURR2_LAST = 57343;
/*     */   
/*     */   private static final int INT_BACKSLASH = 92;
/*     */   
/*     */   private static final int INT_U = 117;
/*     */   
/*     */   private static final int INT_0 = 48;
/*     */   
/*  37 */   protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder = new ThreadLocal();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected TextBuffer _textBuffer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ByteArrayBuilder _byteBuilder;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final char[] _quoteBuffer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonStringEncoder()
/*     */   {
/*  65 */     this._quoteBuffer = new char[6];
/*  66 */     this._quoteBuffer[0] = '\\';
/*  67 */     this._quoteBuffer[2] = '0';
/*  68 */     this._quoteBuffer[3] = '0';
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonStringEncoder getInstance()
/*     */   {
/*  77 */     SoftReference localSoftReference = (SoftReference)_threadEncoder.get();
/*  78 */     JsonStringEncoder localJsonStringEncoder = localSoftReference == null ? null : (JsonStringEncoder)localSoftReference.get();
/*     */     
/*  80 */     if (localJsonStringEncoder == null) {
/*  81 */       localJsonStringEncoder = new JsonStringEncoder();
/*  82 */       _threadEncoder.set(new SoftReference(localJsonStringEncoder));
/*     */     }
/*  84 */     return localJsonStringEncoder;
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
/*     */   public char[] quoteAsString(String paramString)
/*     */   {
/*  99 */     TextBuffer localTextBuffer = this._textBuffer;
/* 100 */     if (localTextBuffer == null)
/*     */     {
/* 102 */       this._textBuffer = (localTextBuffer = new TextBuffer(null));
/*     */     }
/* 104 */     char[] arrayOfChar = localTextBuffer.emptyAndGetCurrentSegment();
/* 105 */     int[] arrayOfInt = CharTypes.get7BitOutputEscapes();
/* 106 */     int i = arrayOfInt.length;
/* 107 */     int j = 0;
/* 108 */     int k = paramString.length();
/* 109 */     int m = 0;
/*     */     
/*     */ 
/* 112 */     while (j < k)
/*     */     {
/*     */       for (;;) {
/* 115 */         n = paramString.charAt(j);
/* 116 */         if ((n < i) && (arrayOfInt[n] != 0)) {
/*     */           break;
/*     */         }
/* 119 */         if (m >= arrayOfChar.length) {
/* 120 */           arrayOfChar = localTextBuffer.finishCurrentSegment();
/* 121 */           m = 0;
/*     */         }
/* 123 */         arrayOfChar[(m++)] = n;
/* 124 */         j++; if (j >= k) {
/*     */           break label261;
/*     */         }
/*     */       }
/*     */       
/* 129 */       int n = paramString.charAt(j++);
/* 130 */       int i1 = arrayOfInt[n];
/* 131 */       int i2 = i1 < 0 ? _appendNumericEscape(n, this._quoteBuffer) : _appendNamedEscape(i1, this._quoteBuffer);
/*     */       
/*     */ 
/*     */ 
/* 135 */       if (m + i2 > arrayOfChar.length) {
/* 136 */         int i3 = arrayOfChar.length - m;
/* 137 */         if (i3 > 0) {
/* 138 */           System.arraycopy(this._quoteBuffer, 0, arrayOfChar, m, i3);
/*     */         }
/* 140 */         arrayOfChar = localTextBuffer.finishCurrentSegment();
/* 141 */         int i4 = i2 - i3;
/* 142 */         System.arraycopy(this._quoteBuffer, i3, arrayOfChar, 0, i4);
/* 143 */         m = i4;
/*     */       } else {
/* 145 */         System.arraycopy(this._quoteBuffer, 0, arrayOfChar, m, i2);
/* 146 */         m += i2;
/*     */       } }
/*     */     label261:
/* 149 */     localTextBuffer.setCurrentLength(m);
/* 150 */     return localTextBuffer.contentsAsArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] quoteAsUTF8(String paramString)
/*     */   {
/* 159 */     ByteArrayBuilder localByteArrayBuilder = this._byteBuilder;
/* 160 */     if (localByteArrayBuilder == null)
/*     */     {
/* 162 */       this._byteBuilder = (localByteArrayBuilder = new ByteArrayBuilder(null));
/*     */     }
/* 164 */     int i = 0;
/* 165 */     int j = paramString.length();
/* 166 */     int k = 0;
/* 167 */     byte[] arrayOfByte = localByteArrayBuilder.resetAndGetFirstSegment();
/*     */     
/*     */ 
/* 170 */     while (i < j) {
/* 171 */       int[] arrayOfInt = CharTypes.get7BitOutputEscapes();
/*     */       
/*     */       for (;;)
/*     */       {
/* 175 */         m = paramString.charAt(i);
/* 176 */         if ((m > 127) || (arrayOfInt[m] != 0)) {
/*     */           break;
/*     */         }
/* 179 */         if (k >= arrayOfByte.length) {
/* 180 */           arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 181 */           k = 0;
/*     */         }
/* 183 */         arrayOfByte[(k++)] = ((byte)m);
/* 184 */         i++; if (i >= j) {
/*     */           break label492;
/*     */         }
/*     */       }
/* 188 */       if (k >= arrayOfByte.length) {
/* 189 */         arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 190 */         k = 0;
/*     */       }
/*     */       
/* 193 */       int m = paramString.charAt(i++);
/* 194 */       if (m <= 127) {
/* 195 */         int n = arrayOfInt[m];
/*     */         
/* 197 */         k = _appendByteEscape(m, n, localByteArrayBuilder, k);
/* 198 */         arrayOfByte = localByteArrayBuilder.getCurrentSegment();
/*     */       } else {
/* 200 */         if (m <= 2047) {
/* 201 */           arrayOfByte[(k++)] = ((byte)(0xC0 | m >> 6));
/* 202 */           m = 0x80 | m & 0x3F;
/*     */ 
/*     */         }
/* 205 */         else if ((m < 55296) || (m > 57343)) {
/* 206 */           arrayOfByte[(k++)] = ((byte)(0xE0 | m >> 12));
/* 207 */           if (k >= arrayOfByte.length) {
/* 208 */             arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 209 */             k = 0;
/*     */           }
/* 211 */           arrayOfByte[(k++)] = ((byte)(0x80 | m >> 6 & 0x3F));
/* 212 */           m = 0x80 | m & 0x3F;
/*     */         } else {
/* 214 */           if (m > 56319) {
/* 215 */             _illegalSurrogate(m);
/*     */           }
/*     */           
/* 218 */           if (i >= j) {
/* 219 */             _illegalSurrogate(m);
/*     */           }
/* 221 */           m = _convertSurrogate(m, paramString.charAt(i++));
/* 222 */           if (m > 1114111) {
/* 223 */             _illegalSurrogate(m);
/*     */           }
/* 225 */           arrayOfByte[(k++)] = ((byte)(0xF0 | m >> 18));
/* 226 */           if (k >= arrayOfByte.length) {
/* 227 */             arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 228 */             k = 0;
/*     */           }
/* 230 */           arrayOfByte[(k++)] = ((byte)(0x80 | m >> 12 & 0x3F));
/* 231 */           if (k >= arrayOfByte.length) {
/* 232 */             arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 233 */             k = 0;
/*     */           }
/* 235 */           arrayOfByte[(k++)] = ((byte)(0x80 | m >> 6 & 0x3F));
/* 236 */           m = 0x80 | m & 0x3F;
/*     */         }
/*     */         
/* 239 */         if (k >= arrayOfByte.length) {
/* 240 */           arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 241 */           k = 0;
/*     */         }
/* 243 */         arrayOfByte[(k++)] = ((byte)m); } }
/*     */     label492:
/* 245 */     return this._byteBuilder.completeAndCoalesce(k);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] encodeAsUTF8(String paramString)
/*     */   {
/* 255 */     ByteArrayBuilder localByteArrayBuilder = this._byteBuilder;
/* 256 */     if (localByteArrayBuilder == null)
/*     */     {
/* 258 */       this._byteBuilder = (localByteArrayBuilder = new ByteArrayBuilder(null));
/*     */     }
/* 260 */     int i = 0;
/* 261 */     int j = paramString.length();
/* 262 */     int k = 0;
/* 263 */     byte[] arrayOfByte = localByteArrayBuilder.resetAndGetFirstSegment();
/* 264 */     int m = arrayOfByte.length;
/*     */     
/*     */ 
/* 267 */     while (i < j) {
/* 268 */       int n = paramString.charAt(i++);
/*     */       
/*     */ 
/* 271 */       while (n <= 127) {
/* 272 */         if (k >= m) {
/* 273 */           arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 274 */           m = arrayOfByte.length;
/* 275 */           k = 0;
/*     */         }
/* 277 */         arrayOfByte[(k++)] = ((byte)n);
/* 278 */         if (i >= j) {
/*     */           break label443;
/*     */         }
/* 281 */         n = paramString.charAt(i++);
/*     */       }
/*     */       
/*     */ 
/* 285 */       if (k >= m) {
/* 286 */         arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 287 */         m = arrayOfByte.length;
/* 288 */         k = 0;
/*     */       }
/* 290 */       if (n < 2048) {
/* 291 */         arrayOfByte[(k++)] = ((byte)(0xC0 | n >> 6));
/*     */ 
/*     */       }
/* 294 */       else if ((n < 55296) || (n > 57343)) {
/* 295 */         arrayOfByte[(k++)] = ((byte)(0xE0 | n >> 12));
/* 296 */         if (k >= m) {
/* 297 */           arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 298 */           m = arrayOfByte.length;
/* 299 */           k = 0;
/*     */         }
/* 301 */         arrayOfByte[(k++)] = ((byte)(0x80 | n >> 6 & 0x3F));
/*     */       } else {
/* 303 */         if (n > 56319) {
/* 304 */           _illegalSurrogate(n);
/*     */         }
/*     */         
/* 307 */         if (i >= j) {
/* 308 */           _illegalSurrogate(n);
/*     */         }
/* 310 */         n = _convertSurrogate(n, paramString.charAt(i++));
/* 311 */         if (n > 1114111) {
/* 312 */           _illegalSurrogate(n);
/*     */         }
/* 314 */         arrayOfByte[(k++)] = ((byte)(0xF0 | n >> 18));
/* 315 */         if (k >= m) {
/* 316 */           arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 317 */           m = arrayOfByte.length;
/* 318 */           k = 0;
/*     */         }
/* 320 */         arrayOfByte[(k++)] = ((byte)(0x80 | n >> 12 & 0x3F));
/* 321 */         if (k >= m) {
/* 322 */           arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 323 */           m = arrayOfByte.length;
/* 324 */           k = 0;
/*     */         }
/* 326 */         arrayOfByte[(k++)] = ((byte)(0x80 | n >> 6 & 0x3F));
/*     */       }
/*     */       
/* 329 */       if (k >= m) {
/* 330 */         arrayOfByte = localByteArrayBuilder.finishCurrentSegment();
/* 331 */         m = arrayOfByte.length;
/* 332 */         k = 0;
/*     */       }
/* 334 */       arrayOfByte[(k++)] = ((byte)(0x80 | n & 0x3F)); }
/*     */     label443:
/* 336 */     return this._byteBuilder.completeAndCoalesce(k);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int _appendNumericEscape(int paramInt, char[] paramArrayOfChar)
/*     */   {
/* 347 */     paramArrayOfChar[1] = 'u';
/*     */     
/* 349 */     paramArrayOfChar[4] = HEX_CHARS[(paramInt >> 4)];
/* 350 */     paramArrayOfChar[5] = HEX_CHARS[(paramInt & 0xF)];
/* 351 */     return 6;
/*     */   }
/*     */   
/*     */   private int _appendNamedEscape(int paramInt, char[] paramArrayOfChar)
/*     */   {
/* 356 */     paramArrayOfChar[1] = ((char)paramInt);
/* 357 */     return 2;
/*     */   }
/*     */   
/*     */   private int _appendByteEscape(int paramInt1, int paramInt2, ByteArrayBuilder paramByteArrayBuilder, int paramInt3)
/*     */   {
/* 362 */     paramByteArrayBuilder.setCurrentSegmentLength(paramInt3);
/* 363 */     paramByteArrayBuilder.append(92);
/* 364 */     if (paramInt2 < 0) {
/* 365 */       paramByteArrayBuilder.append(117);
/* 366 */       if (paramInt1 > 255) {
/* 367 */         int i = paramInt1 >> 8;
/* 368 */         paramByteArrayBuilder.append(HEX_BYTES[(i >> 4)]);
/* 369 */         paramByteArrayBuilder.append(HEX_BYTES[(i & 0xF)]);
/* 370 */         paramInt1 &= 0xFF;
/*     */       } else {
/* 372 */         paramByteArrayBuilder.append(48);
/* 373 */         paramByteArrayBuilder.append(48);
/*     */       }
/* 375 */       paramByteArrayBuilder.append(HEX_BYTES[(paramInt1 >> 4)]);
/* 376 */       paramByteArrayBuilder.append(HEX_BYTES[(paramInt1 & 0xF)]);
/*     */     } else {
/* 378 */       paramByteArrayBuilder.append((byte)paramInt2);
/*     */     }
/* 380 */     return paramByteArrayBuilder.getCurrentSegmentLength();
/*     */   }
/*     */   
/*     */ 
/*     */   protected static int _convertSurrogate(int paramInt1, int paramInt2)
/*     */   {
/* 386 */     if ((paramInt2 < 56320) || (paramInt2 > 57343)) {
/* 387 */       throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(paramInt1) + ", second 0x" + Integer.toHexString(paramInt2) + "; illegal combination");
/*     */     }
/* 389 */     return 65536 + (paramInt1 - 55296 << 10) + (paramInt2 - 56320);
/*     */   }
/*     */   
/*     */   protected static void _illegalSurrogate(int paramInt) {
/* 393 */     throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(paramInt));
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/JsonStringEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */