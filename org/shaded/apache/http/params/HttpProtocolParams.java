/*     */ package org.shaded.apache.http.params;
/*     */ 
/*     */ import org.shaded.apache.http.HttpVersion;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpProtocolParams
/*     */   implements CoreProtocolPNames
/*     */ {
/*     */   public static String getHttpElementCharset(HttpParams params)
/*     */   {
/*  62 */     if (params == null) {
/*  63 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  65 */     String charset = (String)params.getParameter("http.protocol.element-charset");
/*     */     
/*  67 */     if (charset == null) {
/*  68 */       charset = "US-ASCII";
/*     */     }
/*  70 */     return charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setHttpElementCharset(HttpParams params, String charset)
/*     */   {
/*  80 */     if (params == null) {
/*  81 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  83 */     params.setParameter("http.protocol.element-charset", charset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getContentCharset(HttpParams params)
/*     */   {
/*  94 */     if (params == null) {
/*  95 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/*  97 */     String charset = (String)params.getParameter("http.protocol.content-charset");
/*     */     
/*  99 */     if (charset == null) {
/* 100 */       charset = "ISO-8859-1";
/*     */     }
/* 102 */     return charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setContentCharset(HttpParams params, String charset)
/*     */   {
/* 112 */     if (params == null) {
/* 113 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 115 */     params.setParameter("http.protocol.content-charset", charset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ProtocolVersion getVersion(HttpParams params)
/*     */   {
/* 126 */     if (params == null) {
/* 127 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 129 */     Object param = params.getParameter("http.protocol.version");
/*     */     
/* 131 */     if (param == null) {
/* 132 */       return HttpVersion.HTTP_1_1;
/*     */     }
/* 134 */     return (ProtocolVersion)param;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setVersion(HttpParams params, ProtocolVersion version)
/*     */   {
/* 144 */     if (params == null) {
/* 145 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 147 */     params.setParameter("http.protocol.version", version);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getUserAgent(HttpParams params)
/*     */   {
/* 158 */     if (params == null) {
/* 159 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 161 */     return (String)params.getParameter("http.useragent");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setUserAgent(HttpParams params, String useragent)
/*     */   {
/* 171 */     if (params == null) {
/* 172 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 174 */     params.setParameter("http.useragent", useragent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean useExpectContinue(HttpParams params)
/*     */   {
/* 185 */     if (params == null) {
/* 186 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 188 */     return params.getBooleanParameter("http.protocol.expect-continue", false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setUseExpectContinue(HttpParams params, boolean b)
/*     */   {
/* 199 */     if (params == null) {
/* 200 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 202 */     params.setBooleanParameter("http.protocol.expect-continue", b);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/params/HttpProtocolParams.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */