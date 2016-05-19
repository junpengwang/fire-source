/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonEncoding;
/*     */ import com.shaded.fasterxml.jackson.core.util.BufferRecycler;
/*     */ import com.shaded.fasterxml.jackson.core.util.BufferRecycler.ByteBufferType;
/*     */ import com.shaded.fasterxml.jackson.core.util.BufferRecycler.CharBufferType;
/*     */ import com.shaded.fasterxml.jackson.core.util.TextBuffer;
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
/*     */ public final class IOContext
/*     */ {
/*     */   protected final Object _sourceRef;
/*     */   protected JsonEncoding _encoding;
/*     */   protected final boolean _managedResource;
/*     */   protected final BufferRecycler _bufferRecycler;
/*  57 */   protected byte[] _readIOBuffer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  63 */   protected byte[] _writeEncodingBuffer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  69 */   protected byte[] _base64Buffer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  76 */   protected char[] _tokenCBuffer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  83 */   protected char[] _concatCBuffer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  91 */   protected char[] _nameCopyBuffer = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IOContext(BufferRecycler paramBufferRecycler, Object paramObject, boolean paramBoolean)
/*     */   {
/* 101 */     this._bufferRecycler = paramBufferRecycler;
/* 102 */     this._sourceRef = paramObject;
/* 103 */     this._managedResource = paramBoolean;
/*     */   }
/*     */   
/*     */   public void setEncoding(JsonEncoding paramJsonEncoding) {
/* 107 */     this._encoding = paramJsonEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 116 */   public Object getSourceReference() { return this._sourceRef; }
/* 117 */   public JsonEncoding getEncoding() { return this._encoding; }
/* 118 */   public boolean isResourceManaged() { return this._managedResource; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TextBuffer constructTextBuffer()
/*     */   {
/* 127 */     return new TextBuffer(this._bufferRecycler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] allocReadIOBuffer()
/*     */   {
/* 137 */     _verifyAlloc(this._readIOBuffer);
/* 138 */     return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER);
/*     */   }
/*     */   
/*     */   public byte[] allocWriteEncodingBuffer()
/*     */   {
/* 143 */     _verifyAlloc(this._writeEncodingBuffer);
/* 144 */     return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] allocBase64Buffer()
/*     */   {
/* 152 */     _verifyAlloc(this._base64Buffer);
/* 153 */     return this._base64Buffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER);
/*     */   }
/*     */   
/*     */   public char[] allocTokenBuffer()
/*     */   {
/* 158 */     _verifyAlloc(this._tokenCBuffer);
/* 159 */     return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER);
/*     */   }
/*     */   
/*     */   public char[] allocConcatBuffer()
/*     */   {
/* 164 */     _verifyAlloc(this._concatCBuffer);
/* 165 */     return this._concatCBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER);
/*     */   }
/*     */   
/*     */   public char[] allocNameCopyBuffer(int paramInt)
/*     */   {
/* 170 */     _verifyAlloc(this._nameCopyBuffer);
/* 171 */     return this._nameCopyBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void releaseReadIOBuffer(byte[] paramArrayOfByte)
/*     */   {
/* 180 */     if (paramArrayOfByte != null)
/*     */     {
/*     */ 
/*     */ 
/* 184 */       _verifyRelease(paramArrayOfByte, this._readIOBuffer);
/* 185 */       this._readIOBuffer = null;
/* 186 */       this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER, paramArrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseWriteEncodingBuffer(byte[] paramArrayOfByte)
/*     */   {
/* 192 */     if (paramArrayOfByte != null)
/*     */     {
/*     */ 
/*     */ 
/* 196 */       _verifyRelease(paramArrayOfByte, this._writeEncodingBuffer);
/* 197 */       this._writeEncodingBuffer = null;
/* 198 */       this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER, paramArrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseBase64Buffer(byte[] paramArrayOfByte)
/*     */   {
/* 204 */     if (paramArrayOfByte != null) {
/* 205 */       _verifyRelease(paramArrayOfByte, this._base64Buffer);
/* 206 */       this._base64Buffer = null;
/* 207 */       this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER, paramArrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseTokenBuffer(char[] paramArrayOfChar)
/*     */   {
/* 213 */     if (paramArrayOfChar != null) {
/* 214 */       _verifyRelease(paramArrayOfChar, this._tokenCBuffer);
/* 215 */       this._tokenCBuffer = null;
/* 216 */       this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER, paramArrayOfChar);
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseConcatBuffer(char[] paramArrayOfChar)
/*     */   {
/* 222 */     if (paramArrayOfChar != null) {
/* 223 */       _verifyRelease(paramArrayOfChar, this._concatCBuffer);
/* 224 */       this._concatCBuffer = null;
/* 225 */       this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER, paramArrayOfChar);
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseNameCopyBuffer(char[] paramArrayOfChar)
/*     */   {
/* 231 */     if (paramArrayOfChar != null) {
/* 232 */       _verifyRelease(paramArrayOfChar, this._nameCopyBuffer);
/* 233 */       this._nameCopyBuffer = null;
/* 234 */       this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, paramArrayOfChar);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final void _verifyAlloc(Object paramObject)
/*     */   {
/* 246 */     if (paramObject != null) {
/* 247 */       throw new IllegalStateException("Trying to call same allocXxx() method second time");
/*     */     }
/*     */   }
/*     */   
/*     */   private final void _verifyRelease(Object paramObject1, Object paramObject2)
/*     */   {
/* 253 */     if (paramObject1 != paramObject2) {
/* 254 */       throw new IllegalArgumentException("Trying to release buffer not owned by the context");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/IOContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */