/*    */ package org.shaded.apache.http.impl.cookie;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.cookie.Cookie;
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
/*    */ public class RFC2109VersionHandler
/*    */   extends AbstractCookieAttributeHandler
/*    */ {
/*    */   public void parse(SetCookie cookie, String value)
/*    */     throws MalformedCookieException
/*    */   {
/* 49 */     if (cookie == null) {
/* 50 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 52 */     if (value == null) {
/* 53 */       throw new MalformedCookieException("Missing value for version attribute");
/*    */     }
/* 55 */     if (value.trim().length() == 0) {
/* 56 */       throw new MalformedCookieException("Blank value for version attribute");
/*    */     }
/*    */     try {
/* 59 */       cookie.setVersion(Integer.parseInt(value));
/*    */     } catch (NumberFormatException e) {
/* 61 */       throw new MalformedCookieException("Invalid version: " + e.getMessage());
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void validate(Cookie cookie, CookieOrigin origin)
/*    */     throws MalformedCookieException
/*    */   {
/* 69 */     if (cookie == null) {
/* 70 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 72 */     if (cookie.getVersion() < 0) {
/* 73 */       throw new MalformedCookieException("Cookie version may not be negative");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2109VersionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */