/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Type;
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
/*     */ public final class AnnotatedParameter
/*     */   extends AnnotatedMember
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedWithParams _owner;
/*     */   protected final Type _type;
/*     */   protected final int _index;
/*     */   
/*     */   public AnnotatedParameter(AnnotatedWithParams paramAnnotatedWithParams, Type paramType, AnnotationMap paramAnnotationMap, int paramInt)
/*     */   {
/*  52 */     super(paramAnnotationMap);
/*  53 */     this._owner = paramAnnotatedWithParams;
/*  54 */     this._type = paramType;
/*  55 */     this._index = paramInt;
/*     */   }
/*     */   
/*     */   public AnnotatedParameter withAnnotations(AnnotationMap paramAnnotationMap)
/*     */   {
/*  60 */     if (paramAnnotationMap == this._annotations) {
/*  61 */       return this;
/*     */     }
/*  63 */     return this._owner.replaceParameterAnnotations(this._index, paramAnnotationMap);
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
/*     */   public AnnotatedElement getAnnotated()
/*     */   {
/*  77 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getModifiers()
/*     */   {
/*  84 */     return this._owner.getModifiers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  91 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/* 100 */     return this._annotations == null ? null : this._annotations.get(paramClass);
/*     */   }
/*     */   
/*     */   public Type getGenericType()
/*     */   {
/* 105 */     return this._type;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> getRawType()
/*     */   {
/* 111 */     if ((this._type instanceof Class)) {
/* 112 */       return (Class)this._type;
/*     */     }
/*     */     
/* 115 */     JavaType localJavaType = TypeFactory.defaultInstance().constructType(this._type);
/* 116 */     return localJavaType.getRawClass();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getDeclaringClass()
/*     */   {
/* 127 */     return this._owner.getDeclaringClass();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Member getMember()
/*     */   {
/* 135 */     return this._owner.getMember();
/*     */   }
/*     */   
/*     */   public void setValue(Object paramObject1, Object paramObject2)
/*     */     throws UnsupportedOperationException
/*     */   {
/* 141 */     throw new UnsupportedOperationException("Cannot call setValue() on constructor parameter of " + getDeclaringClass().getName());
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getValue(Object paramObject)
/*     */     throws UnsupportedOperationException
/*     */   {
/* 148 */     throw new UnsupportedOperationException("Cannot call getValue() on constructor parameter of " + getDeclaringClass().getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getParameterType()
/*     */   {
/* 158 */     return this._type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedWithParams getOwner()
/*     */   {
/* 166 */     return this._owner;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getIndex()
/*     */   {
/* 173 */     return this._index;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 184 */     return "[parameter #" + getIndex() + ", annotations: " + this._annotations + "]";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */