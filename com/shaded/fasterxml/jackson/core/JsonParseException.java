/*    */ package com.shaded.fasterxml.jackson.core;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsonParseException
/*    */   extends JsonProcessingException
/*    */ {
/*    */   static final long serialVersionUID = 123L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public JsonParseException(String paramString, JsonLocation paramJsonLocation)
/*    */   {
/* 20 */     super(paramString, paramJsonLocation);
/*    */   }
/*    */   
/*    */   public JsonParseException(String paramString, JsonLocation paramJsonLocation, Throwable paramThrowable)
/*    */   {
/* 25 */     super(paramString, paramJsonLocation, paramThrowable);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonParseException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */