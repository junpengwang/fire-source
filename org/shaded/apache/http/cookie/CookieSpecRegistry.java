/*     */ package org.shaded.apache.http.cookie;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public final class CookieSpecRegistry
/*     */ {
/*     */   @GuardedBy("this")
/*     */   private final Map<String, CookieSpecFactory> registeredSpecs;
/*     */   
/*     */   public CookieSpecRegistry()
/*     */   {
/*  57 */     this.registeredSpecs = new LinkedHashMap();
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
/*     */   public synchronized void register(String name, CookieSpecFactory factory)
/*     */   {
/*  72 */     if (name == null) {
/*  73 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/*  75 */     if (factory == null) {
/*  76 */       throw new IllegalArgumentException("Cookie spec factory may not be null");
/*     */     }
/*  78 */     this.registeredSpecs.put(name.toLowerCase(Locale.ENGLISH), factory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void unregister(String id)
/*     */   {
/*  87 */     if (id == null) {
/*  88 */       throw new IllegalArgumentException("Id may not be null");
/*     */     }
/*  90 */     this.registeredSpecs.remove(id.toLowerCase(Locale.ENGLISH));
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
/*     */   public synchronized CookieSpec getCookieSpec(String name, HttpParams params)
/*     */     throws IllegalStateException
/*     */   {
/* 107 */     if (name == null) {
/* 108 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/* 110 */     CookieSpecFactory factory = (CookieSpecFactory)this.registeredSpecs.get(name.toLowerCase(Locale.ENGLISH));
/* 111 */     if (factory != null) {
/* 112 */       return factory.newInstance(params);
/*     */     }
/* 114 */     throw new IllegalStateException("Unsupported cookie spec: " + name);
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
/*     */   public synchronized CookieSpec getCookieSpec(String name)
/*     */     throws IllegalStateException
/*     */   {
/* 129 */     return getCookieSpec(name, null);
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
/*     */   public synchronized List<String> getSpecNames()
/*     */   {
/* 142 */     return new ArrayList(this.registeredSpecs.keySet());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setItems(Map<String, CookieSpecFactory> map)
/*     */   {
/* 152 */     if (map == null) {
/* 153 */       return;
/*     */     }
/* 155 */     this.registeredSpecs.clear();
/* 156 */     this.registeredSpecs.putAll(map);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/CookieSpecRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */