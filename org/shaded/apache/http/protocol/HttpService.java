/*     */ package org.shaded.apache.http.protocol;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.ConnectionReuseStrategy;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpResponseFactory;
/*     */ import org.shaded.apache.http.HttpServerConnection;
/*     */ import org.shaded.apache.http.HttpVersion;
/*     */ import org.shaded.apache.http.MethodNotSupportedException;
/*     */ import org.shaded.apache.http.ProtocolException;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.UnsupportedHttpVersionException;
/*     */ import org.shaded.apache.http.entity.ByteArrayEntity;
/*     */ import org.shaded.apache.http.params.DefaultedHttpParams;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ import org.shaded.apache.http.util.EncodingUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpService
/*     */ {
/*  79 */   private HttpParams params = null;
/*  80 */   private HttpProcessor processor = null;
/*  81 */   private HttpRequestHandlerResolver handlerResolver = null;
/*  82 */   private ConnectionReuseStrategy connStrategy = null;
/*  83 */   private HttpResponseFactory responseFactory = null;
/*  84 */   private HttpExpectationVerifier expectationVerifier = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpService(HttpProcessor proc, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory)
/*     */   {
/*  98 */     setHttpProcessor(proc);
/*  99 */     setConnReuseStrategy(connStrategy);
/* 100 */     setResponseFactory(responseFactory);
/*     */   }
/*     */   
/*     */   public void setHttpProcessor(HttpProcessor processor) {
/* 104 */     if (processor == null) {
/* 105 */       throw new IllegalArgumentException("HTTP processor may not be null");
/*     */     }
/* 107 */     this.processor = processor;
/*     */   }
/*     */   
/*     */   public void setConnReuseStrategy(ConnectionReuseStrategy connStrategy) {
/* 111 */     if (connStrategy == null) {
/* 112 */       throw new IllegalArgumentException("Connection reuse strategy may not be null");
/*     */     }
/* 114 */     this.connStrategy = connStrategy;
/*     */   }
/*     */   
/*     */   public void setResponseFactory(HttpResponseFactory responseFactory) {
/* 118 */     if (responseFactory == null) {
/* 119 */       throw new IllegalArgumentException("Response factory may not be null");
/*     */     }
/* 121 */     this.responseFactory = responseFactory;
/*     */   }
/*     */   
/*     */   public void setHandlerResolver(HttpRequestHandlerResolver handlerResolver) {
/* 125 */     this.handlerResolver = handlerResolver;
/*     */   }
/*     */   
/*     */   public void setExpectationVerifier(HttpExpectationVerifier expectationVerifier) {
/* 129 */     this.expectationVerifier = expectationVerifier;
/*     */   }
/*     */   
/*     */   public HttpParams getParams() {
/* 133 */     return this.params;
/*     */   }
/*     */   
/*     */   public void setParams(HttpParams params) {
/* 137 */     this.params = params;
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
/*     */   public void handleRequest(HttpServerConnection conn, HttpContext context)
/*     */     throws IOException, HttpException
/*     */   {
/* 154 */     context.setAttribute("http.connection", conn);
/*     */     
/* 156 */     HttpResponse response = null;
/*     */     
/*     */     try
/*     */     {
/* 160 */       HttpRequest request = conn.receiveRequestHeader();
/* 161 */       request.setParams(new DefaultedHttpParams(request.getParams(), this.params));
/*     */       
/*     */ 
/* 164 */       ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/*     */       
/* 166 */       if (!ver.lessEquals(HttpVersion.HTTP_1_1))
/*     */       {
/* 168 */         ver = HttpVersion.HTTP_1_1;
/*     */       }
/*     */       
/* 171 */       if ((request instanceof HttpEntityEnclosingRequest))
/*     */       {
/* 173 */         if (((HttpEntityEnclosingRequest)request).expectContinue()) {
/* 174 */           response = this.responseFactory.newHttpResponse(ver, 100, context);
/*     */           
/* 176 */           response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/*     */           
/*     */ 
/* 179 */           if (this.expectationVerifier != null) {
/*     */             try {
/* 181 */               this.expectationVerifier.verify(request, response, context);
/*     */             } catch (HttpException ex) {
/* 183 */               response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, context);
/*     */               
/* 185 */               response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/*     */               
/* 187 */               handleException(ex, response);
/*     */             }
/*     */           }
/* 190 */           if (response.getStatusLine().getStatusCode() < 200)
/*     */           {
/*     */ 
/* 193 */             conn.sendResponseHeader(response);
/* 194 */             conn.flush();
/* 195 */             response = null;
/* 196 */             conn.receiveRequestEntity((HttpEntityEnclosingRequest)request);
/*     */           }
/*     */         } else {
/* 199 */           conn.receiveRequestEntity((HttpEntityEnclosingRequest)request);
/*     */         }
/*     */       }
/*     */       
/* 203 */       if (response == null) {
/* 204 */         response = this.responseFactory.newHttpResponse(ver, 200, context);
/* 205 */         response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/*     */         
/*     */ 
/* 208 */         context.setAttribute("http.request", request);
/* 209 */         context.setAttribute("http.response", response);
/*     */         
/* 211 */         this.processor.process(request, context);
/* 212 */         doService(request, response, context);
/*     */       }
/*     */       
/*     */ 
/* 216 */       if ((request instanceof HttpEntityEnclosingRequest)) {
/* 217 */         HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
/* 218 */         if (entity != null) {
/* 219 */           entity.consumeContent();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (HttpException ex) {
/* 224 */       response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, context);
/*     */       
/*     */ 
/* 227 */       response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/*     */       
/* 229 */       handleException(ex, response);
/*     */     }
/*     */     
/* 232 */     this.processor.process(response, context);
/* 233 */     conn.sendResponseHeader(response);
/* 234 */     conn.sendResponseEntity(response);
/* 235 */     conn.flush();
/*     */     
/* 237 */     if (!this.connStrategy.keepAlive(response, context)) {
/* 238 */       conn.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void handleException(HttpException ex, HttpResponse response)
/*     */   {
/* 251 */     if ((ex instanceof MethodNotSupportedException)) {
/* 252 */       response.setStatusCode(501);
/* 253 */     } else if ((ex instanceof UnsupportedHttpVersionException)) {
/* 254 */       response.setStatusCode(505);
/* 255 */     } else if ((ex instanceof ProtocolException)) {
/* 256 */       response.setStatusCode(400);
/*     */     } else {
/* 258 */       response.setStatusCode(500);
/*     */     }
/* 260 */     byte[] msg = EncodingUtils.getAsciiBytes(ex.getMessage());
/* 261 */     ByteArrayEntity entity = new ByteArrayEntity(msg);
/* 262 */     entity.setContentType("text/plain; charset=US-ASCII");
/* 263 */     response.setEntity(entity);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void doService(HttpRequest request, HttpResponse response, HttpContext context)
/*     */     throws HttpException, IOException
/*     */   {
/* 287 */     HttpRequestHandler handler = null;
/* 288 */     if (this.handlerResolver != null) {
/* 289 */       String requestURI = request.getRequestLine().getUri();
/* 290 */       handler = this.handlerResolver.lookup(requestURI);
/*     */     }
/* 292 */     if (handler != null) {
/* 293 */       handler.handle(request, response, context);
/*     */     } else {
/* 295 */       response.setStatusCode(501);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/HttpService.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */