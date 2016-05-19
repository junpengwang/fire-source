/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter.None;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class BasicBeanDescription
/*     */   extends BeanDescription
/*     */ {
/*     */   protected final MapperConfig<?> _config;
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */   protected final AnnotatedClass _classInfo;
/*     */   protected TypeBindings _bindings;
/*     */   protected final List<BeanPropertyDefinition> _properties;
/*     */   protected ObjectIdInfo _objectIdInfo;
/*     */   protected AnnotatedMethod _anySetterMethod;
/*     */   protected Map<Object, AnnotatedMember> _injectables;
/*     */   protected Set<String> _ignoredPropertyNames;
/*     */   protected AnnotatedMethod _jsonValueMethod;
/*     */   protected AnnotatedMember _anyGetter;
/*     */   
/*     */   protected BasicBeanDescription(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, AnnotatedClass paramAnnotatedClass, List<BeanPropertyDefinition> paramList)
/*     */   {
/*  91 */     super(paramJavaType);
/*  92 */     this._config = paramMapperConfig;
/*  93 */     this._annotationIntrospector = (paramMapperConfig == null ? null : paramMapperConfig.getAnnotationIntrospector());
/*  94 */     this._classInfo = paramAnnotatedClass;
/*  95 */     this._properties = paramList;
/*     */   }
/*     */   
/*     */   protected BasicBeanDescription(POJOPropertiesCollector paramPOJOPropertiesCollector)
/*     */   {
/* 100 */     this(paramPOJOPropertiesCollector.getConfig(), paramPOJOPropertiesCollector.getType(), paramPOJOPropertiesCollector.getClassDef(), paramPOJOPropertiesCollector.getProperties());
/* 101 */     this._objectIdInfo = paramPOJOPropertiesCollector.getObjectIdInfo();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BasicBeanDescription forDeserialization(POJOPropertiesCollector paramPOJOPropertiesCollector)
/*     */   {
/* 110 */     BasicBeanDescription localBasicBeanDescription = new BasicBeanDescription(paramPOJOPropertiesCollector);
/* 111 */     localBasicBeanDescription._anySetterMethod = paramPOJOPropertiesCollector.getAnySetterMethod();
/* 112 */     localBasicBeanDescription._ignoredPropertyNames = paramPOJOPropertiesCollector.getIgnoredPropertyNames();
/* 113 */     localBasicBeanDescription._injectables = paramPOJOPropertiesCollector.getInjectables();
/* 114 */     localBasicBeanDescription._jsonValueMethod = paramPOJOPropertiesCollector.getJsonValueMethod();
/* 115 */     return localBasicBeanDescription;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BasicBeanDescription forSerialization(POJOPropertiesCollector paramPOJOPropertiesCollector)
/*     */   {
/* 124 */     BasicBeanDescription localBasicBeanDescription = new BasicBeanDescription(paramPOJOPropertiesCollector);
/* 125 */     localBasicBeanDescription._jsonValueMethod = paramPOJOPropertiesCollector.getJsonValueMethod();
/* 126 */     localBasicBeanDescription._anyGetter = paramPOJOPropertiesCollector.getAnyGetter();
/* 127 */     return localBasicBeanDescription;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BasicBeanDescription forOtherUse(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 138 */     return new BasicBeanDescription(paramMapperConfig, paramJavaType, paramAnnotatedClass, Collections.emptyList());
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
/*     */   public boolean removeProperty(String paramString)
/*     */   {
/* 157 */     Iterator localIterator = this._properties.iterator();
/* 158 */     while (localIterator.hasNext()) {
/* 159 */       BeanPropertyDefinition localBeanPropertyDefinition = (BeanPropertyDefinition)localIterator.next();
/* 160 */       if (localBeanPropertyDefinition.getName().equals(paramString)) {
/* 161 */         localIterator.remove();
/* 162 */         return true;
/*     */       }
/*     */     }
/* 165 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedClass getClassInfo()
/*     */   {
/* 175 */     return this._classInfo;
/*     */   }
/*     */   
/* 178 */   public ObjectIdInfo getObjectIdInfo() { return this._objectIdInfo; }
/*     */   
/*     */   public List<BeanPropertyDefinition> findProperties()
/*     */   {
/* 182 */     return this._properties;
/*     */   }
/*     */   
/*     */   public AnnotatedMethod findJsonValueMethod()
/*     */   {
/* 187 */     return this._jsonValueMethod;
/*     */   }
/*     */   
/*     */   public Set<String> getIgnoredPropertyNames()
/*     */   {
/* 192 */     if (this._ignoredPropertyNames == null) {
/* 193 */       return Collections.emptySet();
/*     */     }
/* 195 */     return this._ignoredPropertyNames;
/*     */   }
/*     */   
/*     */   public boolean hasKnownClassAnnotations()
/*     */   {
/* 200 */     return this._classInfo.hasAnnotations();
/*     */   }
/*     */   
/*     */   public Annotations getClassAnnotations()
/*     */   {
/* 205 */     return this._classInfo.getAnnotations();
/*     */   }
/*     */   
/*     */ 
/*     */   public TypeBindings bindingsForBeanType()
/*     */   {
/* 211 */     if (this._bindings == null) {
/* 212 */       this._bindings = new TypeBindings(this._config.getTypeFactory(), this._type);
/*     */     }
/* 214 */     return this._bindings;
/*     */   }
/*     */   
/*     */   public JavaType resolveType(Type paramType)
/*     */   {
/* 219 */     if (paramType == null) {
/* 220 */       return null;
/*     */     }
/* 222 */     return bindingsForBeanType().resolveType(paramType);
/*     */   }
/*     */   
/*     */   public AnnotatedConstructor findDefaultConstructor()
/*     */   {
/* 227 */     return this._classInfo.getDefaultConstructor();
/*     */   }
/*     */   
/*     */   public AnnotatedMethod findAnySetter()
/*     */     throws IllegalArgumentException
/*     */   {
/* 233 */     if (this._anySetterMethod != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 242 */       Class localClass = this._anySetterMethod.getRawParameterType(0);
/* 243 */       if ((localClass != String.class) && (localClass != Object.class)) {
/* 244 */         throw new IllegalArgumentException("Invalid 'any-setter' annotation on method " + this._anySetterMethod.getName() + "(): first argument not of type String or Object, but " + localClass.getName());
/*     */       }
/*     */     }
/* 247 */     return this._anySetterMethod;
/*     */   }
/*     */   
/*     */   public Map<Object, AnnotatedMember> findInjectables()
/*     */   {
/* 252 */     return this._injectables;
/*     */   }
/*     */   
/*     */   public List<AnnotatedConstructor> getConstructors()
/*     */   {
/* 257 */     return this._classInfo.getConstructors();
/*     */   }
/*     */   
/*     */ 
/*     */   public Object instantiateBean(boolean paramBoolean)
/*     */   {
/* 263 */     AnnotatedConstructor localAnnotatedConstructor = this._classInfo.getDefaultConstructor();
/* 264 */     if (localAnnotatedConstructor == null) {
/* 265 */       return null;
/*     */     }
/* 267 */     if (paramBoolean) {
/* 268 */       localAnnotatedConstructor.fixAccess();
/*     */     }
/*     */     try {
/* 271 */       return localAnnotatedConstructor.getAnnotated().newInstance(new Object[0]);
/*     */     } catch (Exception localException) {
/* 273 */       Object localObject = localException;
/* 274 */       while (((Throwable)localObject).getCause() != null) {
/* 275 */         localObject = ((Throwable)localObject).getCause();
/*     */       }
/* 277 */       if ((localObject instanceof Error)) throw ((Error)localObject);
/* 278 */       if ((localObject instanceof RuntimeException)) throw ((RuntimeException)localObject);
/* 279 */       throw new IllegalArgumentException("Failed to instantiate bean of type " + this._classInfo.getAnnotated().getName() + ": (" + localObject.getClass().getName() + ") " + ((Throwable)localObject).getMessage(), (Throwable)localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedMethod findMethod(String paramString, Class<?>[] paramArrayOfClass)
/*     */   {
/* 291 */     return this._classInfo.findMethod(paramString, paramArrayOfClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonFormat.Value findExpectedFormat(JsonFormat.Value paramValue)
/*     */   {
/* 303 */     if (this._annotationIntrospector != null) {
/* 304 */       JsonFormat.Value localValue = this._annotationIntrospector.findFormat(this._classInfo);
/* 305 */       if (localValue != null) {
/* 306 */         return localValue;
/*     */       }
/*     */     }
/* 309 */     return paramValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Converter<Object, Object> findSerializationConverter()
/*     */   {
/* 321 */     if (this._annotationIntrospector == null) {
/* 322 */       return null;
/*     */     }
/* 324 */     return _createConverter(this._annotationIntrospector.findSerializationConverter(this._classInfo));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonInclude.Include findSerializationInclusion(JsonInclude.Include paramInclude)
/*     */   {
/* 336 */     if (this._annotationIntrospector == null) {
/* 337 */       return paramInclude;
/*     */     }
/* 339 */     return this._annotationIntrospector.findSerializationInclusion(this._classInfo, paramInclude);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedMember findAnyGetter()
/*     */     throws IllegalArgumentException
/*     */   {
/* 351 */     if (this._anyGetter != null)
/*     */     {
/*     */ 
/*     */ 
/* 355 */       Class localClass = this._anyGetter.getRawType();
/* 356 */       if (!Map.class.isAssignableFrom(localClass)) {
/* 357 */         throw new IllegalArgumentException("Invalid 'any-getter' annotation on method " + this._anyGetter.getName() + "(): return type is not instance of java.util.Map");
/*     */       }
/*     */     }
/* 360 */     return this._anyGetter;
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<String, AnnotatedMember> findBackReferenceProperties()
/*     */   {
/* 366 */     HashMap localHashMap = null;
/* 367 */     for (BeanPropertyDefinition localBeanPropertyDefinition : this._properties) {
/* 368 */       AnnotatedMember localAnnotatedMember = localBeanPropertyDefinition.getMutator();
/* 369 */       if (localAnnotatedMember != null)
/*     */       {
/*     */ 
/* 372 */         AnnotationIntrospector.ReferenceProperty localReferenceProperty = this._annotationIntrospector.findReferenceType(localAnnotatedMember);
/* 373 */         if ((localReferenceProperty != null) && (localReferenceProperty.isBackReference())) {
/* 374 */           if (localHashMap == null) {
/* 375 */             localHashMap = new HashMap();
/*     */           }
/* 377 */           String str = localReferenceProperty.getName();
/* 378 */           if (localHashMap.put(str, localAnnotatedMember) != null)
/* 379 */             throw new IllegalArgumentException("Multiple back-reference properties with name '" + str + "'");
/*     */         }
/*     */       }
/*     */     }
/* 383 */     return localHashMap;
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
/*     */   public List<AnnotatedMethod> getFactoryMethods()
/*     */   {
/* 396 */     List localList = this._classInfo.getStaticMethods();
/* 397 */     if (localList.isEmpty()) {
/* 398 */       return localList;
/*     */     }
/* 400 */     ArrayList localArrayList = new ArrayList();
/* 401 */     for (AnnotatedMethod localAnnotatedMethod : localList) {
/* 402 */       if (isFactoryMethod(localAnnotatedMethod)) {
/* 403 */         localArrayList.add(localAnnotatedMethod);
/*     */       }
/*     */     }
/* 406 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */   public Constructor<?> findSingleArgConstructor(Class<?>... paramVarArgs)
/*     */   {
/* 412 */     for (AnnotatedConstructor localAnnotatedConstructor : this._classInfo.getConstructors())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 417 */       if (localAnnotatedConstructor.getParameterCount() == 1) {
/* 418 */         Class localClass = localAnnotatedConstructor.getRawParameterType(0);
/* 419 */         for (Class<?> localClass1 : paramVarArgs) {
/* 420 */           if (localClass1 == localClass) {
/* 421 */             return localAnnotatedConstructor.getAnnotated();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 426 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Method findFactoryMethod(Class<?>... paramVarArgs)
/*     */   {
/* 433 */     for (AnnotatedMethod localAnnotatedMethod : this._classInfo.getStaticMethods()) {
/* 434 */       if (isFactoryMethod(localAnnotatedMethod))
/*     */       {
/* 436 */         Class localClass = localAnnotatedMethod.getRawParameterType(0);
/* 437 */         for (Class<?> localClass1 : paramVarArgs)
/*     */         {
/* 439 */           if (localClass.isAssignableFrom(localClass1)) {
/* 440 */             return localAnnotatedMethod.getAnnotated();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 445 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isFactoryMethod(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 454 */     Class localClass = paramAnnotatedMethod.getRawReturnType();
/* 455 */     if (!getBeanClass().isAssignableFrom(localClass)) {
/* 456 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 463 */     if (this._annotationIntrospector.hasCreatorAnnotation(paramAnnotatedMethod)) {
/* 464 */       return true;
/*     */     }
/* 466 */     if ("valueOf".equals(paramAnnotatedMethod.getName())) {
/* 467 */       return true;
/*     */     }
/* 469 */     return false;
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
/*     */   public List<String> findCreatorPropertyNames()
/*     */   {
/* 483 */     ArrayList localArrayList = null;
/*     */     
/* 485 */     for (int i = 0; i < 2; i++) {
/* 486 */       List localList = i == 0 ? getConstructors() : getFactoryMethods();
/*     */       
/* 488 */       for (AnnotatedWithParams localAnnotatedWithParams : localList) {
/* 489 */         int j = localAnnotatedWithParams.getParameterCount();
/* 490 */         if (j >= 1) {
/* 491 */           PropertyName localPropertyName = this._annotationIntrospector.findNameForDeserialization(localAnnotatedWithParams.getParameter(0));
/* 492 */           if (localPropertyName != null)
/*     */           {
/*     */ 
/* 495 */             if (localArrayList == null) {
/* 496 */               localArrayList = new ArrayList();
/*     */             }
/* 498 */             localArrayList.add(localPropertyName.getSimpleName());
/* 499 */             for (int k = 1; k < j; k++) {
/* 500 */               localPropertyName = this._annotationIntrospector.findNameForDeserialization(localAnnotatedWithParams.getParameter(k));
/* 501 */               localArrayList.add(localPropertyName == null ? null : localPropertyName.getSimpleName());
/*     */             }
/*     */           }
/*     */         } } }
/* 505 */     if (localArrayList == null) {
/* 506 */       return Collections.emptyList();
/*     */     }
/* 508 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> findPOJOBuilder()
/*     */   {
/* 520 */     return this._annotationIntrospector == null ? null : this._annotationIntrospector.findPOJOBuilder(this._classInfo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonPOJOBuilder.Value findPOJOBuilderConfig()
/*     */   {
/* 527 */     return this._annotationIntrospector == null ? null : this._annotationIntrospector.findPOJOBuilderConfig(this._classInfo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Converter<Object, Object> findDeserializationConverter()
/*     */   {
/* 534 */     if (this._annotationIntrospector == null) {
/* 535 */       return null;
/*     */     }
/* 537 */     return _createConverter(this._annotationIntrospector.findDeserializationConverter(this._classInfo));
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
/*     */ 
/*     */ 
/*     */   public LinkedHashMap<String, AnnotatedField> _findPropertyFields(Collection<String> paramCollection, boolean paramBoolean)
/*     */   {
/* 559 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/* 560 */     for (BeanPropertyDefinition localBeanPropertyDefinition : this._properties) {
/* 561 */       AnnotatedField localAnnotatedField = localBeanPropertyDefinition.getField();
/* 562 */       if (localAnnotatedField != null) {
/* 563 */         String str = localBeanPropertyDefinition.getName();
/* 564 */         if ((paramCollection == null) || 
/* 565 */           (!paramCollection.contains(str)))
/*     */         {
/*     */ 
/*     */ 
/* 569 */           localLinkedHashMap.put(str, localAnnotatedField); }
/*     */       }
/*     */     }
/* 572 */     return localLinkedHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Converter<Object, Object> _createConverter(Object paramObject)
/*     */   {
/* 584 */     if (paramObject == null) {
/* 585 */       return null;
/*     */     }
/* 587 */     if ((paramObject instanceof Converter)) {
/* 588 */       return (Converter)paramObject;
/*     */     }
/* 590 */     if (!(paramObject instanceof Class)) {
/* 591 */       throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + paramObject.getClass().getName() + "; expected type Converter or Class<Converter> instead");
/*     */     }
/*     */     
/* 594 */     Class localClass = (Class)paramObject;
/*     */     
/* 596 */     if ((localClass == Converter.None.class) || (localClass == NoClass.class)) {
/* 597 */       return null;
/*     */     }
/* 599 */     if (!Converter.class.isAssignableFrom(localClass)) {
/* 600 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + localClass.getName() + "; expected Class<Converter>");
/*     */     }
/*     */     
/* 603 */     HandlerInstantiator localHandlerInstantiator = this._config.getHandlerInstantiator();
/* 604 */     Converter localConverter = localHandlerInstantiator == null ? null : localHandlerInstantiator.converterInstance(this._config, this._classInfo, localClass);
/* 605 */     if (localConverter == null) {
/* 606 */       localConverter = (Converter)ClassUtil.createInstance(localClass, this._config.canOverrideAccessModifiers());
/*     */     }
/*     */     
/* 609 */     return localConverter;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/BasicBeanDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */