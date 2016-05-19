/*     */ package com.firebase.client.authentication;
/*     */ 
/*     */ import com.firebase.client.AuthData;
/*     */ import com.firebase.client.CredentialStore;
/*     */ import com.firebase.client.Firebase;
/*     */ import com.firebase.client.Firebase.AuthListener;
/*     */ import com.firebase.client.Firebase.AuthResultHandler;
/*     */ import com.firebase.client.Firebase.AuthStateListener;
/*     */ import com.firebase.client.Firebase.CompletionListener;
/*     */ import com.firebase.client.Firebase.ResultHandler;
/*     */ import com.firebase.client.Firebase.ValueResultHandler;
/*     */ import com.firebase.client.FirebaseError;
/*     */ import com.firebase.client.RunLoop;
/*     */ import com.firebase.client.authentication.util.JsonWebToken;
/*     */ import com.firebase.client.core.Context;
/*     */ import com.firebase.client.core.PersistentConnection;
/*     */ import com.firebase.client.core.Repo;
/*     */ import com.firebase.client.core.RepoInfo;
/*     */ import com.firebase.client.utilities.HttpUtilities.HttpRequestType;
/*     */ import com.firebase.client.utilities.LogWrapper;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import org.shaded.apache.http.client.methods.HttpUriRequest;
/*     */ import org.shaded.apache.http.impl.client.DefaultHttpClient;
/*     */ 
/*     */ public class AuthenticationManager
/*     */ {
/*     */   private static final String TOKEN_KEY = "token";
/*     */   private static final String USER_DATA_KEY = "userData";
/*     */   private static final String AUTH_DATA_KEY = "authData";
/*     */   private static final String ERROR_KEY = "error";
/*     */   private static final String CUSTOM_PROVIDER = "custom";
/*     */   private static final String LOG_TAG = "AuthenticationManager";
/*     */   private static final int CONNECTION_TIMEOUT = 20000;
/*     */   private final Context context;
/*     */   private final Repo repo;
/*     */   private final RepoInfo repoInfo;
/*     */   private final PersistentConnection connection;
/*     */   private final CredentialStore store;
/*     */   private final LogWrapper logger;
/*     */   private final Set<Firebase.AuthStateListener> listenerSet;
/*     */   private AuthData authData;
/*     */   private AuthAttempt currentAuthAttempt;
/*     */   
/*     */   private class AuthAttempt
/*     */   {
/*     */     private final Firebase.AuthResultHandler handler;
/*     */     private final Firebase.AuthListener legacyListener;
/*  55 */     private boolean fired = false;
/*     */     
/*     */     AuthAttempt(Firebase.AuthResultHandler handler) {
/*  58 */       this.handler = handler;
/*  59 */       this.legacyListener = null;
/*     */     }
/*     */     
/*     */     AuthAttempt(Firebase.AuthListener legacyListener) {
/*  63 */       this.legacyListener = legacyListener;
/*  64 */       this.handler = null;
/*     */     }
/*     */     
/*     */     public void fireError(final FirebaseError error) {
/*  68 */       if ((!this.fired) || (this.legacyListener != null)) {
/*  69 */         AuthenticationManager.this.fireEvent(new Runnable()
/*     */         {
/*     */           public void run() {
/*  72 */             if (AuthenticationManager.AuthAttempt.this.legacyListener != null) {
/*  73 */               AuthenticationManager.AuthAttempt.this.legacyListener.onAuthError(error);
/*  74 */             } else if (AuthenticationManager.AuthAttempt.this.handler != null) {
/*  75 */               AuthenticationManager.AuthAttempt.this.handler.onAuthenticationError(error);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*  80 */       this.fired = true;
/*     */     }
/*     */     
/*     */     public void fireSuccess(final AuthData authData) {
/*  84 */       if ((!this.fired) || (this.legacyListener != null)) {
/*  85 */         AuthenticationManager.this.fireEvent(new Runnable()
/*     */         {
/*     */           public void run() {
/*  88 */             if (AuthenticationManager.AuthAttempt.this.legacyListener != null) {
/*  89 */               AuthenticationManager.AuthAttempt.this.legacyListener.onAuthSuccess(authData);
/*  90 */             } else if (AuthenticationManager.AuthAttempt.this.handler != null) {
/*  91 */               AuthenticationManager.AuthAttempt.this.handler.onAuthenticated(authData);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*  96 */       this.fired = true;
/*     */     }
/*     */     
/*     */     public void fireRevoked(final FirebaseError error) {
/* 100 */       if (this.legacyListener != null) {
/* 101 */         AuthenticationManager.this.fireEvent(new Runnable()
/*     */         {
/*     */           public void run() {
/* 104 */             AuthenticationManager.AuthAttempt.this.legacyListener.onAuthRevoked(error);
/*     */           }
/*     */         });
/*     */       }
/* 108 */       this.fired = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public AuthenticationManager(Context context, Repo repo, RepoInfo repoInfo, PersistentConnection connection) {
/* 113 */     this.context = context;
/* 114 */     this.repo = repo;
/* 115 */     this.repoInfo = repoInfo;
/* 116 */     this.connection = connection;
/* 117 */     this.authData = null;
/* 118 */     this.store = context.getCredentialStore();
/* 119 */     this.logger = context.getLogger("AuthenticationManager");
/* 120 */     this.listenerSet = new java.util.HashSet();
/*     */   }
/*     */   
/*     */   private void fireEvent(Runnable r) {
/* 124 */     this.context.getEventTarget().postEvent(r);
/*     */   }
/*     */   
/*     */   private void fireOnSuccess(final Firebase.ValueResultHandler handler, final Object result) {
/* 128 */     if (handler != null) {
/* 129 */       fireEvent(new Runnable()
/*     */       {
/*     */         public void run() {
/* 132 */           handler.onSuccess(result);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireOnError(final Firebase.ValueResultHandler handler, final FirebaseError error) {
/* 139 */     if (handler != null) {
/* 140 */       fireEvent(new Runnable()
/*     */       {
/*     */         public void run() {
/* 143 */           handler.onError(error);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   private Firebase.ValueResultHandler ignoreResultValueHandler(final Firebase.ResultHandler handler) {
/* 150 */     new Firebase.ValueResultHandler()
/*     */     {
/*     */       public void onSuccess(Object result) {
/* 153 */         handler.onSuccess();
/*     */       }
/*     */       
/*     */       public void onError(FirebaseError error)
/*     */       {
/* 158 */         handler.onError(error);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   private void preemptAnyExistingAttempts() {
/* 164 */     if (this.currentAuthAttempt != null) {
/* 165 */       FirebaseError error = new FirebaseError(-5, "Due to another authentication attempt, this authentication attempt was aborted before it could complete.");
/*     */       
/* 167 */       this.currentAuthAttempt.fireError(error);
/* 168 */       this.currentAuthAttempt = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private FirebaseError decodeErrorResponse(Object errorResponse) {
/* 173 */     String code = (String)Utilities.getOrNull(errorResponse, "code", String.class);
/* 174 */     String message = (String)Utilities.getOrNull(errorResponse, "message", String.class);
/* 175 */     String details = (String)Utilities.getOrNull(errorResponse, "details", String.class);
/* 176 */     if (code != null)
/*     */     {
/* 178 */       return FirebaseError.fromStatus(code, message, details);
/*     */     }
/* 180 */     String errorMessage = message == null ? "Error while authenticating." : message;
/* 181 */     return new FirebaseError(64537, errorMessage, details);
/*     */   }
/*     */   
/*     */   private boolean attemptHasBeenPreempted(AuthAttempt attempt)
/*     */   {
/* 186 */     return attempt != this.currentAuthAttempt;
/*     */   }
/*     */   
/*     */   private AuthAttempt newAuthAttempt(Firebase.AuthResultHandler handler) {
/* 190 */     preemptAnyExistingAttempts();
/* 191 */     this.currentAuthAttempt = new AuthAttempt(handler);
/* 192 */     return this.currentAuthAttempt;
/*     */   }
/*     */   
/*     */   private AuthAttempt newAuthAttempt(Firebase.AuthListener listener)
/*     */   {
/* 197 */     preemptAnyExistingAttempts();
/* 198 */     this.currentAuthAttempt = new AuthAttempt(listener);
/* 199 */     return this.currentAuthAttempt;
/*     */   }
/*     */   
/*     */   private void fireAuthErrorIfNotPreempted(final FirebaseError error, final AuthAttempt attempt) {
/* 203 */     if (!attemptHasBeenPreempted(attempt)) {
/* 204 */       if (attempt != null) {
/* 205 */         fireEvent(new Runnable()
/*     */         {
/*     */           public void run() {
/* 208 */             attempt.fireError(error);
/*     */           }
/*     */         });
/*     */       }
/* 212 */       this.currentAuthAttempt = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkServerSettings() {
/* 217 */     if (this.repoInfo.isDemoHost()) {
/* 218 */       this.logger.warn("Firebase authentication is supported on production Firebases only (*.firebaseio.com). To secure your Firebase, create a production Firebase at https://www.firebase.com.");
/*     */     }
/* 220 */     else if ((this.repoInfo.isCustomHost()) && (!this.context.isCustomAuthenticationServerSet())) {
/* 221 */       throw new IllegalStateException("For a custom firebase host you must first set your authentication server before using authentication features!");
/*     */     }
/*     */   }
/*     */   
/*     */   private String getFirebaseCredentialIdentifier() {
/* 226 */     return this.repoInfo.host;
/*     */   }
/*     */   
/*     */   private void scheduleNow(Runnable r) {
/* 230 */     this.context.getRunLoop().scheduleNow(r);
/*     */   }
/*     */   
/*     */   private AuthData parseAuthData(String token, Map<String, Object> rawAuthData, Map<String, Object> userData)
/*     */   {
/* 235 */     Map<String, Object> authData = (Map)Utilities.getOrNull(rawAuthData, "auth", Map.class);
/* 236 */     if (authData == null) {
/* 237 */       this.logger.warn("Received invalid auth data: " + rawAuthData);
/*     */     }
/*     */     
/* 240 */     Object expiresObj = rawAuthData.get("expires");
/*     */     long expires;
/* 242 */     long expires; if (expiresObj == null) {
/* 243 */       expires = 0L; } else { long expires;
/* 244 */       if ((expiresObj instanceof Integer)) {
/* 245 */         expires = ((Integer)expiresObj).intValue(); } else { long expires;
/* 246 */         if ((expiresObj instanceof Long)) {
/* 247 */           expires = ((Long)expiresObj).longValue(); } else { long expires;
/* 248 */           if ((expiresObj instanceof Double)) {
/* 249 */             expires = ((Double)expiresObj).longValue();
/*     */           } else
/* 251 */             expires = 0L;
/*     */         }
/*     */       } }
/* 254 */     String uid = (String)Utilities.getOrNull(authData, "uid", String.class);
/* 255 */     if (uid == null) {
/* 256 */       uid = (String)Utilities.getOrNull(userData, "uid", String.class);
/*     */     }
/*     */     
/* 259 */     String provider = (String)Utilities.getOrNull(authData, "provider", String.class);
/* 260 */     if (provider == null) {
/* 261 */       provider = (String)Utilities.getOrNull(userData, "provider", String.class);
/*     */     }
/* 263 */     if (provider == null) {
/* 264 */       provider = "custom";
/*     */     }
/*     */     
/* 267 */     if ((uid == null) || (uid.isEmpty())) {
/* 268 */       this.logger.warn("Received invalid auth data: " + authData);
/*     */     }
/* 270 */     Map<String, Object> providerData = (Map)Utilities.getOrNull(userData, provider, Map.class);
/* 271 */     if (providerData == null) {
/* 272 */       providerData = new HashMap();
/*     */     }
/* 274 */     return new AuthData(token, expires, uid, provider, authData, providerData);
/*     */   }
/*     */   
/*     */   private void handleBadAuthStatus(FirebaseError error, AuthAttempt attempt, boolean revoked)
/*     */   {
/* 279 */     boolean expiredToken = error.getCode() == -6;
/* 280 */     if ((expiredToken) && (this.context.getAuthExpirationBehavior() == com.firebase.client.core.AuthExpirationBehavior.PAUSE_WRITES_UNTIL_REAUTH))
/*     */     {
/* 282 */       if (this.logger.logsDebug()) this.logger.debug("Pausing writes due to expired token.");
/* 283 */       this.connection.pauseWrites();
/* 284 */     } else if (this.connection.writesPaused()) {
/* 285 */       assert (this.context.getAuthExpirationBehavior() == com.firebase.client.core.AuthExpirationBehavior.PAUSE_WRITES_UNTIL_REAUTH);
/* 286 */       if (this.logger.logsDebug()) this.logger.debug("Invalid auth while writes are paused; keeping existing session.");
/*     */     } else {
/* 288 */       clearSession();
/*     */     }
/*     */     
/* 291 */     updateAuthState(null);
/* 292 */     if (attempt != null) {
/* 293 */       if (revoked) {
/* 294 */         attempt.fireRevoked(error);
/*     */       } else {
/* 296 */         attempt.fireError(error);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void handleAuthSuccess(String credential, Map<String, Object> authDataMap, Map<String, Object> optionalUserData, boolean isNewSession, AuthAttempt attempt)
/*     */   {
/*     */     JsonWebToken token;
/*     */     try
/*     */     {
/* 306 */       token = JsonWebToken.decode(credential);
/*     */     } catch (IOException e) {
/* 308 */       if (this.logger.logsDebug()) this.logger.debug("Failed to parse JWT, probably a Firebase secret.");
/* 309 */       token = null;
/*     */     }
/* 311 */     if (isNewSession)
/*     */     {
/*     */ 
/* 314 */       if ((token != null) && 
/* 315 */         (!saveSession(credential, authDataMap, optionalUserData))) {
/* 316 */         this.logger.warn("Failed to store credentials! Authentication will not be persistent!");
/*     */       }
/*     */     }
/*     */     
/* 320 */     AuthData authData = parseAuthData(credential, authDataMap, optionalUserData);
/* 321 */     updateAuthState(authData);
/* 322 */     if (attempt != null) {
/* 323 */       attempt.fireSuccess(authData);
/*     */     }
/*     */     
/* 326 */     if (this.connection.writesPaused()) {
/* 327 */       if (this.logger.logsDebug()) this.logger.debug("Unpausing writes after successful login.");
/* 328 */       this.connection.unpauseWrites();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resumeSession()
/*     */   {
/*     */     try {
/* 335 */       String credentialData = this.store.loadCredential(getFirebaseCredentialIdentifier(), this.context.getSessionPersistenceKey());
/* 336 */       if (credentialData != null) {
/* 337 */         Map<String, Object> credentials = (Map)com.firebase.client.utilities.encoding.JsonHelpers.getMapper().readValue(credentialData, new com.shaded.fasterxml.jackson.core.type.TypeReference() {});
/* 339 */         final String tokenString = (String)Utilities.getOrNull(credentials, "token", String.class);
/* 340 */         final Map<String, Object> authDataObj = (Map)Utilities.getOrNull(credentials, "authData", Map.class);
/* 341 */         final Map<String, Object> userData = (Map)Utilities.getOrNull(credentials, "userData", Map.class);
/* 342 */         if (authDataObj != null) {
/* 343 */           AuthData authData = parseAuthData(tokenString, authDataObj, userData);
/* 344 */           updateAuthState(authData);
/* 345 */           this.context.getRunLoop().scheduleNow(new Runnable()
/*     */           {
/*     */             public void run() {
/* 348 */               AuthenticationManager.this.connection.auth(tokenString, new Firebase.AuthListener()
/*     */               {
/*     */                 public void onAuthError(FirebaseError error) {
/* 351 */                   AuthenticationManager.this.handleBadAuthStatus(error, null, false);
/*     */                 }
/*     */                 
/*     */                 public void onAuthSuccess(Object authData)
/*     */                 {
/* 356 */                   AuthenticationManager.this.handleAuthSuccess(AuthenticationManager.6.this.val$tokenString, AuthenticationManager.6.this.val$authDataObj, AuthenticationManager.6.this.val$userData, false, null);
/*     */                 }
/*     */                 
/*     */                 public void onAuthRevoked(FirebaseError error)
/*     */                 {
/* 361 */                   AuthenticationManager.this.handleBadAuthStatus(error, null, true);
/*     */                 }
/*     */               });
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/* 369 */       this.logger.warn("Failed resuming authentication session!", e);
/* 370 */       clearSession();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean saveSession(String token, Map<String, Object> authData, Map<String, Object> userData) {
/* 375 */     String firebaseId = getFirebaseCredentialIdentifier();
/* 376 */     String sessionId = this.context.getSessionPersistenceKey();
/* 377 */     this.store.clearCredential(firebaseId, sessionId);
/* 378 */     Map<String, Object> sessionMap = new HashMap();
/* 379 */     sessionMap.put("token", token);
/* 380 */     sessionMap.put("authData", authData);
/* 381 */     sessionMap.put("userData", userData);
/*     */     try {
/* 383 */       if (this.logger.logsDebug()) this.logger.debug("Storing credentials for Firebase \"" + firebaseId + "\" and session \"" + sessionId + "\".");
/* 384 */       String credentialData = com.firebase.client.utilities.encoding.JsonHelpers.getMapper().writeValueAsString(sessionMap);
/* 385 */       return this.store.storeCredential(firebaseId, sessionId, credentialData);
/*     */     } catch (com.shaded.fasterxml.jackson.core.JsonProcessingException e) {
/* 387 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean clearSession() {
/* 392 */     String firebaseId = getFirebaseCredentialIdentifier();
/* 393 */     String sessionId = this.context.getSessionPersistenceKey();
/* 394 */     if (this.logger.logsDebug()) this.logger.debug("Clearing credentials for Firebase \"" + firebaseId + "\" and session \"" + sessionId + "\".");
/* 395 */     return this.store.clearCredential(firebaseId, sessionId);
/*     */   }
/*     */   
/*     */   private void updateAuthState(final AuthData authData) {
/* 399 */     boolean changed = authData != null;
/* 400 */     this.authData = authData;
/* 401 */     if (changed) {
/* 402 */       for (final Firebase.AuthStateListener listener : this.listenerSet) {
/* 403 */         fireEvent(new Runnable()
/*     */         {
/*     */           public void run() {
/* 406 */             listener.onAuthStateChanged(authData);
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String buildUrlPath(String urlPath) {
/* 414 */     StringBuilder path = new StringBuilder();
/* 415 */     path.append("/v2/");
/* 416 */     path.append(this.repoInfo.namespace);
/* 417 */     if (!urlPath.startsWith("/")) {
/* 418 */       path.append("/");
/*     */     }
/* 420 */     path.append(urlPath);
/* 421 */     return path.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void makeRequest(String urlPath, HttpUtilities.HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams, final RequestHandler handler)
/*     */   {
/* 429 */     Map<String, String> actualUrlParams = new HashMap(urlParams);
/* 430 */     actualUrlParams.put("transport", "json");
/* 431 */     actualUrlParams.put("v", this.context.getPlatformVersion());
/* 432 */     final HttpUriRequest request = com.firebase.client.utilities.HttpUtilities.requestWithType(this.context.getAuthenticationServer(), buildUrlPath(urlPath), type, actualUrlParams, requestParams);
/* 433 */     if (this.logger.logsDebug())
/*     */     {
/* 435 */       URI uri = request.getURI();
/* 436 */       String scheme = uri.getScheme();
/* 437 */       String authority = uri.getAuthority();
/* 438 */       String path = uri.getPath();
/* 439 */       int numQueryParams = uri.getQuery().split("&").length;
/* 440 */       this.logger.debug(String.format("Sending request to %s://%s%s with %d query params", new Object[] { scheme, authority, path, Integer.valueOf(numQueryParams) }));
/*     */     }
/* 442 */     this.context.runBackgroundTask(new Runnable()
/*     */     {
/*     */       public void run() {
/* 445 */         org.shaded.apache.http.params.HttpParams httpParameters = new org.shaded.apache.http.params.BasicHttpParams();
/* 446 */         org.shaded.apache.http.params.HttpConnectionParams.setConnectionTimeout(httpParameters, 20000);
/* 447 */         org.shaded.apache.http.params.HttpConnectionParams.setSoTimeout(httpParameters, 20000);
/*     */         
/* 449 */         DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
/* 450 */         httpClient.getParams().setParameter("http.protocol.cookie-policy", "compatibility");
/*     */         try {
/* 452 */           final Map<String, Object> result = (Map)httpClient.execute(request, new JsonBasicResponseHandler());
/* 453 */           if (result == null) {
/* 454 */             throw new IOException("Authentication server did not respond with a valid response");
/*     */           }
/* 456 */           AuthenticationManager.this.scheduleNow(new Runnable()
/*     */           {
/*     */             public void run() {
/* 459 */               AuthenticationManager.8.this.val$handler.onResult(result);
/*     */             }
/*     */           });
/*     */         } catch (IOException e) {
/* 463 */           AuthenticationManager.this.scheduleNow(new Runnable()
/*     */           {
/*     */             public void run() {
/* 466 */               AuthenticationManager.8.this.val$handler.onError(e);
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void makeAuthenticationRequest(String urlPath, Map<String, String> params, Firebase.AuthResultHandler handler) {
/* 475 */     final AuthAttempt attempt = newAuthAttempt(handler);
/* 476 */     makeRequest(urlPath, HttpUtilities.HttpRequestType.GET, params, Collections.emptyMap(), new RequestHandler()
/*     */     {
/*     */       public void onResult(Map<String, Object> result) {
/* 479 */         Object errorResponse = result.get("error");
/* 480 */         String token = (String)Utilities.getOrNull(result, "token", String.class);
/* 481 */         if ((errorResponse == null) && (token != null))
/*     */         {
/* 483 */           if (!AuthenticationManager.this.attemptHasBeenPreempted(attempt)) {
/* 484 */             AuthenticationManager.this.authWithCredential(token, result, attempt);
/*     */           }
/*     */         } else {
/* 487 */           FirebaseError error = AuthenticationManager.this.decodeErrorResponse(errorResponse);
/* 488 */           AuthenticationManager.this.fireAuthErrorIfNotPreempted(error, attempt);
/*     */         }
/*     */       }
/*     */       
/*     */       public void onError(IOException e)
/*     */       {
/* 494 */         FirebaseError error = new FirebaseError(-24, "There was an exception while connecting to the authentication server: " + e.getLocalizedMessage());
/*     */         
/* 496 */         AuthenticationManager.this.fireAuthErrorIfNotPreempted(error, attempt);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void makeOperationRequest(String urlPath, HttpUtilities.HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams, Firebase.ResultHandler handler, boolean logUserOut)
/*     */   {
/* 508 */     makeOperationRequestWithResult(urlPath, type, urlParams, requestParams, ignoreResultValueHandler(handler), logUserOut);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void makeOperationRequestWithResult(String urlPath, HttpUtilities.HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams, final Firebase.ValueResultHandler<Map<String, Object>> handler, final boolean logUserOut)
/*     */   {
/* 517 */     makeRequest(urlPath, type, urlParams, requestParams, new RequestHandler()
/*     */     {
/*     */       public void onResult(final Map<String, Object> result) {
/* 520 */         Object errorResponse = result.get("error");
/* 521 */         if (errorResponse == null) {
/* 522 */           if (logUserOut) {
/* 523 */             String uid = (String)Utilities.getOrNull(result, "uid", String.class);
/* 524 */             if ((uid != null) && (AuthenticationManager.this.authData != null) && (uid.equals(AuthenticationManager.this.authData.getUid()))) {
/* 525 */               AuthenticationManager.this.unauth(null, false);
/*     */             }
/*     */           }
/*     */           
/* 529 */           AuthenticationManager.this.scheduleNow(new Runnable()
/*     */           {
/*     */             public void run() {
/* 532 */               AuthenticationManager.this.fireOnSuccess(AuthenticationManager.10.this.val$handler, result);
/*     */             }
/*     */           });
/*     */         } else {
/* 536 */           FirebaseError error = AuthenticationManager.this.decodeErrorResponse(errorResponse);
/* 537 */           AuthenticationManager.this.fireOnError(handler, error);
/*     */         }
/*     */       }
/*     */       
/*     */       public void onError(IOException e)
/*     */       {
/* 543 */         FirebaseError error = new FirebaseError(-24, "There was an exception while performing the request: " + e.getLocalizedMessage());
/*     */         
/* 545 */         AuthenticationManager.this.fireOnError(handler, error);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void authWithCredential(final String credential, final Map<String, Object> optionalUserData, final AuthAttempt attempt)
/*     */   {
/* 552 */     if (attempt != this.currentAuthAttempt) {
/* 553 */       throw new IllegalStateException("Ooops. We messed up tracking which authentications are running!");
/*     */     }
/* 555 */     if (this.logger.logsDebug()) { this.logger.debug("Authenticating with credential of length " + credential.length());
/*     */     }
/* 557 */     this.currentAuthAttempt = null;
/* 558 */     this.connection.auth(credential, new Firebase.AuthListener()
/*     */     {
/*     */       public void onAuthSuccess(Object authData) {
/* 561 */         AuthenticationManager.this.handleAuthSuccess(credential, (Map)authData, optionalUserData, true, attempt);
/*     */       }
/*     */       
/*     */       public void onAuthRevoked(FirebaseError error) {
/* 565 */         AuthenticationManager.this.handleBadAuthStatus(error, attempt, true);
/*     */       }
/*     */       
/*     */       public void onAuthError(FirebaseError error) {
/* 569 */         AuthenticationManager.this.handleBadAuthStatus(error, attempt, false);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public AuthData getAuth() {
/* 575 */     return this.authData;
/*     */   }
/*     */   
/*     */   public void unauth() {
/* 579 */     checkServerSettings();
/* 580 */     unauth(null);
/*     */   }
/*     */   
/*     */   public void unauth(Firebase.CompletionListener listener) {
/* 584 */     unauth(listener, true);
/*     */   }
/*     */   
/*     */   public void unauth(final Firebase.CompletionListener listener, boolean waitForCompletion) {
/* 588 */     checkServerSettings();
/* 589 */     final Semaphore semaphore = new Semaphore(0);
/* 590 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 593 */         AuthenticationManager.this.preemptAnyExistingAttempts();
/* 594 */         AuthenticationManager.this.updateAuthState(null);
/*     */         
/* 596 */         semaphore.release();
/* 597 */         AuthenticationManager.this.clearSession();
/* 598 */         AuthenticationManager.this.connection.unauth(new Firebase.CompletionListener()
/*     */         {
/*     */           public void onComplete(FirebaseError error, Firebase unusedRef) {
/* 601 */             if (AuthenticationManager.12.this.val$listener != null) {
/* 602 */               Firebase ref = new Firebase(AuthenticationManager.this.repo, new com.firebase.client.core.Path(""));
/* 603 */               AuthenticationManager.12.this.val$listener.onComplete(error, ref);
/*     */             }
/*     */           }
/*     */         });
/*     */         
/* 608 */         if (AuthenticationManager.this.connection.writesPaused()) {
/* 609 */           if (AuthenticationManager.this.logger.logsDebug()) AuthenticationManager.this.logger.debug("Unpausing writes after explicit unauth.");
/* 610 */           AuthenticationManager.this.connection.unpauseWrites();
/*     */         }
/*     */       }
/*     */     });
/* 614 */     if (waitForCompletion) {
/*     */       try {
/* 616 */         semaphore.acquire();
/*     */       } catch (InterruptedException e) {
/* 618 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void addAuthStateListener(final Firebase.AuthStateListener listener)
/*     */   {
/* 625 */     checkServerSettings();
/* 626 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 629 */         AuthenticationManager.this.listenerSet.add(listener);
/* 630 */         final AuthData authData = AuthenticationManager.this.authData;
/* 631 */         AuthenticationManager.this.fireEvent(new Runnable()
/*     */         {
/*     */           public void run() {
/* 634 */             AuthenticationManager.13.this.val$listener.onAuthStateChanged(authData);
/*     */           }
/*     */         });
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void removeAuthStateListener(final Firebase.AuthStateListener listener) {
/* 642 */     checkServerSettings();
/* 643 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 646 */         AuthenticationManager.this.listenerSet.remove(listener);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void authAnonymously(final Firebase.AuthResultHandler handler) {
/* 652 */     checkServerSettings();
/* 653 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 656 */         Map<String, String> params = new HashMap();
/* 657 */         AuthenticationManager.this.makeAuthenticationRequest("/auth/anonymous", params, handler);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void authWithPassword(final String email, final String password, final Firebase.AuthResultHandler handler) {
/* 663 */     checkServerSettings();
/* 664 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 667 */         Map<String, String> params = new HashMap();
/* 668 */         params.put("email", email);
/* 669 */         params.put("password", password);
/* 670 */         AuthenticationManager.this.makeAuthenticationRequest("/auth/password", params, handler);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void authWithCustomToken(final String token, final Firebase.AuthResultHandler handler) {
/* 676 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 679 */         AuthenticationManager.AuthAttempt attempt = AuthenticationManager.this.newAuthAttempt(handler);
/* 680 */         AuthenticationManager.this.authWithCredential(token, null, attempt);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void authWithFirebaseToken(final String token, final Firebase.AuthListener listener)
/*     */   {
/* 691 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 694 */         AuthenticationManager.AuthAttempt attempt = AuthenticationManager.this.newAuthAttempt(listener);
/* 695 */         AuthenticationManager.this.authWithCredential(token, null, attempt);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void authWithOAuthToken(String provider, String token, Firebase.AuthResultHandler handler) {
/* 701 */     if (token == null) {
/* 702 */       throw new IllegalArgumentException("Token must not be null!");
/*     */     }
/* 704 */     Map<String, String> params = new HashMap();
/* 705 */     params.put("access_token", token);
/* 706 */     authWithOAuthToken(provider, params, handler);
/*     */   }
/*     */   
/*     */   public void authWithOAuthToken(final String provider, final Map<String, String> params, final Firebase.AuthResultHandler handler) {
/* 710 */     checkServerSettings();
/* 711 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 714 */         String url = String.format("/auth/%s/token", new Object[] { provider });
/* 715 */         AuthenticationManager.this.makeAuthenticationRequest(url, params, handler);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void createUser(String email, String password, Firebase.ResultHandler handler) {
/* 721 */     createUser(email, password, ignoreResultValueHandler(handler));
/*     */   }
/*     */   
/*     */   public void createUser(final String email, final String password, final Firebase.ValueResultHandler<Map<String, Object>> handler) {
/* 725 */     checkServerSettings();
/* 726 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 729 */         Map<String, String> requestParams = new HashMap();
/* 730 */         requestParams.put("email", email);
/* 731 */         requestParams.put("password", password);
/* 732 */         AuthenticationManager.this.makeOperationRequestWithResult("/users", HttpUtilities.HttpRequestType.POST, Collections.emptyMap(), requestParams, handler, false);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeUser(final String email, final String password, final Firebase.ResultHandler handler)
/*     */   {
/* 740 */     checkServerSettings();
/* 741 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 744 */         Map<String, String> urlParams = new HashMap();
/* 745 */         urlParams.put("password", password);
/* 746 */         String url = String.format("/users/%s", new Object[] { email });
/* 747 */         AuthenticationManager.this.makeOperationRequest(url, HttpUtilities.HttpRequestType.DELETE, urlParams, Collections.emptyMap(), handler, true);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void changePassword(final String email, final String oldPassword, final String newPassword, final Firebase.ResultHandler handler)
/*     */   {
/* 754 */     checkServerSettings();
/* 755 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 758 */         Map<String, String> urlParams = new HashMap();
/* 759 */         urlParams.put("oldPassword", oldPassword);
/* 760 */         Map<String, String> requestParams = new HashMap();
/* 761 */         requestParams.put("password", newPassword);
/* 762 */         String url = String.format("/users/%s/password", new Object[] { email });
/* 763 */         AuthenticationManager.this.makeOperationRequest(url, HttpUtilities.HttpRequestType.PUT, urlParams, requestParams, handler, false);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void changeEmail(final String oldEmail, final String password, final String newEmail, final Firebase.ResultHandler handler) {
/* 769 */     checkServerSettings();
/* 770 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 773 */         Map<String, String> urlParams = new HashMap();
/* 774 */         urlParams.put("password", password);
/* 775 */         Map<String, String> requestParams = new HashMap();
/* 776 */         requestParams.put("email", newEmail);
/* 777 */         String url = String.format("/users/%s/email", new Object[] { oldEmail });
/* 778 */         AuthenticationManager.this.makeOperationRequest(url, HttpUtilities.HttpRequestType.PUT, urlParams, requestParams, handler, false);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void resetPassword(final String email, final Firebase.ResultHandler handler) {
/* 784 */     checkServerSettings();
/* 785 */     scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 788 */         String url = String.format("/users/%s/password", new Object[] { email });
/* 789 */         Map<String, String> params = Collections.emptyMap();
/* 790 */         AuthenticationManager.this.makeOperationRequest(url, HttpUtilities.HttpRequestType.POST, params, params, handler, false);
/*     */       }
/*     */     });
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/authentication/AuthenticationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */