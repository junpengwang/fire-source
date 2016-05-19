/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanPropertyWriter
/*     */   implements BeanProperty
/*     */ {
/*  38 */   public static final Object MARKER_FOR_EMPTY = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final AnnotatedMember _member;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Annotations _contextAnnotations;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _declaredType;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Method _accessorMethod;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Field _field;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HashMap<Object, Object> _internalSettings;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SerializedString _name;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final PropertyName _wrapperName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _cfgSerializationType;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _serializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _nullSerializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected PropertySerializerMap _dynamicSerializers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean _suppressNulls;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Object _suppressableValue;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Class<?>[] _includeInViews;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected TypeSerializer _typeSerializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JavaType _nonTrivialBaseType;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean _isRequired;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanPropertyWriter(BeanPropertyDefinition paramBeanPropertyDefinition, AnnotatedMember paramAnnotatedMember, Annotations paramAnnotations, JavaType paramJavaType1, JsonSerializer<?> paramJsonSerializer, TypeSerializer paramTypeSerializer, JavaType paramJavaType2, boolean paramBoolean, Object paramObject)
/*     */   {
/* 191 */     this._member = paramAnnotatedMember;
/* 192 */     this._contextAnnotations = paramAnnotations;
/* 193 */     this._name = new SerializedString(paramBeanPropertyDefinition.getName());
/* 194 */     this._wrapperName = paramBeanPropertyDefinition.getWrapperName();
/* 195 */     this._declaredType = paramJavaType1;
/* 196 */     this._serializer = paramJsonSerializer;
/* 197 */     this._dynamicSerializers = (paramJsonSerializer == null ? PropertySerializerMap.emptyMap() : null);
/* 198 */     this._typeSerializer = paramTypeSerializer;
/* 199 */     this._cfgSerializationType = paramJavaType2;
/* 200 */     this._isRequired = paramBeanPropertyDefinition.isRequired();
/*     */     
/* 202 */     if ((paramAnnotatedMember instanceof AnnotatedField)) {
/* 203 */       this._accessorMethod = null;
/* 204 */       this._field = ((Field)paramAnnotatedMember.getMember());
/* 205 */     } else if ((paramAnnotatedMember instanceof AnnotatedMethod)) {
/* 206 */       this._accessorMethod = ((Method)paramAnnotatedMember.getMember());
/* 207 */       this._field = null;
/*     */     } else {
/* 209 */       throw new IllegalArgumentException("Can not pass member of type " + paramAnnotatedMember.getClass().getName());
/*     */     }
/* 211 */     this._suppressNulls = paramBoolean;
/* 212 */     this._suppressableValue = paramObject;
/* 213 */     this._includeInViews = paramBeanPropertyDefinition.findViews();
/*     */     
/*     */ 
/* 216 */     this._nullSerializer = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected BeanPropertyWriter(BeanPropertyWriter paramBeanPropertyWriter)
/*     */   {
/* 223 */     this(paramBeanPropertyWriter, paramBeanPropertyWriter._name);
/*     */   }
/*     */   
/*     */   protected BeanPropertyWriter(BeanPropertyWriter paramBeanPropertyWriter, SerializedString paramSerializedString)
/*     */   {
/* 228 */     this._name = paramSerializedString;
/* 229 */     this._wrapperName = paramBeanPropertyWriter._wrapperName;
/*     */     
/* 231 */     this._member = paramBeanPropertyWriter._member;
/* 232 */     this._contextAnnotations = paramBeanPropertyWriter._contextAnnotations;
/* 233 */     this._declaredType = paramBeanPropertyWriter._declaredType;
/* 234 */     this._accessorMethod = paramBeanPropertyWriter._accessorMethod;
/* 235 */     this._field = paramBeanPropertyWriter._field;
/* 236 */     this._serializer = paramBeanPropertyWriter._serializer;
/* 237 */     this._nullSerializer = paramBeanPropertyWriter._nullSerializer;
/*     */     
/* 239 */     if (paramBeanPropertyWriter._internalSettings != null) {
/* 240 */       this._internalSettings = new HashMap(paramBeanPropertyWriter._internalSettings);
/*     */     }
/* 242 */     this._cfgSerializationType = paramBeanPropertyWriter._cfgSerializationType;
/* 243 */     this._dynamicSerializers = paramBeanPropertyWriter._dynamicSerializers;
/* 244 */     this._suppressNulls = paramBeanPropertyWriter._suppressNulls;
/* 245 */     this._suppressableValue = paramBeanPropertyWriter._suppressableValue;
/* 246 */     this._includeInViews = paramBeanPropertyWriter._includeInViews;
/* 247 */     this._typeSerializer = paramBeanPropertyWriter._typeSerializer;
/* 248 */     this._nonTrivialBaseType = paramBeanPropertyWriter._nonTrivialBaseType;
/* 249 */     this._isRequired = paramBeanPropertyWriter._isRequired;
/*     */   }
/*     */   
/*     */   public BeanPropertyWriter rename(NameTransformer paramNameTransformer) {
/* 253 */     String str = paramNameTransformer.transform(this._name.getValue());
/* 254 */     if (str.equals(this._name.toString())) {
/* 255 */       return this;
/*     */     }
/* 257 */     return new BeanPropertyWriter(this, new SerializedString(str));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void assignSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 268 */     if ((this._serializer != null) && (this._serializer != paramJsonSerializer)) {
/* 269 */       throw new IllegalStateException("Can not override serializer");
/*     */     }
/* 271 */     this._serializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void assignNullSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 282 */     if ((this._nullSerializer != null) && (this._nullSerializer != paramJsonSerializer)) {
/* 283 */       throw new IllegalStateException("Can not override null serializer");
/*     */     }
/* 285 */     this._nullSerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanPropertyWriter unwrappingWriter(NameTransformer paramNameTransformer)
/*     */   {
/* 293 */     return new UnwrappingBeanPropertyWriter(this, paramNameTransformer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNonTrivialBaseType(JavaType paramJavaType)
/*     */   {
/* 302 */     this._nonTrivialBaseType = paramJavaType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 313 */     return this._name.getValue();
/*     */   }
/*     */   
/*     */   public JavaType getType()
/*     */   {
/* 318 */     return this._declaredType;
/*     */   }
/*     */   
/*     */   public PropertyName getWrapperName()
/*     */   {
/* 323 */     return this._wrapperName;
/*     */   }
/*     */   
/*     */   public boolean isRequired()
/*     */   {
/* 328 */     return this._isRequired;
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/* 333 */     return this._member.getAnnotation(paramClass);
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getContextAnnotation(Class<A> paramClass)
/*     */   {
/* 338 */     return this._contextAnnotations.get(paramClass);
/*     */   }
/*     */   
/*     */   public AnnotatedMember getMember()
/*     */   {
/* 343 */     return this._member;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
/*     */     throws JsonMappingException
/*     */   {
/* 351 */     if (paramJsonObjectFormatVisitor != null) {
/* 352 */       if (isRequired()) {
/* 353 */         paramJsonObjectFormatVisitor.property(this);
/*     */       } else {
/* 355 */         paramJsonObjectFormatVisitor.optionalProperty(this);
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
/*     */   public Object getInternalSetting(Object paramObject)
/*     */   {
/* 374 */     if (this._internalSettings == null) {
/* 375 */       return null;
/*     */     }
/* 377 */     return this._internalSettings.get(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object setInternalSetting(Object paramObject1, Object paramObject2)
/*     */   {
/* 387 */     if (this._internalSettings == null) {
/* 388 */       this._internalSettings = new HashMap();
/*     */     }
/* 390 */     return this._internalSettings.put(paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object removeInternalSetting(Object paramObject)
/*     */   {
/* 400 */     Object localObject = null;
/* 401 */     if (this._internalSettings != null) {
/* 402 */       localObject = this._internalSettings.remove(paramObject);
/*     */       
/* 404 */       if (this._internalSettings.size() == 0) {
/* 405 */         this._internalSettings = null;
/*     */       }
/*     */     }
/* 408 */     return localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 417 */   public SerializedString getSerializedName() { return this._name; }
/*     */   
/* 419 */   public boolean hasSerializer() { return this._serializer != null; }
/* 420 */   public boolean hasNullSerializer() { return this._nullSerializer != null; }
/*     */   
/* 422 */   public boolean willSuppressNulls() { return this._suppressNulls; }
/*     */   
/*     */   public JsonSerializer<Object> getSerializer()
/*     */   {
/* 426 */     return this._serializer;
/*     */   }
/*     */   
/*     */   public JavaType getSerializationType() {
/* 430 */     return this._cfgSerializationType;
/*     */   }
/*     */   
/*     */   public Class<?> getRawSerializationType() {
/* 434 */     return this._cfgSerializationType == null ? null : this._cfgSerializationType.getRawClass();
/*     */   }
/*     */   
/*     */   public Class<?> getPropertyType()
/*     */   {
/* 439 */     if (this._accessorMethod != null) {
/* 440 */       return this._accessorMethod.getReturnType();
/*     */     }
/* 442 */     return this._field.getType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getGenericPropertyType()
/*     */   {
/* 452 */     if (this._accessorMethod != null) {
/* 453 */       return this._accessorMethod.getGenericReturnType();
/*     */     }
/* 455 */     return this._field.getGenericType();
/*     */   }
/*     */   
/* 458 */   public Class<?>[] getViews() { return this._includeInViews; }
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
/*     */   protected boolean isRequired(AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/* 473 */     return this._isRequired;
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
/*     */   public void depositSchemaProperty(ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 498 */     JavaType localJavaType = getSerializationType();
/*     */     
/* 500 */     Class localClass1 = localJavaType == null ? getGenericPropertyType() : localJavaType.getRawClass();
/*     */     
/*     */ 
/* 503 */     JsonSerializer localJsonSerializer = getSerializer();
/* 504 */     if (localJsonSerializer == null) {
/* 505 */       Class localClass2 = getRawSerializationType();
/* 506 */       if (localClass2 == null) {
/* 507 */         localClass2 = getPropertyType();
/*     */       }
/* 509 */       localJsonSerializer = paramSerializerProvider.findValueSerializer(localClass2, this);
/*     */     }
/* 511 */     boolean bool = !isRequired();
/* 512 */     JsonNode localJsonNode; if ((localJsonSerializer instanceof SchemaAware)) {
/* 513 */       localJsonNode = ((SchemaAware)localJsonSerializer).getSchema(paramSerializerProvider, localClass1, bool);
/*     */     } else {
/* 515 */       localJsonNode = JsonSchema.getDefaultSchemaNode();
/*     */     }
/* 517 */     paramObjectNode.put(getName(), localJsonNode);
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
/*     */   public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws Exception
/*     */   {
/* 534 */     Object localObject = get(paramObject);
/*     */     
/* 536 */     if (localObject == null) {
/* 537 */       if (this._nullSerializer != null) {
/* 538 */         paramJsonGenerator.writeFieldName(this._name);
/* 539 */         this._nullSerializer.serialize(null, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/* 541 */       return;
/*     */     }
/*     */     
/* 544 */     JsonSerializer localJsonSerializer = this._serializer;
/* 545 */     if (localJsonSerializer == null) {
/* 546 */       Class localClass = localObject.getClass();
/* 547 */       PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/* 548 */       localJsonSerializer = localPropertySerializerMap.serializerFor(localClass);
/* 549 */       if (localJsonSerializer == null) {
/* 550 */         localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
/*     */       }
/*     */     }
/*     */     
/* 554 */     if (this._suppressableValue != null) {
/* 555 */       if (MARKER_FOR_EMPTY == this._suppressableValue) {
/* 556 */         if (!localJsonSerializer.isEmpty(localObject)) {}
/*     */ 
/*     */       }
/* 559 */       else if (this._suppressableValue.equals(localObject)) {
/* 560 */         return;
/*     */       }
/*     */     }
/*     */     
/* 564 */     if (localObject == paramObject) {
/* 565 */       _handleSelfReference(paramObject, localJsonSerializer);
/*     */     }
/* 567 */     paramJsonGenerator.writeFieldName(this._name);
/* 568 */     if (this._typeSerializer == null) {
/* 569 */       localJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 571 */       localJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
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
/*     */   public void serializeAsColumn(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws Exception
/*     */   {
/* 585 */     Object localObject = get(paramObject);
/* 586 */     if (localObject == null) {
/* 587 */       if (this._nullSerializer != null) {
/* 588 */         this._nullSerializer.serialize(null, paramJsonGenerator, paramSerializerProvider);
/*     */       } else {
/* 590 */         paramJsonGenerator.writeNull();
/*     */       }
/* 592 */       return;
/*     */     }
/*     */     
/* 595 */     JsonSerializer localJsonSerializer = this._serializer;
/* 596 */     if (localJsonSerializer == null) {
/* 597 */       Class localClass = localObject.getClass();
/* 598 */       PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/* 599 */       localJsonSerializer = localPropertySerializerMap.serializerFor(localClass);
/* 600 */       if (localJsonSerializer == null) {
/* 601 */         localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
/*     */       }
/*     */     }
/*     */     
/* 605 */     if (this._suppressableValue != null) {
/* 606 */       if (MARKER_FOR_EMPTY == this._suppressableValue) {
/* 607 */         if (localJsonSerializer.isEmpty(localObject)) {
/* 608 */           serializeAsPlaceholder(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */       }
/* 611 */       else if (this._suppressableValue.equals(localObject)) {
/* 612 */         serializeAsPlaceholder(paramObject, paramJsonGenerator, paramSerializerProvider);
/* 613 */         return;
/*     */       }
/*     */     }
/*     */     
/* 617 */     if (localObject == paramObject) {
/* 618 */       _handleSelfReference(paramObject, localJsonSerializer);
/*     */     }
/* 620 */     if (this._typeSerializer == null) {
/* 621 */       localJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 623 */       localJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
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
/*     */   public void serializeAsPlaceholder(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws Exception
/*     */   {
/* 638 */     if (this._nullSerializer != null) {
/* 639 */       this._nullSerializer.serialize(null, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 641 */       paramJsonGenerator.writeNull();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/*     */     PropertySerializerMap.SerializerAndMapResult localSerializerAndMapResult;
/*     */     
/*     */ 
/*     */ 
/* 655 */     if (this._nonTrivialBaseType != null) {
/* 656 */       JavaType localJavaType = paramSerializerProvider.constructSpecializedType(this._nonTrivialBaseType, paramClass);
/* 657 */       localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(localJavaType, paramSerializerProvider, this);
/*     */     } else {
/* 659 */       localSerializerAndMapResult = paramPropertySerializerMap.findAndAddSerializer(paramClass, paramSerializerProvider, this);
/*     */     }
/*     */     
/* 662 */     if (paramPropertySerializerMap != localSerializerAndMapResult.map) {
/* 663 */       this._dynamicSerializers = localSerializerAndMapResult.map;
/*     */     }
/* 665 */     return localSerializerAndMapResult.serializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Object get(Object paramObject)
/*     */     throws Exception
/*     */   {
/* 678 */     if (this._accessorMethod != null) {
/* 679 */       return this._accessorMethod.invoke(paramObject, new Object[0]);
/*     */     }
/* 681 */     return this._field.get(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _handleSelfReference(Object paramObject, JsonSerializer<?> paramJsonSerializer)
/*     */     throws JsonMappingException
/*     */   {
/* 690 */     if (paramJsonSerializer.usesObjectId()) {
/* 691 */       return;
/*     */     }
/* 693 */     throw new JsonMappingException("Direct self-reference leading to cycle");
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 699 */     StringBuilder localStringBuilder = new StringBuilder(40);
/* 700 */     localStringBuilder.append("property '").append(getName()).append("' (");
/* 701 */     if (this._accessorMethod != null) {
/* 702 */       localStringBuilder.append("via method ").append(this._accessorMethod.getDeclaringClass().getName()).append("#").append(this._accessorMethod.getName());
/*     */     } else {
/* 704 */       localStringBuilder.append("field \"").append(this._field.getDeclaringClass().getName()).append("#").append(this._field.getName());
/*     */     }
/* 706 */     if (this._serializer == null) {
/* 707 */       localStringBuilder.append(", no static serializer");
/*     */     } else {
/* 709 */       localStringBuilder.append(", static serializer of type " + this._serializer.getClass().getName());
/*     */     }
/* 711 */     localStringBuilder.append(')');
/* 712 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/BeanPropertyWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */