/*    */ package com.shaded.fasterxml.jackson.core.sym;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Name2
/*    */   extends Name
/*    */ {
/*    */   final int mQuad1;
/*    */   
/*    */ 
/*    */ 
/*    */   final int mQuad2;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   Name2(String paramString, int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 21 */     super(paramString, paramInt1);
/* 22 */     this.mQuad1 = paramInt2;
/* 23 */     this.mQuad2 = paramInt3;
/*    */   }
/*    */   
/*    */   public boolean equals(int paramInt) {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int paramInt1, int paramInt2)
/*    */   {
/* 32 */     return (paramInt1 == this.mQuad1) && (paramInt2 == this.mQuad2);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean equals(int[] paramArrayOfInt, int paramInt)
/*    */   {
/* 38 */     return (paramInt == 2) && (paramArrayOfInt[0] == this.mQuad1) && (paramArrayOfInt[1] == this.mQuad2);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/sym/Name2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */