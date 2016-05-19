/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.ProtocolException;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.client.methods.HttpUriRequest;
/*     */ import org.shaded.apache.http.message.AbstractHttpMessage;
/*     */ import org.shaded.apache.http.message.BasicRequestLine;
/*     */ import org.shaded.apache.http.message.HeaderGroup;
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
/*     */ @NotThreadSafe
/*     */ public class RequestWrapper
/*     */   extends AbstractHttpMessage
/*     */   implements HttpUriRequest
/*     */ {
/*     */   private final HttpRequest original;
/*     */   private URI uri;
/*     */   private String method;
/*     */   private ProtocolVersion version;
/*     */   private int execCount;
/*     */   
/*     */   public RequestWrapper(HttpRequest request)
/*     */     throws ProtocolException
/*     */   {
/*  67 */     if (request == null) {
/*  68 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/*  70 */     this.original = request;
/*  71 */     setParams(request.getParams());
/*     */     
/*  73 */     if ((request instanceof HttpUriRequest)) {
/*  74 */       this.uri = ((HttpUriRequest)request).getURI();
/*  75 */       this.method = ((HttpUriRequest)request).getMethod();
/*  76 */       this.version = null;
/*     */     } else {
/*  78 */       RequestLine requestLine = request.getRequestLine();
/*     */       try {
/*  80 */         this.uri = new URI(requestLine.getUri());
/*     */       } catch (URISyntaxException ex) {
/*  82 */         throw new ProtocolException("Invalid request URI: " + requestLine.getUri(), ex);
/*     */       }
/*     */       
/*  85 */       this.method = requestLine.getMethod();
/*  86 */       this.version = request.getProtocolVersion();
/*     */     }
/*  88 */     this.execCount = 0;
/*     */   }
/*     */   
/*     */   public void resetHeaders()
/*     */   {
/*  93 */     this.headergroup.clear();
/*  94 */     setHeaders(this.original.getAllHeaders());
/*     */   }
/*     */   
/*     */   public String getMethod() {
/*  98 */     return this.method;
/*     */   }
/*     */   
/*     */   public void setMethod(String method) {
/* 102 */     if (method == null) {
/* 103 */       throw new IllegalArgumentException("Method name may not be null");
/*     */     }
/* 105 */     this.method = method;
/*     */   }
/*     */   
/*     */   public ProtocolVersion getProtocolVersion() {
/* 109 */     if (this.version == null) {
/* 110 */       this.version = HttpProtocolParams.getVersion(getParams());
/*     */     }
/* 112 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setProtocolVersion(ProtocolVersion version) {
/* 116 */     this.version = version;
/*     */   }
/*     */   
/*     */   public URI getURI()
/*     */   {
/* 121 */     return this.uri;
/*     */   }
/*     */   
/*     */   public void setURI(URI uri) {
/* 125 */     this.uri = uri;
/*     */   }
/*     */   
/*     */   public RequestLine getRequestLine() {
/* 129 */     String method = getMethod();
/* 130 */     ProtocolVersion ver = getProtocolVersion();
/* 131 */     String uritext = null;
/* 132 */     if (this.uri != null) {
/* 133 */       uritext = this.uri.toASCIIString();
/*     */     }
/* 135 */     if ((uritext == null) || (uritext.length() == 0)) {
/* 136 */       uritext = "/";
/*     */     }
/* 138 */     return new BasicRequestLine(method, uritext, ver);
/*     */   }
/*     */   
/*     */   public void abort() throws UnsupportedOperationException {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isAborted() {
/* 146 */     return false;
/*     */   }
/*     */   
/*     */   public HttpRequest getOriginal() {
/* 150 */     return this.original;
/*     */   }
/*     */   
/*     */   public boolean isRepeatable() {
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public int getExecCount() {
/* 158 */     return this.execCount;
/*     */   }
/*     */   
/*     */   public void incrementExecCount() {
/* 162 */     this.execCount += 1;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/RequestWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */