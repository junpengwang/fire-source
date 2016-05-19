/*    */ package com.shaded.fasterxml.jackson.databind.module;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*    */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiators.Base;
/*    */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleValueInstantiators
/*    */   extends ValueInstantiators.Base
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8929386427526115130L;
/*    */   protected HashMap<ClassKey, ValueInstantiator> _classMappings;
/*    */   
/*    */   public SimpleValueInstantiators()
/*    */   {
/* 31 */     this._classMappings = new HashMap();
/*    */   }
/*    */   
/*    */ 
/*    */   public SimpleValueInstantiators addValueInstantiator(Class<?> paramClass, ValueInstantiator paramValueInstantiator)
/*    */   {
/* 37 */     this._classMappings.put(new ClassKey(paramClass), paramValueInstantiator);
/* 38 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ValueInstantiator findValueInstantiator(DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription, ValueInstantiator paramValueInstantiator)
/*    */   {
/* 45 */     ValueInstantiator localValueInstantiator = (ValueInstantiator)this._classMappings.get(new ClassKey(paramBeanDescription.getBeanClass()));
/* 46 */     return localValueInstantiator == null ? paramValueInstantiator : localValueInstantiator;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/module/SimpleValueInstantiators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */