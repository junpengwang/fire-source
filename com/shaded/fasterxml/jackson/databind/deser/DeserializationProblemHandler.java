/*    */ package com.shaded.fasterxml.jackson.databind.deser;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import java.io.IOException;
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
/*    */ public abstract class DeserializationProblemHandler
/*    */ {
/*    */   public boolean handleUnknownProperty(DeserializationContext paramDeserializationContext, JsonParser paramJsonParser, JsonDeserializer<?> paramJsonDeserializer, Object paramObject, String paramString)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/DeserializationProblemHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */