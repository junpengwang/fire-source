/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import org.shaded.apache.http.FormattedHeader;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.StatusLine;
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
/*     */ public class BasicLineFormatter
/*     */   implements LineFormatter
/*     */ {
/*  66 */   public static final BasicLineFormatter DEFAULT = new BasicLineFormatter();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CharArrayBuffer initBuffer(CharArrayBuffer buffer)
/*     */   {
/*  82 */     if (buffer != null) {
/*  83 */       buffer.clear();
/*     */     } else {
/*  85 */       buffer = new CharArrayBuffer(64);
/*     */     }
/*  87 */     return buffer;
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
/*     */   public static final String formatProtocolVersion(ProtocolVersion version, LineFormatter formatter)
/*     */   {
/* 104 */     if (formatter == null)
/* 105 */       formatter = DEFAULT;
/* 106 */     return formatter.appendProtocolVersion(null, version).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer appendProtocolVersion(CharArrayBuffer buffer, ProtocolVersion version)
/*     */   {
/* 113 */     if (version == null) {
/* 114 */       throw new IllegalArgumentException("Protocol version may not be null");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 119 */     CharArrayBuffer result = buffer;
/* 120 */     int len = estimateProtocolVersionLen(version);
/* 121 */     if (result == null) {
/* 122 */       result = new CharArrayBuffer(len);
/*     */     } else {
/* 124 */       result.ensureCapacity(len);
/*     */     }
/*     */     
/* 127 */     result.append(version.getProtocol());
/* 128 */     result.append('/');
/* 129 */     result.append(Integer.toString(version.getMajor()));
/* 130 */     result.append('.');
/* 131 */     result.append(Integer.toString(version.getMinor()));
/*     */     
/* 133 */     return result;
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
/*     */   protected int estimateProtocolVersionLen(ProtocolVersion version)
/*     */   {
/* 147 */     return version.getProtocol().length() + 4;
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
/*     */   public static final String formatRequestLine(RequestLine reqline, LineFormatter formatter)
/*     */   {
/* 163 */     if (formatter == null)
/* 164 */       formatter = DEFAULT;
/* 165 */     return formatter.formatRequestLine(null, reqline).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer formatRequestLine(CharArrayBuffer buffer, RequestLine reqline)
/*     */   {
/* 172 */     if (reqline == null) {
/* 173 */       throw new IllegalArgumentException("Request line may not be null");
/*     */     }
/*     */     
/*     */ 
/* 177 */     CharArrayBuffer result = initBuffer(buffer);
/* 178 */     doFormatRequestLine(result, reqline);
/*     */     
/* 180 */     return result;
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
/*     */   protected void doFormatRequestLine(CharArrayBuffer buffer, RequestLine reqline)
/*     */   {
/* 194 */     String method = reqline.getMethod();
/* 195 */     String uri = reqline.getUri();
/*     */     
/*     */ 
/* 198 */     int len = method.length() + 1 + uri.length() + 1 + estimateProtocolVersionLen(reqline.getProtocolVersion());
/*     */     
/* 200 */     buffer.ensureCapacity(len);
/*     */     
/* 202 */     buffer.append(method);
/* 203 */     buffer.append(' ');
/* 204 */     buffer.append(uri);
/* 205 */     buffer.append(' ');
/* 206 */     appendProtocolVersion(buffer, reqline.getProtocolVersion());
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
/*     */   public static final String formatStatusLine(StatusLine statline, LineFormatter formatter)
/*     */   {
/* 223 */     if (formatter == null)
/* 224 */       formatter = DEFAULT;
/* 225 */     return formatter.formatStatusLine(null, statline).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer formatStatusLine(CharArrayBuffer buffer, StatusLine statline)
/*     */   {
/* 232 */     if (statline == null) {
/* 233 */       throw new IllegalArgumentException("Status line may not be null");
/*     */     }
/*     */     
/*     */ 
/* 237 */     CharArrayBuffer result = initBuffer(buffer);
/* 238 */     doFormatStatusLine(result, statline);
/*     */     
/* 240 */     return result;
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
/*     */   protected void doFormatStatusLine(CharArrayBuffer buffer, StatusLine statline)
/*     */   {
/* 255 */     int len = estimateProtocolVersionLen(statline.getProtocolVersion()) + 1 + 3 + 1;
/*     */     
/* 257 */     String reason = statline.getReasonPhrase();
/* 258 */     if (reason != null) {
/* 259 */       len += reason.length();
/*     */     }
/* 261 */     buffer.ensureCapacity(len);
/*     */     
/* 263 */     appendProtocolVersion(buffer, statline.getProtocolVersion());
/* 264 */     buffer.append(' ');
/* 265 */     buffer.append(Integer.toString(statline.getStatusCode()));
/* 266 */     buffer.append(' ');
/* 267 */     if (reason != null) {
/* 268 */       buffer.append(reason);
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
/*     */   public static final String formatHeader(Header header, LineFormatter formatter)
/*     */   {
/* 285 */     if (formatter == null)
/* 286 */       formatter = DEFAULT;
/* 287 */     return formatter.formatHeader(null, header).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer formatHeader(CharArrayBuffer buffer, Header header)
/*     */   {
/* 294 */     if (header == null) {
/* 295 */       throw new IllegalArgumentException("Header may not be null");
/*     */     }
/*     */     
/* 298 */     CharArrayBuffer result = null;
/*     */     
/* 300 */     if ((header instanceof FormattedHeader))
/*     */     {
/* 302 */       result = ((FormattedHeader)header).getBuffer();
/*     */     } else {
/* 304 */       result = initBuffer(buffer);
/* 305 */       doFormatHeader(result, header);
/*     */     }
/* 307 */     return result;
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
/*     */   protected void doFormatHeader(CharArrayBuffer buffer, Header header)
/*     */   {
/* 322 */     String name = header.getName();
/* 323 */     String value = header.getValue();
/*     */     
/* 325 */     int len = name.length() + 2;
/* 326 */     if (value != null) {
/* 327 */       len += value.length();
/*     */     }
/* 329 */     buffer.ensureCapacity(len);
/*     */     
/* 331 */     buffer.append(name);
/* 332 */     buffer.append(": ");
/* 333 */     if (value != null) {
/* 334 */       buffer.append(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicLineFormatter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */