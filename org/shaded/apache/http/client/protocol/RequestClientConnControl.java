/*    */ package org.shaded.apache.http.client.protocol;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpException;
/*    */ import org.shaded.apache.http.HttpRequest;
/*    */ import org.shaded.apache.http.HttpRequestInterceptor;
/*    */ import org.shaded.apache.http.RequestLine;
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.conn.ManagedClientConnection;
/*    */ import org.shaded.apache.http.conn.routing.HttpRoute;
/*    */ import org.shaded.apache.http.protocol.HttpContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class RequestClientConnControl
/*    */   implements HttpRequestInterceptor
/*    */ {
/*    */   private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";
/*    */   
/*    */   public void process(HttpRequest request, HttpContext context)
/*    */     throws HttpException, IOException
/*    */   {
/* 61 */     if (request == null) {
/* 62 */       throw new IllegalArgumentException("HTTP request may not be null");
/*    */     }
/*    */     
/* 65 */     String method = request.getRequestLine().getMethod();
/* 66 */     if (method.equalsIgnoreCase("CONNECT")) {
/* 67 */       request.setHeader("Proxy-Connection", "Keep-Alive");
/* 68 */       return;
/*    */     }
/*    */     
/*    */ 
/* 72 */     ManagedClientConnection conn = (ManagedClientConnection)context.getAttribute("http.connection");
/*    */     
/* 74 */     if (conn == null) {
/* 75 */       throw new IllegalStateException("Client connection not specified in HTTP context");
/*    */     }
/*    */     
/* 78 */     HttpRoute route = conn.getRoute();
/*    */     
/* 80 */     if (((route.getHopCount() == 1) || (route.isTunnelled())) && 
/* 81 */       (!request.containsHeader("Connection"))) {
/* 82 */       request.addHeader("Connection", "Keep-Alive");
/*    */     }
/*    */     
/* 85 */     if ((route.getHopCount() == 2) && (!route.isTunnelled()) && 
/* 86 */       (!request.containsHeader("Proxy-Connection"))) {
/* 87 */       request.addHeader("Proxy-Connection", "Keep-Alive");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/protocol/RequestClientConnControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */