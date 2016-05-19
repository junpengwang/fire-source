/*    */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*    */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TypeIdResolverBase
/*    */   implements TypeIdResolver
/*    */ {
/*    */   protected final TypeFactory _typeFactory;
/*    */   protected final JavaType _baseType;
/*    */   
/*    */   protected TypeIdResolverBase(JavaType paramJavaType, TypeFactory paramTypeFactory)
/*    */   {
/* 19 */     this._baseType = paramJavaType;
/* 20 */     this._typeFactory = paramTypeFactory;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void init(JavaType paramJavaType) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String idFromBaseType()
/*    */   {
/* 36 */     return idFromValueAndType(null, this._baseType.getRawClass());
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/TypeIdResolverBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */