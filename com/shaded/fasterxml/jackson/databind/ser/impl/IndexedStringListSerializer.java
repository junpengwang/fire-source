/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public final class IndexedStringListSerializer
/*     */   extends StaticListSerializerBase<List<String>>
/*     */   implements ContextualSerializer
/*     */ {
/*  29 */   public static final IndexedStringListSerializer instance = new IndexedStringListSerializer();
/*     */   
/*     */ 
/*     */ 
/*     */   protected final JsonSerializer<String> _serializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected IndexedStringListSerializer()
/*     */   {
/*  40 */     this(null);
/*     */   }
/*     */   
/*     */   public IndexedStringListSerializer(JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  45 */     super(List.class);
/*  46 */     this._serializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */   protected JsonNode contentSchema()
/*     */   {
/*  51 */     return createSchemaNode("string", true);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void acceptContentVisitor(JsonArrayFormatVisitor paramJsonArrayFormatVisitor)
/*     */     throws JsonMappingException
/*     */   {
/*  58 */     paramJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.STRING);
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
/*     */   public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  76 */     JsonSerializer localJsonSerializer = null;
/*     */     
/*  78 */     if (paramBeanProperty != null) {
/*  79 */       AnnotatedMember localAnnotatedMember = paramBeanProperty.getMember();
/*  80 */       if (localAnnotatedMember != null) {
/*  81 */         Object localObject = paramSerializerProvider.getAnnotationIntrospector().findContentSerializer(localAnnotatedMember);
/*  82 */         if (localObject != null) {
/*  83 */           localJsonSerializer = paramSerializerProvider.serializerInstance(localAnnotatedMember, localObject);
/*     */         }
/*     */       }
/*     */     }
/*  87 */     if (localJsonSerializer == null) {
/*  88 */       localJsonSerializer = this._serializer;
/*     */     }
/*     */     
/*  91 */     localJsonSerializer = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, localJsonSerializer);
/*  92 */     if (localJsonSerializer == null) {
/*  93 */       localJsonSerializer = paramSerializerProvider.findValueSerializer(String.class, paramBeanProperty);
/*  94 */     } else if ((localJsonSerializer instanceof ContextualSerializer)) {
/*  95 */       localJsonSerializer = ((ContextualSerializer)localJsonSerializer).createContextual(paramSerializerProvider, paramBeanProperty);
/*     */     }
/*     */     
/*  98 */     if (isDefaultSerializer(localJsonSerializer)) {
/*  99 */       localJsonSerializer = null;
/*     */     }
/*     */     
/* 102 */     if (localJsonSerializer == this._serializer) {
/* 103 */       return this;
/*     */     }
/* 105 */     return new IndexedStringListSerializer(localJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serialize(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 118 */     int i = paramList.size();
/*     */     
/* 120 */     if ((i == 1) && (paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED))) {
/* 121 */       _serializeUnwrapped(paramList, paramJsonGenerator, paramSerializerProvider);
/* 122 */       return;
/*     */     }
/*     */     
/* 125 */     paramJsonGenerator.writeStartArray();
/* 126 */     if (this._serializer == null) {
/* 127 */       serializeContents(paramList, paramJsonGenerator, paramSerializerProvider, i);
/*     */     } else {
/* 129 */       serializeUsingCustom(paramList, paramJsonGenerator, paramSerializerProvider, i);
/*     */     }
/* 131 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */   private final void _serializeUnwrapped(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 137 */     if (this._serializer == null) {
/* 138 */       serializeContents(paramList, paramJsonGenerator, paramSerializerProvider, 1);
/*     */     } else {
/* 140 */       serializeUsingCustom(paramList, paramJsonGenerator, paramSerializerProvider, 1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void serializeWithType(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 149 */     int i = paramList.size();
/* 150 */     paramTypeSerializer.writeTypePrefixForArray(paramList, paramJsonGenerator);
/* 151 */     if (this._serializer == null) {
/* 152 */       serializeContents(paramList, paramJsonGenerator, paramSerializerProvider, i);
/*     */     } else {
/* 154 */       serializeUsingCustom(paramList, paramJsonGenerator, paramSerializerProvider, i);
/*     */     }
/* 156 */     paramTypeSerializer.writeTypeSuffixForArray(paramList, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   private final void serializeContents(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 163 */     int i = 0;
/*     */     try {
/* 165 */       for (; i < paramInt; i++) {
/* 166 */         String str = (String)paramList.get(i);
/* 167 */         if (str == null) {
/* 168 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/* 170 */           paramJsonGenerator.writeString(str);
/*     */         }
/*     */       }
/*     */     } catch (Exception localException) {
/* 174 */       wrapAndThrow(paramSerializerProvider, localException, paramList, i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private final void serializeUsingCustom(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, int paramInt)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 182 */     int i = 0;
/*     */     try {
/* 184 */       JsonSerializer localJsonSerializer = this._serializer;
/* 185 */       for (i = 0; i < paramInt; i++) {
/* 186 */         String str = (String)paramList.get(i);
/* 187 */         if (str == null) {
/* 188 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/* 190 */           localJsonSerializer.serialize(str, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */       }
/*     */     } catch (Exception localException) {
/* 194 */       wrapAndThrow(paramSerializerProvider, localException, paramList, i);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/IndexedStringListSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */