/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.UnknownHostException;
/*     */ import javax.net.ssl.SSLHandshakeException;
/*     */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.NoHttpResponseException;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.client.HttpRequestRetryHandler;
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
/*     */ @Immutable
/*     */ public class DefaultHttpRequestRetryHandler
/*     */   implements HttpRequestRetryHandler
/*     */ {
/*     */   private final int retryCount;
/*     */   private final boolean requestSentRetryEnabled;
/*     */   
/*     */   public DefaultHttpRequestRetryHandler(int retryCount, boolean requestSentRetryEnabled)
/*     */   {
/*  66 */     this.retryCount = retryCount;
/*  67 */     this.requestSentRetryEnabled = requestSentRetryEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DefaultHttpRequestRetryHandler()
/*     */   {
/*  74 */     this(3, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean retryRequest(IOException exception, int executionCount, HttpContext context)
/*     */   {
/*  84 */     if (exception == null) {
/*  85 */       throw new IllegalArgumentException("Exception parameter may not be null");
/*     */     }
/*  87 */     if (context == null) {
/*  88 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/*  90 */     if (executionCount > this.retryCount)
/*     */     {
/*  92 */       return false;
/*     */     }
/*  94 */     if ((exception instanceof NoHttpResponseException))
/*     */     {
/*  96 */       return true;
/*     */     }
/*  98 */     if ((exception instanceof InterruptedIOException))
/*     */     {
/* 100 */       return false;
/*     */     }
/* 102 */     if ((exception instanceof UnknownHostException))
/*     */     {
/* 104 */       return false;
/*     */     }
/* 106 */     if ((exception instanceof ConnectException))
/*     */     {
/* 108 */       return false;
/*     */     }
/* 110 */     if ((exception instanceof SSLHandshakeException))
/*     */     {
/* 112 */       return false;
/*     */     }
/*     */     
/* 115 */     HttpRequest request = (HttpRequest)context.getAttribute("http.request");
/*     */     
/* 117 */     boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
/* 118 */     if (idempotent)
/*     */     {
/* 120 */       return true;
/*     */     }
/*     */     
/* 123 */     Boolean b = (Boolean)context.getAttribute("http.request_sent");
/*     */     
/* 125 */     boolean sent = (b != null) && (b.booleanValue());
/*     */     
/* 127 */     if ((!sent) || (this.requestSentRetryEnabled))
/*     */     {
/*     */ 
/* 130 */       return true;
/*     */     }
/*     */     
/* 133 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRequestSentRetryEnabled()
/*     */   {
/* 141 */     return this.requestSentRetryEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getRetryCount()
/*     */   {
/* 148 */     return this.retryCount;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/DefaultHttpRequestRetryHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */