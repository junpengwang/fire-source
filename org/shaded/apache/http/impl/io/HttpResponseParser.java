/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.HttpResponseFactory;
/*     */ import org.shaded.apache.http.NoHttpResponseException;
/*     */ import org.shaded.apache.http.ParseException;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
/*     */ import org.shaded.apache.http.message.LineParser;
/*     */ import org.shaded.apache.http.message.ParserCursor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpResponseParser
/*     */   extends AbstractMessageParser
/*     */ {
/*     */   private final HttpResponseFactory responseFactory;
/*     */   private final CharArrayBuffer lineBuf;
/*     */   
/*     */   public HttpResponseParser(SessionInputBuffer buffer, LineParser parser, HttpResponseFactory responseFactory, HttpParams params)
/*     */   {
/*  89 */     super(buffer, parser, params);
/*  90 */     if (responseFactory == null) {
/*  91 */       throw new IllegalArgumentException("Response factory may not be null");
/*     */     }
/*  93 */     this.responseFactory = responseFactory;
/*  94 */     this.lineBuf = new CharArrayBuffer(128);
/*     */   }
/*     */   
/*     */ 
/*     */   protected HttpMessage parseHead(SessionInputBuffer sessionBuffer)
/*     */     throws IOException, HttpException, ParseException
/*     */   {
/* 101 */     this.lineBuf.clear();
/* 102 */     int i = sessionBuffer.readLine(this.lineBuf);
/* 103 */     if (i == -1) {
/* 104 */       throw new NoHttpResponseException("The target server failed to respond");
/*     */     }
/*     */     
/* 107 */     ParserCursor cursor = new ParserCursor(0, this.lineBuf.length());
/* 108 */     StatusLine statusline = this.lineParser.parseStatusLine(this.lineBuf, cursor);
/* 109 */     return this.responseFactory.newHttpResponse(statusline, null);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/HttpResponseParser.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */