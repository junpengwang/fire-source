/*     */ package org.shaded.apache.http.auth;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.shaded.apache.http.annotation.GuardedBy;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.params.HttpParams;
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
/*     */ @ThreadSafe
/*     */ public final class AuthSchemeRegistry
/*     */ {
/*     */   @GuardedBy("this")
/*     */   private final Map<String, AuthSchemeFactory> registeredSchemes;
/*     */   
/*     */   public AuthSchemeRegistry()
/*     */   {
/*  54 */     this.registeredSchemes = new LinkedHashMap();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void register(String name, AuthSchemeFactory factory)
/*     */   {
/*  75 */     if (name == null) {
/*  76 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/*  78 */     if (factory == null) {
/*  79 */       throw new IllegalArgumentException("Authentication scheme factory may not be null");
/*     */     }
/*  81 */     this.registeredSchemes.put(name.toLowerCase(Locale.ENGLISH), factory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void unregister(String name)
/*     */   {
/*  91 */     if (name == null) {
/*  92 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/*  94 */     this.registeredSchemes.remove(name.toLowerCase(Locale.ENGLISH));
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
/*     */   public synchronized AuthScheme getAuthScheme(String name, HttpParams params)
/*     */     throws IllegalStateException
/*     */   {
/* 111 */     if (name == null) {
/* 112 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/* 114 */     AuthSchemeFactory factory = (AuthSchemeFactory)this.registeredSchemes.get(name.toLowerCase(Locale.ENGLISH));
/* 115 */     if (factory != null) {
/* 116 */       return factory.newInstance(params);
/*     */     }
/* 118 */     throw new IllegalStateException("Unsupported authentication scheme: " + name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized List<String> getSchemeNames()
/*     */   {
/* 129 */     return new ArrayList(this.registeredSchemes.keySet());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setItems(Map<String, AuthSchemeFactory> map)
/*     */   {
/* 139 */     if (map == null) {
/* 140 */       return;
/*     */     }
/* 142 */     this.registeredSchemes.clear();
/* 143 */     this.registeredSchemes.putAll(map);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/AuthSchemeRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */