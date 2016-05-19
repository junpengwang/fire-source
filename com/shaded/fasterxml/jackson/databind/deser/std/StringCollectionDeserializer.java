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
/*     */ @JacksonStdImpl
/*     */ public final class StringCollectionDeserializer
/*     */   extends ContainerDeserializerBase<Collection<String>>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JavaType _collectionType;
/*     */   protected final JsonDeserializer<String> _valueDeserializer;
/*     */   protected final ValueInstantiator _valueInstantiator;
/*     */   protected final JsonDeserializer<Object> _delegateDeserializer;
/*     */   
/*     */   public StringCollectionDeserializer(JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer, ValueInstantiator paramValueInstantiator)
/*     */   {
/*  60 */     this(paramJavaType, paramValueInstantiator, null, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StringCollectionDeserializer(JavaType paramJavaType, ValueInstantiator paramValueInstantiator, JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2)
/*     */   {
/*  68 */     super(paramJavaType.getRawClass());
/*  69 */     this._collectionType = paramJavaType;
/*  70 */     this._valueDeserializer = paramJsonDeserializer2;
/*  71 */     this._valueInstantiator = paramValueInstantiator;
/*  72 */     this._delegateDeserializer = paramJsonDeserializer1;
/*     */   }
/*     */   
/*     */ 
/*     */   protected StringCollectionDeserializer withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2)
/*     */   {
/*  78 */     if ((this._valueDeserializer == paramJsonDeserializer2) && (this._delegateDeserializer == paramJsonDeserializer1)) {
/*  79 */       return this;
/*     */     }
/*  81 */     return new StringCollectionDeserializer(this._collectionType, this._valueInstantiator, paramJsonDeserializer1, paramJsonDeserializer2);
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
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  95 */     JsonDeserializer localJsonDeserializer = null;
/*  96 */     if (this._valueInstantiator != null) {
/*  97 */       localObject = this._valueInstantiator.getDelegateCreator();
/*  98 */       if (localObject != null) {
/*  99 */         JavaType localJavaType = this._valueInstantiator.getDelegateType(paramDeserializationContext.getConfig());
/* 100 */         localJsonDeserializer = findDeserializer(paramDeserializationContext, localJavaType, paramBeanProperty);
/*     */       }
/*     */     }
/* 103 */     Object localObject = this._valueDeserializer;
/* 104 */     if (localObject == null)
/*     */     {
/* 106 */       localObject = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, (JsonDeserializer)localObject);
/* 107 */       if (localObject == null)
/*     */       {
/* 109 */         localObject = paramDeserializationContext.findContextualValueDeserializer(this._collectionType.getContentType(), paramBeanProperty);
/*     */       }
/*     */     }
/* 112 */     else if ((localObject instanceof ContextualDeserializer)) {
/* 113 */       localObject = ((ContextualDeserializer)localObject).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/* 116 */     if (isDefaultDeserializer((JsonDeserializer)localObject)) {
/* 117 */       localObject = null;
/*     */     }
/* 119 */     return withResolved(localJsonDeserializer, (JsonDeserializer)localObject);
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
/* 130 */     return this._collectionType.getContentType();
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonDeserializer<Object> getContentDeserializer()
/*     */   {
/* 136 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 137 */     return localJsonDeserializer;
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
/*     */   public Collection<String> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 151 */     if (this._delegateDeserializer != null) {
/* 152 */       return (Collection)this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */     
/* 155 */     Collection localCollection = (Collection)this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/* 156 */     return deserialize(paramJsonParser, paramDeserializationContext, localCollection);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<String> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<String> paramCollection)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 165 */     if (!paramJsonParser.isExpectedStartArrayToken()) {
/* 166 */       return handleNonArray(paramJsonParser, paramDeserializationContext, paramCollection);
/*     */     }
/*     */     
/* 169 */     if (this._valueDeserializer != null) {
/* 170 */       return deserializeUsingCustom(paramJsonParser, paramDeserializationContext, paramCollection, this._valueDeserializer);
/*     */     }
/*     */     
/*     */     JsonToken localJsonToken;
/* 174 */     while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY) {
/* 175 */       paramCollection.add(localJsonToken == JsonToken.VALUE_NULL ? null : _parseString(paramJsonParser, paramDeserializationContext));
/*     */     }
/* 177 */     return paramCollection;
/*     */   }
/*     */   
/*     */ 
/*     */   private Collection<String> deserializeUsingCustom(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<String> paramCollection, JsonDeserializer<String> paramJsonDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*     */     JsonToken localJsonToken;
/* 185 */     while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */     {
/*     */       String str;
/* 188 */       if (localJsonToken == JsonToken.VALUE_NULL) {
/* 189 */         str = null;
/*     */       } else {
/* 191 */         str = (String)paramJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */       }
/* 193 */       paramCollection.add(str);
/*     */     }
/* 195 */     return paramCollection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 204 */     return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Collection<String> handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<String> paramCollection)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 217 */     if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
/* 218 */       throw paramDeserializationContext.mappingException(this._collectionType.getRawClass());
/*     */     }
/*     */     
/* 221 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 222 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     
/*     */     String str;
/*     */     
/* 226 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/* 227 */       str = null;
/*     */     } else {
/* 229 */       str = localJsonDeserializer == null ? _parseString(paramJsonParser, paramDeserializationContext) : (String)localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */     }
/* 231 */     paramCollection.add(str);
/* 232 */     return paramCollection;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StringCollectionDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */