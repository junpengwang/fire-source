/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.HttpResponseFactory;
/*     */ import org.shaded.apache.http.NoHttpResponseException;
/*     */ import org.shaded.apache.http.ProtocolException;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.impl.io.AbstractMessageParser;
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
/*     */ @ThreadSafe
/*     */ public class DefaultResponseParser
/*     */   extends AbstractMessageParser
/*     */ {
/*  66 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */   private final HttpResponseFactory responseFactory;
/*     */   
/*     */   private final CharArrayBuffer lineBuf;
/*     */   
/*     */   private final int maxGarbageLines;
/*     */   
/*     */ 
/*     */   public DefaultResponseParser(SessionInputBuffer buffer, LineParser parser, HttpResponseFactory responseFactory, HttpParams params)
/*     */   {
/*  77 */     super(buffer, parser, params);
/*  78 */     if (responseFactory == null) {
/*  79 */       throw new IllegalArgumentException("Response factory may not be null");
/*     */     }
/*     */     
/*  82 */     this.responseFactory = responseFactory;
/*  83 */     this.lineBuf = new CharArrayBuffer(128);
/*  84 */     this.maxGarbageLines = params.getIntParameter("http.connection.max-status-line-garbage", Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HttpMessage parseHead(SessionInputBuffer sessionBuffer)
/*     */     throws IOException, HttpException
/*     */   {
/*  93 */     int count = 0;
/*  94 */     ParserCursor cursor = null;
/*     */     for (;;)
/*     */     {
/*  97 */       this.lineBuf.clear();
/*  98 */       int i = sessionBuffer.readLine(this.lineBuf);
/*  99 */       if ((i == -1) && (count == 0))
/*     */       {
/* 101 */         throw new NoHttpResponseException("The target server failed to respond");
/*     */       }
/* 103 */       cursor = new ParserCursor(0, this.lineBuf.length());
/* 104 */       if (this.lineParser.hasProtocolVersion(this.lineBuf, cursor)) {
/*     */         break;
/*     */       }
/* 107 */       if ((i == -1) || (count >= this.maxGarbageLines))
/*     */       {
/* 109 */         throw new ProtocolException("The server failed to respond with a valid HTTP response");
/*     */       }
/*     */       
/* 112 */       if (this.log.isDebugEnabled()) {
/* 113 */         this.log.debug("Garbage in response: " + this.lineBuf.toString());
/*     */       }
/* 115 */       count++;
/*     */     }
/*     */     
/* 118 */     StatusLine statusline = this.lineParser.parseStatusLine(this.lineBuf, cursor);
/* 119 */     return this.responseFactory.newHttpResponse(statusline, null);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/DefaultResponseParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */