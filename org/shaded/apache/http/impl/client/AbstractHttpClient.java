/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.net.URI;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.ConnectionReuseStrategy;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpRequestInterceptor;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpResponseInterceptor;
/*     */ import org.shaded.apache.http.annotation.GuardedBy;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.auth.AuthSchemeRegistry;
/*     */ import org.shaded.apache.http.client.AuthenticationHandler;
/*     */ import org.shaded.apache.http.client.ClientProtocolException;
/*     */ import org.shaded.apache.http.client.CookieStore;
/*     */ import org.shaded.apache.http.client.CredentialsProvider;
/*     */ import org.shaded.apache.http.client.HttpClient;
/*     */ import org.shaded.apache.http.client.HttpRequestRetryHandler;
/*     */ import org.shaded.apache.http.client.RedirectHandler;
/*     */ import org.shaded.apache.http.client.RequestDirector;
/*     */ import org.shaded.apache.http.client.ResponseHandler;
/*     */ import org.shaded.apache.http.client.UserTokenHandler;
/*     */ import org.shaded.apache.http.client.methods.HttpUriRequest;
/*     */ import org.shaded.apache.http.conn.ClientConnectionManager;
/*     */ import org.shaded.apache.http.conn.ConnectionKeepAliveStrategy;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoutePlanner;
/*     */ import org.shaded.apache.http.cookie.CookieSpecRegistry;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ import org.shaded.apache.http.protocol.BasicHttpProcessor;
/*     */ import org.shaded.apache.http.protocol.DefaultedHttpContext;
/*     */ import org.shaded.apache.http.protocol.HttpContext;
/*     */ import org.shaded.apache.http.protocol.HttpProcessor;
/*     */ import org.shaded.apache.http.protocol.HttpRequestExecutor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public abstract class AbstractHttpClient
/*     */   implements HttpClient
/*     */ {
/* 159 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private HttpParams defaultParams;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private HttpRequestExecutor requestExec;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private ClientConnectionManager connManager;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private ConnectionReuseStrategy reuseStrategy;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private ConnectionKeepAliveStrategy keepAliveStrategy;
/*     */   
/*     */ 
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private CookieSpecRegistry supportedCookieSpecs;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private AuthSchemeRegistry supportedAuthSchemes;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private BasicHttpProcessor httpProcessor;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private HttpRequestRetryHandler retryHandler;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private RedirectHandler redirectHandler;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private AuthenticationHandler targetAuthHandler;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private AuthenticationHandler proxyAuthHandler;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private CookieStore cookieStore;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private CredentialsProvider credsProvider;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private HttpRoutePlanner routePlanner;
/*     */   
/*     */ 
/*     */   @GuardedBy("this")
/*     */   private UserTokenHandler userTokenHandler;
/*     */   
/*     */ 
/*     */ 
/*     */   protected AbstractHttpClient(ClientConnectionManager conman, HttpParams params)
/*     */   {
/* 235 */     this.defaultParams = params;
/* 236 */     this.connManager = conman;
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract HttpParams createHttpParams();
/*     */   
/*     */ 
/*     */   protected abstract HttpContext createHttpContext();
/*     */   
/*     */ 
/*     */   protected abstract HttpRequestExecutor createRequestExecutor();
/*     */   
/*     */ 
/*     */   protected abstract ClientConnectionManager createClientConnectionManager();
/*     */   
/*     */ 
/*     */   protected abstract AuthSchemeRegistry createAuthSchemeRegistry();
/*     */   
/*     */ 
/*     */   protected abstract CookieSpecRegistry createCookieSpecRegistry();
/*     */   
/*     */ 
/*     */   protected abstract ConnectionReuseStrategy createConnectionReuseStrategy();
/*     */   
/*     */ 
/*     */   protected abstract ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy();
/*     */   
/*     */ 
/*     */   protected abstract BasicHttpProcessor createHttpProcessor();
/*     */   
/*     */ 
/*     */   protected abstract HttpRequestRetryHandler createHttpRequestRetryHandler();
/*     */   
/*     */ 
/*     */   protected abstract RedirectHandler createRedirectHandler();
/*     */   
/*     */ 
/*     */   protected abstract AuthenticationHandler createTargetAuthenticationHandler();
/*     */   
/*     */ 
/*     */   protected abstract AuthenticationHandler createProxyAuthenticationHandler();
/*     */   
/*     */ 
/*     */   protected abstract CookieStore createCookieStore();
/*     */   
/*     */ 
/*     */   protected abstract CredentialsProvider createCredentialsProvider();
/*     */   
/*     */ 
/*     */   protected abstract HttpRoutePlanner createHttpRoutePlanner();
/*     */   
/*     */ 
/*     */   protected abstract UserTokenHandler createUserTokenHandler();
/*     */   
/*     */   public final synchronized HttpParams getParams()
/*     */   {
/* 292 */     if (this.defaultParams == null) {
/* 293 */       this.defaultParams = createHttpParams();
/*     */     }
/* 295 */     return this.defaultParams;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setParams(HttpParams params)
/*     */   {
/* 306 */     this.defaultParams = params;
/*     */   }
/*     */   
/*     */   public final synchronized ClientConnectionManager getConnectionManager()
/*     */   {
/* 311 */     if (this.connManager == null) {
/* 312 */       this.connManager = createClientConnectionManager();
/*     */     }
/* 314 */     return this.connManager;
/*     */   }
/*     */   
/*     */   public final synchronized HttpRequestExecutor getRequestExecutor()
/*     */   {
/* 319 */     if (this.requestExec == null) {
/* 320 */       this.requestExec = createRequestExecutor();
/*     */     }
/* 322 */     return this.requestExec;
/*     */   }
/*     */   
/*     */   public final synchronized AuthSchemeRegistry getAuthSchemes()
/*     */   {
/* 327 */     if (this.supportedAuthSchemes == null) {
/* 328 */       this.supportedAuthSchemes = createAuthSchemeRegistry();
/*     */     }
/* 330 */     return this.supportedAuthSchemes;
/*     */   }
/*     */   
/*     */   public synchronized void setAuthSchemes(AuthSchemeRegistry authSchemeRegistry)
/*     */   {
/* 335 */     this.supportedAuthSchemes = authSchemeRegistry;
/*     */   }
/*     */   
/*     */   public final synchronized CookieSpecRegistry getCookieSpecs()
/*     */   {
/* 340 */     if (this.supportedCookieSpecs == null) {
/* 341 */       this.supportedCookieSpecs = createCookieSpecRegistry();
/*     */     }
/* 343 */     return this.supportedCookieSpecs;
/*     */   }
/*     */   
/*     */   public synchronized void setCookieSpecs(CookieSpecRegistry cookieSpecRegistry)
/*     */   {
/* 348 */     this.supportedCookieSpecs = cookieSpecRegistry;
/*     */   }
/*     */   
/*     */   public final synchronized ConnectionReuseStrategy getConnectionReuseStrategy()
/*     */   {
/* 353 */     if (this.reuseStrategy == null) {
/* 354 */       this.reuseStrategy = createConnectionReuseStrategy();
/*     */     }
/* 356 */     return this.reuseStrategy;
/*     */   }
/*     */   
/*     */   public synchronized void setReuseStrategy(ConnectionReuseStrategy reuseStrategy)
/*     */   {
/* 361 */     this.reuseStrategy = reuseStrategy;
/*     */   }
/*     */   
/*     */   public final synchronized ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy()
/*     */   {
/* 366 */     if (this.keepAliveStrategy == null) {
/* 367 */       this.keepAliveStrategy = createConnectionKeepAliveStrategy();
/*     */     }
/* 369 */     return this.keepAliveStrategy;
/*     */   }
/*     */   
/*     */   public synchronized void setKeepAliveStrategy(ConnectionKeepAliveStrategy keepAliveStrategy)
/*     */   {
/* 374 */     this.keepAliveStrategy = keepAliveStrategy;
/*     */   }
/*     */   
/*     */   public final synchronized HttpRequestRetryHandler getHttpRequestRetryHandler()
/*     */   {
/* 379 */     if (this.retryHandler == null) {
/* 380 */       this.retryHandler = createHttpRequestRetryHandler();
/*     */     }
/* 382 */     return this.retryHandler;
/*     */   }
/*     */   
/*     */   public synchronized void setHttpRequestRetryHandler(HttpRequestRetryHandler retryHandler)
/*     */   {
/* 387 */     this.retryHandler = retryHandler;
/*     */   }
/*     */   
/*     */   public final synchronized RedirectHandler getRedirectHandler()
/*     */   {
/* 392 */     if (this.redirectHandler == null) {
/* 393 */       this.redirectHandler = createRedirectHandler();
/*     */     }
/* 395 */     return this.redirectHandler;
/*     */   }
/*     */   
/*     */   public synchronized void setRedirectHandler(RedirectHandler redirectHandler)
/*     */   {
/* 400 */     this.redirectHandler = redirectHandler;
/*     */   }
/*     */   
/*     */   public final synchronized AuthenticationHandler getTargetAuthenticationHandler()
/*     */   {
/* 405 */     if (this.targetAuthHandler == null) {
/* 406 */       this.targetAuthHandler = createTargetAuthenticationHandler();
/*     */     }
/* 408 */     return this.targetAuthHandler;
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void setTargetAuthenticationHandler(AuthenticationHandler targetAuthHandler)
/*     */   {
/* 414 */     this.targetAuthHandler = targetAuthHandler;
/*     */   }
/*     */   
/*     */   public final synchronized AuthenticationHandler getProxyAuthenticationHandler()
/*     */   {
/* 419 */     if (this.proxyAuthHandler == null) {
/* 420 */       this.proxyAuthHandler = createProxyAuthenticationHandler();
/*     */     }
/* 422 */     return this.proxyAuthHandler;
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void setProxyAuthenticationHandler(AuthenticationHandler proxyAuthHandler)
/*     */   {
/* 428 */     this.proxyAuthHandler = proxyAuthHandler;
/*     */   }
/*     */   
/*     */   public final synchronized CookieStore getCookieStore()
/*     */   {
/* 433 */     if (this.cookieStore == null) {
/* 434 */       this.cookieStore = createCookieStore();
/*     */     }
/* 436 */     return this.cookieStore;
/*     */   }
/*     */   
/*     */   public synchronized void setCookieStore(CookieStore cookieStore)
/*     */   {
/* 441 */     this.cookieStore = cookieStore;
/*     */   }
/*     */   
/*     */   public final synchronized CredentialsProvider getCredentialsProvider()
/*     */   {
/* 446 */     if (this.credsProvider == null) {
/* 447 */       this.credsProvider = createCredentialsProvider();
/*     */     }
/* 449 */     return this.credsProvider;
/*     */   }
/*     */   
/*     */   public synchronized void setCredentialsProvider(CredentialsProvider credsProvider)
/*     */   {
/* 454 */     this.credsProvider = credsProvider;
/*     */   }
/*     */   
/*     */   public final synchronized HttpRoutePlanner getRoutePlanner()
/*     */   {
/* 459 */     if (this.routePlanner == null) {
/* 460 */       this.routePlanner = createHttpRoutePlanner();
/*     */     }
/* 462 */     return this.routePlanner;
/*     */   }
/*     */   
/*     */   public synchronized void setRoutePlanner(HttpRoutePlanner routePlanner)
/*     */   {
/* 467 */     this.routePlanner = routePlanner;
/*     */   }
/*     */   
/*     */   public final synchronized UserTokenHandler getUserTokenHandler()
/*     */   {
/* 472 */     if (this.userTokenHandler == null) {
/* 473 */       this.userTokenHandler = createUserTokenHandler();
/*     */     }
/* 475 */     return this.userTokenHandler;
/*     */   }
/*     */   
/*     */   public synchronized void setUserTokenHandler(UserTokenHandler userTokenHandler)
/*     */   {
/* 480 */     this.userTokenHandler = userTokenHandler;
/*     */   }
/*     */   
/*     */   protected final synchronized BasicHttpProcessor getHttpProcessor()
/*     */   {
/* 485 */     if (this.httpProcessor == null) {
/* 486 */       this.httpProcessor = createHttpProcessor();
/*     */     }
/* 488 */     return this.httpProcessor;
/*     */   }
/*     */   
/*     */   public synchronized void addResponseInterceptor(HttpResponseInterceptor itcp)
/*     */   {
/* 493 */     getHttpProcessor().addInterceptor(itcp);
/*     */   }
/*     */   
/*     */   public synchronized void addResponseInterceptor(HttpResponseInterceptor itcp, int index)
/*     */   {
/* 498 */     getHttpProcessor().addInterceptor(itcp, index);
/*     */   }
/*     */   
/*     */   public synchronized HttpResponseInterceptor getResponseInterceptor(int index)
/*     */   {
/* 503 */     return getHttpProcessor().getResponseInterceptor(index);
/*     */   }
/*     */   
/*     */   public synchronized int getResponseInterceptorCount()
/*     */   {
/* 508 */     return getHttpProcessor().getResponseInterceptorCount();
/*     */   }
/*     */   
/*     */   public synchronized void clearResponseInterceptors()
/*     */   {
/* 513 */     getHttpProcessor().clearResponseInterceptors();
/*     */   }
/*     */   
/*     */   public synchronized void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> clazz)
/*     */   {
/* 518 */     getHttpProcessor().removeResponseInterceptorByClass(clazz);
/*     */   }
/*     */   
/*     */   public synchronized void addRequestInterceptor(HttpRequestInterceptor itcp)
/*     */   {
/* 523 */     getHttpProcessor().addInterceptor(itcp);
/*     */   }
/*     */   
/*     */   public synchronized void addRequestInterceptor(HttpRequestInterceptor itcp, int index)
/*     */   {
/* 528 */     getHttpProcessor().addInterceptor(itcp, index);
/*     */   }
/*     */   
/*     */   public synchronized HttpRequestInterceptor getRequestInterceptor(int index)
/*     */   {
/* 533 */     return getHttpProcessor().getRequestInterceptor(index);
/*     */   }
/*     */   
/*     */   public synchronized int getRequestInterceptorCount()
/*     */   {
/* 538 */     return getHttpProcessor().getRequestInterceptorCount();
/*     */   }
/*     */   
/*     */   public synchronized void clearRequestInterceptors()
/*     */   {
/* 543 */     getHttpProcessor().clearRequestInterceptors();
/*     */   }
/*     */   
/*     */   public synchronized void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> clazz)
/*     */   {
/* 548 */     getHttpProcessor().removeRequestInterceptorByClass(clazz);
/*     */   }
/*     */   
/*     */   public final HttpResponse execute(HttpUriRequest request)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 554 */     return execute(request, (HttpContext)null);
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
/*     */   public final HttpResponse execute(HttpUriRequest request, HttpContext context)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 571 */     if (request == null) {
/* 572 */       throw new IllegalArgumentException("Request must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 576 */     return execute(determineTarget(request), request, context);
/*     */   }
/*     */   
/*     */ 
/*     */   private HttpHost determineTarget(HttpUriRequest request)
/*     */   {
/* 582 */     HttpHost target = null;
/*     */     
/* 584 */     URI requestURI = request.getURI();
/* 585 */     if (requestURI.isAbsolute()) {
/* 586 */       target = new HttpHost(requestURI.getHost(), requestURI.getPort(), requestURI.getScheme());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 591 */     return target;
/*     */   }
/*     */   
/*     */   public final HttpResponse execute(HttpHost target, HttpRequest request)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 597 */     return execute(target, request, (HttpContext)null);
/*     */   }
/*     */   
/*     */ 
/*     */   public final HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 604 */     if (request == null) {
/* 605 */       throw new IllegalArgumentException("Request must not be null.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 611 */     HttpContext execContext = null;
/* 612 */     RequestDirector director = null;
/*     */     
/*     */ 
/*     */ 
/* 616 */     synchronized (this)
/*     */     {
/* 618 */       HttpContext defaultContext = createHttpContext();
/* 619 */       if (context == null) {
/* 620 */         execContext = defaultContext;
/*     */       } else {
/* 622 */         execContext = new DefaultedHttpContext(context, defaultContext);
/*     */       }
/*     */       
/* 625 */       director = createClientRequestDirector(getRequestExecutor(), getConnectionManager(), getConnectionReuseStrategy(), getConnectionKeepAliveStrategy(), getRoutePlanner(), getHttpProcessor().copy(), getHttpRequestRetryHandler(), getRedirectHandler(), getTargetAuthenticationHandler(), getProxyAuthenticationHandler(), getUserTokenHandler(), determineParams(request));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 641 */       return director.execute(target, request, execContext);
/*     */     } catch (HttpException httpException) {
/* 643 */       throw new ClientProtocolException(httpException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   protected RequestDirector createClientRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler stateHandler, HttpParams params)
/*     */   {
/* 660 */     return new DefaultRequestDirector(this.log, requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectHandler, targetAuthHandler, proxyAuthHandler, stateHandler, params);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HttpParams determineParams(HttpRequest req)
/*     */   {
/* 692 */     return new ClientParamsStack(null, getParams(), req.getParams(), null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 700 */     return (T)execute(request, responseHandler, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 708 */     HttpHost target = determineTarget(request);
/* 709 */     return (T)execute(target, request, responseHandler, context);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 717 */     return (T)execute(target, request, responseHandler, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
/*     */     throws IOException, ClientProtocolException
/*     */   {
/* 726 */     if (responseHandler == null) {
/* 727 */       throw new IllegalArgumentException("Response handler must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 731 */     HttpResponse response = execute(target, request, context);
/*     */     T result;
/*     */     try
/*     */     {
/* 735 */       result = responseHandler.handleResponse(response);
/*     */     } catch (Throwable t) {
/* 737 */       HttpEntity entity = response.getEntity();
/* 738 */       if (entity != null) {
/*     */         try {
/* 740 */           entity.consumeContent();
/*     */         }
/*     */         catch (Throwable t2)
/*     */         {
/* 744 */           this.log.warn("Error consuming content after an exception.", t2);
/*     */         }
/*     */       }
/*     */       
/* 748 */       if ((t instanceof Error)) {
/* 749 */         throw ((Error)t);
/*     */       }
/*     */       
/* 752 */       if ((t instanceof RuntimeException)) {
/* 753 */         throw ((RuntimeException)t);
/*     */       }
/*     */       
/* 756 */       if ((t instanceof IOException)) {
/* 757 */         throw ((IOException)t);
/*     */       }
/*     */       
/* 760 */       throw new UndeclaredThrowableException(t);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 765 */     HttpEntity entity = response.getEntity();
/* 766 */     if (entity != null)
/*     */     {
/* 768 */       entity.consumeContent();
/*     */     }
/*     */     
/* 771 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/AbstractHttpClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */