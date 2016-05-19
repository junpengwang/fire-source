/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
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
/*     */ public abstract class AnnotatedWithParams
/*     */   extends AnnotatedMember
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotationMap[] _paramAnnotations;
/*     */   
/*     */   protected AnnotatedWithParams(AnnotationMap paramAnnotationMap, AnnotationMap[] paramArrayOfAnnotationMap)
/*     */   {
/*  35 */     super(paramAnnotationMap);
/*  36 */     this._paramAnnotations = paramArrayOfAnnotationMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void addOrOverrideParam(int paramInt, Annotation paramAnnotation)
/*     */   {
/*  47 */     AnnotationMap localAnnotationMap = this._paramAnnotations[paramInt];
/*  48 */     if (localAnnotationMap == null) {
/*  49 */       localAnnotationMap = new AnnotationMap();
/*  50 */       this._paramAnnotations[paramInt] = localAnnotationMap;
/*     */     }
/*  52 */     localAnnotationMap.add(paramAnnotation);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnnotatedParameter replaceParameterAnnotations(int paramInt, AnnotationMap paramAnnotationMap)
/*     */   {
/*  61 */     this._paramAnnotations[paramInt] = paramAnnotationMap;
/*  62 */     return getParameter(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JavaType getType(TypeBindings paramTypeBindings, TypeVariable<?>[] paramArrayOfTypeVariable)
/*     */   {
/*  74 */     if ((paramArrayOfTypeVariable != null) && (paramArrayOfTypeVariable.length > 0)) {
/*  75 */       paramTypeBindings = paramTypeBindings.childInstance();
/*  76 */       for (TypeVariable<?> localTypeVariable : paramArrayOfTypeVariable) {
/*  77 */         String str = localTypeVariable.getName();
/*     */         
/*  79 */         paramTypeBindings._addPlaceholder(str);
/*     */         
/*  81 */         Type localType = localTypeVariable.getBounds()[0];
/*  82 */         JavaType localJavaType = localType == null ? TypeFactory.unknownType() : paramTypeBindings.resolveType(localType);
/*     */         
/*  84 */         paramTypeBindings.addBinding(localTypeVariable.getName(), localJavaType);
/*     */       }
/*     */     }
/*  87 */     return paramTypeBindings.resolveType(getGenericType());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/*  99 */     return this._annotations.get(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final AnnotationMap getParameterAnnotations(int paramInt)
/*     */   {
/* 110 */     if ((this._paramAnnotations != null) && 
/* 111 */       (paramInt >= 0) && (paramInt <= this._paramAnnotations.length)) {
/* 112 */       return this._paramAnnotations[paramInt];
/*     */     }
/*     */     
/* 115 */     return null;
/*     */   }
/*     */   
/*     */   public final AnnotatedParameter getParameter(int paramInt) {
/* 119 */     return new AnnotatedParameter(this, getGenericParameterType(paramInt), getParameterAnnotations(paramInt), paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract int getParameterCount();
/*     */   
/*     */ 
/*     */   public abstract Class<?> getRawParameterType(int paramInt);
/*     */   
/*     */ 
/*     */   public abstract Type getGenericParameterType(int paramInt);
/*     */   
/*     */ 
/*     */   public final JavaType resolveParameterType(int paramInt, TypeBindings paramTypeBindings)
/*     */   {
/* 134 */     return paramTypeBindings.resolveType(getGenericParameterType(paramInt));
/*     */   }
/*     */   
/* 137 */   public final int getAnnotationCount() { return this._annotations.size(); }
/*     */   
/*     */   public abstract Object call()
/*     */     throws Exception;
/*     */   
/*     */   public abstract Object call(Object[] paramArrayOfObject)
/*     */     throws Exception;
/*     */   
/*     */   public abstract Object call1(Object paramObject)
/*     */     throws Exception;
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedWithParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */