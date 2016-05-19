/*     */ package org.shaded.apache.http.impl.conn.tsccm;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.conn.ClientConnectionOperator;
/*     */ import org.shaded.apache.http.conn.ConnectionPoolTimeoutException;
/*     */ import org.shaded.apache.http.conn.OperatedClientConnection;
/*     */ import org.shaded.apache.http.conn.params.ConnManagerParams;
/*     */ import org.shaded.apache.http.conn.params.ConnPerRoute;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ import org.shaded.apache.http.impl.conn.IdleConnectionHandler;
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
/*     */ public class ConnPoolByRoute
/*     */   extends AbstractConnPool
/*     */ {
/*  65 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */   private final HttpParams params;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final ClientConnectionOperator operator;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Queue<BasicPoolEntry> freeConnections;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Queue<WaitingThread> waitingThreads;
/*     */   
/*     */ 
/*     */   protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
/*     */   
/*     */ 
/*     */ 
/*     */   public ConnPoolByRoute(ClientConnectionOperator operator, HttpParams params)
/*     */   {
/*  90 */     if (operator == null) {
/*  91 */       throw new IllegalArgumentException("Connection operator may not be null");
/*     */     }
/*  93 */     this.operator = operator;
/*  94 */     this.params = params;
/*     */     
/*  96 */     this.freeConnections = createFreeConnQueue();
/*  97 */     this.waitingThreads = createWaitingThreadQueue();
/*  98 */     this.routeToPool = createRouteToPoolMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Queue<BasicPoolEntry> createFreeConnQueue()
/*     */   {
/* 109 */     return new LinkedList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Queue<WaitingThread> createWaitingThreadQueue()
/*     */   {
/* 119 */     return new LinkedList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap()
/*     */   {
/* 129 */     return new HashMap();
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
/*     */   protected RouteSpecificPool newRouteSpecificPool(HttpRoute route)
/*     */   {
/* 142 */     ConnPerRoute connPerRoute = ConnManagerParams.getMaxConnectionsPerRoute(this.params);
/* 143 */     return new RouteSpecificPool(route, connPerRoute.getMaxForRoute(route));
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
/*     */   protected WaitingThread newWaitingThread(Condition cond, RouteSpecificPool rospl)
/*     */   {
/* 158 */     return new WaitingThread(cond, rospl);
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
/*     */   protected RouteSpecificPool getRoutePool(HttpRoute route, boolean create)
/*     */   {
/* 173 */     RouteSpecificPool rospl = null;
/* 174 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 177 */       rospl = (RouteSpecificPool)this.routeToPool.get(route);
/* 178 */       if ((rospl == null) && (create))
/*     */       {
/* 180 */         rospl = newRouteSpecificPool(route);
/* 181 */         this.routeToPool.put(route, rospl);
/*     */       }
/*     */     }
/*     */     finally {
/* 185 */       this.poolLock.unlock();
/*     */     }
/*     */     
/* 188 */     return rospl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getConnectionsInPool(HttpRoute route)
/*     */   {
/* 195 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 198 */       RouteSpecificPool rospl = getRoutePool(route, false);
/* 199 */       return rospl != null ? rospl.getEntryCount() : 0;
/*     */     }
/*     */     finally {
/* 202 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PoolEntryRequest requestPoolEntry(final HttpRoute route, final Object state)
/*     */   {
/* 211 */     final WaitingThreadAborter aborter = new WaitingThreadAborter();
/*     */     
/* 213 */     new PoolEntryRequest()
/*     */     {
/*     */       public void abortRequest() {
/* 216 */         ConnPoolByRoute.this.poolLock.lock();
/*     */         try {
/* 218 */           aborter.abort();
/*     */         } finally {
/* 220 */           ConnPoolByRoute.this.poolLock.unlock();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */       public BasicPoolEntry getPoolEntry(long timeout, TimeUnit tunit)
/*     */         throws InterruptedException, ConnectionPoolTimeoutException
/*     */       {
/* 228 */         return ConnPoolByRoute.this.getEntryBlocking(route, state, timeout, tunit, aborter);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BasicPoolEntry getEntryBlocking(HttpRoute route, Object state, long timeout, TimeUnit tunit, WaitingThreadAborter aborter)
/*     */     throws ConnectionPoolTimeoutException, InterruptedException
/*     */   {
/* 258 */     int maxTotalConnections = ConnManagerParams.getMaxTotalConnections(this.params);
/*     */     
/* 260 */     Date deadline = null;
/* 261 */     if (timeout > 0L) {
/* 262 */       deadline = new Date(System.currentTimeMillis() + tunit.toMillis(timeout));
/*     */     }
/*     */     
/*     */ 
/* 266 */     BasicPoolEntry entry = null;
/* 267 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 270 */       RouteSpecificPool rospl = getRoutePool(route, true);
/* 271 */       WaitingThread waitingThread = null;
/*     */       
/* 273 */       while (entry == null)
/*     */       {
/* 275 */         if (this.isShutDown) {
/* 276 */           throw new IllegalStateException("Connection pool shut down.");
/*     */         }
/*     */         
/*     */ 
/* 280 */         if (this.log.isDebugEnabled()) {
/* 281 */           this.log.debug("Total connections kept alive: " + this.freeConnections.size());
/* 282 */           this.log.debug("Total issued connections: " + this.leasedConnections.size());
/* 283 */           this.log.debug("Total allocated connection: " + this.numConnections + " out of " + maxTotalConnections);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 292 */         entry = getFreeEntry(rospl, state);
/* 293 */         if (entry != null) {
/*     */           break;
/*     */         }
/*     */         
/* 297 */         boolean hasCapacity = rospl.getCapacity() > 0;
/*     */         
/* 299 */         if (this.log.isDebugEnabled()) {
/* 300 */           this.log.debug("Available capacity: " + rospl.getCapacity() + " out of " + rospl.getMaxEntries() + " [" + route + "][" + state + "]");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 305 */         if ((hasCapacity) && (this.numConnections < maxTotalConnections))
/*     */         {
/* 307 */           entry = createEntry(rospl, this.operator);
/*     */         }
/* 309 */         else if ((hasCapacity) && (!this.freeConnections.isEmpty()))
/*     */         {
/* 311 */           deleteLeastUsedEntry();
/* 312 */           entry = createEntry(rospl, this.operator);
/*     */         }
/*     */         else
/*     */         {
/* 316 */           if (this.log.isDebugEnabled()) {
/* 317 */             this.log.debug("Need to wait for connection [" + route + "][" + state + "]");
/*     */           }
/*     */           
/*     */ 
/* 321 */           if (waitingThread == null) {
/* 322 */             waitingThread = newWaitingThread(this.poolLock.newCondition(), rospl);
/*     */             
/* 324 */             aborter.setWaitingThread(waitingThread);
/*     */           }
/*     */           
/* 327 */           boolean success = false;
/*     */           try {
/* 329 */             rospl.queueThread(waitingThread);
/* 330 */             this.waitingThreads.add(waitingThread);
/* 331 */             success = waitingThread.await(deadline);
/*     */ 
/*     */ 
/*     */           }
/*     */           finally
/*     */           {
/*     */ 
/* 338 */             rospl.removeThread(waitingThread);
/* 339 */             this.waitingThreads.remove(waitingThread);
/*     */           }
/*     */           
/*     */ 
/* 343 */           if ((!success) && (deadline != null) && (deadline.getTime() <= System.currentTimeMillis()))
/*     */           {
/* 345 */             throw new ConnectionPoolTimeoutException("Timeout waiting for connection");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 352 */       this.poolLock.unlock();
/*     */     }
/*     */     
/* 355 */     return entry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void freeEntry(BasicPoolEntry entry, boolean reusable, long validDuration, TimeUnit timeUnit)
/*     */   {
/* 364 */     HttpRoute route = entry.getPlannedRoute();
/* 365 */     if (this.log.isDebugEnabled()) {
/* 366 */       this.log.debug("Releasing connection [" + route + "][" + entry.getState() + "]");
/*     */     }
/*     */     
/*     */ 
/* 370 */     this.poolLock.lock();
/*     */     try {
/* 372 */       if (this.isShutDown)
/*     */       {
/*     */ 
/* 375 */         closeConnection(entry.getConnection());
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 380 */         this.leasedConnections.remove(entry);
/*     */         
/* 382 */         RouteSpecificPool rospl = getRoutePool(route, true);
/*     */         
/* 384 */         if (reusable) {
/* 385 */           if (this.log.isDebugEnabled()) {
/* 386 */             this.log.debug("Pooling connection [" + route + "][" + entry.getState() + "]" + "; keep alive for " + validDuration + " " + timeUnit.toString());
/*     */           }
/*     */           
/*     */ 
/* 390 */           rospl.freeEntry(entry);
/* 391 */           this.freeConnections.add(entry);
/* 392 */           this.idleConnHandler.add(entry.getConnection(), validDuration, timeUnit);
/*     */         } else {
/* 394 */           rospl.dropEntry();
/* 395 */           this.numConnections -= 1;
/*     */         }
/*     */         
/* 398 */         notifyWaitingThread(rospl);
/*     */       }
/*     */     } finally {
/* 401 */       this.poolLock.unlock();
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
/*     */   protected BasicPoolEntry getFreeEntry(RouteSpecificPool rospl, Object state)
/*     */   {
/* 418 */     BasicPoolEntry entry = null;
/* 419 */     this.poolLock.lock();
/*     */     try {
/* 421 */       boolean done = false;
/* 422 */       while (!done)
/*     */       {
/* 424 */         entry = rospl.allocEntry(state);
/*     */         
/* 426 */         if (entry != null) {
/* 427 */           if (this.log.isDebugEnabled()) {
/* 428 */             this.log.debug("Getting free connection [" + rospl.getRoute() + "][" + state + "]");
/*     */           }
/*     */           
/*     */ 
/* 432 */           this.freeConnections.remove(entry);
/* 433 */           boolean valid = this.idleConnHandler.remove(entry.getConnection());
/* 434 */           if (!valid)
/*     */           {
/*     */ 
/* 437 */             if (this.log.isDebugEnabled()) {
/* 438 */               this.log.debug("Closing expired free connection [" + rospl.getRoute() + "][" + state + "]");
/*     */             }
/* 440 */             closeConnection(entry.getConnection());
/*     */             
/*     */ 
/*     */ 
/* 444 */             rospl.dropEntry();
/* 445 */             this.numConnections -= 1;
/*     */           } else {
/* 447 */             this.leasedConnections.add(entry);
/* 448 */             done = true;
/*     */           }
/*     */         }
/*     */         else {
/* 452 */           done = true;
/* 453 */           if (this.log.isDebugEnabled()) {
/* 454 */             this.log.debug("No free connections [" + rospl.getRoute() + "][" + state + "]");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     finally {
/* 460 */       this.poolLock.unlock();
/*     */     }
/*     */     
/* 463 */     return entry;
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
/*     */   protected BasicPoolEntry createEntry(RouteSpecificPool rospl, ClientConnectionOperator op)
/*     */   {
/* 480 */     if (this.log.isDebugEnabled()) {
/* 481 */       this.log.debug("Creating new connection [" + rospl.getRoute() + "]");
/*     */     }
/*     */     
/*     */ 
/* 485 */     BasicPoolEntry entry = new BasicPoolEntry(op, rospl.getRoute());
/*     */     
/* 487 */     this.poolLock.lock();
/*     */     try {
/* 489 */       rospl.createdEntry(entry);
/* 490 */       this.numConnections += 1;
/* 491 */       this.leasedConnections.add(entry);
/*     */     } finally {
/* 493 */       this.poolLock.unlock();
/*     */     }
/*     */     
/* 496 */     return entry;
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
/*     */   protected void deleteEntry(BasicPoolEntry entry)
/*     */   {
/* 513 */     HttpRoute route = entry.getPlannedRoute();
/*     */     
/* 515 */     if (this.log.isDebugEnabled()) {
/* 516 */       this.log.debug("Deleting connection [" + route + "][" + entry.getState() + "]");
/*     */     }
/*     */     
/*     */ 
/* 520 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 523 */       closeConnection(entry.getConnection());
/*     */       
/* 525 */       RouteSpecificPool rospl = getRoutePool(route, true);
/* 526 */       rospl.deleteEntry(entry);
/* 527 */       this.numConnections -= 1;
/* 528 */       if (rospl.isUnused()) {
/* 529 */         this.routeToPool.remove(route);
/*     */       }
/*     */       
/* 532 */       this.idleConnHandler.remove(entry.getConnection());
/*     */     }
/*     */     finally {
/* 535 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void deleteLeastUsedEntry()
/*     */   {
/*     */     try
/*     */     {
/* 547 */       this.poolLock.lock();
/*     */       
/*     */ 
/*     */ 
/* 551 */       BasicPoolEntry entry = (BasicPoolEntry)this.freeConnections.remove();
/*     */       
/* 553 */       if (entry != null) {
/* 554 */         deleteEntry(entry);
/* 555 */       } else if (this.log.isDebugEnabled()) {
/* 556 */         this.log.debug("No free connection to delete.");
/*     */       }
/*     */     }
/*     */     finally {
/* 560 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void handleLostEntry(HttpRoute route)
/*     */   {
/* 569 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 572 */       RouteSpecificPool rospl = getRoutePool(route, true);
/* 573 */       rospl.dropEntry();
/* 574 */       if (rospl.isUnused()) {
/* 575 */         this.routeToPool.remove(route);
/*     */       }
/*     */       
/* 578 */       this.numConnections -= 1;
/* 579 */       notifyWaitingThread(rospl);
/*     */     }
/*     */     finally {
/* 582 */       this.poolLock.unlock();
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
/*     */ 
/*     */ 
/*     */   protected void notifyWaitingThread(RouteSpecificPool rospl)
/*     */   {
/* 602 */     WaitingThread waitingThread = null;
/*     */     
/* 604 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 607 */       if ((rospl != null) && (rospl.hasThread())) {
/* 608 */         if (this.log.isDebugEnabled()) {
/* 609 */           this.log.debug("Notifying thread waiting on pool [" + rospl.getRoute() + "]");
/*     */         }
/*     */         
/* 612 */         waitingThread = rospl.nextThread();
/* 613 */       } else if (!this.waitingThreads.isEmpty()) {
/* 614 */         if (this.log.isDebugEnabled()) {
/* 615 */           this.log.debug("Notifying thread waiting on any pool");
/*     */         }
/* 617 */         waitingThread = (WaitingThread)this.waitingThreads.remove();
/* 618 */       } else if (this.log.isDebugEnabled()) {
/* 619 */         this.log.debug("Notifying no-one, there are no waiting threads");
/*     */       }
/*     */       
/* 622 */       if (waitingThread != null) {
/* 623 */         waitingThread.wakeup();
/*     */       }
/*     */     }
/*     */     finally {
/* 627 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void deleteClosedConnections()
/*     */   {
/* 634 */     this.poolLock.lock();
/*     */     try {
/* 636 */       Iterator<BasicPoolEntry> iter = this.freeConnections.iterator();
/* 637 */       while (iter.hasNext()) {
/* 638 */         BasicPoolEntry entry = (BasicPoolEntry)iter.next();
/* 639 */         if (!entry.getConnection().isOpen()) {
/* 640 */           iter.remove();
/* 641 */           deleteEntry(entry);
/*     */         }
/*     */       }
/*     */     } finally {
/* 645 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 654 */     this.poolLock.lock();
/*     */     try
/*     */     {
/* 657 */       super.shutdown();
/*     */       
/*     */ 
/*     */ 
/* 661 */       Iterator<BasicPoolEntry> ibpe = this.freeConnections.iterator();
/* 662 */       while (ibpe.hasNext()) {
/* 663 */         BasicPoolEntry entry = (BasicPoolEntry)ibpe.next();
/* 664 */         ibpe.remove();
/*     */         
/* 666 */         if (this.log.isDebugEnabled()) {
/* 667 */           this.log.debug("Closing connection [" + entry.getPlannedRoute() + "][" + entry.getState() + "]");
/*     */         }
/*     */         
/* 670 */         closeConnection(entry.getConnection());
/*     */       }
/*     */       
/*     */ 
/* 674 */       Iterator<WaitingThread> iwth = this.waitingThreads.iterator();
/* 675 */       while (iwth.hasNext()) {
/* 676 */         WaitingThread waiter = (WaitingThread)iwth.next();
/* 677 */         iwth.remove();
/* 678 */         waiter.wakeup();
/*     */       }
/*     */       
/* 681 */       this.routeToPool.clear();
/*     */     }
/*     */     finally {
/* 684 */       this.poolLock.unlock();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/ConnPoolByRoute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */