/*     */ package com.firebase.client.utilities;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.shaded.apache.http.client.methods.HttpEntityEnclosingRequestBase;
/*     */ import org.shaded.apache.http.client.methods.HttpGet;
/*     */ import org.shaded.apache.http.client.methods.HttpPost;
/*     */ import org.shaded.apache.http.client.methods.HttpPut;
/*     */ import org.shaded.apache.http.client.methods.HttpUriRequest;
/*     */ 
/*     */ public class HttpUtilities
/*     */ {
/*     */   public static enum HttpRequestType
/*     */   {
/*  21 */     GET,  POST,  DELETE,  PUT;
/*     */     
/*     */     private HttpRequestType() {}
/*     */   }
/*     */   
/*  26 */   public static URI buildUrl(String server, String path, Map<String, String> params) { try { URI serverURI = new URI(server);
/*  27 */       URI uri = new URI(serverURI.getScheme(), serverURI.getAuthority(), path, null, null);
/*     */       
/*  29 */       String query = null;
/*  30 */       if (params != null) {
/*  31 */         StringBuilder queryBuilder = new StringBuilder();
/*  32 */         boolean first = true;
/*  33 */         for (Map.Entry<String, String> entry : params.entrySet()) {
/*  34 */           if (!first) {
/*  35 */             queryBuilder.append("&");
/*     */           }
/*  37 */           first = false;
/*  38 */           queryBuilder.append(URLEncoder.encode((String)entry.getKey(), "utf-8"));
/*  39 */           queryBuilder.append("=");
/*  40 */           queryBuilder.append(URLEncoder.encode((String)entry.getValue(), "utf-8"));
/*     */         }
/*  42 */         query = queryBuilder.toString();
/*     */       }
/*  44 */       if (query != null) {
/*  45 */         return new URI(uri.toASCIIString() + "?" + query);
/*     */       }
/*  47 */       return uri;
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*  50 */       throw new RuntimeException("Couldn't build valid auth URI.", e);
/*     */     } catch (URISyntaxException e) {
/*  52 */       throw new RuntimeException("Couldn't build valid auth URI.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addMethodParams(HttpEntityEnclosingRequestBase request, Map<String, String> params) {
/*  57 */     if (params != null) {
/*  58 */       List<org.shaded.apache.http.NameValuePair> postParams = new ArrayList();
/*  59 */       for (Map.Entry<String, String> entry : params.entrySet()) {
/*  60 */         postParams.add(new org.shaded.apache.http.message.BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
/*     */       }
/*     */       try {
/*  63 */         request.setEntity(new org.shaded.apache.http.client.entity.UrlEncodedFormEntity(postParams, "utf-8"));
/*     */       } catch (UnsupportedEncodingException e) {
/*  65 */         throw new RuntimeException("Didn't find utf-8 encoding", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static HttpUriRequest requestWithType(String server, String path, HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams) {
/*  71 */     switch (type) {
/*     */     case GET: 
/*     */     case DELETE: 
/*  74 */       urlParams = new java.util.HashMap(urlParams);
/*  75 */       urlParams.putAll(requestParams);
/*  76 */       break;
/*     */     }
/*     */     
/*     */     
/*  80 */     if (type == HttpRequestType.DELETE) {
/*  81 */       urlParams.put("_method", "delete");
/*     */     }
/*  83 */     URI url = buildUrl(server, path, urlParams);
/*  84 */     switch (type) {
/*     */     case GET: 
/*  86 */       return new HttpGet(url);
/*     */     
/*     */     case POST: 
/*  89 */       HttpPost post = new HttpPost(url);
/*  90 */       if (requestParams != null) {
/*  91 */         addMethodParams(post, requestParams);
/*     */       }
/*  93 */       return post;
/*     */     
/*     */     case DELETE: 
/*  96 */       return new org.shaded.apache.http.client.methods.HttpDelete(url);
/*     */     
/*     */     case PUT: 
/*  99 */       HttpPut put = new HttpPut(url);
/* 100 */       if (requestParams != null) {
/* 101 */         addMethodParams(put, requestParams);
/*     */       }
/* 103 */       return put;
/*     */     }
/*     */     
/* 106 */     throw new IllegalStateException("Shouldn't reach here!");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/HttpUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */