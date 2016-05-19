/*     */ package org.shaded.apache.http.client.protocol;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderIterator;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpResponseInterceptor;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.client.CookieStore;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.CookieSpec;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ import org.shaded.apache.http.protocol.HttpContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class ResponseProcessCookies
/*     */   implements HttpResponseInterceptor
/*     */ {
/*  59 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(HttpResponse response, HttpContext context)
/*     */     throws HttpException, IOException
/*     */   {
/*  67 */     if (response == null) {
/*  68 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/*  70 */     if (context == null) {
/*  71 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/*     */     
/*     */ 
/*  75 */     CookieSpec cookieSpec = (CookieSpec)context.getAttribute("http.cookie-spec");
/*     */     
/*  77 */     if (cookieSpec == null) {
/*  78 */       return;
/*     */     }
/*     */     
/*  81 */     CookieStore cookieStore = (CookieStore)context.getAttribute("http.cookie-store");
/*     */     
/*  83 */     if (cookieStore == null) {
/*  84 */       this.log.info("CookieStore not available in HTTP context");
/*  85 */       return;
/*     */     }
/*     */     
/*  88 */     CookieOrigin cookieOrigin = (CookieOrigin)context.getAttribute("http.cookie-origin");
/*     */     
/*  90 */     if (cookieOrigin == null) {
/*  91 */       this.log.info("CookieOrigin not available in HTTP context");
/*  92 */       return;
/*     */     }
/*  94 */     HeaderIterator it = response.headerIterator("Set-Cookie");
/*  95 */     processCookies(it, cookieSpec, cookieOrigin, cookieStore);
/*     */     
/*     */ 
/*  98 */     if (cookieSpec.getVersion() > 0)
/*     */     {
/*     */ 
/* 101 */       it = response.headerIterator("Set-Cookie2");
/* 102 */       processCookies(it, cookieSpec, cookieOrigin, cookieStore);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void processCookies(HeaderIterator iterator, CookieSpec cookieSpec, CookieOrigin cookieOrigin, CookieStore cookieStore)
/*     */   {
/* 111 */     while (iterator.hasNext()) {
/* 112 */       Header header = iterator.nextHeader();
/*     */       try {
/* 114 */         List<Cookie> cookies = cookieSpec.parse(header, cookieOrigin);
/* 115 */         for (Cookie cookie : cookies) {
/*     */           try {
/* 117 */             cookieSpec.validate(cookie, cookieOrigin);
/* 118 */             cookieStore.addCookie(cookie);
/*     */             
/* 120 */             if (this.log.isDebugEnabled()) {
/* 121 */               this.log.debug("Cookie accepted: \"" + cookie + "\". ");
/*     */             }
/*     */           }
/*     */           catch (MalformedCookieException ex) {
/* 125 */             if (this.log.isWarnEnabled()) {
/* 126 */               this.log.warn("Cookie rejected: \"" + cookie + "\". " + ex.getMessage());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (MalformedCookieException ex) {
/* 132 */         if (this.log.isWarnEnabled()) {
/* 133 */           this.log.warn("Invalid cookie header: \"" + header + "\". " + ex.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/protocol/ResponseProcessCookies.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */