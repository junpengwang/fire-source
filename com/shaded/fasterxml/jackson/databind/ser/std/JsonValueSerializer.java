/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.MapperFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.JsonSchema;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.SchemaAware;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class JsonValueSerializer
/*     */   extends StdSerializer<Object>
/*     */   implements ContextualSerializer, JsonFormatVisitable, SchemaAware
/*     */ {
/*     */   protected final Method _accessorMethod;
/*     */   protected final JsonSerializer<Object> _valueSerializer;
/*     */   protected final BeanProperty _property;
/*     */   protected final boolean _forceTypeInformation;
/*     */   
/*     */   public JsonValueSerializer(Method paramMethod, JsonSerializer<Object> paramJsonSerializer)
/*     */   {
/*  65 */     super(Object.class);
/*  66 */     this._accessorMethod = paramMethod;
/*  67 */     this._valueSerializer = paramJsonSerializer;
/*  68 */     this._property = null;
/*  69 */     this._forceTypeInformation = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonValueSerializer(JsonValueSerializer paramJsonValueSerializer, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer, boolean paramBoolean)
/*     */   {
/*  76 */     super(_notNullClass(paramJsonValueSerializer.handledType()));
/*  77 */     this._accessorMethod = paramJsonValueSerializer._accessorMethod;
/*  78 */     this._valueSerializer = paramJsonSerializer;
/*  79 */     this._property = paramBeanProperty;
/*  80 */     this._forceTypeInformation = paramBoolean;
/*     */   }
/*     */   
/*     */   private static final Class<Object> _notNullClass(Class<?> paramClass)
/*     */   {
/*  85 */     return paramClass == null ? Object.class : paramClass;
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonValueSerializer withResolved(BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer, boolean paramBoolean)
/*     */   {
/*  91 */     if ((this._property == paramBeanProperty) && (this._valueSerializer == paramJsonSerializer) && (paramBoolean == this._forceTypeInformation))
/*     */     {
/*  93 */       return this;
/*     */     }
/*  95 */     return new JsonValueSerializer(this, paramBeanProperty, paramJsonSerializer, paramBoolean);
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
/* 113 */     JsonSerializer localJsonSerializer = this._valueSerializer;
/* 114 */     if (localJsonSerializer == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 119 */       if ((paramSerializerProvider.isEnabled(MapperFeature.USE_STATIC_TYPING)) || (Modifier.isFinal(this._accessorMethod.getReturnType().getModifiers())))
/*     */       {
/* 121 */         JavaType localJavaType = paramSerializerProvider.constructType(this._accessorMethod.getGenericReturnType());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */         localJsonSerializer = paramSerializerProvider.findTypedValueSerializer(localJavaType, false, this._property);
/* 132 */         boolean bool = isNaturalTypeWithStdHandling(localJavaType.getRawClass(), localJsonSerializer);
/* 133 */         return withResolved(paramBeanProperty, localJsonSerializer, bool);
/*     */       }
/* 135 */     } else if ((localJsonSerializer instanceof ContextualSerializer)) {
/* 136 */       localJsonSerializer = ((ContextualSerializer)localJsonSerializer).createContextual(paramSerializerProvider, paramBeanProperty);
/* 137 */       return withResolved(paramBeanProperty, localJsonSerializer, this._forceTypeInformation);
/*     */     }
/* 139 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*     */     try
/*     */     {
/* 153 */       Object localObject1 = this._accessorMethod.invoke(paramObject, new Object[0]);
/* 154 */       if (localObject1 == null) {
/* 155 */         paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/* 156 */         return;
/*     */       }
/* 158 */       localObject2 = this._valueSerializer;
/* 159 */       if (localObject2 == null) {
/* 160 */         Class localClass = localObject1.getClass();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 166 */         localObject2 = paramSerializerProvider.findTypedValueSerializer(localClass, true, this._property);
/*     */       }
/* 168 */       ((JsonSerializer)localObject2).serialize(localObject1, paramJsonGenerator, paramSerializerProvider);
/*     */     } catch (IOException localIOException) {
/* 170 */       throw localIOException;
/*     */     } catch (Exception localException) {
/* 172 */       Object localObject2 = localException;
/*     */       
/* 174 */       while (((localObject2 instanceof InvocationTargetException)) && (((Throwable)localObject2).getCause() != null)) {
/* 175 */         localObject2 = ((Throwable)localObject2).getCause();
/*     */       }
/*     */       
/* 178 */       if ((localObject2 instanceof Error)) {
/* 179 */         throw ((Error)localObject2);
/*     */       }
/*     */       
/* 182 */       throw JsonMappingException.wrapWithPath((Throwable)localObject2, paramObject, this._accessorMethod.getName() + "()");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 192 */     Object localObject1 = null;
/*     */     try {
/* 194 */       localObject1 = this._accessorMethod.invoke(paramObject, new Object[0]);
/*     */       
/* 196 */       if (localObject1 == null) {
/* 197 */         paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/* 198 */         return;
/*     */       }
/* 200 */       JsonSerializer localJsonSerializer = this._valueSerializer;
/* 201 */       if (localJsonSerializer == null)
/*     */       {
/* 203 */         localJsonSerializer = paramSerializerProvider.findValueSerializer(localObject1.getClass(), this._property);
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 208 */       else if (this._forceTypeInformation) {
/* 209 */         paramTypeSerializer.writeTypePrefixForScalar(paramObject, paramJsonGenerator);
/* 210 */         localJsonSerializer.serialize(localObject1, paramJsonGenerator, paramSerializerProvider);
/* 211 */         paramTypeSerializer.writeTypeSuffixForScalar(paramObject, paramJsonGenerator);
/* 212 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 220 */       localJsonSerializer.serializeWithType(localObject1, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
/*     */     } catch (IOException localIOException) {
/* 222 */       throw localIOException;
/*     */     } catch (Exception localException) {
/* 224 */       Object localObject2 = localException;
/*     */       
/* 226 */       while (((localObject2 instanceof InvocationTargetException)) && (((Throwable)localObject2).getCause() != null)) {
/* 227 */         localObject2 = ((Throwable)localObject2).getCause();
/*     */       }
/*     */       
/* 230 */       if ((localObject2 instanceof Error)) {
/* 231 */         throw ((Error)localObject2);
/*     */       }
/*     */       
/* 234 */       throw JsonMappingException.wrapWithPath((Throwable)localObject2, paramObject, this._accessorMethod.getName() + "()");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/* 243 */     return (this._valueSerializer instanceof SchemaAware) ? ((SchemaAware)this._valueSerializer).getSchema(paramSerializerProvider, null) : JsonSchema.getDefaultSchemaNode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 252 */     if (this._valueSerializer != null) {
/* 253 */       this._valueSerializer.acceptJsonFormatVisitor(paramJsonFormatVisitorWrapper, null);
/*     */     } else {
/* 255 */       paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean isNaturalTypeWithStdHandling(Class<?> paramClass, JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 262 */     if (paramClass.isPrimitive()) {
/* 263 */       if ((paramClass != Integer.TYPE) && (paramClass != Boolean.TYPE) && (paramClass != Double.TYPE)) {
/* 264 */         return false;
/*     */       }
/*     */     }
/* 267 */     else if ((paramClass != String.class) && (paramClass != Integer.class) && (paramClass != Boolean.class) && (paramClass != Double.class))
/*     */     {
/* 269 */       return false;
/*     */     }
/*     */     
/* 272 */     return isDefaultSerializer(paramJsonSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 284 */     return "(@JsonValue serializer for method " + this._accessorMethod.getDeclaringClass() + "#" + this._accessorMethod.getName() + ")";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/JsonValueSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */