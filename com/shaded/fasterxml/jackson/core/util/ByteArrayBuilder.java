/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.util.LinkedList;
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
/*     */ public final class ByteArrayBuilder
/*     */   extends OutputStream
/*     */ {
/*  27 */   private static final byte[] NO_BYTES = new byte[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int INITIAL_BLOCK_SIZE = 500;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final int MAX_BLOCK_SIZE = 262144;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
/*     */   
/*     */ 
/*     */ 
/*     */   private final BufferRecycler _bufferRecycler;
/*     */   
/*     */ 
/*     */ 
/*  48 */   private final LinkedList<byte[]> _pastBlocks = new LinkedList();
/*     */   
/*     */ 
/*     */   private int _pastLen;
/*     */   
/*     */ 
/*     */   private byte[] _currBlock;
/*     */   
/*     */   private int _currBlockPtr;
/*     */   
/*     */ 
/*  59 */   public ByteArrayBuilder() { this(null); }
/*     */   
/*  61 */   public ByteArrayBuilder(BufferRecycler paramBufferRecycler) { this(paramBufferRecycler, 500); }
/*     */   
/*  63 */   public ByteArrayBuilder(int paramInt) { this(null, paramInt); }
/*     */   
/*     */   public ByteArrayBuilder(BufferRecycler paramBufferRecycler, int paramInt)
/*     */   {
/*  67 */     this._bufferRecycler = paramBufferRecycler;
/*  68 */     if (paramBufferRecycler == null) {
/*  69 */       this._currBlock = new byte[paramInt];
/*     */     } else {
/*  71 */       this._currBlock = paramBufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER);
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset()
/*     */   {
/*  77 */     this._pastLen = 0;
/*  78 */     this._currBlockPtr = 0;
/*     */     
/*  80 */     if (!this._pastBlocks.isEmpty()) {
/*  81 */       this._pastBlocks.clear();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/*  91 */     reset();
/*  92 */     if ((this._bufferRecycler != null) && (this._currBlock != null)) {
/*  93 */       this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER, this._currBlock);
/*  94 */       this._currBlock = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void append(int paramInt)
/*     */   {
/* 100 */     if (this._currBlockPtr >= this._currBlock.length) {
/* 101 */       _allocMore();
/*     */     }
/* 103 */     this._currBlock[(this._currBlockPtr++)] = ((byte)paramInt);
/*     */   }
/*     */   
/*     */   public void appendTwoBytes(int paramInt)
/*     */   {
/* 108 */     if (this._currBlockPtr + 1 < this._currBlock.length) {
/* 109 */       this._currBlock[(this._currBlockPtr++)] = ((byte)(paramInt >> 8));
/* 110 */       this._currBlock[(this._currBlockPtr++)] = ((byte)paramInt);
/*     */     } else {
/* 112 */       append(paramInt >> 8);
/* 113 */       append(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   public void appendThreeBytes(int paramInt)
/*     */   {
/* 119 */     if (this._currBlockPtr + 2 < this._currBlock.length) {
/* 120 */       this._currBlock[(this._currBlockPtr++)] = ((byte)(paramInt >> 16));
/* 121 */       this._currBlock[(this._currBlockPtr++)] = ((byte)(paramInt >> 8));
/* 122 */       this._currBlock[(this._currBlockPtr++)] = ((byte)paramInt);
/*     */     } else {
/* 124 */       append(paramInt >> 16);
/* 125 */       append(paramInt >> 8);
/* 126 */       append(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] toByteArray()
/*     */   {
/* 136 */     int i = this._pastLen + this._currBlockPtr;
/*     */     
/* 138 */     if (i == 0) {
/* 139 */       return NO_BYTES;
/*     */     }
/*     */     
/* 142 */     byte[] arrayOfByte1 = new byte[i];
/* 143 */     int j = 0;
/*     */     
/* 145 */     for (byte[] arrayOfByte2 : this._pastBlocks) {
/* 146 */       int k = arrayOfByte2.length;
/* 147 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte1, j, k);
/* 148 */       j += k;
/*     */     }
/* 150 */     System.arraycopy(this._currBlock, 0, arrayOfByte1, j, this._currBlockPtr);
/* 151 */     j += this._currBlockPtr;
/* 152 */     if (j != i) {
/* 153 */       throw new RuntimeException("Internal error: total len assumed to be " + i + ", copied " + j + " bytes");
/*     */     }
/*     */     
/* 156 */     if (!this._pastBlocks.isEmpty()) {
/* 157 */       reset();
/*     */     }
/* 159 */     return arrayOfByte1;
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
/*     */   public byte[] resetAndGetFirstSegment()
/*     */   {
/* 173 */     reset();
/* 174 */     return this._currBlock;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] finishCurrentSegment()
/*     */   {
/* 183 */     _allocMore();
/* 184 */     return this._currBlock;
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
/*     */   public byte[] completeAndCoalesce(int paramInt)
/*     */   {
/* 198 */     this._currBlockPtr = paramInt;
/* 199 */     return toByteArray();
/*     */   }
/*     */   
/*     */   public byte[] getCurrentSegment() {
/* 203 */     return this._currBlock;
/*     */   }
/*     */   
/*     */   public void setCurrentSegmentLength(int paramInt) {
/* 207 */     this._currBlockPtr = paramInt;
/*     */   }
/*     */   
/*     */   public int getCurrentSegmentLength() {
/* 211 */     return this._currBlockPtr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte)
/*     */   {
/* 222 */     write(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     for (;;)
/*     */     {
/* 229 */       int i = this._currBlock.length - this._currBlockPtr;
/* 230 */       int j = Math.min(i, paramInt2);
/* 231 */       if (j > 0) {
/* 232 */         System.arraycopy(paramArrayOfByte, paramInt1, this._currBlock, this._currBlockPtr, j);
/* 233 */         paramInt1 += j;
/* 234 */         this._currBlockPtr += j;
/* 235 */         paramInt2 -= j;
/*     */       }
/* 237 */       if (paramInt2 <= 0) break;
/* 238 */       _allocMore();
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(int paramInt)
/*     */   {
/* 244 */     append(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void close() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void flush() {}
/*     */   
/*     */ 
/*     */ 
/*     */   private void _allocMore()
/*     */   {
/* 259 */     this._pastLen += this._currBlock.length;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 267 */     int i = Math.max(this._pastLen >> 1, 1000);
/*     */     
/* 269 */     if (i > 262144) {
/* 270 */       i = 262144;
/*     */     }
/* 272 */     this._pastBlocks.add(this._currBlock);
/* 273 */     this._currBlock = new byte[i];
/* 274 */     this._currBlockPtr = 0;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/ByteArrayBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */