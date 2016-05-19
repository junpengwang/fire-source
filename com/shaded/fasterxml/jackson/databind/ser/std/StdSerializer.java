/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StdSerializer<T>
/*     */   extends JsonSerializer<T>
/*     */   implements JsonFormatVisitable, SchemaAware
/*     */ {
/*     */   protected final Class<T> _handledType;
/*     */   
/*     */   protected StdSerializer(Class<T> paramClass)
/*     */   {
/*  40 */     this._handledType = paramClass;
/*     */   }
/*     */   
/*     */   protected StdSerializer(JavaType paramJavaType)
/*     */   {
/*  45 */     this._handledType = paramJavaType.getRawClass();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StdSerializer(Class<?> paramClass, boolean paramBoolean)
/*     */   {
/*  54 */     this._handledType = paramClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<T> handledType()
/*     */   {
/*  63 */     return this._handledType;
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
/*     */   public abstract void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/*  89 */     return createSchemaNode("string");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 100 */     ObjectNode localObjectNode = (ObjectNode)getSchema(paramSerializerProvider, paramType);
/* 101 */     if (!paramBoolean) {
/* 102 */       localObjectNode.put("required", !paramBoolean);
/*     */     }
/* 104 */     return localObjectNode;
/*     */   }
/*     */   
/*     */   protected ObjectNode createObjectNode() {
/* 108 */     return JsonNodeFactory.instance.objectNode();
/*     */   }
/*     */   
/*     */   protected ObjectNode createSchemaNode(String paramString)
/*     */   {
/* 113 */     ObjectNode localObjectNode = createObjectNode();
/* 114 */     localObjectNode.put("type", paramString);
/* 115 */     return localObjectNode;
/*     */   }
/*     */   
/*     */   protected ObjectNode createSchemaNode(String paramString, boolean paramBoolean)
/*     */   {
/* 120 */     ObjectNode localObjectNode = createSchemaNode(paramString);
/*     */     
/* 122 */     if (!paramBoolean) {
/* 123 */       localObjectNode.put("required", !paramBoolean);
/*     */     }
/* 125 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 136 */     paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void wrapAndThrow(SerializerProvider paramSerializerProvider, Throwable paramThrowable, Object paramObject, String paramString)
/*     */     throws IOException
/*     */   {
/* 165 */     while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
/* 166 */       paramThrowable = paramThrowable.getCause();
/*     */     }
/*     */     
/* 169 */     if ((paramThrowable instanceof Error)) {
/* 170 */       throw ((Error)paramThrowable);
/*     */     }
/*     */     
/* 173 */     int i = (paramSerializerProvider == null) || (paramSerializerProvider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS)) ? 1 : 0;
/* 174 */     if ((paramThrowable instanceof IOException)) {
/* 175 */       if ((i == 0) || (!(paramThrowable instanceof JsonMappingException))) {
/* 176 */         throw ((IOException)paramThrowable);
/*     */       }
/* 178 */     } else if ((i == 0) && 
/* 179 */       ((paramThrowable instanceof RuntimeException))) {
/* 180 */       throw ((RuntimeException)paramThrowable);
/*     */     }
/*     */     
/*     */ 
/* 184 */     throw JsonMappingException.wrapWithPath(paramThrowable, paramObject, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void wrapAndThrow(SerializerProvider paramSerializerProvider, Throwable paramThrowable, Object paramObject, int paramInt)
/*     */     throws IOException
/*     */   {
/* 191 */     while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
/* 192 */       paramThrowable = paramThrowable.getCause();
/*     */     }
/*     */     
/* 195 */     if ((paramThrowable instanceof Error)) {
/* 196 */       throw ((Error)paramThrowable);
/*     */     }
/*     */     
/* 199 */     int i = (paramSerializerProvider == null) || (paramSerializerProvider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS)) ? 1 : 0;
/* 200 */     if ((paramThrowable instanceof IOException)) {
/* 201 */       if ((i == 0) || (!(paramThrowable instanceof JsonMappingException))) {
/* 202 */         throw ((IOException)paramThrowable);
/*     */       }
/* 204 */     } else if ((i == 0) && 
/* 205 */       ((paramThrowable instanceof RuntimeException))) {
/* 206 */       throw ((RuntimeException)paramThrowable);
/*     */     }
/*     */     
/*     */ 
/* 210 */     throw JsonMappingException.wrapWithPath(paramThrowable, paramObject, paramInt);
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
/*     */   protected boolean isDefaultSerializer(JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 226 */     return (paramJsonSerializer != null) && (paramJsonSerializer.getClass().getAnnotation(JacksonStdImpl.class) != null);
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
/*     */   protected JsonSerializer<?> findConvertingContentSerializer(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer)
/*     */     throws JsonMappingException
/*     */   {
/* 243 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializerProvider.getAnnotationIntrospector();
/* 244 */     if ((localAnnotationIntrospector != null) && (paramBeanProperty != null)) {
/* 245 */       Object localObject = localAnnotationIntrospector.findSerializationContentConverter(paramBeanProperty.getMember());
/* 246 */       if (localObject != null) {
/* 247 */         Converter localConverter = paramSerializerProvider.converterInstance(paramBeanProperty.getMember(), localObject);
/* 248 */         JavaType localJavaType = localConverter.getOutputType(paramSerializerProvider.getTypeFactory());
/* 249 */         if (paramJsonSerializer == null) {
/* 250 */           paramJsonSerializer = paramSerializerProvider.findValueSerializer(localJavaType, paramBeanProperty);
/*     */         }
/* 252 */         return new StdDelegatingSerializer(localConverter, localJavaType, paramJsonSerializer);
/*     */       }
/*     */     }
/* 255 */     return paramJsonSerializer;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StdSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */