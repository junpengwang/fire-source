/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.params.AbstractHttpParams;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotThreadSafe
/*     */ public class ClientParamsStack
/*     */   extends AbstractHttpParams
/*     */ {
/*     */   protected final HttpParams applicationParams;
/*     */   protected final HttpParams clientParams;
/*     */   protected final HttpParams requestParams;
/*     */   protected final HttpParams overrideParams;
/*     */   
/*     */   public ClientParamsStack(HttpParams aparams, HttpParams cparams, HttpParams rparams, HttpParams oparams)
/*     */   {
/*  99 */     this.applicationParams = aparams;
/* 100 */     this.clientParams = cparams;
/* 101 */     this.requestParams = rparams;
/* 102 */     this.overrideParams = oparams;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ClientParamsStack(ClientParamsStack stack)
/*     */   {
/* 114 */     this(stack.getApplicationParams(), stack.getClientParams(), stack.getRequestParams(), stack.getOverrideParams());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ClientParamsStack(ClientParamsStack stack, HttpParams aparams, HttpParams cparams, HttpParams rparams, HttpParams oparams)
/*     */   {
/* 137 */     this(aparams != null ? aparams : stack.getApplicationParams(), cparams != null ? cparams : stack.getClientParams(), rparams != null ? rparams : stack.getRequestParams(), oparams != null ? oparams : stack.getOverrideParams());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final HttpParams getApplicationParams()
/*     */   {
/* 150 */     return this.applicationParams;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final HttpParams getClientParams()
/*     */   {
/* 159 */     return this.clientParams;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final HttpParams getRequestParams()
/*     */   {
/* 168 */     return this.requestParams;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final HttpParams getOverrideParams()
/*     */   {
/* 177 */     return this.overrideParams;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getParameter(String name)
/*     */   {
/* 191 */     if (name == null) {
/* 192 */       throw new IllegalArgumentException("Parameter name must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 196 */     Object result = null;
/*     */     
/* 198 */     if (this.overrideParams != null) {
/* 199 */       result = this.overrideParams.getParameter(name);
/*     */     }
/* 201 */     if ((result == null) && (this.requestParams != null)) {
/* 202 */       result = this.requestParams.getParameter(name);
/*     */     }
/* 204 */     if ((result == null) && (this.clientParams != null)) {
/* 205 */       result = this.clientParams.getParameter(name);
/*     */     }
/* 207 */     if ((result == null) && (this.applicationParams != null)) {
/* 208 */       result = this.applicationParams.getParameter(name);
/*     */     }
/* 210 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpParams setParameter(String name, Object value)
/*     */     throws UnsupportedOperationException
/*     */   {
/* 229 */     throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean removeParameter(String name)
/*     */   {
/* 247 */     throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpParams copy()
/*     */   {
/* 266 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/ClientParamsStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */