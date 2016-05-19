/*     */ package org.shaded.apache.http.conn.params;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import org.shaded.apache.http.HttpHost;
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
/*     */ @Immutable
/*     */ public class ConnRouteParams
/*     */   implements ConnRoutePNames
/*     */ {
/*  51 */   public static final HttpHost NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  58 */   public static final HttpRoute NO_ROUTE = new HttpRoute(NO_HOST);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static HttpHost getDefaultProxy(HttpParams params)
/*     */   {
/*  77 */     if (params == null) {
/*  78 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*  80 */     HttpHost proxy = (HttpHost)params.getParameter("http.route.default-proxy");
/*     */     
/*  82 */     if ((proxy != null) && (NO_HOST.equals(proxy)))
/*     */     {
/*  84 */       proxy = null;
/*     */     }
/*  86 */     return proxy;
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
/*     */   public static void setDefaultProxy(HttpParams params, HttpHost proxy)
/*     */   {
/* 101 */     if (params == null) {
/* 102 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/* 104 */     params.setParameter("http.route.default-proxy", proxy);
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
/*     */   public static HttpRoute getForcedRoute(HttpParams params)
/*     */   {
/* 119 */     if (params == null) {
/* 120 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/* 122 */     HttpRoute route = (HttpRoute)params.getParameter("http.route.forced-route");
/*     */     
/* 124 */     if ((route != null) && (NO_ROUTE.equals(route)))
/*     */     {
/* 126 */       route = null;
/*     */     }
/* 128 */     return route;
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
/*     */   public static void setForcedRoute(HttpParams params, HttpRoute route)
/*     */   {
/* 143 */     if (params == null) {
/* 144 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/* 146 */     params.setParameter("http.route.forced-route", route);
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
/*     */   public static InetAddress getLocalAddress(HttpParams params)
/*     */   {
/* 162 */     if (params == null) {
/* 163 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/* 165 */     InetAddress local = (InetAddress)params.getParameter("http.route.local-address");
/*     */     
/*     */ 
/* 168 */     return local;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setLocalAddress(HttpParams params, InetAddress local)
/*     */   {
/* 180 */     if (params == null) {
/* 181 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/* 183 */     params.setParameter("http.route.local-address", local);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/params/ConnRouteParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */