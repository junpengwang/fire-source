/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class CollectionDeserializer
/*     */   extends ContainerDeserializerBase<Collection<Object>>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = -2003828398549708958L;
/*     */   protected final JavaType _collectionType;
/*     */   protected final JsonDeserializer<Object> _valueDeserializer;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final JsonDeserializer<Object> _delegateDeserializer;
/*     */   
/*     */   public CollectionDeserializer(JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer, TypeDeserializer paramTypeDeserializer, ValueInstantiator paramValueInstantiator)
/*     */   {
/*  71 */     this(paramJavaType, paramJsonDeserializer, paramTypeDeserializer, paramValueInstantiator, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CollectionDeserializer(JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer1, TypeDeserializer paramTypeDeserializer, ValueInstantiator paramValueInstantiator, JsonDeserializer<Object> paramJsonDeserializer2)
/*     */   {
/*  82 */     super(paramJavaType.getRawClass());
/*  83 */     this._collectionType = paramJavaType;
/*  84 */     this._valueDeserializer = paramJsonDeserializer1;
/*  85 */     this._valueTypeDeserializer = paramTypeDeserializer;
/*  86 */     this._valueInstantiator = paramValueInstantiator;
/*  87 */     this._delegateDeserializer = paramJsonDeserializer2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CollectionDeserializer(CollectionDeserializer paramCollectionDeserializer)
/*     */   {
/*  96 */     super(paramCollectionDeserializer._valueClass);
/*  97 */     this._collectionType = paramCollectionDeserializer._collectionType;
/*  98 */     this._valueDeserializer = paramCollectionDeserializer._valueDeserializer;
/*  99 */     this._valueTypeDeserializer = paramCollectionDeserializer._valueTypeDeserializer;
/* 100 */     this._valueInstantiator = paramCollectionDeserializer._valueInstantiator;
/* 101 */     this._delegateDeserializer = paramCollectionDeserializer._delegateDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CollectionDeserializer withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, TypeDeserializer paramTypeDeserializer)
/*     */   {
/* 111 */     if ((paramJsonDeserializer1 == this._delegateDeserializer) && (paramJsonDeserializer2 == this._valueDeserializer) && (paramTypeDeserializer == this._valueTypeDeserializer)) {
/* 112 */       return this;
/*     */     }
/* 114 */     return new CollectionDeserializer(this._collectionType, paramJsonDeserializer2, paramTypeDeserializer, this._valueInstantiator, paramJsonDeserializer1);
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
/*     */   public CollectionDeserializer createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 136 */     JsonDeserializer localJsonDeserializer = null;
/* 137 */     if ((this._valueInstantiator != null) && (this._valueInstantiator.canCreateUsingDelegate())) {
/* 138 */       localObject = this._valueInstantiator.getDelegateType(paramDeserializationContext.getConfig());
/* 139 */       if (localObject == null) {
/* 140 */         throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._collectionType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
/*     */       }
/*     */       
/*     */ 
/* 144 */       localJsonDeserializer = findDeserializer(paramDeserializationContext, (JavaType)localObject, paramBeanProperty);
/*     */     }
/*     */     
/* 147 */     Object localObject = this._valueDeserializer;
/*     */     
/* 149 */     localObject = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, (JsonDeserializer)localObject);
/* 150 */     if (localObject == null) {
/* 151 */       localObject = paramDeserializationContext.findContextualValueDeserializer(this._collectionType.getContentType(), paramBeanProperty);
/*     */ 
/*     */     }
/* 154 */     else if ((localObject instanceof ContextualDeserializer)) {
/* 155 */       localObject = ((ContextualDeserializer)localObject).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/*     */ 
/* 159 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/* 160 */     if (localTypeDeserializer != null) {
/* 161 */       localTypeDeserializer = localTypeDeserializer.forProperty(paramBeanProperty);
/*     */     }
/* 163 */     return withResolved(localJsonDeserializer, (JsonDeserializer)localObject, localTypeDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType getContentType()
/*     */   {
/* 174 */     return this._collectionType.getContentType();
/*     */   }
/*     */   
/*     */   public JsonDeserializer<Object> getContentDeserializer()
/*     */   {
/* 179 */     return this._valueDeserializer;
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
/*     */   public Collection<Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 193 */     if (this._delegateDeserializer != null) {
/* 194 */       return (Collection)this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */     if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
/* 202 */       String str = paramJsonParser.getText();
/* 203 */       if (str.length() == 0) {
/* 204 */         return (Collection)this._valueInstantiator.createFromString(paramDeserializationContext, str);
/*     */       }
/*     */     }
/* 207 */     return deserialize(paramJsonParser, paramDeserializationContext, (Collection)this._valueInstantiator.createUsingDefault(paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<Object> paramCollection)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 216 */     if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 217 */       return handleNonArray(paramJsonParser, paramDeserializationContext, paramCollection);
/*     */     }
/*     */     
/* 220 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/*     */     
/* 222 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/*     */     JsonToken localJsonToken;
/* 224 */     while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */     {
/*     */       Object localObject;
/* 227 */       if (localJsonToken == JsonToken.VALUE_NULL) {
/* 228 */         localObject = null;
/* 229 */       } else if (localTypeDeserializer == null) {
/* 230 */         localObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */       } else {
/* 232 */         localObject = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */       }
/* 234 */       paramCollection.add(localObject);
/*     */     }
/* 236 */     return paramCollection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 245 */     return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Collection<Object> handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<Object> paramCollection)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 258 */     if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 259 */       throw paramDeserializationContext.mappingException(this._collectionType.getRawClass());
/*     */     }
/* 261 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 262 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/* 263 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/*     */     Object localObject;
/*     */     
/* 267 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 268 */       localObject = null;
/* 269 */     } else if (localTypeDeserializer == null) {
/* 270 */       localObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */     } else {
/* 272 */       localObject = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */     }
/* 274 */     paramCollection.add(localObject);
/* 275 */     return paramCollection;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/CollectionDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */