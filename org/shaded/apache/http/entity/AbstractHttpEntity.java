/*     */ package org.shaded.apache.http.entity;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.message.BasicHeader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractHttpEntity
/*     */   implements HttpEntity
/*     */ {
/*     */   protected Header contentType;
/*     */   protected Header contentEncoding;
/*     */   protected boolean chunked;
/*     */   
/*     */   public Header getContentType()
/*     */   {
/*  75 */     return this.contentType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Header getContentEncoding()
/*     */   {
/*  87 */     return this.contentEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isChunked()
/*     */   {
/*  98 */     return this.chunked;
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
/*     */   public void setContentType(Header contentType)
/*     */   {
/* 111 */     this.contentType = contentType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setContentType(String ctString)
/*     */   {
/* 123 */     Header h = null;
/* 124 */     if (ctString != null) {
/* 125 */       h = new BasicHeader("Content-Type", ctString);
/*     */     }
/* 127 */     setContentType(h);
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
/*     */   public void setContentEncoding(Header contentEncoding)
/*     */   {
/* 140 */     this.contentEncoding = contentEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setContentEncoding(String ceString)
/*     */   {
/* 152 */     Header h = null;
/* 153 */     if (ceString != null) {
/* 154 */       h = new BasicHeader("Content-Encoding", ceString);
/*     */     }
/* 156 */     setContentEncoding(h);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChunked(boolean b)
/*     */   {
/* 168 */     this.chunked = b;
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
/*     */   public void consumeContent()
/*     */     throws IOException, UnsupportedOperationException
/*     */   {
/* 187 */     if (isStreaming()) {
/* 188 */       throw new UnsupportedOperationException("streaming entity does not implement consumeContent()");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/AbstractHttpEntity.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */