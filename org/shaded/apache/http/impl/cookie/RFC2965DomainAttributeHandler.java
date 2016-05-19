/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.cookie.ClientCookie;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class RFC2965DomainAttributeHandler
/*     */   implements CookieAttributeHandler
/*     */ {
/*     */   public void parse(SetCookie cookie, String domain)
/*     */     throws MalformedCookieException
/*     */   {
/*  59 */     if (cookie == null) {
/*  60 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/*  62 */     if (domain == null) {
/*  63 */       throw new MalformedCookieException("Missing value for domain attribute");
/*     */     }
/*     */     
/*  66 */     if (domain.trim().length() == 0) {
/*  67 */       throw new MalformedCookieException("Blank value for domain attribute");
/*     */     }
/*     */     
/*  70 */     domain = domain.toLowerCase(Locale.ENGLISH);
/*  71 */     if (!domain.startsWith("."))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */       domain = '.' + domain;
/*     */     }
/*  79 */     cookie.setDomain(domain);
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
/*     */   public boolean domainMatch(String host, String domain)
/*     */   {
/*  98 */     boolean match = (host.equals(domain)) || ((domain.startsWith(".")) && (host.endsWith(domain)));
/*     */     
/*     */ 
/* 101 */     return match;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void validate(Cookie cookie, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 109 */     if (cookie == null) {
/* 110 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 112 */     if (origin == null) {
/* 113 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 115 */     String host = origin.getHost().toLowerCase(Locale.ENGLISH);
/* 116 */     if (cookie.getDomain() == null) {
/* 117 */       throw new MalformedCookieException("Invalid cookie state: domain not specified");
/*     */     }
/*     */     
/* 120 */     String cookieDomain = cookie.getDomain().toLowerCase(Locale.ENGLISH);
/*     */     
/* 122 */     if (((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("domain")))
/*     */     {
/*     */ 
/* 125 */       if (!cookieDomain.startsWith(".")) {
/* 126 */         throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2109: domain must start with a dot");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 132 */       int dotIndex = cookieDomain.indexOf('.', 1);
/* 133 */       if (((dotIndex < 0) || (dotIndex == cookieDomain.length() - 1)) && (!cookieDomain.equals(".local")))
/*     */       {
/* 135 */         throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: the value contains no embedded dots " + "and the value is not .local");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 142 */       if (!domainMatch(host, cookieDomain)) {
/* 143 */         throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: effective host name does not " + "domain-match domain attribute.");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 150 */       String effectiveHostWithoutDomain = host.substring(0, host.length() - cookieDomain.length());
/*     */       
/* 152 */       if (effectiveHostWithoutDomain.indexOf('.') != -1) {
/* 153 */         throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: " + "effective host minus domain may not contain any dots");
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */     }
/* 160 */     else if (!cookie.getDomain().equals(host)) {
/* 161 */       throw new MalformedCookieException("Illegal domain attribute: \"" + cookie.getDomain() + "\"." + "Domain of origin: \"" + host + "\"");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean match(Cookie cookie, CookieOrigin origin)
/*     */   {
/* 173 */     if (cookie == null) {
/* 174 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 176 */     if (origin == null) {
/* 177 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 179 */     String host = origin.getHost().toLowerCase(Locale.ENGLISH);
/* 180 */     String cookieDomain = cookie.getDomain();
/*     */     
/*     */ 
/*     */ 
/* 184 */     if (!domainMatch(host, cookieDomain)) {
/* 185 */       return false;
/*     */     }
/*     */     
/* 188 */     String effectiveHostWithoutDomain = host.substring(0, host.length() - cookieDomain.length());
/*     */     
/* 190 */     return effectiveHostWithoutDomain.indexOf('.') == -1;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2965DomainAttributeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */