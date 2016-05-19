/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.NameValuePair;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieAttributeHandler;
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
/*     */ @NotThreadSafe
/*     */ public abstract class CookieSpecBase
/*     */   extends AbstractCookieSpec
/*     */ {
/*     */   protected static String getDefaultPath(CookieOrigin origin)
/*     */   {
/*  53 */     String defaultPath = origin.getPath();
/*  54 */     int lastSlashIndex = defaultPath.lastIndexOf('/');
/*  55 */     if (lastSlashIndex >= 0) {
/*  56 */       if (lastSlashIndex == 0)
/*     */       {
/*  58 */         lastSlashIndex = 1;
/*     */       }
/*  60 */       defaultPath = defaultPath.substring(0, lastSlashIndex);
/*     */     }
/*  62 */     return defaultPath;
/*     */   }
/*     */   
/*     */   protected static String getDefaultDomain(CookieOrigin origin) {
/*  66 */     return origin.getHost();
/*     */   }
/*     */   
/*     */   protected List<Cookie> parse(HeaderElement[] elems, CookieOrigin origin) throws MalformedCookieException
/*     */   {
/*  71 */     List<Cookie> cookies = new ArrayList(elems.length);
/*  72 */     for (HeaderElement headerelement : elems) {
/*  73 */       String name = headerelement.getName();
/*  74 */       String value = headerelement.getValue();
/*  75 */       if ((name == null) || (name.length() == 0)) {
/*  76 */         throw new MalformedCookieException("Cookie name may not be empty");
/*     */       }
/*     */       
/*  79 */       BasicClientCookie cookie = new BasicClientCookie(name, value);
/*  80 */       cookie.setPath(getDefaultPath(origin));
/*  81 */       cookie.setDomain(getDefaultDomain(origin));
/*     */       
/*     */ 
/*  84 */       NameValuePair[] attribs = headerelement.getParameters();
/*  85 */       for (int j = attribs.length - 1; j >= 0; j--) {
/*  86 */         NameValuePair attrib = attribs[j];
/*  87 */         String s = attrib.getName().toLowerCase(Locale.ENGLISH);
/*     */         
/*  89 */         cookie.setAttribute(s, attrib.getValue());
/*     */         
/*  91 */         CookieAttributeHandler handler = findAttribHandler(s);
/*  92 */         if (handler != null) {
/*  93 */           handler.parse(cookie, attrib.getValue());
/*     */         }
/*     */       }
/*  96 */       cookies.add(cookie);
/*     */     }
/*  98 */     return cookies;
/*     */   }
/*     */   
/*     */   public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException
/*     */   {
/* 103 */     if (cookie == null) {
/* 104 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 106 */     if (origin == null) {
/* 107 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 109 */     for (CookieAttributeHandler handler : getAttribHandlers()) {
/* 110 */       handler.validate(cookie, origin);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean match(Cookie cookie, CookieOrigin origin) {
/* 115 */     if (cookie == null) {
/* 116 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 118 */     if (origin == null) {
/* 119 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 121 */     for (CookieAttributeHandler handler : getAttribHandlers()) {
/* 122 */       if (!handler.match(cookie, origin)) {
/* 123 */         return false;
/*     */       }
/*     */     }
/* 126 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/CookieSpecBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */