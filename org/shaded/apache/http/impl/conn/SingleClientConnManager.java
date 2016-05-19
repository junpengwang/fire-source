/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.annotation.GuardedBy;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.conn.ClientConnectionManager;
/*     */ import org.shaded.apache.http.conn.ClientConnectionOperator;
/*     */ import org.shaded.apache.http.conn.ClientConnectionRequest;
/*     */ import org.shaded.apache.http.conn.ManagedClientConnection;
/*     */ import org.shaded.apache.http.conn.OperatedClientConnection;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ import org.shaded.apache.http.conn.routing.RouteTracker;
/*     */ import org.shaded.apache.http.conn.scheme.SchemeRegistry;
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
/*     */ @ThreadSafe
/*     */ public class SingleClientConnManager
/*     */   implements ClientConnectionManager
/*     */ {
/*  64 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */   public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
/*     */   
/*     */ 
/*     */ 
/*     */   protected final SchemeRegistry schemeRegistry;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final ClientConnectionOperator connOperator;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final boolean alwaysShutDown;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   protected PoolEntry uniquePoolEntry;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   protected ConnAdapter managedConn;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   protected long lastReleaseTime;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   protected long connectionExpiresTime;
/*     */   
/*     */ 
/*     */   protected volatile boolean isShutDown;
/*     */   
/*     */ 
/*     */ 
/*     */   public SingleClientConnManager(HttpParams params, SchemeRegistry schreg)
/*     */   {
/* 108 */     if (schreg == null) {
/* 109 */       throw new IllegalArgumentException("Scheme registry must not be null.");
/*     */     }
/*     */     
/* 112 */     this.schemeRegistry = schreg;
/* 113 */     this.connOperator = createConnectionOperator(schreg);
/* 114 */     this.uniquePoolEntry = new PoolEntry();
/* 115 */     this.managedConn = null;
/* 116 */     this.lastReleaseTime = -1L;
/* 117 */     this.alwaysShutDown = false;
/* 118 */     this.isShutDown = false;
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable
/*     */   {
/*     */     try {
/* 124 */       shutdown();
/*     */     } finally {
/* 126 */       super.finalize();
/*     */     }
/*     */   }
/*     */   
/*     */   public SchemeRegistry getSchemeRegistry() {
/* 131 */     return this.schemeRegistry;
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
/*     */   protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg)
/*     */   {
/* 148 */     return new DefaultClientConnectionOperator(schreg);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void assertStillUp()
/*     */     throws IllegalStateException
/*     */   {
/* 157 */     if (this.isShutDown) {
/* 158 */       throw new IllegalStateException("Manager is shut down.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public final ClientConnectionRequest requestConnection(final HttpRoute route, final Object state)
/*     */   {
/* 165 */     new ClientConnectionRequest()
/*     */     {
/*     */       public void abortRequest() {}
/*     */       
/*     */ 
/*     */ 
/*     */       public ManagedClientConnection getConnection(long timeout, TimeUnit tunit)
/*     */       {
/* 173 */         return SingleClientConnManager.this.getConnection(route, state);
/*     */       }
/*     */     };
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
/*     */   public synchronized ManagedClientConnection getConnection(HttpRoute route, Object state)
/*     */   {
/* 189 */     if (route == null) {
/* 190 */       throw new IllegalArgumentException("Route may not be null.");
/*     */     }
/* 192 */     assertStillUp();
/*     */     
/* 194 */     if (this.log.isDebugEnabled()) {
/* 195 */       this.log.debug("Get connection for route " + route);
/*     */     }
/*     */     
/* 198 */     if (this.managedConn != null) {
/* 199 */       throw new IllegalStateException("Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
/*     */     }
/*     */     
/* 202 */     boolean recreate = false;
/* 203 */     boolean shutdown = false;
/*     */     
/*     */ 
/* 206 */     closeExpiredConnections();
/*     */     
/* 208 */     if (this.uniquePoolEntry.connection.isOpen()) {
/* 209 */       RouteTracker tracker = this.uniquePoolEntry.tracker;
/* 210 */       shutdown = (tracker == null) || (!tracker.toRoute().equals(route));
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/*     */ 
/* 218 */       recreate = true;
/*     */     }
/*     */     
/* 221 */     if (shutdown) {
/* 222 */       recreate = true;
/*     */       try {
/* 224 */         this.uniquePoolEntry.shutdown();
/*     */       } catch (IOException iox) {
/* 226 */         this.log.debug("Problem shutting down connection.", iox);
/*     */       }
/*     */     }
/*     */     
/* 230 */     if (recreate) {
/* 231 */       this.uniquePoolEntry = new PoolEntry();
/*     */     }
/* 233 */     this.managedConn = new ConnAdapter(this.uniquePoolEntry, route);
/*     */     
/* 235 */     return this.managedConn;
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void releaseConnection(ManagedClientConnection conn, long validDuration, TimeUnit timeUnit)
/*     */   {
/* 241 */     assertStillUp();
/*     */     
/* 243 */     if (!(conn instanceof ConnAdapter)) {
/* 244 */       throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 249 */     if (this.log.isDebugEnabled()) {
/* 250 */       this.log.debug("Releasing connection " + conn);
/*     */     }
/*     */     
/* 253 */     ConnAdapter sca = (ConnAdapter)conn;
/* 254 */     if (sca.poolEntry == null)
/* 255 */       return;
/* 256 */     ClientConnectionManager manager = sca.getManager();
/* 257 */     if ((manager != null) && (manager != this)) {
/* 258 */       throw new IllegalArgumentException("Connection not obtained from this manager.");
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 264 */       if ((sca.isOpen()) && ((this.alwaysShutDown) || (!sca.isMarkedReusable())))
/*     */       {
/*     */ 
/* 267 */         if (this.log.isDebugEnabled()) {
/* 268 */           this.log.debug("Released connection open but not reusable.");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 275 */         sca.shutdown();
/*     */       }
/*     */     } catch (IOException iox) {
/* 278 */       if (this.log.isDebugEnabled()) {
/* 279 */         this.log.debug("Exception shutting down released connection.", iox);
/*     */       }
/*     */     } finally {
/* 282 */       sca.detach();
/* 283 */       this.managedConn = null;
/* 284 */       this.lastReleaseTime = System.currentTimeMillis();
/* 285 */       if (validDuration > 0L) {
/* 286 */         this.connectionExpiresTime = (timeUnit.toMillis(validDuration) + this.lastReleaseTime);
/*     */       } else
/* 288 */         this.connectionExpiresTime = Long.MAX_VALUE;
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void closeExpiredConnections() {
/* 293 */     if (System.currentTimeMillis() >= this.connectionExpiresTime) {
/* 294 */       closeIdleConnections(0L, TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void closeIdleConnections(long idletime, TimeUnit tunit) {
/* 299 */     assertStillUp();
/*     */     
/*     */ 
/* 302 */     if (tunit == null) {
/* 303 */       throw new IllegalArgumentException("Time unit must not be null.");
/*     */     }
/*     */     
/* 306 */     if ((this.managedConn == null) && (this.uniquePoolEntry.connection.isOpen())) {
/* 307 */       long cutoff = System.currentTimeMillis() - tunit.toMillis(idletime);
/*     */       
/* 309 */       if (this.lastReleaseTime <= cutoff) {
/*     */         try {
/* 311 */           this.uniquePoolEntry.close();
/*     */         }
/*     */         catch (IOException iox) {
/* 314 */           this.log.debug("Problem closing idle connection.", iox);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void shutdown()
/*     */   {
/* 322 */     this.isShutDown = true;
/*     */     
/* 324 */     if (this.managedConn != null) {
/* 325 */       this.managedConn.detach();
/*     */     }
/*     */     try {
/* 328 */       if (this.uniquePoolEntry != null) {
/* 329 */         this.uniquePoolEntry.shutdown();
/*     */       }
/*     */     } catch (IOException iox) {
/* 332 */       this.log.debug("Problem while shutting down manager.", iox);
/*     */     } finally {
/* 334 */       this.uniquePoolEntry = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected synchronized void revokeConnection()
/*     */   {
/* 343 */     if (this.managedConn == null)
/* 344 */       return;
/* 345 */     this.managedConn.detach();
/*     */     try {
/* 347 */       this.uniquePoolEntry.shutdown();
/*     */     }
/*     */     catch (IOException iox) {
/* 350 */       this.log.debug("Problem while shutting down connection.", iox);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected class PoolEntry
/*     */     extends AbstractPoolEntry
/*     */   {
/*     */     protected PoolEntry()
/*     */     {
/* 364 */       super(null);
/*     */     }
/*     */     
/*     */ 
/*     */     protected void close()
/*     */       throws IOException
/*     */     {
/* 371 */       shutdownEntry();
/* 372 */       if (this.connection.isOpen()) {
/* 373 */         this.connection.close();
/*     */       }
/*     */     }
/*     */     
/*     */     protected void shutdown()
/*     */       throws IOException
/*     */     {
/* 380 */       shutdownEntry();
/* 381 */       if (this.connection.isOpen()) {
/* 382 */         this.connection.shutdown();
/*     */       }
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
/*     */   protected class ConnAdapter
/*     */     extends AbstractPooledConnAdapter
/*     */   {
/*     */     protected ConnAdapter(SingleClientConnManager.PoolEntry entry, HttpRoute route)
/*     */     {
/* 399 */       super(entry);
/* 400 */       markReusable();
/* 401 */       entry.route = route;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/SingleClientConnManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */