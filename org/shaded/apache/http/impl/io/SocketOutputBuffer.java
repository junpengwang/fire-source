/*    */ package org.shaded.apache.http.impl.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import org.shaded.apache.http.params.HttpParams;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SocketOutputBuffer
/*    */   extends AbstractSessionOutputBuffer
/*    */ {
/*    */   public SocketOutputBuffer(Socket socket, int buffersize, HttpParams params)
/*    */     throws IOException
/*    */   {
/* 74 */     if (socket == null) {
/* 75 */       throw new IllegalArgumentException("Socket may not be null");
/*    */     }
/* 77 */     if (buffersize < 0) {
/* 78 */       buffersize = socket.getSendBufferSize();
/*    */     }
/* 80 */     if (buffersize < 1024) {
/* 81 */       buffersize = 1024;
/*    */     }
/* 83 */     init(socket.getOutputStream(), buffersize, params);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/SocketOutputBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */