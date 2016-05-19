/*     */ package org.shaded.apache.http.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotThreadSafe
/*     */ public class BasicEofSensorWatcher
/*     */   implements EofSensorWatcher
/*     */ {
/*     */   protected final ManagedClientConnection managedConn;
/*     */   protected final boolean attemptReuse;
/*     */   
/*     */   public BasicEofSensorWatcher(ManagedClientConnection conn, boolean reuse)
/*     */   {
/*  58 */     if (conn == null) {
/*  59 */       throw new IllegalArgumentException("Connection may not be null.");
/*     */     }
/*     */     
/*  62 */     this.managedConn = conn;
/*  63 */     this.attemptReuse = reuse;
/*     */   }
/*     */   
/*     */   public boolean eofDetected(InputStream wrapped) throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  70 */       if (this.attemptReuse)
/*     */       {
/*     */ 
/*  73 */         wrapped.close();
/*  74 */         this.managedConn.markReusable();
/*     */       }
/*     */     } finally {
/*  77 */       this.managedConn.releaseConnection();
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   public boolean streamClosed(InputStream wrapped) throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  86 */       if (this.attemptReuse)
/*     */       {
/*     */ 
/*  89 */         wrapped.close();
/*  90 */         this.managedConn.markReusable();
/*     */       }
/*     */     } finally {
/*  93 */       this.managedConn.releaseConnection();
/*     */     }
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   public boolean streamAbort(InputStream wrapped)
/*     */     throws IOException
/*     */   {
/* 101 */     this.managedConn.abortConnection();
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/BasicEofSensorWatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */