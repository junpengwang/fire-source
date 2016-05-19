/*    */ package com.shaded.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.AnnotatedElement;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Annotated
/*    */ {
/*    */   public abstract <A extends Annotation> A getAnnotation(Class<A> paramClass);
/*    */   
/*    */   public final <A extends Annotation> boolean hasAnnotation(Class<A> paramClass)
/*    */   {
/* 24 */     return getAnnotation(paramClass) != null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract Annotated withAnnotations(AnnotationMap paramAnnotationMap);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final Annotated withFallBackAnnotationsFrom(Annotated paramAnnotated)
/*    */   {
/* 38 */     return withAnnotations(AnnotationMap.merge(getAllAnnotations(), paramAnnotated.getAllAnnotations()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public abstract AnnotatedElement getAnnotated();
/*    */   
/*    */ 
/*    */   protected abstract int getModifiers();
/*    */   
/*    */ 
/*    */   public final boolean isPublic()
/*    */   {
/* 51 */     return Modifier.isPublic(getModifiers());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public abstract String getName();
/*    */   
/*    */ 
/*    */   public JavaType getType(TypeBindings paramTypeBindings)
/*    */   {
/* 61 */     return paramTypeBindings.resolveType(getGenericType());
/*    */   }
/*    */   
/*    */   public abstract Type getGenericType();
/*    */   
/*    */   public abstract Class<?> getRawType();
/*    */   
/*    */   protected abstract AnnotationMap getAllAnnotations();
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/Annotated.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */