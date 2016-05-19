/*     */ package com.shaded.fasterxml.jackson.core.format;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonFactory;
/*     */ import java.io.EOFException;
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
/*     */ public abstract interface InputAccessor
/*     */ {
/*     */   public abstract boolean hasMoreBytes()
/*     */     throws IOException;
/*     */   
/*     */   public abstract byte nextByte()
/*     */     throws IOException;
/*     */   
/*     */   public abstract void reset();
/*     */   
/*     */   public static class Std
/*     */     implements InputAccessor
/*     */   {
/*     */     protected final InputStream _in;
/*     */     protected final byte[] _buffer;
/*     */     protected final int _bufferedStart;
/*     */     protected int _bufferedEnd;
/*     */     protected int _ptr;
/*     */     
/*     */     public Std(InputStream paramInputStream, byte[] paramArrayOfByte)
/*     */     {
/*  66 */       this._in = paramInputStream;
/*  67 */       this._buffer = paramArrayOfByte;
/*  68 */       this._bufferedStart = 0;
/*  69 */       this._ptr = 0;
/*  70 */       this._bufferedEnd = 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Std(byte[] paramArrayOfByte)
/*     */     {
/*  79 */       this._in = null;
/*  80 */       this._buffer = paramArrayOfByte;
/*     */       
/*  82 */       this._bufferedStart = 0;
/*  83 */       this._bufferedEnd = paramArrayOfByte.length;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Std(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  94 */       this._in = null;
/*  95 */       this._buffer = paramArrayOfByte;
/*  96 */       this._ptr = paramInt1;
/*  97 */       this._bufferedStart = paramInt1;
/*  98 */       this._bufferedEnd = (paramInt1 + paramInt2);
/*     */     }
/*     */     
/*     */     public boolean hasMoreBytes()
/*     */       throws IOException
/*     */     {
/* 104 */       if (this._ptr < this._bufferedEnd) {
/* 105 */         return true;
/*     */       }
/* 107 */       if (this._in == null) {
/* 108 */         return false;
/*     */       }
/* 110 */       int i = this._buffer.length - this._ptr;
/* 111 */       if (i < 1) {
/* 112 */         return false;
/*     */       }
/* 114 */       int j = this._in.read(this._buffer, this._ptr, i);
/* 115 */       if (j <= 0) {
/* 116 */         return false;
/*     */       }
/* 118 */       this._bufferedEnd += j;
/* 119 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public byte nextByte()
/*     */       throws IOException
/*     */     {
/* 126 */       if ((this._ptr >= this._bufferedEnd) && 
/* 127 */         (!hasMoreBytes())) {
/* 128 */         throw new EOFException("Failed auto-detect: could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
/*     */       }
/*     */       
/* 131 */       return this._buffer[(this._ptr++)];
/*     */     }
/*     */     
/*     */     public void reset()
/*     */     {
/* 136 */       this._ptr = this._bufferedStart;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public DataFormatMatcher createMatcher(JsonFactory paramJsonFactory, MatchStrength paramMatchStrength)
/*     */     {
/* 147 */       return new DataFormatMatcher(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, paramJsonFactory, paramMatchStrength);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/format/InputAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */