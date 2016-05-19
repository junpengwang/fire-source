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
/*     */ public class ContentLengthOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private final SessionOutputBuffer out;
/*     */   private final long contentLength;
/*  69 */   private long total = 0L;
/*     */   
/*     */ 
/*  72 */   private boolean closed = false;
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
/*     */   public ContentLengthOutputStream(SessionOutputBuffer out, long contentLength)
/*     */   {
/*  86 */     if (out == null) {
/*  87 */       throw new IllegalArgumentException("Session output buffer may not be null");
/*     */     }
/*  89 */     if (contentLength < 0L) {
/*  90 */       throw new IllegalArgumentException("Content length may not be negative");
/*     */     }
/*  92 */     this.out = out;
/*  93 */     this.contentLength = contentLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 102 */     if (!this.closed) {
/* 103 */       this.closed = true;
/* 104 */       this.out.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 109 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 113 */     if (this.closed) {
/* 114 */       throw new IOException("Attempted write to closed stream.");
/*     */     }
/* 116 */     if (this.total < this.contentLength) {
/* 117 */       long max = this.contentLength - this.total;
/* 118 */       if (len > max) {
/* 119 */         len = (int)max;
/*     */       }
/* 121 */       this.out.write(b, off, len);
/* 122 */       this.total += len;
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 127 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/* 131 */     if (this.closed) {
/* 132 */       throw new IOException("Attempted write to closed stream.");
/*     */     }
/* 134 */     if (this.total < this.contentLength) {
/* 135 */       this.out.write(b);
/* 136 */       this.total += 1L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/ContentLengthOutputStream.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */