/*    */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StdScalarDeserializer<T>
/*    */   extends StdDeserializer<T>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected StdScalarDeserializer(Class<?> paramClass)
/*    */   {
/* 20 */     super(paramClass);
/*    */   }
/*    */   
/*    */   protected StdScalarDeserializer(JavaType paramJavaType) {
/* 24 */     super(paramJavaType);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 32 */     return paramTypeDeserializer.deserializeTypedFromScalar(paramJsonParser, paramDeserializationContext);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StdScalarDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */