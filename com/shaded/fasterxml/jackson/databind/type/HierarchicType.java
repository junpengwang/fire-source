/*    */ package com.shaded.fasterxml.jackson.databind.type;
/*    */ 
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
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
/*    */ public class HierarchicType
/*    */ {
/*    */   protected final Type _actualType;
/*    */   protected final Class<?> _rawClass;
/*    */   protected final ParameterizedType _genericType;
/*    */   protected HierarchicType _superType;
/*    */   protected HierarchicType _subType;
/*    */   
/*    */   public HierarchicType(Type paramType)
/*    */   {
/* 30 */     this._actualType = paramType;
/* 31 */     if ((paramType instanceof Class)) {
/* 32 */       this._rawClass = ((Class)paramType);
/* 33 */       this._genericType = null;
/* 34 */     } else if ((paramType instanceof ParameterizedType)) {
/* 35 */       this._genericType = ((ParameterizedType)paramType);
/* 36 */       this._rawClass = ((Class)this._genericType.getRawType());
/*    */     } else {
/* 38 */       throw new IllegalArgumentException("Type " + paramType.getClass().getName() + " can not be used to construct HierarchicType");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   private HierarchicType(Type paramType, Class<?> paramClass, ParameterizedType paramParameterizedType, HierarchicType paramHierarchicType1, HierarchicType paramHierarchicType2)
/*    */   {
/* 45 */     this._actualType = paramType;
/* 46 */     this._rawClass = paramClass;
/* 47 */     this._genericType = paramParameterizedType;
/* 48 */     this._superType = paramHierarchicType1;
/* 49 */     this._subType = paramHierarchicType2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public HierarchicType deepCloneWithoutSubtype()
/*    */   {
/* 58 */     HierarchicType localHierarchicType1 = this._superType == null ? null : this._superType.deepCloneWithoutSubtype();
/* 59 */     HierarchicType localHierarchicType2 = new HierarchicType(this._actualType, this._rawClass, this._genericType, localHierarchicType1, null);
/* 60 */     if (localHierarchicType1 != null) {
/* 61 */       localHierarchicType1.setSubType(localHierarchicType2);
/*    */     }
/* 63 */     return localHierarchicType2;
/*    */   }
/*    */   
/* 66 */   public void setSuperType(HierarchicType paramHierarchicType) { this._superType = paramHierarchicType; }
/* 67 */   public final HierarchicType getSuperType() { return this._superType; }
/* 68 */   public void setSubType(HierarchicType paramHierarchicType) { this._subType = paramHierarchicType; }
/* 69 */   public final HierarchicType getSubType() { return this._subType; }
/*    */   
/* 71 */   public final boolean isGeneric() { return this._genericType != null; }
/* 72 */   public final ParameterizedType asGeneric() { return this._genericType; }
/*    */   
/* 74 */   public final Class<?> getRawClass() { return this._rawClass; }
/*    */   
/*    */   public String toString()
/*    */   {
/* 78 */     if (this._genericType != null) {
/* 79 */       return this._genericType.toString();
/*    */     }
/* 81 */     return this._rawClass.getName();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/HierarchicType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */