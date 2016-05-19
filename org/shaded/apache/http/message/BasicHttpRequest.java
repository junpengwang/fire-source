/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.params.HttpProtocolParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicHttpRequest
/*     */   extends AbstractHttpMessage
/*     */   implements HttpRequest
/*     */ {
/*     */   private final String method;
/*     */   private final String uri;
/*     */   private RequestLine requestline;
/*     */   
/*     */   public BasicHttpRequest(String method, String uri)
/*     */   {
/*  71 */     if (method == null) {
/*  72 */       throw new IllegalArgumentException("Method name may not be null");
/*     */     }
/*  74 */     if (uri == null) {
/*  75 */       throw new IllegalArgumentException("Request URI may not be null");
/*     */     }
/*  77 */     this.method = method;
/*  78 */     this.uri = uri;
/*  79 */     this.requestline = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicHttpRequest(String method, String uri, ProtocolVersion ver)
/*     */   {
/*  91 */     this(new BasicRequestLine(method, uri, ver));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicHttpRequest(RequestLine requestline)
/*     */   {
/* 101 */     if (requestline == null) {
/* 102 */       throw new IllegalArgumentException("Request line may not be null");
/*     */     }
/* 104 */     this.requestline = requestline;
/* 105 */     this.method = requestline.getMethod();
/* 106 */     this.uri = requestline.getUri();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ProtocolVersion getProtocolVersion()
/*     */   {
/* 118 */     return getRequestLine().getProtocolVersion();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RequestLine getRequestLine()
/*     */   {
/* 129 */     if (this.requestline == null) {
/* 130 */       ProtocolVersion ver = HttpProtocolParams.getVersion(getParams());
/* 131 */       this.requestline = new BasicRequestLine(this.method, this.uri, ver);
/*     */     }
/* 133 */     return this.requestline;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHttpRequest.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */