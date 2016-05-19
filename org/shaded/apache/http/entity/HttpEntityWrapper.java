/*     */ package org.shaded.apache.http.entity;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpEntityWrapper
/*     */   implements HttpEntity
/*     */ {
/*     */   protected HttpEntity wrappedEntity;
/*     */   
/*     */   public HttpEntityWrapper(HttpEntity wrapped)
/*     */   {
/*  65 */     if (wrapped == null) {
/*  66 */       throw new IllegalArgumentException("wrapped entity must not be null");
/*     */     }
/*     */     
/*  69 */     this.wrappedEntity = wrapped;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isRepeatable()
/*     */   {
/*  75 */     return this.wrappedEntity.isRepeatable();
/*     */   }
/*     */   
/*     */   public boolean isChunked() {
/*  79 */     return this.wrappedEntity.isChunked();
/*     */   }
/*     */   
/*     */   public long getContentLength() {
/*  83 */     return this.wrappedEntity.getContentLength();
/*     */   }
/*     */   
/*     */   public Header getContentType() {
/*  87 */     return this.wrappedEntity.getContentType();
/*     */   }
/*     */   
/*     */   public Header getContentEncoding() {
/*  91 */     return this.wrappedEntity.getContentEncoding();
/*     */   }
/*     */   
/*     */   public InputStream getContent() throws IOException
/*     */   {
/*  96 */     return this.wrappedEntity.getContent();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream outstream) throws IOException
/*     */   {
/* 101 */     this.wrappedEntity.writeTo(outstream);
/*     */   }
/*     */   
/*     */   public boolean isStreaming() {
/* 105 */     return this.wrappedEntity.isStreaming();
/*     */   }
/*     */   
/*     */   public void consumeContent() throws IOException
/*     */   {
/* 110 */     this.wrappedEntity.consumeContent();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/HttpEntityWrapper.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */