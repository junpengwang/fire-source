/*    */ package org.shaded.apache.http.entity;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ByteArrayEntity
/*    */   extends AbstractHttpEntity
/*    */   implements Cloneable
/*    */ {
/*    */   protected final byte[] content;
/*    */   
/*    */   public ByteArrayEntity(byte[] b)
/*    */   {
/* 53 */     if (b == null) {
/* 54 */       throw new IllegalArgumentException("Source byte array may not be null");
/*    */     }
/* 56 */     this.content = b;
/*    */   }
/*    */   
/*    */   public boolean isRepeatable() {
/* 60 */     return true;
/*    */   }
/*    */   
/*    */   public long getContentLength() {
/* 64 */     return this.content.length;
/*    */   }
/*    */   
/*    */   public InputStream getContent() {
/* 68 */     return new ByteArrayInputStream(this.content);
/*    */   }
/*    */   
/*    */   public void writeTo(OutputStream outstream) throws IOException {
/* 72 */     if (outstream == null) {
/* 73 */       throw new IllegalArgumentException("Output stream may not be null");
/*    */     }
/* 75 */     outstream.write(this.content);
/* 76 */     outstream.flush();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isStreaming()
/*    */   {
/* 86 */     return false;
/*    */   }
/*    */   
/*    */   public Object clone() throws CloneNotSupportedException {
/* 90 */     return super.clone();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/ByteArrayEntity.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */