/*     */ package org.shaded.apache.http.auth;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.util.LangUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class UsernamePasswordCredentials
/*     */   implements Credentials
/*     */ {
/*     */   private final BasicUserPrincipal principal;
/*     */   private final String password;
/*     */   
/*     */   public UsernamePasswordCredentials(String usernamePassword)
/*     */   {
/*  55 */     if (usernamePassword == null) {
/*  56 */       throw new IllegalArgumentException("Username:password string may not be null");
/*     */     }
/*  58 */     int atColon = usernamePassword.indexOf(':');
/*  59 */     if (atColon >= 0) {
/*  60 */       this.principal = new BasicUserPrincipal(usernamePassword.substring(0, atColon));
/*  61 */       this.password = usernamePassword.substring(atColon + 1);
/*     */     } else {
/*  63 */       this.principal = new BasicUserPrincipal(usernamePassword);
/*  64 */       this.password = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UsernamePasswordCredentials(String userName, String password)
/*     */   {
/*  77 */     if (userName == null) {
/*  78 */       throw new IllegalArgumentException("Username may not be null");
/*     */     }
/*  80 */     this.principal = new BasicUserPrincipal(userName);
/*  81 */     this.password = password;
/*     */   }
/*     */   
/*     */   public Principal getUserPrincipal() {
/*  85 */     return this.principal;
/*     */   }
/*     */   
/*     */   public String getUserName() {
/*  89 */     return this.principal.getName();
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  93 */     return this.password;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  98 */     return this.principal.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 103 */     if (o == null) return false;
/* 104 */     if (this == o) return true;
/* 105 */     if ((o instanceof UsernamePasswordCredentials)) {
/* 106 */       UsernamePasswordCredentials that = (UsernamePasswordCredentials)o;
/* 107 */       if (LangUtils.equals(this.principal, that.principal)) {
/* 108 */         return true;
/*     */       }
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 116 */     return this.principal.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/UsernamePasswordCredentials.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */