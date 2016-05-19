/*      */ package org.shaded.apache.http.impl.client;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import org.shaded.apache.commons.logging.Log;
/*      */ import org.shaded.apache.commons.logging.LogFactory;
/*      */ import org.shaded.apache.http.ConnectionReuseStrategy;
/*      */ import org.shaded.apache.http.Header;
/*      */ import org.shaded.apache.http.HttpEntity;
/*      */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*      */ import org.shaded.apache.http.HttpException;
/*      */ import org.shaded.apache.http.HttpHost;
/*      */ import org.shaded.apache.http.HttpRequest;
/*      */ import org.shaded.apache.http.HttpResponse;
/*      */ import org.shaded.apache.http.ProtocolException;
/*      */ import org.shaded.apache.http.ProtocolVersion;
/*      */ import org.shaded.apache.http.RequestLine;
/*      */ import org.shaded.apache.http.StatusLine;
/*      */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*      */ import org.shaded.apache.http.auth.AuthScheme;
/*      */ import org.shaded.apache.http.auth.AuthScope;
/*      */ import org.shaded.apache.http.auth.AuthState;
/*      */ import org.shaded.apache.http.auth.AuthenticationException;
/*      */ import org.shaded.apache.http.auth.Credentials;
/*      */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*      */ import org.shaded.apache.http.client.AuthenticationHandler;
/*      */ import org.shaded.apache.http.client.CredentialsProvider;
/*      */ import org.shaded.apache.http.client.HttpRequestRetryHandler;
/*      */ import org.shaded.apache.http.client.NonRepeatableRequestException;
/*      */ import org.shaded.apache.http.client.RedirectException;
/*      */ import org.shaded.apache.http.client.RedirectHandler;
/*      */ import org.shaded.apache.http.client.RequestDirector;
/*      */ import org.shaded.apache.http.client.UserTokenHandler;
/*      */ import org.shaded.apache.http.client.methods.AbortableHttpRequest;
/*      */ import org.shaded.apache.http.client.params.HttpClientParams;
/*      */ import org.shaded.apache.http.client.utils.URIUtils;
/*      */ import org.shaded.apache.http.conn.BasicManagedEntity;
/*      */ import org.shaded.apache.http.conn.ClientConnectionManager;
/*      */ import org.shaded.apache.http.conn.ClientConnectionRequest;
/*      */ import org.shaded.apache.http.conn.ConnectionKeepAliveStrategy;
/*      */ import org.shaded.apache.http.conn.ManagedClientConnection;
/*      */ import org.shaded.apache.http.conn.params.ConnManagerParams;
/*      */ import org.shaded.apache.http.conn.routing.BasicRouteDirector;
/*      */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*      */ import org.shaded.apache.http.conn.routing.HttpRouteDirector;
/*      */ import org.shaded.apache.http.conn.routing.HttpRoutePlanner;
/*      */ import org.shaded.apache.http.conn.scheme.Scheme;
/*      */ import org.shaded.apache.http.conn.scheme.SchemeRegistry;
/*      */ import org.shaded.apache.http.entity.BufferedHttpEntity;
/*      */ import org.shaded.apache.http.message.BasicHttpRequest;
/*      */ import org.shaded.apache.http.params.HttpConnectionParams;
/*      */ import org.shaded.apache.http.params.HttpParams;
/*      */ import org.shaded.apache.http.params.HttpProtocolParams;
/*      */ import org.shaded.apache.http.protocol.HttpContext;
/*      */ import org.shaded.apache.http.protocol.HttpProcessor;
/*      */ import org.shaded.apache.http.protocol.HttpRequestExecutor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @NotThreadSafe
/*      */ public class DefaultRequestDirector
/*      */   implements RequestDirector
/*      */ {
/*      */   private final Log log;
/*      */   protected final ClientConnectionManager connManager;
/*      */   protected final HttpRoutePlanner routePlanner;
/*      */   protected final ConnectionReuseStrategy reuseStrategy;
/*      */   protected final ConnectionKeepAliveStrategy keepAliveStrategy;
/*      */   protected final HttpRequestExecutor requestExec;
/*      */   protected final HttpProcessor httpProcessor;
/*      */   protected final HttpRequestRetryHandler retryHandler;
/*      */   protected final RedirectHandler redirectHandler;
/*      */   protected final AuthenticationHandler targetAuthHandler;
/*      */   protected final AuthenticationHandler proxyAuthHandler;
/*      */   protected final UserTokenHandler userTokenHandler;
/*      */   protected final HttpParams params;
/*      */   protected ManagedClientConnection managedConn;
/*      */   protected final AuthState targetAuthState;
/*      */   protected final AuthState proxyAuthState;
/*      */   private int redirectCount;
/*      */   private int maxRedirects;
/*      */   private HttpHost virtualHost;
/*      */   
/*      */   DefaultRequestDirector(Log log, HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler userTokenHandler, HttpParams params)
/*      */   {
/*  202 */     if (log == null) {
/*  203 */       throw new IllegalArgumentException("Log may not be null.");
/*      */     }
/*      */     
/*  206 */     if (requestExec == null) {
/*  207 */       throw new IllegalArgumentException("Request executor may not be null.");
/*      */     }
/*      */     
/*  210 */     if (conman == null) {
/*  211 */       throw new IllegalArgumentException("Client connection manager may not be null.");
/*      */     }
/*      */     
/*  214 */     if (reustrat == null) {
/*  215 */       throw new IllegalArgumentException("Connection reuse strategy may not be null.");
/*      */     }
/*      */     
/*  218 */     if (kastrat == null) {
/*  219 */       throw new IllegalArgumentException("Connection keep alive strategy may not be null.");
/*      */     }
/*      */     
/*  222 */     if (rouplan == null) {
/*  223 */       throw new IllegalArgumentException("Route planner may not be null.");
/*      */     }
/*      */     
/*  226 */     if (httpProcessor == null) {
/*  227 */       throw new IllegalArgumentException("HTTP protocol processor may not be null.");
/*      */     }
/*      */     
/*  230 */     if (retryHandler == null) {
/*  231 */       throw new IllegalArgumentException("HTTP request retry handler may not be null.");
/*      */     }
/*      */     
/*  234 */     if (redirectHandler == null) {
/*  235 */       throw new IllegalArgumentException("Redirect handler may not be null.");
/*      */     }
/*      */     
/*  238 */     if (targetAuthHandler == null) {
/*  239 */       throw new IllegalArgumentException("Target authentication handler may not be null.");
/*      */     }
/*      */     
/*  242 */     if (proxyAuthHandler == null) {
/*  243 */       throw new IllegalArgumentException("Proxy authentication handler may not be null.");
/*      */     }
/*      */     
/*  246 */     if (userTokenHandler == null) {
/*  247 */       throw new IllegalArgumentException("User token handler may not be null.");
/*      */     }
/*      */     
/*  250 */     if (params == null) {
/*  251 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*      */     }
/*      */     
/*  254 */     this.log = log;
/*  255 */     this.requestExec = requestExec;
/*  256 */     this.connManager = conman;
/*  257 */     this.reuseStrategy = reustrat;
/*  258 */     this.keepAliveStrategy = kastrat;
/*  259 */     this.routePlanner = rouplan;
/*  260 */     this.httpProcessor = httpProcessor;
/*  261 */     this.retryHandler = retryHandler;
/*  262 */     this.redirectHandler = redirectHandler;
/*  263 */     this.targetAuthHandler = targetAuthHandler;
/*  264 */     this.proxyAuthHandler = proxyAuthHandler;
/*  265 */     this.userTokenHandler = userTokenHandler;
/*  266 */     this.params = params;
/*      */     
/*  268 */     this.managedConn = null;
/*      */     
/*  270 */     this.redirectCount = 0;
/*  271 */     this.maxRedirects = this.params.getIntParameter("http.protocol.max-redirects", 100);
/*  272 */     this.targetAuthState = new AuthState();
/*  273 */     this.proxyAuthState = new AuthState();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public DefaultRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler userTokenHandler, HttpParams params)
/*      */   {
/*  289 */     this(LogFactory.getLog(DefaultRequestDirector.class), requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectHandler, targetAuthHandler, proxyAuthHandler, userTokenHandler, params);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private RequestWrapper wrapRequest(HttpRequest request)
/*      */     throws ProtocolException
/*      */   {
/*  307 */     if ((request instanceof HttpEntityEnclosingRequest)) {
/*  308 */       return new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)request);
/*      */     }
/*      */     
/*  311 */     return new RequestWrapper(request);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void rewriteRequestURI(RequestWrapper request, HttpRoute route)
/*      */     throws ProtocolException
/*      */   {
/*      */     try
/*      */     {
/*  322 */       URI uri = request.getURI();
/*  323 */       if ((route.getProxyHost() != null) && (!route.isTunnelled()))
/*      */       {
/*  325 */         if (!uri.isAbsolute()) {
/*  326 */           HttpHost target = route.getTargetHost();
/*  327 */           uri = URIUtils.rewriteURI(uri, target);
/*  328 */           request.setURI(uri);
/*      */         }
/*      */         
/*      */       }
/*  332 */       else if (uri.isAbsolute()) {
/*  333 */         uri = URIUtils.rewriteURI(uri, null);
/*  334 */         request.setURI(uri);
/*      */       }
/*      */     }
/*      */     catch (URISyntaxException ex)
/*      */     {
/*  339 */       throw new ProtocolException("Invalid URI: " + request.getRequestLine().getUri(), ex);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
/*      */     throws HttpException, IOException
/*      */   {
/*  350 */     HttpRequest orig = request;
/*  351 */     RequestWrapper origWrapper = wrapRequest(orig);
/*  352 */     origWrapper.setParams(this.params);
/*  353 */     HttpRoute origRoute = determineRoute(target, origWrapper, context);
/*      */     
/*  355 */     this.virtualHost = ((HttpHost)orig.getParams().getParameter("http.virtual-host"));
/*      */     
/*      */ 
/*  358 */     RoutedRequest roureq = new RoutedRequest(origWrapper, origRoute);
/*      */     
/*  360 */     long timeout = ConnManagerParams.getTimeout(this.params);
/*      */     
/*  362 */     int execCount = 0;
/*      */     
/*  364 */     boolean reuse = false;
/*  365 */     boolean done = false;
/*      */     try {
/*  367 */       HttpResponse response = null;
/*  368 */       while (!done)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  374 */         RequestWrapper wrapper = roureq.getRequest();
/*  375 */         HttpRoute route = roureq.getRoute();
/*  376 */         response = null;
/*      */         
/*      */ 
/*  379 */         Object userToken = context.getAttribute("http.user-token");
/*      */         
/*      */ 
/*  382 */         if (this.managedConn == null) {
/*  383 */           ClientConnectionRequest connRequest = this.connManager.requestConnection(route, userToken);
/*      */           
/*  385 */           if ((orig instanceof AbortableHttpRequest)) {
/*  386 */             ((AbortableHttpRequest)orig).setConnectionRequest(connRequest);
/*      */           }
/*      */           try
/*      */           {
/*  390 */             this.managedConn = connRequest.getConnection(timeout, TimeUnit.MILLISECONDS);
/*      */           } catch (InterruptedException interrupted) {
/*  392 */             InterruptedIOException iox = new InterruptedIOException();
/*  393 */             iox.initCause(interrupted);
/*  394 */             throw iox;
/*      */           }
/*      */           
/*  397 */           if (HttpConnectionParams.isStaleCheckingEnabled(this.params))
/*      */           {
/*  399 */             if (this.managedConn.isOpen()) {
/*  400 */               this.log.debug("Stale connection check");
/*  401 */               if (this.managedConn.isStale()) {
/*  402 */                 this.log.debug("Stale connection detected");
/*  403 */                 this.managedConn.close();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  409 */         if ((orig instanceof AbortableHttpRequest)) {
/*  410 */           ((AbortableHttpRequest)orig).setReleaseTrigger(this.managedConn);
/*      */         }
/*      */         
/*      */ 
/*  414 */         if (!this.managedConn.isOpen()) {
/*  415 */           this.managedConn.open(route, context, this.params);
/*      */         } else {
/*  417 */           this.managedConn.setSocketTimeout(HttpConnectionParams.getSoTimeout(this.params));
/*      */         }
/*      */         try
/*      */         {
/*  421 */           establishRoute(route, context);
/*      */         } catch (TunnelRefusedException ex) {
/*  423 */           if (this.log.isDebugEnabled()) {
/*  424 */             this.log.debug(ex.getMessage());
/*      */           }
/*  426 */           response = ex.getResponse();
/*  427 */           break;
/*      */         }
/*      */         
/*      */ 
/*  431 */         wrapper.resetHeaders();
/*      */         
/*      */ 
/*  434 */         rewriteRequestURI(wrapper, route);
/*      */         
/*      */ 
/*  437 */         target = this.virtualHost;
/*      */         
/*  439 */         if (target == null) {
/*  440 */           target = route.getTargetHost();
/*      */         }
/*      */         
/*  443 */         HttpHost proxy = route.getProxyHost();
/*      */         
/*      */ 
/*  446 */         context.setAttribute("http.target_host", target);
/*      */         
/*  448 */         context.setAttribute("http.proxy_host", proxy);
/*      */         
/*  450 */         context.setAttribute("http.connection", this.managedConn);
/*      */         
/*  452 */         context.setAttribute("http.auth.target-scope", this.targetAuthState);
/*      */         
/*  454 */         context.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
/*      */         
/*      */ 
/*      */ 
/*  458 */         this.requestExec.preProcess(wrapper, this.httpProcessor, context);
/*      */         
/*  460 */         boolean retrying = true;
/*  461 */         Exception retryReason = null;
/*  462 */         while (retrying)
/*      */         {
/*  464 */           execCount++;
/*      */           
/*  466 */           wrapper.incrementExecCount();
/*  467 */           if ((wrapper.getExecCount() > 1) && (!wrapper.isRepeatable())) {
/*  468 */             this.log.debug("Cannot retry non-repeatable request");
/*  469 */             if (retryReason != null) {
/*  470 */               throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", retryReason);
/*      */             }
/*      */             
/*      */ 
/*  474 */             throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
/*      */           }
/*      */           
/*      */ 
/*      */           try
/*      */           {
/*  480 */             if (this.log.isDebugEnabled()) {
/*  481 */               this.log.debug("Attempt " + execCount + " to execute request");
/*      */             }
/*  483 */             response = this.requestExec.execute(wrapper, this.managedConn, context);
/*  484 */             retrying = false;
/*      */           }
/*      */           catch (IOException ex) {
/*  487 */             this.log.debug("Closing the connection.");
/*  488 */             this.managedConn.close();
/*  489 */             if (this.retryHandler.retryRequest(ex, execCount, context)) {
/*  490 */               if (this.log.isInfoEnabled()) {
/*  491 */                 this.log.info("I/O exception (" + ex.getClass().getName() + ") caught when processing request: " + ex.getMessage());
/*      */               }
/*      */               
/*      */ 
/*  495 */               if (this.log.isDebugEnabled()) {
/*  496 */                 this.log.debug(ex.getMessage(), ex);
/*      */               }
/*  498 */               this.log.info("Retrying request");
/*  499 */               retryReason = ex;
/*      */             } else {
/*  501 */               throw ex;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  506 */           if (!route.isTunnelled()) {
/*  507 */             this.log.debug("Reopening the direct connection.");
/*  508 */             this.managedConn.open(route, context, this.params);
/*      */           }
/*      */           else {
/*  511 */             this.log.debug("Proxied connection. Need to start over.");
/*  512 */             retrying = false;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  519 */         if (response != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  525 */           response.setParams(this.params);
/*  526 */           this.requestExec.postProcess(response, this.httpProcessor, context);
/*      */           
/*      */ 
/*      */ 
/*  530 */           reuse = this.reuseStrategy.keepAlive(response, context);
/*  531 */           if (reuse)
/*      */           {
/*  533 */             long duration = this.keepAliveStrategy.getKeepAliveDuration(response, context);
/*  534 */             this.managedConn.setIdleDuration(duration, TimeUnit.MILLISECONDS);
/*      */             
/*  536 */             if (this.log.isDebugEnabled()) {
/*  537 */               if (duration >= 0L) {
/*  538 */                 this.log.debug("Connection can be kept alive for " + duration + " ms");
/*      */               } else {
/*  540 */                 this.log.debug("Connection can be kept alive indefinitely");
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*  545 */           RoutedRequest followup = handleResponse(roureq, response, context);
/*  546 */           if (followup == null) {
/*  547 */             done = true;
/*      */           } else {
/*  549 */             if (reuse)
/*      */             {
/*  551 */               HttpEntity entity = response.getEntity();
/*  552 */               if (entity != null) {
/*  553 */                 entity.consumeContent();
/*      */               }
/*      */               
/*      */ 
/*  557 */               this.managedConn.markReusable();
/*      */             } else {
/*  559 */               this.managedConn.close();
/*      */             }
/*      */             
/*  562 */             if (!followup.getRoute().equals(roureq.getRoute())) {
/*  563 */               releaseConnection();
/*      */             }
/*  565 */             roureq = followup;
/*      */           }
/*      */           
/*  568 */           if ((this.managedConn != null) && (userToken == null)) {
/*  569 */             userToken = this.userTokenHandler.getUserToken(context);
/*  570 */             context.setAttribute("http.user-token", userToken);
/*  571 */             if (userToken != null) {
/*  572 */               this.managedConn.setState(userToken);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  580 */       if ((response == null) || (response.getEntity() == null) || (!response.getEntity().isStreaming()))
/*      */       {
/*      */ 
/*  583 */         if (reuse)
/*  584 */           this.managedConn.markReusable();
/*  585 */         releaseConnection();
/*      */       }
/*      */       else {
/*  588 */         HttpEntity entity = response.getEntity();
/*  589 */         entity = new BasicManagedEntity(entity, this.managedConn, reuse);
/*  590 */         response.setEntity(entity);
/*      */       }
/*      */       
/*  593 */       return response;
/*      */     }
/*      */     catch (HttpException ex) {
/*  596 */       abortConnection();
/*  597 */       throw ex;
/*      */     } catch (IOException ex) {
/*  599 */       abortConnection();
/*  600 */       throw ex;
/*      */     } catch (RuntimeException ex) {
/*  602 */       abortConnection();
/*  603 */       throw ex;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void releaseConnection()
/*      */   {
/*      */     try
/*      */     {
/*  617 */       this.managedConn.releaseConnection();
/*      */     } catch (IOException ignored) {
/*  619 */       this.log.debug("IOException releasing connection", ignored);
/*      */     }
/*  621 */     this.managedConn = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
/*      */     throws HttpException
/*      */   {
/*  646 */     if (target == null) {
/*  647 */       target = (HttpHost)request.getParams().getParameter("http.default-host");
/*      */     }
/*      */     
/*  650 */     if (target == null) {
/*  651 */       throw new IllegalStateException("Target host must not be null, or set in parameters.");
/*      */     }
/*      */     
/*      */ 
/*  655 */     return this.routePlanner.determineRoute(target, request, context);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void establishRoute(HttpRoute route, HttpContext context)
/*      */     throws HttpException, IOException
/*      */   {
/*  671 */     HttpRouteDirector rowdy = new BasicRouteDirector();
/*      */     int step;
/*      */     do {
/*  674 */       HttpRoute fact = this.managedConn.getRoute();
/*  675 */       step = rowdy.nextStep(route, fact);
/*      */       
/*  677 */       switch (step)
/*      */       {
/*      */       case 1: 
/*      */       case 2: 
/*  681 */         this.managedConn.open(route, context, this.params);
/*  682 */         break;
/*      */       
/*      */       case 3: 
/*  685 */         boolean secure = createTunnelToTarget(route, context);
/*  686 */         this.log.debug("Tunnel to target created.");
/*  687 */         this.managedConn.tunnelTarget(secure, this.params);
/*  688 */         break;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       case 4: 
/*  695 */         int hop = fact.getHopCount() - 1;
/*  696 */         boolean secure = createTunnelToProxy(route, hop, context);
/*  697 */         this.log.debug("Tunnel to proxy created.");
/*  698 */         this.managedConn.tunnelProxy(route.getHopTarget(hop), secure, this.params);
/*      */         
/*  700 */         break;
/*      */       
/*      */ 
/*      */       case 5: 
/*  704 */         this.managedConn.layerProtocol(context, this.params);
/*  705 */         break;
/*      */       
/*      */       case -1: 
/*  708 */         throw new IllegalStateException("Unable to establish route.\nplanned = " + route + "\ncurrent = " + fact);
/*      */       
/*      */ 
/*      */ 
/*      */       case 0: 
/*      */         break;
/*      */       
/*      */ 
/*      */ 
/*      */       default: 
/*  718 */         throw new IllegalStateException("Unknown step indicator " + step + " from RouteDirector.");
/*      */       
/*      */       }
/*      */       
/*  722 */     } while (step > 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean createTunnelToTarget(HttpRoute route, HttpContext context)
/*      */     throws HttpException, IOException
/*      */   {
/*  750 */     HttpHost proxy = route.getProxyHost();
/*  751 */     HttpHost target = route.getTargetHost();
/*  752 */     HttpResponse response = null;
/*      */     
/*  754 */     boolean done = false;
/*  755 */     while (!done)
/*      */     {
/*  757 */       done = true;
/*      */       
/*  759 */       if (!this.managedConn.isOpen()) {
/*  760 */         this.managedConn.open(route, context, this.params);
/*      */       }
/*      */       
/*  763 */       HttpRequest connect = createConnectRequest(route, context);
/*  764 */       connect.setParams(this.params);
/*      */       
/*      */ 
/*  767 */       context.setAttribute("http.target_host", target);
/*      */       
/*  769 */       context.setAttribute("http.proxy_host", proxy);
/*      */       
/*  771 */       context.setAttribute("http.connection", this.managedConn);
/*      */       
/*  773 */       context.setAttribute("http.auth.target-scope", this.targetAuthState);
/*      */       
/*  775 */       context.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
/*      */       
/*  777 */       context.setAttribute("http.request", connect);
/*      */       
/*      */ 
/*  780 */       this.requestExec.preProcess(connect, this.httpProcessor, context);
/*      */       
/*  782 */       response = this.requestExec.execute(connect, this.managedConn, context);
/*      */       
/*  784 */       response.setParams(this.params);
/*  785 */       this.requestExec.postProcess(response, this.httpProcessor, context);
/*      */       
/*  787 */       int status = response.getStatusLine().getStatusCode();
/*  788 */       if (status < 200) {
/*  789 */         throw new HttpException("Unexpected response to CONNECT request: " + response.getStatusLine());
/*      */       }
/*      */       
/*      */ 
/*  793 */       CredentialsProvider credsProvider = (CredentialsProvider)context.getAttribute("http.auth.credentials-provider");
/*      */       
/*      */ 
/*  796 */       if ((credsProvider != null) && (HttpClientParams.isAuthenticating(this.params))) {
/*  797 */         if (this.proxyAuthHandler.isAuthenticationRequested(response, context))
/*      */         {
/*  799 */           this.log.debug("Proxy requested authentication");
/*  800 */           Map<String, Header> challenges = this.proxyAuthHandler.getChallenges(response, context);
/*      */           try
/*      */           {
/*  803 */             processChallenges(challenges, this.proxyAuthState, this.proxyAuthHandler, response, context);
/*      */           }
/*      */           catch (AuthenticationException ex)
/*      */           {
/*  807 */             if (this.log.isWarnEnabled()) {
/*  808 */               this.log.warn("Authentication error: " + ex.getMessage());
/*  809 */               break;
/*      */             }
/*      */           }
/*  812 */           updateAuthState(this.proxyAuthState, proxy, credsProvider);
/*      */           
/*  814 */           if (this.proxyAuthState.getCredentials() != null) {
/*  815 */             done = false;
/*      */             
/*      */ 
/*  818 */             if (this.reuseStrategy.keepAlive(response, context)) {
/*  819 */               this.log.debug("Connection kept alive");
/*      */               
/*  821 */               HttpEntity entity = response.getEntity();
/*  822 */               if (entity != null) {
/*  823 */                 entity.consumeContent();
/*      */               }
/*      */             } else {
/*  826 */               this.managedConn.close();
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  833 */           this.proxyAuthState.setAuthScope(null);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  838 */     int status = response.getStatusLine().getStatusCode();
/*      */     
/*  840 */     if (status > 299)
/*      */     {
/*      */ 
/*  843 */       HttpEntity entity = response.getEntity();
/*  844 */       if (entity != null) {
/*  845 */         response.setEntity(new BufferedHttpEntity(entity));
/*      */       }
/*      */       
/*  848 */       this.managedConn.close();
/*  849 */       throw new TunnelRefusedException("CONNECT refused by proxy: " + response.getStatusLine(), response);
/*      */     }
/*      */     
/*      */ 
/*  853 */     this.managedConn.markReusable();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  859 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean createTunnelToProxy(HttpRoute route, int hop, HttpContext context)
/*      */     throws HttpException, IOException
/*      */   {
/*  895 */     throw new UnsupportedOperationException("Proxy chains are not supported.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected HttpRequest createConnectRequest(HttpRoute route, HttpContext context)
/*      */   {
/*  916 */     HttpHost target = route.getTargetHost();
/*      */     
/*  918 */     String host = target.getHostName();
/*  919 */     int port = target.getPort();
/*  920 */     if (port < 0) {
/*  921 */       Scheme scheme = this.connManager.getSchemeRegistry().getScheme(target.getSchemeName());
/*      */       
/*  923 */       port = scheme.getDefaultPort();
/*      */     }
/*      */     
/*  926 */     StringBuilder buffer = new StringBuilder(host.length() + 6);
/*  927 */     buffer.append(host);
/*  928 */     buffer.append(':');
/*  929 */     buffer.append(Integer.toString(port));
/*      */     
/*  931 */     String authority = buffer.toString();
/*  932 */     ProtocolVersion ver = HttpProtocolParams.getVersion(this.params);
/*  933 */     HttpRequest req = new BasicHttpRequest("CONNECT", authority, ver);
/*      */     
/*      */ 
/*  936 */     return req;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected RoutedRequest handleResponse(RoutedRequest roureq, HttpResponse response, HttpContext context)
/*      */     throws HttpException, IOException
/*      */   {
/*  958 */     HttpRoute route = roureq.getRoute();
/*  959 */     RequestWrapper request = roureq.getRequest();
/*      */     
/*  961 */     HttpParams params = request.getParams();
/*  962 */     if ((HttpClientParams.isRedirecting(params)) && (this.redirectHandler.isRedirectRequested(response, context)))
/*      */     {
/*      */ 
/*  965 */       if (this.redirectCount >= this.maxRedirects) {
/*  966 */         throw new RedirectException("Maximum redirects (" + this.maxRedirects + ") exceeded");
/*      */       }
/*      */       
/*  969 */       this.redirectCount += 1;
/*      */       
/*      */ 
/*  972 */       this.virtualHost = null;
/*      */       
/*  974 */       URI uri = this.redirectHandler.getLocationURI(response, context);
/*      */       
/*  976 */       HttpHost newTarget = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  982 */       this.targetAuthState.setAuthScope(null);
/*  983 */       this.proxyAuthState.setAuthScope(null);
/*      */       
/*      */ 
/*  986 */       if (!route.getTargetHost().equals(newTarget)) {
/*  987 */         this.targetAuthState.invalidate();
/*  988 */         AuthScheme authScheme = this.proxyAuthState.getAuthScheme();
/*  989 */         if ((authScheme != null) && (authScheme.isConnectionBased())) {
/*  990 */           this.proxyAuthState.invalidate();
/*      */         }
/*      */       }
/*      */       
/*  994 */       HttpRedirect redirect = new HttpRedirect(request.getMethod(), uri);
/*  995 */       HttpRequest orig = request.getOriginal();
/*  996 */       redirect.setHeaders(orig.getAllHeaders());
/*      */       
/*  998 */       RequestWrapper wrapper = new RequestWrapper(redirect);
/*  999 */       wrapper.setParams(params);
/*      */       
/* 1001 */       HttpRoute newRoute = determineRoute(newTarget, wrapper, context);
/* 1002 */       RoutedRequest newRequest = new RoutedRequest(wrapper, newRoute);
/*      */       
/* 1004 */       if (this.log.isDebugEnabled()) {
/* 1005 */         this.log.debug("Redirecting to '" + uri + "' via " + newRoute);
/*      */       }
/*      */       
/* 1008 */       return newRequest;
/*      */     }
/*      */     
/* 1011 */     CredentialsProvider credsProvider = (CredentialsProvider)context.getAttribute("http.auth.credentials-provider");
/*      */     
/*      */ 
/* 1014 */     if ((credsProvider != null) && (HttpClientParams.isAuthenticating(params)))
/*      */     {
/* 1016 */       if (this.targetAuthHandler.isAuthenticationRequested(response, context))
/*      */       {
/* 1018 */         HttpHost target = (HttpHost)context.getAttribute("http.target_host");
/*      */         
/* 1020 */         if (target == null) {
/* 1021 */           target = route.getTargetHost();
/*      */         }
/*      */         
/* 1024 */         this.log.debug("Target requested authentication");
/* 1025 */         Map<String, Header> challenges = this.targetAuthHandler.getChallenges(response, context);
/*      */         try
/*      */         {
/* 1028 */           processChallenges(challenges, this.targetAuthState, this.targetAuthHandler, response, context);
/*      */         }
/*      */         catch (AuthenticationException ex)
/*      */         {
/* 1032 */           if (this.log.isWarnEnabled()) {
/* 1033 */             this.log.warn("Authentication error: " + ex.getMessage());
/* 1034 */             return null;
/*      */           }
/*      */         }
/* 1037 */         updateAuthState(this.targetAuthState, target, credsProvider);
/*      */         
/* 1039 */         if (this.targetAuthState.getCredentials() != null)
/*      */         {
/* 1041 */           return roureq;
/*      */         }
/* 1043 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1047 */       this.targetAuthState.setAuthScope(null);
/*      */       
/*      */ 
/* 1050 */       if (this.proxyAuthHandler.isAuthenticationRequested(response, context))
/*      */       {
/* 1052 */         HttpHost proxy = route.getProxyHost();
/*      */         
/* 1054 */         this.log.debug("Proxy requested authentication");
/* 1055 */         Map<String, Header> challenges = this.proxyAuthHandler.getChallenges(response, context);
/*      */         try
/*      */         {
/* 1058 */           processChallenges(challenges, this.proxyAuthState, this.proxyAuthHandler, response, context);
/*      */         }
/*      */         catch (AuthenticationException ex)
/*      */         {
/* 1062 */           if (this.log.isWarnEnabled()) {
/* 1063 */             this.log.warn("Authentication error: " + ex.getMessage());
/* 1064 */             return null;
/*      */           }
/*      */         }
/* 1067 */         updateAuthState(this.proxyAuthState, proxy, credsProvider);
/*      */         
/* 1069 */         if (this.proxyAuthState.getCredentials() != null)
/*      */         {
/* 1071 */           return roureq;
/*      */         }
/* 1073 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1077 */       this.proxyAuthState.setAuthScope(null);
/*      */     }
/*      */     
/* 1080 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void abortConnection()
/*      */   {
/* 1090 */     ManagedClientConnection mcc = this.managedConn;
/* 1091 */     if (mcc != null)
/*      */     {
/*      */ 
/* 1094 */       this.managedConn = null;
/*      */       try {
/* 1096 */         mcc.abortConnection();
/*      */       } catch (IOException ex) {
/* 1098 */         if (this.log.isDebugEnabled()) {
/* 1099 */           this.log.debug(ex.getMessage(), ex);
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1104 */         mcc.releaseConnection();
/*      */       } catch (IOException ignored) {
/* 1106 */         this.log.debug("Error releasing connection", ignored);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void processChallenges(Map<String, Header> challenges, AuthState authState, AuthenticationHandler authHandler, HttpResponse response, HttpContext context)
/*      */     throws MalformedChallengeException, AuthenticationException
/*      */   {
/* 1120 */     AuthScheme authScheme = authState.getAuthScheme();
/* 1121 */     if (authScheme == null)
/*      */     {
/* 1123 */       authScheme = authHandler.selectScheme(challenges, response, context);
/* 1124 */       authState.setAuthScheme(authScheme);
/*      */     }
/* 1126 */     String id = authScheme.getSchemeName();
/*      */     
/* 1128 */     Header challenge = (Header)challenges.get(id.toLowerCase(Locale.ENGLISH));
/* 1129 */     if (challenge == null) {
/* 1130 */       throw new AuthenticationException(id + " authorization challenge expected, but not found");
/*      */     }
/*      */     
/* 1133 */     authScheme.processChallenge(challenge);
/* 1134 */     this.log.debug("Authorization challenge processed");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void updateAuthState(AuthState authState, HttpHost host, CredentialsProvider credsProvider)
/*      */   {
/* 1143 */     if (!authState.isValid()) {
/* 1144 */       return;
/*      */     }
/*      */     
/* 1147 */     String hostname = host.getHostName();
/* 1148 */     int port = host.getPort();
/* 1149 */     if (port < 0) {
/* 1150 */       Scheme scheme = this.connManager.getSchemeRegistry().getScheme(host);
/* 1151 */       port = scheme.getDefaultPort();
/*      */     }
/*      */     
/* 1154 */     AuthScheme authScheme = authState.getAuthScheme();
/* 1155 */     AuthScope authScope = new AuthScope(hostname, port, authScheme.getRealm(), authScheme.getSchemeName());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1161 */     if (this.log.isDebugEnabled()) {
/* 1162 */       this.log.debug("Authentication scope: " + authScope);
/*      */     }
/* 1164 */     Credentials creds = authState.getCredentials();
/* 1165 */     if (creds == null) {
/* 1166 */       creds = credsProvider.getCredentials(authScope);
/* 1167 */       if (this.log.isDebugEnabled()) {
/* 1168 */         if (creds != null) {
/* 1169 */           this.log.debug("Found credentials");
/*      */         } else {
/* 1171 */           this.log.debug("Credentials not found");
/*      */         }
/*      */       }
/*      */     }
/* 1175 */     else if (authScheme.isComplete()) {
/* 1176 */       this.log.debug("Authentication failed");
/* 1177 */       creds = null;
/*      */     }
/*      */     
/* 1180 */     authState.setAuthScope(authScope);
/* 1181 */     authState.setCredentials(creds);
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/DefaultRequestDirector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */