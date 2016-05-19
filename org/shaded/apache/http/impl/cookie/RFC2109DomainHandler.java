/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieAttributeHandler;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ import org.shaded.apache.http.cookie.SetCookie;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class RFC2109DomainHandler
/*     */   implements CookieAttributeHandler
/*     */ {
/*     */   public void parse(SetCookie cookie, String value)
/*     */     throws MalformedCookieException
/*     */   {
/*  52 */     if (cookie == null) {
/*  53 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/*  55 */     if (value == null) {
/*  56 */       throw new MalformedCookieException("Missing value for domain attribute");
/*     */     }
/*  58 */     if (value.trim().length() == 0) {
/*  59 */       throw new MalformedCookieException("Blank value for domain attribute");
/*     */     }
/*  61 */     cookie.setDomain(value);
/*     */   }
/*     */   
/*     */   public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException
/*     */   {
/*  66 */     if (cookie == null) {
/*  67 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/*  69 */     if (origin == null) {
/*  70 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/*  72 */     String host = origin.getHost();
/*  73 */     String domain = cookie.getDomain();
/*  74 */     if (domain == null) {
/*  75 */       throw new MalformedCookieException("Cookie domain may not be null");
/*     */     }
/*  77 */     if (!domain.equals(host)) {
/*  78 */       int dotIndex = domain.indexOf('.');
/*  79 */       if (dotIndex == -1) {
/*  80 */         throw new MalformedCookieException("Domain attribute \"" + domain + "\" does not match the host \"" + host + "\"");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  86 */       if (!domain.startsWith(".")) {
/*  87 */         throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must start with a dot");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  92 */       dotIndex = domain.indexOf('.', 1);
/*  93 */       if ((dotIndex < 0) || (dotIndex == domain.length() - 1)) {
/*  94 */         throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must contain an embedded dot");
/*     */       }
/*     */       
/*     */ 
/*  98 */       host = host.toLowerCase(Locale.ENGLISH);
/*  99 */       if (!host.endsWith(domain)) {
/* 100 */         throw new MalformedCookieException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 105 */       String hostWithoutDomain = host.substring(0, host.length() - domain.length());
/* 106 */       if (hostWithoutDomain.indexOf('.') != -1) {
/* 107 */         throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates RFC 2109: host minus domain may not contain any dots");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean match(Cookie cookie, CookieOrigin origin)
/*     */   {
/* 115 */     if (cookie == null) {
/* 116 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 118 */     if (origin == null) {
/* 119 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 121 */     String host = origin.getHost();
/* 122 */     String domain = cookie.getDomain();
/* 123 */     if (domain == null) {
/* 124 */       return false;
/*     */     }
/* 126 */     return (host.equals(domain)) || ((domain.startsWith(".")) && (host.endsWith(domain)));
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2109DomainHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */