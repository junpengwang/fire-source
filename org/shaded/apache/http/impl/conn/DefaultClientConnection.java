/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpResponseFactory;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.conn.OperatedClientConnection;
/*     */ import org.shaded.apache.http.impl.SocketHttpClientConnection;
/*     */ import org.shaded.apache.http.io.HttpMessageParser;
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
/*     */ @NotThreadSafe
/*     */ public class DefaultClientConnection
/*     */   extends SocketHttpClientConnection
/*     */   implements OperatedClientConnection
/*     */ {
/*  70 */   private final Log log = LogFactory.getLog(getClass());
/*  71 */   private final Log headerLog = LogFactory.getLog("org.shaded.apache.http.headers");
/*  72 */   private final Log wireLog = LogFactory.getLog("org.shaded.apache.http.wire");
/*     */   
/*     */ 
/*     */ 
/*     */   private volatile Socket socket;
/*     */   
/*     */ 
/*     */   private HttpHost targetHost;
/*     */   
/*     */ 
/*     */   private boolean connSecure;
/*     */   
/*     */ 
/*     */   private volatile boolean shutdown;
/*     */   
/*     */ 
/*     */ 
/*     */   public final HttpHost getTargetHost()
/*     */   {
/*  91 */     return this.targetHost;
/*     */   }
/*     */   
/*     */   public final boolean isSecure() {
/*  95 */     return this.connSecure;
/*     */   }
/*     */   
/*     */   public final Socket getSocket()
/*     */   {
/* 100 */     return this.socket;
/*     */   }
/*     */   
/*     */   public void opening(Socket sock, HttpHost target) throws IOException {
/* 104 */     assertNotOpen();
/* 105 */     this.socket = sock;
/* 106 */     this.targetHost = target;
/*     */     
/*     */ 
/* 109 */     if (this.shutdown) {
/* 110 */       sock.close();
/*     */       
/* 112 */       throw new IOException("Connection already shutdown");
/*     */     }
/*     */   }
/*     */   
/*     */   public void openCompleted(boolean secure, HttpParams params) throws IOException {
/* 117 */     assertNotOpen();
/* 118 */     if (params == null) {
/* 119 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*     */     
/* 122 */     this.connSecure = secure;
/* 123 */     bind(this.socket, params);
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
/*     */   public void shutdown()
/*     */     throws IOException
/*     */   {
/* 141 */     this.log.debug("Connection shut down");
/* 142 */     this.shutdown = true;
/*     */     
/* 144 */     super.shutdown();
/* 145 */     Socket sock = this.socket;
/* 146 */     if (sock != null) {
/* 147 */       sock.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() throws IOException
/*     */   {
/* 153 */     this.log.debug("Connection closed");
/* 154 */     super.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 162 */     if (buffersize == -1) {
/* 163 */       buffersize = 8192;
/*     */     }
/* 165 */     SessionInputBuffer inbuffer = super.createSessionInputBuffer(socket, buffersize, params);
/*     */     
/*     */ 
/*     */ 
/* 169 */     if (this.wireLog.isDebugEnabled()) {
/* 170 */       inbuffer = new LoggingSessionInputBuffer(inbuffer, new Wire(this.wireLog));
/*     */     }
/* 172 */     return inbuffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 180 */     if (buffersize == -1) {
/* 181 */       buffersize = 8192;
/*     */     }
/* 183 */     SessionOutputBuffer outbuffer = super.createSessionOutputBuffer(socket, buffersize, params);
/*     */     
/*     */ 
/*     */ 
/* 187 */     if (this.wireLog.isDebugEnabled()) {
/* 188 */       outbuffer = new LoggingSessionOutputBuffer(outbuffer, new Wire(this.wireLog));
/*     */     }
/* 190 */     return outbuffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HttpMessageParser createResponseParser(SessionInputBuffer buffer, HttpResponseFactory responseFactory, HttpParams params)
/*     */   {
/* 199 */     return new DefaultResponseParser(buffer, null, responseFactory, params);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void update(Socket sock, HttpHost target, boolean secure, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 207 */     assertOpen();
/* 208 */     if (target == null) {
/* 209 */       throw new IllegalArgumentException("Target host must not be null.");
/*     */     }
/*     */     
/* 212 */     if (params == null) {
/* 213 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 217 */     if (sock != null) {
/* 218 */       this.socket = sock;
/* 219 */       bind(sock, params);
/*     */     }
/* 221 */     this.targetHost = target;
/* 222 */     this.connSecure = secure;
/*     */   }
/*     */   
/*     */   public HttpResponse receiveResponseHeader() throws HttpException, IOException
/*     */   {
/* 227 */     HttpResponse response = super.receiveResponseHeader();
/* 228 */     if (this.log.isDebugEnabled()) {
/* 229 */       this.log.debug("Receiving response: " + response.getStatusLine());
/*     */     }
/* 231 */     if (this.headerLog.isDebugEnabled()) {
/* 232 */       this.headerLog.debug("<< " + response.getStatusLine().toString());
/* 233 */       Header[] headers = response.getAllHeaders();
/* 234 */       for (Header header : headers) {
/* 235 */         this.headerLog.debug("<< " + header.toString());
/*     */       }
/*     */     }
/* 238 */     return response;
/*     */   }
/*     */   
/*     */   public void sendRequestHeader(HttpRequest request) throws HttpException, IOException
/*     */   {
/* 243 */     if (this.log.isDebugEnabled()) {
/* 244 */       this.log.debug("Sending request: " + request.getRequestLine());
/*     */     }
/* 246 */     super.sendRequestHeader(request);
/* 247 */     if (this.headerLog.isDebugEnabled()) {
/* 248 */       this.headerLog.debug(">> " + request.getRequestLine().toString());
/* 249 */       Header[] headers = request.getAllHeaders();
/* 250 */       for (Header header : headers) {
/* 251 */         this.headerLog.debug(">> " + header.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/DefaultClientConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */