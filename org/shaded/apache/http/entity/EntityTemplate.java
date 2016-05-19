/*    */ package org.shaded.apache.http.entity;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public class EntityTemplate
/*    */   extends AbstractHttpEntity
/*    */ {
/*    */   private final ContentProducer contentproducer;
/*    */   
/*    */   public EntityTemplate(ContentProducer contentproducer)
/*    */   {
/* 53 */     if (contentproducer == null) {
/* 54 */       throw new IllegalArgumentException("Content producer may not be null");
/*    */     }
/* 56 */     this.contentproducer = contentproducer;
/*    */   }
/*    */   
/*    */   public long getContentLength() {
/* 60 */     return -1L;
/*    */   }
/*    */   
/*    */   public InputStream getContent() {
/* 64 */     throw new UnsupportedOperationException("Entity template does not implement getContent()");
/*    */   }
/*    */   
/*    */   public boolean isRepeatable() {
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   public void writeTo(OutputStream outstream) throws IOException {
/* 72 */     if (outstream == null) {
/* 73 */       throw new IllegalArgumentException("Output stream may not be null");
/*    */     }
/* 75 */     this.contentproducer.writeTo(outstream);
/*    */   }
/*    */   
/*    */   public boolean isStreaming() {
/* 79 */     return true;
/*    */   }
/*    */   
/*    */   public void consumeContent()
/*    */     throws IOException
/*    */   {}
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/entity/EntityTemplate.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */