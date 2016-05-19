/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MergedStream
/*     */   extends InputStream
/*     */ {
/*     */   protected final IOContext _context;
/*     */   final InputStream _in;
/*     */   byte[] _buffer;
/*     */   int _ptr;
/*     */   final int _end;
/*     */   
/*     */   public MergedStream(IOContext paramIOContext, InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  29 */     this._context = paramIOContext;
/*  30 */     this._in = paramInputStream;
/*  31 */     this._buffer = paramArrayOfByte;
/*  32 */     this._ptr = paramInt1;
/*  33 */     this._end = paramInt2;
/*     */   }
/*     */   
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/*  39 */     if (this._buffer != null) {
/*  40 */       return this._end - this._ptr;
/*     */     }
/*  42 */     return this._in.available();
/*     */   }
/*     */   
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  48 */     freeMergedBuffer();
/*  49 */     this._in.close();
/*     */   }
/*     */   
/*     */ 
/*     */   public void mark(int paramInt)
/*     */   {
/*  55 */     if (this._buffer == null) {
/*  56 */       this._in.mark(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/*  64 */     return (this._buffer == null) && (this._in.markSupported());
/*     */   }
/*     */   
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  70 */     if (this._buffer != null) {
/*  71 */       int i = this._buffer[(this._ptr++)] & 0xFF;
/*  72 */       if (this._ptr >= this._end) {
/*  73 */         freeMergedBuffer();
/*     */       }
/*  75 */       return i;
/*     */     }
/*  77 */     return this._in.read();
/*     */   }
/*     */   
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/*  83 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*  89 */     if (this._buffer != null) {
/*  90 */       int i = this._end - this._ptr;
/*  91 */       if (paramInt2 > i) {
/*  92 */         paramInt2 = i;
/*     */       }
/*  94 */       System.arraycopy(this._buffer, this._ptr, paramArrayOfByte, paramInt1, paramInt2);
/*  95 */       this._ptr += paramInt2;
/*  96 */       if (this._ptr >= this._end) {
/*  97 */         freeMergedBuffer();
/*     */       }
/*  99 */       return paramInt2;
/*     */     }
/* 101 */     return this._in.read(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void reset()
/*     */     throws IOException
/*     */   {
/* 107 */     if (this._buffer == null) {
/* 108 */       this._in.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 115 */     long l = 0L;
/*     */     
/* 117 */     if (this._buffer != null) {
/* 118 */       int i = this._end - this._ptr;
/*     */       
/* 120 */       if (i > paramLong) {
/* 121 */         this._ptr += (int)paramLong;
/* 122 */         return paramLong;
/*     */       }
/* 124 */       freeMergedBuffer();
/* 125 */       l += i;
/* 126 */       paramLong -= i;
/*     */     }
/*     */     
/* 129 */     if (paramLong > 0L) {
/* 130 */       l += this._in.skip(paramLong);
/*     */     }
/* 132 */     return l;
/*     */   }
/*     */   
/*     */   private void freeMergedBuffer()
/*     */   {
/* 137 */     byte[] arrayOfByte = this._buffer;
/* 138 */     if (arrayOfByte != null) {
/* 139 */       this._buffer = null;
/* 140 */       if (this._context != null) {
/* 141 */         this._context.releaseReadIOBuffer(arrayOfByte);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/MergedStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */