/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public final class ManagedReferenceProperty
/*     */   extends SettableBeanProperty
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final String _referenceName;
/*     */   protected final boolean _isContainer;
/*     */   protected final SettableBeanProperty _managedProperty;
/*     */   protected final SettableBeanProperty _backProperty;
/*     */   
/*     */   public ManagedReferenceProperty(SettableBeanProperty paramSettableBeanProperty1, String paramString, SettableBeanProperty paramSettableBeanProperty2, Annotations paramAnnotations, boolean paramBoolean)
/*     */   {
/*  42 */     super(paramSettableBeanProperty1.getName(), paramSettableBeanProperty1.getType(), paramSettableBeanProperty1.getWrapperName(), paramSettableBeanProperty1.getValueTypeDeserializer(), paramAnnotations, paramSettableBeanProperty1.isRequired());
/*     */     
/*     */ 
/*  45 */     this._referenceName = paramString;
/*  46 */     this._managedProperty = paramSettableBeanProperty1;
/*  47 */     this._backProperty = paramSettableBeanProperty2;
/*  48 */     this._isContainer = paramBoolean;
/*     */   }
/*     */   
/*     */   protected ManagedReferenceProperty(ManagedReferenceProperty paramManagedReferenceProperty, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  53 */     super(paramManagedReferenceProperty, paramJsonDeserializer);
/*  54 */     this._referenceName = paramManagedReferenceProperty._referenceName;
/*  55 */     this._isContainer = paramManagedReferenceProperty._isContainer;
/*  56 */     this._managedProperty = paramManagedReferenceProperty._managedProperty;
/*  57 */     this._backProperty = paramManagedReferenceProperty._backProperty;
/*     */   }
/*     */   
/*     */   protected ManagedReferenceProperty(ManagedReferenceProperty paramManagedReferenceProperty, String paramString) {
/*  61 */     super(paramManagedReferenceProperty, paramString);
/*  62 */     this._referenceName = paramManagedReferenceProperty._referenceName;
/*  63 */     this._isContainer = paramManagedReferenceProperty._isContainer;
/*  64 */     this._managedProperty = paramManagedReferenceProperty._managedProperty;
/*  65 */     this._backProperty = paramManagedReferenceProperty._backProperty;
/*     */   }
/*     */   
/*     */   public ManagedReferenceProperty withName(String paramString)
/*     */   {
/*  70 */     return new ManagedReferenceProperty(this, paramString);
/*     */   }
/*     */   
/*     */   public ManagedReferenceProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  75 */     return new ManagedReferenceProperty(this, paramJsonDeserializer);
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
/*  86 */     return this._managedProperty.getAnnotation(paramClass);
/*     */   }
/*     */   
/*  89 */   public AnnotatedMember getMember() { return this._managedProperty.getMember(); }
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
/* 102 */     set(paramObject, this._managedProperty.deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 110 */     return setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
/*     */   }
/*     */   
/*     */ 
/*     */   public final void set(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 117 */     setAndReturn(paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object setAndReturn(Object paramObject1, Object paramObject2)
/*     */     throws IOException
/*     */   {
/* 124 */     Object localObject1 = this._managedProperty.setAndReturn(paramObject1, paramObject2);
/*     */     
/*     */ 
/*     */ 
/* 128 */     if (paramObject2 != null) {
/* 129 */       if (this._isContainer) {
/* 130 */         if ((paramObject2 instanceof Object[])) {
/* 131 */           for (Object localObject4 : (Object[])paramObject2)
/* 132 */             if (localObject4 != null)
/* 133 */               this._backProperty.set(localObject4, paramObject1);
/*     */         } else {
/*     */           Object localObject3;
/* 136 */           if ((paramObject2 instanceof Collection)) {
/* 137 */             for (??? = ((Collection)paramObject2).iterator(); ((Iterator)???).hasNext();) { localObject3 = ((Iterator)???).next();
/* 138 */               if (localObject3 != null) {
/* 139 */                 this._backProperty.set(localObject3, paramObject1);
/*     */               }
/*     */             }
/* 142 */           } else if ((paramObject2 instanceof Map)) {
/* 143 */             for (??? = ((Map)paramObject2).values().iterator(); ((Iterator)???).hasNext();) { localObject3 = ((Iterator)???).next();
/* 144 */               if (localObject3 != null) {
/* 145 */                 this._backProperty.set(localObject3, paramObject1);
/*     */               }
/*     */             }
/*     */           } else {
/* 149 */             throw new IllegalStateException("Unsupported container type (" + paramObject2.getClass().getName() + ") when resolving reference '" + this._referenceName + "'");
/*     */           }
/*     */         }
/*     */       } else {
/* 153 */         this._backProperty.set(paramObject2, paramObject1);
/*     */       }
/*     */     }
/* 156 */     return localObject1;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/ManagedReferenceProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */