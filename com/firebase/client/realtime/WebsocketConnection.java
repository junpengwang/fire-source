/*     */ package com.firebase.client.realtime;
/*     */ 
/*     */ import com.firebase.client.RunLoop;
/*     */ import com.firebase.client.core.Context;
/*     */ import com.firebase.client.core.RepoInfo;
/*     */ import com.firebase.client.realtime.util.StringListReader;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import com.firebase.client.utilities.encoding.JsonHelpers;
/*     */ import com.firebase.tubesock.WebSocket;
/*     */ import com.firebase.tubesock.WebSocketEventHandler;
/*     */ import com.firebase.tubesock.WebSocketException;
/*     */ import com.firebase.tubesock.WebSocketMessage;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebsocketConnection
/*     */ {
/*  30 */   private static long connectionId = 0L;
/*     */   
/*     */ 
/*     */   private static final long KEEP_ALIVE = 45000L;
/*     */   
/*     */ 
/*     */   private static final long CONNECT_TIMEOUT = 30000L;
/*     */   
/*     */   private static final int MAX_FRAME_SIZE = 16384;
/*     */   
/*     */   private WSClient conn;
/*     */   
/*     */ 
/*     */   private class WSClientTubesock
/*     */     implements WebsocketConnection.WSClient, WebSocketEventHandler
/*     */   {
/*     */     private WebSocket ws;
/*     */     
/*     */ 
/*     */     private WSClientTubesock(WebSocket ws)
/*     */     {
/*  51 */       this.ws = ws;
/*  52 */       this.ws.setEventHandler(this);
/*     */     }
/*     */     
/*     */     public void onOpen()
/*     */     {
/*  57 */       WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
/*     */         public void run() {
/*  59 */           WebsocketConnection.this.connectTimeout.cancel(false);
/*  60 */           WebsocketConnection.this.everConnected = true;
/*  61 */           if (WebsocketConnection.this.logger.logsDebug()) WebsocketConnection.this.logger.debug("websocket opened");
/*  62 */           WebsocketConnection.this.resetKeepAlive();
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*     */     public void onMessage(WebSocketMessage msg)
/*     */     {
/*  69 */       final String str = msg.getText();
/*  70 */       if (WebsocketConnection.this.logger.logsDebug()) WebsocketConnection.this.logger.debug("ws message: " + str);
/*  71 */       WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
/*     */         public void run() {
/*  73 */           WebsocketConnection.this.handleIncomingFrame(str);
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*     */     public void onClose()
/*     */     {
/*  80 */       String logMessage = "closed";
/*  81 */       WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
/*     */         public void run() {
/*  83 */           if (WebsocketConnection.this.logger.logsDebug()) WebsocketConnection.this.logger.debug("closed");
/*  84 */           WebsocketConnection.this.onClosed();
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*     */     public void onError(final WebSocketException e)
/*     */     {
/*  91 */       WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
/*     */         public void run() {
/*  93 */           String logMessage = "had an error";
/*  94 */           if (WebsocketConnection.this.logger.logsDebug()) WebsocketConnection.this.logger.debug(logMessage, e);
/*  95 */           if (e.getMessage().startsWith("unknown host")) {
/*  96 */             if (WebsocketConnection.this.logger.logsDebug()) WebsocketConnection.this.logger.debug("If you are running on Android, have you added <uses-permission android:name=\"android.permission.INTERNET\" /> under <manifest> in AndroidManifest.xml?");
/*     */           }
/*  98 */           else if (WebsocketConnection.this.logger.logsDebug()) { WebsocketConnection.this.logger.debug("|" + e.getMessage() + "|");
/*     */           }
/* 100 */           WebsocketConnection.this.onClosed();
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*     */     public void onLogMessage(String msg)
/*     */     {
/* 107 */       if (WebsocketConnection.this.logger.logsDebug()) WebsocketConnection.this.logger.debug("Tubesock: " + msg);
/*     */     }
/*     */     
/*     */     public void send(String msg) {
/* 111 */       this.ws.send(msg);
/*     */     }
/*     */     
/*     */     public void close() {
/* 115 */       this.ws.close();
/*     */     }
/*     */     
/*     */     private void shutdown() {
/* 119 */       this.ws.close();
/*     */       try {
/* 121 */         this.ws.blockClose();
/*     */       } catch (InterruptedException e) {
/* 123 */         WebsocketConnection.this.logger.error("Interrupted while shutting down websocket threads", e);
/*     */       }
/*     */     }
/*     */     
/*     */     public void connect() {
/*     */       try {
/* 129 */         this.ws.connect();
/*     */       } catch (WebSocketException e) {
/* 131 */         if (WebsocketConnection.this.logger.logsDebug()) WebsocketConnection.this.logger.debug("Error connecting", e);
/* 132 */         shutdown();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 138 */   private boolean everConnected = false;
/* 139 */   private boolean isClosed = false;
/* 140 */   private long totalFrames = 0L;
/*     */   private StringListReader frameReader;
/*     */   private Delegate delegate;
/*     */   private ScheduledFuture keepAlive;
/*     */   private ObjectMapper mapper;
/*     */   private ScheduledFuture connectTimeout;
/*     */   private Context ctx;
/*     */   private LogWrapper logger;
/*     */   private MapType mapType;
/*     */   
/*     */   public WebsocketConnection(Context ctx, RepoInfo repoInfo, Delegate delegate) {
/* 151 */     long connId = connectionId++;
/* 152 */     this.mapper = JsonHelpers.getMapper();
/* 153 */     this.mapType = this.mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
/* 154 */     this.delegate = delegate;
/* 155 */     this.ctx = ctx;
/* 156 */     this.logger = ctx.getLogger("WebSocket", "ws_" + connId);
/* 157 */     this.conn = createConnection(repoInfo);
/*     */   }
/*     */   
/*     */   private WSClient createConnection(RepoInfo repoInfo) {
/* 161 */     URI uri = repoInfo.getConnectionURL();
/* 162 */     Map<String, String> extraHeaders = new HashMap();
/* 163 */     extraHeaders.put("User-Agent", this.ctx.getUserAgent());
/* 164 */     WebSocket ws = new WebSocket(uri, null, extraHeaders);
/* 165 */     WSClientTubesock client = new WSClientTubesock(ws, null);
/* 166 */     return client;
/*     */   }
/*     */   
/*     */   public void open() {
/* 170 */     this.conn.connect();
/* 171 */     this.connectTimeout = this.ctx.getRunLoop().schedule(new Runnable()
/*     */     {
/* 173 */       public void run() { WebsocketConnection.this.closeIfNeverConnected(); } }, 30000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void start() {}
/*     */   
/*     */ 
/*     */   public void close()
/*     */   {
/* 183 */     if (this.logger.logsDebug()) this.logger.debug("websocket is being closed");
/* 184 */     this.isClosed = true;
/*     */     
/*     */ 
/* 187 */     this.conn.close();
/* 188 */     if (this.connectTimeout != null) {
/* 189 */       this.connectTimeout.cancel(true);
/*     */     }
/* 191 */     if (this.keepAlive != null) {
/* 192 */       this.keepAlive.cancel(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void send(Map<String, Object> message) {
/* 197 */     resetKeepAlive();
/*     */     try
/*     */     {
/* 200 */       String toSend = this.mapper.writeValueAsString(message);
/* 201 */       String[] segs = Utilities.splitIntoFrames(toSend, 16384);
/* 202 */       if (segs.length > 1) {
/* 203 */         this.conn.send("" + segs.length);
/*     */       }
/*     */       
/* 206 */       for (int i = 0; i < segs.length; i++) {
/* 207 */         this.conn.send(segs[i]);
/*     */       }
/*     */     } catch (IOException e) {
/* 210 */       this.logger.error("Failed to serialize message: " + message.toString(), e);
/* 211 */       shutdown();
/*     */     }
/*     */   }
/*     */   
/*     */   private void appendFrame(String message) {
/* 216 */     this.frameReader.addString(message);
/* 217 */     this.totalFrames -= 1L;
/* 218 */     if (this.totalFrames == 0L) {
/*     */       try
/*     */       {
/* 221 */         this.frameReader.freeze();
/* 222 */         Map<String, Object> decoded = (Map)this.mapper.readValue(this.frameReader, this.mapType);
/* 223 */         this.frameReader = null;
/* 224 */         if (this.logger.logsDebug()) this.logger.debug("handleIncomingFrame complete frame: " + decoded);
/* 225 */         this.delegate.onMessage(decoded);
/*     */       } catch (IOException e) {
/* 227 */         this.logger.error("Error parsing frame: " + this.frameReader.toString(), e);
/* 228 */         close();
/* 229 */         shutdown();
/*     */       } catch (ClassCastException e) {
/* 231 */         this.logger.error("Error parsing frame (cast error): " + this.frameReader.toString(), e);
/* 232 */         close();
/* 233 */         shutdown();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void handleNewFrameCount(int numFrames) {
/* 239 */     this.totalFrames = numFrames;
/* 240 */     this.frameReader = new StringListReader();
/* 241 */     if (this.logger.logsDebug()) { this.logger.debug("HandleNewFrameCount: " + this.totalFrames);
/*     */     }
/*     */   }
/*     */   
/*     */   private String extractFrameCount(String message)
/*     */   {
/* 247 */     if (message.length() <= 6) {
/*     */       try {
/* 249 */         int frameCount = Integer.parseInt(message);
/* 250 */         if (frameCount > 0) {
/* 251 */           handleNewFrameCount(frameCount);
/*     */         }
/* 253 */         return null;
/*     */       }
/*     */       catch (NumberFormatException e) {}
/*     */     }
/*     */     
/* 258 */     handleNewFrameCount(1);
/* 259 */     return message;
/*     */   }
/*     */   
/*     */   private void handleIncomingFrame(String message) {
/* 263 */     if (!this.isClosed) {
/* 264 */       resetKeepAlive();
/* 265 */       if (isBuffering()) {
/* 266 */         appendFrame(message);
/*     */       } else {
/* 268 */         String remaining = extractFrameCount(message);
/* 269 */         if (remaining != null) {
/* 270 */           appendFrame(remaining);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void resetKeepAlive() {
/* 277 */     if (!this.isClosed) {
/* 278 */       if (this.keepAlive != null) {
/* 279 */         this.keepAlive.cancel(false);
/* 280 */         if (this.logger.logsDebug()) this.logger.debug("Reset keepAlive. Remaining: " + this.keepAlive.getDelay(TimeUnit.MILLISECONDS));
/*     */       }
/* 282 */       else if (this.logger.logsDebug()) { this.logger.debug("Reset keepAlive");
/*     */       }
/* 284 */       this.keepAlive = this.ctx.getRunLoop().schedule(nop(), 45000L);
/*     */     }
/*     */   }
/*     */   
/*     */   private Runnable nop() {
/* 289 */     new Runnable() {
/*     */       public void run() {
/* 291 */         if (WebsocketConnection.this.conn != null) {
/* 292 */           WebsocketConnection.this.conn.send("0");
/* 293 */           WebsocketConnection.this.resetKeepAlive();
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   private boolean isBuffering() {
/* 300 */     return this.frameReader != null;
/*     */   }
/*     */   
/*     */ 
/*     */   private void onClosed()
/*     */   {
/* 306 */     if (!this.isClosed) {
/* 307 */       if (this.logger.logsDebug()) this.logger.debug("closing itself");
/* 308 */       shutdown();
/*     */     }
/* 310 */     this.conn = null;
/* 311 */     if (this.keepAlive != null) {
/* 312 */       this.keepAlive.cancel(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void shutdown() {
/* 317 */     this.isClosed = true;
/* 318 */     this.delegate.onDisconnect(this.everConnected);
/*     */   }
/*     */   
/*     */   private void closeIfNeverConnected() {
/* 322 */     if ((!this.everConnected) && (!this.isClosed)) {
/* 323 */       if (this.logger.logsDebug()) this.logger.debug("timed out on connect");
/* 324 */       this.conn.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract interface WSClient
/*     */   {
/*     */     public abstract void connect();
/*     */     
/*     */     public abstract void close();
/*     */     
/*     */     public abstract void send(String paramString);
/*     */   }
/*     */   
/*     */   public static abstract interface Delegate
/*     */   {
/*     */     public abstract void onMessage(Map<String, Object> paramMap);
/*     */     
/*     */     public abstract void onDisconnect(boolean paramBoolean);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/realtime/WebsocketConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */