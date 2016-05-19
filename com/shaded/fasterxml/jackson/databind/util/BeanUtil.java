/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
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
/*     */ public class BeanUtil
/*     */ {
/*     */   public static String okNameForGetter(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/*  19 */     String str1 = paramAnnotatedMethod.getName();
/*  20 */     String str2 = okNameForIsGetter(paramAnnotatedMethod, str1);
/*  21 */     if (str2 == null) {
/*  22 */       str2 = okNameForRegularGetter(paramAnnotatedMethod, str1);
/*     */     }
/*  24 */     return str2;
/*     */   }
/*     */   
/*     */   public static String okNameForRegularGetter(AnnotatedMethod paramAnnotatedMethod, String paramString)
/*     */   {
/*  29 */     if (paramString.startsWith("get"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  37 */       if ("getCallbacks".equals(paramString)) {
/*  38 */         if (isCglibGetCallbacks(paramAnnotatedMethod)) {
/*  39 */           return null;
/*     */         }
/*  41 */       } else if ("getMetaClass".equals(paramString))
/*     */       {
/*     */ 
/*     */ 
/*  45 */         if (isGroovyMetaClassGetter(paramAnnotatedMethod)) {
/*  46 */           return null;
/*     */         }
/*     */       }
/*  49 */       return manglePropertyName(paramString.substring(3));
/*     */     }
/*  51 */     return null;
/*     */   }
/*     */   
/*     */   public static String okNameForIsGetter(AnnotatedMethod paramAnnotatedMethod, String paramString)
/*     */   {
/*  56 */     if (paramString.startsWith("is"))
/*     */     {
/*  58 */       Class localClass = paramAnnotatedMethod.getRawType();
/*  59 */       if ((localClass != Boolean.class) && (localClass != Boolean.TYPE)) {
/*  60 */         return null;
/*     */       }
/*  62 */       return manglePropertyName(paramString.substring(2));
/*     */     }
/*     */     
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public static String okNameForSetter(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/*  70 */     String str = okNameForMutator(paramAnnotatedMethod, "set");
/*  71 */     if (str != null)
/*     */     {
/*  73 */       if (("metaClass".equals(str)) && 
/*  74 */         (isGroovyMetaClassSetter(paramAnnotatedMethod))) {
/*  75 */         return null;
/*     */       }
/*     */       
/*  78 */       return str;
/*     */     }
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public static String okNameForMutator(AnnotatedMethod paramAnnotatedMethod, String paramString)
/*     */   {
/*  85 */     String str = paramAnnotatedMethod.getName();
/*  86 */     if (str.startsWith(paramString)) {
/*  87 */       return manglePropertyName(str.substring(paramString.length()));
/*     */     }
/*  89 */     return null;
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
/*     */   protected static boolean isCglibGetCallbacks(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 111 */     Class localClass1 = paramAnnotatedMethod.getRawType();
/*     */     
/* 113 */     if ((localClass1 == null) || (!localClass1.isArray())) {
/* 114 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 120 */     Class localClass2 = localClass1.getComponentType();
/*     */     
/* 122 */     Package localPackage = localClass2.getPackage();
/* 123 */     if (localPackage != null) {
/* 124 */       String str = localPackage.getName();
/* 125 */       if ((str.startsWith("net.sf.cglib")) || (str.startsWith("org.hibernate.repackage.cglib")))
/*     */       {
/*     */ 
/* 128 */         return true;
/*     */       }
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static boolean isGroovyMetaClassSetter(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 140 */     Class localClass = paramAnnotatedMethod.getRawParameterType(0);
/* 141 */     Package localPackage = localClass.getPackage();
/* 142 */     if ((localPackage != null) && (localPackage.getName().startsWith("groovy.lang"))) {
/* 143 */       return true;
/*     */     }
/* 145 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static boolean isGroovyMetaClassGetter(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 153 */     Class localClass = paramAnnotatedMethod.getRawType();
/* 154 */     if ((localClass == null) || (localClass.isArray())) {
/* 155 */       return false;
/*     */     }
/* 157 */     Package localPackage = localClass.getPackage();
/* 158 */     if ((localPackage != null) && (localPackage.getName().startsWith("groovy.lang"))) {
/* 159 */       return true;
/*     */     }
/* 161 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static String manglePropertyName(String paramString)
/*     */   {
/* 173 */     int i = paramString.length();
/*     */     
/*     */ 
/* 176 */     if (i == 0) {
/* 177 */       return null;
/*     */     }
/*     */     
/* 180 */     StringBuilder localStringBuilder = null;
/* 181 */     for (int j = 0; j < i; j++) {
/* 182 */       char c1 = paramString.charAt(j);
/* 183 */       char c2 = Character.toLowerCase(c1);
/* 184 */       if (c1 == c2) {
/*     */         break;
/*     */       }
/* 187 */       if (localStringBuilder == null) {
/* 188 */         localStringBuilder = new StringBuilder(paramString);
/*     */       }
/* 190 */       localStringBuilder.setCharAt(j, c2);
/*     */     }
/* 192 */     return localStringBuilder == null ? paramString : localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/BeanUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */