/*     */ package org.shaded.apache.http.conn.params;
/*     */ 
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class ConnManagerParams
/*     */   implements ConnManagerPNames
/*     */ {
/*     */   public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;
/*     */   
/*     */   public static long getTimeout(HttpParams params)
/*     */   {
/*  56 */     if (params == null) {
/*  57 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  59 */     return params.getLongParameter("http.conn-manager.timeout", 0L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setTimeout(HttpParams params, long timeout)
/*     */   {
/*  70 */     if (params == null) {
/*  71 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  73 */     params.setLongParameter("http.conn-manager.timeout", timeout);
/*     */   }
/*     */   
/*     */ 
/*  77 */   private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE = new ConnPerRoute()
/*     */   {
/*     */     public int getMaxForRoute(HttpRoute route) {
/*  80 */       return 2;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setMaxConnectionsPerRoute(HttpParams params, ConnPerRoute connPerRoute)
/*     */   {
/*  96 */     if (params == null) {
/*  97 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*     */     }
/*     */     
/* 100 */     params.setParameter("http.conn-manager.max-per-route", connPerRoute);
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
/*     */   public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams params)
/*     */   {
/* 113 */     if (params == null) {
/* 114 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*     */     }
/*     */     
/* 117 */     ConnPerRoute connPerRoute = (ConnPerRoute)params.getParameter("http.conn-manager.max-per-route");
/* 118 */     if (connPerRoute == null) {
/* 119 */       connPerRoute = DEFAULT_CONN_PER_ROUTE;
/*     */     }
/* 121 */     return connPerRoute;
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
/*     */   public static void setMaxTotalConnections(HttpParams params, int maxTotalConnections)
/*     */   {
/* 136 */     if (params == null) {
/* 137 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*     */     }
/*     */     
/* 140 */     params.setIntParameter("http.conn-manager.max-total", maxTotalConnections);
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
/*     */   public static int getMaxTotalConnections(HttpParams params)
/*     */   {
/* 154 */     if (params == null) {
/* 155 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*     */     }
/*     */     
/* 158 */     return params.getIntParameter("http.conn-manager.max-total", 20);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/params/ConnManagerParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */