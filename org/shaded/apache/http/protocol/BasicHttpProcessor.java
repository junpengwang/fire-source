/*     */ package org.shaded.apache.http.protocol;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.http.HttpException;
/*     */ import org.shaded.apache.http.HttpRequest;
/*     */ import org.shaded.apache.http.HttpRequestInterceptor;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.HttpResponseInterceptor;
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
/*     */ public final class BasicHttpProcessor
/*     */   implements HttpProcessor, HttpRequestInterceptorList, HttpResponseInterceptorList, Cloneable
/*     */ {
/*  59 */   protected final List requestInterceptors = new ArrayList();
/*  60 */   protected final List responseInterceptors = new ArrayList();
/*     */   
/*     */ 
/*     */   public void addRequestInterceptor(HttpRequestInterceptor itcp)
/*     */   {
/*  65 */     if (itcp == null) {
/*  66 */       return;
/*     */     }
/*  68 */     this.requestInterceptors.add(itcp);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addRequestInterceptor(HttpRequestInterceptor itcp, int index)
/*     */   {
/*  74 */     if (itcp == null) {
/*  75 */       return;
/*     */     }
/*     */     
/*  78 */     this.requestInterceptors.add(index, itcp);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addResponseInterceptor(HttpResponseInterceptor itcp, int index)
/*     */   {
/*  84 */     if (itcp == null) {
/*  85 */       return;
/*     */     }
/*     */     
/*  88 */     this.responseInterceptors.add(index, itcp);
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeRequestInterceptorByClass(Class clazz)
/*     */   {
/*  94 */     Iterator it = this.requestInterceptors.iterator();
/*  95 */     while (it.hasNext()) {
/*  96 */       Object request = it.next();
/*  97 */       if (request.getClass().equals(clazz)) {
/*  98 */         it.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeResponseInterceptorByClass(Class clazz)
/*     */   {
/* 105 */     Iterator it = this.responseInterceptors.iterator();
/* 106 */     while (it.hasNext()) {
/* 107 */       Object request = it.next();
/* 108 */       if (request.getClass().equals(clazz)) {
/* 109 */         it.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void addInterceptor(HttpRequestInterceptor interceptor)
/*     */   {
/* 121 */     addRequestInterceptor(interceptor);
/*     */   }
/*     */   
/*     */ 
/*     */   public final void addInterceptor(HttpRequestInterceptor interceptor, int index)
/*     */   {
/* 127 */     addRequestInterceptor(interceptor, index);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRequestInterceptorCount()
/*     */   {
/* 133 */     return this.requestInterceptors.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HttpRequestInterceptor getRequestInterceptor(int index)
/*     */   {
/* 140 */     if ((index < 0) || (index >= this.requestInterceptors.size())) {
/* 141 */       return null;
/*     */     }
/* 143 */     return (HttpRequestInterceptor)this.requestInterceptors.get(index);
/*     */   }
/*     */   
/*     */ 
/*     */   public void clearRequestInterceptors()
/*     */   {
/* 149 */     this.requestInterceptors.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addResponseInterceptor(HttpResponseInterceptor itcp)
/*     */   {
/* 156 */     if (itcp == null) {
/* 157 */       return;
/*     */     }
/* 159 */     this.responseInterceptors.add(itcp);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void addInterceptor(HttpResponseInterceptor interceptor)
/*     */   {
/* 169 */     addResponseInterceptor(interceptor);
/*     */   }
/*     */   
/*     */   public final void addInterceptor(HttpResponseInterceptor interceptor, int index)
/*     */   {
/* 174 */     addResponseInterceptor(interceptor, index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getResponseInterceptorCount()
/*     */   {
/* 181 */     return this.responseInterceptors.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HttpResponseInterceptor getResponseInterceptor(int index)
/*     */   {
/* 188 */     if ((index < 0) || (index >= this.responseInterceptors.size())) {
/* 189 */       return null;
/*     */     }
/* 191 */     return (HttpResponseInterceptor)this.responseInterceptors.get(index);
/*     */   }
/*     */   
/*     */ 
/*     */   public void clearResponseInterceptors()
/*     */   {
/* 197 */     this.responseInterceptors.clear();
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
/*     */   public void setInterceptors(List list)
/*     */   {
/* 219 */     if (list == null) {
/* 220 */       throw new IllegalArgumentException("List must not be null.");
/*     */     }
/* 222 */     this.requestInterceptors.clear();
/* 223 */     this.responseInterceptors.clear();
/* 224 */     for (int i = 0; i < list.size(); i++) {
/* 225 */       Object obj = list.get(i);
/* 226 */       if ((obj instanceof HttpRequestInterceptor)) {
/* 227 */         addInterceptor((HttpRequestInterceptor)obj);
/*     */       }
/* 229 */       if ((obj instanceof HttpResponseInterceptor)) {
/* 230 */         addInterceptor((HttpResponseInterceptor)obj);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearInterceptors()
/*     */   {
/* 239 */     clearRequestInterceptors();
/* 240 */     clearResponseInterceptors();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void process(HttpRequest request, HttpContext context)
/*     */     throws IOException, HttpException
/*     */   {
/* 248 */     for (int i = 0; i < this.requestInterceptors.size(); i++) {
/* 249 */       HttpRequestInterceptor interceptor = (HttpRequestInterceptor)this.requestInterceptors.get(i);
/*     */       
/* 251 */       interceptor.process(request, context);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void process(HttpResponse response, HttpContext context)
/*     */     throws IOException, HttpException
/*     */   {
/* 260 */     for (int i = 0; i < this.responseInterceptors.size(); i++) {
/* 261 */       HttpResponseInterceptor interceptor = (HttpResponseInterceptor)this.responseInterceptors.get(i);
/*     */       
/* 263 */       interceptor.process(response, context);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyInterceptors(BasicHttpProcessor target)
/*     */   {
/* 274 */     target.requestInterceptors.clear();
/* 275 */     target.requestInterceptors.addAll(this.requestInterceptors);
/* 276 */     target.responseInterceptors.clear();
/* 277 */     target.responseInterceptors.addAll(this.responseInterceptors);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicHttpProcessor copy()
/*     */   {
/* 286 */     BasicHttpProcessor clone = new BasicHttpProcessor();
/* 287 */     copyInterceptors(clone);
/* 288 */     return clone;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 292 */     BasicHttpProcessor clone = (BasicHttpProcessor)super.clone();
/* 293 */     copyInterceptors(clone);
/* 294 */     return clone;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/BasicHttpProcessor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */