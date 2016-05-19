/*     */ package org.shaded.apache.http.entity;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
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
/*     */ public class FileEntity
/*     */   extends AbstractHttpEntity
/*     */   implements Cloneable
/*     */ {
/*     */   protected final File file;
/*     */   
/*     */   public FileEntity(File file, String contentType)
/*     */   {
/*  54 */     if (file == null) {
/*  55 */       throw new IllegalArgumentException("File may not be null");
/*     */     }
/*  57 */     this.file = file;
/*  58 */     setContentType(contentType);
/*     */   }
/*     */   
/*     */   public boolean isRepeatable() {
/*  62 */     return true;
/*     */   }
/*     */   
/*     */   public long getContentLength() {
/*  66 */     return this.file.length();
/*     */   }
/*     */   
/*     */   public InputStream getContent() throws IOException {
/*  70 */     return new FileInputStream(this.file);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream outstream) throws IOException {
/*  74 */     if (outstream == null) {
/*  75 */       throw new IllegalArgumentException("Output stream may not be null");
/*     */     }
/*  77 */     InputStream instream = new FileInputStream(this.file);
/*     */     try {
/*  79 */       byte[] tmp = new byte['က'];
/*     */       int l;
/*  81 */       while ((l = instream.read(tmp)) != -1) {
/*  82 */         outstream.write(tmp, 0, l);
/*     */       }
/*  84 */       outstream.flush();
/*     */     } finally {
/*  86 */       instream.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isStreaming()
/*     */   {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 102 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/FileEntity.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */