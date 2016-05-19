/*    */ package org.shaded.apache.http.impl.cookie;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.cookie.Cookie;
/*    */ import org.shaded.apache.http.cookie.CookieAttributeHandler;
/*    */ import org.shaded.apache.http.cookie.CookieOrigin;
/*    */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*    */ import org.shaded.apache.http.cookie.SetCookie;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class BasicPathHandler
/*    */   implements CookieAttributeHandler
/*    */ {
/*    */   public void parse(SetCookie cookie, String value)
/*    */     throws MalformedCookieException
/*    */   {
/* 50 */     if (cookie == null) {
/* 51 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 53 */     if ((value == null) || (value.trim().length() == 0)) {
/* 54 */       value = "/";
/*    */     }
/* 56 */     cookie.setPath(value);
/*    */   }
/*    */   
/*    */   public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException
/*    */   {
/* 61 */     if (!match(cookie, origin)) {
/* 62 */       throw new MalformedCookieException("Illegal path attribute \"" + cookie.getPath() + "\". Path of origin: \"" + origin.getPath() + "\"");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*    */   {
/* 69 */     if (cookie == null) {
/* 70 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 72 */     if (origin == null) {
/* 73 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*    */     }
/* 75 */     String targetpath = origin.getPath();
/* 76 */     String topmostPath = cookie.getPath();
/* 77 */     if (topmostPath == null) {
/* 78 */       topmostPath = "/";
/*    */     }
/* 80 */     if ((topmostPath.length() > 1) && (topmostPath.endsWith("/"))) {
/* 81 */       topmostPath = topmostPath.substring(0, topmostPath.length() - 1);
/*    */     }
/* 83 */     boolean match = targetpath.startsWith(topmostPath);
/*    */     
/*    */ 
/* 86 */     if ((match) && (targetpath.length() != topmostPath.length()) && 
/* 87 */       (!topmostPath.endsWith("/"))) {
/* 88 */       match = targetpath.charAt(topmostPath.length()) == '/';
/*    */     }
/*    */     
/* 91 */     return match;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BasicPathHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */