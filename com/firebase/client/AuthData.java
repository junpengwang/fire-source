/*     */ package com.firebase.client;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthData
/*     */ {
/*     */   private final String token;
/*     */   private final long expires;
/*     */   private final String uid;
/*     */   private final String provider;
/*     */   private final Map<String, Object> providerData;
/*     */   private final Map<String, Object> auth;
/*     */   
/*     */   public AuthData(String token, long expires, String uid, String provider, Map<String, Object> auth, Map<String, Object> providerData)
/*     */   {
/*  20 */     this.token = token;
/*  21 */     this.expires = expires;
/*  22 */     this.uid = uid;
/*  23 */     this.provider = provider;
/*  24 */     this.providerData = (providerData != null ? Collections.unmodifiableMap(providerData) : null);
/*  25 */     this.auth = (auth != null ? Collections.unmodifiableMap(auth) : null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getToken()
/*     */   {
/*  34 */     return this.token;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getExpires()
/*     */   {
/*  43 */     return this.expires;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getUid()
/*     */   {
/*  52 */     return this.uid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getProvider()
/*     */   {
/*  61 */     return this.provider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, Object> getProviderData()
/*     */   {
/*  70 */     return this.providerData;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, Object> getAuth()
/*     */   {
/*  78 */     return this.auth;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/*  83 */     if (this == o) return true;
/*  84 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*     */     }
/*  86 */     AuthData authData = (AuthData)o;
/*     */     
/*  88 */     if (this.provider != null ? !this.provider.equals(authData.provider) : authData.provider != null) return false;
/*  89 */     if (this.providerData != null ? !this.providerData.equals(authData.providerData) : authData.providerData != null)
/*  90 */       return false;
/*  91 */     if (this.auth != null ? !this.auth.equals(authData.auth) : authData.auth != null) return false;
/*  92 */     if (this.token != null ? !this.token.equals(authData.token) : authData.token != null) return false;
/*  93 */     if (this.expires != authData.expires) return false;
/*  94 */     if (this.uid != null ? !this.uid.equals(authData.uid) : authData.uid != null) { return false;
/*     */     }
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 101 */     int result = this.token != null ? this.token.hashCode() : 0;
/* 102 */     result = 31 * result + (this.uid != null ? this.uid.hashCode() : 0);
/* 103 */     result = 31 * result + (this.provider != null ? this.provider.hashCode() : 0);
/* 104 */     result = 31 * result + (this.providerData != null ? this.providerData.hashCode() : 0);
/* 105 */     result = 31 * result + (this.auth != null ? this.auth.hashCode() : 0);
/* 106 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return "AuthData{uid='" + this.uid + '\'' + ", provider='" + this.provider + '\'' + ", token='" + (this.token == null ? null : "***") + '\'' + ", expires='" + this.expires + '\'' + ", auth='" + this.auth + '\'' + ", providerData='" + this.providerData + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/AuthData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */