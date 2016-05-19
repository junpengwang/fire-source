/*    */ package com.firebase.client.utilities.tuple;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathAndId
/*    */ {
/*    */   private Path path;
/*    */   private long id;
/*    */   
/*    */   public PathAndId(Path path, long id)
/*    */   {
/* 16 */     this.path = path;
/* 17 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Path getPath() {
/* 21 */     return this.path;
/*    */   }
/*    */   
/*    */   public long getId() {
/* 25 */     return this.id;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/tuple/PathAndId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */