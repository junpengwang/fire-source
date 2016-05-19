/*    */ package com.shaded.fasterxml.jackson.databind.module;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.deser.KeyDeserializers;
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
/*    */ public class SimpleKeyDeserializers
/*    */   implements KeyDeserializers, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6786398737835438187L;
/* 27 */   protected HashMap<ClassKey, KeyDeserializer> _classMappings = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SimpleKeyDeserializers addDeserializer(Class<?> paramClass, KeyDeserializer paramKeyDeserializer)
/*    */   {
/* 39 */     if (this._classMappings == null) {
/* 40 */       this._classMappings = new HashMap();
/*    */     }
/* 42 */     this._classMappings.put(new ClassKey(paramClass), paramKeyDeserializer);
/* 43 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public KeyDeserializer findKeyDeserializer(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
/*    */   {
/* 56 */     if (this._classMappings == null) {
/* 57 */       return null;
/*    */     }
/* 59 */     return (KeyDeserializer)this._classMappings.get(new ClassKey(paramJavaType.getRawClass()));
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/module/SimpleKeyDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */