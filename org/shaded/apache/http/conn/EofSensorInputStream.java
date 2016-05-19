/*     */ package org.shaded.apache.http.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
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
/*     */ public class EofSensorInputStream
/*     */   extends InputStream
/*     */   implements ConnectionReleaseTrigger
/*     */ {
/*     */   protected InputStream wrappedStream;
/*     */   private boolean selfClosed;
/*     */   private final EofSensorWatcher eofWatcher;
/*     */   
/*     */   public EofSensorInputStream(InputStream in, EofSensorWatcher watcher)
/*     */   {
/*  89 */     if (in == null) {
/*  90 */       throw new IllegalArgumentException("Wrapped stream may not be null.");
/*     */     }
/*     */     
/*     */ 
/*  94 */     this.wrappedStream = in;
/*  95 */     this.selfClosed = false;
/*  96 */     this.eofWatcher = watcher;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isReadAllowed()
/*     */     throws IOException
/*     */   {
/* 109 */     if (this.selfClosed) {
/* 110 */       throw new IOException("Attempted read on closed stream.");
/*     */     }
/* 112 */     return this.wrappedStream != null;
/*     */   }
/*     */   
/*     */   public int read() throws IOException
/*     */   {
/* 117 */     int l = -1;
/*     */     
/* 119 */     if (isReadAllowed()) {
/*     */       try {
/* 121 */         l = this.wrappedStream.read();
/* 122 */         checkEOF(l);
/*     */       } catch (IOException ex) {
/* 124 */         checkAbort();
/* 125 */         throw ex;
/*     */       }
/*     */     }
/*     */     
/* 129 */     return l;
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException
/*     */   {
/* 134 */     int l = -1;
/*     */     
/* 136 */     if (isReadAllowed()) {
/*     */       try {
/* 138 */         l = this.wrappedStream.read(b, off, len);
/* 139 */         checkEOF(l);
/*     */       } catch (IOException ex) {
/* 141 */         checkAbort();
/* 142 */         throw ex;
/*     */       }
/*     */     }
/*     */     
/* 146 */     return l;
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException
/*     */   {
/* 151 */     int l = -1;
/*     */     
/* 153 */     if (isReadAllowed()) {
/*     */       try {
/* 155 */         l = this.wrappedStream.read(b);
/* 156 */         checkEOF(l);
/*     */       } catch (IOException ex) {
/* 158 */         checkAbort();
/* 159 */         throw ex;
/*     */       }
/*     */     }
/* 162 */     return l;
/*     */   }
/*     */   
/*     */   public int available() throws IOException
/*     */   {
/* 167 */     int a = 0;
/*     */     
/* 169 */     if (isReadAllowed()) {
/*     */       try {
/* 171 */         a = this.wrappedStream.available();
/*     */       }
/*     */       catch (IOException ex) {
/* 174 */         checkAbort();
/* 175 */         throw ex;
/*     */       }
/*     */     }
/*     */     
/* 179 */     return a;
/*     */   }
/*     */   
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 185 */     this.selfClosed = true;
/* 186 */     checkClose();
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
/*     */   protected void checkEOF(int eof)
/*     */     throws IOException
/*     */   {
/* 207 */     if ((this.wrappedStream != null) && (eof < 0)) {
/*     */       try {
/* 209 */         boolean scws = true;
/* 210 */         if (this.eofWatcher != null)
/* 211 */           scws = this.eofWatcher.eofDetected(this.wrappedStream);
/* 212 */         if (scws)
/* 213 */           this.wrappedStream.close();
/*     */       } finally {
/* 215 */         this.wrappedStream = null;
/*     */       }
/*     */     }
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
/*     */   protected void checkClose()
/*     */     throws IOException
/*     */   {
/* 233 */     if (this.wrappedStream != null) {
/*     */       try {
/* 235 */         boolean scws = true;
/* 236 */         if (this.eofWatcher != null)
/* 237 */           scws = this.eofWatcher.streamClosed(this.wrappedStream);
/* 238 */         if (scws)
/* 239 */           this.wrappedStream.close();
/*     */       } finally {
/* 241 */         this.wrappedStream = null;
/*     */       }
/*     */     }
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
/*     */   protected void checkAbort()
/*     */     throws IOException
/*     */   {
/* 261 */     if (this.wrappedStream != null) {
/*     */       try {
/* 263 */         boolean scws = true;
/* 264 */         if (this.eofWatcher != null)
/* 265 */           scws = this.eofWatcher.streamAbort(this.wrappedStream);
/* 266 */         if (scws)
/* 267 */           this.wrappedStream.close();
/*     */       } finally {
/* 269 */         this.wrappedStream = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void releaseConnection()
/*     */     throws IOException
/*     */   {
/* 278 */     close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void abortConnection()
/*     */     throws IOException
/*     */   {
/* 290 */     this.selfClosed = true;
/* 291 */     checkAbort();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/EofSensorInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */