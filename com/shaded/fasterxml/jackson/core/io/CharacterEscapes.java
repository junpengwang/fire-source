/*    */ package com.shaded.fasterxml.jackson.core.io;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.SerializableString;
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
/*    */ 
/*    */ 
/*    */ public abstract class CharacterEscapes
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int ESCAPE_NONE = 0;
/*    */   public static final int ESCAPE_STANDARD = -1;
/*    */   public static final int ESCAPE_CUSTOM = -2;
/*    */   
/*    */   public abstract int[] getEscapeCodesForAscii();
/*    */   
/*    */   public abstract SerializableString getEscapeSequence(int paramInt);
/*    */   
/*    */   public static int[] standardAsciiEscapesForJSON()
/*    */   {
/* 67 */     int[] arrayOfInt1 = CharTypes.get7BitOutputEscapes();
/* 68 */     int i = arrayOfInt1.length;
/* 69 */     int[] arrayOfInt2 = new int[i];
/* 70 */     System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, arrayOfInt1.length);
/* 71 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/CharacterEscapes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */