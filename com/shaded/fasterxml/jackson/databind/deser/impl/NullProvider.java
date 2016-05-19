/*    */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NullProvider
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final Object _nullValue;
/*    */   private final boolean _isPrimitive;
/*    */   private final Class<?> _rawType;
/*    */   
/*    */   public NullProvider(JavaType paramJavaType, Object paramObject)
/*    */   {
/* 25 */     this._nullValue = paramObject;
/*    */     
/* 27 */     this._isPrimitive = paramJavaType.isPrimitive();
/* 28 */     this._rawType = paramJavaType.getRawClass();
/*    */   }
/*    */   
/*    */   public Object nullValue(DeserializationContext paramDeserializationContext) throws JsonProcessingException
/*    */   {
/* 33 */     if ((this._isPrimitive) && (paramDeserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES))) {
/* 34 */       throw paramDeserializationContext.mappingException("Can not map JSON null into type " + this._rawType.getName() + " (set DeserializationConfig.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES to 'false' to allow)");
/*    */     }
/*    */     
/* 37 */     return this._nullValue;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/NullProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */