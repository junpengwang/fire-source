/*    */ package com.firebase.client.core;
/*    */ 
/*    */ import java.net.URI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RepoInfo
/*    */ {
/*    */   private static final String VERSION_PARAM = "v";
/*    */   public String host;
/*    */   public boolean secure;
/*    */   public String namespace;
/*    */   public String internalHost;
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "http" + (this.secure ? "s" : "") + "://" + this.host;
/*    */   }
/*    */   
/*    */   public String toDebugString() {
/* 25 */     return "(host=" + this.host + ", secure=" + this.secure + ", ns=" + this.namespace + " internal=" + this.internalHost + ")";
/*    */   }
/*    */   
/*    */   public URI getConnectionURL() {
/* 29 */     String scheme = this.secure ? "wss" : "ws";
/* 30 */     String url = scheme + "://" + this.internalHost + "/.ws?ns=" + this.namespace + "&" + "v" + "=" + "5";
/*    */     
/* 32 */     return URI.create(url);
/*    */   }
/*    */   
/*    */   public boolean isCacheableHost() {
/* 36 */     return this.internalHost.startsWith("s-");
/*    */   }
/*    */   
/*    */   public boolean isSecure() {
/* 40 */     return this.secure;
/*    */   }
/*    */   
/*    */   public boolean isDemoHost() {
/* 44 */     return this.host.contains(".firebaseio-demo.com");
/*    */   }
/*    */   
/*    */   public boolean isCustomHost() {
/* 48 */     return (!this.host.contains(".firebaseio.com")) && (!this.host.contains(".firebaseio-demo.com"));
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/RepoInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */