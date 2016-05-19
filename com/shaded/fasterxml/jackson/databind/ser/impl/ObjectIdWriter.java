/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
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
/*    */ public final class ObjectIdWriter
/*    */ {
/*    */   public final JavaType idType;
/*    */   public final SerializedString propertyName;
/*    */   public final ObjectIdGenerator<?> generator;
/*    */   public final JsonSerializer<Object> serializer;
/*    */   public final boolean alwaysAsId;
/*    */   
/*    */   protected ObjectIdWriter(JavaType paramJavaType, SerializedString paramSerializedString, ObjectIdGenerator<?> paramObjectIdGenerator, JsonSerializer<?> paramJsonSerializer, boolean paramBoolean)
/*    */   {
/* 53 */     this.idType = paramJavaType;
/* 54 */     this.propertyName = paramSerializedString;
/* 55 */     this.generator = paramObjectIdGenerator;
/* 56 */     this.serializer = paramJsonSerializer;
/* 57 */     this.alwaysAsId = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ObjectIdWriter construct(JavaType paramJavaType, String paramString, ObjectIdGenerator<?> paramObjectIdGenerator, boolean paramBoolean)
/*    */   {
/* 68 */     SerializedString localSerializedString = paramString == null ? null : new SerializedString(paramString);
/* 69 */     return new ObjectIdWriter(paramJavaType, localSerializedString, paramObjectIdGenerator, null, paramBoolean);
/*    */   }
/*    */   
/*    */   public ObjectIdWriter withSerializer(JsonSerializer<?> paramJsonSerializer) {
/* 73 */     return new ObjectIdWriter(this.idType, this.propertyName, this.generator, paramJsonSerializer, this.alwaysAsId);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ObjectIdWriter withAlwaysAsId(boolean paramBoolean)
/*    */   {
/* 80 */     if (paramBoolean == this.alwaysAsId) {
/* 81 */       return this;
/*    */     }
/* 83 */     return new ObjectIdWriter(this.idType, this.propertyName, this.generator, this.serializer, paramBoolean);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/ObjectIdWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */