/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
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
/*     */ public final class AnnotatedMethod
/*     */   extends AnnotatedWithParams
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final transient Method _method;
/*     */   protected Class<?>[] _paramClasses;
/*     */   protected Serialization _serialization;
/*     */   
/*     */   public AnnotatedMethod(Method paramMethod, AnnotationMap paramAnnotationMap, AnnotationMap[] paramArrayOfAnnotationMap)
/*     */   {
/*  37 */     super(paramAnnotationMap, paramArrayOfAnnotationMap);
/*  38 */     if (paramMethod == null) {
/*  39 */       throw new IllegalArgumentException("Can not construct AnnotatedMethod with null Method");
/*     */     }
/*  41 */     this._method = paramMethod;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnnotatedMethod(Serialization paramSerialization)
/*     */   {
/*  50 */     super(null, null);
/*  51 */     this._method = null;
/*  52 */     this._serialization = paramSerialization;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedMethod withMethod(Method paramMethod)
/*     */   {
/*  61 */     return new AnnotatedMethod(paramMethod, this._annotations, this._paramAnnotations);
/*     */   }
/*     */   
/*     */   public AnnotatedMethod withAnnotations(AnnotationMap paramAnnotationMap)
/*     */   {
/*  66 */     return new AnnotatedMethod(this._method, paramAnnotationMap, this._paramAnnotations);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method getAnnotated()
/*     */   {
/*  76 */     return this._method;
/*     */   }
/*     */   
/*  79 */   public int getModifiers() { return this._method.getModifiers(); }
/*     */   
/*     */   public String getName() {
/*  82 */     return this._method.getName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getGenericType()
/*     */   {
/*  91 */     return this._method.getGenericReturnType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getRawType()
/*     */   {
/* 101 */     return this._method.getReturnType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType getType(TypeBindings paramTypeBindings)
/*     */   {
/* 110 */     return getType(paramTypeBindings, this._method.getTypeParameters());
/*     */   }
/*     */   
/*     */   public final Object call() throws Exception
/*     */   {
/* 115 */     return this._method.invoke(null, new Object[0]);
/*     */   }
/*     */   
/*     */   public final Object call(Object[] paramArrayOfObject) throws Exception
/*     */   {
/* 120 */     return this._method.invoke(null, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   public final Object call1(Object paramObject) throws Exception
/*     */   {
/* 125 */     return this._method.invoke(null, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getDeclaringClass()
/*     */   {
/* 135 */     return this._method.getDeclaringClass();
/*     */   }
/*     */   
/* 138 */   public Method getMember() { return this._method; }
/*     */   
/*     */   public void setValue(Object paramObject1, Object paramObject2)
/*     */     throws IllegalArgumentException
/*     */   {
/*     */     try
/*     */     {
/* 145 */       this._method.invoke(paramObject1, new Object[] { paramObject2 });
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 147 */       throw new IllegalArgumentException("Failed to setValue() with method " + getFullName() + ": " + localIllegalAccessException.getMessage(), localIllegalAccessException);
/*     */     }
/*     */     catch (InvocationTargetException localInvocationTargetException) {
/* 150 */       throw new IllegalArgumentException("Failed to setValue() with method " + getFullName() + ": " + localInvocationTargetException.getMessage(), localInvocationTargetException);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getValue(Object paramObject)
/*     */     throws IllegalArgumentException
/*     */   {
/*     */     try
/*     */     {
/* 159 */       return this._method.invoke(paramObject, new Object[0]);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 161 */       throw new IllegalArgumentException("Failed to getValue() with method " + getFullName() + ": " + localIllegalAccessException.getMessage(), localIllegalAccessException);
/*     */     }
/*     */     catch (InvocationTargetException localInvocationTargetException) {
/* 164 */       throw new IllegalArgumentException("Failed to getValue() with method " + getFullName() + ": " + localInvocationTargetException.getMessage(), localInvocationTargetException);
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
/*     */   public int getParameterCount()
/*     */   {
/* 177 */     return getRawParameterTypes().length;
/*     */   }
/*     */   
/*     */   public String getFullName() {
/* 181 */     return getDeclaringClass().getName() + "#" + getName() + "(" + getParameterCount() + " params)";
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?>[] getRawParameterTypes()
/*     */   {
/* 187 */     if (this._paramClasses == null) {
/* 188 */       this._paramClasses = this._method.getParameterTypes();
/*     */     }
/* 190 */     return this._paramClasses;
/*     */   }
/*     */   
/*     */   public Type[] getGenericParameterTypes() {
/* 194 */     return this._method.getGenericParameterTypes();
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> getRawParameterType(int paramInt)
/*     */   {
/* 200 */     Class[] arrayOfClass = getRawParameterTypes();
/* 201 */     return paramInt >= arrayOfClass.length ? null : arrayOfClass[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */   public Type getGenericParameterType(int paramInt)
/*     */   {
/* 207 */     Type[] arrayOfType = this._method.getGenericParameterTypes();
/* 208 */     return paramInt >= arrayOfType.length ? null : arrayOfType[paramInt];
/*     */   }
/*     */   
/*     */   public Class<?> getRawReturnType() {
/* 212 */     return this._method.getReturnType();
/*     */   }
/*     */   
/*     */   public Type getGenericReturnType() {
/* 216 */     return this._method.getGenericReturnType();
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
/* 228 */     return "[method " + getFullName() + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Object writeReplace()
/*     */   {
/* 238 */     return new AnnotatedMethod(new Serialization(this._method));
/*     */   }
/*     */   
/*     */   Object readResolve() {
/* 242 */     Class localClass = this._serialization.clazz;
/*     */     try {
/* 244 */       Method localMethod = localClass.getDeclaredMethod(this._serialization.name, this._serialization.args);
/*     */       
/*     */ 
/* 247 */       if (!localMethod.isAccessible()) {
/* 248 */         ClassUtil.checkAndFixAccess(localMethod);
/*     */       }
/* 250 */       return new AnnotatedMethod(localMethod, null, null);
/*     */     } catch (Exception localException) {
/* 252 */       throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + localClass.getName());
/*     */     }
/*     */   }
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
/*     */     protected Class<?>[] args;
/*     */     
/*     */ 
/*     */     public Serialization(Method paramMethod)
/*     */     {
/* 271 */       this.clazz = paramMethod.getDeclaringClass();
/* 272 */       this.name = paramMethod.getName();
/* 273 */       this.args = paramMethod.getParameterTypes();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */