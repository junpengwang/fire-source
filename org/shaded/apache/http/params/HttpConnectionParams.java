/*     */ package org.shaded.apache.http.params;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpConnectionParams
/*     */   implements CoreConnectionPNames
/*     */ {
/*     */   public static int getSoTimeout(HttpParams params)
/*     */   {
/*  56 */     if (params == null) {
/*  57 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  59 */     return params.getIntParameter("http.socket.timeout", 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setSoTimeout(HttpParams params, int timeout)
/*     */   {
/*  69 */     if (params == null) {
/*  70 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  72 */     params.setIntParameter("http.socket.timeout", timeout);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean getTcpNoDelay(HttpParams params)
/*     */   {
/*  84 */     if (params == null) {
/*  85 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  87 */     return params.getBooleanParameter("http.tcp.nodelay", true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setTcpNoDelay(HttpParams params, boolean value)
/*     */   {
/*  98 */     if (params == null) {
/*  99 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 101 */     params.setBooleanParameter("http.tcp.nodelay", value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getSocketBufferSize(HttpParams params)
/*     */   {
/* 112 */     if (params == null) {
/* 113 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 115 */     return params.getIntParameter("http.socket.buffer-size", -1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setSocketBufferSize(HttpParams params, int size)
/*     */   {
/* 127 */     if (params == null) {
/* 128 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 130 */     params.setIntParameter("http.socket.buffer-size", size);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getLinger(HttpParams params)
/*     */   {
/* 141 */     if (params == null) {
/* 142 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 144 */     return params.getIntParameter("http.socket.linger", -1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setLinger(HttpParams params, int value)
/*     */   {
/* 154 */     if (params == null) {
/* 155 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 157 */     params.setIntParameter("http.socket.linger", value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getConnectionTimeout(HttpParams params)
/*     */   {
/* 168 */     if (params == null) {
/* 169 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 171 */     return params.getIntParameter("http.connection.timeout", 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setConnectionTimeout(HttpParams params, int timeout)
/*     */   {
/* 183 */     if (params == null) {
/* 184 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 186 */     params.setIntParameter("http.connection.timeout", timeout);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isStaleCheckingEnabled(HttpParams params)
/*     */   {
/* 198 */     if (params == null) {
/* 199 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 201 */     return params.getBooleanParameter("http.connection.stalecheck", true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setStaleCheckingEnabled(HttpParams params, boolean value)
/*     */   {
/* 213 */     if (params == null) {
/* 214 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 216 */     params.setBooleanParameter("http.connection.stalecheck", value);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/params/HttpConnectionParams.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */