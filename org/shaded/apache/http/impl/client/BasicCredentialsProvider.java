/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import org.shaded.apache.http.annotation.GuardedBy;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.auth.AuthScope;
/*     */ import org.shaded.apache.http.auth.Credentials;
/*     */ import org.shaded.apache.http.client.CredentialsProvider;
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
/*     */ public class BasicCredentialsProvider
/*     */   implements CredentialsProvider
/*     */ {
/*     */   @GuardedBy("this")
/*     */   private final HashMap<AuthScope, Credentials> credMap;
/*     */   
/*     */   public BasicCredentialsProvider()
/*     */   {
/*  54 */     this.credMap = new HashMap();
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void setCredentials(AuthScope authscope, Credentials credentials)
/*     */   {
/*  60 */     if (authscope == null) {
/*  61 */       throw new IllegalArgumentException("Authentication scope may not be null");
/*     */     }
/*  63 */     this.credMap.put(authscope, credentials);
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
/*     */   private static Credentials matchCredentials(HashMap<AuthScope, Credentials> map, AuthScope authscope)
/*     */   {
/*  78 */     Credentials creds = (Credentials)map.get(authscope);
/*  79 */     if (creds == null)
/*     */     {
/*     */ 
/*  82 */       int bestMatchFactor = -1;
/*  83 */       AuthScope bestMatch = null;
/*  84 */       for (AuthScope current : map.keySet()) {
/*  85 */         int factor = authscope.match(current);
/*  86 */         if (factor > bestMatchFactor) {
/*  87 */           bestMatchFactor = factor;
/*  88 */           bestMatch = current;
/*     */         }
/*     */       }
/*  91 */       if (bestMatch != null) {
/*  92 */         creds = (Credentials)map.get(bestMatch);
/*     */       }
/*     */     }
/*  95 */     return creds;
/*     */   }
/*     */   
/*     */   public synchronized Credentials getCredentials(AuthScope authscope) {
/*  99 */     if (authscope == null) {
/* 100 */       throw new IllegalArgumentException("Authentication scope may not be null");
/*     */     }
/* 102 */     return matchCredentials(this.credMap, authscope);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 107 */     return this.credMap.toString();
/*     */   }
/*     */   
/*     */   public synchronized void clear() {
/* 111 */     this.credMap.clear();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/BasicCredentialsProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */