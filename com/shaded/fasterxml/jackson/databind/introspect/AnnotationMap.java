/*    */ package com.shaded.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AnnotationMap
/*    */   implements Annotations
/*    */ {
/*    */   protected HashMap<Class<? extends Annotation>, Annotation> _annotations;
/*    */   
/*    */   public AnnotationMap() {}
/*    */   
/*    */   private AnnotationMap(HashMap<Class<? extends Annotation>, Annotation> paramHashMap)
/*    */   {
/* 21 */     this._annotations = paramHashMap;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public <A extends Annotation> A get(Class<A> paramClass)
/*    */   {
/* 28 */     if (this._annotations == null) {
/* 29 */       return null;
/*    */     }
/* 31 */     return (Annotation)this._annotations.get(paramClass);
/*    */   }
/*    */   
/*    */   public static AnnotationMap merge(AnnotationMap paramAnnotationMap1, AnnotationMap paramAnnotationMap2)
/*    */   {
/* 36 */     if ((paramAnnotationMap1 == null) || (paramAnnotationMap1._annotations == null) || (paramAnnotationMap1._annotations.isEmpty())) {
/* 37 */       return paramAnnotationMap2;
/*    */     }
/* 39 */     if ((paramAnnotationMap2 == null) || (paramAnnotationMap2._annotations == null) || (paramAnnotationMap2._annotations.isEmpty())) {
/* 40 */       return paramAnnotationMap1;
/*    */     }
/* 42 */     HashMap localHashMap = new HashMap();
/*    */     
/*    */ 
/* 45 */     for (Iterator localIterator = paramAnnotationMap2._annotations.values().iterator(); localIterator.hasNext();) { localAnnotation = (Annotation)localIterator.next();
/* 46 */       localHashMap.put(localAnnotation.annotationType(), localAnnotation);
/*    */     }
/*    */     Annotation localAnnotation;
/* 49 */     for (localIterator = paramAnnotationMap1._annotations.values().iterator(); localIterator.hasNext();) { localAnnotation = (Annotation)localIterator.next();
/* 50 */       localHashMap.put(localAnnotation.annotationType(), localAnnotation);
/*    */     }
/* 52 */     return new AnnotationMap(localHashMap);
/*    */   }
/*    */   
/*    */   public int size()
/*    */   {
/* 57 */     return this._annotations == null ? 0 : this._annotations.size();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addIfNotPresent(Annotation paramAnnotation)
/*    */   {
/* 66 */     if ((this._annotations == null) || (!this._annotations.containsKey(paramAnnotation.annotationType()))) {
/* 67 */       _add(paramAnnotation);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void add(Annotation paramAnnotation)
/*    */   {
/* 75 */     _add(paramAnnotation);
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 81 */     if (this._annotations == null) {
/* 82 */       return "[null]";
/*    */     }
/* 84 */     return this._annotations.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected final void _add(Annotation paramAnnotation)
/*    */   {
/* 95 */     if (this._annotations == null) {
/* 96 */       this._annotations = new HashMap();
/*    */     }
/* 98 */     this._annotations.put(paramAnnotation.annotationType(), paramAnnotation);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotationMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */