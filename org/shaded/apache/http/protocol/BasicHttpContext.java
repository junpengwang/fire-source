/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ public class BasicHttpContext
/*    */   implements HttpContext
/*    */ {
/*    */   private final HttpContext parentContext;
/* 51 */   private Map map = null;
/*    */   
/*    */   public BasicHttpContext() {
/* 54 */     this(null);
/*    */   }
/*    */   
/*    */   public BasicHttpContext(HttpContext parentContext)
/*    */   {
/* 59 */     this.parentContext = parentContext;
/*    */   }
/*    */   
/*    */   public Object getAttribute(String id) {
/* 63 */     if (id == null) {
/* 64 */       throw new IllegalArgumentException("Id may not be null");
/*    */     }
/* 66 */     Object obj = null;
/* 67 */     if (this.map != null) {
/* 68 */       obj = this.map.get(id);
/*    */     }
/* 70 */     if ((obj == null) && (this.parentContext != null)) {
/* 71 */       obj = this.parentContext.getAttribute(id);
/*    */     }
/* 73 */     return obj;
/*    */   }
/*    */   
/*    */   public void setAttribute(String id, Object obj) {
/* 77 */     if (id == null) {
/* 78 */       throw new IllegalArgumentException("Id may not be null");
/*    */     }
/* 80 */     if (this.map == null) {
/* 81 */       this.map = new HashMap();
/*    */     }
/* 83 */     this.map.put(id, obj);
/*    */   }
/*    */   
/*    */   public Object removeAttribute(String id) {
/* 87 */     if (id == null) {
/* 88 */       throw new IllegalArgumentException("Id may not be null");
/*    */     }
/* 90 */     if (this.map != null) {
/* 91 */       return this.map.remove(id);
/*    */     }
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/BasicHttpContext.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */