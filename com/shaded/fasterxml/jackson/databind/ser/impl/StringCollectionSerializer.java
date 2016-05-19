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
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class StringCollectionSerializer
/*     */   extends StaticListSerializerBase<Collection<String>>
/*     */   implements ContextualSerializer
/*     */ {
/*  29 */   public static final StringCollectionSerializer instance = new StringCollectionSerializer();
/*     */   
/*     */ 
/*     */ 
/*     */   protected final JsonSerializer<String> _serializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StringCollectionSerializer()
/*     */   {
/*  40 */     this(null);
/*     */   }
/*     */   
/*     */ 
/*     */   protected StringCollectionSerializer(JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  46 */     super(Collection.class);
/*  47 */     this._serializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */   protected JsonNode contentSchema() {
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
/* 105 */     return new StringCollectionSerializer(localJsonSerializer);
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
/*     */   public void serialize(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 119 */     if ((paramCollection.size() == 1) && (paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED))) {
/* 120 */       _serializeUnwrapped(paramCollection, paramJsonGenerator, paramSerializerProvider);
/* 121 */       return;
/*     */     }
/* 123 */     paramJsonGenerator.writeStartArray();
/* 124 */     if (this._serializer == null) {
/* 125 */       serializeContents(paramCollection, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 127 */       serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 129 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */   private final void _serializeUnwrapped(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 135 */     if (this._serializer == null) {
/* 136 */       serializeContents(paramCollection, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 138 */       serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void serializeWithType(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 147 */     paramTypeSerializer.writeTypePrefixForArray(paramCollection, paramJsonGenerator);
/* 148 */     if (this._serializer == null) {
/* 149 */       serializeContents(paramCollection, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 151 */       serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 153 */     paramTypeSerializer.writeTypeSuffixForArray(paramCollection, paramJsonGenerator);
/*     */   }
/*     */   
/*     */   private final void serializeContents(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 159 */     if (this._serializer != null) {
/* 160 */       serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
/* 161 */       return;
/*     */     }
/* 163 */     int i = 0;
/* 164 */     for (String str : paramCollection) {
/*     */       try {
/* 166 */         if (str == null) {
/* 167 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/* 169 */           paramJsonGenerator.writeString(str);
/*     */         }
/* 171 */         i++;
/*     */       } catch (Exception localException) {
/* 173 */         wrapAndThrow(paramSerializerProvider, localException, paramCollection, i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void serializeUsingCustom(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 181 */     JsonSerializer localJsonSerializer = this._serializer;
/* 182 */     int i = 0;
/* 183 */     for (String str : paramCollection) {
/*     */       try {
/* 185 */         if (str == null) {
/* 186 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */         } else {
/* 188 */           localJsonSerializer.serialize(str, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */       } catch (Exception localException) {
/* 191 */         wrapAndThrow(paramSerializerProvider, localException, paramCollection, i);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/StringCollectionSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */