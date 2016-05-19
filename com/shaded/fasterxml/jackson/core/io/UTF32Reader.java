/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.io.CharConversionException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class UTF32Reader
/*     */   extends BaseReader
/*     */ {
/*     */   protected final boolean _bigEndian;
/*  20 */   protected char _surrogate = '\000';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  25 */   protected int _charCount = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  30 */   protected int _byteCount = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean _managedBuffers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UTF32Reader(IOContext paramIOContext, InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  44 */     super(paramIOContext, paramInputStream, paramArrayOfByte, paramInt1, paramInt2);
/*  45 */     this._bigEndian = paramBoolean;
/*  46 */     this._managedBuffers = (paramInputStream != null);
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
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*  60 */     if (this._buffer == null) {
/*  61 */       return -1;
/*     */     }
/*  63 */     if (paramInt2 < 1) {
/*  64 */       return paramInt2;
/*     */     }
/*     */     
/*  67 */     if ((paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length)) {
/*  68 */       reportBounds(paramArrayOfChar, paramInt1, paramInt2);
/*     */     }
/*     */     
/*  71 */     paramInt2 += paramInt1;
/*  72 */     int i = paramInt1;
/*     */     
/*     */     int j;
/*  75 */     if (this._surrogate != 0) {
/*  76 */       paramArrayOfChar[(i++)] = this._surrogate;
/*  77 */       this._surrogate = '\000';
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/*  83 */       j = this._length - this._ptr;
/*  84 */       if ((j < 4) && 
/*  85 */         (!loadMore(j))) {
/*  86 */         return -1;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  92 */     while (i < paramInt2) {
/*  93 */       j = this._ptr;
/*     */       
/*     */       int k;
/*  96 */       if (this._bigEndian) {
/*  97 */         k = this._buffer[j] << 24 | (this._buffer[(j + 1)] & 0xFF) << 16 | (this._buffer[(j + 2)] & 0xFF) << 8 | this._buffer[(j + 3)] & 0xFF;
/*     */       }
/*     */       else {
/* 100 */         k = this._buffer[j] & 0xFF | (this._buffer[(j + 1)] & 0xFF) << 8 | (this._buffer[(j + 2)] & 0xFF) << 16 | this._buffer[(j + 3)] << 24;
/*     */       }
/*     */       
/* 103 */       this._ptr += 4;
/*     */       
/*     */ 
/*     */ 
/* 107 */       if (k > 65535) {
/* 108 */         if (k > 1114111) {
/* 109 */           reportInvalid(k, i - paramInt1, "(above " + Integer.toHexString(1114111) + ") ");
/*     */         }
/*     */         
/* 112 */         k -= 65536;
/* 113 */         paramArrayOfChar[(i++)] = ((char)(55296 + (k >> 10)));
/*     */         
/* 115 */         k = 0xDC00 | k & 0x3FF;
/*     */         
/* 117 */         if (i >= paramInt2) {
/* 118 */           this._surrogate = ((char)k);
/* 119 */           break;
/*     */         }
/*     */       }
/* 122 */       paramArrayOfChar[(i++)] = ((char)k);
/* 123 */       if (this._ptr >= this._length) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 128 */     paramInt2 = i - paramInt1;
/* 129 */     this._charCount += paramInt2;
/* 130 */     return paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void reportUnexpectedEOF(int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 142 */     int i = this._byteCount + paramInt1;
/* 143 */     int j = this._charCount;
/*     */     
/* 145 */     throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + paramInt1 + ", needed " + paramInt2 + ", at char #" + j + ", byte #" + i + ")");
/*     */   }
/*     */   
/*     */ 
/*     */   private void reportInvalid(int paramInt1, int paramInt2, String paramString)
/*     */     throws IOException
/*     */   {
/* 152 */     int i = this._byteCount + this._ptr - 1;
/* 153 */     int j = this._charCount + paramInt2;
/*     */     
/* 155 */     throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(paramInt1) + paramString + " at char #" + j + ", byte #" + i + ")");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean loadMore(int paramInt)
/*     */     throws IOException
/*     */   {
/* 168 */     this._byteCount += this._length - paramInt;
/*     */     
/*     */     int i;
/* 171 */     if (paramInt > 0) {
/* 172 */       if (this._ptr > 0) {
/* 173 */         for (i = 0; i < paramInt; i++) {
/* 174 */           this._buffer[i] = this._buffer[(this._ptr + i)];
/*     */         }
/* 176 */         this._ptr = 0;
/*     */       }
/* 178 */       this._length = paramInt;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 183 */       this._ptr = 0;
/* 184 */       i = this._in == null ? -1 : this._in.read(this._buffer);
/* 185 */       if (i < 1) {
/* 186 */         this._length = 0;
/* 187 */         if (i < 0) {
/* 188 */           if (this._managedBuffers) {
/* 189 */             freeBuffers();
/*     */           }
/* 191 */           return false;
/*     */         }
/*     */         
/* 194 */         reportStrangeStream();
/*     */       }
/* 196 */       this._length = i;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 202 */     while (this._length < 4) {
/* 203 */       i = this._in == null ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
/* 204 */       if (i < 1) {
/* 205 */         if (i < 0) {
/* 206 */           if (this._managedBuffers) {
/* 207 */             freeBuffers();
/*     */           }
/* 209 */           reportUnexpectedEOF(this._length, 4);
/*     */         }
/*     */         
/* 212 */         reportStrangeStream();
/*     */       }
/* 214 */       this._length += i;
/*     */     }
/* 216 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/UTF32Reader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */