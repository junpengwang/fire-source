/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyFilter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.FilterProvider;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class SimpleFilterProvider
/*     */   extends FilterProvider
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2825494703774121220L;
/*     */   protected final Map<String, BeanPropertyFilter> _filtersById;
/*     */   protected BeanPropertyFilter _defaultFilter;
/*  36 */   protected boolean _cfgFailOnUnknownId = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleFilterProvider()
/*     */   {
/*  45 */     this(new HashMap());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SimpleFilterProvider(Map<String, BeanPropertyFilter> paramMap)
/*     */   {
/*  52 */     this._filtersById = paramMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleFilterProvider setDefaultFilter(BeanPropertyFilter paramBeanPropertyFilter)
/*     */   {
/*  63 */     this._defaultFilter = paramBeanPropertyFilter;
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   public BeanPropertyFilter getDefaultFilter() {
/*  68 */     return this._defaultFilter;
/*     */   }
/*     */   
/*     */   public SimpleFilterProvider setFailOnUnknownId(boolean paramBoolean) {
/*  72 */     this._cfgFailOnUnknownId = paramBoolean;
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public boolean willFailOnUnknownId() {
/*  77 */     return this._cfgFailOnUnknownId;
/*     */   }
/*     */   
/*     */   public SimpleFilterProvider addFilter(String paramString, BeanPropertyFilter paramBeanPropertyFilter) {
/*  81 */     this._filtersById.put(paramString, paramBeanPropertyFilter);
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public BeanPropertyFilter removeFilter(String paramString) {
/*  86 */     return (BeanPropertyFilter)this._filtersById.remove(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanPropertyFilter findFilter(Object paramObject)
/*     */   {
/*  98 */     BeanPropertyFilter localBeanPropertyFilter = (BeanPropertyFilter)this._filtersById.get(paramObject);
/*  99 */     if (localBeanPropertyFilter == null) {
/* 100 */       localBeanPropertyFilter = this._defaultFilter;
/* 101 */       if ((localBeanPropertyFilter == null) && (this._cfgFailOnUnknownId)) {
/* 102 */         throw new IllegalArgumentException("No filter configured with id '" + paramObject + "' (type " + paramObject.getClass().getName() + ")");
/*     */       }
/*     */     }
/*     */     
/* 106 */     return localBeanPropertyFilter;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/SimpleFilterProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */