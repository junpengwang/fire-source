/*     */ package org.shaded.apache.http.impl.entity;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.entity.BasicHttpEntity;
/*     */ import org.shaded.apache.http.entity.ContentLengthStrategy;
/*     */ import org.shaded.apache.http.impl.io.ChunkedInputStream;
/*     */ import org.shaded.apache.http.impl.io.ContentLengthInputStream;
/*     */ import org.shaded.apache.http.impl.io.IdentityInputStream;
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
/*     */ public class EntityDeserializer
/*     */ {
/*     */   private final ContentLengthStrategy lenStrategy;
/*     */   
/*     */   public EntityDeserializer(ContentLengthStrategy lenStrategy)
/*     */   {
/*  73 */     if (lenStrategy == null) {
/*  74 */       throw new IllegalArgumentException("Content length strategy may not be null");
/*     */     }
/*  76 */     this.lenStrategy = lenStrategy;
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
/*     */ 
/*     */ 
/*     */   protected BasicHttpEntity doDeserialize(SessionInputBuffer inbuffer, HttpMessage message)
/*     */     throws HttpException, IOException
/*     */   {
/*  97 */     BasicHttpEntity entity = new BasicHttpEntity();
/*     */     
/*  99 */     long len = this.lenStrategy.determineLength(message);
/* 100 */     if (len == -2L) {
/* 101 */       entity.setChunked(true);
/* 102 */       entity.setContentLength(-1L);
/* 103 */       entity.setContent(new ChunkedInputStream(inbuffer));
/* 104 */     } else if (len == -1L) {
/* 105 */       entity.setChunked(false);
/* 106 */       entity.setContentLength(-1L);
/* 107 */       entity.setContent(new IdentityInputStream(inbuffer));
/*     */     } else {
/* 109 */       entity.setChunked(false);
/* 110 */       entity.setContentLength(len);
/* 111 */       entity.setContent(new ContentLengthInputStream(inbuffer, len));
/*     */     }
/*     */     
/* 114 */     Header contentTypeHeader = message.getFirstHeader("Content-Type");
/* 115 */     if (contentTypeHeader != null) {
/* 116 */       entity.setContentType(contentTypeHeader);
/*     */     }
/* 118 */     Header contentEncodingHeader = message.getFirstHeader("Content-Encoding");
/* 119 */     if (contentEncodingHeader != null) {
/* 120 */       entity.setContentEncoding(contentEncodingHeader);
/*     */     }
/* 122 */     return entity;
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
/*     */ 
/*     */   public HttpEntity deserialize(SessionInputBuffer inbuffer, HttpMessage message)
/*     */     throws HttpException, IOException
/*     */   {
/* 142 */     if (inbuffer == null) {
/* 143 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*     */     }
/* 145 */     if (message == null) {
/* 146 */       throw new IllegalArgumentException("HTTP message may not be null");
/*     */     }
/* 148 */     return doDeserialize(inbuffer, message);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/entity/EntityDeserializer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */