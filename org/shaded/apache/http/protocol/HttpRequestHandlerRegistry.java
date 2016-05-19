/*     */ package org.shaded.apache.http.protocol;
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRequestHandlerRegistry
/*     */   implements HttpRequestHandlerResolver
/*     */ {
/*     */   private final UriPatternMatcher matcher;
/*     */   
/*     */   public HttpRequestHandlerRegistry()
/*     */   {
/*  61 */     this.matcher = new UriPatternMatcher();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void register(String pattern, HttpRequestHandler handler)
/*     */   {
/*  72 */     if (pattern == null) {
/*  73 */       throw new IllegalArgumentException("URI request pattern may not be null");
/*     */     }
/*  75 */     if (handler == null) {
/*  76 */       throw new IllegalArgumentException("Request handler may not be null");
/*     */     }
/*  78 */     this.matcher.register(pattern, handler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unregister(String pattern)
/*     */   {
/*  87 */     this.matcher.unregister(pattern);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHandlers(Map map)
/*     */   {
/*  95 */     this.matcher.setHandlers(map);
/*     */   }
/*     */   
/*     */   public HttpRequestHandler lookup(String requestURI) {
/*  99 */     return (HttpRequestHandler)this.matcher.lookup(requestURI);
/*     */   }
/*     */   
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   protected boolean matchUriRequestPattern(String pattern, String requestUri) {
/* 106 */     return this.matcher.matchUriRequestPattern(pattern, requestUri);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/HttpRequestHandlerRegistry.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */