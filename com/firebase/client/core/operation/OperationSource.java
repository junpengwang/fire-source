/*    */ package com.firebase.client.core.operation;
/*    */ 
/*    */ import com.firebase.client.core.view.QueryParams;
/*    */ 
/*    */ public class OperationSource
/*    */ {
/*    */   private static enum Source {
/*  8 */     User,  Server;
/*    */     
/*    */     private Source() {} }
/* 11 */   public static final OperationSource USER = new OperationSource(Source.User, null, false);
/* 12 */   public static final OperationSource SERVER = new OperationSource(Source.Server, null, false);
/*    */   private final Source source;
/*    */   
/* 15 */   public static OperationSource forServerTaggedQuery(QueryParams queryParams) { return new OperationSource(Source.Server, queryParams, true); }
/*    */   
/*    */ 
/*    */   private final QueryParams queryParams;
/*    */   
/*    */   private final boolean tagged;
/*    */   public OperationSource(Source source, QueryParams queryParams, boolean tagged)
/*    */   {
/* 23 */     this.source = source;
/* 24 */     this.queryParams = queryParams;
/* 25 */     this.tagged = tagged;
/* 26 */     assert ((!tagged) || (isFromServer()));
/*    */   }
/*    */   
/*    */   public boolean isFromUser() {
/* 30 */     return this.source == Source.User;
/*    */   }
/*    */   
/*    */   public boolean isFromServer() {
/* 34 */     return this.source == Source.Server;
/*    */   }
/*    */   
/*    */   public boolean isTagged() {
/* 38 */     return this.tagged;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 43 */     return "OperationSource{source=" + this.source + ", queryParams=" + this.queryParams + ", tagged=" + this.tagged + '}';
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public QueryParams getQueryParams()
/*    */   {
/* 51 */     return this.queryParams;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/operation/OperationSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */