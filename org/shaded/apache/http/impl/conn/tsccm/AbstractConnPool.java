/*     */ package org.shaded.apache.http.impl.conn.tsccm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.annotation.GuardedBy;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.conn.ConnectionPoolTimeoutException;
/*     */ import org.shaded.apache.http.conn.OperatedClientConnection;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ import org.shaded.apache.http.impl.conn.IdleConnectionHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class AbstractConnPool
/*     */   implements RefQueueHandler
/*     */ {
/*  63 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Lock poolLock;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("poolLock")
/*     */   protected Set<BasicPoolEntry> leasedConnections;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("poolLock")
/*     */   protected IdleConnectionHandler idleConnHandler;
/*     */   
/*     */ 
/*     */   @GuardedBy("poolLock")
/*     */   protected int numConnections;
/*     */   
/*     */ 
/*     */   protected volatile boolean isShutDown;
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   protected Set<BasicPoolEntryRef> issuedConnections;
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   protected ReferenceQueue<Object> refQueue;
/*     */   
/*     */ 
/*     */ 
/*     */   protected AbstractConnPool()
/*     */   {
/*  98 */     this.leasedConnections = new HashSet();
/*  99 */     this.idleConnHandler = new IdleConnectionHandler();
/*     */     
/* 101 */     boolean fair = false;
/* 102 */     this.poolLock = new ReentrantLock(fair);
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
/*     */   @Deprecated
/*     */   public void enableConnectionGC()
/*     */     throws IllegalStateException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final BasicPoolEntry getEntry(HttpRoute route, Object state, long timeout, TimeUnit tunit)
/*     */     throws ConnectionPoolTimeoutException, InterruptedException
/*     */   {
/* 136 */     return requestPoolEntry(route, state).getPoolEntry(timeout, tunit);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract PoolEntryRequest requestPoolEntry(HttpRoute paramHttpRoute, Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void freeEntry(BasicPoolEntry paramBasicPoolEntry, boolean paramBoolean, long paramLong, TimeUnit paramTimeUnit);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void handleReference(Reference<?> ref) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected abstract void handleLostEntry(HttpRoute paramHttpRoute);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeIdleConnections(long idletime, TimeUnit tunit)
/*     */   {
/* 178 */     if (tunit == null) {
/* 179 */       throw new IllegalArgumentException("Time unit must not be null.");
/*     */     }
/*     */     
/* 182 */     this.poolLock.lock();
/*     */     try {
/* 184 */       this.idleConnHandler.closeIdleConnections(tunit.toMillis(idletime));
/*     */     } finally {
/* 186 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeExpiredConnections() {
/* 191 */     this.poolLock.lock();
/*     */     try {
/* 193 */       this.idleConnHandler.closeExpiredConnections();
/*     */     } finally {
/* 195 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void deleteClosedConnections();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 213 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 216 */       if (this.isShutDown) {
/*     */         return;
/*     */       }
/*     */       
/* 220 */       Iterator<BasicPoolEntry> iter = this.leasedConnections.iterator();
/* 221 */       while (iter.hasNext()) {
/* 222 */         BasicPoolEntry entry = (BasicPoolEntry)iter.next();
/* 223 */         iter.remove();
/* 224 */         closeConnection(entry.getConnection());
/*     */       }
/* 226 */       this.idleConnHandler.removeAll();
/*     */       
/* 228 */       this.isShutDown = true;
/*     */     }
/*     */     finally {
/* 231 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void closeConnection(OperatedClientConnection conn)
/*     */   {
/* 242 */     if (conn != null) {
/*     */       try {
/* 244 */         conn.close();
/*     */       } catch (IOException ex) {
/* 246 */         this.log.debug("I/O error closing connection", ex);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/AbstractConnPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */