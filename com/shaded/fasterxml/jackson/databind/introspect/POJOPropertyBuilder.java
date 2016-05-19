/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POJOPropertyBuilder
/*     */   extends BeanPropertyDefinition
/*     */   implements Comparable<POJOPropertyBuilder>
/*     */ {
/*     */   protected final boolean _forSerialization;
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */   protected final String _name;
/*     */   protected final String _internalName;
/*     */   protected Linked<AnnotatedField> _fields;
/*     */   protected Linked<AnnotatedParameter> _ctorParameters;
/*     */   protected Linked<AnnotatedMethod> _getters;
/*     */   protected Linked<AnnotatedMethod> _setters;
/*     */   
/*     */   public POJOPropertyBuilder(String paramString, AnnotationIntrospector paramAnnotationIntrospector, boolean paramBoolean)
/*     */   {
/*  46 */     this._internalName = paramString;
/*  47 */     this._name = paramString;
/*  48 */     this._annotationIntrospector = paramAnnotationIntrospector;
/*  49 */     this._forSerialization = paramBoolean;
/*     */   }
/*     */   
/*     */   public POJOPropertyBuilder(POJOPropertyBuilder paramPOJOPropertyBuilder, String paramString)
/*     */   {
/*  54 */     this._internalName = paramPOJOPropertyBuilder._internalName;
/*  55 */     this._name = paramString;
/*  56 */     this._annotationIntrospector = paramPOJOPropertyBuilder._annotationIntrospector;
/*  57 */     this._fields = paramPOJOPropertyBuilder._fields;
/*  58 */     this._ctorParameters = paramPOJOPropertyBuilder._ctorParameters;
/*  59 */     this._getters = paramPOJOPropertyBuilder._getters;
/*  60 */     this._setters = paramPOJOPropertyBuilder._setters;
/*  61 */     this._forSerialization = paramPOJOPropertyBuilder._forSerialization;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public POJOPropertyBuilder withName(String paramString)
/*     */   {
/*  72 */     return new POJOPropertyBuilder(this, paramString);
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
/*     */   public int compareTo(POJOPropertyBuilder paramPOJOPropertyBuilder)
/*     */   {
/*  87 */     if (this._ctorParameters != null) {
/*  88 */       if (paramPOJOPropertyBuilder._ctorParameters == null) {
/*  89 */         return -1;
/*     */       }
/*  91 */     } else if (paramPOJOPropertyBuilder._ctorParameters != null) {
/*  92 */       return 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  97 */     return getName().compareTo(paramPOJOPropertyBuilder.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 107 */     return this._name;
/*     */   }
/*     */   
/* 110 */   public String getInternalName() { return this._internalName; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyName getWrapperName()
/*     */   {
/* 119 */     AnnotatedMember localAnnotatedMember = getPrimaryMember();
/* 120 */     return (localAnnotatedMember == null) || (this._annotationIntrospector == null) ? null : this._annotationIntrospector.findWrapperName(localAnnotatedMember);
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
/*     */   public boolean isExplicitlyIncluded()
/*     */   {
/* 134 */     return (_anyExplicitNames(this._fields)) || (_anyExplicitNames(this._getters)) || (_anyExplicitNames(this._setters)) || (_anyExplicitNames(this._ctorParameters));
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
/*     */   public boolean hasGetter()
/*     */   {
/* 148 */     return this._getters != null;
/*     */   }
/*     */   
/* 151 */   public boolean hasSetter() { return this._setters != null; }
/*     */   
/*     */   public boolean hasField() {
/* 154 */     return this._fields != null;
/*     */   }
/*     */   
/* 157 */   public boolean hasConstructorParameter() { return this._ctorParameters != null; }
/*     */   
/*     */   public boolean couldSerialize()
/*     */   {
/* 161 */     return (this._getters != null) || (this._fields != null);
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedMethod getGetter()
/*     */   {
/* 167 */     if (this._getters == null) {
/* 168 */       return null;
/*     */     }
/*     */     
/* 171 */     Object localObject = (AnnotatedMethod)this._getters.value;
/* 172 */     for (Linked localLinked = this._getters.next; 
/* 173 */         localLinked != null; localLinked = localLinked.next)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 178 */       AnnotatedMethod localAnnotatedMethod = (AnnotatedMethod)localLinked.value;
/* 179 */       Class localClass1 = ((AnnotatedMethod)localObject).getDeclaringClass();
/* 180 */       Class localClass2 = localAnnotatedMethod.getDeclaringClass();
/* 181 */       if (localClass1 != localClass2) {
/* 182 */         if (localClass1.isAssignableFrom(localClass2)) {
/* 183 */           localObject = localAnnotatedMethod;
/*     */ 
/*     */         }
/* 186 */         else if (localClass2.isAssignableFrom(localClass1)) {}
/*     */ 
/*     */       }
/*     */       else {
/* 190 */         throw new IllegalArgumentException("Conflicting getter definitions for property \"" + getName() + "\": " + ((AnnotatedMethod)localObject).getFullName() + " vs " + localAnnotatedMethod.getFullName());
/*     */       }
/*     */     }
/* 193 */     return (AnnotatedMethod)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedMethod getSetter()
/*     */   {
/* 199 */     if (this._setters == null) {
/* 200 */       return null;
/*     */     }
/*     */     
/* 203 */     Object localObject = (AnnotatedMethod)this._setters.value;
/* 204 */     for (Linked localLinked = this._setters.next; 
/* 205 */         localLinked != null; localLinked = localLinked.next)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 210 */       AnnotatedMethod localAnnotatedMethod = (AnnotatedMethod)localLinked.value;
/* 211 */       Class localClass1 = ((AnnotatedMethod)localObject).getDeclaringClass();
/* 212 */       Class localClass2 = localAnnotatedMethod.getDeclaringClass();
/* 213 */       if (localClass1 != localClass2) {
/* 214 */         if (localClass1.isAssignableFrom(localClass2)) {
/* 215 */           localObject = localAnnotatedMethod;
/*     */ 
/*     */         }
/* 218 */         else if (localClass2.isAssignableFrom(localClass1)) {}
/*     */ 
/*     */       }
/*     */       else {
/* 222 */         throw new IllegalArgumentException("Conflicting setter definitions for property \"" + getName() + "\": " + ((AnnotatedMethod)localObject).getFullName() + " vs " + localAnnotatedMethod.getFullName());
/*     */       }
/*     */     }
/* 225 */     return (AnnotatedMethod)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedField getField()
/*     */   {
/* 231 */     if (this._fields == null) {
/* 232 */       return null;
/*     */     }
/*     */     
/* 235 */     Object localObject = (AnnotatedField)this._fields.value;
/* 236 */     for (Linked localLinked = this._fields.next; 
/* 237 */         localLinked != null; localLinked = localLinked.next) {
/* 238 */       AnnotatedField localAnnotatedField = (AnnotatedField)localLinked.value;
/* 239 */       Class localClass1 = ((AnnotatedField)localObject).getDeclaringClass();
/* 240 */       Class localClass2 = localAnnotatedField.getDeclaringClass();
/* 241 */       if (localClass1 != localClass2) {
/* 242 */         if (localClass1.isAssignableFrom(localClass2)) {
/* 243 */           localObject = localAnnotatedField;
/*     */ 
/*     */         }
/* 246 */         else if (localClass2.isAssignableFrom(localClass1)) {}
/*     */ 
/*     */       }
/*     */       else {
/* 250 */         throw new IllegalArgumentException("Multiple fields representing property \"" + getName() + "\": " + ((AnnotatedField)localObject).getFullName() + " vs " + localAnnotatedField.getFullName());
/*     */       }
/*     */     }
/* 253 */     return (AnnotatedField)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedParameter getConstructorParameter()
/*     */   {
/* 259 */     if (this._ctorParameters == null) {
/* 260 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 270 */     Linked localLinked = this._ctorParameters;
/*     */     do {
/* 272 */       if ((((AnnotatedParameter)localLinked.value).getOwner() instanceof AnnotatedConstructor)) {
/* 273 */         return (AnnotatedParameter)localLinked.value;
/*     */       }
/* 275 */       localLinked = localLinked.next;
/* 276 */     } while (localLinked != null);
/* 277 */     return (AnnotatedParameter)this._ctorParameters.value;
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedMember getAccessor()
/*     */   {
/* 283 */     Object localObject = getGetter();
/* 284 */     if (localObject == null) {
/* 285 */       localObject = getField();
/*     */     }
/* 287 */     return (AnnotatedMember)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedMember getMutator()
/*     */   {
/* 293 */     Object localObject = getConstructorParameter();
/* 294 */     if (localObject == null) {
/* 295 */       localObject = getSetter();
/* 296 */       if (localObject == null) {
/* 297 */         localObject = getField();
/*     */       }
/*     */     }
/* 300 */     return (AnnotatedMember)localObject;
/*     */   }
/*     */   
/*     */   public AnnotatedMember getPrimaryMember()
/*     */   {
/* 305 */     if (this._forSerialization) {
/* 306 */       return getAccessor();
/*     */     }
/* 308 */     return getMutator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?>[] findViews()
/*     */   {
/* 319 */     (Class[])fromMemberAnnotations(new WithMember()
/*     */     {
/*     */       public Class<?>[] withMember(AnnotatedMember paramAnonymousAnnotatedMember) {
/* 322 */         return POJOPropertyBuilder.this._annotationIntrospector.findViews(paramAnonymousAnnotatedMember);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public AnnotationIntrospector.ReferenceProperty findReferenceType()
/*     */   {
/* 329 */     (AnnotationIntrospector.ReferenceProperty)fromMemberAnnotations(new WithMember()
/*     */     {
/*     */       public AnnotationIntrospector.ReferenceProperty withMember(AnnotatedMember paramAnonymousAnnotatedMember) {
/* 332 */         return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(paramAnonymousAnnotatedMember);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public boolean isTypeId()
/*     */   {
/* 339 */     Boolean localBoolean = (Boolean)fromMemberAnnotations(new WithMember()
/*     */     {
/*     */       public Boolean withMember(AnnotatedMember paramAnonymousAnnotatedMember) {
/* 342 */         return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(paramAnonymousAnnotatedMember);
/*     */       }
/* 344 */     });
/* 345 */     return (localBoolean != null) && (localBoolean.booleanValue());
/*     */   }
/*     */   
/*     */   public boolean isRequired()
/*     */   {
/* 350 */     Boolean localBoolean = (Boolean)fromMemberAnnotations(new WithMember()
/*     */     {
/*     */       public Boolean withMember(AnnotatedMember paramAnonymousAnnotatedMember) {
/* 353 */         return POJOPropertyBuilder.this._annotationIntrospector.hasRequiredMarker(paramAnonymousAnnotatedMember);
/*     */       }
/* 355 */     });
/* 356 */     return (localBoolean != null) && (localBoolean.booleanValue());
/*     */   }
/*     */   
/*     */   public ObjectIdInfo findObjectIdInfo()
/*     */   {
/* 361 */     (ObjectIdInfo)fromMemberAnnotations(new WithMember()
/*     */     {
/*     */       public ObjectIdInfo withMember(AnnotatedMember paramAnonymousAnnotatedMember) {
/* 364 */         ObjectIdInfo localObjectIdInfo = POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(paramAnonymousAnnotatedMember);
/* 365 */         if (localObjectIdInfo != null) {
/* 366 */           localObjectIdInfo = POJOPropertyBuilder.this._annotationIntrospector.findObjectReferenceInfo(paramAnonymousAnnotatedMember, localObjectIdInfo);
/*     */         }
/* 368 */         return localObjectIdInfo;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addField(AnnotatedField paramAnnotatedField, String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 380 */     this._fields = new Linked(paramAnnotatedField, this._fields, paramString, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   public void addCtor(AnnotatedParameter paramAnnotatedParameter, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 384 */     this._ctorParameters = new Linked(paramAnnotatedParameter, this._ctorParameters, paramString, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   public void addGetter(AnnotatedMethod paramAnnotatedMethod, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 388 */     this._getters = new Linked(paramAnnotatedMethod, this._getters, paramString, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   public void addSetter(AnnotatedMethod paramAnnotatedMethod, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 392 */     this._setters = new Linked(paramAnnotatedMethod, this._setters, paramString, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAll(POJOPropertyBuilder paramPOJOPropertyBuilder)
/*     */   {
/* 401 */     this._fields = merge(this._fields, paramPOJOPropertyBuilder._fields);
/* 402 */     this._ctorParameters = merge(this._ctorParameters, paramPOJOPropertyBuilder._ctorParameters);
/* 403 */     this._getters = merge(this._getters, paramPOJOPropertyBuilder._getters);
/* 404 */     this._setters = merge(this._setters, paramPOJOPropertyBuilder._setters);
/*     */   }
/*     */   
/*     */   private static <T> Linked<T> merge(Linked<T> paramLinked1, Linked<T> paramLinked2)
/*     */   {
/* 409 */     if (paramLinked1 == null) {
/* 410 */       return paramLinked2;
/*     */     }
/* 412 */     if (paramLinked2 == null) {
/* 413 */       return paramLinked1;
/*     */     }
/* 415 */     return paramLinked1.append(paramLinked2);
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
/*     */   public void removeIgnored()
/*     */   {
/* 430 */     this._fields = _removeIgnored(this._fields);
/* 431 */     this._getters = _removeIgnored(this._getters);
/* 432 */     this._setters = _removeIgnored(this._setters);
/* 433 */     this._ctorParameters = _removeIgnored(this._ctorParameters);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void removeNonVisible()
/*     */   {
/* 441 */     removeNonVisible(false);
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
/*     */   public void removeNonVisible(boolean paramBoolean)
/*     */   {
/* 457 */     this._getters = _removeNonVisible(this._getters);
/* 458 */     this._ctorParameters = _removeNonVisible(this._ctorParameters);
/*     */     
/* 460 */     if ((paramBoolean) || (this._getters == null)) {
/* 461 */       this._fields = _removeNonVisible(this._fields);
/* 462 */       this._setters = _removeNonVisible(this._setters);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trimByVisibility()
/*     */   {
/* 473 */     this._fields = _trimByVisibility(this._fields);
/* 474 */     this._getters = _trimByVisibility(this._getters);
/* 475 */     this._setters = _trimByVisibility(this._setters);
/* 476 */     this._ctorParameters = _trimByVisibility(this._ctorParameters);
/*     */   }
/*     */   
/*     */   public void mergeAnnotations(boolean paramBoolean)
/*     */   {
/*     */     AnnotationMap localAnnotationMap;
/* 482 */     if (paramBoolean) {
/* 483 */       if (this._getters != null) {
/* 484 */         localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._getters, this._fields, this._ctorParameters, this._setters });
/* 485 */         this._getters = this._getters.withValue(((AnnotatedMethod)this._getters.value).withAnnotations(localAnnotationMap));
/* 486 */       } else if (this._fields != null) {
/* 487 */         localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._fields, this._ctorParameters, this._setters });
/* 488 */         this._fields = this._fields.withValue(((AnnotatedField)this._fields.value).withAnnotations(localAnnotationMap));
/*     */       }
/*     */     }
/* 491 */     else if (this._ctorParameters != null) {
/* 492 */       localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._ctorParameters, this._setters, this._fields, this._getters });
/* 493 */       this._ctorParameters = this._ctorParameters.withValue(((AnnotatedParameter)this._ctorParameters.value).withAnnotations(localAnnotationMap));
/* 494 */     } else if (this._setters != null) {
/* 495 */       localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._setters, this._fields, this._getters });
/* 496 */       this._setters = this._setters.withValue(((AnnotatedMethod)this._setters.value).withAnnotations(localAnnotationMap));
/* 497 */     } else if (this._fields != null) {
/* 498 */       localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._fields, this._getters });
/* 499 */       this._fields = this._fields.withValue(((AnnotatedField)this._fields.value).withAnnotations(localAnnotationMap));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private AnnotationMap _mergeAnnotations(int paramInt, Linked<? extends AnnotatedMember>... paramVarArgs)
/*     */   {
/* 506 */     AnnotationMap localAnnotationMap = ((AnnotatedMember)paramVarArgs[paramInt].value).getAllAnnotations();
/* 507 */     paramInt++;
/* 508 */     for (; paramInt < paramVarArgs.length; paramInt++) {
/* 509 */       if (paramVarArgs[paramInt] != null) {
/* 510 */         return AnnotationMap.merge(localAnnotationMap, _mergeAnnotations(paramInt, paramVarArgs));
/*     */       }
/*     */     }
/* 513 */     return localAnnotationMap;
/*     */   }
/*     */   
/*     */   private <T> Linked<T> _removeIgnored(Linked<T> paramLinked)
/*     */   {
/* 518 */     if (paramLinked == null) {
/* 519 */       return paramLinked;
/*     */     }
/* 521 */     return paramLinked.withoutIgnored();
/*     */   }
/*     */   
/*     */   private <T> Linked<T> _removeNonVisible(Linked<T> paramLinked)
/*     */   {
/* 526 */     if (paramLinked == null) {
/* 527 */       return paramLinked;
/*     */     }
/* 529 */     return paramLinked.withoutNonVisible();
/*     */   }
/*     */   
/*     */   private <T> Linked<T> _trimByVisibility(Linked<T> paramLinked)
/*     */   {
/* 534 */     if (paramLinked == null) {
/* 535 */       return paramLinked;
/*     */     }
/* 537 */     return paramLinked.trimByVisibility();
/*     */   }
/*     */   
/*     */   private <T> boolean _anyExplicitNames(Linked<T> paramLinked)
/*     */   {
/* 548 */     for (; 
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 548 */         paramLinked != null; paramLinked = paramLinked.next) {
/* 549 */       if ((paramLinked.explicitName != null) && (paramLinked.explicitName.length() > 0)) {
/* 550 */         return true;
/*     */       }
/*     */     }
/* 553 */     return false;
/*     */   }
/*     */   
/*     */   public boolean anyVisible() {
/* 557 */     return (_anyVisible(this._fields)) || (_anyVisible(this._getters)) || (_anyVisible(this._setters)) || (_anyVisible(this._ctorParameters));
/*     */   }
/*     */   
/*     */   private <T> boolean _anyVisible(Linked<T> paramLinked)
/*     */   {
/* 566 */     for (; 
/*     */         
/*     */ 
/*     */ 
/* 566 */         paramLinked != null; paramLinked = paramLinked.next) {
/* 567 */       if (paramLinked.isVisible) {
/* 568 */         return true;
/*     */       }
/*     */     }
/* 571 */     return false;
/*     */   }
/*     */   
/*     */   public boolean anyIgnorals() {
/* 575 */     return (_anyIgnorals(this._fields)) || (_anyIgnorals(this._getters)) || (_anyIgnorals(this._setters)) || (_anyIgnorals(this._ctorParameters));
/*     */   }
/*     */   
/*     */   private <T> boolean _anyIgnorals(Linked<T> paramLinked)
/*     */   {
/* 584 */     for (; 
/*     */         
/*     */ 
/*     */ 
/* 584 */         paramLinked != null; paramLinked = paramLinked.next) {
/* 585 */       if (paramLinked.isMarkedIgnored) {
/* 586 */         return true;
/*     */       }
/*     */     }
/* 589 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String findNewName()
/*     */   {
/* 599 */     Linked localLinked = null;
/* 600 */     localLinked = findRenamed(this._fields, localLinked);
/* 601 */     localLinked = findRenamed(this._getters, localLinked);
/* 602 */     localLinked = findRenamed(this._setters, localLinked);
/* 603 */     localLinked = findRenamed(this._ctorParameters, localLinked);
/* 604 */     return localLinked == null ? null : localLinked.explicitName;
/*     */   }
/*     */   
/*     */   private Linked<? extends AnnotatedMember> findRenamed(Linked<? extends AnnotatedMember> paramLinked1, Linked<? extends AnnotatedMember> paramLinked2)
/*     */   {
/* 610 */     for (; 
/* 610 */         paramLinked1 != null; paramLinked1 = paramLinked1.next) {
/* 611 */       String str = paramLinked1.explicitName;
/* 612 */       if (str != null)
/*     */       {
/*     */ 
/*     */ 
/* 616 */         if (!str.equals(this._name))
/*     */         {
/*     */ 
/* 619 */           if (paramLinked2 == null) {
/* 620 */             paramLinked2 = paramLinked1;
/*     */ 
/*     */           }
/* 623 */           else if (!str.equals(paramLinked2.explicitName)) {
/* 624 */             throw new IllegalStateException("Conflicting property name definitions: '" + paramLinked2.explicitName + "' (for " + paramLinked2.value + ") vs '" + paramLinked1.explicitName + "' (for " + paramLinked1.value + ")");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 630 */     return paramLinked2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 637 */     StringBuilder localStringBuilder = new StringBuilder();
/* 638 */     localStringBuilder.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 644 */     localStringBuilder.append("]");
/* 645 */     return localStringBuilder.toString();
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
/*     */   protected <T> T fromMemberAnnotations(WithMember<T> paramWithMember)
/*     */   {
/* 660 */     Object localObject = null;
/* 661 */     if (this._annotationIntrospector != null) {
/* 662 */       if (this._forSerialization) {
/* 663 */         if (this._getters != null) {
/* 664 */           localObject = paramWithMember.withMember((AnnotatedMember)this._getters.value);
/*     */         }
/*     */       } else {
/* 667 */         if (this._ctorParameters != null) {
/* 668 */           localObject = paramWithMember.withMember((AnnotatedMember)this._ctorParameters.value);
/*     */         }
/* 670 */         if ((localObject == null) && (this._setters != null)) {
/* 671 */           localObject = paramWithMember.withMember((AnnotatedMember)this._setters.value);
/*     */         }
/*     */       }
/* 674 */       if ((localObject == null) && (this._fields != null)) {
/* 675 */         localObject = paramWithMember.withMember((AnnotatedMember)this._fields.value);
/*     */       }
/*     */     }
/* 678 */     return (T)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class Linked<T>
/*     */   {
/*     */     public final T value;
/*     */     
/*     */ 
/*     */ 
/*     */     public final Linked<T> next;
/*     */     
/*     */ 
/*     */ 
/*     */     public final String explicitName;
/*     */     
/*     */ 
/*     */ 
/*     */     public final boolean isVisible;
/*     */     
/*     */ 
/*     */ 
/*     */     public final boolean isMarkedIgnored;
/*     */     
/*     */ 
/*     */ 
/*     */     public Linked(T paramT, Linked<T> paramLinked, String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*     */     {
/* 708 */       this.value = paramT;
/* 709 */       this.next = paramLinked;
/*     */       
/* 711 */       if (paramString == null) {
/* 712 */         this.explicitName = null;
/*     */       } else {
/* 714 */         this.explicitName = (paramString.length() == 0 ? null : paramString);
/*     */       }
/* 716 */       this.isVisible = paramBoolean1;
/* 717 */       this.isMarkedIgnored = paramBoolean2;
/*     */     }
/*     */     
/*     */     public Linked<T> withValue(T paramT)
/*     */     {
/* 722 */       if (paramT == this.value) {
/* 723 */         return this;
/*     */       }
/* 725 */       return new Linked(paramT, this.next, this.explicitName, this.isVisible, this.isMarkedIgnored);
/*     */     }
/*     */     
/*     */     public Linked<T> withNext(Linked<T> paramLinked) {
/* 729 */       if (paramLinked == this.next) {
/* 730 */         return this;
/*     */       }
/* 732 */       return new Linked(this.value, paramLinked, this.explicitName, this.isVisible, this.isMarkedIgnored);
/*     */     }
/*     */     
/*     */     public Linked<T> withoutIgnored()
/*     */     {
/* 737 */       if (this.isMarkedIgnored) {
/* 738 */         return this.next == null ? null : this.next.withoutIgnored();
/*     */       }
/* 740 */       if (this.next != null) {
/* 741 */         Linked localLinked = this.next.withoutIgnored();
/* 742 */         if (localLinked != this.next) {
/* 743 */           return withNext(localLinked);
/*     */         }
/*     */       }
/* 746 */       return this;
/*     */     }
/*     */     
/*     */     public Linked<T> withoutNonVisible()
/*     */     {
/* 751 */       Linked localLinked = this.next == null ? null : this.next.withoutNonVisible();
/* 752 */       return this.isVisible ? withNext(localLinked) : localLinked;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Linked<T> append(Linked<T> paramLinked)
/*     */     {
/* 761 */       if (this.next == null) {
/* 762 */         return withNext(paramLinked);
/*     */       }
/* 764 */       return withNext(this.next.append(paramLinked));
/*     */     }
/*     */     
/*     */     public Linked<T> trimByVisibility()
/*     */     {
/* 769 */       if (this.next == null) {
/* 770 */         return this;
/*     */       }
/* 772 */       Linked localLinked = this.next.trimByVisibility();
/* 773 */       if (this.explicitName != null) {
/* 774 */         if (localLinked.explicitName == null) {
/* 775 */           return withNext(null);
/*     */         }
/*     */         
/* 778 */         return withNext(localLinked);
/*     */       }
/* 780 */       if (localLinked.explicitName != null) {
/* 781 */         return localLinked;
/*     */       }
/*     */       
/* 784 */       if (this.isVisible == localLinked.isVisible) {
/* 785 */         return withNext(localLinked);
/*     */       }
/* 787 */       return this.isVisible ? withNext(null) : localLinked;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 792 */       String str = this.value.toString() + "[visible=" + this.isVisible + "]";
/* 793 */       if (this.next != null) {
/* 794 */         str = str + ", " + this.next.toString();
/*     */       }
/* 796 */       return str;
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract interface WithMember<T>
/*     */   {
/*     */     public abstract T withMember(AnnotatedMember paramAnnotatedMember);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/POJOPropertyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */