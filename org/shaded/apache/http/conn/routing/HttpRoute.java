/*     */ package org.shaded.apache.http.conn.routing;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import org.shaded.apache.http.HttpHost;
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
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class HttpRoute
/*     */   implements RouteInfo, Cloneable
/*     */ {
/*  46 */   private static final HttpHost[] EMPTY_HTTP_HOST_ARRAY = new HttpHost[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final HttpHost targetHost;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final InetAddress localAddress;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final HttpHost[] proxyChain;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final RouteInfo.TunnelType tunnelled;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final RouteInfo.LayerType layered;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean secure;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private HttpRoute(InetAddress local, HttpHost target, HttpHost[] proxies, boolean secure, RouteInfo.TunnelType tunnelled, RouteInfo.LayerType layered)
/*     */   {
/*  94 */     if (target == null) {
/*  95 */       throw new IllegalArgumentException("Target host may not be null.");
/*     */     }
/*     */     
/*  98 */     if (proxies == null) {
/*  99 */       throw new IllegalArgumentException("Proxies may not be null.");
/*     */     }
/*     */     
/* 102 */     if ((tunnelled == RouteInfo.TunnelType.TUNNELLED) && (proxies.length == 0)) {
/* 103 */       throw new IllegalArgumentException("Proxy required if tunnelled.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 108 */     if (tunnelled == null)
/* 109 */       tunnelled = RouteInfo.TunnelType.PLAIN;
/* 110 */     if (layered == null) {
/* 111 */       layered = RouteInfo.LayerType.PLAIN;
/*     */     }
/* 113 */     this.targetHost = target;
/* 114 */     this.localAddress = local;
/* 115 */     this.proxyChain = proxies;
/* 116 */     this.secure = secure;
/* 117 */     this.tunnelled = tunnelled;
/* 118 */     this.layered = layered;
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
/*     */ 
/*     */   public HttpRoute(HttpHost target, InetAddress local, HttpHost[] proxies, boolean secure, RouteInfo.TunnelType tunnelled, RouteInfo.LayerType layered)
/*     */   {
/* 137 */     this(local, target, toChain(proxies), secure, tunnelled, layered);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpRoute(HttpHost target, InetAddress local, HttpHost proxy, boolean secure, RouteInfo.TunnelType tunnelled, RouteInfo.LayerType layered)
/*     */   {
/* 160 */     this(local, target, toChain(proxy), secure, tunnelled, layered);
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
/*     */   public HttpRoute(HttpHost target, InetAddress local, boolean secure)
/*     */   {
/* 175 */     this(local, target, EMPTY_HTTP_HOST_ARRAY, secure, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpRoute(HttpHost target)
/*     */   {
/* 185 */     this(null, target, EMPTY_HTTP_HOST_ARRAY, false, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
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
/*     */ 
/*     */   public HttpRoute(HttpHost target, InetAddress local, HttpHost proxy, boolean secure)
/*     */   {
/* 204 */     this(local, target, toChain(proxy), secure, secure ? RouteInfo.TunnelType.TUNNELLED : RouteInfo.TunnelType.PLAIN, secure ? RouteInfo.LayerType.LAYERED : RouteInfo.LayerType.PLAIN);
/*     */     
/*     */ 
/* 207 */     if (proxy == null) {
/* 208 */       throw new IllegalArgumentException("Proxy host may not be null.");
/*     */     }
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
/*     */   private static HttpHost[] toChain(HttpHost proxy)
/*     */   {
/* 222 */     if (proxy == null) {
/* 223 */       return EMPTY_HTTP_HOST_ARRAY;
/*     */     }
/* 225 */     return new HttpHost[] { proxy };
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
/*     */   private static HttpHost[] toChain(HttpHost[] proxies)
/*     */   {
/* 238 */     if ((proxies == null) || (proxies.length < 1)) {
/* 239 */       return EMPTY_HTTP_HOST_ARRAY;
/*     */     }
/* 241 */     for (HttpHost proxy : proxies) {
/* 242 */       if (proxy == null) {
/* 243 */         throw new IllegalArgumentException("Proxy chain may not contain null elements.");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 248 */     HttpHost[] result = new HttpHost[proxies.length];
/* 249 */     System.arraycopy(proxies, 0, result, 0, proxies.length);
/*     */     
/* 251 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final HttpHost getTargetHost()
/*     */   {
/* 258 */     return this.targetHost;
/*     */   }
/*     */   
/*     */ 
/*     */   public final InetAddress getLocalAddress()
/*     */   {
/* 264 */     return this.localAddress;
/*     */   }
/*     */   
/*     */   public final int getHopCount()
/*     */   {
/* 269 */     return this.proxyChain.length + 1;
/*     */   }
/*     */   
/*     */   public final HttpHost getHopTarget(int hop)
/*     */   {
/* 274 */     if (hop < 0) {
/* 275 */       throw new IllegalArgumentException("Hop index must not be negative: " + hop);
/*     */     }
/* 277 */     int hopcount = getHopCount();
/* 278 */     if (hop >= hopcount) {
/* 279 */       throw new IllegalArgumentException("Hop index " + hop + " exceeds route length " + hopcount);
/*     */     }
/*     */     
/*     */ 
/* 283 */     HttpHost result = null;
/* 284 */     if (hop < hopcount - 1) {
/* 285 */       result = this.proxyChain[hop];
/*     */     } else {
/* 287 */       result = this.targetHost;
/*     */     }
/* 289 */     return result;
/*     */   }
/*     */   
/*     */   public final HttpHost getProxyHost()
/*     */   {
/* 294 */     return this.proxyChain.length == 0 ? null : this.proxyChain[0];
/*     */   }
/*     */   
/*     */   public final RouteInfo.TunnelType getTunnelType()
/*     */   {
/* 299 */     return this.tunnelled;
/*     */   }
/*     */   
/*     */   public final boolean isTunnelled()
/*     */   {
/* 304 */     return this.tunnelled == RouteInfo.TunnelType.TUNNELLED;
/*     */   }
/*     */   
/*     */   public final RouteInfo.LayerType getLayerType()
/*     */   {
/* 309 */     return this.layered;
/*     */   }
/*     */   
/*     */   public final boolean isLayered()
/*     */   {
/* 314 */     return this.layered == RouteInfo.LayerType.LAYERED;
/*     */   }
/*     */   
/*     */   public final boolean isSecure()
/*     */   {
/* 319 */     return this.secure;
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
/*     */   public final boolean equals(Object o)
/*     */   {
/* 333 */     if (o == this)
/* 334 */       return true;
/* 335 */     if (!(o instanceof HttpRoute)) {
/* 336 */       return false;
/*     */     }
/* 338 */     HttpRoute that = (HttpRoute)o;
/* 339 */     boolean equal = this.targetHost.equals(that.targetHost);
/* 340 */     equal &= ((this.localAddress == that.localAddress) || ((this.localAddress != null) && (this.localAddress.equals(that.localAddress))));
/*     */     
/*     */ 
/*     */ 
/* 344 */     equal &= ((this.proxyChain == that.proxyChain) || (this.proxyChain.length == that.proxyChain.length));
/*     */     
/*     */ 
/*     */ 
/* 348 */     equal &= ((this.secure == that.secure) && (this.tunnelled == that.tunnelled) && (this.layered == that.layered));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 354 */     if ((equal) && (this.proxyChain != null)) {
/* 355 */       for (int i = 0; (equal) && (i < this.proxyChain.length); i++) {
/* 356 */         equal = this.proxyChain[i].equals(that.proxyChain[i]);
/*     */       }
/*     */     }
/* 359 */     return equal;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 371 */     int hc = this.targetHost.hashCode();
/*     */     
/* 373 */     if (this.localAddress != null)
/* 374 */       hc ^= this.localAddress.hashCode();
/* 375 */     hc ^= this.proxyChain.length;
/* 376 */     for (HttpHost aProxyChain : this.proxyChain) { hc ^= aProxyChain.hashCode();
/*     */     }
/* 378 */     if (this.secure) {
/* 379 */       hc ^= 0x11111111;
/*     */     }
/* 381 */     hc ^= this.tunnelled.hashCode();
/* 382 */     hc ^= this.layered.hashCode();
/*     */     
/* 384 */     return hc;
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
/* 395 */     StringBuilder cab = new StringBuilder(50 + getHopCount() * 30);
/*     */     
/* 397 */     cab.append("HttpRoute[");
/* 398 */     if (this.localAddress != null) {
/* 399 */       cab.append(this.localAddress);
/* 400 */       cab.append("->");
/*     */     }
/* 402 */     cab.append('{');
/* 403 */     if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED)
/* 404 */       cab.append('t');
/* 405 */     if (this.layered == RouteInfo.LayerType.LAYERED)
/* 406 */       cab.append('l');
/* 407 */     if (this.secure)
/* 408 */       cab.append('s');
/* 409 */     cab.append("}->");
/* 410 */     for (HttpHost aProxyChain : this.proxyChain) {
/* 411 */       cab.append(aProxyChain);
/* 412 */       cab.append("->");
/*     */     }
/* 414 */     cab.append(this.targetHost);
/* 415 */     cab.append(']');
/*     */     
/* 417 */     return cab.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 424 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/routing/HttpRoute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */