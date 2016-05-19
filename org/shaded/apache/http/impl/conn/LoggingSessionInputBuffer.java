/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.io.HttpTransportMetrics;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
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
/*     */ @Immutable
/*     */ public class LoggingSessionInputBuffer
/*     */   implements SessionInputBuffer
/*     */ {
/*     */   private final SessionInputBuffer in;
/*     */   private final Wire wire;
/*     */   
/*     */   public LoggingSessionInputBuffer(SessionInputBuffer in, Wire wire)
/*     */   {
/*  59 */     this.in = in;
/*  60 */     this.wire = wire;
/*     */   }
/*     */   
/*     */   public boolean isDataAvailable(int timeout) throws IOException {
/*  64 */     return this.in.isDataAvailable(timeout);
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/*  68 */     int l = this.in.read(b, off, len);
/*  69 */     if ((this.wire.enabled()) && (l > 0)) {
/*  70 */       this.wire.input(b, off, l);
/*     */     }
/*  72 */     return l;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  76 */     int l = this.in.read();
/*  77 */     if ((this.wire.enabled()) && (l != -1)) {
/*  78 */       this.wire.input(l);
/*     */     }
/*  80 */     return l;
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/*  84 */     int l = this.in.read(b);
/*  85 */     if ((this.wire.enabled()) && (l > 0)) {
/*  86 */       this.wire.input(b, 0, l);
/*     */     }
/*  88 */     return l;
/*     */   }
/*     */   
/*     */   public String readLine() throws IOException {
/*  92 */     String s = this.in.readLine();
/*  93 */     if ((this.wire.enabled()) && (s != null)) {
/*  94 */       this.wire.input(s + "[EOL]");
/*     */     }
/*  96 */     return s;
/*     */   }
/*     */   
/*     */   public int readLine(CharArrayBuffer buffer) throws IOException {
/* 100 */     int l = this.in.readLine(buffer);
/* 101 */     if ((this.wire.enabled()) && (l >= 0)) {
/* 102 */       int pos = buffer.length() - l;
/* 103 */       String s = new String(buffer.buffer(), pos, l);
/* 104 */       this.wire.input(s + "[EOL]");
/*     */     }
/* 106 */     return l;
/*     */   }
/*     */   
/*     */   public HttpTransportMetrics getMetrics() {
/* 110 */     return this.in.getMetrics();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/LoggingSessionInputBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */