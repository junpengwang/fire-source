/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PropertyBasedCreator
/*     */ {
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final HashMap<String, SettableBeanProperty> _properties;
/*     */   protected final int _propertyCount;
/*     */   protected final Object[] _defaultValues;
/*     */   protected final SettableBeanProperty[] _propertiesWithInjectables;
/*     */   
/*     */   protected PropertyBasedCreator(ValueInstantiator paramValueInstantiator, SettableBeanProperty[] paramArrayOfSettableBeanProperty, Object[] paramArrayOfObject)
/*     */   {
/*  62 */     this._valueInstantiator = paramValueInstantiator;
/*  63 */     this._properties = new HashMap();
/*  64 */     SettableBeanProperty[] arrayOfSettableBeanProperty = null;
/*  65 */     int i = paramArrayOfSettableBeanProperty.length;
/*  66 */     this._propertyCount = i;
/*  67 */     for (int j = 0; j < i; j++) {
/*  68 */       SettableBeanProperty localSettableBeanProperty = paramArrayOfSettableBeanProperty[j];
/*  69 */       this._properties.put(localSettableBeanProperty.getName(), localSettableBeanProperty);
/*  70 */       Object localObject = localSettableBeanProperty.getInjectableValueId();
/*  71 */       if (localObject != null) {
/*  72 */         if (arrayOfSettableBeanProperty == null) {
/*  73 */           arrayOfSettableBeanProperty = new SettableBeanProperty[i];
/*     */         }
/*  75 */         arrayOfSettableBeanProperty[j] = localSettableBeanProperty;
/*     */       }
/*     */     }
/*  78 */     this._defaultValues = paramArrayOfObject;
/*  79 */     this._propertiesWithInjectables = arrayOfSettableBeanProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PropertyBasedCreator construct(DeserializationContext paramDeserializationContext, ValueInstantiator paramValueInstantiator, SettableBeanProperty[] paramArrayOfSettableBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  90 */     int i = paramArrayOfSettableBeanProperty.length;
/*  91 */     SettableBeanProperty[] arrayOfSettableBeanProperty = new SettableBeanProperty[i];
/*  92 */     Object[] arrayOfObject = null;
/*  93 */     for (int j = 0; j < i; j++) {
/*  94 */       SettableBeanProperty localSettableBeanProperty = paramArrayOfSettableBeanProperty[j];
/*  95 */       if (!localSettableBeanProperty.hasValueDeserializer()) {
/*  96 */         localSettableBeanProperty = localSettableBeanProperty.withValueDeserializer(paramDeserializationContext.findContextualValueDeserializer(localSettableBeanProperty.getType(), localSettableBeanProperty));
/*     */       }
/*  98 */       arrayOfSettableBeanProperty[j] = localSettableBeanProperty;
/*     */       
/*     */ 
/* 101 */       JsonDeserializer localJsonDeserializer = localSettableBeanProperty.getValueDeserializer();
/* 102 */       Object localObject = localJsonDeserializer == null ? null : localJsonDeserializer.getNullValue();
/* 103 */       if ((localObject == null) && (localSettableBeanProperty.getType().isPrimitive())) {
/* 104 */         localObject = ClassUtil.defaultValue(localSettableBeanProperty.getType().getRawClass());
/*     */       }
/* 106 */       if (localObject != null) {
/* 107 */         if (arrayOfObject == null) {
/* 108 */           arrayOfObject = new Object[i];
/*     */         }
/* 110 */         arrayOfObject[j] = localObject;
/*     */       }
/*     */     }
/* 113 */     return new PropertyBasedCreator(paramValueInstantiator, arrayOfSettableBeanProperty, arrayOfObject);
/*     */   }
/*     */   
/*     */   public void assignDeserializer(SettableBeanProperty paramSettableBeanProperty, JsonDeserializer<Object> paramJsonDeserializer) {
/* 117 */     paramSettableBeanProperty = paramSettableBeanProperty.withValueDeserializer(paramJsonDeserializer);
/* 118 */     this._properties.put(paramSettableBeanProperty.getName(), paramSettableBeanProperty);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<SettableBeanProperty> properties()
/*     */   {
/* 128 */     return this._properties.values();
/*     */   }
/*     */   
/*     */   public SettableBeanProperty findCreatorProperty(String paramString) {
/* 132 */     return (SettableBeanProperty)this._properties.get(paramString);
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
/*     */   public PropertyValueBuffer startBuilding(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, ObjectIdReader paramObjectIdReader)
/*     */   {
/* 149 */     PropertyValueBuffer localPropertyValueBuffer = new PropertyValueBuffer(paramJsonParser, paramDeserializationContext, this._propertyCount, paramObjectIdReader);
/* 150 */     if (this._propertiesWithInjectables != null) {
/* 151 */       localPropertyValueBuffer.inject(this._propertiesWithInjectables);
/*     */     }
/* 153 */     return localPropertyValueBuffer;
/*     */   }
/*     */   
/*     */   public Object build(DeserializationContext paramDeserializationContext, PropertyValueBuffer paramPropertyValueBuffer) throws IOException
/*     */   {
/* 158 */     Object localObject = this._valueInstantiator.createFromObjectWith(paramDeserializationContext, paramPropertyValueBuffer.getParameters(this._defaultValues));
/*     */     
/* 160 */     localObject = paramPropertyValueBuffer.handleIdValue(paramDeserializationContext, localObject);
/*     */     
/*     */ 
/* 163 */     for (PropertyValue localPropertyValue = paramPropertyValueBuffer.buffered(); localPropertyValue != null; localPropertyValue = localPropertyValue.next) {
/* 164 */       localPropertyValue.assign(localObject);
/*     */     }
/* 166 */     return localObject;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/PropertyBasedCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */