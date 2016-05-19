/*     */ package org.shaded.apache.http.cookie;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class CookieOrigin
/*     */ {
/*     */   private final String host;
/*     */   private final int port;
/*     */   private final String path;
/*     */   private final boolean secure;
/*     */   
/*     */   public CookieOrigin(String host, int port, String path, boolean secure)
/*     */   {
/*  49 */     if (host == null) {
/*  50 */       throw new IllegalArgumentException("Host of origin may not be null");
/*     */     }
/*     */     
/*  53 */     if (host.trim().length() == 0) {
/*  54 */       throw new IllegalArgumentException("Host of origin may not be blank");
/*     */     }
/*     */     
/*  57 */     if (port < 0) {
/*  58 */       throw new IllegalArgumentException("Invalid port: " + port);
/*     */     }
/*  60 */     if (path == null) {
/*  61 */       throw new IllegalArgumentException("Path of origin may not be null.");
/*     */     }
/*     */     
/*  64 */     this.host = host.toLowerCase(Locale.ENGLISH);
/*  65 */     this.port = port;
/*  66 */     if (path.trim().length() != 0) {
/*  67 */       this.path = path;
/*     */     } else {
/*  69 */       this.path = "/";
/*     */     }
/*  71 */     this.secure = secure;
/*     */   }
/*     */   
/*     */   public String getHost() {
/*  75 */     return this.host;
/*     */   }
/*     */   
/*     */   public String getPath() {
/*  79 */     return this.path;
/*     */   }
/*     */   
/*     */   public int getPort() {
/*  83 */     return this.port;
/*     */   }
/*     */   
/*     */   public boolean isSecure() {
/*  87 */     return this.secure;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  92 */     StringBuilder buffer = new StringBuilder();
/*  93 */     buffer.append('[');
/*  94 */     if (this.secure) {
/*  95 */       buffer.append("(secure)");
/*     */     }
/*  97 */     buffer.append(this.host);
/*  98 */     buffer.append(':');
/*  99 */     buffer.append(Integer.toString(this.port));
/* 100 */     buffer.append(this.path);
/* 101 */     buffer.append(']');
/* 102 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/cookie/CookieOrigin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */