/*    */ package org.shaded.apache.http.conn.routing;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import org.shaded.apache.http.HttpHost;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface RouteInfo
/*    */ {
/*    */   public abstract HttpHost getTargetHost();
/*    */   
/*    */   public abstract InetAddress getLocalAddress();
/*    */   
/*    */   public abstract int getHopCount();
/*    */   
/*    */   public abstract HttpHost getHopTarget(int paramInt);
/*    */   
/*    */   public abstract HttpHost getProxyHost();
/*    */   
/*    */   public abstract TunnelType getTunnelType();
/*    */   
/*    */   public abstract boolean isTunnelled();
/*    */   
/*    */   public abstract LayerType getLayerType();
/*    */   
/*    */   public abstract boolean isLayered();
/*    */   
/*    */   public abstract boolean isSecure();
/*    */   
/*    */   public static enum TunnelType
/*    */   {
/* 49 */     PLAIN,  TUNNELLED;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     private TunnelType() {}
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static enum LayerType
/*    */   {
/* 64 */     PLAIN,  LAYERED;
/*    */     
/*    */     private LayerType() {}
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/routing/RouteInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */