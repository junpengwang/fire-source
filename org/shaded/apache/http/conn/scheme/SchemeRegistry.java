/*     */ package org.shaded.apache.http.conn.scheme;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.annotation.GuardedBy;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
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
/*     */ @ThreadSafe
/*     */ public final class SchemeRegistry
/*     */ {
/*     */   @GuardedBy("this")
/*     */   private final Map<String, Scheme> registeredSchemes;
/*     */   
/*     */   public SchemeRegistry()
/*     */   {
/*  57 */     this.registeredSchemes = new LinkedHashMap();
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
/*     */   public final synchronized Scheme getScheme(String name)
/*     */   {
/*  71 */     Scheme found = get(name);
/*  72 */     if (found == null) {
/*  73 */       throw new IllegalStateException("Scheme '" + name + "' not registered.");
/*     */     }
/*     */     
/*  76 */     return found;
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
/*     */   public final synchronized Scheme getScheme(HttpHost host)
/*     */   {
/*  91 */     if (host == null) {
/*  92 */       throw new IllegalArgumentException("Host must not be null.");
/*     */     }
/*  94 */     return getScheme(host.getSchemeName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized Scheme get(String name)
/*     */   {
/* 106 */     if (name == null) {
/* 107 */       throw new IllegalArgumentException("Name must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 111 */     Scheme found = (Scheme)this.registeredSchemes.get(name);
/* 112 */     return found;
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
/*     */   public final synchronized Scheme register(Scheme sch)
/*     */   {
/* 126 */     if (sch == null) {
/* 127 */       throw new IllegalArgumentException("Scheme must not be null.");
/*     */     }
/* 129 */     Scheme old = (Scheme)this.registeredSchemes.put(sch.getName(), sch);
/* 130 */     return old;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized Scheme unregister(String name)
/*     */   {
/* 142 */     if (name == null) {
/* 143 */       throw new IllegalArgumentException("Name must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 147 */     Scheme gone = (Scheme)this.registeredSchemes.remove(name);
/* 148 */     return gone;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized List<String> getSchemeNames()
/*     */   {
/* 157 */     return new ArrayList(this.registeredSchemes.keySet());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setItems(Map<String, Scheme> map)
/*     */   {
/* 167 */     if (map == null) {
/* 168 */       return;
/*     */     }
/* 170 */     this.registeredSchemes.clear();
/* 171 */     this.registeredSchemes.putAll(map);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/scheme/SchemeRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */