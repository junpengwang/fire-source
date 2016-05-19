/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.CookieSpec;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ import org.shaded.apache.http.cookie.SetCookie2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class BestMatchSpec
/*     */   implements CookieSpec
/*     */ {
/*     */   private final String[] datepatterns;
/*     */   private final boolean oneHeader;
/*     */   private RFC2965Spec strict;
/*     */   private RFC2109Spec obsoleteStrict;
/*     */   private BrowserCompatSpec compat;
/*     */   private NetscapeDraftSpec netscape;
/*     */   
/*     */   public BestMatchSpec(String[] datepatterns, boolean oneHeader)
/*     */   {
/*  63 */     this.datepatterns = (datepatterns == null ? null : (String[])datepatterns.clone());
/*  64 */     this.oneHeader = oneHeader;
/*     */   }
/*     */   
/*     */   public BestMatchSpec() {
/*  68 */     this(null, false);
/*     */   }
/*     */   
/*     */   private RFC2965Spec getStrict() {
/*  72 */     if (this.strict == null) {
/*  73 */       this.strict = new RFC2965Spec(this.datepatterns, this.oneHeader);
/*     */     }
/*  75 */     return this.strict;
/*     */   }
/*     */   
/*     */   private RFC2109Spec getObsoleteStrict() {
/*  79 */     if (this.obsoleteStrict == null) {
/*  80 */       this.obsoleteStrict = new RFC2109Spec(this.datepatterns, this.oneHeader);
/*     */     }
/*  82 */     return this.obsoleteStrict;
/*     */   }
/*     */   
/*     */   private BrowserCompatSpec getCompat() {
/*  86 */     if (this.compat == null) {
/*  87 */       this.compat = new BrowserCompatSpec(this.datepatterns);
/*     */     }
/*  89 */     return this.compat;
/*     */   }
/*     */   
/*     */   private NetscapeDraftSpec getNetscape() {
/*  93 */     if (this.netscape == null) {
/*  94 */       this.netscape = new NetscapeDraftSpec(this.datepatterns);
/*     */     }
/*  96 */     return this.netscape;
/*     */   }
/*     */   
/*     */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 102 */     if (header == null) {
/* 103 */       throw new IllegalArgumentException("Header may not be null");
/*     */     }
/* 105 */     if (origin == null) {
/* 106 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 108 */     HeaderElement[] helems = header.getElements();
/* 109 */     boolean versioned = false;
/* 110 */     boolean netscape = false;
/* 111 */     for (HeaderElement helem : helems) {
/* 112 */       if (helem.getParameterByName("version") != null) {
/* 113 */         versioned = true;
/*     */       }
/* 115 */       if (helem.getParameterByName("expires") != null) {
/* 116 */         netscape = true;
/*     */       }
/*     */     }
/*     */     
/* 120 */     if (versioned) {
/* 121 */       if ("Set-Cookie2".equals(header.getName())) {
/* 122 */         return getStrict().parse(helems, origin);
/*     */       }
/* 124 */       return getObsoleteStrict().parse(helems, origin);
/*     */     }
/* 126 */     if (netscape)
/*     */     {
/*     */ 
/*     */ 
/* 130 */       return getNetscape().parse(header, origin);
/*     */     }
/* 132 */     return getCompat().parse(helems, origin);
/*     */   }
/*     */   
/*     */ 
/*     */   public void validate(Cookie cookie, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 139 */     if (cookie == null) {
/* 140 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 142 */     if (origin == null) {
/* 143 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 145 */     if (cookie.getVersion() > 0) {
/* 146 */       if ((cookie instanceof SetCookie2)) {
/* 147 */         getStrict().validate(cookie, origin);
/*     */       } else {
/* 149 */         getObsoleteStrict().validate(cookie, origin);
/*     */       }
/*     */     } else {
/* 152 */       getCompat().validate(cookie, origin);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean match(Cookie cookie, CookieOrigin origin) {
/* 157 */     if (cookie == null) {
/* 158 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 160 */     if (origin == null) {
/* 161 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 163 */     if (cookie.getVersion() > 0) {
/* 164 */       if ((cookie instanceof SetCookie2)) {
/* 165 */         return getStrict().match(cookie, origin);
/*     */       }
/* 167 */       return getObsoleteStrict().match(cookie, origin);
/*     */     }
/*     */     
/* 170 */     return getCompat().match(cookie, origin);
/*     */   }
/*     */   
/*     */   public List<Header> formatCookies(List<Cookie> cookies)
/*     */   {
/* 175 */     if (cookies == null) {
/* 176 */       throw new IllegalArgumentException("List of cookie may not be null");
/*     */     }
/* 178 */     int version = Integer.MAX_VALUE;
/* 179 */     boolean isSetCookie2 = true;
/* 180 */     for (Cookie cookie : cookies) {
/* 181 */       if (!(cookie instanceof SetCookie2)) {
/* 182 */         isSetCookie2 = false;
/*     */       }
/* 184 */       if (cookie.getVersion() < version) {
/* 185 */         version = cookie.getVersion();
/*     */       }
/*     */     }
/* 188 */     if (version > 0) {
/* 189 */       if (isSetCookie2) {
/* 190 */         return getStrict().formatCookies(cookies);
/*     */       }
/* 192 */       return getObsoleteStrict().formatCookies(cookies);
/*     */     }
/*     */     
/* 195 */     return getCompat().formatCookies(cookies);
/*     */   }
/*     */   
/*     */   public int getVersion()
/*     */   {
/* 200 */     return getStrict().getVersion();
/*     */   }
/*     */   
/*     */   public Header getVersionHeader() {
/* 204 */     return getStrict().getVersionHeader();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 209 */     return "best-match";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BestMatchSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */