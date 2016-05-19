/*     */ package org.shaded.apache.http.auth;
/*     */ 
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
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
/*     */ @NotThreadSafe
/*     */ public class AuthState
/*     */ {
/*     */   private AuthScheme authScheme;
/*     */   private AuthScope authScope;
/*     */   private Credentials credentials;
/*     */   
/*     */   public void invalidate()
/*     */   {
/*  63 */     this.authScheme = null;
/*  64 */     this.authScope = null;
/*  65 */     this.credentials = null;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/*  69 */     return this.authScheme != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAuthScheme(AuthScheme authScheme)
/*     */   {
/*  78 */     if (authScheme == null) {
/*  79 */       invalidate();
/*  80 */       return;
/*     */     }
/*  82 */     this.authScheme = authScheme;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AuthScheme getAuthScheme()
/*     */   {
/*  91 */     return this.authScheme;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Credentials getCredentials()
/*     */   {
/* 101 */     return this.credentials;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCredentials(Credentials credentials)
/*     */   {
/* 111 */     this.credentials = credentials;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AuthScope getAuthScope()
/*     */   {
/* 121 */     return this.authScope;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAuthScope(AuthScope authScope)
/*     */   {
/* 130 */     this.authScope = authScope;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 136 */     StringBuilder buffer = new StringBuilder();
/* 137 */     buffer.append("auth scope [");
/* 138 */     buffer.append(this.authScope);
/* 139 */     buffer.append("]; credentials set [");
/* 140 */     buffer.append(this.credentials != null ? "true" : "false");
/* 141 */     buffer.append("]");
/* 142 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/AuthState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */