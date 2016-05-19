/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpRequestFactory;
/*     */ import org.shaded.apache.http.MethodNotSupportedException;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.message.BasicHttpEntityEnclosingRequest;
/*     */ import org.shaded.apache.http.message.BasicHttpRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHttpRequestFactory
/*     */   implements HttpRequestFactory
/*     */ {
/*  51 */   private static final String[] RFC2616_COMMON_METHODS = { "GET" };
/*     */   
/*     */ 
/*     */ 
/*  55 */   private static final String[] RFC2616_ENTITY_ENC_METHODS = { "POST", "PUT" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  60 */   private static final String[] RFC2616_SPECIAL_METHODS = { "HEAD", "OPTIONS", "DELETE", "TRACE" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isOneOf(String[] methods, String method)
/*     */   {
/*  73 */     for (int i = 0; i < methods.length; i++) {
/*  74 */       if (methods[i].equalsIgnoreCase(method)) {
/*  75 */         return true;
/*     */       }
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public HttpRequest newHttpRequest(RequestLine requestline) throws MethodNotSupportedException
/*     */   {
/*  83 */     if (requestline == null) {
/*  84 */       throw new IllegalArgumentException("Request line may not be null");
/*     */     }
/*  86 */     String method = requestline.getMethod();
/*  87 */     if (isOneOf(RFC2616_COMMON_METHODS, method))
/*  88 */       return new BasicHttpRequest(requestline);
/*  89 */     if (isOneOf(RFC2616_ENTITY_ENC_METHODS, method))
/*  90 */       return new BasicHttpEntityEnclosingRequest(requestline);
/*  91 */     if (isOneOf(RFC2616_SPECIAL_METHODS, method)) {
/*  92 */       return new BasicHttpRequest(requestline);
/*     */     }
/*  94 */     throw new MethodNotSupportedException(method + " method not supported");
/*     */   }
/*     */   
/*     */   public HttpRequest newHttpRequest(String method, String uri)
/*     */     throws MethodNotSupportedException
/*     */   {
/* 100 */     if (isOneOf(RFC2616_COMMON_METHODS, method))
/* 101 */       return new BasicHttpRequest(method, uri);
/* 102 */     if (isOneOf(RFC2616_ENTITY_ENC_METHODS, method))
/* 103 */       return new BasicHttpEntityEnclosingRequest(method, uri);
/* 104 */     if (isOneOf(RFC2616_SPECIAL_METHODS, method)) {
/* 105 */       return new BasicHttpRequest(method, uri);
/*     */     }
/* 107 */     throw new MethodNotSupportedException(method + " method not supported");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/DefaultHttpRequestFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */