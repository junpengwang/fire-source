/*     */ package com.firebase.tubesock;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ class WebSocketHandshake
/*     */ {
/*     */   private static final String WEBSOCKET_VERSION = "13";
/*  13 */   private URI url = null;
/*  14 */   private String protocol = null;
/*  15 */   private String nonce = null;
/*  16 */   private Map<String, String> extraHeaders = null;
/*     */   
/*     */   public WebSocketHandshake(URI url, String protocol, Map<String, String> extraHeaders)
/*     */   {
/*  20 */     this.url = url;
/*  21 */     this.protocol = protocol;
/*  22 */     this.extraHeaders = extraHeaders;
/*  23 */     this.nonce = createNonce();
/*     */   }
/*     */   
/*     */   public byte[] getHandshake() {
/*  27 */     String path = this.url.getPath();
/*  28 */     String query = this.url.getQuery();
/*  29 */     path = path + (query == null ? "" : new StringBuilder().append("?").append(query).toString());
/*  30 */     String host = this.url.getHost();
/*     */     
/*  32 */     if (this.url.getPort() != -1) {
/*  33 */       host = host + ":" + this.url.getPort();
/*     */     }
/*     */     
/*  36 */     LinkedHashMap<String, String> header = new LinkedHashMap();
/*  37 */     header.put("Host", host);
/*  38 */     header.put("Upgrade", "websocket");
/*  39 */     header.put("Connection", "Upgrade");
/*  40 */     header.put("Sec-WebSocket-Version", "13");
/*  41 */     header.put("Sec-WebSocket-Key", this.nonce);
/*     */     
/*  43 */     if (this.protocol != null) {
/*  44 */       header.put("Sec-WebSocket-Protocol", this.protocol);
/*     */     }
/*     */     
/*  47 */     if (this.extraHeaders != null) {
/*  48 */       for (String fieldName : this.extraHeaders.keySet())
/*     */       {
/*     */ 
/*  51 */         if (!header.containsKey(fieldName)) {
/*  52 */           header.put(fieldName, this.extraHeaders.get(fieldName));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  57 */     String handshake = "GET " + path + " HTTP/1.1\r\n";
/*  58 */     handshake = handshake + generateHeader(header);
/*  59 */     handshake = handshake + "\r\n";
/*     */     
/*  61 */     byte[] handshakeBytes = new byte[handshake.getBytes().length];
/*  62 */     System.arraycopy(handshake.getBytes(), 0, handshakeBytes, 0, handshake.getBytes().length);
/*     */     
/*  64 */     return handshakeBytes;
/*     */   }
/*     */   
/*     */   private String generateHeader(LinkedHashMap<String, String> headers) {
/*  68 */     String header = new String();
/*  69 */     for (String fieldName : headers.keySet()) {
/*  70 */       header = header + fieldName + ": " + (String)headers.get(fieldName) + "\r\n";
/*     */     }
/*  72 */     return header;
/*     */   }
/*     */   
/*     */   private String createNonce() {
/*  76 */     byte[] nonce = new byte[16];
/*  77 */     for (int i = 0; i < 16; i++) {
/*  78 */       nonce[i] = ((byte)rand(0, 255));
/*     */     }
/*  80 */     return Base64.encodeToString(nonce, false);
/*     */   }
/*     */   
/*     */   public void verifyServerStatusLine(String statusLine) {
/*  84 */     int statusCode = Integer.valueOf(statusLine.substring(9, 12)).intValue();
/*     */     
/*  86 */     if (statusCode == 407)
/*  87 */       throw new WebSocketException("connection failed: proxy authentication not supported");
/*  88 */     if (statusCode == 404)
/*  89 */       throw new WebSocketException("connection failed: 404 not found");
/*  90 */     if (statusCode != 101) {
/*  91 */       throw new WebSocketException("connection failed: unknown status code " + statusCode);
/*     */     }
/*     */   }
/*     */   
/*     */   public void verifyServerHandshakeHeaders(HashMap<String, String> headers) {
/*  96 */     if (!((String)headers.get("Upgrade")).toLowerCase(Locale.US).equals("websocket"))
/*  97 */       throw new WebSocketException("connection failed: missing header field in server handshake: Upgrade");
/*  98 */     if (!((String)headers.get("Connection")).toLowerCase(Locale.US).equals("upgrade")) {
/*  99 */       throw new WebSocketException("connection failed: missing header field in server handshake: Connection");
/*     */     }
/*     */   }
/*     */   
/*     */   private int rand(int min, int max) {
/* 104 */     int rand = (int)(Math.random() * max + min);
/* 105 */     return rand;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/WebSocketHandshake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */