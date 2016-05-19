/*     */ package org.shaded.apache.http.conn.scheme;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.annotation.Immutable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Scheme
/*     */ {
/*     */   private final String name;
/*     */   private final SocketFactory socketFactory;
/*     */   private final int defaultPort;
/*     */   private final boolean layered;
/*     */   private String stringRep;
/*     */   
/*     */   public Scheme(String name, SocketFactory factory, int port)
/*     */   {
/*  93 */     if (name == null) {
/*  94 */       throw new IllegalArgumentException("Scheme name may not be null");
/*     */     }
/*     */     
/*  97 */     if (factory == null) {
/*  98 */       throw new IllegalArgumentException("Socket factory may not be null");
/*     */     }
/*     */     
/* 101 */     if ((port <= 0) || (port > 65535)) {
/* 102 */       throw new IllegalArgumentException("Port is invalid: " + port);
/*     */     }
/*     */     
/*     */ 
/* 106 */     this.name = name.toLowerCase(Locale.ENGLISH);
/* 107 */     this.socketFactory = factory;
/* 108 */     this.defaultPort = port;
/* 109 */     this.layered = (factory instanceof LayeredSocketFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getDefaultPort()
/*     */   {
/* 119 */     return this.defaultPort;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SocketFactory getSocketFactory()
/*     */   {
/* 131 */     return this.socketFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getName()
/*     */   {
/* 141 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isLayered()
/*     */   {
/* 152 */     return this.layered;
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
/*     */   public final int resolvePort(int port)
/*     */   {
/* 166 */     return port <= 0 ? this.defaultPort : port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String toString()
/*     */   {
/* 177 */     if (this.stringRep == null) {
/* 178 */       StringBuilder buffer = new StringBuilder();
/* 179 */       buffer.append(this.name);
/* 180 */       buffer.append(':');
/* 181 */       buffer.append(Integer.toString(this.defaultPort));
/* 182 */       this.stringRep = buffer.toString();
/*     */     }
/* 184 */     return this.stringRep;
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
/*     */   public final boolean equals(Object obj)
/*     */   {
/* 197 */     if (obj == null) return false;
/* 198 */     if (this == obj) return true;
/* 199 */     if (!(obj instanceof Scheme)) { return false;
/*     */     }
/* 201 */     Scheme s = (Scheme)obj;
/* 202 */     return (this.name.equals(s.name)) && (this.defaultPort == s.defaultPort) && (this.layered == s.layered) && (this.socketFactory.equals(s.socketFactory));
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
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 217 */     int hash = 17;
/* 218 */     hash = LangUtils.hashCode(hash, this.defaultPort);
/* 219 */     hash = LangUtils.hashCode(hash, this.name);
/* 220 */     hash = LangUtils.hashCode(hash, this.layered);
/* 221 */     hash = LangUtils.hashCode(hash, this.socketFactory);
/* 222 */     return hash;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/scheme/Scheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */