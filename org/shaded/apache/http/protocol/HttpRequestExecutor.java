/*     */ package org.shaded.apache.http.protocol;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ProtocolException;
/*     */ import org.shaded.apache.http.HttpClientConnection;
/*     */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpVersion;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.StatusLine;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRequestExecutor
/*     */ {
/*     */   protected boolean canResponseHaveBody(HttpRequest request, HttpResponse response)
/*     */   {
/*  86 */     if ("HEAD".equalsIgnoreCase(request.getRequestLine().getMethod())) {
/*  87 */       return false;
/*     */     }
/*  89 */     int status = response.getStatusLine().getStatusCode();
/*  90 */     return (status >= 200) && (status != 204) && (status != 304) && (status != 205);
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
/*     */   public HttpResponse execute(HttpRequest request, HttpClientConnection conn, HttpContext context)
/*     */     throws IOException, HttpException
/*     */   {
/* 113 */     if (request == null) {
/* 114 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/* 116 */     if (conn == null) {
/* 117 */       throw new IllegalArgumentException("Client connection may not be null");
/*     */     }
/* 119 */     if (context == null) {
/* 120 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/*     */     try
/*     */     {
/* 124 */       HttpResponse response = doSendRequest(request, conn, context);
/* 125 */       if (response == null) {}
/* 126 */       return doReceiveResponse(request, conn, context);
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/* 130 */       conn.close();
/* 131 */       throw ex;
/*     */     } catch (HttpException ex) {
/* 133 */       conn.close();
/* 134 */       throw ex;
/*     */     } catch (RuntimeException ex) {
/* 136 */       conn.close();
/* 137 */       throw ex;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void preProcess(HttpRequest request, HttpProcessor processor, HttpContext context)
/*     */     throws HttpException, IOException
/*     */   {
/* 158 */     if (request == null) {
/* 159 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/* 161 */     if (processor == null) {
/* 162 */       throw new IllegalArgumentException("HTTP processor may not be null");
/*     */     }
/* 164 */     if (context == null) {
/* 165 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/* 167 */     context.setAttribute("http.request", request);
/* 168 */     processor.process(request, context);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HttpResponse doSendRequest(HttpRequest request, HttpClientConnection conn, HttpContext context)
/*     */     throws IOException, HttpException
/*     */   {
/* 198 */     if (request == null) {
/* 199 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/* 201 */     if (conn == null) {
/* 202 */       throw new IllegalArgumentException("HTTP connection may not be null");
/*     */     }
/* 204 */     if (context == null) {
/* 205 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/*     */     
/* 208 */     HttpResponse response = null;
/*     */     
/* 210 */     context.setAttribute("http.connection", conn);
/* 211 */     context.setAttribute("http.request_sent", Boolean.FALSE);
/*     */     
/* 213 */     conn.sendRequestHeader(request);
/* 214 */     if ((request instanceof HttpEntityEnclosingRequest))
/*     */     {
/*     */ 
/*     */ 
/* 218 */       boolean sendentity = true;
/* 219 */       ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/*     */       
/* 221 */       if ((((HttpEntityEnclosingRequest)request).expectContinue()) && (!ver.lessEquals(HttpVersion.HTTP_1_0)))
/*     */       {
/*     */ 
/* 224 */         conn.flush();
/*     */         
/*     */ 
/* 227 */         int tms = request.getParams().getIntParameter("http.protocol.wait-for-continue", 2000);
/*     */         
/*     */ 
/* 230 */         if (conn.isResponseAvailable(tms)) {
/* 231 */           response = conn.receiveResponseHeader();
/* 232 */           if (canResponseHaveBody(request, response)) {
/* 233 */             conn.receiveResponseEntity(response);
/*     */           }
/* 235 */           int status = response.getStatusLine().getStatusCode();
/* 236 */           if (status < 200) {
/* 237 */             if (status != 100) {
/* 238 */               throw new ProtocolException("Unexpected response: " + response.getStatusLine());
/*     */             }
/*     */             
/*     */ 
/* 242 */             response = null;
/*     */           } else {
/* 244 */             sendentity = false;
/*     */           }
/*     */         }
/*     */       }
/* 248 */       if (sendentity) {
/* 249 */         conn.sendRequestEntity((HttpEntityEnclosingRequest)request);
/*     */       }
/*     */     }
/* 252 */     conn.flush();
/* 253 */     context.setAttribute("http.request_sent", Boolean.TRUE);
/* 254 */     return response;
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
/*     */   protected HttpResponse doReceiveResponse(HttpRequest request, HttpClientConnection conn, HttpContext context)
/*     */     throws HttpException, IOException
/*     */   {
/* 277 */     if (request == null) {
/* 278 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/* 280 */     if (conn == null) {
/* 281 */       throw new IllegalArgumentException("HTTP connection may not be null");
/*     */     }
/* 283 */     if (context == null) {
/* 284 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/*     */     
/* 287 */     HttpResponse response = null;
/* 288 */     int statuscode = 0;
/*     */     
/* 290 */     while ((response == null) || (statuscode < 200))
/*     */     {
/* 292 */       response = conn.receiveResponseHeader();
/* 293 */       if (canResponseHaveBody(request, response)) {
/* 294 */         conn.receiveResponseEntity(response);
/*     */       }
/* 296 */       statuscode = response.getStatusLine().getStatusCode();
/*     */     }
/*     */     
/*     */ 
/* 300 */     return response;
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
/*     */ 
/*     */ 
/*     */   public void postProcess(HttpResponse response, HttpProcessor processor, HttpContext context)
/*     */     throws HttpException, IOException
/*     */   {
/* 326 */     if (response == null) {
/* 327 */       throw new IllegalArgumentException("HTTP response may not be null");
/*     */     }
/* 329 */     if (processor == null) {
/* 330 */       throw new IllegalArgumentException("HTTP processor may not be null");
/*     */     }
/* 332 */     if (context == null) {
/* 333 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/* 335 */     context.setAttribute("http.response", response);
/* 336 */     processor.process(response, context);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/HttpRequestExecutor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */