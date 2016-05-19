/*     */ package org.shaded.apache.http.client.utils;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Stack;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class URIUtils
/*     */ {
/*     */   public static URI createURI(String scheme, String host, int port, String path, String query, String fragment)
/*     */     throws URISyntaxException
/*     */   {
/*  80 */     StringBuilder buffer = new StringBuilder();
/*  81 */     if (host != null) {
/*  82 */       if (scheme != null) {
/*  83 */         buffer.append(scheme);
/*  84 */         buffer.append("://");
/*     */       }
/*  86 */       buffer.append(host);
/*  87 */       if (port > 0) {
/*  88 */         buffer.append(':');
/*  89 */         buffer.append(port);
/*     */       }
/*     */     }
/*  92 */     if ((path == null) || (!path.startsWith("/"))) {
/*  93 */       buffer.append('/');
/*     */     }
/*  95 */     if (path != null) {
/*  96 */       buffer.append(path);
/*     */     }
/*  98 */     if (query != null) {
/*  99 */       buffer.append('?');
/* 100 */       buffer.append(query);
/*     */     }
/* 102 */     if (fragment != null) {
/* 103 */       buffer.append('#');
/* 104 */       buffer.append(fragment);
/*     */     }
/* 106 */     return new URI(buffer.toString());
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
/*     */   public static URI rewriteURI(URI uri, HttpHost target, boolean dropFragment)
/*     */     throws URISyntaxException
/*     */   {
/* 129 */     if (uri == null) {
/* 130 */       throw new IllegalArgumentException("URI may nor be null");
/*     */     }
/* 132 */     if (target != null) {
/* 133 */       return createURI(target.getSchemeName(), target.getHostName(), target.getPort(), uri.getRawPath(), uri.getRawQuery(), dropFragment ? null : uri.getRawFragment());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 141 */     return createURI(null, null, -1, uri.getRawPath(), uri.getRawQuery(), dropFragment ? null : uri.getRawFragment());
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
/*     */   public static URI rewriteURI(URI uri, HttpHost target)
/*     */     throws URISyntaxException
/*     */   {
/* 159 */     return rewriteURI(uri, target, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static URI resolve(URI baseURI, String reference)
/*     */   {
/* 171 */     return resolve(baseURI, URI.create(reference));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static URI resolve(URI baseURI, URI reference)
/*     */   {
/* 183 */     if (baseURI == null) {
/* 184 */       throw new IllegalArgumentException("Base URI may nor be null");
/*     */     }
/* 186 */     if (reference == null) {
/* 187 */       throw new IllegalArgumentException("Reference URI may nor be null");
/*     */     }
/* 189 */     String s = reference.toString();
/* 190 */     if (s.startsWith("?")) {
/* 191 */       return resolveReferenceStartingWithQueryString(baseURI, reference);
/*     */     }
/* 193 */     boolean emptyReference = s.length() == 0;
/* 194 */     if (emptyReference) {
/* 195 */       reference = URI.create("#");
/*     */     }
/* 197 */     URI resolved = baseURI.resolve(reference);
/* 198 */     if (emptyReference) {
/* 199 */       String resolvedString = resolved.toString();
/* 200 */       resolved = URI.create(resolvedString.substring(0, resolvedString.indexOf('#')));
/*     */     }
/*     */     
/* 203 */     return removeDotSegments(resolved);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static URI resolveReferenceStartingWithQueryString(URI baseURI, URI reference)
/*     */   {
/* 215 */     String baseUri = baseURI.toString();
/* 216 */     baseUri = baseUri.indexOf('?') > -1 ? baseUri.substring(0, baseUri.indexOf('?')) : baseUri;
/*     */     
/* 218 */     return URI.create(baseUri + reference.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static URI removeDotSegments(URI uri)
/*     */   {
/* 228 */     String path = uri.getPath();
/* 229 */     if ((path == null) || (path.indexOf("/.") == -1))
/*     */     {
/* 231 */       return uri;
/*     */     }
/* 233 */     String[] inputSegments = path.split("/");
/* 234 */     Stack<String> outputSegments = new Stack();
/* 235 */     for (int i = 0; i < inputSegments.length; i++) {
/* 236 */       if ((inputSegments[i].length() != 0) && (!".".equals(inputSegments[i])))
/*     */       {
/*     */ 
/* 239 */         if ("..".equals(inputSegments[i])) {
/* 240 */           if (!outputSegments.isEmpty()) {
/* 241 */             outputSegments.pop();
/*     */           }
/*     */         } else
/* 244 */           outputSegments.push(inputSegments[i]);
/*     */       }
/*     */     }
/* 247 */     StringBuilder outputBuffer = new StringBuilder();
/* 248 */     for (String outputSegment : outputSegments) {
/* 249 */       outputBuffer.append('/').append(outputSegment);
/*     */     }
/*     */     try {
/* 252 */       return new URI(uri.getScheme(), uri.getAuthority(), outputBuffer.toString(), uri.getQuery(), uri.getFragment());
/*     */     }
/*     */     catch (URISyntaxException e) {
/* 255 */       throw new IllegalArgumentException(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/utils/URIUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */