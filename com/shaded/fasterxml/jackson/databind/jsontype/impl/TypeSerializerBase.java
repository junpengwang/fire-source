/*    */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ 
/*    */ public abstract class TypeSerializerBase
/*    */   extends TypeSerializer
/*    */ {
/*    */   protected final TypeIdResolver _idResolver;
/*    */   protected final BeanProperty _property;
/*    */   
/*    */   protected TypeSerializerBase(TypeIdResolver paramTypeIdResolver, BeanProperty paramBeanProperty)
/*    */   {
/* 16 */     this._idResolver = paramTypeIdResolver;
/* 17 */     this._property = paramBeanProperty;
/*    */   }
/*    */   
/*    */   public abstract JsonTypeInfo.As getTypeInclusion();
/*    */   
/*    */   public String getPropertyName()
/*    */   {
/* 24 */     return null;
/*    */   }
/*    */   
/* 27 */   public TypeIdResolver getTypeIdResolver() { return this._idResolver; }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected String idFromValue(Object paramObject)
/*    */   {
/* 36 */     return this._idResolver.idFromValue(paramObject);
/*    */   }
/*    */   
/*    */   protected String idFromValueAndType(Object paramObject, Class<?> paramClass) {
/* 40 */     return this._idResolver.idFromValueAndType(paramObject, paramClass);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/TypeSerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */