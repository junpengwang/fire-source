/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class NetscapeDomainHandler
/*     */   extends BasicDomainHandler
/*     */ {
/*     */   public void validate(Cookie cookie, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/*  52 */     super.validate(cookie, origin);
/*     */     
/*  54 */     String host = origin.getHost();
/*  55 */     String domain = cookie.getDomain();
/*  56 */     if (host.contains(".")) {
/*  57 */       int domainParts = new StringTokenizer(domain, ".").countTokens();
/*     */       
/*  59 */       if (isSpecialDomain(domain)) {
/*  60 */         if (domainParts < 2) {
/*  61 */           throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification for " + "special domains");
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*  67 */       else if (domainParts < 3) {
/*  68 */         throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification");
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
/*     */ 
/*     */   private static boolean isSpecialDomain(String domain)
/*     */   {
/*  83 */     String ucDomain = domain.toUpperCase(Locale.ENGLISH);
/*  84 */     return (ucDomain.endsWith(".COM")) || (ucDomain.endsWith(".EDU")) || (ucDomain.endsWith(".NET")) || (ucDomain.endsWith(".GOV")) || (ucDomain.endsWith(".MIL")) || (ucDomain.endsWith(".ORG")) || (ucDomain.endsWith(".INT"));
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
/*  95 */     if (cookie == null) {
/*  96 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/*  98 */     if (origin == null) {
/*  99 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 101 */     String host = origin.getHost();
/* 102 */     String domain = cookie.getDomain();
/* 103 */     if (domain == null) {
/* 104 */       return false;
/*     */     }
/* 106 */     return host.endsWith(domain);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/NetscapeDomainHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */