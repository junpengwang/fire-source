/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.Proxy.Type;
/*     */ import java.net.ProxySelector;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.conn.params.ConnRouteParams;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoutePlanner;
/*     */ import org.shaded.apache.http.conn.scheme.Scheme;
/*     */ import org.shaded.apache.http.conn.scheme.SchemeRegistry;
/*     */ import org.shaded.apache.http.protocol.HttpContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class ProxySelectorRoutePlanner
/*     */   implements HttpRoutePlanner
/*     */ {
/*     */   protected final SchemeRegistry schemeRegistry;
/*     */   protected ProxySelector proxySelector;
/*     */   
/*     */   public ProxySelectorRoutePlanner(SchemeRegistry schreg, ProxySelector prosel)
/*     */   {
/*  91 */     if (schreg == null) {
/*  92 */       throw new IllegalArgumentException("SchemeRegistry must not be null.");
/*     */     }
/*     */     
/*  95 */     this.schemeRegistry = schreg;
/*  96 */     this.proxySelector = prosel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ProxySelector getProxySelector()
/*     */   {
/* 105 */     return this.proxySelector;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProxySelector(ProxySelector prosel)
/*     */   {
/* 115 */     this.proxySelector = prosel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
/*     */     throws HttpException
/*     */   {
/* 123 */     if (request == null) {
/* 124 */       throw new IllegalStateException("Request must not be null.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 129 */     HttpRoute route = ConnRouteParams.getForcedRoute(request.getParams());
/*     */     
/* 131 */     if (route != null) {
/* 132 */       return route;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 137 */     if (target == null) {
/* 138 */       throw new IllegalStateException("Target host must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 142 */     InetAddress local = ConnRouteParams.getLocalAddress(request.getParams());
/*     */     
/* 144 */     HttpHost proxy = determineProxy(target, request, context);
/*     */     
/* 146 */     Scheme schm = this.schemeRegistry.getScheme(target.getSchemeName());
/*     */     
/*     */ 
/*     */ 
/* 150 */     boolean secure = schm.isLayered();
/*     */     
/* 152 */     if (proxy == null) {
/* 153 */       route = new HttpRoute(target, local, secure);
/*     */     } else {
/* 155 */       route = new HttpRoute(target, local, proxy, secure);
/*     */     }
/* 157 */     return route;
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
/*     */   protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context)
/*     */     throws HttpException
/*     */   {
/* 178 */     ProxySelector psel = this.proxySelector;
/* 179 */     if (psel == null)
/* 180 */       psel = ProxySelector.getDefault();
/* 181 */     if (psel == null) {
/* 182 */       return null;
/*     */     }
/* 184 */     URI targetURI = null;
/*     */     try {
/* 186 */       targetURI = new URI(target.toURI());
/*     */     } catch (URISyntaxException usx) {
/* 188 */       throw new HttpException("Cannot convert host to URI: " + target, usx);
/*     */     }
/*     */     
/* 191 */     List<Proxy> proxies = psel.select(targetURI);
/*     */     
/* 193 */     Proxy p = chooseProxy(proxies, target, request, context);
/*     */     
/* 195 */     HttpHost result = null;
/* 196 */     if (p.type() == Proxy.Type.HTTP)
/*     */     {
/* 198 */       if (!(p.address() instanceof InetSocketAddress)) {
/* 199 */         throw new HttpException("Unable to handle non-Inet proxy address: " + p.address());
/*     */       }
/*     */       
/* 202 */       InetSocketAddress isa = (InetSocketAddress)p.address();
/*     */       
/* 204 */       result = new HttpHost(getHost(isa), isa.getPort());
/*     */     }
/*     */     
/* 207 */     return result;
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
/*     */   protected String getHost(InetSocketAddress isa)
/*     */   {
/* 225 */     return isa.isUnresolved() ? isa.getHostName() : isa.getAddress().getHostAddress();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Proxy chooseProxy(List<Proxy> proxies, HttpHost target, HttpRequest request, HttpContext context)
/*     */   {
/* 251 */     if ((proxies == null) || (proxies.isEmpty())) {
/* 252 */       throw new IllegalArgumentException("Proxy list must not be empty.");
/*     */     }
/*     */     
/*     */ 
/* 256 */     Proxy result = null;
/*     */     
/*     */ 
/* 259 */     for (int i = 0; (result == null) && (i < proxies.size()); i++)
/*     */     {
/* 261 */       Proxy p = (Proxy)proxies.get(i);
/* 262 */       switch (p.type())
/*     */       {
/*     */       case DIRECT: 
/*     */       case HTTP: 
/* 266 */         result = p;
/*     */       }
/*     */       
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 276 */     if (result == null)
/*     */     {
/*     */ 
/*     */ 
/* 280 */       result = Proxy.NO_PROXY;
/*     */     }
/*     */     
/* 283 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/ProxySelectorRoutePlanner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */