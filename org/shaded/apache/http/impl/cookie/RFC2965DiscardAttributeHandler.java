/*    */ package org.shaded.apache.http.impl.cookie;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
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
/*    */ @Immutable
/*    */ public class RFC2965DiscardAttributeHandler
/*    */   implements CookieAttributeHandler
/*    */ {
/*    */   public void parse(SetCookie cookie, String commenturl)
/*    */     throws MalformedCookieException
/*    */   {
/* 53 */     if ((cookie instanceof SetCookie2)) {
/* 54 */       SetCookie2 cookie2 = (SetCookie2)cookie;
/* 55 */       cookie2.setDiscard(true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException
/*    */   {}
/*    */   
/*    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*    */   {
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2965DiscardAttributeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */