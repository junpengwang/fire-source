/*     */ package org.shaded.apache.http.entity;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerializableEntity
/*     */   extends AbstractHttpEntity
/*     */ {
/*     */   private byte[] objSer;
/*     */   private Serializable objRef;
/*     */   
/*     */   public SerializableEntity(Serializable ser, boolean bufferize)
/*     */     throws IOException
/*     */   {
/*  69 */     if (ser == null) {
/*  70 */       throw new IllegalArgumentException("Source object may not be null");
/*     */     }
/*     */     
/*  73 */     if (bufferize) {
/*  74 */       createBytes(ser);
/*     */     } else {
/*  76 */       this.objRef = ser;
/*     */     }
/*     */   }
/*     */   
/*     */   private void createBytes(Serializable ser) throws IOException {
/*  81 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  82 */     ObjectOutputStream out = new ObjectOutputStream(baos);
/*  83 */     out.writeObject(ser);
/*  84 */     out.flush();
/*  85 */     this.objSer = baos.toByteArray();
/*     */   }
/*     */   
/*     */   public InputStream getContent() throws IOException, IllegalStateException {
/*  89 */     if (this.objSer == null) {
/*  90 */       createBytes(this.objRef);
/*     */     }
/*  92 */     return new ByteArrayInputStream(this.objSer);
/*     */   }
/*     */   
/*     */   public long getContentLength() {
/*  96 */     if (this.objSer == null) {
/*  97 */       return -1L;
/*     */     }
/*  99 */     return this.objSer.length;
/*     */   }
/*     */   
/*     */   public boolean isRepeatable()
/*     */   {
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isStreaming() {
/* 108 */     return this.objSer == null;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream outstream) throws IOException {
/* 112 */     if (outstream == null) {
/* 113 */       throw new IllegalArgumentException("Output stream may not be null");
/*     */     }
/*     */     
/* 116 */     if (this.objSer == null) {
/* 117 */       ObjectOutputStream out = new ObjectOutputStream(outstream);
/* 118 */       out.writeObject(this.objRef);
/* 119 */       out.flush();
/*     */     } else {
/* 121 */       outstream.write(this.objSer);
/* 122 */       outstream.flush();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/SerializableEntity.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */