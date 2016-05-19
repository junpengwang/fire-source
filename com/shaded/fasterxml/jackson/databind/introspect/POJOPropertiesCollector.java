/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.MapperFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyNamingStrategy;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.util.BeanUtil;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POJOPropertiesCollector
/*     */ {
/*     */   protected final MapperConfig<?> _config;
/*     */   protected final boolean _forSerialization;
/*     */   protected final JavaType _type;
/*     */   protected final AnnotatedClass _classDef;
/*     */   protected final VisibilityChecker<?> _visibilityChecker;
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */   protected final String _mutatorPrefix;
/*  64 */   protected final LinkedHashMap<String, POJOPropertyBuilder> _properties = new LinkedHashMap();
/*     */   
/*     */ 
/*  67 */   protected LinkedList<POJOPropertyBuilder> _creatorProperties = null;
/*     */   
/*  69 */   protected LinkedList<AnnotatedMember> _anyGetters = null;
/*     */   
/*  71 */   protected LinkedList<AnnotatedMethod> _anySetters = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  76 */   protected LinkedList<AnnotatedMethod> _jsonValueGetters = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HashSet<String> _ignoredPropertyNames;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected LinkedHashMap<Object, AnnotatedMember> _injectables;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected POJOPropertiesCollector(MapperConfig<?> paramMapperConfig, boolean paramBoolean, JavaType paramJavaType, AnnotatedClass paramAnnotatedClass, String paramString)
/*     */   {
/* 101 */     this._config = paramMapperConfig;
/* 102 */     this._forSerialization = paramBoolean;
/* 103 */     this._type = paramJavaType;
/* 104 */     this._classDef = paramAnnotatedClass;
/* 105 */     this._mutatorPrefix = (paramString == null ? "set" : paramString);
/* 106 */     this._annotationIntrospector = (paramMapperConfig.isAnnotationProcessingEnabled() ? this._config.getAnnotationIntrospector() : null);
/*     */     
/* 108 */     if (this._annotationIntrospector == null) {
/* 109 */       this._visibilityChecker = this._config.getDefaultVisibilityChecker();
/*     */     } else {
/* 111 */       this._visibilityChecker = this._annotationIntrospector.findAutoDetectVisibility(paramAnnotatedClass, this._config.getDefaultVisibilityChecker());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MapperConfig<?> getConfig()
/*     */   {
/* 123 */     return this._config;
/*     */   }
/*     */   
/*     */   public JavaType getType() {
/* 127 */     return this._type;
/*     */   }
/*     */   
/*     */   public AnnotatedClass getClassDef() {
/* 131 */     return this._classDef;
/*     */   }
/*     */   
/*     */   public AnnotationIntrospector getAnnotationIntrospector() {
/* 135 */     return this._annotationIntrospector;
/*     */   }
/*     */   
/*     */   public List<BeanPropertyDefinition> getProperties()
/*     */   {
/* 140 */     return new ArrayList(this._properties.values());
/*     */   }
/*     */   
/*     */   public Map<Object, AnnotatedMember> getInjectables() {
/* 144 */     return this._injectables;
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedMethod getJsonValueMethod()
/*     */   {
/* 150 */     if (this._jsonValueGetters != null) {
/* 151 */       if (this._jsonValueGetters.size() > 1) {
/* 152 */         reportProblem("Multiple value properties defined (" + this._jsonValueGetters.get(0) + " vs " + this._jsonValueGetters.get(1) + ")");
/*     */       }
/*     */       
/*     */ 
/* 156 */       return (AnnotatedMethod)this._jsonValueGetters.get(0);
/*     */     }
/* 158 */     return null;
/*     */   }
/*     */   
/*     */   public AnnotatedMember getAnyGetter()
/*     */   {
/* 163 */     if (this._anyGetters != null) {
/* 164 */       if (this._anyGetters.size() > 1) {
/* 165 */         reportProblem("Multiple 'any-getters' defined (" + this._anyGetters.get(0) + " vs " + this._anyGetters.get(1) + ")");
/*     */       }
/*     */       
/* 168 */       return (AnnotatedMember)this._anyGetters.getFirst();
/*     */     }
/* 170 */     return null;
/*     */   }
/*     */   
/*     */   public AnnotatedMethod getAnySetterMethod()
/*     */   {
/* 175 */     if (this._anySetters != null) {
/* 176 */       if (this._anySetters.size() > 1) {
/* 177 */         reportProblem("Multiple 'any-setters' defined (" + this._anySetters.get(0) + " vs " + this._anySetters.get(1) + ")");
/*     */       }
/*     */       
/* 180 */       return (AnnotatedMethod)this._anySetters.getFirst();
/*     */     }
/* 182 */     return null;
/*     */   }
/*     */   
/*     */   public Set<String> getIgnoredPropertyNames() {
/* 186 */     return this._ignoredPropertyNames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectIdInfo getObjectIdInfo()
/*     */   {
/* 194 */     if (this._annotationIntrospector == null) {
/* 195 */       return null;
/*     */     }
/* 197 */     ObjectIdInfo localObjectIdInfo = this._annotationIntrospector.findObjectIdInfo(this._classDef);
/* 198 */     if (localObjectIdInfo != null) {
/* 199 */       localObjectIdInfo = this._annotationIntrospector.findObjectReferenceInfo(this._classDef, localObjectIdInfo);
/*     */     }
/* 201 */     return localObjectIdInfo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> findPOJOBuilderClass()
/*     */   {
/* 209 */     return this._annotationIntrospector.findPOJOBuilder(this._classDef);
/*     */   }
/*     */   
/*     */   protected Map<String, POJOPropertyBuilder> getPropertyMap()
/*     */   {
/* 214 */     return this._properties;
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
/*     */   public POJOPropertiesCollector collect()
/*     */   {
/* 229 */     this._properties.clear();
/*     */     
/*     */ 
/* 232 */     _addFields();
/* 233 */     _addMethods();
/* 234 */     _addCreators();
/* 235 */     _addInjectables();
/*     */     
/*     */ 
/* 238 */     _removeUnwantedProperties();
/*     */     
/*     */ 
/* 241 */     _renameProperties();
/*     */     
/* 243 */     PropertyNamingStrategy localPropertyNamingStrategy = _findNamingStrategy();
/* 244 */     if (localPropertyNamingStrategy != null) {
/* 245 */       _renameUsing(localPropertyNamingStrategy);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 252 */     for (Iterator localIterator = this._properties.values().iterator(); localIterator.hasNext();) { localPOJOPropertyBuilder = (POJOPropertyBuilder)localIterator.next();
/* 253 */       localPOJOPropertyBuilder.trimByVisibility();
/*     */     }
/*     */     
/*     */     POJOPropertyBuilder localPOJOPropertyBuilder;
/* 257 */     for (localIterator = this._properties.values().iterator(); localIterator.hasNext();) { localPOJOPropertyBuilder = (POJOPropertyBuilder)localIterator.next();
/* 258 */       localPOJOPropertyBuilder.mergeAnnotations(this._forSerialization);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 264 */     if (this._config.isEnabled(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)) {
/* 265 */       _renameWithWrappers();
/*     */     }
/*     */     
/*     */ 
/* 269 */     _sortProperties();
/* 270 */     return this;
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
/*     */   protected void _sortProperties()
/*     */   {
/* 285 */     AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
/*     */     
/* 287 */     Boolean localBoolean = localAnnotationIntrospector == null ? null : localAnnotationIntrospector.findSerializationSortAlphabetically(this._classDef);
/*     */     boolean bool;
/* 289 */     if (localBoolean == null) {
/* 290 */       bool = this._config.shouldSortPropertiesAlphabetically();
/*     */     } else {
/* 292 */       bool = localBoolean.booleanValue();
/*     */     }
/* 294 */     String[] arrayOfString = localAnnotationIntrospector == null ? null : localAnnotationIntrospector.findSerializationPropertyOrder(this._classDef);
/*     */     
/*     */ 
/* 297 */     if ((!bool) && (this._creatorProperties == null) && (arrayOfString == null)) {
/* 298 */       return;
/*     */     }
/* 300 */     int i = this._properties.size();
/*     */     
/*     */     Object localObject1;
/* 303 */     if (bool) {
/* 304 */       localObject1 = new TreeMap();
/*     */     } else {
/* 306 */       localObject1 = new LinkedHashMap(i + i);
/*     */     }
/*     */     
/* 309 */     for (Object localObject2 = this._properties.values().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (POJOPropertyBuilder)((Iterator)localObject2).next();
/* 310 */       ((Map)localObject1).put(((POJOPropertyBuilder)localObject3).getName(), localObject3); }
/*     */     Object localObject3;
/* 312 */     localObject2 = new LinkedHashMap(i + i);
/*     */     
/* 314 */     if (arrayOfString != null) {
/* 315 */       for (Object localObject4 : arrayOfString) {
/* 316 */         Object localObject5 = (POJOPropertyBuilder)((Map)localObject1).get(localObject4);
/* 317 */         if (localObject5 == null) {
/* 318 */           for (POJOPropertyBuilder localPOJOPropertyBuilder2 : this._properties.values()) {
/* 319 */             if (((String)localObject4).equals(localPOJOPropertyBuilder2.getInternalName())) {
/* 320 */               localObject5 = localPOJOPropertyBuilder2;
/*     */               
/* 322 */               localObject4 = localPOJOPropertyBuilder2.getName();
/* 323 */               break;
/*     */             }
/*     */           }
/*     */         }
/* 327 */         if (localObject5 != null) {
/* 328 */           ((Map)localObject2).put(localObject4, localObject5);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 333 */     if (this._creatorProperties != null) {
/* 334 */       for (localObject3 = this._creatorProperties.iterator(); ((Iterator)localObject3).hasNext();) { POJOPropertyBuilder localPOJOPropertyBuilder1 = (POJOPropertyBuilder)((Iterator)localObject3).next();
/* 335 */         ((Map)localObject2).put(localPOJOPropertyBuilder1.getName(), localPOJOPropertyBuilder1);
/*     */       }
/*     */     }
/*     */     
/* 339 */     ((Map)localObject2).putAll((Map)localObject1);
/*     */     
/* 341 */     this._properties.clear();
/* 342 */     this._properties.putAll((Map)localObject2);
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
/*     */   protected void _addFields()
/*     */   {
/* 356 */     AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 361 */     int i = (!this._forSerialization) && (!this._config.isEnabled(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS)) ? 1 : 0;
/*     */     
/* 363 */     for (AnnotatedField localAnnotatedField : this._classDef.fields()) {
/* 364 */       String str1 = localAnnotatedField.getName();
/*     */       
/*     */       String str2;
/* 367 */       if (localAnnotationIntrospector == null) {
/* 368 */         str2 = null; } else { PropertyName localPropertyName;
/* 369 */         if (this._forSerialization)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 375 */           localPropertyName = localAnnotationIntrospector.findNameForSerialization(localAnnotatedField);
/* 376 */           str2 = localPropertyName == null ? null : localPropertyName.getSimpleName();
/*     */         } else {
/* 378 */           localPropertyName = localAnnotationIntrospector.findNameForDeserialization(localAnnotatedField);
/* 379 */           str2 = localPropertyName == null ? null : localPropertyName.getSimpleName();
/*     */         } }
/* 381 */       if ("".equals(str2)) {
/* 382 */         str2 = str1;
/*     */       }
/*     */       
/* 385 */       boolean bool1 = str2 != null;
/* 386 */       if (!bool1) {
/* 387 */         bool1 = this._visibilityChecker.isFieldVisible(localAnnotatedField);
/*     */       }
/*     */       
/* 390 */       boolean bool2 = (localAnnotationIntrospector != null) && (localAnnotationIntrospector.hasIgnoreMarker(localAnnotatedField));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 396 */       if ((i == 0) || (str2 != null) || (bool2) || (!Modifier.isFinal(localAnnotatedField.getModifiers())))
/*     */       {
/*     */ 
/*     */ 
/* 400 */         _property(str1).addField(localAnnotatedField, str2, bool1, bool2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void _addCreators()
/*     */   {
/* 409 */     AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
/*     */     
/* 411 */     if (localAnnotationIntrospector == null) {
/* 412 */       return;
/*     */     }
/* 414 */     for (Iterator localIterator = this._classDef.getConstructors().iterator(); localIterator.hasNext();) { localObject = (AnnotatedConstructor)localIterator.next();
/* 415 */       if (this._creatorProperties == null) {
/* 416 */         this._creatorProperties = new LinkedList();
/*     */       }
/* 418 */       i = 0; for (j = ((AnnotatedConstructor)localObject).getParameterCount(); i < j; i++) {
/* 419 */         localAnnotatedParameter = ((AnnotatedConstructor)localObject).getParameter(i);
/* 420 */         localPropertyName = localAnnotationIntrospector.findNameForDeserialization(localAnnotatedParameter);
/* 421 */         str = localPropertyName == null ? null : localPropertyName.getSimpleName();
/*     */         
/* 423 */         if (str != null)
/*     */         {
/* 425 */           localPOJOPropertyBuilder = _property(str);
/* 426 */           localPOJOPropertyBuilder.addCtor(localAnnotatedParameter, str, true, false);
/* 427 */           this._creatorProperties.add(localPOJOPropertyBuilder); } } }
/*     */     Object localObject;
/*     */     int i;
/*     */     int j;
/* 431 */     AnnotatedParameter localAnnotatedParameter; PropertyName localPropertyName; String str; POJOPropertyBuilder localPOJOPropertyBuilder; for (localIterator = this._classDef.getStaticMethods().iterator(); localIterator.hasNext();) { localObject = (AnnotatedMethod)localIterator.next();
/* 432 */       if (this._creatorProperties == null) {
/* 433 */         this._creatorProperties = new LinkedList();
/*     */       }
/* 435 */       i = 0; for (j = ((AnnotatedMethod)localObject).getParameterCount(); i < j; i++) {
/* 436 */         localAnnotatedParameter = ((AnnotatedMethod)localObject).getParameter(i);
/* 437 */         localPropertyName = localAnnotationIntrospector.findNameForDeserialization(localAnnotatedParameter);
/* 438 */         str = localPropertyName == null ? null : localPropertyName.getSimpleName();
/*     */         
/* 440 */         if (str != null)
/*     */         {
/* 442 */           localPOJOPropertyBuilder = _property(str);
/* 443 */           localPOJOPropertyBuilder.addCtor(localAnnotatedParameter, str, true, false);
/* 444 */           this._creatorProperties.add(localPOJOPropertyBuilder);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _addMethods()
/*     */   {
/* 455 */     AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
/*     */     
/* 457 */     for (AnnotatedMethod localAnnotatedMethod : this._classDef.memberMethods())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 463 */       int i = localAnnotatedMethod.getParameterCount();
/* 464 */       if (i == 0) {
/* 465 */         _addGetterMethod(localAnnotatedMethod, localAnnotationIntrospector);
/* 466 */       } else if (i == 1) {
/* 467 */         _addSetterMethod(localAnnotatedMethod, localAnnotationIntrospector);
/* 468 */       } else if ((i == 2) && 
/* 469 */         (localAnnotationIntrospector != null) && (localAnnotationIntrospector.hasAnySetterAnnotation(localAnnotatedMethod))) {
/* 470 */         if (this._anySetters == null) {
/* 471 */           this._anySetters = new LinkedList();
/*     */         }
/* 473 */         this._anySetters.add(localAnnotatedMethod);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void _addGetterMethod(AnnotatedMethod paramAnnotatedMethod, AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 482 */     if (paramAnnotationIntrospector != null) {
/* 483 */       if (paramAnnotationIntrospector.hasAnyGetterAnnotation(paramAnnotatedMethod)) {
/* 484 */         if (this._anyGetters == null) {
/* 485 */           this._anyGetters = new LinkedList();
/*     */         }
/* 487 */         this._anyGetters.add(paramAnnotatedMethod);
/* 488 */         return;
/*     */       }
/*     */       
/* 491 */       if (paramAnnotationIntrospector.hasAsValueAnnotation(paramAnnotatedMethod)) {
/* 492 */         if (this._jsonValueGetters == null) {
/* 493 */           this._jsonValueGetters = new LinkedList();
/*     */         }
/* 495 */         this._jsonValueGetters.add(paramAnnotatedMethod);
/* 496 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 502 */     PropertyName localPropertyName = paramAnnotationIntrospector == null ? null : paramAnnotationIntrospector.findNameForSerialization(paramAnnotatedMethod);
/* 503 */     Object localObject = localPropertyName == null ? null : localPropertyName.getSimpleName();
/* 504 */     String str; boolean bool1; if (localObject == null) {
/* 505 */       str = BeanUtil.okNameForRegularGetter(paramAnnotatedMethod, paramAnnotatedMethod.getName());
/* 506 */       if (str == null) {
/* 507 */         str = BeanUtil.okNameForIsGetter(paramAnnotatedMethod, paramAnnotatedMethod.getName());
/* 508 */         if (str == null) {
/* 509 */           return;
/*     */         }
/* 511 */         bool1 = this._visibilityChecker.isIsGetterVisible(paramAnnotatedMethod);
/*     */       } else {
/* 513 */         bool1 = this._visibilityChecker.isGetterVisible(paramAnnotatedMethod);
/*     */       }
/*     */     }
/*     */     else {
/* 517 */       str = BeanUtil.okNameForGetter(paramAnnotatedMethod);
/*     */       
/* 519 */       if (str == null) {
/* 520 */         str = paramAnnotatedMethod.getName();
/*     */       }
/* 522 */       if (((String)localObject).length() == 0) {
/* 523 */         localObject = str;
/*     */       }
/* 525 */       bool1 = true;
/*     */     }
/* 527 */     boolean bool2 = paramAnnotationIntrospector == null ? false : paramAnnotationIntrospector.hasIgnoreMarker(paramAnnotatedMethod);
/* 528 */     _property(str).addGetter(paramAnnotatedMethod, (String)localObject, bool1, bool2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void _addSetterMethod(AnnotatedMethod paramAnnotatedMethod, AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 535 */     PropertyName localPropertyName = paramAnnotationIntrospector == null ? null : paramAnnotationIntrospector.findNameForDeserialization(paramAnnotatedMethod);
/* 536 */     Object localObject = localPropertyName == null ? null : localPropertyName.getSimpleName();
/* 537 */     String str; boolean bool1; if (localObject == null) {
/* 538 */       str = BeanUtil.okNameForMutator(paramAnnotatedMethod, this._mutatorPrefix);
/* 539 */       if (str == null) {
/* 540 */         return;
/*     */       }
/* 542 */       bool1 = this._visibilityChecker.isSetterVisible(paramAnnotatedMethod);
/*     */     }
/*     */     else {
/* 545 */       str = BeanUtil.okNameForMutator(paramAnnotatedMethod, this._mutatorPrefix);
/*     */       
/* 547 */       if (str == null) {
/* 548 */         str = paramAnnotatedMethod.getName();
/*     */       }
/* 550 */       if (((String)localObject).length() == 0) {
/* 551 */         localObject = str;
/*     */       }
/* 553 */       bool1 = true;
/*     */     }
/* 555 */     boolean bool2 = paramAnnotationIntrospector == null ? false : paramAnnotationIntrospector.hasIgnoreMarker(paramAnnotatedMethod);
/* 556 */     _property(str).addSetter(paramAnnotatedMethod, (String)localObject, bool1, bool2);
/*     */   }
/*     */   
/*     */   protected void _addInjectables()
/*     */   {
/* 561 */     AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
/* 562 */     if (localAnnotationIntrospector == null) {
/* 563 */       return;
/*     */     }
/*     */     
/*     */ 
/* 567 */     for (Iterator localIterator = this._classDef.fields().iterator(); localIterator.hasNext();) { localObject = (AnnotatedField)localIterator.next();
/* 568 */       _doAddInjectable(localAnnotationIntrospector.findInjectableValueId((AnnotatedMember)localObject), (AnnotatedMember)localObject);
/*     */     }
/*     */     Object localObject;
/* 571 */     for (localIterator = this._classDef.memberMethods().iterator(); localIterator.hasNext();) { localObject = (AnnotatedMethod)localIterator.next();
/*     */       
/*     */ 
/*     */ 
/* 575 */       if (((AnnotatedMethod)localObject).getParameterCount() == 1)
/*     */       {
/*     */ 
/* 578 */         _doAddInjectable(localAnnotationIntrospector.findInjectableValueId((AnnotatedMember)localObject), (AnnotatedMember)localObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void _doAddInjectable(Object paramObject, AnnotatedMember paramAnnotatedMember) {
/* 584 */     if (paramObject == null) {
/* 585 */       return;
/*     */     }
/* 587 */     if (this._injectables == null) {
/* 588 */       this._injectables = new LinkedHashMap();
/*     */     }
/* 590 */     AnnotatedMember localAnnotatedMember = (AnnotatedMember)this._injectables.put(paramObject, paramAnnotatedMember);
/* 591 */     if (localAnnotatedMember != null) {
/* 592 */       String str = paramObject == null ? "[null]" : paramObject.getClass().getName();
/* 593 */       throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf(paramObject) + "' (of type " + str + ")");
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
/*     */   protected void _removeUnwantedProperties()
/*     */   {
/* 610 */     Iterator localIterator = this._properties.entrySet().iterator();
/* 611 */     boolean bool = !this._config.isEnabled(MapperFeature.INFER_PROPERTY_MUTATORS);
/*     */     
/* 613 */     while (localIterator.hasNext()) {
/* 614 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 615 */       POJOPropertyBuilder localPOJOPropertyBuilder = (POJOPropertyBuilder)localEntry.getValue();
/*     */       
/*     */ 
/* 618 */       if (!localPOJOPropertyBuilder.anyVisible()) {
/* 619 */         localIterator.remove();
/*     */       }
/*     */       else
/*     */       {
/* 623 */         if (localPOJOPropertyBuilder.anyIgnorals())
/*     */         {
/* 625 */           if (!localPOJOPropertyBuilder.isExplicitlyIncluded()) {
/* 626 */             localIterator.remove();
/* 627 */             _addIgnored(localPOJOPropertyBuilder.getName());
/* 628 */             continue;
/*     */           }
/*     */           
/* 631 */           localPOJOPropertyBuilder.removeIgnored();
/* 632 */           if ((!this._forSerialization) && (!localPOJOPropertyBuilder.couldDeserialize())) {
/* 633 */             _addIgnored(localPOJOPropertyBuilder.getName());
/*     */           }
/*     */         }
/*     */         
/* 637 */         localPOJOPropertyBuilder.removeNonVisible(bool);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void _addIgnored(String paramString) {
/* 643 */     if (!this._forSerialization) {
/* 644 */       if (this._ignoredPropertyNames == null) {
/* 645 */         this._ignoredPropertyNames = new HashSet();
/*     */       }
/* 647 */       this._ignoredPropertyNames.add(paramString);
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
/*     */   protected void _renameProperties()
/*     */   {
/* 660 */     Iterator localIterator = this._properties.entrySet().iterator();
/* 661 */     LinkedList localLinkedList = null;
/* 662 */     Object localObject; POJOPropertyBuilder localPOJOPropertyBuilder1; String str; while (localIterator.hasNext()) {
/* 663 */       localObject = (Map.Entry)localIterator.next();
/* 664 */       localPOJOPropertyBuilder1 = (POJOPropertyBuilder)((Map.Entry)localObject).getValue();
/* 665 */       str = localPOJOPropertyBuilder1.findNewName();
/* 666 */       if (str != null) {
/* 667 */         if (localLinkedList == null) {
/* 668 */           localLinkedList = new LinkedList();
/*     */         }
/* 670 */         localPOJOPropertyBuilder1 = localPOJOPropertyBuilder1.withName(str);
/* 671 */         localLinkedList.add(localPOJOPropertyBuilder1);
/* 672 */         localIterator.remove();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 677 */     if (localLinkedList != null) {
/* 678 */       for (localObject = localLinkedList.iterator(); ((Iterator)localObject).hasNext();) { localPOJOPropertyBuilder1 = (POJOPropertyBuilder)((Iterator)localObject).next();
/* 679 */         str = localPOJOPropertyBuilder1.getName();
/* 680 */         POJOPropertyBuilder localPOJOPropertyBuilder2 = (POJOPropertyBuilder)this._properties.get(str);
/* 681 */         if (localPOJOPropertyBuilder2 == null) {
/* 682 */           this._properties.put(str, localPOJOPropertyBuilder1);
/*     */         } else {
/* 684 */           localPOJOPropertyBuilder2.addAll(localPOJOPropertyBuilder1);
/*     */         }
/*     */         
/*     */ 
/* 688 */         if (this._creatorProperties != null) {
/* 689 */           for (int i = 0; i < this._creatorProperties.size(); i++) {
/* 690 */             if (((POJOPropertyBuilder)this._creatorProperties.get(i)).getInternalName() == localPOJOPropertyBuilder1.getInternalName()) {
/* 691 */               this._creatorProperties.set(i, localPOJOPropertyBuilder1);
/* 692 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void _renameUsing(PropertyNamingStrategy paramPropertyNamingStrategy)
/*     */   {
/* 702 */     POJOPropertyBuilder[] arrayOfPOJOPropertyBuilder1 = (POJOPropertyBuilder[])this._properties.values().toArray(new POJOPropertyBuilder[this._properties.size()]);
/* 703 */     this._properties.clear();
/* 704 */     for (POJOPropertyBuilder localPOJOPropertyBuilder1 : arrayOfPOJOPropertyBuilder1) {
/* 705 */       String str = localPOJOPropertyBuilder1.getName();
/* 706 */       if (this._forSerialization) {
/* 707 */         if (localPOJOPropertyBuilder1.hasGetter()) {
/* 708 */           str = paramPropertyNamingStrategy.nameForGetterMethod(this._config, localPOJOPropertyBuilder1.getGetter(), str);
/* 709 */         } else if (localPOJOPropertyBuilder1.hasField()) {
/* 710 */           str = paramPropertyNamingStrategy.nameForField(this._config, localPOJOPropertyBuilder1.getField(), str);
/*     */         }
/*     */       }
/* 713 */       else if (localPOJOPropertyBuilder1.hasSetter()) {
/* 714 */         str = paramPropertyNamingStrategy.nameForSetterMethod(this._config, localPOJOPropertyBuilder1.getSetter(), str);
/* 715 */       } else if (localPOJOPropertyBuilder1.hasConstructorParameter()) {
/* 716 */         str = paramPropertyNamingStrategy.nameForConstructorParameter(this._config, localPOJOPropertyBuilder1.getConstructorParameter(), str);
/* 717 */       } else if (localPOJOPropertyBuilder1.hasField()) {
/* 718 */         str = paramPropertyNamingStrategy.nameForField(this._config, localPOJOPropertyBuilder1.getField(), str);
/* 719 */       } else if (localPOJOPropertyBuilder1.hasGetter())
/*     */       {
/*     */ 
/*     */ 
/* 723 */         str = paramPropertyNamingStrategy.nameForGetterMethod(this._config, localPOJOPropertyBuilder1.getGetter(), str);
/*     */       }
/*     */       
/* 726 */       if (!str.equals(localPOJOPropertyBuilder1.getName())) {
/* 727 */         localPOJOPropertyBuilder1 = localPOJOPropertyBuilder1.withName(str);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 732 */       POJOPropertyBuilder localPOJOPropertyBuilder2 = (POJOPropertyBuilder)this._properties.get(str);
/* 733 */       if (localPOJOPropertyBuilder2 == null) {
/* 734 */         this._properties.put(str, localPOJOPropertyBuilder1);
/*     */       } else {
/* 736 */         localPOJOPropertyBuilder2.addAll(localPOJOPropertyBuilder1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _renameWithWrappers()
/*     */   {
/* 746 */     Iterator localIterator = this._properties.entrySet().iterator();
/* 747 */     LinkedList localLinkedList = null;
/* 748 */     Object localObject1; POJOPropertyBuilder localPOJOPropertyBuilder; Object localObject2; Object localObject3; while (localIterator.hasNext()) {
/* 749 */       localObject1 = (Map.Entry)localIterator.next();
/* 750 */       localPOJOPropertyBuilder = (POJOPropertyBuilder)((Map.Entry)localObject1).getValue();
/* 751 */       localObject2 = localPOJOPropertyBuilder.getPrimaryMember();
/* 752 */       if (localObject2 != null)
/*     */       {
/*     */ 
/* 755 */         localObject3 = this._annotationIntrospector.findWrapperName((Annotated)localObject2);
/* 756 */         if ((localObject3 != null) && (((PropertyName)localObject3).hasSimpleName()))
/*     */         {
/*     */ 
/* 759 */           String str = ((PropertyName)localObject3).getSimpleName();
/* 760 */           if (!str.equals(localPOJOPropertyBuilder.getName())) {
/* 761 */             if (localLinkedList == null) {
/* 762 */               localLinkedList = new LinkedList();
/*     */             }
/* 764 */             localPOJOPropertyBuilder = localPOJOPropertyBuilder.withName(str);
/* 765 */             localLinkedList.add(localPOJOPropertyBuilder);
/* 766 */             localIterator.remove();
/*     */           }
/*     */         }
/*     */       } }
/* 770 */     if (localLinkedList != null) {
/* 771 */       for (localObject1 = localLinkedList.iterator(); ((Iterator)localObject1).hasNext();) { localPOJOPropertyBuilder = (POJOPropertyBuilder)((Iterator)localObject1).next();
/* 772 */         localObject2 = localPOJOPropertyBuilder.getName();
/* 773 */         localObject3 = (POJOPropertyBuilder)this._properties.get(localObject2);
/* 774 */         if (localObject3 == null) {
/* 775 */           this._properties.put(localObject2, localPOJOPropertyBuilder);
/*     */         } else {
/* 777 */           ((POJOPropertyBuilder)localObject3).addAll(localPOJOPropertyBuilder);
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
/*     */   protected void reportProblem(String paramString)
/*     */   {
/* 791 */     throw new IllegalArgumentException("Problem with definition of " + this._classDef + ": " + paramString);
/*     */   }
/*     */   
/*     */   protected POJOPropertyBuilder _property(String paramString)
/*     */   {
/* 796 */     POJOPropertyBuilder localPOJOPropertyBuilder = (POJOPropertyBuilder)this._properties.get(paramString);
/* 797 */     if (localPOJOPropertyBuilder == null) {
/* 798 */       localPOJOPropertyBuilder = new POJOPropertyBuilder(paramString, this._annotationIntrospector, this._forSerialization);
/*     */       
/* 800 */       this._properties.put(paramString, localPOJOPropertyBuilder);
/*     */     }
/* 802 */     return localPOJOPropertyBuilder;
/*     */   }
/*     */   
/*     */   private PropertyNamingStrategy _findNamingStrategy()
/*     */   {
/* 807 */     Object localObject = this._annotationIntrospector == null ? null : this._annotationIntrospector.findNamingStrategy(this._classDef);
/*     */     
/* 809 */     if (localObject == null) {
/* 810 */       return this._config.getPropertyNamingStrategy();
/*     */     }
/* 812 */     if ((localObject instanceof PropertyNamingStrategy)) {
/* 813 */       return (PropertyNamingStrategy)localObject;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 818 */     if (!(localObject instanceof Class)) {
/* 819 */       throw new IllegalStateException("AnnotationIntrospector returned PropertyNamingStrategy definition of type " + localObject.getClass().getName() + "; expected type PropertyNamingStrategy or Class<PropertyNamingStrategy> instead");
/*     */     }
/*     */     
/* 822 */     Class localClass = (Class)localObject;
/* 823 */     if (!PropertyNamingStrategy.class.isAssignableFrom(localClass)) {
/* 824 */       throw new IllegalStateException("AnnotationIntrospector returned Class " + localClass.getName() + "; expected Class<PropertyNamingStrategy>");
/*     */     }
/*     */     
/* 827 */     HandlerInstantiator localHandlerInstantiator = this._config.getHandlerInstantiator();
/* 828 */     if (localHandlerInstantiator != null) {
/* 829 */       PropertyNamingStrategy localPropertyNamingStrategy = localHandlerInstantiator.namingStrategyInstance(this._config, this._classDef, localClass);
/* 830 */       if (localPropertyNamingStrategy != null) {
/* 831 */         return localPropertyNamingStrategy;
/*     */       }
/*     */     }
/* 834 */     return (PropertyNamingStrategy)ClassUtil.createInstance(localClass, this._config.canOverrideAccessModifiers());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/POJOPropertiesCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */