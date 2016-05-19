/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer.None;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ public class AnnotationIntrospectorPair
/*     */   extends AnnotationIntrospector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotationIntrospector _primary;
/*     */   protected final AnnotationIntrospector _secondary;
/*     */   
/*     */   public AnnotationIntrospectorPair(AnnotationIntrospector paramAnnotationIntrospector1, AnnotationIntrospector paramAnnotationIntrospector2)
/*     */   {
/*  47 */     this._primary = paramAnnotationIntrospector1;
/*  48 */     this._secondary = paramAnnotationIntrospector2;
/*     */   }
/*     */   
/*     */   public Version version()
/*     */   {
/*  53 */     return this._primary.version();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnnotationIntrospector create(AnnotationIntrospector paramAnnotationIntrospector1, AnnotationIntrospector paramAnnotationIntrospector2)
/*     */   {
/*  64 */     if (paramAnnotationIntrospector1 == null) {
/*  65 */       return paramAnnotationIntrospector2;
/*     */     }
/*  67 */     if (paramAnnotationIntrospector2 == null) {
/*  68 */       return paramAnnotationIntrospector1;
/*     */     }
/*  70 */     return new AnnotationIntrospectorPair(paramAnnotationIntrospector1, paramAnnotationIntrospector2);
/*     */   }
/*     */   
/*     */   public Collection<AnnotationIntrospector> allIntrospectors()
/*     */   {
/*  75 */     return allIntrospectors(new ArrayList());
/*     */   }
/*     */   
/*     */ 
/*     */   public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> paramCollection)
/*     */   {
/*  81 */     this._primary.allIntrospectors(paramCollection);
/*  82 */     this._secondary.allIntrospectors(paramCollection);
/*  83 */     return paramCollection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isAnnotationBundle(Annotation paramAnnotation)
/*     */   {
/*  90 */     return (this._primary.isAnnotationBundle(paramAnnotation)) || (this._secondary.isAnnotationBundle(paramAnnotation));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyName findRootName(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 102 */     PropertyName localPropertyName1 = this._primary.findRootName(paramAnnotatedClass);
/* 103 */     if (localPropertyName1 == null) {
/* 104 */       return this._secondary.findRootName(paramAnnotatedClass);
/*     */     }
/* 106 */     if (localPropertyName1.hasSimpleName()) {
/* 107 */       return localPropertyName1;
/*     */     }
/*     */     
/* 110 */     PropertyName localPropertyName2 = this._secondary.findRootName(paramAnnotatedClass);
/* 111 */     return localPropertyName2 == null ? localPropertyName1 : localPropertyName2;
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] findPropertiesToIgnore(Annotated paramAnnotated)
/*     */   {
/* 117 */     String[] arrayOfString = this._primary.findPropertiesToIgnore(paramAnnotated);
/* 118 */     if (arrayOfString == null) {
/* 119 */       arrayOfString = this._secondary.findPropertiesToIgnore(paramAnnotated);
/*     */     }
/* 121 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */   public Boolean findIgnoreUnknownProperties(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 127 */     Boolean localBoolean = this._primary.findIgnoreUnknownProperties(paramAnnotatedClass);
/* 128 */     if (localBoolean == null) {
/* 129 */       localBoolean = this._secondary.findIgnoreUnknownProperties(paramAnnotatedClass);
/*     */     }
/* 131 */     return localBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */   public Boolean isIgnorableType(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 137 */     Boolean localBoolean = this._primary.isIgnorableType(paramAnnotatedClass);
/* 138 */     if (localBoolean == null) {
/* 139 */       localBoolean = this._secondary.isIgnorableType(paramAnnotatedClass);
/*     */     }
/* 141 */     return localBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findFilterId(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 147 */     Object localObject = this._primary.findFilterId(paramAnnotatedClass);
/* 148 */     if (localObject == null) {
/* 149 */       localObject = this._secondary.findFilterId(paramAnnotatedClass);
/*     */     }
/* 151 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findNamingStrategy(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 157 */     Object localObject = this._primary.findNamingStrategy(paramAnnotatedClass);
/* 158 */     if (localObject == null) {
/* 159 */       localObject = this._secondary.findNamingStrategy(paramAnnotatedClass);
/*     */     }
/* 161 */     return localObject;
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
/*     */   public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass paramAnnotatedClass, VisibilityChecker<?> paramVisibilityChecker)
/*     */   {
/* 177 */     paramVisibilityChecker = this._secondary.findAutoDetectVisibility(paramAnnotatedClass, paramVisibilityChecker);
/* 178 */     return this._primary.findAutoDetectVisibility(paramAnnotatedClass, paramVisibilityChecker);
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
/*     */   public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedClass paramAnnotatedClass, JavaType paramJavaType)
/*     */   {
/* 191 */     TypeResolverBuilder localTypeResolverBuilder = this._primary.findTypeResolver(paramMapperConfig, paramAnnotatedClass, paramJavaType);
/* 192 */     if (localTypeResolverBuilder == null) {
/* 193 */       localTypeResolverBuilder = this._secondary.findTypeResolver(paramMapperConfig, paramAnnotatedClass, paramJavaType);
/*     */     }
/* 195 */     return localTypeResolverBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
/*     */   {
/* 202 */     TypeResolverBuilder localTypeResolverBuilder = this._primary.findPropertyTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
/* 203 */     if (localTypeResolverBuilder == null) {
/* 204 */       localTypeResolverBuilder = this._secondary.findPropertyTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
/*     */     }
/* 206 */     return localTypeResolverBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
/*     */   {
/* 213 */     TypeResolverBuilder localTypeResolverBuilder = this._primary.findPropertyContentTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
/* 214 */     if (localTypeResolverBuilder == null) {
/* 215 */       localTypeResolverBuilder = this._secondary.findPropertyContentTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
/*     */     }
/* 217 */     return localTypeResolverBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<NamedType> findSubtypes(Annotated paramAnnotated)
/*     */   {
/* 223 */     List localList1 = this._primary.findSubtypes(paramAnnotated);
/* 224 */     List localList2 = this._secondary.findSubtypes(paramAnnotated);
/* 225 */     if ((localList1 == null) || (localList1.isEmpty())) return localList2;
/* 226 */     if ((localList2 == null) || (localList2.isEmpty())) return localList1;
/* 227 */     ArrayList localArrayList = new ArrayList(localList1.size() + localList2.size());
/* 228 */     localArrayList.addAll(localList1);
/* 229 */     localArrayList.addAll(localList2);
/* 230 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */   public String findTypeName(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 236 */     String str = this._primary.findTypeName(paramAnnotatedClass);
/* 237 */     if ((str == null) || (str.length() == 0)) {
/* 238 */       str = this._secondary.findTypeName(paramAnnotatedClass);
/*     */     }
/* 240 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 248 */     AnnotationIntrospector.ReferenceProperty localReferenceProperty = this._primary.findReferenceType(paramAnnotatedMember);
/* 249 */     if (localReferenceProperty == null) {
/* 250 */       localReferenceProperty = this._secondary.findReferenceType(paramAnnotatedMember);
/*     */     }
/* 252 */     return localReferenceProperty;
/*     */   }
/*     */   
/*     */ 
/*     */   public NameTransformer findUnwrappingNameTransformer(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 258 */     NameTransformer localNameTransformer = this._primary.findUnwrappingNameTransformer(paramAnnotatedMember);
/* 259 */     if (localNameTransformer == null) {
/* 260 */       localNameTransformer = this._secondary.findUnwrappingNameTransformer(paramAnnotatedMember);
/*     */     }
/* 262 */     return localNameTransformer;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findInjectableValueId(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 268 */     Object localObject = this._primary.findInjectableValueId(paramAnnotatedMember);
/* 269 */     if (localObject == null) {
/* 270 */       localObject = this._secondary.findInjectableValueId(paramAnnotatedMember);
/*     */     }
/* 272 */     return localObject;
/*     */   }
/*     */   
/*     */   public boolean hasIgnoreMarker(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 277 */     return (this._primary.hasIgnoreMarker(paramAnnotatedMember)) || (this._secondary.hasIgnoreMarker(paramAnnotatedMember));
/*     */   }
/*     */   
/*     */ 
/*     */   public Boolean hasRequiredMarker(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 283 */     Boolean localBoolean = this._primary.hasRequiredMarker(paramAnnotatedMember);
/* 284 */     if (localBoolean == null) {
/* 285 */       localBoolean = this._secondary.hasRequiredMarker(paramAnnotatedMember);
/*     */     }
/* 287 */     return localBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object findSerializer(Annotated paramAnnotated)
/*     */   {
/* 295 */     Object localObject = this._primary.findSerializer(paramAnnotated);
/* 296 */     if (localObject == null) {
/* 297 */       localObject = this._secondary.findSerializer(paramAnnotated);
/*     */     }
/* 299 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findKeySerializer(Annotated paramAnnotated)
/*     */   {
/* 305 */     Object localObject = this._primary.findKeySerializer(paramAnnotated);
/* 306 */     if ((localObject == null) || (localObject == JsonSerializer.None.class) || (localObject == NoClass.class)) {
/* 307 */       localObject = this._secondary.findKeySerializer(paramAnnotated);
/*     */     }
/* 309 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findContentSerializer(Annotated paramAnnotated)
/*     */   {
/* 315 */     Object localObject = this._primary.findContentSerializer(paramAnnotated);
/* 316 */     if ((localObject == null) || (localObject == JsonSerializer.None.class) || (localObject == NoClass.class)) {
/* 317 */       localObject = this._secondary.findContentSerializer(paramAnnotated);
/*     */     }
/* 319 */     return localObject;
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
/*     */   public JsonInclude.Include findSerializationInclusion(Annotated paramAnnotated, JsonInclude.Include paramInclude)
/*     */   {
/* 337 */     paramInclude = this._secondary.findSerializationInclusion(paramAnnotated, paramInclude);
/* 338 */     paramInclude = this._primary.findSerializationInclusion(paramAnnotated, paramInclude);
/* 339 */     return paramInclude;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findSerializationType(Annotated paramAnnotated)
/*     */   {
/* 345 */     Class localClass = this._primary.findSerializationType(paramAnnotated);
/* 346 */     if (localClass == null) {
/* 347 */       localClass = this._secondary.findSerializationType(paramAnnotated);
/*     */     }
/* 349 */     return localClass;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findSerializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 355 */     Class localClass = this._primary.findSerializationKeyType(paramAnnotated, paramJavaType);
/* 356 */     if (localClass == null) {
/* 357 */       localClass = this._secondary.findSerializationKeyType(paramAnnotated, paramJavaType);
/*     */     }
/* 359 */     return localClass;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findSerializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 365 */     Class localClass = this._primary.findSerializationContentType(paramAnnotated, paramJavaType);
/* 366 */     if (localClass == null) {
/* 367 */       localClass = this._secondary.findSerializationContentType(paramAnnotated, paramJavaType);
/*     */     }
/* 369 */     return localClass;
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonSerialize.Typing findSerializationTyping(Annotated paramAnnotated)
/*     */   {
/* 375 */     JsonSerialize.Typing localTyping = this._primary.findSerializationTyping(paramAnnotated);
/* 376 */     if (localTyping == null) {
/* 377 */       localTyping = this._secondary.findSerializationTyping(paramAnnotated);
/*     */     }
/* 379 */     return localTyping;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findSerializationConverter(Annotated paramAnnotated)
/*     */   {
/* 385 */     Object localObject = this._primary.findSerializationConverter(paramAnnotated);
/* 386 */     if (localObject == null) {
/* 387 */       localObject = this._secondary.findSerializationConverter(paramAnnotated);
/*     */     }
/* 389 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findSerializationContentConverter(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 395 */     Object localObject = this._primary.findSerializationContentConverter(paramAnnotatedMember);
/* 396 */     if (localObject == null) {
/* 397 */       localObject = this._secondary.findSerializationContentConverter(paramAnnotatedMember);
/*     */     }
/* 399 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?>[] findViews(Annotated paramAnnotated)
/*     */   {
/* 409 */     Class[] arrayOfClass = this._primary.findViews(paramAnnotated);
/* 410 */     if (arrayOfClass == null) {
/* 411 */       arrayOfClass = this._secondary.findViews(paramAnnotated);
/*     */     }
/* 413 */     return arrayOfClass;
/*     */   }
/*     */   
/*     */   public Boolean isTypeId(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 418 */     Boolean localBoolean = this._primary.isTypeId(paramAnnotatedMember);
/* 419 */     if (localBoolean == null) {
/* 420 */       localBoolean = this._secondary.isTypeId(paramAnnotatedMember);
/*     */     }
/* 422 */     return localBoolean;
/*     */   }
/*     */   
/*     */   public ObjectIdInfo findObjectIdInfo(Annotated paramAnnotated)
/*     */   {
/* 427 */     ObjectIdInfo localObjectIdInfo = this._primary.findObjectIdInfo(paramAnnotated);
/* 428 */     if (localObjectIdInfo == null) {
/* 429 */       localObjectIdInfo = this._secondary.findObjectIdInfo(paramAnnotated);
/*     */     }
/* 431 */     return localObjectIdInfo;
/*     */   }
/*     */   
/*     */ 
/*     */   public ObjectIdInfo findObjectReferenceInfo(Annotated paramAnnotated, ObjectIdInfo paramObjectIdInfo)
/*     */   {
/* 437 */     paramObjectIdInfo = this._secondary.findObjectReferenceInfo(paramAnnotated, paramObjectIdInfo);
/* 438 */     paramObjectIdInfo = this._primary.findObjectReferenceInfo(paramAnnotated, paramObjectIdInfo);
/* 439 */     return paramObjectIdInfo;
/*     */   }
/*     */   
/*     */   public JsonFormat.Value findFormat(Annotated paramAnnotated)
/*     */   {
/* 444 */     JsonFormat.Value localValue = this._primary.findFormat(paramAnnotated);
/* 445 */     if (localValue == null) {
/* 446 */       localValue = this._secondary.findFormat(paramAnnotated);
/*     */     }
/* 448 */     return localValue;
/*     */   }
/*     */   
/*     */   public PropertyName findWrapperName(Annotated paramAnnotated)
/*     */   {
/* 453 */     Object localObject = this._primary.findWrapperName(paramAnnotated);
/* 454 */     if (localObject == null) {
/* 455 */       localObject = this._secondary.findWrapperName(paramAnnotated);
/* 456 */     } else if (localObject == PropertyName.USE_DEFAULT)
/*     */     {
/* 458 */       PropertyName localPropertyName = this._secondary.findWrapperName(paramAnnotated);
/* 459 */       if (localPropertyName != null) {
/* 460 */         localObject = localPropertyName;
/*     */       }
/*     */     }
/* 463 */     return (PropertyName)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String[] findSerializationPropertyOrder(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 470 */     String[] arrayOfString = this._primary.findSerializationPropertyOrder(paramAnnotatedClass);
/* 471 */     if (arrayOfString == null) {
/* 472 */       arrayOfString = this._secondary.findSerializationPropertyOrder(paramAnnotatedClass);
/*     */     }
/* 474 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Boolean findSerializationSortAlphabetically(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 484 */     Boolean localBoolean = this._primary.findSerializationSortAlphabetically(paramAnnotatedClass);
/* 485 */     if (localBoolean == null) {
/* 486 */       localBoolean = this._secondary.findSerializationSortAlphabetically(paramAnnotatedClass);
/*     */     }
/* 488 */     return localBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public PropertyName findNameForSerialization(Annotated paramAnnotated)
/*     */   {
/* 495 */     Object localObject = this._primary.findNameForSerialization(paramAnnotated);
/*     */     
/* 497 */     if (localObject == null) {
/* 498 */       localObject = this._secondary.findNameForSerialization(paramAnnotated);
/* 499 */     } else if (localObject == PropertyName.USE_DEFAULT) {
/* 500 */       PropertyName localPropertyName = this._secondary.findNameForSerialization(paramAnnotated);
/* 501 */       if (localPropertyName != null) {
/* 502 */         localObject = localPropertyName;
/*     */       }
/*     */     }
/* 505 */     return (PropertyName)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasAsValueAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 511 */     return (this._primary.hasAsValueAnnotation(paramAnnotatedMethod)) || (this._secondary.hasAsValueAnnotation(paramAnnotatedMethod));
/*     */   }
/*     */   
/*     */ 
/*     */   public String findEnumValue(Enum<?> paramEnum)
/*     */   {
/* 517 */     String str = this._primary.findEnumValue(paramEnum);
/* 518 */     if (str == null) {
/* 519 */       str = this._secondary.findEnumValue(paramEnum);
/*     */     }
/* 521 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object findDeserializer(Annotated paramAnnotated)
/*     */   {
/* 529 */     Object localObject = this._primary.findDeserializer(paramAnnotated);
/* 530 */     if (localObject == null) {
/* 531 */       localObject = this._secondary.findDeserializer(paramAnnotated);
/*     */     }
/* 533 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findKeyDeserializer(Annotated paramAnnotated)
/*     */   {
/* 539 */     Object localObject = this._primary.findKeyDeserializer(paramAnnotated);
/* 540 */     if ((localObject == null) || (localObject == KeyDeserializer.None.class) || (localObject == NoClass.class)) {
/* 541 */       localObject = this._secondary.findKeyDeserializer(paramAnnotated);
/*     */     }
/* 543 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findContentDeserializer(Annotated paramAnnotated)
/*     */   {
/* 549 */     Object localObject = this._primary.findContentDeserializer(paramAnnotated);
/* 550 */     if ((localObject == null) || (localObject == JsonDeserializer.None.class) || (localObject == NoClass.class)) {
/* 551 */       localObject = this._secondary.findContentDeserializer(paramAnnotated);
/*     */     }
/* 553 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findDeserializationType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 559 */     Class localClass = this._primary.findDeserializationType(paramAnnotated, paramJavaType);
/* 560 */     if (localClass == null) {
/* 561 */       localClass = this._secondary.findDeserializationType(paramAnnotated, paramJavaType);
/*     */     }
/* 563 */     return localClass;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findDeserializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 569 */     Class localClass = this._primary.findDeserializationKeyType(paramAnnotated, paramJavaType);
/* 570 */     if (localClass == null) {
/* 571 */       localClass = this._secondary.findDeserializationKeyType(paramAnnotated, paramJavaType);
/*     */     }
/* 573 */     return localClass;
/*     */   }
/*     */   
/*     */   public Class<?> findDeserializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 578 */     Class localClass = this._primary.findDeserializationContentType(paramAnnotated, paramJavaType);
/* 579 */     if (localClass == null) {
/* 580 */       localClass = this._secondary.findDeserializationContentType(paramAnnotated, paramJavaType);
/*     */     }
/* 582 */     return localClass;
/*     */   }
/*     */   
/*     */   public Object findDeserializationConverter(Annotated paramAnnotated)
/*     */   {
/* 587 */     Object localObject = this._primary.findDeserializationConverter(paramAnnotated);
/* 588 */     if (localObject == null) {
/* 589 */       localObject = this._secondary.findDeserializationConverter(paramAnnotated);
/*     */     }
/* 591 */     return localObject;
/*     */   }
/*     */   
/*     */   public Object findDeserializationContentConverter(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 596 */     Object localObject = this._primary.findDeserializationContentConverter(paramAnnotatedMember);
/* 597 */     if (localObject == null) {
/* 598 */       localObject = this._secondary.findDeserializationContentConverter(paramAnnotatedMember);
/*     */     }
/* 600 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object findValueInstantiator(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 608 */     Object localObject = this._primary.findValueInstantiator(paramAnnotatedClass);
/* 609 */     if (localObject == null) {
/* 610 */       localObject = this._secondary.findValueInstantiator(paramAnnotatedClass);
/*     */     }
/* 612 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findPOJOBuilder(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 618 */     Class localClass = this._primary.findPOJOBuilder(paramAnnotatedClass);
/* 619 */     if (localClass == null) {
/* 620 */       localClass = this._secondary.findPOJOBuilder(paramAnnotatedClass);
/*     */     }
/* 622 */     return localClass;
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 628 */     JsonPOJOBuilder.Value localValue = this._primary.findPOJOBuilderConfig(paramAnnotatedClass);
/* 629 */     if (localValue == null) {
/* 630 */       localValue = this._secondary.findPOJOBuilderConfig(paramAnnotatedClass);
/*     */     }
/* 632 */     return localValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyName findNameForDeserialization(Annotated paramAnnotated)
/*     */   {
/* 641 */     Object localObject = this._primary.findNameForDeserialization(paramAnnotated);
/* 642 */     if (localObject == null) {
/* 643 */       localObject = this._secondary.findNameForDeserialization(paramAnnotated);
/* 644 */     } else if (localObject == PropertyName.USE_DEFAULT) {
/* 645 */       PropertyName localPropertyName = this._secondary.findNameForDeserialization(paramAnnotated);
/* 646 */       if (localPropertyName != null) {
/* 647 */         localObject = localPropertyName;
/*     */       }
/*     */     }
/* 650 */     return (PropertyName)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasAnySetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 656 */     return (this._primary.hasAnySetterAnnotation(paramAnnotatedMethod)) || (this._secondary.hasAnySetterAnnotation(paramAnnotatedMethod));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasAnyGetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 662 */     return (this._primary.hasAnyGetterAnnotation(paramAnnotatedMethod)) || (this._secondary.hasAnyGetterAnnotation(paramAnnotatedMethod));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasCreatorAnnotation(Annotated paramAnnotated)
/*     */   {
/* 668 */     return (this._primary.hasCreatorAnnotation(paramAnnotated)) || (this._secondary.hasCreatorAnnotation(paramAnnotated));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public boolean isHandled(Annotation paramAnnotation)
/*     */   {
/* 680 */     return (this._primary.isHandled(paramAnnotation)) || (this._secondary.isHandled(paramAnnotation));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public String findDeserializationName(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 689 */     Object localObject = this._primary.findDeserializationName(paramAnnotatedMethod);
/* 690 */     if (localObject == null) {
/* 691 */       localObject = this._secondary.findDeserializationName(paramAnnotatedMethod);
/* 692 */     } else if (((String)localObject).length() == 0)
/*     */     {
/*     */ 
/*     */ 
/* 696 */       String str = this._secondary.findDeserializationName(paramAnnotatedMethod);
/* 697 */       if (str != null) {
/* 698 */         localObject = str;
/*     */       }
/*     */     }
/* 701 */     return (String)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   public String findDeserializationName(AnnotatedField paramAnnotatedField)
/*     */   {
/* 708 */     Object localObject = this._primary.findDeserializationName(paramAnnotatedField);
/* 709 */     if (localObject == null) {
/* 710 */       localObject = this._secondary.findDeserializationName(paramAnnotatedField);
/* 711 */     } else if (((String)localObject).length() == 0)
/*     */     {
/*     */ 
/*     */ 
/* 715 */       String str = this._secondary.findDeserializationName(paramAnnotatedField);
/* 716 */       if (str != null) {
/* 717 */         localObject = str;
/*     */       }
/*     */     }
/* 720 */     return (String)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   public String findDeserializationName(AnnotatedParameter paramAnnotatedParameter)
/*     */   {
/* 727 */     String str = this._primary.findDeserializationName(paramAnnotatedParameter);
/* 728 */     if (str == null) {
/* 729 */       str = this._secondary.findDeserializationName(paramAnnotatedParameter);
/*     */     }
/* 731 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public String findSerializationName(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 740 */     Object localObject = this._primary.findSerializationName(paramAnnotatedMethod);
/* 741 */     if (localObject == null) {
/* 742 */       localObject = this._secondary.findSerializationName(paramAnnotatedMethod);
/* 743 */     } else if (((String)localObject).length() == 0)
/*     */     {
/*     */ 
/*     */ 
/* 747 */       String str = this._secondary.findSerializationName(paramAnnotatedMethod);
/* 748 */       if (str != null) {
/* 749 */         localObject = str;
/*     */       }
/*     */     }
/* 752 */     return (String)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   public String findSerializationName(AnnotatedField paramAnnotatedField)
/*     */   {
/* 759 */     Object localObject = this._primary.findSerializationName(paramAnnotatedField);
/* 760 */     if (localObject == null) {
/* 761 */       localObject = this._secondary.findSerializationName(paramAnnotatedField);
/* 762 */     } else if (((String)localObject).length() == 0)
/*     */     {
/*     */ 
/*     */ 
/* 766 */       String str = this._secondary.findSerializationName(paramAnnotatedField);
/* 767 */       if (str != null) {
/* 768 */         localObject = str;
/*     */       }
/*     */     }
/* 771 */     return (String)localObject;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotationIntrospectorPair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */