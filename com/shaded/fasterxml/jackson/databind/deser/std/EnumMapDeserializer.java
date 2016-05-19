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
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.util.EnumMap;
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
/*     */ public class EnumMapDeserializer
/*     */   extends StdDeserializer<EnumMap<?, ?>>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1518773374647478964L;
/*     */   protected final JavaType _mapType;
/*     */   protected final Class<?> _enumClass;
/*     */   protected JsonDeserializer<Enum<?>> _keyDeserializer;
/*     */   protected JsonDeserializer<Object> _valueDeserializer;
/*     */   protected final TypeDeserializer _valueTypeDeserializer;
/*     */   
/*     */   @Deprecated
/*     */   public EnumMapDeserializer(JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2)
/*     */   {
/*  54 */     this(paramJavaType, paramJsonDeserializer1, paramJsonDeserializer2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnumMapDeserializer(JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, TypeDeserializer paramTypeDeserializer)
/*     */   {
/*  61 */     super(EnumMap.class);
/*  62 */     this._mapType = paramJavaType;
/*  63 */     this._enumClass = paramJavaType.getKeyType().getRawClass();
/*  64 */     this._keyDeserializer = paramJsonDeserializer1;
/*  65 */     this._valueDeserializer = paramJsonDeserializer2;
/*  66 */     this._valueTypeDeserializer = paramTypeDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public EnumMapDeserializer withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2)
/*     */   {
/*  76 */     return withResolved(paramJsonDeserializer1, paramJsonDeserializer2, null);
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumMapDeserializer withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, TypeDeserializer paramTypeDeserializer)
/*     */   {
/*  82 */     if ((paramJsonDeserializer1 == this._keyDeserializer) && (paramJsonDeserializer2 == this._valueDeserializer) && (paramTypeDeserializer == this._valueTypeDeserializer))
/*     */     {
/*     */ 
/*  85 */       return this;
/*     */     }
/*  87 */     return new EnumMapDeserializer(this._mapType, paramJsonDeserializer1, paramJsonDeserializer2, this._valueTypeDeserializer);
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
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 102 */     JsonDeserializer localJsonDeserializer1 = this._keyDeserializer;
/* 103 */     if (localJsonDeserializer1 == null) {
/* 104 */       localJsonDeserializer1 = paramDeserializationContext.findContextualValueDeserializer(this._mapType.getKeyType(), paramBeanProperty);
/*     */     }
/* 106 */     JsonDeserializer localJsonDeserializer2 = this._valueDeserializer;
/* 107 */     if (localJsonDeserializer2 == null) {
/* 108 */       localJsonDeserializer2 = paramDeserializationContext.findContextualValueDeserializer(this._mapType.getContentType(), paramBeanProperty);
/*     */     }
/* 110 */     else if ((localJsonDeserializer2 instanceof ContextualDeserializer)) {
/* 111 */       localJsonDeserializer2 = ((ContextualDeserializer)localJsonDeserializer2).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/* 114 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/* 115 */     if (localTypeDeserializer != null) {
/* 116 */       localTypeDeserializer = localTypeDeserializer.forProperty(paramBeanProperty);
/*     */     }
/* 118 */     return withResolved(localJsonDeserializer1, localJsonDeserializer2, localTypeDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCachable()
/*     */   {
/* 126 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumMap<?, ?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 139 */     if (paramJsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
/* 140 */       throw paramDeserializationContext.mappingException(EnumMap.class);
/*     */     }
/* 142 */     EnumMap localEnumMap = constructMap();
/* 143 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 144 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/*     */     
/* 146 */     while (paramJsonParser.nextToken() != JsonToken.END_OBJECT) {
/* 147 */       Enum localEnum = (Enum)this._keyDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/* 148 */       Object localObject1; if (localEnum == null) {
/* 149 */         if (!paramDeserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
/* 150 */           localObject1 = null;
/*     */           try {
/* 152 */             if (paramJsonParser.hasCurrentToken()) {
/* 153 */               localObject1 = paramJsonParser.getText();
/*     */             }
/*     */           } catch (Exception localException) {}
/* 156 */           throw paramDeserializationContext.weirdStringException((String)localObject1, this._enumClass, "value not one of declared Enum instance names");
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 161 */         paramJsonParser.nextToken();
/* 162 */         paramJsonParser.skipChildren();
/*     */       }
/*     */       else
/*     */       {
/* 166 */         localObject1 = paramJsonParser.nextToken();
/*     */         
/*     */ 
/*     */         Object localObject2;
/*     */         
/*     */ 
/* 172 */         if (localObject1 == JsonToken.VALUE_NULL) {
/* 173 */           localObject2 = null;
/* 174 */         } else if (localTypeDeserializer == null) {
/* 175 */           localObject2 = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */         } else {
/* 177 */           localObject2 = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */         }
/* 179 */         localEnumMap.put(localEnum, localObject2);
/*     */       } }
/* 181 */     return localEnumMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 190 */     return paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */   private EnumMap<?, ?> constructMap() {
/* 194 */     return new EnumMap(this._enumClass);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/EnumMapDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */