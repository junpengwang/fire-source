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
/*     */ public class NTCredentials
/*     */   implements Credentials
/*     */ {
/*     */   private final NTUserPrincipal principal;
/*     */   private final String password;
/*     */   private final String workstation;
/*     */   
/*     */   public NTCredentials(String usernamePassword)
/*     */   {
/*  62 */     if (usernamePassword == null) {
/*  63 */       throw new IllegalArgumentException("Username:password string may not be null");
/*     */     }
/*     */     
/*  66 */     int atColon = usernamePassword.indexOf(':');
/*  67 */     String username; if (atColon >= 0) {
/*  68 */       String username = usernamePassword.substring(0, atColon);
/*  69 */       this.password = usernamePassword.substring(atColon + 1);
/*     */     } else {
/*  71 */       username = usernamePassword;
/*  72 */       this.password = null;
/*     */     }
/*  74 */     int atSlash = username.indexOf('/');
/*  75 */     if (atSlash >= 0) {
/*  76 */       this.principal = new NTUserPrincipal(username.substring(0, atSlash).toUpperCase(Locale.ENGLISH), username.substring(atSlash + 1));
/*     */     }
/*     */     else
/*     */     {
/*  80 */       this.principal = new NTUserPrincipal(null, username.substring(atSlash + 1));
/*     */     }
/*     */     
/*     */ 
/*  84 */     this.workstation = null;
/*     */   }
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
/*     */   public NTCredentials(String userName, String password, String workstation, String domain)
/*     */   {
/* 102 */     if (userName == null) {
/* 103 */       throw new IllegalArgumentException("User name may not be null");
/*     */     }
/* 105 */     this.principal = new NTUserPrincipal(domain, userName);
/* 106 */     this.password = password;
/* 107 */     if (workstation != null) {
/* 108 */       this.workstation = workstation.toUpperCase(Locale.ENGLISH);
/*     */     } else {
/* 110 */       this.workstation = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public Principal getUserPrincipal() {
/* 115 */     return this.principal;
/*     */   }
/*     */   
/*     */   public String getUserName() {
/* 119 */     return this.principal.getUsername();
/*     */   }
/*     */   
/*     */   public String getPassword() {
/* 123 */     return this.password;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDomain()
/*     */   {
/* 132 */     return this.principal.getDomain();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getWorkstation()
/*     */   {
/* 141 */     return this.workstation;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 146 */     int hash = 17;
/* 147 */     hash = LangUtils.hashCode(hash, this.principal);
/* 148 */     hash = LangUtils.hashCode(hash, this.workstation);
/* 149 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 154 */     if (o == null) return false;
/* 155 */     if (this == o) return true;
/* 156 */     if ((o instanceof NTCredentials)) {
/* 157 */       NTCredentials that = (NTCredentials)o;
/* 158 */       if ((LangUtils.equals(this.principal, that.principal)) && (LangUtils.equals(this.workstation, that.workstation)))
/*     */       {
/* 160 */         return true;
/*     */       }
/*     */     }
/* 163 */     return false;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 168 */     StringBuilder buffer = new StringBuilder();
/* 169 */     buffer.append("[principal: ");
/* 170 */     buffer.append(this.principal);
/* 171 */     buffer.append("][workstation: ");
/* 172 */     buffer.append(this.workstation);
/* 173 */     buffer.append("]");
/* 174 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/NTCredentials.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */