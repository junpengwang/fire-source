/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import org.shaded.apache.http.HttpInetConnection;
/*     */ import org.shaded.apache.http.impl.io.SocketInputBuffer;
/*     */ import org.shaded.apache.http.impl.io.SocketOutputBuffer;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
/*     */ import org.shaded.apache.http.io.SessionOutputBuffer;
/*     */ import org.shaded.apache.http.params.HttpConnectionParams;
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
/*     */ public class SocketHttpServerConnection
/*     */   extends AbstractHttpServerConnection
/*     */   implements HttpInetConnection
/*     */ {
/*     */   private volatile boolean open;
/*  61 */   private volatile Socket socket = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void assertNotOpen()
/*     */   {
/*  68 */     if (this.open) {
/*  69 */       throw new IllegalStateException("Connection is already open");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void assertOpen() {
/*  74 */     if (!this.open) {
/*  75 */       throw new IllegalStateException("Connection is not open");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   protected SessionInputBuffer createHttpDataReceiver(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/*  86 */     return createSessionInputBuffer(socket, buffersize, params);
/*     */   }
/*     */   
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   protected SessionOutputBuffer createHttpDataTransmitter(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/*  96 */     return createSessionOutputBuffer(socket, buffersize, params);
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
/*     */   protected SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 118 */     return new SocketInputBuffer(socket, buffersize, params);
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
/*     */   protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 140 */     return new SocketOutputBuffer(socket, buffersize, params);
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
/*     */   protected void bind(Socket socket, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 169 */     if (socket == null) {
/* 170 */       throw new IllegalArgumentException("Socket may not be null");
/*     */     }
/* 172 */     if (params == null) {
/* 173 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 175 */     this.socket = socket;
/*     */     
/* 177 */     int buffersize = HttpConnectionParams.getSocketBufferSize(params);
/*     */     
/* 179 */     init(createHttpDataReceiver(socket, buffersize, params), createHttpDataTransmitter(socket, buffersize, params), params);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 184 */     this.open = true;
/*     */   }
/*     */   
/*     */   protected Socket getSocket() {
/* 188 */     return this.socket;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 192 */     return this.open;
/*     */   }
/*     */   
/*     */   public InetAddress getLocalAddress() {
/* 196 */     if (this.socket != null) {
/* 197 */       return this.socket.getLocalAddress();
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */   
/*     */   public int getLocalPort()
/*     */   {
/* 204 */     if (this.socket != null) {
/* 205 */       return this.socket.getLocalPort();
/*     */     }
/* 207 */     return -1;
/*     */   }
/*     */   
/*     */   public InetAddress getRemoteAddress()
/*     */   {
/* 212 */     if (this.socket != null) {
/* 213 */       return this.socket.getInetAddress();
/*     */     }
/* 215 */     return null;
/*     */   }
/*     */   
/*     */   public int getRemotePort()
/*     */   {
/* 220 */     if (this.socket != null) {
/* 221 */       return this.socket.getPort();
/*     */     }
/* 223 */     return -1;
/*     */   }
/*     */   
/*     */   public void setSocketTimeout(int timeout)
/*     */   {
/* 228 */     assertOpen();
/* 229 */     if (this.socket != null) {
/*     */       try {
/* 231 */         this.socket.setSoTimeout(timeout);
/*     */       }
/*     */       catch (SocketException ignore) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSocketTimeout()
/*     */   {
/* 241 */     if (this.socket != null) {
/*     */       try {
/* 243 */         return this.socket.getSoTimeout();
/*     */       } catch (SocketException ignore) {
/* 245 */         return -1;
/*     */       }
/*     */     }
/* 248 */     return -1;
/*     */   }
/*     */   
/*     */   public void shutdown() throws IOException
/*     */   {
/* 253 */     this.open = false;
/* 254 */     Socket tmpsocket = this.socket;
/* 255 */     if (tmpsocket != null) {
/* 256 */       tmpsocket.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 261 */     if (!this.open) {
/* 262 */       return;
/*     */     }
/* 264 */     this.open = false;
/* 265 */     doFlush();
/*     */     try {
/*     */       try {
/* 268 */         this.socket.shutdownOutput();
/*     */       }
/*     */       catch (IOException ignore) {}
/*     */       try {
/* 272 */         this.socket.shutdownInput();
/*     */       }
/*     */       catch (IOException ignore) {}
/*     */     }
/*     */     catch (UnsupportedOperationException ignore) {}
/*     */     
/* 278 */     this.socket.close();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/SocketHttpServerConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */