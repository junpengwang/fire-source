/*     */ package com.shaded.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.MapperFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ClassIntrospector.MixInResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
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
/*     */ public abstract class MapperConfig<T extends MapperConfig<T>>
/*     */   implements ClassIntrospector.MixInResolver, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8891625428805876137L;
/*     */   protected final int _mapperFeatures;
/*     */   protected final BaseSettings _base;
/*     */   
/*     */   protected MapperConfig(BaseSettings paramBaseSettings, int paramInt)
/*     */   {
/*  57 */     this._base = paramBaseSettings;
/*  58 */     this._mapperFeatures = paramInt;
/*     */   }
/*     */   
/*     */   protected MapperConfig(MapperConfig<T> paramMapperConfig)
/*     */   {
/*  63 */     this._base = paramMapperConfig._base;
/*  64 */     this._mapperFeatures = paramMapperConfig._mapperFeatures;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <F extends Enum<F>,  extends ConfigFeature> int collectFeatureDefaults(Class<F> paramClass)
/*     */   {
/*  73 */     int i = 0;
/*  74 */     for (Enum localEnum : (Enum[])paramClass.getEnumConstants()) {
/*  75 */       if (((ConfigFeature)localEnum).enabledByDefault()) {
/*  76 */         i |= ((ConfigFeature)localEnum).getMask();
/*     */       }
/*     */     }
/*  79 */     return i;
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
/*     */   public abstract T with(MapperFeature... paramVarArgs);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T without(MapperFeature... paramVarArgs);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isEnabled(MapperFeature paramMapperFeature)
/*     */   {
/* 110 */     return (this._mapperFeatures & paramMapperFeature.getMask()) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isAnnotationProcessingEnabled()
/*     */   {
/* 120 */     return isEnabled(MapperFeature.USE_ANNOTATIONS);
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
/*     */   public final boolean canOverrideAccessModifiers()
/*     */   {
/* 135 */     return isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean shouldSortPropertiesAlphabetically()
/*     */   {
/* 143 */     return isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean useRootWrapping();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ClassIntrospector getClassIntrospector()
/*     */   {
/* 160 */     return this._base.getClassIntrospector();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotationIntrospector getAnnotationIntrospector()
/*     */   {
/* 170 */     return this._base.getAnnotationIntrospector();
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
/*     */   public VisibilityChecker<?> getDefaultVisibilityChecker()
/*     */   {
/* 183 */     return this._base.getVisibilityChecker();
/*     */   }
/*     */   
/*     */   public final PropertyNamingStrategy getPropertyNamingStrategy() {
/* 187 */     return this._base.getPropertyNamingStrategy();
/*     */   }
/*     */   
/*     */   public final HandlerInstantiator getHandlerInstantiator() {
/* 191 */     return this._base.getHandlerInstantiator();
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
/*     */   public final TypeResolverBuilder<?> getDefaultTyper(JavaType paramJavaType)
/*     */   {
/* 207 */     return this._base.getTypeResolverBuilder();
/*     */   }
/*     */   
/*     */   public abstract SubtypeResolver getSubtypeResolver();
/*     */   
/*     */   public final TypeFactory getTypeFactory() {
/* 213 */     return this._base.getTypeFactory();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JavaType constructType(Class<?> paramClass)
/*     */   {
/* 225 */     return getTypeFactory().constructType(paramClass, (TypeBindings)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final JavaType constructType(TypeReference<?> paramTypeReference)
/*     */   {
/* 237 */     return getTypeFactory().constructType(paramTypeReference.getType(), (TypeBindings)null);
/*     */   }
/*     */   
/*     */   public JavaType constructSpecializedType(JavaType paramJavaType, Class<?> paramClass) {
/* 241 */     return getTypeFactory().constructSpecializedType(paramJavaType, paramClass);
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
/*     */   public BeanDescription introspectClassAnnotations(Class<?> paramClass)
/*     */   {
/* 255 */     return introspectClassAnnotations(constructType(paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract BeanDescription introspectClassAnnotations(JavaType paramJavaType);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanDescription introspectDirectClassAnnotations(Class<?> paramClass)
/*     */   {
/* 270 */     return introspectDirectClassAnnotations(constructType(paramClass));
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
/*     */   public abstract BeanDescription introspectDirectClassAnnotations(JavaType paramJavaType);
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
/*     */   public final DateFormat getDateFormat()
/*     */   {
/* 301 */     return this._base.getDateFormat();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final Locale getLocale()
/*     */   {
/* 308 */     return this._base.getLocale();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final TimeZone getTimeZone()
/*     */   {
/* 315 */     return this._base.getTimeZone();
/*     */   }
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
/*     */   public Base64Variant getBase64Variant()
/*     */   {
/* 330 */     return this._base.getBase64Variant();
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
/*     */   public TypeResolverBuilder<?> typeResolverBuilderInstance(Annotated paramAnnotated, Class<? extends TypeResolverBuilder<?>> paramClass)
/*     */   {
/* 346 */     HandlerInstantiator localHandlerInstantiator = getHandlerInstantiator();
/* 347 */     if (localHandlerInstantiator != null) {
/* 348 */       TypeResolverBuilder localTypeResolverBuilder = localHandlerInstantiator.typeResolverBuilderInstance(this, paramAnnotated, paramClass);
/* 349 */       if (localTypeResolverBuilder != null) {
/* 350 */         return localTypeResolverBuilder;
/*     */       }
/*     */     }
/* 353 */     return (TypeResolverBuilder)ClassUtil.createInstance(paramClass, canOverrideAccessModifiers());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeIdResolver typeIdResolverInstance(Annotated paramAnnotated, Class<? extends TypeIdResolver> paramClass)
/*     */   {
/* 363 */     HandlerInstantiator localHandlerInstantiator = getHandlerInstantiator();
/* 364 */     if (localHandlerInstantiator != null) {
/* 365 */       TypeIdResolver localTypeIdResolver = localHandlerInstantiator.typeIdResolverInstance(this, paramAnnotated, paramClass);
/* 366 */       if (localTypeIdResolver != null) {
/* 367 */         return localTypeIdResolver;
/*     */       }
/*     */     }
/* 370 */     return (TypeIdResolver)ClassUtil.createInstance(paramClass, canOverrideAccessModifiers());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/cfg/MapperConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */