/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.MalformedChunkCodingException;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ import org.shaded.apache.http.util.ExceptionUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private SessionInputBuffer in;
/*     */   private final CharArrayBuffer buffer;
/*     */   private int chunkSize;
/*     */   private int pos;
/*  75 */   private boolean bof = true;
/*     */   
/*     */ 
/*  78 */   private boolean eof = false;
/*     */   
/*     */ 
/*  81 */   private boolean closed = false;
/*     */   
/*  83 */   private Header[] footers = new Header[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedInputStream(SessionInputBuffer in)
/*     */   {
/*  92 */     if (in == null) {
/*  93 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*     */     }
/*  95 */     this.in = in;
/*  96 */     this.pos = 0;
/*  97 */     this.buffer = new CharArrayBuffer(16);
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 113 */     if (this.closed) {
/* 114 */       throw new IOException("Attempted read from closed stream.");
/*     */     }
/* 116 */     if (this.eof) {
/* 117 */       return -1;
/*     */     }
/* 119 */     if (this.pos >= this.chunkSize) {
/* 120 */       nextChunk();
/* 121 */       if (this.eof) {
/* 122 */         return -1;
/*     */       }
/*     */     }
/* 125 */     int b = this.in.read();
/* 126 */     if (b != -1) {
/* 127 */       this.pos += 1;
/*     */     }
/* 129 */     return b;
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
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 144 */     if (this.closed) {
/* 145 */       throw new IOException("Attempted read from closed stream.");
/*     */     }
/*     */     
/* 148 */     if (this.eof) {
/* 149 */       return -1;
/*     */     }
/* 151 */     if (this.pos >= this.chunkSize) {
/* 152 */       nextChunk();
/* 153 */       if (this.eof) {
/* 154 */         return -1;
/*     */       }
/*     */     }
/* 157 */     len = Math.min(len, this.chunkSize - this.pos);
/* 158 */     int bytesRead = this.in.read(b, off, len);
/* 159 */     if (bytesRead != -1) {
/* 160 */       this.pos += bytesRead;
/* 161 */       return bytesRead;
/*     */     }
/* 163 */     throw new MalformedChunkCodingException("Truncated chunk");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] b)
/*     */     throws IOException
/*     */   {
/* 175 */     return read(b, 0, b.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void nextChunk()
/*     */     throws IOException
/*     */   {
/* 183 */     this.chunkSize = getChunkSize();
/* 184 */     if (this.chunkSize < 0) {
/* 185 */       throw new MalformedChunkCodingException("Negative chunk size");
/*     */     }
/* 187 */     this.bof = false;
/* 188 */     this.pos = 0;
/* 189 */     if (this.chunkSize == 0) {
/* 190 */       this.eof = true;
/* 191 */       parseTrailerHeaders();
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
/*     */   private int getChunkSize()
/*     */     throws IOException
/*     */   {
/* 210 */     if (!this.bof) {
/* 211 */       int cr = this.in.read();
/* 212 */       int lf = this.in.read();
/* 213 */       if ((cr != 13) || (lf != 10)) {
/* 214 */         throw new MalformedChunkCodingException("CRLF expected at end of chunk");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 219 */     this.buffer.clear();
/* 220 */     int i = this.in.readLine(this.buffer);
/* 221 */     if (i == -1) {
/* 222 */       return 0;
/*     */     }
/* 224 */     int separator = this.buffer.indexOf(59);
/* 225 */     if (separator < 0) {
/* 226 */       separator = this.buffer.length();
/*     */     }
/*     */     try {
/* 229 */       return Integer.parseInt(this.buffer.substringTrimmed(0, separator), 16);
/*     */     } catch (NumberFormatException e) {
/* 231 */       throw new MalformedChunkCodingException("Bad chunk header");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void parseTrailerHeaders()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 241 */       this.footers = AbstractMessageParser.parseHeaders(this.in, -1, -1, null);
/*     */     }
/*     */     catch (HttpException e) {
/* 244 */       IOException ioe = new MalformedChunkCodingException("Invalid footer: " + e.getMessage());
/*     */       
/* 246 */       ExceptionUtils.initCause(ioe, e);
/* 247 */       throw ioe;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 258 */     if (!this.closed) {
/*     */       try {
/* 260 */         if (!this.eof) {
/* 261 */           exhaustInputStream(this);
/*     */         }
/*     */       } finally {
/* 264 */         this.eof = true;
/* 265 */         this.closed = true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Header[] getFooters() {
/* 271 */     return (Header[])this.footers.clone();
/*     */   }
/*     */   
/*     */   static void exhaustInputStream(InputStream inStream) throws IOException
/*     */   {
/* 276 */     byte[] buffer = new byte['Ѐ'];
/* 277 */     while (inStream.read(buffer) >= 0) {}
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/ChunkedInputStream.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */