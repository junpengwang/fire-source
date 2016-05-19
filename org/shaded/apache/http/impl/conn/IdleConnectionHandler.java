/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.HttpConnection;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class IdleConnectionHandler
/*     */ {
/*  53 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */   private final Map<HttpConnection, TimeValues> connectionToTimes;
/*     */   
/*     */ 
/*     */   public IdleConnectionHandler()
/*     */   {
/*  61 */     this.connectionToTimes = new HashMap();
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
/*     */   public void add(HttpConnection connection, long validDuration, TimeUnit unit)
/*     */   {
/*  74 */     long timeAdded = System.currentTimeMillis();
/*     */     
/*  76 */     if (this.log.isDebugEnabled()) {
/*  77 */       this.log.debug("Adding connection at: " + timeAdded);
/*     */     }
/*     */     
/*  80 */     this.connectionToTimes.put(connection, new TimeValues(timeAdded, validDuration, unit));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove(HttpConnection connection)
/*     */   {
/*  92 */     TimeValues times = (TimeValues)this.connectionToTimes.remove(connection);
/*  93 */     if (times == null) {
/*  94 */       this.log.warn("Removing a connection that never existed!");
/*  95 */       return true;
/*     */     }
/*  97 */     return System.currentTimeMillis() <= times.timeExpires;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAll()
/*     */   {
/* 105 */     this.connectionToTimes.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeIdleConnections(long idleTime)
/*     */   {
/* 116 */     long idleTimeout = System.currentTimeMillis() - idleTime;
/*     */     
/* 118 */     if (this.log.isDebugEnabled()) {
/* 119 */       this.log.debug("Checking for connections, idle timeout: " + idleTimeout);
/*     */     }
/* 121 */     Iterator<HttpConnection> connectionIter = this.connectionToTimes.keySet().iterator();
/*     */     
/* 123 */     while (connectionIter.hasNext()) {
/* 124 */       HttpConnection conn = (HttpConnection)connectionIter.next();
/* 125 */       TimeValues times = (TimeValues)this.connectionToTimes.get(conn);
/* 126 */       long connectionTime = times.timeAdded;
/* 127 */       if (connectionTime <= idleTimeout) {
/* 128 */         if (this.log.isDebugEnabled()) {
/* 129 */           this.log.debug("Closing idle connection, connection time: " + connectionTime);
/*     */         }
/*     */         try {
/* 132 */           conn.close();
/*     */         } catch (IOException ex) {
/* 134 */           this.log.debug("I/O error closing connection", ex);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeExpiredConnections()
/*     */   {
/* 142 */     long now = System.currentTimeMillis();
/* 143 */     if (this.log.isDebugEnabled()) {
/* 144 */       this.log.debug("Checking for expired connections, now: " + now);
/*     */     }
/*     */     
/* 147 */     Iterator<HttpConnection> connectionIter = this.connectionToTimes.keySet().iterator();
/*     */     
/*     */ 
/* 150 */     while (connectionIter.hasNext()) {
/* 151 */       HttpConnection conn = (HttpConnection)connectionIter.next();
/* 152 */       TimeValues times = (TimeValues)this.connectionToTimes.get(conn);
/* 153 */       if (times.timeExpires <= now) {
/* 154 */         if (this.log.isDebugEnabled()) {
/* 155 */           this.log.debug("Closing connection, expired @: " + times.timeExpires);
/*     */         }
/*     */         try {
/* 158 */           conn.close();
/*     */         } catch (IOException ex) {
/* 160 */           this.log.debug("I/O error closing connection", ex);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class TimeValues
/*     */   {
/*     */     private final long timeAdded;
/*     */     
/*     */     private final long timeExpires;
/*     */     
/*     */ 
/*     */     TimeValues(long now, long validDuration, TimeUnit validUnit)
/*     */     {
/* 176 */       this.timeAdded = now;
/* 177 */       if (validDuration > 0L) {
/* 178 */         this.timeExpires = (now + validUnit.toMillis(validDuration));
/*     */       } else {
/* 180 */         this.timeExpires = Long.MAX_VALUE;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/IdleConnectionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */