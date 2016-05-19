/*    */ package com.shaded.fasterxml.jackson.core.sym;
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
/*    */ public final class Name1
/*    */   extends Name
/*    */ {
/* 15 */   static final Name1 sEmptyName = new Name1("", 0, 0);
/*    */   
/*    */   final int mQuad;
/*    */   
/*    */   Name1(String paramString, int paramInt1, int paramInt2)
/*    */   {
/* 21 */     super(paramString, paramInt1);
/* 22 */     this.mQuad = paramInt2;
/*    */   }
/*    */   
/* 25 */   static Name1 getEmptyName() { return sEmptyName; }
/*    */   
/*    */ 
/*    */   public boolean equals(int paramInt)
/*    */   {
/* 30 */     return paramInt == this.mQuad;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean equals(int paramInt1, int paramInt2)
/*    */   {
/* 36 */     return (paramInt1 == this.mQuad) && (paramInt2 == 0);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean equals(int[] paramArrayOfInt, int paramInt)
/*    */   {
/* 42 */     return (paramInt == 1) && (paramArrayOfInt[0] == this.mQuad);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/sym/Name1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */