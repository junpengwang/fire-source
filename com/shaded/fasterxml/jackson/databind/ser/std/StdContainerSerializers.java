/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.impl.IteratorSerializer;
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
/*    */ public class StdContainerSerializers
/*    */ {
/*    */   public static ContainerSerializer<?> indexedListSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*    */   {
/* 25 */     return new IndexedListSerializer(paramJavaType, paramBoolean, paramTypeSerializer, null, paramJsonSerializer);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ContainerSerializer<?> collectionSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer)
/*    */   {
/* 34 */     return new CollectionSerializer(paramJavaType, paramBoolean, paramTypeSerializer, null, paramJsonSerializer);
/*    */   }
/*    */   
/*    */ 
/*    */   public static ContainerSerializer<?> iteratorSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer)
/*    */   {
/* 40 */     return new IteratorSerializer(paramJavaType, paramBoolean, paramTypeSerializer, null);
/*    */   }
/*    */   
/*    */ 
/*    */   public static ContainerSerializer<?> iterableSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer)
/*    */   {
/* 46 */     return new IterableSerializer(paramJavaType, paramBoolean, paramTypeSerializer, null);
/*    */   }
/*    */   
/*    */   public static JsonSerializer<?> enumSetSerializer(JavaType paramJavaType)
/*    */   {
/* 51 */     return new EnumSetSerializer(paramJavaType, null);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static ContainerSerializer<?> indexedListSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty, JsonSerializer<Object> paramJsonSerializer)
/*    */   {
/* 68 */     return indexedListSerializer(paramJavaType, paramBoolean, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static ContainerSerializer<?> collectionSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty, JsonSerializer<Object> paramJsonSerializer)
/*    */   {
/* 79 */     return collectionSerializer(paramJavaType, paramBoolean, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StdContainerSerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */