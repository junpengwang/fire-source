/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContentLengthInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private static final int BUFFER_SIZE = 2048;
/*     */   private long contentLength;
/*  65 */   private long pos = 0L;
/*     */   
/*     */ 
/*  68 */   private boolean closed = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  73 */   private SessionInputBuffer in = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContentLengthInputStream(SessionInputBuffer in, long contentLength)
/*     */   {
/*  85 */     if (in == null) {
/*  86 */       throw new IllegalArgumentException("Input stream may not be null");
/*     */     }
/*  88 */     if (contentLength < 0L) {
/*  89 */       throw new IllegalArgumentException("Content length may not be negative");
/*     */     }
/*  91 */     this.in = in;
/*  92 */     this.contentLength = contentLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 103 */     if (!this.closed) {
/*     */       try {
/* 105 */         byte[] buffer = new byte['ࠀ'];
/* 106 */         while (read(buffer) >= 0) {}
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/* 111 */         this.closed = true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 124 */     if (this.closed) {
/* 125 */       throw new IOException("Attempted read from closed stream.");
/*     */     }
/*     */     
/* 128 */     if (this.pos >= this.contentLength) {
/* 129 */       return -1;
/*     */     }
/* 131 */     this.pos += 1L;
/* 132 */     return this.in.read();
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
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 148 */     if (this.closed) {
/* 149 */       throw new IOException("Attempted read from closed stream.");
/*     */     }
/*     */     
/* 152 */     if (this.pos >= this.contentLength) {
/* 153 */       return -1;
/*     */     }
/*     */     
/* 156 */     if (this.pos + len > this.contentLength) {
/* 157 */       len = (int)(this.contentLength - this.pos);
/*     */     }
/* 159 */     int count = this.in.read(b, off, len);
/* 160 */     this.pos += count;
/* 161 */     return count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] b)
/*     */     throws IOException
/*     */   {
/* 173 */     return read(b, 0, b.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long skip(long n)
/*     */     throws IOException
/*     */   {
/* 185 */     if (n <= 0L) {
/* 186 */       return 0L;
/*     */     }
/* 188 */     byte[] buffer = new byte['ࠀ'];
/*     */     
/*     */ 
/* 191 */     long remaining = Math.min(n, this.contentLength - this.pos);
/*     */     
/* 193 */     long count = 0L;
/* 194 */     while (remaining > 0L) {
/* 195 */       int l = read(buffer, 0, (int)Math.min(2048L, remaining));
/* 196 */       if (l == -1) {
/*     */         break;
/*     */       }
/* 199 */       count += l;
/* 200 */       remaining -= l;
/*     */     }
/* 202 */     return count;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/ContentLengthInputStream.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */