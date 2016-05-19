/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class AbstractDeserializer
/*     */   extends JsonDeserializer<Object>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3010349050434697698L;
/*     */   protected final JavaType _baseType;
/*     */   protected final ObjectIdReader _objectIdReader;
/*     */   protected final Map<String, SettableBeanProperty> _backRefProperties;
/*     */   protected final boolean _acceptString;
/*     */   protected final boolean _acceptBoolean;
/*     */   protected final boolean _acceptInt;
/*     */   protected final boolean _acceptDouble;
/*     */   
/*     */   public AbstractDeserializer(BeanDeserializerBuilder paramBeanDeserializerBuilder, BeanDescription paramBeanDescription, Map<String, SettableBeanProperty> paramMap)
/*     */   {
/*  42 */     this._baseType = paramBeanDescription.getType();
/*  43 */     this._objectIdReader = paramBeanDeserializerBuilder.getObjectIdReader();
/*  44 */     this._backRefProperties = paramMap;
/*  45 */     Class localClass = this._baseType.getRawClass();
/*  46 */     this._acceptString = localClass.isAssignableFrom(String.class);
/*  47 */     this._acceptBoolean = ((localClass == Boolean.TYPE) || (localClass.isAssignableFrom(Boolean.class)));
/*  48 */     this._acceptInt = ((localClass == Integer.TYPE) || (localClass.isAssignableFrom(Integer.class)));
/*  49 */     this._acceptDouble = ((localClass == Double.TYPE) || (localClass.isAssignableFrom(Double.class)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCachable()
/*     */   {
/*  59 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectIdReader getObjectIdReader()
/*     */   {
/*  68 */     return this._objectIdReader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SettableBeanProperty findBackReference(String paramString)
/*     */   {
/*  77 */     return this._backRefProperties == null ? null : (SettableBeanProperty)this._backRefProperties.get(paramString);
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
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  93 */     if (this._objectIdReader != null) {
/*  94 */       localObject = paramJsonParser.getCurrentToken();
/*     */       
/*  96 */       if ((localObject != null) && (((JsonToken)localObject).isScalarValue())) {
/*  97 */         return _deserializeFromObjectId(paramJsonParser, paramDeserializationContext);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 102 */     Object localObject = _deserializeIfNatural(paramJsonParser, paramDeserializationContext);
/* 103 */     if (localObject != null) {
/* 104 */       return localObject;
/*     */     }
/* 106 */     return paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 114 */     throw paramDeserializationContext.instantiationException(this._baseType.getRawClass(), "abstract types either need to be mapped to concrete types, have custom deserializer, or be instantiated with additional type information");
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
/*     */   protected Object _deserializeIfNatural(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 134 */     switch (paramJsonParser.getCurrentToken()) {
/*     */     case VALUE_STRING: 
/* 136 */       if (this._acceptString) {
/* 137 */         return paramJsonParser.getText();
/*     */       }
/*     */       break;
/*     */     case VALUE_NUMBER_INT: 
/* 141 */       if (this._acceptInt) {
/* 142 */         return Integer.valueOf(paramJsonParser.getIntValue());
/*     */       }
/*     */       
/*     */       break;
/*     */     case VALUE_NUMBER_FLOAT: 
/* 147 */       if (this._acceptDouble) {
/* 148 */         return Double.valueOf(paramJsonParser.getDoubleValue());
/*     */       }
/*     */       break;
/*     */     case VALUE_TRUE: 
/* 152 */       if (this._acceptBoolean) {
/* 153 */         return Boolean.TRUE;
/*     */       }
/*     */       break;
/*     */     case VALUE_FALSE: 
/* 157 */       if (this._acceptBoolean) {
/* 158 */         return Boolean.FALSE;
/*     */       }
/*     */       break;
/*     */     }
/* 162 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object _deserializeFromObjectId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 172 */     Object localObject1 = this._objectIdReader.deserializer.deserialize(paramJsonParser, paramDeserializationContext);
/* 173 */     ReadableObjectId localReadableObjectId = paramDeserializationContext.findObjectId(localObject1, this._objectIdReader.generator);
/*     */     
/* 175 */     Object localObject2 = localReadableObjectId.item;
/* 176 */     if (localObject2 == null) {
/* 177 */       throw new IllegalStateException("Could not resolve Object Id [" + localObject1 + "] -- unresolved forward-reference?");
/*     */     }
/* 179 */     return localObject2;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/AbstractDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */