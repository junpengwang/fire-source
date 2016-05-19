/*    */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
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
/*    */ public class AsExternalTypeDeserializer
/*    */   extends AsArrayTypeDeserializer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public AsExternalTypeDeserializer(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
/*    */   {
/* 25 */     super(paramJavaType, paramTypeIdResolver, paramString, paramBoolean, paramClass);
/*    */   }
/*    */   
/*    */   public AsExternalTypeDeserializer(AsExternalTypeDeserializer paramAsExternalTypeDeserializer, BeanProperty paramBeanProperty) {
/* 29 */     super(paramAsExternalTypeDeserializer, paramBeanProperty);
/*    */   }
/*    */   
/*    */ 
/*    */   public TypeDeserializer forProperty(BeanProperty paramBeanProperty)
/*    */   {
/* 35 */     if (paramBeanProperty == this._property) {
/* 36 */       return this;
/*    */     }
/* 38 */     return new AsExternalTypeDeserializer(this, paramBeanProperty);
/*    */   }
/*    */   
/*    */   public JsonTypeInfo.As getTypeInclusion()
/*    */   {
/* 43 */     return JsonTypeInfo.As.EXTERNAL_PROPERTY;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/AsExternalTypeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */