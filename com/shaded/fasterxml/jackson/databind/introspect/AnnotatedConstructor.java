/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
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
/*     */ public final class AnnotatedConstructor
/*     */   extends AnnotatedWithParams
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Constructor<?> _constructor;
/*     */   protected Serialization _serialization;
/*     */   
/*     */   public AnnotatedConstructor(Constructor<?> paramConstructor, AnnotationMap paramAnnotationMap, AnnotationMap[] paramArrayOfAnnotationMap)
/*     */   {
/*  34 */     super(paramAnnotationMap, paramArrayOfAnnotationMap);
/*  35 */     if (paramConstructor == null) {
/*  36 */       throw new IllegalArgumentException("Null constructor not allowed");
/*     */     }
/*  38 */     this._constructor = paramConstructor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnnotatedConstructor(Serialization paramSerialization)
/*     */   {
/*  47 */     super(null, null);
/*  48 */     this._constructor = null;
/*  49 */     this._serialization = paramSerialization;
/*     */   }
/*     */   
/*     */   public AnnotatedConstructor withAnnotations(AnnotationMap paramAnnotationMap)
/*     */   {
/*  54 */     return new AnnotatedConstructor(this._constructor, paramAnnotationMap, this._paramAnnotations);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Constructor<?> getAnnotated()
/*     */   {
/*  64 */     return this._constructor;
/*     */   }
/*     */   
/*  67 */   public int getModifiers() { return this._constructor.getModifiers(); }
/*     */   
/*     */   public String getName() {
/*  70 */     return this._constructor.getName();
/*     */   }
/*     */   
/*     */   public Type getGenericType() {
/*  74 */     return getRawType();
/*     */   }
/*     */   
/*     */   public Class<?> getRawType()
/*     */   {
/*  79 */     return this._constructor.getDeclaringClass();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType getType(TypeBindings paramTypeBindings)
/*     */   {
/*  90 */     return getType(paramTypeBindings, this._constructor.getTypeParameters());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getParameterCount()
/*     */   {
/* 101 */     return this._constructor.getParameterTypes().length;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> getRawParameterType(int paramInt)
/*     */   {
/* 107 */     Class[] arrayOfClass = this._constructor.getParameterTypes();
/* 108 */     return paramInt >= arrayOfClass.length ? null : arrayOfClass[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */   public Type getGenericParameterType(int paramInt)
/*     */   {
/* 114 */     Type[] arrayOfType = this._constructor.getGenericParameterTypes();
/* 115 */     return paramInt >= arrayOfType.length ? null : arrayOfType[paramInt];
/*     */   }
/*     */   
/*     */   public final Object call() throws Exception
/*     */   {
/* 120 */     return this._constructor.newInstance(new Object[0]);
/*     */   }
/*     */   
/*     */   public final Object call(Object[] paramArrayOfObject) throws Exception
/*     */   {
/* 125 */     return this._constructor.newInstance(paramArrayOfObject);
/*     */   }
/*     */   
/*     */   public final Object call1(Object paramObject) throws Exception
/*     */   {
/* 130 */     return this._constructor.newInstance(new Object[] { paramObject });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getDeclaringClass()
/*     */   {
/* 140 */     return this._constructor.getDeclaringClass();
/*     */   }
/*     */   
/* 143 */   public Member getMember() { return this._constructor; }
/*     */   
/*     */ 
/*     */   public void setValue(Object paramObject1, Object paramObject2)
/*     */     throws UnsupportedOperationException
/*     */   {
/* 149 */     throw new UnsupportedOperationException("Cannot call setValue() on constructor of " + getDeclaringClass().getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object getValue(Object paramObject)
/*     */     throws UnsupportedOperationException
/*     */   {
/* 157 */     throw new UnsupportedOperationException("Cannot call getValue() on constructor of " + getDeclaringClass().getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 169 */     return "[constructor for " + getName() + ", annotations: " + this._annotations + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Object writeReplace()
/*     */   {
/* 179 */     return new AnnotatedConstructor(new Serialization(this._constructor));
/*     */   }
/*     */   
/*     */   Object readResolve() {
/* 183 */     Class localClass = this._serialization.clazz;
/*     */     try {
/* 185 */       Constructor localConstructor = localClass.getDeclaredConstructor(this._serialization.args);
/*     */       
/* 187 */       if (!localConstructor.isAccessible()) {
/* 188 */         ClassUtil.checkAndFixAccess(localConstructor);
/*     */       }
/* 190 */       return new AnnotatedConstructor(localConstructor, null, null);
/*     */     } catch (Exception localException) {
/* 192 */       throw new IllegalArgumentException("Could not find constructor with " + this._serialization.args.length + " args from Class '" + localClass.getName());
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
/*     */     protected Class<?>[] args;
/*     */     
/*     */ 
/*     */     public Serialization(Constructor<?> paramConstructor)
/*     */     {
/* 210 */       this.clazz = paramConstructor.getDeclaringClass();
/* 211 */       this.args = paramConstructor.getParameterTypes();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedConstructor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */