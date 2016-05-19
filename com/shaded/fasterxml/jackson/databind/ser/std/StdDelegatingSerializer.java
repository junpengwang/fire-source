/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ResolvableSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*     */ import java.io.IOException;
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
/*     */ public class StdDelegatingSerializer
/*     */   extends StdSerializer<Object>
/*     */   implements ContextualSerializer, ResolvableSerializer, JsonFormatVisitable, SchemaAware
/*     */ {
/*     */   protected final Converter<Object, ?> _converter;
/*     */   protected final JavaType _delegateType;
/*     */   protected final JsonSerializer<Object> _delegateSerializer;
/*     */   
/*     */   public StdDelegatingSerializer(Converter<?, ?> paramConverter)
/*     */   {
/*  53 */     super(Object.class);
/*  54 */     this._converter = paramConverter;
/*  55 */     this._delegateType = null;
/*  56 */     this._delegateSerializer = null;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> StdDelegatingSerializer(Class<T> paramClass, Converter<T, ?> paramConverter)
/*     */   {
/*  62 */     super(paramClass, false);
/*  63 */     this._converter = paramConverter;
/*  64 */     this._delegateType = null;
/*  65 */     this._delegateSerializer = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public StdDelegatingSerializer(Converter<Object, ?> paramConverter, JavaType paramJavaType, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  72 */     super(paramJavaType);
/*  73 */     this._converter = paramConverter;
/*  74 */     this._delegateType = paramJavaType;
/*  75 */     this._delegateSerializer = paramJsonSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StdDelegatingSerializer withDelegate(Converter<Object, ?> paramConverter, JavaType paramJavaType, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/*  85 */     if (getClass() != StdDelegatingSerializer.class) {
/*  86 */       throw new IllegalStateException("Sub-class " + getClass().getName() + " must override 'withDelegate'");
/*     */     }
/*  88 */     return new StdDelegatingSerializer(paramConverter, paramJavaType, paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resolve(SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/* 100 */     if ((this._delegateSerializer != null) && ((this._delegateSerializer instanceof ResolvableSerializer)))
/*     */     {
/* 102 */       ((ResolvableSerializer)this._delegateSerializer).resolve(paramSerializerProvider);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 111 */     if (this._delegateSerializer != null) {
/* 112 */       if ((this._delegateSerializer instanceof ContextualSerializer)) {
/* 113 */         localObject = ((ContextualSerializer)this._delegateSerializer).createContextual(paramSerializerProvider, paramBeanProperty);
/* 114 */         if (localObject == this._delegateSerializer) {
/* 115 */           return this;
/*     */         }
/* 117 */         return withDelegate(this._converter, this._delegateType, (JsonSerializer)localObject);
/*     */       }
/* 119 */       return this;
/*     */     }
/*     */     
/* 122 */     Object localObject = this._delegateType;
/* 123 */     if (localObject == null) {
/* 124 */       localObject = this._converter.getOutputType(paramSerializerProvider.getTypeFactory());
/*     */     }
/*     */     
/* 127 */     return withDelegate(this._converter, (JavaType)localObject, paramSerializerProvider.findValueSerializer((JavaType)localObject, paramBeanProperty));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Converter<Object, ?> getConverter()
/*     */   {
/* 138 */     return this._converter;
/*     */   }
/*     */   
/*     */   public JsonSerializer<?> getDelegatee()
/*     */   {
/* 143 */     return this._delegateSerializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 156 */     Object localObject = convertValue(paramObject);
/*     */     
/* 158 */     if (localObject == null) {
/* 159 */       paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/* 160 */       return;
/*     */     }
/* 162 */     this._delegateSerializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 173 */     Object localObject = convertValue(paramObject);
/* 174 */     this._delegateSerializer.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isEmpty(Object paramObject)
/*     */   {
/* 180 */     Object localObject = convertValue(paramObject);
/* 181 */     return this._delegateSerializer.isEmpty(localObject);
/*     */   }
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
/* 194 */     if ((this._delegateSerializer instanceof SchemaAware)) {
/* 195 */       return ((SchemaAware)this._delegateSerializer).getSchema(paramSerializerProvider, paramType);
/*     */     }
/* 197 */     return super.getSchema(paramSerializerProvider, paramType);
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType, boolean paramBoolean)
/*     */     throws JsonMappingException
/*     */   {
/* 204 */     if ((this._delegateSerializer instanceof SchemaAware)) {
/* 205 */       return ((SchemaAware)this._delegateSerializer).getSchema(paramSerializerProvider, paramType, paramBoolean);
/*     */     }
/* 207 */     return super.getSchema(paramSerializerProvider, paramType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 217 */     this._delegateSerializer.acceptJsonFormatVisitor(paramJsonFormatVisitorWrapper, paramJavaType);
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
/*     */   protected Object convertValue(Object paramObject)
/*     */   {
/* 238 */     return this._converter.convert(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StdDelegatingSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */