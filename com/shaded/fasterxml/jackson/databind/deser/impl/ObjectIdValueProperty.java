/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectIdValueProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final ObjectIdReader _objectIdReader;
/*     */   
/*     */   @Deprecated
/*     */   public ObjectIdValueProperty(ObjectIdReader paramObjectIdReader)
/*     */   {
/*  27 */     this(paramObjectIdReader, true);
/*     */   }
/*     */   
/*     */ 
/*     */   public ObjectIdValueProperty(ObjectIdReader paramObjectIdReader, boolean paramBoolean)
/*     */   {
/*  33 */     super(paramObjectIdReader.propertyName, paramObjectIdReader.idType, null, null, null, paramBoolean);
/*     */     
/*  35 */     this._objectIdReader = paramObjectIdReader;
/*  36 */     this._valueDeserializer = paramObjectIdReader.deserializer;
/*     */   }
/*     */   
/*     */   protected ObjectIdValueProperty(ObjectIdValueProperty paramObjectIdValueProperty, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  41 */     super(paramObjectIdValueProperty, paramJsonDeserializer);
/*  42 */     this._objectIdReader = paramObjectIdValueProperty._objectIdReader;
/*     */   }
/*     */   
/*     */   protected ObjectIdValueProperty(ObjectIdValueProperty paramObjectIdValueProperty, String paramString) {
/*  46 */     super(paramObjectIdValueProperty, paramString);
/*  47 */     this._objectIdReader = paramObjectIdValueProperty._objectIdReader;
/*     */   }
/*     */   
/*     */   public ObjectIdValueProperty withName(String paramString)
/*     */   {
/*  52 */     return new ObjectIdValueProperty(this, paramString);
/*     */   }
/*     */   
/*     */   public ObjectIdValueProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  57 */     return new ObjectIdValueProperty(this, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */   {
/*  64 */     return null;
/*     */   }
/*     */   
/*  67 */   public AnnotatedMember getMember() { return null; }
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
/*  80 */     deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  89 */     Object localObject = this._valueDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*  90 */     ReadableObjectId localReadableObjectId = paramDeserializationContext.findObjectId(localObject, this._objectIdReader.generator);
/*  91 */     localReadableObjectId.bindItem(paramObject);
/*     */     
/*  93 */     SettableBeanProperty localSettableBeanProperty = this._objectIdReader.idProperty;
/*  94 */     if (localSettableBeanProperty != null) {
/*  95 */       return localSettableBeanProperty.setAndReturn(paramObject, localObject);
/*     */     }
/*  97 */     return paramObject;
/*     */   }
/*     */   
/*     */   public void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 103 */     setAndReturn(paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 110 */     SettableBeanProperty localSettableBeanProperty = this._objectIdReader.idProperty;
/* 111 */     if (localSettableBeanProperty == null) {
/* 112 */       throw new UnsupportedOperationException("Should not call set() on ObjectIdProperty that has no SettableBeanProperty");
/*     */     }
/*     */     
/* 115 */     return localSettableBeanProperty.setAndReturn(paramObject1, paramObject2);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/ObjectIdValueProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */