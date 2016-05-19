/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.util.EnumSet;
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
/*     */ public class EnumSetDeserializer
/*     */   extends StdDeserializer<EnumSet<?>>
/*     */   implements ContextualDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 3479455075597887177L;
/*     */   protected final JavaType _enumType;
/*     */   protected final Class<Enum> _enumClass;
/*     */   protected JsonDeserializer<Enum<?>> _enumDeserializer;
/*     */   
/*     */   public EnumSetDeserializer(JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  40 */     super(EnumSet.class);
/*  41 */     this._enumType = paramJavaType;
/*  42 */     this._enumClass = paramJavaType.getRawClass();
/*  43 */     this._enumDeserializer = paramJsonDeserializer;
/*     */   }
/*     */   
/*     */   public EnumSetDeserializer withDeserializer(JsonDeserializer<?> paramJsonDeserializer) {
/*  47 */     if (this._enumDeserializer == paramJsonDeserializer) {
/*  48 */       return this;
/*     */     }
/*  50 */     return new EnumSetDeserializer(this._enumType, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCachable()
/*     */   {
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  64 */     JsonDeserializer localJsonDeserializer = this._enumDeserializer;
/*  65 */     if (localJsonDeserializer == null) {
/*  66 */       localJsonDeserializer = paramDeserializationContext.findContextualValueDeserializer(this._enumType, paramBeanProperty);
/*     */     }
/*  68 */     else if ((localJsonDeserializer instanceof ContextualDeserializer)) {
/*  69 */       localJsonDeserializer = ((ContextualDeserializer)localJsonDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*     */     
/*  72 */     return withDeserializer(localJsonDeserializer);
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
/*     */   public EnumSet<?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  87 */     if (!paramJsonParser.isExpectedStartArrayToken()) {
/*  88 */       throw paramDeserializationContext.mappingException(EnumSet.class);
/*     */     }
/*  90 */     EnumSet localEnumSet = constructSet();
/*     */     
/*     */     JsonToken localJsonToken;
/*  93 */     while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */       if (localJsonToken == JsonToken.VALUE_NULL) {
/* 100 */         throw paramDeserializationContext.mappingException(this._enumClass);
/*     */       }
/* 102 */       Enum localEnum = (Enum)this._enumDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */       
/*     */ 
/*     */ 
/* 106 */       if (localEnum != null) {
/* 107 */         localEnumSet.add(localEnum);
/*     */       }
/*     */     }
/* 110 */     return localEnumSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 118 */     return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private EnumSet constructSet()
/*     */   {
/* 125 */     return EnumSet.noneOf(this._enumClass);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/EnumSetDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */