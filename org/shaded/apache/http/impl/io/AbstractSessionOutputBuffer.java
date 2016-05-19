/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.shaded.apache.http.io.HttpTransportMetrics;
/*     */ import org.shaded.apache.http.io.SessionOutputBuffer;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ import org.shaded.apache.http.params.HttpProtocolParams;
/*     */ import org.shaded.apache.http.util.ByteArrayBuffer;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSessionOutputBuffer
/*     */   implements SessionOutputBuffer
/*     */ {
/*  58 */   private static final byte[] CRLF = { 13, 10 };
/*     */   
/*     */   private static final int MAX_CHUNK = 256;
/*     */   
/*     */   private OutputStream outstream;
/*     */   
/*     */   private ByteArrayBuffer buffer;
/*  65 */   private String charset = "US-ASCII";
/*  66 */   private boolean ascii = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private HttpTransportMetricsImpl metrics;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void init(OutputStream outstream, int buffersize, HttpParams params)
/*     */   {
/*  86 */     if (outstream == null) {
/*  87 */       throw new IllegalArgumentException("Input stream may not be null");
/*     */     }
/*  89 */     if (buffersize <= 0) {
/*  90 */       throw new IllegalArgumentException("Buffer size may not be negative or zero");
/*     */     }
/*  92 */     if (params == null) {
/*  93 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  95 */     this.outstream = outstream;
/*  96 */     this.buffer = new ByteArrayBuffer(buffersize);
/*  97 */     this.charset = HttpProtocolParams.getHttpElementCharset(params);
/*  98 */     this.ascii = ((this.charset.equalsIgnoreCase("US-ASCII")) || (this.charset.equalsIgnoreCase("ASCII")));
/*     */     
/* 100 */     this.metrics = new HttpTransportMetricsImpl();
/*     */   }
/*     */   
/*     */   protected void flushBuffer() throws IOException {
/* 104 */     int len = this.buffer.length();
/* 105 */     if (len > 0) {
/* 106 */       this.outstream.write(this.buffer.buffer(), 0, len);
/* 107 */       this.buffer.clear();
/* 108 */       this.metrics.incrementBytesTransferred(len);
/*     */     }
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 113 */     flushBuffer();
/* 114 */     this.outstream.flush();
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 118 */     if (b == null) {
/* 119 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 124 */     if ((len > 256) || (len > this.buffer.capacity()))
/*     */     {
/* 126 */       flushBuffer();
/*     */       
/* 128 */       this.outstream.write(b, off, len);
/* 129 */       this.metrics.incrementBytesTransferred(len);
/*     */     }
/*     */     else {
/* 132 */       int freecapacity = this.buffer.capacity() - this.buffer.length();
/* 133 */       if (len > freecapacity)
/*     */       {
/* 135 */         flushBuffer();
/*     */       }
/*     */       
/* 138 */       this.buffer.append(b, off, len);
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 143 */     if (b == null) {
/* 144 */       return;
/*     */     }
/* 146 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/* 150 */     if (this.buffer.isFull()) {
/* 151 */       flushBuffer();
/*     */     }
/* 153 */     this.buffer.append(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeLine(String s)
/*     */     throws IOException
/*     */   {
/* 166 */     if (s == null) {
/* 167 */       return;
/*     */     }
/* 169 */     if (s.length() > 0) {
/* 170 */       write(s.getBytes(this.charset));
/*     */     }
/* 172 */     write(CRLF);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeLine(CharArrayBuffer s)
/*     */     throws IOException
/*     */   {
/* 185 */     if (s == null) {
/* 186 */       return;
/*     */     }
/* 188 */     if (this.ascii) {
/* 189 */       int off = 0;
/* 190 */       int remaining = s.length();
/* 191 */       while (remaining > 0) {
/* 192 */         int chunk = this.buffer.capacity() - this.buffer.length();
/* 193 */         chunk = Math.min(chunk, remaining);
/* 194 */         if (chunk > 0) {
/* 195 */           this.buffer.append(s, off, chunk);
/*     */         }
/* 197 */         if (this.buffer.isFull()) {
/* 198 */           flushBuffer();
/*     */         }
/* 200 */         off += chunk;
/* 201 */         remaining -= chunk;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 206 */       byte[] tmp = s.toString().getBytes(this.charset);
/* 207 */       write(tmp);
/*     */     }
/* 209 */     write(CRLF);
/*     */   }
/*     */   
/*     */   public HttpTransportMetrics getMetrics() {
/* 213 */     return this.metrics;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/AbstractSessionOutputBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */