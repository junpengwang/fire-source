/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
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
/*     */ public class ArrayBlockingQueueDeserializer
/*     */   extends CollectionDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 5471961369237518580L;
/*     */   
/*     */   public ArrayBlockingQueueDeserializer(JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer1, TypeDeserializer paramTypeDeserializer, ValueInstantiator paramValueInstantiator, JsonDeserializer<Object> paramJsonDeserializer2)
/*     */   {
/*  40 */     super(paramJavaType, paramJsonDeserializer1, paramTypeDeserializer, paramValueInstantiator, paramJsonDeserializer2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ArrayBlockingQueueDeserializer(ArrayBlockingQueueDeserializer paramArrayBlockingQueueDeserializer)
/*     */   {
/*  48 */     super(paramArrayBlockingQueueDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ArrayBlockingQueueDeserializer withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, TypeDeserializer paramTypeDeserializer)
/*     */   {
/*  59 */     if ((paramJsonDeserializer1 == this._delegateDeserializer) && (paramJsonDeserializer2 == this._valueDeserializer) && (paramTypeDeserializer == this._valueTypeDeserializer)) {
/*  60 */       return this;
/*     */     }
/*  62 */     return new ArrayBlockingQueueDeserializer(this._collectionType, paramJsonDeserializer2, paramTypeDeserializer, this._valueInstantiator, paramJsonDeserializer1);
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
/*     */   public Collection<Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  79 */     if (this._delegateDeserializer != null) {
/*  80 */       return (Collection)this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */     
/*  83 */     if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
/*  84 */       String str = paramJsonParser.getText();
/*  85 */       if (str.length() == 0) {
/*  86 */         return (Collection)this._valueInstantiator.createFromString(paramDeserializationContext, str);
/*     */       }
/*     */     }
/*  89 */     return deserialize(paramJsonParser, paramDeserializationContext, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<Object> paramCollection)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  98 */     if (!paramJsonParser.isExpectedStartArrayToken()) {
/*  99 */       return handleNonArray(paramJsonParser, paramDeserializationContext, new ArrayBlockingQueue(1));
/*     */     }
/* 101 */     ArrayList localArrayList = new ArrayList();
/*     */     
/* 103 */     JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/*     */     
/* 105 */     TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
/*     */     JsonToken localJsonToken;
/* 107 */     while ((localJsonToken = paramJsonParser.nextToken()) != JsonToken.END_ARRAY)
/*     */     {
/*     */       Object localObject;
/* 110 */       if (localJsonToken == JsonToken.VALUE_NULL) {
/* 111 */         localObject = null;
/* 112 */       } else if (localTypeDeserializer == null) {
/* 113 */         localObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */       } else {
/* 115 */         localObject = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
/*     */       }
/* 117 */       localArrayList.add(localObject);
/*     */     }
/* 119 */     if (paramCollection != null) {
/* 120 */       paramCollection.addAll(localArrayList);
/* 121 */       return paramCollection;
/*     */     }
/* 123 */     return new ArrayBlockingQueue(localArrayList.size(), false, localArrayList);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 132 */     return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/ArrayBlockingQueueDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */