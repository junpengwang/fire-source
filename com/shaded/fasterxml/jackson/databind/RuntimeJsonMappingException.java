/*    */ package com.shaded.fasterxml.jackson.databind;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RuntimeJsonMappingException
/*    */   extends RuntimeException
/*    */ {
/*    */   public RuntimeJsonMappingException(JsonMappingException paramJsonMappingException)
/*    */   {
/* 11 */     super(paramJsonMappingException);
/*    */   }
/*    */   
/*    */   public RuntimeJsonMappingException(String paramString) {
/* 15 */     super(paramString);
/*    */   }
/*    */   
/*    */   public RuntimeJsonMappingException(String paramString, JsonMappingException paramJsonMappingException) {
/* 19 */     super(paramString, paramJsonMappingException);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/RuntimeJsonMappingException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */