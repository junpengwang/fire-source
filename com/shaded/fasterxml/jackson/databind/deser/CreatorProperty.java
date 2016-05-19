/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreatorProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final AnnotatedParameter _annotated;
/*     */   protected final Object _injectableValueId;
/*     */   protected final int _creatorIndex;
/*     */   
/*     */   @Deprecated
/*     */   public CreatorProperty(String paramString, JavaType paramJavaType, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations, AnnotatedParameter paramAnnotatedParameter, int paramInt, Object paramObject)
/*     */   {
/*  60 */     this(paramString, paramJavaType, null, paramTypeDeserializer, paramAnnotations, paramAnnotatedParameter, paramInt, paramObject, true);
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
/*     */   public CreatorProperty(String paramString, JavaType paramJavaType, PropertyName paramPropertyName, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations, AnnotatedParameter paramAnnotatedParameter, int paramInt, Object paramObject, boolean paramBoolean)
/*     */   {
/*  82 */     super(paramString, paramJavaType, paramPropertyName, paramTypeDeserializer, paramAnnotations, paramBoolean);
/*  83 */     this._annotated = paramAnnotatedParameter;
/*  84 */     this._creatorIndex = paramInt;
/*  85 */     this._injectableValueId = paramObject;
/*     */   }
/*     */   
/*     */   protected CreatorProperty(CreatorProperty paramCreatorProperty, String paramString) {
/*  89 */     super(paramCreatorProperty, paramString);
/*  90 */     this._annotated = paramCreatorProperty._annotated;
/*  91 */     this._creatorIndex = paramCreatorProperty._creatorIndex;
/*  92 */     this._injectableValueId = paramCreatorProperty._injectableValueId;
/*     */   }
/*     */   
/*     */   protected CreatorProperty(CreatorProperty paramCreatorProperty, JsonDeserializer<?> paramJsonDeserializer) {
/*  96 */     super(paramCreatorProperty, paramJsonDeserializer);
/*  97 */     this._annotated = paramCreatorProperty._annotated;
/*  98 */     this._creatorIndex = paramCreatorProperty._creatorIndex;
/*  99 */     this._injectableValueId = paramCreatorProperty._injectableValueId;
/*     */   }
/*     */   
/*     */   public CreatorProperty withName(String paramString)
/*     */   {
/* 104 */     return new CreatorProperty(this, paramString);
/*     */   }
/*     */   
/*     */   public CreatorProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 109 */     return new CreatorProperty(this, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object findInjectableValue(DeserializationContext paramDeserializationContext, Object paramObject)
/*     */   {
/* 118 */     if (this._injectableValueId == null) {
/* 119 */       throw new IllegalStateException("Property '" + getName() + "' (type " + getClass().getName() + ") has no injectable value id configured");
/*     */     }
/*     */     
/* 122 */     return paramDeserializationContext.findInjectableValue(this._injectableValueId, this, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void inject(DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException
/*     */   {
/* 131 */     set(paramObject, findInjectableValue(paramDeserializationContext, paramObject));
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
/* 142 */     if (this._annotated == null) {
/* 143 */       return null;
/*     */     }
/* 145 */     return this._annotated.getAnnotation(paramClass);
/*     */   }
/*     */   
/* 148 */   public AnnotatedMember getMember() { return this._annotated; }
/*     */   
/*     */   public int getCreatorIndex() {
/* 151 */     return this._creatorIndex;
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
/*     */   public void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 165 */     set(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 173 */     return setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 182 */     throw new IllegalStateException("Method should never be called on a " + getClass().getName());
/*     */   }
/*     */   
/*     */   public Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 188 */     return paramObject1;
/*     */   }
/*     */   
/*     */   public Object getInjectableValueId()
/*     */   {
/* 193 */     return this._injectableValueId;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 197 */     return "[creator property, name '" + getName() + "'; inject id '" + this._injectableValueId + "']";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/CreatorProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */