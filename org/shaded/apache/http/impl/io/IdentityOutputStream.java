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
/*     */ public class IdentityOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private final SessionOutputBuffer out;
/*  62 */   private boolean closed = false;
/*     */   
/*     */   public IdentityOutputStream(SessionOutputBuffer out)
/*     */   {
/*  66 */     if (out == null) {
/*  67 */       throw new IllegalArgumentException("Session output buffer may not be null");
/*     */     }
/*  69 */     this.out = out;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  78 */     if (!this.closed) {
/*  79 */       this.closed = true;
/*  80 */       this.out.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/*  85 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  89 */     if (this.closed) {
/*  90 */       throw new IOException("Attempted write to closed stream.");
/*     */     }
/*  92 */     this.out.write(b, off, len);
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  96 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/* 100 */     if (this.closed) {
/* 101 */       throw new IOException("Attempted write to closed stream.");
/*     */     }
/* 103 */     this.out.write(b);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/IdentityOutputStream.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */