/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
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
/*     */ public final class SettableAnyProperty
/*     */ {
/*     */   protected final BeanProperty _property;
/*     */   protected final Method _setter;
/*     */   protected final JavaType _type;
/*     */   protected JsonDeserializer<Object> _valueDeserializer;
/*     */   
/*     */   public SettableAnyProperty(BeanProperty paramBeanProperty, AnnotatedMethod paramAnnotatedMethod, JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer)
/*     */   {
/*  47 */     this(paramBeanProperty, paramAnnotatedMethod.getAnnotated(), paramJavaType, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */   public SettableAnyProperty(BeanProperty paramBeanProperty, Method paramMethod, JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer)
/*     */   {
/*  52 */     this._property = paramBeanProperty;
/*  53 */     this._type = paramJavaType;
/*  54 */     this._setter = paramMethod;
/*  55 */     this._valueDeserializer = paramJsonDeserializer;
/*     */   }
/*     */   
/*     */   public SettableAnyProperty withValueDeserializer(JsonDeserializer<Object> paramJsonDeserializer) {
/*  59 */     return new SettableAnyProperty(this._property, this._setter, this._type, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  68 */   public BeanProperty getProperty() { return this._property; }
/*     */   
/*  70 */   public boolean hasValueDeserializer() { return this._valueDeserializer != null; }
/*     */   
/*  72 */   public JavaType getType() { return this._type; }
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
/*     */   public final void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  88 */     set(paramObject, paramString, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */   public final Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  94 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*  95 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/*  96 */       return null;
/*     */     }
/*  98 */     return this._valueDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */   public final void set(Object paramObject1, String paramString, Object paramObject2) throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 105 */       this._setter.invoke(paramObject1, new Object[] { paramString, paramObject2 });
/*     */     } catch (Exception localException) {
/* 107 */       _throwAsIOE(localException, paramString, paramObject2);
/*     */     }
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
/*     */   protected void _throwAsIOE(Exception paramException, String paramString, Object paramObject)
/*     */     throws IOException
/*     */   {
/* 125 */     if ((paramException instanceof IllegalArgumentException)) {
/* 126 */       localObject = paramObject == null ? "[NULL]" : paramObject.getClass().getName();
/* 127 */       StringBuilder localStringBuilder = new StringBuilder("Problem deserializing \"any\" property '").append(paramString);
/* 128 */       localStringBuilder.append("' of class " + getClassName() + " (expected type: ").append(this._type);
/* 129 */       localStringBuilder.append("; actual type: ").append((String)localObject).append(")");
/* 130 */       String str = paramException.getMessage();
/* 131 */       if (str != null) {
/* 132 */         localStringBuilder.append(", problem: ").append(str);
/*     */       } else {
/* 134 */         localStringBuilder.append(" (no error message provided)");
/*     */       }
/* 136 */       throw new JsonMappingException(localStringBuilder.toString(), null, paramException);
/*     */     }
/* 138 */     if ((paramException instanceof IOException)) {
/* 139 */       throw ((IOException)paramException);
/*     */     }
/* 141 */     if ((paramException instanceof RuntimeException)) {
/* 142 */       throw ((RuntimeException)paramException);
/*     */     }
/*     */     
/* 145 */     Object localObject = paramException;
/* 146 */     while (((Throwable)localObject).getCause() != null) {
/* 147 */       localObject = ((Throwable)localObject).getCause();
/*     */     }
/* 149 */     throw new JsonMappingException(((Throwable)localObject).getMessage(), null, (Throwable)localObject);
/*     */   }
/*     */   
/* 152 */   private String getClassName() { return this._setter.getDeclaringClass().getName(); }
/*     */   
/* 154 */   public String toString() { return "[any property on class " + getClassName() + "]"; }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/SettableAnyProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */