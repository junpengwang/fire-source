/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.MapperFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ValueInjector;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class BeanDeserializerBuilder
/*     */ {
/*     */   protected final BeanDescription _beanDesc;
/*     */   protected final boolean _defaultViewInclusion;
/*  41 */   protected final Map<String, SettableBeanProperty> _properties = new LinkedHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected List<ValueInjector> _injectables;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HashMap<String, SettableBeanProperty> _backRefProperties;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HashSet<String> _ignorableProps;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ValueInstantiator _valueInstantiator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectIdReader _objectIdReader;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SettableAnyProperty _anySetter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean _ignoreAllUnknown;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnnotatedMethod _buildMethod;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonPOJOBuilder.Value _builderConfig;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanDeserializerBuilder(BeanDescription paramBeanDescription, DeserializationConfig paramDeserializationConfig)
/*     */   {
/* 100 */     this._beanDesc = paramBeanDescription;
/* 101 */     this._defaultViewInclusion = paramDeserializationConfig.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanDeserializerBuilder(BeanDeserializerBuilder paramBeanDeserializerBuilder)
/*     */   {
/* 110 */     this._beanDesc = paramBeanDeserializerBuilder._beanDesc;
/* 111 */     this._defaultViewInclusion = paramBeanDeserializerBuilder._defaultViewInclusion;
/*     */     
/* 113 */     this._anySetter = paramBeanDeserializerBuilder._anySetter;
/* 114 */     this._ignoreAllUnknown = paramBeanDeserializerBuilder._ignoreAllUnknown;
/*     */     
/*     */ 
/* 117 */     this._properties.putAll(paramBeanDeserializerBuilder._properties);
/* 118 */     this._backRefProperties = _copy(paramBeanDeserializerBuilder._backRefProperties);
/*     */     
/* 120 */     this._ignorableProps = paramBeanDeserializerBuilder._ignorableProps;
/* 121 */     this._valueInstantiator = paramBeanDeserializerBuilder._valueInstantiator;
/* 122 */     this._objectIdReader = paramBeanDeserializerBuilder._objectIdReader;
/*     */     
/* 124 */     this._buildMethod = paramBeanDeserializerBuilder._buildMethod;
/* 125 */     this._builderConfig = paramBeanDeserializerBuilder._builderConfig;
/*     */   }
/*     */   
/*     */   private static HashMap<String, SettableBeanProperty> _copy(HashMap<String, SettableBeanProperty> paramHashMap)
/*     */   {
/* 130 */     if (paramHashMap == null) {
/* 131 */       return null;
/*     */     }
/* 133 */     return new HashMap(paramHashMap);
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
/*     */   public void addOrReplaceProperty(SettableBeanProperty paramSettableBeanProperty, boolean paramBoolean)
/*     */   {
/* 147 */     this._properties.put(paramSettableBeanProperty.getName(), paramSettableBeanProperty);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addProperty(SettableBeanProperty paramSettableBeanProperty)
/*     */   {
/* 157 */     SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)this._properties.put(paramSettableBeanProperty.getName(), paramSettableBeanProperty);
/* 158 */     if ((localSettableBeanProperty != null) && (localSettableBeanProperty != paramSettableBeanProperty)) {
/* 159 */       throw new IllegalArgumentException("Duplicate property '" + paramSettableBeanProperty.getName() + "' for " + this._beanDesc.getType());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addBackReferenceProperty(String paramString, SettableBeanProperty paramSettableBeanProperty)
/*     */   {
/* 170 */     if (this._backRefProperties == null) {
/* 171 */       this._backRefProperties = new HashMap(4);
/*     */     }
/* 173 */     this._backRefProperties.put(paramString, paramSettableBeanProperty);
/*     */     
/* 175 */     if (this._properties != null) {
/* 176 */       this._properties.remove(paramSettableBeanProperty.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addInjectable(String paramString, JavaType paramJavaType, Annotations paramAnnotations, AnnotatedMember paramAnnotatedMember, Object paramObject)
/*     */   {
/* 186 */     if (this._injectables == null) {
/* 187 */       this._injectables = new ArrayList();
/*     */     }
/* 189 */     this._injectables.add(new ValueInjector(paramString, paramJavaType, paramAnnotations, paramAnnotatedMember, paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addIgnorable(String paramString)
/*     */   {
/* 199 */     if (this._ignorableProps == null) {
/* 200 */       this._ignorableProps = new HashSet();
/*     */     }
/* 202 */     this._ignorableProps.add(paramString);
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
/*     */   public void addCreatorProperty(SettableBeanProperty paramSettableBeanProperty)
/*     */   {
/* 217 */     addProperty(paramSettableBeanProperty);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void addCreatorProperty(BeanPropertyDefinition paramBeanPropertyDefinition) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAnySetter(SettableAnyProperty paramSettableAnyProperty)
/*     */   {
/* 231 */     if ((this._anySetter != null) && (paramSettableAnyProperty != null)) {
/* 232 */       throw new IllegalStateException("_anySetter already set to non-null");
/*     */     }
/* 234 */     this._anySetter = paramSettableAnyProperty;
/*     */   }
/*     */   
/*     */   public void setIgnoreUnknownProperties(boolean paramBoolean) {
/* 238 */     this._ignoreAllUnknown = paramBoolean;
/*     */   }
/*     */   
/*     */   public void setValueInstantiator(ValueInstantiator paramValueInstantiator) {
/* 242 */     this._valueInstantiator = paramValueInstantiator;
/*     */   }
/*     */   
/*     */   public void setObjectIdReader(ObjectIdReader paramObjectIdReader) {
/* 246 */     this._objectIdReader = paramObjectIdReader;
/*     */   }
/*     */   
/*     */   public void setPOJOBuilder(AnnotatedMethod paramAnnotatedMethod, JsonPOJOBuilder.Value paramValue) {
/* 250 */     this._buildMethod = paramAnnotatedMethod;
/* 251 */     this._builderConfig = paramValue;
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
/*     */   public Iterator<SettableBeanProperty> getProperties()
/*     */   {
/* 269 */     return this._properties.values().iterator();
/*     */   }
/*     */   
/*     */   public SettableBeanProperty findProperty(String paramString) {
/* 273 */     return (SettableBeanProperty)this._properties.get(paramString);
/*     */   }
/*     */   
/*     */   public boolean hasProperty(String paramString) {
/* 277 */     return findProperty(paramString) != null;
/*     */   }
/*     */   
/*     */   public SettableBeanProperty removeProperty(String paramString) {
/* 281 */     return (SettableBeanProperty)this._properties.remove(paramString);
/*     */   }
/*     */   
/*     */   public SettableAnyProperty getAnySetter() {
/* 285 */     return this._anySetter;
/*     */   }
/*     */   
/*     */   public ValueInstantiator getValueInstantiator() {
/* 289 */     return this._valueInstantiator;
/*     */   }
/*     */   
/*     */   public List<ValueInjector> getInjectables() {
/* 293 */     return this._injectables;
/*     */   }
/*     */   
/*     */   public ObjectIdReader getObjectIdReader() {
/* 297 */     return this._objectIdReader;
/*     */   }
/*     */   
/*     */   public AnnotatedMethod getBuildMethod() {
/* 301 */     return this._buildMethod;
/*     */   }
/*     */   
/*     */   public JsonPOJOBuilder.Value getBuilderConfig() {
/* 305 */     return this._builderConfig;
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
/*     */   public JsonDeserializer<?> build()
/*     */   {
/* 323 */     Collection localCollection = this._properties.values();
/* 324 */     BeanPropertyMap localBeanPropertyMap = new BeanPropertyMap(localCollection);
/* 325 */     localBeanPropertyMap.assignIndexes();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 330 */     boolean bool = !this._defaultViewInclusion;
/*     */     Object localObject;
/* 332 */     if (!bool) {
/* 333 */       for (localObject = localCollection.iterator(); ((Iterator)localObject).hasNext();) { SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)((Iterator)localObject).next();
/* 334 */         if (localSettableBeanProperty.hasViews()) {
/* 335 */           bool = true;
/* 336 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 342 */     if (this._objectIdReader != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 347 */       localObject = new ObjectIdValueProperty(this._objectIdReader, true);
/* 348 */       localBeanPropertyMap = localBeanPropertyMap.withProperty((SettableBeanProperty)localObject);
/*     */     }
/*     */     
/* 351 */     return new BeanDeserializer(this, this._beanDesc, localBeanPropertyMap, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, bool);
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
/*     */   public AbstractDeserializer buildAbstract()
/*     */   {
/* 365 */     return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> buildBuilderBased(JavaType paramJavaType, String paramString)
/*     */   {
/* 376 */     if (this._buildMethod == null) {
/* 377 */       throw new IllegalArgumentException("Builder class " + this._beanDesc.getBeanClass().getName() + " does not have build method '" + paramString + "()'");
/*     */     }
/*     */     
/*     */ 
/* 381 */     Class localClass = this._buildMethod.getRawReturnType();
/* 382 */     if (!paramJavaType.getRawClass().isAssignableFrom(localClass)) {
/* 383 */       throw new IllegalArgumentException("Build method '" + this._buildMethod.getFullName() + " has bad return type (" + localClass.getName() + "), not compatible with POJO type (" + paramJavaType.getRawClass().getName() + ")");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 388 */     Collection localCollection = this._properties.values();
/* 389 */     BeanPropertyMap localBeanPropertyMap = new BeanPropertyMap(localCollection);
/* 390 */     localBeanPropertyMap.assignIndexes();
/*     */     
/* 392 */     boolean bool = !this._defaultViewInclusion;
/*     */     Object localObject;
/* 394 */     if (!bool) {
/* 395 */       for (localObject = localCollection.iterator(); ((Iterator)localObject).hasNext();) { SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)((Iterator)localObject).next();
/* 396 */         if (localSettableBeanProperty.hasViews()) {
/* 397 */           bool = true;
/* 398 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 403 */     if (this._objectIdReader != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 408 */       localObject = new ObjectIdValueProperty(this._objectIdReader, true);
/* 409 */       localBeanPropertyMap = localBeanPropertyMap.withProperty((SettableBeanProperty)localObject);
/*     */     }
/*     */     
/* 412 */     return new BuilderBasedDeserializer(this, this._beanDesc, localBeanPropertyMap, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, bool);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/BeanDeserializerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */