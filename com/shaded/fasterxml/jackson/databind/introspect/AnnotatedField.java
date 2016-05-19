/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
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
/*     */ public final class AnnotatedField
/*     */   extends AnnotatedMember
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7364428299211355871L;
/*     */   protected final transient Field _field;
/*     */   protected Serialization _serialization;
/*     */   
/*     */   public AnnotatedField(Field paramField, AnnotationMap paramAnnotationMap)
/*     */   {
/*  41 */     super(paramAnnotationMap);
/*  42 */     this._field = paramField;
/*     */   }
/*     */   
/*     */   public AnnotatedField withAnnotations(AnnotationMap paramAnnotationMap)
/*     */   {
/*  47 */     return new AnnotatedField(this._field, paramAnnotationMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnnotatedField(Serialization paramSerialization)
/*     */   {
/*  55 */     super(null);
/*  56 */     this._field = null;
/*  57 */     this._serialization = paramSerialization;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Field getAnnotated()
/*     */   {
/*  67 */     return this._field;
/*     */   }
/*     */   
/*  70 */   public int getModifiers() { return this._field.getModifiers(); }
/*     */   
/*     */   public String getName() {
/*  73 */     return this._field.getName();
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/*  78 */     return this._annotations == null ? null : this._annotations.get(paramClass);
/*     */   }
/*     */   
/*     */   public Type getGenericType()
/*     */   {
/*  83 */     return this._field.getGenericType();
/*     */   }
/*     */   
/*     */   public Class<?> getRawType()
/*     */   {
/*  88 */     return this._field.getType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getDeclaringClass()
/*     */   {
/*  98 */     return this._field.getDeclaringClass();
/*     */   }
/*     */   
/* 101 */   public Member getMember() { return this._field; }
/*     */   
/*     */   public void setValue(Object paramObject1, Object paramObject2) throws IllegalArgumentException
/*     */   {
/*     */     try
/*     */     {
/* 107 */       this._field.set(paramObject1, paramObject2);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 109 */       throw new IllegalArgumentException("Failed to setValue() for field " + getFullName() + ": " + localIllegalAccessException.getMessage(), localIllegalAccessException);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getValue(Object paramObject)
/*     */     throws IllegalArgumentException
/*     */   {
/*     */     try
/*     */     {
/* 118 */       return this._field.get(paramObject);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 120 */       throw new IllegalArgumentException("Failed to getValue() for field " + getFullName() + ": " + localIllegalAccessException.getMessage(), localIllegalAccessException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFullName()
/*     */   {
/* 132 */     return getDeclaringClass().getName() + "#" + getName();
/*     */   }
/*     */   
/* 135 */   public int getAnnotationCount() { return this._annotations.size(); }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 140 */     return "[field " + getFullName() + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Object writeReplace()
/*     */   {
/* 150 */     return new AnnotatedField(new Serialization(this._field));
/*     */   }
/*     */   
/*     */   Object readResolve() {
/* 154 */     Class localClass = this._serialization.clazz;
/*     */     try {
/* 156 */       Field localField = localClass.getDeclaredField(this._serialization.name);
/*     */       
/* 158 */       if (!localField.isAccessible()) {
/* 159 */         ClassUtil.checkAndFixAccess(localField);
/*     */       }
/* 161 */       return new AnnotatedField(localField, null);
/*     */     } catch (Exception localException) {
/* 163 */       throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + localClass.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class Serialization
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected Class<?> clazz;
/*     */     
/*     */     protected String name;
/*     */     
/*     */ 
/*     */     public Serialization(Field paramField)
/*     */     {
/* 181 */       this.clazz = paramField.getDeclaringClass();
/* 182 */       this.name = paramField.getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */