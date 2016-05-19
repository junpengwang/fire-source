/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.HttpConnectionMetrics;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpRequestFactory;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpServerConnection;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.impl.entity.EntityDeserializer;
/*     */ import org.shaded.apache.http.impl.entity.EntitySerializer;
/*     */ import org.shaded.apache.http.impl.entity.LaxContentLengthStrategy;
/*     */ import org.shaded.apache.http.impl.entity.StrictContentLengthStrategy;
/*     */ import org.shaded.apache.http.impl.io.HttpRequestParser;
/*     */ import org.shaded.apache.http.impl.io.HttpResponseWriter;
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
/*     */ public abstract class AbstractHttpServerConnection
/*     */   implements HttpServerConnection
/*     */ {
/*     */   private final EntitySerializer entityserializer;
/*     */   private final EntityDeserializer entitydeserializer;
/*  75 */   private SessionInputBuffer inbuffer = null;
/*  76 */   private SessionOutputBuffer outbuffer = null;
/*  77 */   private EofSensor eofSensor = null;
/*  78 */   private HttpMessageParser requestParser = null;
/*  79 */   private HttpMessageWriter responseWriter = null;
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
/*     */   public AbstractHttpServerConnection()
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
/*     */   protected HttpRequestFactory createHttpRequestFactory()
/*     */   {
/* 144 */     return new DefaultHttpRequestFactory();
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
/*     */   protected HttpMessageParser createRequestParser(SessionInputBuffer buffer, HttpRequestFactory requestFactory, HttpParams params)
/*     */   {
/* 165 */     return new HttpRequestParser(buffer, null, requestFactory, params);
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
/*     */   protected HttpMessageWriter createResponseWriter(SessionOutputBuffer buffer, HttpParams params)
/*     */   {
/* 184 */     return new HttpResponseWriter(buffer, null, params);
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
/* 218 */     this.requestParser = createRequestParser(inbuffer, createHttpRequestFactory(), params);
/*     */     
/*     */ 
/*     */ 
/* 222 */     this.responseWriter = createResponseWriter(outbuffer, params);
/*     */     
/* 224 */     this.metrics = new HttpConnectionMetricsImpl(inbuffer.getMetrics(), outbuffer.getMetrics());
/*     */   }
/*     */   
/*     */ 
/*     */   public HttpRequest receiveRequestHeader()
/*     */     throws HttpException, IOException
/*     */   {
/* 231 */     assertOpen();
/* 232 */     HttpRequest request = (HttpRequest)this.requestParser.parse();
/* 233 */     this.metrics.incrementRequestCount();
/* 234 */     return request;
/*     */   }
/*     */   
/*     */   public void receiveRequestEntity(HttpEntityEnclosingRequest request) throws HttpException, IOException
/*     */   {
/* 239 */     if (request == null) {
/* 240 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/* 242 */     assertOpen();
/* 243 */     HttpEntity entity = this.entitydeserializer.deserialize(this.inbuffer, request);
/* 244 */     request.setEntity(entity);
/*     */   }
/*     */   
/*     */   protected void doFlush() throws IOException {
/* 248 */     this.outbuffer.flush();
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 252 */     assertOpen();
/* 253 */     doFlush();
/*     */   }
/*     */   
/*     */   public void sendResponseHeader(HttpResponse response) throws HttpException, IOException
/*     */   {
/* 258 */     if (response == null) {
/* 259 */       throw new IllegalArgumentException("HTTP response may not be null");
/*     */     }
/* 261 */     assertOpen();
/* 262 */     this.responseWriter.write(response);
/* 263 */     if (response.getStatusLine().getStatusCode() >= 200) {
/* 264 */       this.metrics.incrementResponseCount();
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendResponseEntity(HttpResponse response) throws HttpException, IOException
/*     */   {
/* 270 */     if (response.getEntity() == null) {
/* 271 */       return;
/*     */     }
/* 273 */     this.entityserializer.serialize(this.outbuffer, response, response.getEntity());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean isEof()
/*     */   {
/* 280 */     return (this.eofSensor != null) && (this.eofSensor.isEof());
/*     */   }
/*     */   
/*     */   public boolean isStale() {
/* 284 */     if (!isOpen()) {
/* 285 */       return true;
/*     */     }
/* 287 */     if (isEof()) {
/* 288 */       return true;
/*     */     }
/*     */     try {
/* 291 */       this.inbuffer.isDataAvailable(1);
/* 292 */       return isEof();
/*     */     } catch (IOException ex) {}
/* 294 */     return true;
/*     */   }
/*     */   
/*     */   public HttpConnectionMetrics getMetrics()
/*     */   {
/* 299 */     return this.metrics;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/AbstractHttpServerConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */