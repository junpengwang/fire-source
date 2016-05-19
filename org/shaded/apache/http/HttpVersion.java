/*     */ package org.shaded.apache.http;
/*     */ 
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
/*     */ 
/*     */ public final class HttpVersion
/*     */   extends ProtocolVersion
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5856653513894415344L;
/*     */   public static final String HTTP = "HTTP";
/*  61 */   public static final HttpVersion HTTP_0_9 = new HttpVersion(0, 9);
/*     */   
/*     */ 
/*  64 */   public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);
/*     */   
/*     */ 
/*  67 */   public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpVersion(int major, int minor)
/*     */   {
/*  79 */     super("HTTP", major, minor);
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
/*     */   public ProtocolVersion forVersion(int major, int minor)
/*     */   {
/*  93 */     if ((major == this.major) && (minor == this.minor)) {
/*  94 */       return this;
/*     */     }
/*     */     
/*  97 */     if (major == 1) {
/*  98 */       if (minor == 0) {
/*  99 */         return HTTP_1_0;
/*     */       }
/* 101 */       if (minor == 1) {
/* 102 */         return HTTP_1_1;
/*     */       }
/*     */     }
/* 105 */     if ((major == 0) && (minor == 9)) {
/* 106 */       return HTTP_0_9;
/*     */     }
/*     */     
/*     */ 
/* 110 */     return new HttpVersion(major, minor);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpVersion.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */