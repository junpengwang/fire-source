/*     */ package org.shaded.apache.http.util;
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
/*     */ public final class ByteArrayBuffer
/*     */ {
/*     */   private byte[] buffer;
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
/*     */   private int len;
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
/*     */   public ByteArrayBuffer(int capacity)
/*     */   {
/*  55 */     if (capacity < 0) {
/*  56 */       throw new IllegalArgumentException("Buffer capacity may not be negative");
/*     */     }
/*  58 */     this.buffer = new byte[capacity];
/*     */   }
/*     */   
/*     */   private void expand(int newlen) {
/*  62 */     byte[] newbuffer = new byte[Math.max(this.buffer.length << 1, newlen)];
/*  63 */     System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
/*  64 */     this.buffer = newbuffer;
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
/*     */   public void append(byte[] b, int off, int len)
/*     */   {
/*  80 */     if (b == null) {
/*  81 */       return;
/*     */     }
/*  83 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length))
/*     */     {
/*  85 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  87 */     if (len == 0) {
/*  88 */       return;
/*     */     }
/*  90 */     int newlen = this.len + len;
/*  91 */     if (newlen > this.buffer.length) {
/*  92 */       expand(newlen);
/*     */     }
/*  94 */     System.arraycopy(b, off, this.buffer, this.len, len);
/*  95 */     this.len = newlen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(int b)
/*     */   {
/* 105 */     int newlen = this.len + 1;
/* 106 */     if (newlen > this.buffer.length) {
/* 107 */       expand(newlen);
/*     */     }
/* 109 */     this.buffer[this.len] = ((byte)b);
/* 110 */     this.len = newlen;
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
/*     */   public void append(char[] b, int off, int len)
/*     */   {
/* 128 */     if (b == null) {
/* 129 */       return;
/*     */     }
/* 131 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length))
/*     */     {
/* 133 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 135 */     if (len == 0) {
/* 136 */       return;
/*     */     }
/* 138 */     int oldlen = this.len;
/* 139 */     int newlen = oldlen + len;
/* 140 */     if (newlen > this.buffer.length) {
/* 141 */       expand(newlen);
/*     */     }
/* 143 */     int i1 = off; for (int i2 = oldlen; i2 < newlen; i2++) {
/* 144 */       this.buffer[i2] = ((byte)b[i1]);i1++;
/*     */     }
/* 146 */     this.len = newlen;
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
/*     */   public void append(CharArrayBuffer b, int off, int len)
/*     */   {
/* 165 */     if (b == null) {
/* 166 */       return;
/*     */     }
/* 168 */     append(b.buffer(), off, len);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 175 */     this.len = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] toByteArray()
/*     */   {
/* 184 */     byte[] b = new byte[this.len];
/* 185 */     if (this.len > 0) {
/* 186 */       System.arraycopy(this.buffer, 0, b, 0, this.len);
/*     */     }
/* 188 */     return b;
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
/*     */   public int byteAt(int i)
/*     */   {
/* 202 */     return this.buffer[i];
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
/* 213 */     return this.buffer.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int length()
/*     */   {
/* 222 */     return this.len;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] buffer()
/*     */   {
/* 231 */     return this.buffer;
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
/* 245 */     if ((len < 0) || (len > this.buffer.length)) {
/* 246 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 248 */     this.len = len;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 258 */     return this.len == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFull()
/*     */   {
/* 268 */     return this.len == this.buffer.length;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/util/ByteArrayBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */