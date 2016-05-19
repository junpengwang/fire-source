/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.conn.ClientConnectionOperator;
/*     */ import org.shaded.apache.http.conn.OperatedClientConnection;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ import org.shaded.apache.http.conn.routing.RouteTracker;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ import org.shaded.apache.http.protocol.HttpContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractPoolEntry
/*     */ {
/*     */   protected final ClientConnectionOperator connOperator;
/*     */   protected final OperatedClientConnection connection;
/*     */   protected volatile HttpRoute route;
/*     */   protected volatile Object state;
/*     */   protected volatile RouteTracker tracker;
/*     */   
/*     */   protected AbstractPoolEntry(ClientConnectionOperator connOperator, HttpRoute route)
/*     */   {
/*  86 */     if (connOperator == null) {
/*  87 */       throw new IllegalArgumentException("Connection operator may not be null");
/*     */     }
/*  89 */     this.connOperator = connOperator;
/*  90 */     this.connection = connOperator.createConnection();
/*  91 */     this.route = route;
/*  92 */     this.tracker = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getState()
/*     */   {
/* 101 */     return this.state;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setState(Object state)
/*     */   {
/* 110 */     this.state = state;
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
/*     */   public void open(HttpRoute route, HttpContext context, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 126 */     if (route == null) {
/* 127 */       throw new IllegalArgumentException("Route must not be null.");
/*     */     }
/*     */     
/* 130 */     if (params == null) {
/* 131 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*     */     
/* 134 */     if ((this.tracker != null) && (this.tracker.isConnected())) {
/* 135 */       throw new IllegalStateException("Connection already open.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 144 */     this.tracker = new RouteTracker(route);
/* 145 */     HttpHost proxy = route.getProxyHost();
/*     */     
/* 147 */     this.connOperator.openConnection(this.connection, proxy != null ? proxy : route.getTargetHost(), route.getLocalAddress(), context, params);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 153 */     RouteTracker localTracker = this.tracker;
/*     */     
/*     */ 
/*     */ 
/* 157 */     if (localTracker == null) {
/* 158 */       throw new IOException("Request aborted");
/*     */     }
/*     */     
/* 161 */     if (proxy == null) {
/* 162 */       localTracker.connectTarget(this.connection.isSecure());
/*     */     } else {
/* 164 */       localTracker.connectProxy(proxy, this.connection.isSecure());
/*     */     }
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
/*     */   public void tunnelTarget(boolean secure, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 183 */     if (params == null) {
/* 184 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 188 */     if ((this.tracker == null) || (!this.tracker.isConnected())) {
/* 189 */       throw new IllegalStateException("Connection not open.");
/*     */     }
/* 191 */     if (this.tracker.isTunnelled()) {
/* 192 */       throw new IllegalStateException("Connection is already tunnelled.");
/*     */     }
/*     */     
/*     */ 
/* 196 */     this.connection.update(null, this.tracker.getTargetHost(), secure, params);
/*     */     
/* 198 */     this.tracker.tunnelTarget(secure);
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
/*     */   public void tunnelProxy(HttpHost next, boolean secure, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 219 */     if (next == null) {
/* 220 */       throw new IllegalArgumentException("Next proxy must not be null.");
/*     */     }
/*     */     
/* 223 */     if (params == null) {
/* 224 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 229 */     if ((this.tracker == null) || (!this.tracker.isConnected())) {
/* 230 */       throw new IllegalStateException("Connection not open.");
/*     */     }
/*     */     
/* 233 */     this.connection.update(null, next, secure, params);
/* 234 */     this.tracker.tunnelProxy(next, secure);
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
/*     */   public void layerProtocol(HttpContext context, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 249 */     if (params == null) {
/* 250 */       throw new IllegalArgumentException("Parameters must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 254 */     if ((this.tracker == null) || (!this.tracker.isConnected())) {
/* 255 */       throw new IllegalStateException("Connection not open.");
/*     */     }
/* 257 */     if (!this.tracker.isTunnelled())
/*     */     {
/* 259 */       throw new IllegalStateException("Protocol layering without a tunnel not supported.");
/*     */     }
/*     */     
/* 262 */     if (this.tracker.isLayered()) {
/* 263 */       throw new IllegalStateException("Multiple protocol layering not supported.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 273 */     HttpHost target = this.tracker.getTargetHost();
/*     */     
/* 275 */     this.connOperator.updateSecureConnection(this.connection, target, context, params);
/*     */     
/*     */ 
/* 278 */     this.tracker.layerProtocol(this.connection.isSecure());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void shutdownEntry()
/*     */   {
/* 289 */     this.tracker = null;
/* 290 */     this.state = null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/AbstractPoolEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */