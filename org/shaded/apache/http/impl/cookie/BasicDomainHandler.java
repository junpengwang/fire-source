/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
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
/*     */ @Immutable
/*     */ public class BasicDomainHandler
/*     */   implements CookieAttributeHandler
/*     */ {
/*     */   public void parse(SetCookie cookie, String value)
/*     */     throws MalformedCookieException
/*     */   {
/*  50 */     if (cookie == null) {
/*  51 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/*  53 */     if (value == null) {
/*  54 */       throw new MalformedCookieException("Missing value for domain attribute");
/*     */     }
/*  56 */     if (value.trim().length() == 0) {
/*  57 */       throw new MalformedCookieException("Blank value for domain attribute");
/*     */     }
/*  59 */     cookie.setDomain(value);
/*     */   }
/*     */   
/*     */   public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException
/*     */   {
/*  64 */     if (cookie == null) {
/*  65 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/*  67 */     if (origin == null) {
/*  68 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  75 */     String host = origin.getHost();
/*  76 */     String domain = cookie.getDomain();
/*  77 */     if (domain == null) {
/*  78 */       throw new MalformedCookieException("Cookie domain may not be null");
/*     */     }
/*  80 */     if (host.contains("."))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*  85 */       if (!host.endsWith(domain)) {
/*  86 */         if (domain.startsWith(".")) {
/*  87 */           domain = domain.substring(1, domain.length());
/*     */         }
/*  89 */         if (!host.equals(domain)) {
/*  90 */           throw new MalformedCookieException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/*  96 */     else if (!host.equals(domain)) {
/*  97 */       throw new MalformedCookieException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean match(Cookie cookie, CookieOrigin origin)
/*     */   {
/* 105 */     if (cookie == null) {
/* 106 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 108 */     if (origin == null) {
/* 109 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 111 */     String host = origin.getHost();
/* 112 */     String domain = cookie.getDomain();
/* 113 */     if (domain == null) {
/* 114 */       return false;
/*     */     }
/* 116 */     if (host.equals(domain)) {
/* 117 */       return true;
/*     */     }
/* 119 */     if (!domain.startsWith(".")) {
/* 120 */       domain = '.' + domain;
/*     */     }
/* 122 */     return (host.endsWith(domain)) || (host.equals(domain.substring(1)));
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BasicDomainHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */