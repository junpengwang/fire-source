/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonBackReference;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFilter;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonIdentityInfo;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonInclude;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonManagedReference;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonProperty;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonPropertyOrder;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonRawValue;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonSubTypes;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonSubTypes.Type;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonUnwrapped;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonValue;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonView;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonDeserialize;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonNaming;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import java.lang.annotation.Annotation;
/*     */ 
/*     */ public class JacksonAnnotationIntrospector extends com.shaded.fasterxml.jackson.databind.AnnotationIntrospector implements java.io.Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public com.shaded.fasterxml.jackson.core.Version version()
/*     */   {
/*  36 */     return com.shaded.fasterxml.jackson.databind.cfg.PackageVersion.VERSION;
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
/*     */   @Deprecated
/*     */   public boolean isHandled(Annotation paramAnnotation)
/*     */   {
/*  50 */     Class localClass = paramAnnotation.annotationType();
/*  51 */     return localClass.getAnnotation(com.shaded.fasterxml.jackson.annotation.JacksonAnnotation.class) != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAnnotationBundle(Annotation paramAnnotation)
/*     */   {
/*  61 */     return paramAnnotation.annotationType().getAnnotation(com.shaded.fasterxml.jackson.annotation.JacksonAnnotationsInside.class) != null;
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
/*     */   public PropertyName findRootName(AnnotatedClass paramAnnotatedClass)
/*     */   {
/*  82 */     com.shaded.fasterxml.jackson.annotation.JsonRootName localJsonRootName = (com.shaded.fasterxml.jackson.annotation.JsonRootName)paramAnnotatedClass.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonRootName.class);
/*  83 */     if (localJsonRootName == null) {
/*  84 */       return null;
/*     */     }
/*  86 */     return new PropertyName(localJsonRootName.value());
/*     */   }
/*     */   
/*     */   public String[] findPropertiesToIgnore(Annotated paramAnnotated)
/*     */   {
/*  91 */     JsonIgnoreProperties localJsonIgnoreProperties = (JsonIgnoreProperties)paramAnnotated.getAnnotation(JsonIgnoreProperties.class);
/*  92 */     return localJsonIgnoreProperties == null ? null : localJsonIgnoreProperties.value();
/*     */   }
/*     */   
/*     */   public Boolean findIgnoreUnknownProperties(AnnotatedClass paramAnnotatedClass)
/*     */   {
/*  97 */     JsonIgnoreProperties localJsonIgnoreProperties = (JsonIgnoreProperties)paramAnnotatedClass.getAnnotation(JsonIgnoreProperties.class);
/*  98 */     return localJsonIgnoreProperties == null ? null : Boolean.valueOf(localJsonIgnoreProperties.ignoreUnknown());
/*     */   }
/*     */   
/*     */   public Boolean isIgnorableType(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 103 */     com.shaded.fasterxml.jackson.annotation.JsonIgnoreType localJsonIgnoreType = (com.shaded.fasterxml.jackson.annotation.JsonIgnoreType)paramAnnotatedClass.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonIgnoreType.class);
/* 104 */     return localJsonIgnoreType == null ? null : Boolean.valueOf(localJsonIgnoreType.value());
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findFilterId(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 110 */     JsonFilter localJsonFilter = (JsonFilter)paramAnnotatedClass.getAnnotation(JsonFilter.class);
/* 111 */     if (localJsonFilter != null) {
/* 112 */       String str = localJsonFilter.value();
/*     */       
/* 114 */       if (str.length() > 0) {
/* 115 */         return str;
/*     */       }
/*     */     }
/* 118 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findNamingStrategy(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 124 */     JsonNaming localJsonNaming = (JsonNaming)paramAnnotatedClass.getAnnotation(JsonNaming.class);
/* 125 */     return localJsonNaming == null ? null : localJsonNaming.value();
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
/*     */   public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass paramAnnotatedClass, VisibilityChecker<?> paramVisibilityChecker)
/*     */   {
/* 138 */     com.shaded.fasterxml.jackson.annotation.JsonAutoDetect localJsonAutoDetect = (com.shaded.fasterxml.jackson.annotation.JsonAutoDetect)paramAnnotatedClass.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonAutoDetect.class);
/* 139 */     return localJsonAutoDetect == null ? paramVisibilityChecker : paramVisibilityChecker.with(localJsonAutoDetect);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 151 */     JsonManagedReference localJsonManagedReference = (JsonManagedReference)paramAnnotatedMember.getAnnotation(JsonManagedReference.class);
/* 152 */     if (localJsonManagedReference != null) {
/* 153 */       return com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.managed(localJsonManagedReference.value());
/*     */     }
/* 155 */     JsonBackReference localJsonBackReference = (JsonBackReference)paramAnnotatedMember.getAnnotation(JsonBackReference.class);
/* 156 */     if (localJsonBackReference != null) {
/* 157 */       return com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.back(localJsonBackReference.value());
/*     */     }
/* 159 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public com.shaded.fasterxml.jackson.databind.util.NameTransformer findUnwrappingNameTransformer(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 165 */     JsonUnwrapped localJsonUnwrapped = (JsonUnwrapped)paramAnnotatedMember.getAnnotation(JsonUnwrapped.class);
/*     */     
/*     */ 
/* 168 */     if ((localJsonUnwrapped == null) || (!localJsonUnwrapped.enabled())) {
/* 169 */       return null;
/*     */     }
/* 171 */     String str1 = localJsonUnwrapped.prefix();
/* 172 */     String str2 = localJsonUnwrapped.suffix();
/* 173 */     return com.shaded.fasterxml.jackson.databind.util.NameTransformer.simpleTransformer(str1, str2);
/*     */   }
/*     */   
/*     */   public boolean hasIgnoreMarker(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 178 */     return _isIgnorable(paramAnnotatedMember);
/*     */   }
/*     */   
/*     */ 
/*     */   public Boolean hasRequiredMarker(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 184 */     JsonProperty localJsonProperty = (JsonProperty)paramAnnotatedMember.getAnnotation(JsonProperty.class);
/* 185 */     if (localJsonProperty != null) {
/* 186 */       return Boolean.valueOf(localJsonProperty.required());
/*     */     }
/* 188 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findInjectableValueId(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 194 */     com.shaded.fasterxml.jackson.annotation.JacksonInject localJacksonInject = (com.shaded.fasterxml.jackson.annotation.JacksonInject)paramAnnotatedMember.getAnnotation(com.shaded.fasterxml.jackson.annotation.JacksonInject.class);
/* 195 */     if (localJacksonInject == null) {
/* 196 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 201 */     String str = localJacksonInject.value();
/* 202 */     if (str.length() == 0)
/*     */     {
/* 204 */       if (!(paramAnnotatedMember instanceof AnnotatedMethod)) {
/* 205 */         return paramAnnotatedMember.getRawType().getName();
/*     */       }
/* 207 */       AnnotatedMethod localAnnotatedMethod = (AnnotatedMethod)paramAnnotatedMember;
/* 208 */       if (localAnnotatedMethod.getParameterCount() == 0) {
/* 209 */         return paramAnnotatedMember.getRawType().getName();
/*     */       }
/* 211 */       return localAnnotatedMethod.getRawParameterType(0).getName();
/*     */     }
/* 213 */     return str;
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
/* 226 */     return _findTypeResolver(paramMapperConfig, paramAnnotatedClass, paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
/*     */   {
/* 236 */     if (paramJavaType.isContainerType()) { return null;
/*     */     }
/* 238 */     return _findTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
/*     */   {
/* 248 */     if (!paramJavaType.isContainerType()) {
/* 249 */       throw new IllegalArgumentException("Must call method with a container type (got " + paramJavaType + ")");
/*     */     }
/* 251 */     return _findTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */   public java.util.List<com.shaded.fasterxml.jackson.databind.jsontype.NamedType> findSubtypes(Annotated paramAnnotated)
/*     */   {
/* 257 */     JsonSubTypes localJsonSubTypes = (JsonSubTypes)paramAnnotated.getAnnotation(JsonSubTypes.class);
/* 258 */     if (localJsonSubTypes == null) return null;
/* 259 */     JsonSubTypes.Type[] arrayOfType1 = localJsonSubTypes.value();
/* 260 */     java.util.ArrayList localArrayList = new java.util.ArrayList(arrayOfType1.length);
/* 261 */     for (JsonSubTypes.Type localType : arrayOfType1) {
/* 262 */       localArrayList.add(new com.shaded.fasterxml.jackson.databind.jsontype.NamedType(localType.value(), localType.name()));
/*     */     }
/* 264 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */   public String findTypeName(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 270 */     com.shaded.fasterxml.jackson.annotation.JsonTypeName localJsonTypeName = (com.shaded.fasterxml.jackson.annotation.JsonTypeName)paramAnnotatedClass.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonTypeName.class);
/* 271 */     return localJsonTypeName == null ? null : localJsonTypeName.value();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object findSerializer(Annotated paramAnnotated)
/*     */   {
/* 283 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 284 */     if (localJsonSerialize != null) {
/* 285 */       localObject = localJsonSerialize.using();
/* 286 */       if (localObject != com.shaded.fasterxml.jackson.databind.JsonSerializer.None.class) {
/* 287 */         return localObject;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 295 */     Object localObject = (JsonRawValue)paramAnnotated.getAnnotation(JsonRawValue.class);
/* 296 */     if ((localObject != null) && (((JsonRawValue)localObject).value()))
/*     */     {
/* 298 */       Class localClass = paramAnnotated.getRawType();
/* 299 */       return new com.shaded.fasterxml.jackson.databind.ser.std.RawSerializer(localClass);
/*     */     }
/* 301 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<? extends com.shaded.fasterxml.jackson.databind.JsonSerializer<?>> findKeySerializer(Annotated paramAnnotated)
/*     */   {
/* 307 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 308 */     if (localJsonSerialize != null) {
/* 309 */       Class localClass = localJsonSerialize.keyUsing();
/* 310 */       if (localClass != com.shaded.fasterxml.jackson.databind.JsonSerializer.None.class) {
/* 311 */         return localClass;
/*     */       }
/*     */     }
/* 314 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<? extends com.shaded.fasterxml.jackson.databind.JsonSerializer<?>> findContentSerializer(Annotated paramAnnotated)
/*     */   {
/* 320 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 321 */     if (localJsonSerialize != null) {
/* 322 */       Class localClass = localJsonSerialize.contentUsing();
/* 323 */       if (localClass != com.shaded.fasterxml.jackson.databind.JsonSerializer.None.class) {
/* 324 */         return localClass;
/*     */       }
/*     */     }
/* 327 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonInclude.Include findSerializationInclusion(Annotated paramAnnotated, JsonInclude.Include paramInclude)
/*     */   {
/* 333 */     JsonInclude localJsonInclude = (JsonInclude)paramAnnotated.getAnnotation(JsonInclude.class);
/* 334 */     if (localJsonInclude != null) {
/* 335 */       return localJsonInclude.value();
/*     */     }
/* 337 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 338 */     if (localJsonSerialize != null)
/*     */     {
/* 340 */       com.shaded.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion localInclusion = localJsonSerialize.include();
/* 341 */       switch (localInclusion) {
/*     */       case ALWAYS: 
/* 343 */         return JsonInclude.Include.ALWAYS;
/*     */       case NON_NULL: 
/* 345 */         return JsonInclude.Include.NON_NULL;
/*     */       case NON_DEFAULT: 
/* 347 */         return JsonInclude.Include.NON_DEFAULT;
/*     */       case NON_EMPTY: 
/* 349 */         return JsonInclude.Include.NON_EMPTY;
/*     */       }
/*     */     }
/* 352 */     return paramInclude;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findSerializationType(Annotated paramAnnotated)
/*     */   {
/* 358 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 359 */     if (localJsonSerialize != null) {
/* 360 */       Class localClass = localJsonSerialize.as();
/* 361 */       if (localClass != NoClass.class) {
/* 362 */         return localClass;
/*     */       }
/*     */     }
/* 365 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findSerializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 371 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 372 */     if (localJsonSerialize != null) {
/* 373 */       Class localClass = localJsonSerialize.keyAs();
/* 374 */       if (localClass != NoClass.class) {
/* 375 */         return localClass;
/*     */       }
/*     */     }
/* 378 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findSerializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 384 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 385 */     if (localJsonSerialize != null) {
/* 386 */       Class localClass = localJsonSerialize.contentAs();
/* 387 */       if (localClass != NoClass.class) {
/* 388 */         return localClass;
/*     */       }
/*     */     }
/* 391 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public com.shaded.fasterxml.jackson.databind.annotation.JsonSerialize.Typing findSerializationTyping(Annotated paramAnnotated)
/*     */   {
/* 397 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 398 */     return localJsonSerialize == null ? null : localJsonSerialize.typing();
/*     */   }
/*     */   
/*     */   public Object findSerializationConverter(Annotated paramAnnotated)
/*     */   {
/* 403 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
/* 404 */     if (localJsonSerialize != null) {
/* 405 */       Class localClass = localJsonSerialize.converter();
/* 406 */       if (localClass != com.shaded.fasterxml.jackson.databind.util.Converter.None.class) {
/* 407 */         return localClass;
/*     */       }
/*     */     }
/* 410 */     return null;
/*     */   }
/*     */   
/*     */   public Object findSerializationContentConverter(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 415 */     JsonSerialize localJsonSerialize = (JsonSerialize)paramAnnotatedMember.getAnnotation(JsonSerialize.class);
/* 416 */     if (localJsonSerialize != null) {
/* 417 */       Class localClass = localJsonSerialize.contentConverter();
/* 418 */       if (localClass != com.shaded.fasterxml.jackson.databind.util.Converter.None.class) {
/* 419 */         return localClass;
/*     */       }
/*     */     }
/* 422 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?>[] findViews(Annotated paramAnnotated)
/*     */   {
/* 428 */     JsonView localJsonView = (JsonView)paramAnnotated.getAnnotation(JsonView.class);
/* 429 */     return localJsonView == null ? null : localJsonView.value();
/*     */   }
/*     */   
/*     */   public Boolean isTypeId(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 434 */     return Boolean.valueOf(paramAnnotatedMember.hasAnnotation(com.shaded.fasterxml.jackson.annotation.JsonTypeId.class));
/*     */   }
/*     */   
/*     */   public ObjectIdInfo findObjectIdInfo(Annotated paramAnnotated)
/*     */   {
/* 439 */     JsonIdentityInfo localJsonIdentityInfo = (JsonIdentityInfo)paramAnnotated.getAnnotation(JsonIdentityInfo.class);
/* 440 */     if ((localJsonIdentityInfo == null) || (localJsonIdentityInfo.generator() == com.shaded.fasterxml.jackson.annotation.ObjectIdGenerators.None.class)) {
/* 441 */       return null;
/*     */     }
/* 443 */     return new ObjectIdInfo(localJsonIdentityInfo.property(), localJsonIdentityInfo.scope(), localJsonIdentityInfo.generator());
/*     */   }
/*     */   
/*     */   public ObjectIdInfo findObjectReferenceInfo(Annotated paramAnnotated, ObjectIdInfo paramObjectIdInfo)
/*     */   {
/* 448 */     com.shaded.fasterxml.jackson.annotation.JsonIdentityReference localJsonIdentityReference = (com.shaded.fasterxml.jackson.annotation.JsonIdentityReference)paramAnnotated.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonIdentityReference.class);
/* 449 */     if (localJsonIdentityReference != null) {
/* 450 */       paramObjectIdInfo = paramObjectIdInfo.withAlwaysAsId(localJsonIdentityReference.alwaysAsId());
/*     */     }
/* 452 */     return paramObjectIdInfo;
/*     */   }
/*     */   
/*     */   public com.shaded.fasterxml.jackson.annotation.JsonFormat.Value findFormat(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 457 */     return findFormat(paramAnnotatedMember);
/*     */   }
/*     */   
/*     */   public com.shaded.fasterxml.jackson.annotation.JsonFormat.Value findFormat(Annotated paramAnnotated)
/*     */   {
/* 462 */     com.shaded.fasterxml.jackson.annotation.JsonFormat localJsonFormat = (com.shaded.fasterxml.jackson.annotation.JsonFormat)paramAnnotated.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonFormat.class);
/* 463 */     return localJsonFormat == null ? null : new com.shaded.fasterxml.jackson.annotation.JsonFormat.Value(localJsonFormat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] findSerializationPropertyOrder(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 474 */     JsonPropertyOrder localJsonPropertyOrder = (JsonPropertyOrder)paramAnnotatedClass.getAnnotation(JsonPropertyOrder.class);
/* 475 */     return localJsonPropertyOrder == null ? null : localJsonPropertyOrder.value();
/*     */   }
/*     */   
/*     */   public Boolean findSerializationSortAlphabetically(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 480 */     JsonPropertyOrder localJsonPropertyOrder = (JsonPropertyOrder)paramAnnotatedClass.getAnnotation(JsonPropertyOrder.class);
/* 481 */     return localJsonPropertyOrder == null ? null : Boolean.valueOf(localJsonPropertyOrder.alphabetic());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyName findNameForSerialization(Annotated paramAnnotated)
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 496 */     if ((paramAnnotated instanceof AnnotatedField)) {
/* 497 */       str = findSerializationName((AnnotatedField)paramAnnotated);
/* 498 */     } else if ((paramAnnotated instanceof AnnotatedMethod)) {
/* 499 */       str = findSerializationName((AnnotatedMethod)paramAnnotated);
/*     */     } else {
/* 501 */       str = null;
/*     */     }
/* 503 */     if (str != null) {
/* 504 */       if (str.length() == 0) {
/* 505 */         return PropertyName.USE_DEFAULT;
/*     */       }
/* 507 */       return new PropertyName(str);
/*     */     }
/* 509 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public String findSerializationName(AnnotatedField paramAnnotatedField)
/*     */   {
/* 515 */     JsonProperty localJsonProperty = (JsonProperty)paramAnnotatedField.getAnnotation(JsonProperty.class);
/* 516 */     if (localJsonProperty != null) {
/* 517 */       return localJsonProperty.value();
/*     */     }
/*     */     
/*     */ 
/* 521 */     if ((paramAnnotatedField.hasAnnotation(JsonSerialize.class)) || (paramAnnotatedField.hasAnnotation(JsonView.class))) {
/* 522 */       return "";
/*     */     }
/* 524 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String findSerializationName(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 531 */     com.shaded.fasterxml.jackson.annotation.JsonGetter localJsonGetter = (com.shaded.fasterxml.jackson.annotation.JsonGetter)paramAnnotatedMethod.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonGetter.class);
/* 532 */     if (localJsonGetter != null) {
/* 533 */       return localJsonGetter.value();
/*     */     }
/* 535 */     JsonProperty localJsonProperty = (JsonProperty)paramAnnotatedMethod.getAnnotation(JsonProperty.class);
/* 536 */     if (localJsonProperty != null) {
/* 537 */       return localJsonProperty.value();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 543 */     if ((paramAnnotatedMethod.hasAnnotation(JsonSerialize.class)) || (paramAnnotatedMethod.hasAnnotation(JsonView.class))) {
/* 544 */       return "";
/*     */     }
/* 546 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasAsValueAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 552 */     JsonValue localJsonValue = (JsonValue)paramAnnotatedMethod.getAnnotation(JsonValue.class);
/*     */     
/* 554 */     return (localJsonValue != null) && (localJsonValue.value());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<? extends com.shaded.fasterxml.jackson.databind.JsonDeserializer<?>> findDeserializer(Annotated paramAnnotated)
/*     */   {
/* 566 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
/* 567 */     if (localJsonDeserialize != null) {
/* 568 */       Class localClass = localJsonDeserialize.using();
/* 569 */       if (localClass != com.shaded.fasterxml.jackson.databind.JsonDeserializer.None.class) {
/* 570 */         return localClass;
/*     */       }
/*     */     }
/* 573 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<? extends com.shaded.fasterxml.jackson.databind.KeyDeserializer> findKeyDeserializer(Annotated paramAnnotated)
/*     */   {
/* 579 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
/* 580 */     if (localJsonDeserialize != null) {
/* 581 */       Class localClass = localJsonDeserialize.keyUsing();
/* 582 */       if (localClass != com.shaded.fasterxml.jackson.databind.KeyDeserializer.None.class) {
/* 583 */         return localClass;
/*     */       }
/*     */     }
/* 586 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<? extends com.shaded.fasterxml.jackson.databind.JsonDeserializer<?>> findContentDeserializer(Annotated paramAnnotated)
/*     */   {
/* 592 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
/* 593 */     if (localJsonDeserialize != null) {
/* 594 */       Class localClass = localJsonDeserialize.contentUsing();
/* 595 */       if (localClass != com.shaded.fasterxml.jackson.databind.JsonDeserializer.None.class) {
/* 596 */         return localClass;
/*     */       }
/*     */     }
/* 599 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Class<?> findDeserializationType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 606 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
/* 607 */     if (localJsonDeserialize != null) {
/* 608 */       Class localClass = localJsonDeserialize.as();
/* 609 */       if (localClass != NoClass.class) {
/* 610 */         return localClass;
/*     */       }
/*     */     }
/* 613 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Class<?> findDeserializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 620 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
/* 621 */     if (localJsonDeserialize != null) {
/* 622 */       Class localClass = localJsonDeserialize.keyAs();
/* 623 */       if (localClass != NoClass.class) {
/* 624 */         return localClass;
/*     */       }
/*     */     }
/* 627 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Class<?> findDeserializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 634 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
/* 635 */     if (localJsonDeserialize != null) {
/* 636 */       Class localClass = localJsonDeserialize.contentAs();
/* 637 */       if (localClass != NoClass.class) {
/* 638 */         return localClass;
/*     */       }
/*     */     }
/* 641 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findDeserializationConverter(Annotated paramAnnotated)
/*     */   {
/* 647 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
/* 648 */     if (localJsonDeserialize != null) {
/* 649 */       Class localClass = localJsonDeserialize.converter();
/* 650 */       if (localClass != com.shaded.fasterxml.jackson.databind.util.Converter.None.class) {
/* 651 */         return localClass;
/*     */       }
/*     */     }
/* 654 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object findDeserializationContentConverter(AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 660 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotatedMember.getAnnotation(JsonDeserialize.class);
/* 661 */     if (localJsonDeserialize != null) {
/* 662 */       Class localClass = localJsonDeserialize.contentConverter();
/* 663 */       if (localClass != com.shaded.fasterxml.jackson.databind.util.Converter.None.class) {
/* 664 */         return localClass;
/*     */       }
/*     */     }
/* 667 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object findValueInstantiator(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 678 */     com.shaded.fasterxml.jackson.databind.annotation.JsonValueInstantiator localJsonValueInstantiator = (com.shaded.fasterxml.jackson.databind.annotation.JsonValueInstantiator)paramAnnotatedClass.getAnnotation(com.shaded.fasterxml.jackson.databind.annotation.JsonValueInstantiator.class);
/*     */     
/* 680 */     return localJsonValueInstantiator == null ? null : localJsonValueInstantiator.value();
/*     */   }
/*     */   
/*     */ 
/*     */   public Class<?> findPOJOBuilder(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 686 */     JsonDeserialize localJsonDeserialize = (JsonDeserialize)paramAnnotatedClass.getAnnotation(JsonDeserialize.class);
/* 687 */     return (localJsonDeserialize == null) || (localJsonDeserialize.builder() == NoClass.class) ? null : localJsonDeserialize.builder();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass paramAnnotatedClass)
/*     */   {
/* 694 */     com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder localJsonPOJOBuilder = (com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder)paramAnnotatedClass.getAnnotation(com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.class);
/* 695 */     return localJsonPOJOBuilder == null ? null : new com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value(localJsonPOJOBuilder);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyName findNameForDeserialization(Annotated paramAnnotated)
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 710 */     if ((paramAnnotated instanceof AnnotatedField)) {
/* 711 */       str = findDeserializationName((AnnotatedField)paramAnnotated);
/* 712 */     } else if ((paramAnnotated instanceof AnnotatedMethod)) {
/* 713 */       str = findDeserializationName((AnnotatedMethod)paramAnnotated);
/* 714 */     } else if ((paramAnnotated instanceof AnnotatedParameter)) {
/* 715 */       str = findDeserializationName((AnnotatedParameter)paramAnnotated);
/*     */     } else {
/* 717 */       str = null;
/*     */     }
/* 719 */     if (str != null) {
/* 720 */       if (str.length() == 0) {
/* 721 */         return PropertyName.USE_DEFAULT;
/*     */       }
/* 723 */       return new PropertyName(str);
/*     */     }
/* 725 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String findDeserializationName(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 732 */     com.shaded.fasterxml.jackson.annotation.JsonSetter localJsonSetter = (com.shaded.fasterxml.jackson.annotation.JsonSetter)paramAnnotatedMethod.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonSetter.class);
/* 733 */     if (localJsonSetter != null) {
/* 734 */       return localJsonSetter.value();
/*     */     }
/* 736 */     JsonProperty localJsonProperty = (JsonProperty)paramAnnotatedMethod.getAnnotation(JsonProperty.class);
/* 737 */     if (localJsonProperty != null) {
/* 738 */       return localJsonProperty.value();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 743 */     if ((paramAnnotatedMethod.hasAnnotation(JsonDeserialize.class)) || (paramAnnotatedMethod.hasAnnotation(JsonView.class)) || (paramAnnotatedMethod.hasAnnotation(JsonBackReference.class)) || (paramAnnotatedMethod.hasAnnotation(JsonManagedReference.class)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 748 */       return "";
/*     */     }
/* 750 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public String findDeserializationName(AnnotatedField paramAnnotatedField)
/*     */   {
/* 756 */     JsonProperty localJsonProperty = (JsonProperty)paramAnnotatedField.getAnnotation(JsonProperty.class);
/* 757 */     if (localJsonProperty != null) {
/* 758 */       return localJsonProperty.value();
/*     */     }
/*     */     
/*     */ 
/* 762 */     if ((paramAnnotatedField.hasAnnotation(JsonDeserialize.class)) || (paramAnnotatedField.hasAnnotation(JsonView.class)) || (paramAnnotatedField.hasAnnotation(JsonBackReference.class)) || (paramAnnotatedField.hasAnnotation(JsonManagedReference.class)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 767 */       return "";
/*     */     }
/* 769 */     return null;
/*     */   }
/*     */   
/*     */   public String findDeserializationName(AnnotatedParameter paramAnnotatedParameter)
/*     */   {
/* 774 */     if (paramAnnotatedParameter != null) {
/* 775 */       JsonProperty localJsonProperty = (JsonProperty)paramAnnotatedParameter.getAnnotation(JsonProperty.class);
/* 776 */       if (localJsonProperty != null) {
/* 777 */         return localJsonProperty.value();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 784 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasAnySetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 794 */     return paramAnnotatedMethod.hasAnnotation(com.shaded.fasterxml.jackson.annotation.JsonAnySetter.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasAnyGetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/* 803 */     return paramAnnotatedMethod.hasAnnotation(com.shaded.fasterxml.jackson.annotation.JsonAnyGetter.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasCreatorAnnotation(Annotated paramAnnotated)
/*     */   {
/* 813 */     return paramAnnotated.hasAnnotation(com.shaded.fasterxml.jackson.annotation.JsonCreator.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean _isIgnorable(Annotated paramAnnotated)
/*     */   {
/* 824 */     com.shaded.fasterxml.jackson.annotation.JsonIgnore localJsonIgnore = (com.shaded.fasterxml.jackson.annotation.JsonIgnore)paramAnnotated.getAnnotation(com.shaded.fasterxml.jackson.annotation.JsonIgnore.class);
/* 825 */     return (localJsonIgnore != null) && (localJsonIgnore.value());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected TypeResolverBuilder<?> _findTypeResolver(MapperConfig<?> paramMapperConfig, Annotated paramAnnotated, JavaType paramJavaType)
/*     */   {
/* 837 */     JsonTypeInfo localJsonTypeInfo = (JsonTypeInfo)paramAnnotated.getAnnotation(JsonTypeInfo.class);
/* 838 */     com.shaded.fasterxml.jackson.databind.annotation.JsonTypeResolver localJsonTypeResolver = (com.shaded.fasterxml.jackson.databind.annotation.JsonTypeResolver)paramAnnotated.getAnnotation(com.shaded.fasterxml.jackson.databind.annotation.JsonTypeResolver.class);
/*     */     
/* 840 */     if (localJsonTypeResolver != null) {
/* 841 */       if (localJsonTypeInfo == null) {
/* 842 */         return null;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 848 */       localObject = paramMapperConfig.typeResolverBuilderInstance(paramAnnotated, localJsonTypeResolver.value());
/*     */     } else {
/* 850 */       if (localJsonTypeInfo == null) {
/* 851 */         return null;
/*     */       }
/*     */       
/* 854 */       if (localJsonTypeInfo.use() == com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id.NONE) {
/* 855 */         return _constructNoTypeResolverBuilder();
/*     */       }
/* 857 */       localObject = _constructStdTypeResolverBuilder();
/*     */     }
/*     */     
/* 860 */     JsonTypeIdResolver localJsonTypeIdResolver = (JsonTypeIdResolver)paramAnnotated.getAnnotation(JsonTypeIdResolver.class);
/* 861 */     com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver localTypeIdResolver = localJsonTypeIdResolver == null ? null : paramMapperConfig.typeIdResolverInstance(paramAnnotated, localJsonTypeIdResolver.value());
/*     */     
/* 863 */     if (localTypeIdResolver != null) {
/* 864 */       localTypeIdResolver.init(paramJavaType);
/*     */     }
/* 866 */     Object localObject = ((TypeResolverBuilder)localObject).init(localJsonTypeInfo.use(), localTypeIdResolver);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 871 */     com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As localAs = localJsonTypeInfo.include();
/* 872 */     if ((localAs == com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As.EXTERNAL_PROPERTY) && ((paramAnnotated instanceof AnnotatedClass))) {
/* 873 */       localAs = com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
/*     */     }
/* 875 */     localObject = ((TypeResolverBuilder)localObject).inclusion(localAs);
/* 876 */     localObject = ((TypeResolverBuilder)localObject).typeProperty(localJsonTypeInfo.property());
/* 877 */     Class localClass = localJsonTypeInfo.defaultImpl();
/* 878 */     if (localClass != com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.None.class) {
/* 879 */       localObject = ((TypeResolverBuilder)localObject).defaultImpl(localClass);
/*     */     }
/* 881 */     localObject = ((TypeResolverBuilder)localObject).typeIdVisibility(localJsonTypeInfo.visible());
/* 882 */     return (TypeResolverBuilder<?>)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected com.shaded.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder _constructStdTypeResolverBuilder()
/*     */   {
/* 890 */     return new com.shaded.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected com.shaded.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder _constructNoTypeResolverBuilder()
/*     */   {
/* 898 */     return com.shaded.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder.noTypeInfoBuilder();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/JacksonAnnotationIntrospector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */