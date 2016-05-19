/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.type.SimpleType;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicClassIntrospector
/*     */   extends ClassIntrospector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected static final BasicBeanDescription STRING_DESC;
/*     */   protected static final BasicBeanDescription BOOLEAN_DESC;
/*     */   protected static final BasicBeanDescription INT_DESC;
/*     */   protected static final BasicBeanDescription LONG_DESC;
/*     */   
/*     */   static
/*     */   {
/*  26 */     AnnotatedClass localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(String.class, null, null);
/*  27 */     STRING_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(String.class), localAnnotatedClass);
/*     */     
/*     */ 
/*     */ 
/*  31 */     localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(Boolean.TYPE, null, null);
/*  32 */     BOOLEAN_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Boolean.TYPE), localAnnotatedClass);
/*     */     
/*     */ 
/*     */ 
/*  36 */     localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(Integer.TYPE, null, null);
/*  37 */     INT_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Integer.TYPE), localAnnotatedClass);
/*     */     
/*     */ 
/*     */ 
/*  41 */     localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(Long.TYPE, null, null);
/*  42 */     LONG_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Long.TYPE), localAnnotatedClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  51 */   public static final BasicClassIntrospector instance = new BasicClassIntrospector();
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
/*     */   public BasicBeanDescription forSerialization(SerializationConfig paramSerializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
/*     */   {
/*  66 */     BasicBeanDescription localBasicBeanDescription = _findCachedDesc(paramJavaType);
/*  67 */     if (localBasicBeanDescription == null) {
/*  68 */       localBasicBeanDescription = BasicBeanDescription.forSerialization(collectProperties(paramSerializationConfig, paramJavaType, paramMixInResolver, true, "set"));
/*     */     }
/*     */     
/*  71 */     return localBasicBeanDescription;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicBeanDescription forDeserialization(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
/*     */   {
/*  79 */     BasicBeanDescription localBasicBeanDescription = _findCachedDesc(paramJavaType);
/*  80 */     if (localBasicBeanDescription == null) {
/*  81 */       localBasicBeanDescription = BasicBeanDescription.forDeserialization(collectProperties(paramDeserializationConfig, paramJavaType, paramMixInResolver, false, "set"));
/*     */     }
/*     */     
/*  84 */     return localBasicBeanDescription;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicBeanDescription forDeserializationWithBuilder(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
/*     */   {
/*  92 */     return BasicBeanDescription.forDeserialization(collectPropertiesWithBuilder(paramDeserializationConfig, paramJavaType, paramMixInResolver, false));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicBeanDescription forCreation(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
/*     */   {
/* 100 */     BasicBeanDescription localBasicBeanDescription = _findCachedDesc(paramJavaType);
/* 101 */     if (localBasicBeanDescription == null) {
/* 102 */       localBasicBeanDescription = BasicBeanDescription.forDeserialization(collectProperties(paramDeserializationConfig, paramJavaType, paramMixInResolver, false, "set"));
/*     */     }
/*     */     
/* 105 */     return localBasicBeanDescription;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BasicBeanDescription forClassAnnotations(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
/*     */   {
/* 112 */     boolean bool = paramMapperConfig.isAnnotationProcessingEnabled();
/* 113 */     AnnotatedClass localAnnotatedClass = AnnotatedClass.construct(paramJavaType.getRawClass(), bool ? paramMapperConfig.getAnnotationIntrospector() : null, paramMixInResolver);
/*     */     
/* 115 */     return BasicBeanDescription.forOtherUse(paramMapperConfig, paramJavaType, localAnnotatedClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BasicBeanDescription forDirectClassAnnotations(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver)
/*     */   {
/* 122 */     boolean bool = paramMapperConfig.isAnnotationProcessingEnabled();
/* 123 */     AnnotationIntrospector localAnnotationIntrospector = paramMapperConfig.getAnnotationIntrospector();
/* 124 */     AnnotatedClass localAnnotatedClass = AnnotatedClass.constructWithoutSuperTypes(paramJavaType.getRawClass(), bool ? localAnnotationIntrospector : null, paramMixInResolver);
/*     */     
/* 126 */     return BasicBeanDescription.forOtherUse(paramMapperConfig, paramJavaType, localAnnotatedClass);
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
/*     */   protected POJOPropertiesCollector collectProperties(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver, boolean paramBoolean, String paramString)
/*     */   {
/* 139 */     boolean bool = paramMapperConfig.isAnnotationProcessingEnabled();
/* 140 */     AnnotatedClass localAnnotatedClass = AnnotatedClass.construct(paramJavaType.getRawClass(), bool ? paramMapperConfig.getAnnotationIntrospector() : null, paramMixInResolver);
/*     */     
/* 142 */     return constructPropertyCollector(paramMapperConfig, localAnnotatedClass, paramJavaType, paramBoolean, paramString).collect();
/*     */   }
/*     */   
/*     */ 
/*     */   protected POJOPropertiesCollector collectPropertiesWithBuilder(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, ClassIntrospector.MixInResolver paramMixInResolver, boolean paramBoolean)
/*     */   {
/* 148 */     boolean bool = paramMapperConfig.isAnnotationProcessingEnabled();
/* 149 */     AnnotationIntrospector localAnnotationIntrospector = bool ? paramMapperConfig.getAnnotationIntrospector() : null;
/* 150 */     AnnotatedClass localAnnotatedClass = AnnotatedClass.construct(paramJavaType.getRawClass(), localAnnotationIntrospector, paramMixInResolver);
/* 151 */     JsonPOJOBuilder.Value localValue = localAnnotationIntrospector == null ? null : localAnnotationIntrospector.findPOJOBuilderConfig(localAnnotatedClass);
/* 152 */     String str = localValue == null ? "with" : localValue.withPrefix;
/* 153 */     return constructPropertyCollector(paramMapperConfig, localAnnotatedClass, paramJavaType, paramBoolean, str).collect();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> paramMapperConfig, AnnotatedClass paramAnnotatedClass, JavaType paramJavaType, boolean paramBoolean, String paramString)
/*     */   {
/* 164 */     return new POJOPropertiesCollector(paramMapperConfig, paramBoolean, paramJavaType, paramAnnotatedClass, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BasicBeanDescription _findCachedDesc(JavaType paramJavaType)
/*     */   {
/* 173 */     Class localClass = paramJavaType.getRawClass();
/* 174 */     if (localClass == String.class) {
/* 175 */       return STRING_DESC;
/*     */     }
/* 177 */     if (localClass == Boolean.TYPE) {
/* 178 */       return BOOLEAN_DESC;
/*     */     }
/* 180 */     if (localClass == Integer.TYPE) {
/* 181 */       return INT_DESC;
/*     */     }
/* 183 */     if (localClass == Long.TYPE) {
/* 184 */       return LONG_DESC;
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/BasicClassIntrospector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */