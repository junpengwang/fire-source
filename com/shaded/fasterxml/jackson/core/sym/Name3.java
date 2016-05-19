/*    */ package com.shaded.fasterxml.jackson.core.sym;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Name3
/*    */   extends Name
/*    */ {
/*    */   final int mQuad1;
/*    */   
/*    */   final int mQuad2;
/*    */   
/*    */   final int mQuad3;
/*    */   
/*    */ 
/*    */   Name3(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 17 */     super(paramString, paramInt1);
/* 18 */     this.mQuad1 = paramInt2;
/* 19 */     this.mQuad2 = paramInt3;
/* 20 */     this.mQuad3 = paramInt4;
/*    */   }
/*    */   
/*    */   public boolean equals(int paramInt)
/*    */   {
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int paramInt1, int paramInt2) {
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int[] paramArrayOfInt, int paramInt)
/*    */   {
/* 34 */     return (paramInt == 3) && (paramArrayOfInt[0] == this.mQuad1) && (paramArrayOfInt[1] == this.mQuad2) && (paramArrayOfInt[2] == this.mQuad3);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/sym/Name3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */