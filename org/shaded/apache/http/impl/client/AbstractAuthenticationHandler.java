/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.shaded.apache.commons.logging.Log;
/*     */ import org.shaded.apache.commons.logging.LogFactory;
/*     */ import org.shaded.apache.http.FormattedHeader;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.auth.AuthScheme;
/*     */ import org.shaded.apache.http.auth.AuthSchemeRegistry;
/*     */ import org.shaded.apache.http.auth.AuthenticationException;
/*     */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*     */ import org.shaded.apache.http.client.AuthenticationHandler;
/*     */ import org.shaded.apache.http.protocol.HTTP;
/*     */ import org.shaded.apache.http.protocol.HttpContext;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class AbstractAuthenticationHandler
/*     */   implements AuthenticationHandler
/*     */ {
/*  63 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*  65 */   private static final List<String> DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList(new String[] { "ntlm", "digest", "basic" }));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Map<String, Header> parseChallenges(Header[] headers)
/*     */     throws MalformedChallengeException
/*     */   {
/*  79 */     Map<String, Header> map = new HashMap(headers.length);
/*  80 */     for (Header header : headers) { int pos;
/*     */       CharArrayBuffer buffer;
/*     */       int pos;
/*  83 */       if ((header instanceof FormattedHeader)) {
/*  84 */         CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/*  85 */         pos = ((FormattedHeader)header).getValuePos();
/*     */       } else {
/*  87 */         String s = header.getValue();
/*  88 */         if (s == null) {
/*  89 */           throw new MalformedChallengeException("Header value is null");
/*     */         }
/*  91 */         buffer = new CharArrayBuffer(s.length());
/*  92 */         buffer.append(s);
/*  93 */         pos = 0;
/*     */       }
/*  95 */       while ((pos < buffer.length()) && (HTTP.isWhitespace(buffer.charAt(pos)))) {
/*  96 */         pos++;
/*     */       }
/*  98 */       int beginIndex = pos;
/*  99 */       while ((pos < buffer.length()) && (!HTTP.isWhitespace(buffer.charAt(pos)))) {
/* 100 */         pos++;
/*     */       }
/* 102 */       int endIndex = pos;
/* 103 */       String s = buffer.substring(beginIndex, endIndex);
/* 104 */       map.put(s.toLowerCase(Locale.ENGLISH), header);
/*     */     }
/* 106 */     return map;
/*     */   }
/*     */   
/*     */   protected List<String> getAuthPreferences() {
/* 110 */     return DEFAULT_SCHEME_PRIORITY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public AuthScheme selectScheme(Map<String, Header> challenges, HttpResponse response, HttpContext context)
/*     */     throws AuthenticationException
/*     */   {
/* 118 */     AuthSchemeRegistry registry = (AuthSchemeRegistry)context.getAttribute("http.authscheme-registry");
/*     */     
/* 120 */     if (registry == null) {
/* 121 */       throw new IllegalStateException("AuthScheme registry not set in HTTP context");
/*     */     }
/*     */     
/*     */ 
/* 125 */     Collection<String> authPrefs = (Collection)context.getAttribute("http.auth.scheme-pref");
/*     */     
/* 127 */     if (authPrefs == null) {
/* 128 */       authPrefs = getAuthPreferences();
/*     */     }
/*     */     
/* 131 */     if (this.log.isDebugEnabled()) {
/* 132 */       this.log.debug("Authentication schemes in the order of preference: " + authPrefs);
/*     */     }
/*     */     
/*     */ 
/* 136 */     AuthScheme authScheme = null;
/* 137 */     for (String id : authPrefs) {
/* 138 */       Header challenge = (Header)challenges.get(id.toLowerCase(Locale.ENGLISH));
/*     */       
/* 140 */       if (challenge != null) {
/* 141 */         if (this.log.isDebugEnabled()) {
/* 142 */           this.log.debug(id + " authentication scheme selected");
/*     */         }
/*     */         try {
/* 145 */           authScheme = registry.getAuthScheme(id, response.getParams());
/*     */         }
/*     */         catch (IllegalStateException e) {
/* 148 */           if (this.log.isWarnEnabled()) {
/* 149 */             this.log.warn("Authentication scheme " + id + " not supported");
/*     */           }
/*     */           break label307;
/*     */         }
/*     */       }
/* 154 */       else if (this.log.isDebugEnabled()) {
/* 155 */         this.log.debug("Challenge for " + id + " authentication scheme not available");
/*     */       }
/*     */     }
/*     */     
/*     */     label307:
/* 160 */     if (authScheme == null)
/*     */     {
/* 162 */       throw new AuthenticationException("Unable to respond to any of these challenges: " + challenges);
/*     */     }
/*     */     
/*     */ 
/* 166 */     return authScheme;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/AbstractAuthenticationHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */