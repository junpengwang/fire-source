/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import org.shaded.apache.http.HttpConnectionMetrics;
/*     */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.conn.ClientConnectionManager;
/*     */ import org.shaded.apache.http.conn.ManagedClientConnection;
/*     */ import org.shaded.apache.http.conn.OperatedClientConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractClientConnAdapter
/*     */   implements ManagedClientConnection
/*     */ {
/*     */   private volatile ClientConnectionManager connManager;
/*     */   private volatile OperatedClientConnection wrappedConnection;
/*     */   private volatile boolean markedReusable;
/*     */   private volatile boolean released;
/*     */   private volatile long duration;
/*     */   
/*     */   protected AbstractClientConnAdapter(ClientConnectionManager mgr, OperatedClientConnection conn)
/*     */   {
/* 100 */     this.connManager = mgr;
/* 101 */     this.wrappedConnection = conn;
/* 102 */     this.markedReusable = false;
/* 103 */     this.released = false;
/* 104 */     this.duration = Long.MAX_VALUE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected synchronized void detach()
/*     */   {
/* 112 */     this.wrappedConnection = null;
/* 113 */     this.connManager = null;
/* 114 */     this.duration = Long.MAX_VALUE;
/*     */   }
/*     */   
/*     */   protected OperatedClientConnection getWrappedConnection() {
/* 118 */     return this.wrappedConnection;
/*     */   }
/*     */   
/*     */   protected ClientConnectionManager getManager() {
/* 122 */     return this.connManager;
/*     */   }
/*     */   
/*     */   protected final void assertNotAborted() throws InterruptedIOException {
/* 126 */     if (this.released) {
/* 127 */       throw new InterruptedIOException("Connection has been shut down");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void assertValid(OperatedClientConnection wrappedConn)
/*     */     throws IllegalStateException
/*     */   {
/* 139 */     if (wrappedConn == null) {
/* 140 */       throw new IllegalStateException("No wrapped connection");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 145 */     OperatedClientConnection conn = getWrappedConnection();
/* 146 */     if (conn == null) {
/* 147 */       return false;
/*     */     }
/* 149 */     return conn.isOpen();
/*     */   }
/*     */   
/*     */   public boolean isStale() {
/* 153 */     if (this.released)
/* 154 */       return true;
/* 155 */     OperatedClientConnection conn = getWrappedConnection();
/* 156 */     if (conn == null) {
/* 157 */       return true;
/*     */     }
/* 159 */     return conn.isStale();
/*     */   }
/*     */   
/*     */   public void setSocketTimeout(int timeout) {
/* 163 */     OperatedClientConnection conn = getWrappedConnection();
/* 164 */     assertValid(conn);
/* 165 */     conn.setSocketTimeout(timeout);
/*     */   }
/*     */   
/*     */   public int getSocketTimeout() {
/* 169 */     OperatedClientConnection conn = getWrappedConnection();
/* 170 */     assertValid(conn);
/* 171 */     return conn.getSocketTimeout();
/*     */   }
/*     */   
/*     */   public HttpConnectionMetrics getMetrics() {
/* 175 */     OperatedClientConnection conn = getWrappedConnection();
/* 176 */     assertValid(conn);
/* 177 */     return conn.getMetrics();
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 181 */     assertNotAborted();
/* 182 */     OperatedClientConnection conn = getWrappedConnection();
/* 183 */     assertValid(conn);
/* 184 */     conn.flush();
/*     */   }
/*     */   
/*     */   public boolean isResponseAvailable(int timeout) throws IOException {
/* 188 */     assertNotAborted();
/* 189 */     OperatedClientConnection conn = getWrappedConnection();
/* 190 */     assertValid(conn);
/* 191 */     return conn.isResponseAvailable(timeout);
/*     */   }
/*     */   
/*     */   public void receiveResponseEntity(HttpResponse response) throws HttpException, IOException
/*     */   {
/* 196 */     assertNotAborted();
/* 197 */     OperatedClientConnection conn = getWrappedConnection();
/* 198 */     assertValid(conn);
/* 199 */     unmarkReusable();
/* 200 */     conn.receiveResponseEntity(response);
/*     */   }
/*     */   
/*     */   public HttpResponse receiveResponseHeader() throws HttpException, IOException
/*     */   {
/* 205 */     assertNotAborted();
/* 206 */     OperatedClientConnection conn = getWrappedConnection();
/* 207 */     assertValid(conn);
/* 208 */     unmarkReusable();
/* 209 */     return conn.receiveResponseHeader();
/*     */   }
/*     */   
/*     */   public void sendRequestEntity(HttpEntityEnclosingRequest request) throws HttpException, IOException
/*     */   {
/* 214 */     assertNotAborted();
/* 215 */     OperatedClientConnection conn = getWrappedConnection();
/* 216 */     assertValid(conn);
/* 217 */     unmarkReusable();
/* 218 */     conn.sendRequestEntity(request);
/*     */   }
/*     */   
/*     */   public void sendRequestHeader(HttpRequest request) throws HttpException, IOException
/*     */   {
/* 223 */     assertNotAborted();
/* 224 */     OperatedClientConnection conn = getWrappedConnection();
/* 225 */     assertValid(conn);
/* 226 */     unmarkReusable();
/* 227 */     conn.sendRequestHeader(request);
/*     */   }
/*     */   
/*     */   public InetAddress getLocalAddress() {
/* 231 */     OperatedClientConnection conn = getWrappedConnection();
/* 232 */     assertValid(conn);
/* 233 */     return conn.getLocalAddress();
/*     */   }
/*     */   
/*     */   public int getLocalPort() {
/* 237 */     OperatedClientConnection conn = getWrappedConnection();
/* 238 */     assertValid(conn);
/* 239 */     return conn.getLocalPort();
/*     */   }
/*     */   
/*     */   public InetAddress getRemoteAddress() {
/* 243 */     OperatedClientConnection conn = getWrappedConnection();
/* 244 */     assertValid(conn);
/* 245 */     return conn.getRemoteAddress();
/*     */   }
/*     */   
/*     */   public int getRemotePort() {
/* 249 */     OperatedClientConnection conn = getWrappedConnection();
/* 250 */     assertValid(conn);
/* 251 */     return conn.getRemotePort();
/*     */   }
/*     */   
/*     */   public boolean isSecure() {
/* 255 */     OperatedClientConnection conn = getWrappedConnection();
/* 256 */     assertValid(conn);
/* 257 */     return conn.isSecure();
/*     */   }
/*     */   
/*     */   public SSLSession getSSLSession() {
/* 261 */     OperatedClientConnection conn = getWrappedConnection();
/* 262 */     assertValid(conn);
/* 263 */     if (!isOpen()) {
/* 264 */       return null;
/*     */     }
/* 266 */     SSLSession result = null;
/* 267 */     Socket sock = conn.getSocket();
/* 268 */     if ((sock instanceof SSLSocket)) {
/* 269 */       result = ((SSLSocket)sock).getSession();
/*     */     }
/* 271 */     return result;
/*     */   }
/*     */   
/*     */   public void markReusable() {
/* 275 */     this.markedReusable = true;
/*     */   }
/*     */   
/*     */   public void unmarkReusable() {
/* 279 */     this.markedReusable = false;
/*     */   }
/*     */   
/*     */   public boolean isMarkedReusable() {
/* 283 */     return this.markedReusable;
/*     */   }
/*     */   
/*     */   public void setIdleDuration(long duration, TimeUnit unit) {
/* 287 */     if (duration > 0L) {
/* 288 */       this.duration = unit.toMillis(duration);
/*     */     } else {
/* 290 */       this.duration = -1L;
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void releaseConnection() {
/* 295 */     if (this.released) {
/* 296 */       return;
/*     */     }
/* 298 */     this.released = true;
/* 299 */     if (this.connManager != null) {
/* 300 */       this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void abortConnection() {
/* 305 */     if (this.released) {
/* 306 */       return;
/*     */     }
/* 308 */     this.released = true;
/* 309 */     unmarkReusable();
/*     */     try {
/* 311 */       shutdown();
/*     */     }
/*     */     catch (IOException ignore) {}
/* 314 */     if (this.connManager != null) {
/* 315 */       this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/AbstractClientConnAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */