/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty.Std;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.MapperFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.FilteredBeanPropertyWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.MapSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanSerializerFactory
/*     */   extends BasicSerializerFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  62 */   public static final BeanSerializerFactory instance = new BeanSerializerFactory(null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanSerializerFactory(SerializerFactoryConfig paramSerializerFactoryConfig)
/*     */   {
/*  75 */     super(paramSerializerFactoryConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SerializerFactory withConfig(SerializerFactoryConfig paramSerializerFactoryConfig)
/*     */   {
/*  87 */     if (this._factoryConfig == paramSerializerFactoryConfig) {
/*  88 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  96 */     if (getClass() != BeanSerializerFactory.class) {
/*  97 */       throw new IllegalStateException("Subtype of BeanSerializerFactory (" + getClass().getName() + ") has not properly overridden method 'withAdditionalSerializers': can not instantiate subtype with " + "additional serializer definitions");
/*     */     }
/*     */     
/*     */ 
/* 101 */     return new BeanSerializerFactory(paramSerializerFactoryConfig);
/*     */   }
/*     */   
/*     */   protected Iterable<Serializers> customSerializers()
/*     */   {
/* 106 */     return this._factoryConfig.serializers();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<Object> createSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 132 */     SerializationConfig localSerializationConfig = paramSerializerProvider.getConfig();
/* 133 */     BeanDescription localBeanDescription = localSerializationConfig.introspect(paramJavaType);
/* 134 */     JsonSerializer localJsonSerializer = findSerializerFromAnnotation(paramSerializerProvider, localBeanDescription.getClassInfo());
/* 135 */     if (localJsonSerializer != null) {
/* 136 */       return localJsonSerializer;
/*     */     }
/*     */     
/*     */ 
/* 140 */     JavaType localJavaType1 = modifyTypeByAnnotation(localSerializationConfig, localBeanDescription.getClassInfo(), paramJavaType);
/* 141 */     boolean bool; if (localJavaType1 == paramJavaType) {
/* 142 */       bool = false;
/*     */     } else {
/* 144 */       bool = true;
/* 145 */       if (localJavaType1.getRawClass() != paramJavaType.getRawClass()) {
/* 146 */         localBeanDescription = localSerializationConfig.introspect(localJavaType1);
/*     */       }
/*     */     }
/*     */     
/* 150 */     Converter localConverter = localBeanDescription.findSerializationConverter();
/* 151 */     if (localConverter == null) {
/* 152 */       return _createSerializer2(paramSerializerProvider, localJavaType1, localBeanDescription, bool);
/*     */     }
/* 154 */     JavaType localJavaType2 = localConverter.getOutputType(paramSerializerProvider.getTypeFactory());
/* 155 */     return new StdDelegatingSerializer(localConverter, localJavaType2, _createSerializer2(paramSerializerProvider, localJavaType2, localBeanDescription, true));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<?> _createSerializer2(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 164 */     JsonSerializer localJsonSerializer = findSerializerByAnnotations(paramSerializerProvider, paramJavaType, paramBeanDescription);
/* 165 */     if (localJsonSerializer != null) {
/* 166 */       return localJsonSerializer;
/*     */     }
/* 168 */     SerializationConfig localSerializationConfig = paramSerializerProvider.getConfig();
/*     */     
/*     */     Iterator localIterator;
/*     */     
/* 172 */     if (paramJavaType.isContainerType()) {
/* 173 */       if (!paramBoolean) {
/* 174 */         paramBoolean = usesStaticTyping(localSerializationConfig, paramBeanDescription, null);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 187 */       localJsonSerializer = buildContainerSerializer(paramSerializerProvider, paramJavaType, paramBeanDescription, paramBoolean);
/*     */       
/* 189 */       if (localJsonSerializer != null) {
/* 190 */         return localJsonSerializer;
/*     */       }
/*     */     }
/*     */     else {
/* 194 */       for (localIterator = customSerializers().iterator(); localIterator.hasNext();) { localObject = (Serializers)localIterator.next();
/* 195 */         localJsonSerializer = ((Serializers)localObject).findSerializer(localSerializationConfig, paramJavaType, paramBeanDescription);
/* 196 */         if (localJsonSerializer != null) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     Object localObject;
/*     */     
/* 205 */     if (localJsonSerializer == null) {
/* 206 */       localJsonSerializer = findSerializerByLookup(paramJavaType, localSerializationConfig, paramBeanDescription, paramBoolean);
/* 207 */       if (localJsonSerializer == null) {
/* 208 */         localJsonSerializer = findSerializerByPrimaryType(paramSerializerProvider, paramJavaType, paramBeanDescription, paramBoolean);
/* 209 */         if (localJsonSerializer == null)
/*     */         {
/*     */ 
/*     */ 
/* 213 */           localJsonSerializer = findBeanSerializer(paramSerializerProvider, paramJavaType, paramBeanDescription);
/*     */           
/* 215 */           if (localJsonSerializer == null) {
/* 216 */             localJsonSerializer = findSerializerByAddonType(localSerializationConfig, paramJavaType, paramBeanDescription, paramBoolean);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 221 */     if (localJsonSerializer != null)
/*     */     {
/* 223 */       if (this._factoryConfig.hasSerializerModifiers()) {
/* 224 */         for (localIterator = this._factoryConfig.serializerModifiers().iterator(); localIterator.hasNext();) { localObject = (BeanSerializerModifier)localIterator.next();
/* 225 */           localJsonSerializer = ((BeanSerializerModifier)localObject).modifySerializer(localSerializationConfig, paramBeanDescription, localJsonSerializer);
/*     */         }
/*     */       }
/*     */     }
/* 229 */     return localJsonSerializer;
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
/*     */   @Deprecated
/*     */   public final JsonSerializer<Object> findBeanSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 249 */     return findBeanSerializer(paramSerializerProvider, paramJavaType, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<Object> findBeanSerializer(SerializerProvider paramSerializerProvider, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 261 */     if (!isPotentialBeanType(paramJavaType.getRawClass()))
/*     */     {
/*     */ 
/* 264 */       if (!paramJavaType.isEnumType()) {
/* 265 */         return null;
/*     */       }
/*     */     }
/* 268 */     return constructBeanSerializer(paramSerializerProvider, paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public final TypeSerializer findPropertyTypeSerializer(JavaType paramJavaType, SerializationConfig paramSerializationConfig, AnnotatedMember paramAnnotatedMember, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 278 */     return findPropertyTypeSerializer(paramJavaType, paramSerializationConfig, paramAnnotatedMember);
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
/*     */   public TypeSerializer findPropertyTypeSerializer(JavaType paramJavaType, SerializationConfig paramSerializationConfig, AnnotatedMember paramAnnotatedMember)
/*     */     throws JsonMappingException
/*     */   {
/* 295 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/* 296 */     TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findPropertyTypeResolver(paramSerializationConfig, paramAnnotatedMember, paramJavaType);
/*     */     
/* 298 */     if (localTypeResolverBuilder == null) {
/* 299 */       return createTypeSerializer(paramSerializationConfig, paramJavaType);
/*     */     }
/* 301 */     Collection localCollection = paramSerializationConfig.getSubtypeResolver().collectAndResolveSubtypes(paramAnnotatedMember, paramSerializationConfig, localAnnotationIntrospector, paramJavaType);
/*     */     
/* 303 */     return localTypeResolverBuilder.buildTypeSerializer(paramSerializationConfig, paramJavaType, localCollection);
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
/*     */   public TypeSerializer findPropertyContentTypeSerializer(JavaType paramJavaType, SerializationConfig paramSerializationConfig, AnnotatedMember paramAnnotatedMember)
/*     */     throws JsonMappingException
/*     */   {
/* 320 */     JavaType localJavaType = paramJavaType.getContentType();
/* 321 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/* 322 */     TypeResolverBuilder localTypeResolverBuilder = localAnnotationIntrospector.findPropertyContentTypeResolver(paramSerializationConfig, paramAnnotatedMember, paramJavaType);
/*     */     
/* 324 */     if (localTypeResolverBuilder == null) {
/* 325 */       return createTypeSerializer(paramSerializationConfig, localJavaType);
/*     */     }
/* 327 */     Collection localCollection = paramSerializationConfig.getSubtypeResolver().collectAndResolveSubtypes(paramAnnotatedMember, paramSerializationConfig, localAnnotationIntrospector, localJavaType);
/*     */     
/* 329 */     return localTypeResolverBuilder.buildTypeSerializer(paramSerializationConfig, localJavaType, localCollection);
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
/*     */   @Deprecated
/*     */   protected final JsonSerializer<Object> constructBeanSerializer(SerializerProvider paramSerializerProvider, BeanDescription paramBeanDescription, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 349 */     return constructBeanSerializer(paramSerializerProvider, paramBeanDescription);
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
/*     */   protected JsonSerializer<Object> constructBeanSerializer(SerializerProvider paramSerializerProvider, BeanDescription paramBeanDescription)
/*     */     throws JsonMappingException
/*     */   {
/* 364 */     if (paramBeanDescription.getBeanClass() == Object.class) {
/* 365 */       return paramSerializerProvider.getUnknownTypeSerializer(Object.class);
/*     */     }
/*     */     
/* 368 */     SerializationConfig localSerializationConfig = paramSerializerProvider.getConfig();
/* 369 */     BeanSerializerBuilder localBeanSerializerBuilder = constructBeanSerializerBuilder(paramBeanDescription);
/* 370 */     localBeanSerializerBuilder.setConfig(localSerializationConfig);
/*     */     
/*     */ 
/* 373 */     Object localObject1 = findBeanProperties(paramSerializerProvider, paramBeanDescription, localBeanSerializerBuilder);
/* 374 */     if (localObject1 == null) {
/* 375 */       localObject1 = new ArrayList();
/*     */     }
/*     */     
/* 378 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 379 */       for (localObject2 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (BeanSerializerModifier)((Iterator)localObject2).next();
/* 380 */         localObject1 = ((BeanSerializerModifier)localObject3).changeProperties(localSerializationConfig, paramBeanDescription, (List)localObject1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 385 */     localObject1 = filterBeanProperties(localSerializationConfig, paramBeanDescription, (List)localObject1);
/*     */     
/*     */ 
/* 388 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 389 */       for (localObject2 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (BeanSerializerModifier)((Iterator)localObject2).next();
/* 390 */         localObject1 = ((BeanSerializerModifier)localObject3).orderProperties(localSerializationConfig, paramBeanDescription, (List)localObject1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 398 */     localBeanSerializerBuilder.setObjectIdWriter(constructObjectIdHandler(paramSerializerProvider, paramBeanDescription, (List)localObject1));
/*     */     
/* 400 */     localBeanSerializerBuilder.setProperties((List)localObject1);
/* 401 */     localBeanSerializerBuilder.setFilterId(findFilterId(localSerializationConfig, paramBeanDescription));
/*     */     
/* 403 */     Object localObject2 = paramBeanDescription.findAnyGetter();
/* 404 */     if (localObject2 != null) {
/* 405 */       if (localSerializationConfig.canOverrideAccessModifiers()) {
/* 406 */         ((AnnotatedMember)localObject2).fixAccess();
/*     */       }
/* 408 */       localObject3 = ((AnnotatedMember)localObject2).getType(paramBeanDescription.bindingsForBeanType());
/*     */       
/* 410 */       boolean bool = localSerializationConfig.isEnabled(MapperFeature.USE_STATIC_TYPING);
/* 411 */       JavaType localJavaType = ((JavaType)localObject3).getContentType();
/* 412 */       TypeSerializer localTypeSerializer = createTypeSerializer(localSerializationConfig, localJavaType);
/*     */       
/* 414 */       MapSerializer localMapSerializer = MapSerializer.construct(null, (JavaType)localObject3, bool, localTypeSerializer, null, null);
/*     */       
/* 416 */       BeanProperty.Std localStd = new BeanProperty.Std(((AnnotatedMember)localObject2).getName(), localJavaType, null, paramBeanDescription.getClassAnnotations(), (AnnotatedMember)localObject2, false);
/*     */       
/* 418 */       localBeanSerializerBuilder.setAnyGetter(new AnyGetterWriter(localStd, (AnnotatedMember)localObject2, localMapSerializer));
/*     */     }
/*     */     
/* 421 */     processViews(localSerializationConfig, localBeanSerializerBuilder);
/*     */     
/*     */ 
/* 424 */     if (this._factoryConfig.hasSerializerModifiers()) {
/* 425 */       for (localObject3 = this._factoryConfig.serializerModifiers().iterator(); ((Iterator)localObject3).hasNext();) { BeanSerializerModifier localBeanSerializerModifier = (BeanSerializerModifier)((Iterator)localObject3).next();
/* 426 */         localBeanSerializerBuilder = localBeanSerializerModifier.updateBuilder(localSerializationConfig, paramBeanDescription, localBeanSerializerBuilder);
/*     */       }
/*     */     }
/*     */     
/* 430 */     Object localObject3 = localBeanSerializerBuilder.build();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 435 */     if (localObject3 == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 440 */       if (paramBeanDescription.hasKnownClassAnnotations()) {
/* 441 */         return localBeanSerializerBuilder.createDummy();
/*     */       }
/*     */     }
/* 444 */     return (JsonSerializer<Object>)localObject3;
/*     */   }
/*     */   
/*     */ 
/*     */   protected ObjectIdWriter constructObjectIdHandler(SerializerProvider paramSerializerProvider, BeanDescription paramBeanDescription, List<BeanPropertyWriter> paramList)
/*     */     throws JsonMappingException
/*     */   {
/* 451 */     ObjectIdInfo localObjectIdInfo = paramBeanDescription.getObjectIdInfo();
/* 452 */     if (localObjectIdInfo == null) {
/* 453 */       return null;
/*     */     }
/*     */     
/* 456 */     Class localClass = localObjectIdInfo.getGeneratorType();
/*     */     
/*     */ 
/* 459 */     if (localClass == ObjectIdGenerators.PropertyGenerator.class) {
/* 460 */       localObject1 = localObjectIdInfo.getPropertyName();
/* 461 */       localObject2 = null;
/*     */       
/* 463 */       int i = 0; for (int j = paramList.size();; i++) {
/* 464 */         if (i == j) {
/* 465 */           throw new IllegalArgumentException("Invalid Object Id definition for " + paramBeanDescription.getBeanClass().getName() + ": can not find property with name '" + (String)localObject1 + "'");
/*     */         }
/*     */         
/* 468 */         BeanPropertyWriter localBeanPropertyWriter = (BeanPropertyWriter)paramList.get(i);
/* 469 */         if (((String)localObject1).equals(localBeanPropertyWriter.getName())) {
/* 470 */           localObject2 = localBeanPropertyWriter;
/*     */           
/*     */ 
/*     */ 
/* 474 */           if (i <= 0) break;
/* 475 */           paramList.remove(i);
/* 476 */           paramList.add(0, localObject2); break;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 481 */       JavaType localJavaType = ((BeanPropertyWriter)localObject2).getType();
/* 482 */       localObject3 = new PropertyBasedObjectIdGenerator(localObjectIdInfo, (BeanPropertyWriter)localObject2);
/*     */       
/* 484 */       return ObjectIdWriter.construct(localJavaType, null, (ObjectIdGenerator)localObject3, localObjectIdInfo.getAlwaysAsId());
/*     */     }
/*     */     
/*     */ 
/* 488 */     Object localObject1 = paramSerializerProvider.constructType(localClass);
/*     */     
/* 490 */     Object localObject2 = paramSerializerProvider.getTypeFactory().findTypeParameters(localObject1, ObjectIdGenerator.class)[0];
/* 491 */     Object localObject3 = paramSerializerProvider.objectIdGeneratorInstance(paramBeanDescription.getClassInfo(), localObjectIdInfo);
/* 492 */     return ObjectIdWriter.construct((JavaType)localObject2, localObjectIdInfo.getPropertyName(), (ObjectIdGenerator)localObject3, localObjectIdInfo.getAlwaysAsId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanPropertyWriter constructFilteredBeanWriter(BeanPropertyWriter paramBeanPropertyWriter, Class<?>[] paramArrayOfClass)
/*     */   {
/* 504 */     return FilteredBeanPropertyWriter.constructViewBased(paramBeanPropertyWriter, paramArrayOfClass);
/*     */   }
/*     */   
/*     */ 
/*     */   protected PropertyBuilder constructPropertyBuilder(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription)
/*     */   {
/* 510 */     return new PropertyBuilder(paramSerializationConfig, paramBeanDescription);
/*     */   }
/*     */   
/*     */   protected BeanSerializerBuilder constructBeanSerializerBuilder(BeanDescription paramBeanDescription) {
/* 514 */     return new BeanSerializerBuilder(paramBeanDescription);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object findFilterId(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription)
/*     */   {
/* 523 */     return paramSerializationConfig.getAnnotationIntrospector().findFilterId(paramBeanDescription.getClassInfo());
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
/* 542 */     return (ClassUtil.canBeABeanType(paramClass) == null) && (!ClassUtil.isProxyType(paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected List<BeanPropertyWriter> findBeanProperties(SerializerProvider paramSerializerProvider, BeanDescription paramBeanDescription, BeanSerializerBuilder paramBeanSerializerBuilder)
/*     */     throws JsonMappingException
/*     */   {
/* 553 */     List localList = paramBeanDescription.findProperties();
/* 554 */     SerializationConfig localSerializationConfig = paramSerializerProvider.getConfig();
/*     */     
/*     */ 
/* 557 */     removeIgnorableTypes(localSerializationConfig, paramBeanDescription, localList);
/*     */     
/*     */ 
/* 560 */     if (localSerializationConfig.isEnabled(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)) {
/* 561 */       removeSetterlessGetters(localSerializationConfig, paramBeanDescription, localList);
/*     */     }
/*     */     
/*     */ 
/* 565 */     if (localList.isEmpty()) {
/* 566 */       return null;
/*     */     }
/*     */     
/* 569 */     boolean bool = usesStaticTyping(localSerializationConfig, paramBeanDescription, null);
/* 570 */     PropertyBuilder localPropertyBuilder = constructPropertyBuilder(localSerializationConfig, paramBeanDescription);
/*     */     
/* 572 */     ArrayList localArrayList = new ArrayList(localList.size());
/* 573 */     TypeBindings localTypeBindings = paramBeanDescription.bindingsForBeanType();
/* 574 */     for (BeanPropertyDefinition localBeanPropertyDefinition : localList) {
/* 575 */       AnnotatedMember localAnnotatedMember = localBeanPropertyDefinition.getAccessor();
/*     */       
/* 577 */       if (localBeanPropertyDefinition.isTypeId()) {
/* 578 */         if (localAnnotatedMember != null) {
/* 579 */           if (localSerializationConfig.canOverrideAccessModifiers()) {
/* 580 */             localAnnotatedMember.fixAccess();
/*     */           }
/* 582 */           paramBeanSerializerBuilder.setTypeId(localAnnotatedMember);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 587 */         AnnotationIntrospector.ReferenceProperty localReferenceProperty = localBeanPropertyDefinition.findReferenceType();
/* 588 */         if ((localReferenceProperty == null) || (!localReferenceProperty.isBackReference()))
/*     */         {
/*     */ 
/* 591 */           if ((localAnnotatedMember instanceof AnnotatedMethod)) {
/* 592 */             localArrayList.add(_constructWriter(paramSerializerProvider, localBeanPropertyDefinition, localTypeBindings, localPropertyBuilder, bool, (AnnotatedMethod)localAnnotatedMember));
/*     */           } else
/* 594 */             localArrayList.add(_constructWriter(paramSerializerProvider, localBeanPropertyDefinition, localTypeBindings, localPropertyBuilder, bool, (AnnotatedField)localAnnotatedMember)); }
/*     */       }
/*     */     }
/* 597 */     return localArrayList;
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
/*     */   protected List<BeanPropertyWriter> filterBeanProperties(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, List<BeanPropertyWriter> paramList)
/*     */   {
/* 613 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/* 614 */     AnnotatedClass localAnnotatedClass = paramBeanDescription.getClassInfo();
/* 615 */     String[] arrayOfString = localAnnotationIntrospector.findPropertiesToIgnore(localAnnotatedClass);
/* 616 */     if ((arrayOfString != null) && (arrayOfString.length > 0)) {
/* 617 */       HashSet localHashSet = ArrayBuilders.arrayToSet(arrayOfString);
/* 618 */       Iterator localIterator = paramList.iterator();
/* 619 */       while (localIterator.hasNext()) {
/* 620 */         if (localHashSet.contains(((BeanPropertyWriter)localIterator.next()).getName())) {
/* 621 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     }
/* 625 */     return paramList;
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
/*     */   protected void processViews(SerializationConfig paramSerializationConfig, BeanSerializerBuilder paramBeanSerializerBuilder)
/*     */   {
/* 640 */     List localList = paramBeanSerializerBuilder.getProperties();
/* 641 */     boolean bool = paramSerializationConfig.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
/* 642 */     int i = localList.size();
/* 643 */     int j = 0;
/* 644 */     BeanPropertyWriter[] arrayOfBeanPropertyWriter = new BeanPropertyWriter[i];
/*     */     
/* 646 */     for (int k = 0; k < i; k++) {
/* 647 */       BeanPropertyWriter localBeanPropertyWriter = (BeanPropertyWriter)localList.get(k);
/* 648 */       Class[] arrayOfClass = localBeanPropertyWriter.getViews();
/* 649 */       if (arrayOfClass == null) {
/* 650 */         if (bool) {
/* 651 */           arrayOfBeanPropertyWriter[k] = localBeanPropertyWriter;
/*     */         }
/*     */       } else {
/* 654 */         j++;
/* 655 */         arrayOfBeanPropertyWriter[k] = constructFilteredBeanWriter(localBeanPropertyWriter, arrayOfClass);
/*     */       }
/*     */     }
/*     */     
/* 659 */     if ((bool) && (j == 0)) {
/* 660 */       return;
/*     */     }
/* 662 */     paramBeanSerializerBuilder.setFilteredProperties(arrayOfBeanPropertyWriter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void removeIgnorableTypes(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, List<BeanPropertyDefinition> paramList)
/*     */   {
/* 673 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/* 674 */     HashMap localHashMap = new HashMap();
/* 675 */     Iterator localIterator = paramList.iterator();
/* 676 */     while (localIterator.hasNext()) {
/* 677 */       BeanPropertyDefinition localBeanPropertyDefinition = (BeanPropertyDefinition)localIterator.next();
/* 678 */       AnnotatedMember localAnnotatedMember = localBeanPropertyDefinition.getAccessor();
/* 679 */       if (localAnnotatedMember == null) {
/* 680 */         localIterator.remove();
/*     */       }
/*     */       else {
/* 683 */         Class localClass = localAnnotatedMember.getRawType();
/* 684 */         Boolean localBoolean = (Boolean)localHashMap.get(localClass);
/* 685 */         if (localBoolean == null) {
/* 686 */           BeanDescription localBeanDescription = paramSerializationConfig.introspectClassAnnotations(localClass);
/* 687 */           AnnotatedClass localAnnotatedClass = localBeanDescription.getClassInfo();
/* 688 */           localBoolean = localAnnotationIntrospector.isIgnorableType(localAnnotatedClass);
/*     */           
/* 690 */           if (localBoolean == null) {
/* 691 */             localBoolean = Boolean.FALSE;
/*     */           }
/* 693 */           localHashMap.put(localClass, localBoolean);
/*     */         }
/*     */         
/* 696 */         if (localBoolean.booleanValue()) {
/* 697 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void removeSetterlessGetters(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, List<BeanPropertyDefinition> paramList)
/*     */   {
/* 708 */     Iterator localIterator = paramList.iterator();
/* 709 */     while (localIterator.hasNext()) {
/* 710 */       BeanPropertyDefinition localBeanPropertyDefinition = (BeanPropertyDefinition)localIterator.next();
/*     */       
/*     */ 
/* 713 */       if ((!localBeanPropertyDefinition.couldDeserialize()) && (!localBeanPropertyDefinition.isExplicitlyIncluded())) {
/* 714 */         localIterator.remove();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanPropertyWriter _constructWriter(SerializerProvider paramSerializerProvider, BeanPropertyDefinition paramBeanPropertyDefinition, TypeBindings paramTypeBindings, PropertyBuilder paramPropertyBuilder, boolean paramBoolean, AnnotatedMember paramAnnotatedMember)
/*     */     throws JsonMappingException
/*     */   {
/* 734 */     String str = paramBeanPropertyDefinition.getName();
/* 735 */     if (paramSerializerProvider.canOverrideAccessModifiers()) {
/* 736 */       paramAnnotatedMember.fixAccess();
/*     */     }
/* 738 */     JavaType localJavaType = paramAnnotatedMember.getType(paramTypeBindings);
/* 739 */     BeanProperty.Std localStd = new BeanProperty.Std(str, localJavaType, paramBeanPropertyDefinition.getWrapperName(), paramPropertyBuilder.getClassAnnotations(), paramAnnotatedMember, paramBeanPropertyDefinition.isRequired());
/*     */     
/*     */ 
/*     */ 
/* 743 */     JsonSerializer localJsonSerializer = findSerializerFromAnnotation(paramSerializerProvider, paramAnnotatedMember);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 748 */     if ((localJsonSerializer instanceof ResolvableSerializer)) {
/* 749 */       ((ResolvableSerializer)localJsonSerializer).resolve(paramSerializerProvider);
/*     */     }
/* 751 */     if ((localJsonSerializer instanceof ContextualSerializer)) {
/* 752 */       localJsonSerializer = ((ContextualSerializer)localJsonSerializer).createContextual(paramSerializerProvider, localStd);
/*     */     }
/*     */     
/* 755 */     TypeSerializer localTypeSerializer1 = null;
/* 756 */     if (ClassUtil.isCollectionMapOrArray(localJavaType.getRawClass())) {
/* 757 */       localTypeSerializer1 = findPropertyContentTypeSerializer(localJavaType, paramSerializerProvider.getConfig(), paramAnnotatedMember);
/*     */     }
/*     */     
/*     */ 
/* 761 */     TypeSerializer localTypeSerializer2 = findPropertyTypeSerializer(localJavaType, paramSerializerProvider.getConfig(), paramAnnotatedMember);
/* 762 */     BeanPropertyWriter localBeanPropertyWriter = paramPropertyBuilder.buildWriter(paramBeanPropertyDefinition, localJavaType, localJsonSerializer, localTypeSerializer2, localTypeSerializer1, paramAnnotatedMember, paramBoolean);
/*     */     
/* 764 */     return localBeanPropertyWriter;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/BeanSerializerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */