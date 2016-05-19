/*     */ package org.shaded.apache.http.impl.auth;
/*     */ 
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.auth.AuthenticationException;
/*     */ import org.shaded.apache.http.auth.Credentials;
/*     */ import org.shaded.apache.http.auth.InvalidCredentialsException;
/*     */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*     */ import org.shaded.apache.http.auth.NTCredentials;
/*     */ import org.shaded.apache.http.message.BufferedHeader;
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
/*     */ @NotThreadSafe
/*     */ public class NTLMScheme
/*     */   extends AuthSchemeBase
/*     */ {
/*     */   private final NTLMEngine engine;
/*     */   private State state;
/*     */   private String challenge;
/*     */   
/*     */   static enum State
/*     */   {
/*  59 */     UNINITIATED, 
/*  60 */     CHALLENGE_RECEIVED, 
/*  61 */     MSG_TYPE1_GENERATED, 
/*  62 */     MSG_TYPE2_RECEVIED, 
/*  63 */     MSG_TYPE3_GENERATED, 
/*  64 */     FAILED;
/*     */     
/*     */ 
/*     */ 
/*     */     private State() {}
/*     */   }
/*     */   
/*     */ 
/*     */   public NTLMScheme(NTLMEngine engine)
/*     */   {
/*  74 */     if (engine == null) {
/*  75 */       throw new IllegalArgumentException("NTLM engine may not be null");
/*     */     }
/*  77 */     this.engine = engine;
/*  78 */     this.state = State.UNINITIATED;
/*  79 */     this.challenge = null;
/*     */   }
/*     */   
/*     */   public String getSchemeName() {
/*  83 */     return "ntlm";
/*     */   }
/*     */   
/*     */   public String getParameter(String name)
/*     */   {
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public String getRealm()
/*     */   {
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isConnectionBased() {
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   protected void parseChallenge(CharArrayBuffer buffer, int pos, int len)
/*     */     throws MalformedChallengeException
/*     */   {
/* 103 */     String challenge = buffer.substringTrimmed(pos, len);
/* 104 */     if (challenge.length() == 0) {
/* 105 */       if (this.state == State.UNINITIATED) {
/* 106 */         this.state = State.CHALLENGE_RECEIVED;
/*     */       } else {
/* 108 */         this.state = State.FAILED;
/*     */       }
/* 110 */       this.challenge = null;
/*     */     } else {
/* 112 */       this.state = State.MSG_TYPE2_RECEVIED;
/* 113 */       this.challenge = challenge;
/*     */     }
/*     */   }
/*     */   
/*     */   public Header authenticate(Credentials credentials, HttpRequest request)
/*     */     throws AuthenticationException
/*     */   {
/* 120 */     NTCredentials ntcredentials = null;
/*     */     try {
/* 122 */       ntcredentials = (NTCredentials)credentials;
/*     */     } catch (ClassCastException e) {
/* 124 */       throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + credentials.getClass().getName());
/*     */     }
/*     */     
/*     */ 
/* 128 */     String response = null;
/* 129 */     if ((this.state == State.CHALLENGE_RECEIVED) || (this.state == State.FAILED)) {
/* 130 */       response = this.engine.generateType1Msg(ntcredentials.getDomain(), ntcredentials.getWorkstation());
/*     */       
/*     */ 
/* 133 */       this.state = State.MSG_TYPE1_GENERATED;
/* 134 */     } else if (this.state == State.MSG_TYPE2_RECEVIED) {
/* 135 */       response = this.engine.generateType3Msg(ntcredentials.getUserName(), ntcredentials.getPassword(), ntcredentials.getDomain(), ntcredentials.getWorkstation(), this.challenge);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 141 */       this.state = State.MSG_TYPE3_GENERATED;
/*     */     } else {
/* 143 */       throw new AuthenticationException("Unexpected state: " + this.state);
/*     */     }
/* 145 */     CharArrayBuffer buffer = new CharArrayBuffer(32);
/* 146 */     if (isProxy()) {
/* 147 */       buffer.append("Proxy-Authorization");
/*     */     } else {
/* 149 */       buffer.append("Authorization");
/*     */     }
/* 151 */     buffer.append(": NTLM ");
/* 152 */     buffer.append(response);
/* 153 */     return new BufferedHeader(buffer);
/*     */   }
/*     */   
/*     */   public boolean isComplete() {
/* 157 */     return (this.state == State.MSG_TYPE3_GENERATED) || (this.state == State.FAILED);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/NTLMScheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */