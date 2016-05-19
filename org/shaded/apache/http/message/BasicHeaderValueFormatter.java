/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.NameValuePair;
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
/*     */ public class BasicHeaderValueFormatter
/*     */   implements HeaderValueFormatter
/*     */ {
/*  59 */   public static final BasicHeaderValueFormatter DEFAULT = new BasicHeaderValueFormatter();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String UNSAFE_CHARS = "\"\\";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String formatElements(HeaderElement[] elems, boolean quote, HeaderValueFormatter formatter)
/*     */   {
/*  97 */     if (formatter == null)
/*  98 */       formatter = DEFAULT;
/*  99 */     return formatter.formatElements(null, elems, quote).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer formatElements(CharArrayBuffer buffer, HeaderElement[] elems, boolean quote)
/*     */   {
/* 107 */     if (elems == null) {
/* 108 */       throw new IllegalArgumentException("Header element array must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 112 */     int len = estimateElementsLen(elems);
/* 113 */     if (buffer == null) {
/* 114 */       buffer = new CharArrayBuffer(len);
/*     */     } else {
/* 116 */       buffer.ensureCapacity(len);
/*     */     }
/*     */     
/* 119 */     for (int i = 0; i < elems.length; i++) {
/* 120 */       if (i > 0) {
/* 121 */         buffer.append(", ");
/*     */       }
/* 123 */       formatHeaderElement(buffer, elems[i], quote);
/*     */     }
/*     */     
/* 126 */     return buffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int estimateElementsLen(HeaderElement[] elems)
/*     */   {
/* 138 */     if ((elems == null) || (elems.length < 1)) {
/* 139 */       return 0;
/*     */     }
/* 141 */     int result = (elems.length - 1) * 2;
/* 142 */     for (int i = 0; i < elems.length; i++) {
/* 143 */       result += estimateHeaderElementLen(elems[i]);
/*     */     }
/*     */     
/* 146 */     return result;
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
/*     */   public static final String formatHeaderElement(HeaderElement elem, boolean quote, HeaderValueFormatter formatter)
/*     */   {
/* 166 */     if (formatter == null)
/* 167 */       formatter = DEFAULT;
/* 168 */     return formatter.formatHeaderElement(null, elem, quote).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer formatHeaderElement(CharArrayBuffer buffer, HeaderElement elem, boolean quote)
/*     */   {
/* 176 */     if (elem == null) {
/* 177 */       throw new IllegalArgumentException("Header element must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 181 */     int len = estimateHeaderElementLen(elem);
/* 182 */     if (buffer == null) {
/* 183 */       buffer = new CharArrayBuffer(len);
/*     */     } else {
/* 185 */       buffer.ensureCapacity(len);
/*     */     }
/*     */     
/* 188 */     buffer.append(elem.getName());
/* 189 */     String value = elem.getValue();
/* 190 */     if (value != null) {
/* 191 */       buffer.append('=');
/* 192 */       doFormatValue(buffer, value, quote);
/*     */     }
/*     */     
/* 195 */     int parcnt = elem.getParameterCount();
/* 196 */     if (parcnt > 0) {
/* 197 */       for (int i = 0; i < parcnt; i++) {
/* 198 */         buffer.append("; ");
/* 199 */         formatNameValuePair(buffer, elem.getParameter(i), quote);
/*     */       }
/*     */     }
/*     */     
/* 203 */     return buffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int estimateHeaderElementLen(HeaderElement elem)
/*     */   {
/* 215 */     if (elem == null) {
/* 216 */       return 0;
/*     */     }
/* 218 */     int result = elem.getName().length();
/* 219 */     String value = elem.getValue();
/* 220 */     if (value != null)
/*     */     {
/* 222 */       result += 3 + value.length();
/*     */     }
/*     */     
/* 225 */     int parcnt = elem.getParameterCount();
/* 226 */     if (parcnt > 0) {
/* 227 */       for (int i = 0; i < parcnt; i++) {
/* 228 */         result += 2 + estimateNameValuePairLen(elem.getParameter(i));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 233 */     return result;
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
/*     */   public static final String formatParameters(NameValuePair[] nvps, boolean quote, HeaderValueFormatter formatter)
/*     */   {
/* 254 */     if (formatter == null)
/* 255 */       formatter = DEFAULT;
/* 256 */     return formatter.formatParameters(null, nvps, quote).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer formatParameters(CharArrayBuffer buffer, NameValuePair[] nvps, boolean quote)
/*     */   {
/* 264 */     if (nvps == null) {
/* 265 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 269 */     int len = estimateParametersLen(nvps);
/* 270 */     if (buffer == null) {
/* 271 */       buffer = new CharArrayBuffer(len);
/*     */     } else {
/* 273 */       buffer.ensureCapacity(len);
/*     */     }
/*     */     
/* 276 */     for (int i = 0; i < nvps.length; i++) {
/* 277 */       if (i > 0) {
/* 278 */         buffer.append("; ");
/*     */       }
/* 280 */       formatNameValuePair(buffer, nvps[i], quote);
/*     */     }
/*     */     
/* 283 */     return buffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int estimateParametersLen(NameValuePair[] nvps)
/*     */   {
/* 295 */     if ((nvps == null) || (nvps.length < 1)) {
/* 296 */       return 0;
/*     */     }
/* 298 */     int result = (nvps.length - 1) * 2;
/* 299 */     for (int i = 0; i < nvps.length; i++) {
/* 300 */       result += estimateNameValuePairLen(nvps[i]);
/*     */     }
/*     */     
/* 303 */     return result;
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
/*     */   public static final String formatNameValuePair(NameValuePair nvp, boolean quote, HeaderValueFormatter formatter)
/*     */   {
/* 322 */     if (formatter == null)
/* 323 */       formatter = DEFAULT;
/* 324 */     return formatter.formatNameValuePair(null, nvp, quote).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharArrayBuffer formatNameValuePair(CharArrayBuffer buffer, NameValuePair nvp, boolean quote)
/*     */   {
/* 332 */     if (nvp == null) {
/* 333 */       throw new IllegalArgumentException("NameValuePair must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 337 */     int len = estimateNameValuePairLen(nvp);
/* 338 */     if (buffer == null) {
/* 339 */       buffer = new CharArrayBuffer(len);
/*     */     } else {
/* 341 */       buffer.ensureCapacity(len);
/*     */     }
/*     */     
/* 344 */     buffer.append(nvp.getName());
/* 345 */     String value = nvp.getValue();
/* 346 */     if (value != null) {
/* 347 */       buffer.append('=');
/* 348 */       doFormatValue(buffer, value, quote);
/*     */     }
/*     */     
/* 351 */     return buffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int estimateNameValuePairLen(NameValuePair nvp)
/*     */   {
/* 363 */     if (nvp == null) {
/* 364 */       return 0;
/*     */     }
/* 366 */     int result = nvp.getName().length();
/* 367 */     String value = nvp.getValue();
/* 368 */     if (value != null)
/*     */     {
/* 370 */       result += 3 + value.length();
/*     */     }
/* 372 */     return result;
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
/*     */   protected void doFormatValue(CharArrayBuffer buffer, String value, boolean quote)
/*     */   {
/* 390 */     if (!quote) {
/* 391 */       for (int i = 0; (i < value.length()) && (!quote); i++) {
/* 392 */         quote = isSeparator(value.charAt(i));
/*     */       }
/*     */     }
/*     */     
/* 396 */     if (quote) {
/* 397 */       buffer.append('"');
/*     */     }
/* 399 */     for (int i = 0; i < value.length(); i++) {
/* 400 */       char ch = value.charAt(i);
/* 401 */       if (isUnsafe(ch)) {
/* 402 */         buffer.append('\\');
/*     */       }
/* 404 */       buffer.append(ch);
/*     */     }
/* 406 */     if (quote) {
/* 407 */       buffer.append('"');
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
/*     */   protected boolean isSeparator(char ch)
/*     */   {
/* 421 */     return " ;,:@()<>\\\"/[]?={}\t".indexOf(ch) >= 0;
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
/*     */   protected boolean isUnsafe(char ch)
/*     */   {
/* 434 */     return "\"\\".indexOf(ch) >= 0;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHeaderValueFormatter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */