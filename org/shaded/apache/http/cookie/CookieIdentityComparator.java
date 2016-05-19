/*    */ package org.shaded.apache.http.cookie;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ import org.shaded.apache.http.annotation.Immutable;
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
/*    */ public class CookieIdentityComparator
/*    */   implements Serializable, Comparator<Cookie>
/*    */ {
/*    */   private static final long serialVersionUID = 4466565437490631532L;
/*    */   
/*    */   public int compare(Cookie c1, Cookie c2)
/*    */   {
/* 49 */     int res = c1.getName().compareTo(c2.getName());
/* 50 */     if (res == 0)
/*    */     {
/* 52 */       String d1 = c1.getDomain();
/* 53 */       if (d1 == null) {
/* 54 */         d1 = "";
/* 55 */       } else if (d1.indexOf('.') == -1) {
/* 56 */         d1 = d1 + ".local";
/*    */       }
/* 58 */       String d2 = c2.getDomain();
/* 59 */       if (d2 == null) {
/* 60 */         d2 = "";
/* 61 */       } else if (d2.indexOf('.') == -1) {
/* 62 */         d2 = d2 + ".local";
/*    */       }
/* 64 */       res = d1.compareToIgnoreCase(d2);
/*    */     }
/* 66 */     if (res == 0) {
/* 67 */       String p1 = c1.getPath();
/* 68 */       if (p1 == null) {
/* 69 */         p1 = "/";
/*    */       }
/* 71 */       String p2 = c2.getPath();
/* 72 */       if (p2 == null) {
/* 73 */         p2 = "/";
/*    */       }
/* 75 */       res = p1.compareTo(p2);
/*    */     }
/* 77 */     return res;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/CookieIdentityComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */