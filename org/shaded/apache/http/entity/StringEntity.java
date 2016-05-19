/*     */ package org.shaded.apache.http.entity;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringEntity
/*     */   extends AbstractHttpEntity
/*     */   implements Cloneable
/*     */ {
/*     */   protected final byte[] content;
/*     */   
/*     */   public StringEntity(String s, String charset)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  58 */     if (s == null) {
/*  59 */       throw new IllegalArgumentException("Source string may not be null");
/*     */     }
/*  61 */     if (charset == null) {
/*  62 */       charset = "ISO-8859-1";
/*     */     }
/*  64 */     this.content = s.getBytes(charset);
/*  65 */     setContentType("text/plain; charset=" + charset);
/*     */   }
/*     */   
/*     */   public StringEntity(String s) throws UnsupportedEncodingException
/*     */   {
/*  70 */     this(s, null);
/*     */   }
/*     */   
/*     */   public boolean isRepeatable() {
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   public long getContentLength() {
/*  78 */     return this.content.length;
/*     */   }
/*     */   
/*     */   public InputStream getContent() throws IOException {
/*  82 */     return new ByteArrayInputStream(this.content);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream outstream) throws IOException {
/*  86 */     if (outstream == null) {
/*  87 */       throw new IllegalArgumentException("Output stream may not be null");
/*     */     }
/*  89 */     outstream.write(this.content);
/*  90 */     outstream.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isStreaming()
/*     */   {
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 103 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/StringEntity.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */