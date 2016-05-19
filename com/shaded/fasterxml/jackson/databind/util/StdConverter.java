/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
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
/*    */ public abstract class StdConverter<IN, OUT>
/*    */   implements Converter<IN, OUT>
/*    */ {
/*    */   public abstract OUT convert(IN paramIN);
/*    */   
/*    */   public JavaType getInputType(TypeFactory paramTypeFactory)
/*    */   {
/* 28 */     JavaType[] arrayOfJavaType = paramTypeFactory.findTypeParameters(getClass(), Converter.class);
/* 29 */     if ((arrayOfJavaType == null) || (arrayOfJavaType.length < 2)) {
/* 30 */       throw new IllegalStateException("Can not find OUT type parameter for Converter of type " + getClass().getName());
/*    */     }
/* 32 */     return arrayOfJavaType[0];
/*    */   }
/*    */   
/*    */ 
/*    */   public JavaType getOutputType(TypeFactory paramTypeFactory)
/*    */   {
/* 38 */     JavaType[] arrayOfJavaType = paramTypeFactory.findTypeParameters(getClass(), Converter.class);
/* 39 */     if ((arrayOfJavaType == null) || (arrayOfJavaType.length < 2)) {
/* 40 */       throw new IllegalStateException("Can not find OUT type parameter for Converter of type " + getClass().getName());
/*    */     }
/* 42 */     return arrayOfJavaType[1];
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/StdConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */