/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
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
/*     */ public final class SetterlessProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedMethod _annotated;
/*     */   protected final Method _getter;
/*     */   
/*     */   public SetterlessProperty(BeanPropertyDefinition paramBeanPropertyDefinition, JavaType paramJavaType, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations, AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/*  41 */     super(paramBeanPropertyDefinition, paramJavaType, paramTypeDeserializer, paramAnnotations);
/*  42 */     this._annotated = paramAnnotatedMethod;
/*  43 */     this._getter = paramAnnotatedMethod.getAnnotated();
/*     */   }
/*     */   
/*     */   protected SetterlessProperty(SetterlessProperty paramSetterlessProperty, JsonDeserializer<?> paramJsonDeserializer) {
/*  47 */     super(paramSetterlessProperty, paramJsonDeserializer);
/*  48 */     this._annotated = paramSetterlessProperty._annotated;
/*  49 */     this._getter = paramSetterlessProperty._getter;
/*     */   }
/*     */   
/*     */   protected SetterlessProperty(SetterlessProperty paramSetterlessProperty, String paramString) {
/*  53 */     super(paramSetterlessProperty, paramString);
/*  54 */     this._annotated = paramSetterlessProperty._annotated;
/*  55 */     this._getter = paramSetterlessProperty._getter;
/*     */   }
/*     */   
/*     */   public SetterlessProperty withName(String paramString)
/*     */   {
/*  60 */     return new SetterlessProperty(this, paramString);
/*     */   }
/*     */   
/*     */   public SetterlessProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  65 */     return new SetterlessProperty(this, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/*  76 */     return this._annotated.getAnnotation(paramClass);
/*     */   }
/*     */   
/*  79 */   public AnnotatedMember getMember() { return this._annotated; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  92 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*  93 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/*     */       return;
/*     */     }
/*     */     
/*     */ 
/*     */     Object localObject;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 103 */       localObject = this._getter.invoke(paramObject, new Object[0]);
/*     */     } catch (Exception localException) {
/* 105 */       _throwAsIOE(localException);
/* 106 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */     if (localObject == null) {
/* 114 */       throw new JsonMappingException("Problem deserializing 'setterless' property '" + getName() + "': get method returned null");
/*     */     }
/* 116 */     this._valueDeserializer.deserialize(paramJsonParser, paramDeserializationContext, localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 124 */     deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
/* 125 */     return paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public final void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 132 */     throw new UnsupportedOperationException("Should never call 'set' on setterless property");
/*     */   }
/*     */   
/*     */ 
/*     */   public Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 139 */     set(paramObject1, paramObject2);
/* 140 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/SetterlessProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */