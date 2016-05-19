/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
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
/*     */ public final class InnerClassProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final SettableBeanProperty _delegate;
/*     */   protected final Constructor<?> _creator;
/*     */   
/*     */   public InnerClassProperty(SettableBeanProperty paramSettableBeanProperty, Constructor<?> paramConstructor)
/*     */   {
/*  40 */     super(paramSettableBeanProperty);
/*  41 */     this._delegate = paramSettableBeanProperty;
/*  42 */     this._creator = paramConstructor;
/*     */   }
/*     */   
/*     */   protected InnerClassProperty(InnerClassProperty paramInnerClassProperty, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  47 */     super(paramInnerClassProperty, paramJsonDeserializer);
/*  48 */     this._delegate = paramInnerClassProperty._delegate.withValueDeserializer(paramJsonDeserializer);
/*  49 */     this._creator = paramInnerClassProperty._creator;
/*     */   }
/*     */   
/*     */   protected InnerClassProperty(InnerClassProperty paramInnerClassProperty, String paramString) {
/*  53 */     super(paramInnerClassProperty, paramString);
/*  54 */     this._delegate = paramInnerClassProperty._delegate.withName(paramString);
/*  55 */     this._creator = paramInnerClassProperty._creator;
/*     */   }
/*     */   
/*     */   public InnerClassProperty withName(String paramString)
/*     */   {
/*  60 */     return new InnerClassProperty(this, paramString);
/*     */   }
/*     */   
/*     */   public InnerClassProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  65 */     return new InnerClassProperty(this, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/*  72 */     return this._delegate.getAnnotation(paramClass);
/*     */   }
/*     */   
/*  75 */   public AnnotatedMember getMember() { return this._delegate.getMember(); }
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
/*  88 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */     Object localObject;
/*  90 */     if (localJsonToken == JsonToken.VALUE_NULL) {
/*  91 */       localObject = this._nullProvider == null ? null : this._nullProvider.nullValue(paramDeserializationContext);
/*  92 */     } else if (this._valueTypeDeserializer != null) {
/*  93 */       localObject = this._valueDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, this._valueTypeDeserializer);
/*     */     } else {
/*     */       try {
/*  96 */         localObject = this._creator.newInstance(new Object[] { paramObject });
/*     */       } catch (Exception localException) {
/*  98 */         ClassUtil.unwrapAndThrowAsIAE(localException, "Failed to instantiate class " + this._creator.getDeclaringClass().getName() + ", problem: " + localException.getMessage());
/*  99 */         localObject = null;
/*     */       }
/* 101 */       this._valueDeserializer.deserialize(paramJsonParser, paramDeserializationContext, localObject);
/*     */     }
/* 103 */     set(paramObject, localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 111 */     return setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */   public final void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 117 */     this._delegate.set(paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 124 */     return this._delegate.setAndReturn(paramObject1, paramObject2);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/InnerClassProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */