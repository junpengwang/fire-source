/*    */ package com.shaded.fasterxml.jackson.core.sym;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NameN
/*    */   extends Name
/*    */ {
/*    */   final int[] mQuads;
/*    */   
/*    */   final int mQuadLen;
/*    */   
/*    */ 
/*    */   NameN(String paramString, int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*    */   {
/* 15 */     super(paramString, paramInt1);
/*    */     
/*    */ 
/*    */ 
/* 19 */     if (paramInt2 < 3) {
/* 20 */       throw new IllegalArgumentException("Qlen must >= 3");
/*    */     }
/* 22 */     this.mQuads = paramArrayOfInt;
/* 23 */     this.mQuadLen = paramInt2;
/*    */   }
/*    */   
/*    */   public boolean equals(int paramInt)
/*    */   {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int paramInt1, int paramInt2) {
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(int[] paramArrayOfInt, int paramInt)
/*    */   {
/* 37 */     if (paramInt != this.mQuadLen) {
/* 38 */       return false;
/*    */     }
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
/* 61 */     for (int i = 0; i < paramInt; i++) {
/* 62 */       if (paramArrayOfInt[i] != this.mQuads[i]) {
/* 63 */         return false;
/*    */       }
/*    */     }
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/sym/NameN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */