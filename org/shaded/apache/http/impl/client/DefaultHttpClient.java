/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import org.shaded.apache.http.ConnectionReuseStrategy;
/*     */ import org.shaded.apache.http.HttpVersion;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.auth.AuthSchemeRegistry;
/*     */ import org.shaded.apache.http.client.AuthenticationHandler;
/*     */ import org.shaded.apache.http.client.CookieStore;
/*     */ import org.shaded.apache.http.client.CredentialsProvider;
/*     */ import org.shaded.apache.http.client.HttpRequestRetryHandler;
/*     */ import org.shaded.apache.http.client.RedirectHandler;
/*     */ import org.shaded.apache.http.client.UserTokenHandler;
/*     */ import org.shaded.apache.http.client.protocol.RequestAddCookies;
/*     */ import org.shaded.apache.http.client.protocol.RequestClientConnControl;
/*     */ import org.shaded.apache.http.client.protocol.RequestDefaultHeaders;
/*     */ import org.shaded.apache.http.client.protocol.RequestProxyAuthentication;
/*     */ import org.shaded.apache.http.client.protocol.RequestTargetAuthentication;
/*     */ import org.shaded.apache.http.client.protocol.ResponseProcessCookies;
/*     */ import org.shaded.apache.http.conn.ClientConnectionManager;
/*     */ import org.shaded.apache.http.conn.ClientConnectionManagerFactory;
/*     */ import org.shaded.apache.http.conn.ConnectionKeepAliveStrategy;
/*     */ import org.shaded.apache.http.conn.routing.HttpRoutePlanner;
/*     */ import org.shaded.apache.http.conn.scheme.PlainSocketFactory;
/*     */ import org.shaded.apache.http.conn.scheme.Scheme;
/*     */ import org.shaded.apache.http.conn.scheme.SchemeRegistry;
/*     */ import org.shaded.apache.http.conn.ssl.SSLSocketFactory;
/*     */ import org.shaded.apache.http.cookie.CookieSpecRegistry;
/*     */ import org.shaded.apache.http.impl.DefaultConnectionReuseStrategy;
/*     */ import org.shaded.apache.http.impl.auth.BasicSchemeFactory;
/*     */ import org.shaded.apache.http.impl.auth.DigestSchemeFactory;
/*     */ import org.shaded.apache.http.impl.conn.DefaultHttpRoutePlanner;
/*     */ import org.shaded.apache.http.impl.conn.SingleClientConnManager;
/*     */ import org.shaded.apache.http.impl.cookie.BestMatchSpecFactory;
/*     */ import org.shaded.apache.http.impl.cookie.BrowserCompatSpecFactory;
/*     */ import org.shaded.apache.http.impl.cookie.NetscapeDraftSpecFactory;
/*     */ import org.shaded.apache.http.impl.cookie.RFC2109SpecFactory;
/*     */ import org.shaded.apache.http.impl.cookie.RFC2965SpecFactory;
/*     */ import org.shaded.apache.http.params.BasicHttpParams;
/*     */ import org.shaded.apache.http.params.HttpConnectionParams;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ import org.shaded.apache.http.params.HttpProtocolParams;
/*     */ import org.shaded.apache.http.protocol.BasicHttpContext;
/*     */ import org.shaded.apache.http.protocol.BasicHttpProcessor;
/*     */ import org.shaded.apache.http.protocol.HttpContext;
/*     */ import org.shaded.apache.http.protocol.HttpRequestExecutor;
/*     */ import org.shaded.apache.http.protocol.RequestContent;
/*     */ import org.shaded.apache.http.protocol.RequestExpectContinue;
/*     */ import org.shaded.apache.http.protocol.RequestTargetHost;
/*     */ import org.shaded.apache.http.protocol.RequestUserAgent;
/*     */ import org.shaded.apache.http.util.VersionInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class DefaultHttpClient
/*     */   extends AbstractHttpClient
/*     */ {
/*     */   public DefaultHttpClient(ClientConnectionManager conman, HttpParams params)
/*     */   {
/* 168 */     super(conman, params);
/*     */   }
/*     */   
/*     */   public DefaultHttpClient(HttpParams params)
/*     */   {
/* 173 */     super(null, params);
/*     */   }
/*     */   
/*     */   public DefaultHttpClient()
/*     */   {
/* 178 */     super(null, null);
/*     */   }
/*     */   
/*     */ 
/*     */   protected HttpParams createHttpParams()
/*     */   {
/* 184 */     HttpParams params = new BasicHttpParams();
/* 185 */     HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
/*     */     
/* 187 */     HttpProtocolParams.setContentCharset(params, "ISO-8859-1");
/*     */     
/* 189 */     HttpProtocolParams.setUseExpectContinue(params, true);
/*     */     
/* 191 */     HttpConnectionParams.setTcpNoDelay(params, true);
/*     */     
/* 193 */     HttpConnectionParams.setSocketBufferSize(params, 8192);
/*     */     
/*     */ 
/*     */ 
/* 197 */     VersionInfo vi = VersionInfo.loadVersionInfo("org.shaded.apache.http.client", getClass().getClassLoader());
/*     */     
/* 199 */     String release = vi != null ? vi.getRelease() : "UNAVAILABLE";
/*     */     
/* 201 */     HttpProtocolParams.setUserAgent(params, "Apache-HttpClient/" + release + " (java 1.5)");
/*     */     
/*     */ 
/* 204 */     return params;
/*     */   }
/*     */   
/*     */ 
/*     */   protected HttpRequestExecutor createRequestExecutor()
/*     */   {
/* 210 */     return new HttpRequestExecutor();
/*     */   }
/*     */   
/*     */ 
/*     */   protected ClientConnectionManager createClientConnectionManager()
/*     */   {
/* 216 */     SchemeRegistry registry = new SchemeRegistry();
/* 217 */     registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
/*     */     
/* 219 */     registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
/*     */     
/*     */ 
/* 222 */     ClientConnectionManager connManager = null;
/* 223 */     HttpParams params = getParams();
/*     */     
/* 225 */     ClientConnectionManagerFactory factory = null;
/*     */     
/* 227 */     String className = (String)params.getParameter("http.connection-manager.factory-class-name");
/*     */     
/* 229 */     if (className != null) {
/*     */       try {
/* 231 */         Class<?> clazz = Class.forName(className);
/* 232 */         factory = (ClientConnectionManagerFactory)clazz.newInstance();
/*     */       } catch (ClassNotFoundException ex) {
/* 234 */         throw new IllegalStateException("Invalid class name: " + className);
/*     */       } catch (IllegalAccessException ex) {
/* 236 */         throw new IllegalAccessError(ex.getMessage());
/*     */       } catch (InstantiationException ex) {
/* 238 */         throw new InstantiationError(ex.getMessage());
/*     */       }
/*     */     }
/* 241 */     if (factory != null) {
/* 242 */       connManager = factory.newInstance(params, registry);
/*     */     } else {
/* 244 */       connManager = new SingleClientConnManager(getParams(), registry);
/*     */     }
/*     */     
/* 247 */     return connManager;
/*     */   }
/*     */   
/*     */ 
/*     */   protected HttpContext createHttpContext()
/*     */   {
/* 253 */     HttpContext context = new BasicHttpContext();
/* 254 */     context.setAttribute("http.scheme-registry", getConnectionManager().getSchemeRegistry());
/*     */     
/*     */ 
/* 257 */     context.setAttribute("http.authscheme-registry", getAuthSchemes());
/*     */     
/*     */ 
/* 260 */     context.setAttribute("http.cookiespec-registry", getCookieSpecs());
/*     */     
/*     */ 
/* 263 */     context.setAttribute("http.cookie-store", getCookieStore());
/*     */     
/*     */ 
/* 266 */     context.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
/*     */     
/*     */ 
/* 269 */     return context;
/*     */   }
/*     */   
/*     */ 
/*     */   protected ConnectionReuseStrategy createConnectionReuseStrategy()
/*     */   {
/* 275 */     return new DefaultConnectionReuseStrategy();
/*     */   }
/*     */   
/*     */   protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy()
/*     */   {
/* 280 */     return new DefaultConnectionKeepAliveStrategy();
/*     */   }
/*     */   
/*     */ 
/*     */   protected AuthSchemeRegistry createAuthSchemeRegistry()
/*     */   {
/* 286 */     AuthSchemeRegistry registry = new AuthSchemeRegistry();
/* 287 */     registry.register("Basic", new BasicSchemeFactory());
/*     */     
/*     */ 
/* 290 */     registry.register("Digest", new DigestSchemeFactory());
/*     */     
/*     */ 
/* 293 */     return registry;
/*     */   }
/*     */   
/*     */ 
/*     */   protected CookieSpecRegistry createCookieSpecRegistry()
/*     */   {
/* 299 */     CookieSpecRegistry registry = new CookieSpecRegistry();
/* 300 */     registry.register("best-match", new BestMatchSpecFactory());
/*     */     
/*     */ 
/* 303 */     registry.register("compatibility", new BrowserCompatSpecFactory());
/*     */     
/*     */ 
/* 306 */     registry.register("netscape", new NetscapeDraftSpecFactory());
/*     */     
/*     */ 
/* 309 */     registry.register("rfc2109", new RFC2109SpecFactory());
/*     */     
/*     */ 
/* 312 */     registry.register("rfc2965", new RFC2965SpecFactory());
/*     */     
/*     */ 
/* 315 */     return registry;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BasicHttpProcessor createHttpProcessor()
/*     */   {
/* 321 */     BasicHttpProcessor httpproc = new BasicHttpProcessor();
/* 322 */     httpproc.addInterceptor(new RequestDefaultHeaders());
/*     */     
/* 324 */     httpproc.addInterceptor(new RequestContent());
/* 325 */     httpproc.addInterceptor(new RequestTargetHost());
/*     */     
/* 327 */     httpproc.addInterceptor(new RequestClientConnControl());
/* 328 */     httpproc.addInterceptor(new RequestUserAgent());
/* 329 */     httpproc.addInterceptor(new RequestExpectContinue());
/*     */     
/* 331 */     httpproc.addInterceptor(new RequestAddCookies());
/* 332 */     httpproc.addInterceptor(new ResponseProcessCookies());
/*     */     
/* 334 */     httpproc.addInterceptor(new RequestTargetAuthentication());
/* 335 */     httpproc.addInterceptor(new RequestProxyAuthentication());
/* 336 */     return httpproc;
/*     */   }
/*     */   
/*     */ 
/*     */   protected HttpRequestRetryHandler createHttpRequestRetryHandler()
/*     */   {
/* 342 */     return new DefaultHttpRequestRetryHandler();
/*     */   }
/*     */   
/*     */ 
/*     */   protected RedirectHandler createRedirectHandler()
/*     */   {
/* 348 */     return new DefaultRedirectHandler();
/*     */   }
/*     */   
/*     */ 
/*     */   protected AuthenticationHandler createTargetAuthenticationHandler()
/*     */   {
/* 354 */     return new DefaultTargetAuthenticationHandler();
/*     */   }
/*     */   
/*     */ 
/*     */   protected AuthenticationHandler createProxyAuthenticationHandler()
/*     */   {
/* 360 */     return new DefaultProxyAuthenticationHandler();
/*     */   }
/*     */   
/*     */ 
/*     */   protected CookieStore createCookieStore()
/*     */   {
/* 366 */     return new BasicCookieStore();
/*     */   }
/*     */   
/*     */ 
/*     */   protected CredentialsProvider createCredentialsProvider()
/*     */   {
/* 372 */     return new BasicCredentialsProvider();
/*     */   }
/*     */   
/*     */ 
/*     */   protected HttpRoutePlanner createHttpRoutePlanner()
/*     */   {
/* 378 */     return new DefaultHttpRoutePlanner(getConnectionManager().getSchemeRegistry());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected UserTokenHandler createUserTokenHandler()
/*     */   {
/* 385 */     return new DefaultUserTokenHandler();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/DefaultHttpClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */