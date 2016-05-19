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
/*    */ public class BasicSecureHandler
/*    */   extends AbstractCookieAttributeHandler
/*    */ {
/*    */   public void parse(SetCookie cookie, String value)
/*    */     throws MalformedCookieException
/*    */   {
/* 49 */     if (cookie == null) {
/* 50 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 52 */     cookie.setSecure(true);
/*    */   }
/*    */   
/*    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*    */   {
/* 57 */     if (cookie == null) {
/* 58 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 60 */     if (origin == null) {
/* 61 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*    */     }
/* 63 */     return (!cookie.isSecure()) || (origin.isSecure());
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BasicSecureHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */