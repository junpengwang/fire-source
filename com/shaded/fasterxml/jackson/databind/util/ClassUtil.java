/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClassUtil
/*     */ {
/*     */   public static List<Class<?>> findSuperTypes(Class<?> paramClass1, Class<?> paramClass2)
/*     */   {
/*  30 */     return findSuperTypes(paramClass1, paramClass2, new ArrayList(8));
/*     */   }
/*     */   
/*     */   public static List<Class<?>> findSuperTypes(Class<?> paramClass1, Class<?> paramClass2, List<Class<?>> paramList)
/*     */   {
/*  35 */     _addSuperTypes(paramClass1, paramClass2, paramList, false);
/*  36 */     return paramList;
/*     */   }
/*     */   
/*     */   private static void _addSuperTypes(Class<?> paramClass1, Class<?> paramClass2, Collection<Class<?>> paramCollection, boolean paramBoolean)
/*     */   {
/*  41 */     if ((paramClass1 == paramClass2) || (paramClass1 == null) || (paramClass1 == Object.class)) {
/*  42 */       return;
/*     */     }
/*  44 */     if (paramBoolean) {
/*  45 */       if (paramCollection.contains(paramClass1)) {
/*  46 */         return;
/*     */       }
/*  48 */       paramCollection.add(paramClass1);
/*     */     }
/*  50 */     for (Class localClass : paramClass1.getInterfaces()) {
/*  51 */       _addSuperTypes(localClass, paramClass2, paramCollection, true);
/*     */     }
/*  53 */     _addSuperTypes(paramClass1.getSuperclass(), paramClass2, paramCollection, true);
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
/*     */ 
/*     */ 
/*     */   public static String canBeABeanType(Class<?> paramClass)
/*     */   {
/*  69 */     if (paramClass.isAnnotation()) {
/*  70 */       return "annotation";
/*     */     }
/*  72 */     if (paramClass.isArray()) {
/*  73 */       return "array";
/*     */     }
/*  75 */     if (paramClass.isEnum()) {
/*  76 */       return "enum";
/*     */     }
/*  78 */     if (paramClass.isPrimitive()) {
/*  79 */       return "primitive";
/*     */     }
/*     */     
/*     */ 
/*  83 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String isLocalType(Class<?> paramClass, boolean paramBoolean)
/*     */   {
/*     */     try
/*     */     {
/*  94 */       if (paramClass.getEnclosingMethod() != null) {
/*  95 */         return "local/anonymous";
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */       if ((!paramBoolean) && 
/* 103 */         (paramClass.getEnclosingClass() != null) && 
/* 104 */         (!Modifier.isStatic(paramClass.getModifiers()))) {
/* 105 */         return "non-static member class";
/*     */       }
/*     */     }
/*     */     catch (SecurityException localSecurityException) {}catch (NullPointerException localNullPointerException) {}
/*     */     
/*     */ 
/*     */ 
/* 112 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<?> getOuterClass(Class<?> paramClass)
/*     */   {
/*     */     try
/*     */     {
/* 123 */       if (paramClass.getEnclosingMethod() != null) {
/* 124 */         return null;
/*     */       }
/* 126 */       if (!Modifier.isStatic(paramClass.getModifiers())) {
/* 127 */         return paramClass.getEnclosingClass();
/*     */       }
/*     */     }
/*     */     catch (SecurityException localSecurityException) {}catch (NullPointerException localNullPointerException) {}
/* 131 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isProxyType(Class<?> paramClass)
/*     */   {
/* 149 */     String str = paramClass.getName();
/*     */     
/* 151 */     if ((str.startsWith("net.sf.cglib.proxy.")) || (str.startsWith("org.hibernate.proxy.")))
/*     */     {
/* 153 */       return true;
/*     */     }
/*     */     
/* 156 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isConcrete(Class<?> paramClass)
/*     */   {
/* 165 */     int i = paramClass.getModifiers();
/* 166 */     return (i & 0x600) == 0;
/*     */   }
/*     */   
/*     */   public static boolean isConcrete(Member paramMember)
/*     */   {
/* 171 */     int i = paramMember.getModifiers();
/* 172 */     return (i & 0x600) == 0;
/*     */   }
/*     */   
/*     */   public static boolean isCollectionMapOrArray(Class<?> paramClass)
/*     */   {
/* 177 */     if (paramClass.isArray()) return true;
/* 178 */     if (Collection.class.isAssignableFrom(paramClass)) return true;
/* 179 */     if (Map.class.isAssignableFrom(paramClass)) return true;
/* 180 */     return false;
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
/*     */ 
/*     */ 
/*     */   public static String getClassDescription(Object paramObject)
/*     */   {
/* 196 */     if (paramObject == null) {
/* 197 */       return "unknown";
/*     */     }
/* 199 */     Class localClass = (paramObject instanceof Class) ? (Class)paramObject : paramObject.getClass();
/*     */     
/* 201 */     return localClass.getName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<?> findClass(String paramString)
/*     */     throws ClassNotFoundException
/*     */   {
/* 213 */     if (paramString.indexOf('.') < 0) {
/* 214 */       if ("int".equals(paramString)) return Integer.TYPE;
/* 215 */       if ("long".equals(paramString)) return Long.TYPE;
/* 216 */       if ("float".equals(paramString)) return Float.TYPE;
/* 217 */       if ("double".equals(paramString)) return Double.TYPE;
/* 218 */       if ("boolean".equals(paramString)) return Boolean.TYPE;
/* 219 */       if ("byte".equals(paramString)) return Byte.TYPE;
/* 220 */       if ("char".equals(paramString)) return Character.TYPE;
/* 221 */       if ("short".equals(paramString)) return Short.TYPE;
/* 222 */       if ("void".equals(paramString)) { return Void.TYPE;
/*     */       }
/*     */     }
/* 225 */     Throwable localThrowable = null;
/* 226 */     ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
/*     */     
/* 228 */     if (localClassLoader != null) {
/*     */       try {
/* 230 */         return Class.forName(paramString, true, localClassLoader);
/*     */       } catch (Exception localException1) {
/* 232 */         localThrowable = getRootCause(localException1);
/*     */       }
/*     */     }
/*     */     try {
/* 236 */       return Class.forName(paramString);
/*     */     } catch (Exception localException2) {
/* 238 */       if (localThrowable == null) {
/* 239 */         localThrowable = getRootCause(localException2);
/*     */       }
/*     */       
/* 242 */       if ((localThrowable instanceof RuntimeException)) {
/* 243 */         throw ((RuntimeException)localThrowable);
/*     */       }
/* 245 */       throw new ClassNotFoundException(localThrowable.getMessage(), localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean hasGetterSignature(Method paramMethod)
/*     */   {
/* 257 */     if (Modifier.isStatic(paramMethod.getModifiers())) {
/* 258 */       return false;
/*     */     }
/*     */     
/* 261 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 262 */     if ((arrayOfClass != null) && (arrayOfClass.length != 0)) {
/* 263 */       return false;
/*     */     }
/*     */     
/* 266 */     if (Void.TYPE == paramMethod.getReturnType()) {
/* 267 */       return false;
/*     */     }
/*     */     
/* 270 */     return true;
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
/*     */ 
/*     */   public static Throwable getRootCause(Throwable paramThrowable)
/*     */   {
/* 285 */     while (paramThrowable.getCause() != null) {
/* 286 */       paramThrowable = paramThrowable.getCause();
/*     */     }
/* 288 */     return paramThrowable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void throwRootCause(Throwable paramThrowable)
/*     */     throws Exception
/*     */   {
/* 299 */     paramThrowable = getRootCause(paramThrowable);
/* 300 */     if ((paramThrowable instanceof Exception)) {
/* 301 */       throw ((Exception)paramThrowable);
/*     */     }
/* 303 */     throw ((Error)paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void throwAsIAE(Throwable paramThrowable)
/*     */   {
/* 312 */     throwAsIAE(paramThrowable, paramThrowable.getMessage());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void throwAsIAE(Throwable paramThrowable, String paramString)
/*     */   {
/* 322 */     if ((paramThrowable instanceof RuntimeException)) {
/* 323 */       throw ((RuntimeException)paramThrowable);
/*     */     }
/* 325 */     if ((paramThrowable instanceof Error)) {
/* 326 */       throw ((Error)paramThrowable);
/*     */     }
/* 328 */     throw new IllegalArgumentException(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unwrapAndThrowAsIAE(Throwable paramThrowable)
/*     */   {
/* 338 */     throwAsIAE(getRootCause(paramThrowable));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unwrapAndThrowAsIAE(Throwable paramThrowable, String paramString)
/*     */   {
/* 348 */     throwAsIAE(getRootCause(paramThrowable), paramString);
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
/*     */   public static <T> T createInstance(Class<T> paramClass, boolean paramBoolean)
/*     */     throws IllegalArgumentException
/*     */   {
/* 373 */     Constructor localConstructor = findConstructor(paramClass, paramBoolean);
/* 374 */     if (localConstructor == null) {
/* 375 */       throw new IllegalArgumentException("Class " + paramClass.getName() + " has no default (no arg) constructor");
/*     */     }
/*     */     try {
/* 378 */       return (T)localConstructor.newInstance(new Object[0]);
/*     */     } catch (Exception localException) {
/* 380 */       unwrapAndThrowAsIAE(localException, "Failed to instantiate class " + paramClass.getName() + ", problem: " + localException.getMessage()); }
/* 381 */     return null;
/*     */   }
/*     */   
/*     */   public static <T> Constructor<T> findConstructor(Class<T> paramClass, boolean paramBoolean)
/*     */     throws IllegalArgumentException
/*     */   {
/*     */     try
/*     */     {
/* 389 */       Constructor localConstructor = paramClass.getDeclaredConstructor(new Class[0]);
/* 390 */       if (paramBoolean) {
/* 391 */         checkAndFixAccess(localConstructor);
/*     */ 
/*     */       }
/* 394 */       else if (!Modifier.isPublic(localConstructor.getModifiers())) {
/* 395 */         throw new IllegalArgumentException("Default constructor for " + paramClass.getName() + " is not accessible (non-public?): not allowed to try modify access via Reflection: can not instantiate type");
/*     */       }
/*     */       
/* 398 */       return localConstructor;
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException) {}catch (Exception localException)
/*     */     {
/* 402 */       unwrapAndThrowAsIAE(localException, "Failed to find default constructor of class " + paramClass.getName() + ", problem: " + localException.getMessage());
/*     */     }
/* 404 */     return null;
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
/*     */ 
/*     */   public static Object defaultValue(Class<?> paramClass)
/*     */   {
/* 419 */     if (paramClass == Integer.TYPE) {
/* 420 */       return Integer.valueOf(0);
/*     */     }
/* 422 */     if (paramClass == Long.TYPE) {
/* 423 */       return Long.valueOf(0L);
/*     */     }
/* 425 */     if (paramClass == Boolean.TYPE) {
/* 426 */       return Boolean.FALSE;
/*     */     }
/* 428 */     if (paramClass == Double.TYPE) {
/* 429 */       return Double.valueOf(0.0D);
/*     */     }
/* 431 */     if (paramClass == Float.TYPE) {
/* 432 */       return Float.valueOf(0.0F);
/*     */     }
/* 434 */     if (paramClass == Byte.TYPE) {
/* 435 */       return Byte.valueOf((byte)0);
/*     */     }
/* 437 */     if (paramClass == Short.TYPE) {
/* 438 */       return Short.valueOf((short)0);
/*     */     }
/* 440 */     if (paramClass == Character.TYPE) {
/* 441 */       return Character.valueOf('\000');
/*     */     }
/* 443 */     throw new IllegalArgumentException("Class " + paramClass.getName() + " is not a primitive type");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<?> wrapperType(Class<?> paramClass)
/*     */   {
/* 452 */     if (paramClass == Integer.TYPE) {
/* 453 */       return Integer.class;
/*     */     }
/* 455 */     if (paramClass == Long.TYPE) {
/* 456 */       return Long.class;
/*     */     }
/* 458 */     if (paramClass == Boolean.TYPE) {
/* 459 */       return Boolean.class;
/*     */     }
/* 461 */     if (paramClass == Double.TYPE) {
/* 462 */       return Double.class;
/*     */     }
/* 464 */     if (paramClass == Float.TYPE) {
/* 465 */       return Float.class;
/*     */     }
/* 467 */     if (paramClass == Byte.TYPE) {
/* 468 */       return Byte.class;
/*     */     }
/* 470 */     if (paramClass == Short.TYPE) {
/* 471 */       return Short.class;
/*     */     }
/* 473 */     if (paramClass == Character.TYPE) {
/* 474 */       return Character.class;
/*     */     }
/* 476 */     throw new IllegalArgumentException("Class " + paramClass.getName() + " is not a primitive type");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void checkAndFixAccess(Member paramMember)
/*     */   {
/* 494 */     AccessibleObject localAccessibleObject = (AccessibleObject)paramMember;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 502 */       localAccessibleObject.setAccessible(true);
/*     */ 
/*     */     }
/*     */     catch (SecurityException localSecurityException)
/*     */     {
/*     */ 
/* 508 */       if (!localAccessibleObject.isAccessible()) {
/* 509 */         Class localClass = paramMember.getDeclaringClass();
/* 510 */         throw new IllegalArgumentException("Can not access " + paramMember + " (from class " + localClass.getName() + "; failed to set access: " + localSecurityException.getMessage());
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<? extends Enum<?>> findEnumType(EnumSet<?> paramEnumSet)
/*     */   {
/* 531 */     if (!paramEnumSet.isEmpty()) {
/* 532 */       return findEnumType((Enum)paramEnumSet.iterator().next());
/*     */     }
/*     */     
/* 535 */     return EnumTypeLocator.instance.enumTypeFor(paramEnumSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<? extends Enum<?>> findEnumType(EnumMap<?, ?> paramEnumMap)
/*     */   {
/* 546 */     if (!paramEnumMap.isEmpty()) {
/* 547 */       return findEnumType((Enum)paramEnumMap.keySet().iterator().next());
/*     */     }
/*     */     
/* 550 */     return EnumTypeLocator.instance.enumTypeFor(paramEnumMap);
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
/*     */   public static Class<? extends Enum<?>> findEnumType(Enum<?> paramEnum)
/*     */   {
/* 563 */     Class localClass = paramEnum.getClass();
/* 564 */     if (localClass.getSuperclass() != Enum.class) {
/* 565 */       localClass = localClass.getSuperclass();
/*     */     }
/* 567 */     return localClass;
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
/*     */   public static Class<? extends Enum<?>> findEnumType(Class<?> paramClass)
/*     */   {
/* 580 */     if (paramClass.getSuperclass() != Enum.class) {
/* 581 */       paramClass = paramClass.getSuperclass();
/*     */     }
/* 583 */     return paramClass;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isJacksonStdImpl(Object paramObject)
/*     */   {
/* 600 */     return (paramObject != null) && (isJacksonStdImpl(paramObject.getClass()));
/*     */   }
/*     */   
/*     */   public static boolean isJacksonStdImpl(Class<?> paramClass) {
/* 604 */     return paramClass.getAnnotation(JacksonStdImpl.class) != null;
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
/*     */ 
/*     */   private static class EnumTypeLocator
/*     */   {
/* 619 */     static final EnumTypeLocator instance = new EnumTypeLocator();
/*     */     
/*     */     private final Field enumSetTypeField;
/*     */     
/*     */     private final Field enumMapTypeField;
/*     */     
/*     */ 
/*     */     private EnumTypeLocator()
/*     */     {
/* 628 */       this.enumSetTypeField = locateField(EnumSet.class, "elementType", Class.class);
/* 629 */       this.enumMapTypeField = locateField(EnumMap.class, "elementType", Class.class);
/*     */     }
/*     */     
/*     */ 
/*     */     public Class<? extends Enum<?>> enumTypeFor(EnumSet<?> paramEnumSet)
/*     */     {
/* 635 */       if (this.enumSetTypeField != null) {
/* 636 */         return (Class)get(paramEnumSet, this.enumSetTypeField);
/*     */       }
/* 638 */       throw new IllegalStateException("Can not figure out type for EnumSet (odd JDK platform?)");
/*     */     }
/*     */     
/*     */ 
/*     */     public Class<? extends Enum<?>> enumTypeFor(EnumMap<?, ?> paramEnumMap)
/*     */     {
/* 644 */       if (this.enumMapTypeField != null) {
/* 645 */         return (Class)get(paramEnumMap, this.enumMapTypeField);
/*     */       }
/* 647 */       throw new IllegalStateException("Can not figure out type for EnumMap (odd JDK platform?)");
/*     */     }
/*     */     
/*     */     private Object get(Object paramObject, Field paramField)
/*     */     {
/*     */       try {
/* 653 */         return paramField.get(paramObject);
/*     */       } catch (Exception localException) {
/* 655 */         throw new IllegalArgumentException(localException);
/*     */       }
/*     */     }
/*     */     
/*     */     private static Field locateField(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*     */     {
/* 661 */       Object localObject = null;
/*     */       
/* 663 */       Field[] arrayOfField1 = paramClass1.getDeclaredFields();
/* 664 */       Field localField; for (localField : arrayOfField1) {
/* 665 */         if ((paramString.equals(localField.getName())) && (localField.getType() == paramClass2)) {
/* 666 */           localObject = localField;
/* 667 */           break;
/*     */         }
/*     */       }
/*     */       
/* 671 */       if (localObject == null) {
/* 672 */         for (localField : arrayOfField1) {
/* 673 */           if (localField.getType() == paramClass2)
/*     */           {
/* 675 */             if (localObject != null) return null;
/* 676 */             localObject = localField;
/*     */           }
/*     */         }
/*     */       }
/* 680 */       if (localObject != null) {
/*     */         try {
/* 682 */           ((Field)localObject).setAccessible(true);
/*     */         } catch (Throwable localThrowable) {}
/*     */       }
/* 685 */       return (Field)localObject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/ClassUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */