/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.conn.ClientConnectionManager;
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
/*     */ public abstract class AbstractPooledConnAdapter
/*     */   extends AbstractClientConnAdapter
/*     */ {
/*     */   protected volatile AbstractPoolEntry poolEntry;
/*     */   
/*     */   protected AbstractPooledConnAdapter(ClientConnectionManager manager, AbstractPoolEntry entry)
/*     */   {
/*  63 */     super(manager, entry.connection);
/*  64 */     this.poolEntry = entry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected final void assertAttached()
/*     */   {
/*  76 */     if (this.poolEntry == null) {
/*  77 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected synchronized void detach()
/*     */   {
/*  87 */     super.detach();
/*  88 */     this.poolEntry = null;
/*     */   }
/*     */   
/*     */   public HttpRoute getRoute() {
/*  92 */     AbstractPoolEntry entry = this.poolEntry;
/*  93 */     if (entry == null) {
/*  94 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/*  96 */     return entry.tracker == null ? null : entry.tracker.toRoute();
/*     */   }
/*     */   
/*     */ 
/*     */   public void open(HttpRoute route, HttpContext context, HttpParams params)
/*     */     throws IOException
/*     */   {
/* 103 */     assertNotAborted();
/* 104 */     AbstractPoolEntry entry = this.poolEntry;
/* 105 */     if (entry == null) {
/* 106 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/* 108 */     entry.open(route, context, params);
/*     */   }
/*     */   
/*     */   public void tunnelTarget(boolean secure, HttpParams params) throws IOException
/*     */   {
/* 113 */     assertNotAborted();
/* 114 */     AbstractPoolEntry entry = this.poolEntry;
/* 115 */     if (entry == null) {
/* 116 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/* 118 */     entry.tunnelTarget(secure, params);
/*     */   }
/*     */   
/*     */   public void tunnelProxy(HttpHost next, boolean secure, HttpParams params) throws IOException
/*     */   {
/* 123 */     assertNotAborted();
/* 124 */     AbstractPoolEntry entry = this.poolEntry;
/* 125 */     if (entry == null) {
/* 126 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/* 128 */     entry.tunnelProxy(next, secure, params);
/*     */   }
/*     */   
/*     */   public void layerProtocol(HttpContext context, HttpParams params) throws IOException
/*     */   {
/* 133 */     assertNotAborted();
/* 134 */     AbstractPoolEntry entry = this.poolEntry;
/* 135 */     if (entry == null) {
/* 136 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/* 138 */     entry.layerProtocol(context, params);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 142 */     AbstractPoolEntry entry = this.poolEntry;
/* 143 */     if (entry != null) {
/* 144 */       entry.shutdownEntry();
/*     */     }
/* 146 */     OperatedClientConnection conn = getWrappedConnection();
/* 147 */     if (conn != null) {
/* 148 */       conn.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void shutdown() throws IOException {
/* 153 */     AbstractPoolEntry entry = this.poolEntry;
/* 154 */     if (entry != null) {
/* 155 */       entry.shutdownEntry();
/*     */     }
/* 157 */     OperatedClientConnection conn = getWrappedConnection();
/* 158 */     if (conn != null) {
/* 159 */       conn.shutdown();
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getState() {
/* 164 */     AbstractPoolEntry entry = this.poolEntry;
/* 165 */     if (entry == null) {
/* 166 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/* 168 */     return entry.getState();
/*     */   }
/*     */   
/*     */   public void setState(Object state) {
/* 172 */     AbstractPoolEntry entry = this.poolEntry;
/* 173 */     if (entry == null) {
/* 174 */       throw new IllegalStateException("Adapter is detached.");
/*     */     }
/* 176 */     entry.setState(state);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/AbstractPooledConnAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */