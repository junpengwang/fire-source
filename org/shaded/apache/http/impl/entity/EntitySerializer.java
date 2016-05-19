/*     */ package org.shaded.apache.http.impl.entity;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.entity.ContentLengthStrategy;
/*     */ import org.shaded.apache.http.impl.io.ChunkedOutputStream;
/*     */ import org.shaded.apache.http.impl.io.ContentLengthOutputStream;
/*     */ import org.shaded.apache.http.impl.io.IdentityOutputStream;
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
/*     */ public class EntitySerializer
/*     */ {
/*     */   private final ContentLengthStrategy lenStrategy;
/*     */   
/*     */   public EntitySerializer(ContentLengthStrategy lenStrategy)
/*     */   {
/*  70 */     if (lenStrategy == null) {
/*  71 */       throw new IllegalArgumentException("Content length strategy may not be null");
/*     */     }
/*  73 */     this.lenStrategy = lenStrategy;
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
/*     */   protected OutputStream doSerialize(SessionOutputBuffer outbuffer, HttpMessage message)
/*     */     throws HttpException, IOException
/*     */   {
/*  93 */     long len = this.lenStrategy.determineLength(message);
/*  94 */     if (len == -2L)
/*  95 */       return new ChunkedOutputStream(outbuffer);
/*  96 */     if (len == -1L) {
/*  97 */       return new IdentityOutputStream(outbuffer);
/*     */     }
/*  99 */     return new ContentLengthOutputStream(outbuffer, len);
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
/*     */   public void serialize(SessionOutputBuffer outbuffer, HttpMessage message, HttpEntity entity)
/*     */     throws HttpException, IOException
/*     */   {
/* 117 */     if (outbuffer == null) {
/* 118 */       throw new IllegalArgumentException("Session output buffer may not be null");
/*     */     }
/* 120 */     if (message == null) {
/* 121 */       throw new IllegalArgumentException("HTTP message may not be null");
/*     */     }
/* 123 */     if (entity == null) {
/* 124 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*     */     }
/* 126 */     OutputStream outstream = doSerialize(outbuffer, message);
/* 127 */     entity.writeTo(outstream);
/* 128 */     outstream.close();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/entity/EntitySerializer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */