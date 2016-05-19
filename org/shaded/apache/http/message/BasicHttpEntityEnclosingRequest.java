/*    */ package org.shaded.apache.http.message;
/*    */ 
/*    */ import org.shaded.apache.http.Header;
/*    */ import org.shaded.apache.http.HttpEntity;
/*    */ import org.shaded.apache.http.HttpEntityEnclosingRequest;
/*    */ import org.shaded.apache.http.ProtocolVersion;
/*    */ import org.shaded.apache.http.RequestLine;
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
/*    */ public class BasicHttpEntityEnclosingRequest
/*    */   extends BasicHttpRequest
/*    */   implements HttpEntityEnclosingRequest
/*    */ {
/*    */   private HttpEntity entity;
/*    */   
/*    */   public BasicHttpEntityEnclosingRequest(String method, String uri)
/*    */   {
/* 54 */     super(method, uri);
/*    */   }
/*    */   
/*    */   public BasicHttpEntityEnclosingRequest(String method, String uri, ProtocolVersion ver)
/*    */   {
/* 59 */     super(method, uri, ver);
/*    */   }
/*    */   
/*    */   public BasicHttpEntityEnclosingRequest(RequestLine requestline) {
/* 63 */     super(requestline);
/*    */   }
/*    */   
/*    */   public HttpEntity getEntity() {
/* 67 */     return this.entity;
/*    */   }
/*    */   
/*    */   public void setEntity(HttpEntity entity) {
/* 71 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public boolean expectContinue() {
/* 75 */     Header expect = getFirstHeader("Expect");
/* 76 */     return (expect != null) && ("100-Continue".equalsIgnoreCase(expect.getValue()));
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHttpEntityEnclosingRequest.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */