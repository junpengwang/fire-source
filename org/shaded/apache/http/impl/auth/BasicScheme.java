/*     */ package org.shaded.apache.http.impl.auth;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import org.shaded.apache.commons.codec.binary.Base64;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.auth.AuthenticationException;
/*     */ import org.shaded.apache.http.auth.Credentials;
/*     */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*     */ import org.shaded.apache.http.auth.params.AuthParams;
/*     */ import org.shaded.apache.http.message.BufferedHeader;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ import org.shaded.apache.http.util.EncodingUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class BasicScheme
/*     */   extends RFC2617Scheme
/*     */ {
/*     */   private boolean complete;
/*     */   
/*     */   public BasicScheme()
/*     */   {
/*  65 */     this.complete = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSchemeName()
/*     */   {
/*  74 */     return "basic";
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
/*     */   public void processChallenge(Header header)
/*     */     throws MalformedChallengeException
/*     */   {
/*  88 */     super.processChallenge(header);
/*  89 */     this.complete = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isComplete()
/*     */   {
/*  99 */     return this.complete;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConnectionBased()
/*     */   {
/* 108 */     return false;
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
/*     */   public Header authenticate(Credentials credentials, HttpRequest request)
/*     */     throws AuthenticationException
/*     */   {
/* 127 */     if (credentials == null) {
/* 128 */       throw new IllegalArgumentException("Credentials may not be null");
/*     */     }
/* 130 */     if (request == null) {
/* 131 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/*     */     
/* 134 */     String charset = AuthParams.getCredentialCharset(request.getParams());
/* 135 */     return authenticate(credentials, charset, isProxy());
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
/*     */   public static Header authenticate(Credentials credentials, String charset, boolean proxy)
/*     */   {
/* 151 */     if (credentials == null) {
/* 152 */       throw new IllegalArgumentException("Credentials may not be null");
/*     */     }
/* 154 */     if (charset == null) {
/* 155 */       throw new IllegalArgumentException("charset may not be null");
/*     */     }
/*     */     
/* 158 */     StringBuilder tmp = new StringBuilder();
/* 159 */     tmp.append(credentials.getUserPrincipal().getName());
/* 160 */     tmp.append(":");
/* 161 */     tmp.append(credentials.getPassword() == null ? "null" : credentials.getPassword());
/*     */     
/* 163 */     byte[] base64password = Base64.encodeBase64(EncodingUtils.getBytes(tmp.toString(), charset));
/*     */     
/*     */ 
/* 166 */     CharArrayBuffer buffer = new CharArrayBuffer(32);
/* 167 */     if (proxy) {
/* 168 */       buffer.append("Proxy-Authorization");
/*     */     } else {
/* 170 */       buffer.append("Authorization");
/*     */     }
/* 172 */     buffer.append(": Basic ");
/* 173 */     buffer.append(base64password, 0, base64password.length);
/*     */     
/* 175 */     return new BufferedHeader(buffer);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/BasicScheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */