/*    */ package com.shaded.fasterxml.jackson.core.sym;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Name
/*    */ {
/*    */   protected final String _name;
/*    */   
/*    */ 
/*    */   protected final int _hashCode;
/*    */   
/*    */ 
/*    */ 
/*    */   protected Name(String paramString, int paramInt)
/*    */   {
/* 17 */     this._name = paramString;
/* 18 */     this._hashCode = paramInt;
/*    */   }
/*    */   
/* 21 */   public String getName() { return this._name; }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract boolean equals(int paramInt);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract boolean equals(int paramInt1, int paramInt2);
/*    */   
/*    */ 
/*    */ 
/*    */   public abstract boolean equals(int[] paramArrayOfInt, int paramInt);
/*    */   
/*    */ 
/*    */ 
/* 41 */   public String toString() { return this._name; }
/*    */   
/* 43 */   public final int hashCode() { return this._hashCode; }
/*    */   
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 48 */     return paramObject == this;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/sym/Name.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */