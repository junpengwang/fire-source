/*     */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ public class TypeNameIdResolver
/*     */   extends TypeIdResolverBase
/*     */ {
/*     */   protected final MapperConfig<?> _config;
/*     */   protected final HashMap<String, String> _typeToId;
/*     */   protected final HashMap<String, JavaType> _idToType;
/*     */   
/*     */   protected TypeNameIdResolver(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, HashMap<String, String> paramHashMap, HashMap<String, JavaType> paramHashMap1)
/*     */   {
/*  30 */     super(paramJavaType, paramMapperConfig.getTypeFactory());
/*  31 */     this._config = paramMapperConfig;
/*  32 */     this._typeToId = paramHashMap;
/*  33 */     this._idToType = paramHashMap1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static TypeNameIdResolver construct(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, Collection<NamedType> paramCollection, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  41 */     if (paramBoolean1 == paramBoolean2) throw new IllegalArgumentException();
/*  42 */     HashMap localHashMap1 = null;
/*  43 */     HashMap localHashMap2 = null;
/*     */     
/*  45 */     if (paramBoolean1) {
/*  46 */       localHashMap1 = new HashMap();
/*     */     }
/*  48 */     if (paramBoolean2) {
/*  49 */       localHashMap2 = new HashMap();
/*     */     }
/*  51 */     if (paramCollection != null) {
/*  52 */       for (NamedType localNamedType : paramCollection)
/*     */       {
/*     */ 
/*     */ 
/*  56 */         Class localClass = localNamedType.getType();
/*  57 */         String str = localNamedType.hasName() ? localNamedType.getName() : _defaultTypeId(localClass);
/*  58 */         if (paramBoolean1) {
/*  59 */           localHashMap1.put(localClass.getName(), str);
/*     */         }
/*  61 */         if (paramBoolean2)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*  66 */           JavaType localJavaType = (JavaType)localHashMap2.get(str);
/*  67 */           if ((localJavaType == null) || 
/*  68 */             (!localClass.isAssignableFrom(localJavaType.getRawClass())))
/*     */           {
/*     */ 
/*     */ 
/*  72 */             localHashMap2.put(str, paramMapperConfig.constructType(localClass)); }
/*     */         }
/*     */       }
/*     */     }
/*  76 */     return new TypeNameIdResolver(paramMapperConfig, paramJavaType, localHashMap1, localHashMap2);
/*     */   }
/*     */   
/*     */   public JsonTypeInfo.Id getMechanism() {
/*  80 */     return JsonTypeInfo.Id.NAME;
/*     */   }
/*     */   
/*     */   public String idFromValue(Object paramObject)
/*     */   {
/*  85 */     Class localClass = paramObject.getClass();
/*  86 */     String str1 = localClass.getName();
/*     */     String str2;
/*  88 */     synchronized (this._typeToId) {
/*  89 */       str2 = (String)this._typeToId.get(str1);
/*  90 */       if (str2 == null)
/*     */       {
/*     */ 
/*  93 */         if (this._config.isAnnotationProcessingEnabled()) {
/*  94 */           BeanDescription localBeanDescription = this._config.introspectClassAnnotations(localClass);
/*  95 */           str2 = this._config.getAnnotationIntrospector().findTypeName(localBeanDescription.getClassInfo());
/*     */         }
/*  97 */         if (str2 == null)
/*     */         {
/*  99 */           str2 = _defaultTypeId(localClass);
/*     */         }
/* 101 */         this._typeToId.put(str1, str2);
/*     */       }
/*     */     }
/* 104 */     return str2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String idFromValueAndType(Object paramObject, Class<?> paramClass)
/*     */   {
/* 113 */     if (paramObject == null) {
/* 114 */       return null;
/*     */     }
/* 116 */     return idFromValue(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */   public JavaType typeFromId(String paramString)
/*     */     throws IllegalArgumentException
/*     */   {
/* 123 */     JavaType localJavaType = (JavaType)this._idToType.get(paramString);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 129 */     return localJavaType;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 135 */     StringBuilder localStringBuilder = new StringBuilder();
/* 136 */     localStringBuilder.append('[').append(getClass().getName());
/* 137 */     localStringBuilder.append("; id-to-type=").append(this._idToType);
/* 138 */     localStringBuilder.append(']');
/* 139 */     return localStringBuilder.toString();
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
/*     */   protected static String _defaultTypeId(Class<?> paramClass)
/*     */   {
/* 154 */     String str = paramClass.getName();
/* 155 */     int i = str.lastIndexOf('.');
/* 156 */     return i < 0 ? str : str.substring(i + 1);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/TypeNameIdResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */