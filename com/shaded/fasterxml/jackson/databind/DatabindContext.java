/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter.None;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DatabindContext
/*     */ {
/*     */   public abstract MapperConfig<?> getConfig();
/*     */   
/*     */   public abstract AnnotationIntrospector getAnnotationIntrospector();
/*     */   
/*     */   public final boolean isEnabled(MapperFeature paramMapperFeature)
/*     */   {
/*  62 */     return getConfig().isEnabled(paramMapperFeature);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean canOverrideAccessModifiers()
/*     */   {
/*  72 */     return getConfig().canOverrideAccessModifiers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Class<?> getActiveView();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType constructType(Type paramType)
/*     */   {
/*  92 */     return getTypeFactory().constructType(paramType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType constructSpecializedType(JavaType paramJavaType, Class<?> paramClass)
/*     */   {
/* 100 */     return getConfig().constructSpecializedType(paramJavaType, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract TypeFactory getTypeFactory();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectIdGenerator<?> objectIdGeneratorInstance(Annotated paramAnnotated, ObjectIdInfo paramObjectIdInfo)
/*     */     throws JsonMappingException
/*     */   {
/* 115 */     Class localClass = paramObjectIdInfo.getGeneratorType();
/* 116 */     MapperConfig localMapperConfig = getConfig();
/* 117 */     HandlerInstantiator localHandlerInstantiator = localMapperConfig.getHandlerInstantiator();
/* 118 */     ObjectIdGenerator localObjectIdGenerator = localHandlerInstantiator == null ? null : localHandlerInstantiator.objectIdGeneratorInstance(localMapperConfig, paramAnnotated, localClass);
/* 119 */     if (localObjectIdGenerator == null) {
/* 120 */       localObjectIdGenerator = (ObjectIdGenerator)ClassUtil.createInstance(localClass, localMapperConfig.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 123 */     return localObjectIdGenerator.forScope(paramObjectIdInfo.getScope());
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
/*     */   public Converter<Object, Object> converterInstance(Annotated paramAnnotated, Object paramObject)
/*     */     throws JsonMappingException
/*     */   {
/* 137 */     if (paramObject == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     if ((paramObject instanceof Converter)) {
/* 141 */       return (Converter)paramObject;
/*     */     }
/* 143 */     if (!(paramObject instanceof Class)) {
/* 144 */       throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + paramObject.getClass().getName() + "; expected type Converter or Class<Converter> instead");
/*     */     }
/*     */     
/* 147 */     Class localClass = (Class)paramObject;
/*     */     
/* 149 */     if ((localClass == Converter.None.class) || (localClass == NoClass.class)) {
/* 150 */       return null;
/*     */     }
/* 152 */     if (!Converter.class.isAssignableFrom(localClass)) {
/* 153 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + localClass.getName() + "; expected Class<Converter>");
/*     */     }
/*     */     
/* 156 */     MapperConfig localMapperConfig = getConfig();
/* 157 */     HandlerInstantiator localHandlerInstantiator = localMapperConfig.getHandlerInstantiator();
/* 158 */     Converter localConverter = localHandlerInstantiator == null ? null : localHandlerInstantiator.converterInstance(localMapperConfig, paramAnnotated, localClass);
/* 159 */     if (localConverter == null) {
/* 160 */       localConverter = (Converter)ClassUtil.createInstance(localClass, localMapperConfig.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 163 */     return localConverter;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/DatabindContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */