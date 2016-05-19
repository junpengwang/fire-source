/*    */ package com.firebase.client;
/*    */ 
/*    */ import com.firebase.client.core.Repo;
/*    */ import com.firebase.client.core.RepoManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FirebaseApp
/*    */ {
/*    */   private final Repo repo;
/*    */   
/*    */   protected FirebaseApp(Repo repo)
/*    */   {
/* 24 */     this.repo = repo;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void purgeOutstandingWrites()
/*    */   {
/* 37 */     this.repo.scheduleNow(new Runnable()
/*    */     {
/*    */       public void run() {
/* 40 */         FirebaseApp.this.repo.purgeOutstandingWrites();
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void goOnline()
/*    */   {
/* 49 */     RepoManager.resume(this.repo);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void goOffline()
/*    */   {
/* 56 */     RepoManager.interrupt(this.repo);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/FirebaseApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */