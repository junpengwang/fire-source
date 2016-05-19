/*     */ package org.shaded.apache.http.auth;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.util.Locale;
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
/*     */ @Immutable
/*     */ public class NTUserPrincipal
/*     */   implements Principal
/*     */ {
/*     */   private final String username;
/*     */   private final String domain;
/*     */   private final String ntname;
/*     */   
/*     */   public NTUserPrincipal(String domain, String username)
/*     */   {
/*  52 */     if (username == null) {
/*  53 */       throw new IllegalArgumentException("User name may not be null");
/*     */     }
/*  55 */     this.username = username;
/*  56 */     if (domain != null) {
/*  57 */       this.domain = domain.toUpperCase(Locale.ENGLISH);
/*     */     } else {
/*  59 */       this.domain = null;
/*     */     }
/*  61 */     if ((this.domain != null) && (this.domain.length() > 0)) {
/*  62 */       StringBuilder buffer = new StringBuilder();
/*  63 */       buffer.append(this.domain);
/*  64 */       buffer.append('/');
/*  65 */       buffer.append(this.username);
/*  66 */       this.ntname = buffer.toString();
/*     */     } else {
/*  68 */       this.ntname = this.username;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName() {
/*  73 */     return this.ntname;
/*     */   }
/*     */   
/*     */   public String getDomain() {
/*  77 */     return this.domain;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/*  81 */     return this.username;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  86 */     int hash = 17;
/*  87 */     hash = LangUtils.hashCode(hash, this.username);
/*  88 */     hash = LangUtils.hashCode(hash, this.domain);
/*  89 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/*  94 */     if (o == null) return false;
/*  95 */     if (this == o) return true;
/*  96 */     if ((o instanceof NTUserPrincipal)) {
/*  97 */       NTUserPrincipal that = (NTUserPrincipal)o;
/*  98 */       if ((LangUtils.equals(this.username, that.username)) && (LangUtils.equals(this.domain, that.domain)))
/*     */       {
/* 100 */         return true;
/*     */       }
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 108 */     return this.ntname;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/NTUserPrincipal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */