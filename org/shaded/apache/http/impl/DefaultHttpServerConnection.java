/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHttpServerConnection
/*     */   extends SocketHttpServerConnection
/*     */ {
/*     */   public void bind(Socket socket, HttpParams params)
/*     */     throws IOException
/*     */   {
/*  77 */     if (socket == null) {
/*  78 */       throw new IllegalArgumentException("Socket may not be null");
/*     */     }
/*  80 */     if (params == null) {
/*  81 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  83 */     assertNotOpen();
/*  84 */     socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(params));
/*  85 */     socket.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
/*     */     
/*  87 */     int linger = HttpConnectionParams.getLinger(params);
/*  88 */     if (linger >= 0) {
/*  89 */       socket.setSoLinger(linger > 0, linger);
/*     */     }
/*  91 */     super.bind(socket, params);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  95 */     StringBuffer buffer = new StringBuffer();
/*  96 */     buffer.append("[");
/*  97 */     if (isOpen()) {
/*  98 */       buffer.append(getRemotePort());
/*     */     } else {
/* 100 */       buffer.append("closed");
/*     */     }
/* 102 */     buffer.append("]");
/* 103 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/DefaultHttpServerConnection.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */