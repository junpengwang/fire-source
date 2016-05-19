/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class StringArraySerializer
/*     */   extends ArraySerializerBase<String[]>
/*     */   implements ContextualSerializer
/*     */ {
/*  33 */   private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(String.class);
/*     */   
/*  35 */   public static final StringArraySerializer instance = new StringArraySerializer();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JsonSerializer<Object> _elementSerializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StringArraySerializer()
/*     */   {
/*  50 */     super(String[].class, null);
/*  51 */     this._elementSerializer = null;
/*     */   }
/*     */   
/*     */ 
/*     */   public StringArraySerializer(StringArraySerializer paramStringArraySerializer, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  57 */     super(paramStringArraySerializer, paramBeanProperty);
/*  58 */     this._elementSerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */   {
/*  67 */     return this;
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
/*  85 */     JsonSerializer localJsonSerializer = null;
/*     */     
/*  87 */     if (paramBeanProperty != null) {
/*  88 */       AnnotatedMember localAnnotatedMember = paramBeanProperty.getMember();
/*  89 */       if (localAnnotatedMember != null) {
/*  90 */         Object localObject = paramSerializerProvider.getAnnotationIntrospector().findContentSerializer(localAnnotatedMember);
/*  91 */         if (localObject != null) {
/*  92 */           localJsonSerializer = paramSerializerProvider.serializerInstance(localAnnotatedMember, localObject);
/*     */         }
/*     */       }
/*     */     }
/*  96 */     if (localJsonSerializer == null) {
/*  97 */       localJsonSerializer = this._elementSerializer;
/*     */     }
/*     */     
/* 100 */     localJsonSerializer = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, localJsonSerializer);
/* 101 */     if (localJsonSerializer == null) {
/* 102 */       localJsonSerializer = paramSerializerProvider.findValueSerializer(String.class, paramBeanProperty);
/* 103 */     } else if ((localJsonSerializer instanceof ContextualSerializer)) {
/* 104 */       localJsonSerializer = ((ContextualSerializer)localJsonSerializer).createContextual(paramSerializerProvider, paramBeanProperty);
/*     */     }
/*     */     
/* 107 */     if (isDefaultSerializer(localJsonSerializer)) {
/* 108 */       localJsonSerializer = null;
/*     */     }
/*     */     
/* 111 */     if (localJsonSerializer == this._elementSerializer) {
/* 112 */       return this;
/*     */     }
/* 114 */     return new StringArraySerializer(this, paramBeanProperty, localJsonSerializer);
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
/* 125 */     return VALUE_TYPE;
/*     */   }
/*     */   
/*     */   public JsonSerializer<?> getContentSerializer()
/*     */   {
/* 130 */     return this._elementSerializer;
/*     */   }
/*     */   
/*     */   public boolean isEmpty(String[] paramArrayOfString)
/*     */   {
/* 135 */     return (paramArrayOfString == null) || (paramArrayOfString.length == 0);
/*     */   }
/*     */   
/*     */   public boolean hasSingleElement(String[] paramArrayOfString)
/*     */   {
/* 140 */     return paramArrayOfString.length == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeContents(String[] paramArrayOfString, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 153 */     int i = paramArrayOfString.length;
/* 154 */     if (i == 0) {
/* 155 */       return;
/*     */     }
/* 157 */     if (this._elementSerializer != null) {
/* 158 */       serializeContentsSlow(paramArrayOfString, paramJsonGenerator, paramSerializerProvider, this._elementSerializer);
/* 159 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 169 */     for (int j = 0; j < i; j++) {
/* 170 */       String str = paramArrayOfString[j];
/* 171 */       if (str == null) {
/* 172 */         paramJsonGenerator.writeNull();
/*     */       }
/*     */       else {
/* 175 */         paramJsonGenerator.writeString(paramArrayOfString[j]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void serializeContentsSlow(String[] paramArrayOfString, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<Object> paramJsonSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 184 */     int i = 0; for (int j = paramArrayOfString.length; i < j; i++) {
/* 185 */       String str = paramArrayOfString[i];
/* 186 */       if (str == null) {
/* 187 */         paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*     */       } else {
/* 189 */         paramJsonSerializer.serialize(paramArrayOfString[i], paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */   {
/* 197 */     ObjectNode localObjectNode = createSchemaNode("array", true);
/* 198 */     localObjectNode.put("items", createSchemaNode("string"));
/* 199 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 206 */     if (paramJsonFormatVisitorWrapper != null) {
/* 207 */       JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 208 */       if (localJsonArrayFormatVisitor != null) {
/* 209 */         localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.STRING);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/StringArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */