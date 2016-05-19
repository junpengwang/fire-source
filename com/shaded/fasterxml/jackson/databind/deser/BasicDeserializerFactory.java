/*      */ package com.shaded.fasterxml.jackson.databind.deser;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*      */ import com.shaded.fasterxml.jackson.databind.BeanProperty.Std;
/*      */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*      */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*      */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*      */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*      */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*      */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*      */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*      */ import com.shaded.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.CreatorCollector;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*      */ import com.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
/*      */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*      */ import com.shaded.fasterxml.jackson.databind.type.MapLikeType;
/*      */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*      */ import com.shaded.fasterxml.jackson.databind.util.EnumResolver;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ 
/*      */ public abstract class BasicDeserializerFactory extends DeserializerFactory implements java.io.Serializable
/*      */ {
/*   36 */   private static final Class<?> CLASS_OBJECT = Object.class;
/*   37 */   private static final Class<?> CLASS_STRING = String.class;
/*   38 */   private static final Class<?> CLASS_CHAR_BUFFER = CharSequence.class;
/*   39 */   private static final Class<?> CLASS_ITERABLE = Iterable.class;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   51 */   static final HashMap<String, Class<? extends java.util.Map>> _mapFallbacks = new HashMap();
/*      */   static final HashMap<String, Class<? extends Collection>> _collectionFallbacks;
/*      */   
/*   54 */   static { _mapFallbacks.put(java.util.Map.class.getName(), java.util.LinkedHashMap.class);
/*   55 */     _mapFallbacks.put(java.util.concurrent.ConcurrentMap.class.getName(), java.util.concurrent.ConcurrentHashMap.class);
/*   56 */     _mapFallbacks.put(java.util.SortedMap.class.getName(), java.util.TreeMap.class);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   63 */     _mapFallbacks.put("java.util.NavigableMap", java.util.TreeMap.class);
/*      */     try {
/*   65 */       Class localClass1 = java.util.concurrent.ConcurrentNavigableMap.class;
/*   66 */       Class localClass2 = java.util.concurrent.ConcurrentSkipListMap.class;
/*      */       
/*   68 */       Class localClass3 = localClass2;
/*   69 */       _mapFallbacks.put(localClass1.getName(), localClass3);
/*      */     } catch (Throwable localThrowable) {
/*   71 */       System.err.println("Problems with (optional) types: " + localThrowable);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   80 */     _collectionFallbacks = new HashMap();
/*      */     
/*      */ 
/*   83 */     _collectionFallbacks.put(Collection.class.getName(), java.util.ArrayList.class);
/*   84 */     _collectionFallbacks.put(java.util.List.class.getName(), java.util.ArrayList.class);
/*   85 */     _collectionFallbacks.put(java.util.Set.class.getName(), java.util.HashSet.class);
/*   86 */     _collectionFallbacks.put(java.util.SortedSet.class.getName(), java.util.TreeSet.class);
/*   87 */     _collectionFallbacks.put(java.util.Queue.class.getName(), java.util.LinkedList.class);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   94 */     _collectionFallbacks.put("java.util.Deque", java.util.LinkedList.class);
/*   95 */     _collectionFallbacks.put("java.util.NavigableSet", java.util.TreeSet.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BasicDeserializerFactory(DeserializerFactoryConfig paramDeserializerFactoryConfig)
/*      */   {
/*  117 */     this._factoryConfig = paramDeserializerFactoryConfig;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public DeserializerFactoryConfig getFactoryConfig()
/*      */   {
/*  128 */     return this._factoryConfig;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final DeserializerFactory withAdditionalDeserializers(Deserializers paramDeserializers)
/*      */   {
/*  145 */     return withConfig(this._factoryConfig.withAdditionalDeserializers(paramDeserializers));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers paramKeyDeserializers)
/*      */   {
/*  154 */     return withConfig(this._factoryConfig.withAdditionalKeyDeserializers(paramKeyDeserializers));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final DeserializerFactory withDeserializerModifier(BeanDeserializerModifier paramBeanDeserializerModifier)
/*      */   {
/*  163 */     return withConfig(this._factoryConfig.withDeserializerModifier(paramBeanDeserializerModifier));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final DeserializerFactory withAbstractTypeResolver(com.shaded.fasterxml.jackson.databind.AbstractTypeResolver paramAbstractTypeResolver)
/*      */   {
/*  172 */     return withConfig(this._factoryConfig.withAbstractTypeResolver(paramAbstractTypeResolver));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final DeserializerFactory withValueInstantiators(ValueInstantiators paramValueInstantiators)
/*      */   {
/*  181 */     return withConfig(this._factoryConfig.withValueInstantiators(paramValueInstantiators));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JavaType mapAbstractType(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*      */     throws JsonMappingException
/*      */   {
/*      */     for (;;)
/*      */     {
/*  196 */       JavaType localJavaType = _mapAbstractType2(paramDeserializationConfig, paramJavaType);
/*  197 */       if (localJavaType == null) {
/*  198 */         return paramJavaType;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  204 */       Class localClass1 = paramJavaType.getRawClass();
/*  205 */       Class localClass2 = localJavaType.getRawClass();
/*  206 */       if ((localClass1 == localClass2) || (!localClass1.isAssignableFrom(localClass2))) {
/*  207 */         throw new IllegalArgumentException("Invalid abstract type resolution from " + paramJavaType + " to " + localJavaType + ": latter is not a subtype of former");
/*      */       }
/*  209 */       paramJavaType = localJavaType;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private JavaType _mapAbstractType2(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*      */     throws JsonMappingException
/*      */   {
/*  220 */     Class localClass = paramJavaType.getRawClass();
/*  221 */     if (this._factoryConfig.hasAbstractTypeResolvers()) {
/*  222 */       for (com.shaded.fasterxml.jackson.databind.AbstractTypeResolver localAbstractTypeResolver : this._factoryConfig.abstractTypeResolvers()) {
/*  223 */         JavaType localJavaType = localAbstractTypeResolver.findTypeMapping(paramDeserializationConfig, paramJavaType);
/*  224 */         if ((localJavaType != null) && (localJavaType.getRawClass() != localClass)) {
/*  225 */           return localJavaType;
/*      */         }
/*      */       }
/*      */     }
/*  229 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ValueInstantiator findValueInstantiator(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  248 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*      */     
/*  250 */     ValueInstantiator localValueInstantiator = null;
/*      */     
/*  252 */     com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass localAnnotatedClass = paramBeanDescription.getClassInfo();
/*  253 */     Object localObject1 = paramDeserializationContext.getAnnotationIntrospector().findValueInstantiator(localAnnotatedClass);
/*  254 */     if (localObject1 != null) {
/*  255 */       localValueInstantiator = _valueInstantiatorInstance(localDeserializationConfig, localAnnotatedClass, localObject1);
/*      */     }
/*  257 */     if (localValueInstantiator == null)
/*      */     {
/*      */ 
/*      */ 
/*  261 */       localValueInstantiator = _findStdValueInstantiator(localDeserializationConfig, paramBeanDescription);
/*  262 */       if (localValueInstantiator == null) {
/*  263 */         localValueInstantiator = _constructDefaultValueInstantiator(paramDeserializationContext, paramBeanDescription);
/*      */       }
/*      */     }
/*      */     
/*      */     Object localObject2;
/*  268 */     if (this._factoryConfig.hasValueInstantiators()) {
/*  269 */       for (localObject2 = this._factoryConfig.valueInstantiators().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (ValueInstantiators)((Iterator)localObject2).next();
/*  270 */         localValueInstantiator = ((ValueInstantiators)localObject3).findValueInstantiator(localDeserializationConfig, paramBeanDescription, localValueInstantiator);
/*      */         
/*  272 */         if (localValueInstantiator == null) {
/*  273 */           throw new JsonMappingException("Broken registered ValueInstantiators (of type " + localObject3.getClass().getName() + "): returned null ValueInstantiator");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     Object localObject3;
/*      */     
/*  280 */     if (localValueInstantiator.getIncompleteParameter() != null) {
/*  281 */       localObject2 = localValueInstantiator.getIncompleteParameter();
/*  282 */       localObject3 = ((AnnotatedParameter)localObject2).getOwner();
/*  283 */       throw new IllegalArgumentException("Argument #" + ((AnnotatedParameter)localObject2).getIndex() + " of constructor " + localObject3 + " has no property name annotation; must have name when multiple-paramater constructor annotated as Creator");
/*      */     }
/*      */     
/*  286 */     return localValueInstantiator;
/*      */   }
/*      */   
/*      */ 
/*      */   private ValueInstantiator _findStdValueInstantiator(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  293 */     return com.shaded.fasterxml.jackson.databind.deser.std.JacksonDeserializers.findValueInstantiator(paramDeserializationConfig, paramBeanDescription);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ValueInstantiator _constructDefaultValueInstantiator(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  304 */     boolean bool = paramDeserializationContext.canOverrideAccessModifiers();
/*  305 */     CreatorCollector localCreatorCollector = new CreatorCollector(paramBeanDescription, bool);
/*  306 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/*      */     
/*      */ 
/*  309 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*  310 */     VisibilityChecker localVisibilityChecker = localDeserializationConfig.getDefaultVisibilityChecker();
/*  311 */     localVisibilityChecker = localAnnotationIntrospector.findAutoDetectVisibility(paramBeanDescription.getClassInfo(), localVisibilityChecker);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  316 */     _addDeserializerFactoryMethods(paramDeserializationContext, paramBeanDescription, localVisibilityChecker, localAnnotationIntrospector, localCreatorCollector);
/*      */     
/*  318 */     if (paramBeanDescription.getType().isConcrete()) {
/*  319 */       _addDeserializerConstructors(paramDeserializationContext, paramBeanDescription, localVisibilityChecker, localAnnotationIntrospector, localCreatorCollector);
/*      */     }
/*  321 */     return localCreatorCollector.constructValueInstantiator(localDeserializationConfig);
/*      */   }
/*      */   
/*      */ 
/*      */   public ValueInstantiator _valueInstantiatorInstance(DeserializationConfig paramDeserializationConfig, Annotated paramAnnotated, Object paramObject)
/*      */     throws JsonMappingException
/*      */   {
/*  328 */     if (paramObject == null) {
/*  329 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  334 */     if ((paramObject instanceof ValueInstantiator)) {
/*  335 */       return (ValueInstantiator)paramObject;
/*      */     }
/*  337 */     if (!(paramObject instanceof Class)) {
/*  338 */       throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + paramObject.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
/*      */     }
/*      */     
/*      */ 
/*  342 */     Class localClass = (Class)paramObject;
/*  343 */     if (localClass == com.shaded.fasterxml.jackson.databind.annotation.NoClass.class) {
/*  344 */       return null;
/*      */     }
/*  346 */     if (!ValueInstantiator.class.isAssignableFrom(localClass)) {
/*  347 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + localClass.getName() + "; expected Class<ValueInstantiator>");
/*      */     }
/*      */     
/*  350 */     com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator localHandlerInstantiator = paramDeserializationConfig.getHandlerInstantiator();
/*  351 */     if (localHandlerInstantiator != null) {
/*  352 */       ValueInstantiator localValueInstantiator = localHandlerInstantiator.valueInstantiatorInstance(paramDeserializationConfig, paramAnnotated, localClass);
/*  353 */       if (localValueInstantiator != null) {
/*  354 */         return localValueInstantiator;
/*      */       }
/*      */     }
/*  357 */     return (ValueInstantiator)com.shaded.fasterxml.jackson.databind.util.ClassUtil.createInstance(localClass, paramDeserializationConfig.canOverrideAccessModifiers());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _addDeserializerConstructors(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector)
/*      */     throws JsonMappingException
/*      */   {
/*  370 */     AnnotatedConstructor localAnnotatedConstructor1 = paramBeanDescription.findDefaultConstructor();
/*  371 */     if ((localAnnotatedConstructor1 != null) && (
/*  372 */       (!paramCreatorCollector.hasDefaultCreator()) || (paramAnnotationIntrospector.hasCreatorAnnotation(localAnnotatedConstructor1)))) {
/*  373 */       paramCreatorCollector.setDefaultCreator(localAnnotatedConstructor1);
/*      */     }
/*      */     
/*      */ 
/*  377 */     String[] arrayOfString = null;
/*  378 */     AnnotatedConstructor localAnnotatedConstructor2 = null;
/*  379 */     for (Iterator localIterator = paramBeanDescription.findProperties().iterator(); localIterator.hasNext();) { localObject1 = (BeanPropertyDefinition)localIterator.next();
/*  380 */       if (((BeanPropertyDefinition)localObject1).getConstructorParameter() != null) {
/*  381 */         AnnotatedParameter localAnnotatedParameter1 = ((BeanPropertyDefinition)localObject1).getConstructorParameter();
/*  382 */         com.shaded.fasterxml.jackson.databind.introspect.AnnotatedWithParams localAnnotatedWithParams = localAnnotatedParameter1.getOwner();
/*  383 */         if ((localAnnotatedWithParams instanceof AnnotatedConstructor)) {
/*  384 */           if (localAnnotatedConstructor2 == null) {
/*  385 */             localAnnotatedConstructor2 = (AnnotatedConstructor)localAnnotatedWithParams;
/*  386 */             arrayOfString = new String[localAnnotatedConstructor2.getParameterCount()];
/*      */           }
/*  388 */           arrayOfString[localAnnotatedParameter1.getIndex()] = ((BeanPropertyDefinition)localObject1).getName();
/*      */         }
/*      */       }
/*      */     }
/*      */     Object localObject1;
/*  393 */     for (localIterator = paramBeanDescription.getConstructors().iterator(); localIterator.hasNext();) { localObject1 = (AnnotatedConstructor)localIterator.next();
/*  394 */       int i = ((AnnotatedConstructor)localObject1).getParameterCount();
/*  395 */       boolean bool1 = (paramAnnotationIntrospector.hasCreatorAnnotation((Annotated)localObject1)) || (localObject1 == localAnnotatedConstructor2);
/*  396 */       boolean bool2 = paramVisibilityChecker.isCreatorVisible((AnnotatedMember)localObject1);
/*      */       Object localObject2;
/*  398 */       if (i == 1) {
/*  399 */         localObject2 = localObject1 == localAnnotatedConstructor2 ? arrayOfString[0] : null;
/*  400 */         _handleSingleArgumentConstructor(paramDeserializationContext, paramBeanDescription, paramVisibilityChecker, paramAnnotationIntrospector, paramCreatorCollector, (AnnotatedConstructor)localObject1, bool1, bool2, (String)localObject2);
/*      */ 
/*      */ 
/*      */       }
/*  404 */       else if ((bool1) || (bool2))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  413 */         localObject2 = null;
/*  414 */         int j = 0;
/*  415 */         int k = 0;
/*  416 */         CreatorProperty[] arrayOfCreatorProperty = new CreatorProperty[i];
/*  417 */         for (int m = 0; m < i; m++) {
/*  418 */           AnnotatedParameter localAnnotatedParameter2 = ((AnnotatedConstructor)localObject1).getParameter(m);
/*  419 */           String str = null;
/*  420 */           if (localObject1 == localAnnotatedConstructor2) {
/*  421 */             str = arrayOfString[m];
/*      */           }
/*  423 */           if (str == null) {
/*  424 */             localObject3 = localAnnotatedParameter2 == null ? null : paramAnnotationIntrospector.findNameForDeserialization(localAnnotatedParameter2);
/*  425 */             str = localObject3 == null ? null : ((PropertyName)localObject3).getSimpleName();
/*      */           }
/*  427 */           Object localObject3 = paramAnnotationIntrospector.findInjectableValueId(localAnnotatedParameter2);
/*  428 */           if ((str != null) && (str.length() > 0)) {
/*  429 */             j++;
/*  430 */             arrayOfCreatorProperty[m] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, str, m, localAnnotatedParameter2, localObject3);
/*  431 */           } else if (localObject3 != null) {
/*  432 */             k++;
/*  433 */             arrayOfCreatorProperty[m] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, str, m, localAnnotatedParameter2, localObject3);
/*  434 */           } else if (localObject2 == null) {
/*  435 */             localObject2 = localAnnotatedParameter2;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  440 */         if ((bool1) || (j > 0) || (k > 0))
/*      */         {
/*  442 */           if (j + k == i) {
/*  443 */             paramCreatorCollector.addPropertyCreator((com.shaded.fasterxml.jackson.databind.introspect.AnnotatedWithParams)localObject1, arrayOfCreatorProperty);
/*  444 */           } else if ((j == 0) && (k + 1 == i))
/*      */           {
/*  446 */             paramCreatorCollector.addDelegatingCreator((com.shaded.fasterxml.jackson.databind.introspect.AnnotatedWithParams)localObject1, arrayOfCreatorProperty);
/*      */           } else {
/*  448 */             paramCreatorCollector.addIncompeteParameter((AnnotatedParameter)localObject2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _handleSingleArgumentConstructor(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector, AnnotatedConstructor paramAnnotatedConstructor, boolean paramBoolean1, boolean paramBoolean2, String paramString)
/*      */     throws JsonMappingException
/*      */   {
/*  461 */     AnnotatedParameter localAnnotatedParameter = paramAnnotatedConstructor.getParameter(0);
/*  462 */     if (paramString == null) {
/*  463 */       localObject1 = localAnnotatedParameter == null ? null : paramAnnotationIntrospector.findNameForDeserialization(localAnnotatedParameter);
/*  464 */       paramString = localObject1 == null ? null : ((PropertyName)localObject1).getSimpleName();
/*      */     }
/*  466 */     Object localObject1 = paramAnnotationIntrospector.findInjectableValueId(localAnnotatedParameter);
/*      */     
/*  468 */     if ((localObject1 != null) || ((paramString != null) && (paramString.length() > 0)))
/*      */     {
/*  470 */       localObject2 = new CreatorProperty[1];
/*  471 */       localObject2[0] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, paramString, 0, localAnnotatedParameter, localObject1);
/*  472 */       paramCreatorCollector.addPropertyCreator(paramAnnotatedConstructor, (CreatorProperty[])localObject2);
/*  473 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  477 */     Object localObject2 = paramAnnotatedConstructor.getRawParameterType(0);
/*  478 */     if (localObject2 == String.class) {
/*  479 */       if ((paramBoolean1) || (paramBoolean2)) {
/*  480 */         paramCreatorCollector.addStringCreator(paramAnnotatedConstructor);
/*      */       }
/*  482 */       return true;
/*      */     }
/*  484 */     if ((localObject2 == Integer.TYPE) || (localObject2 == Integer.class)) {
/*  485 */       if ((paramBoolean1) || (paramBoolean2)) {
/*  486 */         paramCreatorCollector.addIntCreator(paramAnnotatedConstructor);
/*      */       }
/*  488 */       return true;
/*      */     }
/*  490 */     if ((localObject2 == Long.TYPE) || (localObject2 == Long.class)) {
/*  491 */       if ((paramBoolean1) || (paramBoolean2)) {
/*  492 */         paramCreatorCollector.addLongCreator(paramAnnotatedConstructor);
/*      */       }
/*  494 */       return true;
/*      */     }
/*  496 */     if ((localObject2 == Double.TYPE) || (localObject2 == Double.class)) {
/*  497 */       if ((paramBoolean1) || (paramBoolean2)) {
/*  498 */         paramCreatorCollector.addDoubleCreator(paramAnnotatedConstructor);
/*      */       }
/*  500 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  504 */     if (paramBoolean1) {
/*  505 */       paramCreatorCollector.addDelegatingCreator(paramAnnotatedConstructor, null);
/*  506 */       return true;
/*      */     }
/*  508 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void _addDeserializerFactoryMethods(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector)
/*      */     throws JsonMappingException
/*      */   {
/*  516 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*  517 */     for (AnnotatedMethod localAnnotatedMethod : paramBeanDescription.getFactoryMethods()) {
/*  518 */       boolean bool = paramAnnotationIntrospector.hasCreatorAnnotation(localAnnotatedMethod);
/*  519 */       int i = localAnnotatedMethod.getParameterCount();
/*      */       
/*  521 */       if (i == 0) {
/*  522 */         if (bool) {
/*  523 */           paramCreatorCollector.setDefaultCreator(localAnnotatedMethod);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  528 */         if (i == 1) {
/*  529 */           localObject1 = localAnnotatedMethod.getParameter(0);
/*  530 */           localObject2 = localObject1 == null ? null : paramAnnotationIntrospector.findNameForDeserialization((Annotated)localObject1);
/*  531 */           String str1 = localObject2 == null ? null : ((PropertyName)localObject2).getSimpleName();
/*  532 */           Object localObject3 = paramAnnotationIntrospector.findInjectableValueId((AnnotatedMember)localObject1);
/*      */           
/*  534 */           if ((localObject3 == null) && ((str1 == null) || (str1.length() == 0))) {
/*  535 */             _handleSingleArgumentFactory(localDeserializationConfig, paramBeanDescription, paramVisibilityChecker, paramAnnotationIntrospector, paramCreatorCollector, localAnnotatedMethod, bool);
/*      */             
/*      */ 
/*  538 */             continue;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  543 */           if (!paramAnnotationIntrospector.hasCreatorAnnotation(localAnnotatedMethod)) {
/*      */             continue;
/*      */           }
/*      */         }
/*      */         
/*  548 */         Object localObject1 = null;
/*  549 */         Object localObject2 = new CreatorProperty[i];
/*  550 */         int j = 0;
/*  551 */         int k = 0;
/*  552 */         for (int m = 0; m < i; m++) {
/*  553 */           AnnotatedParameter localAnnotatedParameter = localAnnotatedMethod.getParameter(m);
/*  554 */           PropertyName localPropertyName = localAnnotatedParameter == null ? null : paramAnnotationIntrospector.findNameForDeserialization(localAnnotatedParameter);
/*  555 */           String str2 = localPropertyName == null ? null : localPropertyName.getSimpleName();
/*  556 */           Object localObject4 = paramAnnotationIntrospector.findInjectableValueId(localAnnotatedParameter);
/*  557 */           if ((str2 != null) && (str2.length() > 0)) {
/*  558 */             j++;
/*  559 */             localObject2[m] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, str2, m, localAnnotatedParameter, localObject4);
/*  560 */           } else if (localObject4 != null) {
/*  561 */             k++;
/*  562 */             localObject2[m] = constructCreatorProperty(paramDeserializationContext, paramBeanDescription, str2, m, localAnnotatedParameter, localObject4);
/*  563 */           } else if (localObject1 == null) {
/*  564 */             localObject1 = localAnnotatedParameter;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  569 */         if ((bool) || (j > 0) || (k > 0))
/*      */         {
/*  571 */           if (j + k == i) {
/*  572 */             paramCreatorCollector.addPropertyCreator(localAnnotatedMethod, (CreatorProperty[])localObject2);
/*  573 */           } else if ((j == 0) && (k + 1 == i))
/*      */           {
/*  575 */             paramCreatorCollector.addDelegatingCreator(localAnnotatedMethod, (CreatorProperty[])localObject2);
/*      */           } else {
/*  577 */             throw new IllegalArgumentException("Argument #" + ((AnnotatedParameter)localObject1).getIndex() + " of factory method " + localAnnotatedMethod + " has no property name annotation; must have name when multiple-paramater constructor annotated as Creator");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _handleSingleArgumentFactory(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, VisibilityChecker<?> paramVisibilityChecker, AnnotationIntrospector paramAnnotationIntrospector, CreatorCollector paramCreatorCollector, AnnotatedMethod paramAnnotatedMethod, boolean paramBoolean)
/*      */     throws JsonMappingException
/*      */   {
/*  590 */     Class localClass = paramAnnotatedMethod.getRawParameterType(0);
/*      */     
/*  592 */     if (localClass == String.class) {
/*  593 */       if ((paramBoolean) || (paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod))) {
/*  594 */         paramCreatorCollector.addStringCreator(paramAnnotatedMethod);
/*      */       }
/*  596 */       return true;
/*      */     }
/*  598 */     if ((localClass == Integer.TYPE) || (localClass == Integer.class)) {
/*  599 */       if ((paramBoolean) || (paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod))) {
/*  600 */         paramCreatorCollector.addIntCreator(paramAnnotatedMethod);
/*      */       }
/*  602 */       return true;
/*      */     }
/*  604 */     if ((localClass == Long.TYPE) || (localClass == Long.class)) {
/*  605 */       if ((paramBoolean) || (paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod))) {
/*  606 */         paramCreatorCollector.addLongCreator(paramAnnotatedMethod);
/*      */       }
/*  608 */       return true;
/*      */     }
/*  610 */     if ((localClass == Double.TYPE) || (localClass == Double.class)) {
/*  611 */       if ((paramBoolean) || (paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod))) {
/*  612 */         paramCreatorCollector.addDoubleCreator(paramAnnotatedMethod);
/*      */       }
/*  614 */       return true;
/*      */     }
/*  616 */     if ((localClass == Boolean.TYPE) || (localClass == Boolean.class)) {
/*  617 */       if ((paramBoolean) || (paramVisibilityChecker.isCreatorVisible(paramAnnotatedMethod))) {
/*  618 */         paramCreatorCollector.addBooleanCreator(paramAnnotatedMethod);
/*      */       }
/*  620 */       return true;
/*      */     }
/*  622 */     if (paramAnnotationIntrospector.hasCreatorAnnotation(paramAnnotatedMethod)) {
/*  623 */       paramCreatorCollector.addDelegatingCreator(paramAnnotatedMethod, null);
/*  624 */       return true;
/*      */     }
/*  626 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected CreatorProperty constructCreatorProperty(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, String paramString, int paramInt, AnnotatedParameter paramAnnotatedParameter, Object paramObject)
/*      */     throws JsonMappingException
/*      */   {
/*  640 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*  641 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/*  642 */     Boolean localBoolean = localAnnotationIntrospector == null ? null : localAnnotationIntrospector.hasRequiredMarker(paramAnnotatedParameter);
/*      */     
/*  644 */     boolean bool = localBoolean == null ? false : localBoolean.booleanValue();
/*      */     
/*  646 */     JavaType localJavaType1 = localDeserializationConfig.getTypeFactory().constructType(paramAnnotatedParameter.getParameterType(), paramBeanDescription.bindingsForBeanType());
/*  647 */     BeanProperty.Std localStd = new BeanProperty.Std(paramString, localJavaType1, localAnnotationIntrospector.findWrapperName(paramAnnotatedParameter), paramBeanDescription.getClassAnnotations(), paramAnnotatedParameter, bool);
/*      */     
/*      */ 
/*  650 */     JavaType localJavaType2 = resolveType(paramDeserializationContext, paramBeanDescription, localJavaType1, paramAnnotatedParameter);
/*  651 */     if (localJavaType2 != localJavaType1) {
/*  652 */       localStd = localStd.withType(localJavaType2);
/*      */     }
/*      */     
/*  655 */     JsonDeserializer localJsonDeserializer = findDeserializerFromAnnotation(paramDeserializationContext, paramAnnotatedParameter);
/*      */     
/*  657 */     localJavaType2 = modifyTypeByAnnotation(paramDeserializationContext, paramAnnotatedParameter, localJavaType2);
/*      */     
/*      */ 
/*  660 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType2.getTypeHandler();
/*      */     
/*  662 */     if (localTypeDeserializer == null) {
/*  663 */       localTypeDeserializer = findTypeDeserializer(localDeserializationConfig, localJavaType2);
/*      */     }
/*      */     
/*  666 */     CreatorProperty localCreatorProperty = new CreatorProperty(paramString, localJavaType2, localStd.getWrapperName(), localTypeDeserializer, paramBeanDescription.getClassAnnotations(), paramAnnotatedParameter, paramInt, paramObject, localStd.isRequired());
/*      */     
/*      */ 
/*  669 */     if (localJsonDeserializer != null) {
/*  670 */       localCreatorProperty = localCreatorProperty.withValueDeserializer(localJsonDeserializer);
/*      */     }
/*  672 */     return localCreatorProperty;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> createArrayDeserializer(DeserializationContext paramDeserializationContext, com.shaded.fasterxml.jackson.databind.type.ArrayType paramArrayType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  686 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*  687 */     JavaType localJavaType = paramArrayType.getContentType();
/*      */     
/*      */ 
/*  690 */     JsonDeserializer localJsonDeserializer = (JsonDeserializer)localJavaType.getValueHandler();
/*      */     
/*  692 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType.getTypeHandler();
/*      */     
/*  694 */     if (localTypeDeserializer == null) {
/*  695 */       localTypeDeserializer = findTypeDeserializer(localDeserializationConfig, localJavaType);
/*      */     }
/*      */     
/*  698 */     Object localObject1 = _findCustomArrayDeserializer(paramArrayType, localDeserializationConfig, paramBeanDescription, localTypeDeserializer, localJsonDeserializer);
/*      */     Object localObject2;
/*  700 */     if (localObject1 == null) {
/*  701 */       if (localJsonDeserializer == null) {
/*  702 */         localObject2 = localJavaType.getRawClass();
/*  703 */         if (localJavaType.isPrimitive())
/*  704 */           return com.shaded.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers.forType((Class)localObject2);
/*  705 */         if (localObject2 == String.class) {
/*  706 */           return com.shaded.fasterxml.jackson.databind.deser.std.StringArrayDeserializer.instance;
/*      */         }
/*      */       }
/*  709 */       if (localObject1 == null) {
/*  710 */         localObject1 = new com.shaded.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer(paramArrayType, localJsonDeserializer, localTypeDeserializer);
/*      */       }
/*      */     }
/*      */     
/*  714 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/*  715 */       for (localObject2 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { BeanDeserializerModifier localBeanDeserializerModifier = (BeanDeserializerModifier)((Iterator)localObject2).next();
/*  716 */         localObject1 = localBeanDeserializerModifier.modifyArrayDeserializer(localDeserializationConfig, paramArrayType, paramBeanDescription, (JsonDeserializer)localObject1);
/*      */       }
/*      */     }
/*  719 */     return (JsonDeserializer<?>)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<?> _findCustomArrayDeserializer(com.shaded.fasterxml.jackson.databind.type.ArrayType paramArrayType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*      */     throws JsonMappingException
/*      */   {
/*  727 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/*  728 */       JsonDeserializer localJsonDeserializer = localDeserializers.findArrayDeserializer(paramArrayType, paramDeserializationConfig, paramBeanDescription, paramTypeDeserializer, paramJsonDeserializer);
/*      */       
/*  730 */       if (localJsonDeserializer != null) {
/*  731 */         return localJsonDeserializer;
/*      */       }
/*      */     }
/*  734 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> createCollectionDeserializer(DeserializationContext paramDeserializationContext, CollectionType paramCollectionType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  748 */     JavaType localJavaType = paramCollectionType.getContentType();
/*      */     
/*  750 */     JsonDeserializer localJsonDeserializer = (JsonDeserializer)localJavaType.getValueHandler();
/*  751 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*      */     
/*      */ 
/*  754 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType.getTypeHandler();
/*      */     
/*  756 */     if (localTypeDeserializer == null) {
/*  757 */       localTypeDeserializer = findTypeDeserializer(localDeserializationConfig, localJavaType);
/*      */     }
/*      */     
/*      */ 
/*  761 */     Object localObject1 = _findCustomCollectionDeserializer(paramCollectionType, localDeserializationConfig, paramBeanDescription, localTypeDeserializer, localJsonDeserializer);
/*      */     Object localObject2;
/*  763 */     if (localObject1 == null) {
/*  764 */       localObject2 = paramCollectionType.getRawClass();
/*  765 */       if (localJsonDeserializer == null)
/*      */       {
/*  767 */         if (java.util.EnumSet.class.isAssignableFrom((Class)localObject2)) {
/*  768 */           localObject1 = new com.shaded.fasterxml.jackson.databind.deser.std.EnumSetDeserializer(localJavaType, null);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  782 */     if (localObject1 == null) {
/*  783 */       if ((paramCollectionType.isInterface()) || (paramCollectionType.isAbstract())) {
/*  784 */         localObject2 = _mapAbstractCollectionType(paramCollectionType, localDeserializationConfig);
/*  785 */         if (localObject2 == null) {
/*  786 */           throw new IllegalArgumentException("Can not find a deserializer for non-concrete Collection type " + paramCollectionType);
/*      */         }
/*  788 */         paramCollectionType = (CollectionType)localObject2;
/*      */         
/*  790 */         paramBeanDescription = localDeserializationConfig.introspectForCreation(paramCollectionType);
/*      */       }
/*  792 */       localObject2 = findValueInstantiator(paramDeserializationContext, paramBeanDescription);
/*  793 */       if (!((ValueInstantiator)localObject2).canCreateUsingDefault())
/*      */       {
/*  795 */         if (paramCollectionType.getRawClass() == java.util.concurrent.ArrayBlockingQueue.class) {
/*  796 */           return new com.shaded.fasterxml.jackson.databind.deser.std.ArrayBlockingQueueDeserializer(paramCollectionType, localJsonDeserializer, localTypeDeserializer, (ValueInstantiator)localObject2, null);
/*      */         }
/*      */       }
/*      */       
/*  800 */       if (localJavaType.getRawClass() == String.class)
/*      */       {
/*  802 */         localObject1 = new com.shaded.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer(paramCollectionType, localJsonDeserializer, (ValueInstantiator)localObject2);
/*      */       } else {
/*  804 */         localObject1 = new com.shaded.fasterxml.jackson.databind.deser.std.CollectionDeserializer(paramCollectionType, localJsonDeserializer, localTypeDeserializer, (ValueInstantiator)localObject2);
/*      */       }
/*      */     }
/*      */     
/*  808 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/*  809 */       for (localObject2 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { BeanDeserializerModifier localBeanDeserializerModifier = (BeanDeserializerModifier)((Iterator)localObject2).next();
/*  810 */         localObject1 = localBeanDeserializerModifier.modifyCollectionDeserializer(localDeserializationConfig, paramCollectionType, paramBeanDescription, (JsonDeserializer)localObject1);
/*      */       }
/*      */     }
/*  813 */     return (JsonDeserializer<?>)localObject1;
/*      */   }
/*      */   
/*      */   protected CollectionType _mapAbstractCollectionType(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig)
/*      */   {
/*  818 */     Class localClass = paramJavaType.getRawClass();
/*  819 */     localClass = (Class)_collectionFallbacks.get(localClass.getName());
/*  820 */     if (localClass == null) {
/*  821 */       return null;
/*      */     }
/*  823 */     return (CollectionType)paramDeserializationConfig.constructSpecializedType(paramJavaType, localClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<?> _findCustomCollectionDeserializer(CollectionType paramCollectionType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*      */     throws JsonMappingException
/*      */   {
/*  831 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/*  832 */       JsonDeserializer localJsonDeserializer = localDeserializers.findCollectionDeserializer(paramCollectionType, paramDeserializationConfig, paramBeanDescription, paramTypeDeserializer, paramJsonDeserializer);
/*      */       
/*  834 */       if (localJsonDeserializer != null) {
/*  835 */         return localJsonDeserializer;
/*      */       }
/*      */     }
/*  838 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationContext paramDeserializationContext, CollectionLikeType paramCollectionLikeType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  847 */     JavaType localJavaType = paramCollectionLikeType.getContentType();
/*      */     
/*  849 */     JsonDeserializer localJsonDeserializer1 = (JsonDeserializer)localJavaType.getValueHandler();
/*  850 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*      */     
/*      */ 
/*  853 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType.getTypeHandler();
/*      */     
/*  855 */     if (localTypeDeserializer == null) {
/*  856 */       localTypeDeserializer = findTypeDeserializer(localDeserializationConfig, localJavaType);
/*      */     }
/*  858 */     JsonDeserializer localJsonDeserializer2 = _findCustomCollectionLikeDeserializer(paramCollectionLikeType, localDeserializationConfig, paramBeanDescription, localTypeDeserializer, localJsonDeserializer1);
/*      */     
/*  860 */     if (localJsonDeserializer2 != null)
/*      */     {
/*  862 */       if (this._factoryConfig.hasDeserializerModifiers()) {
/*  863 */         for (BeanDeserializerModifier localBeanDeserializerModifier : this._factoryConfig.deserializerModifiers()) {
/*  864 */           localJsonDeserializer2 = localBeanDeserializerModifier.modifyCollectionLikeDeserializer(localDeserializationConfig, paramCollectionLikeType, paramBeanDescription, localJsonDeserializer2);
/*      */         }
/*      */       }
/*      */     }
/*  868 */     return localJsonDeserializer2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<?> _findCustomCollectionLikeDeserializer(CollectionLikeType paramCollectionLikeType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*      */     throws JsonMappingException
/*      */   {
/*  876 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/*  877 */       JsonDeserializer localJsonDeserializer = localDeserializers.findCollectionLikeDeserializer(paramCollectionLikeType, paramDeserializationConfig, paramBeanDescription, paramTypeDeserializer, paramJsonDeserializer);
/*      */       
/*  879 */       if (localJsonDeserializer != null) {
/*  880 */         return localJsonDeserializer;
/*      */       }
/*      */     }
/*  883 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> createMapDeserializer(DeserializationContext paramDeserializationContext, MapType paramMapType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  897 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*  898 */     JavaType localJavaType1 = paramMapType.getKeyType();
/*  899 */     JavaType localJavaType2 = paramMapType.getContentType();
/*      */     
/*      */ 
/*      */ 
/*  903 */     JsonDeserializer localJsonDeserializer = (JsonDeserializer)localJavaType2.getValueHandler();
/*      */     
/*      */ 
/*  906 */     KeyDeserializer localKeyDeserializer = (KeyDeserializer)localJavaType1.getValueHandler();
/*      */     
/*  908 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType2.getTypeHandler();
/*      */     
/*  910 */     if (localTypeDeserializer == null) {
/*  911 */       localTypeDeserializer = findTypeDeserializer(localDeserializationConfig, localJavaType2);
/*      */     }
/*      */     
/*      */ 
/*  915 */     Object localObject1 = _findCustomMapDeserializer(paramMapType, localDeserializationConfig, paramBeanDescription, localKeyDeserializer, localTypeDeserializer, localJsonDeserializer);
/*      */     Object localObject2;
/*      */     Object localObject3;
/*  918 */     if (localObject1 == null)
/*      */     {
/*  920 */       localObject2 = paramMapType.getRawClass();
/*  921 */       if (java.util.EnumMap.class.isAssignableFrom((Class)localObject2)) {
/*  922 */         localObject3 = localJavaType1.getRawClass();
/*  923 */         if ((localObject3 == null) || (!((Class)localObject3).isEnum())) {
/*  924 */           throw new IllegalArgumentException("Can not construct EnumMap; generic (key) type not available");
/*      */         }
/*  926 */         localObject1 = new com.shaded.fasterxml.jackson.databind.deser.std.EnumMapDeserializer(paramMapType, null, localJsonDeserializer, localTypeDeserializer);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  940 */       if (localObject1 == null) {
/*  941 */         if ((paramMapType.isInterface()) || (paramMapType.isAbstract()))
/*      */         {
/*  943 */           localObject3 = (Class)_mapFallbacks.get(((Class)localObject2).getName());
/*  944 */           if (localObject3 == null) {
/*  945 */             throw new IllegalArgumentException("Can not find a deserializer for non-concrete Map type " + paramMapType);
/*      */           }
/*  947 */           localObject2 = localObject3;
/*  948 */           paramMapType = (MapType)localDeserializationConfig.constructSpecializedType(paramMapType, (Class)localObject2);
/*      */           
/*  950 */           paramBeanDescription = localDeserializationConfig.introspectForCreation(paramMapType);
/*      */         }
/*  952 */         localObject3 = findValueInstantiator(paramDeserializationContext, paramBeanDescription);
/*  953 */         com.shaded.fasterxml.jackson.databind.deser.std.MapDeserializer localMapDeserializer = new com.shaded.fasterxml.jackson.databind.deser.std.MapDeserializer(paramMapType, (ValueInstantiator)localObject3, localKeyDeserializer, localJsonDeserializer, localTypeDeserializer);
/*  954 */         localMapDeserializer.setIgnorableProperties(localDeserializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(paramBeanDescription.getClassInfo()));
/*  955 */         localObject1 = localMapDeserializer;
/*      */       }
/*      */     }
/*      */     
/*  959 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/*  960 */       for (localObject2 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (BeanDeserializerModifier)((Iterator)localObject2).next();
/*  961 */         localObject1 = ((BeanDeserializerModifier)localObject3).modifyMapDeserializer(localDeserializationConfig, paramMapType, paramBeanDescription, (JsonDeserializer)localObject1);
/*      */       }
/*      */     }
/*  964 */     return (JsonDeserializer<?>)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> createMapLikeDeserializer(DeserializationContext paramDeserializationContext, MapLikeType paramMapLikeType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/*  973 */     JavaType localJavaType1 = paramMapLikeType.getKeyType();
/*  974 */     JavaType localJavaType2 = paramMapLikeType.getContentType();
/*  975 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*      */     
/*      */ 
/*      */ 
/*  979 */     JsonDeserializer localJsonDeserializer1 = (JsonDeserializer)localJavaType2.getValueHandler();
/*      */     
/*      */ 
/*  982 */     KeyDeserializer localKeyDeserializer = (KeyDeserializer)localJavaType1.getValueHandler();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  989 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType2.getTypeHandler();
/*      */     
/*  991 */     if (localTypeDeserializer == null) {
/*  992 */       localTypeDeserializer = findTypeDeserializer(localDeserializationConfig, localJavaType2);
/*      */     }
/*  994 */     JsonDeserializer localJsonDeserializer2 = _findCustomMapLikeDeserializer(paramMapLikeType, localDeserializationConfig, paramBeanDescription, localKeyDeserializer, localTypeDeserializer, localJsonDeserializer1);
/*      */     
/*  996 */     if (localJsonDeserializer2 != null)
/*      */     {
/*  998 */       if (this._factoryConfig.hasDeserializerModifiers()) {
/*  999 */         for (BeanDeserializerModifier localBeanDeserializerModifier : this._factoryConfig.deserializerModifiers()) {
/* 1000 */           localJsonDeserializer2 = localBeanDeserializerModifier.modifyMapLikeDeserializer(localDeserializationConfig, paramMapLikeType, paramBeanDescription, localJsonDeserializer2);
/*      */         }
/*      */       }
/*      */     }
/* 1004 */     return localJsonDeserializer2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<?> _findCustomMapDeserializer(MapType paramMapType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*      */     throws JsonMappingException
/*      */   {
/* 1013 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/* 1014 */       JsonDeserializer localJsonDeserializer = localDeserializers.findMapDeserializer(paramMapType, paramDeserializationConfig, paramBeanDescription, paramKeyDeserializer, paramTypeDeserializer, paramJsonDeserializer);
/*      */       
/* 1016 */       if (localJsonDeserializer != null) {
/* 1017 */         return localJsonDeserializer;
/*      */       }
/*      */     }
/* 1020 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<?> _findCustomMapLikeDeserializer(MapLikeType paramMapLikeType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer)
/*      */     throws JsonMappingException
/*      */   {
/* 1029 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/* 1030 */       JsonDeserializer localJsonDeserializer = localDeserializers.findMapLikeDeserializer(paramMapLikeType, paramDeserializationConfig, paramBeanDescription, paramKeyDeserializer, paramTypeDeserializer, paramJsonDeserializer);
/*      */       
/* 1032 */       if (localJsonDeserializer != null) {
/* 1033 */         return localJsonDeserializer;
/*      */       }
/*      */     }
/* 1036 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> createEnumDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/* 1053 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/* 1054 */     Class localClass1 = paramJavaType.getRawClass();
/*      */     
/* 1056 */     Object localObject1 = _findCustomEnumDeserializer(localClass1, localDeserializationConfig, paramBeanDescription);
/* 1057 */     Iterator localIterator; Object localObject2; if (localObject1 == null)
/*      */     {
/* 1059 */       for (localIterator = paramBeanDescription.getFactoryMethods().iterator(); localIterator.hasNext();) { localObject2 = (AnnotatedMethod)localIterator.next();
/* 1060 */         if (paramDeserializationContext.getAnnotationIntrospector().hasCreatorAnnotation((Annotated)localObject2)) {
/* 1061 */           int i = ((AnnotatedMethod)localObject2).getParameterCount();
/* 1062 */           if (i == 1) {
/* 1063 */             Class localClass2 = ((AnnotatedMethod)localObject2).getRawReturnType();
/*      */             
/* 1065 */             if (localClass2.isAssignableFrom(localClass1)) {
/* 1066 */               localObject1 = com.shaded.fasterxml.jackson.databind.deser.std.EnumDeserializer.deserializerForCreator(localDeserializationConfig, localClass1, (AnnotatedMethod)localObject2);
/* 1067 */               break;
/*      */             }
/*      */           }
/* 1070 */           throw new IllegalArgumentException("Unsuitable method (" + localObject2 + ") decorated with @JsonCreator (for Enum type " + localClass1.getName() + ")");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1075 */       if (localObject1 == null) {
/* 1076 */         localObject1 = new com.shaded.fasterxml.jackson.databind.deser.std.EnumDeserializer(constructEnumResolver(localClass1, localDeserializationConfig, paramBeanDescription.findJsonValueMethod()));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1081 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 1082 */       for (localIterator = this._factoryConfig.deserializerModifiers().iterator(); localIterator.hasNext();) { localObject2 = (BeanDeserializerModifier)localIterator.next();
/* 1083 */         localObject1 = ((BeanDeserializerModifier)localObject2).modifyEnumDeserializer(localDeserializationConfig, paramJavaType, paramBeanDescription, (JsonDeserializer)localObject1);
/*      */       }
/*      */     }
/* 1086 */     return (JsonDeserializer<?>)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */   protected JsonDeserializer<?> _findCustomEnumDeserializer(Class<?> paramClass, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/* 1093 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/* 1094 */       JsonDeserializer localJsonDeserializer = localDeserializers.findEnumDeserializer(paramClass, paramDeserializationConfig, paramBeanDescription);
/* 1095 */       if (localJsonDeserializer != null) {
/* 1096 */         return localJsonDeserializer;
/*      */       }
/*      */     }
/* 1099 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> createTreeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/* 1114 */     Class localClass = paramJavaType.getRawClass();
/*      */     
/* 1116 */     JsonDeserializer localJsonDeserializer = _findCustomTreeNodeDeserializer(localClass, paramDeserializationConfig, paramBeanDescription);
/*      */     
/* 1118 */     if (localJsonDeserializer != null) {
/* 1119 */       return localJsonDeserializer;
/*      */     }
/* 1121 */     return com.shaded.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer.getDeserializer(localClass);
/*      */   }
/*      */   
/*      */ 
/*      */   protected JsonDeserializer<?> _findCustomTreeNodeDeserializer(Class<? extends com.shaded.fasterxml.jackson.databind.JsonNode> paramClass, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/* 1128 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/* 1129 */       JsonDeserializer localJsonDeserializer = localDeserializers.findTreeNodeDeserializer(paramClass, paramDeserializationConfig, paramBeanDescription);
/* 1130 */       if (localJsonDeserializer != null) {
/* 1131 */         return localJsonDeserializer;
/*      */       }
/*      */     }
/* 1134 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TypeDeserializer findTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*      */     throws JsonMappingException
/*      */   {
/* 1148 */     Class localClass = paramJavaType.getRawClass();
/* 1149 */     BeanDescription localBeanDescription = paramDeserializationConfig.introspectClassAnnotations(localClass);
/* 1150 */     com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass localAnnotatedClass = localBeanDescription.getClassInfo();
/* 1151 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationConfig.getAnnotationIntrospector();
/* 1152 */     TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findTypeResolver(paramDeserializationConfig, localAnnotatedClass, paramJavaType);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1157 */     Collection localCollection = null;
/* 1158 */     if (localTypeResolverBuilder == null) {
/* 1159 */       localTypeResolverBuilder = paramDeserializationConfig.getDefaultTyper(paramJavaType);
/* 1160 */       if (localTypeResolverBuilder == null) {
/* 1161 */         return null;
/*      */       }
/*      */     } else {
/* 1164 */       localCollection = paramDeserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(localAnnotatedClass, paramDeserializationConfig, localAnnotationIntrospector);
/*      */     }
/*      */     
/*      */ 
/* 1168 */     if ((localTypeResolverBuilder.getDefaultImpl() == null) && (paramJavaType.isAbstract())) {
/* 1169 */       JavaType localJavaType = mapAbstractType(paramDeserializationConfig, paramJavaType);
/* 1170 */       if ((localJavaType != null) && (localJavaType.getRawClass() != paramJavaType.getRawClass())) {
/* 1171 */         localTypeResolverBuilder = localTypeResolverBuilder.defaultImpl(localJavaType.getRawClass());
/*      */       }
/*      */     }
/* 1174 */     return localTypeResolverBuilder.buildTypeDeserializer(paramDeserializationConfig, paramJavaType, localCollection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public KeyDeserializer createKeyDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
/*      */     throws JsonMappingException
/*      */   {
/* 1188 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/* 1189 */     KeyDeserializer localKeyDeserializer = null;
/* 1190 */     Object localObject1; Object localObject2; if (this._factoryConfig.hasKeyDeserializers()) {
/* 1191 */       localObject1 = localDeserializationConfig.introspectClassAnnotations(paramJavaType.getRawClass());
/* 1192 */       for (localObject2 = this._factoryConfig.keyDeserializers().iterator(); ((Iterator)localObject2).hasNext();) { KeyDeserializers localKeyDeserializers = (KeyDeserializers)((Iterator)localObject2).next();
/* 1193 */         localKeyDeserializer = localKeyDeserializers.findKeyDeserializer(paramJavaType, localDeserializationConfig, (BeanDescription)localObject1);
/* 1194 */         if (localKeyDeserializer != null) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1200 */     if (localKeyDeserializer == null) {
/* 1201 */       if (paramJavaType.isEnumType()) {
/* 1202 */         return _createEnumKeyDeserializer(paramDeserializationContext, paramJavaType);
/*      */       }
/* 1204 */       localKeyDeserializer = StdKeyDeserializers.findStringBasedKeyDeserializer(localDeserializationConfig, paramJavaType);
/*      */     }
/*      */     
/*      */ 
/* 1208 */     if ((localKeyDeserializer != null) && 
/* 1209 */       (this._factoryConfig.hasDeserializerModifiers())) {
/* 1210 */       for (localObject1 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (BeanDeserializerModifier)((Iterator)localObject1).next();
/* 1211 */         localKeyDeserializer = ((BeanDeserializerModifier)localObject2).modifyKeyDeserializer(localDeserializationConfig, paramJavaType, localKeyDeserializer);
/*      */       }
/*      */     }
/*      */     
/* 1215 */     return localKeyDeserializer;
/*      */   }
/*      */   
/*      */ 
/*      */   private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
/*      */     throws JsonMappingException
/*      */   {
/* 1222 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/* 1223 */     BeanDescription localBeanDescription = localDeserializationConfig.introspect(paramJavaType);
/* 1224 */     JsonDeserializer localJsonDeserializer1 = findDeserializerFromAnnotation(paramDeserializationContext, localBeanDescription.getClassInfo());
/* 1225 */     if (localJsonDeserializer1 != null) {
/* 1226 */       return StdKeyDeserializers.constructDelegatingKeyDeserializer(localDeserializationConfig, paramJavaType, localJsonDeserializer1);
/*      */     }
/* 1228 */     Class localClass1 = paramJavaType.getRawClass();
/*      */     
/* 1230 */     JsonDeserializer localJsonDeserializer2 = _findCustomEnumDeserializer(localClass1, localDeserializationConfig, localBeanDescription);
/* 1231 */     if (localJsonDeserializer2 != null) {
/* 1232 */       return StdKeyDeserializers.constructDelegatingKeyDeserializer(localDeserializationConfig, paramJavaType, localJsonDeserializer1);
/*      */     }
/*      */     
/* 1235 */     EnumResolver localEnumResolver = constructEnumResolver(localClass1, localDeserializationConfig, localBeanDescription.findJsonValueMethod());
/*      */     
/* 1237 */     for (AnnotatedMethod localAnnotatedMethod : localBeanDescription.getFactoryMethods()) {
/* 1238 */       if (localDeserializationConfig.getAnnotationIntrospector().hasCreatorAnnotation(localAnnotatedMethod)) {
/* 1239 */         int i = localAnnotatedMethod.getParameterCount();
/* 1240 */         if (i == 1) {
/* 1241 */           Class localClass2 = localAnnotatedMethod.getRawReturnType();
/*      */           
/* 1243 */           if (localClass2.isAssignableFrom(localClass1))
/*      */           {
/* 1245 */             if (localAnnotatedMethod.getGenericParameterType(0) != String.class) {
/* 1246 */               throw new IllegalArgumentException("Parameter #0 type for factory method (" + localAnnotatedMethod + ") not suitable, must be java.lang.String");
/*      */             }
/* 1248 */             if (localDeserializationConfig.canOverrideAccessModifiers()) {
/* 1249 */               com.shaded.fasterxml.jackson.databind.util.ClassUtil.checkAndFixAccess(localAnnotatedMethod.getMember());
/*      */             }
/* 1251 */             return StdKeyDeserializers.constructEnumKeyDeserializer(localEnumResolver, localAnnotatedMethod);
/*      */           }
/*      */         }
/* 1254 */         throw new IllegalArgumentException("Unsuitable method (" + localAnnotatedMethod + ") decorated with @JsonCreator (for Enum type " + localClass1.getName() + ")");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1259 */     return StdKeyDeserializers.constructEnumKeyDeserializer(localEnumResolver);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final DeserializerFactoryConfig _factoryConfig;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TypeDeserializer findPropertyTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, AnnotatedMember paramAnnotatedMember)
/*      */     throws JsonMappingException
/*      */   {
/* 1285 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationConfig.getAnnotationIntrospector();
/* 1286 */     TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findPropertyTypeResolver(paramDeserializationConfig, paramAnnotatedMember, paramJavaType);
/*      */     
/* 1288 */     if (localTypeResolverBuilder == null) {
/* 1289 */       return findTypeDeserializer(paramDeserializationConfig, paramJavaType);
/*      */     }
/*      */     
/* 1292 */     Collection localCollection = paramDeserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(paramAnnotatedMember, paramDeserializationConfig, localAnnotationIntrospector, paramJavaType);
/*      */     
/* 1294 */     return localTypeResolverBuilder.buildTypeDeserializer(paramDeserializationConfig, paramJavaType, localCollection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TypeDeserializer findPropertyContentTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, AnnotatedMember paramAnnotatedMember)
/*      */     throws JsonMappingException
/*      */   {
/* 1312 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationConfig.getAnnotationIntrospector();
/* 1313 */     TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findPropertyContentTypeResolver(paramDeserializationConfig, paramAnnotatedMember, paramJavaType);
/* 1314 */     JavaType localJavaType = paramJavaType.getContentType();
/*      */     
/* 1316 */     if (localTypeResolverBuilder == null) {
/* 1317 */       return findTypeDeserializer(paramDeserializationConfig, localJavaType);
/*      */     }
/*      */     
/* 1320 */     Collection localCollection = paramDeserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(paramAnnotatedMember, paramDeserializationConfig, localAnnotationIntrospector, localJavaType);
/*      */     
/* 1322 */     return localTypeResolverBuilder.buildTypeDeserializer(paramDeserializationConfig, localJavaType, localCollection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonDeserializer<?> findDefaultDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*      */     throws JsonMappingException
/*      */   {
/* 1336 */     Class localClass = paramJavaType.getRawClass();
/* 1337 */     String str = localClass.getName();
/* 1338 */     if ((localClass.isPrimitive()) || (str.startsWith("java.")))
/*      */     {
/* 1340 */       if (localClass == CLASS_OBJECT) {
/* 1341 */         return com.shaded.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer.instance;
/*      */       }
/* 1343 */       if ((localClass == CLASS_STRING) || (localClass == CLASS_CHAR_BUFFER)) {
/* 1344 */         return com.shaded.fasterxml.jackson.databind.deser.std.StringDeserializer.instance;
/*      */       }
/* 1346 */       if (localClass == CLASS_ITERABLE)
/*      */       {
/* 1348 */         localObject = paramDeserializationContext.getTypeFactory();
/* 1349 */         JavaType localJavaType = paramJavaType.containedTypeCount() > 0 ? paramJavaType.containedType(0) : com.shaded.fasterxml.jackson.databind.type.TypeFactory.unknownType();
/* 1350 */         CollectionType localCollectionType = ((com.shaded.fasterxml.jackson.databind.type.TypeFactory)localObject).constructCollectionType(Collection.class, localJavaType);
/*      */         
/* 1352 */         return createCollectionDeserializer(paramDeserializationContext, localCollectionType, paramBeanDescription);
/*      */       }
/*      */       
/* 1355 */       Object localObject = com.shaded.fasterxml.jackson.databind.deser.std.NumberDeserializers.find(localClass, str);
/* 1356 */       if (localObject == null) {
/* 1357 */         localObject = com.shaded.fasterxml.jackson.databind.deser.std.DateDeserializers.find(localClass, str);
/* 1358 */         if (localObject == null) {
/* 1359 */           localObject = com.shaded.fasterxml.jackson.databind.deser.std.JdkDeserializers.find(localClass, str);
/*      */         }
/*      */       }
/* 1362 */       return (JsonDeserializer<?>)localObject;
/*      */     }
/* 1364 */     if (str.startsWith("com.fasterxml."))
/*      */     {
/* 1366 */       return com.shaded.fasterxml.jackson.databind.deser.std.JacksonDeserializers.find(localClass);
/*      */     }
/* 1368 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext paramDeserializationContext, Annotated paramAnnotated)
/*      */     throws JsonMappingException
/*      */   {
/* 1387 */     Object localObject = paramDeserializationContext.getAnnotationIntrospector().findDeserializer(paramAnnotated);
/* 1388 */     if (localObject == null) {
/* 1389 */       return null;
/*      */     }
/* 1391 */     return paramDeserializationContext.deserializerInstance(paramAnnotated, localObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected <T extends JavaType> T modifyTypeByAnnotation(DeserializationContext paramDeserializationContext, Annotated paramAnnotated, T paramT)
/*      */     throws JsonMappingException
/*      */   {
/* 1416 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/* 1417 */     Class localClass1 = localAnnotationIntrospector.findDeserializationType(paramAnnotated, paramT);
/* 1418 */     if (localClass1 != null) {
/*      */       try {
/* 1420 */         paramT = paramT.narrowBy(localClass1);
/*      */       } catch (IllegalArgumentException localIllegalArgumentException1) {
/* 1422 */         throw new JsonMappingException("Failed to narrow type " + paramT + " with concrete-type annotation (value " + localClass1.getName() + "), method '" + paramAnnotated.getName() + "': " + localIllegalArgumentException1.getMessage(), null, localIllegalArgumentException1);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1427 */     if (paramT.isContainerType()) {
/* 1428 */       Class localClass2 = localAnnotationIntrospector.findDeserializationKeyType(paramAnnotated, paramT.getKeyType());
/* 1429 */       if (localClass2 != null)
/*      */       {
/* 1431 */         if (!(paramT instanceof MapLikeType)) {
/* 1432 */           throw new JsonMappingException("Illegal key-type annotation: type " + paramT + " is not a Map(-like) type");
/*      */         }
/*      */         try {
/* 1435 */           paramT = ((MapLikeType)paramT).narrowKey(localClass2);
/*      */         } catch (IllegalArgumentException localIllegalArgumentException2) {
/* 1437 */           throw new JsonMappingException("Failed to narrow key type " + paramT + " with key-type annotation (" + localClass2.getName() + "): " + localIllegalArgumentException2.getMessage(), null, localIllegalArgumentException2);
/*      */         }
/*      */       }
/* 1440 */       JavaType localJavaType1 = paramT.getKeyType();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1445 */       if ((localJavaType1 != null) && (localJavaType1.getValueHandler() == null)) {
/* 1446 */         localObject1 = localAnnotationIntrospector.findKeyDeserializer(paramAnnotated);
/* 1447 */         KeyDeserializer localKeyDeserializer = paramDeserializationContext.keyDeserializerInstance(paramAnnotated, localObject1);
/* 1448 */         if (localKeyDeserializer != null) {
/* 1449 */           paramT = ((MapLikeType)paramT).withKeyValueHandler(localKeyDeserializer);
/* 1450 */           localJavaType1 = paramT.getKeyType();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1455 */       Object localObject1 = localAnnotationIntrospector.findDeserializationContentType(paramAnnotated, paramT.getContentType());
/* 1456 */       if (localObject1 != null) {
/*      */         try {
/* 1458 */           paramT = paramT.narrowContentsBy((Class)localObject1);
/*      */         } catch (IllegalArgumentException localIllegalArgumentException3) {
/* 1460 */           throw new JsonMappingException("Failed to narrow content type " + paramT + " with content-type annotation (" + ((Class)localObject1).getName() + "): " + localIllegalArgumentException3.getMessage(), null, localIllegalArgumentException3);
/*      */         }
/*      */       }
/*      */       
/* 1464 */       JavaType localJavaType2 = paramT.getContentType();
/* 1465 */       if (localJavaType2.getValueHandler() == null) {
/* 1466 */         Object localObject2 = localAnnotationIntrospector.findContentDeserializer(paramAnnotated);
/* 1467 */         JsonDeserializer localJsonDeserializer = paramDeserializationContext.deserializerInstance(paramAnnotated, localObject2);
/* 1468 */         if (localJsonDeserializer != null) {
/* 1469 */           paramT = paramT.withContentValueHandler(localJsonDeserializer);
/*      */         }
/*      */       }
/*      */     }
/* 1473 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JavaType resolveType(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, JavaType paramJavaType, AnnotatedMember paramAnnotatedMember)
/*      */     throws JsonMappingException
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1488 */     if (paramJavaType.isContainerType()) {
/* 1489 */       localObject1 = paramDeserializationContext.getAnnotationIntrospector();
/* 1490 */       JavaType localJavaType = paramJavaType.getKeyType();
/* 1491 */       if (localJavaType != null) {
/* 1492 */         localObject2 = ((AnnotationIntrospector)localObject1).findKeyDeserializer(paramAnnotatedMember);
/* 1493 */         localObject3 = paramDeserializationContext.keyDeserializerInstance(paramAnnotatedMember, localObject2);
/* 1494 */         if (localObject3 != null) {
/* 1495 */           paramJavaType = ((MapLikeType)paramJavaType).withKeyValueHandler(localObject3);
/* 1496 */           localJavaType = paramJavaType.getKeyType();
/*      */         }
/*      */       }
/*      */       
/* 1500 */       Object localObject2 = ((AnnotationIntrospector)localObject1).findContentDeserializer(paramAnnotatedMember);
/* 1501 */       Object localObject3 = paramDeserializationContext.deserializerInstance(paramAnnotatedMember, localObject2);
/* 1502 */       if (localObject3 != null) {
/* 1503 */         paramJavaType = paramJavaType.withContentValueHandler(localObject3);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1510 */       if ((paramAnnotatedMember instanceof AnnotatedMember)) {
/* 1511 */         TypeDeserializer localTypeDeserializer = findPropertyContentTypeDeserializer(paramDeserializationContext.getConfig(), paramJavaType, paramAnnotatedMember);
/*      */         
/* 1513 */         if (localTypeDeserializer != null) {
/* 1514 */           paramJavaType = paramJavaType.withContentTypeHandler(localTypeDeserializer);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1520 */     if ((paramAnnotatedMember instanceof AnnotatedMember)) {
/* 1521 */       localObject1 = findPropertyTypeDeserializer(paramDeserializationContext.getConfig(), paramJavaType, paramAnnotatedMember);
/*      */     }
/*      */     else
/*      */     {
/* 1525 */       localObject1 = findTypeDeserializer(paramDeserializationContext.getConfig(), paramJavaType);
/*      */     }
/* 1527 */     if (localObject1 != null) {
/* 1528 */       paramJavaType = paramJavaType.withTypeHandler(localObject1);
/*      */     }
/* 1530 */     return paramJavaType;
/*      */   }
/*      */   
/*      */ 
/*      */   protected EnumResolver<?> constructEnumResolver(Class<?> paramClass, DeserializationConfig paramDeserializationConfig, AnnotatedMethod paramAnnotatedMethod)
/*      */   {
/* 1536 */     if (paramAnnotatedMethod != null) {
/* 1537 */       java.lang.reflect.Method localMethod = paramAnnotatedMethod.getAnnotated();
/* 1538 */       if (paramDeserializationConfig.canOverrideAccessModifiers()) {
/* 1539 */         com.shaded.fasterxml.jackson.databind.util.ClassUtil.checkAndFixAccess(localMethod);
/*      */       }
/* 1541 */       return EnumResolver.constructUnsafeUsingMethod(paramClass, localMethod);
/*      */     }
/*      */     
/* 1544 */     if (paramDeserializationConfig.isEnabled(com.shaded.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING)) {
/* 1545 */       return EnumResolver.constructUnsafeUsingToString(paramClass);
/*      */     }
/* 1547 */     return EnumResolver.constructUnsafe(paramClass, paramDeserializationConfig.getAnnotationIntrospector());
/*      */   }
/*      */   
/*      */   protected AnnotatedMethod _findJsonValueFor(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*      */   {
/* 1552 */     if (paramJavaType == null) {
/* 1553 */       return null;
/*      */     }
/* 1555 */     BeanDescription localBeanDescription = paramDeserializationConfig.introspect(paramJavaType);
/* 1556 */     return localBeanDescription.findJsonValueMethod();
/*      */   }
/*      */   
/*      */   protected abstract DeserializerFactory withConfig(DeserializerFactoryConfig paramDeserializerFactoryConfig);
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/BasicDeserializerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */