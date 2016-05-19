/*     */ package org.shaded.apache.http.client.protocol;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpRequestInterceptor;
/*     */ import org.shaded.apache.http.ProtocolException;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.client.CookieStore;
/*     */ import org.shaded.apache.http.client.methods.HttpUriRequest;
/*     */ import org.shaded.apache.http.client.params.HttpClientParams;
/*     */ import org.shaded.apache.http.conn.ManagedClientConnection;
/*     */ import org.shaded.apache.http.conn.scheme.Scheme;
/*     */ import org.shaded.apache.http.conn.scheme.SchemeRegistry;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.CookieSpec;
/*     */ import org.shaded.apache.http.cookie.CookieSpecRegistry;
/*     */ import org.shaded.apache.http.cookie.SetCookie2;
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
/*     */ @Immutable
/*     */ public class RequestAddCookies
/*     */   implements HttpRequestInterceptor
/*     */ {
/*  79 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(HttpRequest request, HttpContext context)
/*     */     throws HttpException, IOException
/*     */   {
/*  87 */     if (request == null) {
/*  88 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/*  90 */     if (context == null) {
/*  91 */       throw new IllegalArgumentException("HTTP context may not be null");
/*     */     }
/*     */     
/*  94 */     String method = request.getRequestLine().getMethod();
/*  95 */     if (method.equalsIgnoreCase("CONNECT")) {
/*  96 */       return;
/*     */     }
/*     */     
/*     */ 
/* 100 */     CookieStore cookieStore = (CookieStore)context.getAttribute("http.cookie-store");
/*     */     
/* 102 */     if (cookieStore == null) {
/* 103 */       this.log.info("Cookie store not available in HTTP context");
/* 104 */       return;
/*     */     }
/*     */     
/*     */ 
/* 108 */     CookieSpecRegistry registry = (CookieSpecRegistry)context.getAttribute("http.cookiespec-registry");
/*     */     
/* 110 */     if (registry == null) {
/* 111 */       this.log.info("CookieSpec registry not available in HTTP context");
/* 112 */       return;
/*     */     }
/*     */     
/*     */ 
/* 116 */     HttpHost targetHost = (HttpHost)context.getAttribute("http.target_host");
/*     */     
/* 118 */     if (targetHost == null) {
/* 119 */       throw new IllegalStateException("Target host not specified in HTTP context");
/*     */     }
/*     */     
/*     */ 
/* 123 */     ManagedClientConnection conn = (ManagedClientConnection)context.getAttribute("http.connection");
/*     */     
/* 125 */     if (conn == null) {
/* 126 */       throw new IllegalStateException("Client connection not specified in HTTP context");
/*     */     }
/*     */     
/* 129 */     String policy = HttpClientParams.getCookiePolicy(request.getParams());
/* 130 */     if (this.log.isDebugEnabled()) {
/* 131 */       this.log.debug("CookieSpec selected: " + policy);
/*     */     }
/*     */     URI requestURI;
/*     */     URI requestURI;
/* 135 */     if ((request instanceof HttpUriRequest)) {
/* 136 */       requestURI = ((HttpUriRequest)request).getURI();
/*     */     } else {
/*     */       try {
/* 139 */         requestURI = new URI(request.getRequestLine().getUri());
/*     */       } catch (URISyntaxException ex) {
/* 141 */         throw new ProtocolException("Invalid request URI: " + request.getRequestLine().getUri(), ex);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 146 */     String hostName = targetHost.getHostName();
/* 147 */     int port = targetHost.getPort();
/* 148 */     if (port < 0)
/*     */     {
/*     */ 
/* 151 */       SchemeRegistry sr = (SchemeRegistry)context.getAttribute("http.scheme-registry");
/*     */       
/* 153 */       if (sr != null) {
/* 154 */         Scheme scheme = sr.get(targetHost.getSchemeName());
/* 155 */         port = scheme.resolvePort(port);
/*     */       } else {
/* 157 */         port = conn.getRemotePort();
/*     */       }
/*     */     }
/*     */     
/* 161 */     CookieOrigin cookieOrigin = new CookieOrigin(hostName, port, requestURI.getPath(), conn.isSecure());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 168 */     CookieSpec cookieSpec = registry.getCookieSpec(policy, request.getParams());
/*     */     
/* 170 */     List<Cookie> cookies = new ArrayList(cookieStore.getCookies());
/*     */     
/* 172 */     List<Cookie> matchedCookies = new ArrayList();
/* 173 */     Date now = new Date();
/* 174 */     for (Cookie cookie : cookies) {
/* 175 */       if (!cookie.isExpired(now)) {
/* 176 */         if (cookieSpec.match(cookie, cookieOrigin)) {
/* 177 */           if (this.log.isDebugEnabled()) {
/* 178 */             this.log.debug("Cookie " + cookie + " match " + cookieOrigin);
/*     */           }
/* 180 */           matchedCookies.add(cookie);
/*     */         }
/*     */       }
/* 183 */       else if (this.log.isDebugEnabled()) {
/* 184 */         this.log.debug("Cookie " + cookie + " expired");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 189 */     if (!matchedCookies.isEmpty()) {
/* 190 */       List<Header> headers = cookieSpec.formatCookies(matchedCookies);
/* 191 */       for (Header header : headers) {
/* 192 */         request.addHeader(header);
/*     */       }
/*     */     }
/*     */     
/* 196 */     int ver = cookieSpec.getVersion();
/* 197 */     if (ver > 0) {
/* 198 */       boolean needVersionHeader = false;
/* 199 */       for (Cookie cookie : matchedCookies) {
/* 200 */         if ((ver != cookie.getVersion()) || (!(cookie instanceof SetCookie2))) {
/* 201 */           needVersionHeader = true;
/*     */         }
/*     */       }
/*     */       
/* 205 */       if (needVersionHeader) {
/* 206 */         Header header = cookieSpec.getVersionHeader();
/* 207 */         if (header != null)
/*     */         {
/* 209 */           request.addHeader(header);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 216 */     context.setAttribute("http.cookie-spec", cookieSpec);
/* 217 */     context.setAttribute("http.cookie-origin", cookieOrigin);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/protocol/RequestAddCookies.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */