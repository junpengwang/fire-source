/*     */ package org.shaded.apache.http.protocol;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UriPatternMatcher
/*     */ {
/*     */   private final Map map;
/*     */   
/*     */   public UriPatternMatcher()
/*     */   {
/*  62 */     this.map = new HashMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void register(String pattern, Object obj)
/*     */   {
/*  72 */     if (pattern == null) {
/*  73 */       throw new IllegalArgumentException("URI request pattern may not be null");
/*     */     }
/*  75 */     this.map.put(pattern, obj);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unregister(String pattern)
/*     */   {
/*  84 */     if (pattern == null) {
/*  85 */       return;
/*     */     }
/*  87 */     this.map.remove(pattern);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHandlers(Map map)
/*     */   {
/*  97 */     if (map == null) {
/*  98 */       throw new IllegalArgumentException("Map of handlers may not be null");
/*     */     }
/* 100 */     this.map.clear();
/* 101 */     this.map.putAll(map);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object lookup(String requestURI)
/*     */   {
/* 111 */     if (requestURI == null) {
/* 112 */       throw new IllegalArgumentException("Request URI may not be null");
/*     */     }
/*     */     
/* 115 */     int index = requestURI.indexOf("?");
/* 116 */     if (index != -1) {
/* 117 */       requestURI = requestURI.substring(0, index);
/*     */     }
/*     */     
/*     */ 
/* 121 */     Object handler = this.map.get(requestURI);
/* 122 */     String bestMatch; Iterator it; if (handler == null)
/*     */     {
/* 124 */       bestMatch = null;
/* 125 */       for (it = this.map.keySet().iterator(); it.hasNext();) {
/* 126 */         String pattern = (String)it.next();
/* 127 */         if (matchUriRequestPattern(pattern, requestURI))
/*     */         {
/* 129 */           if ((bestMatch == null) || (bestMatch.length() < pattern.length()) || ((bestMatch.length() == pattern.length()) && (pattern.endsWith("*"))))
/*     */           {
/*     */ 
/* 132 */             handler = this.map.get(pattern);
/* 133 */             bestMatch = pattern;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 138 */     return handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean matchUriRequestPattern(String pattern, String requestUri)
/*     */   {
/* 150 */     if (pattern.equals("*")) {
/* 151 */       return true;
/*     */     }
/* 153 */     return ((pattern.endsWith("*")) && (requestUri.startsWith(pattern.substring(0, pattern.length() - 1)))) || ((pattern.startsWith("*")) && (requestUri.endsWith(pattern.substring(1, pattern.length()))));
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/UriPatternMatcher.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */