/*    */ package com.shaded.fasterxml.jackson.databind.type;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public final class ClassKey
/*    */   implements Comparable<ClassKey>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String _className;
/*    */   private Class<?> _class;
/*    */   private int _hashCode;
/*    */   
/*    */   public ClassKey()
/*    */   {
/* 37 */     this._class = null;
/* 38 */     this._className = null;
/* 39 */     this._hashCode = 0;
/*    */   }
/*    */   
/*    */   public ClassKey(Class<?> paramClass)
/*    */   {
/* 44 */     this._class = paramClass;
/* 45 */     this._className = paramClass.getName();
/* 46 */     this._hashCode = this._className.hashCode();
/*    */   }
/*    */   
/*    */   public void reset(Class<?> paramClass)
/*    */   {
/* 51 */     this._class = paramClass;
/* 52 */     this._className = paramClass.getName();
/* 53 */     this._hashCode = this._className.hashCode();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int compareTo(ClassKey paramClassKey)
/*    */   {
/* 66 */     return this._className.compareTo(paramClassKey._className);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 78 */     if (paramObject == this) return true;
/* 79 */     if (paramObject == null) return false;
/* 80 */     if (paramObject.getClass() != getClass()) return false;
/* 81 */     ClassKey localClassKey = (ClassKey)paramObject;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 90 */     return localClassKey._class == this._class;
/*    */   }
/*    */   
/* 93 */   public int hashCode() { return this._hashCode; }
/*    */   
/* 95 */   public String toString() { return this._className; }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/ClassKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */