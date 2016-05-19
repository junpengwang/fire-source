/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableAnyProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import java.io.IOException;
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
/*     */ public abstract class PropertyValue
/*     */ {
/*     */   public final PropertyValue next;
/*     */   public final Object value;
/*     */   
/*     */   protected PropertyValue(PropertyValue paramPropertyValue, Object paramObject)
/*     */   {
/*  25 */     this.next = paramPropertyValue;
/*  26 */     this.value = paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void assign(Object paramObject)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class Regular
/*     */     extends PropertyValue
/*     */   {
/*     */     final SettableBeanProperty _property;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Regular(PropertyValue paramPropertyValue, Object paramObject, SettableBeanProperty paramSettableBeanProperty)
/*     */     {
/*  54 */       super(paramObject);
/*  55 */       this._property = paramSettableBeanProperty;
/*     */     }
/*     */     
/*     */ 
/*     */     public void assign(Object paramObject)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/*  62 */       this._property.set(paramObject, this.value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class Any
/*     */     extends PropertyValue
/*     */   {
/*     */     final SettableAnyProperty _property;
/*     */     
/*     */ 
/*     */ 
/*     */     final String _propertyName;
/*     */     
/*     */ 
/*     */ 
/*     */     public Any(PropertyValue paramPropertyValue, Object paramObject, SettableAnyProperty paramSettableAnyProperty, String paramString)
/*     */     {
/*  82 */       super(paramObject);
/*  83 */       this._property = paramSettableAnyProperty;
/*  84 */       this._propertyName = paramString;
/*     */     }
/*     */     
/*     */ 
/*     */     public void assign(Object paramObject)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/*  91 */       this._property.set(paramObject, this._propertyName, this.value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class Map
/*     */     extends PropertyValue
/*     */   {
/*     */     final Object _key;
/*     */     
/*     */ 
/*     */ 
/*     */     public Map(PropertyValue paramPropertyValue, Object paramObject1, Object paramObject2)
/*     */     {
/* 106 */       super(paramObject1);
/* 107 */       this._key = paramObject2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void assign(Object paramObject)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 115 */       ((Map)paramObject).put(this._key, this.value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/PropertyValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */