/*     */ package com.firebase.client.realtime;
/*     */ 
/*     */ import com.firebase.client.core.Context;
/*     */ import com.firebase.client.core.RepoInfo;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Connection
/*     */   implements WebsocketConnection.Delegate
/*     */ {
/*  27 */   private static long connectionIds = 0L;
/*     */   
/*  29 */   private static enum State { REALTIME_CONNECTING,  REALTIME_CONNECTED,  REALTIME_DISCONNECTED;
/*     */     
/*     */     private State() {}
/*     */   }
/*     */   
/*     */   private static final String REQUEST_TYPE = "t";
/*     */   private static final String REQUEST_TYPE_DATA = "d";
/*     */   private static final String REQUEST_PAYLOAD = "d";
/*     */   private static final String SERVER_ENVELOPE_TYPE = "t";
/*     */   private static final String SERVER_DATA_MESSAGE = "d";
/*     */   private static final String SERVER_CONTROL_MESSAGE = "c";
/*     */   private static final String SERVER_ENVELOPE_DATA = "d";
/*     */   private static final String SERVER_CONTROL_MESSAGE_TYPE = "t";
/*     */   private static final String SERVER_CONTROL_MESSAGE_SHUTDOWN = "s";
/*     */   private static final String SERVER_CONTROL_MESSAGE_RESET = "r";
/*     */   private static final String SERVER_CONTROL_MESSAGE_HELLO = "h";
/*     */   private static final String SERVER_CONTROL_MESSAGE_DATA = "d";
/*     */   private static final String SERVER_HELLO_TIMESTAMP = "ts";
/*     */   private static final String SERVER_HELLO_HOST = "h";
/*     */   private RepoInfo repoInfo;
/*     */   private WebsocketConnection conn;
/*     */   private Delegate delegate;
/*     */   private State state;
/*     */   private LogWrapper logger;
/*     */   public Connection(Context ctx, RepoInfo repoInfo, Delegate delegate)
/*     */   {
/*  55 */     long connId = connectionIds++;
/*  56 */     this.repoInfo = repoInfo;
/*  57 */     this.delegate = delegate;
/*  58 */     this.logger = ctx.getLogger("Connection", "conn_" + connId);
/*  59 */     this.state = State.REALTIME_CONNECTING;
/*  60 */     this.conn = new WebsocketConnection(ctx, repoInfo, this);
/*     */   }
/*     */   
/*     */   public void open() {
/*  64 */     if (this.logger.logsDebug()) this.logger.debug("Opening a connection");
/*  65 */     this.conn.open();
/*     */   }
/*     */   
/*     */   public void close() {
/*  69 */     if (this.state != State.REALTIME_DISCONNECTED) {
/*  70 */       if (this.logger.logsDebug()) this.logger.debug("closing realtime connection");
/*  71 */       this.state = State.REALTIME_DISCONNECTED;
/*     */       
/*  73 */       if (this.conn != null) {
/*  74 */         this.conn.close();
/*  75 */         this.conn = null;
/*     */       }
/*     */       
/*  78 */       this.delegate.onDisconnect();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void sendRequest(Map<String, Object> message)
/*     */   {
/*  85 */     Map<String, Object> request = new HashMap();
/*  86 */     request.put("t", "d");
/*  87 */     request.put("d", message);
/*     */     
/*  89 */     sendData(request);
/*     */   }
/*     */   
/*     */   public void onMessage(Map<String, Object> message)
/*     */   {
/*     */     try {
/*  95 */       String messageType = (String)message.get("t");
/*  96 */       if (messageType != null) {
/*  97 */         if (messageType.equals("d")) {
/*  98 */           Map<String, Object> data = (Map)message.get("d");
/*  99 */           onDataMessage(data);
/* 100 */         } else if (messageType.equals("c")) {
/* 101 */           Map<String, Object> data = (Map)message.get("d");
/* 102 */           onControlMessage(data);
/*     */         }
/* 104 */         else if (this.logger.logsDebug()) { this.logger.debug("Ignoring unknown server message type: " + messageType);
/*     */         }
/*     */       } else {
/* 107 */         if (this.logger.logsDebug()) this.logger.debug("Failed to parse server message: missing message type:" + message.toString());
/* 108 */         close();
/*     */       }
/*     */     } catch (ClassCastException e) {
/* 111 */       if (this.logger.logsDebug()) this.logger.debug("Failed to parse server message: " + e.toString());
/* 112 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDisconnect(boolean wasEverConnected)
/*     */   {
/* 118 */     this.conn = null;
/* 119 */     if ((!wasEverConnected) && (this.state == State.REALTIME_CONNECTING)) {
/* 120 */       if (this.logger.logsDebug()) this.logger.debug("Realtime connection failed");
/* 121 */       if (!this.repoInfo.isCacheableHost()) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 126 */     else if (this.logger.logsDebug()) { this.logger.debug("Realtime connection lost");
/*     */     }
/*     */     
/* 129 */     close();
/*     */   }
/*     */   
/*     */   private void onDataMessage(Map<String, Object> data) {
/* 133 */     if (this.logger.logsDebug()) { this.logger.debug("received data message: " + data.toString());
/*     */     }
/* 135 */     this.delegate.onDataMessage(data);
/*     */   }
/*     */   
/*     */   private void onControlMessage(Map<String, Object> data) {
/* 139 */     if (this.logger.logsDebug()) this.logger.debug("Got control message: " + data.toString());
/*     */     try {
/* 141 */       String messageType = (String)data.get("t");
/* 142 */       if (messageType != null) {
/* 143 */         if (messageType.equals("s")) {
/* 144 */           String reason = (String)data.get("d");
/* 145 */           onConnectionShutdown(reason);
/* 146 */         } else if (messageType.equals("r")) {
/* 147 */           String host = (String)data.get("d");
/* 148 */           onReset(host);
/* 149 */         } else if (messageType.equals("h")) {
/* 150 */           Map<String, Object> handshakeData = (Map)data.get("d");
/*     */           
/* 152 */           onHandshake(handshakeData);
/*     */         }
/* 154 */         else if (this.logger.logsDebug()) { this.logger.debug("Ignoring unknown control message: " + messageType);
/*     */         }
/*     */       } else {
/* 157 */         if (this.logger.logsDebug()) this.logger.debug("Got invalid control message: " + data.toString());
/* 158 */         close();
/*     */       }
/*     */     } catch (ClassCastException e) {
/* 161 */       if (this.logger.logsDebug()) this.logger.debug("Failed to parse control message: " + e.toString());
/* 162 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */   private void onConnectionShutdown(String reason) {
/* 167 */     if (this.logger.logsDebug()) this.logger.debug("Connection shutdown command received. Shutting down...");
/* 168 */     this.delegate.onKill(reason);
/* 169 */     close();
/*     */   }
/*     */   
/*     */   private void onHandshake(Map<String, Object> handshake) {
/* 173 */     long timestamp = ((Long)handshake.get("ts")).longValue();
/* 174 */     String host = (String)handshake.get("h");
/* 175 */     this.repoInfo.internalHost = host;
/*     */     
/* 177 */     if (this.state == State.REALTIME_CONNECTING) {
/* 178 */       this.conn.start();
/* 179 */       onConnectionReady(timestamp);
/*     */     }
/*     */   }
/*     */   
/*     */   private void onConnectionReady(long timestamp) {
/* 184 */     if (this.logger.logsDebug()) this.logger.debug("realtime connection established");
/* 185 */     this.state = State.REALTIME_CONNECTED;
/* 186 */     this.delegate.onReady(timestamp);
/*     */   }
/*     */   
/*     */   private void onReset(String host)
/*     */   {
/* 191 */     if (this.logger.logsDebug()) this.logger.debug("Got a reset; killing connection to " + this.repoInfo.internalHost + "; instead connecting to " + host);
/* 192 */     this.repoInfo.internalHost = host;
/*     */   }
/*     */   
/*     */   private void sendData(Map<String, Object> data) {
/* 196 */     if (this.state != State.REALTIME_CONNECTED) {
/* 197 */       if (this.logger.logsDebug()) this.logger.debug("Tried to send on an unconnected connection");
/*     */     } else {
/* 199 */       if (this.logger.logsDebug()) this.logger.debug("Sending data: " + data.toString());
/* 200 */       this.conn.send(data);
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract interface Delegate
/*     */   {
/*     */     public abstract void onReady(long paramLong);
/*     */     
/*     */     public abstract void onDataMessage(Map<String, Object> paramMap);
/*     */     
/*     */     public abstract void onDisconnect();
/*     */     
/*     */     public abstract void onKill(String paramString);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/realtime/Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */