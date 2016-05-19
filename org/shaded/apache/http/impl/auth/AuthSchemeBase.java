/*     */ package org.shaded.apache.http.impl.auth;
/*     */ 
/*     */ import org.shaded.apache.http.FormattedHeader;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.auth.AuthScheme;
/*     */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*     */ import org.shaded.apache.http.protocol.HTTP;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotThreadSafe
/*     */ public abstract class AuthSchemeBase
/*     */   implements AuthScheme
/*     */ {
/*     */   private boolean proxy;
/*     */   
/*     */   public void processChallenge(Header header)
/*     */     throws MalformedChallengeException
/*     */   {
/*  72 */     if (header == null) {
/*  73 */       throw new IllegalArgumentException("Header may not be null");
/*     */     }
/*  75 */     String authheader = header.getName();
/*  76 */     if (authheader.equalsIgnoreCase("WWW-Authenticate")) {
/*  77 */       this.proxy = false;
/*  78 */     } else if (authheader.equalsIgnoreCase("Proxy-Authenticate")) {
/*  79 */       this.proxy = true;
/*     */     } else {
/*  81 */       throw new MalformedChallengeException("Unexpected header name: " + authheader);
/*     */     }
/*     */     int pos;
/*     */     CharArrayBuffer buffer;
/*     */     int pos;
/*  86 */     if ((header instanceof FormattedHeader)) {
/*  87 */       CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/*  88 */       pos = ((FormattedHeader)header).getValuePos();
/*     */     } else {
/*  90 */       String s = header.getValue();
/*  91 */       if (s == null) {
/*  92 */         throw new MalformedChallengeException("Header value is null");
/*     */       }
/*  94 */       buffer = new CharArrayBuffer(s.length());
/*  95 */       buffer.append(s);
/*  96 */       pos = 0;
/*     */     }
/*  98 */     while ((pos < buffer.length()) && (HTTP.isWhitespace(buffer.charAt(pos)))) {
/*  99 */       pos++;
/*     */     }
/* 101 */     int beginIndex = pos;
/* 102 */     while ((pos < buffer.length()) && (!HTTP.isWhitespace(buffer.charAt(pos)))) {
/* 103 */       pos++;
/*     */     }
/* 105 */     int endIndex = pos;
/* 106 */     String s = buffer.substring(beginIndex, endIndex);
/* 107 */     if (!s.equalsIgnoreCase(getSchemeName())) {
/* 108 */       throw new MalformedChallengeException("Invalid scheme identifier: " + s);
/*     */     }
/*     */     
/* 111 */     parseChallenge(buffer, pos, buffer.length());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void parseChallenge(CharArrayBuffer paramCharArrayBuffer, int paramInt1, int paramInt2)
/*     */     throws MalformedChallengeException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isProxy()
/*     */   {
/* 125 */     return this.proxy;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 130 */     return getSchemeName();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/AuthSchemeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */