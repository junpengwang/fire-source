/*    */ package com.firebase.client.authentication;
/*    */ 
/*    */ import com.firebase.client.core.Context;
/*    */ import com.firebase.client.utilities.LogWrapper;
/*    */ 
/*    */ public class NoopCredentialStore implements com.firebase.client.CredentialStore
/*    */ {
/*    */   private final LogWrapper logger;
/*    */   
/*    */   public NoopCredentialStore(Context context)
/*    */   {
/* 12 */     this.logger = context.getLogger("CredentialStore");
/*    */   }
/*    */   
/*    */ 
/*    */   public String loadCredential(String firebaseId, String sessionId)
/*    */   {
/* 18 */     return null;
/*    */   }
/*    */   
/*    */   public boolean storeCredential(String firebaseId, String sessionId, String credentialData)
/*    */   {
/* 23 */     this.logger.warn("Using no-op credential store. Not persisting credentials! If you want to persist authentication, please use a custom implementation of CredentialStore.");
/*    */     
/* 25 */     return true;
/*    */   }
/*    */   
/*    */   public boolean clearCredential(String firebaseId, String sessionId)
/*    */   {
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/authentication/NoopCredentialStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */