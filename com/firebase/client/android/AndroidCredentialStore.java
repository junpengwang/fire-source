/*    */ package com.firebase.client.android;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.SharedPreferences;
/*    */ import android.content.SharedPreferences.Editor;
/*    */ 
/*    */ public class AndroidCredentialStore implements com.firebase.client.CredentialStore
/*    */ {
/*    */   private static final String ANDROID_SHARED_PREFERENCE_NAME = "com.firebase.authentication.credentials";
/*    */   private final SharedPreferences sharedPreferences;
/*    */   
/*    */   public AndroidCredentialStore(Context context)
/*    */   {
/* 14 */     this.sharedPreferences = context.getSharedPreferences("com.firebase.authentication.credentials", 0);
/*    */   }
/*    */   
/*    */   private String buildKey(String firebaseId, String sessionId) {
/* 18 */     return firebaseId + "/" + sessionId;
/*    */   }
/*    */   
/*    */   public String loadCredential(String firebaseId, String sessionId)
/*    */   {
/* 23 */     return this.sharedPreferences.getString(buildKey(firebaseId, sessionId), null);
/*    */   }
/*    */   
/*    */   public boolean storeCredential(String firebaseId, String sessionId, String credential)
/*    */   {
/* 28 */     SharedPreferences.Editor editor = this.sharedPreferences.edit();
/* 29 */     editor.putString(buildKey(firebaseId, sessionId), credential);
/* 30 */     return editor.commit();
/*    */   }
/*    */   
/*    */   public boolean clearCredential(String firebaseId, String sessionId)
/*    */   {
/* 35 */     SharedPreferences.Editor editor = this.sharedPreferences.edit();
/* 36 */     editor.remove(buildKey(firebaseId, sessionId));
/* 37 */     return editor.commit();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/android/AndroidCredentialStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */