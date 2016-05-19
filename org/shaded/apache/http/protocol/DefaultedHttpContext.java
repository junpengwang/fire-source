/*    */ package org.shaded.apache.http.protocol;
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
/*    */ public final class DefaultedHttpContext
/*    */   implements HttpContext
/*    */ {
/*    */   private final HttpContext local;
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
/*    */   private final HttpContext defaults;
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
/*    */   public DefaultedHttpContext(HttpContext local, HttpContext defaults)
/*    */   {
/* 52 */     if (local == null) {
/* 53 */       throw new IllegalArgumentException("HTTP context may not be null");
/*    */     }
/* 55 */     this.local = local;
/* 56 */     this.defaults = defaults;
/*    */   }
/*    */   
/*    */   public Object getAttribute(String id) {
/* 60 */     Object obj = this.local.getAttribute(id);
/* 61 */     if (obj == null) {
/* 62 */       return this.defaults.getAttribute(id);
/*    */     }
/* 64 */     return obj;
/*    */   }
/*    */   
/*    */   public Object removeAttribute(String id)
/*    */   {
/* 69 */     return this.local.removeAttribute(id);
/*    */   }
/*    */   
/*    */   public void setAttribute(String id, Object obj) {
/* 73 */     this.local.setAttribute(id, obj);
/*    */   }
/*    */   
/*    */   public HttpContext getDefaults() {
/* 77 */     return this.defaults;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/DefaultedHttpContext.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */