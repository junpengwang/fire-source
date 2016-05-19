/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.cookie.ClientCookie;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.CookiePathComparator;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ import org.shaded.apache.http.message.BufferedHeader;
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
/*     */ @NotThreadSafe
/*     */ public class RFC2109Spec
/*     */   extends CookieSpecBase
/*     */ {
/*  60 */   private static final CookiePathComparator PATH_COMPARATOR = new CookiePathComparator();
/*     */   
/*  62 */   private static final String[] DATE_PATTERNS = { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy" };
/*     */   
/*     */ 
/*     */   private final String[] datepatterns;
/*     */   
/*     */ 
/*     */   private final boolean oneHeader;
/*     */   
/*     */ 
/*     */ 
/*     */   public RFC2109Spec(String[] datepatterns, boolean oneHeader)
/*     */   {
/*  74 */     if (datepatterns != null) {
/*  75 */       this.datepatterns = ((String[])datepatterns.clone());
/*     */     } else {
/*  77 */       this.datepatterns = DATE_PATTERNS;
/*     */     }
/*  79 */     this.oneHeader = oneHeader;
/*  80 */     registerAttribHandler("version", new RFC2109VersionHandler());
/*  81 */     registerAttribHandler("path", new BasicPathHandler());
/*  82 */     registerAttribHandler("domain", new RFC2109DomainHandler());
/*  83 */     registerAttribHandler("max-age", new BasicMaxAgeHandler());
/*  84 */     registerAttribHandler("secure", new BasicSecureHandler());
/*  85 */     registerAttribHandler("comment", new BasicCommentHandler());
/*  86 */     registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
/*     */   }
/*     */   
/*     */ 
/*     */   public RFC2109Spec()
/*     */   {
/*  92 */     this(null, false);
/*     */   }
/*     */   
/*     */   public List<Cookie> parse(Header header, CookieOrigin origin) throws MalformedCookieException
/*     */   {
/*  97 */     if (header == null) {
/*  98 */       throw new IllegalArgumentException("Header may not be null");
/*     */     }
/* 100 */     if (origin == null) {
/* 101 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 103 */     if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
/* 104 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*     */     }
/*     */     
/* 107 */     HeaderElement[] elems = header.getElements();
/* 108 */     return parse(elems, origin);
/*     */   }
/*     */   
/*     */   public void validate(Cookie cookie, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 114 */     if (cookie == null) {
/* 115 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 117 */     String name = cookie.getName();
/* 118 */     if (name.indexOf(' ') != -1) {
/* 119 */       throw new MalformedCookieException("Cookie name may not contain blanks");
/*     */     }
/* 121 */     if (name.startsWith("$")) {
/* 122 */       throw new MalformedCookieException("Cookie name may not start with $");
/*     */     }
/* 124 */     super.validate(cookie, origin);
/*     */   }
/*     */   
/*     */   public List<Header> formatCookies(List<Cookie> cookies) {
/* 128 */     if (cookies == null) {
/* 129 */       throw new IllegalArgumentException("List of cookies may not be null");
/*     */     }
/* 131 */     if (cookies.isEmpty()) {
/* 132 */       throw new IllegalArgumentException("List of cookies may not be empty");
/*     */     }
/* 134 */     if (cookies.size() > 1)
/*     */     {
/* 136 */       cookies = new ArrayList(cookies);
/* 137 */       Collections.sort(cookies, PATH_COMPARATOR);
/*     */     }
/* 139 */     if (this.oneHeader) {
/* 140 */       return doFormatOneHeader(cookies);
/*     */     }
/* 142 */     return doFormatManyHeaders(cookies);
/*     */   }
/*     */   
/*     */   private List<Header> doFormatOneHeader(List<Cookie> cookies)
/*     */   {
/* 147 */     int version = Integer.MAX_VALUE;
/*     */     
/* 149 */     for (Cookie cookie : cookies) {
/* 150 */       if (cookie.getVersion() < version) {
/* 151 */         version = cookie.getVersion();
/*     */       }
/*     */     }
/* 154 */     CharArrayBuffer buffer = new CharArrayBuffer(40 * cookies.size());
/* 155 */     buffer.append("Cookie");
/* 156 */     buffer.append(": ");
/* 157 */     buffer.append("$Version=");
/* 158 */     buffer.append(Integer.toString(version));
/* 159 */     for (Cookie cooky : cookies) {
/* 160 */       buffer.append("; ");
/* 161 */       Cookie cookie = cooky;
/* 162 */       formatCookieAsVer(buffer, cookie, version);
/*     */     }
/* 164 */     List<Header> headers = new ArrayList(1);
/* 165 */     headers.add(new BufferedHeader(buffer));
/* 166 */     return headers;
/*     */   }
/*     */   
/*     */   private List<Header> doFormatManyHeaders(List<Cookie> cookies) {
/* 170 */     List<Header> headers = new ArrayList(cookies.size());
/* 171 */     for (Cookie cookie : cookies) {
/* 172 */       int version = cookie.getVersion();
/* 173 */       CharArrayBuffer buffer = new CharArrayBuffer(40);
/* 174 */       buffer.append("Cookie: ");
/* 175 */       buffer.append("$Version=");
/* 176 */       buffer.append(Integer.toString(version));
/* 177 */       buffer.append("; ");
/* 178 */       formatCookieAsVer(buffer, cookie, version);
/* 179 */       headers.add(new BufferedHeader(buffer));
/*     */     }
/* 181 */     return headers;
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
/*     */   protected void formatParamAsVer(CharArrayBuffer buffer, String name, String value, int version)
/*     */   {
/* 195 */     buffer.append(name);
/* 196 */     buffer.append("=");
/* 197 */     if (value != null) {
/* 198 */       if (version > 0) {
/* 199 */         buffer.append('"');
/* 200 */         buffer.append(value);
/* 201 */         buffer.append('"');
/*     */       } else {
/* 203 */         buffer.append(value);
/*     */       }
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
/*     */   protected void formatCookieAsVer(CharArrayBuffer buffer, Cookie cookie, int version)
/*     */   {
/* 217 */     formatParamAsVer(buffer, cookie.getName(), cookie.getValue(), version);
/* 218 */     if ((cookie.getPath() != null) && 
/* 219 */       ((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("path")))
/*     */     {
/* 221 */       buffer.append("; ");
/* 222 */       formatParamAsVer(buffer, "$Path", cookie.getPath(), version);
/*     */     }
/*     */     
/* 225 */     if ((cookie.getDomain() != null) && 
/* 226 */       ((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("domain")))
/*     */     {
/* 228 */       buffer.append("; ");
/* 229 */       formatParamAsVer(buffer, "$Domain", cookie.getDomain(), version);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getVersion()
/*     */   {
/* 235 */     return 1;
/*     */   }
/*     */   
/*     */   public Header getVersionHeader() {
/* 239 */     return null;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 244 */     return "rfc2109";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2109Spec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */