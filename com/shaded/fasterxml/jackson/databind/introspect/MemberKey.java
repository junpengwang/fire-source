/*    */ package com.shaded.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MemberKey
/*    */ {
/* 13 */   static final Class<?>[] NO_CLASSES = new Class[0];
/*    */   
/*    */   final String _name;
/*    */   final Class<?>[] _argTypes;
/*    */   
/*    */   public MemberKey(Method paramMethod)
/*    */   {
/* 20 */     this(paramMethod.getName(), paramMethod.getParameterTypes());
/*    */   }
/*    */   
/*    */   public MemberKey(Constructor<?> paramConstructor)
/*    */   {
/* 25 */     this("", paramConstructor.getParameterTypes());
/*    */   }
/*    */   
/*    */   public MemberKey(String paramString, Class<?>[] paramArrayOfClass)
/*    */   {
/* 30 */     this._name = paramString;
/* 31 */     this._argTypes = (paramArrayOfClass == null ? NO_CLASSES : paramArrayOfClass);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 36 */     return this._name + "(" + this._argTypes.length + "-args)";
/*    */   }
/*    */   
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 42 */     return this._name.hashCode() + this._argTypes.length;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 48 */     if (paramObject == this) return true;
/* 49 */     if (paramObject == null) return false;
/* 50 */     if (paramObject.getClass() != getClass()) {
/* 51 */       return false;
/*    */     }
/* 53 */     MemberKey localMemberKey = (MemberKey)paramObject;
/* 54 */     if (!this._name.equals(localMemberKey._name)) {
/* 55 */       return false;
/*    */     }
/* 57 */     Class[] arrayOfClass = localMemberKey._argTypes;
/* 58 */     int i = this._argTypes.length;
/* 59 */     if (arrayOfClass.length != i) {
/* 60 */       return false;
/*    */     }
/* 62 */     for (int j = 0; j < i; j++) {
/* 63 */       Class localClass1 = arrayOfClass[j];
/* 64 */       Class localClass2 = this._argTypes[j];
/* 65 */       if (localClass1 != localClass2)
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 76 */         if ((!localClass1.isAssignableFrom(localClass2)) && (!localClass2.isAssignableFrom(localClass1)))
/*    */         {
/*    */ 
/* 79 */           return false; } }
/*    */     }
/* 81 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/MemberKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */