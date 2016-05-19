/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*    */ 
/*    */ 
/*    */ public class PropertyBasedObjectIdGenerator
/*    */   extends ObjectIdGenerators.PropertyGenerator
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final BeanPropertyWriter _property;
/*    */   
/*    */   public PropertyBasedObjectIdGenerator(ObjectIdInfo paramObjectIdInfo, BeanPropertyWriter paramBeanPropertyWriter)
/*    */   {
/* 18 */     this(paramObjectIdInfo.getScope(), paramBeanPropertyWriter);
/*    */   }
/*    */   
/*    */   protected PropertyBasedObjectIdGenerator(Class<?> paramClass, BeanPropertyWriter paramBeanPropertyWriter)
/*    */   {
/* 23 */     super(paramClass);
/* 24 */     this._property = paramBeanPropertyWriter;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean canUseFor(ObjectIdGenerator<?> paramObjectIdGenerator)
/*    */   {
/* 33 */     if (paramObjectIdGenerator.getClass() == getClass()) {
/* 34 */       PropertyBasedObjectIdGenerator localPropertyBasedObjectIdGenerator = (PropertyBasedObjectIdGenerator)paramObjectIdGenerator;
/* 35 */       if (localPropertyBasedObjectIdGenerator.getScope() == this._scope)
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 42 */         return localPropertyBasedObjectIdGenerator._property == this._property;
/*    */       }
/*    */     }
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public Object generateId(Object paramObject)
/*    */   {
/*    */     try {
/* 51 */       return this._property.get(paramObject);
/*    */     } catch (RuntimeException localRuntimeException) {
/* 53 */       throw localRuntimeException;
/*    */     } catch (Exception localException) {
/* 55 */       throw new IllegalStateException("Problem accessing property '" + this._property.getName() + "': " + localException.getMessage(), localException);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public ObjectIdGenerator<Object> forScope(Class<?> paramClass)
/*    */   {
/* 62 */     return paramClass == this._scope ? this : new PropertyBasedObjectIdGenerator(paramClass, this._property);
/*    */   }
/*    */   
/*    */ 
/*    */   public ObjectIdGenerator<Object> newForSerialization(Object paramObject)
/*    */   {
/* 68 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */   public ObjectIdGenerator.IdKey key(Object paramObject)
/*    */   {
/* 74 */     return new ObjectIdGenerator.IdKey(getClass(), this._scope, paramObject);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/PropertyBasedObjectIdGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */