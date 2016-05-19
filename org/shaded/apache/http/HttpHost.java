/*     */ package org.shaded.apache.http;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpHost
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7529410654042457626L;
/*     */   public static final String DEFAULT_SCHEME_NAME = "http";
/*     */   protected final String hostname;
/*     */   protected final String lcHostname;
/*     */   protected final int port;
/*     */   protected final String schemeName;
/*     */   
/*     */   public HttpHost(String hostname, int port, String scheme)
/*     */   {
/*  82 */     if (hostname == null) {
/*  83 */       throw new IllegalArgumentException("Host name may not be null");
/*     */     }
/*  85 */     this.hostname = hostname;
/*  86 */     this.lcHostname = hostname.toLowerCase(Locale.ENGLISH);
/*  87 */     if (scheme != null) {
/*  88 */       this.schemeName = scheme.toLowerCase(Locale.ENGLISH);
/*     */     } else {
/*  90 */       this.schemeName = "http";
/*     */     }
/*  92 */     this.port = port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpHost(String hostname, int port)
/*     */   {
/* 103 */     this(hostname, port, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpHost(String hostname)
/*     */   {
/* 112 */     this(hostname, -1, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpHost(HttpHost httphost)
/*     */   {
/* 121 */     this(httphost.hostname, httphost.port, httphost.schemeName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getHostName()
/*     */   {
/* 130 */     return this.hostname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getPort()
/*     */   {
/* 139 */     return this.port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSchemeName()
/*     */   {
/* 148 */     return this.schemeName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toURI()
/*     */   {
/* 157 */     CharArrayBuffer buffer = new CharArrayBuffer(32);
/* 158 */     buffer.append(this.schemeName);
/* 159 */     buffer.append("://");
/* 160 */     buffer.append(this.hostname);
/* 161 */     if (this.port != -1) {
/* 162 */       buffer.append(':');
/* 163 */       buffer.append(Integer.toString(this.port));
/*     */     }
/* 165 */     return buffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toHostString()
/*     */   {
/* 175 */     CharArrayBuffer buffer = new CharArrayBuffer(32);
/* 176 */     buffer.append(this.hostname);
/* 177 */     if (this.port != -1) {
/* 178 */       buffer.append(':');
/* 179 */       buffer.append(Integer.toString(this.port));
/*     */     }
/* 181 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 186 */     return toURI();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 191 */     if (obj == null) return false;
/* 192 */     if (this == obj) return true;
/* 193 */     if ((obj instanceof HttpHost)) {
/* 194 */       HttpHost that = (HttpHost)obj;
/* 195 */       return (this.lcHostname.equals(that.lcHostname)) && (this.port == that.port) && (this.schemeName.equals(that.schemeName));
/*     */     }
/*     */     
/*     */ 
/* 199 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 207 */     int hash = 17;
/* 208 */     hash = LangUtils.hashCode(hash, this.lcHostname);
/* 209 */     hash = LangUtils.hashCode(hash, this.port);
/* 210 */     hash = LangUtils.hashCode(hash, this.schemeName);
/* 211 */     return hash;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 215 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/HttpHost.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */