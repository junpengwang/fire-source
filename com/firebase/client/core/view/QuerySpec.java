/*    */ package com.firebase.client.core.view;
/*    */ 
/*    */ import com.firebase.client.core.Path;
/*    */ 
/*    */ public class QuerySpec
/*    */ {
/*    */   private final Path path;
/*    */   private final QueryParams params;
/*    */   
/*    */   public static QuerySpec defaultQueryAtPath(Path path) {
/* 11 */     return new QuerySpec(path, QueryParams.DEFAULT_PARAMS);
/*    */   }
/*    */   
/*    */   public QuerySpec(Path path, QueryParams params) {
/* 15 */     this.path = path;
/* 16 */     this.params = params;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Path getPath()
/*    */   {
/* 23 */     return this.path;
/*    */   }
/*    */   
/*    */   public QueryParams getParams() {
/* 27 */     return this.params;
/*    */   }
/*    */   
/*    */   public static QuerySpec fromPathAndQueryObject(Path path, java.util.Map<String, Object> map) {
/* 31 */     QueryParams params = QueryParams.fromQueryObject(map);
/* 32 */     return new QuerySpec(path, params);
/*    */   }
/*    */   
/*    */   public com.firebase.client.snapshot.Index getIndex()
/*    */   {
/* 37 */     return this.params.getIndex();
/*    */   }
/*    */   
/*    */   public boolean isDefault() {
/* 41 */     return this.params.isDefault();
/*    */   }
/*    */   
/*    */   public boolean loadsAllData() {
/* 45 */     return this.params.loadsAllData();
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 50 */     return this.path + ":" + this.params;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 55 */     if (this == o) return true;
/* 56 */     if ((o == null) || (getClass() != o.getClass())) return false;
/* 57 */     QuerySpec that = (QuerySpec)o;
/*    */     
/* 59 */     if (!this.path.equals(that.path)) return false;
/* 60 */     if (!this.params.equals(that.params)) { return false;
/*    */     }
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 67 */     int result = this.path.hashCode();
/* 68 */     result = 31 * result + this.params.hashCode();
/* 69 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/view/QuerySpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */