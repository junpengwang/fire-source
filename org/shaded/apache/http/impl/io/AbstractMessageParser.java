/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.ParseException;
/*     */ import org.shaded.apache.http.ProtocolException;
/*     */ import org.shaded.apache.http.io.HttpMessageParser;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
/*     */ import org.shaded.apache.http.message.BasicLineParser;
/*     */ import org.shaded.apache.http.message.LineParser;
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
/*     */ public abstract class AbstractMessageParser
/*     */   implements HttpMessageParser
/*     */ {
/*     */   private final SessionInputBuffer sessionBuffer;
/*     */   private final int maxHeaderCount;
/*     */   private final int maxLineLen;
/*     */   protected final LineParser lineParser;
/*     */   
/*     */   public AbstractMessageParser(SessionInputBuffer buffer, LineParser parser, HttpParams params)
/*     */   {
/*  90 */     if (buffer == null) {
/*  91 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*     */     }
/*  93 */     if (params == null) {
/*  94 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  96 */     this.sessionBuffer = buffer;
/*  97 */     this.maxHeaderCount = params.getIntParameter("http.connection.max-header-count", -1);
/*     */     
/*  99 */     this.maxLineLen = params.getIntParameter("http.connection.max-line-length", -1);
/*     */     
/* 101 */     this.lineParser = (parser != null ? parser : BasicLineParser.DEFAULT);
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
/*     */   public static Header[] parseHeaders(SessionInputBuffer inbuffer, int maxHeaderCount, int maxLineLen, LineParser parser)
/*     */     throws HttpException, IOException
/*     */   {
/* 128 */     if (inbuffer == null) {
/* 129 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*     */     }
/* 131 */     if (parser == null) {
/* 132 */       parser = BasicLineParser.DEFAULT;
/*     */     }
/* 134 */     ArrayList headerLines = new ArrayList();
/*     */     
/* 136 */     CharArrayBuffer current = null;
/* 137 */     CharArrayBuffer previous = null;
/*     */     for (;;) {
/* 139 */       if (current == null) {
/* 140 */         current = new CharArrayBuffer(64);
/*     */       } else {
/* 142 */         current.clear();
/*     */       }
/* 144 */       int l = inbuffer.readLine(current);
/* 145 */       if ((l == -1) || (current.length() < 1)) {
/*     */         break;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 152 */       if (((current.charAt(0) == ' ') || (current.charAt(0) == '\t')) && (previous != null))
/*     */       {
/*     */ 
/* 155 */         int i = 0;
/* 156 */         while (i < current.length()) {
/* 157 */           char ch = current.charAt(i);
/* 158 */           if ((ch != ' ') && (ch != '\t')) {
/*     */             break;
/*     */           }
/* 161 */           i++;
/*     */         }
/* 163 */         if ((maxLineLen > 0) && (previous.length() + 1 + current.length() - i > maxLineLen))
/*     */         {
/* 165 */           throw new IOException("Maximum line length limit exceeded");
/*     */         }
/* 167 */         previous.append(' ');
/* 168 */         previous.append(current, i, current.length() - i);
/*     */       } else {
/* 170 */         headerLines.add(current);
/* 171 */         previous = current;
/* 172 */         current = null;
/*     */       }
/* 174 */       if ((maxHeaderCount > 0) && (headerLines.size() >= maxHeaderCount)) {
/* 175 */         throw new IOException("Maximum header count exceeded");
/*     */       }
/*     */     }
/* 178 */     Header[] headers = new Header[headerLines.size()];
/* 179 */     for (int i = 0; i < headerLines.size(); i++) {
/* 180 */       CharArrayBuffer buffer = (CharArrayBuffer)headerLines.get(i);
/*     */       try {
/* 182 */         headers[i] = parser.parseHeader(buffer);
/*     */       } catch (ParseException ex) {
/* 184 */         throw new ProtocolException(ex.getMessage());
/*     */       }
/*     */     }
/* 187 */     return headers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract HttpMessage parseHead(SessionInputBuffer paramSessionInputBuffer)
/*     */     throws IOException, HttpException, ParseException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpMessage parse()
/*     */     throws IOException, HttpException
/*     */   {
/* 208 */     HttpMessage message = null;
/*     */     try {
/* 210 */       message = parseHead(this.sessionBuffer);
/*     */     } catch (ParseException px) {
/* 212 */       throw new ProtocolException(px.getMessage(), px);
/*     */     }
/* 214 */     Header[] headers = parseHeaders(this.sessionBuffer, this.maxHeaderCount, this.maxLineLen, this.lineParser);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 219 */     message.setHeaders(headers);
/* 220 */     return message;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/AbstractMessageParser.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */