/*     */ package org.shaded.apache.http.impl.conn.tsccm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.conn.ClientConnectionManager;
/*     */ import org.shaded.apache.http.conn.ClientConnectionOperator;
/*     */ import org.shaded.apache.http.conn.ClientConnectionRequest;
/*     */ import org.shaded.apache.http.conn.ConnectionPoolTimeoutException;
/*     */ import org.shaded.apache.http.conn.ManagedClientConnection;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ import org.shaded.apache.http.conn.scheme.SchemeRegistry;
/*     */ import org.shaded.apache.http.impl.conn.DefaultClientConnectionOperator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadSafeClientConnManager
/*     */   implements ClientConnectionManager
/*     */ {
/*  75 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SchemeRegistry schemeRegistry;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final AbstractConnPool connectionPool;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final ClientConnectionOperator connOperator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThreadSafeClientConnManager(HttpParams params, SchemeRegistry schreg)
/*     */   {
/*  95 */     if (params == null) {
/*  96 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  98 */     if (schreg == null) {
/*  99 */       throw new IllegalArgumentException("Scheme registry may not be null");
/*     */     }
/* 101 */     this.schemeRegistry = schreg;
/* 102 */     this.connOperator = createConnectionOperator(schreg);
/* 103 */     this.connectionPool = createConnectionPool(params);
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable
/*     */   {
/*     */     try
/*     */     {
/* 110 */       shutdown();
/*     */     } finally {
/* 112 */       super.finalize();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractConnPool createConnectionPool(HttpParams params)
/*     */   {
/* 122 */     return new ConnPoolByRoute(this.connOperator, params);
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
/*     */   protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg)
/*     */   {
/* 140 */     return new DefaultClientConnectionOperator(schreg);
/*     */   }
/*     */   
/*     */   public SchemeRegistry getSchemeRegistry() {
/* 144 */     return this.schemeRegistry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ClientConnectionRequest requestConnection(final HttpRoute route, Object state)
/*     */   {
/* 151 */     final PoolEntryRequest poolRequest = this.connectionPool.requestPoolEntry(route, state);
/*     */     
/*     */ 
/* 154 */     new ClientConnectionRequest()
/*     */     {
/*     */       public void abortRequest() {
/* 157 */         poolRequest.abortRequest();
/*     */       }
/*     */       
/*     */       public ManagedClientConnection getConnection(long timeout, TimeUnit tunit)
/*     */         throws InterruptedException, ConnectionPoolTimeoutException
/*     */       {
/* 163 */         if (route == null) {
/* 164 */           throw new IllegalArgumentException("Route may not be null.");
/*     */         }
/*     */         
/* 167 */         if (ThreadSafeClientConnManager.this.log.isDebugEnabled()) {
/* 168 */           ThreadSafeClientConnManager.this.log.debug("ThreadSafeClientConnManager.getConnection: " + route + ", timeout = " + timeout);
/*     */         }
/*     */         
/*     */ 
/* 172 */         BasicPoolEntry entry = poolRequest.getPoolEntry(timeout, tunit);
/* 173 */         return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, entry);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void releaseConnection(ManagedClientConnection conn, long validDuration, TimeUnit timeUnit)
/*     */   {
/* 182 */     if (!(conn instanceof BasicPooledConnAdapter)) {
/* 183 */       throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
/*     */     }
/*     */     
/*     */ 
/* 187 */     BasicPooledConnAdapter hca = (BasicPooledConnAdapter)conn;
/* 188 */     if ((hca.getPoolEntry() != null) && (hca.getManager() != this)) {
/* 189 */       throw new IllegalArgumentException("Connection not obtained from this manager.");
/*     */     }
/*     */     
/* 192 */     synchronized (hca) {
/* 193 */       BasicPoolEntry entry = (BasicPoolEntry)hca.getPoolEntry();
/* 194 */       if (entry == null) {
/* 195 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 199 */         if ((hca.isOpen()) && (!hca.isMarkedReusable()))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 208 */           hca.shutdown(); }
/*     */       } catch (IOException iox) {
/*     */         boolean reusable;
/* 211 */         if (this.log.isDebugEnabled())
/* 212 */           this.log.debug("Exception shutting down released connection.", iox);
/*     */       } finally {
/*     */         boolean reusable;
/* 215 */         boolean reusable = hca.isMarkedReusable();
/* 216 */         if (this.log.isDebugEnabled()) {
/* 217 */           if (reusable) {
/* 218 */             this.log.debug("Released connection is reusable.");
/*     */           } else {
/* 220 */             this.log.debug("Released connection is not reusable.");
/*     */           }
/*     */         }
/* 223 */         hca.detach();
/* 224 */         this.connectionPool.freeEntry(entry, reusable, validDuration, timeUnit);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 230 */     this.log.debug("Shutting down");
/* 231 */     this.connectionPool.shutdown();
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
/*     */   public int getConnectionsInPool(HttpRoute route)
/*     */   {
/* 245 */     return ((ConnPoolByRoute)this.connectionPool).getConnectionsInPool(route);
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
/*     */   public int getConnectionsInPool()
/*     */   {
/* 259 */     this.connectionPool.poolLock.lock();
/* 260 */     int count = this.connectionPool.numConnections;
/* 261 */     this.connectionPool.poolLock.unlock();
/* 262 */     return count;
/*     */   }
/*     */   
/*     */   public void closeIdleConnections(long idleTimeout, TimeUnit tunit) {
/* 266 */     if (this.log.isDebugEnabled()) {
/* 267 */       this.log.debug("Closing connections idle for " + idleTimeout + " " + tunit);
/*     */     }
/* 269 */     this.connectionPool.closeIdleConnections(idleTimeout, tunit);
/* 270 */     this.connectionPool.deleteClosedConnections();
/*     */   }
/*     */   
/*     */   public void closeExpiredConnections() {
/* 274 */     this.log.debug("Closing expired connections");
/* 275 */     this.connectionPool.closeExpiredConnections();
/* 276 */     this.connectionPool.deleteClosedConnections();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/ThreadSafeClientConnManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */