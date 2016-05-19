/*    */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
/*    */ 
/*    */ public class PropertyBasedObjectIdGenerator extends ObjectIdGenerators.PropertyGenerator
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public PropertyBasedObjectIdGenerator(Class<?> paramClass)
/*    */   {
/* 13 */     super(paramClass);
/*    */   }
/*    */   
/*    */   public Object generateId(Object paramObject)
/*    */   {
/* 18 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public ObjectIdGenerator<Object> forScope(Class<?> paramClass)
/*    */   {
/* 23 */     return paramClass == this._scope ? this : new PropertyBasedObjectIdGenerator(paramClass);
/*    */   }
/*    */   
/*    */   public ObjectIdGenerator<Object> newForSerialization(Object paramObject)
/*    */   {
/* 28 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */   public ObjectIdGenerator.IdKey key(Object paramObject)
/*    */   {
/* 34 */     return new ObjectIdGenerator.IdKey(getClass(), this._scope, paramObject);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/PropertyBasedObjectIdGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */