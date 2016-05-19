/*    */ package org.shaded.apache.http.client.params;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.shaded.apache.http.Header;
/*    */ import org.shaded.apache.http.HttpHost;
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*    */ import org.shaded.apache.http.conn.ClientConnectionManagerFactory;
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
/*    */ public class ClientParamBean
/*    */   extends HttpAbstractParamBean
/*    */ {
/*    */   public ClientParamBean(HttpParams params)
/*    */   {
/* 51 */     super(params);
/*    */   }
/*    */   
/*    */   public void setConnectionManagerFactoryClassName(String factory) {
/* 55 */     this.params.setParameter("http.connection-manager.factory-class-name", factory);
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setConnectionManagerFactory(ClientConnectionManagerFactory factory) {
/* 60 */     this.params.setParameter("http.connection-manager.factory-object", factory);
/*    */   }
/*    */   
/*    */   public void setHandleRedirects(boolean handle) {
/* 64 */     this.params.setBooleanParameter("http.protocol.handle-redirects", handle);
/*    */   }
/*    */   
/*    */   public void setRejectRelativeRedirect(boolean reject) {
/* 68 */     this.params.setBooleanParameter("http.protocol.reject-relative-redirect", reject);
/*    */   }
/*    */   
/*    */   public void setMaxRedirects(int maxRedirects) {
/* 72 */     this.params.setIntParameter("http.protocol.max-redirects", maxRedirects);
/*    */   }
/*    */   
/*    */   public void setAllowCircularRedirects(boolean allow) {
/* 76 */     this.params.setBooleanParameter("http.protocol.allow-circular-redirects", allow);
/*    */   }
/*    */   
/*    */   public void setHandleAuthentication(boolean handle) {
/* 80 */     this.params.setBooleanParameter("http.protocol.handle-authentication", handle);
/*    */   }
/*    */   
/*    */   public void setCookiePolicy(String policy) {
/* 84 */     this.params.setParameter("http.protocol.cookie-policy", policy);
/*    */   }
/*    */   
/*    */   public void setVirtualHost(HttpHost host) {
/* 88 */     this.params.setParameter("http.virtual-host", host);
/*    */   }
/*    */   
/*    */   public void setDefaultHeaders(Collection<Header> headers) {
/* 92 */     this.params.setParameter("http.default-headers", headers);
/*    */   }
/*    */   
/*    */   public void setDefaultHost(HttpHost host) {
/* 96 */     this.params.setParameter("http.default-host", host);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/params/ClientParamBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */