/*    */ package com.shaded.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*    */ import java.io.Serializable;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Member;
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
/*    */ public abstract class AnnotatedMember
/*    */   extends Annotated
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7364428299211355871L;
/*    */   protected final transient AnnotationMap _annotations;
/*    */   
/*    */   protected AnnotatedMember(AnnotationMap paramAnnotationMap)
/*    */   {
/* 28 */     this._annotations = paramAnnotationMap;
/*    */   }
/*    */   
/*    */   public abstract Class<?> getDeclaringClass();
/*    */   
/*    */   public abstract Member getMember();
/*    */   
/*    */   protected AnnotationMap getAllAnnotations()
/*    */   {
/* 37 */     return this._annotations;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void addOrOverride(Annotation paramAnnotation)
/*    */   {
/* 46 */     this._annotations.add(paramAnnotation);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void addIfNotPresent(Annotation paramAnnotation)
/*    */   {
/* 55 */     this._annotations.addIfNotPresent(paramAnnotation);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void fixAccess()
/*    */   {
/* 64 */     ClassUtil.checkAndFixAccess(getMember());
/*    */   }
/*    */   
/*    */   public abstract void setValue(Object paramObject1, Object paramObject2)
/*    */     throws UnsupportedOperationException, IllegalArgumentException;
/*    */   
/*    */   public abstract Object getValue(Object paramObject)
/*    */     throws UnsupportedOperationException, IllegalArgumentException;
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedMember.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */