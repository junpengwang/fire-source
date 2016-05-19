/*    */ package com.shaded.fasterxml.jackson.core;
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
/*    */ public enum JsonEncoding
/*    */ {
/* 19 */   UTF8("UTF-8", false), 
/* 20 */   UTF16_BE("UTF-16BE", true), 
/* 21 */   UTF16_LE("UTF-16LE", false), 
/* 22 */   UTF32_BE("UTF-32BE", true), 
/* 23 */   UTF32_LE("UTF-32LE", false);
/*    */   
/*    */ 
/*    */   protected final String _javaName;
/*    */   
/*    */   protected final boolean _bigEndian;
/*    */   
/*    */   private JsonEncoding(String paramString, boolean paramBoolean)
/*    */   {
/* 32 */     this._javaName = paramString;
/* 33 */     this._bigEndian = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getJavaName()
/*    */   {
/* 41 */     return this._javaName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isBigEndian()
/*    */   {
/* 51 */     return this._bigEndian;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonEncoding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */