/*    */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id;
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
/*    */ public class MinimalClassNameIdResolver
/*    */   extends ClassNameIdResolver
/*    */ {
/*    */   protected final String _basePackageName;
/*    */   protected final String _basePackagePrefix;
/*    */   
/*    */   protected MinimalClassNameIdResolver(JavaType paramJavaType, TypeFactory paramTypeFactory)
/*    */   {
/* 25 */     super(paramJavaType, paramTypeFactory);
/* 26 */     String str = paramJavaType.getRawClass().getName();
/* 27 */     int i = str.lastIndexOf('.');
/* 28 */     if (i < 0) {
/* 29 */       this._basePackageName = "";
/* 30 */       this._basePackagePrefix = ".";
/*    */     } else {
/* 32 */       this._basePackagePrefix = str.substring(0, i + 1);
/* 33 */       this._basePackageName = str.substring(0, i);
/*    */     }
/*    */   }
/*    */   
/*    */   public JsonTypeInfo.Id getMechanism() {
/* 38 */     return JsonTypeInfo.Id.MINIMAL_CLASS;
/*    */   }
/*    */   
/*    */   public String idFromValue(Object paramObject)
/*    */   {
/* 43 */     String str = paramObject.getClass().getName();
/* 44 */     if (str.startsWith(this._basePackagePrefix))
/*    */     {
/* 46 */       return str.substring(this._basePackagePrefix.length() - 1);
/*    */     }
/* 48 */     return str;
/*    */   }
/*    */   
/*    */ 
/*    */   public JavaType typeFromId(String paramString)
/*    */   {
/* 54 */     if (paramString.startsWith(".")) {
/* 55 */       StringBuilder localStringBuilder = new StringBuilder(paramString.length() + this._basePackageName.length());
/* 56 */       if (this._basePackageName.length() == 0)
/*    */       {
/* 58 */         localStringBuilder.append(paramString.substring(1));
/*    */       }
/*    */       else {
/* 61 */         localStringBuilder.append(this._basePackageName).append(paramString);
/*    */       }
/* 63 */       paramString = localStringBuilder.toString();
/*    */     }
/* 65 */     return super.typeFromId(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/MinimalClassNameIdResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */