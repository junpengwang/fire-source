/*     */ package org.shaded.apache.http.entity;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputStreamEntity
/*     */   extends AbstractHttpEntity
/*     */ {
/*     */   private static final int BUFFER_SIZE = 2048;
/*     */   private final InputStream content;
/*     */   private final long length;
/*  53 */   private boolean consumed = false;
/*     */   
/*     */   public InputStreamEntity(InputStream instream, long length)
/*     */   {
/*  57 */     if (instream == null) {
/*  58 */       throw new IllegalArgumentException("Source input stream may not be null");
/*     */     }
/*  60 */     this.content = instream;
/*  61 */     this.length = length;
/*     */   }
/*     */   
/*     */   public boolean isRepeatable() {
/*  65 */     return false;
/*     */   }
/*     */   
/*     */   public long getContentLength() {
/*  69 */     return this.length;
/*     */   }
/*     */   
/*     */   public InputStream getContent() throws IOException {
/*  73 */     return this.content;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream outstream) throws IOException {
/*  77 */     if (outstream == null) {
/*  78 */       throw new IllegalArgumentException("Output stream may not be null");
/*     */     }
/*  80 */     InputStream instream = this.content;
/*  81 */     byte[] buffer = new byte['ࠀ'];
/*     */     
/*  83 */     if (this.length < 0L) {
/*     */       int l;
/*  85 */       while ((l = instream.read(buffer)) != -1) {
/*  86 */         outstream.write(buffer, 0, l);
/*     */       }
/*     */     }
/*     */     
/*  90 */     long remaining = this.length;
/*  91 */     while (remaining > 0L) {
/*  92 */       int l = instream.read(buffer, 0, (int)Math.min(2048L, remaining));
/*  93 */       if (l == -1) {
/*     */         break;
/*     */       }
/*  96 */       outstream.write(buffer, 0, l);
/*  97 */       remaining -= l;
/*     */     }
/*     */     
/* 100 */     this.consumed = true;
/*     */   }
/*     */   
/*     */   public boolean isStreaming()
/*     */   {
/* 105 */     return !this.consumed;
/*     */   }
/*     */   
/*     */   public void consumeContent() throws IOException
/*     */   {
/* 110 */     this.consumed = true;
/*     */     
/*     */ 
/* 113 */     this.content.close();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/InputStreamEntity.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */