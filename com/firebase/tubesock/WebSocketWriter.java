/*     */ package com.firebase.tubesock;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WebSocketWriter
/*     */   extends Thread
/*     */ {
/*     */   private BlockingQueue<ByteBuffer> pendingBuffers;
/*  25 */   private final Random random = new Random();
/*  26 */   private volatile boolean stop = false;
/*  27 */   private boolean closeSent = false;
/*     */   private WebSocket websocket;
/*     */   private WritableByteChannel channel;
/*     */   
/*     */   WebSocketWriter(WebSocket websocket, String threadBaseName, int clientId) {
/*  32 */     setName(threadBaseName + "Writer-" + clientId);
/*  33 */     this.websocket = websocket;
/*  34 */     this.pendingBuffers = new LinkedBlockingQueue();
/*     */   }
/*     */   
/*     */   void setOutput(OutputStream output) {
/*  38 */     this.channel = Channels.newChannel(output);
/*     */   }
/*     */   
/*     */   private ByteBuffer frameInBuffer(byte opcode, boolean masking, byte[] data) throws IOException {
/*  42 */     int headerLength = 2;
/*  43 */     if (masking) {
/*  44 */       headerLength += 4;
/*     */     }
/*  46 */     int length = data.length;
/*  47 */     if (length >= 126)
/*     */     {
/*  49 */       if (length <= 65535) {
/*  50 */         headerLength += 2;
/*     */       } else
/*  52 */         headerLength += 8;
/*     */     }
/*  54 */     ByteBuffer frame = ByteBuffer.allocate(data.length + headerLength);
/*     */     
/*  56 */     byte fin = Byte.MIN_VALUE;
/*  57 */     byte startByte = (byte)(fin | opcode);
/*  58 */     frame.put(startByte);
/*     */     
/*     */ 
/*     */ 
/*  62 */     if (length < 126) {
/*  63 */       if (masking) {
/*  64 */         length = 0x80 | length;
/*     */       }
/*  66 */       frame.put((byte)length);
/*  67 */     } else if (length <= 65535) {
/*  68 */       int length_field = 126;
/*  69 */       if (masking) {
/*  70 */         length_field = 0x80 | length_field;
/*     */       }
/*  72 */       frame.put((byte)length_field);
/*     */       
/*  74 */       frame.putShort((short)length);
/*     */     } else {
/*  76 */       int length_field = 127;
/*  77 */       if (masking) {
/*  78 */         length_field = 0x80 | length_field;
/*     */       }
/*  80 */       frame.put((byte)length_field);
/*     */       
/*  82 */       frame.putInt(0);
/*  83 */       frame.putInt(length);
/*     */     }
/*     */     
/*     */ 
/*  87 */     if (masking) {
/*  88 */       byte[] mask = generateMask();
/*  89 */       frame.put(mask);
/*     */       
/*  91 */       for (int i = 0; i < data.length; i++) {
/*  92 */         frame.put((byte)(data[i] ^ mask[(i % 4)]));
/*     */       }
/*     */     }
/*     */     
/*  96 */     frame.flip();
/*  97 */     return frame;
/*     */   }
/*     */   
/*     */   private byte[] generateMask() {
/* 101 */     byte[] mask = new byte[4];
/* 102 */     this.random.nextBytes(mask);
/* 103 */     return mask;
/*     */   }
/*     */   
/*     */   synchronized void send(byte opcode, boolean masking, byte[] data) throws IOException {
/* 107 */     ByteBuffer frame = frameInBuffer(opcode, masking, data);
/* 108 */     if ((this.stop) && ((this.closeSent) || (opcode != 8))) {
/* 109 */       throw new WebSocketException("Shouldn't be sending");
/*     */     }
/* 111 */     if (opcode == 8) {
/* 112 */       this.closeSent = true;
/*     */     }
/* 114 */     this.pendingBuffers.add(frame);
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*     */     try {
/* 120 */       while ((!this.stop) && (!Thread.interrupted())) {
/* 121 */         writeMessage();
/*     */       }
/*     */       
/* 124 */       for (int i = 0; i < this.pendingBuffers.size(); i++) {
/* 125 */         writeMessage();
/*     */       }
/*     */     } catch (IOException e) {
/* 128 */       handleError(new WebSocketException("IO Exception", e));
/*     */     }
/*     */     catch (InterruptedException e) {}
/*     */   }
/*     */   
/*     */   private void writeMessage()
/*     */     throws InterruptedException, IOException
/*     */   {
/* 136 */     ByteBuffer msg = (ByteBuffer)this.pendingBuffers.take();
/* 137 */     this.channel.write(msg);
/*     */   }
/*     */   
/*     */   void stopIt() {
/* 141 */     this.stop = true;
/*     */   }
/*     */   
/*     */   private void handleError(WebSocketException e) {
/* 145 */     this.websocket.handleReceiverError(e);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/WebSocketWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */