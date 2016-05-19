/*    */ package org.shaded.apache.http.impl.cookie;
/*    */ 
/*    */ import java.util.Date;
/*    */ import org.shaded.apache.http.annotation.Immutable;
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
/*    */ 
/*    */ @Immutable
/*    */ public class BasicMaxAgeHandler
/*    */   extends AbstractCookieAttributeHandler
/*    */ {
/*    */   public void parse(SetCookie cookie, String value)
/*    */     throws MalformedCookieException
/*    */   {
/* 49 */     if (cookie == null) {
/* 50 */       throw new IllegalArgumentException("Cookie may not be null");
/*    */     }
/* 52 */     if (value == null) {
/* 53 */       throw new MalformedCookieException("Missing value for max-age attribute");
/*    */     }
/*    */     int age;
/*    */     try {
/* 57 */       age = Integer.parseInt(value);
/*    */     } catch (NumberFormatException e) {
/* 59 */       throw new MalformedCookieException("Invalid max-age attribute: " + value);
/*    */     }
/*    */     
/* 62 */     if (age < 0) {
/* 63 */       throw new MalformedCookieException("Negative max-age attribute: " + value);
/*    */     }
/*    */     
/* 66 */     cookie.setExpiryDate(new Date(System.currentTimeMillis() + age * 1000L));
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BasicMaxAgeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */