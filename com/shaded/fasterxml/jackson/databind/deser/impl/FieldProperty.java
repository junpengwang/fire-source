/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
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
/*     */ public final class FieldProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedField _annotated;
/*     */   protected final transient Field _field;
/*     */   
/*     */   public FieldProperty(BeanPropertyDefinition paramBeanPropertyDefinition, JavaType paramJavaType, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations, AnnotatedField paramAnnotatedField)
/*     */   {
/*  42 */     super(paramBeanPropertyDefinition, paramJavaType, paramTypeDeserializer, paramAnnotations);
/*  43 */     this._annotated = paramAnnotatedField;
/*  44 */     this._field = paramAnnotatedField.getAnnotated();
/*     */   }
/*     */   
/*     */   protected FieldProperty(FieldProperty paramFieldProperty, JsonDeserializer<?> paramJsonDeserializer) {
/*  48 */     super(paramFieldProperty, paramJsonDeserializer);
/*  49 */     this._annotated = paramFieldProperty._annotated;
/*  50 */     this._field = paramFieldProperty._field;
/*     */   }
/*     */   
/*     */   protected FieldProperty(FieldProperty paramFieldProperty, String paramString) {
/*  54 */     super(paramFieldProperty, paramString);
/*  55 */     this._annotated = paramFieldProperty._annotated;
/*  56 */     this._field = paramFieldProperty._field;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected FieldProperty(FieldProperty paramFieldProperty, Field paramField)
/*     */   {
/*  64 */     super(paramFieldProperty);
/*  65 */     this._annotated = paramFieldProperty._annotated;
/*  66 */     if (paramField == null) {
/*  67 */       throw new IllegalArgumentException("No Field passed for property '" + paramFieldProperty.getName() + "' (class " + paramFieldProperty.getDeclaringClass().getName() + ")");
/*     */     }
/*     */     
/*  70 */     this._field = paramField;
/*     */   }
/*     */   
/*     */   public FieldProperty withName(String paramString)
/*     */   {
/*  75 */     return new FieldProperty(this, paramString);
/*     */   }
/*     */   
/*     */   public FieldProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  80 */     return new FieldProperty(this, paramJsonDeserializer);
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
/*  91 */     return this._annotated.getAnnotation(paramClass);
/*     */   }
/*     */   
/*  94 */   public AnnotatedMember getMember() { return this._annotated; }
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
/* 107 */     set(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 115 */     return setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */   public final void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 123 */       this._field.set(paramObject1, paramObject2);
/*     */     } catch (Exception localException) {
/* 125 */       _throwAsIOE(localException, paramObject2);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 134 */       this._field.set(paramObject1, paramObject2);
/*     */     } catch (Exception localException) {
/* 136 */       _throwAsIOE(localException, paramObject2);
/*     */     }
/* 138 */     return paramObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Object readResolve()
/*     */   {
/* 148 */     return new FieldProperty(this, this._annotated.getAnnotated());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/FieldProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */