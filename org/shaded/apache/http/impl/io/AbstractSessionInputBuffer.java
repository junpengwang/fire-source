/*     */ package org.shaded.apache.http.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.shaded.apache.http.io.HttpTransportMetrics;
/*     */ import org.shaded.apache.http.io.SessionInputBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSessionInputBuffer
/*     */   implements SessionInputBuffer
/*     */ {
/*     */   private InputStream instream;
/*     */   private byte[] buffer;
/*     */   private int bufferpos;
/*     */   private int bufferlen;
/*  65 */   private ByteArrayBuffer linebuffer = null;
/*     */   
/*  67 */   private String charset = "US-ASCII";
/*  68 */   private boolean ascii = true;
/*  69 */   private int maxLineLen = -1;
/*     */   
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void init(InputStream instream, int buffersize, HttpParams params)
/*     */   {
/*  96 */     if (instream == null) {
/*  97 */       throw new IllegalArgumentException("Input stream may not be null");
/*     */     }
/*  99 */     if (buffersize <= 0) {
/* 100 */       throw new IllegalArgumentException("Buffer size may not be negative or zero");
/*     */     }
/* 102 */     if (params == null) {
/* 103 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 105 */     this.instream = instream;
/* 106 */     this.buffer = new byte[buffersize];
/* 107 */     this.bufferpos = 0;
/* 108 */     this.bufferlen = 0;
/* 109 */     this.linebuffer = new ByteArrayBuffer(buffersize);
/* 110 */     this.charset = HttpProtocolParams.getHttpElementCharset(params);
/* 111 */     this.ascii = ((this.charset.equalsIgnoreCase("US-ASCII")) || (this.charset.equalsIgnoreCase("ASCII")));
/*     */     
/* 113 */     this.maxLineLen = params.getIntParameter("http.connection.max-line-length", -1);
/* 114 */     this.metrics = new HttpTransportMetricsImpl();
/*     */   }
/*     */   
/*     */   protected int fillBuffer() throws IOException
/*     */   {
/* 119 */     if (this.bufferpos > 0) {
/* 120 */       int len = this.bufferlen - this.bufferpos;
/* 121 */       if (len > 0) {
/* 122 */         System.arraycopy(this.buffer, this.bufferpos, this.buffer, 0, len);
/*     */       }
/* 124 */       this.bufferpos = 0;
/* 125 */       this.bufferlen = len;
/*     */     }
/*     */     
/* 128 */     int off = this.bufferlen;
/* 129 */     int len = this.buffer.length - off;
/* 130 */     int l = this.instream.read(this.buffer, off, len);
/* 131 */     if (l == -1) {
/* 132 */       return -1;
/*     */     }
/* 134 */     this.bufferlen = (off + l);
/* 135 */     this.metrics.incrementBytesTransferred(l);
/* 136 */     return l;
/*     */   }
/*     */   
/*     */   protected boolean hasBufferedData()
/*     */   {
/* 141 */     return this.bufferpos < this.bufferlen;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/* 145 */     int noRead = 0;
/* 146 */     while (!hasBufferedData()) {
/* 147 */       noRead = fillBuffer();
/* 148 */       if (noRead == -1) {
/* 149 */         return -1;
/*     */       }
/*     */     }
/* 152 */     return this.buffer[(this.bufferpos++)] & 0xFF;
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 156 */     if (b == null) {
/* 157 */       return 0;
/*     */     }
/* 159 */     int noRead = 0;
/* 160 */     while (!hasBufferedData()) {
/* 161 */       noRead = fillBuffer();
/* 162 */       if (noRead == -1) {
/* 163 */         return -1;
/*     */       }
/*     */     }
/* 166 */     int chunk = this.bufferlen - this.bufferpos;
/* 167 */     if (chunk > len) {
/* 168 */       chunk = len;
/*     */     }
/* 170 */     System.arraycopy(this.buffer, this.bufferpos, b, off, chunk);
/* 171 */     this.bufferpos += chunk;
/* 172 */     return chunk;
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 176 */     if (b == null) {
/* 177 */       return 0;
/*     */     }
/* 179 */     return read(b, 0, b.length);
/*     */   }
/*     */   
/*     */   private int locateLF() {
/* 183 */     for (int i = this.bufferpos; i < this.bufferlen; i++) {
/* 184 */       if (this.buffer[i] == 10) {
/* 185 */         return i;
/*     */       }
/*     */     }
/* 188 */     return -1;
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
/*     */   public int readLine(CharArrayBuffer charbuffer)
/*     */     throws IOException
/*     */   {
/* 207 */     if (charbuffer == null) {
/* 208 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 210 */     this.linebuffer.clear();
/* 211 */     int noRead = 0;
/* 212 */     boolean retry = true;
/* 213 */     while (retry)
/*     */     {
/* 215 */       int i = locateLF();
/* 216 */       if (i != -1)
/*     */       {
/* 218 */         if (this.linebuffer.isEmpty())
/*     */         {
/* 220 */           return lineFromReadBuffer(charbuffer, i);
/*     */         }
/* 222 */         retry = false;
/* 223 */         int len = i + 1 - this.bufferpos;
/* 224 */         this.linebuffer.append(this.buffer, this.bufferpos, len);
/* 225 */         this.bufferpos = (i + 1);
/*     */       }
/*     */       else {
/* 228 */         if (hasBufferedData()) {
/* 229 */           int len = this.bufferlen - this.bufferpos;
/* 230 */           this.linebuffer.append(this.buffer, this.bufferpos, len);
/* 231 */           this.bufferpos = this.bufferlen;
/*     */         }
/* 233 */         noRead = fillBuffer();
/* 234 */         if (noRead == -1) {
/* 235 */           retry = false;
/*     */         }
/*     */       }
/* 238 */       if ((this.maxLineLen > 0) && (this.linebuffer.length() >= this.maxLineLen)) {
/* 239 */         throw new IOException("Maximum line length limit exceeded");
/*     */       }
/*     */     }
/* 242 */     if ((noRead == -1) && (this.linebuffer.isEmpty()))
/*     */     {
/* 244 */       return -1;
/*     */     }
/* 246 */     return lineFromLineBuffer(charbuffer);
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
/*     */   private int lineFromLineBuffer(CharArrayBuffer charbuffer)
/*     */     throws IOException
/*     */   {
/* 265 */     int l = this.linebuffer.length();
/* 266 */     if (l > 0) {
/* 267 */       if (this.linebuffer.byteAt(l - 1) == 10) {
/* 268 */         l--;
/* 269 */         this.linebuffer.setLength(l);
/*     */       }
/*     */       
/* 272 */       if ((l > 0) && 
/* 273 */         (this.linebuffer.byteAt(l - 1) == 13)) {
/* 274 */         l--;
/* 275 */         this.linebuffer.setLength(l);
/*     */       }
/*     */     }
/*     */     
/* 279 */     l = this.linebuffer.length();
/* 280 */     if (this.ascii) {
/* 281 */       charbuffer.append(this.linebuffer, 0, l);
/*     */     }
/*     */     else
/*     */     {
/* 285 */       String s = new String(this.linebuffer.buffer(), 0, l, this.charset);
/* 286 */       charbuffer.append(s);
/*     */     }
/* 288 */     return l;
/*     */   }
/*     */   
/*     */   private int lineFromReadBuffer(CharArrayBuffer charbuffer, int pos) throws IOException
/*     */   {
/* 293 */     int off = this.bufferpos;
/*     */     
/* 295 */     this.bufferpos = (pos + 1);
/* 296 */     if ((pos > 0) && (this.buffer[(pos - 1)] == 13))
/*     */     {
/* 298 */       pos--;
/*     */     }
/* 300 */     int len = pos - off;
/* 301 */     if (this.ascii) {
/* 302 */       charbuffer.append(this.buffer, off, len);
/*     */     }
/*     */     else
/*     */     {
/* 306 */       String s = new String(this.buffer, off, len, this.charset);
/* 307 */       charbuffer.append(s);
/*     */     }
/* 309 */     return len;
/*     */   }
/*     */   
/*     */   public String readLine() throws IOException {
/* 313 */     CharArrayBuffer charbuffer = new CharArrayBuffer(64);
/* 314 */     int l = readLine(charbuffer);
/* 315 */     if (l != -1) {
/* 316 */       return charbuffer.toString();
/*     */     }
/* 318 */     return null;
/*     */   }
/*     */   
/*     */   public HttpTransportMetrics getMetrics()
/*     */   {
/* 323 */     return this.metrics;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/io/AbstractSessionInputBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */