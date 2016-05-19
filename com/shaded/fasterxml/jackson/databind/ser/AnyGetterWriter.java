/*    */ package com.shaded.fasterxml.jackson.databind.ser;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.MapSerializer;
/*    */ import java.util.Map;
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
/*    */ public class AnyGetterWriter
/*    */ {
/*    */   protected final BeanProperty _property;
/*    */   protected final AnnotatedMember _accessor;
/*    */   protected MapSerializer _serializer;
/*    */   
/*    */   public AnyGetterWriter(BeanProperty paramBeanProperty, AnnotatedMember paramAnnotatedMember, MapSerializer paramMapSerializer)
/*    */   {
/* 29 */     this._accessor = paramAnnotatedMember;
/* 30 */     this._property = paramBeanProperty;
/* 31 */     this._serializer = paramMapSerializer;
/*    */   }
/*    */   
/*    */   public void getAndSerialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws Exception
/*    */   {
/* 37 */     Object localObject = this._accessor.getValue(paramObject);
/* 38 */     if (localObject == null) {
/* 39 */       return;
/*    */     }
/* 41 */     if (!(localObject instanceof Map)) {
/* 42 */       throw new JsonMappingException("Value returned by 'any-getter' (" + this._accessor.getName() + "()) not java.util.Map but " + localObject.getClass().getName());
/*    */     }
/*    */     
/* 45 */     this._serializer.serializeFields((Map)localObject, paramJsonGenerator, paramSerializerProvider);
/*    */   }
/*    */   
/*    */   public void resolve(SerializerProvider paramSerializerProvider)
/*    */     throws JsonMappingException
/*    */   {
/* 51 */     this._serializer = ((MapSerializer)this._serializer.createContextual(paramSerializerProvider, this._property));
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/AnyGetterWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */