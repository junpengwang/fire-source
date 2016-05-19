/*     */ package org.shaded.apache.http.params;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ public final class BasicHttpParams
/*     */   extends AbstractHttpParams
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -7086398485908701455L;
/*  58 */   private final HashMap parameters = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getParameter(String name)
/*     */   {
/*  65 */     return this.parameters.get(name);
/*     */   }
/*     */   
/*     */   public HttpParams setParameter(String name, Object value) {
/*  69 */     this.parameters.put(name, value);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public boolean removeParameter(String name)
/*     */   {
/*  75 */     if (this.parameters.containsKey(name)) {
/*  76 */       this.parameters.remove(name);
/*  77 */       return true;
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParameters(String[] names, Object value)
/*     */   {
/*  91 */     for (int i = 0; i < names.length; i++) {
/*  92 */       setParameter(names[i], value);
/*     */     }
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
/*     */   public boolean isParameterSet(String name)
/*     */   {
/* 108 */     return getParameter(name) != null;
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
/*     */   public boolean isParameterSetLocally(String name)
/*     */   {
/* 122 */     return this.parameters.get(name) != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 129 */     this.parameters.clear();
/*     */   }
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
/* 141 */     BasicHttpParams clone = new BasicHttpParams();
/* 142 */     copyParams(clone);
/* 143 */     return clone;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 147 */     BasicHttpParams clone = (BasicHttpParams)super.clone();
/* 148 */     copyParams(clone);
/* 149 */     return clone;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyParams(HttpParams target)
/*     */   {
/* 159 */     Iterator iter = this.parameters.entrySet().iterator();
/* 160 */     while (iter.hasNext()) {
/* 161 */       Map.Entry me = (Map.Entry)iter.next();
/* 162 */       if ((me.getKey() instanceof String)) {
/* 163 */         target.setParameter((String)me.getKey(), me.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/params/BasicHttpParams.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */