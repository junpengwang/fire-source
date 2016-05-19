/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.util.BufferRecycler;
/*     */ import com.shaded.fasterxml.jackson.core.util.TextBuffer;
/*     */ import java.io.Writer;
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
/*     */ public final class SegmentedStringWriter
/*     */   extends Writer
/*     */ {
/*     */   protected final TextBuffer _buffer;
/*     */   
/*     */   public SegmentedStringWriter(BufferRecycler paramBufferRecycler)
/*     */   {
/*  24 */     this._buffer = new TextBuffer(paramBufferRecycler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Writer append(char paramChar)
/*     */   {
/*  36 */     write(paramChar);
/*  37 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public Writer append(CharSequence paramCharSequence)
/*     */   {
/*  43 */     String str = paramCharSequence.toString();
/*  44 */     this._buffer.append(str, 0, str.length());
/*  45 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public Writer append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/*  51 */     String str = paramCharSequence.subSequence(paramInt1, paramInt2).toString();
/*  52 */     this._buffer.append(str, 0, str.length());
/*  53 */     return this;
/*     */   }
/*     */   
/*     */   public void close() {}
/*     */   
/*     */   public void flush() {}
/*     */   
/*     */   public void write(char[] paramArrayOfChar)
/*     */   {
/*  62 */     this._buffer.append(paramArrayOfChar, 0, paramArrayOfChar.length);
/*     */   }
/*     */   
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/*  67 */     this._buffer.append(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void write(int paramInt)
/*     */   {
/*  72 */     this._buffer.append((char)paramInt);
/*     */   }
/*     */   
/*     */   public void write(String paramString) {
/*  76 */     this._buffer.append(paramString, 0, paramString.length());
/*     */   }
/*     */   
/*     */   public void write(String paramString, int paramInt1, int paramInt2) {
/*  80 */     this._buffer.append(paramString, paramInt1, paramInt2);
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
/*     */   public String getAndClear()
/*     */   {
/*  98 */     String str = this._buffer.contentsAsString();
/*  99 */     this._buffer.releaseBuffers();
/* 100 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/SegmentedStringWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */