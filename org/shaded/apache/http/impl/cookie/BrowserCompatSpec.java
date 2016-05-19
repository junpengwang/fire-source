/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.FormattedHeader;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ import org.shaded.apache.http.message.BufferedHeader;
/*     */ import org.shaded.apache.http.message.ParserCursor;
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
/*     */ @NotThreadSafe
/*     */ public class BrowserCompatSpec
/*     */   extends CookieSpecBase
/*     */ {
/*     */   @Deprecated
/*  60 */   protected static final String[] DATE_PATTERNS = { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */   private static final String[] DEFAULT_DATE_PATTERNS = { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String[] datepatterns;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BrowserCompatSpec(String[] datepatterns)
/*     */   {
/*  99 */     if (datepatterns != null) {
/* 100 */       this.datepatterns = ((String[])datepatterns.clone());
/*     */     } else {
/* 102 */       this.datepatterns = DEFAULT_DATE_PATTERNS;
/*     */     }
/* 104 */     registerAttribHandler("path", new BasicPathHandler());
/* 105 */     registerAttribHandler("domain", new BasicDomainHandler());
/* 106 */     registerAttribHandler("max-age", new BasicMaxAgeHandler());
/* 107 */     registerAttribHandler("secure", new BasicSecureHandler());
/* 108 */     registerAttribHandler("comment", new BasicCommentHandler());
/* 109 */     registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
/*     */   }
/*     */   
/*     */ 
/*     */   public BrowserCompatSpec()
/*     */   {
/* 115 */     this(null);
/*     */   }
/*     */   
/*     */   public List<Cookie> parse(Header header, CookieOrigin origin) throws MalformedCookieException
/*     */   {
/* 120 */     if (header == null) {
/* 121 */       throw new IllegalArgumentException("Header may not be null");
/*     */     }
/* 123 */     if (origin == null) {
/* 124 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 126 */     String headername = header.getName();
/* 127 */     String headervalue = header.getValue();
/* 128 */     if (!headername.equalsIgnoreCase("Set-Cookie")) {
/* 129 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*     */     }
/*     */     
/* 132 */     boolean isNetscapeCookie = false;
/* 133 */     int i1 = headervalue.toLowerCase(Locale.ENGLISH).indexOf("expires=");
/* 134 */     if (i1 != -1) {
/* 135 */       i1 += "expires=".length();
/* 136 */       int i2 = headervalue.indexOf(';', i1);
/* 137 */       if (i2 == -1) {
/* 138 */         i2 = headervalue.length();
/*     */       }
/*     */       try {
/* 141 */         DateUtils.parseDate(headervalue.substring(i1, i2), this.datepatterns);
/* 142 */         isNetscapeCookie = true;
/*     */       }
/*     */       catch (DateParseException e) {}
/*     */     }
/*     */     
/* 147 */     HeaderElement[] elems = null;
/* 148 */     if (isNetscapeCookie) {
/* 149 */       NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
/*     */       ParserCursor cursor;
/*     */       CharArrayBuffer buffer;
/* 152 */       ParserCursor cursor; if ((header instanceof FormattedHeader)) {
/* 153 */         CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/* 154 */         cursor = new ParserCursor(((FormattedHeader)header).getValuePos(), buffer.length());
/*     */       }
/*     */       else
/*     */       {
/* 158 */         String s = header.getValue();
/* 159 */         if (s == null) {
/* 160 */           throw new MalformedCookieException("Header value is null");
/*     */         }
/* 162 */         buffer = new CharArrayBuffer(s.length());
/* 163 */         buffer.append(s);
/* 164 */         cursor = new ParserCursor(0, buffer.length());
/*     */       }
/* 166 */       elems = new HeaderElement[] { parser.parseHeader(buffer, cursor) };
/*     */     } else {
/* 168 */       elems = header.getElements();
/*     */     }
/* 170 */     return parse(elems, origin);
/*     */   }
/*     */   
/*     */   public List<Header> formatCookies(List<Cookie> cookies) {
/* 174 */     if (cookies == null) {
/* 175 */       throw new IllegalArgumentException("List of cookies may not be null");
/*     */     }
/* 177 */     if (cookies.isEmpty()) {
/* 178 */       throw new IllegalArgumentException("List of cookies may not be empty");
/*     */     }
/* 180 */     CharArrayBuffer buffer = new CharArrayBuffer(20 * cookies.size());
/* 181 */     buffer.append("Cookie");
/* 182 */     buffer.append(": ");
/* 183 */     for (int i = 0; i < cookies.size(); i++) {
/* 184 */       Cookie cookie = (Cookie)cookies.get(i);
/* 185 */       if (i > 0) {
/* 186 */         buffer.append("; ");
/*     */       }
/* 188 */       buffer.append(cookie.getName());
/* 189 */       buffer.append("=");
/* 190 */       String s = cookie.getValue();
/* 191 */       if (s != null) {
/* 192 */         buffer.append(s);
/*     */       }
/*     */     }
/* 195 */     List<Header> headers = new ArrayList(1);
/* 196 */     headers.add(new BufferedHeader(buffer));
/* 197 */     return headers;
/*     */   }
/*     */   
/*     */   public int getVersion() {
/* 201 */     return 0;
/*     */   }
/*     */   
/*     */   public Header getVersionHeader() {
/* 205 */     return null;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 210 */     return "compatibility";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BrowserCompatSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */