/*     */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StdSubtypeResolver
/*     */   extends SubtypeResolver
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected LinkedHashSet<NamedType> _registeredSubtypes;
/*     */   
/*     */   public void registerSubtypes(NamedType... paramVarArgs)
/*     */   {
/*  34 */     if (this._registeredSubtypes == null) {
/*  35 */       this._registeredSubtypes = new LinkedHashSet();
/*     */     }
/*  37 */     for (NamedType localNamedType : paramVarArgs) {
/*  38 */       this._registeredSubtypes.add(localNamedType);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void registerSubtypes(Class<?>... paramVarArgs)
/*     */   {
/*  45 */     NamedType[] arrayOfNamedType = new NamedType[paramVarArgs.length];
/*  46 */     int i = 0; for (int j = paramVarArgs.length; i < j; i++) {
/*  47 */       arrayOfNamedType[i] = new NamedType(paramVarArgs[i]);
/*     */     }
/*  49 */     registerSubtypes(arrayOfNamedType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember paramAnnotatedMember, MapperConfig<?> paramMapperConfig, AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/*  60 */     return collectAndResolveSubtypes(paramAnnotatedMember, paramMapperConfig, paramAnnotationIntrospector, null);
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
/*     */   public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember paramAnnotatedMember, MapperConfig<?> paramMapperConfig, AnnotationIntrospector paramAnnotationIntrospector, JavaType paramJavaType)
/*     */   {
/*  75 */     Class localClass = paramJavaType == null ? paramAnnotatedMember.getRawType() : paramJavaType.getRawClass();
/*     */     
/*  77 */     HashMap localHashMap = new HashMap();
/*     */     
/*  79 */     if (this._registeredSubtypes != null) {
/*  80 */       for (localObject1 = this._registeredSubtypes.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (NamedType)((Iterator)localObject1).next();
/*     */         
/*  82 */         if (localClass.isAssignableFrom(((NamedType)localObject2).getType())) {
/*  83 */           localObject3 = AnnotatedClass.constructWithoutSuperTypes(((NamedType)localObject2).getType(), paramAnnotationIntrospector, paramMapperConfig);
/*  84 */           _collectAndResolve((AnnotatedClass)localObject3, (NamedType)localObject2, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  90 */     Object localObject1 = paramAnnotationIntrospector.findSubtypes(paramAnnotatedMember);
/*  91 */     if (localObject1 != null) {
/*  92 */       for (localObject2 = ((Collection)localObject1).iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (NamedType)((Iterator)localObject2).next();
/*  93 */         AnnotatedClass localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(((NamedType)localObject3).getType(), paramAnnotationIntrospector, paramMapperConfig);
/*  94 */         _collectAndResolve(localAnnotatedClass, (NamedType)localObject3, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
/*     */       }
/*     */     }
/*     */     
/*  98 */     Object localObject2 = new NamedType(localClass, null);
/*  99 */     Object localObject3 = AnnotatedClass.constructWithoutSuperTypes(localClass, paramAnnotationIntrospector, paramMapperConfig);
/*     */     
/*     */ 
/* 102 */     _collectAndResolve((AnnotatedClass)localObject3, (NamedType)localObject2, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
/* 103 */     return new ArrayList(localHashMap.values());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass paramAnnotatedClass, MapperConfig<?> paramMapperConfig, AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 110 */     HashMap localHashMap = new HashMap();
/*     */     
/* 112 */     if (this._registeredSubtypes != null) {
/* 113 */       localObject = paramAnnotatedClass.getRawType();
/* 114 */       for (NamedType localNamedType : this._registeredSubtypes)
/*     */       {
/* 116 */         if (((Class)localObject).isAssignableFrom(localNamedType.getType())) {
/* 117 */           AnnotatedClass localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(localNamedType.getType(), paramAnnotationIntrospector, paramMapperConfig);
/* 118 */           _collectAndResolve(localAnnotatedClass, localNamedType, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 123 */     Object localObject = new NamedType(paramAnnotatedClass.getRawType(), null);
/* 124 */     _collectAndResolve(paramAnnotatedClass, (NamedType)localObject, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
/* 125 */     return new ArrayList(localHashMap.values());
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
/*     */   protected void _collectAndResolve(AnnotatedClass paramAnnotatedClass, NamedType paramNamedType, MapperConfig<?> paramMapperConfig, AnnotationIntrospector paramAnnotationIntrospector, HashMap<NamedType, NamedType> paramHashMap)
/*     */   {
/* 141 */     if (!paramNamedType.hasName()) {
/* 142 */       localObject = paramAnnotationIntrospector.findTypeName(paramAnnotatedClass);
/* 143 */       if (localObject != null) {
/* 144 */         paramNamedType = new NamedType(paramNamedType.getType(), (String)localObject);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 149 */     if (paramHashMap.containsKey(paramNamedType))
/*     */     {
/* 151 */       if (paramNamedType.hasName()) {
/* 152 */         localObject = (NamedType)paramHashMap.get(paramNamedType);
/* 153 */         if (!((NamedType)localObject).hasName()) {
/* 154 */           paramHashMap.put(paramNamedType, paramNamedType);
/*     */         }
/*     */       }
/* 157 */       return;
/*     */     }
/*     */     
/* 160 */     paramHashMap.put(paramNamedType, paramNamedType);
/* 161 */     Object localObject = paramAnnotationIntrospector.findSubtypes(paramAnnotatedClass);
/* 162 */     if ((localObject != null) && (!((Collection)localObject).isEmpty())) {
/* 163 */       for (NamedType localNamedType : (Collection)localObject) {
/* 164 */         AnnotatedClass localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(localNamedType.getType(), paramAnnotationIntrospector, paramMapperConfig);
/*     */         
/* 166 */         if (!localNamedType.hasName()) {
/* 167 */           localNamedType = new NamedType(localNamedType.getType(), paramAnnotationIntrospector.findTypeName(localAnnotatedClass));
/*     */         }
/* 169 */         _collectAndResolve(localAnnotatedClass, localNamedType, paramMapperConfig, paramAnnotationIntrospector, paramHashMap);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/StdSubtypeResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */