/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.ProtocolException;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.client.CircularRedirectException;
/*     */ import org.shaded.apache.http.client.RedirectHandler;
/*     */ import org.shaded.apache.http.client.utils.URIUtils;
/*     */ import org.shaded.apache.http.params.HttpParams;
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
/*     */ @Immutable
/*     */ public class DefaultRedirectHandler
/*     */   implements RedirectHandler
/*     */ {
/*  61 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */   private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRedirectRequested(HttpResponse response, HttpContext context)
/*     */   {
/*  72 */     if (response == null) {
/*  73 */       throw new IllegalArgumentException("HTTP response may not be null");
/*     */     }
/*     */     
/*  76 */     int statusCode = response.getStatusLine().getStatusCode();
/*  77 */     switch (statusCode) {
/*     */     case 301: 
/*     */     case 302: 
/*     */     case 307: 
/*  81 */       HttpRequest request = (HttpRequest)context.getAttribute("http.request");
/*     */       
/*  83 */       String method = request.getRequestLine().getMethod();
/*  84 */       return (method.equalsIgnoreCase("GET")) || (method.equalsIgnoreCase("HEAD"));
/*     */     
/*     */     case 303: 
/*  87 */       return true;
/*     */     }
/*  89 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public URI getLocationURI(HttpResponse response, HttpContext context)
/*     */     throws ProtocolException
/*     */   {
/*  96 */     if (response == null) {
/*  97 */       throw new IllegalArgumentException("HTTP response may not be null");
/*     */     }
/*     */     
/* 100 */     Header locationHeader = response.getFirstHeader("location");
/* 101 */     if (locationHeader == null)
/*     */     {
/* 103 */       throw new ProtocolException("Received redirect response " + response.getStatusLine() + " but no location header");
/*     */     }
/*     */     
/*     */ 
/* 107 */     String location = locationHeader.getValue();
/* 108 */     if (this.log.isDebugEnabled()) {
/* 109 */       this.log.debug("Redirect requested to location '" + location + "'");
/*     */     }
/*     */     URI uri;
/*     */     try
/*     */     {
/* 114 */       uri = new URI(location);
/*     */     } catch (URISyntaxException ex) {
/* 116 */       throw new ProtocolException("Invalid redirect URI: " + location, ex);
/*     */     }
/*     */     
/* 119 */     HttpParams params = response.getParams();
/*     */     
/*     */ 
/* 122 */     if (!uri.isAbsolute()) {
/* 123 */       if (params.isParameterTrue("http.protocol.reject-relative-redirect")) {
/* 124 */         throw new ProtocolException("Relative redirect location '" + uri + "' not allowed");
/*     */       }
/*     */       
/*     */ 
/* 128 */       HttpHost target = (HttpHost)context.getAttribute("http.target_host");
/*     */       
/* 130 */       if (target == null) {
/* 131 */         throw new IllegalStateException("Target host not available in the HTTP context");
/*     */       }
/*     */       
/*     */ 
/* 135 */       HttpRequest request = (HttpRequest)context.getAttribute("http.request");
/*     */       
/*     */       try
/*     */       {
/* 139 */         URI requestURI = new URI(request.getRequestLine().getUri());
/* 140 */         URI absoluteRequestURI = URIUtils.rewriteURI(requestURI, target, true);
/* 141 */         uri = URIUtils.resolve(absoluteRequestURI, uri);
/*     */       } catch (URISyntaxException ex) {
/* 143 */         throw new ProtocolException(ex.getMessage(), ex);
/*     */       }
/*     */     }
/*     */     
/* 147 */     if (params.isParameterFalse("http.protocol.allow-circular-redirects"))
/*     */     {
/* 149 */       RedirectLocations redirectLocations = (RedirectLocations)context.getAttribute("http.protocol.redirect-locations");
/*     */       
/*     */ 
/* 152 */       if (redirectLocations == null) {
/* 153 */         redirectLocations = new RedirectLocations();
/* 154 */         context.setAttribute("http.protocol.redirect-locations", redirectLocations);
/*     */       }
/*     */       
/*     */       URI redirectURI;
/* 158 */       if (uri.getFragment() != null) {
/*     */         try {
/* 160 */           HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
/*     */           
/*     */ 
/*     */ 
/* 164 */           redirectURI = URIUtils.rewriteURI(uri, target, true);
/*     */         } catch (URISyntaxException ex) {
/* 166 */           throw new ProtocolException(ex.getMessage(), ex);
/*     */         }
/*     */       } else {
/* 169 */         redirectURI = uri;
/*     */       }
/*     */       
/* 172 */       if (redirectLocations.contains(redirectURI)) {
/* 173 */         throw new CircularRedirectException("Circular redirect to '" + redirectURI + "'");
/*     */       }
/*     */       
/* 176 */       redirectLocations.add(redirectURI);
/*     */     }
/*     */     
/*     */ 
/* 180 */     return uri;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/DefaultRedirectHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */