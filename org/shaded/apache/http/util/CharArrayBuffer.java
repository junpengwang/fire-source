/*     */ package org.shaded.apache.http.util;
/*     */ 
/*     */ import org.shaded.apache.http.protocol.HTTP;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CharArrayBuffer
/*     */ {
/*     */   private char[] buffer;
/*     */   private int len;
/*     */   
/*     */   public CharArrayBuffer(int capacity)
/*     */   {
/*  57 */     if (capacity < 0) {
/*  58 */       throw new IllegalArgumentException("Buffer capacity may not be negative");
/*     */     }
/*  60 */     this.buffer = new char[capacity];
/*     */   }
/*     */   
/*     */   private void expand(int newlen) {
/*  64 */     char[] newbuffer = new char[Math.max(this.buffer.length << 1, newlen)];
/*  65 */     System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
/*  66 */     this.buffer = newbuffer;
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
/*     */   public void append(char[] b, int off, int len)
/*     */   {
/*  82 */     if (b == null) {
/*  83 */       return;
/*     */     }
/*  85 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length))
/*     */     {
/*  87 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  89 */     if (len == 0) {
/*  90 */       return;
/*     */     }
/*  92 */     int newlen = this.len + len;
/*  93 */     if (newlen > this.buffer.length) {
/*  94 */       expand(newlen);
/*     */     }
/*  96 */     System.arraycopy(b, off, this.buffer, this.len, len);
/*  97 */     this.len = newlen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(String str)
/*     */   {
/* 107 */     if (str == null) {
/* 108 */       str = "null";
/*     */     }
/* 110 */     int strlen = str.length();
/* 111 */     int newlen = this.len + strlen;
/* 112 */     if (newlen > this.buffer.length) {
/* 113 */       expand(newlen);
/*     */     }
/* 115 */     str.getChars(0, strlen, this.buffer, this.len);
/* 116 */     this.len = newlen;
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
/*     */   public void append(CharArrayBuffer b, int off, int len)
/*     */   {
/* 133 */     if (b == null) {
/* 134 */       return;
/*     */     }
/* 136 */     append(b.buffer, off, len);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(CharArrayBuffer b)
/*     */   {
/* 147 */     if (b == null) {
/* 148 */       return;
/*     */     }
/* 150 */     append(b.buffer, 0, b.len);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(char ch)
/*     */   {
/* 160 */     int newlen = this.len + 1;
/* 161 */     if (newlen > this.buffer.length) {
/* 162 */       expand(newlen);
/*     */     }
/* 164 */     this.buffer[this.len] = ch;
/* 165 */     this.len = newlen;
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
/*     */   public void append(byte[] b, int off, int len)
/*     */   {
/* 183 */     if (b == null) {
/* 184 */       return;
/*     */     }
/* 186 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length))
/*     */     {
/* 188 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 190 */     if (len == 0) {
/* 191 */       return;
/*     */     }
/* 193 */     int oldlen = this.len;
/* 194 */     int newlen = oldlen + len;
/* 195 */     if (newlen > this.buffer.length) {
/* 196 */       expand(newlen);
/*     */     }
/* 198 */     int i1 = off; for (int i2 = oldlen; i2 < newlen; i2++) {
/* 199 */       this.buffer[i2] = ((char)(b[i1] & 0xFF));i1++;
/*     */     }
/* 201 */     this.len = newlen;
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
/*     */   public void append(ByteArrayBuffer b, int off, int len)
/*     */   {
/* 219 */     if (b == null) {
/* 220 */       return;
/*     */     }
/* 222 */     append(b.buffer(), off, len);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(Object obj)
/*     */   {
/* 233 */     append(String.valueOf(obj));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 240 */     this.len = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char[] toCharArray()
/*     */   {
/* 249 */     char[] b = new char[this.len];
/* 250 */     if (this.len > 0) {
/* 251 */       System.arraycopy(this.buffer, 0, b, 0, this.len);
/*     */     }
/* 253 */     return b;
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
/*     */   public char charAt(int i)
/*     */   {
/* 267 */     return this.buffer[i];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char[] buffer()
/*     */   {
/* 276 */     return this.buffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int capacity()
/*     */   {
/* 287 */     return this.buffer.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int length()
/*     */   {
/* 296 */     return this.len;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void ensureCapacity(int required)
/*     */   {
/* 308 */     if (required <= 0) {
/* 309 */       return;
/*     */     }
/* 311 */     int available = this.buffer.length - this.len;
/* 312 */     if (required > available) {
/* 313 */       expand(this.len + required);
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
/*     */   public void setLength(int len)
/*     */   {
/* 328 */     if ((len < 0) || (len > this.buffer.length)) {
/* 329 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 331 */     this.len = len;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 341 */     return this.len == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFull()
/*     */   {
/* 351 */     return this.len == this.buffer.length;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int indexOf(int ch, int beginIndex, int endIndex)
/*     */   {
/* 376 */     if (beginIndex < 0) {
/* 377 */       beginIndex = 0;
/*     */     }
/* 379 */     if (endIndex > this.len) {
/* 380 */       endIndex = this.len;
/*     */     }
/* 382 */     if (beginIndex > endIndex) {
/* 383 */       return -1;
/*     */     }
/* 385 */     for (int i = beginIndex; i < endIndex; i++) {
/* 386 */       if (this.buffer[i] == ch) {
/* 387 */         return i;
/*     */       }
/*     */     }
/* 390 */     return -1;
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
/*     */   public int indexOf(int ch)
/*     */   {
/* 404 */     return indexOf(ch, 0, this.len);
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
/*     */   public String substring(int beginIndex, int endIndex)
/*     */   {
/* 422 */     if (beginIndex < 0) {
/* 423 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 425 */     if (endIndex > this.len) {
/* 426 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 428 */     if (beginIndex > endIndex) {
/* 429 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 431 */     return new String(this.buffer, beginIndex, endIndex - beginIndex);
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
/*     */   public String substringTrimmed(int beginIndex, int endIndex)
/*     */   {
/* 451 */     if (beginIndex < 0) {
/* 452 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 454 */     if (endIndex > this.len) {
/* 455 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 457 */     if (beginIndex > endIndex) {
/* 458 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 460 */     while ((beginIndex < endIndex) && (HTTP.isWhitespace(this.buffer[beginIndex]))) {
/* 461 */       beginIndex++;
/*     */     }
/* 463 */     while ((endIndex > beginIndex) && (HTTP.isWhitespace(this.buffer[(endIndex - 1)]))) {
/* 464 */       endIndex--;
/*     */     }
/* 466 */     return new String(this.buffer, beginIndex, endIndex - beginIndex);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 470 */     return new String(this.buffer, 0, this.len);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/util/CharArrayBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */