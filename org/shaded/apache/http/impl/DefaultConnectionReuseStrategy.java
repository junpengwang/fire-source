/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import org.shaded.apache.http.ConnectionReuseStrategy;
/*     */ import org.shaded.apache.http.HeaderIterator;
/*     */ import org.shaded.apache.http.HttpConnection;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpVersion;
/*     */ import org.shaded.apache.http.ParseException;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.TokenIterator;
/*     */ import org.shaded.apache.http.message.BasicTokenIterator;
/*     */ import org.shaded.apache.http.protocol.HttpContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultConnectionReuseStrategy
/*     */   implements ConnectionReuseStrategy
/*     */ {
/*     */   public boolean keepAlive(HttpResponse response, HttpContext context)
/*     */   {
/*  78 */     if (response == null) {
/*  79 */       throw new IllegalArgumentException("HTTP response may not be null.");
/*     */     }
/*     */     
/*  82 */     if (context == null) {
/*  83 */       throw new IllegalArgumentException("HTTP context may not be null.");
/*     */     }
/*     */     
/*     */ 
/*  87 */     HttpConnection conn = (HttpConnection)context.getAttribute("http.connection");
/*     */     
/*     */ 
/*  90 */     if ((conn != null) && (!conn.isOpen())) {
/*  91 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  96 */     HttpEntity entity = response.getEntity();
/*  97 */     ProtocolVersion ver = response.getStatusLine().getProtocolVersion();
/*  98 */     if ((entity != null) && 
/*  99 */       (entity.getContentLength() < 0L) && (
/* 100 */       (!entity.isChunked()) || (ver.lessEquals(HttpVersion.HTTP_1_0))))
/*     */     {
/*     */ 
/*     */ 
/* 104 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 112 */     HeaderIterator hit = response.headerIterator("Connection");
/* 113 */     if (!hit.hasNext()) {
/* 114 */       hit = response.headerIterator("Proxy-Connection");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 139 */     if (hit.hasNext()) {
/*     */       try {
/* 141 */         TokenIterator ti = createTokenIterator(hit);
/* 142 */         boolean keepalive = false;
/* 143 */         while (ti.hasNext()) {
/* 144 */           String token = ti.nextToken();
/* 145 */           if ("Close".equalsIgnoreCase(token))
/* 146 */             return false;
/* 147 */           if ("Keep-Alive".equalsIgnoreCase(token))
/*     */           {
/* 149 */             keepalive = true;
/*     */           }
/*     */         }
/* 152 */         if (keepalive) {
/* 153 */           return true;
/*     */         }
/*     */         
/*     */       }
/*     */       catch (ParseException px)
/*     */       {
/* 159 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 164 */     return !ver.lessEquals(HttpVersion.HTTP_1_0);
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
/*     */   protected TokenIterator createTokenIterator(HeaderIterator hit)
/*     */   {
/* 178 */     return new BasicTokenIterator(hit);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/DefaultConnectionReuseStrategy.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */