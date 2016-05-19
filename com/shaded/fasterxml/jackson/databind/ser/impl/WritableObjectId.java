/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.ObjectIdGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WritableObjectId
/*    */ {
/*    */   public final ObjectIdGenerator<?> generator;
/*    */   public Object id;
/* 22 */   protected boolean idWritten = false;
/*    */   
/*    */   public WritableObjectId(ObjectIdGenerator<?> paramObjectIdGenerator) {
/* 25 */     this.generator = paramObjectIdGenerator;
/*    */   }
/*    */   
/*    */   public boolean writeAsId(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, ObjectIdWriter paramObjectIdWriter)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 31 */     if ((this.id != null) && ((this.idWritten) || (paramObjectIdWriter.alwaysAsId))) {
/* 32 */       paramObjectIdWriter.serializer.serialize(this.id, paramJsonGenerator, paramSerializerProvider);
/* 33 */       return true;
/*    */     }
/* 35 */     return false;
/*    */   }
/*    */   
/*    */   public Object generateId(Object paramObject) {
/* 39 */     return this.id = this.generator.generateId(paramObject);
/*    */   }
/*    */   
/*    */ 
/*    */   public void writeAsField(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, ObjectIdWriter paramObjectIdWriter)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 46 */     SerializedString localSerializedString = paramObjectIdWriter.propertyName;
/* 47 */     this.idWritten = true;
/* 48 */     if (localSerializedString != null) {
/* 49 */       paramJsonGenerator.writeFieldName(localSerializedString);
/* 50 */       paramObjectIdWriter.serializer.serialize(this.id, paramJsonGenerator, paramSerializerProvider);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/WritableObjectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */