/*    */ package com.firebase.client.core;
/*    */ 
/*    */ import com.firebase.client.FirebaseException;
/*    */ import com.firebase.client.RunLoop;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RepoManager
/*    */ {
/* 15 */   private static final RepoManager instance = new RepoManager();
/*    */   
/*    */   public static Repo getRepo(Context ctx, RepoInfo info) throws FirebaseException
/*    */   {
/* 19 */     return instance.getLocalRepo(ctx, info);
/*    */   }
/*    */   
/*    */   public static void interrupt(Context ctx) {
/* 23 */     instance.interruptInternal(ctx);
/*    */   }
/*    */   
/*    */   public static void interrupt(Repo repo) {
/* 27 */     repo.scheduleNow(new Runnable()
/*    */     {
/*    */       public void run() {
/* 30 */         this.val$repo.interrupt();
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   public static void resume(Repo repo) {
/* 36 */     repo.scheduleNow(new Runnable()
/*    */     {
/*    */       public void run() {
/* 39 */         this.val$repo.resume();
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   public static void resume(Context ctx) {
/* 45 */     instance.resumeInternal(ctx);
/*    */   }
/*    */   
/* 48 */   private final Map<Context, Map<String, Repo>> repos = new HashMap();
/*    */   
/*    */ 
/*    */ 
/*    */   private Repo getLocalRepo(Context ctx, RepoInfo info)
/*    */     throws FirebaseException
/*    */   {
/* 55 */     ctx.freeze();
/* 56 */     String repoHash = "https://" + info.host + "/" + info.namespace;
/* 57 */     synchronized (this.repos) {
/* 58 */       if (!this.repos.containsKey(ctx)) {
/* 59 */         Map<String, Repo> innerMap = new HashMap();
/* 60 */         this.repos.put(ctx, innerMap);
/*    */       }
/* 62 */       Map<String, Repo> innerMap = (Map)this.repos.get(ctx);
/* 63 */       if (!innerMap.containsKey(repoHash)) {
/* 64 */         Repo repo = new Repo(info, ctx);
/* 65 */         innerMap.put(repoHash, repo);
/* 66 */         return repo;
/*    */       }
/* 68 */       return (Repo)innerMap.get(repoHash);
/*    */     }
/*    */   }
/*    */   
/*    */   private void interruptInternal(final Context ctx)
/*    */   {
/* 74 */     RunLoop runLoop = ctx.getRunLoop();
/* 75 */     if (runLoop != null) {
/* 76 */       runLoop.scheduleNow(new Runnable()
/*    */       {
/*    */         public void run() {
/* 79 */           synchronized (RepoManager.this.repos) {
/* 80 */             boolean allEmpty = true;
/* 81 */             if (RepoManager.this.repos.containsKey(ctx)) {
/* 82 */               for (Repo repo : ((Map)RepoManager.this.repos.get(ctx)).values()) {
/* 83 */                 repo.interrupt();
/* 84 */                 allEmpty = (allEmpty) && (!repo.hasListeners());
/*    */               }
/* 86 */               if (allEmpty) {
/* 87 */                 ctx.stop();
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       });
/*    */     }
/*    */   }
/*    */   
/*    */   private void resumeInternal(final Context ctx) {
/* 97 */     RunLoop runLoop = ctx.getRunLoop();
/* 98 */     if (runLoop != null) {
/* 99 */       runLoop.scheduleNow(new Runnable()
/*    */       {
/*    */         public void run() {
/* :2 */           synchronized (RepoManager.this.repos) {
/* :3 */             if (RepoManager.this.repos.containsKey(ctx)) {
/* :4 */               for (Repo repo : ((Map)RepoManager.this.repos.get(ctx)).values()) {
/* :5 */                 repo.resume();
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       });
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/RepoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */