/*     */ package org.shaded.apache.http.impl.auth;
/*     */ 
/*     */ import java.security.MessageDigest;
/*     */ import java.security.Principal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.RequestLine;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.auth.AuthenticationException;
/*     */ import org.shaded.apache.http.auth.Credentials;
/*     */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*     */ import org.shaded.apache.http.auth.params.AuthParams;
/*     */ import org.shaded.apache.http.message.BasicHeaderValueFormatter;
/*     */ import org.shaded.apache.http.message.BasicNameValuePair;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class DigestScheme
/*     */   extends RFC2617Scheme
/*     */ {
/*  83 */   private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */   
/*     */ 
/*     */   private boolean complete;
/*     */   
/*     */ 
/*     */   private static final String NC = "00000001";
/*     */   
/*     */   private static final int QOP_MISSING = 0;
/*     */   
/*     */   private static final int QOP_AUTH_INT = 1;
/*     */   
/*     */   private static final int QOP_AUTH = 2;
/*     */   
/*  97 */   private int qopVariant = 0;
/*     */   
/*     */ 
/*     */   private String cnonce;
/*     */   
/*     */ 
/*     */   public DigestScheme()
/*     */   {
/* 105 */     this.complete = false;
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
/* 119 */     super.processChallenge(header);
/*     */     
/* 121 */     if (getParameter("realm") == null) {
/* 122 */       throw new MalformedChallengeException("missing realm in challange");
/*     */     }
/* 124 */     if (getParameter("nonce") == null) {
/* 125 */       throw new MalformedChallengeException("missing nonce in challange");
/*     */     }
/*     */     
/* 128 */     boolean unsupportedQop = false;
/*     */     
/* 130 */     String qop = getParameter("qop");
/* 131 */     if (qop != null) {
/* 132 */       StringTokenizer tok = new StringTokenizer(qop, ",");
/* 133 */       while (tok.hasMoreTokens()) {
/* 134 */         String variant = tok.nextToken().trim();
/* 135 */         if (variant.equals("auth")) {
/* 136 */           this.qopVariant = 2;
/* 137 */           break; }
/* 138 */         if (variant.equals("auth-int")) {
/* 139 */           this.qopVariant = 1;
/*     */         } else {
/* 141 */           unsupportedQop = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 146 */     if ((unsupportedQop) && (this.qopVariant == 0)) {
/* 147 */       throw new MalformedChallengeException("None of the qop methods is supported");
/*     */     }
/*     */     
/* 150 */     this.cnonce = null;
/* 151 */     this.complete = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isComplete()
/*     */   {
/* 161 */     String s = getParameter("stale");
/* 162 */     if ("true".equalsIgnoreCase(s)) {
/* 163 */       return false;
/*     */     }
/* 165 */     return this.complete;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSchemeName()
/*     */   {
/* 175 */     return "digest";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConnectionBased()
/*     */   {
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   public void overrideParamter(String name, String value) {
/* 188 */     getParameters().put(name, value);
/*     */   }
/*     */   
/*     */   private String getCnonce() {
/* 192 */     if (this.cnonce == null) {
/* 193 */       this.cnonce = createCnonce();
/*     */     }
/* 195 */     return this.cnonce;
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
/*     */   public Header authenticate(Credentials credentials, HttpRequest request)
/*     */     throws AuthenticationException
/*     */   {
/* 216 */     if (credentials == null) {
/* 217 */       throw new IllegalArgumentException("Credentials may not be null");
/*     */     }
/* 219 */     if (request == null) {
/* 220 */       throw new IllegalArgumentException("HTTP request may not be null");
/*     */     }
/*     */     
/*     */ 
/* 224 */     getParameters().put("methodname", request.getRequestLine().getMethod());
/* 225 */     getParameters().put("uri", request.getRequestLine().getUri());
/* 226 */     String charset = getParameter("charset");
/* 227 */     if (charset == null) {
/* 228 */       charset = AuthParams.getCredentialCharset(request.getParams());
/* 229 */       getParameters().put("charset", charset);
/*     */     }
/* 231 */     String digest = createDigest(credentials);
/* 232 */     return createDigestHeader(credentials, digest);
/*     */   }
/*     */   
/*     */   private static MessageDigest createMessageDigest(String digAlg) throws UnsupportedDigestAlgorithmException
/*     */   {
/*     */     try {
/* 238 */       return MessageDigest.getInstance(digAlg);
/*     */     } catch (Exception e) {
/* 240 */       throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + digAlg);
/*     */     }
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
/*     */   private String createDigest(Credentials credentials)
/*     */     throws AuthenticationException
/*     */   {
/* 255 */     String uri = getParameter("uri");
/* 256 */     String realm = getParameter("realm");
/* 257 */     String nonce = getParameter("nonce");
/* 258 */     String method = getParameter("methodname");
/* 259 */     String algorithm = getParameter("algorithm");
/* 260 */     if (uri == null) {
/* 261 */       throw new IllegalStateException("URI may not be null");
/*     */     }
/* 263 */     if (realm == null) {
/* 264 */       throw new IllegalStateException("Realm may not be null");
/*     */     }
/* 266 */     if (nonce == null) {
/* 267 */       throw new IllegalStateException("Nonce may not be null");
/*     */     }
/*     */     
/* 270 */     if (algorithm == null) {
/* 271 */       algorithm = "MD5";
/*     */     }
/*     */     
/* 274 */     String charset = getParameter("charset");
/* 275 */     if (charset == null) {
/* 276 */       charset = "ISO-8859-1";
/*     */     }
/*     */     
/* 279 */     if (this.qopVariant == 1) {
/* 280 */       throw new AuthenticationException("Unsupported qop in HTTP Digest authentication");
/*     */     }
/*     */     
/*     */ 
/* 284 */     String digAlg = algorithm;
/* 285 */     if (digAlg.equalsIgnoreCase("MD5-sess")) {
/* 286 */       digAlg = "MD5";
/*     */     }
/* 288 */     MessageDigest digester = createMessageDigest(digAlg);
/*     */     
/* 290 */     String uname = credentials.getUserPrincipal().getName();
/* 291 */     String pwd = credentials.getPassword();
/*     */     
/*     */ 
/* 294 */     StringBuilder tmp = new StringBuilder(uname.length() + realm.length() + pwd.length() + 2);
/* 295 */     tmp.append(uname);
/* 296 */     tmp.append(':');
/* 297 */     tmp.append(realm);
/* 298 */     tmp.append(':');
/* 299 */     tmp.append(pwd);
/*     */     
/* 301 */     String a1 = tmp.toString();
/*     */     
/*     */ 
/* 304 */     if (algorithm.equalsIgnoreCase("MD5-sess"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 309 */       algorithm = "MD5";
/* 310 */       String cnonce = getCnonce();
/*     */       
/* 312 */       String tmp2 = encode(digester.digest(EncodingUtils.getBytes(a1, charset)));
/* 313 */       StringBuilder tmp3 = new StringBuilder(tmp2.length() + nonce.length() + cnonce.length() + 2);
/*     */       
/* 315 */       tmp3.append(tmp2);
/* 316 */       tmp3.append(':');
/* 317 */       tmp3.append(nonce);
/* 318 */       tmp3.append(':');
/* 319 */       tmp3.append(cnonce);
/* 320 */       a1 = tmp3.toString();
/*     */     }
/* 322 */     String hasha1 = encode(digester.digest(EncodingUtils.getBytes(a1, charset)));
/*     */     
/* 324 */     String a2 = null;
/* 325 */     if (this.qopVariant != 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 330 */       a2 = method + ':' + uri;
/*     */     }
/* 332 */     String hasha2 = encode(digester.digest(EncodingUtils.getAsciiBytes(a2)));
/*     */     
/*     */     String serverDigestValue;
/*     */     String serverDigestValue;
/* 336 */     if (this.qopVariant == 0) {
/* 337 */       StringBuilder tmp2 = new StringBuilder(hasha1.length() + nonce.length() + hasha1.length());
/*     */       
/* 339 */       tmp2.append(hasha1);
/* 340 */       tmp2.append(':');
/* 341 */       tmp2.append(nonce);
/* 342 */       tmp2.append(':');
/* 343 */       tmp2.append(hasha2);
/* 344 */       serverDigestValue = tmp2.toString();
/*     */     } else {
/* 346 */       String qopOption = getQopVariantString();
/* 347 */       String cnonce = getCnonce();
/*     */       
/* 349 */       StringBuilder tmp2 = new StringBuilder(hasha1.length() + nonce.length() + "00000001".length() + cnonce.length() + qopOption.length() + hasha2.length() + 5);
/*     */       
/* 351 */       tmp2.append(hasha1);
/* 352 */       tmp2.append(':');
/* 353 */       tmp2.append(nonce);
/* 354 */       tmp2.append(':');
/* 355 */       tmp2.append("00000001");
/* 356 */       tmp2.append(':');
/* 357 */       tmp2.append(cnonce);
/* 358 */       tmp2.append(':');
/* 359 */       tmp2.append(qopOption);
/* 360 */       tmp2.append(':');
/* 361 */       tmp2.append(hasha2);
/* 362 */       serverDigestValue = tmp2.toString();
/*     */     }
/*     */     
/* 365 */     String serverDigest = encode(digester.digest(EncodingUtils.getAsciiBytes(serverDigestValue)));
/*     */     
/*     */ 
/* 368 */     return serverDigest;
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
/*     */   private Header createDigestHeader(Credentials credentials, String digest)
/*     */   {
/* 383 */     CharArrayBuffer buffer = new CharArrayBuffer(128);
/* 384 */     if (isProxy()) {
/* 385 */       buffer.append("Proxy-Authorization");
/*     */     } else {
/* 387 */       buffer.append("Authorization");
/*     */     }
/* 389 */     buffer.append(": Digest ");
/*     */     
/* 391 */     String uri = getParameter("uri");
/* 392 */     String realm = getParameter("realm");
/* 393 */     String nonce = getParameter("nonce");
/* 394 */     String opaque = getParameter("opaque");
/* 395 */     String response = digest;
/* 396 */     String algorithm = getParameter("algorithm");
/*     */     
/* 398 */     String uname = credentials.getUserPrincipal().getName();
/*     */     
/* 400 */     List<BasicNameValuePair> params = new ArrayList(20);
/* 401 */     params.add(new BasicNameValuePair("username", uname));
/* 402 */     params.add(new BasicNameValuePair("realm", realm));
/* 403 */     params.add(new BasicNameValuePair("nonce", nonce));
/* 404 */     params.add(new BasicNameValuePair("uri", uri));
/* 405 */     params.add(new BasicNameValuePair("response", response));
/*     */     
/* 407 */     if (this.qopVariant != 0) {
/* 408 */       params.add(new BasicNameValuePair("qop", getQopVariantString()));
/* 409 */       params.add(new BasicNameValuePair("nc", "00000001"));
/* 410 */       params.add(new BasicNameValuePair("cnonce", getCnonce()));
/*     */     }
/* 412 */     if (algorithm != null) {
/* 413 */       params.add(new BasicNameValuePair("algorithm", algorithm));
/*     */     }
/* 415 */     if (opaque != null) {
/* 416 */       params.add(new BasicNameValuePair("opaque", opaque));
/*     */     }
/*     */     
/* 419 */     for (int i = 0; i < params.size(); i++) {
/* 420 */       BasicNameValuePair param = (BasicNameValuePair)params.get(i);
/* 421 */       if (i > 0) {
/* 422 */         buffer.append(", ");
/*     */       }
/* 424 */       boolean noQuotes = ("nc".equals(param.getName())) || ("qop".equals(param.getName()));
/*     */       
/* 426 */       BasicHeaderValueFormatter.DEFAULT.formatNameValuePair(buffer, param, !noQuotes);
/*     */     }
/*     */     
/* 429 */     return new BufferedHeader(buffer);
/*     */   }
/*     */   
/*     */   private String getQopVariantString() { String qopOption;
/*     */     String qopOption;
/* 434 */     if (this.qopVariant == 1) {
/* 435 */       qopOption = "auth-int";
/*     */     } else {
/* 437 */       qopOption = "auth";
/*     */     }
/* 439 */     return qopOption;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String encode(byte[] binaryData)
/*     */   {
/* 450 */     int n = binaryData.length;
/* 451 */     char[] buffer = new char[n * 2];
/* 452 */     for (int i = 0; i < n; i++) {
/* 453 */       int low = binaryData[i] & 0xF;
/* 454 */       int high = (binaryData[i] & 0xF0) >> 4;
/* 455 */       buffer[(i * 2)] = HEXADECIMAL[high];
/* 456 */       buffer[(i * 2 + 1)] = HEXADECIMAL[low];
/*     */     }
/*     */     
/* 459 */     return new String(buffer);
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
/*     */   public static String createCnonce()
/*     */   {
/* 472 */     MessageDigest md5Helper = createMessageDigest("MD5");
/*     */     
/* 474 */     String cnonce = Long.toString(System.currentTimeMillis());
/* 475 */     cnonce = encode(md5Helper.digest(EncodingUtils.getAsciiBytes(cnonce)));
/*     */     
/* 477 */     return cnonce;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/DigestScheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */