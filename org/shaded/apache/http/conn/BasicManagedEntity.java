/*     */ package org.shaded.apache.http.conn;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.entity.HttpEntityWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class BasicManagedEntity
/*     */   extends HttpEntityWrapper
/*     */   implements ConnectionReleaseTrigger, EofSensorWatcher
/*     */ {
/*     */   protected ManagedClientConnection managedConn;
/*     */   protected final boolean attemptReuse;
/*     */   
/*     */   public BasicManagedEntity(HttpEntity entity, ManagedClientConnection conn, boolean reuse)
/*     */   {
/*  70 */     super(entity);
/*     */     
/*  72 */     if (conn == null) {
/*  73 */       throw new IllegalArgumentException("Connection may not be null.");
/*     */     }
/*     */     
/*  76 */     this.managedConn = conn;
/*  77 */     this.attemptReuse = reuse;
/*     */   }
/*     */   
/*     */   public boolean isRepeatable()
/*     */   {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public InputStream getContent() throws IOException
/*     */   {
/*  87 */     return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
/*     */   }
/*     */   
/*     */   public void consumeContent() throws IOException
/*     */   {
/*  92 */     if (this.managedConn == null) {
/*  93 */       return;
/*     */     }
/*     */     try {
/*  96 */       if (this.attemptReuse)
/*     */       {
/*  98 */         this.wrappedEntity.consumeContent();
/*  99 */         this.managedConn.markReusable();
/*     */       }
/*     */     } finally {
/* 102 */       releaseManagedConnection();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream outstream) throws IOException
/*     */   {
/* 108 */     super.writeTo(outstream);
/* 109 */     consumeContent();
/*     */   }
/*     */   
/*     */   public void releaseConnection() throws IOException {
/* 113 */     consumeContent();
/*     */   }
/*     */   
/*     */   public void abortConnection() throws IOException
/*     */   {
/* 118 */     if (this.managedConn != null) {
/*     */       try {
/* 120 */         this.managedConn.abortConnection();
/*     */       } finally {
/* 122 */         this.managedConn = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean eofDetected(InputStream wrapped) throws IOException {
/*     */     try {
/* 129 */       if ((this.attemptReuse) && (this.managedConn != null))
/*     */       {
/*     */ 
/* 132 */         wrapped.close();
/* 133 */         this.managedConn.markReusable();
/*     */       }
/*     */     } finally {
/* 136 */       releaseManagedConnection();
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */   
/*     */   public boolean streamClosed(InputStream wrapped) throws IOException {
/*     */     try {
/* 143 */       if ((this.attemptReuse) && (this.managedConn != null))
/*     */       {
/*     */ 
/* 146 */         wrapped.close();
/* 147 */         this.managedConn.markReusable();
/*     */       }
/*     */     } finally {
/* 150 */       releaseManagedConnection();
/*     */     }
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   public boolean streamAbort(InputStream wrapped) throws IOException {
/* 156 */     if (this.managedConn != null) {
/* 157 */       this.managedConn.abortConnection();
/*     */     }
/* 159 */     return false;
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
/*     */   protected void releaseManagedConnection()
/*     */     throws IOException
/*     */   {
/* 173 */     if (this.managedConn != null) {
/*     */       try {
/* 175 */         this.managedConn.releaseConnection();
/*     */       } finally {
/* 177 */         this.managedConn = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/BasicManagedEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */