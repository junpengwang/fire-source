/*      */ package com.shaded.fasterxml.jackson.databind.deser;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Shape;
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*      */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*      */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*      */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*      */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*      */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*      */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*      */ import com.shaded.fasterxml.jackson.databind.BeanProperty.Std;
/*      */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*      */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*      */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*      */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*      */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*      */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.InnerClassProperty;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.ManagedReferenceProperty;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.impl.ValueInjector;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.std.StdDeserializer;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*      */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*      */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*      */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*      */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*      */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*      */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*      */ import com.shaded.fasterxml.jackson.databind.util.TokenBuffer;
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
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
/*      */ public abstract class BeanDeserializerBase
/*      */   extends StdDeserializer<Object>
/*      */   implements ContextualDeserializer, ResolvableDeserializer, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -2038793552422727904L;
/*      */   private final transient Annotations _classAnnotations;
/*      */   protected final JavaType _beanType;
/*      */   protected final JsonFormat.Shape _serializationShape;
/*      */   protected final ValueInstantiator _valueInstantiator;
/*      */   protected JsonDeserializer<Object> _delegateDeserializer;
/*      */   protected PropertyBasedCreator _propertyBasedCreator;
/*      */   protected boolean _nonStandardCreation;
/*      */   protected boolean _vanillaProcessing;
/*      */   protected final BeanPropertyMap _beanProperties;
/*      */   protected final ValueInjector[] _injectables;
/*      */   protected SettableAnyProperty _anySetter;
/*      */   protected final HashSet<String> _ignorableProps;
/*      */   protected final boolean _ignoreAllUnknown;
/*      */   protected final boolean _needViewProcesing;
/*      */   protected final Map<String, SettableBeanProperty> _backRefs;
/*      */   protected transient HashMap<ClassKey, JsonDeserializer<Object>> _subDeserializers;
/*      */   protected UnwrappedPropertyHandler _unwrappedPropertyHandler;
/*      */   protected ExternalTypeHandler _externalTypeIdHandler;
/*      */   protected final ObjectIdReader _objectIdReader;
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBuilder paramBeanDeserializerBuilder, BeanDescription paramBeanDescription, BeanPropertyMap paramBeanPropertyMap, Map<String, SettableBeanProperty> paramMap, HashSet<String> paramHashSet, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  206 */     super(paramBeanDescription.getType());
/*      */     
/*  208 */     AnnotatedClass localAnnotatedClass = paramBeanDescription.getClassInfo();
/*  209 */     this._classAnnotations = localAnnotatedClass.getAnnotations();
/*  210 */     this._beanType = paramBeanDescription.getType();
/*  211 */     this._valueInstantiator = paramBeanDeserializerBuilder.getValueInstantiator();
/*      */     
/*  213 */     this._beanProperties = paramBeanPropertyMap;
/*  214 */     this._backRefs = paramMap;
/*  215 */     this._ignorableProps = paramHashSet;
/*  216 */     this._ignoreAllUnknown = paramBoolean1;
/*      */     
/*  218 */     this._anySetter = paramBeanDeserializerBuilder.getAnySetter();
/*  219 */     List localList = paramBeanDeserializerBuilder.getInjectables();
/*  220 */     this._injectables = ((localList == null) || (localList.isEmpty()) ? null : (ValueInjector[])localList.toArray(new ValueInjector[localList.size()]));
/*      */     
/*  222 */     this._objectIdReader = paramBeanDeserializerBuilder.getObjectIdReader();
/*  223 */     this._nonStandardCreation = ((this._unwrappedPropertyHandler != null) || (this._valueInstantiator.canCreateUsingDelegate()) || (this._valueInstantiator.canCreateFromObjectWith()) || (!this._valueInstantiator.canCreateUsingDefault()));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  230 */     JsonFormat.Value localValue = paramBeanDescription.findExpectedFormat(null);
/*  231 */     this._serializationShape = (localValue == null ? null : localValue.getShape());
/*      */     
/*  233 */     this._needViewProcesing = paramBoolean2;
/*  234 */     this._vanillaProcessing = ((!this._nonStandardCreation) && (this._injectables == null) && (!this._needViewProcesing) && (this._objectIdReader != null));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase)
/*      */   {
/*  244 */     this(paramBeanDeserializerBase, paramBeanDeserializerBase._ignoreAllUnknown);
/*      */   }
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, boolean paramBoolean)
/*      */   {
/*  249 */     super(paramBeanDeserializerBase._beanType);
/*      */     
/*  251 */     this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
/*  252 */     this._beanType = paramBeanDeserializerBase._beanType;
/*      */     
/*  254 */     this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
/*  255 */     this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
/*  256 */     this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
/*      */     
/*  258 */     this._beanProperties = paramBeanDeserializerBase._beanProperties;
/*  259 */     this._backRefs = paramBeanDeserializerBase._backRefs;
/*  260 */     this._ignorableProps = paramBeanDeserializerBase._ignorableProps;
/*  261 */     this._ignoreAllUnknown = paramBoolean;
/*  262 */     this._anySetter = paramBeanDeserializerBase._anySetter;
/*  263 */     this._injectables = paramBeanDeserializerBase._injectables;
/*  264 */     this._objectIdReader = paramBeanDeserializerBase._objectIdReader;
/*      */     
/*  266 */     this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
/*  267 */     this._unwrappedPropertyHandler = paramBeanDeserializerBase._unwrappedPropertyHandler;
/*  268 */     this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
/*  269 */     this._serializationShape = paramBeanDeserializerBase._serializationShape;
/*      */     
/*  271 */     this._vanillaProcessing = paramBeanDeserializerBase._vanillaProcessing;
/*      */   }
/*      */   
/*      */   protected BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, NameTransformer paramNameTransformer)
/*      */   {
/*  276 */     super(paramBeanDeserializerBase._beanType);
/*      */     
/*  278 */     this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
/*  279 */     this._beanType = paramBeanDeserializerBase._beanType;
/*      */     
/*  281 */     this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
/*  282 */     this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
/*  283 */     this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
/*      */     
/*  285 */     this._backRefs = paramBeanDeserializerBase._backRefs;
/*  286 */     this._ignorableProps = paramBeanDeserializerBase._ignorableProps;
/*  287 */     this._ignoreAllUnknown = ((paramNameTransformer != null) || (paramBeanDeserializerBase._ignoreAllUnknown));
/*  288 */     this._anySetter = paramBeanDeserializerBase._anySetter;
/*  289 */     this._injectables = paramBeanDeserializerBase._injectables;
/*  290 */     this._objectIdReader = paramBeanDeserializerBase._objectIdReader;
/*      */     
/*  292 */     this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
/*  293 */     UnwrappedPropertyHandler localUnwrappedPropertyHandler = paramBeanDeserializerBase._unwrappedPropertyHandler;
/*      */     
/*  295 */     if (paramNameTransformer != null)
/*      */     {
/*  297 */       if (localUnwrappedPropertyHandler != null) {
/*  298 */         localUnwrappedPropertyHandler = localUnwrappedPropertyHandler.renameAll(paramNameTransformer);
/*      */       }
/*      */       
/*  301 */       this._beanProperties = paramBeanDeserializerBase._beanProperties.renameAll(paramNameTransformer);
/*      */     } else {
/*  303 */       this._beanProperties = paramBeanDeserializerBase._beanProperties;
/*      */     }
/*  305 */     this._unwrappedPropertyHandler = localUnwrappedPropertyHandler;
/*  306 */     this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
/*  307 */     this._serializationShape = paramBeanDeserializerBase._serializationShape;
/*      */     
/*      */ 
/*  310 */     this._vanillaProcessing = false;
/*      */   }
/*      */   
/*      */   public BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, ObjectIdReader paramObjectIdReader)
/*      */   {
/*  315 */     super(paramBeanDeserializerBase._beanType);
/*      */     
/*  317 */     this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
/*  318 */     this._beanType = paramBeanDeserializerBase._beanType;
/*      */     
/*  320 */     this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
/*  321 */     this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
/*  322 */     this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
/*      */     
/*  324 */     this._backRefs = paramBeanDeserializerBase._backRefs;
/*  325 */     this._ignorableProps = paramBeanDeserializerBase._ignorableProps;
/*  326 */     this._ignoreAllUnknown = paramBeanDeserializerBase._ignoreAllUnknown;
/*  327 */     this._anySetter = paramBeanDeserializerBase._anySetter;
/*  328 */     this._injectables = paramBeanDeserializerBase._injectables;
/*      */     
/*  330 */     this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
/*  331 */     this._unwrappedPropertyHandler = paramBeanDeserializerBase._unwrappedPropertyHandler;
/*  332 */     this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
/*  333 */     this._serializationShape = paramBeanDeserializerBase._serializationShape;
/*      */     
/*  335 */     this._vanillaProcessing = paramBeanDeserializerBase._vanillaProcessing;
/*      */     
/*      */ 
/*  338 */     this._objectIdReader = paramObjectIdReader;
/*      */     
/*  340 */     if (paramObjectIdReader == null) {
/*  341 */       this._beanProperties = paramBeanDeserializerBase._beanProperties;
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*  347 */       ObjectIdValueProperty localObjectIdValueProperty = new ObjectIdValueProperty(paramObjectIdReader, true);
/*  348 */       this._beanProperties = paramBeanDeserializerBase._beanProperties.withProperty(localObjectIdValueProperty);
/*      */     }
/*      */   }
/*      */   
/*      */   public BeanDeserializerBase(BeanDeserializerBase paramBeanDeserializerBase, HashSet<String> paramHashSet)
/*      */   {
/*  354 */     super(paramBeanDeserializerBase._beanType);
/*      */     
/*  356 */     this._classAnnotations = paramBeanDeserializerBase._classAnnotations;
/*  357 */     this._beanType = paramBeanDeserializerBase._beanType;
/*      */     
/*  359 */     this._valueInstantiator = paramBeanDeserializerBase._valueInstantiator;
/*  360 */     this._delegateDeserializer = paramBeanDeserializerBase._delegateDeserializer;
/*  361 */     this._propertyBasedCreator = paramBeanDeserializerBase._propertyBasedCreator;
/*      */     
/*  363 */     this._backRefs = paramBeanDeserializerBase._backRefs;
/*  364 */     this._ignorableProps = paramHashSet;
/*  365 */     this._ignoreAllUnknown = paramBeanDeserializerBase._ignoreAllUnknown;
/*  366 */     this._anySetter = paramBeanDeserializerBase._anySetter;
/*  367 */     this._injectables = paramBeanDeserializerBase._injectables;
/*      */     
/*  369 */     this._nonStandardCreation = paramBeanDeserializerBase._nonStandardCreation;
/*  370 */     this._unwrappedPropertyHandler = paramBeanDeserializerBase._unwrappedPropertyHandler;
/*  371 */     this._needViewProcesing = paramBeanDeserializerBase._needViewProcesing;
/*  372 */     this._serializationShape = paramBeanDeserializerBase._serializationShape;
/*      */     
/*  374 */     this._vanillaProcessing = paramBeanDeserializerBase._vanillaProcessing;
/*  375 */     this._objectIdReader = paramBeanDeserializerBase._objectIdReader;
/*  376 */     this._beanProperties = paramBeanDeserializerBase._beanProperties;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract BeanDeserializerBase withObjectIdReader(ObjectIdReader paramObjectIdReader);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract BeanDeserializerBase withIgnorableProperties(HashSet<String> paramHashSet);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract BeanDeserializerBase asArrayDeserializer();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resolve(DeserializationContext paramDeserializationContext)
/*      */     throws JsonMappingException
/*      */   {
/*  410 */     ExternalTypeHandler.Builder localBuilder = null;
/*      */     
/*  412 */     if (this._valueInstantiator.canCreateFromObjectWith()) {
/*  413 */       localObject1 = this._valueInstantiator.getFromObjectArguments(paramDeserializationContext.getConfig());
/*  414 */       this._propertyBasedCreator = PropertyBasedCreator.construct(paramDeserializationContext, this._valueInstantiator, (SettableBeanProperty[])localObject1);
/*      */       
/*  416 */       for (localObject2 = this._propertyBasedCreator.properties().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (SettableBeanProperty)((Iterator)localObject2).next();
/*  417 */         if (((SettableBeanProperty)localObject3).hasValueTypeDeserializer()) {
/*  418 */           localObject4 = ((SettableBeanProperty)localObject3).getValueTypeDeserializer();
/*  419 */           if (((TypeDeserializer)localObject4).getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
/*  420 */             if (localBuilder == null) {
/*  421 */               localBuilder = new ExternalTypeHandler.Builder();
/*      */             }
/*  423 */             localBuilder.addExternal((SettableBeanProperty)localObject3, (TypeDeserializer)localObject4);
/*      */           }
/*      */         }
/*      */       } }
/*      */     Object localObject3;
/*      */     Object localObject4;
/*  429 */     Object localObject1 = null;
/*      */     
/*  431 */     for (Object localObject2 = this._beanProperties.iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (SettableBeanProperty)((Iterator)localObject2).next();
/*  432 */       localObject4 = localObject3;
/*      */       Object localObject6;
/*  434 */       if (!((SettableBeanProperty)localObject4).hasValueDeserializer())
/*      */       {
/*  436 */         localObject5 = findConvertingDeserializer(paramDeserializationContext, (SettableBeanProperty)localObject4);
/*  437 */         if (localObject5 == null) {
/*  438 */           localObject5 = findDeserializer(paramDeserializationContext, ((SettableBeanProperty)localObject4).getType(), (BeanProperty)localObject4);
/*      */         }
/*  440 */         localObject4 = ((SettableBeanProperty)localObject4).withValueDeserializer((JsonDeserializer)localObject5);
/*      */       } else {
/*  442 */         localObject5 = ((SettableBeanProperty)localObject4).getValueDeserializer();
/*  443 */         if ((localObject5 instanceof ContextualDeserializer)) {
/*  444 */           localObject6 = ((ContextualDeserializer)localObject5).createContextual(paramDeserializationContext, (BeanProperty)localObject4);
/*  445 */           if (localObject6 != localObject5) {
/*  446 */             localObject4 = ((SettableBeanProperty)localObject4).withValueDeserializer((JsonDeserializer)localObject6);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  451 */       localObject4 = _resolveManagedReferenceProperty(paramDeserializationContext, (SettableBeanProperty)localObject4);
/*      */       
/*  453 */       Object localObject5 = _resolveUnwrappedProperty(paramDeserializationContext, (SettableBeanProperty)localObject4);
/*  454 */       if (localObject5 != null) {
/*  455 */         localObject4 = localObject5;
/*  456 */         if (localObject1 == null) {
/*  457 */           localObject1 = new UnwrappedPropertyHandler();
/*      */         }
/*  459 */         ((UnwrappedPropertyHandler)localObject1).addProperty((SettableBeanProperty)localObject4);
/*      */       }
/*      */       else
/*      */       {
/*  463 */         localObject4 = _resolveInnerClassValuedProperty(paramDeserializationContext, (SettableBeanProperty)localObject4);
/*  464 */         if (localObject4 != localObject3) {
/*  465 */           this._beanProperties.replace((SettableBeanProperty)localObject4);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  471 */         if (((SettableBeanProperty)localObject4).hasValueTypeDeserializer()) {
/*  472 */           localObject6 = ((SettableBeanProperty)localObject4).getValueTypeDeserializer();
/*  473 */           if (((TypeDeserializer)localObject6).getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
/*  474 */             if (localBuilder == null) {
/*  475 */               localBuilder = new ExternalTypeHandler.Builder();
/*      */             }
/*  477 */             localBuilder.addExternal((SettableBeanProperty)localObject4, (TypeDeserializer)localObject6);
/*      */             
/*  479 */             this._beanProperties.remove((SettableBeanProperty)localObject4);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  486 */     if ((this._anySetter != null) && (!this._anySetter.hasValueDeserializer())) {
/*  487 */       this._anySetter = this._anySetter.withValueDeserializer(findDeserializer(paramDeserializationContext, this._anySetter.getType(), this._anySetter.getProperty()));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  492 */     if (this._valueInstantiator.canCreateUsingDelegate()) {
/*  493 */       localObject2 = this._valueInstantiator.getDelegateType(paramDeserializationContext.getConfig());
/*  494 */       if (localObject2 == null) {
/*  495 */         throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._beanType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
/*      */       }
/*      */       
/*      */ 
/*  499 */       localObject3 = this._valueInstantiator.getDelegateCreator();
/*      */       
/*  501 */       localObject4 = new BeanProperty.Std(null, (JavaType)localObject2, null, this._classAnnotations, (AnnotatedMember)localObject3, false);
/*      */       
/*  503 */       this._delegateDeserializer = findDeserializer(paramDeserializationContext, (JavaType)localObject2, (BeanProperty)localObject4);
/*      */     }
/*      */     
/*  506 */     if (localBuilder != null) {
/*  507 */       this._externalTypeIdHandler = localBuilder.build();
/*      */       
/*  509 */       this._nonStandardCreation = true;
/*      */     }
/*      */     
/*  512 */     this._unwrappedPropertyHandler = ((UnwrappedPropertyHandler)localObject1);
/*  513 */     if (localObject1 != null) {
/*  514 */       this._nonStandardCreation = true;
/*      */     }
/*      */     
/*      */ 
/*  518 */     this._vanillaProcessing = ((this._vanillaProcessing) && (!this._nonStandardCreation));
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
/*      */   protected JsonDeserializer<Object> findConvertingDeserializer(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
/*      */     throws JsonMappingException
/*      */   {
/*  532 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/*  533 */     if (localAnnotationIntrospector != null) {
/*  534 */       Object localObject = localAnnotationIntrospector.findDeserializationConverter(paramSettableBeanProperty.getMember());
/*  535 */       if (localObject != null) {
/*  536 */         Converter localConverter = paramDeserializationContext.converterInstance(paramSettableBeanProperty.getMember(), localObject);
/*  537 */         JavaType localJavaType = localConverter.getInputType(paramDeserializationContext.getTypeFactory());
/*  538 */         JsonDeserializer localJsonDeserializer = paramDeserializationContext.findContextualValueDeserializer(localJavaType, paramSettableBeanProperty);
/*  539 */         return new StdDelegatingDeserializer(localConverter, localJavaType, localJsonDeserializer);
/*      */       }
/*      */     }
/*  542 */     return null;
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
/*      */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*      */     throws JsonMappingException
/*      */   {
/*  556 */     ObjectIdReader localObjectIdReader = this._objectIdReader;
/*  557 */     String[] arrayOfString = null;
/*      */     
/*      */ 
/*  560 */     AnnotationIntrospector localAnnotationIntrospector = paramDeserializationContext.getAnnotationIntrospector();
/*  561 */     AnnotatedMember localAnnotatedMember = (paramBeanProperty == null) || (localAnnotationIntrospector == null) ? null : paramBeanProperty.getMember();
/*      */     Object localObject4;
/*  563 */     if ((paramBeanProperty != null) && (localAnnotationIntrospector != null)) {
/*  564 */       arrayOfString = localAnnotationIntrospector.findPropertiesToIgnore(localAnnotatedMember);
/*  565 */       localObject1 = localAnnotationIntrospector.findObjectIdInfo(localAnnotatedMember);
/*  566 */       if (localObject1 != null)
/*      */       {
/*  568 */         localObject1 = localAnnotationIntrospector.findObjectReferenceInfo(localAnnotatedMember, (ObjectIdInfo)localObject1);
/*      */         
/*  570 */         localObject2 = ((ObjectIdInfo)localObject1).getGeneratorType();
/*      */         
/*      */         SettableBeanProperty localSettableBeanProperty;
/*      */         
/*      */         Object localObject5;
/*  575 */         if (localObject2 == ObjectIdGenerators.PropertyGenerator.class) {
/*  576 */           localObject3 = ((ObjectIdInfo)localObject1).getPropertyName();
/*  577 */           localSettableBeanProperty = findProperty((String)localObject3);
/*  578 */           if (localSettableBeanProperty == null) {
/*  579 */             throw new IllegalArgumentException("Invalid Object Id definition for " + getBeanClass().getName() + ": can not find property with name '" + (String)localObject3 + "'");
/*      */           }
/*      */           
/*  582 */           localObject4 = localSettableBeanProperty.getType();
/*  583 */           localObject5 = new PropertyBasedObjectIdGenerator(((ObjectIdInfo)localObject1).getScope());
/*      */         } else {
/*  585 */           localObject3 = paramDeserializationContext.constructType((Class)localObject2);
/*  586 */           localObject4 = paramDeserializationContext.getTypeFactory().findTypeParameters(localObject3, ObjectIdGenerator.class)[0];
/*  587 */           localSettableBeanProperty = null;
/*  588 */           localObject5 = paramDeserializationContext.objectIdGeneratorInstance(localAnnotatedMember, (ObjectIdInfo)localObject1);
/*      */         }
/*  590 */         Object localObject3 = paramDeserializationContext.findRootValueDeserializer((JavaType)localObject4);
/*  591 */         localObjectIdReader = ObjectIdReader.construct((JavaType)localObject4, ((ObjectIdInfo)localObject1).getPropertyName(), (ObjectIdGenerator)localObject5, (JsonDeserializer)localObject3, localSettableBeanProperty);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  596 */     Object localObject1 = this;
/*  597 */     if ((localObjectIdReader != null) && (localObjectIdReader != this._objectIdReader)) {
/*  598 */       localObject1 = ((BeanDeserializerBase)localObject1).withObjectIdReader(localObjectIdReader);
/*      */     }
/*      */     
/*  601 */     if ((arrayOfString != null) && (arrayOfString.length != 0)) {
/*  602 */       localObject2 = ArrayBuilders.setAndArray(((BeanDeserializerBase)localObject1)._ignorableProps, arrayOfString);
/*  603 */       localObject1 = ((BeanDeserializerBase)localObject1).withIgnorableProperties((HashSet)localObject2);
/*      */     }
/*      */     
/*      */ 
/*  607 */     Object localObject2 = null;
/*  608 */     if (localAnnotatedMember != null) {
/*  609 */       localObject4 = localAnnotationIntrospector.findFormat(localAnnotatedMember);
/*      */       
/*  611 */       if (localObject4 != null) {
/*  612 */         localObject2 = ((JsonFormat.Value)localObject4).getShape();
/*      */       }
/*      */     }
/*  615 */     if (localObject2 == null) {
/*  616 */       localObject2 = this._serializationShape;
/*      */     }
/*  618 */     if (localObject2 == JsonFormat.Shape.ARRAY) {
/*  619 */       localObject1 = ((BeanDeserializerBase)localObject1).asArrayDeserializer();
/*      */     }
/*  621 */     return (JsonDeserializer<?>)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
/*      */   {
/*  632 */     String str = paramSettableBeanProperty.getManagedReferenceName();
/*  633 */     if (str == null) {
/*  634 */       return paramSettableBeanProperty;
/*      */     }
/*  636 */     JsonDeserializer localJsonDeserializer = paramSettableBeanProperty.getValueDeserializer();
/*  637 */     SettableBeanProperty localSettableBeanProperty = null;
/*  638 */     boolean bool = false;
/*  639 */     if ((localJsonDeserializer instanceof BeanDeserializerBase)) {
/*  640 */       localSettableBeanProperty = ((BeanDeserializerBase)localJsonDeserializer).findBackReference(str);
/*  641 */     } else if ((localJsonDeserializer instanceof ContainerDeserializerBase)) {
/*  642 */       localObject1 = ((ContainerDeserializerBase)localJsonDeserializer).getContentDeserializer();
/*  643 */       if (!(localObject1 instanceof BeanDeserializerBase)) {
/*  644 */         localObject2 = localObject1 == null ? "NULL" : localObject1.getClass().getName();
/*  645 */         throw new IllegalArgumentException("Can not handle managed/back reference '" + str + "': value deserializer is of type ContainerDeserializerBase, but content type is not handled by a BeanDeserializer " + " (instead it's of type " + (String)localObject2 + ")");
/*      */       }
/*      */       
/*      */ 
/*  649 */       localSettableBeanProperty = ((BeanDeserializerBase)localObject1).findBackReference(str);
/*  650 */       bool = true;
/*  651 */     } else if ((localJsonDeserializer instanceof AbstractDeserializer)) {
/*  652 */       localSettableBeanProperty = ((AbstractDeserializer)localJsonDeserializer).findBackReference(str);
/*      */     } else {
/*  654 */       throw new IllegalArgumentException("Can not handle managed/back reference '" + str + "': type for value deserializer is not BeanDeserializer or ContainerDeserializerBase, but " + localJsonDeserializer.getClass().getName());
/*      */     }
/*      */     
/*      */ 
/*  658 */     if (localSettableBeanProperty == null) {
/*  659 */       throw new IllegalArgumentException("Can not handle managed/back reference '" + str + "': no back reference property found from type " + paramSettableBeanProperty.getType());
/*      */     }
/*      */     
/*      */ 
/*  663 */     Object localObject1 = this._beanType;
/*  664 */     Object localObject2 = localSettableBeanProperty.getType();
/*  665 */     if (!((JavaType)localObject2).getRawClass().isAssignableFrom(((JavaType)localObject1).getRawClass())) {
/*  666 */       throw new IllegalArgumentException("Can not handle managed/back reference '" + str + "': back reference type (" + ((JavaType)localObject2).getRawClass().getName() + ") not compatible with managed type (" + ((JavaType)localObject1).getRawClass().getName() + ")");
/*      */     }
/*      */     
/*      */ 
/*  670 */     return new ManagedReferenceProperty(paramSettableBeanProperty, str, localSettableBeanProperty, this._classAnnotations, bool);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected SettableBeanProperty _resolveUnwrappedProperty(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
/*      */   {
/*  681 */     AnnotatedMember localAnnotatedMember = paramSettableBeanProperty.getMember();
/*  682 */     if (localAnnotatedMember != null) {
/*  683 */       NameTransformer localNameTransformer = paramDeserializationContext.getAnnotationIntrospector().findUnwrappingNameTransformer(localAnnotatedMember);
/*  684 */       if (localNameTransformer != null) {
/*  685 */         JsonDeserializer localJsonDeserializer1 = paramSettableBeanProperty.getValueDeserializer();
/*  686 */         JsonDeserializer localJsonDeserializer2 = localJsonDeserializer1.unwrappingDeserializer(localNameTransformer);
/*  687 */         if ((localJsonDeserializer2 != localJsonDeserializer1) && (localJsonDeserializer2 != null))
/*      */         {
/*  689 */           return paramSettableBeanProperty.withValueDeserializer(localJsonDeserializer2);
/*      */         }
/*      */       }
/*      */     }
/*  693 */     return null;
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
/*      */   protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationContext paramDeserializationContext, SettableBeanProperty paramSettableBeanProperty)
/*      */   {
/*  706 */     JsonDeserializer localJsonDeserializer = paramSettableBeanProperty.getValueDeserializer();
/*      */     
/*  708 */     if ((localJsonDeserializer instanceof BeanDeserializerBase)) {
/*  709 */       BeanDeserializerBase localBeanDeserializerBase = (BeanDeserializerBase)localJsonDeserializer;
/*  710 */       ValueInstantiator localValueInstantiator = localBeanDeserializerBase.getValueInstantiator();
/*  711 */       if (!localValueInstantiator.canCreateUsingDefault()) {
/*  712 */         Class localClass1 = paramSettableBeanProperty.getType().getRawClass();
/*  713 */         Class localClass2 = ClassUtil.getOuterClass(localClass1);
/*      */         
/*  715 */         if ((localClass2 != null) && (localClass2 == this._beanType.getRawClass())) {
/*  716 */           for (Constructor localConstructor : localClass1.getConstructors()) {
/*  717 */             Class[] arrayOfClass = localConstructor.getParameterTypes();
/*  718 */             if ((arrayOfClass.length == 1) && (arrayOfClass[0] == localClass2)) {
/*  719 */               if (paramDeserializationContext.getConfig().canOverrideAccessModifiers()) {
/*  720 */                 ClassUtil.checkAndFixAccess(localConstructor);
/*      */               }
/*  722 */               return new InnerClassProperty(paramSettableBeanProperty, localConstructor);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  728 */     return paramSettableBeanProperty;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isCachable()
/*      */   {
/*  738 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectIdReader getObjectIdReader()
/*      */   {
/*  747 */     return this._objectIdReader;
/*      */   }
/*      */   
/*      */   public boolean hasProperty(String paramString) {
/*  751 */     return this._beanProperties.find(paramString) != null;
/*      */   }
/*      */   
/*      */   public boolean hasViews() {
/*  755 */     return this._needViewProcesing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getPropertyCount()
/*      */   {
/*  762 */     return this._beanProperties.size();
/*      */   }
/*      */   
/*      */   public Collection<Object> getKnownPropertyNames()
/*      */   {
/*  767 */     ArrayList localArrayList = new ArrayList();
/*  768 */     for (SettableBeanProperty localSettableBeanProperty : this._beanProperties) {
/*  769 */       localArrayList.add(localSettableBeanProperty.getName());
/*      */     }
/*  771 */     return localArrayList;
/*      */   }
/*      */   
/*  774 */   public final Class<?> getBeanClass() { return this._beanType.getRawClass(); }
/*      */   
/*  776 */   public JavaType getValueType() { return this._beanType; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Iterator<SettableBeanProperty> properties()
/*      */   {
/*  787 */     if (this._beanProperties == null) {
/*  788 */       throw new IllegalStateException("Can only call after BeanDeserializer has been resolved");
/*      */     }
/*  790 */     return this._beanProperties.iterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Iterator<SettableBeanProperty> creatorProperties()
/*      */   {
/*  802 */     if (this._propertyBasedCreator == null) {
/*  803 */       return Collections.emptyList().iterator();
/*      */     }
/*  805 */     return this._propertyBasedCreator.properties().iterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SettableBeanProperty findProperty(String paramString)
/*      */   {
/*  817 */     SettableBeanProperty localSettableBeanProperty = this._beanProperties == null ? null : this._beanProperties.find(paramString);
/*      */     
/*  819 */     if ((localSettableBeanProperty == null) && (this._propertyBasedCreator != null)) {
/*  820 */       localSettableBeanProperty = this._propertyBasedCreator.findCreatorProperty(paramString);
/*      */     }
/*  822 */     return localSettableBeanProperty;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SettableBeanProperty findBackReference(String paramString)
/*      */   {
/*  831 */     if (this._backRefs == null) {
/*  832 */       return null;
/*      */     }
/*  834 */     return (SettableBeanProperty)this._backRefs.get(paramString);
/*      */   }
/*      */   
/*      */   public ValueInstantiator getValueInstantiator() {
/*  838 */     return this._valueInstantiator;
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
/*      */   public void replaceProperty(SettableBeanProperty paramSettableBeanProperty1, SettableBeanProperty paramSettableBeanProperty2)
/*      */   {
/*  862 */     this._beanProperties.replace(paramSettableBeanProperty2);
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
/*      */   public abstract Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  886 */     if (this._objectIdReader != null) {
/*  887 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*      */       
/*  889 */       if ((localJsonToken != null) && (localJsonToken.isScalarValue())) {
/*  890 */         return deserializeFromObjectId(paramJsonParser, paramDeserializationContext);
/*      */       }
/*      */     }
/*      */     
/*  894 */     return paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
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
/*      */   protected Object deserializeWithObjectId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  910 */     String str = this._objectIdReader.propertyName;
/*      */     
/*  912 */     if (str.equals(paramJsonParser.getCurrentName())) {
/*  913 */       return deserializeFromObject(paramJsonParser, paramDeserializationContext);
/*      */     }
/*      */     
/*  916 */     TokenBuffer localTokenBuffer1 = new TokenBuffer(paramJsonParser.getCodec());
/*  917 */     TokenBuffer localTokenBuffer2 = null;
/*  918 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/*  919 */       localObject = paramJsonParser.getCurrentName();
/*      */       
/*  921 */       if (localTokenBuffer2 == null) {
/*  922 */         if (str.equals(localObject)) {
/*  923 */           localTokenBuffer2 = new TokenBuffer(paramJsonParser.getCodec());
/*  924 */           localTokenBuffer2.writeFieldName((String)localObject);
/*  925 */           paramJsonParser.nextToken();
/*  926 */           localTokenBuffer2.copyCurrentStructure(paramJsonParser);
/*  927 */           localTokenBuffer2.append(localTokenBuffer1);
/*  928 */           localTokenBuffer1 = null;
/*      */         } else {
/*  930 */           localTokenBuffer1.writeFieldName((String)localObject);
/*  931 */           paramJsonParser.nextToken();
/*  932 */           localTokenBuffer1.copyCurrentStructure(paramJsonParser);
/*      */         }
/*      */       } else {
/*  935 */         localTokenBuffer2.writeFieldName((String)localObject);
/*  936 */         paramJsonParser.nextToken();
/*  937 */         localTokenBuffer2.copyCurrentStructure(paramJsonParser);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  942 */     Object localObject = localTokenBuffer2 == null ? localTokenBuffer1 : localTokenBuffer2;
/*  943 */     ((TokenBuffer)localObject).writeEndObject();
/*      */     
/*  945 */     JsonParser localJsonParser = ((TokenBuffer)localObject).asParser();
/*  946 */     localJsonParser.nextToken();
/*  947 */     return deserializeFromObject(localJsonParser, paramDeserializationContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object deserializeFromObjectId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  957 */     Object localObject1 = this._objectIdReader.deserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*  958 */     ReadableObjectId localReadableObjectId = paramDeserializationContext.findObjectId(localObject1, this._objectIdReader.generator);
/*      */     
/*  960 */     Object localObject2 = localReadableObjectId.item;
/*  961 */     if (localObject2 == null) {
/*  962 */       throw new IllegalStateException("Could not resolve Object Id [" + localObject1 + "] (for " + this._beanType + ") -- unresolved forward-reference?");
/*      */     }
/*      */     
/*  965 */     return localObject2;
/*      */   }
/*      */   
/*      */ 
/*      */   protected Object deserializeFromObjectUsingNonDefault(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  972 */     if (this._delegateDeserializer != null) {
/*  973 */       return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*      */     }
/*      */     
/*  976 */     if (this._propertyBasedCreator != null) {
/*  977 */       return _deserializeUsingPropertyBased(paramJsonParser, paramDeserializationContext);
/*      */     }
/*      */     
/*  980 */     if (this._beanType.isAbstract()) {
/*  981 */       throw JsonMappingException.from(paramJsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
/*      */     }
/*      */     
/*  984 */     throw JsonMappingException.from(paramJsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected abstract Object _deserializeUsingPropertyBased(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException;
/*      */   
/*      */ 
/*      */ 
/*      */   public Object deserializeFromNumber(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  997 */     if (this._objectIdReader != null) {
/*  998 */       return deserializeFromObjectId(paramJsonParser, paramDeserializationContext);
/*      */     }
/*      */     Object localObject;
/* 1001 */     switch (paramJsonParser.getNumberType()) {
/*      */     case INT: 
/* 1003 */       if ((this._delegateDeserializer != null) && 
/* 1004 */         (!this._valueInstantiator.canCreateFromInt())) {
/* 1005 */         localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/* 1006 */         if (this._injectables != null) {
/* 1007 */           injectValues(paramDeserializationContext, localObject);
/*      */         }
/* 1009 */         return localObject;
/*      */       }
/*      */       
/* 1012 */       return this._valueInstantiator.createFromInt(paramDeserializationContext, paramJsonParser.getIntValue());
/*      */     case LONG: 
/* 1014 */       if ((this._delegateDeserializer != null) && 
/* 1015 */         (!this._valueInstantiator.canCreateFromInt())) {
/* 1016 */         localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/* 1017 */         if (this._injectables != null) {
/* 1018 */           injectValues(paramDeserializationContext, localObject);
/*      */         }
/* 1020 */         return localObject;
/*      */       }
/*      */       
/* 1023 */       return this._valueInstantiator.createFromLong(paramDeserializationContext, paramJsonParser.getLongValue());
/*      */     }
/*      */     
/* 1026 */     if (this._delegateDeserializer != null) {
/* 1027 */       localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/* 1028 */       if (this._injectables != null) {
/* 1029 */         injectValues(paramDeserializationContext, localObject);
/*      */       }
/* 1031 */       return localObject;
/*      */     }
/* 1033 */     throw paramDeserializationContext.instantiationException(getBeanClass(), "no suitable creator method found to deserialize from JSON integer number");
/*      */   }
/*      */   
/*      */ 
/*      */   public Object deserializeFromString(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1040 */     if (this._objectIdReader != null) {
/* 1041 */       return deserializeFromObjectId(paramJsonParser, paramDeserializationContext);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1047 */     if ((this._delegateDeserializer != null) && 
/* 1048 */       (!this._valueInstantiator.canCreateFromString())) {
/* 1049 */       Object localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/* 1050 */       if (this._injectables != null) {
/* 1051 */         injectValues(paramDeserializationContext, localObject);
/*      */       }
/* 1053 */       return localObject;
/*      */     }
/*      */     
/* 1056 */     return this._valueInstantiator.createFromString(paramDeserializationContext, paramJsonParser.getText());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object deserializeFromDouble(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1067 */     switch (paramJsonParser.getNumberType()) {
/*      */     case FLOAT: 
/*      */     case DOUBLE: 
/* 1070 */       if ((this._delegateDeserializer != null) && 
/* 1071 */         (!this._valueInstantiator.canCreateFromDouble())) {
/* 1072 */         Object localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/* 1073 */         if (this._injectables != null) {
/* 1074 */           injectValues(paramDeserializationContext, localObject);
/*      */         }
/* 1076 */         return localObject;
/*      */       }
/*      */       
/* 1079 */       return this._valueInstantiator.createFromDouble(paramDeserializationContext, paramJsonParser.getDoubleValue());
/*      */     }
/*      */     
/* 1082 */     if (this._delegateDeserializer != null) {
/* 1083 */       return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*      */     }
/* 1085 */     throw paramDeserializationContext.instantiationException(getBeanClass(), "no suitable creator method found to deserialize from JSON floating-point number");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object deserializeFromBoolean(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1094 */     if ((this._delegateDeserializer != null) && 
/* 1095 */       (!this._valueInstantiator.canCreateFromBoolean())) {
/* 1096 */       Object localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/* 1097 */       if (this._injectables != null) {
/* 1098 */         injectValues(paramDeserializationContext, localObject);
/*      */       }
/* 1100 */       return localObject;
/*      */     }
/*      */     
/* 1103 */     boolean bool = paramJsonParser.getCurrentToken() == JsonToken.VALUE_TRUE;
/* 1104 */     return this._valueInstantiator.createFromBoolean(paramDeserializationContext, bool);
/*      */   }
/*      */   
/*      */   public Object deserializeFromArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1110 */     if (this._delegateDeserializer != null) {
/*      */       try {
/* 1112 */         Object localObject = this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/* 1113 */         if (this._injectables != null) {
/* 1114 */           injectValues(paramDeserializationContext, localObject);
/*      */         }
/* 1116 */         return localObject;
/*      */       } catch (Exception localException) {
/* 1118 */         wrapInstantiationProblem(localException, paramDeserializationContext);
/*      */       }
/*      */     }
/* 1121 */     throw paramDeserializationContext.mappingException(getBeanClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void injectValues(DeserializationContext paramDeserializationContext, Object paramObject)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1133 */     for (ValueInjector localValueInjector : this._injectables) {
/* 1134 */       localValueInjector.inject(paramDeserializationContext, paramObject);
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
/*      */   protected void handleUnknownProperty(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1152 */     if ((this._ignoreAllUnknown) || ((this._ignorableProps != null) && (this._ignorableProps.contains(paramString))))
/*      */     {
/* 1154 */       paramJsonParser.skipChildren();
/* 1155 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1160 */     super.handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object handleUnknownProperties(DeserializationContext paramDeserializationContext, Object paramObject, TokenBuffer paramTokenBuffer)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1172 */     paramTokenBuffer.writeEndObject();
/*      */     
/*      */ 
/* 1175 */     JsonParser localJsonParser = paramTokenBuffer.asParser();
/* 1176 */     while (localJsonParser.nextToken() != JsonToken.END_OBJECT) {
/* 1177 */       String str = localJsonParser.getCurrentName();
/*      */       
/* 1179 */       localJsonParser.nextToken();
/* 1180 */       handleUnknownProperty(localJsonParser, paramDeserializationContext, paramObject, str);
/*      */     }
/* 1182 */     return paramObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void handleUnknownVanilla(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1193 */     if ((this._ignorableProps != null) && (this._ignorableProps.contains(paramString))) {
/* 1194 */       paramJsonParser.skipChildren();
/* 1195 */     } else if (this._anySetter != null) {
/*      */       try
/*      */       {
/* 1198 */         this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, paramString);
/*      */       } catch (Exception localException) {
/* 1200 */         wrapAndThrow(localException, paramObject, paramString, paramDeserializationContext);
/*      */       }
/*      */       
/*      */     } else {
/* 1204 */       handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, paramString);
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
/*      */ 
/*      */   protected Object handlePolymorphic(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, TokenBuffer paramTokenBuffer)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1224 */     JsonDeserializer localJsonDeserializer = _findSubclassDeserializer(paramDeserializationContext, paramObject, paramTokenBuffer);
/* 1225 */     if (localJsonDeserializer != null) {
/* 1226 */       if (paramTokenBuffer != null)
/*      */       {
/* 1228 */         paramTokenBuffer.writeEndObject();
/* 1229 */         JsonParser localJsonParser = paramTokenBuffer.asParser();
/* 1230 */         localJsonParser.nextToken();
/* 1231 */         paramObject = localJsonDeserializer.deserialize(localJsonParser, paramDeserializationContext, paramObject);
/*      */       }
/*      */       
/* 1234 */       if (paramJsonParser != null) {
/* 1235 */         paramObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext, paramObject);
/*      */       }
/* 1237 */       return paramObject;
/*      */     }
/*      */     
/* 1240 */     if (paramTokenBuffer != null) {
/* 1241 */       paramObject = handleUnknownProperties(paramDeserializationContext, paramObject, paramTokenBuffer);
/*      */     }
/*      */     
/* 1244 */     if (paramJsonParser != null) {
/* 1245 */       paramObject = deserialize(paramJsonParser, paramDeserializationContext, paramObject);
/*      */     }
/* 1247 */     return paramObject;
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
/*      */   protected JsonDeserializer<Object> _findSubclassDeserializer(DeserializationContext paramDeserializationContext, Object paramObject, TokenBuffer paramTokenBuffer)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1261 */     synchronized (this) {
/* 1262 */       localJsonDeserializer = this._subDeserializers == null ? null : (JsonDeserializer)this._subDeserializers.get(new ClassKey(paramObject.getClass()));
/*      */     }
/* 1264 */     if (localJsonDeserializer != null) {
/* 1265 */       return localJsonDeserializer;
/*      */     }
/*      */     
/* 1268 */     ??? = paramDeserializationContext.constructType(paramObject.getClass());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1275 */     JsonDeserializer localJsonDeserializer = paramDeserializationContext.findRootValueDeserializer((JavaType)???);
/*      */     
/* 1277 */     if (localJsonDeserializer != null) {
/* 1278 */       synchronized (this) {
/* 1279 */         if (this._subDeserializers == null) {
/* 1280 */           this._subDeserializers = new HashMap();
/*      */         }
/* 1282 */         this._subDeserializers.put(new ClassKey(paramObject.getClass()), localJsonDeserializer);
/*      */       }
/*      */     }
/* 1285 */     return localJsonDeserializer;
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
/*      */ 
/*      */   public void wrapAndThrow(Throwable paramThrowable, Object paramObject, String paramString, DeserializationContext paramDeserializationContext)
/*      */     throws IOException
/*      */   {
/* 1311 */     throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(paramThrowable, paramDeserializationContext), paramObject, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */   public void wrapAndThrow(Throwable paramThrowable, Object paramObject, int paramInt, DeserializationContext paramDeserializationContext)
/*      */     throws IOException
/*      */   {
/* 1318 */     throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(paramThrowable, paramDeserializationContext), paramObject, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Throwable throwOrReturnThrowable(Throwable paramThrowable, DeserializationContext paramDeserializationContext)
/*      */     throws IOException
/*      */   {
/* 1328 */     while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
/* 1329 */       paramThrowable = paramThrowable.getCause();
/*      */     }
/*      */     
/* 1332 */     if ((paramThrowable instanceof Error)) {
/* 1333 */       throw ((Error)paramThrowable);
/*      */     }
/* 1335 */     int i = (paramDeserializationContext == null) || (paramDeserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS)) ? 1 : 0;
/*      */     
/* 1337 */     if ((paramThrowable instanceof IOException)) {
/* 1338 */       if ((i == 0) || (!(paramThrowable instanceof JsonProcessingException))) {
/* 1339 */         throw ((IOException)paramThrowable);
/*      */       }
/* 1341 */     } else if ((i == 0) && 
/* 1342 */       ((paramThrowable instanceof RuntimeException))) {
/* 1343 */       throw ((RuntimeException)paramThrowable);
/*      */     }
/*      */     
/* 1346 */     return paramThrowable;
/*      */   }
/*      */   
/*      */   protected void wrapInstantiationProblem(Throwable paramThrowable, DeserializationContext paramDeserializationContext)
/*      */     throws IOException
/*      */   {
/* 1352 */     while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
/* 1353 */       paramThrowable = paramThrowable.getCause();
/*      */     }
/*      */     
/* 1356 */     if ((paramThrowable instanceof Error)) {
/* 1357 */       throw ((Error)paramThrowable);
/*      */     }
/* 1359 */     int i = (paramDeserializationContext == null) || (paramDeserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS)) ? 1 : 0;
/* 1360 */     if ((paramThrowable instanceof IOException))
/*      */     {
/* 1362 */       throw ((IOException)paramThrowable); }
/* 1363 */     if ((i == 0) && 
/* 1364 */       ((paramThrowable instanceof RuntimeException))) {
/* 1365 */       throw ((RuntimeException)paramThrowable);
/*      */     }
/*      */     
/* 1368 */     throw paramDeserializationContext.instantiationException(this._beanType.getRawClass(), paramThrowable);
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/BeanDeserializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */