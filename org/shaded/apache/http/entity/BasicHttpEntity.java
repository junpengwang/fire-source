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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicHttpEntity
/*     */   extends AbstractHttpEntity
/*     */ {
/*     */   private InputStream content;
/*     */   private boolean contentObtained;
/*     */   private long length;
/*     */   
/*     */   public BasicHttpEntity()
/*     */   {
/*  60 */     this.length = -1L;
/*     */   }
/*     */   
/*     */   public long getContentLength()
/*     */   {
/*  65 */     return this.length;
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
/*     */   public InputStream getContent()
/*     */     throws IllegalStateException
/*     */   {
/*  80 */     if (this.content == null) {
/*  81 */       throw new IllegalStateException("Content has not been provided");
/*     */     }
/*  83 */     if (this.contentObtained) {
/*  84 */       throw new IllegalStateException("Content has been consumed");
/*     */     }
/*  86 */     this.contentObtained = true;
/*  87 */     return this.content;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRepeatable()
/*     */   {
/*  97 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setContentLength(long len)
/*     */   {
/* 107 */     this.length = len;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setContent(InputStream instream)
/*     */   {
/* 117 */     this.content = instream;
/* 118 */     this.contentObtained = false;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream outstream) throws IOException
/*     */   {
/* 123 */     if (outstream == null) {
/* 124 */       throw new IllegalArgumentException("Output stream may not be null");
/*     */     }
/* 126 */     InputStream instream = getContent();
/*     */     
/* 128 */     byte[] tmp = new byte['ࠀ'];
/* 129 */     int l; while ((l = instream.read(tmp)) != -1) {
/* 130 */       outstream.write(tmp, 0, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isStreaming()
/*     */   {
/* 136 */     return (!this.contentObtained) && (this.content != null);
/*     */   }
/*     */   
/*     */   public void consumeContent() throws IOException
/*     */   {
/* 141 */     if (this.content != null) {
/* 142 */       this.content.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/BasicHttpEntity.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */