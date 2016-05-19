/*     */ package com.shaded.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public final class Base64Variant
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   static final char PADDING_CHAR_NONE = '\000';
/*     */   public static final int BASE64_VALUE_INVALID = -1;
/*     */   public static final int BASE64_VALUE_PADDING = -2;
/*  51 */   private final transient int[] _asciiToBase64 = new int[''];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */   private final transient char[] _base64ToAsciiC = new char[64];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  63 */   private final transient byte[] _base64ToAsciiB = new byte[64];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final String _name;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final transient boolean _usesPadding;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final transient char _paddingChar;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final transient int _maxLineLength;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base64Variant(String paramString1, String paramString2, boolean paramBoolean, char paramChar, int paramInt)
/*     */   {
/* 107 */     this._name = paramString1;
/* 108 */     this._usesPadding = paramBoolean;
/* 109 */     this._paddingChar = paramChar;
/* 110 */     this._maxLineLength = paramInt;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 115 */     int i = paramString2.length();
/* 116 */     if (i != 64) {
/* 117 */       throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + i + ")");
/*     */     }
/*     */     
/*     */ 
/* 121 */     paramString2.getChars(0, i, this._base64ToAsciiC, 0);
/* 122 */     Arrays.fill(this._asciiToBase64, -1);
/* 123 */     for (int j = 0; j < i; j++) {
/* 124 */       int k = this._base64ToAsciiC[j];
/* 125 */       this._base64ToAsciiB[j] = ((byte)k);
/* 126 */       this._asciiToBase64[k] = j;
/*     */     }
/*     */     
/*     */ 
/* 130 */     if (paramBoolean) {
/* 131 */       this._asciiToBase64[paramChar] = -2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base64Variant(Base64Variant paramBase64Variant, String paramString, int paramInt)
/*     */   {
/* 142 */     this(paramBase64Variant, paramString, paramBase64Variant._usesPadding, paramBase64Variant._paddingChar, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base64Variant(Base64Variant paramBase64Variant, String paramString, boolean paramBoolean, char paramChar, int paramInt)
/*     */   {
/* 152 */     this._name = paramString;
/* 153 */     byte[] arrayOfByte = paramBase64Variant._base64ToAsciiB;
/* 154 */     System.arraycopy(arrayOfByte, 0, this._base64ToAsciiB, 0, arrayOfByte.length);
/* 155 */     char[] arrayOfChar = paramBase64Variant._base64ToAsciiC;
/* 156 */     System.arraycopy(arrayOfChar, 0, this._base64ToAsciiC, 0, arrayOfChar.length);
/* 157 */     int[] arrayOfInt = paramBase64Variant._asciiToBase64;
/* 158 */     System.arraycopy(arrayOfInt, 0, this._asciiToBase64, 0, arrayOfInt.length);
/*     */     
/* 160 */     this._usesPadding = paramBoolean;
/* 161 */     this._paddingChar = paramChar;
/* 162 */     this._maxLineLength = paramInt;
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
/*     */   protected Object readResolve()
/*     */   {
/* 176 */     return Base64Variants.valueOf(this._name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 185 */   public String getName() { return this._name; }
/*     */   
/* 187 */   public boolean usesPadding() { return this._usesPadding; }
/* 188 */   public boolean usesPaddingChar(char paramChar) { return paramChar == this._paddingChar; }
/* 189 */   public boolean usesPaddingChar(int paramInt) { return paramInt == this._paddingChar; }
/* 190 */   public char getPaddingChar() { return this._paddingChar; }
/* 191 */   public byte getPaddingByte() { return (byte)this._paddingChar; }
/*     */   
/* 193 */   public int getMaxLineLength() { return this._maxLineLength; }
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
/*     */   public int decodeBase64Char(char paramChar)
/*     */   {
/* 206 */     int i = paramChar;
/* 207 */     return i <= 127 ? this._asciiToBase64[i] : -1;
/*     */   }
/*     */   
/*     */   public int decodeBase64Char(int paramInt)
/*     */   {
/* 212 */     return paramInt <= 127 ? this._asciiToBase64[paramInt] : -1;
/*     */   }
/*     */   
/*     */   public int decodeBase64Byte(byte paramByte)
/*     */   {
/* 217 */     int i = paramByte;
/* 218 */     return i <= 127 ? this._asciiToBase64[i] : -1;
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
/*     */   public char encodeBase64BitsAsChar(int paramInt)
/*     */   {
/* 232 */     return this._base64ToAsciiC[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int encodeBase64Chunk(int paramInt1, char[] paramArrayOfChar, int paramInt2)
/*     */   {
/* 241 */     paramArrayOfChar[(paramInt2++)] = this._base64ToAsciiC[(paramInt1 >> 18 & 0x3F)];
/* 242 */     paramArrayOfChar[(paramInt2++)] = this._base64ToAsciiC[(paramInt1 >> 12 & 0x3F)];
/* 243 */     paramArrayOfChar[(paramInt2++)] = this._base64ToAsciiC[(paramInt1 >> 6 & 0x3F)];
/* 244 */     paramArrayOfChar[(paramInt2++)] = this._base64ToAsciiC[(paramInt1 & 0x3F)];
/* 245 */     return paramInt2;
/*     */   }
/*     */   
/*     */   public void encodeBase64Chunk(StringBuilder paramStringBuilder, int paramInt)
/*     */   {
/* 250 */     paramStringBuilder.append(this._base64ToAsciiC[(paramInt >> 18 & 0x3F)]);
/* 251 */     paramStringBuilder.append(this._base64ToAsciiC[(paramInt >> 12 & 0x3F)]);
/* 252 */     paramStringBuilder.append(this._base64ToAsciiC[(paramInt >> 6 & 0x3F)]);
/* 253 */     paramStringBuilder.append(this._base64ToAsciiC[(paramInt & 0x3F)]);
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
/*     */   public int encodeBase64Partial(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
/*     */   {
/* 266 */     paramArrayOfChar[(paramInt3++)] = this._base64ToAsciiC[(paramInt1 >> 18 & 0x3F)];
/* 267 */     paramArrayOfChar[(paramInt3++)] = this._base64ToAsciiC[(paramInt1 >> 12 & 0x3F)];
/* 268 */     if (this._usesPadding) {
/* 269 */       paramArrayOfChar[(paramInt3++)] = (paramInt2 == 2 ? this._base64ToAsciiC[(paramInt1 >> 6 & 0x3F)] : this._paddingChar);
/*     */       
/* 271 */       paramArrayOfChar[(paramInt3++)] = this._paddingChar;
/*     */     }
/* 273 */     else if (paramInt2 == 2) {
/* 274 */       paramArrayOfChar[(paramInt3++)] = this._base64ToAsciiC[(paramInt1 >> 6 & 0x3F)];
/*     */     }
/*     */     
/* 277 */     return paramInt3;
/*     */   }
/*     */   
/*     */   public void encodeBase64Partial(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*     */   {
/* 282 */     paramStringBuilder.append(this._base64ToAsciiC[(paramInt1 >> 18 & 0x3F)]);
/* 283 */     paramStringBuilder.append(this._base64ToAsciiC[(paramInt1 >> 12 & 0x3F)]);
/* 284 */     if (this._usesPadding) {
/* 285 */       paramStringBuilder.append(paramInt2 == 2 ? this._base64ToAsciiC[(paramInt1 >> 6 & 0x3F)] : this._paddingChar);
/*     */       
/* 287 */       paramStringBuilder.append(this._paddingChar);
/*     */     }
/* 289 */     else if (paramInt2 == 2) {
/* 290 */       paramStringBuilder.append(this._base64ToAsciiC[(paramInt1 >> 6 & 0x3F)]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte encodeBase64BitsAsByte(int paramInt)
/*     */   {
/* 298 */     return this._base64ToAsciiB[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int encodeBase64Chunk(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*     */   {
/* 307 */     paramArrayOfByte[(paramInt2++)] = this._base64ToAsciiB[(paramInt1 >> 18 & 0x3F)];
/* 308 */     paramArrayOfByte[(paramInt2++)] = this._base64ToAsciiB[(paramInt1 >> 12 & 0x3F)];
/* 309 */     paramArrayOfByte[(paramInt2++)] = this._base64ToAsciiB[(paramInt1 >> 6 & 0x3F)];
/* 310 */     paramArrayOfByte[(paramInt2++)] = this._base64ToAsciiB[(paramInt1 & 0x3F)];
/* 311 */     return paramInt2;
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
/*     */   public int encodeBase64Partial(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
/*     */   {
/* 324 */     paramArrayOfByte[(paramInt3++)] = this._base64ToAsciiB[(paramInt1 >> 18 & 0x3F)];
/* 325 */     paramArrayOfByte[(paramInt3++)] = this._base64ToAsciiB[(paramInt1 >> 12 & 0x3F)];
/* 326 */     if (this._usesPadding) {
/* 327 */       int i = (byte)this._paddingChar;
/* 328 */       paramArrayOfByte[(paramInt3++)] = (paramInt2 == 2 ? this._base64ToAsciiB[(paramInt1 >> 6 & 0x3F)] : i);
/*     */       
/* 330 */       paramArrayOfByte[(paramInt3++)] = i;
/*     */     }
/* 332 */     else if (paramInt2 == 2) {
/* 333 */       paramArrayOfByte[(paramInt3++)] = this._base64ToAsciiB[(paramInt1 >> 6 & 0x3F)];
/*     */     }
/*     */     
/* 336 */     return paramInt3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String encode(byte[] paramArrayOfByte)
/*     */   {
/* 348 */     return encode(paramArrayOfByte, false);
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
/*     */   public String encode(byte[] paramArrayOfByte, boolean paramBoolean)
/*     */   {
/* 361 */     int i = paramArrayOfByte.length;
/*     */     
/*     */ 
/*     */ 
/* 365 */     int j = i + (i >> 2) + (i >> 3);
/* 366 */     StringBuilder localStringBuilder = new StringBuilder(j);
/*     */     
/* 368 */     if (paramBoolean) {
/* 369 */       localStringBuilder.append('"');
/*     */     }
/*     */     
/* 372 */     j = getMaxLineLength() >> 2;
/*     */     
/*     */ 
/* 375 */     int k = 0;
/* 376 */     int m = i - 3;
/*     */     
/* 378 */     while (k <= m)
/*     */     {
/* 380 */       n = paramArrayOfByte[(k++)] << 8;
/* 381 */       n |= paramArrayOfByte[(k++)] & 0xFF;
/* 382 */       n = n << 8 | paramArrayOfByte[(k++)] & 0xFF;
/* 383 */       encodeBase64Chunk(localStringBuilder, n);
/* 384 */       j--; if (j <= 0)
/*     */       {
/* 386 */         localStringBuilder.append('\\');
/* 387 */         localStringBuilder.append('n');
/* 388 */         j = getMaxLineLength() >> 2;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 393 */     int n = i - k;
/* 394 */     if (n > 0) {
/* 395 */       int i1 = paramArrayOfByte[(k++)] << 16;
/* 396 */       if (n == 2) {
/* 397 */         i1 |= (paramArrayOfByte[(k++)] & 0xFF) << 8;
/*     */       }
/* 399 */       encodeBase64Partial(localStringBuilder, i1, n);
/*     */     }
/*     */     
/* 402 */     if (paramBoolean) {
/* 403 */       localStringBuilder.append('"');
/*     */     }
/* 405 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 415 */     return this._name;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/Base64Variant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */