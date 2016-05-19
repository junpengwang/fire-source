/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AbstractTypeResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty.Std;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.MapperFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.SetterlessProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.std.JdkDeserializers.AtomicReferenceDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.SimpleBeanPropertyDefinition;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ public class BeanDeserializerFactory extends BasicDeserializerFactory implements java.io.Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  44 */   private static final Class<?>[] INIT_CAUSE_PARAMS = { Throwable.class };
/*     */   
/*  46 */   private static final Class<?>[] NO_VIEWS = new Class[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  58 */   public static final BeanDeserializerFactory instance = new BeanDeserializerFactory(new DeserializerFactoryConfig());
/*     */   
/*     */   public BeanDeserializerFactory(DeserializerFactoryConfig paramDeserializerFactoryConfig)
/*     */   {
/*  62 */     super(paramDeserializerFactoryConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeserializerFactory withConfig(DeserializerFactoryConfig paramDeserializerFactoryConfig)
/*     */   {
/*  73 */     if (this._factoryConfig == paramDeserializerFactoryConfig) {
/*  74 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  82 */     if (getClass() != BeanDeserializerFactory.class) {
/*  83 */       throw new IllegalStateException("Subtype of BeanDeserializerFactory (" + getClass().getName() + ") has not properly overridden method 'withAdditionalDeserializers': can not instantiate subtype with " + "additional deserializer definitions");
/*     */     }
/*     */     
/*     */ 
/*  87 */     return new BeanDeserializerFactory(paramDeserializerFactoryConfig);
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
/*     */   protected JsonDeserializer<Object> _findCustomBeanDeserializer(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 103 */     for (Deserializers localDeserializers : this._factoryConfig.deserializers()) {
/* 104 */       JsonDeserializer localJsonDeserializer = localDeserializers.findBeanDeserializer(paramJavaType, paramDeserializationConfig, paramBeanDescription);
/* 105 */       if (localJsonDeserializer != null) {
/* 106 */         return localJsonDeserializer;
/*     */       }
/*     */     }
/* 109 */     return null;
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
/*     */   public JsonDeserializer<Object> createBeanDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 128 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*     */     
/* 130 */     JsonDeserializer localJsonDeserializer = _findCustomBeanDeserializer(paramJavaType, localDeserializationConfig, paramBeanDescription);
/* 131 */     if (localJsonDeserializer != null) {
/* 132 */       return localJsonDeserializer;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 138 */     if (paramJavaType.isThrowable()) {
/* 139 */       return buildThrowableDeserializer(paramDeserializationContext, paramJavaType, paramBeanDescription);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 144 */     if (paramJavaType.isAbstract())
/*     */     {
/* 146 */       localObject = materializeAbstractType(paramDeserializationContext, paramJavaType, paramBeanDescription);
/* 147 */       if (localObject != null)
/*     */       {
/*     */ 
/*     */ 
/* 151 */         paramBeanDescription = localDeserializationConfig.introspect((JavaType)localObject);
/* 152 */         return buildBeanDeserializer(paramDeserializationContext, (JavaType)localObject, paramBeanDescription);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 158 */     Object localObject = findStdDeserializer(paramDeserializationContext, paramJavaType, paramBeanDescription);
/* 159 */     if (localObject != null) {
/* 160 */       return (JsonDeserializer<Object>)localObject;
/*     */     }
/*     */     
/*     */ 
/* 164 */     if (!isPotentialBeanType(paramJavaType.getRawClass())) {
/* 165 */       return null;
/*     */     }
/*     */     
/* 168 */     return buildBeanDeserializer(paramDeserializationContext, paramJavaType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<Object> createBuilderBasedDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription, Class<?> paramClass)
/*     */     throws JsonMappingException
/*     */   {
/* 178 */     JavaType localJavaType = paramDeserializationContext.constructType(paramClass);
/* 179 */     BeanDescription localBeanDescription = paramDeserializationContext.getConfig().introspectForBuilder(localJavaType);
/* 180 */     return buildBuilderBasedDeserializer(paramDeserializationContext, paramJavaType, localBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<?> findStdDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 193 */     JsonDeserializer localJsonDeserializer = findDefaultDeserializer(paramDeserializationContext, paramJavaType, paramBeanDescription);
/* 194 */     if (localJsonDeserializer != null) {
/* 195 */       return localJsonDeserializer;
/*     */     }
/*     */     
/* 198 */     Class localClass = paramJavaType.getRawClass();
/*     */     
/* 200 */     if (AtomicReference.class.isAssignableFrom(localClass))
/*     */     {
/* 202 */       TypeFactory localTypeFactory = paramDeserializationContext.getTypeFactory();
/* 203 */       JavaType[] arrayOfJavaType = localTypeFactory.findTypeParameters(paramJavaType, AtomicReference.class);
/*     */       JavaType localJavaType;
/* 205 */       if ((arrayOfJavaType == null) || (arrayOfJavaType.length < 1)) {
/* 206 */         localJavaType = TypeFactory.unknownType();
/*     */       } else {
/* 208 */         localJavaType = arrayOfJavaType[0];
/*     */       }
/* 210 */       return new JdkDeserializers.AtomicReferenceDeserializer(localJavaType);
/*     */     }
/* 212 */     return findOptionalStdDeserializer(paramDeserializationContext, paramJavaType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<?> findOptionalStdDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 224 */     return OptionalHandlerFactory.instance.findDeserializer(paramJavaType, paramDeserializationContext.getConfig(), paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */   protected JavaType materializeAbstractType(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 231 */     JavaType localJavaType1 = paramBeanDescription.getType();
/*     */     
/*     */ 
/* 234 */     for (AbstractTypeResolver localAbstractTypeResolver : this._factoryConfig.abstractTypeResolvers()) {
/* 235 */       JavaType localJavaType2 = localAbstractTypeResolver.resolveAbstractType(paramDeserializationContext.getConfig(), localJavaType1);
/* 236 */       if (localJavaType2 != null) {
/* 237 */         return localJavaType2;
/*     */       }
/*     */     }
/* 240 */     return null;
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
/*     */   public JsonDeserializer<Object> buildBeanDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 263 */     ValueInstantiator localValueInstantiator = findValueInstantiator(paramDeserializationContext, paramBeanDescription);
/* 264 */     BeanDeserializerBuilder localBeanDeserializerBuilder = constructBeanDeserializerBuilder(paramDeserializationContext, paramBeanDescription);
/* 265 */     localBeanDeserializerBuilder.setValueInstantiator(localValueInstantiator);
/*     */     
/* 267 */     addBeanProps(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/* 268 */     addObjectIdReader(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/*     */     
/*     */ 
/* 271 */     addReferenceProperties(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/* 272 */     addInjectables(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/*     */     
/* 274 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*     */     Object localObject1;
/* 276 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 277 */       for (localObject1 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (BeanDeserializerModifier)((Iterator)localObject1).next();
/* 278 */         localBeanDeserializerBuilder = ((BeanDeserializerModifier)localObject2).updateBuilder(localDeserializationConfig, paramBeanDescription, localBeanDeserializerBuilder);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/* 286 */     if ((paramJavaType.isAbstract()) && (!localValueInstantiator.canInstantiate())) {
/* 287 */       localObject1 = localBeanDeserializerBuilder.buildAbstract();
/*     */     } else {
/* 289 */       localObject1 = localBeanDeserializerBuilder.build();
/*     */     }
/*     */     
/*     */ 
/* 293 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 294 */       for (localObject2 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { BeanDeserializerModifier localBeanDeserializerModifier = (BeanDeserializerModifier)((Iterator)localObject2).next();
/* 295 */         localObject1 = localBeanDeserializerModifier.modifyDeserializer(localDeserializationConfig, paramBeanDescription, (JsonDeserializer)localObject1);
/*     */       }
/*     */     }
/* 298 */     return (JsonDeserializer<Object>)localObject1;
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
/*     */   protected JsonDeserializer<Object> buildBuilderBasedDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 314 */     ValueInstantiator localValueInstantiator = findValueInstantiator(paramDeserializationContext, paramBeanDescription);
/* 315 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/* 316 */     BeanDeserializerBuilder localBeanDeserializerBuilder = constructBeanDeserializerBuilder(paramDeserializationContext, paramBeanDescription);
/* 317 */     localBeanDeserializerBuilder.setValueInstantiator(localValueInstantiator);
/*     */     
/* 319 */     addBeanProps(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/* 320 */     addObjectIdReader(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/*     */     
/*     */ 
/* 323 */     addReferenceProperties(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/* 324 */     addInjectables(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/*     */     
/* 326 */     JsonPOJOBuilder.Value localValue = paramBeanDescription.findPOJOBuilderConfig();
/* 327 */     String str = localValue == null ? "build" : localValue.buildMethodName;
/*     */     
/*     */ 
/*     */ 
/* 331 */     AnnotatedMethod localAnnotatedMethod = paramBeanDescription.findMethod(str, null);
/* 332 */     if ((localAnnotatedMethod != null) && 
/* 333 */       (localDeserializationConfig.canOverrideAccessModifiers())) {
/* 334 */       ClassUtil.checkAndFixAccess(localAnnotatedMethod.getMember());
/*     */     }
/*     */     
/* 337 */     localBeanDeserializerBuilder.setPOJOBuilder(localAnnotatedMethod, localValue);
/*     */     
/* 339 */     if (this._factoryConfig.hasDeserializerModifiers())
/* 340 */       for (localObject1 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (BeanDeserializerModifier)((Iterator)localObject1).next();
/* 341 */         localBeanDeserializerBuilder = ((BeanDeserializerModifier)localObject2).updateBuilder(localDeserializationConfig, paramBeanDescription, localBeanDeserializerBuilder);
/*     */       }
/*     */     Object localObject2;
/* 344 */     Object localObject1 = localBeanDeserializerBuilder.buildBuilderBased(paramJavaType, str);
/*     */     
/*     */ 
/*     */ 
/* 348 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 349 */       for (localObject2 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { BeanDeserializerModifier localBeanDeserializerModifier = (BeanDeserializerModifier)((Iterator)localObject2).next();
/* 350 */         localObject1 = localBeanDeserializerModifier.modifyDeserializer(localDeserializationConfig, paramBeanDescription, (JsonDeserializer)localObject1);
/*     */       }
/*     */     }
/* 353 */     return (JsonDeserializer<Object>)localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void addObjectIdReader(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, BeanDeserializerBuilder paramBeanDeserializerBuilder)
/*     */     throws JsonMappingException
/*     */   {
/* 360 */     ObjectIdInfo localObjectIdInfo = paramBeanDescription.getObjectIdInfo();
/* 361 */     if (localObjectIdInfo == null) {
/* 362 */       return;
/*     */     }
/* 364 */     Class localClass = localObjectIdInfo.getGeneratorType();
/*     */     
/*     */     SettableBeanProperty localSettableBeanProperty;
/*     */     
/*     */     JavaType localJavaType;
/*     */     Object localObject2;
/* 370 */     if (localClass == ObjectIdGenerators.PropertyGenerator.class) {
/* 371 */       localObject1 = localObjectIdInfo.getPropertyName();
/* 372 */       localSettableBeanProperty = paramBeanDeserializerBuilder.findProperty((String)localObject1);
/* 373 */       if (localSettableBeanProperty == null) {
/* 374 */         throw new IllegalArgumentException("Invalid Object Id definition for " + paramBeanDescription.getBeanClass().getName() + ": can not find property with name '" + (String)localObject1 + "'");
/*     */       }
/*     */       
/* 377 */       localJavaType = localSettableBeanProperty.getType();
/* 378 */       localObject2 = new com.shaded.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator(localObjectIdInfo.getScope());
/*     */     } else {
/* 380 */       localObject1 = paramDeserializationContext.constructType(localClass);
/* 381 */       localJavaType = paramDeserializationContext.getTypeFactory().findTypeParameters(localObject1, ObjectIdGenerator.class)[0];
/* 382 */       localSettableBeanProperty = null;
/* 383 */       localObject2 = paramDeserializationContext.objectIdGeneratorInstance(paramBeanDescription.getClassInfo(), localObjectIdInfo);
/*     */     }
/*     */     
/* 386 */     Object localObject1 = paramDeserializationContext.findRootValueDeserializer(localJavaType);
/* 387 */     paramBeanDeserializerBuilder.setObjectIdReader(com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdReader.construct(localJavaType, localObjectIdInfo.getPropertyName(), (ObjectIdGenerator)localObject2, (JsonDeserializer)localObject1, localSettableBeanProperty));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<Object> buildThrowableDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 396 */     DeserializationConfig localDeserializationConfig = paramDeserializationContext.getConfig();
/*     */     
/* 398 */     BeanDeserializerBuilder localBeanDeserializerBuilder = constructBeanDeserializerBuilder(paramDeserializationContext, paramBeanDescription);
/* 399 */     localBeanDeserializerBuilder.setValueInstantiator(findValueInstantiator(paramDeserializationContext, paramBeanDescription));
/*     */     
/* 401 */     addBeanProps(paramDeserializationContext, paramBeanDescription, localBeanDeserializerBuilder);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 408 */     AnnotatedMethod localAnnotatedMethod = paramBeanDescription.findMethod("initCause", INIT_CAUSE_PARAMS);
/* 409 */     Object localObject2; if (localAnnotatedMethod != null) {
/* 410 */       localObject1 = SimpleBeanPropertyDefinition.construct(paramDeserializationContext.getConfig(), localAnnotatedMethod, "cause");
/* 411 */       localObject2 = constructSettableProperty(paramDeserializationContext, paramBeanDescription, (BeanPropertyDefinition)localObject1, localAnnotatedMethod.getGenericParameterType(0));
/*     */       
/* 413 */       if (localObject2 != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 418 */         localBeanDeserializerBuilder.addOrReplaceProperty((SettableBeanProperty)localObject2, true);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 423 */     localBeanDeserializerBuilder.addIgnorable("localizedMessage");
/*     */     
/* 425 */     localBeanDeserializerBuilder.addIgnorable("suppressed");
/*     */     
/*     */ 
/*     */ 
/* 429 */     localBeanDeserializerBuilder.addIgnorable("message");
/*     */     
/*     */ 
/* 432 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 433 */       for (localObject1 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (BeanDeserializerModifier)((Iterator)localObject1).next();
/* 434 */         localBeanDeserializerBuilder = ((BeanDeserializerModifier)localObject2).updateBuilder(localDeserializationConfig, paramBeanDescription, localBeanDeserializerBuilder);
/*     */       }
/*     */     }
/* 437 */     Object localObject1 = localBeanDeserializerBuilder.build();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 442 */     if ((localObject1 instanceof BeanDeserializer)) {
/* 443 */       localObject1 = new com.shaded.fasterxml.jackson.databind.deser.std.ThrowableDeserializer((BeanDeserializer)localObject1);
/*     */     }
/*     */     
/*     */ 
/* 447 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 448 */       for (localObject2 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { BeanDeserializerModifier localBeanDeserializerModifier = (BeanDeserializerModifier)((Iterator)localObject2).next();
/* 449 */         localObject1 = localBeanDeserializerModifier.modifyDeserializer(localDeserializationConfig, paramBeanDescription, (JsonDeserializer)localObject1);
/*     */       }
/*     */     }
/* 452 */     return (JsonDeserializer<Object>)localObject1;
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
/*     */   protected BeanDeserializerBuilder constructBeanDeserializerBuilder(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription)
/*     */   {
/* 469 */     return new BeanDeserializerBuilder(paramBeanDescription, paramDeserializationContext.getConfig());
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
/*     */   protected void addBeanProps(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, BeanDeserializerBuilder paramBeanDeserializerBuilder)
/*     */     throws JsonMappingException
/*     */   {
/* 483 */     SettableBeanProperty[] arrayOfSettableBeanProperty = paramBeanDeserializerBuilder.getValueInstantiator().getFromObjectArguments(paramDeserializationContext.getConfig());
/*     */     
/*     */ 
/*     */ 
/* 487 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/* 488 */     boolean bool = false;
/*     */     
/* 490 */     Object localObject1 = localAnnotationIntrospector.findIgnoreUnknownProperties(paramBeanDescription.getClassInfo());
/* 491 */     if (localObject1 != null) {
/* 492 */       bool = ((Boolean)localObject1).booleanValue();
/* 493 */       paramBeanDeserializerBuilder.setIgnoreUnknownProperties(bool);
/*     */     }
/*     */     
/*     */ 
/* 497 */     localObject1 = com.shaded.fasterxml.jackson.databind.util.ArrayBuilders.arrayToSet(localAnnotationIntrospector.findPropertiesToIgnore(paramBeanDescription.getClassInfo()));
/* 498 */     for (Object localObject2 = ((Set)localObject1).iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (String)((Iterator)localObject2).next();
/* 499 */       paramBeanDeserializerBuilder.addIgnorable((String)localObject3);
/*     */     }
/*     */     Object localObject3;
/* 502 */     localObject2 = paramBeanDescription.findAnySetter();
/* 503 */     if (localObject2 != null) {
/* 504 */       paramBeanDeserializerBuilder.setAnySetter(constructAnySetter(paramDeserializationContext, paramBeanDescription, (AnnotatedMethod)localObject2));
/*     */     }
/*     */     
/*     */ 
/* 508 */     if (localObject2 == null) {
/* 509 */       localObject3 = paramBeanDescription.getIgnoredPropertyNames();
/* 510 */       if (localObject3 != null) {
/* 511 */         for (localObject4 = ((Collection)localObject3).iterator(); ((Iterator)localObject4).hasNext();) { localObject5 = (String)((Iterator)localObject4).next();
/*     */           
/*     */ 
/* 514 */           paramBeanDeserializerBuilder.addIgnorable((String)localObject5);
/*     */         }
/*     */       }
/*     */     }
/* 518 */     int i = (paramDeserializationContext.isEnabled(MapperFeature.USE_GETTERS_AS_SETTERS)) && (paramDeserializationContext.isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) ? 1 : 0;
/*     */     
/*     */ 
/*     */ 
/* 522 */     Object localObject4 = filterBeanProps(paramDeserializationContext, paramBeanDescription, paramBeanDeserializerBuilder, paramBeanDescription.findProperties(), (Set)localObject1);
/*     */     
/*     */ 
/*     */ 
/* 526 */     if (this._factoryConfig.hasDeserializerModifiers()) {
/* 527 */       for (localObject5 = this._factoryConfig.deserializerModifiers().iterator(); ((Iterator)localObject5).hasNext();) { localObject6 = (BeanDeserializerModifier)((Iterator)localObject5).next();
/* 528 */         localObject4 = ((BeanDeserializerModifier)localObject6).updateProperties(paramDeserializationContext.getConfig(), paramBeanDescription, (List)localObject4);
/*     */       }
/*     */     }
/*     */     
/*     */     Object localObject6;
/* 533 */     for (Object localObject5 = ((List)localObject4).iterator(); ((Iterator)localObject5).hasNext();) { localObject6 = (BeanPropertyDefinition)((Iterator)localObject5).next();
/* 534 */       Object localObject7 = null;
/* 535 */       Object localObject8; if (((BeanPropertyDefinition)localObject6).hasConstructorParameter())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 541 */         localObject8 = ((BeanPropertyDefinition)localObject6).getName();
/* 542 */         if (arrayOfSettableBeanProperty != null) {
/* 543 */           for (Object localObject10 : arrayOfSettableBeanProperty) {
/* 544 */             if (((String)localObject8).equals(((SettableBeanProperty)localObject10).getName())) {
/* 545 */               localObject7 = localObject10;
/* 546 */               break;
/*     */             }
/*     */           }
/*     */         }
/* 550 */         if (localObject7 == null) {
/* 551 */           throw paramDeserializationContext.mappingException("Could not find creator property with name '" + (String)localObject8 + "' (in class " + paramBeanDescription.getBeanClass().getName() + ")");
/*     */         }
/*     */         
/* 554 */         paramBeanDeserializerBuilder.addCreatorProperty((SettableBeanProperty)localObject7);
/*     */       }
/*     */       else {
/* 557 */         if (((BeanPropertyDefinition)localObject6).hasSetter()) {
/* 558 */           localObject8 = ((BeanPropertyDefinition)localObject6).getSetter().getGenericParameterType(0);
/* 559 */           localObject7 = constructSettableProperty(paramDeserializationContext, paramBeanDescription, (BeanPropertyDefinition)localObject6, (Type)localObject8);
/* 560 */         } else if (((BeanPropertyDefinition)localObject6).hasField()) {
/* 561 */           localObject8 = ((BeanPropertyDefinition)localObject6).getField().getGenericType();
/* 562 */           localObject7 = constructSettableProperty(paramDeserializationContext, paramBeanDescription, (BeanPropertyDefinition)localObject6, (Type)localObject8);
/* 563 */         } else if ((i != 0) && (((BeanPropertyDefinition)localObject6).hasGetter()))
/*     */         {
/*     */ 
/*     */ 
/* 567 */           localObject8 = ((BeanPropertyDefinition)localObject6).getGetter();
/*     */           
/* 569 */           ??? = ((AnnotatedMethod)localObject8).getRawType();
/* 570 */           if ((Collection.class.isAssignableFrom((Class)???)) || (Map.class.isAssignableFrom((Class)???)))
/*     */           {
/* 572 */             localObject7 = constructSetterlessProperty(paramDeserializationContext, paramBeanDescription, (BeanPropertyDefinition)localObject6);
/*     */           }
/*     */         }
/* 575 */         if (localObject7 != null) {
/* 576 */           localObject8 = ((BeanPropertyDefinition)localObject6).findViews();
/* 577 */           if (localObject8 == null)
/*     */           {
/* 579 */             if (!paramDeserializationContext.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)) {
/* 580 */               localObject8 = NO_VIEWS;
/*     */             }
/*     */           }
/*     */           
/* 584 */           ((SettableBeanProperty)localObject7).setViews((Class[])localObject8);
/* 585 */           paramBeanDeserializerBuilder.addProperty((SettableBeanProperty)localObject7);
/*     */         }
/*     */       }
/*     */     }
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
/*     */   protected List<BeanPropertyDefinition> filterBeanProps(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, BeanDeserializerBuilder paramBeanDeserializerBuilder, List<BeanPropertyDefinition> paramList, Set<String> paramSet)
/*     */     throws JsonMappingException
/*     */   {
/* 602 */     ArrayList localArrayList = new ArrayList(Math.max(4, paramList.size()));
/*     */     
/* 604 */     HashMap localHashMap = new HashMap();
/*     */     
/* 606 */     for (BeanPropertyDefinition localBeanPropertyDefinition : paramList) {
/* 607 */       String str = localBeanPropertyDefinition.getName();
/* 608 */       if (!paramSet.contains(str))
/*     */       {
/*     */ 
/* 611 */         if (!localBeanPropertyDefinition.hasConstructorParameter()) {
/* 612 */           Class localClass = null;
/* 613 */           if (localBeanPropertyDefinition.hasSetter()) {
/* 614 */             localClass = localBeanPropertyDefinition.getSetter().getRawParameterType(0);
/* 615 */           } else if (localBeanPropertyDefinition.hasField()) {
/* 616 */             localClass = localBeanPropertyDefinition.getField().getRawType();
/*     */           }
/*     */           
/*     */ 
/* 620 */           if ((localClass != null) && (isIgnorableType(paramDeserializationContext.getConfig(), paramBeanDescription, localClass, localHashMap)))
/*     */           {
/*     */ 
/* 623 */             paramBeanDeserializerBuilder.addIgnorable(str);
/* 624 */             continue;
/*     */           }
/*     */         }
/* 627 */         localArrayList.add(localBeanPropertyDefinition);
/*     */       } }
/* 629 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addReferenceProperties(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, BeanDeserializerBuilder paramBeanDeserializerBuilder)
/*     */     throws JsonMappingException
/*     */   {
/* 641 */     Map localMap = paramBeanDescription.findBackReferenceProperties();
/* 642 */     if (localMap != null) {
/* 643 */       for (Map.Entry localEntry : localMap.entrySet()) {
/* 644 */         String str = (String)localEntry.getKey();
/* 645 */         AnnotatedMember localAnnotatedMember = (AnnotatedMember)localEntry.getValue();
/*     */         Object localObject;
/* 647 */         if ((localAnnotatedMember instanceof AnnotatedMethod)) {
/* 648 */           localObject = ((AnnotatedMethod)localAnnotatedMember).getGenericParameterType(0);
/*     */         } else {
/* 650 */           localObject = localAnnotatedMember.getRawType();
/*     */         }
/* 652 */         SimpleBeanPropertyDefinition localSimpleBeanPropertyDefinition = SimpleBeanPropertyDefinition.construct(paramDeserializationContext.getConfig(), localAnnotatedMember);
/*     */         
/* 654 */         paramBeanDeserializerBuilder.addBackReferenceProperty(str, constructSettableProperty(paramDeserializationContext, paramBeanDescription, localSimpleBeanPropertyDefinition, (Type)localObject));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addInjectables(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, BeanDeserializerBuilder paramBeanDeserializerBuilder)
/*     */     throws JsonMappingException
/*     */   {
/* 668 */     Map localMap = paramBeanDescription.findInjectables();
/* 669 */     boolean bool; if (localMap != null) {
/* 670 */       bool = paramDeserializationContext.canOverrideAccessModifiers();
/* 671 */       for (Map.Entry localEntry : localMap.entrySet()) {
/* 672 */         AnnotatedMember localAnnotatedMember = (AnnotatedMember)localEntry.getValue();
/* 673 */         if (bool) {
/* 674 */           localAnnotatedMember.fixAccess();
/*     */         }
/* 676 */         paramBeanDeserializerBuilder.addInjectable(localAnnotatedMember.getName(), paramBeanDescription.resolveType(localAnnotatedMember.getGenericType()), paramBeanDescription.getClassAnnotations(), localAnnotatedMember, localEntry.getKey());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SettableAnyProperty constructAnySetter(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, AnnotatedMethod paramAnnotatedMethod)
/*     */     throws JsonMappingException
/*     */   {
/* 691 */     if (paramDeserializationContext.canOverrideAccessModifiers()) {
/* 692 */       paramAnnotatedMethod.fixAccess();
/*     */     }
/*     */     
/* 695 */     JavaType localJavaType = paramBeanDescription.bindingsForBeanType().resolveType(paramAnnotatedMethod.getGenericParameterType(1));
/* 696 */     BeanProperty.Std localStd = new BeanProperty.Std(paramAnnotatedMethod.getName(), localJavaType, null, paramBeanDescription.getClassAnnotations(), paramAnnotatedMethod, false);
/*     */     
/* 698 */     localJavaType = resolveType(paramDeserializationContext, paramBeanDescription, localJavaType, paramAnnotatedMethod);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 705 */     JsonDeserializer localJsonDeserializer = findDeserializerFromAnnotation(paramDeserializationContext, paramAnnotatedMethod);
/* 706 */     if (localJsonDeserializer != null) {
/* 707 */       return new SettableAnyProperty(localStd, paramAnnotatedMethod, localJavaType, localJsonDeserializer);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 712 */     localJavaType = modifyTypeByAnnotation(paramDeserializationContext, paramAnnotatedMethod, localJavaType);
/* 713 */     return new SettableAnyProperty(localStd, paramAnnotatedMethod, localJavaType, null);
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
/*     */   protected SettableBeanProperty constructSettableProperty(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, BeanPropertyDefinition paramBeanPropertyDefinition, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/* 729 */     AnnotatedMember localAnnotatedMember = paramBeanPropertyDefinition.getMutator();
/* 730 */     if (paramDeserializationContext.canOverrideAccessModifiers()) {
/* 731 */       localAnnotatedMember.fixAccess();
/*     */     }
/*     */     
/* 734 */     JavaType localJavaType1 = paramBeanDescription.resolveType(paramType);
/*     */     
/* 736 */     BeanProperty.Std localStd = new BeanProperty.Std(paramBeanPropertyDefinition.getName(), localJavaType1, paramBeanPropertyDefinition.getWrapperName(), paramBeanDescription.getClassAnnotations(), localAnnotatedMember, paramBeanPropertyDefinition.isRequired());
/*     */     
/* 738 */     JavaType localJavaType2 = resolveType(paramDeserializationContext, paramBeanDescription, localJavaType1, localAnnotatedMember);
/*     */     
/* 740 */     if (localJavaType2 != localJavaType1) {
/* 741 */       localStd = localStd.withType(localJavaType2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 747 */     JsonDeserializer localJsonDeserializer = findDeserializerFromAnnotation(paramDeserializationContext, localAnnotatedMember);
/* 748 */     localJavaType2 = modifyTypeByAnnotation(paramDeserializationContext, localAnnotatedMember, localJavaType2);
/* 749 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType2.getTypeHandler();
/*     */     Object localObject;
/* 751 */     if ((localAnnotatedMember instanceof AnnotatedMethod)) {
/* 752 */       localObject = new com.shaded.fasterxml.jackson.databind.deser.impl.MethodProperty(paramBeanPropertyDefinition, localJavaType2, localTypeDeserializer, paramBeanDescription.getClassAnnotations(), (AnnotatedMethod)localAnnotatedMember);
/*     */     }
/*     */     else {
/* 755 */       localObject = new com.shaded.fasterxml.jackson.databind.deser.impl.FieldProperty(paramBeanPropertyDefinition, localJavaType2, localTypeDeserializer, paramBeanDescription.getClassAnnotations(), (AnnotatedField)localAnnotatedMember);
/*     */     }
/*     */     
/* 758 */     if (localJsonDeserializer != null) {
/* 759 */       localObject = ((SettableBeanProperty)localObject).withValueDeserializer(localJsonDeserializer);
/*     */     }
/*     */     
/* 762 */     AnnotationIntrospector.ReferenceProperty localReferenceProperty = paramBeanPropertyDefinition.findReferenceType();
/* 763 */     if ((localReferenceProperty != null) && (localReferenceProperty.isManagedReference())) {
/* 764 */       ((SettableBeanProperty)localObject).setManagedReferenceName(localReferenceProperty.getName());
/*     */     }
/* 766 */     return (SettableBeanProperty)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SettableBeanProperty constructSetterlessProperty(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription, BeanPropertyDefinition paramBeanPropertyDefinition)
/*     */     throws JsonMappingException
/*     */   {
/* 777 */     AnnotatedMethod localAnnotatedMethod = paramBeanPropertyDefinition.getGetter();
/*     */     
/* 779 */     if (paramDeserializationContext.canOverrideAccessModifiers()) {
/* 780 */       localAnnotatedMethod.fixAccess();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 786 */     JavaType localJavaType = localAnnotatedMethod.getType(paramBeanDescription.bindingsForBeanType());
/*     */     
/*     */ 
/*     */ 
/* 790 */     JsonDeserializer localJsonDeserializer = findDeserializerFromAnnotation(paramDeserializationContext, localAnnotatedMethod);
/* 791 */     localJavaType = modifyTypeByAnnotation(paramDeserializationContext, localAnnotatedMethod, localJavaType);
/* 792 */     TypeDeserializer localTypeDeserializer = (TypeDeserializer)localJavaType.getTypeHandler();
/* 793 */     Object localObject = new SetterlessProperty(paramBeanPropertyDefinition, localJavaType, localTypeDeserializer, paramBeanDescription.getClassAnnotations(), localAnnotatedMethod);
/*     */     
/* 795 */     if (localJsonDeserializer != null) {
/* 796 */       localObject = ((SettableBeanProperty)localObject).withValueDeserializer(localJsonDeserializer);
/*     */     }
/* 798 */     return (SettableBeanProperty)localObject;
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
/*     */   protected boolean isPotentialBeanType(Class<?> paramClass)
/*     */   {
/* 817 */     String str = ClassUtil.canBeABeanType(paramClass);
/* 818 */     if (str != null) {
/* 819 */       throw new IllegalArgumentException("Can not deserialize Class " + paramClass.getName() + " (of type " + str + ") as a Bean");
/*     */     }
/* 821 */     if (ClassUtil.isProxyType(paramClass)) {
/* 822 */       throw new IllegalArgumentException("Can not deserialize Proxy class " + paramClass.getName() + " as a Bean");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 827 */     str = ClassUtil.isLocalType(paramClass, true);
/* 828 */     if (str != null) {
/* 829 */       throw new IllegalArgumentException("Can not deserialize Class " + paramClass.getName() + " (of type " + str + ") as a Bean");
/*     */     }
/* 831 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isIgnorableType(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, Class<?> paramClass, Map<Class<?>, Boolean> paramMap)
/*     */   {
/* 841 */     Boolean localBoolean = (Boolean)paramMap.get(paramClass);
/* 842 */     if (localBoolean == null) {
/* 843 */       BeanDescription localBeanDescription = paramDeserializationConfig.introspectClassAnnotations(paramClass);
/* 844 */       localBoolean = paramDeserializationConfig.getAnnotationIntrospector().isIgnorableType(localBeanDescription.getClassInfo());
/*     */       
/* 846 */       if (localBoolean == null) {
/* 847 */         localBoolean = Boolean.FALSE;
/*     */       }
/*     */     }
/* 850 */     return localBoolean.booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/BeanDeserializerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */