/*     */ package org.shaded.apache.http.impl.conn.tsccm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Queue;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.conn.OperatedClientConnection;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ import org.shaded.apache.http.util.LangUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class RouteSpecificPool
/*     */ {
/*  53 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final HttpRoute route;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final int maxEntries;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final LinkedList<BasicPoolEntry> freeEntries;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Queue<WaitingThread> waitingThreads;
/*     */   
/*     */ 
/*     */ 
/*     */   protected int numEntries;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public RouteSpecificPool(HttpRoute route, int maxEntries)
/*     */   {
/*  82 */     this.route = route;
/*  83 */     this.maxEntries = maxEntries;
/*  84 */     this.freeEntries = new LinkedList();
/*  85 */     this.waitingThreads = new LinkedList();
/*  86 */     this.numEntries = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final HttpRoute getRoute()
/*     */   {
/*  96 */     return this.route;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getMaxEntries()
/*     */   {
/* 106 */     return this.maxEntries;
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
/*     */   public boolean isUnused()
/*     */   {
/* 119 */     return (this.numEntries < 1) && (this.waitingThreads.isEmpty());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCapacity()
/*     */   {
/* 129 */     return this.maxEntries - this.numEntries;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getEntryCount()
/*     */   {
/* 141 */     return this.numEntries;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicPoolEntry allocEntry(Object state)
/*     */   {
/* 151 */     if (!this.freeEntries.isEmpty()) {
/* 152 */       ListIterator<BasicPoolEntry> it = this.freeEntries.listIterator(this.freeEntries.size());
/* 153 */       while (it.hasPrevious()) {
/* 154 */         BasicPoolEntry entry = (BasicPoolEntry)it.previous();
/* 155 */         if ((entry.getState() == null) || (LangUtils.equals(state, entry.getState()))) {
/* 156 */           it.remove();
/* 157 */           return entry;
/*     */         }
/*     */       }
/*     */     }
/* 161 */     if ((getCapacity() == 0) && (!this.freeEntries.isEmpty())) {
/* 162 */       BasicPoolEntry entry = (BasicPoolEntry)this.freeEntries.remove();
/* 163 */       entry.shutdownEntry();
/* 164 */       OperatedClientConnection conn = entry.getConnection();
/*     */       try {
/* 166 */         conn.close();
/*     */       } catch (IOException ex) {
/* 168 */         this.log.debug("I/O error closing connection", ex);
/*     */       }
/* 170 */       return entry;
/*     */     }
/* 172 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void freeEntry(BasicPoolEntry entry)
/*     */   {
/* 184 */     if (this.numEntries < 1) {
/* 185 */       throw new IllegalStateException("No entry created for this pool. " + this.route);
/*     */     }
/*     */     
/* 188 */     if (this.numEntries <= this.freeEntries.size()) {
/* 189 */       throw new IllegalStateException("No entry allocated from this pool. " + this.route);
/*     */     }
/*     */     
/* 192 */     this.freeEntries.add(entry);
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
/*     */   public void createdEntry(BasicPoolEntry entry)
/*     */   {
/* 206 */     if (!this.route.equals(entry.getPlannedRoute())) {
/* 207 */       throw new IllegalArgumentException("Entry not planned for this pool.\npool: " + this.route + "\nplan: " + entry.getPlannedRoute());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 213 */     this.numEntries += 1;
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
/*     */   public boolean deleteEntry(BasicPoolEntry entry)
/*     */   {
/* 229 */     boolean found = this.freeEntries.remove(entry);
/* 230 */     if (found)
/* 231 */       this.numEntries -= 1;
/* 232 */     return found;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void dropEntry()
/*     */   {
/* 243 */     if (this.numEntries < 1) {
/* 244 */       throw new IllegalStateException("There is no entry that could be dropped.");
/*     */     }
/*     */     
/* 247 */     this.numEntries -= 1;
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
/*     */   public void queueThread(WaitingThread wt)
/*     */   {
/* 260 */     if (wt == null) {
/* 261 */       throw new IllegalArgumentException("Waiting thread must not be null.");
/*     */     }
/*     */     
/* 264 */     this.waitingThreads.add(wt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasThread()
/*     */   {
/* 275 */     return !this.waitingThreads.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WaitingThread nextThread()
/*     */   {
/* 285 */     return (WaitingThread)this.waitingThreads.peek();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeThread(WaitingThread wt)
/*     */   {
/* 295 */     if (wt == null) {
/* 296 */       return;
/*     */     }
/* 298 */     this.waitingThreads.remove(wt);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/tsccm/RouteSpecificPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */