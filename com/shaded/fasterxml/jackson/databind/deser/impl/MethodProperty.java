/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
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
/*     */ public final class MethodProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedMethod _annotated;
/*     */   protected final transient Method _setter;
/*     */   
/*     */   public MethodProperty(BeanPropertyDefinition paramBeanPropertyDefinition, JavaType paramJavaType, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations, AnnotatedMethod paramAnnotatedMethod)
/*     */   {
/*  38 */     super(paramBeanPropertyDefinition, paramJavaType, paramTypeDeserializer, paramAnnotations);
/*  39 */     this._annotated = paramAnnotatedMethod;
/*  40 */     this._setter = paramAnnotatedMethod.getAnnotated();
/*     */   }
/*     */   
/*     */   protected MethodProperty(MethodProperty paramMethodProperty, JsonDeserializer<?> paramJsonDeserializer) {
/*  44 */     super(paramMethodProperty, paramJsonDeserializer);
/*  45 */     this._annotated = paramMethodProperty._annotated;
/*  46 */     this._setter = paramMethodProperty._setter;
/*     */   }
/*     */   
/*     */   protected MethodProperty(MethodProperty paramMethodProperty, String paramString) {
/*  50 */     super(paramMethodProperty, paramString);
/*  51 */     this._annotated = paramMethodProperty._annotated;
/*  52 */     this._setter = paramMethodProperty._setter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected MethodProperty(MethodProperty paramMethodProperty, Method paramMethod)
/*     */   {
/*  59 */     super(paramMethodProperty);
/*  60 */     this._annotated = paramMethodProperty._annotated;
/*  61 */     this._setter = paramMethod;
/*     */   }
/*     */   
/*     */   public MethodProperty withName(String paramString)
/*     */   {
/*  66 */     return new MethodProperty(this, paramString);
/*     */   }
/*     */   
/*     */   public MethodProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  71 */     return new MethodProperty(this, paramJsonDeserializer);
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
/*  82 */     return this._annotated.getAnnotation(paramClass);
/*     */   }
/*     */   
/*  85 */   public AnnotatedMember getMember() { return this._annotated; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  98 */     set(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 106 */     return setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */   public final void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 114 */       this._setter.invoke(paramObject1, new Object[] { paramObject2 });
/*     */     } catch (Exception localException) {
/* 116 */       _throwAsIOE(localException, paramObject2);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 125 */       Object localObject = this._setter.invoke(paramObject1, new Object[] { paramObject2 });
/* 126 */       return localObject == null ? paramObject1 : localObject;
/*     */     } catch (Exception localException) {
/* 128 */       _throwAsIOE(localException, paramObject2); }
/* 129 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Object readResolve()
/*     */   {
/* 140 */     return new MethodProperty(this, this._annotated.getAnnotated());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/MethodProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */