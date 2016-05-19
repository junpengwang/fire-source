/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.util.InternCache;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.NullProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.impl.FailingDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ViewMatcher;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SettableBeanProperty
/*     */   implements BeanProperty, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1026580169193933453L;
/*  37 */   protected static final JsonDeserializer<Object> MISSING_VALUE_DESERIALIZER = new FailingDeserializer("No _valueDeserializer assigned");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final String _propName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _type;
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
/*     */   protected final transient Annotations _contextAnnotations;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonDeserializer<Object> _valueDeserializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final NullProvider _nullProvider;
/*     */   
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
/*     */   protected String _managedReferenceName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ViewMatcher _viewMatcher;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 126 */   protected int _propertyIndex = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SettableBeanProperty(BeanPropertyDefinition paramBeanPropertyDefinition, JavaType paramJavaType, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations)
/*     */   {
/* 137 */     this(paramBeanPropertyDefinition.getName(), paramJavaType, paramBeanPropertyDefinition.getWrapperName(), paramTypeDeserializer, paramAnnotations, paramBeanPropertyDefinition.isRequired());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected SettableBeanProperty(String paramString, JavaType paramJavaType, PropertyName paramPropertyName, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations)
/*     */   {
/* 145 */     this(paramString, paramJavaType, paramPropertyName, paramTypeDeserializer, paramAnnotations, false);
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
/*     */   protected SettableBeanProperty(String paramString, JavaType paramJavaType, PropertyName paramPropertyName, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations, boolean paramBoolean)
/*     */   {
/* 159 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 160 */       this._propName = "";
/*     */     } else {
/* 162 */       this._propName = InternCache.instance.intern(paramString);
/*     */     }
/* 164 */     this._type = paramJavaType;
/* 165 */     this._wrapperName = paramPropertyName;
/* 166 */     this._isRequired = paramBoolean;
/* 167 */     this._contextAnnotations = paramAnnotations;
/* 168 */     this._viewMatcher = null;
/* 169 */     this._nullProvider = null;
/*     */     
/*     */ 
/* 172 */     if (paramTypeDeserializer != null) {
/* 173 */       paramTypeDeserializer = paramTypeDeserializer.forProperty(this);
/*     */     }
/* 175 */     this._valueTypeDeserializer = paramTypeDeserializer;
/* 176 */     this._valueDeserializer = MISSING_VALUE_DESERIALIZER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SettableBeanProperty(SettableBeanProperty paramSettableBeanProperty)
/*     */   {
/* 184 */     this._propName = paramSettableBeanProperty._propName;
/* 185 */     this._type = paramSettableBeanProperty._type;
/* 186 */     this._wrapperName = paramSettableBeanProperty._wrapperName;
/* 187 */     this._isRequired = paramSettableBeanProperty._isRequired;
/* 188 */     this._contextAnnotations = paramSettableBeanProperty._contextAnnotations;
/* 189 */     this._valueDeserializer = paramSettableBeanProperty._valueDeserializer;
/* 190 */     this._valueTypeDeserializer = paramSettableBeanProperty._valueTypeDeserializer;
/* 191 */     this._nullProvider = paramSettableBeanProperty._nullProvider;
/* 192 */     this._managedReferenceName = paramSettableBeanProperty._managedReferenceName;
/* 193 */     this._propertyIndex = paramSettableBeanProperty._propertyIndex;
/* 194 */     this._viewMatcher = paramSettableBeanProperty._viewMatcher;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SettableBeanProperty(SettableBeanProperty paramSettableBeanProperty, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 203 */     this._propName = paramSettableBeanProperty._propName;
/* 204 */     this._type = paramSettableBeanProperty._type;
/* 205 */     this._wrapperName = paramSettableBeanProperty._wrapperName;
/* 206 */     this._isRequired = paramSettableBeanProperty._isRequired;
/* 207 */     this._contextAnnotations = paramSettableBeanProperty._contextAnnotations;
/* 208 */     this._valueTypeDeserializer = paramSettableBeanProperty._valueTypeDeserializer;
/* 209 */     this._managedReferenceName = paramSettableBeanProperty._managedReferenceName;
/* 210 */     this._propertyIndex = paramSettableBeanProperty._propertyIndex;
/*     */     
/* 212 */     if (paramJsonDeserializer == null) {
/* 213 */       this._nullProvider = null;
/* 214 */       this._valueDeserializer = MISSING_VALUE_DESERIALIZER;
/*     */     } else {
/* 216 */       Object localObject = paramJsonDeserializer.getNullValue();
/* 217 */       this._nullProvider = (localObject == null ? null : new NullProvider(this._type, localObject));
/* 218 */       this._valueDeserializer = paramJsonDeserializer;
/*     */     }
/* 220 */     this._viewMatcher = paramSettableBeanProperty._viewMatcher;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SettableBeanProperty(SettableBeanProperty paramSettableBeanProperty, String paramString)
/*     */   {
/* 228 */     this._propName = paramString;
/* 229 */     this._type = paramSettableBeanProperty._type;
/* 230 */     this._wrapperName = paramSettableBeanProperty._wrapperName;
/* 231 */     this._isRequired = paramSettableBeanProperty._isRequired;
/* 232 */     this._contextAnnotations = paramSettableBeanProperty._contextAnnotations;
/* 233 */     this._valueDeserializer = paramSettableBeanProperty._valueDeserializer;
/* 234 */     this._valueTypeDeserializer = paramSettableBeanProperty._valueTypeDeserializer;
/* 235 */     this._nullProvider = paramSettableBeanProperty._nullProvider;
/* 236 */     this._managedReferenceName = paramSettableBeanProperty._managedReferenceName;
/* 237 */     this._propertyIndex = paramSettableBeanProperty._propertyIndex;
/* 238 */     this._viewMatcher = paramSettableBeanProperty._viewMatcher;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract SettableBeanProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract SettableBeanProperty withName(String paramString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setManagedReferenceName(String paramString)
/*     */   {
/* 266 */     this._managedReferenceName = paramString;
/*     */   }
/*     */   
/*     */   public void setViews(Class<?>[] paramArrayOfClass) {
/* 270 */     if (paramArrayOfClass == null) {
/* 271 */       this._viewMatcher = null;
/*     */     } else {
/* 273 */       this._viewMatcher = ViewMatcher.construct(paramArrayOfClass);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void assignIndex(int paramInt)
/*     */   {
/* 281 */     if (this._propertyIndex != -1) {
/* 282 */       throw new IllegalStateException("Property '" + getName() + "' already had index (" + this._propertyIndex + "), trying to assign " + paramInt);
/*     */     }
/* 284 */     this._propertyIndex = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getName()
/*     */   {
/* 294 */     return this._propName;
/*     */   }
/*     */   
/* 297 */   public boolean isRequired() { return this._isRequired; }
/*     */   
/*     */   public JavaType getType() {
/* 300 */     return this._type;
/*     */   }
/*     */   
/*     */   public PropertyName getWrapperName() {
/* 304 */     return this._wrapperName;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract <A extends Annotation> A getAnnotation(Class<A> paramClass);
/*     */   
/*     */ 
/*     */   public abstract AnnotatedMember getMember();
/*     */   
/*     */   public <A extends Annotation> A getContextAnnotation(Class<A> paramClass)
/*     */   {
/* 315 */     return this._contextAnnotations.get(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */   public void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
/*     */     throws JsonMappingException
/*     */   {
/* 322 */     if (isRequired()) {
/* 323 */       paramJsonObjectFormatVisitor.property(this);
/*     */     } else {
/* 325 */       paramJsonObjectFormatVisitor.optionalProperty(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Class<?> getDeclaringClass()
/*     */   {
/* 336 */     return getMember().getDeclaringClass();
/*     */   }
/*     */   
/* 339 */   public String getManagedReferenceName() { return this._managedReferenceName; }
/*     */   
/*     */   public boolean hasValueDeserializer() {
/* 342 */     return (this._valueDeserializer != null) && (this._valueDeserializer != MISSING_VALUE_DESERIALIZER);
/*     */   }
/*     */   
/* 345 */   public boolean hasValueTypeDeserializer() { return this._valueTypeDeserializer != null; }
/*     */   
/*     */   public JsonDeserializer<Object> getValueDeserializer() {
/* 348 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 349 */     if (localJsonDeserializer == MISSING_VALUE_DESERIALIZER) {
/* 350 */       return null;
/*     */     }
/* 352 */     return localJsonDeserializer;
/*     */   }
/*     */   
/* 355 */   public TypeDeserializer getValueTypeDeserializer() { return this._valueTypeDeserializer; }
/*     */   
/*     */   public boolean visibleInView(Class<?> paramClass) {
/* 358 */     return (this._viewMatcher == null) || (this._viewMatcher.isVisibleForView(paramClass));
/*     */   }
/*     */   
/* 361 */   public boolean hasViews() { return this._viewMatcher != null; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getPropertyIndex()
/*     */   {
/* 370 */     return this._propertyIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCreatorIndex()
/*     */   {
/* 378 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getInjectableValueId()
/*     */   {
/* 384 */     return null;
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
/*     */   public abstract void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException;
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
/*     */   public abstract Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException;
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
/*     */   public abstract void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException;
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
/*     */   public abstract Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException;
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
/*     */   public final Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 456 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/* 458 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 459 */       return this._nullProvider == null ? null : this._nullProvider.nullValue(paramDeserializationContext);
/*     */     }
/* 461 */     if (this._valueTypeDeserializer != null) {
/* 462 */       return this._valueDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, this._valueTypeDeserializer);
/*     */     }
/* 464 */     return this._valueDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
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
/*     */   protected void _throwAsIOE(Exception paramException, Object paramObject)
/*     */     throws IOException
/*     */   {
/* 480 */     if ((paramException instanceof IllegalArgumentException)) {
/* 481 */       String str1 = paramObject == null ? "[NULL]" : paramObject.getClass().getName();
/* 482 */       StringBuilder localStringBuilder = new StringBuilder("Problem deserializing property '").append(getName());
/* 483 */       localStringBuilder.append("' (expected type: ").append(getType());
/* 484 */       localStringBuilder.append("; actual type: ").append(str1).append(")");
/* 485 */       String str2 = paramException.getMessage();
/* 486 */       if (str2 != null) {
/* 487 */         localStringBuilder.append(", problem: ").append(str2);
/*     */       } else {
/* 489 */         localStringBuilder.append(" (no error message provided)");
/*     */       }
/* 491 */       throw new JsonMappingException(localStringBuilder.toString(), null, paramException);
/*     */     }
/* 493 */     _throwAsIOE(paramException);
/*     */   }
/*     */   
/*     */   protected IOException _throwAsIOE(Exception paramException)
/*     */     throws IOException
/*     */   {
/* 499 */     if ((paramException instanceof IOException)) {
/* 500 */       throw ((IOException)paramException);
/*     */     }
/* 502 */     if ((paramException instanceof RuntimeException)) {
/* 503 */       throw ((RuntimeException)paramException);
/*     */     }
/*     */     
/* 506 */     Object localObject = paramException;
/* 507 */     while (((Throwable)localObject).getCause() != null) {
/* 508 */       localObject = ((Throwable)localObject).getCause();
/*     */     }
/* 510 */     throw new JsonMappingException(((Throwable)localObject).getMessage(), null, (Throwable)localObject);
/*     */   }
/*     */   
/* 513 */   public String toString() { return "[property '" + getName() + "']"; }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/SettableBeanProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */