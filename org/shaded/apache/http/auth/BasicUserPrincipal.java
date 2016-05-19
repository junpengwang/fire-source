/*    */ package org.shaded.apache.http.auth;
/*    */ 
/*    */ import java.security.Principal;
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.util.LangUtils;
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
/*    */ public final class BasicUserPrincipal
/*    */   implements Principal
/*    */ {
/*    */   private final String username;
/*    */   
/*    */   public BasicUserPrincipal(String username)
/*    */   {
/* 48 */     if (username == null) {
/* 49 */       throw new IllegalArgumentException("User name may not be null");
/*    */     }
/* 51 */     this.username = username;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 55 */     return this.username;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 60 */     int hash = 17;
/* 61 */     hash = LangUtils.hashCode(hash, this.username);
/* 62 */     return hash;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 67 */     if (o == null) return false;
/* 68 */     if (this == o) return true;
/* 69 */     if ((o instanceof BasicUserPrincipal)) {
/* 70 */       BasicUserPrincipal that = (BasicUserPrincipal)o;
/* 71 */       if (LangUtils.equals(this.username, that.username)) {
/* 72 */         return true;
/*    */       }
/*    */     }
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 80 */     StringBuilder buffer = new StringBuilder();
/* 81 */     buffer.append("[principal: ");
/* 82 */     buffer.append(this.username);
/* 83 */     buffer.append("]");
/* 84 */     return buffer.toString();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/BasicUserPrincipal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */