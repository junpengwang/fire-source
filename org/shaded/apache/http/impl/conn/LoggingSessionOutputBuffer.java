/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.io.HttpTransportMetrics;
/*     */ import org.shaded.apache.http.io.SessionOutputBuffer;
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
/*     */ public class LoggingSessionOutputBuffer
/*     */   implements SessionOutputBuffer
/*     */ {
/*     */   private final SessionOutputBuffer out;
/*     */   private final Wire wire;
/*     */   
/*     */   public LoggingSessionOutputBuffer(SessionOutputBuffer out, Wire wire)
/*     */   {
/*  59 */     this.out = out;
/*  60 */     this.wire = wire;
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  64 */     this.out.write(b, off, len);
/*  65 */     if (this.wire.enabled()) {
/*  66 */       this.wire.output(b, off, len);
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/*  71 */     this.out.write(b);
/*  72 */     if (this.wire.enabled()) {
/*  73 */       this.wire.output(b);
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  78 */     this.out.write(b);
/*  79 */     if (this.wire.enabled()) {
/*  80 */       this.wire.output(b);
/*     */     }
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/*  85 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public void writeLine(CharArrayBuffer buffer) throws IOException {
/*  89 */     this.out.writeLine(buffer);
/*  90 */     if (this.wire.enabled()) {
/*  91 */       String s = new String(buffer.buffer(), 0, buffer.length());
/*  92 */       this.wire.output(s + "[EOL]");
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeLine(String s) throws IOException {
/*  97 */     this.out.writeLine(s);
/*  98 */     if (this.wire.enabled()) {
/*  99 */       this.wire.output(s + "[EOL]");
/*     */     }
/*     */   }
/*     */   
/*     */   public HttpTransportMetrics getMetrics() {
/* 104 */     return this.out.getMetrics();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/LoggingSessionOutputBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */