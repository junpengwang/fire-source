/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.HttpClientConnection;
/*     */ import org.shaded.apache.http.HttpConnectionMetrics;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpResponseFactory;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.impl.entity.EntityDeserializer;
/*     */ import org.shaded.apache.http.impl.entity.EntitySerializer;
/*     */ import org.shaded.apache.http.impl.entity.LaxContentLengthStrategy;
/*     */ import org.shaded.apache.http.impl.entity.StrictContentLengthStrategy;
/*     */ import org.shaded.apache.http.impl.io.HttpRequestWriter;
/*     */ import org.shaded.apache.http.impl.io.HttpResponseParser;
/*     */ import org.shaded.apache.http.io.EofSensor;
/*     */ import org.shaded.apache.http.io.HttpMessageParser;
/*     */ import org.shaded.apache.http.io.HttpMessageWriter;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
/*     */ import org.shaded.apache.http.io.SessionOutputBuffer;
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
/*     */ public abstract class AbstractHttpClientConnection
/*     */   implements HttpClientConnection
/*     */ {
/*     */   private final EntitySerializer entityserializer;
/*     */   private final EntityDeserializer entitydeserializer;
/*  75 */   private SessionInputBuffer inbuffer = null;
/*  76 */   private SessionOutputBuffer outbuffer = null;
/*  77 */   private EofSensor eofSensor = null;
/*  78 */   private HttpMessageParser responseParser = null;
/*  79 */   private HttpMessageWriter requestWriter = null;
/*  80 */   private HttpConnectionMetricsImpl metrics = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AbstractHttpClientConnection()
/*     */   {
/*  92 */     this.entityserializer = createEntitySerializer();
/*  93 */     this.entitydeserializer = createEntityDeserializer();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void assertOpen()
/*     */     throws IllegalStateException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected EntityDeserializer createEntityDeserializer()
/*     */   {
/* 115 */     return new EntityDeserializer(new LaxContentLengthStrategy());
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
/*     */   protected EntitySerializer createEntitySerializer()
/*     */   {
/* 130 */     return new EntitySerializer(new StrictContentLengthStrategy());
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
/*     */   protected HttpResponseFactory createHttpResponseFactory()
/*     */   {
/* 144 */     return new DefaultHttpResponseFactory();
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
/*     */   protected HttpMessageParser createResponseParser(SessionInputBuffer buffer, HttpResponseFactory responseFactory, HttpParams params)
/*     */   {
/* 165 */     return new HttpResponseParser(buffer, null, responseFactory, params);
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
/*     */   protected HttpMessageWriter createRequestWriter(SessionOutputBuffer buffer, HttpParams params)
/*     */   {
/* 184 */     return new HttpRequestWriter(buffer, null, params);
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
/*     */   protected void init(SessionInputBuffer inbuffer, SessionOutputBuffer outbuffer, HttpParams params)
/*     */   {
/* 207 */     if (inbuffer == null) {
/* 208 */       throw new IllegalArgumentException("Input session buffer may not be null");
/*     */     }
/* 210 */     if (outbuffer == null) {
/* 211 */       throw new IllegalArgumentException("Output session buffer may not be null");
/*     */     }
/* 213 */     this.inbuffer = inbuffer;
/* 214 */     this.outbuffer = outbuffer;
/* 215 */     if ((inbuffer instanceof EofSensor)) {
/* 216 */       this.eofSensor = ((EofSensor)inbuffer);
/*     */     }
/* 218 */     this.responseParser = createResponseParser(inbuffer, createHttpResponseFactory(), params);
/*     */     
/*     */ 
/*     */ 
/* 222 */     this.requestWriter = createRequestWriter(outbuffer, params);
/*     */     
/* 224 */     this.metrics = new HttpConnectionMetricsImpl(inbuffer.getMetrics(), outbuffer.getMetrics());
/*     */   }
/*     */   
/*     */   public boolean isResponseAvailable(int timeout)
/*     */     throws IOException
/*     */   {
/* 230 */     assertOpen();
/* 231 */     return this.inbuffer.isDataAvailable(timeout);
/*     */   }
/*     */   
/*     */   public void sendRequestHeader(HttpRequest request) throws HttpException, IOException
/*     */   {
/* 236 */     if (request == null) {
/* 237 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/* 239 */     assertOpen();
/* 240 */     this.requestWriter.write(request);
/* 241 */     this.metrics.incrementRequestCount();
/*     */   }
/*     */   
/*     */   public void sendRequestEntity(HttpEntityEnclosingRequest request) throws HttpException, IOException
/*     */   {
/* 246 */     if (request == null) {
/* 247 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/* 249 */     assertOpen();
/* 250 */     if (request.getEntity() == null) {
/* 251 */       return;
/*     */     }
/* 253 */     this.entityserializer.serialize(this.outbuffer, request, request.getEntity());
/*     */   }
/*     */   
/*     */ 
/*     */   protected void doFlush()
/*     */     throws IOException
/*     */   {
/* 260 */     this.outbuffer.flush();
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 264 */     assertOpen();
/* 265 */     doFlush();
/*     */   }
/*     */   
/*     */   public HttpResponse receiveResponseHeader() throws HttpException, IOException
/*     */   {
/* 270 */     assertOpen();
/* 271 */     HttpResponse response = (HttpResponse)this.responseParser.parse();
/* 272 */     if (response.getStatusLine().getStatusCode() >= 200) {
/* 273 */       this.metrics.incrementResponseCount();
/*     */     }
/* 275 */     return response;
/*     */   }
/*     */   
/*     */   public void receiveResponseEntity(HttpResponse response) throws HttpException, IOException
/*     */   {
/* 280 */     if (response == null) {
/* 281 */       throw new IllegalArgumentException("HTTP response may not be null");
/*     */     }
/* 283 */     assertOpen();
/* 284 */     HttpEntity entity = this.entitydeserializer.deserialize(this.inbuffer, response);
/* 285 */     response.setEntity(entity);
/*     */   }
/*     */   
/*     */   protected boolean isEof() {
/* 289 */     return (this.eofSensor != null) && (this.eofSensor.isEof());
/*     */   }
/*     */   
/*     */   public boolean isStale() {
/* 293 */     if (!isOpen()) {
/* 294 */       return true;
/*     */     }
/* 296 */     if (isEof()) {
/* 297 */       return true;
/*     */     }
/*     */     try {
/* 300 */       this.inbuffer.isDataAvailable(1);
/* 301 */       return isEof();
/*     */     } catch (IOException ex) {}
/* 303 */     return true;
/*     */   }
/*     */   
/*     */   public HttpConnectionMetrics getMetrics()
/*     */   {
/* 308 */     return this.metrics;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/AbstractHttpClientConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */