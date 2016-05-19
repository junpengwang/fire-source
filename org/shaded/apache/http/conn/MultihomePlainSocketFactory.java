/*     */ package org.shaded.apache.http.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.conn.scheme.SocketFactory;
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
/*     */ @Immutable
/*     */ public final class MultihomePlainSocketFactory
/*     */   implements SocketFactory
/*     */ {
/*  62 */   private static final MultihomePlainSocketFactory DEFAULT_FACTORY = new MultihomePlainSocketFactory();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MultihomePlainSocketFactory getSocketFactory()
/*     */   {
/*  69 */     return DEFAULT_FACTORY;
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
/*     */   public Socket createSocket()
/*     */   {
/*  82 */     return new Socket();
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
/*     */   public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 105 */     if (host == null) {
/* 106 */       throw new IllegalArgumentException("Target host may not be null.");
/*     */     }
/* 108 */     if (params == null) {
/* 109 */       throw new IllegalArgumentException("Parameters may not be null.");
/*     */     }
/*     */     
/* 112 */     if (sock == null) {
/* 113 */       sock = createSocket();
/*     */     }
/* 115 */     if ((localAddress != null) || (localPort > 0))
/*     */     {
/*     */ 
/* 118 */       if (localPort < 0) {
/* 119 */         localPort = 0;
/*     */       }
/* 121 */       InetSocketAddress isa = new InetSocketAddress(localAddress, localPort);
/*     */       
/* 123 */       sock.bind(isa);
/*     */     }
/*     */     
/* 126 */     int timeout = HttpConnectionParams.getConnectionTimeout(params);
/*     */     
/* 128 */     InetAddress[] inetadrs = InetAddress.getAllByName(host);
/* 129 */     List<InetAddress> addresses = new ArrayList(inetadrs.length);
/* 130 */     addresses.addAll(Arrays.asList(inetadrs));
/* 131 */     Collections.shuffle(addresses);
/*     */     
/* 133 */     IOException lastEx = null;
/* 134 */     for (InetAddress remoteAddress : addresses) {
/*     */       try {
/* 136 */         sock.connect(new InetSocketAddress(remoteAddress, port), timeout);
/*     */       }
/*     */       catch (SocketTimeoutException ex) {
/* 139 */         throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
/*     */       }
/*     */       catch (IOException ex) {
/* 142 */         sock = new Socket();
/*     */         
/* 144 */         lastEx = ex;
/*     */       }
/*     */     }
/* 147 */     if (lastEx != null) {
/* 148 */       throw lastEx;
/*     */     }
/* 150 */     return sock;
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
/*     */   public final boolean isSecure(Socket sock)
/*     */     throws IllegalArgumentException
/*     */   {
/* 168 */     if (sock == null) {
/* 169 */       throw new IllegalArgumentException("Socket may not be null.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 174 */     if (sock.getClass() != Socket.class) {
/* 175 */       throw new IllegalArgumentException("Socket not created by this factory.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 180 */     if (sock.isClosed()) {
/* 181 */       throw new IllegalArgumentException("Socket is closed.");
/*     */     }
/*     */     
/* 184 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/MultihomePlainSocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */