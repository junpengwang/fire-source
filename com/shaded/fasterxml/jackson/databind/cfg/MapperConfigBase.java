/*     */ package com.shaded.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
/*     */ import com.shaded.fasterxml.jackson.annotation.PropertyAccessor;
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.MapperFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>>
/*     */   extends MapperConfig<T>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8378230381628000111L;
/*  28 */   private static final int DEFAULT_MAPPER_FEATURES = collectFeatureDefaults(MapperFeature.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Map<ClassKey, Class<?>> _mixInAnnotations;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SubtypeResolver _subtypeResolver;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final String _rootName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Class<?> _view;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MapperConfigBase(BaseSettings paramBaseSettings, SubtypeResolver paramSubtypeResolver, Map<ClassKey, Class<?>> paramMap)
/*     */   {
/*  76 */     super(paramBaseSettings, DEFAULT_MAPPER_FEATURES);
/*  77 */     this._mixInAnnotations = paramMap;
/*  78 */     this._subtypeResolver = paramSubtypeResolver;
/*  79 */     this._rootName = null;
/*  80 */     this._view = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase)
/*     */   {
/*  89 */     super(paramMapperConfigBase);
/*  90 */     this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
/*  91 */     this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
/*  92 */     this._rootName = paramMapperConfigBase._rootName;
/*  93 */     this._view = paramMapperConfigBase._view;
/*     */   }
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, BaseSettings paramBaseSettings)
/*     */   {
/*  98 */     super(paramBaseSettings, paramMapperConfigBase._mapperFeatures);
/*  99 */     this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
/* 100 */     this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
/* 101 */     this._rootName = paramMapperConfigBase._rootName;
/* 102 */     this._view = paramMapperConfigBase._view;
/*     */   }
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, int paramInt)
/*     */   {
/* 107 */     super(paramMapperConfigBase._base, paramInt);
/* 108 */     this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
/* 109 */     this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
/* 110 */     this._rootName = paramMapperConfigBase._rootName;
/* 111 */     this._view = paramMapperConfigBase._view;
/*     */   }
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, SubtypeResolver paramSubtypeResolver) {
/* 115 */     super(paramMapperConfigBase);
/* 116 */     this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
/* 117 */     this._subtypeResolver = paramSubtypeResolver;
/* 118 */     this._rootName = paramMapperConfigBase._rootName;
/* 119 */     this._view = paramMapperConfigBase._view;
/*     */   }
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, String paramString) {
/* 123 */     super(paramMapperConfigBase);
/* 124 */     this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
/* 125 */     this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
/* 126 */     this._rootName = paramString;
/* 127 */     this._view = paramMapperConfigBase._view;
/*     */   }
/*     */   
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, Class<?> paramClass)
/*     */   {
/* 132 */     super(paramMapperConfigBase);
/* 133 */     this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
/* 134 */     this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
/* 135 */     this._rootName = paramMapperConfigBase._rootName;
/* 136 */     this._view = paramClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, Map<ClassKey, Class<?>> paramMap)
/*     */   {
/* 144 */     super(paramMapperConfigBase);
/* 145 */     this._mixInAnnotations = paramMap;
/* 146 */     this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
/* 147 */     this._rootName = paramMapperConfigBase._rootName;
/* 148 */     this._view = paramMapperConfigBase._view;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(AnnotationIntrospector paramAnnotationIntrospector);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T withAppendedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T withInsertedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(ClassIntrospector paramClassIntrospector);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(DateFormat paramDateFormat);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(HandlerInstantiator paramHandlerInstantiator);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(PropertyNamingStrategy paramPropertyNamingStrategy);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T withRootName(String paramString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(SubtypeResolver paramSubtypeResolver);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(TypeFactory paramTypeFactory);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(TypeResolverBuilder<?> paramTypeResolverBuilder);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T withView(Class<?> paramClass);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(VisibilityChecker<?> paramVisibilityChecker);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(Locale paramLocale);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(TimeZone paramTimeZone);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract T with(Base64Variant paramBase64Variant);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SubtypeResolver getSubtypeResolver()
/*     */   {
/* 307 */     return this._subtypeResolver;
/*     */   }
/*     */   
/*     */   public final String getRootName() {
/* 311 */     return this._rootName;
/*     */   }
/*     */   
/*     */   public final Class<?> getActiveView()
/*     */   {
/* 316 */     return this._view;
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
/*     */   public final Class<?> findMixInClassFor(Class<?> paramClass)
/*     */   {
/* 331 */     return this._mixInAnnotations == null ? null : (Class)this._mixInAnnotations.get(new ClassKey(paramClass));
/*     */   }
/*     */   
/*     */   public final int mixInCount() {
/* 335 */     return this._mixInAnnotations == null ? 0 : this._mixInAnnotations.size();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/cfg/MapperConfigBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */