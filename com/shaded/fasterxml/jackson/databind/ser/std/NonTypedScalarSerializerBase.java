/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NonTypedScalarSerializerBase<T>
/*    */   extends StdScalarSerializer<T>
/*    */ {
/*    */   protected NonTypedScalarSerializerBase(Class<T> paramClass)
/*    */   {
/* 21 */     super(paramClass);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 30 */     serialize(paramT, paramJsonGenerator, paramSerializerProvider);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/NonTypedScalarSerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */