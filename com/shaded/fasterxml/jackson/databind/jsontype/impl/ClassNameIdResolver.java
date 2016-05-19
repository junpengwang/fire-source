/*     */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassNameIdResolver
/*     */   extends TypeIdResolverBase
/*     */ {
/*     */   public ClassNameIdResolver(JavaType paramJavaType, TypeFactory paramTypeFactory)
/*     */   {
/*  20 */     super(paramJavaType, paramTypeFactory);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.Id getMechanism() {
/*  24 */     return JsonTypeInfo.Id.CLASS;
/*     */   }
/*     */   
/*     */ 
/*     */   public void registerSubtype(Class<?> paramClass, String paramString) {}
/*     */   
/*     */   public String idFromValue(Object paramObject)
/*     */   {
/*  32 */     return _idFrom(paramObject, paramObject.getClass());
/*     */   }
/*     */   
/*     */   public String idFromValueAndType(Object paramObject, Class<?> paramClass)
/*     */   {
/*  37 */     return _idFrom(paramObject, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType typeFromId(String paramString)
/*     */   {
/*     */     Object localObject;
/*     */     
/*     */ 
/*  47 */     if (paramString.indexOf('<') > 0) {
/*  48 */       localObject = this._typeFactory.constructFromCanonical(paramString);
/*     */       
/*  50 */       return (JavaType)localObject;
/*     */     }
/*     */     try {
/*  53 */       localObject = ClassUtil.findClass(paramString);
/*  54 */       return this._typeFactory.constructSpecializedType(this._baseType, (Class)localObject);
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/*  56 */       throw new IllegalArgumentException("Invalid type id '" + paramString + "' (for id type 'Id.class'): no such class found");
/*     */     } catch (Exception localException) {
/*  58 */       throw new IllegalArgumentException("Invalid type id '" + paramString + "' (for id type 'Id.class'): " + localException.getMessage(), localException);
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
/*     */   protected final String _idFrom(Object paramObject, Class<?> paramClass)
/*     */   {
/*  71 */     if ((Enum.class.isAssignableFrom(paramClass)) && 
/*  72 */       (!paramClass.isEnum())) {
/*  73 */       paramClass = paramClass.getSuperclass();
/*     */     }
/*     */     
/*  76 */     String str = paramClass.getName();
/*  77 */     Object localObject; Class localClass; if (str.startsWith("java.util"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */       if ((paramObject instanceof EnumSet)) {
/*  87 */         localObject = ClassUtil.findEnumType((EnumSet)paramObject);
/*     */         
/*  89 */         str = TypeFactory.defaultInstance().constructCollectionType(EnumSet.class, (Class)localObject).toCanonical();
/*  90 */       } else if ((paramObject instanceof EnumMap)) {
/*  91 */         localObject = ClassUtil.findEnumType((EnumMap)paramObject);
/*  92 */         localClass = Object.class;
/*     */         
/*  94 */         str = TypeFactory.defaultInstance().constructMapType(EnumMap.class, (Class)localObject, localClass).toCanonical();
/*     */       } else {
/*  96 */         localObject = str.substring(9);
/*  97 */         if (((((String)localObject).startsWith(".Arrays$")) || (((String)localObject).startsWith(".Collections$"))) && (str.indexOf("List") >= 0))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 105 */           str = "java.util.ArrayList";
/*     */         }
/*     */       }
/* 108 */     } else if (str.indexOf('$') >= 0)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 116 */       localObject = ClassUtil.getOuterClass(paramClass);
/* 117 */       if (localObject != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 122 */         localClass = this._baseType.getRawClass();
/* 123 */         if (ClassUtil.getOuterClass(localClass) == null)
/*     */         {
/* 125 */           paramClass = this._baseType.getRawClass();
/* 126 */           str = paramClass.getName();
/*     */         }
/*     */       }
/*     */     }
/* 130 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/ClassNameIdResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */