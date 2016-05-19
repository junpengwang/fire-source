/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ @NotThreadSafe
/*     */ public class NetscapeDraftSpec
/*     */   extends CookieSpecBase
/*     */ {
/*     */   protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
/*     */   private final String[] datepatterns;
/*     */   
/*     */   public NetscapeDraftSpec(String[] datepatterns)
/*     */   {
/*  65 */     if (datepatterns != null) {
/*  66 */       this.datepatterns = ((String[])datepatterns.clone());
/*     */     } else {
/*  68 */       this.datepatterns = new String[] { "EEE, dd-MMM-yyyy HH:mm:ss z" };
/*     */     }
/*  70 */     registerAttribHandler("path", new BasicPathHandler());
/*  71 */     registerAttribHandler("domain", new NetscapeDomainHandler());
/*  72 */     registerAttribHandler("max-age", new BasicMaxAgeHandler());
/*  73 */     registerAttribHandler("secure", new BasicSecureHandler());
/*  74 */     registerAttribHandler("comment", new BasicCommentHandler());
/*  75 */     registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
/*     */   }
/*     */   
/*     */ 
/*     */   public NetscapeDraftSpec()
/*     */   {
/*  81 */     this(null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 112 */     if (header == null) {
/* 113 */       throw new IllegalArgumentException("Header may not be null");
/*     */     }
/* 115 */     if (origin == null) {
/* 116 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 118 */     if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
/* 119 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*     */     }
/*     */     
/* 122 */     NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
/*     */     ParserCursor cursor;
/*     */     CharArrayBuffer buffer;
/* 125 */     ParserCursor cursor; if ((header instanceof FormattedHeader)) {
/* 126 */       CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/* 127 */       cursor = new ParserCursor(((FormattedHeader)header).getValuePos(), buffer.length());
/*     */     }
/*     */     else
/*     */     {
/* 131 */       String s = header.getValue();
/* 132 */       if (s == null) {
/* 133 */         throw new MalformedCookieException("Header value is null");
/*     */       }
/* 135 */       buffer = new CharArrayBuffer(s.length());
/* 136 */       buffer.append(s);
/* 137 */       cursor = new ParserCursor(0, buffer.length());
/*     */     }
/* 139 */     return parse(new HeaderElement[] { parser.parseHeader(buffer, cursor) }, origin);
/*     */   }
/*     */   
/*     */   public List<Header> formatCookies(List<Cookie> cookies) {
/* 143 */     if (cookies == null) {
/* 144 */       throw new IllegalArgumentException("List of cookies may not be null");
/*     */     }
/* 146 */     if (cookies.isEmpty()) {
/* 147 */       throw new IllegalArgumentException("List of cookies may not be empty");
/*     */     }
/* 149 */     CharArrayBuffer buffer = new CharArrayBuffer(20 * cookies.size());
/* 150 */     buffer.append("Cookie");
/* 151 */     buffer.append(": ");
/* 152 */     for (int i = 0; i < cookies.size(); i++) {
/* 153 */       Cookie cookie = (Cookie)cookies.get(i);
/* 154 */       if (i > 0) {
/* 155 */         buffer.append("; ");
/*     */       }
/* 157 */       buffer.append(cookie.getName());
/* 158 */       String s = cookie.getValue();
/* 159 */       if (s != null) {
/* 160 */         buffer.append("=");
/* 161 */         buffer.append(s);
/*     */       }
/*     */     }
/* 164 */     List<Header> headers = new ArrayList(1);
/* 165 */     headers.add(new BufferedHeader(buffer));
/* 166 */     return headers;
/*     */   }
/*     */   
/*     */   public int getVersion() {
/* 170 */     return 0;
/*     */   }
/*     */   
/*     */   public Header getVersionHeader() {
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 179 */     return "netscape";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/NetscapeDraftSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */