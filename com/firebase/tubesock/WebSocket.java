/*     */ package com.firebase.tubesock;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import java.net.URI;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.net.SocketFactory;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import org.shaded.apache.http.conn.ssl.StrictHostnameVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocket
/*     */   extends Thread
/*     */ {
/*     */   private static final String THREAD_BASE_NAME = "TubeSock";
/*  30 */   private static final AtomicInteger clientCount = new AtomicInteger(0);
/*     */   
/*  32 */   private static enum State { NONE,  CONNECTING,  CONNECTED,  DISCONNECTING,  DISCONNECTED;
/*     */     private State() {} }
/*  34 */   private static final Charset UTF8 = Charset.forName("UTF-8");
/*     */   
/*     */   static final byte OPCODE_NONE = 0;
/*     */   
/*     */   static final byte OPCODE_TEXT = 1;
/*     */   static final byte OPCODE_BINARY = 2;
/*     */   static final byte OPCODE_CLOSE = 8;
/*     */   static final byte OPCODE_PING = 9;
/*     */   static final byte OPCODE_PONG = 10;
/*  43 */   private volatile State state = State.NONE;
/*  44 */   private volatile Socket socket = null;
/*     */   
/*  46 */   private WebSocketEventHandler eventHandler = null;
/*     */   
/*     */   private final URI url;
/*     */   
/*     */   private final WebSocketReceiver receiver;
/*     */   private final WebSocketWriter writer;
/*     */   private final WebSocketHandshake handshake;
/*  53 */   private final int clientId = clientCount.incrementAndGet();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocket(URI url)
/*     */   {
/*  60 */     this(url, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocket(URI url, String protocol)
/*     */   {
/*  69 */     this(url, protocol, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocket(URI url, String protocol, Map<String, String> extraHeaders)
/*     */   {
/*  81 */     this.url = url;
/*  82 */     this.handshake = new WebSocketHandshake(url, protocol, extraHeaders);
/*  83 */     this.receiver = new WebSocketReceiver(this);
/*  84 */     this.writer = new WebSocketWriter(this, "TubeSock", this.clientId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEventHandler(WebSocketEventHandler eventHandler)
/*     */   {
/*  92 */     this.eventHandler = eventHandler;
/*     */   }
/*     */   
/*     */   WebSocketEventHandler getEventHandler() {
/*  96 */     return this.eventHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void connect()
/*     */   {
/* 104 */     if (this.state != State.NONE) {
/* 105 */       this.eventHandler.onError(new WebSocketException("connect() already called"));
/* 106 */       close();
/* 107 */       return;
/*     */     }
/* 109 */     setName("TubeSockReader-" + this.clientId);
/* 110 */     this.state = State.CONNECTING;
/* 111 */     start();
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*     */     try {
/* 117 */       Socket socket = createSocket();
/* 118 */       synchronized (this) {
/* 119 */         this.socket = socket;
/* 120 */         if (this.state == State.DISCONNECTED)
/*     */         {
/*     */           try {
/* 123 */             this.socket.close();
/*     */           } catch (IOException e) {
/* 125 */             throw new RuntimeException(e);
/*     */           }
/* 127 */           this.socket = null; return;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 132 */       DataInputStream input = new DataInputStream(socket.getInputStream());
/* 133 */       OutputStream output = socket.getOutputStream();
/*     */       
/* 135 */       output.write(this.handshake.getHandshake());
/*     */       
/* 137 */       boolean handshakeComplete = false;
/* 138 */       int len = 1000;
/* 139 */       byte[] buffer = new byte[len];
/* 140 */       int pos = 0;
/* 141 */       ArrayList<String> handshakeLines = new ArrayList();
/*     */       
/* 143 */       while (!handshakeComplete) {
/* 144 */         int b = input.read();
/* 145 */         if (b == -1) {
/* 146 */           throw new WebSocketException("Connection closed before handshake was complete");
/*     */         }
/* 148 */         buffer[pos] = ((byte)b);
/* 149 */         pos++;
/*     */         
/* 151 */         if ((buffer[(pos - 1)] == 10) && (buffer[(pos - 2)] == 13)) {
/* 152 */           String line = new String(buffer, UTF8);
/* 153 */           if (line.trim().equals("")) {
/* 154 */             handshakeComplete = true;
/*     */           } else {
/* 156 */             handshakeLines.add(line.trim());
/*     */           }
/*     */           
/* 159 */           buffer = new byte[len];
/* 160 */           pos = 0;
/* 161 */         } else if (pos == 1000)
/*     */         {
/* 163 */           String line = new String(buffer, UTF8);
/* 164 */           throw new WebSocketException("Unexpected long line in handshake: " + line);
/*     */         }
/*     */       }
/*     */       
/* 168 */       this.handshake.verifyServerStatusLine((String)handshakeLines.get(0));
/* 169 */       handshakeLines.remove(0);
/*     */       
/* 171 */       HashMap<String, String> headers = new HashMap();
/* 172 */       for (String line : handshakeLines) {
/* 173 */         String[] keyValue = line.split(": ", 2);
/* 174 */         headers.put(keyValue[0], keyValue[1]);
/*     */       }
/* 176 */       this.handshake.verifyServerHandshakeHeaders(headers);
/*     */       
/* 178 */       this.writer.setOutput(output);
/* 179 */       this.receiver.setInput(input);
/* 180 */       this.state = State.CONNECTED;
/* 181 */       this.writer.start();
/* 182 */       this.eventHandler.onOpen();
/* 183 */       this.receiver.run();
/*     */     } catch (WebSocketException wse) {
/* 185 */       this.eventHandler.onError(wse);
/*     */     } catch (IOException ioe) {
/* 187 */       this.eventHandler.onError(new WebSocketException("error while connecting: " + ioe.getMessage(), ioe));
/*     */     } finally {
/* 189 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void send(String data)
/*     */   {
/* 198 */     send((byte)1, data.getBytes(UTF8));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void send(byte[] data)
/*     */   {
/* 206 */     send((byte)2, data);
/*     */   }
/*     */   
/*     */   synchronized void pong(byte[] data) {
/* 210 */     send((byte)10, data);
/*     */   }
/*     */   
/*     */   private synchronized void send(byte opcode, byte[] data) {
/* 214 */     if (this.state != State.CONNECTED)
/*     */     {
/* 216 */       this.eventHandler.onError(new WebSocketException("error while sending data: not connected"));
/*     */     } else {
/*     */       try {
/* 219 */         this.writer.send(opcode, true, data);
/*     */       } catch (IOException e) {
/* 221 */         this.eventHandler.onError(new WebSocketException("Failed to send frame", e));
/* 222 */         close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void handleReceiverError(WebSocketException e) {
/* 228 */     this.eventHandler.onError(e);
/* 229 */     if (this.state == State.CONNECTED) {
/* 230 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void close()
/*     */   {
/* 239 */     switch (this.state) {
/*     */     case NONE: 
/* 241 */       this.state = State.DISCONNECTED;
/* 242 */       return;
/*     */     
/*     */     case CONNECTING: 
/* 245 */       closeSocket();
/* 246 */       return;
/*     */     
/*     */ 
/*     */     case CONNECTED: 
/* 250 */       sendCloseHandshake();
/* 251 */       return;
/*     */     case DISCONNECTING: 
/*     */       
/*     */     case DISCONNECTED: 
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */   void onCloseOpReceived() {
/* 260 */     closeSocket();
/*     */   }
/*     */   
/*     */   private synchronized void closeSocket() {
/* 264 */     if (this.state == State.DISCONNECTED) {
/* 265 */       return;
/*     */     }
/* 267 */     this.receiver.stopit();
/* 268 */     this.writer.stopIt();
/* 269 */     if (this.socket != null) {
/*     */       try {
/* 271 */         this.socket.close();
/*     */       } catch (IOException e) {
/* 273 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/* 276 */     this.state = State.DISCONNECTED;
/*     */     
/* 278 */     this.eventHandler.onClose();
/*     */   }
/*     */   
/*     */   private void sendCloseHandshake() {
/*     */     try {
/* 283 */       this.state = State.DISCONNECTING;
/*     */       
/*     */ 
/* 286 */       this.writer.stopIt();
/* 287 */       this.writer.send((byte)8, true, new byte[0]);
/*     */     } catch (IOException e) {
/* 289 */       this.eventHandler.onError(new WebSocketException("Failed to send close frame", e));
/*     */     }
/*     */   }
/*     */   
/*     */   private Socket createSocket() {
/* 294 */     String scheme = this.url.getScheme();
/* 295 */     String host = this.url.getHost();
/* 296 */     int port = this.url.getPort();
/*     */     
/*     */     Socket socket;
/*     */     
/* 300 */     if ((scheme != null) && (scheme.equals("ws"))) {
/* 301 */       if (port == -1) {
/* 302 */         port = 80;
/*     */       }
/*     */       try {
/* 305 */         socket = new Socket(host, port);
/*     */       } catch (UnknownHostException uhe) {
/* 307 */         throw new WebSocketException("unknown host: " + host, uhe);
/*     */       } catch (IOException ioe) {
/* 309 */         throw new WebSocketException("error while creating socket to " + this.url, ioe);
/*     */       }
/* 311 */     } else if ((scheme != null) && (scheme.equals("wss"))) {
/* 312 */       if (port == -1) {
/* 313 */         port = 443;
/*     */       }
/*     */       try {
/* 316 */         SocketFactory factory = SSLSocketFactory.getDefault();
/* 317 */         socket = factory.createSocket(host, port);
/*     */         
/* 319 */         verifyHost((SSLSocket)socket, host);
/*     */       } catch (UnknownHostException uhe) {
/* 321 */         throw new WebSocketException("unknown host: " + host, uhe);
/*     */       } catch (IOException ioe) {
/* 323 */         throw new WebSocketException("error while creating secure socket to " + this.url, ioe);
/*     */       }
/*     */     } else {
/* 326 */       throw new WebSocketException("unsupported protocol: " + scheme);
/*     */     }
/*     */     
/* 329 */     return socket;
/*     */   }
/*     */   
/*     */   private void verifyHost(SSLSocket socket, String host) throws SSLException {
/* 333 */     Certificate[] certs = socket.getSession().getPeerCertificates();
/* 334 */     X509Certificate peerCert = (X509Certificate)certs[0];
/* 335 */     StrictHostnameVerifier verifier = new StrictHostnameVerifier();
/* 336 */     verifier.verify(host, peerCert);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void blockClose()
/*     */     throws InterruptedException
/*     */   {
/* 346 */     if (this.writer.getState() != Thread.State.NEW) {
/* 347 */       this.writer.join();
/*     */     }
/* 349 */     join();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/WebSocket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */