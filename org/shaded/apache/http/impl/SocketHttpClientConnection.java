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
/*     */ 
/*     */ public class SocketHttpClientConnection
/*     */   extends AbstractHttpClientConnection
/*     */   implements HttpInetConnection
/*     */ {
/*     */   private volatile boolean open;
/*  62 */   private volatile Socket socket = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void assertNotOpen()
/*     */   {
/*  69 */     if (this.open) {
/*  70 */       throw new IllegalStateException("Connection is already open");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void assertOpen() {
/*  75 */     if (!this.open) {
/*  76 */       throw new IllegalStateException("Connection is not open");
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
/*     */ 
/*     */ 
/*     */   protected SessionInputBuffer createSessionInputBuffer(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/*  99 */     return new SocketInputBuffer(socket, buffersize, params);
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
/* 121 */     return new SocketOutputBuffer(socket, buffersize, params);
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
/*     */ 
/*     */   protected void bind(Socket socket, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 152 */     if (socket == null) {
/* 153 */       throw new IllegalArgumentException("Socket may not be null");
/*     */     }
/* 155 */     if (params == null) {
/* 156 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 158 */     this.socket = socket;
/*     */     
/* 160 */     int buffersize = HttpConnectionParams.getSocketBufferSize(params);
/*     */     
/* 162 */     init(createSessionInputBuffer(socket, buffersize, params), createSessionOutputBuffer(socket, buffersize, params), params);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 167 */     this.open = true;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 171 */     return this.open;
/*     */   }
/*     */   
/*     */   protected Socket getSocket() {
/* 175 */     return this.socket;
/*     */   }
/*     */   
/*     */   public InetAddress getLocalAddress() {
/* 179 */     if (this.socket != null) {
/* 180 */       return this.socket.getLocalAddress();
/*     */     }
/* 182 */     return null;
/*     */   }
/*     */   
/*     */   public int getLocalPort()
/*     */   {
/* 187 */     if (this.socket != null) {
/* 188 */       return this.socket.getLocalPort();
/*     */     }
/* 190 */     return -1;
/*     */   }
/*     */   
/*     */   public InetAddress getRemoteAddress()
/*     */   {
/* 195 */     if (this.socket != null) {
/* 196 */       return this.socket.getInetAddress();
/*     */     }
/* 198 */     return null;
/*     */   }
/*     */   
/*     */   public int getRemotePort()
/*     */   {
/* 203 */     if (this.socket != null) {
/* 204 */       return this.socket.getPort();
/*     */     }
/* 206 */     return -1;
/*     */   }
/*     */   
/*     */   public void setSocketTimeout(int timeout)
/*     */   {
/* 211 */     assertOpen();
/* 212 */     if (this.socket != null) {
/*     */       try {
/* 214 */         this.socket.setSoTimeout(timeout);
/*     */       }
/*     */       catch (SocketException ignore) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSocketTimeout()
/*     */   {
/* 224 */     if (this.socket != null) {
/*     */       try {
/* 226 */         return this.socket.getSoTimeout();
/*     */       } catch (SocketException ignore) {
/* 228 */         return -1;
/*     */       }
/*     */     }
/* 231 */     return -1;
/*     */   }
/*     */   
/*     */   public void shutdown() throws IOException
/*     */   {
/* 236 */     this.open = false;
/* 237 */     Socket tmpsocket = this.socket;
/* 238 */     if (tmpsocket != null) {
/* 239 */       tmpsocket.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 244 */     if (!this.open) {
/* 245 */       return;
/*     */     }
/* 247 */     this.open = false;
/* 248 */     doFlush();
/*     */     try {
/*     */       try {
/* 251 */         this.socket.shutdownOutput();
/*     */       }
/*     */       catch (IOException ignore) {}
/*     */       try {
/* 255 */         this.socket.shutdownInput();
/*     */       }
/*     */       catch (IOException ignore) {}
/*     */     }
/*     */     catch (UnsupportedOperationException ignore) {}
/*     */     
/* 261 */     this.socket.close();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/SocketHttpClientConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */