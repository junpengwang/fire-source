/*     */ package org.shaded.apache.http.conn.scheme;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.conn.ConnectTimeoutException;
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
/*     */ @Immutable
/*     */ public final class PlainSocketFactory
/*     */   implements SocketFactory
/*     */ {
/*  60 */   private static final PlainSocketFactory DEFAULT_FACTORY = new PlainSocketFactory();
/*     */   
/*     */ 
/*     */ 
/*     */   private final HostNameResolver nameResolver;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PlainSocketFactory getSocketFactory()
/*     */   {
/*  71 */     return DEFAULT_FACTORY;
/*     */   }
/*     */   
/*     */   public PlainSocketFactory(HostNameResolver nameResolver)
/*     */   {
/*  76 */     this.nameResolver = nameResolver;
/*     */   }
/*     */   
/*     */   public PlainSocketFactory()
/*     */   {
/*  81 */     this(null);
/*     */   }
/*     */   
/*     */   public Socket createSocket() {
/*  85 */     return new Socket();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
/*     */     throws IOException
/*     */   {
/*  93 */     if (host == null) {
/*  94 */       throw new IllegalArgumentException("Target host may not be null.");
/*     */     }
/*  96 */     if (params == null) {
/*  97 */       throw new IllegalArgumentException("Parameters may not be null.");
/*     */     }
/*     */     
/* 100 */     if (sock == null) {
/* 101 */       sock = createSocket();
/*     */     }
/* 103 */     if ((localAddress != null) || (localPort > 0))
/*     */     {
/*     */ 
/* 106 */       if (localPort < 0) {
/* 107 */         localPort = 0;
/*     */       }
/* 109 */       InetSocketAddress isa = new InetSocketAddress(localAddress, localPort);
/*     */       
/* 111 */       sock.bind(isa);
/*     */     }
/*     */     
/* 114 */     int timeout = HttpConnectionParams.getConnectionTimeout(params);
/*     */     InetSocketAddress remoteAddress;
/*     */     InetSocketAddress remoteAddress;
/* 117 */     if (this.nameResolver != null) {
/* 118 */       remoteAddress = new InetSocketAddress(this.nameResolver.resolve(host), port);
/*     */     } else {
/* 120 */       remoteAddress = new InetSocketAddress(host, port);
/*     */     }
/*     */     try {
/* 123 */       sock.connect(remoteAddress, timeout);
/*     */     } catch (SocketTimeoutException ex) {
/* 125 */       throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
/*     */     }
/* 127 */     return sock;
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
/*     */   public final boolean isSecure(Socket sock)
/*     */     throws IllegalArgumentException
/*     */   {
/* 144 */     if (sock == null) {
/* 145 */       throw new IllegalArgumentException("Socket may not be null.");
/*     */     }
/*     */     
/*     */ 
/* 149 */     if (sock.isClosed()) {
/* 150 */       throw new IllegalArgumentException("Socket is closed.");
/*     */     }
/* 152 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/scheme/PlainSocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */