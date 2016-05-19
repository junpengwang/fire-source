/*     */ package com.shaded.fasterxml.jackson.databind.cfg;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
/*     */ import com.shaded.fasterxml.jackson.annotation.PropertyAccessor;
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.StdDateFormat;
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
/*     */ 
/*     */ public final class BaseSettings
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4939673998947122190L;
/*     */   protected final ClassIntrospector _classIntrospector;
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */   protected final VisibilityChecker<?> _visibilityChecker;
/*     */   protected final PropertyNamingStrategy _propertyNamingStrategy;
/*     */   protected final TypeFactory _typeFactory;
/*     */   protected final TypeResolverBuilder<?> _typeResolverBuilder;
/*     */   protected final DateFormat _dateFormat;
/*     */   protected final HandlerInstantiator _handlerInstantiator;
/*     */   protected final Locale _locale;
/*     */   protected final TimeZone _timeZone;
/*     */   protected final Base64Variant _defaultBase64;
/*     */   
/*     */   public BaseSettings(ClassIntrospector paramClassIntrospector, AnnotationIntrospector paramAnnotationIntrospector, VisibilityChecker<?> paramVisibilityChecker, PropertyNamingStrategy paramPropertyNamingStrategy, TypeFactory paramTypeFactory, TypeResolverBuilder<?> paramTypeResolverBuilder, DateFormat paramDateFormat, HandlerInstantiator paramHandlerInstantiator, Locale paramLocale, TimeZone paramTimeZone, Base64Variant paramBase64Variant)
/*     */   {
/* 141 */     this._classIntrospector = paramClassIntrospector;
/* 142 */     this._annotationIntrospector = paramAnnotationIntrospector;
/* 143 */     this._visibilityChecker = paramVisibilityChecker;
/* 144 */     this._propertyNamingStrategy = paramPropertyNamingStrategy;
/* 145 */     this._typeFactory = paramTypeFactory;
/* 146 */     this._typeResolverBuilder = paramTypeResolverBuilder;
/* 147 */     this._dateFormat = paramDateFormat;
/* 148 */     this._handlerInstantiator = paramHandlerInstantiator;
/* 149 */     this._locale = paramLocale;
/* 150 */     this._timeZone = paramTimeZone;
/* 151 */     this._defaultBase64 = paramBase64Variant;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseSettings withClassIntrospector(ClassIntrospector paramClassIntrospector)
/*     */   {
/* 161 */     if (this._classIntrospector == paramClassIntrospector) {
/* 162 */       return this;
/*     */     }
/* 164 */     return new BaseSettings(paramClassIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings withAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 170 */     if (this._annotationIntrospector == paramAnnotationIntrospector) {
/* 171 */       return this;
/*     */     }
/* 173 */     return new BaseSettings(this._classIntrospector, paramAnnotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings withInsertedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 179 */     return withAnnotationIntrospector(AnnotationIntrospectorPair.create(paramAnnotationIntrospector, this._annotationIntrospector));
/*     */   }
/*     */   
/*     */   public BaseSettings withAppendedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector) {
/* 183 */     return withAnnotationIntrospector(AnnotationIntrospectorPair.create(this._annotationIntrospector, paramAnnotationIntrospector));
/*     */   }
/*     */   
/*     */   public BaseSettings withVisibilityChecker(VisibilityChecker<?> paramVisibilityChecker) {
/* 187 */     if (this._visibilityChecker == paramVisibilityChecker) {
/* 188 */       return this;
/*     */     }
/* 190 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, paramVisibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility)
/*     */   {
/* 196 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker.withVisibility(paramPropertyAccessor, paramVisibility), this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseSettings withPropertyNamingStrategy(PropertyNamingStrategy paramPropertyNamingStrategy)
/*     */   {
/* 204 */     if (this._propertyNamingStrategy == paramPropertyNamingStrategy) {
/* 205 */       return this;
/*     */     }
/* 207 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, paramPropertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings withTypeFactory(TypeFactory paramTypeFactory)
/*     */   {
/* 213 */     if (this._typeFactory == paramTypeFactory) {
/* 214 */       return this;
/*     */     }
/* 216 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, paramTypeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings withTypeResolverBuilder(TypeResolverBuilder<?> paramTypeResolverBuilder)
/*     */   {
/* 222 */     if (this._typeResolverBuilder == paramTypeResolverBuilder) {
/* 223 */       return this;
/*     */     }
/* 225 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, paramTypeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings withDateFormat(DateFormat paramDateFormat)
/*     */   {
/* 231 */     if (this._dateFormat == paramDateFormat) {
/* 232 */       return this;
/*     */     }
/* 234 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, paramDateFormat, this._handlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings withHandlerInstantiator(HandlerInstantiator paramHandlerInstantiator)
/*     */   {
/* 240 */     if (this._handlerInstantiator == paramHandlerInstantiator) {
/* 241 */       return this;
/*     */     }
/* 243 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, paramHandlerInstantiator, this._locale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */   public BaseSettings with(Locale paramLocale)
/*     */   {
/* 249 */     if (this._locale == paramLocale) {
/* 250 */       return this;
/*     */     }
/* 252 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, paramLocale, this._timeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseSettings with(TimeZone paramTimeZone)
/*     */   {
/* 264 */     if (paramTimeZone == null) {
/* 265 */       throw new IllegalArgumentException();
/*     */     }
/* 267 */     Object localObject = this._dateFormat;
/* 268 */     if ((localObject instanceof StdDateFormat)) {
/* 269 */       localObject = ((StdDateFormat)localObject).withTimeZone(paramTimeZone);
/*     */     }
/*     */     else {
/* 272 */       localObject = (DateFormat)((DateFormat)localObject).clone();
/* 273 */       ((DateFormat)localObject).setTimeZone(paramTimeZone);
/*     */     }
/* 275 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, (DateFormat)localObject, this._handlerInstantiator, this._locale, paramTimeZone, this._defaultBase64);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseSettings with(Base64Variant paramBase64Variant)
/*     */   {
/* 285 */     if (paramBase64Variant == this._defaultBase64) {
/* 286 */       return this;
/*     */     }
/* 288 */     return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone, paramBase64Variant);
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
/*     */   public ClassIntrospector getClassIntrospector()
/*     */   {
/* 301 */     return this._classIntrospector;
/*     */   }
/*     */   
/*     */   public AnnotationIntrospector getAnnotationIntrospector() {
/* 305 */     return this._annotationIntrospector;
/*     */   }
/*     */   
/*     */   public VisibilityChecker<?> getVisibilityChecker() {
/* 309 */     return this._visibilityChecker;
/*     */   }
/*     */   
/*     */   public PropertyNamingStrategy getPropertyNamingStrategy() {
/* 313 */     return this._propertyNamingStrategy;
/*     */   }
/*     */   
/*     */   public TypeFactory getTypeFactory() {
/* 317 */     return this._typeFactory;
/*     */   }
/*     */   
/*     */   public TypeResolverBuilder<?> getTypeResolverBuilder() {
/* 321 */     return this._typeResolverBuilder;
/*     */   }
/*     */   
/*     */   public DateFormat getDateFormat() {
/* 325 */     return this._dateFormat;
/*     */   }
/*     */   
/*     */   public HandlerInstantiator getHandlerInstantiator() {
/* 329 */     return this._handlerInstantiator;
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/* 333 */     return this._locale;
/*     */   }
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 337 */     return this._timeZone;
/*     */   }
/*     */   
/*     */   public Base64Variant getBase64Variant() {
/* 341 */     return this._defaultBase64;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/cfg/BaseSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */