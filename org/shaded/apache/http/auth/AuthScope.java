/*     */ package org.shaded.apache.http.auth;
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
/*     */ @Immutable
/*     */ public class AuthScope
/*     */ {
/*  50 */   public static final String ANY_HOST = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int ANY_PORT = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  60 */   public static final String ANY_REALM = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  65 */   public static final String ANY_SCHEME = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  72 */   public static final AuthScope ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String scheme;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String realm;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String host;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int port;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AuthScope(String host, int port, String realm, String scheme)
/*     */   {
/* 106 */     this.host = (host == null ? ANY_HOST : host.toLowerCase(Locale.ENGLISH));
/* 107 */     this.port = (port < 0 ? -1 : port);
/* 108 */     this.realm = (realm == null ? ANY_REALM : realm);
/* 109 */     this.scheme = (scheme == null ? ANY_SCHEME : scheme.toUpperCase(Locale.ENGLISH));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public AuthScope(String host, int port, String realm)
/*     */   {
/* 127 */     this(host, port, realm, ANY_SCHEME);
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
/*     */   public AuthScope(String host, int port)
/*     */   {
/* 142 */     this(host, port, ANY_REALM, ANY_SCHEME);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AuthScope(AuthScope authscope)
/*     */   {
/* 150 */     if (authscope == null) {
/* 151 */       throw new IllegalArgumentException("Scope may not be null");
/*     */     }
/* 153 */     this.host = authscope.getHost();
/* 154 */     this.port = authscope.getPort();
/* 155 */     this.realm = authscope.getRealm();
/* 156 */     this.scheme = authscope.getScheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getHost()
/*     */   {
/* 163 */     return this.host;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getPort()
/*     */   {
/* 170 */     return this.port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getRealm()
/*     */   {
/* 177 */     return this.realm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getScheme()
/*     */   {
/* 184 */     return this.scheme;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int match(AuthScope that)
/*     */   {
/* 195 */     int factor = 0;
/* 196 */     if (LangUtils.equals(this.scheme, that.scheme)) {
/* 197 */       factor++;
/*     */     }
/* 199 */     else if ((this.scheme != ANY_SCHEME) && (that.scheme != ANY_SCHEME)) {
/* 200 */       return -1;
/*     */     }
/*     */     
/* 203 */     if (LangUtils.equals(this.realm, that.realm)) {
/* 204 */       factor += 2;
/*     */     }
/* 206 */     else if ((this.realm != ANY_REALM) && (that.realm != ANY_REALM)) {
/* 207 */       return -1;
/*     */     }
/*     */     
/* 210 */     if (this.port == that.port) {
/* 211 */       factor += 4;
/*     */     }
/* 213 */     else if ((this.port != -1) && (that.port != -1)) {
/* 214 */       return -1;
/*     */     }
/*     */     
/* 217 */     if (LangUtils.equals(this.host, that.host)) {
/* 218 */       factor += 8;
/*     */     }
/* 220 */     else if ((this.host != ANY_HOST) && (that.host != ANY_HOST)) {
/* 221 */       return -1;
/*     */     }
/*     */     
/* 224 */     return factor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 232 */     if (o == null) {
/* 233 */       return false;
/*     */     }
/* 235 */     if (o == this) {
/* 236 */       return true;
/*     */     }
/* 238 */     if (!(o instanceof AuthScope)) {
/* 239 */       return super.equals(o);
/*     */     }
/* 241 */     AuthScope that = (AuthScope)o;
/* 242 */     return (LangUtils.equals(this.host, that.host)) && (this.port == that.port) && (LangUtils.equals(this.realm, that.realm)) && (LangUtils.equals(this.scheme, that.scheme));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 254 */     StringBuffer buffer = new StringBuffer();
/* 255 */     if (this.scheme != null) {
/* 256 */       buffer.append(this.scheme.toUpperCase(Locale.ENGLISH));
/* 257 */       buffer.append(' ');
/*     */     }
/* 259 */     if (this.realm != null) {
/* 260 */       buffer.append('\'');
/* 261 */       buffer.append(this.realm);
/* 262 */       buffer.append('\'');
/*     */     } else {
/* 264 */       buffer.append("<any realm>");
/*     */     }
/* 266 */     if (this.host != null) {
/* 267 */       buffer.append('@');
/* 268 */       buffer.append(this.host);
/* 269 */       if (this.port >= 0) {
/* 270 */         buffer.append(':');
/* 271 */         buffer.append(this.port);
/*     */       }
/*     */     }
/* 274 */     return buffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 282 */     int hash = 17;
/* 283 */     hash = LangUtils.hashCode(hash, this.host);
/* 284 */     hash = LangUtils.hashCode(hash, this.port);
/* 285 */     hash = LangUtils.hashCode(hash, this.realm);
/* 286 */     hash = LangUtils.hashCode(hash, this.scheme);
/* 287 */     return hash;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/auth/AuthScope.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */