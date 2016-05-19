/*     */ package com.firebase.tubesock;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WebSocketReceiver
/*     */ {
/*  11 */   private DataInputStream input = null;
/*  12 */   private WebSocket websocket = null;
/*  13 */   private WebSocketEventHandler eventHandler = null;
/*  14 */   private byte[] inputHeader = new byte[112];
/*     */   
/*     */   private MessageBuilderFactory.Builder pendingBuilder;
/*  17 */   private volatile boolean stop = false;
/*     */   
/*     */   WebSocketReceiver(WebSocket websocket)
/*     */   {
/*  21 */     this.websocket = websocket;
/*     */   }
/*     */   
/*     */   void setInput(DataInputStream input) {
/*  25 */     this.input = input;
/*     */   }
/*     */   
/*     */   void run() {
/*  29 */     this.eventHandler = this.websocket.getEventHandler();
/*  30 */     while (!this.stop) {
/*     */       try {
/*  32 */         int offset = 0;
/*  33 */         offset += read(this.inputHeader, offset, 1);
/*  34 */         boolean fin = (this.inputHeader[0] & 0x80) != 0;
/*  35 */         boolean rsv = (this.inputHeader[0] & 0x70) != 0;
/*  36 */         if (rsv) {
/*  37 */           throw new WebSocketException("Invalid frame received");
/*     */         }
/*  39 */         byte opcode = (byte)(this.inputHeader[0] & 0xF);
/*  40 */         offset += read(this.inputHeader, offset, 1);
/*  41 */         byte length = this.inputHeader[1];
/*  42 */         long payload_length = 0L;
/*  43 */         if (length < 126) {
/*  44 */           payload_length = length;
/*  45 */         } else if (length == 126) {
/*  46 */           offset += read(this.inputHeader, offset, 2);
/*  47 */           payload_length = (0xFF & this.inputHeader[2]) << 8 | 0xFF & this.inputHeader[3];
/*  48 */         } else if (length == Byte.MAX_VALUE)
/*     */         {
/*     */ 
/*     */ 
/*  52 */           offset += read(this.inputHeader, offset, 8);
/*     */           
/*  54 */           payload_length = parseLong(this.inputHeader, offset - 8);
/*     */         }
/*     */         
/*  57 */         byte[] payload = new byte[(int)payload_length];
/*  58 */         read(payload, 0, (int)payload_length);
/*  59 */         if (opcode == 8) {
/*  60 */           this.websocket.onCloseOpReceived();
/*  61 */         } else if (opcode != 10)
/*     */         {
/*  63 */           if ((opcode == 1) || (opcode == 2) || (opcode == 9) || (opcode == 0))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*  68 */             appendBytes(fin, opcode, payload);
/*     */           }
/*     */           else {
/*  71 */             throw new WebSocketException("Unsupported opcode: " + opcode);
/*     */           }
/*     */         }
/*     */       } catch (IOException ioe) {
/*  75 */         handleError(new WebSocketException("IO Error", ioe));
/*     */       } catch (WebSocketException e) {
/*  77 */         handleError(e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void appendBytes(boolean fin, byte opcode, byte[] data)
/*     */   {
/*  84 */     if (opcode == 9) {
/*  85 */       if (fin) {
/*  86 */         handlePing(data);
/*     */       } else {
/*  88 */         throw new WebSocketException("PING must not fragment across frames");
/*     */       }
/*     */     } else {
/*  91 */       if ((this.pendingBuilder != null) && (opcode != 0))
/*  92 */         throw new WebSocketException("Failed to continue outstanding frame");
/*  93 */       if ((this.pendingBuilder == null) && (opcode == 0))
/*     */       {
/*  95 */         throw new WebSocketException("Received continuing frame, but there's nothing to continue");
/*     */       }
/*  97 */       if (this.pendingBuilder == null)
/*     */       {
/*  99 */         this.pendingBuilder = MessageBuilderFactory.builder(opcode);
/*     */       }
/* 101 */       if (!this.pendingBuilder.appendBytes(data))
/* 102 */         throw new WebSocketException("Failed to decode frame");
/* 103 */       if (fin) {
/* 104 */         WebSocketMessage message = this.pendingBuilder.toMessage();
/* 105 */         this.pendingBuilder = null;
/*     */         
/* 107 */         if (message == null) {
/* 108 */           throw new WebSocketException("Failed to decode whole message");
/*     */         }
/* 110 */         this.eventHandler.onMessage(message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void handlePing(byte[] payload)
/*     */   {
/* 118 */     if (payload.length <= 125) {
/* 119 */       this.websocket.pong(payload);
/*     */     } else {
/* 121 */       throw new WebSocketException("PING frame too long");
/*     */     }
/*     */   }
/*     */   
/*     */   private long parseLong(byte[] buffer, int offset)
/*     */   {
/* 127 */     return (buffer[(offset + 0)] << 56) + ((buffer[(offset + 1)] & 0xFF) << 48) + ((buffer[(offset + 2)] & 0xFF) << 40) + ((buffer[(offset + 3)] & 0xFF) << 32) + ((buffer[(offset + 4)] & 0xFF) << 24) + ((buffer[(offset + 5)] & 0xFF) << 16) + ((buffer[(offset + 6)] & 0xFF) << 8) + ((buffer[(offset + 7)] & 0xFF) << 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int read(byte[] buffer, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 138 */     this.input.readFully(buffer, offset, length);
/* 139 */     return length;
/*     */   }
/*     */   
/*     */   void stopit() {
/* 143 */     this.stop = true;
/*     */   }
/*     */   
/*     */   boolean isRunning() {
/* 147 */     return !this.stop;
/*     */   }
/*     */   
/*     */   private void handleError(WebSocketException e) {
/* 151 */     stopit();
/* 152 */     this.websocket.handleReceiverError(e);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/WebSocketReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */