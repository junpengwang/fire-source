/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
/*    */ import java.util.EnumSet;
/*    */ 
/*    */ public class EnumSetSerializer extends AsArraySerializerBase<EnumSet<? extends Enum<?>>>
/*    */ {
/*    */   public EnumSetSerializer(JavaType paramJavaType, BeanProperty paramBeanProperty)
/*    */   {
/* 16 */     super(EnumSet.class, paramJavaType, true, null, paramBeanProperty, null);
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumSetSerializer(EnumSetSerializer paramEnumSetSerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*    */   {
/* 22 */     super(paramEnumSetSerializer, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumSetSerializer _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*    */   {
/* 28 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumSetSerializer withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*    */   {
/* 34 */     return new EnumSetSerializer(this, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */   
/*    */   public boolean isEmpty(EnumSet<? extends Enum<?>> paramEnumSet)
/*    */   {
/* 39 */     return (paramEnumSet == null) || (paramEnumSet.isEmpty());
/*    */   }
/*    */   
/*    */   public boolean hasSingleElement(EnumSet<? extends Enum<?>> paramEnumSet)
/*    */   {
/* 44 */     return paramEnumSet.size() == 1;
/*    */   }
/*    */   
/*    */ 
/*    */   public void serializeContents(EnumSet<? extends Enum<?>> paramEnumSet, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, com.shaded.fasterxml.jackson.core.JsonGenerationException
/*    */   {
/* 51 */     JsonSerializer localJsonSerializer = this._elementSerializer;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 56 */     for (Enum localEnum : paramEnumSet) {
/* 57 */       if (localJsonSerializer == null)
/*    */       {
/*    */ 
/*    */ 
/* 61 */         localJsonSerializer = paramSerializerProvider.findValueSerializer(localEnum.getDeclaringClass(), this._property);
/*    */       }
/* 63 */       localJsonSerializer.serialize(localEnum, paramJsonGenerator, paramSerializerProvider);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/EnumSetSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */