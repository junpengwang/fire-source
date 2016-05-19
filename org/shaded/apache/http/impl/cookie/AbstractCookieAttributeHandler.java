/*    */ package org.shaded.apache.http.impl.cookie;
/*    */ 
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.cookie.Cookie;
/*    */ import org.shaded.apache.http.cookie.CookieAttributeHandler;
/*    */ import org.shaded.apache.http.cookie.CookieOrigin;
/*    */ import org.shaded.apache.http.cookie.MalformedCookieException;
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
/*    */ public abstract class AbstractCookieAttributeHandler
/*    */   implements CookieAttributeHandler
/*    */ {
/*    */   public void validate(Cookie cookie, CookieOrigin origin)
/*    */     throws MalformedCookieException
/*    */   {}
/*    */   
/*    */   public boolean match(Cookie cookie, CookieOrigin origin)
/*    */   {
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/AbstractCookieAttributeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */