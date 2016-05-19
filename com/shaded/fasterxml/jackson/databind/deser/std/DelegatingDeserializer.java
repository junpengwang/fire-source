/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
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
/*     */ public abstract class DelegatingDeserializer
/*     */   extends StdDeserializer<Object>
/*     */   implements ContextualDeserializer, ResolvableDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final JsonDeserializer<?> _delegatee;
/*     */   
/*     */   public DelegatingDeserializer(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  37 */     super(_figureType(paramJsonDeserializer));
/*  38 */     this._delegatee = paramJsonDeserializer;
/*     */   }
/*     */   
/*     */   protected abstract JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> paramJsonDeserializer);
/*     */   
/*     */   private static Class<?> _figureType(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  45 */     if ((paramJsonDeserializer instanceof StdDeserializer)) {
/*  46 */       return ((StdDeserializer)paramJsonDeserializer).getValueClass();
/*     */     }
/*  48 */     return Object.class;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resolve(DeserializationContext paramDeserializationContext)
/*     */     throws JsonMappingException
/*     */   {
/*  59 */     if ((this._delegatee instanceof ResolvableDeserializer)) {
/*  60 */       ((ResolvableDeserializer)this._delegatee).resolve(paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/*  69 */     JsonDeserializer localJsonDeserializer = this._delegatee;
/*  70 */     if ((localJsonDeserializer instanceof ContextualDeserializer)) {
/*  71 */       localJsonDeserializer = ((ContextualDeserializer)localJsonDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
/*     */     }
/*  73 */     return _createContextual(paramDeserializationContext, paramBeanProperty, localJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */   protected JsonDeserializer<?> _createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  79 */     if (paramJsonDeserializer == this._delegatee) {
/*  80 */       return this;
/*     */     }
/*  82 */     return newDelegatingInstance(paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  95 */     return this._delegatee.deserialize(paramJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 104 */     return this._delegatee.deserialize(paramJsonParser, paramDeserializationContext, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 112 */     return this._delegatee.deserializeWithType(paramJsonParser, paramDeserializationContext, paramTypeDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> replaceDelegatee(JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/* 124 */     if (paramJsonDeserializer == this._delegatee) {
/* 125 */       return this;
/*     */     }
/* 127 */     return newDelegatingInstance(paramJsonDeserializer);
/*     */   }
/*     */   
/*     */   public Object getNullValue() {
/* 131 */     return this._delegatee.getNullValue();
/*     */   }
/*     */   
/* 134 */   public Object getEmptyValue() { return this._delegatee.getEmptyValue(); }
/*     */   
/*     */   public Collection<Object> getKnownPropertyNames() {
/* 137 */     return this._delegatee.getKnownPropertyNames();
/*     */   }
/*     */   
/* 140 */   public boolean isCachable() { return false; }
/*     */   
/*     */   public ObjectIdReader getObjectIdReader() {
/* 143 */     return this._delegatee.getObjectIdReader();
/*     */   }
/*     */   
/*     */   public JsonDeserializer<?> getDelegatee() {
/* 147 */     return this._delegatee;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/DelegatingDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */