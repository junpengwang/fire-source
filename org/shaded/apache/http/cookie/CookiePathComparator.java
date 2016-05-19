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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class CookiePathComparator
/*    */   implements Serializable, Comparator<Cookie>
/*    */ {
/*    */   private static final long serialVersionUID = 7523645369616405818L;
/*    */   
/*    */   private String normalizePath(Cookie cookie)
/*    */   {
/* 56 */     String path = cookie.getPath();
/* 57 */     if (path == null) {
/* 58 */       path = "/";
/*    */     }
/* 60 */     if (!path.endsWith("/")) {
/* 61 */       path = path + '/';
/*    */     }
/* 63 */     return path;
/*    */   }
/*    */   
/*    */   public int compare(Cookie c1, Cookie c2) {
/* 67 */     String path1 = normalizePath(c1);
/* 68 */     String path2 = normalizePath(c2);
/* 69 */     if (path1.equals(path2))
/* 70 */       return 0;
/* 71 */     if (path1.startsWith(path2))
/* 72 */       return -1;
/* 73 */     if (path2.startsWith(path1)) {
/* 74 */       return 1;
/*    */     }
/*    */     
/* 77 */     return 0;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/CookiePathComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */