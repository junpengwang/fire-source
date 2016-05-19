/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.NameValuePair;
/*     */ import org.shaded.apache.http.ParseException;
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
/*     */ public class BasicHeaderValueParser
/*     */   implements HeaderValueParser
/*     */ {
/*  64 */   public static final BasicHeaderValueParser DEFAULT = new BasicHeaderValueParser();
/*     */   
/*     */   private static final char PARAM_DELIMITER = ';';
/*     */   private static final char ELEM_DELIMITER = ',';
/*  68 */   private static final char[] ALL_DELIMITERS = { ';', ',' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final HeaderElement[] parseElements(String value, HeaderValueParser parser)
/*     */     throws ParseException
/*     */   {
/*  89 */     if (value == null) {
/*  90 */       throw new IllegalArgumentException("Value to parse may not be null");
/*     */     }
/*     */     
/*     */ 
/*  94 */     if (parser == null) {
/*  95 */       parser = DEFAULT;
/*     */     }
/*  97 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/*  98 */     buffer.append(value);
/*  99 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 100 */     return parser.parseElements(buffer, cursor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HeaderElement[] parseElements(CharArrayBuffer buffer, ParserCursor cursor)
/*     */   {
/* 108 */     if (buffer == null) {
/* 109 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 111 */     if (cursor == null) {
/* 112 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/*     */     
/* 115 */     List elements = new ArrayList();
/* 116 */     while (!cursor.atEnd()) {
/* 117 */       HeaderElement element = parseHeaderElement(buffer, cursor);
/* 118 */       if ((element.getName().length() != 0) || (element.getValue() != null)) {
/* 119 */         elements.add(element);
/*     */       }
/*     */     }
/* 122 */     return (HeaderElement[])elements.toArray(new HeaderElement[elements.size()]);
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
/*     */   public static final HeaderElement parseHeaderElement(String value, HeaderValueParser parser)
/*     */     throws ParseException
/*     */   {
/* 140 */     if (value == null) {
/* 141 */       throw new IllegalArgumentException("Value to parse may not be null");
/*     */     }
/*     */     
/*     */ 
/* 145 */     if (parser == null) {
/* 146 */       parser = DEFAULT;
/*     */     }
/* 148 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 149 */     buffer.append(value);
/* 150 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 151 */     return parser.parseHeaderElement(buffer, cursor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HeaderElement parseHeaderElement(CharArrayBuffer buffer, ParserCursor cursor)
/*     */   {
/* 159 */     if (buffer == null) {
/* 160 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 162 */     if (cursor == null) {
/* 163 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/*     */     
/* 166 */     NameValuePair nvp = parseNameValuePair(buffer, cursor);
/* 167 */     NameValuePair[] params = null;
/* 168 */     if (!cursor.atEnd()) {
/* 169 */       char ch = buffer.charAt(cursor.getPos() - 1);
/* 170 */       if (ch != ',') {
/* 171 */         params = parseParameters(buffer, cursor);
/*     */       }
/*     */     }
/* 174 */     return createHeaderElement(nvp.getName(), nvp.getValue(), params);
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
/*     */   protected HeaderElement createHeaderElement(String name, String value, NameValuePair[] params)
/*     */   {
/* 188 */     return new BasicHeaderElement(name, value, params);
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
/*     */   public static final NameValuePair[] parseParameters(String value, HeaderValueParser parser)
/*     */     throws ParseException
/*     */   {
/* 205 */     if (value == null) {
/* 206 */       throw new IllegalArgumentException("Value to parse may not be null");
/*     */     }
/*     */     
/*     */ 
/* 210 */     if (parser == null) {
/* 211 */       parser = DEFAULT;
/*     */     }
/* 213 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 214 */     buffer.append(value);
/* 215 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 216 */     return parser.parseParameters(buffer, cursor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NameValuePair[] parseParameters(CharArrayBuffer buffer, ParserCursor cursor)
/*     */   {
/* 225 */     if (buffer == null) {
/* 226 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 228 */     if (cursor == null) {
/* 229 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/*     */     
/* 232 */     int pos = cursor.getPos();
/* 233 */     int indexTo = cursor.getUpperBound();
/*     */     
/* 235 */     while (pos < indexTo) {
/* 236 */       char ch = buffer.charAt(pos);
/* 237 */       if (!HTTP.isWhitespace(ch)) break;
/* 238 */       pos++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 243 */     cursor.updatePos(pos);
/* 244 */     if (cursor.atEnd()) {
/* 245 */       return new NameValuePair[0];
/*     */     }
/*     */     
/* 248 */     List params = new ArrayList();
/* 249 */     while (!cursor.atEnd()) {
/* 250 */       NameValuePair param = parseNameValuePair(buffer, cursor);
/* 251 */       params.add(param);
/* 252 */       char ch = buffer.charAt(cursor.getPos() - 1);
/* 253 */       if (ch == ',') {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 258 */     return (NameValuePair[])params.toArray(new NameValuePair[params.size()]);
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
/*     */   public static final NameValuePair parseNameValuePair(String value, HeaderValueParser parser)
/*     */     throws ParseException
/*     */   {
/* 275 */     if (value == null) {
/* 276 */       throw new IllegalArgumentException("Value to parse may not be null");
/*     */     }
/*     */     
/*     */ 
/* 280 */     if (parser == null) {
/* 281 */       parser = DEFAULT;
/*     */     }
/* 283 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 284 */     buffer.append(value);
/* 285 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 286 */     return parser.parseNameValuePair(buffer, cursor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor)
/*     */   {
/* 293 */     return parseNameValuePair(buffer, cursor, ALL_DELIMITERS);
/*     */   }
/*     */   
/*     */   private static boolean isOneOf(char ch, char[] chs) {
/* 297 */     if (chs != null) {
/* 298 */       for (int i = 0; i < chs.length; i++) {
/* 299 */         if (ch == chs[i]) {
/* 300 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 304 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor, char[] delimiters)
/*     */   {
/* 311 */     if (buffer == null) {
/* 312 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/* 314 */     if (cursor == null) {
/* 315 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*     */     }
/*     */     
/* 318 */     boolean terminated = false;
/*     */     
/* 320 */     int pos = cursor.getPos();
/* 321 */     int indexFrom = cursor.getPos();
/* 322 */     int indexTo = cursor.getUpperBound();
/*     */     
/*     */ 
/* 325 */     String name = null;
/* 326 */     while (pos < indexTo) {
/* 327 */       char ch = buffer.charAt(pos);
/* 328 */       if (ch == '=') {
/*     */         break;
/*     */       }
/* 331 */       if (isOneOf(ch, delimiters)) {
/* 332 */         terminated = true;
/* 333 */         break;
/*     */       }
/* 335 */       pos++;
/*     */     }
/*     */     
/* 338 */     if (pos == indexTo) {
/* 339 */       terminated = true;
/* 340 */       name = buffer.substringTrimmed(indexFrom, indexTo);
/*     */     } else {
/* 342 */       name = buffer.substringTrimmed(indexFrom, pos);
/* 343 */       pos++;
/*     */     }
/*     */     
/* 346 */     if (terminated) {
/* 347 */       cursor.updatePos(pos);
/* 348 */       return createNameValuePair(name, null);
/*     */     }
/*     */     
/*     */ 
/* 352 */     String value = null;
/* 353 */     int i1 = pos;
/*     */     
/* 355 */     boolean qouted = false;
/* 356 */     boolean escaped = false;
/* 357 */     while (pos < indexTo) {
/* 358 */       char ch = buffer.charAt(pos);
/* 359 */       if ((ch == '"') && (!escaped)) {
/* 360 */         qouted = !qouted;
/*     */       }
/* 362 */       if ((!qouted) && (!escaped) && (isOneOf(ch, delimiters))) {
/* 363 */         terminated = true;
/* 364 */         break;
/*     */       }
/* 366 */       if (escaped) {
/* 367 */         escaped = false;
/*     */       } else {
/* 369 */         escaped = (qouted) && (ch == '\\');
/*     */       }
/* 371 */       pos++;
/*     */     }
/*     */     
/* 374 */     int i2 = pos;
/*     */     
/* 376 */     while ((i1 < i2) && (HTTP.isWhitespace(buffer.charAt(i1)))) {
/* 377 */       i1++;
/*     */     }
/*     */     
/* 380 */     while ((i2 > i1) && (HTTP.isWhitespace(buffer.charAt(i2 - 1)))) {
/* 381 */       i2--;
/*     */     }
/*     */     
/* 384 */     if ((i2 - i1 >= 2) && (buffer.charAt(i1) == '"') && (buffer.charAt(i2 - 1) == '"'))
/*     */     {
/*     */ 
/* 387 */       i1++;
/* 388 */       i2--;
/*     */     }
/* 390 */     value = buffer.substring(i1, i2);
/* 391 */     if (terminated) {
/* 392 */       pos++;
/*     */     }
/* 394 */     cursor.updatePos(pos);
/* 395 */     return createNameValuePair(name, value);
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
/*     */   protected NameValuePair createNameValuePair(String name, String value)
/*     */   {
/* 408 */     return new BasicNameValuePair(name, value);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHeaderValueParser.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */