/*    */ package org.shaded.apache.http.conn.params;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import org.shaded.apache.http.HttpHost;
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*    */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*    */ import org.shaded.apache.http.params.HttpAbstractParamBean;
/*    */ import org.shaded.apache.http.params.HttpParams;
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
/*    */ @NotThreadSafe
/*    */ public class ConnRouteParamBean
/*    */   extends HttpAbstractParamBean
/*    */ {
/*    */   public ConnRouteParamBean(HttpParams params)
/*    */   {
/* 50 */     super(params);
/*    */   }
/*    */   
/*    */   public void setDefaultProxy(HttpHost defaultProxy)
/*    */   {
/* 55 */     this.params.setParameter("http.route.default-proxy", defaultProxy);
/*    */   }
/*    */   
/*    */   public void setLocalAddress(InetAddress address)
/*    */   {
/* 60 */     this.params.setParameter("http.route.local-address", address);
/*    */   }
/*    */   
/*    */   public void setForcedRoute(HttpRoute route)
/*    */   {
/* 65 */     this.params.setParameter("http.route.forced-route", route);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/params/ConnRouteParamBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */