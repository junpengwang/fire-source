/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.shaded.apache.http.io.SessionOutputBuffer;
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
/*     */ 
/*     */ public class ChunkedOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private final SessionOutputBuffer out;
/*     */   private byte[] cache;
/*  58 */   private int cachePosition = 0;
/*     */   
/*  60 */   private boolean wroteLastChunk = false;
/*     */   
/*     */ 
/*  63 */   private boolean closed = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedOutputStream(SessionOutputBuffer out, int bufferSize)
/*     */     throws IOException
/*     */   {
/*  76 */     this.cache = new byte[bufferSize];
/*  77 */     this.out = out;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedOutputStream(SessionOutputBuffer out)
/*     */     throws IOException
/*     */   {
/*  89 */     this(out, 2048);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void flushCache()
/*     */     throws IOException
/*     */   {
/*  97 */     if (this.cachePosition > 0) {
/*  98 */       this.out.writeLine(Integer.toHexString(this.cachePosition));
/*  99 */       this.out.write(this.cache, 0, this.cachePosition);
/* 100 */       this.out.writeLine("");
/* 101 */       this.cachePosition = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void flushCacheWithAppend(byte[] bufferToAppend, int off, int len)
/*     */     throws IOException
/*     */   {
/* 110 */     this.out.writeLine(Integer.toHexString(this.cachePosition + len));
/* 111 */     this.out.write(this.cache, 0, this.cachePosition);
/* 112 */     this.out.write(bufferToAppend, off, len);
/* 113 */     this.out.writeLine("");
/* 114 */     this.cachePosition = 0;
/*     */   }
/*     */   
/*     */   protected void writeClosingChunk() throws IOException
/*     */   {
/* 119 */     this.out.writeLine("0");
/* 120 */     this.out.writeLine("");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void finish()
/*     */     throws IOException
/*     */   {
/* 130 */     if (!this.wroteLastChunk) {
/* 131 */       flushCache();
/* 132 */       writeClosingChunk();
/* 133 */       this.wroteLastChunk = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException
/*     */   {
/* 139 */     if (this.closed) {
/* 140 */       throw new IOException("Attempted write to closed stream.");
/*     */     }
/* 142 */     this.cache[this.cachePosition] = ((byte)b);
/* 143 */     this.cachePosition += 1;
/* 144 */     if (this.cachePosition == this.cache.length) { flushCache();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void write(byte[] b)
/*     */     throws IOException
/*     */   {
/* 152 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void write(byte[] src, int off, int len)
/*     */     throws IOException
/*     */   {
/* 160 */     if (this.closed) {
/* 161 */       throw new IOException("Attempted write to closed stream.");
/*     */     }
/* 163 */     if (len >= this.cache.length - this.cachePosition) {
/* 164 */       flushCacheWithAppend(src, off, len);
/*     */     } else {
/* 166 */       System.arraycopy(src, off, this.cache, this.cachePosition, len);
/* 167 */       this.cachePosition += len;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 175 */     flushCache();
/* 176 */     this.out.flush();
/*     */   }
/*     */   
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 183 */     if (!this.closed) {
/* 184 */       this.closed = true;
/* 185 */       finish();
/* 186 */       this.out.flush();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/ChunkedOutputStream.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */