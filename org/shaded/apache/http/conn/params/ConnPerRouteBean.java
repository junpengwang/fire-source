/*     */ package org.shaded.apache.http.conn.params;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class ConnPerRouteBean
/*     */   implements ConnPerRoute
/*     */ {
/*     */   public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
/*     */   private final Map<HttpRoute, Integer> maxPerHostMap;
/*     */   private int defaultMax;
/*     */   
/*     */   public ConnPerRouteBean(int defaultMax)
/*     */   {
/*  56 */     this.maxPerHostMap = new HashMap();
/*  57 */     setDefaultMaxPerRoute(defaultMax);
/*     */   }
/*     */   
/*     */   public ConnPerRouteBean() {
/*  61 */     this(2);
/*     */   }
/*     */   
/*     */   public int getDefaultMax() {
/*  65 */     return this.defaultMax;
/*     */   }
/*     */   
/*     */   public void setDefaultMaxPerRoute(int max) {
/*  69 */     if (max < 1) {
/*  70 */       throw new IllegalArgumentException("The maximum must be greater than 0.");
/*     */     }
/*     */     
/*  73 */     this.defaultMax = max;
/*     */   }
/*     */   
/*     */   public void setMaxForRoute(HttpRoute route, int max) {
/*  77 */     if (route == null) {
/*  78 */       throw new IllegalArgumentException("HTTP route may not be null.");
/*     */     }
/*     */     
/*  81 */     if (max < 1) {
/*  82 */       throw new IllegalArgumentException("The maximum must be greater than 0.");
/*     */     }
/*     */     
/*  85 */     this.maxPerHostMap.put(route, Integer.valueOf(max));
/*     */   }
/*     */   
/*     */   public int getMaxForRoute(HttpRoute route) {
/*  89 */     if (route == null) {
/*  90 */       throw new IllegalArgumentException("HTTP route may not be null.");
/*     */     }
/*     */     
/*  93 */     Integer max = (Integer)this.maxPerHostMap.get(route);
/*  94 */     if (max != null) {
/*  95 */       return max.intValue();
/*     */     }
/*  97 */     return this.defaultMax;
/*     */   }
/*     */   
/*     */   public void setMaxForRoutes(Map<HttpRoute, Integer> map)
/*     */   {
/* 102 */     if (map == null) {
/* 103 */       return;
/*     */     }
/* 105 */     this.maxPerHostMap.clear();
/* 106 */     this.maxPerHostMap.putAll(map);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 111 */     return this.maxPerHostMap.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/params/ConnPerRouteBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */