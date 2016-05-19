/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumResolver<T extends Enum<T>>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Class<T> _enumClass;
/*     */   protected final T[] _enums;
/*     */   protected final HashMap<String, T> _enumsById;
/*     */   
/*     */   protected EnumResolver(Class<T> paramClass, T[] paramArrayOfT, HashMap<String, T> paramHashMap)
/*     */   {
/*  25 */     this._enumClass = paramClass;
/*  26 */     this._enums = paramArrayOfT;
/*  27 */     this._enumsById = paramHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <ET extends Enum<ET>> EnumResolver<ET> constructFor(Class<ET> paramClass, AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/*  36 */     Enum[] arrayOfEnum1 = (Enum[])paramClass.getEnumConstants();
/*  37 */     if (arrayOfEnum1 == null) {
/*  38 */       throw new IllegalArgumentException("No enum constants for class " + paramClass.getName());
/*     */     }
/*  40 */     HashMap localHashMap = new HashMap();
/*  41 */     for (Enum localEnum : arrayOfEnum1) {
/*  42 */       localHashMap.put(paramAnnotationIntrospector.findEnumValue(localEnum), localEnum);
/*     */     }
/*  44 */     return new EnumResolver(paramClass, arrayOfEnum1, localHashMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <ET extends Enum<ET>> EnumResolver<ET> constructUsingToString(Class<ET> paramClass)
/*     */   {
/*  53 */     Enum[] arrayOfEnum = (Enum[])paramClass.getEnumConstants();
/*  54 */     HashMap localHashMap = new HashMap();
/*     */     
/*  56 */     int i = arrayOfEnum.length; for (;;) { i--; if (i < 0) break;
/*  57 */       Enum localEnum = arrayOfEnum[i];
/*  58 */       localHashMap.put(localEnum.toString(), localEnum);
/*     */     }
/*  60 */     return new EnumResolver(paramClass, arrayOfEnum, localHashMap);
/*     */   }
/*     */   
/*     */ 
/*     */   public static <ET extends Enum<ET>> EnumResolver<ET> constructUsingMethod(Class<ET> paramClass, Method paramMethod)
/*     */   {
/*  66 */     Enum[] arrayOfEnum = (Enum[])paramClass.getEnumConstants();
/*  67 */     HashMap localHashMap = new HashMap();
/*     */     
/*  69 */     int i = arrayOfEnum.length; for (;;) { i--; if (i < 0) break;
/*  70 */       Enum localEnum = arrayOfEnum[i];
/*     */       try {
/*  72 */         Object localObject = paramMethod.invoke(localEnum, new Object[0]);
/*  73 */         if (localObject != null) {
/*  74 */           localHashMap.put(localObject.toString(), localEnum);
/*     */         }
/*     */       } catch (Exception localException) {
/*  77 */         throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + localEnum + ": " + localException.getMessage());
/*     */       }
/*     */     }
/*  80 */     return new EnumResolver(paramClass, arrayOfEnum, localHashMap);
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
/*     */   public static EnumResolver<?> constructUnsafe(Class<?> paramClass, AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/*  93 */     Class<?> localClass = paramClass;
/*  94 */     return constructFor(localClass, paramAnnotationIntrospector);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static EnumResolver<?> constructUnsafeUsingToString(Class<?> paramClass)
/*     */   {
/* 105 */     Class<?> localClass = paramClass;
/* 106 */     return constructUsingToString(localClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static EnumResolver<?> constructUnsafeUsingMethod(Class<?> paramClass, Method paramMethod)
/*     */   {
/* 117 */     Class<?> localClass = paramClass;
/* 118 */     return constructUsingMethod(localClass, paramMethod);
/*     */   }
/*     */   
/*     */   public T findEnum(String paramString)
/*     */   {
/* 123 */     return (Enum)this._enumsById.get(paramString);
/*     */   }
/*     */   
/*     */   public T getEnum(int paramInt)
/*     */   {
/* 128 */     if ((paramInt < 0) || (paramInt >= this._enums.length)) {
/* 129 */       return null;
/*     */     }
/* 131 */     return this._enums[paramInt];
/*     */   }
/*     */   
/* 134 */   public Class<T> getEnumClass() { return this._enumClass; }
/*     */   
/* 136 */   public int lastValidIndex() { return this._enums.length - 1; }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/EnumResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */