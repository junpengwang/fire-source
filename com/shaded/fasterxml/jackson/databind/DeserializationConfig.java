/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
/*     */ import com.shaded.fasterxml.jackson.annotation.PropertyAccessor;
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.BaseSettings;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfigBase;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.LinkedNode;
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
/*     */ public final class DeserializationConfig
/*     */   extends MapperConfigBase<DeserializationFeature, DeserializationConfig>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4227480407273773599L;
/*     */   protected final int _deserFeatures;
/*     */   protected final LinkedNode<DeserializationProblemHandler> _problemHandlers;
/*     */   protected final JsonNodeFactory _nodeFactory;
/*     */   
/*     */   public DeserializationConfig(BaseSettings paramBaseSettings, SubtypeResolver paramSubtypeResolver, Map<ClassKey, Class<?>> paramMap)
/*     */   {
/*  72 */     super(paramBaseSettings, paramSubtypeResolver, paramMap);
/*  73 */     this._deserFeatures = collectFeatureDefaults(DeserializationFeature.class);
/*  74 */     this._nodeFactory = JsonNodeFactory.instance;
/*  75 */     this._problemHandlers = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private DeserializationConfig(DeserializationConfig paramDeserializationConfig, SubtypeResolver paramSubtypeResolver)
/*     */   {
/*  84 */     super(paramDeserializationConfig, paramSubtypeResolver);
/*  85 */     this._deserFeatures = paramDeserializationConfig._deserFeatures;
/*  86 */     this._nodeFactory = paramDeserializationConfig._nodeFactory;
/*  87 */     this._problemHandlers = paramDeserializationConfig._problemHandlers;
/*     */   }
/*     */   
/*     */ 
/*     */   private DeserializationConfig(DeserializationConfig paramDeserializationConfig, int paramInt1, int paramInt2)
/*     */   {
/*  93 */     super(paramDeserializationConfig, paramInt1);
/*  94 */     this._deserFeatures = paramInt2;
/*  95 */     this._nodeFactory = paramDeserializationConfig._nodeFactory;
/*  96 */     this._problemHandlers = paramDeserializationConfig._problemHandlers;
/*     */   }
/*     */   
/*     */   private DeserializationConfig(DeserializationConfig paramDeserializationConfig, BaseSettings paramBaseSettings)
/*     */   {
/* 101 */     super(paramDeserializationConfig, paramBaseSettings);
/* 102 */     this._deserFeatures = paramDeserializationConfig._deserFeatures;
/* 103 */     this._nodeFactory = paramDeserializationConfig._nodeFactory;
/* 104 */     this._problemHandlers = paramDeserializationConfig._problemHandlers;
/*     */   }
/*     */   
/*     */   private DeserializationConfig(DeserializationConfig paramDeserializationConfig, JsonNodeFactory paramJsonNodeFactory)
/*     */   {
/* 109 */     super(paramDeserializationConfig);
/* 110 */     this._deserFeatures = paramDeserializationConfig._deserFeatures;
/* 111 */     this._problemHandlers = paramDeserializationConfig._problemHandlers;
/* 112 */     this._nodeFactory = paramJsonNodeFactory;
/*     */   }
/*     */   
/*     */ 
/*     */   private DeserializationConfig(DeserializationConfig paramDeserializationConfig, LinkedNode<DeserializationProblemHandler> paramLinkedNode)
/*     */   {
/* 118 */     super(paramDeserializationConfig);
/* 119 */     this._deserFeatures = paramDeserializationConfig._deserFeatures;
/* 120 */     this._problemHandlers = paramLinkedNode;
/* 121 */     this._nodeFactory = paramDeserializationConfig._nodeFactory;
/*     */   }
/*     */   
/*     */   private DeserializationConfig(DeserializationConfig paramDeserializationConfig, String paramString)
/*     */   {
/* 126 */     super(paramDeserializationConfig, paramString);
/* 127 */     this._deserFeatures = paramDeserializationConfig._deserFeatures;
/* 128 */     this._problemHandlers = paramDeserializationConfig._problemHandlers;
/* 129 */     this._nodeFactory = paramDeserializationConfig._nodeFactory;
/*     */   }
/*     */   
/*     */   private DeserializationConfig(DeserializationConfig paramDeserializationConfig, Class<?> paramClass)
/*     */   {
/* 134 */     super(paramDeserializationConfig, paramClass);
/* 135 */     this._deserFeatures = paramDeserializationConfig._deserFeatures;
/* 136 */     this._problemHandlers = paramDeserializationConfig._problemHandlers;
/* 137 */     this._nodeFactory = paramDeserializationConfig._nodeFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected DeserializationConfig(DeserializationConfig paramDeserializationConfig, Map<ClassKey, Class<?>> paramMap)
/*     */   {
/* 145 */     super(paramDeserializationConfig, paramMap);
/* 146 */     this._deserFeatures = paramDeserializationConfig._deserFeatures;
/* 147 */     this._problemHandlers = paramDeserializationConfig._problemHandlers;
/* 148 */     this._nodeFactory = paramDeserializationConfig._nodeFactory;
/*     */   }
/*     */   
/*     */   protected BaseSettings getBaseSettings() {
/* 152 */     return this._base;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig with(MapperFeature... paramVarArgs)
/*     */   {
/* 163 */     int i = this._mapperFeatures;
/* 164 */     for (MapperFeature localMapperFeature : paramVarArgs) {
/* 165 */       i |= localMapperFeature.getMask();
/*     */     }
/* 167 */     return i == this._mapperFeatures ? this : new DeserializationConfig(this, i, this._deserFeatures);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DeserializationConfig without(MapperFeature... paramVarArgs)
/*     */   {
/* 174 */     int i = this._mapperFeatures;
/* 175 */     for (MapperFeature localMapperFeature : paramVarArgs) {
/* 176 */       i &= (localMapperFeature.getMask() ^ 0xFFFFFFFF);
/*     */     }
/* 178 */     return i == this._mapperFeatures ? this : new DeserializationConfig(this, i, this._deserFeatures);
/*     */   }
/*     */   
/*     */ 
/*     */   public DeserializationConfig with(ClassIntrospector paramClassIntrospector)
/*     */   {
/* 184 */     return _withBase(this._base.withClassIntrospector(paramClassIntrospector));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 189 */     return _withBase(this._base.withAnnotationIntrospector(paramAnnotationIntrospector));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(VisibilityChecker<?> paramVisibilityChecker)
/*     */   {
/* 194 */     return _withBase(this._base.withVisibilityChecker(paramVisibilityChecker));
/*     */   }
/*     */   
/*     */   public DeserializationConfig withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility)
/*     */   {
/* 199 */     return _withBase(this._base.withVisibility(paramPropertyAccessor, paramVisibility));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(TypeResolverBuilder<?> paramTypeResolverBuilder)
/*     */   {
/* 204 */     return _withBase(this._base.withTypeResolverBuilder(paramTypeResolverBuilder));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(SubtypeResolver paramSubtypeResolver)
/*     */   {
/* 209 */     return this._subtypeResolver == paramSubtypeResolver ? this : new DeserializationConfig(this, paramSubtypeResolver);
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(PropertyNamingStrategy paramPropertyNamingStrategy)
/*     */   {
/* 214 */     return _withBase(this._base.withPropertyNamingStrategy(paramPropertyNamingStrategy));
/*     */   }
/*     */   
/*     */   public DeserializationConfig withRootName(String paramString)
/*     */   {
/* 219 */     if (paramString == null) {
/* 220 */       if (this._rootName == null) {
/* 221 */         return this;
/*     */       }
/* 223 */     } else if (paramString.equals(this._rootName)) {
/* 224 */       return this;
/*     */     }
/* 226 */     return new DeserializationConfig(this, paramString);
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(TypeFactory paramTypeFactory)
/*     */   {
/* 231 */     return _withBase(this._base.withTypeFactory(paramTypeFactory));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(DateFormat paramDateFormat)
/*     */   {
/* 236 */     return _withBase(this._base.withDateFormat(paramDateFormat));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(HandlerInstantiator paramHandlerInstantiator)
/*     */   {
/* 241 */     return _withBase(this._base.withHandlerInstantiator(paramHandlerInstantiator));
/*     */   }
/*     */   
/*     */   public DeserializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 246 */     return _withBase(this._base.withInsertedAnnotationIntrospector(paramAnnotationIntrospector));
/*     */   }
/*     */   
/*     */   public DeserializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 251 */     return _withBase(this._base.withAppendedAnnotationIntrospector(paramAnnotationIntrospector));
/*     */   }
/*     */   
/*     */   public DeserializationConfig withView(Class<?> paramClass)
/*     */   {
/* 256 */     return this._view == paramClass ? this : new DeserializationConfig(this, paramClass);
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(Locale paramLocale)
/*     */   {
/* 261 */     return _withBase(this._base.with(paramLocale));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(TimeZone paramTimeZone)
/*     */   {
/* 266 */     return _withBase(this._base.with(paramTimeZone));
/*     */   }
/*     */   
/*     */   public DeserializationConfig with(Base64Variant paramBase64Variant)
/*     */   {
/* 271 */     return _withBase(this._base.with(paramBase64Variant));
/*     */   }
/*     */   
/*     */   private final DeserializationConfig _withBase(BaseSettings paramBaseSettings) {
/* 275 */     return this._base == paramBaseSettings ? this : new DeserializationConfig(this, paramBaseSettings);
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
/*     */   public DeserializationConfig with(JsonNodeFactory paramJsonNodeFactory)
/*     */   {
/* 289 */     if (this._nodeFactory == paramJsonNodeFactory) {
/* 290 */       return this;
/*     */     }
/* 292 */     return new DeserializationConfig(this, paramJsonNodeFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig withHandler(DeserializationProblemHandler paramDeserializationProblemHandler)
/*     */   {
/* 302 */     if (LinkedNode.contains(this._problemHandlers, paramDeserializationProblemHandler)) {
/* 303 */       return this;
/*     */     }
/* 305 */     return new DeserializationConfig(this, new LinkedNode(paramDeserializationProblemHandler, this._problemHandlers));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig withNoProblemHandlers()
/*     */   {
/* 314 */     if (this._problemHandlers == null) {
/* 315 */       return this;
/*     */     }
/* 317 */     return new DeserializationConfig(this, (LinkedNode)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig with(DeserializationFeature paramDeserializationFeature)
/*     */   {
/* 327 */     int i = this._deserFeatures | paramDeserializationFeature.getMask();
/* 328 */     return i == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig with(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
/*     */   {
/* 339 */     int i = this._deserFeatures | paramDeserializationFeature.getMask();
/* 340 */     for (DeserializationFeature localDeserializationFeature : paramVarArgs) {
/* 341 */       i |= localDeserializationFeature.getMask();
/*     */     }
/* 343 */     return i == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig withFeatures(DeserializationFeature... paramVarArgs)
/*     */   {
/* 353 */     int i = this._deserFeatures;
/* 354 */     for (DeserializationFeature localDeserializationFeature : paramVarArgs) {
/* 355 */       i |= localDeserializationFeature.getMask();
/*     */     }
/* 357 */     return i == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig without(DeserializationFeature paramDeserializationFeature)
/*     */   {
/* 367 */     int i = this._deserFeatures & (paramDeserializationFeature.getMask() ^ 0xFFFFFFFF);
/* 368 */     return i == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig without(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
/*     */   {
/* 379 */     int i = this._deserFeatures & (paramDeserializationFeature.getMask() ^ 0xFFFFFFFF);
/* 380 */     for (DeserializationFeature localDeserializationFeature : paramVarArgs) {
/* 381 */       i &= (localDeserializationFeature.getMask() ^ 0xFFFFFFFF);
/*     */     }
/* 383 */     return i == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializationConfig withoutFeatures(DeserializationFeature... paramVarArgs)
/*     */   {
/* 393 */     int i = this._deserFeatures;
/* 394 */     for (DeserializationFeature localDeserializationFeature : paramVarArgs) {
/* 395 */       i &= (localDeserializationFeature.getMask() ^ 0xFFFFFFFF);
/*     */     }
/* 397 */     return i == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, i);
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
/*     */   public AnnotationIntrospector getAnnotationIntrospector()
/*     */   {
/* 417 */     if (isEnabled(MapperFeature.USE_ANNOTATIONS)) {
/* 418 */       return super.getAnnotationIntrospector();
/*     */     }
/* 420 */     return NopAnnotationIntrospector.instance;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean useRootWrapping()
/*     */   {
/* 426 */     if (this._rootName != null) {
/* 427 */       return this._rootName.length() > 0;
/*     */     }
/* 429 */     return isEnabled(DeserializationFeature.UNWRAP_ROOT_VALUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanDescription introspectClassAnnotations(JavaType paramJavaType)
/*     */   {
/* 438 */     return getClassIntrospector().forClassAnnotations(this, paramJavaType, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanDescription introspectDirectClassAnnotations(JavaType paramJavaType)
/*     */   {
/* 448 */     return getClassIntrospector().forDirectClassAnnotations(this, paramJavaType, this);
/*     */   }
/*     */   
/*     */ 
/*     */   public VisibilityChecker<?> getDefaultVisibilityChecker()
/*     */   {
/* 454 */     VisibilityChecker localVisibilityChecker = super.getDefaultVisibilityChecker();
/* 455 */     if (!isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
/* 456 */       localVisibilityChecker = localVisibilityChecker.withSetterVisibility(JsonAutoDetect.Visibility.NONE);
/*     */     }
/* 458 */     if (!isEnabled(MapperFeature.AUTO_DETECT_CREATORS)) {
/* 459 */       localVisibilityChecker = localVisibilityChecker.withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
/*     */     }
/* 461 */     if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
/* 462 */       localVisibilityChecker = localVisibilityChecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
/*     */     }
/* 464 */     return localVisibilityChecker;
/*     */   }
/*     */   
/*     */   public final boolean isEnabled(DeserializationFeature paramDeserializationFeature) {
/* 468 */     return (this._deserFeatures & paramDeserializationFeature.getMask()) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getDeserializationFeatures()
/*     */   {
/* 478 */     return this._deserFeatures;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LinkedNode<DeserializationProblemHandler> getProblemHandlers()
/*     */   {
/* 486 */     return this._problemHandlers;
/*     */   }
/*     */   
/*     */   public final JsonNodeFactory getNodeFactory() {
/* 490 */     return this._nodeFactory;
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
/*     */   public <T extends BeanDescription> T introspect(JavaType paramJavaType)
/*     */   {
/* 507 */     return getClassIntrospector().forDeserialization(this, paramJavaType, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends BeanDescription> T introspectForCreation(JavaType paramJavaType)
/*     */   {
/* 516 */     return getClassIntrospector().forCreation(this, paramJavaType, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T extends BeanDescription> T introspectForBuilder(JavaType paramJavaType)
/*     */   {
/* 524 */     return getClassIntrospector().forDeserializationWithBuilder(this, paramJavaType, this);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/DeserializationConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */