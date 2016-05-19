/*    */ package org.shaded.apache.http.impl.cookie;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.cookie.ClientCookie;
/*    */ import org.shaded.apache.http.cookie.Cookie;
/*    */ import org.shaded.apache.http.cookie.CookieAttributeHandler;
/*    */ import org.shaded.apache.http.cookie.CookieOrigin;
/*    */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*    */ import org.shaded.apache.http.cookie.SetCookie;
/*    */ import org.shaded.apache.http.cookie.SetCookie2;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class RFC2965VersionAttributeHandler
/*    */   implements CookieAttributeHandler
/*    */ {
/*    */   public void parse(SetCookie cookie, String value)
/*    */     throws MalformedCookieException
/*    */   {
/* 57 */     if (cookie == null) {
/* 58 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 60 */     if (value == null) {
/* 61 */       throw new MalformedCookieException("Missing value for version attribute");
/*    */     }
/*    */     
/* 64 */     int version = -1;
/*    */     try {
/* 66 */       version = Integer.parseInt(value);
/*    */     } catch (NumberFormatException e) {
/* 68 */       version = -1;
/*    */     }
/* 70 */     if (version < 0) {
/* 71 */       throw new MalformedCookieException("Invalid cookie version.");
/*    */     }
/* 73 */     cookie.setVersion(version);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void validate(Cookie cookie, CookieOrigin origin)
/*    */     throws MalformedCookieException
/*    */   {
/* 81 */     if (cookie == null) {
/* 82 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 84 */     if (((cookie instanceof SetCookie2)) && 
/* 85 */       ((cookie instanceof ClientCookie)) && (!((ClientCookie)cookie).containsAttribute("version")))
/*    */     {
/* 87 */       throw new MalformedCookieException("Violates RFC 2965. Version attribute is required.");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*    */   {
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2965VersionAttributeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */