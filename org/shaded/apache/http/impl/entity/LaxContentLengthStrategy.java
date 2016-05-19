/*     */ package org.shaded.apache.http.impl.entity;
/*     */ 
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.ParseException;
/*     */ import org.shaded.apache.http.ProtocolException;
/*     */ import org.shaded.apache.http.entity.ContentLengthStrategy;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LaxContentLengthStrategy
/*     */   implements ContentLengthStrategy
/*     */ {
/*     */   public long determineLength(HttpMessage message)
/*     */     throws HttpException
/*     */   {
/*  65 */     if (message == null) {
/*  66 */       throw new IllegalArgumentException("HTTP message may not be null");
/*     */     }
/*     */     
/*  69 */     HttpParams params = message.getParams();
/*  70 */     boolean strict = params.isParameterTrue("http.protocol.strict-transfer-encoding");
/*     */     
/*  72 */     Header transferEncodingHeader = message.getFirstHeader("Transfer-Encoding");
/*  73 */     Header contentLengthHeader = message.getFirstHeader("Content-Length");
/*     */     
/*     */ 
/*  76 */     if (transferEncodingHeader != null) {
/*  77 */       HeaderElement[] encodings = null;
/*     */       try {
/*  79 */         encodings = transferEncodingHeader.getElements();
/*     */       } catch (ParseException px) {
/*  81 */         throw new ProtocolException("Invalid Transfer-Encoding header value: " + transferEncodingHeader, px);
/*     */       }
/*     */       
/*     */ 
/*  85 */       if (strict)
/*     */       {
/*  87 */         for (int i = 0; i < encodings.length; i++) {
/*  88 */           String encoding = encodings[i].getName();
/*  89 */           if ((encoding != null) && (encoding.length() > 0) && (!encoding.equalsIgnoreCase("chunked")) && (!encoding.equalsIgnoreCase("identity")))
/*     */           {
/*     */ 
/*  92 */             throw new ProtocolException("Unsupported transfer encoding: " + encoding);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  97 */       int len = encodings.length;
/*  98 */       if ("identity".equalsIgnoreCase(transferEncodingHeader.getValue()))
/*  99 */         return -1L;
/* 100 */       if ((len > 0) && ("chunked".equalsIgnoreCase(encodings[(len - 1)].getName())))
/*     */       {
/* 102 */         return -2L;
/*     */       }
/* 104 */       if (strict) {
/* 105 */         throw new ProtocolException("Chunk-encoding must be the last one applied");
/*     */       }
/* 107 */       return -1L;
/*     */     }
/* 109 */     if (contentLengthHeader != null) {
/* 110 */       long contentlen = -1L;
/* 111 */       Header[] headers = message.getHeaders("Content-Length");
/* 112 */       if ((strict) && (headers.length > 1)) {
/* 113 */         throw new ProtocolException("Multiple content length headers");
/*     */       }
/* 115 */       for (int i = headers.length - 1; i >= 0; i--) {
/* 116 */         Header header = headers[i];
/*     */         try {
/* 118 */           contentlen = Long.parseLong(header.getValue());
/*     */         }
/*     */         catch (NumberFormatException e) {
/* 121 */           if (strict) {
/* 122 */             throw new ProtocolException("Invalid content length: " + header.getValue());
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 127 */       if (contentlen >= 0L) {
/* 128 */         return contentlen;
/*     */       }
/* 130 */       return -1L;
/*     */     }
/*     */     
/* 133 */     return -1L;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/entity/LaxContentLengthStrategy.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */