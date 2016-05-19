/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
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
/*     */ public class UnwrappingBeanPropertyWriter
/*     */   extends BeanPropertyWriter
/*     */ {
/*     */   protected final NameTransformer _nameTransformer;
/*     */   
/*     */   public UnwrappingBeanPropertyWriter(BeanPropertyWriter paramBeanPropertyWriter, NameTransformer paramNameTransformer)
/*     */   {
/*  33 */     super(paramBeanPropertyWriter);
/*  34 */     this._nameTransformer = paramNameTransformer;
/*     */   }
/*     */   
/*     */   private UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter paramUnwrappingBeanPropertyWriter, NameTransformer paramNameTransformer, SerializedString paramSerializedString)
/*     */   {
/*  39 */     super(paramUnwrappingBeanPropertyWriter, paramSerializedString);
/*  40 */     this._nameTransformer = paramNameTransformer;
/*     */   }
/*     */   
/*     */ 
/*     */   public UnwrappingBeanPropertyWriter rename(NameTransformer paramNameTransformer)
/*     */   {
/*  46 */     String str1 = this._name.getValue();
/*  47 */     String str2 = paramNameTransformer.transform(str1);
/*     */     
/*     */ 
/*  50 */     paramNameTransformer = NameTransformer.chainedTransformer(paramNameTransformer, this._nameTransformer);
/*     */     
/*  52 */     return new UnwrappingBeanPropertyWriter(this, paramNameTransformer, new SerializedString(str2));
/*     */   }
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
/*  65 */     Object localObject = get(paramObject);
/*  66 */     if (localObject == null)
/*     */     {
/*     */ 
/*  69 */       return;
/*     */     }
/*  71 */     JsonSerializer localJsonSerializer = this._serializer;
/*  72 */     if (localJsonSerializer == null) {
/*  73 */       Class localClass = localObject.getClass();
/*  74 */       PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
/*  75 */       localJsonSerializer = localPropertySerializerMap.serializerFor(localClass);
/*  76 */       if (localJsonSerializer == null) {
/*  77 */         localJsonSerializer = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
/*     */       }
/*     */     }
/*  80 */     if (this._suppressableValue != null) {
/*  81 */       if (MARKER_FOR_EMPTY == this._suppressableValue) {
/*  82 */         if (!localJsonSerializer.isEmpty(localObject)) {}
/*     */ 
/*     */       }
/*  85 */       else if (this._suppressableValue.equals(localObject)) {
/*  86 */         return;
/*     */       }
/*     */     }
/*     */     
/*  90 */     if (localObject == paramObject) {
/*  91 */       _handleSelfReference(paramObject, localJsonSerializer);
/*     */     }
/*     */     
/*     */ 
/*  95 */     if (!localJsonSerializer.isUnwrappingSerializer()) {
/*  96 */       paramJsonGenerator.writeFieldName(this._name);
/*     */     }
/*     */     
/*  99 */     if (this._typeSerializer == null) {
/* 100 */       localJsonSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 102 */       localJsonSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void assignSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/* 110 */     super.assignSerializer(paramJsonSerializer);
/* 111 */     if (this._serializer != null) {
/* 112 */       NameTransformer localNameTransformer = this._nameTransformer;
/* 113 */       if (this._serializer.isUnwrappingSerializer()) {
/* 114 */         localNameTransformer = NameTransformer.chainedTransformer(localNameTransformer, ((UnwrappingBeanSerializer)this._serializer)._nameTransformer);
/*     */       }
/* 116 */       this._serializer = this._serializer.unwrappingSerializer(localNameTransformer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 126 */     if (this._nonTrivialBaseType != null) {
/* 127 */       localObject = paramSerializerProvider.constructSpecializedType(this._nonTrivialBaseType, paramClass);
/* 128 */       localJsonSerializer = paramSerializerProvider.findValueSerializer((JavaType)localObject, this);
/*     */     } else {
/* 130 */       localJsonSerializer = paramSerializerProvider.findValueSerializer(paramClass, this);
/*     */     }
/* 132 */     Object localObject = this._nameTransformer;
/* 133 */     if (localJsonSerializer.isUnwrappingSerializer()) {
/* 134 */       localObject = NameTransformer.chainedTransformer((NameTransformer)localObject, ((UnwrappingBeanSerializer)localJsonSerializer)._nameTransformer);
/*     */     }
/* 136 */     JsonSerializer localJsonSerializer = localJsonSerializer.unwrappingSerializer((NameTransformer)localObject);
/*     */     
/* 138 */     this._dynamicSerializers = this._dynamicSerializers.newWith(paramClass, localJsonSerializer);
/* 139 */     return localJsonSerializer;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/UnwrappingBeanPropertyWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */