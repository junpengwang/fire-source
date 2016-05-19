/*      */ package com.shaded.fasterxml.jackson.databind.introspect;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ public final class AnnotatedClass extends Annotated
/*      */ {
/*   15 */   private static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final Class<?> _class;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final List<Class<?>> _superTypes;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final AnnotationIntrospector _annotationIntrospector;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final ClassIntrospector.MixInResolver _mixInResolver;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final Class<?> _primaryMixIn;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected AnnotationMap _classAnnotations;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   71 */   protected boolean _creatorsResolved = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected AnnotatedConstructor _defaultConstructor;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected List<AnnotatedConstructor> _constructors;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected List<AnnotatedMethod> _creatorMethods;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected AnnotatedMethodMap _memberMethods;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected List<AnnotatedField> _fields;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private AnnotatedClass(Class<?> paramClass, List<Class<?>> paramList, AnnotationIntrospector paramAnnotationIntrospector, ClassIntrospector.MixInResolver paramMixInResolver, AnnotationMap paramAnnotationMap)
/*      */   {
/*  115 */     this._class = paramClass;
/*  116 */     this._superTypes = paramList;
/*  117 */     this._annotationIntrospector = paramAnnotationIntrospector;
/*  118 */     this._mixInResolver = paramMixInResolver;
/*  119 */     this._primaryMixIn = (this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(this._class));
/*      */     
/*  121 */     this._classAnnotations = paramAnnotationMap;
/*      */   }
/*      */   
/*      */   public AnnotatedClass withAnnotations(AnnotationMap paramAnnotationMap)
/*      */   {
/*  126 */     return new AnnotatedClass(this._class, this._superTypes, this._annotationIntrospector, this._mixInResolver, paramAnnotationMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static AnnotatedClass construct(Class<?> paramClass, AnnotationIntrospector paramAnnotationIntrospector, ClassIntrospector.MixInResolver paramMixInResolver)
/*      */   {
/*  138 */     return new AnnotatedClass(paramClass, com.shaded.fasterxml.jackson.databind.util.ClassUtil.findSuperTypes(paramClass, null), paramAnnotationIntrospector, paramMixInResolver, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static AnnotatedClass constructWithoutSuperTypes(Class<?> paramClass, AnnotationIntrospector paramAnnotationIntrospector, ClassIntrospector.MixInResolver paramMixInResolver)
/*      */   {
/*  150 */     return new AnnotatedClass(paramClass, java.util.Collections.emptyList(), paramAnnotationIntrospector, paramMixInResolver, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> getAnnotated()
/*      */   {
/*  161 */     return this._class;
/*      */   }
/*      */   
/*  164 */   public int getModifiers() { return this._class.getModifiers(); }
/*      */   
/*      */   public String getName() {
/*  167 */     return this._class.getName();
/*      */   }
/*      */   
/*      */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*      */   {
/*  172 */     if (this._classAnnotations == null) {
/*  173 */       resolveClassAnnotations();
/*      */     }
/*  175 */     return this._classAnnotations.get(paramClass);
/*      */   }
/*      */   
/*      */   public java.lang.reflect.Type getGenericType()
/*      */   {
/*  180 */     return this._class;
/*      */   }
/*      */   
/*      */   public Class<?> getRawType()
/*      */   {
/*  185 */     return this._class;
/*      */   }
/*      */   
/*      */   protected AnnotationMap getAllAnnotations()
/*      */   {
/*  190 */     if (this._classAnnotations == null) {
/*  191 */       resolveClassAnnotations();
/*      */     }
/*  193 */     return this._classAnnotations;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public com.shaded.fasterxml.jackson.databind.util.Annotations getAnnotations()
/*      */   {
/*  203 */     if (this._classAnnotations == null) {
/*  204 */       resolveClassAnnotations();
/*      */     }
/*  206 */     return this._classAnnotations;
/*      */   }
/*      */   
/*      */   public boolean hasAnnotations() {
/*  210 */     if (this._classAnnotations == null) {
/*  211 */       resolveClassAnnotations();
/*      */     }
/*  213 */     return this._classAnnotations.size() > 0;
/*      */   }
/*      */   
/*      */   public AnnotatedConstructor getDefaultConstructor()
/*      */   {
/*  218 */     if (!this._creatorsResolved) {
/*  219 */       resolveCreators();
/*      */     }
/*  221 */     return this._defaultConstructor;
/*      */   }
/*      */   
/*      */   public List<AnnotatedConstructor> getConstructors()
/*      */   {
/*  226 */     if (!this._creatorsResolved) {
/*  227 */       resolveCreators();
/*      */     }
/*  229 */     return this._constructors;
/*      */   }
/*      */   
/*      */   public List<AnnotatedMethod> getStaticMethods()
/*      */   {
/*  234 */     if (!this._creatorsResolved) {
/*  235 */       resolveCreators();
/*      */     }
/*  237 */     return this._creatorMethods;
/*      */   }
/*      */   
/*      */   public Iterable<AnnotatedMethod> memberMethods()
/*      */   {
/*  242 */     if (this._memberMethods == null) {
/*  243 */       resolveMemberMethods();
/*      */     }
/*  245 */     return this._memberMethods;
/*      */   }
/*      */   
/*      */   public int getMemberMethodCount()
/*      */   {
/*  250 */     if (this._memberMethods == null) {
/*  251 */       resolveMemberMethods();
/*      */     }
/*  253 */     return this._memberMethods.size();
/*      */   }
/*      */   
/*      */   public AnnotatedMethod findMethod(String paramString, Class<?>[] paramArrayOfClass)
/*      */   {
/*  258 */     if (this._memberMethods == null) {
/*  259 */       resolveMemberMethods();
/*      */     }
/*  261 */     return this._memberMethods.find(paramString, paramArrayOfClass);
/*      */   }
/*      */   
/*      */   public int getFieldCount() {
/*  265 */     if (this._fields == null) {
/*  266 */       resolveFields();
/*      */     }
/*  268 */     return this._fields.size();
/*      */   }
/*      */   
/*      */   public Iterable<AnnotatedField> fields()
/*      */   {
/*  273 */     if (this._fields == null) {
/*  274 */       resolveFields();
/*      */     }
/*  276 */     return this._fields;
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
/*      */   private void resolveClassAnnotations()
/*      */   {
/*  292 */     this._classAnnotations = new AnnotationMap();
/*      */     
/*  294 */     if (this._annotationIntrospector != null)
/*      */     {
/*  296 */       if (this._primaryMixIn != null) {
/*  297 */         _addClassMixIns(this._classAnnotations, this._class, this._primaryMixIn);
/*      */       }
/*      */       
/*  300 */       _addAnnotationsIfNotPresent(this._classAnnotations, this._class.getDeclaredAnnotations());
/*      */       
/*      */ 
/*  303 */       for (Class localClass : this._superTypes)
/*      */       {
/*  305 */         _addClassMixIns(this._classAnnotations, localClass);
/*  306 */         _addAnnotationsIfNotPresent(this._classAnnotations, localClass.getDeclaredAnnotations());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  315 */       _addClassMixIns(this._classAnnotations, Object.class);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void resolveCreators()
/*      */   {
/*  326 */     ArrayList localArrayList1 = null;
/*  327 */     Constructor[] arrayOfConstructor1 = this._class.getDeclaredConstructors();
/*  328 */     for (Constructor localConstructor : arrayOfConstructor1) {
/*  329 */       if (localConstructor.getParameterTypes().length == 0) {
/*  330 */         this._defaultConstructor = _constructConstructor(localConstructor, true);
/*      */       } else {
/*  332 */         if (localArrayList1 == null) {
/*  333 */           localArrayList1 = new ArrayList(Math.max(10, arrayOfConstructor1.length));
/*      */         }
/*  335 */         localArrayList1.add(_constructConstructor(localConstructor, false));
/*      */       }
/*      */     }
/*  338 */     if (localArrayList1 == null) {
/*  339 */       this._constructors = java.util.Collections.emptyList();
/*      */     } else {
/*  341 */       this._constructors = localArrayList1;
/*      */     }
/*      */     
/*  344 */     if ((this._primaryMixIn != null) && (
/*  345 */       (this._defaultConstructor != null) || (!this._constructors.isEmpty()))) {
/*  346 */       _addConstructorMixIns(this._primaryMixIn);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  355 */     if (this._annotationIntrospector != null) {
/*  356 */       if ((this._defaultConstructor != null) && 
/*  357 */         (this._annotationIntrospector.hasIgnoreMarker(this._defaultConstructor))) {
/*  358 */         this._defaultConstructor = null;
/*      */       }
/*      */       
/*  361 */       if (this._constructors != null)
/*      */       {
/*  363 */         int i = this._constructors.size(); for (;;) { i--; if (i < 0) break;
/*  364 */           if (this._annotationIntrospector.hasIgnoreMarker((AnnotatedMember)this._constructors.get(i))) {
/*  365 */             this._constructors.remove(i);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  370 */     ArrayList localArrayList2 = null;
/*      */     
/*      */ 
/*  373 */     for (Method localMethod : this._class.getDeclaredMethods())
/*  374 */       if (java.lang.reflect.Modifier.isStatic(localMethod.getModifiers()))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  379 */         if (localArrayList2 == null) {
/*  380 */           localArrayList2 = new ArrayList(8);
/*      */         }
/*  382 */         localArrayList2.add(_constructCreatorMethod(localMethod));
/*      */       }
/*  384 */     if (localArrayList2 == null) {
/*  385 */       this._creatorMethods = java.util.Collections.emptyList();
/*      */     } else {
/*  387 */       this._creatorMethods = localArrayList2;
/*      */       
/*  389 */       if (this._primaryMixIn != null) {
/*  390 */         _addFactoryMixIns(this._primaryMixIn);
/*      */       }
/*      */       
/*  393 */       if (this._annotationIntrospector != null)
/*      */       {
/*  395 */         int k = this._creatorMethods.size(); for (;;) { k--; if (k < 0) break;
/*  396 */           if (this._annotationIntrospector.hasIgnoreMarker((AnnotatedMember)this._creatorMethods.get(k))) {
/*  397 */             this._creatorMethods.remove(k);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  402 */     this._creatorsResolved = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void resolveMemberMethods()
/*      */   {
/*  413 */     this._memberMethods = new AnnotatedMethodMap();
/*  414 */     AnnotatedMethodMap localAnnotatedMethodMap = new AnnotatedMethodMap();
/*      */     
/*  416 */     _addMemberMethods(this._class, this._memberMethods, this._primaryMixIn, localAnnotatedMethodMap);
/*      */     
/*      */ 
/*  419 */     for (Object localObject1 = this._superTypes.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Class)((Iterator)localObject1).next();
/*  420 */       localObject3 = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor((Class)localObject2);
/*  421 */       _addMemberMethods((Class)localObject2, this._memberMethods, (Class)localObject3, localAnnotatedMethodMap); }
/*      */     Object localObject2;
/*      */     Object localObject3;
/*  424 */     if (this._mixInResolver != null) {
/*  425 */       localObject1 = this._mixInResolver.findMixInClassFor(Object.class);
/*  426 */       if (localObject1 != null) {
/*  427 */         _addMethodMixIns(this._class, this._memberMethods, (Class)localObject1, localAnnotatedMethodMap);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  437 */     if ((this._annotationIntrospector != null) && 
/*  438 */       (!localAnnotatedMethodMap.isEmpty())) {
/*  439 */       localObject1 = localAnnotatedMethodMap.iterator();
/*  440 */       while (((Iterator)localObject1).hasNext()) {
/*  441 */         localObject2 = (AnnotatedMethod)((Iterator)localObject1).next();
/*      */         try {
/*  443 */           localObject3 = Object.class.getDeclaredMethod(((AnnotatedMethod)localObject2).getName(), ((AnnotatedMethod)localObject2).getRawParameterTypes());
/*  444 */           if (localObject3 != null) {
/*  445 */             AnnotatedMethod localAnnotatedMethod = _constructMethod((Method)localObject3);
/*  446 */             _addMixOvers(((AnnotatedMethod)localObject2).getAnnotated(), localAnnotatedMethod, false);
/*  447 */             this._memberMethods.add(localAnnotatedMethod);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void resolveFields()
/*      */   {
/*  462 */     Map localMap = _findFields(this._class, null);
/*  463 */     if ((localMap == null) || (localMap.size() == 0)) {
/*  464 */       this._fields = java.util.Collections.emptyList();
/*      */     } else {
/*  466 */       this._fields = new ArrayList(localMap.size());
/*  467 */       this._fields.addAll(localMap.values());
/*      */     }
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
/*      */   protected void _addClassMixIns(AnnotationMap paramAnnotationMap, Class<?> paramClass)
/*      */   {
/*  485 */     if (this._mixInResolver != null) {
/*  486 */       _addClassMixIns(paramAnnotationMap, paramClass, this._mixInResolver.findMixInClassFor(paramClass));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void _addClassMixIns(AnnotationMap paramAnnotationMap, Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  493 */     if (paramClass2 == null) {
/*  494 */       return;
/*      */     }
/*      */     
/*  497 */     _addAnnotationsIfNotPresent(paramAnnotationMap, paramClass2.getDeclaredAnnotations());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  506 */     for (Class localClass : com.shaded.fasterxml.jackson.databind.util.ClassUtil.findSuperTypes(paramClass2, paramClass1)) {
/*  507 */       _addAnnotationsIfNotPresent(paramAnnotationMap, localClass.getDeclaredAnnotations());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _addConstructorMixIns(Class<?> paramClass)
/*      */   {
/*  519 */     MemberKey[] arrayOfMemberKey = null;
/*  520 */     int i = this._constructors == null ? 0 : this._constructors.size();
/*  521 */     for (Constructor localConstructor : paramClass.getDeclaredConstructors()) {
/*  522 */       if (localConstructor.getParameterTypes().length == 0) {
/*  523 */         if (this._defaultConstructor != null) {
/*  524 */           _addMixOvers(localConstructor, this._defaultConstructor, false);
/*      */         }
/*      */       } else {
/*  527 */         if (arrayOfMemberKey == null) {
/*  528 */           arrayOfMemberKey = new MemberKey[i];
/*  529 */           for (int m = 0; m < i; m++) {
/*  530 */             arrayOfMemberKey[m] = new MemberKey(((AnnotatedConstructor)this._constructors.get(m)).getAnnotated());
/*      */           }
/*      */         }
/*  533 */         MemberKey localMemberKey = new MemberKey(localConstructor);
/*      */         
/*  535 */         for (int n = 0; n < i; n++) {
/*  536 */           if (localMemberKey.equals(arrayOfMemberKey[n]))
/*      */           {
/*      */ 
/*  539 */             _addMixOvers(localConstructor, (AnnotatedConstructor)this._constructors.get(n), true);
/*  540 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void _addFactoryMixIns(Class<?> paramClass) {
/*  548 */     MemberKey[] arrayOfMemberKey = null;
/*  549 */     int i = this._creatorMethods.size();
/*      */     
/*  551 */     for (Method localMethod : paramClass.getDeclaredMethods()) {
/*  552 */       if (java.lang.reflect.Modifier.isStatic(localMethod.getModifiers()))
/*      */       {
/*      */ 
/*  555 */         if (localMethod.getParameterTypes().length != 0)
/*      */         {
/*      */ 
/*  558 */           if (arrayOfMemberKey == null) {
/*  559 */             arrayOfMemberKey = new MemberKey[i];
/*  560 */             for (int m = 0; m < i; m++) {
/*  561 */               arrayOfMemberKey[m] = new MemberKey(((AnnotatedMethod)this._creatorMethods.get(m)).getAnnotated());
/*      */             }
/*      */           }
/*  564 */           MemberKey localMemberKey = new MemberKey(localMethod);
/*  565 */           for (int n = 0; n < i; n++) {
/*  566 */             if (localMemberKey.equals(arrayOfMemberKey[n]))
/*      */             {
/*      */ 
/*  569 */               _addMixOvers(localMethod, (AnnotatedMethod)this._creatorMethods.get(n), true);
/*  570 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _addMemberMethods(Class<?> paramClass1, AnnotatedMethodMap paramAnnotatedMethodMap1, Class<?> paramClass2, AnnotatedMethodMap paramAnnotatedMethodMap2)
/*      */   {
/*  585 */     if (paramClass2 != null) {
/*  586 */       _addMethodMixIns(paramClass1, paramAnnotatedMethodMap1, paramClass2, paramAnnotatedMethodMap2);
/*      */     }
/*  588 */     if (paramClass1 == null) {
/*  589 */       return;
/*      */     }
/*      */     
/*      */ 
/*  593 */     for (Method localMethod : paramClass1.getDeclaredMethods()) {
/*  594 */       if (_isIncludableMemberMethod(localMethod))
/*      */       {
/*      */ 
/*  597 */         AnnotatedMethod localAnnotatedMethod1 = paramAnnotatedMethodMap1.find(localMethod);
/*  598 */         if (localAnnotatedMethod1 == null) {
/*  599 */           AnnotatedMethod localAnnotatedMethod2 = _constructMethod(localMethod);
/*  600 */           paramAnnotatedMethodMap1.add(localAnnotatedMethod2);
/*      */           
/*  602 */           localAnnotatedMethod1 = paramAnnotatedMethodMap2.remove(localMethod);
/*  603 */           if (localAnnotatedMethod1 != null) {
/*  604 */             _addMixOvers(localAnnotatedMethod1.getAnnotated(), localAnnotatedMethod2, false);
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/*  610 */           _addMixUnders(localMethod, localAnnotatedMethod1);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  619 */           if ((localAnnotatedMethod1.getDeclaringClass().isInterface()) && (!localMethod.getDeclaringClass().isInterface())) {
/*  620 */             paramAnnotatedMethodMap1.add(localAnnotatedMethod1.withMethod(localMethod));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void _addMethodMixIns(Class<?> paramClass1, AnnotatedMethodMap paramAnnotatedMethodMap1, Class<?> paramClass2, AnnotatedMethodMap paramAnnotatedMethodMap2)
/*      */   {
/*  629 */     ArrayList localArrayList = new ArrayList();
/*  630 */     localArrayList.add(paramClass2);
/*  631 */     com.shaded.fasterxml.jackson.databind.util.ClassUtil.findSuperTypes(paramClass2, paramClass1, localArrayList);
/*  632 */     for (Class localClass : localArrayList) {
/*  633 */       for (Method localMethod : localClass.getDeclaredMethods()) {
/*  634 */         if (_isIncludableMemberMethod(localMethod))
/*      */         {
/*      */ 
/*  637 */           AnnotatedMethod localAnnotatedMethod = paramAnnotatedMethodMap1.find(localMethod);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  642 */           if (localAnnotatedMethod != null) {
/*  643 */             _addMixUnders(localMethod, localAnnotatedMethod);
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  649 */             paramAnnotatedMethodMap2.add(_constructMethod(localMethod));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
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
/*      */   protected Map<String, AnnotatedField> _findFields(Class<?> paramClass, Map<String, AnnotatedField> paramMap)
/*      */   {
/*  668 */     Class localClass = paramClass.getSuperclass();
/*  669 */     if (localClass != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  674 */       paramMap = _findFields(localClass, paramMap);
/*  675 */       for (Field localField : paramClass.getDeclaredFields())
/*      */       {
/*  677 */         if (_isIncludableField(localField))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  685 */           if (paramMap == null) {
/*  686 */             paramMap = new java.util.LinkedHashMap();
/*      */           }
/*  688 */           paramMap.put(localField.getName(), _constructField(localField));
/*      */         }
/*      */       }
/*  691 */       if (this._mixInResolver != null) {
/*  692 */         ??? = this._mixInResolver.findMixInClassFor(paramClass);
/*  693 */         if (??? != null) {
/*  694 */           _addFieldMixIns(localClass, (Class)???, paramMap);
/*      */         }
/*      */       }
/*      */     }
/*  698 */     return paramMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _addFieldMixIns(Class<?> paramClass1, Class<?> paramClass2, Map<String, AnnotatedField> paramMap)
/*      */   {
/*  709 */     ArrayList localArrayList = new ArrayList();
/*  710 */     localArrayList.add(paramClass2);
/*  711 */     com.shaded.fasterxml.jackson.databind.util.ClassUtil.findSuperTypes(paramClass2, paramClass1, localArrayList);
/*  712 */     for (Class localClass : localArrayList) {
/*  713 */       for (Field localField : localClass.getDeclaredFields())
/*      */       {
/*  715 */         if (_isIncludableField(localField))
/*      */         {
/*      */ 
/*  718 */           String str = localField.getName();
/*      */           
/*  720 */           AnnotatedField localAnnotatedField = (AnnotatedField)paramMap.get(str);
/*  721 */           if (localAnnotatedField != null) {
/*  722 */             _addOrOverrideAnnotations(localAnnotatedField, localField.getDeclaredAnnotations());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
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
/*      */   protected AnnotatedMethod _constructMethod(Method paramMethod)
/*      */   {
/*  740 */     if (this._annotationIntrospector == null) {
/*  741 */       return new AnnotatedMethod(paramMethod, _emptyAnnotationMap(), null);
/*      */     }
/*  743 */     return new AnnotatedMethod(paramMethod, _collectRelevantAnnotations(paramMethod.getDeclaredAnnotations()), null);
/*      */   }
/*      */   
/*      */   protected AnnotatedConstructor _constructConstructor(Constructor<?> paramConstructor, boolean paramBoolean)
/*      */   {
/*  748 */     if (this._annotationIntrospector == null) {
/*  749 */       return new AnnotatedConstructor(paramConstructor, _emptyAnnotationMap(), _emptyAnnotationMaps(paramConstructor.getParameterTypes().length));
/*      */     }
/*  751 */     if (paramBoolean) {
/*  752 */       return new AnnotatedConstructor(paramConstructor, _collectRelevantAnnotations(paramConstructor.getDeclaredAnnotations()), null);
/*      */     }
/*  754 */     Annotation[][] arrayOfAnnotation1 = paramConstructor.getParameterAnnotations();
/*  755 */     int i = paramConstructor.getParameterTypes().length;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  761 */     AnnotationMap[] arrayOfAnnotationMap = null;
/*  762 */     if (i != arrayOfAnnotation1.length)
/*      */     {
/*      */ 
/*      */ 
/*  766 */       Class localClass = paramConstructor.getDeclaringClass();
/*      */       Annotation[][] arrayOfAnnotation2;
/*  768 */       if ((localClass.isEnum()) && (i == arrayOfAnnotation1.length + 2)) {
/*  769 */         arrayOfAnnotation2 = arrayOfAnnotation1;
/*  770 */         arrayOfAnnotation1 = new Annotation[arrayOfAnnotation2.length + 2][];
/*  771 */         System.arraycopy(arrayOfAnnotation2, 0, arrayOfAnnotation1, 2, arrayOfAnnotation2.length);
/*  772 */         arrayOfAnnotationMap = _collectRelevantAnnotations(arrayOfAnnotation1);
/*  773 */       } else if (localClass.isMemberClass())
/*      */       {
/*  775 */         if (i == arrayOfAnnotation1.length + 1)
/*      */         {
/*  777 */           arrayOfAnnotation2 = arrayOfAnnotation1;
/*  778 */           arrayOfAnnotation1 = new Annotation[arrayOfAnnotation2.length + 1][];
/*  779 */           System.arraycopy(arrayOfAnnotation2, 0, arrayOfAnnotation1, 1, arrayOfAnnotation2.length);
/*  780 */           arrayOfAnnotationMap = _collectRelevantAnnotations(arrayOfAnnotation1);
/*      */         }
/*      */       }
/*  783 */       if (arrayOfAnnotationMap == null) {
/*  784 */         throw new IllegalStateException("Internal error: constructor for " + paramConstructor.getDeclaringClass().getName() + " has mismatch: " + i + " parameters; " + arrayOfAnnotation1.length + " sets of annotations");
/*      */       }
/*      */     }
/*      */     else {
/*  788 */       arrayOfAnnotationMap = _collectRelevantAnnotations(arrayOfAnnotation1);
/*      */     }
/*  790 */     return new AnnotatedConstructor(paramConstructor, _collectRelevantAnnotations(paramConstructor.getDeclaredAnnotations()), arrayOfAnnotationMap);
/*      */   }
/*      */   
/*      */ 
/*      */   protected AnnotatedMethod _constructCreatorMethod(Method paramMethod)
/*      */   {
/*  796 */     if (this._annotationIntrospector == null) {
/*  797 */       return new AnnotatedMethod(paramMethod, _emptyAnnotationMap(), _emptyAnnotationMaps(paramMethod.getParameterTypes().length));
/*      */     }
/*  799 */     return new AnnotatedMethod(paramMethod, _collectRelevantAnnotations(paramMethod.getDeclaredAnnotations()), _collectRelevantAnnotations(paramMethod.getParameterAnnotations()));
/*      */   }
/*      */   
/*      */ 
/*      */   protected AnnotatedField _constructField(Field paramField)
/*      */   {
/*  805 */     if (this._annotationIntrospector == null) {
/*  806 */       return new AnnotatedField(paramField, _emptyAnnotationMap());
/*      */     }
/*  808 */     return new AnnotatedField(paramField, _collectRelevantAnnotations(paramField.getDeclaredAnnotations()));
/*      */   }
/*      */   
/*      */   private AnnotationMap _emptyAnnotationMap() {
/*  812 */     return new AnnotationMap();
/*      */   }
/*      */   
/*      */   private AnnotationMap[] _emptyAnnotationMaps(int paramInt) {
/*  816 */     if (paramInt == 0) {
/*  817 */       return NO_ANNOTATION_MAPS;
/*      */     }
/*  819 */     AnnotationMap[] arrayOfAnnotationMap = new AnnotationMap[paramInt];
/*  820 */     for (int i = 0; i < paramInt; i++) {
/*  821 */       arrayOfAnnotationMap[i] = _emptyAnnotationMap();
/*      */     }
/*  823 */     return arrayOfAnnotationMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _isIncludableMemberMethod(Method paramMethod)
/*      */   {
/*  834 */     if (java.lang.reflect.Modifier.isStatic(paramMethod.getModifiers())) {
/*  835 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  841 */     if ((paramMethod.isSynthetic()) || (paramMethod.isBridge())) {
/*  842 */       return false;
/*      */     }
/*      */     
/*  845 */     int i = paramMethod.getParameterTypes().length;
/*  846 */     return i <= 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean _isIncludableField(Field paramField)
/*      */   {
/*  854 */     if (paramField.isSynthetic()) {
/*  855 */       return false;
/*      */     }
/*      */     
/*  858 */     int i = paramField.getModifiers();
/*  859 */     if ((java.lang.reflect.Modifier.isStatic(i)) || (java.lang.reflect.Modifier.isTransient(i))) {
/*  860 */       return false;
/*      */     }
/*  862 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected AnnotationMap[] _collectRelevantAnnotations(Annotation[][] paramArrayOfAnnotation)
/*      */   {
/*  873 */     int i = paramArrayOfAnnotation.length;
/*  874 */     AnnotationMap[] arrayOfAnnotationMap = new AnnotationMap[i];
/*  875 */     for (int j = 0; j < i; j++) {
/*  876 */       arrayOfAnnotationMap[j] = _collectRelevantAnnotations(paramArrayOfAnnotation[j]);
/*      */     }
/*  878 */     return arrayOfAnnotationMap;
/*      */   }
/*      */   
/*      */   protected AnnotationMap _collectRelevantAnnotations(Annotation[] paramArrayOfAnnotation)
/*      */   {
/*  883 */     AnnotationMap localAnnotationMap = new AnnotationMap();
/*  884 */     _addAnnotationsIfNotPresent(localAnnotationMap, paramArrayOfAnnotation);
/*  885 */     return localAnnotationMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _addAnnotationsIfNotPresent(AnnotationMap paramAnnotationMap, Annotation[] paramArrayOfAnnotation)
/*      */   {
/*  894 */     if (paramArrayOfAnnotation != null) {
/*  895 */       java.util.LinkedList localLinkedList = null;
/*  896 */       for (Annotation localAnnotation : paramArrayOfAnnotation) {
/*  897 */         if (_isAnnotationBundle(localAnnotation)) {
/*  898 */           if (localLinkedList == null) {
/*  899 */             localLinkedList = new java.util.LinkedList();
/*      */           }
/*  901 */           localLinkedList.add(localAnnotation.annotationType().getDeclaredAnnotations());
/*      */         } else {
/*  903 */           paramAnnotationMap.addIfNotPresent(localAnnotation);
/*      */         }
/*      */       }
/*  906 */       if (localLinkedList != null) {
/*  907 */         for (??? = localLinkedList.iterator(); ((Iterator)???).hasNext();) { Annotation[] arrayOfAnnotation = (Annotation[])((Iterator)???).next();
/*  908 */           _addAnnotationsIfNotPresent(paramAnnotationMap, arrayOfAnnotation);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void _addAnnotationsIfNotPresent(AnnotatedMember paramAnnotatedMember, Annotation[] paramArrayOfAnnotation)
/*      */   {
/*  916 */     if (paramArrayOfAnnotation != null) {
/*  917 */       java.util.LinkedList localLinkedList = null;
/*  918 */       for (Annotation localAnnotation : paramArrayOfAnnotation) {
/*  919 */         if (_isAnnotationBundle(localAnnotation)) {
/*  920 */           if (localLinkedList == null) {
/*  921 */             localLinkedList = new java.util.LinkedList();
/*      */           }
/*  923 */           localLinkedList.add(localAnnotation.annotationType().getDeclaredAnnotations());
/*      */         } else {
/*  925 */           paramAnnotatedMember.addIfNotPresent(localAnnotation);
/*      */         }
/*      */       }
/*  928 */       if (localLinkedList != null) {
/*  929 */         for (??? = localLinkedList.iterator(); ((Iterator)???).hasNext();) { Annotation[] arrayOfAnnotation = (Annotation[])((Iterator)???).next();
/*  930 */           _addAnnotationsIfNotPresent(paramAnnotatedMember, arrayOfAnnotation);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void _addOrOverrideAnnotations(AnnotatedMember paramAnnotatedMember, Annotation[] paramArrayOfAnnotation)
/*      */   {
/*  938 */     if (paramArrayOfAnnotation != null) {
/*  939 */       java.util.LinkedList localLinkedList = null;
/*  940 */       for (Annotation localAnnotation : paramArrayOfAnnotation) {
/*  941 */         if (_isAnnotationBundle(localAnnotation)) {
/*  942 */           if (localLinkedList == null) {
/*  943 */             localLinkedList = new java.util.LinkedList();
/*      */           }
/*  945 */           localLinkedList.add(localAnnotation.annotationType().getDeclaredAnnotations());
/*      */         } else {
/*  947 */           paramAnnotatedMember.addOrOverride(localAnnotation);
/*      */         }
/*      */       }
/*  950 */       if (localLinkedList != null) {
/*  951 */         for (??? = localLinkedList.iterator(); ((Iterator)???).hasNext();) { Annotation[] arrayOfAnnotation = (Annotation[])((Iterator)???).next();
/*  952 */           _addOrOverrideAnnotations(paramAnnotatedMember, arrayOfAnnotation);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _addMixOvers(Constructor<?> paramConstructor, AnnotatedConstructor paramAnnotatedConstructor, boolean paramBoolean)
/*      */   {
/*  965 */     _addOrOverrideAnnotations(paramAnnotatedConstructor, paramConstructor.getDeclaredAnnotations());
/*  966 */     if (paramBoolean) {
/*  967 */       Annotation[][] arrayOfAnnotation = paramConstructor.getParameterAnnotations();
/*  968 */       int i = 0; for (int j = arrayOfAnnotation.length; i < j; i++) {
/*  969 */         for (Annotation localAnnotation : arrayOfAnnotation[i]) {
/*  970 */           paramAnnotatedConstructor.addOrOverrideParam(i, localAnnotation);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _addMixOvers(Method paramMethod, AnnotatedMethod paramAnnotatedMethod, boolean paramBoolean)
/*      */   {
/*  983 */     _addOrOverrideAnnotations(paramAnnotatedMethod, paramMethod.getDeclaredAnnotations());
/*  984 */     if (paramBoolean) {
/*  985 */       Annotation[][] arrayOfAnnotation = paramMethod.getParameterAnnotations();
/*  986 */       int i = 0; for (int j = arrayOfAnnotation.length; i < j; i++) {
/*  987 */         for (Annotation localAnnotation : arrayOfAnnotation[i]) {
/*  988 */           paramAnnotatedMethod.addOrOverrideParam(i, localAnnotation);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _addMixUnders(Method paramMethod, AnnotatedMethod paramAnnotatedMethod)
/*      */   {
/*  999 */     _addAnnotationsIfNotPresent(paramAnnotatedMethod, paramMethod.getDeclaredAnnotations());
/*      */   }
/*      */   
/*      */   private final boolean _isAnnotationBundle(Annotation paramAnnotation)
/*      */   {
/* 1004 */     return (this._annotationIntrospector != null) && (this._annotationIntrospector.isAnnotationBundle(paramAnnotation));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1016 */     return "[AnnotedClass " + this._class.getName() + "]";
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */