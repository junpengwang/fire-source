/*    */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty.Std;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*    */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ 
/*    */ public class ValueInjector
/*    */   extends BeanProperty.Std
/*    */ {
/*    */   protected final Object _valueId;
/*    */   
/*    */   public ValueInjector(String paramString, JavaType paramJavaType, Annotations paramAnnotations, AnnotatedMember paramAnnotatedMember, Object paramObject)
/*    */   {
/* 30 */     super(paramString, paramJavaType, null, paramAnnotations, paramAnnotatedMember, false);
/* 31 */     this._valueId = paramObject;
/*    */   }
/*    */   
/*    */   public Object findValue(DeserializationContext paramDeserializationContext, Object paramObject)
/*    */   {
/* 36 */     return paramDeserializationContext.findInjectableValue(this._valueId, this, paramObject);
/*    */   }
/*    */   
/*    */   public void inject(DeserializationContext paramDeserializationContext, Object paramObject)
/*    */     throws IOException
/*    */   {
/* 42 */     this._member.setValue(paramObject, findValue(paramDeserializationContext, paramObject));
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/ValueInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */