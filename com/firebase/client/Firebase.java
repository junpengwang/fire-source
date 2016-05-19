/*     */ package com.firebase.client;
/*     */ 
/*     */ import com.firebase.client.authentication.AuthenticationManager;
/*     */ import com.firebase.client.core.CompoundWrite;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.Repo;
/*     */ import com.firebase.client.core.RepoManager;
/*     */ import com.firebase.client.core.ValidationPath;
/*     */ import com.firebase.client.core.view.QueryParams;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.snapshot.NodeUtilities;
/*     */ import com.firebase.client.snapshot.PriorityUtilities;
/*     */ import com.firebase.client.utilities.ParsedUrl;
/*     */ import com.firebase.client.utilities.PushIdGenerator;
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ import com.firebase.client.utilities.Validation;
/*     */ import com.firebase.client.utilities.encoding.JsonHelpers;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class Firebase
/*     */   extends Query
/*     */ {
/*     */   private static Config defaultConfig;
/*     */   
/*     */   private AuthenticationManager getAuthenticationManager()
/*     */   {
/*  33 */     return getRepo().getAuthenticationManager();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Firebase(String url)
/*     */   {
/* 165 */     this(Utilities.parseUrl(url));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Firebase(Repo repo, Path path)
/*     */   {
/* 174 */     super(repo, path);
/*     */   }
/*     */   
/*     */   Firebase(String url, Config config) {
/* 178 */     this(Utilities.parseUrl(url), config);
/*     */   }
/*     */   
/*     */   private Firebase(ParsedUrl parsedUrl, Config config) {
/* 182 */     super(RepoManager.getRepo(config, parsedUrl.repoInfo), parsedUrl.path, QueryParams.DEFAULT_PARAMS, false);
/*     */   }
/*     */   
/*     */   private Firebase(ParsedUrl parsedUrl)
/*     */   {
/* 187 */     this(parsedUrl, getDefaultConfig());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Firebase child(String pathString)
/*     */   {
/* 196 */     if (pathString == null) {
/* 197 */       throw new NullPointerException("Can't pass null for argument 'pathString' in child()");
/*     */     }
/* 199 */     if (getPath().isEmpty())
/*     */     {
/* 201 */       Validation.validateRootPathString(pathString);
/*     */     } else {
/* 203 */       Validation.validatePathString(pathString);
/*     */     }
/* 205 */     Path childPath = getPath().child(new Path(pathString));
/* 206 */     return new Firebase(this.repo, childPath);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Firebase push()
/*     */   {
/* 217 */     String childNameStr = PushIdGenerator.generatePushChildName(this.repo.getServerTime());
/* 218 */     ChildKey childKey = ChildKey.fromString(childNameStr);
/* 219 */     return new Firebase(this.repo, getPath().child(childKey));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/* 248 */     setValueInternal(value, PriorityUtilities.parsePriority(null), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(Object value, Object priority)
/*     */   {
/* 278 */     setValueInternal(value, PriorityUtilities.parsePriority(priority), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(Object value, CompletionListener listener)
/*     */   {
/* 308 */     setValueInternal(value, PriorityUtilities.parsePriority(null), listener);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(Object value, Object priority, CompletionListener listener)
/*     */   {
/* 339 */     setValueInternal(value, PriorityUtilities.parsePriority(priority), listener);
/*     */   }
/*     */   
/*     */   private void setValueInternal(Object value, Node priority, final CompletionListener listener) {
/* 343 */     Validation.validateWritablePath(getPath());
/* 344 */     ValidationPath.validateWithObject(getPath(), value);
/*     */     try {
/* 346 */       Object bouncedValue = JsonHelpers.getMapper().convertValue(value, Object.class);
/* 347 */       Validation.validateWritableObject(bouncedValue);
/* 348 */       final Node node = NodeUtilities.NodeFromJSON(bouncedValue, priority);
/* 349 */       this.repo.scheduleNow(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 353 */           Firebase.this.repo.setValue(Firebase.this.getPath(), node, listener);
/*     */         }
/*     */       });
/*     */     } catch (IllegalArgumentException e) {
/* 357 */       throw new FirebaseException("Failed to parse to snapshot", e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPriority(Object priority)
/*     */   {
/* 387 */     setPriorityInternal(PriorityUtilities.parsePriority(priority), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPriority(Object priority, CompletionListener listener)
/*     */   {
/* 416 */     setPriorityInternal(PriorityUtilities.parsePriority(priority), listener);
/*     */   }
/*     */   
/*     */   private void setPriorityInternal(final Node priority, final CompletionListener listener) {
/* 420 */     Validation.validateWritablePath(getPath());
/* 421 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 424 */         Firebase.this.repo.setValue(Firebase.this.getPath().child(ChildKey.getPriorityKey()), priority, listener);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateChildren(Map<String, Object> children)
/*     */   {
/* 436 */     updateChildren(children, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateChildren(final Map<String, Object> children, final CompletionListener listener)
/*     */   {
/* 445 */     if (children == null) {
/* 446 */       throw new NullPointerException("Can't pass null for argument 'children' in updateChildren()");
/*     */     }
/* 448 */     ValidationPath.validateWithObject(getPath(), children);
/* 449 */     Map<ChildKey, Node> parsedUpdate = new HashMap(children.size());
/* 450 */     for (Map.Entry<String, Object> entry : children.entrySet()) {
/* 451 */       Validation.validateWritableObject(entry.getValue());
/* 452 */       parsedUpdate.put(ChildKey.fromString((String)entry.getKey()), NodeUtilities.NodeFromJSON(entry.getValue()));
/*     */     }
/* 454 */     final CompoundWrite merge = CompoundWrite.fromChildMerge(parsedUpdate);
/*     */     
/* 456 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 459 */         Firebase.this.repo.updateChildren(Firebase.this.getPath(), merge, listener, children);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeValue()
/*     */   {
/* 470 */     setValue(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeValue(CompletionListener listener)
/*     */   {
/* 478 */     setValue(null, listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OnDisconnect onDisconnect()
/*     */   {
/* 488 */     Validation.validateWritablePath(getPath());
/* 489 */     return new OnDisconnect(this.repo, getPath());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void auth(String credential, AuthListener listener)
/*     */   {
/* 502 */     if (listener == null) {
/* 503 */       throw new NullPointerException("Can't pass null for argument 'listener' in auth()");
/*     */     }
/* 505 */     if (credential == null) {
/* 506 */       throw new NullPointerException("Can't pass null for argument 'credential' in auth()");
/*     */     }
/* 508 */     getAuthenticationManager().authWithFirebaseToken(credential, listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unauth()
/*     */   {
/* 516 */     getAuthenticationManager().unauth();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void unauth(CompletionListener listener)
/*     */   {
/* 527 */     if (listener == null) {
/* 528 */       throw new NullPointerException("Can't pass null for argument 'listener' in unauth()");
/*     */     }
/* 530 */     getAuthenticationManager().unauth(listener);
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
/*     */   public AuthStateListener addAuthStateListener(AuthStateListener listener)
/*     */   {
/* 552 */     if (listener == null) {
/* 553 */       throw new NullPointerException("Can't pass null for argument 'listener' in addAuthStateListener()");
/*     */     }
/* 555 */     getAuthenticationManager().addAuthStateListener(listener);
/* 556 */     return listener;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAuthStateListener(AuthStateListener listener)
/*     */   {
/* 565 */     if (listener == null) {
/* 566 */       throw new NullPointerException("Can't pass null for argument 'listener' in removeAuthStateListener()");
/*     */     }
/* 568 */     getAuthenticationManager().removeAuthStateListener(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AuthData getAuth()
/*     */   {
/* 579 */     return getAuthenticationManager().getAuth();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void authAnonymously(AuthResultHandler handler)
/*     */   {
/* 589 */     getAuthenticationManager().authAnonymously(handler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void authWithPassword(String email, String password, AuthResultHandler handler)
/*     */   {
/* 601 */     if (email == null) {
/* 602 */       throw new NullPointerException("Can't pass null for argument 'email' in authWithPassword()");
/*     */     }
/* 604 */     if (password == null) {
/* 605 */       throw new NullPointerException("Can't pass null for argument 'password' in authWithPassword()");
/*     */     }
/* 607 */     getAuthenticationManager().authWithPassword(email, password, handler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void authWithCustomToken(String token, AuthResultHandler handler)
/*     */   {
/* 618 */     if (token == null) {
/* 619 */       throw new NullPointerException("Can't pass null for argument 'token' in authWithCustomToken()");
/*     */     }
/* 621 */     getAuthenticationManager().authWithCustomToken(token, handler);
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
/*     */   public void authWithOAuthToken(String provider, String token, AuthResultHandler handler)
/*     */   {
/* 639 */     if (provider == null) {
/* 640 */       throw new NullPointerException("Can't pass null for argument 'provider' in authWithOAuthToken()");
/*     */     }
/* 642 */     if (token == null) {
/* 643 */       throw new NullPointerException("Can't pass null for argument 'token' in authWithOAuthToken()");
/*     */     }
/* 645 */     getAuthenticationManager().authWithOAuthToken(provider, token, handler);
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
/*     */   public void authWithOAuthToken(String provider, Map<String, String> options, AuthResultHandler handler)
/*     */   {
/* 663 */     if (provider == null) {
/* 664 */       throw new NullPointerException("Can't pass null for argument 'provider' in authWithOAuthToken()");
/*     */     }
/* 666 */     if (options == null) {
/* 667 */       throw new NullPointerException("Can't pass null for argument 'options' in authWithOAuthToken()");
/*     */     }
/* 669 */     getAuthenticationManager().authWithOAuthToken(provider, options, handler);
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
/*     */   public void createUser(String email, String password, ResultHandler handler)
/*     */   {
/* 683 */     if (email == null) {
/* 684 */       throw new NullPointerException("Can't pass null for argument 'email' in createUser()");
/*     */     }
/* 686 */     if (password == null) {
/* 687 */       throw new NullPointerException("Can't pass null for argument 'password' in createUser()");
/*     */     }
/* 689 */     getAuthenticationManager().createUser(email, password, handler);
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
/*     */   public void createUser(String email, String password, ValueResultHandler<Map<String, Object>> handler)
/*     */   {
/* 704 */     if (email == null) {
/* 705 */       throw new NullPointerException("Can't pass null for argument 'email' in createUser()");
/*     */     }
/* 707 */     if (password == null) {
/* 708 */       throw new NullPointerException("Can't pass null for argument 'password' in createUser()");
/*     */     }
/* 710 */     getAuthenticationManager().createUser(email, password, handler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeUser(String email, String password, ResultHandler handler)
/*     */   {
/* 722 */     if (email == null) {
/* 723 */       throw new NullPointerException("Can't pass null for argument 'email' in removeUser()");
/*     */     }
/* 725 */     if (password == null) {
/* 726 */       throw new NullPointerException("Can't pass null for argument 'password' in removeUser()");
/*     */     }
/* 728 */     getAuthenticationManager().removeUser(email, password, handler);
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
/*     */   public void changePassword(String email, String oldPassword, String newPassword, ResultHandler handler)
/*     */   {
/* 741 */     if (email == null) {
/* 742 */       throw new NullPointerException("Can't pass null for argument 'email' in changePassword()");
/*     */     }
/* 744 */     if (oldPassword == null) {
/* 745 */       throw new NullPointerException("Can't pass null for argument 'oldPassword' in changePassword()");
/*     */     }
/* 747 */     if (newPassword == null) {
/* 748 */       throw new NullPointerException("Can't pass null for argument 'newPassword' in changePassword()");
/*     */     }
/* 750 */     getAuthenticationManager().changePassword(email, oldPassword, newPassword, handler);
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
/*     */   public void changeEmail(String oldEmail, String password, String newEmail, ResultHandler handler)
/*     */   {
/* 763 */     if (oldEmail == null) {
/* 764 */       throw new NullPointerException("Can't pass null for argument 'oldEmail' in changeEmail()");
/*     */     }
/* 766 */     if (password == null) {
/* 767 */       throw new NullPointerException("Can't pass null for argument 'password' in changeEmail()");
/*     */     }
/* 769 */     if (newEmail == null) {
/* 770 */       throw new NullPointerException("Can't pass null for argument 'newEmail' in changeEmail()");
/*     */     }
/* 772 */     getAuthenticationManager().changeEmail(oldEmail, password, newEmail, handler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetPassword(String email, ResultHandler handler)
/*     */   {
/* 783 */     if (email == null) {
/* 784 */       throw new NullPointerException("Can't pass null for argument 'email' in resetPassword()");
/*     */     }
/* 786 */     getAuthenticationManager().resetPassword(email, handler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void runTransaction(Transaction.Handler handler)
/*     */   {
/* 798 */     runTransaction(handler, true);
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
/*     */   public void runTransaction(final Transaction.Handler handler, final boolean fireLocalEvents)
/*     */   {
/* 811 */     if (handler == null) {
/* 812 */       throw new NullPointerException("Can't pass null for argument 'handler' in runTransaction()");
/*     */     }
/* 814 */     Validation.validateWritablePath(getPath());
/* 815 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 818 */         Firebase.this.repo.startTransaction(Firebase.this.getPath(), handler, fireLocalEvents);
/*     */       }
/*     */     });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void goOffline()
/*     */   {
/* 847 */     goOffline(getDefaultConfig());
/*     */   }
/*     */   
/*     */   static void goOffline(Config config) {
/* 851 */     RepoManager.interrupt(config);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void goOnline()
/*     */   {
/* 860 */     goOnline(getDefaultConfig());
/*     */   }
/*     */   
/*     */   static void goOnline(Config config) {
/* 864 */     RepoManager.resume(config);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FirebaseApp getApp()
/*     */   {
/* 876 */     return this.repo.getFirebaseApp();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 884 */     Firebase parent = getParent();
/* 885 */     if (parent == null) {
/* 886 */       return this.repo.toString();
/*     */     }
/*     */     try {
/* 889 */       return parent.toString() + "/" + URLEncoder.encode(getKey(), "UTF-8").replace("+", "%20");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 892 */       throw new FirebaseException("Failed to URLEncode key: " + getKey(), e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Firebase getParent()
/*     */   {
/* 901 */     Path parentPath = getPath().getParent();
/* 902 */     if (parentPath != null) {
/* 903 */       return new Firebase(this.repo, parentPath);
/*     */     }
/* 905 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Firebase getRoot()
/*     */   {
/* 913 */     return new Firebase(this.repo, new Path(""));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getKey()
/*     */   {
/* 920 */     if (getPath().isEmpty()) {
/* 921 */       return null;
/*     */     }
/* 923 */     return getPath().getBack().asString();
/*     */   }
/*     */   
/*     */   public boolean equals(Object other)
/*     */   {
/* 928 */     return ((other instanceof Firebase)) && (toString().equals(other.toString()));
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 933 */     return toString().hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String getSdkVersion()
/*     */   {
/* 940 */     return "2.3.1";
/*     */   }
/*     */   
/*     */   void setHijackHash(final boolean hijackHash) {
/* 944 */     this.repo.scheduleNow(new Runnable()
/*     */     {
/*     */       public void run() {
/* 947 */         Firebase.this.repo.setHijackHash(hijackHash);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized Config getDefaultConfig()
/*     */   {
/* 957 */     if (defaultConfig == null) {
/* 958 */       defaultConfig = new Config();
/*     */     }
/* 960 */     return defaultConfig;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized void setDefaultConfig(Config config)
/*     */   {
/* 969 */     if ((defaultConfig != null) && (defaultConfig.isFrozen())) {
/* 970 */       throw new FirebaseException("Modifications to Config objects must occur before they are in use");
/*     */     }
/* 972 */     defaultConfig = config;
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
/*     */   public static void setAndroidContext(android.content.Context context)
/*     */   {
/* 986 */     if (context == null) {
/* 987 */       throw new NullPointerException("Can't pass null for argument 'context' in setAndroidContext()");
/*     */     }
/* 989 */     com.firebase.client.core.Context.setAndroidContext(context);
/*     */   }
/*     */   
/*     */   public static abstract interface ValueResultHandler<T>
/*     */   {
/*     */     public abstract void onSuccess(T paramT);
/*     */     
/*     */     public abstract void onError(FirebaseError paramFirebaseError);
/*     */   }
/*     */   
/*     */   public static abstract interface ResultHandler
/*     */   {
/*     */     public abstract void onSuccess();
/*     */     
/*     */     public abstract void onError(FirebaseError paramFirebaseError);
/*     */   }
/*     */   
/*     */   public static abstract interface AuthResultHandler
/*     */   {
/*     */     public abstract void onAuthenticated(AuthData paramAuthData);
/*     */     
/*     */     public abstract void onAuthenticationError(FirebaseError paramFirebaseError);
/*     */   }
/*     */   
/*     */   public static abstract interface AuthStateListener
/*     */   {
/*     */     public abstract void onAuthStateChanged(AuthData paramAuthData);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static abstract interface AuthListener
/*     */   {
/*     */     public abstract void onAuthError(FirebaseError paramFirebaseError);
/*     */     
/*     */     public abstract void onAuthSuccess(Object paramObject);
/*     */     
/*     */     public abstract void onAuthRevoked(FirebaseError paramFirebaseError);
/*     */   }
/*     */   
/*     */   public static abstract interface CompletionListener
/*     */   {
/*     */     public abstract void onComplete(FirebaseError paramFirebaseError, Firebase paramFirebase);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/Firebase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */