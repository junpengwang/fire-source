/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.io.HttpMessageWriter;
/*     */ import org.shaded.apache.http.io.SessionOutputBuffer;
/*     */ import org.shaded.apache.http.message.BasicLineFormatter;
/*     */ import org.shaded.apache.http.message.LineFormatter;
/*     */ import org.shaded.apache.http.params.HttpParams;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMessageWriter
/*     */   implements HttpMessageWriter
/*     */ {
/*     */   protected final SessionOutputBuffer sessionBuffer;
/*     */   protected final CharArrayBuffer lineBuf;
/*     */   protected final LineFormatter lineFormatter;
/*     */   
/*     */   public AbstractMessageWriter(SessionOutputBuffer buffer, LineFormatter formatter, HttpParams params)
/*     */   {
/*  70 */     if (buffer == null) {
/*  71 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*     */     }
/*  73 */     this.sessionBuffer = buffer;
/*  74 */     this.lineBuf = new CharArrayBuffer(128);
/*  75 */     this.lineFormatter = (formatter != null ? formatter : BasicLineFormatter.DEFAULT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void writeHeadLine(HttpMessage paramHttpMessage)
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(HttpMessage message)
/*     */     throws IOException, HttpException
/*     */   {
/*  90 */     if (message == null) {
/*  91 */       throw new IllegalArgumentException("HTTP message may not be null");
/*     */     }
/*  93 */     writeHeadLine(message);
/*  94 */     for (Iterator it = message.headerIterator(); it.hasNext();) {
/*  95 */       Header header = (Header)it.next();
/*  96 */       this.sessionBuffer.writeLine(this.lineFormatter.formatHeader(this.lineBuf, header));
/*     */     }
/*     */     
/*  99 */     this.lineBuf.clear();
/* 100 */     this.sessionBuffer.writeLine(this.lineBuf);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/AbstractMessageWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */