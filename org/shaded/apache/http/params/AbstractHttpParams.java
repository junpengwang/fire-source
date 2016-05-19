/*     */ package org.shaded.apache.http.params;
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
/*     */ public abstract class AbstractHttpParams
/*     */   implements HttpParams
/*     */ {
/*     */   public long getLongParameter(String name, long defaultValue)
/*     */   {
/*  57 */     Object param = getParameter(name);
/*  58 */     if (param == null) {
/*  59 */       return defaultValue;
/*     */     }
/*  61 */     return ((Long)param).longValue();
/*     */   }
/*     */   
/*     */   public HttpParams setLongParameter(String name, long value) {
/*  65 */     setParameter(name, new Long(value));
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public int getIntParameter(String name, int defaultValue) {
/*  70 */     Object param = getParameter(name);
/*  71 */     if (param == null) {
/*  72 */       return defaultValue;
/*     */     }
/*  74 */     return ((Integer)param).intValue();
/*     */   }
/*     */   
/*     */   public HttpParams setIntParameter(String name, int value) {
/*  78 */     setParameter(name, new Integer(value));
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   public double getDoubleParameter(String name, double defaultValue) {
/*  83 */     Object param = getParameter(name);
/*  84 */     if (param == null) {
/*  85 */       return defaultValue;
/*     */     }
/*  87 */     return ((Double)param).doubleValue();
/*     */   }
/*     */   
/*     */   public HttpParams setDoubleParameter(String name, double value) {
/*  91 */     setParameter(name, new Double(value));
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public boolean getBooleanParameter(String name, boolean defaultValue) {
/*  96 */     Object param = getParameter(name);
/*  97 */     if (param == null) {
/*  98 */       return defaultValue;
/*     */     }
/* 100 */     return ((Boolean)param).booleanValue();
/*     */   }
/*     */   
/*     */   public HttpParams setBooleanParameter(String name, boolean value) {
/* 104 */     setParameter(name, value ? Boolean.TRUE : Boolean.FALSE);
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isParameterTrue(String name) {
/* 109 */     return getBooleanParameter(name, false);
/*     */   }
/*     */   
/*     */   public boolean isParameterFalse(String name) {
/* 113 */     return !getBooleanParameter(name, false);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/params/AbstractHttpParams.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */