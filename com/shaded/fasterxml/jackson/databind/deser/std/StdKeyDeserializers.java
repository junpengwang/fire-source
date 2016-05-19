/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.KeyDeserializers;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.EnumResolver;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
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
/*     */ public class StdKeyDeserializers
/*     */   implements KeyDeserializers, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 923268084968181479L;
/*     */   
/*     */   @Deprecated
/*     */   public static KeyDeserializer constructStringKeyDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*     */   {
/*  40 */     return StdKeyDeserializer.StringKD.forType(paramJavaType.getRawClass());
/*     */   }
/*     */   
/*     */   public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> paramEnumResolver) {
/*  44 */     return new StdKeyDeserializer.EnumKD(paramEnumResolver, null);
/*     */   }
/*     */   
/*     */   public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> paramEnumResolver, AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/*  49 */     return new StdKeyDeserializer.EnumKD(paramEnumResolver, paramAnnotatedMethod);
/*     */   }
/*     */   
/*     */ 
/*     */   public static KeyDeserializer constructDelegatingKeyDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  55 */     return new StdKeyDeserializer.DelegatingKD(paramJavaType.getRawClass(), paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static KeyDeserializer findStringBasedKeyDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*     */   {
/*  64 */     BeanDescription localBeanDescription = paramDeserializationConfig.introspect(paramJavaType);
/*     */     
/*  66 */     Constructor localConstructor = localBeanDescription.findSingleArgConstructor(new Class[] { String.class });
/*  67 */     if (localConstructor != null) {
/*  68 */       if (paramDeserializationConfig.canOverrideAccessModifiers()) {
/*  69 */         ClassUtil.checkAndFixAccess(localConstructor);
/*     */       }
/*  71 */       return new StdKeyDeserializer.StringCtorKeyDeserializer(localConstructor);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  76 */     Method localMethod = localBeanDescription.findFactoryMethod(new Class[] { String.class });
/*  77 */     if (localMethod != null) {
/*  78 */       if (paramDeserializationConfig.canOverrideAccessModifiers()) {
/*  79 */         ClassUtil.checkAndFixAccess(localMethod);
/*     */       }
/*  81 */       return new StdKeyDeserializer.StringFactoryKeyDeserializer(localMethod);
/*     */     }
/*     */     
/*  84 */     return null;
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
/*     */   public KeyDeserializer findKeyDeserializer(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/*  98 */     Class localClass = paramJavaType.getRawClass();
/*     */     
/* 100 */     if ((localClass == String.class) || (localClass == Object.class)) {
/* 101 */       return StdKeyDeserializer.StringKD.forType(localClass);
/*     */     }
/* 103 */     if (localClass == UUID.class) {
/* 104 */       return new StdKeyDeserializer.UuidKD();
/*     */     }
/*     */     
/*     */ 
/* 108 */     if (localClass.isPrimitive()) {
/* 109 */       localClass = ClassUtil.wrapperType(localClass);
/*     */     }
/*     */     
/* 112 */     if (localClass == Integer.class) {
/* 113 */       return new StdKeyDeserializer.IntKD();
/*     */     }
/* 115 */     if (localClass == Long.class) {
/* 116 */       return new StdKeyDeserializer.LongKD();
/*     */     }
/* 118 */     if (localClass == Date.class) {
/* 119 */       return new StdKeyDeserializer.DateKD();
/*     */     }
/* 121 */     if (localClass == Calendar.class) {
/* 122 */       return new StdKeyDeserializer.CalendarKD();
/*     */     }
/*     */     
/*     */ 
/* 126 */     if (localClass == Boolean.class) {
/* 127 */       return new StdKeyDeserializer.BoolKD();
/*     */     }
/* 129 */     if (localClass == Byte.class) {
/* 130 */       return new StdKeyDeserializer.ByteKD();
/*     */     }
/* 132 */     if (localClass == Character.class) {
/* 133 */       return new StdKeyDeserializer.CharKD();
/*     */     }
/* 135 */     if (localClass == Short.class) {
/* 136 */       return new StdKeyDeserializer.ShortKD();
/*     */     }
/* 138 */     if (localClass == Float.class) {
/* 139 */       return new StdKeyDeserializer.FloatKD();
/*     */     }
/* 141 */     if (localClass == Double.class) {
/* 142 */       return new StdKeyDeserializer.DoubleKD();
/*     */     }
/* 144 */     if (localClass == Locale.class) {
/* 145 */       return new StdKeyDeserializer.LocaleKD();
/*     */     }
/* 147 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StdKeyDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */