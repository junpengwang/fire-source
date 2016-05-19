/*    */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ContainerDeserializerBase<T>
/*    */   extends StdDeserializer<T>
/*    */ {
/*    */   protected ContainerDeserializerBase(Class<?> paramClass)
/*    */   {
/* 17 */     super(paramClass);
/*    */   }
/*    */   
/*    */   public abstract JavaType getContentType();
/*    */   
/*    */   public abstract JsonDeserializer<Object> getContentDeserializer();
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/ContainerDeserializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */