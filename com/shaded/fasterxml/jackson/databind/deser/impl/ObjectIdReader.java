/*    */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*    */ import java.io.Serializable;
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
/*    */ public final class ObjectIdReader
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public final JavaType idType;
/*    */   public final String propertyName;
/*    */   public final ObjectIdGenerator<?> generator;
/*    */   public final JsonDeserializer<Object> deserializer;
/*    */   public final SettableBeanProperty idProperty;
/*    */   
/*    */   protected ObjectIdReader(JavaType paramJavaType, String paramString, ObjectIdGenerator<?> paramObjectIdGenerator, JsonDeserializer<?> paramJsonDeserializer, SettableBeanProperty paramSettableBeanProperty)
/*    */   {
/* 44 */     this.idType = paramJavaType;
/* 45 */     this.propertyName = paramString;
/* 46 */     this.generator = paramObjectIdGenerator;
/* 47 */     this.deserializer = paramJsonDeserializer;
/* 48 */     this.idProperty = paramSettableBeanProperty;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ObjectIdReader construct(JavaType paramJavaType, String paramString, ObjectIdGenerator<?> paramObjectIdGenerator, JsonDeserializer<?> paramJsonDeserializer, SettableBeanProperty paramSettableBeanProperty)
/*    */   {
/* 60 */     return new ObjectIdReader(paramJavaType, paramString, paramObjectIdGenerator, paramJsonDeserializer, paramSettableBeanProperty);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/ObjectIdReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */