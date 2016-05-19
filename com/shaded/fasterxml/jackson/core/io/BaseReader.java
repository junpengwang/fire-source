/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ abstract class BaseReader
/*     */   extends Reader
/*     */ {
/*     */   protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
/*     */   protected static final char NULL_CHAR = '\000';
/*     */   protected static final char NULL_BYTE = '\000';
/*     */   protected final IOContext _context;
/*     */   protected InputStream _in;
/*     */   protected byte[] _buffer;
/*     */   protected int _ptr;
/*     */   protected int _length;
/*     */   
/*     */   protected BaseReader(IOContext paramIOContext, InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  41 */     this._context = paramIOContext;
/*  42 */     this._in = paramInputStream;
/*  43 */     this._buffer = paramArrayOfByte;
/*  44 */     this._ptr = paramInt1;
/*  45 */     this._length = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  57 */     InputStream localInputStream = this._in;
/*     */     
/*  59 */     if (localInputStream != null) {
/*  60 */       this._in = null;
/*  61 */       freeBuffers();
/*  62 */       localInputStream.close();
/*     */     }
/*     */   }
/*     */   
/*  66 */   protected char[] _tmpBuf = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  76 */     if (this._tmpBuf == null) {
/*  77 */       this._tmpBuf = new char[1];
/*     */     }
/*  79 */     if (read(this._tmpBuf, 0, 1) < 1) {
/*  80 */       return -1;
/*     */     }
/*  82 */     return this._tmpBuf[0];
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
/*     */   public final void freeBuffers()
/*     */   {
/*  98 */     byte[] arrayOfByte = this._buffer;
/*  99 */     if (arrayOfByte != null) {
/* 100 */       this._buffer = null;
/* 101 */       this._context.releaseReadIOBuffer(arrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void reportBounds(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 108 */     throw new ArrayIndexOutOfBoundsException("read(buf," + paramInt1 + "," + paramInt2 + "), cbuf[" + paramArrayOfChar.length + "]");
/*     */   }
/*     */   
/*     */   protected void reportStrangeStream()
/*     */     throws IOException
/*     */   {
/* 114 */     throw new IOException("Strange I/O stream, returned 0 bytes on read");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/BaseReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */