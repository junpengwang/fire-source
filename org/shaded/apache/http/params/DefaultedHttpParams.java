/*    */ package org.shaded.apache.http.params;
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
/*    */ public final class DefaultedHttpParams
/*    */   extends AbstractHttpParams
/*    */ {
/*    */   private final HttpParams local;
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
/*    */   private final HttpParams defaults;
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
/*    */   public DefaultedHttpParams(HttpParams local, HttpParams defaults)
/*    */   {
/* 54 */     if (local == null) {
/* 55 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*    */     }
/* 57 */     this.local = local;
/* 58 */     this.defaults = defaults;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public HttpParams copy()
/*    */   {
/* 65 */     HttpParams clone = this.local.copy();
/* 66 */     return new DefaultedHttpParams(clone, this.defaults);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object getParameter(String name)
/*    */   {
/* 75 */     Object obj = this.local.getParameter(name);
/* 76 */     if ((obj == null) && (this.defaults != null)) {
/* 77 */       obj = this.defaults.getParameter(name);
/*    */     }
/* 79 */     return obj;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean removeParameter(String name)
/*    */   {
/* 87 */     return this.local.removeParameter(name);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public HttpParams setParameter(String name, Object value)
/*    */   {
/* 95 */     return this.local.setParameter(name, value);
/*    */   }
/*    */   
/*    */   public HttpParams getDefaults() {
/* 99 */     return this.defaults;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/params/DefaultedHttpParams.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */