/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferRecycler
/*     */ {
/*     */   public static final int DEFAULT_WRITE_CONCAT_BUFFER_LEN = 2000;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum ByteBufferType
/*     */   {
/*  16 */     READ_IO_BUFFER(4000), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  21 */     WRITE_ENCODING_BUFFER(4000), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  27 */     WRITE_CONCAT_BUFFER(2000), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  35 */     BASE64_CODEC_BUFFER(2000);
/*     */     
/*     */ 
/*     */     protected final int size;
/*     */     
/*  40 */     private ByteBufferType(int paramInt) { this.size = paramInt; }
/*     */   }
/*     */   
/*     */   public static enum CharBufferType {
/*  44 */     TOKEN_BUFFER(2000), 
/*  45 */     CONCAT_BUFFER(2000), 
/*  46 */     TEXT_BUFFER(200), 
/*  47 */     NAME_COPY_BUFFER(200);
/*     */     
/*     */ 
/*     */     protected final int size;
/*     */     
/*  52 */     private CharBufferType(int paramInt) { this.size = paramInt; }
/*     */   }
/*     */   
/*  55 */   protected final byte[][] _byteBuffers = new byte[ByteBufferType.values().length][];
/*  56 */   protected final char[][] _charBuffers = new char[CharBufferType.values().length][];
/*     */   
/*     */ 
/*     */ 
/*     */   public final byte[] allocByteBuffer(ByteBufferType paramByteBufferType)
/*     */   {
/*  62 */     int i = paramByteBufferType.ordinal();
/*  63 */     byte[] arrayOfByte = this._byteBuffers[i];
/*  64 */     if (arrayOfByte == null) {
/*  65 */       arrayOfByte = balloc(paramByteBufferType.size);
/*     */     } else {
/*  67 */       this._byteBuffers[i] = null;
/*     */     }
/*  69 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public final void releaseByteBuffer(ByteBufferType paramByteBufferType, byte[] paramArrayOfByte)
/*     */   {
/*  74 */     this._byteBuffers[paramByteBufferType.ordinal()] = paramArrayOfByte;
/*     */   }
/*     */   
/*     */   public final char[] allocCharBuffer(CharBufferType paramCharBufferType)
/*     */   {
/*  79 */     return allocCharBuffer(paramCharBufferType, 0);
/*     */   }
/*     */   
/*     */   public final char[] allocCharBuffer(CharBufferType paramCharBufferType, int paramInt)
/*     */   {
/*  84 */     if (paramCharBufferType.size > paramInt) {
/*  85 */       paramInt = paramCharBufferType.size;
/*     */     }
/*  87 */     int i = paramCharBufferType.ordinal();
/*  88 */     char[] arrayOfChar = this._charBuffers[i];
/*  89 */     if ((arrayOfChar == null) || (arrayOfChar.length < paramInt)) {
/*  90 */       arrayOfChar = calloc(paramInt);
/*     */     } else {
/*  92 */       this._charBuffers[i] = null;
/*     */     }
/*  94 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */   public final void releaseCharBuffer(CharBufferType paramCharBufferType, char[] paramArrayOfChar)
/*     */   {
/*  99 */     this._charBuffers[paramCharBufferType.ordinal()] = paramArrayOfChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private byte[] balloc(int paramInt)
/*     */   {
/* 110 */     return new byte[paramInt];
/*     */   }
/*     */   
/*     */   private char[] calloc(int paramInt)
/*     */   {
/* 115 */     return new char[paramInt];
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/BufferRecycler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */