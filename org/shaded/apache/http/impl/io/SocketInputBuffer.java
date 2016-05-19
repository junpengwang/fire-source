/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.Socket;
/*     */ import org.shaded.apache.http.io.EofSensor;
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
/*     */ public class SocketInputBuffer
/*     */   extends AbstractSessionInputBuffer
/*     */   implements EofSensor
/*     */ {
/*  54 */   private static final Class SOCKET_TIMEOUT_CLASS = ;
/*     */   
/*     */   private final Socket socket;
/*     */   
/*     */   private boolean eof;
/*     */   
/*     */   private static Class SocketTimeoutExceptionClass()
/*     */   {
/*     */     try
/*     */     {
/*  64 */       return Class.forName("java.net.SocketTimeoutException");
/*     */     } catch (ClassNotFoundException e) {}
/*  66 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean isSocketTimeoutException(InterruptedIOException e)
/*     */   {
/*  71 */     if (SOCKET_TIMEOUT_CLASS != null) {
/*  72 */       return SOCKET_TIMEOUT_CLASS.isInstance(e);
/*     */     }
/*  74 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocketInputBuffer(Socket socket, int buffersize, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 112 */     if (socket == null) {
/* 113 */       throw new IllegalArgumentException("Socket may not be null");
/*     */     }
/* 115 */     this.socket = socket;
/* 116 */     this.eof = false;
/* 117 */     if (buffersize < 0) {
/* 118 */       buffersize = socket.getReceiveBufferSize();
/*     */     }
/* 120 */     if (buffersize < 1024) {
/* 121 */       buffersize = 1024;
/*     */     }
/* 123 */     init(socket.getInputStream(), buffersize, params);
/*     */   }
/*     */   
/*     */   protected int fillBuffer() throws IOException {
/* 127 */     int i = super.fillBuffer();
/* 128 */     this.eof = (i == -1);
/* 129 */     return i;
/*     */   }
/*     */   
/*     */   public boolean isDataAvailable(int timeout) throws IOException {
/* 133 */     boolean result = hasBufferedData();
/* 134 */     if (!result) {
/* 135 */       int oldtimeout = this.socket.getSoTimeout();
/*     */       try {
/* 137 */         this.socket.setSoTimeout(timeout);
/* 138 */         fillBuffer();
/* 139 */         result = hasBufferedData();
/*     */       } catch (InterruptedIOException e) {
/* 141 */         if (!isSocketTimeoutException(e)) {
/* 142 */           throw e;
/*     */         }
/*     */       } finally {
/* 145 */         this.socket.setSoTimeout(oldtimeout);
/*     */       }
/*     */     }
/* 148 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isEof() {
/* 152 */     return this.eof;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/SocketInputBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */