/*     */ package org.shaded.apache.http.conn.routing;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotThreadSafe
/*     */ public final class RouteTracker
/*     */   implements RouteInfo, Cloneable
/*     */ {
/*     */   private final HttpHost targetHost;
/*     */   private final InetAddress localAddress;
/*     */   private boolean connected;
/*     */   private HttpHost[] proxyChain;
/*     */   private RouteInfo.TunnelType tunnelled;
/*     */   private RouteInfo.LayerType layered;
/*     */   private boolean secure;
/*     */   
/*     */   public RouteTracker(HttpHost target, InetAddress local)
/*     */   {
/*  80 */     if (target == null) {
/*  81 */       throw new IllegalArgumentException("Target host may not be null.");
/*     */     }
/*  83 */     this.targetHost = target;
/*  84 */     this.localAddress = local;
/*  85 */     this.tunnelled = RouteInfo.TunnelType.PLAIN;
/*  86 */     this.layered = RouteInfo.LayerType.PLAIN;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RouteTracker(HttpRoute route)
/*     */   {
/*  98 */     this(route.getTargetHost(), route.getLocalAddress());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void connectTarget(boolean secure)
/*     */   {
/* 108 */     if (this.connected) {
/* 109 */       throw new IllegalStateException("Already connected.");
/*     */     }
/* 111 */     this.connected = true;
/* 112 */     this.secure = secure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void connectProxy(HttpHost proxy, boolean secure)
/*     */   {
/* 123 */     if (proxy == null) {
/* 124 */       throw new IllegalArgumentException("Proxy host may not be null.");
/*     */     }
/* 126 */     if (this.connected) {
/* 127 */       throw new IllegalStateException("Already connected.");
/*     */     }
/* 129 */     this.connected = true;
/* 130 */     this.proxyChain = new HttpHost[] { proxy };
/* 131 */     this.secure = secure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void tunnelTarget(boolean secure)
/*     */   {
/* 141 */     if (!this.connected) {
/* 142 */       throw new IllegalStateException("No tunnel unless connected.");
/*     */     }
/* 144 */     if (this.proxyChain == null) {
/* 145 */       throw new IllegalStateException("No tunnel without proxy.");
/*     */     }
/* 147 */     this.tunnelled = RouteInfo.TunnelType.TUNNELLED;
/* 148 */     this.secure = secure;
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
/*     */   public final void tunnelProxy(HttpHost proxy, boolean secure)
/*     */   {
/* 161 */     if (proxy == null) {
/* 162 */       throw new IllegalArgumentException("Proxy host may not be null.");
/*     */     }
/* 164 */     if (!this.connected) {
/* 165 */       throw new IllegalStateException("No tunnel unless connected.");
/*     */     }
/* 167 */     if (this.proxyChain == null) {
/* 168 */       throw new IllegalStateException("No proxy tunnel without proxy.");
/*     */     }
/*     */     
/*     */ 
/* 172 */     HttpHost[] proxies = new HttpHost[this.proxyChain.length + 1];
/* 173 */     System.arraycopy(this.proxyChain, 0, proxies, 0, this.proxyChain.length);
/*     */     
/* 175 */     proxies[(proxies.length - 1)] = proxy;
/*     */     
/* 177 */     this.proxyChain = proxies;
/* 178 */     this.secure = secure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void layerProtocol(boolean secure)
/*     */   {
/* 190 */     if (!this.connected) {
/* 191 */       throw new IllegalStateException("No layered protocol unless connected.");
/*     */     }
/*     */     
/* 194 */     this.layered = RouteInfo.LayerType.LAYERED;
/* 195 */     this.secure = secure;
/*     */   }
/*     */   
/*     */   public final HttpHost getTargetHost() {
/* 199 */     return this.targetHost;
/*     */   }
/*     */   
/*     */   public final InetAddress getLocalAddress() {
/* 203 */     return this.localAddress;
/*     */   }
/*     */   
/*     */   public final int getHopCount() {
/* 207 */     int hops = 0;
/* 208 */     if (this.connected) {
/* 209 */       if (this.proxyChain == null) {
/* 210 */         hops = 1;
/*     */       } else
/* 212 */         hops = this.proxyChain.length + 1;
/*     */     }
/* 214 */     return hops;
/*     */   }
/*     */   
/*     */   public final HttpHost getHopTarget(int hop) {
/* 218 */     if (hop < 0) {
/* 219 */       throw new IllegalArgumentException("Hop index must not be negative: " + hop);
/*     */     }
/* 221 */     int hopcount = getHopCount();
/* 222 */     if (hop >= hopcount) {
/* 223 */       throw new IllegalArgumentException("Hop index " + hop + " exceeds tracked route length " + hopcount + ".");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 228 */     HttpHost result = null;
/* 229 */     if (hop < hopcount - 1) {
/* 230 */       result = this.proxyChain[hop];
/*     */     } else {
/* 232 */       result = this.targetHost;
/*     */     }
/* 234 */     return result;
/*     */   }
/*     */   
/*     */   public final HttpHost getProxyHost() {
/* 238 */     return this.proxyChain == null ? null : this.proxyChain[0];
/*     */   }
/*     */   
/*     */   public final boolean isConnected() {
/* 242 */     return this.connected;
/*     */   }
/*     */   
/*     */   public final RouteInfo.TunnelType getTunnelType() {
/* 246 */     return this.tunnelled;
/*     */   }
/*     */   
/*     */   public final boolean isTunnelled() {
/* 250 */     return this.tunnelled == RouteInfo.TunnelType.TUNNELLED;
/*     */   }
/*     */   
/*     */   public final RouteInfo.LayerType getLayerType() {
/* 254 */     return this.layered;
/*     */   }
/*     */   
/*     */   public final boolean isLayered() {
/* 258 */     return this.layered == RouteInfo.LayerType.LAYERED;
/*     */   }
/*     */   
/*     */   public final boolean isSecure() {
/* 262 */     return this.secure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final HttpRoute toRoute()
/*     */   {
/* 274 */     return !this.connected ? null : new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered);
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
/*     */   public final boolean equals(Object o)
/*     */   {
/* 290 */     if (o == this)
/* 291 */       return true;
/* 292 */     if (!(o instanceof RouteTracker)) {
/* 293 */       return false;
/*     */     }
/* 295 */     RouteTracker that = (RouteTracker)o;
/* 296 */     boolean equal = this.targetHost.equals(that.targetHost);
/* 297 */     equal &= ((this.localAddress == that.localAddress) || ((this.localAddress != null) && (this.localAddress.equals(that.localAddress))));
/*     */     
/*     */ 
/*     */ 
/* 301 */     equal &= ((this.proxyChain == that.proxyChain) || ((this.proxyChain != null) && (that.proxyChain != null) && (this.proxyChain.length == that.proxyChain.length)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 307 */     equal &= ((this.connected == that.connected) && (this.secure == that.secure) && (this.tunnelled == that.tunnelled) && (this.layered == that.layered));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 314 */     if ((equal) && (this.proxyChain != null)) {
/* 315 */       for (int i = 0; (equal) && (i < this.proxyChain.length); i++) {
/* 316 */         equal = this.proxyChain[i].equals(that.proxyChain[i]);
/*     */       }
/*     */     }
/* 319 */     return equal;
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
/*     */   public final int hashCode()
/*     */   {
/* 333 */     int hc = this.targetHost.hashCode();
/*     */     
/* 335 */     if (this.localAddress != null)
/* 336 */       hc ^= this.localAddress.hashCode();
/* 337 */     if (this.proxyChain != null) {
/* 338 */       hc ^= this.proxyChain.length;
/* 339 */       for (int i = 0; i < this.proxyChain.length; i++) {
/* 340 */         hc ^= this.proxyChain[i].hashCode();
/*     */       }
/*     */     }
/* 343 */     if (this.connected)
/* 344 */       hc ^= 0x11111111;
/* 345 */     if (this.secure) {
/* 346 */       hc ^= 0x22222222;
/*     */     }
/* 348 */     hc ^= this.tunnelled.hashCode();
/* 349 */     hc ^= this.layered.hashCode();
/*     */     
/* 351 */     return hc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String toString()
/*     */   {
/* 361 */     StringBuilder cab = new StringBuilder(50 + getHopCount() * 30);
/*     */     
/* 363 */     cab.append("RouteTracker[");
/* 364 */     if (this.localAddress != null) {
/* 365 */       cab.append(this.localAddress);
/* 366 */       cab.append("->");
/*     */     }
/* 368 */     cab.append('{');
/* 369 */     if (this.connected)
/* 370 */       cab.append('c');
/* 371 */     if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED)
/* 372 */       cab.append('t');
/* 373 */     if (this.layered == RouteInfo.LayerType.LAYERED)
/* 374 */       cab.append('l');
/* 375 */     if (this.secure)
/* 376 */       cab.append('s');
/* 377 */     cab.append("}->");
/* 378 */     if (this.proxyChain != null) {
/* 379 */       for (int i = 0; i < this.proxyChain.length; i++) {
/* 380 */         cab.append(this.proxyChain[i]);
/* 381 */         cab.append("->");
/*     */       }
/*     */     }
/* 384 */     cab.append(this.targetHost);
/* 385 */     cab.append(']');
/*     */     
/* 387 */     return cab.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 394 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/routing/RouteTracker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */