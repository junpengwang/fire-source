/*     */ package org.shaded.apache.http.conn.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.conn.ConnectTimeoutException;
/*     */ import org.shaded.apache.http.conn.scheme.HostNameResolver;
/*     */ import org.shaded.apache.http.conn.scheme.LayeredSocketFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SSLSocketFactory
/*     */   implements LayeredSocketFactory
/*     */ {
/*     */   public static final String TLS = "TLS";
/*     */   public static final String SSL = "SSL";
/*     */   public static final String SSLV2 = "SSLv2";
/* 154 */   public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
/*     */   
/*     */ 
/* 157 */   public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
/*     */   
/*     */ 
/* 160 */   public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 166 */   private static final SSLSocketFactory DEFAULT_FACTORY = new SSLSocketFactory();
/*     */   
/*     */   private final SSLContext sslcontext;
/*     */   
/*     */   private final javax.net.ssl.SSLSocketFactory socketfactory;
/*     */   private final HostNameResolver nameResolver;
/*     */   
/*     */   public static SSLSocketFactory getSocketFactory()
/*     */   {
/* 175 */     return DEFAULT_FACTORY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 183 */   private volatile X509HostnameVerifier hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SSLSocketFactory(String algorithm, KeyStore keystore, String keystorePassword, KeyStore truststore, SecureRandom random, HostNameResolver nameResolver)
/*     */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/*     */   {
/* 195 */     if (algorithm == null) {
/* 196 */       algorithm = "TLS";
/*     */     }
/* 198 */     KeyManager[] keymanagers = null;
/* 199 */     if (keystore != null) {
/* 200 */       keymanagers = createKeyManagers(keystore, keystorePassword);
/*     */     }
/* 202 */     TrustManager[] trustmanagers = null;
/* 203 */     if (truststore != null) {
/* 204 */       trustmanagers = createTrustManagers(truststore);
/*     */     }
/* 206 */     this.sslcontext = SSLContext.getInstance(algorithm);
/* 207 */     this.sslcontext.init(keymanagers, trustmanagers, random);
/* 208 */     this.socketfactory = this.sslcontext.getSocketFactory();
/* 209 */     this.nameResolver = nameResolver;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SSLSocketFactory(KeyStore keystore, String keystorePassword, KeyStore truststore)
/*     */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/*     */   {
/* 218 */     this("TLS", keystore, keystorePassword, truststore, null, null);
/*     */   }
/*     */   
/*     */   public SSLSocketFactory(KeyStore keystore, String keystorePassword)
/*     */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/*     */   {
/* 224 */     this("TLS", keystore, keystorePassword, null, null, null);
/*     */   }
/*     */   
/*     */   public SSLSocketFactory(KeyStore truststore)
/*     */     throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
/*     */   {
/* 230 */     this("TLS", null, null, truststore, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SSLSocketFactory(SSLContext sslContext, HostNameResolver nameResolver)
/*     */   {
/* 237 */     this.sslcontext = sslContext;
/* 238 */     this.socketfactory = this.sslcontext.getSocketFactory();
/* 239 */     this.nameResolver = nameResolver;
/*     */   }
/*     */   
/*     */   public SSLSocketFactory(SSLContext sslContext)
/*     */   {
/* 244 */     this(sslContext, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private SSLSocketFactory()
/*     */   {
/* 254 */     this.sslcontext = null;
/* 255 */     this.socketfactory = HttpsURLConnection.getDefaultSSLSocketFactory();
/* 256 */     this.nameResolver = null;
/*     */   }
/*     */   
/*     */   private static KeyManager[] createKeyManagers(KeyStore keystore, String password) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException
/*     */   {
/* 261 */     if (keystore == null) {
/* 262 */       throw new IllegalArgumentException("Keystore may not be null");
/*     */     }
/* 264 */     KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/*     */     
/* 266 */     kmfactory.init(keystore, password != null ? password.toCharArray() : null);
/* 267 */     return kmfactory.getKeyManagers();
/*     */   }
/*     */   
/*     */   private static TrustManager[] createTrustManagers(KeyStore keystore) throws KeyStoreException, NoSuchAlgorithmException
/*     */   {
/* 272 */     if (keystore == null) {
/* 273 */       throw new IllegalArgumentException("Keystore may not be null");
/*     */     }
/* 275 */     TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*     */     
/* 277 */     tmfactory.init(keystore);
/* 278 */     return tmfactory.getTrustManagers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Socket createSocket()
/*     */     throws IOException
/*     */   {
/* 288 */     return (SSLSocket)this.socketfactory.createSocket();
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
/*     */   public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 302 */     if (host == null) {
/* 303 */       throw new IllegalArgumentException("Target host may not be null.");
/*     */     }
/* 305 */     if (params == null) {
/* 306 */       throw new IllegalArgumentException("Parameters may not be null.");
/*     */     }
/*     */     
/* 309 */     SSLSocket sslsock = (SSLSocket)(sock != null ? sock : createSocket());
/*     */     
/*     */ 
/* 312 */     if ((localAddress != null) || (localPort > 0))
/*     */     {
/*     */ 
/* 315 */       if (localPort < 0) {
/* 316 */         localPort = 0;
/*     */       }
/* 318 */       InetSocketAddress isa = new InetSocketAddress(localAddress, localPort);
/*     */       
/* 320 */       sslsock.bind(isa);
/*     */     }
/*     */     
/* 323 */     int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
/* 324 */     int soTimeout = HttpConnectionParams.getSoTimeout(params);
/*     */     InetSocketAddress remoteAddress;
/*     */     InetSocketAddress remoteAddress;
/* 327 */     if (this.nameResolver != null) {
/* 328 */       remoteAddress = new InetSocketAddress(this.nameResolver.resolve(host), port);
/*     */     } else {
/* 330 */       remoteAddress = new InetSocketAddress(host, port);
/*     */     }
/*     */     try {
/* 333 */       sslsock.connect(remoteAddress, connTimeout);
/*     */     } catch (SocketTimeoutException ex) {
/* 335 */       throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
/*     */     }
/* 337 */     sslsock.setSoTimeout(soTimeout);
/*     */     try {
/* 339 */       this.hostnameVerifier.verify(host, sslsock);
/*     */     }
/*     */     catch (IOException iox) {
/*     */       try {
/* 343 */         sslsock.close(); } catch (Exception x) {}
/* 344 */       throw iox;
/*     */     }
/*     */     
/* 347 */     return sslsock;
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
/*     */   public boolean isSecure(Socket sock)
/*     */     throws IllegalArgumentException
/*     */   {
/* 368 */     if (sock == null) {
/* 369 */       throw new IllegalArgumentException("Socket may not be null.");
/*     */     }
/*     */     
/* 372 */     if (!(sock instanceof SSLSocket)) {
/* 373 */       throw new IllegalArgumentException("Socket not created by this factory.");
/*     */     }
/*     */     
/*     */ 
/* 377 */     if (sock.isClosed()) {
/* 378 */       throw new IllegalArgumentException("Socket is closed.");
/*     */     }
/*     */     
/* 381 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
/*     */     throws IOException, UnknownHostException
/*     */   {
/* 393 */     SSLSocket sslSocket = (SSLSocket)this.socketfactory.createSocket(socket, host, port, autoClose);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 399 */     this.hostnameVerifier.verify(host, sslSocket);
/*     */     
/* 401 */     return sslSocket;
/*     */   }
/*     */   
/*     */   public void setHostnameVerifier(X509HostnameVerifier hostnameVerifier) {
/* 405 */     if (hostnameVerifier == null) {
/* 406 */       throw new IllegalArgumentException("Hostname verifier may not be null");
/*     */     }
/* 408 */     this.hostnameVerifier = hostnameVerifier;
/*     */   }
/*     */   
/*     */   public X509HostnameVerifier getHostnameVerifier() {
/* 412 */     return this.hostnameVerifier;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ssl/SSLSocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */