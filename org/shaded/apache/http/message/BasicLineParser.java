/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpVersion;
/*     */ import org.shaded.apache.http.ParseException;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.protocol.HTTP;
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
/*     */ public class BasicLineParser
/*     */   implements LineParser
/*     */ {
/*  69 */   public static final BasicLineParser DEFAULT = new BasicLineParser();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final ProtocolVersion protocol;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicLineParser(ProtocolVersion proto)
/*     */   {
/*  87 */     if (proto == null) {
/*  88 */       proto = HttpVersion.HTTP_1_1;
/*     */     }
/*  90 */     this.protocol = proto;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicLineParser()
/*     */   {
/*  98 */     this(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final ProtocolVersion parseProtocolVersion(String value, LineParser parser)
/*     */     throws ParseException
/*     */   {
/* 107 */     if (value == null) {
/* 108 */       throw new IllegalArgumentException("Value to parse may not be null.");
/*     */     }
/*     */     
/*     */ 
/* 112 */     if (parser == null) {
/* 113 */       parser = DEFAULT;
/*     */     }
/* 115 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 116 */     buffer.append(value);
/* 117 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 118 */     return parser.parseProtocolVersion(buffer, cursor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ProtocolVersion parseProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor)
/*     */     throws ParseException
/*     */   {
/* 127 */     if (buffer == null) {
/* 128 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 130 */     if (cursor == null) {
/* 131 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/*     */     
/* 134 */     String protoname = this.protocol.getProtocol();
/* 135 */     int protolength = protoname.length();
/*     */     
/* 137 */     int indexFrom = cursor.getPos();
/* 138 */     int indexTo = cursor.getUpperBound();
/*     */     
/* 140 */     skipWhitespace(buffer, cursor);
/*     */     
/* 142 */     int i = cursor.getPos();
/*     */     
/*     */ 
/* 145 */     if (i + protolength + 4 > indexTo) {
/* 146 */       throw new ParseException("Not a valid protocol version: " + buffer.substring(indexFrom, indexTo));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 152 */     boolean ok = true;
/* 153 */     for (int j = 0; (ok) && (j < protolength); j++) {
/* 154 */       ok = buffer.charAt(i + j) == protoname.charAt(j);
/*     */     }
/* 156 */     if (ok) {
/* 157 */       ok = buffer.charAt(i + protolength) == '/';
/*     */     }
/* 159 */     if (!ok) {
/* 160 */       throw new ParseException("Not a valid protocol version: " + buffer.substring(indexFrom, indexTo));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 165 */     i += protolength + 1;
/*     */     
/* 167 */     int period = buffer.indexOf(46, i, indexTo);
/* 168 */     if (period == -1) {
/* 169 */       throw new ParseException("Invalid protocol version number: " + buffer.substring(indexFrom, indexTo));
/*     */     }
/*     */     
/*     */     int major;
/*     */     try
/*     */     {
/* 175 */       major = Integer.parseInt(buffer.substringTrimmed(i, period));
/*     */     } catch (NumberFormatException e) {
/* 177 */       throw new ParseException("Invalid protocol major version number: " + buffer.substring(indexFrom, indexTo));
/*     */     }
/*     */     
/*     */ 
/* 181 */     i = period + 1;
/*     */     
/* 183 */     int blank = buffer.indexOf(32, i, indexTo);
/* 184 */     if (blank == -1) {
/* 185 */       blank = indexTo;
/*     */     }
/*     */     int minor;
/*     */     try {
/* 189 */       minor = Integer.parseInt(buffer.substringTrimmed(i, blank));
/*     */     } catch (NumberFormatException e) {
/* 191 */       throw new ParseException("Invalid protocol minor version number: " + buffer.substring(indexFrom, indexTo));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 196 */     cursor.updatePos(blank);
/*     */     
/* 198 */     return createProtocolVersion(major, minor);
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
/*     */   protected ProtocolVersion createProtocolVersion(int major, int minor)
/*     */   {
/* 213 */     return this.protocol.forVersion(major, minor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor)
/*     */   {
/* 222 */     if (buffer == null) {
/* 223 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 225 */     if (cursor == null) {
/* 226 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/* 228 */     int index = cursor.getPos();
/*     */     
/* 230 */     String protoname = this.protocol.getProtocol();
/* 231 */     int protolength = protoname.length();
/*     */     
/* 233 */     if (buffer.length() < protolength + 4) {
/* 234 */       return false;
/*     */     }
/* 236 */     if (index < 0)
/*     */     {
/*     */ 
/* 239 */       index = buffer.length() - 4 - protolength;
/* 240 */     } else if (index == 0)
/*     */     {
/* 242 */       while ((index < buffer.length()) && (HTTP.isWhitespace(buffer.charAt(index))))
/*     */       {
/* 244 */         index++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 249 */     if (index + protolength + 4 > buffer.length()) {
/* 250 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 254 */     boolean ok = true;
/* 255 */     for (int j = 0; (ok) && (j < protolength); j++) {
/* 256 */       ok = buffer.charAt(index + j) == protoname.charAt(j);
/*     */     }
/* 258 */     if (ok) {
/* 259 */       ok = buffer.charAt(index + protolength) == '/';
/*     */     }
/*     */     
/* 262 */     return ok;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final RequestLine parseRequestLine(String value, LineParser parser)
/*     */     throws ParseException
/*     */   {
/* 272 */     if (value == null) {
/* 273 */       throw new IllegalArgumentException("Value to parse may not be null.");
/*     */     }
/*     */     
/*     */ 
/* 277 */     if (parser == null) {
/* 278 */       parser = DEFAULT;
/*     */     }
/* 280 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 281 */     buffer.append(value);
/* 282 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 283 */     return parser.parseRequestLine(buffer, cursor);
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
/*     */   public RequestLine parseRequestLine(CharArrayBuffer buffer, ParserCursor cursor)
/*     */     throws ParseException
/*     */   {
/* 300 */     if (buffer == null) {
/* 301 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 303 */     if (cursor == null) {
/* 304 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/*     */     
/* 307 */     int indexFrom = cursor.getPos();
/* 308 */     int indexTo = cursor.getUpperBound();
/*     */     try
/*     */     {
/* 311 */       skipWhitespace(buffer, cursor);
/* 312 */       int i = cursor.getPos();
/*     */       
/* 314 */       int blank = buffer.indexOf(32, i, indexTo);
/* 315 */       if (blank < 0) {
/* 316 */         throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
/*     */       }
/*     */       
/* 319 */       String method = buffer.substringTrimmed(i, blank);
/* 320 */       cursor.updatePos(blank);
/*     */       
/* 322 */       skipWhitespace(buffer, cursor);
/* 323 */       i = cursor.getPos();
/*     */       
/* 325 */       blank = buffer.indexOf(32, i, indexTo);
/* 326 */       if (blank < 0) {
/* 327 */         throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
/*     */       }
/*     */       
/* 330 */       String uri = buffer.substringTrimmed(i, blank);
/* 331 */       cursor.updatePos(blank);
/*     */       
/* 333 */       ProtocolVersion ver = parseProtocolVersion(buffer, cursor);
/*     */       
/* 335 */       skipWhitespace(buffer, cursor);
/* 336 */       if (!cursor.atEnd()) {
/* 337 */         throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
/*     */       }
/*     */       
/*     */ 
/* 341 */       return createRequestLine(method, uri, ver);
/*     */     } catch (IndexOutOfBoundsException e) {
/* 343 */       throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
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
/*     */ 
/*     */   protected RequestLine createRequestLine(String method, String uri, ProtocolVersion ver)
/*     */   {
/* 362 */     return new BasicRequestLine(method, uri, ver);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final StatusLine parseStatusLine(String value, LineParser parser)
/*     */     throws ParseException
/*     */   {
/* 372 */     if (value == null) {
/* 373 */       throw new IllegalArgumentException("Value to parse may not be null.");
/*     */     }
/*     */     
/*     */ 
/* 377 */     if (parser == null) {
/* 378 */       parser = DEFAULT;
/*     */     }
/* 380 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 381 */     buffer.append(value);
/* 382 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 383 */     return parser.parseStatusLine(buffer, cursor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StatusLine parseStatusLine(CharArrayBuffer buffer, ParserCursor cursor)
/*     */     throws ParseException
/*     */   {
/* 392 */     if (buffer == null) {
/* 393 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 395 */     if (cursor == null) {
/* 396 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/*     */     
/* 399 */     int indexFrom = cursor.getPos();
/* 400 */     int indexTo = cursor.getUpperBound();
/*     */     
/*     */     try
/*     */     {
/* 404 */       ProtocolVersion ver = parseProtocolVersion(buffer, cursor);
/*     */       
/*     */ 
/* 407 */       skipWhitespace(buffer, cursor);
/* 408 */       int i = cursor.getPos();
/*     */       
/* 410 */       int blank = buffer.indexOf(32, i, indexTo);
/* 411 */       if (blank < 0) {
/* 412 */         blank = indexTo;
/*     */       }
/* 414 */       int statusCode = 0;
/*     */       try {
/* 416 */         statusCode = Integer.parseInt(buffer.substringTrimmed(i, blank));
/*     */       }
/*     */       catch (NumberFormatException e) {
/* 419 */         throw new ParseException("Unable to parse status code from status line: " + buffer.substring(indexFrom, indexTo));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 424 */       i = blank;
/* 425 */       String reasonPhrase = null;
/* 426 */       if (i < indexTo) {
/* 427 */         reasonPhrase = buffer.substringTrimmed(i, indexTo);
/*     */       } else {
/* 429 */         reasonPhrase = "";
/*     */       }
/* 431 */       return createStatusLine(ver, statusCode, reasonPhrase);
/*     */     }
/*     */     catch (IndexOutOfBoundsException e) {
/* 434 */       throw new ParseException("Invalid status line: " + buffer.substring(indexFrom, indexTo));
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
/*     */ 
/*     */   protected StatusLine createStatusLine(ProtocolVersion ver, int status, String reason)
/*     */   {
/* 453 */     return new BasicStatusLine(ver, status, reason);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final Header parseHeader(String value, LineParser parser)
/*     */     throws ParseException
/*     */   {
/* 463 */     if (value == null) {
/* 464 */       throw new IllegalArgumentException("Value to parse may not be null");
/*     */     }
/*     */     
/*     */ 
/* 468 */     if (parser == null) {
/* 469 */       parser = DEFAULT;
/*     */     }
/* 471 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 472 */     buffer.append(value);
/* 473 */     return parser.parseHeader(buffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Header parseHeader(CharArrayBuffer buffer)
/*     */     throws ParseException
/*     */   {
/* 482 */     return new BufferedHeader(buffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void skipWhitespace(CharArrayBuffer buffer, ParserCursor cursor)
/*     */   {
/* 490 */     int pos = cursor.getPos();
/* 491 */     int indexTo = cursor.getUpperBound();
/* 492 */     while ((pos < indexTo) && (HTTP.isWhitespace(buffer.charAt(pos))))
/*     */     {
/* 494 */       pos++;
/*     */     }
/* 496 */     cursor.updatePos(pos);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicLineParser.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */