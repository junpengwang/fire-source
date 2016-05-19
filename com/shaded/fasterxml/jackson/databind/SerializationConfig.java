/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*     */ import com.shaded.fasterxml.jackson.annotation.PropertyAccessor;
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.BaseSettings;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfigBase;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.FilterProvider;
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
/*     */ public final class SerializationConfig
/*     */   extends MapperConfigBase<SerializationFeature, SerializationConfig>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8849092838541724233L;
/*     */   protected final int _serFeatures;
/*  54 */   protected JsonInclude.Include _serializationInclusion = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final FilterProvider _filterProvider;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig(BaseSettings paramBaseSettings, SubtypeResolver paramSubtypeResolver, Map<ClassKey, Class<?>> paramMap)
/*     */   {
/*  74 */     super(paramBaseSettings, paramSubtypeResolver, paramMap);
/*  75 */     this._serFeatures = collectFeatureDefaults(SerializationFeature.class);
/*  76 */     this._filterProvider = null;
/*     */   }
/*     */   
/*     */   private SerializationConfig(SerializationConfig paramSerializationConfig, SubtypeResolver paramSubtypeResolver)
/*     */   {
/*  81 */     super(paramSerializationConfig, paramSubtypeResolver);
/*  82 */     this._serFeatures = paramSerializationConfig._serFeatures;
/*  83 */     this._serializationInclusion = paramSerializationConfig._serializationInclusion;
/*  84 */     this._filterProvider = paramSerializationConfig._filterProvider;
/*     */   }
/*     */   
/*     */ 
/*     */   private SerializationConfig(SerializationConfig paramSerializationConfig, int paramInt1, int paramInt2)
/*     */   {
/*  90 */     super(paramSerializationConfig, paramInt1);
/*  91 */     this._serFeatures = paramInt2;
/*  92 */     this._serializationInclusion = paramSerializationConfig._serializationInclusion;
/*  93 */     this._filterProvider = paramSerializationConfig._filterProvider;
/*     */   }
/*     */   
/*     */   private SerializationConfig(SerializationConfig paramSerializationConfig, BaseSettings paramBaseSettings)
/*     */   {
/*  98 */     super(paramSerializationConfig, paramBaseSettings);
/*  99 */     this._serFeatures = paramSerializationConfig._serFeatures;
/* 100 */     this._serializationInclusion = paramSerializationConfig._serializationInclusion;
/* 101 */     this._filterProvider = paramSerializationConfig._filterProvider;
/*     */   }
/*     */   
/*     */   private SerializationConfig(SerializationConfig paramSerializationConfig, FilterProvider paramFilterProvider)
/*     */   {
/* 106 */     super(paramSerializationConfig);
/* 107 */     this._serFeatures = paramSerializationConfig._serFeatures;
/* 108 */     this._serializationInclusion = paramSerializationConfig._serializationInclusion;
/* 109 */     this._filterProvider = paramFilterProvider;
/*     */   }
/*     */   
/*     */   private SerializationConfig(SerializationConfig paramSerializationConfig, Class<?> paramClass)
/*     */   {
/* 114 */     super(paramSerializationConfig, paramClass);
/* 115 */     this._serFeatures = paramSerializationConfig._serFeatures;
/* 116 */     this._serializationInclusion = paramSerializationConfig._serializationInclusion;
/* 117 */     this._filterProvider = paramSerializationConfig._filterProvider;
/*     */   }
/*     */   
/*     */   private SerializationConfig(SerializationConfig paramSerializationConfig, JsonInclude.Include paramInclude)
/*     */   {
/* 122 */     super(paramSerializationConfig);
/* 123 */     this._serFeatures = paramSerializationConfig._serFeatures;
/* 124 */     this._serializationInclusion = paramInclude;
/* 125 */     this._filterProvider = paramSerializationConfig._filterProvider;
/*     */   }
/*     */   
/*     */   private SerializationConfig(SerializationConfig paramSerializationConfig, String paramString)
/*     */   {
/* 130 */     super(paramSerializationConfig, paramString);
/* 131 */     this._serFeatures = paramSerializationConfig._serFeatures;
/* 132 */     this._serializationInclusion = paramSerializationConfig._serializationInclusion;
/* 133 */     this._filterProvider = paramSerializationConfig._filterProvider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SerializationConfig(SerializationConfig paramSerializationConfig, Map<ClassKey, Class<?>> paramMap)
/*     */   {
/* 141 */     super(paramSerializationConfig, paramMap);
/* 142 */     this._serFeatures = paramSerializationConfig._serFeatures;
/* 143 */     this._serializationInclusion = paramSerializationConfig._serializationInclusion;
/* 144 */     this._filterProvider = paramSerializationConfig._filterProvider;
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
/*     */   public SerializationConfig with(MapperFeature... paramVarArgs)
/*     */   {
/* 160 */     int i = this._mapperFeatures;
/* 161 */     for (MapperFeature localMapperFeature : paramVarArgs) {
/* 162 */       i |= localMapperFeature.getMask();
/*     */     }
/* 164 */     return i == this._mapperFeatures ? this : new SerializationConfig(this, i, this._serFeatures);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig without(MapperFeature... paramVarArgs)
/*     */   {
/* 175 */     int i = this._mapperFeatures;
/* 176 */     for (MapperFeature localMapperFeature : paramVarArgs) {
/* 177 */       i &= (localMapperFeature.getMask() ^ 0xFFFFFFFF);
/*     */     }
/* 179 */     return i == this._mapperFeatures ? this : new SerializationConfig(this, i, this._serFeatures);
/*     */   }
/*     */   
/*     */ 
/*     */   public SerializationConfig with(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 185 */     return _withBase(this._base.withAnnotationIntrospector(paramAnnotationIntrospector));
/*     */   }
/*     */   
/*     */   public SerializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 190 */     return _withBase(this._base.withAppendedAnnotationIntrospector(paramAnnotationIntrospector));
/*     */   }
/*     */   
/*     */   public SerializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 195 */     return _withBase(this._base.withInsertedAnnotationIntrospector(paramAnnotationIntrospector));
/*     */   }
/*     */   
/*     */   public SerializationConfig with(ClassIntrospector paramClassIntrospector)
/*     */   {
/* 200 */     return _withBase(this._base.withClassIntrospector(paramClassIntrospector));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig with(DateFormat paramDateFormat)
/*     */   {
/* 210 */     SerializationConfig localSerializationConfig = new SerializationConfig(this, this._base.withDateFormat(paramDateFormat));
/*     */     
/* 212 */     if (paramDateFormat == null) {
/* 213 */       localSerializationConfig = localSerializationConfig.with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
/*     */     } else {
/* 215 */       localSerializationConfig = localSerializationConfig.without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
/*     */     }
/* 217 */     return localSerializationConfig;
/*     */   }
/*     */   
/*     */   public SerializationConfig with(HandlerInstantiator paramHandlerInstantiator)
/*     */   {
/* 222 */     return _withBase(this._base.withHandlerInstantiator(paramHandlerInstantiator));
/*     */   }
/*     */   
/*     */   public SerializationConfig with(PropertyNamingStrategy paramPropertyNamingStrategy)
/*     */   {
/* 227 */     return _withBase(this._base.withPropertyNamingStrategy(paramPropertyNamingStrategy));
/*     */   }
/*     */   
/*     */   public SerializationConfig withRootName(String paramString)
/*     */   {
/* 232 */     if (paramString == null) {
/* 233 */       if (this._rootName == null) {
/* 234 */         return this;
/*     */       }
/* 236 */     } else if (paramString.equals(this._rootName)) {
/* 237 */       return this;
/*     */     }
/* 239 */     return new SerializationConfig(this, paramString);
/*     */   }
/*     */   
/*     */   public SerializationConfig with(SubtypeResolver paramSubtypeResolver)
/*     */   {
/* 244 */     return paramSubtypeResolver == this._subtypeResolver ? this : new SerializationConfig(this, paramSubtypeResolver);
/*     */   }
/*     */   
/*     */   public SerializationConfig with(TypeFactory paramTypeFactory)
/*     */   {
/* 249 */     return _withBase(this._base.withTypeFactory(paramTypeFactory));
/*     */   }
/*     */   
/*     */   public SerializationConfig with(TypeResolverBuilder<?> paramTypeResolverBuilder)
/*     */   {
/* 254 */     return _withBase(this._base.withTypeResolverBuilder(paramTypeResolverBuilder));
/*     */   }
/*     */   
/*     */   public SerializationConfig withView(Class<?> paramClass)
/*     */   {
/* 259 */     return this._view == paramClass ? this : new SerializationConfig(this, paramClass);
/*     */   }
/*     */   
/*     */   public SerializationConfig with(VisibilityChecker<?> paramVisibilityChecker)
/*     */   {
/* 264 */     return _withBase(this._base.withVisibilityChecker(paramVisibilityChecker));
/*     */   }
/*     */   
/*     */   public SerializationConfig withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility)
/*     */   {
/* 269 */     return _withBase(this._base.withVisibility(paramPropertyAccessor, paramVisibility));
/*     */   }
/*     */   
/*     */   public SerializationConfig with(Locale paramLocale)
/*     */   {
/* 274 */     return _withBase(this._base.with(paramLocale));
/*     */   }
/*     */   
/*     */   public SerializationConfig with(TimeZone paramTimeZone)
/*     */   {
/* 279 */     return _withBase(this._base.with(paramTimeZone));
/*     */   }
/*     */   
/*     */   public SerializationConfig with(Base64Variant paramBase64Variant)
/*     */   {
/* 284 */     return _withBase(this._base.with(paramBase64Variant));
/*     */   }
/*     */   
/*     */   private final SerializationConfig _withBase(BaseSettings paramBaseSettings) {
/* 288 */     return this._base == paramBaseSettings ? this : new SerializationConfig(this, paramBaseSettings);
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
/*     */   public SerializationConfig with(SerializationFeature paramSerializationFeature)
/*     */   {
/* 303 */     int i = this._serFeatures | paramSerializationFeature.getMask();
/* 304 */     return i == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig with(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
/*     */   {
/* 314 */     int i = this._serFeatures | paramSerializationFeature.getMask();
/* 315 */     for (SerializationFeature localSerializationFeature : paramVarArgs) {
/* 316 */       i |= localSerializationFeature.getMask();
/*     */     }
/* 318 */     return i == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig withFeatures(SerializationFeature... paramVarArgs)
/*     */   {
/* 328 */     int i = this._serFeatures;
/* 329 */     for (SerializationFeature localSerializationFeature : paramVarArgs) {
/* 330 */       i |= localSerializationFeature.getMask();
/*     */     }
/* 332 */     return i == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig without(SerializationFeature paramSerializationFeature)
/*     */   {
/* 342 */     int i = this._serFeatures & (paramSerializationFeature.getMask() ^ 0xFFFFFFFF);
/* 343 */     return i == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig without(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
/*     */   {
/* 353 */     int i = this._serFeatures & (paramSerializationFeature.getMask() ^ 0xFFFFFFFF);
/* 354 */     for (SerializationFeature localSerializationFeature : paramVarArgs) {
/* 355 */       i &= (localSerializationFeature.getMask() ^ 0xFFFFFFFF);
/*     */     }
/* 357 */     return i == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializationConfig withoutFeatures(SerializationFeature... paramVarArgs)
/*     */   {
/* 367 */     int i = this._serFeatures;
/* 368 */     for (SerializationFeature localSerializationFeature : paramVarArgs) {
/* 369 */       i &= (localSerializationFeature.getMask() ^ 0xFFFFFFFF);
/*     */     }
/* 371 */     return i == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */   public SerializationConfig withFilters(FilterProvider paramFilterProvider)
/*     */   {
/* 376 */     return paramFilterProvider == this._filterProvider ? this : new SerializationConfig(this, paramFilterProvider);
/*     */   }
/*     */   
/*     */   public SerializationConfig withSerializationInclusion(JsonInclude.Include paramInclude) {
/* 380 */     return this._serializationInclusion == paramInclude ? this : new SerializationConfig(this, paramInclude);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean useRootWrapping()
/*     */   {
/* 392 */     if (this._rootName != null) {
/* 393 */       return this._rootName.length() > 0;
/*     */     }
/* 395 */     return isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotationIntrospector getAnnotationIntrospector()
/*     */   {
/* 404 */     if (isEnabled(MapperFeature.USE_ANNOTATIONS)) {
/* 405 */       return super.getAnnotationIntrospector();
/*     */     }
/* 407 */     return AnnotationIntrospector.nopInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanDescription introspectClassAnnotations(JavaType paramJavaType)
/*     */   {
/* 416 */     return getClassIntrospector().forClassAnnotations(this, paramJavaType, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanDescription introspectDirectClassAnnotations(JavaType paramJavaType)
/*     */   {
/* 426 */     return getClassIntrospector().forDirectClassAnnotations(this, paramJavaType, this);
/*     */   }
/*     */   
/*     */ 
/*     */   public VisibilityChecker<?> getDefaultVisibilityChecker()
/*     */   {
/* 432 */     VisibilityChecker localVisibilityChecker = super.getDefaultVisibilityChecker();
/* 433 */     if (!isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
/* 434 */       localVisibilityChecker = localVisibilityChecker.withGetterVisibility(JsonAutoDetect.Visibility.NONE);
/*     */     }
/*     */     
/* 437 */     if (!isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
/* 438 */       localVisibilityChecker = localVisibilityChecker.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
/*     */     }
/* 440 */     if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
/* 441 */       localVisibilityChecker = localVisibilityChecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
/*     */     }
/* 443 */     return localVisibilityChecker;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isEnabled(SerializationFeature paramSerializationFeature)
/*     */   {
/* 453 */     return (this._serFeatures & paramSerializationFeature.getMask()) != 0;
/*     */   }
/*     */   
/*     */   public final int getSerializationFeatures() {
/* 457 */     return this._serFeatures;
/*     */   }
/*     */   
/*     */   public JsonInclude.Include getSerializationInclusion()
/*     */   {
/* 462 */     if (this._serializationInclusion != null) {
/* 463 */       return this._serializationInclusion;
/*     */     }
/* 465 */     return JsonInclude.Include.ALWAYS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FilterProvider getFilterProvider()
/*     */   {
/* 475 */     return this._filterProvider;
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
/*     */   public <T extends BeanDescription> T introspect(JavaType paramJavaType)
/*     */   {
/* 490 */     return getClassIntrospector().forSerialization(this, paramJavaType, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 501 */     return "[SerializationConfig: flags=0x" + Integer.toHexString(this._serFeatures) + "]";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/SerializationConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */